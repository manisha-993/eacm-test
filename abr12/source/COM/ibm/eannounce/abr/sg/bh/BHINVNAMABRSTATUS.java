/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EANTableWrapper;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.RowSelectableTable;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.Attribute;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.objects.Text;
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
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
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
/*      */ public class BHINVNAMABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*      */   private static final int MAXFILE_SIZE = 5000000;
/*  112 */   private StringBuffer rptSb = new StringBuffer();
/*  113 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*      */   
/*      */   private static final int MAX_LEN = 30;
/*      */   private static final int UPDATE_SIZE = 200;
/*  117 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  118 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  120 */   private ResourceBundle rsBundle = null;
/*  121 */   private Hashtable metaTbl = new Hashtable<>();
/*  122 */   private String navName = "";
/*  123 */   private PrintWriter dbgPw = null;
/*  124 */   private String dbgfn = null;
/*  125 */   private int dbgLen = 0;
/*  126 */   private int abr_debuglvl = 0;
/*      */   
/*  128 */   private HashSet swfeatSet = new HashSet();
/*      */   
/*  130 */   private Set dupelistedSet = new HashSet();
/*  131 */   private Hashtable invGrpInvnameTbl = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String WG_SRCHACTION_NAME = "SRDWG";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String STATUS_FINAL = "0020";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String STATUS_RFR = "0040";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String STATUS_CHGREQ = "0050";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DQ_CHGREQ = "0050";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String BHVINVNAME_SQL = "select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE,  f2.attributevalue as STATUS from opicm.text tf join opicm.flag p on tf.entitytype=p.entitytype and tf.entityid=p.entityid join opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid left join opicm.flag f2 on tf.entitytype=f2.entitytype and tf.entityid=f2.entityid and f2.attributecode='STATUS' and f2.enterprise=? and f2.valto>current timestamp and f2.effto>current timestamp left join opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid and t.attributecode='INVNAME' and t.enterprise=? and t.valto>current timestamp and t.effto>current timestamp and t.nlsid=1 where tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and p.attributecode = 'PDHDOMAIN' and p.attributevalue=? and p.enterprise=? and f1.attributecode='INVENTORYGROUP' and f1.enterprise=? and tf.valto>current timestamp and tf.effto>current timestamp and p.valto>current timestamp and p.effto>current timestamp and f1.valto>current timestamp and f1.effto>current timestamp and not exists( select tbh.attributevalue from opicm.text tbh where tbh.entitytype=tf.entitytype and tbh.entityid=tf.entityid and tbh.attributecode='BHINVNAME' and tbh.valto>current timestamp and tbh.effto>current timestamp and tbh.nlsid=1) UNION select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE, f2.attributevalue as STATUS from opicm.text tf join opicm.flag p on tf.entitytype=p.entitytype and tf.entityid=p.entityid join opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid join opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid join opicm.text tbh on tf.entitytype=tbh.entitytype and tf.entityid=tbh.entityid left join opicm.flag f2 on tf.entitytype=f2.entitytype and tf.entityid=f2.entityid and f2.attributecode='STATUS' and f2.enterprise=? and f2.valto>current timestamp and f2.effto>current timestamp where tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and tf.nlsid=1 and p.attributecode = 'PDHDOMAIN' and p.attributevalue=? and p.enterprise=? and t.attributecode='INVNAME' and t.enterprise=? and t.nlsid=1 and f1.attributecode='INVENTORYGROUP' and f1.enterprise=? and tbh.attributecode='BHINVNAME' and tbh.enterprise=? and tbh.nlsid=1 and tf.valto>current timestamp and tf.effto>current timestamp and p.valto>current timestamp and p.effto>current timestamp and t.valto>current timestamp and t.effto>current timestamp and f1.valto>current timestamp and f1.effto>current timestamp and tbh.valto>current timestamp and tbh.effto>current timestamp and t.valfrom > tbh.valfrom order by featurecode asc with ur; ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String INVGRP_INVNAME_SQL = "select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE  from opicm.text tf join opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid left join opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid and tf.nlsid=t.nlsid and t.attributecode='INVNAME' and t.enterprise=? and t.valto>current timestamp and t.effto>current timestamp where tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and tf.nlsid=1 and f1.attributecode='INVENTORYGROUP' and f1.attributevalue=? and f1.enterprise=? and tf.valto>current timestamp and tf.effto>current timestamp and f1.valto>current timestamp and f1.effto>current timestamp order by featurecode asc with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MW_VENTITY_LIMIT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PreparedStatement invgrpPs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PreparedStatement bhinvnameStatement;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  257 */     String str = ABRServerProperties.getValue("BHINVNAMABRSTATUS", "_velimit", "5");
/*      */     
/*  259 */     MW_VENTITY_LIMIT = Integer.parseInt(str);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  264 */   private Vector vctReturnsEntityKeys = new Vector();
/*      */   
/*  266 */   private EntityGroup featureGroup = null;
/*  267 */   private Hashtable setInvNameTbl = new Hashtable<>();
/*  268 */   private StringBuffer updatedSB = new StringBuffer();
/*      */   
/*      */   private void setupPrintWriter() {
/*  271 */     String str = this.m_abri.getFileName();
/*  272 */     int i = str.lastIndexOf(".");
/*  273 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*      */     try {
/*  275 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  276 */     } catch (Exception exception) {
/*  277 */       this.dbgfn = null;
/*  278 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriter() {
/*  282 */     if (this.dbgPw != null) {
/*  283 */       this.dbgPw.flush();
/*  284 */       this.dbgPw.close();
/*  285 */       this.dbgPw = null;
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
/*      */   public void execute_run() {
/*  314 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  316 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  327 */     String str3 = "";
/*      */     
/*  329 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */     
/*      */     try {
/*  333 */       long l = System.currentTimeMillis();
/*  334 */       start_ABRBuild();
/*      */       
/*  336 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */       
/*  338 */       setupPrintWriter();
/*      */       
/*  340 */       this.m_prof.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */ 
/*      */       
/*  343 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  345 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  347 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " extract: " + this.m_abri
/*  348 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  351 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  357 */       this.navName = getNavigationName(entityItem);
/*      */       
/*  359 */       String str = PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN");
/*  360 */       addDebug(entityItem.getKey() + " pdhdomain: " + str);
/*  361 */       if (str != null) {
/*      */         
/*  363 */         String[] arrayOfString = PokUtils.convertToArray(str);
/*  364 */         if (arrayOfString.length > 1) {
/*      */           
/*  366 */           this.args[0] = getLD_Value(entityItem, "PDHDOMAIN");
/*  367 */           addError("INVALID_DOMAIN_ERR", this.args);
/*      */         } else {
/*  369 */           str = arrayOfString[0];
/*      */         } 
/*      */       } else {
/*      */         
/*  373 */         this.args[0] = getLD_Value(entityItem, "PDHDOMAIN");
/*  374 */         addError("INVALID_DOMAIN_ERR", this.args);
/*      */       } 
/*      */       
/*  377 */       if (getReturnCode() == 0) {
/*      */         
/*  379 */         this.bhinvnameStatement = this.m_db.getPDHConnection().prepareStatement("select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE,  f2.attributevalue as STATUS from opicm.text tf join opicm.flag p on tf.entitytype=p.entitytype and tf.entityid=p.entityid join opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid left join opicm.flag f2 on tf.entitytype=f2.entitytype and tf.entityid=f2.entityid and f2.attributecode='STATUS' and f2.enterprise=? and f2.valto>current timestamp and f2.effto>current timestamp left join opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid and t.attributecode='INVNAME' and t.enterprise=? and t.valto>current timestamp and t.effto>current timestamp and t.nlsid=1 where tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and p.attributecode = 'PDHDOMAIN' and p.attributevalue=? and p.enterprise=? and f1.attributecode='INVENTORYGROUP' and f1.enterprise=? and tf.valto>current timestamp and tf.effto>current timestamp and p.valto>current timestamp and p.effto>current timestamp and f1.valto>current timestamp and f1.effto>current timestamp and not exists( select tbh.attributevalue from opicm.text tbh where tbh.entitytype=tf.entitytype and tbh.entityid=tf.entityid and tbh.attributecode='BHINVNAME' and tbh.valto>current timestamp and tbh.effto>current timestamp and tbh.nlsid=1) UNION select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE, f2.attributevalue as STATUS from opicm.text tf join opicm.flag p on tf.entitytype=p.entitytype and tf.entityid=p.entityid join opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid join opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid join opicm.text tbh on tf.entitytype=tbh.entitytype and tf.entityid=tbh.entityid left join opicm.flag f2 on tf.entitytype=f2.entitytype and tf.entityid=f2.entityid and f2.attributecode='STATUS' and f2.enterprise=? and f2.valto>current timestamp and f2.effto>current timestamp where tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and tf.nlsid=1 and p.attributecode = 'PDHDOMAIN' and p.attributevalue=? and p.enterprise=? and t.attributecode='INVNAME' and t.enterprise=? and t.nlsid=1 and f1.attributecode='INVENTORYGROUP' and f1.enterprise=? and tbh.attributecode='BHINVNAME' and tbh.enterprise=? and tbh.nlsid=1 and tf.valto>current timestamp and tf.effto>current timestamp and p.valto>current timestamp and p.effto>current timestamp and t.valto>current timestamp and t.effto>current timestamp and f1.valto>current timestamp and f1.effto>current timestamp and tbh.valto>current timestamp and tbh.effto>current timestamp and t.valfrom > tbh.valfrom order by featurecode asc with ur; ");
/*  380 */         this.invgrpPs = this.m_db.getPDHConnection().prepareStatement("select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE  from opicm.text tf join opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid left join opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid and tf.nlsid=t.nlsid and t.attributecode='INVNAME' and t.enterprise=? and t.valto>current timestamp and t.effto>current timestamp where tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and tf.nlsid=1 and f1.attributecode='INVENTORYGROUP' and f1.attributevalue=? and f1.enterprise=? and tf.valto>current timestamp and tf.effto>current timestamp and f1.valto>current timestamp and f1.effto>current timestamp order by featurecode asc with ur");
/*      */         
/*  382 */         processFeature(str);
/*      */         
/*  384 */         this.rptSb.append(this.updatedSB.toString());
/*  385 */         long l1 = System.currentTimeMillis();
/*  386 */         addDebug("Time to process FEATUREs: " + Stopwatch.format(l1 - l));
/*      */         
/*  388 */         this.updatedSB.setLength(0);
/*  389 */         processSWFeature(entityItem, str);
/*  390 */         addDebug("Time to process SWFEATUREs: " + Stopwatch.format(System.currentTimeMillis() - l1));
/*      */       } 
/*      */       
/*  393 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  395 */     catch (Throwable throwable) {
/*  396 */       StringWriter stringWriter = new StringWriter();
/*  397 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  398 */       String str6 = "<pre>{0}</pre>";
/*  399 */       MessageFormat messageFormat1 = new MessageFormat(str5);
/*  400 */       setReturnCode(-3);
/*  401 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  403 */       this.args[0] = throwable.getMessage();
/*  404 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  405 */       messageFormat1 = new MessageFormat(str6);
/*  406 */       this.args[0] = stringWriter.getBuffer().toString();
/*      */       
/*  408 */       this.rptSb.append(this.updatedSB.toString());
/*  409 */       this.updatedSB.setLength(0);
/*      */       
/*  411 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  412 */       logError("Exception: " + throwable.getMessage());
/*  413 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*      */       
/*  416 */       closeStatements();
/*      */       
/*  418 */       setDGTitle(this.navName);
/*  419 */       setDGRptName(getShortClassName(getClass()));
/*  420 */       setDGRptClass(getABRCode());
/*      */       
/*  422 */       if (!isReadOnly()) {
/*  423 */         clearSoftLock();
/*      */       }
/*  425 */       closePrintWriter();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  430 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  431 */     this.args[0] = getDescription();
/*  432 */     this.args[1] = this.navName;
/*  433 */     String str4 = messageFormat.format(this.args);
/*  434 */     messageFormat = new MessageFormat(str2);
/*  435 */     this.args[0] = this.m_prof.getOPName();
/*  436 */     this.args[1] = this.m_prof.getRoleDescription();
/*  437 */     this.args[2] = this.m_prof.getWGName();
/*  438 */     this.args[3] = getNow();
/*  439 */     this.args[4] = this.navName;
/*  440 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  441 */     this.args[6] = str3 + " " + getABRVersion();
/*      */     
/*  443 */     this.rptSb.append(this.updatedSB.toString());
/*      */     
/*  445 */     restoreXtraContent();
/*      */     
/*  447 */     this.rptSb.insert(0, str4 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  449 */     println(this.rptSb.toString());
/*  450 */     printDGSubmitString();
/*  451 */     println(EACustom.getTOUDiv());
/*  452 */     buildReportFooter();
/*      */     
/*  454 */     this.metaTbl.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  459 */     if (this.dbgfn != null && this.dbgLen + this.rptSb.length() < 5000000) {
/*      */       
/*  461 */       BufferedInputStream bufferedInputStream = null;
/*  462 */       FileInputStream fileInputStream = null;
/*  463 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  465 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  466 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  468 */         String str = null;
/*  469 */         StringBuffer stringBuffer = new StringBuffer();
/*  470 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  472 */         while ((str = bufferedReader.readLine()) != null) {
/*  473 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  475 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  478 */         File file = new File(this.dbgfn);
/*  479 */         if (file.exists()) {
/*  480 */           file.delete();
/*      */         }
/*  482 */       } catch (Exception exception) {
/*  483 */         exception.printStackTrace();
/*      */       } finally {
/*  485 */         if (bufferedInputStream != null) {
/*      */           try {
/*  487 */             bufferedInputStream.close();
/*  488 */           } catch (Exception exception) {
/*  489 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  492 */         if (fileInputStream != null) {
/*      */           try {
/*  494 */             fileInputStream.close();
/*  495 */           } catch (Exception exception) {
/*  496 */             exception.printStackTrace();
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
/*      */   private void processFeature(String paramString) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException {
/*  539 */     addDebug("processFeature for pdhdomain " + paramString);
/*  540 */     this.featureGroup = new EntityGroup(null, this.m_db, this.m_prof, "FEATURE", "Edit", false);
/*      */     
/*  542 */     addHeading(3, this.featureGroup.getLongDescription() + " Checks:");
/*      */ 
/*      */     
/*  545 */     EANMetaAttribute eANMetaAttribute = this.featureGroup.getMetaAttribute("BHINVNAME");
/*  546 */     if (eANMetaAttribute == null) {
/*      */ 
/*      */       
/*  549 */       this.args[0] = "BHINVNAME";
/*  550 */       this.args[1] = this.featureGroup.getEntityType();
/*  551 */       addError("META_ERR", this.args);
/*      */       
/*      */       return;
/*      */     } 
/*  555 */     long l1 = System.currentTimeMillis();
/*      */     
/*  557 */     Vector<EntityInfo> vector = getHWEntities(paramString);
/*  558 */     long l2 = System.currentTimeMillis();
/*  559 */     addDebug("Time to get FEATUREs: " + Stopwatch.format(l2 - l1) + " fcvct.size " + vector.size()); int i;
/*  560 */     for (i = 0; i < vector.size(); i++) {
/*  561 */       EntityInfo entityInfo = vector.elementAt(i);
/*  562 */       setBHInvnameHW(entityInfo);
/*  563 */       entityInfo.dereference();
/*  564 */       if (this.vctReturnsEntityKeys.size() >= 200) {
/*      */         
/*  566 */         updatePDH();
/*  567 */         this.vctReturnsEntityKeys.clear();
/*  568 */         long l = System.currentTimeMillis();
/*  569 */         addDebug("Time to update 200 features: " + Stopwatch.format(l - l2));
/*  570 */         l2 = l;
/*      */       } 
/*      */     } 
/*  573 */     vector.clear();
/*      */     
/*  575 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       
/*  577 */       i = this.vctReturnsEntityKeys.size();
/*  578 */       updatePDH();
/*  579 */       addDebug("Time to update " + i + " features: " + 
/*  580 */           Stopwatch.format(System.currentTimeMillis() - l2));
/*  581 */       this.vctReturnsEntityKeys.clear();
/*      */     } 
/*      */ 
/*      */     
/*  585 */     Iterator<String> iterator = this.invGrpInvnameTbl.keySet().iterator();
/*  586 */     while (iterator.hasNext()) {
/*  587 */       String str = iterator.next();
/*  588 */       Hashtable hashtable = (Hashtable)this.invGrpInvnameTbl.get(str);
/*  589 */       Iterator<String> iterator1 = hashtable.keySet().iterator();
/*  590 */       while (iterator1.hasNext()) {
/*  591 */         String str1 = iterator1.next();
/*  592 */         InvnameInfo invnameInfo = (InvnameInfo)hashtable.get(str1);
/*  593 */         invnameInfo.dereference();
/*      */       } 
/*  595 */       hashtable.clear();
/*      */     } 
/*      */     
/*  598 */     this.invGrpInvnameTbl.clear();
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
/*      */   private void setBHInvnameHW(EntityInfo paramEntityInfo) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  625 */     String str1 = paramEntityInfo.fcode;
/*  626 */     String str2 = null;
/*  627 */     String str3 = paramEntityInfo.invname;
/*  628 */     String str4 = paramEntityInfo.invGrp;
/*  629 */     addDebug(4, "setBHInvnameHW checking " + paramEntityInfo);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  638 */     if (!checkDuplicateHWInvname(str4, str3)) {
/*  639 */       str2 = str3;
/*      */     } else {
/*  641 */       str2 = str1 + "-" + str3;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  646 */     if (str2.length() > 254) {
/*  647 */       addDebug(4, "setBHInvnameHW ERROR " + paramEntityInfo + " bhinvname exceeds text limit " + str2);
/*  648 */       str2 = str2.substring(0, 253);
/*      */     } 
/*      */ 
/*      */     
/*  652 */     setTextValue("BHINVNAME", str2, paramEntityInfo);
/*      */ 
/*      */     
/*  655 */     if (str2.length() > 30) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  660 */       EntityItem entityItem = new EntityItem(this.featureGroup, this.m_prof, this.m_db, paramEntityInfo.entityType, paramEntityInfo.entityid);
/*  661 */       this.args[0] = getLD_NDN(entityItem);
/*  662 */       this.args[1] = PokUtils.getAttributeDescription(this.featureGroup, "BHINVNAME", "BHINVNAME");
/*  663 */       this.args[2] = str2;
/*  664 */       addError("DERIVED_LEN_ERR", this.args);
/*      */       
/*  666 */       this.featureGroup.removeEntityItem(entityItem);
/*  667 */       for (int i = entityItem.getAttributeCount() - 1; i >= 0; i--) {
/*  668 */         EANAttribute eANAttribute = entityItem.getAttribute(i);
/*  669 */         entityItem.removeAttribute(eANAttribute);
/*      */       } 
/*  671 */       entityItem.setParent(null);
/*      */ 
/*      */       
/*  674 */       if ("0020".equals(paramEntityInfo.status) || "0040".equals(paramEntityInfo.status)) {
/*  675 */         setStatusAndDQ(paramEntityInfo);
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
/*      */   private boolean checkDuplicateHWInvname(String paramString1, String paramString2) throws SQLException {
/*  691 */     ResultSet resultSet = null;
/*  692 */     Hashtable<Object, Object> hashtable = (Hashtable)this.invGrpInvnameTbl.get(paramString1);
/*  693 */     if (hashtable != null) {
/*  694 */       InvnameInfo invnameInfo = (InvnameInfo)hashtable.get(paramString2);
/*  695 */       if (invnameInfo != null && 
/*  696 */         invnameInfo.count > 1) {
/*  697 */         String str1 = paramString1 + paramString2;
/*  698 */         if (!this.dupelistedSet.contains(str1)) {
/*  699 */           this.dupelistedSet.add(str1);
/*  700 */           addDebug(4, "found dupe: " + paramString1 + ": " + paramString2 + ": " + invnameInfo);
/*      */         } 
/*  702 */         return true;
/*      */       } 
/*      */       
/*  705 */       return false;
/*      */     } 
/*      */     
/*  708 */     String str = this.m_prof.getEnterprise();
/*  709 */     long l = System.currentTimeMillis();
/*      */     try {
/*  711 */       this.invgrpPs.clearParameters();
/*  712 */       this.invgrpPs.setString(1, str);
/*  713 */       this.invgrpPs.setString(2, str);
/*  714 */       this.invgrpPs.setString(3, paramString1);
/*  715 */       this.invgrpPs.setString(4, str);
/*      */       
/*  717 */       resultSet = this.invgrpPs.executeQuery();
/*  718 */       while (resultSet.next()) {
/*  719 */         int i = resultSet.getInt(1);
/*      */         
/*  721 */         String str1 = resultSet.getString(3);
/*  722 */         String str2 = resultSet.getString(4);
/*  723 */         String str3 = resultSet.getString(5);
/*      */ 
/*      */         
/*  726 */         if (str1 == null) {
/*      */           continue;
/*      */         }
/*      */         
/*  730 */         hashtable = (Hashtable)this.invGrpInvnameTbl.get(str2);
/*  731 */         if (hashtable == null) {
/*  732 */           hashtable = new Hashtable<>();
/*  733 */           this.invGrpInvnameTbl.put(str2, hashtable);
/*  734 */           hashtable.put(str1, new InvnameInfo(i, str3)); continue;
/*      */         } 
/*  736 */         InvnameInfo invnameInfo = (InvnameInfo)hashtable.get(str1);
/*  737 */         if (invnameInfo == null) {
/*  738 */           invnameInfo = new InvnameInfo(i, str3);
/*  739 */           hashtable.put(str1, invnameInfo); continue;
/*      */         } 
/*  741 */         invnameInfo.addId(i, str3);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*  747 */     catch (SQLException sQLException) {
/*  748 */       throw sQLException;
/*      */     } finally {
/*      */       
/*  751 */       if (resultSet != null) {
/*  752 */         resultSet.close();
/*  753 */         resultSet = null;
/*      */       } 
/*  755 */       this.m_db.commit();
/*  756 */       this.m_db.freeStatement();
/*  757 */       this.m_db.isPending();
/*      */     } 
/*  759 */     addDebug(4, "Time to get INVNAMEs for " + paramString1 + " : " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     
/*  761 */     hashtable = (Hashtable<Object, Object>)this.invGrpInvnameTbl.get(paramString1);
/*  762 */     if (hashtable != null) {
/*  763 */       InvnameInfo invnameInfo = (InvnameInfo)hashtable.get(paramString2);
/*  764 */       if (invnameInfo != null && 
/*  765 */         invnameInfo.count > 1) {
/*  766 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  770 */     return false;
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
/*      */   private void processSWFeature(EntityItem paramEntityItem, String paramString) throws Exception {
/*  819 */     addDebug("processSWFeature for pdhdomain " + paramString);
/*  820 */     this.featureGroup = new EntityGroup(null, this.m_db, this.m_prof, "SWFEATURE", "Edit", false);
/*      */     
/*  822 */     addHeading(3, this.featureGroup.getLongDescription() + " Checks:");
/*      */ 
/*      */     
/*  825 */     EANMetaAttribute eANMetaAttribute = this.featureGroup.getMetaAttribute("BHINVNAME");
/*  826 */     if (eANMetaAttribute == null) {
/*      */ 
/*      */       
/*  829 */       this.args[0] = "BHINVNAME";
/*  830 */       this.args[1] = this.featureGroup.getEntityType();
/*  831 */       addError("META_ERR", this.args);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  836 */     EntityItem entityItem = searchForWG(paramString);
/*  837 */     if (entityItem == null) {
/*      */       
/*  839 */       this.args[0] = getLD_Value(paramEntityItem, "PDHDOMAIN");
/*  840 */       addError("WG_NOTFND", this.args);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  845 */     String str = "BHINVNAMEXT";
/*      */     
/*  847 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str), new EntityItem[] { entityItem });
/*      */ 
/*      */     
/*  850 */     addDebug("processSWFeature: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/*      */ 
/*      */     
/*  853 */     EntityGroup entityGroup = entityList.getEntityGroup("MODEL");
/*      */     
/*  855 */     if (entityGroup.getEntityItemCount() > MW_VENTITY_LIMIT) {
/*  856 */       int i = entityGroup.getEntityItemCount() / MW_VENTITY_LIMIT;
/*  857 */       byte b1 = 0;
/*  858 */       Vector<EntityItem> vector = new Vector(1);
/*  859 */       for (byte b2 = 0; b2 <= i; b2++) {
/*  860 */         vector.clear();
/*  861 */         for (byte b = 0; b < MW_VENTITY_LIMIT && 
/*  862 */           b1 != entityGroup.getEntityItemCount(); b++)
/*      */         {
/*      */           
/*  865 */           vector.addElement(entityGroup.getEntityItem(b1++));
/*      */         }
/*  867 */         if (vector.size() > 0) {
/*      */           
/*  869 */           Vector vector1 = new Vector();
/*  870 */           EntityList entityList1 = pullSWModelInfo(vector, vector1);
/*  871 */           setBHInvnameSW(entityList1, vector1);
/*  872 */           entityList1.dereference();
/*  873 */           vector1.clear();
/*      */         } 
/*      */       } 
/*  876 */       vector.clear();
/*  877 */       vector = null;
/*      */     }
/*  879 */     else if (entityGroup.getEntityItemCount() > 0) {
/*  880 */       Vector<EntityItem> vector = new Vector(1);
/*  881 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  882 */         vector.addElement(entityGroup.getEntityItem(b));
/*      */       }
/*      */       
/*  885 */       Vector vector1 = new Vector();
/*  886 */       EntityList entityList1 = pullSWModelInfo(vector, vector1);
/*  887 */       setBHInvnameSW(entityList1, vector1);
/*  888 */       entityList1.dereference();
/*  889 */       vector1.clear();
/*  890 */       vector.clear();
/*  891 */       vector = null;
/*      */     } 
/*      */ 
/*      */     
/*  895 */     entityList.dereference();
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
/*      */   private EntityList pullSWModelInfo(Vector paramVector1, Vector<EntityInfo> paramVector2) throws Exception {
/*  909 */     String str = "BHINVNAMEXT2";
/*  910 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector1.size()];
/*  911 */     paramVector1.copyInto((Object[])arrayOfEntityItem);
/*      */     
/*  913 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str), arrayOfEntityItem);
/*      */ 
/*      */     
/*  916 */     addDebug(2, "pullSWModelInfo: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  922 */     EntityGroup entityGroup = entityList.getParentEntityGroup();
/*  923 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  924 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  925 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SWPRODSTRUCT", "SWFEATURE");
/*  926 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  927 */         EntityItem entityItem1 = vector.elementAt(b1);
/*  928 */         if (this.swfeatSet.contains(entityItem1.getKey())) {
/*  929 */           addDebug(4, "pullSWModelInfo: already handled " + entityItem1.getKey());
/*      */         }
/*      */         else {
/*      */           
/*  933 */           this.swfeatSet.add(entityItem1.getKey());
/*  934 */           String str1 = PokUtils.getAttributeValue(entityItem1, "INVNAME", "", null, false);
/*  935 */           if (str1 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  942 */             EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*  943 */             String str2 = PokUtils.getAttributeValue(entityItem2, "INVNAME", "", null, false);
/*  944 */             addDebug(2, "pullSWModelInfo: " + entityItem.getKey() + " " + entityItem1.getKey() + " invname " + str1 + " " + entityItem2
/*  945 */                 .getKey() + " swpsinvname " + str2);
/*  946 */             if (str2 != null) {
/*      */               
/*  948 */               EntityInfo entityInfo = new EntityInfo(entityItem1);
/*  949 */               paramVector2.add(entityInfo);
/*  950 */               setTextValue("INVNAME", str2, entityInfo);
/*  951 */               entityInfo.invname = str2;
/*  952 */               this.setInvNameTbl.put(entityItem1.getKey(), str2);
/*      */             } else {
/*      */               
/*  955 */               this.args[0] = getLD_NDN(entityItem1);
/*  956 */               this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "INVNAME", "INVNAME");
/*  957 */               addError("REQ_NOTPOPULATED_ERR", this.args);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  964 */             String str2 = PokUtils.getAttributeValue(entityItem1, "BHINVNAME", ", ", null, false);
/*  965 */             boolean bool = true;
/*  966 */             if (str2 != null) {
/*  967 */               addDebug(4, "pullSWModelInfo: " + entityItem.getKey() + " " + entityItem1.getKey() + " invname " + str1 + " bhinvname " + str2);
/*      */               
/*  969 */               String str3 = getTimestamp(entityItem1, "INVNAME");
/*      */               
/*  971 */               String str4 = getTimestamp(entityItem1, "BHINVNAME");
/*  972 */               addDebug(4, "pullSWModelInfo:         invnameDts: " + str3 + " bhinvnameDts: " + str4);
/*  973 */               bool = (str4.compareTo(str3) < 0) ? true : false;
/*      */             } 
/*  975 */             if (bool) {
/*  976 */               EntityInfo entityInfo = new EntityInfo(entityItem1);
/*  977 */               paramVector2.add(entityInfo);
/*      */             } 
/*      */           } 
/*      */         } 
/*  981 */       }  vector.clear();
/*      */     } 
/*      */     
/*  984 */     return entityList;
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
/*      */   private EntityItem searchForWG(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  998 */     Vector<String> vector1 = new Vector(1);
/*  999 */     Vector<String> vector2 = new Vector(1);
/* 1000 */     vector1.addElement("PDHDOMAIN");
/* 1001 */     vector2.addElement(paramString);
/* 1002 */     EntityItem entityItem = null;
/* 1003 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/* 1005 */       StringBuffer stringBuffer = new StringBuffer();
/* 1006 */       addDebug("searchForWG using attrs: " + vector1 + " values: " + vector2);
/* 1007 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, "SRDWG", "WG", false, vector1, vector2, stringBuffer);
/*      */       
/* 1009 */       if (stringBuffer.length() > 0) {
/* 1010 */         addDebug(stringBuffer.toString());
/*      */       }
/* 1012 */     } catch (SBRException sBRException) {
/*      */       
/* 1014 */       StringWriter stringWriter = new StringWriter();
/* 1015 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 1016 */       addDebug("searchForWG SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*      */     
/* 1019 */     if (arrayOfEntityItem != null) {
/* 1020 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1021 */         String str = PokUtils.getAttributeFlagValue(arrayOfEntityItem[b], "PDHDOMAIN");
/* 1022 */         addDebug("searchForWG found " + arrayOfEntityItem[b].getKey() + " wgdomain " + str);
/* 1023 */         if (paramString.equals(str)) {
/* 1024 */           entityItem = arrayOfEntityItem[b];
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1030 */     vector1.clear();
/* 1031 */     vector2.clear();
/* 1032 */     return entityItem;
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
/*      */   private void setBHInvnameSW(EntityList paramEntityList, Vector<EntityInfo> paramVector) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException {
/* 1076 */     EntityGroup entityGroup = paramEntityList.getEntityGroup("SWFEATURE");
/* 1077 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1078 */       EntityInfo entityInfo = paramVector.elementAt(b);
/* 1079 */       EntityItem entityItem = entityGroup.getEntityItem(entityInfo.entityType + entityInfo.entityid);
/*      */       
/* 1081 */       String str1 = entityInfo.fcode;
/* 1082 */       String str2 = PokUtils.getAttributeValue(entityItem, "BHINVNAME", ", ", null, false);
/* 1083 */       String str3 = entityInfo.invname;
/* 1084 */       addDebug(4, "setBHInvnameSW checking " + entityItem.getKey() + " fcode: " + str1 + " invname: " + str3 + " bhinvname " + str2 + " swps.count " + entityItem
/* 1085 */           .getDownLinkCount());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1098 */       if (uniqueSWPS(entityItem, str3)) {
/* 1099 */         str2 = str3;
/*      */       } else {
/* 1101 */         str2 = str1 + "-" + str3;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1106 */       if (str2.length() > 254) {
/* 1107 */         addDebug(4, "setBHInvnameSW ERROR " + entityItem.getKey() + " bhinvname exceeds text limit " + str2);
/* 1108 */         str2 = str2.substring(0, 253);
/*      */       } 
/*      */ 
/*      */       
/* 1112 */       setTextValue("BHINVNAME", str2, entityInfo);
/*      */ 
/*      */       
/* 1115 */       if (str2.length() > 30) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1121 */         this.args[0] = getLD_NDN(entityItem);
/* 1122 */         this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "BHINVNAME", "BHINVNAME");
/* 1123 */         this.args[2] = str2;
/* 1124 */         addError("DERIVED_LEN_ERR", this.args);
/*      */ 
/*      */         
/* 1127 */         if ("0020".equals(entityInfo.status) || "0040".equals(entityInfo.status)) {
/* 1128 */           setStatusAndDQ(entityInfo);
/*      */         }
/*      */       } 
/*      */       
/* 1132 */       entityInfo.dereference();
/*      */     } 
/*      */     
/* 1135 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       
/* 1137 */       updatePDH();
/* 1138 */       this.vctReturnsEntityKeys.clear();
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
/*      */   private boolean uniqueSWPS(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 1160 */     boolean bool = true;
/*      */     
/* 1162 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "SWPRODSTRUCT", "MODEL");
/* 1163 */     StringBuffer stringBuffer = new StringBuffer();
/* 1164 */     for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1165 */       stringBuffer.append(" " + ((EntityItem)vector1.elementAt(b1)).getKey());
/*      */     }
/*      */ 
/*      */     
/* 1169 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "SWPRODSTRUCT", "SWFEATURE");
/* 1170 */     addDebug(4, "uniqueSWPS entered  " + paramEntityItem.getKey() + " invname " + paramString + " with " + stringBuffer.toString() + " swfcVct.size " + vector2
/* 1171 */         .size());
/* 1172 */     for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 1173 */       EntityItem entityItem = vector2.elementAt(b2);
/* 1174 */       String str = PokUtils.getAttributeValue(entityItem, "INVNAME", ", ", null, false);
/* 1175 */       if (str == null)
/*      */       {
/* 1177 */         str = (String)this.setInvNameTbl.get(entityItem.getKey());
/*      */       }
/* 1179 */       if (!entityItem.getKey().equals(paramEntityItem.getKey())) {
/*      */ 
/*      */         
/* 1182 */         addDebug(4, "uniqueSWPS checking " + entityItem.getKey() + " swfcInvname " + str);
/* 1183 */         if (paramString.equals(str)) {
/* 1184 */           bool = false;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1190 */     vector2.clear();
/* 1191 */     vector2 = null;
/* 1192 */     vector1.clear();
/* 1193 */     vector1 = null;
/* 1194 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getLD_Value(EntityItem paramEntityItem, String paramString) {
/* 1203 */     return PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString) + ": " + 
/* 1204 */       PokUtils.getAttributeValue(paramEntityItem, paramString, ",", "<em>** Not Populated **</em>", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getLD_NDN(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1214 */     return paramEntityItem.getEntityGroup().getLongDescription() + " &quot;" + getNavigationName(paramEntityItem) + "&quot;";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 1221 */     super.dereference();
/*      */     
/* 1223 */     this.rsBundle = null;
/* 1224 */     this.rptSb = null;
/* 1225 */     this.args = null;
/*      */     
/* 1227 */     this.metaTbl = null;
/* 1228 */     this.navName = null;
/* 1229 */     this.vctReturnsEntityKeys.clear();
/* 1230 */     this.vctReturnsEntityKeys = null;
/*      */     
/* 1232 */     this.featureGroup = null;
/*      */     
/* 1234 */     this.swfeatSet.clear();
/* 1235 */     this.swfeatSet = null;
/*      */     
/* 1237 */     this.dupelistedSet.clear();
/* 1238 */     this.dupelistedSet = null;
/*      */     
/* 1240 */     this.setInvNameTbl.clear();
/* 1241 */     this.setInvNameTbl = null;
/*      */     
/* 1243 */     Iterator<String> iterator = this.invGrpInvnameTbl.keySet().iterator();
/* 1244 */     while (iterator.hasNext()) {
/* 1245 */       String str = iterator.next();
/* 1246 */       Hashtable hashtable = (Hashtable)this.invGrpInvnameTbl.get(str);
/* 1247 */       Iterator<String> iterator1 = hashtable.keySet().iterator();
/* 1248 */       while (iterator1.hasNext()) {
/* 1249 */         String str1 = iterator1.next();
/* 1250 */         InvnameInfo invnameInfo = (InvnameInfo)hashtable.get(str1);
/* 1251 */         invnameInfo.dereference();
/*      */       } 
/* 1253 */       hashtable.clear();
/*      */     } 
/*      */     
/* 1256 */     this.invGrpInvnameTbl.clear();
/* 1257 */     this.invGrpInvnameTbl = null;
/*      */     
/* 1259 */     this.dbgPw = null;
/* 1260 */     this.dbgfn = null;
/* 1261 */     this.updatedSB = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1267 */     return "$Revision: 1.10 $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1274 */     return "BHINVNAMABRSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1280 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDebug(int paramInt, String paramString) {
/* 1288 */     if (paramInt <= this.abr_debuglvl) {
/* 1289 */       addDebug(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDebug(String paramString) {
/* 1297 */     if (this.dbgPw != null) {
/* 1298 */       this.dbgLen += paramString.length();
/* 1299 */       this.dbgPw.println(paramString);
/* 1300 */       this.dbgPw.flush();
/*      */     } else {
/* 1302 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addHeading(int paramInt, String paramString) {
/* 1308 */     this.rptSb.append("<h" + paramInt + ">" + paramString + "</h" + paramInt + ">" + NEWLINE);
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
/*      */   private void addError(String paramString, Object[] paramArrayOfObject) {
/* 1320 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1323 */     addMessage(this.rsBundle.getString("ERROR_PREFIX"), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1332 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/* 1334 */     if (paramArrayOfObject != null) {
/* 1335 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1336 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1339 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1349 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 1353 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1354 */     if (eANList == null) {
/*      */       
/* 1356 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1357 */       eANList = entityGroup.getMetaAttribute();
/* 1358 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1360 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1362 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1363 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1364 */       if (b + 1 < eANList.size()) {
/* 1365 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1369 */     return stringBuffer.toString().trim();
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
/*      */   private void setTextValue(String paramString1, String paramString2, EntityInfo paramEntityInfo) {
/* 1383 */     if (this.m_cbOn == null) {
/* 1384 */       setControlBlock();
/*      */     }
/*      */     
/* 1387 */     Vector<Text> vector = null;
/*      */     
/* 1389 */     for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1390 */       ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1391 */       if (returnEntityKey.getEntityID() == paramEntityInfo.entityid && returnEntityKey
/* 1392 */         .getEntityType().equals(paramEntityInfo.entityType)) {
/* 1393 */         vector = returnEntityKey.m_vctAttributes;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1397 */     if (vector == null) {
/* 1398 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityInfo.entityType, paramEntityInfo.entityid, true);
/* 1399 */       vector = new Vector();
/* 1400 */       returnEntityKey.m_vctAttributes = vector;
/* 1401 */       this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */     } 
/*      */ 
/*      */     
/* 1405 */     Text text = new Text(this.m_prof.getEnterprise(), paramEntityInfo.entityType, paramEntityInfo.entityid, paramString1, paramString2, 1, this.m_cbOn);
/* 1406 */     vector.addElement(text);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setStatusAndDQ(EntityInfo paramEntityInfo) {
/* 1415 */     addDebug(4, "setStatusAndDQ for " + paramEntityInfo.entityType + paramEntityInfo.entityid);
/*      */     
/* 1417 */     if (this.m_cbOn == null) {
/* 1418 */       setControlBlock();
/*      */     }
/*      */     
/* 1421 */     Vector<SingleFlag> vector = null;
/*      */     
/* 1423 */     for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1424 */       ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1425 */       if (returnEntityKey.getEntityID() == paramEntityInfo.entityid && returnEntityKey
/* 1426 */         .getEntityType().equals(paramEntityInfo.entityType)) {
/* 1427 */         vector = returnEntityKey.m_vctAttributes;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1431 */     if (vector == null) {
/* 1432 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityInfo.entityType, paramEntityInfo.entityid, true);
/* 1433 */       vector = new Vector();
/* 1434 */       returnEntityKey.m_vctAttributes = vector;
/* 1435 */       this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */     } 
/*      */ 
/*      */     
/* 1439 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramEntityInfo.entityType, paramEntityInfo.entityid, "STATUS", "0050", 1, this.m_cbOn);
/*      */ 
/*      */     
/* 1442 */     vector.addElement(singleFlag);
/*      */ 
/*      */     
/* 1445 */     singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramEntityInfo.entityType, paramEntityInfo.entityid, "DATAQUALITY", "0050", 1, this.m_cbOn);
/*      */ 
/*      */     
/* 1448 */     vector.addElement(singleFlag);
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
/*      */   private void updatePDH() throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 1462 */     logMessage(getDescription() + " updating PDH");
/* 1463 */     addDebug(4, "updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys.size());
/*      */     
/* 1465 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       
/*      */       try {
/* 1468 */         this.m_db.update(this.m_prof, this.vctReturnsEntityKeys, false, false);
/*      */ 
/*      */         
/*      */         try {
/* 1472 */           for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1473 */             ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1474 */             for (byte b1 = 0; b1 < returnEntityKey.m_vctAttributes.size(); b1++) {
/* 1475 */               Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(b1);
/* 1476 */               String str = attribute.getAttributeCode();
/* 1477 */               if (str.equals("BHINVNAME") && attribute.getAttributeValue().length() <= 30) {
/*      */                 
/* 1479 */                 MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString("ATTR_SET"));
/* 1480 */                 this.args[0] = PokUtils.getAttributeDescription(this.featureGroup, str, str);
/* 1481 */                 this.args[1] = attribute.getAttributeValue();
/* 1482 */                 this.args[2] = this.featureGroup.getLongDescription();
/* 1483 */                 this.args[3] = returnEntityKey.getEntityType() + returnEntityKey.getEntityID();
/*      */ 
/*      */                 
/* 1486 */                 this.updatedSB.append("<p>" + messageFormat.format(this.args) + "</p>" + NEWLINE);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1491 */         } catch (Exception exception) {
/* 1492 */           exception.printStackTrace();
/* 1493 */           addDebug("exception trying to output msg " + exception);
/*      */         } 
/*      */       } finally {
/*      */         
/* 1497 */         this.vctReturnsEntityKeys.clear();
/* 1498 */         this.m_db.commit();
/* 1499 */         this.m_db.freeStatement();
/* 1500 */         this.m_db.isPending("finally after updatePDH");
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
/*      */   private String getTimestamp(EntityItem paramEntityItem, String paramString) throws Exception {
/* 1512 */     RowSelectableTable rowSelectableTable = new RowSelectableTable((EANTableWrapper)paramEntityItem, paramEntityItem.getLongDescription());
/*      */     
/* 1514 */     String str1 = "";
/* 1515 */     String str2 = paramEntityItem.getEntityType() + ":" + paramString;
/* 1516 */     int i = rowSelectableTable.getRowIndex(str2);
/* 1517 */     if (i < 0) {
/* 1518 */       i = rowSelectableTable.getRowIndex(str2 + ":C");
/*      */     }
/* 1520 */     if (i < 0) {
/* 1521 */       i = rowSelectableTable.getRowIndex(str2 + ":R");
/*      */     }
/* 1523 */     if (i != -1) {
/*      */       
/* 1525 */       EANAttribute eANAttribute = (EANAttribute)rowSelectableTable.getEANObject(i, 1);
/* 1526 */       if (eANAttribute != null) {
/*      */         
/* 1528 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_prof, eANAttribute);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1545 */         if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 1546 */           int j = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1;
/* 1547 */           str1 = attributeChangeHistoryGroup.getChangeHistoryItem(j).getChangeDate();
/*      */         } 
/*      */       } else {
/*      */         
/* 1551 */         addDebug(4, "EANAttribute was null for " + paramString + " in " + paramEntityItem.getKey());
/*      */       } 
/*      */     } else {
/*      */       
/* 1555 */       addDebug("Row for " + paramString + " was not found for " + paramEntityItem.getKey());
/*      */     } 
/*      */     
/* 1558 */     return str1;
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
/*      */   private Vector getHWEntities(String paramString) throws SQLException {
/* 1571 */     ResultSet resultSet = null;
/* 1572 */     Vector<EntityInfo> vector = new Vector();
/* 1573 */     String str = this.m_prof.getEnterprise();
/*      */     
/*      */     try {
/* 1576 */       this.bhinvnameStatement.clearParameters();
/* 1577 */       this.bhinvnameStatement.setString(1, str);
/* 1578 */       this.bhinvnameStatement.setString(2, str);
/* 1579 */       this.bhinvnameStatement.setString(3, str);
/* 1580 */       this.bhinvnameStatement.setString(4, paramString);
/* 1581 */       this.bhinvnameStatement.setString(5, str);
/* 1582 */       this.bhinvnameStatement.setString(6, str);
/* 1583 */       this.bhinvnameStatement.setString(7, str);
/* 1584 */       this.bhinvnameStatement.setString(8, str);
/* 1585 */       this.bhinvnameStatement.setString(9, paramString);
/* 1586 */       this.bhinvnameStatement.setString(10, str);
/* 1587 */       this.bhinvnameStatement.setString(11, str);
/* 1588 */       this.bhinvnameStatement.setString(12, str);
/* 1589 */       this.bhinvnameStatement.setString(13, str);
/*      */       
/* 1591 */       resultSet = this.bhinvnameStatement.executeQuery();
/* 1592 */       while (resultSet.next()) {
/* 1593 */         int i = resultSet.getInt(1);
/* 1594 */         String str1 = resultSet.getString(2);
/* 1595 */         String str2 = resultSet.getString(3);
/* 1596 */         String str3 = resultSet.getString(4);
/* 1597 */         String str4 = resultSet.getString(5);
/* 1598 */         String str5 = resultSet.getString(6);
/* 1599 */         if (str2 == null || str3 == null) {
/* 1600 */           addDebug(4, "getEntities: skipping " + str1 + i + " with invname " + str2 + " invgrp " + str3 + " fcode " + str4);
/*      */           continue;
/*      */         } 
/* 1603 */         EntityInfo entityInfo = new EntityInfo(i, str1, str2, str3, str4, str5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1609 */         vector.add(entityInfo);
/*      */       }
/*      */     
/*      */     }
/* 1613 */     catch (SQLException sQLException) {
/* 1614 */       throw sQLException;
/*      */     } finally {
/*      */       
/* 1617 */       if (resultSet != null) {
/* 1618 */         resultSet.close();
/* 1619 */         resultSet = null;
/*      */       } 
/* 1621 */       this.m_db.commit();
/* 1622 */       this.m_db.freeStatement();
/* 1623 */       this.m_db.isPending();
/*      */     } 
/* 1625 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void closeStatements() {
/*      */     try {
/* 1634 */       if (this.invgrpPs != null) {
/* 1635 */         this.invgrpPs.close();
/* 1636 */         this.invgrpPs = null;
/*      */       } 
/* 1638 */     } catch (Exception exception) {
/* 1639 */       System.err.println("closeStatements(), unable to close invgrpPs. " + exception);
/*      */     } 
/*      */     try {
/* 1642 */       if (this.bhinvnameStatement != null) {
/* 1643 */         this.bhinvnameStatement.close();
/* 1644 */         this.bhinvnameStatement = null;
/*      */       } 
/* 1646 */     } catch (Exception exception) {
/* 1647 */       System.err.println("closeStatements(), unable to close bhinvnameStatement. " + exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntityInfo
/*      */   {
/* 1656 */     private String invname = null;
/* 1657 */     private int entityid = 0;
/* 1658 */     private String entityType = null;
/* 1659 */     private String invGrp = null;
/* 1660 */     private String fcode = null;
/* 1661 */     private String status = null;
/*      */     
/*      */     EntityInfo(EntityItem param1EntityItem) {
/* 1664 */       this.invname = PokUtils.getAttributeValue(param1EntityItem, "INVNAME", "", null, false);
/* 1665 */       this.entityid = param1EntityItem.getEntityID();
/* 1666 */       this.entityType = param1EntityItem.getEntityType();
/* 1667 */       this.invGrp = PokUtils.getAttributeFlagValue(param1EntityItem, "INVENTORYGROUP");
/* 1668 */       this.fcode = PokUtils.getAttributeValue(param1EntityItem, "FEATURECODE", "", null, false);
/* 1669 */       this.status = PokUtils.getAttributeFlagValue(param1EntityItem, "STATUS");
/*      */     }
/*      */     EntityInfo(int param1Int, String param1String1, String param1String2, String param1String3, String param1String4, String param1String5) {
/* 1672 */       this.invname = param1String2;
/* 1673 */       this.entityid = param1Int;
/* 1674 */       this.entityType = param1String1;
/* 1675 */       this.invGrp = param1String3;
/* 1676 */       this.fcode = param1String4;
/* 1677 */       this.status = param1String5;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1681 */       return this.entityType + this.entityid + " fcode " + this.fcode + " invGrp " + this.invGrp + " invname " + this.invname + " status " + this.status;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 1685 */       this.invname = null;
/* 1686 */       this.invGrp = null;
/* 1687 */       this.fcode = null;
/* 1688 */       this.entityType = null;
/* 1689 */       this.status = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class InvnameInfo
/*      */   {
/* 1697 */     private String entityids = "";
/* 1698 */     private String fcodes = "";
/* 1699 */     private int count = 0;
/*      */     
/*      */     InvnameInfo(int param1Int, String param1String) {
/* 1702 */       addId(param1Int, param1String);
/*      */     }
/*      */     void addId(int param1Int, String param1String) {
/* 1705 */       this.entityids += " " + param1Int;
/* 1706 */       this.fcodes += " " + param1String;
/* 1707 */       this.count++;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1711 */       return "entityids: " + this.entityids + " fcodes: " + this.fcodes;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 1715 */       this.entityids = null;
/* 1716 */       this.fcodes = null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\BHINVNAMABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */