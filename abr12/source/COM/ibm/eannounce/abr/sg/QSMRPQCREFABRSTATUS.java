/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QSMRPQCREFABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  59 */   private StringBuffer rptSb = new StringBuffer();
/*  60 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  61 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*  63 */   private ResourceBundle rsBundle = null;
/*  64 */   private Hashtable metaTbl = new Hashtable<>();
/*  65 */   private String navName = "";
/*  66 */   private String strCREFIF = "";
/*     */   
/*  68 */   private String ffFileName = null;
/*  69 */   private String ffPathName = null;
/*  70 */   private String ffFTPPathName = null;
/*  71 */   private String dir = null;
/*  72 */   private String dirDest = null;
/*  73 */   private final String QSMRPTPATH = "_rptpath";
/*  74 */   private final String QSMGENPATH = "_genpath";
/*  75 */   private final String QSMFTPPATH = "_ftppath";
/*  76 */   private int abr_debuglvl = 0;
/*  77 */   private String fileprefix = null;
/*  78 */   private String lineStr = "";
/*     */   
/*     */   private static final String SUCCESS = "SUCCESS";
/*     */   
/*     */   private static final String FAILD = "FAILD";
/*     */   
/*     */   private static final String CREFINIPATH = "_inipath";
/*     */   
/*     */   private static final String FTPSCRPATH = "_script";
/*     */   
/*     */   private static final String TARGETFILENAME = "_targetfilename";
/*     */   
/*     */   private static final String LOGPATH = "_logpath";
/*     */   
/*     */   private static final String BACKUPPATH = "_backuppath";
/*     */   private static final String STATUS_PASSED = "0030";
/*     */   public static final String ISLMRFA = "ISLMRFA";
/*     */   public static final String ISLMPRN = "ISLMPRN";
/*     */   public static final String CSLMPCI = "CSLMPCI";
/*     */   public static final String IPRTNUM = "IPRTNUM";
/*     */   public static final String FPUNINC = "FPUNINC";
/*     */   public static final String DSLMCPA = "DSLMCPA";
/*     */   public static final String DSLMGAD = "DSLMGAD";
/*     */   public static final String DSLMMES = "DSLMMES";
/*     */   public static final String DSLMWDN = "DSLMWDN";
/*     */   public static final String CJLBIC1 = "CJLBIC1";
/*     */   public static final String CJLBIDS = "CJLBIDS";
/*     */   public static final String CJLBIDT = "CJLBIDT";
/*     */   public static final String CJLBOEM = "CJLBOEM";
/*     */   public static final String CJLBPOF = "CJLBPOF";
/*     */   public static final String CJLBSAC = "CJLBSAC";
/*     */   public static final String CREFIF = "CREFIF";
/*     */   public static final String CLASSPT = "CLASSPT";
/*     */   public static final String CSLMFCC = "CSLMFCC";
/*     */   public static final String CSLMSYT = "CSLMSYT";
/*     */   public static final String CVOAT = "CVOAT";
/*     */   public static final String FBRAND = "FBRAND";
/*     */   public static final String FSLMCPU = "FSLMCPU";
/*     */   public static final String FSLMIOP = "FSLMIOP";
/*     */   public static final String FSLMMLC = "FSLMMLC";
/*     */   public static final String IDORIG = "IDORIG";
/*     */   public static final String POGMES = "POGMES";
/*     */   public static final String STSPCFT = "STSPCFT";
/*     */   public static final String TSLMDES = "TSLMDES";
/*     */   public static final String TSLTDES = "TSLTDES";
/*     */   public static final String XMAP = "XMAP";
/*     */   public static final String XMCCN = "XMCCN";
/*     */   public static final String XMEMEA = "XMEMEA";
/*     */   public static final String XMLA = "XMLA";
/*     */   public static final String XMUS = "XMUS";
/*     */   public static final String TIMSTMP = "TIMSTMP";
/*     */   public static final String USERID = "USERID";
/* 130 */   private static final Hashtable COLUMN_LENGTH = new Hashtable<>(); static {
/* 131 */     COLUMN_LENGTH.put("ISLMRFA", "6");
/* 132 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/* 133 */     COLUMN_LENGTH.put("CSLMPCI", "2");
/* 134 */     COLUMN_LENGTH.put("IPRTNUM", "12");
/* 135 */     COLUMN_LENGTH.put("FPUNINC", "1");
/* 136 */     COLUMN_LENGTH.put("DSLMCPA", "10");
/* 137 */     COLUMN_LENGTH.put("DSLMGAD", "10");
/* 138 */     COLUMN_LENGTH.put("DSLMMES", "10");
/* 139 */     COLUMN_LENGTH.put("DSLMWDN", "10");
/* 140 */     COLUMN_LENGTH.put("CJLBIC1", "2");
/* 141 */     COLUMN_LENGTH.put("CJLBIDS", "1");
/* 142 */     COLUMN_LENGTH.put("CJLBIDT", "1");
/* 143 */     COLUMN_LENGTH.put("CJLBOEM", "1");
/* 144 */     COLUMN_LENGTH.put("CJLBPOF", "1");
/* 145 */     COLUMN_LENGTH.put("CJLBSAC", "3");
/* 146 */     COLUMN_LENGTH.put("CREFIF", "1");
/* 147 */     COLUMN_LENGTH.put("CLASSPT", "3");
/* 148 */     COLUMN_LENGTH.put("CSLMFCC", "4");
/* 149 */     COLUMN_LENGTH.put("CSLMSYT", "4");
/* 150 */     COLUMN_LENGTH.put("CVOAT", "1");
/* 151 */     COLUMN_LENGTH.put("FBRAND", "1");
/* 152 */     COLUMN_LENGTH.put("FSLMCPU", "1");
/* 153 */     COLUMN_LENGTH.put("FSLMIOP", "1");
/* 154 */     COLUMN_LENGTH.put("FSLMMLC", "1");
/* 155 */     COLUMN_LENGTH.put("IDORIG", "3");
/* 156 */     COLUMN_LENGTH.put("POGMES", "10");
/* 157 */     COLUMN_LENGTH.put("STSPCFT", "4");
/* 158 */     COLUMN_LENGTH.put("TSLMDES", "30");
/* 159 */     COLUMN_LENGTH.put("TSLTDES", "56");
/* 160 */     COLUMN_LENGTH.put("XMAP", "1");
/* 161 */     COLUMN_LENGTH.put("XMCCN", "1");
/* 162 */     COLUMN_LENGTH.put("XMEMEA", "1");
/* 163 */     COLUMN_LENGTH.put("XMLA", "1");
/* 164 */     COLUMN_LENGTH.put("XMUS", "1");
/* 165 */     COLUMN_LENGTH.put("TIMSTMP", "26");
/* 166 */     COLUMN_LENGTH.put("USERID", "8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceBundle getBundle() {
/* 173 */     return this.rsBundle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 187 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 189 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     String str3 = "";
/* 199 */     boolean bool = true;
/* 200 */     boolean bool1 = true;
/*     */ 
/*     */     
/* 203 */     String str4 = "";
/*     */     
/* 205 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/* 208 */       MessageFormat messageFormat = new MessageFormat(str1);
/* 209 */       arrayOfString[0] = getShortClassName(getClass());
/* 210 */       arrayOfString[1] = "ABR";
/* 211 */       str3 = messageFormat.format(arrayOfString);
/*     */ 
/*     */       
/* 214 */       setReturnCode(0);
/*     */       
/* 216 */       start_ABRBuild(false);
/*     */       
/* 218 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */       
/* 221 */       this.m_elist = getEntityList(getFeatureVEName());
/*     */       
/* 223 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 225 */       this.navName = getNavigationName();
/*     */       
/* 227 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getAttributeHistory("QSMRPQCREFABRSTATUS");
/* 228 */       if (existBefore(attributeChangeHistoryGroup, "0030")) {
/* 229 */         this.strCREFIF = "1";
/*     */       } else {
/* 231 */         this.strCREFIF = "0";
/*     */       } 
/*     */ 
/*     */       
/* 235 */       setDGString(getABRReturnCode());
/* 236 */       setDGRptName("QSMRPQCREFABRSTATUS");
/* 237 */       setDGTitle(this.navName);
/* 238 */       setDGRptClass(getABRCode());
/* 239 */       generateFlatFile(entityItem);
/* 240 */       exeFtpShell(this.ffPathName);
/*     */     }
/* 242 */     catch (Exception exception) {
/*     */       
/* 244 */       exception.printStackTrace();
/*     */       
/* 246 */       setReturnCode(-1);
/* 247 */       StringWriter stringWriter = new StringWriter();
/* 248 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 249 */       String str6 = "<pre>{0}</pre>";
/* 250 */       MessageFormat messageFormat = new MessageFormat(str5);
/* 251 */       setReturnCode(-3);
/* 252 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 254 */       arrayOfString[0] = exception.getMessage();
/* 255 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 256 */       messageFormat = new MessageFormat(str6);
/* 257 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 258 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 259 */       logError("Exception: " + exception.getMessage());
/* 260 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 262 */       setCreateDGEntity(true);
/* 263 */       bool = false;
/* 264 */       bool1 = exeFtpShell(this.ffPathName);
/*     */     } finally {
/* 266 */       if (!isReadOnly()) {
/* 267 */         clearSoftLock();
/*     */       }
/*     */       
/* 270 */       StringBuffer stringBuffer = new StringBuffer();
/* 271 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 272 */       arrayOfString[0] = this.m_prof.getOPName();
/* 273 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 274 */       arrayOfString[2] = this.m_prof.getWGName();
/* 275 */       arrayOfString[3] = getNow();
/* 276 */       stringBuffer.append(bool ? "generated the QSM report file successful " : "generated the QSM report file faild");
/* 277 */       stringBuffer.append(",");
/* 278 */       stringBuffer.append(bool1 ? "send the QSM report file successful " : "sent the QSM report file faild");
/* 279 */       arrayOfString[4] = stringBuffer.toString();
/* 280 */       arrayOfString[5] = str4 + " " + getABRVersion();
/*     */       
/* 282 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 284 */       println(EACustom.getDocTypeHtml());
/* 285 */       println(this.rptSb.toString());
/* 286 */       printDGSubmitString();
/*     */       
/* 288 */       println(EACustom.getTOUDiv());
/* 289 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFileName(EntityItem paramEntityItem) {
/* 302 */     this.fileprefix = ABRServerProperties.getFilePrefix(this.m_abri
/* 303 */         .getABRCode());
/*     */ 
/*     */     
/* 306 */     StringBuffer stringBuffer = new StringBuffer(this.fileprefix.trim());
/* 307 */     stringBuffer.append(paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "_");
/* 308 */     String str = getNow();
/*     */     
/* 310 */     str = str.replace(' ', '_');
/* 311 */     stringBuffer.append(str + ".txt");
/* 312 */     this.dir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_genpath", "/Dgq");
/* 313 */     if (!this.dir.endsWith("/")) {
/* 314 */       this.dir += "/";
/*     */     }
/* 316 */     this.ffFileName = stringBuffer.toString();
/* 317 */     this.ffPathName = this.dir + this.ffFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException, SQLException, MiddlewareException, ParseException {
/* 330 */     FileChannel fileChannel1 = null;
/* 331 */     FileChannel fileChannel2 = null;
/*     */ 
/*     */     
/* 334 */     setFileName(paramEntityItem);
/*     */     
/* 336 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*     */ 
/*     */ 
/*     */     
/* 340 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*     */     
/* 342 */     createFeatureRecords(paramEntityItem, outputStreamWriter);
/*     */     
/* 344 */     outputStreamWriter.close();
/*     */     
/* 346 */     this.dirDest = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_ftppath", "/Dgq");
/* 347 */     if (!this.dirDest.endsWith("/")) {
/* 348 */       this.dirDest += "/";
/*     */     }
/*     */     
/* 351 */     this.ffFTPPathName = this.dirDest + this.ffFileName;
/* 352 */     addDebug("******* " + this.ffFTPPathName);
/*     */     
/*     */     try {
/* 355 */       fileChannel1 = (new FileInputStream(this.ffPathName)).getChannel();
/* 356 */       fileChannel2 = (new FileOutputStream(this.ffFTPPathName)).getChannel();
/* 357 */       fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
/*     */     } finally {
/* 359 */       fileChannel1.close();
/* 360 */       fileChannel2.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createFeatureRecords(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException, ParseException {
/* 377 */     String str1 = "";
/* 378 */     String str2 = "";
/* 379 */     String str3 = "";
/* 380 */     String str4 = "";
/* 381 */     String str5 = "";
/* 382 */     String str6 = "";
/* 383 */     String str7 = "";
/* 384 */     String str8 = "";
/*     */     
/* 386 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*     */     
/* 388 */     String str9 = getEarliestGENAVAILDATE(entityGroup);
/*     */     
/* 390 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 391 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*     */       
/* 393 */       String str10 = PokUtils.getAttributeValue(entityItem1, "ANNDATE", ",", "", false);
/*     */       
/* 395 */       StringBuffer stringBuffer = new StringBuffer();
/* 396 */       stringBuffer.append(getValue("ISLMRFA", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", ",", "", false)));
/*     */       
/* 398 */       Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 399 */       EntityItem entityItem2 = vector1.elementAt(0);
/* 400 */       str1 = "";
/* 401 */       str2 = "";
/* 402 */       str3 = "";
/* 403 */       str2 = PokUtils.getAttributeValue(entityItem2, "SPECMODDESGN", "", "", false);
/* 404 */       EntityGroup entityGroup1 = this.m_elist.getEntityGroup("SGMNTACRNYM");
/* 405 */       if (entityGroup1 != null && entityGroup1.hasData()) {
/* 406 */         EntityItem entityItem = entityGroup1.getEntityItem(0);
/* 407 */         str3 = PokUtils.getAttributeValue(entityItem, "ACRNYM", "", "", false);
/*     */       } 
/*     */       
/* 410 */       str1 = PokUtils.getAttributeValue(entityItem2, "MACHTYPEATR", "", "", false);
/* 411 */       str1 = str1 + PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "", false);
/* 412 */       stringBuffer.append(getValue("ISLMPRN", str1));
/*     */       
/* 414 */       str8 = "";
/* 415 */       str8 = PokUtils.getAttributeValue(paramEntityItem, "FCTYPE", "", "", false);
/* 416 */       if (str8.equals("RPQ-RLISTED") || str8.equals("RPQ-ILISTED") || str8.equals("RPQ-PLISTED")) {
/* 417 */         stringBuffer.append(getValue("CSLMPCI", "MQ"));
/*     */       } else {
/* 419 */         stringBuffer.append(getValue("CSLMPCI", "MF"));
/*     */       } 
/* 421 */       stringBuffer.append(getValue("IPRTNUM", "            "));
/* 422 */       stringBuffer.append(getValue("FPUNINC", "2"));
/* 423 */       if (str10 != null && !str10.equals("")) {
/* 424 */         stringBuffer.append(getValue("DSLMCPA", str10));
/*     */       } else {
/* 426 */         stringBuffer.append(getValue("DSLMCPA", "          "));
/*     */       } 
/* 428 */       stringBuffer.append(getValue("DSLMGAD", str9));
/* 429 */       stringBuffer.append(getValue("DSLMMES", str9));
/* 430 */       stringBuffer.append(getValue("DSLMWDN", "2050-12-31"));
/* 431 */       stringBuffer.append(getValue("CJLBIC1", ""));
/* 432 */       stringBuffer.append(getValue("CJLBIDS", ""));
/* 433 */       stringBuffer.append(getValue("CJLBIDT", " "));
/* 434 */       stringBuffer.append(getValue("CJLBOEM", str2));
/* 435 */       stringBuffer.append(getValue("CJLBPOF", " "));
/*     */       
/* 437 */       stringBuffer.append(getValue("CJLBSAC", str3));
/*     */       
/* 439 */       stringBuffer.append(getValue("CREFIF", this.strCREFIF));
/*     */       
/* 441 */       stringBuffer.append(getValue("CLASSPT", "IHW"));
/* 442 */       stringBuffer.append(getValue("CSLMFCC", PokUtils.getAttributeValue(entityItem2, "FUNCCLS", "", "")));
/* 443 */       stringBuffer.append(getValue("CSLMSYT", PokUtils.getAttributeValue(entityItem2, "SYSTEMTYPE", "", "")));
/* 444 */       stringBuffer.append(getValue("CVOAT", " "));
/*     */       
/* 446 */       str4 = "";
/* 447 */       str7 = "";
/* 448 */       String str11 = PokUtils.getAttributeValue(paramEntityItem, "GENAREASELECTION", "", "");
/* 449 */       String str12 = "";
/* 450 */       EntityItem entityItem3 = null;
/* 451 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem2, "MODELGEOMOD", "GEOMOD");
/* 452 */       if (vector2.size() > 0) {
/* 453 */         for (int i = 0; i < vector2.size(); i++) {
/* 454 */           entityItem3 = vector2.elementAt(i);
/* 455 */           str12 = PokUtils.getAttributeValue(entityItem3, "GENAREASELECTION", "", "");
/* 456 */           if (str12.equals(str11)) {
/* 457 */             str4 = PokUtils.getAttributeValue(entityItem3, "EMEABRANDCD", "", "");
/* 458 */             str7 = PokUtils.getAttributeValue(entityItem3, "INTEGRATEDMODEL", "", "");
/* 459 */             i = vector2.size();
/*     */           } else {
/* 461 */             entityItem3 = null;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 466 */       stringBuffer.append(getValue("FBRAND", str4));
/*     */       
/* 468 */       str5 = "";
/* 469 */       str6 = PokUtils.getAttributeValue(entityItem2, "SYSIDUNIT", "", "");
/* 470 */       if (str6.equals("SIU-CPU")) {
/* 471 */         str5 = "CPU";
/*     */       }
/* 473 */       stringBuffer.append(getValue("FSLMCPU", str5));
/* 474 */       stringBuffer.append(getValue("FSLMIOP", str7));
/* 475 */       stringBuffer.append(getValue("FSLMMLC", PokUtils.getAttributeValue(entityItem2, "MACHLVLCNTRL", "", "")));
/* 476 */       stringBuffer.append(getValue("IDORIG", PokUtils.getAttributeValue(entityItem2, "SPECMODDESGN", "", "")));
/* 477 */       stringBuffer.append(getValue("POGMES", "0"));
/* 478 */       stringBuffer.append(getValue("STSPCFT", "    "));
/* 479 */       stringBuffer.append(getValue("TSLMDES", PokUtils.getAttributeValue(paramEntityItem, "INVNAME", "", "")));
/* 480 */       stringBuffer.append(getValue("TSLTDES", " "));
/*     */       
/* 482 */       String str13 = "N";
/* 483 */       String str14 = "N";
/* 484 */       String str15 = "N";
/* 485 */       String str16 = "N";
/* 486 */       String str17 = "N";
/*     */       
/* 488 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/* 489 */       if (eANFlagAttribute != null) {
/* 490 */         if (eANFlagAttribute.isSelected("1999")) {
/* 491 */           str13 = "Y";
/* 492 */           str14 = "Y";
/* 493 */           str15 = "Y";
/* 494 */           str16 = "Y";
/* 495 */           str17 = "Y";
/*     */         } else {
/* 497 */           if (eANFlagAttribute.isSelected("6199")) {
/* 498 */             str13 = "Y";
/*     */           }
/* 500 */           if (eANFlagAttribute.isSelected("6200")) {
/* 501 */             str14 = "Y";
/*     */           }
/* 503 */           if (eANFlagAttribute.isSelected("6198")) {
/* 504 */             str15 = "Y";
/*     */           }
/* 506 */           if (eANFlagAttribute.isSelected("6204")) {
/* 507 */             str16 = "Y";
/*     */           }
/* 509 */           if (eANFlagAttribute.isSelected("6221")) {
/* 510 */             str17 = "Y";
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 515 */       stringBuffer.append(getValue("XMAP", str13));
/* 516 */       stringBuffer.append(getValue("XMCCN", str14));
/* 517 */       stringBuffer.append(getValue("XMEMEA", str15));
/* 518 */       stringBuffer.append(getValue("XMLA", str16));
/* 519 */       stringBuffer.append(getValue("XMUS", str17));
/*     */       
/* 521 */       stringBuffer.append(getValue("TIMSTMP", " "));
/* 522 */       stringBuffer.append(getValue("USERID", " "));
/*     */       
/* 524 */       stringBuffer.append(NEWLINE);
/* 525 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 526 */       paramOutputStreamWriter.flush();
/*     */       
/* 528 */       str1 = "";
/* 529 */       str2 = "";
/* 530 */       str3 = "";
/* 531 */       str4 = "";
/* 532 */       str5 = "";
/* 533 */       str6 = "";
/* 534 */       str7 = "";
/* 535 */       str8 = "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getEarliestGENAVAILDATE(EntityGroup paramEntityGroup) throws ParseException {
/* 547 */     EntityGroup entityGroup = paramEntityGroup;
/* 548 */     Date date1 = null;
/* 549 */     Date date2 = null;
/* 550 */     String str = "";
/* 551 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*     */     
/* 553 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 554 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 555 */       str = PokUtils.getAttributeValue(entityItem, "GENAVAILDATE", ",", "", false);
/*     */       
/* 557 */       if (!str.equals("")) {
/* 558 */         date2 = simpleDateFormat.parse(str);
/*     */         
/* 560 */         if (date1 != null && date2 != null) {
/*     */           
/* 562 */           if (date1.after(date2))
/*     */           {
/* 564 */             date1 = date2;
/*     */           }
/*     */         }
/* 567 */         else if (date2 != null) {
/*     */           
/* 569 */           date1 = date2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 574 */     if (date1 != null && !date1.equals("")) {
/* 575 */       return simpleDateFormat.format(date1);
/*     */     }
/*     */     
/* 578 */     return "2050-12-31";
/*     */   }
/*     */   
/*     */   protected String getValue(String paramString1, String paramString2) {
/* 582 */     if (paramString2 == null)
/* 583 */       paramString2 = ""; 
/* 584 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 585 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1)
/* 586 */         .toString());
/* 587 */     if (i == j)
/* 588 */       return paramString2; 
/* 589 */     if (i > j) {
/* 590 */       return paramString2.substring(0, j);
/*     */     }
/* 592 */     return paramString2 + getBlank(j - i);
/*     */   }
/*     */   
/*     */   protected String getBlank(int paramInt) {
/* 596 */     StringBuffer stringBuffer = new StringBuffer();
/* 597 */     while (paramInt > 0) {
/* 598 */       stringBuffer.append(" ");
/* 599 */       paramInt--;
/*     */     } 
/* 601 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/* 611 */     if (paramAttributeChangeHistoryGroup != null) {
/* 612 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 613 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 614 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 615 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 620 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 626 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 634 */     String str = getBundle().getString(paramString);
/* 635 */     if (paramArrayOfObject != null) {
/* 636 */       MessageFormat messageFormat = new MessageFormat(str);
/* 637 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 640 */     addOutput(str);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 645 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 651 */     addOutput(paramString);
/* 652 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 665 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 666 */     setReturnCode(-1);
/*     */ 
/*     */     
/* 669 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 670 */     Object[] arrayOfObject = new Object[2];
/* 671 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 672 */     arrayOfObject[1] = this.navName;
/*     */     
/* 674 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 683 */     String str = getBundle().getString(paramString2);
/*     */     
/* 685 */     if (paramArrayOfObject != null) {
/* 686 */       MessageFormat messageFormat = new MessageFormat(str);
/* 687 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 690 */     addOutput(paramString1 + " " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 700 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 710 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 713 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 714 */     if (eANList == null) {
/*     */       
/* 716 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 717 */       eANList = entityGroup.getMetaAttribute();
/* 718 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 720 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 722 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 723 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 724 */       if (b + 1 < eANList.size()) {
/* 725 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/*     */     
/* 729 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 741 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*     */     
/* 743 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, getEntityType(), getEntityID()) });
/*     */ 
/*     */     
/* 746 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 747 */         PokUtils.outputList(entityList));
/*     */     
/* 749 */     return entityList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFeatureVEName() {
/* 757 */     return "QSMRPQFULLVE1";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 767 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 776 */     return "QSMRPQCREFABRSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributeChangeHistoryGroup getAttributeHistory(String paramString) throws MiddlewareException {
/* 786 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */     
/* 788 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/* 789 */     if (eANAttribute != null) {
/* 790 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*     */     }
/*     */     
/* 793 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exeFtpShell(String paramString) {
/* 805 */     String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_script", null) + " -f " + paramString;
/* 806 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_inipath", null);
/* 807 */     if (str2 != null)
/* 808 */       str1 = str1 + " -i " + str2; 
/* 809 */     if (this.dir != null)
/* 810 */       str1 = str1 + " -d " + this.dir; 
/* 811 */     if (this.fileprefix != null)
/* 812 */       str1 = str1 + " -p " + this.fileprefix; 
/* 813 */     String str3 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_targetfilename", null);
/* 814 */     if (str3 != null)
/* 815 */       str1 = str1 + " -t " + str3; 
/* 816 */     String str4 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_logpath", null);
/* 817 */     if (str4 != null)
/* 818 */       str1 = str1 + " -l " + str4; 
/* 819 */     String str5 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_backuppath", null);
/* 820 */     if (str5 != null)
/* 821 */       str1 = str1 + " -b " + str5; 
/* 822 */     Runtime runtime = Runtime.getRuntime();
/* 823 */     String str6 = "";
/* 824 */     BufferedReader bufferedReader = null;
/* 825 */     BufferedInputStream bufferedInputStream = null;
/*     */     try {
/* 827 */       Process process = runtime.exec(str1);
/* 828 */       if (process.waitFor() != 0) {
/* 829 */         return false;
/*     */       }
/* 831 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 832 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 833 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 834 */         str6 = str6 + this.lineStr;
/* 835 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 836 */           return false;
/*     */         }
/*     */       } 
/* 839 */     } catch (Exception exception) {
/* 840 */       exception.printStackTrace();
/* 841 */       return false;
/*     */     } finally {
/* 843 */       if (bufferedReader != null) {
/*     */         try {
/* 845 */           bufferedReader.close();
/* 846 */           bufferedInputStream.close();
/* 847 */         } catch (IOException iOException) {
/* 848 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 852 */     return !(str6 == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMRPQCREFABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */