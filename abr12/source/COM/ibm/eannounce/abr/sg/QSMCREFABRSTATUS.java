/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Date;
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
/*      */ 
/*      */ 
/*      */ public class QSMCREFABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*   97 */   private StringBuffer rptSb = new StringBuffer();
/*   98 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   99 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*  101 */   private ResourceBundle rsBundle = null;
/*  102 */   private Hashtable metaTbl = new Hashtable<>();
/*  103 */   private String navName = "";
/*  104 */   private String strCREFIF = "";
/*      */   
/*  106 */   private String ffFileName = null;
/*  107 */   private String ffPathName = null;
/*  108 */   private String ffFTPPathName = null;
/*  109 */   private String dir = null;
/*  110 */   private String dirDest = null;
/*  111 */   private final String QSMRPTPATH = "_rptpath";
/*  112 */   private final String QSMGENPATH = "_genpath";
/*  113 */   private final String QSMFTPPATH = "_ftppath";
/*  114 */   private int abr_debuglvl = 0;
/*  115 */   private String fileprefix = null;
/*  116 */   private String lineStr = "";
/*      */   
/*      */   private static final String SUCCESS = "SUCCESS";
/*      */   
/*      */   private static final String FAILD = "FAILD";
/*      */   
/*      */   private static final String CREFINIPATH = "_inipath";
/*      */   
/*      */   private static final String FTPSCRPATH = "_script";
/*      */   
/*      */   private static final String TARGETFILENAME = "_targetfilename";
/*      */   
/*      */   private static final String LOGPATH = "_logpath";
/*      */   
/*      */   private static final String BACKUPPATH = "_backuppath";
/*      */   private static final String STATUS_PASSED = "0030";
/*      */   public static final String ISLMRFA = "ISLMRFA";
/*      */   public static final String ISLMPRN = "ISLMPRN";
/*      */   public static final String CSLMPCI = "CSLMPCI";
/*      */   public static final String IPRTNUM = "IPRTNUM";
/*      */   public static final String FPUNINC = "FPUNINC";
/*      */   public static final String DSLMCPA = "DSLMCPA";
/*      */   public static final String DSLMGAD = "DSLMGAD";
/*      */   public static final String DSLMMES = "DSLMMES";
/*      */   public static final String DSLMWDN = "DSLMWDN";
/*      */   public static final String CJLBIC1 = "CJLBIC1";
/*      */   public static final String CJLBIDS = "CJLBIDS";
/*      */   public static final String CJLBIDT = "CJLBIDT";
/*      */   public static final String CJLBOEM = "CJLBOEM";
/*      */   public static final String CJLBPOF = "CJLBPOF";
/*      */   public static final String CJLBSAC = "CJLBSAC";
/*      */   public static final String CREFIF = "CREFIF";
/*      */   public static final String CLASSPT = "CLASSPT";
/*      */   public static final String CSLMFCC = "CSLMFCC";
/*      */   public static final String CSLMSYT = "CSLMSYT";
/*      */   public static final String CVOAT = "CVOAT";
/*      */   public static final String FBRAND = "FBRAND";
/*      */   public static final String FSLMCPU = "FSLMCPU";
/*      */   public static final String FSLMIOP = "FSLMIOP";
/*      */   public static final String FSLMMLC = "FSLMMLC";
/*      */   public static final String IDORIG = "IDORIG";
/*      */   public static final String POGMES = "POGMES";
/*      */   public static final String STSPCFT = "STSPCFT";
/*      */   public static final String TSLMDES = "TSLMDES";
/*      */   public static final String TSLTDES = "TSLTDES";
/*      */   public static final String XMAP = "XMAP";
/*      */   public static final String XMCCN = "XMCCN";
/*      */   public static final String XMEMEA = "XMEMEA";
/*      */   public static final String XMLA = "XMLA";
/*      */   public static final String XMUS = "XMUS";
/*      */   public static final String TIMSTMP = "TIMSTMP";
/*      */   public static final String USERID = "USERID";
/*  168 */   private static final Hashtable COLUMN_LENGTH = new Hashtable<>(); static {
/*  169 */     COLUMN_LENGTH.put("ISLMRFA", "6");
/*  170 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/*  171 */     COLUMN_LENGTH.put("CSLMPCI", "2");
/*  172 */     COLUMN_LENGTH.put("IPRTNUM", "12");
/*  173 */     COLUMN_LENGTH.put("FPUNINC", "1");
/*  174 */     COLUMN_LENGTH.put("DSLMCPA", "10");
/*  175 */     COLUMN_LENGTH.put("DSLMGAD", "10");
/*  176 */     COLUMN_LENGTH.put("DSLMMES", "10");
/*  177 */     COLUMN_LENGTH.put("DSLMWDN", "10");
/*  178 */     COLUMN_LENGTH.put("CJLBIC1", "2");
/*  179 */     COLUMN_LENGTH.put("CJLBIDS", "1");
/*  180 */     COLUMN_LENGTH.put("CJLBIDT", "1");
/*  181 */     COLUMN_LENGTH.put("CJLBOEM", "1");
/*  182 */     COLUMN_LENGTH.put("CJLBPOF", "1");
/*  183 */     COLUMN_LENGTH.put("CJLBSAC", "3");
/*  184 */     COLUMN_LENGTH.put("CREFIF", "1");
/*  185 */     COLUMN_LENGTH.put("CLASSPT", "3");
/*  186 */     COLUMN_LENGTH.put("CSLMFCC", "4");
/*  187 */     COLUMN_LENGTH.put("CSLMSYT", "4");
/*  188 */     COLUMN_LENGTH.put("CVOAT", "1");
/*  189 */     COLUMN_LENGTH.put("FBRAND", "1");
/*  190 */     COLUMN_LENGTH.put("FSLMCPU", "1");
/*  191 */     COLUMN_LENGTH.put("FSLMIOP", "1");
/*  192 */     COLUMN_LENGTH.put("FSLMMLC", "1");
/*  193 */     COLUMN_LENGTH.put("IDORIG", "3");
/*  194 */     COLUMN_LENGTH.put("POGMES", "10");
/*  195 */     COLUMN_LENGTH.put("STSPCFT", "4");
/*  196 */     COLUMN_LENGTH.put("TSLMDES", "30");
/*  197 */     COLUMN_LENGTH.put("TSLTDES", "56");
/*  198 */     COLUMN_LENGTH.put("XMAP", "1");
/*  199 */     COLUMN_LENGTH.put("XMCCN", "1");
/*  200 */     COLUMN_LENGTH.put("XMEMEA", "1");
/*  201 */     COLUMN_LENGTH.put("XMLA", "1");
/*  202 */     COLUMN_LENGTH.put("XMUS", "1");
/*  203 */     COLUMN_LENGTH.put("TIMSTMP", "26");
/*  204 */     COLUMN_LENGTH.put("USERID", "8");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  211 */     return this.rsBundle;
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
/*      */   public void execute_run() {
/*  225 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  227 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  236 */     String str3 = "";
/*  237 */     boolean bool = true;
/*  238 */     boolean bool1 = true;
/*      */ 
/*      */     
/*  241 */     String str4 = "";
/*  242 */     String str5 = "";
/*      */     
/*  244 */     String[] arrayOfString = new String[10];
/*      */     
/*      */     try {
/*  247 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  248 */       arrayOfString[0] = getShortClassName(getClass());
/*  249 */       arrayOfString[1] = "ABR";
/*  250 */       str3 = messageFormat.format(arrayOfString);
/*      */ 
/*      */       
/*  253 */       setReturnCode(0);
/*      */       
/*  255 */       start_ABRBuild(false);
/*      */       
/*  257 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */ 
/*      */       
/*  260 */       this.m_elist = getEntityList(getModelVEName());
/*      */       
/*  262 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  264 */       this.navName = getNavigationName();
/*  265 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/*      */       
/*  267 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getAttributeHistory("QSMCREFABRSTATUS");
/*  268 */       if (existBefore(attributeChangeHistoryGroup, "0030")) {
/*  269 */         this.strCREFIF = "1";
/*      */       } else {
/*  271 */         this.strCREFIF = "0";
/*      */       } 
/*      */ 
/*      */       
/*  275 */       setDGString(getABRReturnCode());
/*  276 */       setDGRptName("QSMCREFABRSTATUS");
/*  277 */       setDGTitle(this.navName);
/*  278 */       setDGRptClass(getABRCode());
/*  279 */       generateFlatFile(entityItem);
/*  280 */       exeFtpShell(this.ffPathName);
/*      */     }
/*  282 */     catch (Exception exception) {
/*      */       
/*  284 */       exception.printStackTrace();
/*      */       
/*  286 */       setReturnCode(-1);
/*  287 */       StringWriter stringWriter = new StringWriter();
/*  288 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  289 */       String str7 = "<pre>{0}</pre>";
/*  290 */       MessageFormat messageFormat = new MessageFormat(str6);
/*  291 */       setReturnCode(-3);
/*  292 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  294 */       arrayOfString[0] = exception.getMessage();
/*  295 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/*  296 */       messageFormat = new MessageFormat(str7);
/*  297 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  298 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/*  299 */       logError("Exception: " + exception.getMessage());
/*  300 */       logError(stringWriter.getBuffer().toString());
/*      */       
/*  302 */       setCreateDGEntity(true);
/*  303 */       bool = false;
/*  304 */       bool1 = exeFtpShell(this.ffPathName);
/*      */     } finally {
/*  306 */       if (!isReadOnly()) {
/*  307 */         clearSoftLock();
/*      */       }
/*      */       
/*  310 */       StringBuffer stringBuffer = new StringBuffer();
/*  311 */       MessageFormat messageFormat = new MessageFormat(str2);
/*  312 */       arrayOfString[0] = this.m_prof.getOPName();
/*  313 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/*  314 */       arrayOfString[2] = this.m_prof.getWGName();
/*  315 */       arrayOfString[3] = getNow();
/*  316 */       stringBuffer.append(bool ? "generated the QSM report file successful " : "generated the QSM report file faild");
/*  317 */       stringBuffer.append(",");
/*  318 */       stringBuffer.append(bool1 ? "send the QSM report file successful " : "sent the QSM report file faild");
/*  319 */       arrayOfString[4] = stringBuffer.toString();
/*  320 */       arrayOfString[5] = str4 + " " + getABRVersion();
/*      */       
/*  322 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*      */       
/*  324 */       println(EACustom.getDocTypeHtml());
/*  325 */       println(this.rptSb.toString());
/*  326 */       printDGSubmitString();
/*      */       
/*  328 */       println(EACustom.getTOUDiv());
/*  329 */       buildReportFooter();
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
/*      */   private void setFileName(EntityItem paramEntityItem) {
/*  342 */     this.fileprefix = ABRServerProperties.getFilePrefix(this.m_abri
/*  343 */         .getABRCode());
/*      */ 
/*      */     
/*  346 */     StringBuffer stringBuffer = new StringBuffer(this.fileprefix.trim());
/*  347 */     stringBuffer.append(paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "_");
/*  348 */     String str = getNow();
/*      */     
/*  350 */     str = str.replace(' ', '_');
/*  351 */     stringBuffer.append(str + ".txt");
/*  352 */     this.dir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_genpath", "/Dgq");
/*  353 */     if (!this.dir.endsWith("/")) {
/*  354 */       this.dir += "/";
/*      */     }
/*  356 */     this.ffFileName = stringBuffer.toString();
/*  357 */     this.ffPathName = this.dir + this.ffFileName;
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
/*      */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException, SQLException, MiddlewareException, ParseException {
/*  370 */     FileChannel fileChannel1 = null;
/*  371 */     FileChannel fileChannel2 = null;
/*      */ 
/*      */     
/*  374 */     setFileName(paramEntityItem);
/*      */     
/*  376 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*      */ 
/*      */ 
/*      */     
/*  380 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */     
/*  382 */     createModelRecords(paramEntityItem, outputStreamWriter);
/*  383 */     createFeatureRecords(paramEntityItem, outputStreamWriter);
/*  384 */     createModelConvertRecords(paramEntityItem, outputStreamWriter);
/*      */     
/*  386 */     outputStreamWriter.close();
/*      */     
/*  388 */     this.dirDest = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_ftppath", "/Dgq");
/*  389 */     if (!this.dirDest.endsWith("/")) {
/*  390 */       this.dirDest += "/";
/*      */     }
/*      */     
/*  393 */     this.ffFTPPathName = this.dirDest + this.ffFileName;
/*      */     
/*      */     try {
/*  396 */       fileChannel1 = (new FileInputStream(this.ffPathName)).getChannel();
/*  397 */       fileChannel2 = (new FileOutputStream(this.ffFTPPathName)).getChannel();
/*  398 */       fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
/*      */     } finally {
/*  400 */       fileChannel1.close();
/*  401 */       fileChannel2.close();
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
/*      */   private void createModelRecords(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, ParseException {
/*  417 */     String str1 = "";
/*  418 */     String str2 = "";
/*  419 */     String str3 = "";
/*  420 */     String str4 = "";
/*      */     
/*  422 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/*  424 */     String str5 = getEarliestPlanAvailDate(entityGroup);
/*  425 */     String str6 = getEarliestMESAvailDate(entityGroup);
/*  426 */     String str7 = PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", ",", "", false);
/*  427 */     String str8 = "";
/*  428 */     boolean bool = false;
/*      */     
/*  430 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  431 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  433 */       str8 = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/*  434 */       if (str8.equals("EPIC")) {
/*  435 */         bool = true;
/*      */       }
/*      */       
/*  438 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*      */       
/*  440 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  441 */         StringBuffer stringBuffer = new StringBuffer();
/*  442 */         stringBuffer.append(getValue("ISLMRFA", bool ? PokUtils.getAttributeValue(entityItem, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem, "ANNNUMBER", "", "")));
/*  443 */         EntityItem entityItem1 = vector.elementAt(b1);
/*  444 */         String str9 = PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", "", "");
/*  445 */         str9 = str9 + PokUtils.getAttributeValue(entityItem1, "MODELATR", "", "");
/*  446 */         stringBuffer.append(getValue("ISLMPRN", str9));
/*  447 */         stringBuffer.append(getValue("CSLMPCI", "MM"));
/*  448 */         stringBuffer.append(getValue("IPRTNUM", "            "));
/*  449 */         stringBuffer.append(getValue("FPUNINC", "2"));
/*  450 */         if (str7 != null && !str7.equals("")) {
/*  451 */           stringBuffer.append(getValue("DSLMCPA", str7));
/*      */         } else {
/*  453 */           stringBuffer.append(getValue("DSLMCPA", "          "));
/*      */         } 
/*  455 */         stringBuffer.append(getValue("DSLMGAD", str5));
/*  456 */         stringBuffer.append(getValue("DSLMMES", str6));
/*  457 */         stringBuffer.append(getValue("DSLMWDN", "2050-12-31"));
/*      */         
/*  459 */         String str10 = PokUtils.getAttributeValue(paramEntityItem, "INDDEFNCATG", ",", "", false);
/*  460 */         if (str10.length() >= 2) {
/*  461 */           stringBuffer.append(getValue("CJLBIC1", str10.substring(0, 2)));
/*      */         } else {
/*  463 */           stringBuffer.append(getValue("CJLBIC1", ""));
/*      */         } 
/*  465 */         if (str10.length() >= 3) {
/*  466 */           stringBuffer.append(getValue("CJLBIDS", str10.substring(2)));
/*      */         } else {
/*  468 */           stringBuffer.append(getValue("CJLBIDS", ""));
/*      */         } 
/*      */         
/*  471 */         stringBuffer.append(getValue("CJLBIDT", " "));
/*  472 */         stringBuffer.append(getValue("CJLBOEM", PokUtils.getAttributeValue(entityItem1, "SPECMODDESGN", "", "")));
/*  473 */         stringBuffer.append(getValue("CJLBPOF", " "));
/*      */         
/*  475 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("SGMNTACRNYM");
/*  476 */         if (entityGroup1 != null && entityGroup1.hasData()) {
/*  477 */           EntityItem entityItem3 = entityGroup1.getEntityItem(0);
/*  478 */           stringBuffer.append(getValue("CJLBSAC", PokUtils.getAttributeValue(entityItem3, "ACRNYM", "", "")));
/*      */         } else {
/*  480 */           stringBuffer.append(getValue("CJLBSAC", "   "));
/*      */         } 
/*  482 */         stringBuffer.append(getValue("CREFIF", this.strCREFIF));
/*  483 */         stringBuffer.append(getValue("CLASSPT", PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "", false)));
/*  484 */         stringBuffer.append(getValue("CSLMFCC", PokUtils.getAttributeValue(entityItem1, "FUNCCLS", "", "")));
/*  485 */         stringBuffer.append(getValue("CSLMSYT", PokUtils.getAttributeValue(entityItem1, "SYSTEMTYPE", "", "")));
/*  486 */         stringBuffer.append(getValue("CVOAT", " "));
/*      */         
/*  488 */         str1 = "";
/*  489 */         str4 = "";
/*  490 */         String str11 = PokUtils.getAttributeValue(entityItem, "GENAREASELECTION", "", "");
/*  491 */         String str12 = "";
/*  492 */         EntityItem entityItem2 = null;
/*  493 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "MODELGEOMOD", "GEOMOD");
/*  494 */         if (vector1.size() > 0) {
/*  495 */           for (int i = 0; i < vector1.size(); i++) {
/*  496 */             entityItem2 = vector1.elementAt(i);
/*  497 */             str12 = PokUtils.getAttributeValue(entityItem2, "GENAREASELECTION", "", "");
/*  498 */             if (str12.equals(str11)) {
/*  499 */               str1 = PokUtils.getAttributeValue(entityItem2, "EMEABRANDCD", "", "");
/*  500 */               str4 = PokUtils.getAttributeValue(entityItem2, "INTEGRATEDMODEL", "", "");
/*  501 */               i = vector1.size();
/*      */             } else {
/*  503 */               entityItem2 = null;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  508 */         stringBuffer.append(getValue("FBRAND", str1));
/*      */         
/*  510 */         str2 = "";
/*  511 */         str3 = PokUtils.getAttributeValue(entityItem1, "SYSIDUNIT", "", "");
/*  512 */         if (str3.equals("SIU-CPU")) {
/*  513 */           str2 = "CPU";
/*      */         }
/*  515 */         stringBuffer.append(getValue("FSLMCPU", str2));
/*  516 */         stringBuffer.append(getValue("FSLMIOP", str4));
/*  517 */         stringBuffer.append(getValue("FSLMMLC", PokUtils.getAttributeValue(entityItem1, "MACHLVLCNTRL", "", "")));
/*  518 */         stringBuffer.append(getValue("IDORIG", PokUtils.getAttributeValue(entityItem1, "SPECMODDESGN", "", "")));
/*  519 */         stringBuffer.append(getValue("POGMES", " "));
/*  520 */         stringBuffer.append(getValue("STSPCFT", "    "));
/*  521 */         stringBuffer.append(getValue("TSLMDES", PokUtils.getAttributeValue(entityItem1, "INVNAME", "", "")));
/*  522 */         stringBuffer.append(getValue("TSLTDES", " "));
/*      */         
/*  524 */         String str13 = "N";
/*  525 */         String str14 = "N";
/*  526 */         String str15 = "N";
/*  527 */         String str16 = "N";
/*  528 */         String str17 = "N";
/*      */         
/*  530 */         Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem, "ANNGAA", "GENERALAREA");
/*  531 */         for (byte b2 = 0; b2 < vector2.size(); b2++) {
/*  532 */           EntityItem entityItem3 = vector2.elementAt(b2);
/*  533 */           String str = PokUtils.getAttributeValue(entityItem3, "RFAGEO", "", "");
/*  534 */           if (str.equals("AP")) {
/*  535 */             str13 = "Y";
/*  536 */           } else if (str.equals("CCN")) {
/*  537 */             str14 = "Y";
/*  538 */           } else if (str.equals("EMEA")) {
/*  539 */             str15 = "Y";
/*  540 */           } else if (str.equals("LA")) {
/*  541 */             str16 = "Y";
/*  542 */           } else if (str.equals("US")) {
/*  543 */             str17 = "Y";
/*      */           } 
/*      */         } 
/*  546 */         stringBuffer.append(getValue("XMAP", str13));
/*  547 */         stringBuffer.append(getValue("XMCCN", str14));
/*  548 */         stringBuffer.append(getValue("XMEMEA", str15));
/*  549 */         stringBuffer.append(getValue("XMLA", str16));
/*  550 */         stringBuffer.append(getValue("XMUS", str17));
/*      */         
/*  552 */         stringBuffer.append(getValue("TIMSTMP", " "));
/*  553 */         stringBuffer.append(getValue("USERID", " "));
/*      */         
/*  555 */         stringBuffer.append(NEWLINE);
/*  556 */         paramOutputStreamWriter.write(stringBuffer.toString());
/*  557 */         paramOutputStreamWriter.flush();
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
/*      */   private void createFeatureRecords(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException, ParseException {
/*  576 */     String str1 = "";
/*  577 */     String str2 = "";
/*  578 */     String str3 = "";
/*  579 */     String str4 = "";
/*  580 */     String str5 = "";
/*  581 */     String str6 = "";
/*  582 */     String str7 = "";
/*  583 */     String str8 = "";
/*      */     
/*  585 */     this.m_elist = getEntityList(getFeatureVEName());
/*      */     
/*  587 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/*  589 */     String str9 = getEarliestPlanAvailDate(entityGroup);
/*  590 */     String str10 = getEarliestMESAvailDate(entityGroup);
/*  591 */     String str11 = PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", ",", "", false);
/*  592 */     String str12 = "";
/*  593 */     boolean bool = false;
/*      */     
/*  595 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  596 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  598 */       str12 = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/*  599 */       if (str12.equals("EPIC")) {
/*  600 */         bool = true;
/*      */       }
/*      */       
/*  603 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*      */       
/*  605 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  606 */         StringBuffer stringBuffer = new StringBuffer();
/*  607 */         stringBuffer.append(getValue("ISLMRFA", bool ? PokUtils.getAttributeValue(entityItem, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem, "ANNNUMBER", "", "")));
/*  608 */         EntityItem entityItem1 = vector.elementAt(b1);
/*      */         
/*  610 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getProdstructVEName());
/*      */         
/*  612 */         EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, entityItem1.getEntityType(), entityItem1.getEntityID()) });
/*      */         
/*  614 */         EntityGroup entityGroup1 = entityList.getEntityGroup("FEATURE");
/*  615 */         EntityGroup entityGroup2 = entityList.getEntityGroup("MODEL");
/*  616 */         EntityItem entityItem2 = entityGroup1.getEntityItem(0);
/*  617 */         EntityItem entityItem3 = entityGroup2.getEntityItem(0);
/*  618 */         str1 = "";
/*  619 */         str2 = "";
/*  620 */         str3 = "";
/*  621 */         str2 = PokUtils.getAttributeValue(entityItem3, "SPECMODDESGN", "", "", false);
/*      */         
/*  623 */         EntityGroup entityGroup3 = this.m_elist.getEntityGroup("SGMNTACRNYM");
/*  624 */         if (entityGroup3 != null && entityGroup3.hasData()) {
/*  625 */           EntityItem entityItem5 = entityGroup3.getEntityItem(0);
/*  626 */           str3 = PokUtils.getAttributeValue(entityItem5, "ACRNYM", "", "", false);
/*      */         } 
/*      */         
/*  629 */         str1 = PokUtils.getAttributeValue(entityItem3, "MACHTYPEATR", "", "", false);
/*  630 */         str1 = str1 + PokUtils.getAttributeValue(entityItem2, "FEATURECODE", "", "", false);
/*  631 */         stringBuffer.append(getValue("ISLMPRN", str1));
/*      */         
/*  633 */         str8 = "";
/*  634 */         str8 = PokUtils.getAttributeValue(entityItem2, "FCTYPE", "", "", false);
/*  635 */         if (str8.equals("RPQ-RLISTED") || str8.equals("RPQ-ILISTED") || str8.equals("RPQ-PLISTED")) {
/*  636 */           stringBuffer.append(getValue("CSLMPCI", "MQ"));
/*      */         } else {
/*  638 */           stringBuffer.append(getValue("CSLMPCI", "MF"));
/*      */         } 
/*  640 */         stringBuffer.append(getValue("IPRTNUM", "            "));
/*  641 */         stringBuffer.append(getValue("FPUNINC", "2"));
/*  642 */         if (str11 != null && !str11.equals("")) {
/*  643 */           stringBuffer.append(getValue("DSLMCPA", str11));
/*      */         } else {
/*  645 */           stringBuffer.append(getValue("DSLMCPA", "          "));
/*      */         } 
/*  647 */         stringBuffer.append(getValue("DSLMGAD", str9));
/*  648 */         stringBuffer.append(getValue("DSLMMES", str10));
/*  649 */         stringBuffer.append(getValue("DSLMWDN", "2050-12-31"));
/*  650 */         String str13 = PokUtils.getAttributeValue(paramEntityItem, "INDDEFNCATG", ",", "", false);
/*  651 */         if (str13.length() >= 2) {
/*  652 */           stringBuffer.append(getValue("CJLBIC1", str13.substring(0, 2)));
/*      */         } else {
/*  654 */           stringBuffer.append(getValue("CJLBIC1", ""));
/*      */         } 
/*  656 */         if (str13.length() >= 3) {
/*  657 */           stringBuffer.append(getValue("CJLBIDS", str13.substring(2)));
/*      */         } else {
/*  659 */           stringBuffer.append(getValue("CJLBIDS", ""));
/*      */         } 
/*  661 */         stringBuffer.append(getValue("CJLBIDT", " "));
/*  662 */         stringBuffer.append(getValue("CJLBOEM", str2));
/*  663 */         stringBuffer.append(getValue("CJLBPOF", " "));
/*      */         
/*  665 */         stringBuffer.append(getValue("CJLBSAC", str3));
/*      */         
/*  667 */         stringBuffer.append(getValue("CREFIF", this.strCREFIF));
/*      */         
/*  669 */         stringBuffer.append(getValue("CLASSPT", PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "")));
/*  670 */         stringBuffer.append(getValue("CSLMFCC", PokUtils.getAttributeValue(entityItem3, "FUNCCLS", "", "")));
/*  671 */         stringBuffer.append(getValue("CSLMSYT", PokUtils.getAttributeValue(entityItem3, "SYSTEMTYPE", "", "")));
/*  672 */         stringBuffer.append(getValue("CVOAT", " "));
/*      */         
/*  674 */         str4 = "";
/*  675 */         str7 = "";
/*  676 */         String str14 = PokUtils.getAttributeValue(entityItem, "GENAREASELECTION", "", "");
/*  677 */         String str15 = "";
/*  678 */         EntityItem entityItem4 = null;
/*  679 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem3, "MODELGEOMOD", "GEOMOD");
/*  680 */         if (vector1.size() > 0) {
/*  681 */           for (int i = 0; i < vector1.size(); i++) {
/*  682 */             entityItem4 = vector1.elementAt(i);
/*  683 */             str15 = PokUtils.getAttributeValue(entityItem4, "GENAREASELECTION", "", "");
/*  684 */             if (str15.equals(str14)) {
/*  685 */               str4 = PokUtils.getAttributeValue(entityItem4, "EMEABRANDCD", "", "");
/*  686 */               str7 = PokUtils.getAttributeValue(entityItem4, "INTEGRATEDMODEL", "", "");
/*  687 */               i = vector1.size();
/*      */             } else {
/*  689 */               entityItem4 = null;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  694 */         stringBuffer.append(getValue("FBRAND", str4));
/*      */         
/*  696 */         str5 = "";
/*  697 */         str6 = PokUtils.getAttributeValue(entityItem3, "SYSIDUNIT", "", "");
/*  698 */         if (str6.equals("SIU-CPU")) {
/*  699 */           str5 = "CPU";
/*      */         }
/*  701 */         stringBuffer.append(getValue("FSLMCPU", str5));
/*  702 */         stringBuffer.append(getValue("FSLMIOP", str7));
/*  703 */         stringBuffer.append(getValue("FSLMMLC", PokUtils.getAttributeValue(entityItem3, "MACHLVLCNTRL", "", "")));
/*  704 */         stringBuffer.append(getValue("IDORIG", PokUtils.getAttributeValue(entityItem3, "SPECMODDESGN", "", "")));
/*  705 */         stringBuffer.append(getValue("POGMES", "0"));
/*  706 */         stringBuffer.append(getValue("STSPCFT", "    "));
/*  707 */         stringBuffer.append(getValue("TSLMDES", PokUtils.getAttributeValue(entityItem2, "INVNAME", "", "")));
/*  708 */         stringBuffer.append(getValue("TSLTDES", " "));
/*      */         
/*  710 */         String str16 = "N";
/*  711 */         String str17 = "N";
/*  712 */         String str18 = "N";
/*  713 */         String str19 = "N";
/*  714 */         String str20 = "N";
/*      */         
/*  716 */         Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem, "ANNGAA", "GENERALAREA");
/*  717 */         for (byte b2 = 0; b2 < vector2.size(); b2++) {
/*  718 */           EntityItem entityItem5 = vector2.elementAt(b2);
/*  719 */           String str = PokUtils.getAttributeValue(entityItem5, "RFAGEO", "", "");
/*  720 */           if (str.equals("AP")) {
/*  721 */             str16 = "Y";
/*  722 */           } else if (str.equals("CCN")) {
/*  723 */             str17 = "Y";
/*  724 */           } else if (str.equals("EMEA")) {
/*  725 */             str18 = "Y";
/*  726 */           } else if (str.equals("LA")) {
/*  727 */             str19 = "Y";
/*  728 */           } else if (str.equals("US")) {
/*  729 */             str20 = "Y";
/*      */           } 
/*      */         } 
/*      */         
/*  733 */         stringBuffer.append(getValue("XMAP", str16));
/*  734 */         stringBuffer.append(getValue("XMCCN", str17));
/*  735 */         stringBuffer.append(getValue("XMEMEA", str18));
/*  736 */         stringBuffer.append(getValue("XMLA", str19));
/*  737 */         stringBuffer.append(getValue("XMUS", str20));
/*      */         
/*  739 */         stringBuffer.append(getValue("TIMSTMP", " "));
/*  740 */         stringBuffer.append(getValue("USERID", " "));
/*      */         
/*  742 */         stringBuffer.append(NEWLINE);
/*  743 */         paramOutputStreamWriter.write(stringBuffer.toString());
/*  744 */         paramOutputStreamWriter.flush();
/*      */         
/*  746 */         str1 = "";
/*  747 */         str2 = "";
/*  748 */         str3 = "";
/*  749 */         str4 = "";
/*  750 */         str5 = "";
/*  751 */         str6 = "";
/*  752 */         str7 = "";
/*  753 */         str8 = "";
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
/*      */   private void createModelConvertRecords(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException, ParseException {
/*  771 */     String str1 = "";
/*  772 */     String str2 = "";
/*  773 */     String str3 = "";
/*  774 */     String str4 = "";
/*  775 */     String str5 = "";
/*  776 */     String str6 = "";
/*  777 */     String str7 = "";
/*      */     
/*  779 */     this.m_elist = getEntityList(getModelConvertVEName());
/*      */     
/*  781 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/*  783 */     String str8 = getEarliestPlanAvailDate(entityGroup);
/*  784 */     String str9 = getEarliestMESAvailDate(entityGroup);
/*  785 */     String str10 = PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", ",", "", false);
/*  786 */     String str11 = "";
/*  787 */     boolean bool = false;
/*      */     
/*  789 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  790 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*      */       
/*  792 */       str11 = PokUtils.getAttributeValue(entityItem1, "AVAILANNTYPE", "", "");
/*  793 */       if (str11.equals("EPIC")) {
/*  794 */         bool = true;
/*      */       }
/*      */       
/*  797 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "MODELCONVERTAVAIL", "MODELCONVERT");
/*  798 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem1, "MODELAVAIL", "MODEL");
/*  799 */       EntityItem entityItem2 = null;
/*  800 */       if (vector2.size() > 0) {
/*  801 */         entityItem2 = vector2.elementAt(0);
/*      */       }
/*      */       
/*  804 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  805 */         StringBuffer stringBuffer = new StringBuffer();
/*  806 */         stringBuffer.append(getValue("ISLMRFA", bool ? PokUtils.getAttributeValue(entityItem1, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem, "ANNNUMBER", "", "")));
/*  807 */         EntityItem entityItem3 = vector1.elementAt(b1);
/*      */         
/*  809 */         str1 = "";
/*  810 */         str2 = "";
/*  811 */         str3 = "";
/*  812 */         str1 = PokUtils.getAttributeValue(entityItem3, "FROMMACHTYPE", "", "", false);
/*  813 */         str1 = str1 + PokUtils.getAttributeValue(entityItem3, "FROMMODEL", "", "", false);
/*  814 */         str1 = str1 + PokUtils.getAttributeValue(entityItem3, "TOMACHTYPE", "", "", false);
/*  815 */         str1 = str1 + PokUtils.getAttributeValue(entityItem3, "TOMODEL", "", "", false);
/*  816 */         if (entityItem2 != null) {
/*  817 */           str2 = PokUtils.getAttributeValue(entityItem2, "SPECMODDESGN", "", "", false);
/*  818 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem2, "MODELSGMTACRONYMA", "SGMNTACRNYM");
/*  819 */           if (vector.size() > 0) {
/*  820 */             EntityItem entityItem = vector.elementAt(0);
/*  821 */             str3 = PokUtils.getAttributeValue(entityItem, "ACRNYM", "", "", false);
/*      */           } 
/*      */         } 
/*      */         
/*  825 */         stringBuffer.append(getValue("ISLMPRN", str1));
/*  826 */         stringBuffer.append(getValue("CSLMPCI", "TM"));
/*  827 */         stringBuffer.append(getValue("IPRTNUM", "            "));
/*  828 */         stringBuffer.append(getValue("FPUNINC", "2"));
/*  829 */         if (str10 != null && !str10.equals("")) {
/*  830 */           stringBuffer.append(getValue("DSLMCPA", str10));
/*      */         } else {
/*  832 */           stringBuffer.append(getValue("DSLMCPA", "          "));
/*      */         } 
/*  834 */         stringBuffer.append(getValue("DSLMGAD", str8));
/*  835 */         stringBuffer.append(getValue("DSLMMES", str9));
/*  836 */         stringBuffer.append(getValue("DSLMWDN", "2050-12-31"));
/*  837 */         stringBuffer.append(getValue("CJLBIC1", ""));
/*  838 */         stringBuffer.append(getValue("CJLBIDS", ""));
/*  839 */         stringBuffer.append(getValue("CJLBIDT", " "));
/*  840 */         stringBuffer.append(getValue("CJLBOEM", str2));
/*  841 */         stringBuffer.append(getValue("CJLBPOF", " "));
/*  842 */         stringBuffer.append(getValue("CJLBSAC", str3));
/*  843 */         stringBuffer.append(getValue("CREFIF", this.strCREFIF));
/*  844 */         stringBuffer.append(getValue("CLASSPT", PokUtils.getAttributeValue(entityItem1, "AVAILANNTYPE", "", "")));
/*      */         
/*  846 */         if (entityItem2 != null) {
/*  847 */           stringBuffer.append(getValue("CSLMFCC", PokUtils.getAttributeValue(entityItem2, "FUNCCLS", "", "")));
/*  848 */           stringBuffer.append(getValue("CSLMSYT", PokUtils.getAttributeValue(entityItem2, "SYSTEMTYPE", "", "")));
/*      */         } else {
/*  850 */           stringBuffer.append(getValue("CSLMFCC", " "));
/*  851 */           stringBuffer.append(getValue("CSLMSYT", " "));
/*      */         } 
/*      */         
/*  854 */         stringBuffer.append(getValue("CVOAT", " "));
/*      */         
/*  856 */         str4 = "";
/*  857 */         str7 = "";
/*  858 */         String str12 = PokUtils.getAttributeValue(entityItem1, "GENAREASELECTION", "", "");
/*  859 */         String str13 = "";
/*  860 */         EntityItem entityItem4 = null;
/*  861 */         Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem2, "MODELGEOMOD", "GEOMOD");
/*  862 */         if (vector3.size() > 0) {
/*  863 */           for (int i = 0; i < vector3.size(); i++) {
/*  864 */             entityItem4 = vector3.elementAt(i);
/*  865 */             str13 = PokUtils.getAttributeValue(entityItem4, "GENAREASELECTION", "", "");
/*  866 */             if (str13.equals(str12)) {
/*  867 */               str4 = PokUtils.getAttributeValue(entityItem4, "EMEABRANDCD", "", "");
/*  868 */               str7 = PokUtils.getAttributeValue(entityItem4, "INTEGRATEDMODEL", "", "");
/*  869 */               i = vector3.size();
/*      */             } else {
/*  871 */               entityItem4 = null;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  876 */         stringBuffer.append(getValue("FBRAND", str4));
/*      */         
/*  878 */         str5 = "";
/*  879 */         str6 = "";
/*  880 */         if (entityItem2 != null) {
/*  881 */           str6 = PokUtils.getAttributeValue(entityItem2, "SYSIDUNIT", "", "");
/*      */         }
/*      */         
/*  884 */         if (str6.equals("SIU-CPU")) {
/*  885 */           str5 = "CPU";
/*      */         }
/*      */         
/*  888 */         stringBuffer.append(getValue("FSLMCPU", str5));
/*  889 */         stringBuffer.append(getValue("FSLMIOP", str7));
/*      */         
/*  891 */         if (entityItem2 != null) {
/*  892 */           stringBuffer.append(getValue("FSLMMLC", PokUtils.getAttributeValue(entityItem2, "MACHLVLCNTRL", "", "")));
/*  893 */           stringBuffer.append(getValue("IDORIG", PokUtils.getAttributeValue(entityItem2, "SPECMODDESGN", "", "")));
/*      */         } else {
/*  895 */           stringBuffer.append(getValue("FSLMMLC", ""));
/*  896 */           stringBuffer.append(getValue("IDORIG", ""));
/*      */         } 
/*      */         
/*  899 */         stringBuffer.append(getValue("POGMES", " "));
/*  900 */         stringBuffer.append(getValue("STSPCFT", "    "));
/*      */         
/*  902 */         if (entityItem2 != null) {
/*  903 */           stringBuffer.append(getValue("TSLMDES", PokUtils.getAttributeValue(entityItem2, "INVNAME", "", "")));
/*      */         }
/*      */         
/*  906 */         stringBuffer.append(getValue("TSLTDES", " "));
/*      */         
/*  908 */         String str14 = "N";
/*  909 */         String str15 = "N";
/*  910 */         String str16 = "N";
/*  911 */         String str17 = "N";
/*  912 */         String str18 = "N";
/*      */         
/*  914 */         Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(paramEntityItem, "ANNGAA", "GENERALAREA");
/*  915 */         for (byte b2 = 0; b2 < vector4.size(); b2++) {
/*  916 */           EntityItem entityItem = vector4.elementAt(b2);
/*  917 */           String str = PokUtils.getAttributeValue(entityItem, "RFAGEO", "", "");
/*  918 */           if (str.equals("AP")) {
/*  919 */             str14 = "Y";
/*  920 */           } else if (str.equals("CCN")) {
/*  921 */             str15 = "Y";
/*  922 */           } else if (str.equals("EMEA")) {
/*  923 */             str16 = "Y";
/*  924 */           } else if (str.equals("LA")) {
/*  925 */             str17 = "Y";
/*  926 */           } else if (str.equals("US")) {
/*  927 */             str18 = "Y";
/*      */           } 
/*      */         } 
/*      */         
/*  931 */         stringBuffer.append(getValue("XMAP", str14));
/*  932 */         stringBuffer.append(getValue("XMCCN", str15));
/*  933 */         stringBuffer.append(getValue("XMEMEA", str16));
/*  934 */         stringBuffer.append(getValue("XMLA", str17));
/*  935 */         stringBuffer.append(getValue("XMUS", str18));
/*  936 */         stringBuffer.append(getValue("TIMSTMP", " "));
/*  937 */         stringBuffer.append(getValue("USERID", " "));
/*      */         
/*  939 */         stringBuffer.append(NEWLINE);
/*  940 */         paramOutputStreamWriter.write(stringBuffer.toString());
/*  941 */         paramOutputStreamWriter.flush();
/*      */         
/*  943 */         str1 = "";
/*  944 */         str2 = "";
/*  945 */         str3 = "";
/*  946 */         str4 = "";
/*  947 */         str5 = "";
/*  948 */         str6 = "";
/*  949 */         str7 = "";
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
/*      */   private String getEarliestMESAvailDate(EntityGroup paramEntityGroup) throws ParseException {
/*  963 */     EntityGroup entityGroup = paramEntityGroup;
/*  964 */     Date date1 = null;
/*  965 */     Date date2 = null;
/*  966 */     String str = "";
/*  967 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*      */     
/*  969 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  970 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  971 */       String str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/*  972 */       if (str1.equals("MES Planned Availability")) {
/*  973 */         str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false);
/*  974 */         date2 = simpleDateFormat.parse(str);
/*      */         
/*  976 */         if (date1 != null && date2 != null) {
/*      */           
/*  978 */           if (date1.after(date2))
/*      */           {
/*  980 */             date1 = date2;
/*      */           }
/*      */         }
/*  983 */         else if (date2 != null) {
/*      */           
/*  985 */           date1 = date2;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  990 */     if (date1 != null && !date1.equals("")) {
/*  991 */       return simpleDateFormat.format(date1);
/*      */     }
/*      */     
/*  994 */     return "2050-12-31";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getEarliestPlanAvailDate(EntityGroup paramEntityGroup) throws ParseException {
/* 1005 */     EntityGroup entityGroup = paramEntityGroup;
/* 1006 */     Date date1 = null;
/* 1007 */     Date date2 = null;
/* 1008 */     String str = "";
/* 1009 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*      */     
/* 1011 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1012 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 1013 */       String str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/* 1014 */       if (str1.equals("Planned Availability")) {
/* 1015 */         str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false);
/* 1016 */         date2 = simpleDateFormat.parse(str);
/*      */         
/* 1018 */         if (date1 != null && date2 != null) {
/*      */           
/* 1020 */           if (date1.after(date2))
/*      */           {
/* 1022 */             date1 = date2;
/*      */           }
/*      */         }
/* 1025 */         else if (date2 != null) {
/*      */           
/* 1027 */           date1 = date2;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1032 */     if (date1 != null && !date1.equals("")) {
/* 1033 */       return simpleDateFormat.format(date1);
/*      */     }
/*      */     
/* 1036 */     return "2050-12-31";
/*      */   }
/*      */   
/*      */   protected String getValue(String paramString1, String paramString2) {
/* 1040 */     if (paramString2 == null)
/* 1041 */       paramString2 = ""; 
/* 1042 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 1043 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1)
/* 1044 */         .toString());
/* 1045 */     if (i == j)
/* 1046 */       return paramString2; 
/* 1047 */     if (i > j) {
/* 1048 */       return paramString2.substring(0, j);
/*      */     }
/* 1050 */     return paramString2 + getBlank(j - i);
/*      */   }
/*      */   
/*      */   protected String getBlank(int paramInt) {
/* 1054 */     StringBuffer stringBuffer = new StringBuffer();
/* 1055 */     while (paramInt > 0) {
/* 1056 */       stringBuffer.append(" ");
/* 1057 */       paramInt--;
/*      */     } 
/* 1059 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/* 1069 */     if (paramAttributeChangeHistoryGroup != null) {
/* 1070 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1071 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1072 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1073 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1078 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1084 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 1092 */     String str = getBundle().getString(paramString);
/* 1093 */     if (paramArrayOfObject != null) {
/* 1094 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1095 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1098 */     addOutput(str);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1103 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1109 */     addOutput(paramString);
/* 1110 */     setReturnCode(-1);
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
/*      */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 1123 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 1124 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1127 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 1128 */     Object[] arrayOfObject = new Object[2];
/* 1129 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 1130 */     arrayOfObject[1] = this.navName;
/*      */     
/* 1132 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1141 */     String str = getBundle().getString(paramString2);
/*      */     
/* 1143 */     if (paramArrayOfObject != null) {
/* 1144 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1145 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1148 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 1158 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1168 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1171 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1172 */     if (eANList == null) {
/*      */       
/* 1174 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1175 */       eANList = entityGroup.getMetaAttribute();
/* 1176 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1178 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1180 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1181 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1182 */       if (b + 1 < eANList.size()) {
/* 1183 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1187 */     return stringBuffer.toString();
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
/*      */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 1199 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*      */     
/* 1201 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, getEntityType(), getEntityID()) });
/*      */ 
/*      */     
/* 1204 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 1205 */         PokUtils.outputList(entityList));
/*      */     
/* 1207 */     return entityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getModelVEName() {
/* 1216 */     return "QSMFULL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFeatureVEName() {
/* 1225 */     return "QSMFULL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProdstructVEName() {
/* 1233 */     return "QSMCREF2";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getModelConvertVEName() {
/* 1241 */     return "QSMCREF3";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1251 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1260 */     return "QSMCREFABRSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getAttributeHistory(String paramString) throws MiddlewareException {
/* 1270 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1272 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/* 1273 */     if (eANAttribute != null) {
/* 1274 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1276 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exeFtpShell(String paramString) {
/* 1285 */     String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_script", null) + " -f " + paramString;
/* 1286 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_inipath", null);
/* 1287 */     if (str2 != null)
/* 1288 */       str1 = str1 + " -i " + str2; 
/* 1289 */     if (this.dir != null)
/* 1290 */       str1 = str1 + " -d " + this.dir; 
/* 1291 */     if (this.fileprefix != null)
/* 1292 */       str1 = str1 + " -p " + this.fileprefix; 
/* 1293 */     String str3 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_targetfilename", null);
/* 1294 */     if (str3 != null)
/* 1295 */       str1 = str1 + " -t " + str3; 
/* 1296 */     String str4 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_logpath", null);
/* 1297 */     if (str4 != null)
/* 1298 */       str1 = str1 + " -l " + str4; 
/* 1299 */     String str5 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_backuppath", null);
/* 1300 */     if (str5 != null)
/* 1301 */       str1 = str1 + " -b " + str5; 
/* 1302 */     Runtime runtime = Runtime.getRuntime();
/* 1303 */     String str6 = "";
/* 1304 */     BufferedReader bufferedReader = null;
/* 1305 */     BufferedInputStream bufferedInputStream = null;
/*      */     
/*      */     try {
/* 1308 */       Process process = runtime.exec(str1);
/* 1309 */       if (process.waitFor() != 0) {
/* 1310 */         return false;
/*      */       }
/* 1312 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 1313 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 1314 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 1315 */         str6 = str6 + this.lineStr;
/* 1316 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 1317 */           return false;
/*      */         }
/*      */       } 
/* 1320 */     } catch (Exception exception) {
/* 1321 */       exception.printStackTrace();
/* 1322 */       return false;
/*      */     } finally {
/* 1324 */       if (bufferedReader != null) {
/*      */         try {
/* 1326 */           bufferedReader.close();
/* 1327 */           bufferedInputStream.close();
/* 1328 */         } catch (IOException iOException) {
/* 1329 */           iOException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 1333 */     return !(str6 == null);
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMCREFABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */