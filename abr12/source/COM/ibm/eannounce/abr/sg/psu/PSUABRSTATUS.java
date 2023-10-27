/*      */ package COM.ibm.eannounce.abr.sg.psu;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.DeleteActionItem;
/*      */ import COM.ibm.eannounce.objects.EANActionItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaFoundation;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.LinkActionItem;
/*      */ import COM.ibm.eannounce.objects.WorkflowException;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.Attribute;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.objects.Text;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
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
/*      */ import java.util.Comparator;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
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
/*      */ public class PSUABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*      */   private static final int MAXFILE_SIZE = 5000000;
/*  185 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*      */ 
/*      */   
/*  188 */   static final int UPDATE_SIZE = Integer.parseInt(ABRServerProperties.getValue("PSUABRSTATUS", "_UpdateSize", "200"));
/*      */   
/*      */   static final String UPDATE_CLASS = "Update Class";
/*      */   
/*      */   static final String UPDATE_ENTITYTYPE = "Entity Type";
/*      */   
/*      */   static final String UPDATE_ENTITYID = "Entity ID";
/*      */   
/*      */   static final String UPDATE_ATTRCODE = "Update Attribute Code";
/*      */   
/*      */   static final String UPDATE_ATTRTYPE = "Attribute Type";
/*      */   
/*      */   static final String UPDATE_ATTRACT = "Attribute Update Action";
/*      */   
/*      */   static final String UPDATE_ATTRVAL = "Update Attribute Value";
/*      */   
/*      */   static final String UPDATE_REF_ENTITYTYPE = "Entity Type Referenced";
/*      */   
/*      */   static final String UPDATE_REF_ENTITYID = "Entity ID Referenced";
/*      */   
/*      */   static final String UPDATE_RELTYPE = "Relator";
/*      */   
/*      */   static final String UPDATE_RELACT = "Relator Action";
/*      */   
/*      */   private static final String PSUPROGRESS_Chunking = "PSUPRG2";
/*      */   
/*      */   private static final String PSUPROGRESS_Complete = "PSUPRG3";
/*      */   
/*      */   private static final String PSUCRITERIA_View = "VIEW";
/*      */   
/*      */   private static final String PSUCRITERIA_List = "LIST";
/*      */   static final String PSUCLASS_Reference = "PSUC2";
/*      */   static final String PSUCLASS_Update = "PSUC1";
/*      */   static final String PSUATTRACTION_N = "N";
/*      */   static final String PSUATTRACTION_D = "D";
/*  223 */   private static final String[] PDHUPDATE_ATTRS = new String[] { "PSUDESCRIPTION", "CAT2", "PSUCRITERIA", "PSUVIEW", "PSUDBTYPE", "PSULAST", "PSUMAX", "PSUHIGHENTITYTYPE", "PSUHIGHENTITYID", "PSUPROGRESS", "PDHDOMAIN" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  229 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*  231 */   private StringBuffer rptSb = new StringBuffer();
/*  232 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  234 */   private ResourceBundle rsBundle = null;
/*  235 */   private Hashtable navMetaTbl = new Hashtable<>();
/*  236 */   private StringBuffer updatedSB = new StringBuffer();
/*  237 */   private String navName = "";
/*  238 */   private String cat2 = null;
/*  239 */   private PrintWriter dbgPw = null;
/*  240 */   private String dbgfn = null;
/*  241 */   private String psucriteria = "";
/*  242 */   private int dbgLen = 0;
/*  243 */   private int abr_debuglvl = 0;
/*  244 */   private ControlBlock cbOff = null;
/*  245 */   private Vector vctReturnsEntityKeys = new Vector();
/*  246 */   private Hashtable m_typeTbl = new Hashtable<>();
/*  247 */   private Hashtable actionItemTbl = null;
/*      */   private String currentPSUHIGHENTITYTYPE;
/*      */   private String currentPSUPROGRESS;
/*      */   private int currentPSUHIGHENTITYID;
/*  251 */   private Profile poweruserProf = null;
/*  252 */   private Hashtable updatedRootTbl = new Hashtable<>();
/*  253 */   private ExtractActionItem xai = null;
/*  254 */   private int psuLast = 0;
/*  255 */   private AttrComparator attrComp = new AttrComparator();
/*      */   
/*      */   private void setupPrintWriter() {
/*  258 */     String str = this.m_abri.getFileName();
/*  259 */     int i = str.lastIndexOf(".");
/*  260 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*      */     try {
/*  262 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  263 */     } catch (Exception exception) {
/*  264 */       this.dbgfn = null;
/*  265 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriter() {
/*  269 */     if (this.dbgPw != null) {
/*  270 */       this.dbgPw.flush();
/*  271 */       this.dbgPw.close();
/*  272 */       this.dbgPw = null;
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
/*      */   public void execute_run() {
/*  337 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  339 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  350 */     String str3 = "";
/*  351 */     EntityItem entityItem = null;
/*      */     
/*  353 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  356 */       long l = System.currentTimeMillis();
/*  357 */       start_ABRBuild();
/*      */       
/*  359 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */       
/*  361 */       setupPrintWriter();
/*      */       
/*  363 */       this.m_prof.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */ 
/*      */       
/*  366 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  368 */       entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  370 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " extract: " + this.m_abri
/*  371 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  374 */       this.navName = getNavigationName(entityItem);
/*      */       
/*  376 */       this.cat2 = PokUtils.getAttributeValue(entityItem, "CAT2", ",", null, false);
/*      */       
/*  378 */       this.psuLast = Integer.parseInt(PokUtils.getAttributeValue(entityItem, "PSULAST", "", "0", false));
/*      */ 
/*      */       
/*  381 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  386 */       this.currentPSUHIGHENTITYTYPE = PokUtils.getAttributeValue(entityItem, "PSUHIGHENTITYTYPE", "", null, false);
/*  387 */       this.currentPSUPROGRESS = PokUtils.getAttributeFlagValue(entityItem, "PSUPROGRESS");
/*  388 */       this.currentPSUHIGHENTITYID = Integer.parseInt(PokUtils.getAttributeValue(entityItem, "PSUHIGHENTITYID", "", "-1", false));
/*      */ 
/*      */       
/*  391 */       addDebug(entityItem.getKey() + " PSUPROGRESS: " + this.currentPSUPROGRESS + " PSUHIGHENTITYTYPE: " + this.currentPSUHIGHENTITYTYPE + " PSUHIGHENTITYID: " + this.currentPSUHIGHENTITYID + " CAT2: " + this.cat2 + " psuLast " + this.psuLast);
/*      */ 
/*      */ 
/*      */       
/*  395 */       if ("PSUPRG3".equals(this.currentPSUPROGRESS)) {
/*      */ 
/*      */         
/*  398 */         this.args[0] = getLD_Value(entityItem, "PSUPROGRESS");
/*  399 */         addMessage("", "COMPLETED_ERR", this.args);
/*      */       } else {
/*      */         
/*  402 */         this.psucriteria = PokUtils.getAttributeFlagValue(entityItem, "PSUCRITERIA");
/*  403 */         addDebug(entityItem.getKey() + " PSUCRITERIA: " + this.psucriteria);
/*  404 */         if (this.psucriteria == null) {
/*      */           
/*  406 */           this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  407 */           this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "PSUCRITERIA", "PSUCRITERIA");
/*  408 */           addError("REQ_NOTPOPULATED_ERR", this.args);
/*      */         
/*      */         }
/*  411 */         else if ("VIEW".equals(this.psucriteria)) {
/*      */           
/*  413 */           PSUView pSUView = new PSUView(this, entityItem);
/*  414 */           pSUView.execute(this.m_elist.getEntityGroup("PDHUPDATEACT"));
/*  415 */           pSUView.dereference();
/*  416 */         } else if ("LIST".equals(this.psucriteria)) {
/*      */           
/*  418 */           PSUList pSUList = new PSUList(this, entityItem);
/*  419 */           pSUList.execute(this.m_elist.getEntityGroup("PDHUPDATEACT"));
/*  420 */           pSUList.dereference();
/*      */         } else {
/*      */           
/*  423 */           this.args[0] = getLD_Value(entityItem, "PSUCRITERIA");
/*  424 */           addError("CRITERIA_NOTSUPP_ERR", this.args);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  429 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  431 */     catch (Throwable throwable) {
/*  432 */       StringWriter stringWriter = new StringWriter();
/*  433 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  434 */       String str6 = "<pre>{0}</pre>";
/*  435 */       MessageFormat messageFormat1 = new MessageFormat(str5);
/*  436 */       setReturnCode(-3);
/*  437 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  439 */       this.args[0] = throwable.getMessage();
/*  440 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  441 */       messageFormat1 = new MessageFormat(str6);
/*  442 */       this.args[0] = stringWriter.getBuffer().toString();
/*      */       
/*  444 */       this.rptSb.append(getAllUpdateInfo());
/*      */       
/*  446 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  447 */       logError("Exception: " + throwable.getMessage());
/*  448 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*      */       
/*  451 */       setDGTitle(this.navName);
/*  452 */       setDGRptName(getShortClassName(getClass()));
/*  453 */       setDGRptClass(getABRCode());
/*      */       
/*  455 */       if (this.cat2 != null) {
/*      */         
/*  457 */         StringTokenizer stringTokenizer = new StringTokenizer(this.cat2, ",");
/*  458 */         String[] arrayOfString = new String[stringTokenizer.countTokens()];
/*  459 */         boolean bool = false;
/*  460 */         while (stringTokenizer.hasMoreTokens()) {
/*  461 */           arrayOfString[bool] = stringTokenizer.nextToken();
/*      */         }
/*  463 */         setDGCat2(arrayOfString);
/*      */       } 
/*      */       
/*  466 */       if (!isReadOnly()) {
/*  467 */         clearSoftLock();
/*      */       }
/*  469 */       closePrintWriter();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  474 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  475 */     this.args[0] = getDescription();
/*  476 */     this.args[1] = this.navName;
/*  477 */     String str4 = messageFormat.format(this.args);
/*  478 */     messageFormat = new MessageFormat(str2);
/*  479 */     this.args[0] = this.m_prof.getOPName();
/*  480 */     this.args[1] = this.m_prof.getRoleDescription();
/*  481 */     this.args[2] = this.m_prof.getWGName();
/*  482 */     this.args[3] = getNow();
/*  483 */     this.args[4] = this.navName;
/*  484 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  485 */     this.args[6] = str3 + " " + getABRVersion();
/*      */     
/*  487 */     this.rptSb.append(getAllUpdateInfo());
/*      */     
/*  489 */     restoreXtraContent();
/*      */     
/*  491 */     this.rptSb.insert(0, str4 + messageFormat.format(this.args) + NEWLINE + getRequestInfo(entityItem));
/*      */     
/*  493 */     println(this.rptSb.toString());
/*  494 */     printDGSubmitString();
/*  495 */     println(EACustom.getTOUDiv());
/*  496 */     buildReportFooter();
/*      */     
/*  498 */     this.navMetaTbl.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getRequestInfo(EntityItem paramEntityItem) {
/*  509 */     if (paramEntityItem == null) {
/*  510 */       return "";
/*      */     }
/*  512 */     StringBuffer stringBuffer = new StringBuffer("<table width='600'>" + NEWLINE);
/*  513 */     stringBuffer.append("<tr><th>Attribute</th><th>Entry Value</th><th>Exit Value</th></tr>" + NEWLINE);
/*  514 */     stringBuffer.append("<tr><td colspan='3'><b>" + paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.navName + "</b></td></tr>" + NEWLINE);
/*      */ 
/*      */     
/*  517 */     for (byte b = 0; b < PDHUPDATE_ATTRS.length; b++) {
/*  518 */       String str1 = PDHUPDATE_ATTRS[b];
/*  519 */       String str2 = PokUtils.getAttributeValue(paramEntityItem, str1, ", ", "<em>** Not Populated **</em>");
/*  520 */       stringBuffer.append("<tr><td>" + 
/*  521 */           PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), str1, str1) + ": </td><td>" + str2 + "</td><td>" + 
/*  522 */           getExitValue(str2, str1) + "</td></tr>" + NEWLINE);
/*      */     } 
/*      */ 
/*      */     
/*  526 */     stringBuffer.append("</table><br />" + NEWLINE);
/*      */     
/*  528 */     return stringBuffer.toString();
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
/*      */   void addUpdateInfo(Hashtable paramHashtable) {
/*  548 */     this.updatedSB.append("<tr><td>" + getUpdateValue(paramHashtable, "Update Class") + "</td><td>" + 
/*  549 */         getUpdateValue(paramHashtable, "Entity Type") + "</td><td>" + 
/*  550 */         getUpdateValue(paramHashtable, "Entity ID") + "</td><td>" + 
/*  551 */         getUpdateValue(paramHashtable, "Update Attribute Code") + "</td>");
/*  552 */     this.updatedSB.append("<td>" + getUpdateValue(paramHashtable, "Attribute Type") + "</td><td>" + 
/*  553 */         getUpdateValue(paramHashtable, "Attribute Update Action") + "</td><td>" + 
/*  554 */         getUpdateValue(paramHashtable, "Update Attribute Value") + "</td>");
/*  555 */     this.updatedSB.append("<td>" + getUpdateValue(paramHashtable, "Entity Type Referenced") + "</td><td>" + 
/*  556 */         getUpdateValue(paramHashtable, "Entity ID Referenced") + "</td><td>" + 
/*  557 */         getUpdateValue(paramHashtable, "Relator") + "</td><td>" + 
/*  558 */         getUpdateValue(paramHashtable, "Relator Action") + "</td></tr>" + NEWLINE);
/*  559 */     paramHashtable.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getUpdateValue(Hashtable paramHashtable, String paramString) {
/*  568 */     String str = "&nbsp;";
/*  569 */     if (paramHashtable.containsKey(paramString)) {
/*  570 */       String str1 = paramHashtable.get(paramString).toString();
/*  571 */       if (str1.trim().length() > 0) {
/*  572 */         str = str1;
/*      */       }
/*      */     } 
/*      */     
/*  576 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getExitValue(String paramString1, String paramString2) {
/*  585 */     String str = paramString1;
/*  586 */     if (this.updatedRootTbl.containsKey(paramString2)) {
/*  587 */       String str1 = this.updatedRootTbl.get(paramString2).toString();
/*  588 */       if (str1.trim().length() > 0) {
/*  589 */         str = str1;
/*      */       }
/*      */     } 
/*      */     
/*  593 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getAllUpdateInfo() {
/*  600 */     StringBuffer stringBuffer = new StringBuffer();
/*  601 */     if (this.updatedSB.length() > 0) {
/*      */       
/*  603 */       stringBuffer.append("<table><tr><th>Update Class</th><th>Entity Type</th><th>Entity ID</th><th>Update Attribute Code</th>");
/*  604 */       stringBuffer.append("<th>Attribute Type</th><th>Attribute Update Action</th><th>Update Attribute Value</th>");
/*  605 */       stringBuffer.append("<th>Entity Type Referenced</th><th>Entity ID Referenced</th><th>Relator</th><th>Relator Action</th></tr>" + NEWLINE);
/*      */       
/*  607 */       stringBuffer.append(this.updatedSB.toString());
/*      */       
/*  609 */       stringBuffer.append("</table>" + NEWLINE);
/*  610 */       this.updatedSB.setLength(0);
/*      */     } 
/*      */     
/*  613 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private void restoreXtraContent() {
/*  617 */     if (this.dbgfn != null && this.dbgLen + this.rptSb.length() < 5000000) {
/*      */       
/*  619 */       BufferedInputStream bufferedInputStream = null;
/*  620 */       FileInputStream fileInputStream = null;
/*  621 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  623 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  624 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  626 */         String str = null;
/*  627 */         StringBuffer stringBuffer = new StringBuffer();
/*  628 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  630 */         while ((str = bufferedReader.readLine()) != null) {
/*  631 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  633 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  636 */         File file = new File(this.dbgfn);
/*  637 */         if (file.exists()) {
/*  638 */           file.delete();
/*      */         }
/*  640 */       } catch (Exception exception) {
/*  641 */         exception.printStackTrace();
/*      */       } finally {
/*  643 */         if (bufferedInputStream != null) {
/*      */           try {
/*  645 */             bufferedInputStream.close();
/*  646 */           } catch (Exception exception) {
/*  647 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  650 */         if (fileInputStream != null) {
/*      */           try {
/*  652 */             fileInputStream.close();
/*  653 */           } catch (Exception exception) {
/*  654 */             exception.printStackTrace();
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
/*      */   void doUpdates(EntityItem paramEntityItem, OPICMList paramOPICMList, boolean paramBoolean) throws RemoteException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException {
/*  676 */     int i = 0;
/*      */     
/*  678 */     for (byte b = 0; b < paramOPICMList.size(); b++) {
/*  679 */       PSUUpdateData pSUUpdateData = (PSUUpdateData)paramOPICMList.getAt(b);
/*  680 */       addDebug(3, "doUpdates[" + b + "]: " + pSUUpdateData.hashkey());
/*      */       
/*  682 */       if (pSUUpdateData instanceof PSUDeleteData) {
/*  683 */         i += deleteData((PSUDeleteData)pSUUpdateData);
/*  684 */         pSUUpdateData.outputUserInfo();
/*  685 */       } else if (pSUUpdateData instanceof PSULinkData) {
/*  686 */         pSUUpdateData.outputUserInfo();
/*  687 */         createLinks((PSULinkData)pSUUpdateData);
/*      */       } else {
/*  689 */         pSUUpdateData.outputUserInfo();
/*  690 */         pSUUpdateData.removeAttrs();
/*  691 */         if (pSUUpdateData.rek.m_vctAttributes != null && pSUUpdateData.rek.m_vctAttributes.size() > 0) {
/*      */           
/*  693 */           Collections.sort(pSUUpdateData.rek.m_vctAttributes, this.attrComp);
/*      */ 
/*      */           
/*  696 */           this.vctReturnsEntityKeys.addElement(pSUUpdateData.rek);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  701 */     String str = null;
/*  702 */     int j = 0;
/*  703 */     if (paramOPICMList.size() > 0) {
/*  704 */       PSUUpdateData pSUUpdateData = (PSUUpdateData)paramOPICMList.getAt(paramOPICMList.size() - 1);
/*  705 */       str = pSUUpdateData.getEntityType();
/*  706 */       j = pSUUpdateData.getHighEntityId();
/*      */     } 
/*      */ 
/*      */     
/*  710 */     saveHighEntity(paramEntityItem, str, j, paramBoolean, paramOPICMList
/*  711 */         .size() + i);
/*      */     
/*  713 */     updatePDH();
/*      */ 
/*      */     
/*  716 */     while (paramOPICMList.size() > 0) {
/*  717 */       PSUUpdateData pSUUpdateData = (PSUUpdateData)paramOPICMList.remove(0);
/*  718 */       pSUUpdateData.dereference();
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
/*      */   private void createLinks(PSULinkData paramPSULinkData) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/*  738 */     addDebug(4, "createLinks: entered psulink: " + paramPSULinkData);
/*      */     
/*  740 */     if (this.actionItemTbl == null) {
/*  741 */       this.actionItemTbl = new Hashtable<>();
/*      */     }
/*      */     
/*  744 */     EntityItem[] arrayOfEntityItem = { new EntityItem(null, this.m_prof, paramPSULinkData.getEntityType(), paramPSULinkData.getEntityId()) };
/*      */ 
/*      */     
/*  747 */     for (byte b = 0; b < paramPSULinkData.getChildrenList().size(); b++) {
/*  748 */       PSUChildList pSUChildList = (PSUChildList)paramPSULinkData.getChildrenList().getAt(b);
/*  749 */       LinkActionItem linkActionItem = (LinkActionItem)this.actionItemTbl.get(pSUChildList.getActionName());
/*  750 */       if (linkActionItem == null) {
/*  751 */         linkActionItem = new LinkActionItem(null, this.m_db, this.m_prof, pSUChildList.getActionName());
/*  752 */         if (linkActionItem.getMetaLink() == null)
/*      */         {
/*  754 */           throw new MiddlewareException("Linkaction " + pSUChildList.getActionName() + " is undefined.");
/*      */         }
/*  756 */         this.actionItemTbl.put(pSUChildList.getActionName(), linkActionItem);
/*      */       } 
/*      */       
/*  759 */       EntityItem[] arrayOfEntityItem1 = new EntityItem[pSUChildList.getChildrenList().size()];
/*      */ 
/*      */       
/*  762 */       for (byte b1 = 0; b1 < pSUChildList.getChildrenList().size(); b1++) {
/*  763 */         PSUUpdateData pSUUpdateData = (PSUUpdateData)pSUChildList.getChildrenList().getAt(b1);
/*  764 */         arrayOfEntityItem1[b1] = new EntityItem(null, this.m_prof, pSUUpdateData.getEntityType(), pSUUpdateData.getEntityId());
/*  765 */         addDebug(4, "createLinks: psuchild: " + pSUUpdateData + " psuRelatorAct: " + pSUChildList
/*  766 */             .getActionName());
/*      */       } 
/*      */ 
/*      */       
/*  770 */       if (linkActionItem.isOppSelect()) {
/*  771 */         linkActionItem.setParentEntityItems(arrayOfEntityItem1);
/*  772 */         linkActionItem.setChildEntityItems(arrayOfEntityItem);
/*      */       } else {
/*  774 */         linkActionItem.setParentEntityItems(arrayOfEntityItem);
/*  775 */         linkActionItem.setChildEntityItems(arrayOfEntityItem1);
/*      */       } 
/*  777 */       Vector<OPICMList> vector = linkActionItem.executeLink(this.m_db, this.m_prof);
/*  778 */       OPICMList oPICMList = vector.elementAt(1);
/*  779 */       for (byte b2 = 0; b2 < oPICMList.size(); b2++) {
/*  780 */         ReturnRelatorKey returnRelatorKey = (ReturnRelatorKey)oPICMList.getAt(b2);
/*  781 */         addDebug(4, "createLinks:  ReturnRelatorKey[" + b2 + "]: " + returnRelatorKey);
/*  782 */         String str = returnRelatorKey.m_strEntity2Type + returnRelatorKey.m_iEntity2ID;
/*  783 */         if (linkActionItem.isOppSelect()) {
/*  784 */           str = returnRelatorKey.m_strEntity1Type + returnRelatorKey.m_iEntity1ID;
/*      */         }
/*  786 */         PSUUpdateData pSUUpdateData = (PSUUpdateData)pSUChildList.getChildrenList().get(str);
/*  787 */         if (pSUUpdateData.rek != null && pSUUpdateData.rek.m_vctAttributes != null) {
/*      */           
/*  789 */           pSUUpdateData.rek.m_iEntityID = returnRelatorKey.getReturnID();
/*  790 */           pSUUpdateData.rek.m_strEntityType = returnRelatorKey.getEntityType();
/*  791 */           pSUUpdateData.setRelatorKey(returnRelatorKey.getEntityType() + returnRelatorKey.getReturnID());
/*  792 */           for (byte b3 = 0; b3 < pSUUpdateData.rek.m_vctAttributes.size(); b3++) {
/*  793 */             Attribute attribute = pSUUpdateData.rek.m_vctAttributes.elementAt(b3);
/*  794 */             attribute.m_iEntityID = returnRelatorKey.getReturnID();
/*  795 */             attribute.m_strEntityType = returnRelatorKey.getEntityType();
/*      */           } 
/*      */           
/*  798 */           if (pSUUpdateData.rek.m_vctAttributes.size() > 0) {
/*  799 */             Collections.sort(pSUUpdateData.rek.m_vctAttributes, this.attrComp);
/*      */           }
/*  801 */           addDebug(4, "createLinks: new relator: " + pSUUpdateData);
/*  802 */           this.vctReturnsEntityKeys.addElement(pSUUpdateData.rek);
/*      */         } 
/*      */       } 
/*      */     } 
/*  806 */     paramPSULinkData.outputUserInfoWithRelator();
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
/*      */   private int deleteData(PSUDeleteData paramPSUDeleteData) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  831 */     addDebug(4, "deleteData: entered psudelete: " + paramPSUDeleteData);
/*      */     
/*  833 */     if (this.actionItemTbl == null) {
/*  834 */       this.actionItemTbl = new Hashtable<>();
/*      */     }
/*  836 */     DeleteActionItem deleteActionItem = (DeleteActionItem)this.actionItemTbl.get(paramPSUDeleteData.actionName);
/*  837 */     if (deleteActionItem == null) {
/*  838 */       deleteActionItem = new DeleteActionItem(null, this.m_db, this.m_prof, paramPSUDeleteData.actionName);
/*  839 */       if (deleteActionItem.getEntityType() == null)
/*      */       {
/*  841 */         throw new MiddlewareException("Deleteaction " + paramPSUDeleteData.actionName + " is undefined.");
/*      */       }
/*      */       
/*  844 */       this.actionItemTbl.put(paramPSUDeleteData.actionName, deleteActionItem);
/*      */     } 
/*  846 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramPSUDeleteData.idVct.size()];
/*  847 */     for (byte b = 0; b < paramPSUDeleteData.idVct.size(); b++) {
/*  848 */       arrayOfEntityItem[b] = new EntityItem(null, this.m_prof, paramPSUDeleteData.getEntityType(), ((Integer)paramPSUDeleteData.idVct
/*  849 */           .elementAt(b)).intValue());
/*      */     }
/*      */     
/*      */     try {
/*  853 */       deleteActionItem.setEntityItems(arrayOfEntityItem);
/*      */       
/*  855 */       deleteActionItem.executeAction(this.m_db, this.m_prof);
/*      */     } finally {
/*      */       
/*  858 */       this.m_db.commit();
/*  859 */       this.m_db.freeStatement();
/*  860 */       this.m_db.isPending("finally after deleteData");
/*      */     } 
/*      */     
/*  863 */     return paramPSUDeleteData.idVct.size() - 1;
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
/*      */   private void saveHighEntity(EntityItem paramEntityItem, String paramString, int paramInt1, boolean paramBoolean, int paramInt2) {
/*  879 */     addDebug(3, "saveHighEntity: highEntityType: " + paramString + " highEntityId: " + paramInt1 + " numProcessed: " + paramInt2 + " allProcessed: " + paramBoolean);
/*      */ 
/*      */     
/*  882 */     if (this.m_cbOn == null) {
/*  883 */       setControlBlock();
/*      */     }
/*      */     
/*  886 */     if (paramString != null && !paramString.equals(this.currentPSUHIGHENTITYTYPE)) {
/*  887 */       setTextValue(paramEntityItem, "PSUHIGHENTITYTYPE", paramString);
/*  888 */       this.updatedRootTbl.put("PSUHIGHENTITYTYPE", paramString);
/*      */       
/*  890 */       this.currentPSUHIGHENTITYTYPE = paramString;
/*      */     } 
/*      */     
/*  893 */     if (paramInt1 > 0) {
/*  894 */       setTextValue(paramEntityItem, "PSUHIGHENTITYID", "" + paramInt1);
/*  895 */       this.updatedRootTbl.put("PSUHIGHENTITYID", "" + paramInt1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  900 */     String str = paramBoolean ? "PSUPRG3" : "PSUPRG2";
/*  901 */     if (!str.equals(this.currentPSUPROGRESS)) {
/*  902 */       setUniqueFlagValue(paramEntityItem, "PSUPROGRESS", str);
/*  903 */       this.currentPSUPROGRESS = str;
/*  904 */       this.updatedRootTbl.put("PSUPROGRESS", paramBoolean ? "Complete" : "Chunking");
/*      */     } 
/*      */ 
/*      */     
/*  908 */     if (paramInt2 > 0) {
/*  909 */       addDebug(3, "saveHighEntity: before psuLast: " + this.psuLast);
/*      */       
/*  911 */       this.psuLast += paramInt2;
/*      */       
/*  913 */       setTextValue(paramEntityItem, "PSULAST", "" + this.psuLast);
/*  914 */       this.updatedRootTbl.put("PSULAST", "" + this.psuLast);
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
/*      */   boolean wasPreviouslyProcessed(String paramString, int paramInt) {
/*  929 */     if (this.currentPSUHIGHENTITYTYPE == null) {
/*  930 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  934 */     if (paramString.compareTo(this.currentPSUHIGHENTITYTYPE) < 0) {
/*  935 */       addDebug(4, "wasPreviouslyProcessed psuEntityType: " + paramString + " is before highEntityType: " + this.currentPSUHIGHENTITYTYPE);
/*  936 */       return true;
/*      */     } 
/*  938 */     if (paramString.equals(this.currentPSUHIGHENTITYTYPE) && paramInt <= this.currentPSUHIGHENTITYID) {
/*  939 */       addDebug(4, "wasPreviouslyProcessed psuEntityId: " + paramInt + " is before or equal highEntityId: " + this.currentPSUHIGHENTITYID);
/*  940 */       return true;
/*      */     } 
/*      */     
/*  943 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getAttrType(String paramString) {
/*  952 */     return (String)this.m_typeTbl.get(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getAttrAction(ControlBlock paramControlBlock) {
/*  961 */     return (paramControlBlock == this.m_cbOn) ? "N" : "D";
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
/*      */   void getCurrentValues(OPICMList paramOPICMList) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  974 */     Vector<EntityItem> vector = new Vector();
/*      */     
/*  976 */     Profile profile = this.m_prof;
/*  977 */     if (this.poweruserProf == null) {
/*  978 */       getPowerUserRole();
/*      */     }
/*  980 */     if (this.poweruserProf != null) {
/*  981 */       profile = this.poweruserProf;
/*      */     }
/*      */     
/*  984 */     if (this.xai == null) {
/*  985 */       this.xai = new ExtractActionItem(null, this.m_db, profile, "dummy") { private static final long serialVersionUID = 1L;
/*      */           
/*      */           public String getPurpose() {
/*  988 */             return "Edit";
/*      */           } }
/*      */         ;
/*      */     }
/*  992 */     for (byte b1 = 0; b1 < paramOPICMList.size(); b1++) {
/*  993 */       PSUUpdateData pSUUpdateData = (PSUUpdateData)paramOPICMList.getAt(b1);
/*  994 */       addDebug(3, "getCurrentValues: needed[" + b1 + "] " + pSUUpdateData);
/*  995 */       vector.add(new EntityItem(null, profile, pSUUpdateData.getEntityType(), pSUUpdateData.getEntityId()));
/*      */     } 
/*      */     
/*  998 */     EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/*  999 */     vector.copyInto((Object[])arrayOfEntityItem);
/*      */     
/* 1001 */     EntityList entityList = this.m_db.getEntityList(profile, this.xai, arrayOfEntityItem);
/* 1002 */     EntityGroup entityGroup = entityList.getParentEntityGroup();
/*      */ 
/*      */     
/* 1005 */     for (byte b2 = 0; b2 < paramOPICMList.size(); b2++) {
/* 1006 */       PSUUpdateData pSUUpdateData = (PSUUpdateData)paramOPICMList.getAt(b2);
/* 1007 */       EntityItem entityItem = entityGroup.getEntityItem(pSUUpdateData.getEntityType() + pSUUpdateData.getEntityId());
/*      */       
/* 1009 */       for (byte b = 0; b < pSUUpdateData.rek.m_vctAttributes.size(); b++) {
/* 1010 */         Attribute attribute = pSUUpdateData.rek.m_vctAttributes.elementAt(b);
/* 1011 */         if (attribute.m_cbControlBlock == this.cbOff) {
/* 1012 */           HashSet<String> hashSet; EANAttribute eANAttribute; String str1 = attribute.getAttributeCode();
/* 1013 */           String str2 = getAttrType(str1);
/*      */           
/* 1015 */           String str3 = null;
/* 1016 */           char c = str2.toUpperCase().charAt(0);
/* 1017 */           switch (c) {
/*      */             case 'A':
/*      */             case 'S':
/*      */             case 'U':
/* 1021 */               str3 = PokUtils.getAttributeFlagValue(entityItem, str1);
/*      */               break;
/*      */             case 'F':
/* 1024 */               hashSet = new HashSet();
/* 1025 */               hashSet.add(attribute.getAttributeValue());
/* 1026 */               if (PokUtils.contains(entityItem, str1, hashSet)) {
/* 1027 */                 str3 = attribute.getAttributeValue();
/*      */               } else {
/* 1029 */                 str3 = null;
/*      */               } 
/* 1031 */               hashSet.clear();
/*      */               break;
/*      */             case 'T':
/* 1034 */               eANAttribute = entityItem.getAttribute(str1);
/* 1035 */               if (eANAttribute != null) {
/* 1036 */                 str3 = eANAttribute.get().toString();
/*      */               }
/*      */               break;
/*      */           } 
/*      */           
/* 1041 */           if (str3 == null) {
/* 1042 */             EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(str1);
/* 1043 */             if (eANMetaAttribute == null) {
/* 1044 */               str3 = "Not found in " + pSUUpdateData.getEntityType() + " meta";
/*      */             } else {
/* 1046 */               str3 = "Not populated";
/*      */             } 
/* 1048 */             pSUUpdateData.addRemoveAttr(attribute);
/*      */           } 
/*      */           
/* 1051 */           attribute.m_strAttributeValue = str3;
/* 1052 */           addDebug(3, "getCurrentValues: psuEntityType: " + pSUUpdateData
/* 1053 */               .getEntityType() + " psuEntityId: " + pSUUpdateData.getEntityId() + " psuAttr: " + str1 + " psuAttrType: " + str2 + " curValue: " + str3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1060 */     entityList.dereference();
/* 1061 */     entityList = null;
/* 1062 */     vector.clear();
/* 1063 */     vector = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void getPowerUserRole() {
/* 1068 */     String str = "POWERUSER";
/*      */     try {
/* 1070 */       this.poweruserProf = this.m_prof.getProfileForRoleCode(this.m_db, str, str, 1);
/* 1071 */       if (this.poweruserProf == null) {
/*      */         
/* 1073 */         this.args[0] = str;
/* 1074 */         addError("ROLE_ERR", this.args);
/*      */       } else {
/* 1076 */         addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + this.poweruserProf.getRoleCode());
/* 1077 */         this.poweruserProf.setReadLanguage(0);
/*      */       } 
/* 1079 */     } catch (Exception exception) {
/* 1080 */       exception.printStackTrace();
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
/*      */   void setAttribute(String paramString1, PSUUpdateData paramPSUUpdateData, String paramString2, String paramString3) {
/* 1093 */     if (this.m_cbOn == null) {
/* 1094 */       setControlBlock();
/*      */     }
/* 1096 */     setAttribute(paramString1, paramPSUUpdateData, paramString2, paramString3, this.m_cbOn);
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
/*      */   void deactivateAttribute(String paramString1, PSUUpdateData paramPSUUpdateData, String paramString2, String paramString3) {
/* 1108 */     if (this.cbOff == null) {
/* 1109 */       this.cbOff = new ControlBlock("1980-01-01-00.00.00.000000", "1980-01-01-00.00.00.000000", "1980-01-01-00.00.00.000000", "1980-01-01-00.00.00.000000", this.m_prof.getOPWGID());
/*      */     }
/* 1111 */     setAttribute(paramString1, paramPSUUpdateData, paramString2, paramString3, this.cbOff);
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
/*      */   private void setAttribute(String paramString1, PSUUpdateData paramPSUUpdateData, String paramString2, String paramString3, ControlBlock paramControlBlock) {
/* 1124 */     this.m_typeTbl.put(paramString2, paramString1);
/*      */     
/* 1126 */     char c = paramString1.toUpperCase().charAt(0);
/* 1127 */     switch (c) {
/*      */       case 'A':
/*      */       case 'S':
/*      */       case 'U':
/* 1131 */         paramPSUUpdateData.setUniqueFlagValue(paramString2, paramString3, paramControlBlock);
/*      */         break;
/*      */       
/*      */       case 'F':
/* 1135 */         paramPSUUpdateData.setMultiFlagValue(paramString2, paramString3, paramControlBlock);
/*      */         break;
/*      */       case 'T':
/* 1138 */         paramPSUUpdateData.setTextValue(paramString2, paramString3, paramControlBlock);
/*      */         break;
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
/*      */   private void setTextValue(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 1151 */     addDebug(4, "setTextValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */ 
/*      */     
/* 1155 */     String str = PokUtils.getAttributeValue(paramEntityItem, paramString1, "", "", false);
/* 1156 */     if (paramString2.equalsIgnoreCase(str)) {
/* 1157 */       addDebug("setTextValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/* 1158 */       logMessage("setTextValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1162 */     Vector<Text> vector = null;
/*      */     
/* 1164 */     for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1165 */       ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1166 */       if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1167 */         .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1168 */         vector = returnEntityKey.m_vctAttributes;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1172 */     if (vector == null) {
/* 1173 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1174 */       vector = new Vector();
/* 1175 */       returnEntityKey.m_vctAttributes = vector;
/* 1176 */       this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */     } 
/*      */ 
/*      */     
/* 1180 */     Text text = new Text(this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/* 1181 */     vector.addElement(text);
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
/*      */   private void setUniqueFlagValue(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 1193 */     addDebug(4, "setUniqueFlagValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */ 
/*      */     
/* 1197 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 1198 */     if (paramString2.equalsIgnoreCase(str)) {
/* 1199 */       addDebug("setUniqueFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/* 1200 */       logMessage("setUniqueFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1204 */     Vector<SingleFlag> vector = null;
/*      */     
/* 1206 */     for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1207 */       ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1208 */       if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1209 */         .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1210 */         vector = returnEntityKey.m_vctAttributes;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1214 */     if (vector == null) {
/* 1215 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1216 */       vector = new Vector();
/* 1217 */       returnEntityKey.m_vctAttributes = vector;
/* 1218 */       this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */     } 
/*      */     
/* 1221 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */ 
/*      */     
/* 1224 */     vector.addElement(singleFlag);
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
/*      */   void updatePDH() throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 1238 */     logMessage(getDescription() + " updating PDH");
/* 1239 */     addDebug(4, "updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys.size());
/*      */     
/* 1241 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       try {
/* 1243 */         this.m_db.update(this.m_prof, this.vctReturnsEntityKeys, false, false);
/*      */       } finally {
/*      */         
/* 1246 */         this.vctReturnsEntityKeys.clear();
/* 1247 */         this.m_db.commit();
/* 1248 */         this.m_db.freeStatement();
/* 1249 */         this.m_db.isPending("finally after updatePDH");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 1258 */     super.dereference();
/*      */     
/* 1260 */     if (this.xai != null) {
/* 1261 */       this.xai.dereference();
/* 1262 */       this.xai = null;
/*      */     } 
/*      */     
/* 1265 */     this.attrComp = null;
/* 1266 */     this.poweruserProf = null;
/* 1267 */     this.rsBundle = null;
/* 1268 */     this.rptSb = null;
/* 1269 */     this.args = null;
/*      */     
/* 1271 */     this.cbOff = null;
/* 1272 */     this.navMetaTbl = null;
/* 1273 */     this.navName = null;
/* 1274 */     this.psucriteria = null;
/* 1275 */     this.currentPSUHIGHENTITYTYPE = null;
/* 1276 */     this.currentPSUPROGRESS = null;
/*      */     
/* 1278 */     this.updatedRootTbl.clear();
/* 1279 */     this.updatedRootTbl = null;
/*      */     
/* 1281 */     if (this.actionItemTbl != null) {
/* 1282 */       for (Enumeration enumeration = this.actionItemTbl.keys(); enumeration.hasMoreElements(); ) {
/* 1283 */         EANActionItem eANActionItem = (EANActionItem)this.actionItemTbl.get(enumeration.nextElement());
/* 1284 */         if (eANActionItem instanceof LinkActionItem) {
/* 1285 */           LinkActionItem linkActionItem = (LinkActionItem)eANActionItem;
/* 1286 */           linkActionItem.dereference(); continue;
/* 1287 */         }  if (eANActionItem instanceof DeleteActionItem) {
/* 1288 */           DeleteActionItem deleteActionItem = (DeleteActionItem)eANActionItem;
/* 1289 */           deleteActionItem.dereference();
/*      */         } 
/*      */       } 
/* 1292 */       this.actionItemTbl.clear();
/* 1293 */       this.actionItemTbl = null;
/*      */     } 
/* 1295 */     this.m_typeTbl.clear();
/* 1296 */     this.m_typeTbl = null;
/*      */     
/* 1298 */     this.vctReturnsEntityKeys.clear();
/* 1299 */     this.vctReturnsEntityKeys = null;
/*      */     
/* 1301 */     this.dbgPw = null;
/* 1302 */     this.dbgfn = null;
/* 1303 */     this.updatedSB = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1309 */     return "$Revision: 1.2 $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1316 */     return "PSUABRSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getLD_Value(EntityItem paramEntityItem, String paramString) {
/* 1324 */     return PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString) + ": " + 
/* 1325 */       PokUtils.getAttributeValue(paramEntityItem, paramString, ",", "<em>** Not Populated **</em>", false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1331 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addDebug(int paramInt, String paramString) {
/* 1339 */     if (paramInt <= this.abr_debuglvl) {
/* 1340 */       addDebug(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addDebug(String paramString) {
/* 1348 */     if (this.dbgPw != null) {
/* 1349 */       this.dbgLen += paramString.length();
/* 1350 */       this.dbgPw.println(paramString);
/* 1351 */       this.dbgPw.flush();
/*      */     } else {
/* 1353 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/*      */   void addError(String paramString, Object[] paramArrayOfObject) {
/* 1367 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1370 */     addMessage(this.rsBundle.getString("ERROR_PREFIX"), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1379 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/* 1381 */     if (paramArrayOfObject != null) {
/* 1382 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1383 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1386 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1396 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 1400 */     EANList eANList = (EANList)this.navMetaTbl.get(paramEntityItem.getEntityType());
/* 1401 */     if (eANList == null) {
/* 1402 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1403 */       eANList = entityGroup.getMetaAttribute();
/* 1404 */       this.navMetaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1406 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1407 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1408 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1409 */       if (b + 1 < eANList.size()) {
/* 1410 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1414 */     return stringBuffer.toString().trim();
/*      */   }
/*      */   
/*      */   private class AttrComparator
/*      */     implements Comparator {
/*      */     private AttrComparator() {}
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 1422 */       Attribute attribute1 = (Attribute)param1Object1;
/* 1423 */       Attribute attribute2 = (Attribute)param1Object2;
/* 1424 */       String str1 = (String)PSUABRSTATUS.this.m_typeTbl.get(attribute1.getAttributeCode());
/* 1425 */       String str2 = (String)PSUABRSTATUS.this.m_typeTbl.get(attribute2.getAttributeCode());
/* 1426 */       if (str1.charAt(0) == 'A') {
/* 1427 */         str1 = "Z";
/*      */       }
/* 1429 */       if (str2.charAt(0) == 'A') {
/* 1430 */         str2 = "Z";
/*      */       }
/* 1432 */       if (str1.charAt(0) == 'S') {
/* 1433 */         str1 = "Y";
/*      */       }
/* 1435 */       if (str2.charAt(0) == 'S') {
/* 1436 */         str2 = "Y";
/*      */       }
/* 1438 */       return str1.compareTo(str2);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\psu\PSUABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */