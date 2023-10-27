/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class QSMRPQWITHDRAWALFULLABR extends PokBaseABR {
/*  33 */   private StringBuffer rptSb = new StringBuffer();
/*  34 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  35 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*  37 */   private ResourceBundle rsBundle = null;
/*  38 */   private Hashtable metaTbl = new Hashtable<>();
/*  39 */   private String navName = "";
/*     */   
/*  41 */   private String ffFileName = null;
/*  42 */   private String ffPathName = null;
/*  43 */   private String ffFTPPathName = null;
/*  44 */   private String dir = null;
/*  45 */   private String dirDest = null;
/*  46 */   private final String QSMRPTPATH = "_rptpath";
/*  47 */   private final String QSMGENPATH = "_genpath";
/*  48 */   private final String QSMFTPPATH = "_ftppath";
/*  49 */   private int abr_debuglvl = 0;
/*  50 */   private String abrcode = "";
/*     */   
/*     */   private static final String CREFINIPATH = "_inipath";
/*     */   private static final String FTPSCRPATH = "_script";
/*     */   private static final String TARGETFILENAME = "_wdtargetfilename";
/*     */   private static final String LOGPATH = "_logpath";
/*     */   private static final String BACKUPPATH = "_backuppath";
/*     */   private static final String FILEPREFIX = "_wdfilePrefix";
/*  58 */   private String lineStr = "";
/*     */   
/*     */   private EntityItem rootEntity;
/*     */   private static final String FAILD = "FAILD";
/*     */   private static final String IFTYPE = "IFTYPE";
/*     */   private static final String IOPUCTY = "IOPUCTY";
/*     */   private static final String ISLMPAL = "ISLMPAL";
/*     */   private static final String ISLMRFA = "ISLMRFA";
/*     */   private static final String ISLMPRN = "ISLMPRN";
/*     */   private static final String DSLMWDN = "DSLMWDN";
/*     */   private static final String ISLMTYP = "ISLMTYP";
/*     */   private static final String ISLMMOD = "ISLMMOD";
/*     */   private static final String ISLMFTR = "ISLMFTR";
/*     */   private static final Hashtable COLUMN_LENGTH;
/*  72 */   private static final List geoWWList = Collections.unmodifiableList(new ArrayList()
/*     */       {
/*     */       
/*     */       });
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
/*     */   static {
/*  87 */     COLUMN_LENGTH = new Hashtable<>();
/*  88 */     COLUMN_LENGTH.put("IFTYPE", "20");
/*  89 */     COLUMN_LENGTH.put("IOPUCTY", "3");
/*  90 */     COLUMN_LENGTH.put("ISLMPAL", "8");
/*  91 */     COLUMN_LENGTH.put("ISLMRFA", "6");
/*  92 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/*  93 */     COLUMN_LENGTH.put("DSLMWDN", "10");
/*  94 */     COLUMN_LENGTH.put("ISLMTYP", "4");
/*  95 */     COLUMN_LENGTH.put("ISLMMOD", "3");
/*  96 */     COLUMN_LENGTH.put("ISLMFTR", "6");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceBundle getBundle() {
/* 103 */     return this.rsBundle;
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
/*     */ 
/*     */   
/*     */   public void processThis(QSMRPQFULLABRSTATUS paramQSMRPQFULLABRSTATUS, Profile paramProfile, Database paramDatabase, String paramString, StringBuffer paramStringBuffer, EntityItem paramEntityItem) throws Exception {
/*     */     try {
/* 122 */       this.m_prof = paramProfile;
/* 123 */       this.m_db = paramDatabase;
/* 124 */       this.rootEntity = paramEntityItem;
/* 125 */       this.abrcode = paramString;
/* 126 */       this.rptSb = paramStringBuffer;
/*     */       
/* 128 */       this.m_elist = getEntityList(getFeatureVEName());
/*     */       
/* 130 */       if (this.m_elist.getEntityGroupCount() > 0)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 136 */         generateFlatFile(paramEntityItem);
/* 137 */         exeFtpShell(this.ffPathName);
/*     */       }
/*     */     
/* 140 */     } catch (IOException iOException) {
/*     */       
/* 142 */       iOException.printStackTrace();
/* 143 */     } catch (ParseException parseException) {
/*     */       
/* 145 */       parseException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setFileName(EntityItem paramEntityItem) {
/* 150 */     String str = ABRServerProperties.getValue(this.abrcode, "_wdfilePrefix", null);
/* 151 */     StringBuffer stringBuffer = new StringBuffer(str.trim());
/*     */     
/*     */     try {
/* 154 */       DatePackage datePackage = this.m_db.getDates();
/* 155 */       String str1 = datePackage.getNow();
/*     */       
/* 157 */       str1 = str1.replace(' ', '_');
/* 158 */       stringBuffer.append(paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "_");
/* 159 */       stringBuffer.append(str1 + ".txt");
/* 160 */       this.dir = ABRServerProperties.getValue(this.abrcode, "_genpath", "/Dgq");
/* 161 */       if (!this.dir.endsWith("/")) {
/* 162 */         this.dir += "/";
/*     */       }
/* 164 */       this.ffFileName = stringBuffer.toString();
/* 165 */       this.ffPathName = this.dir + this.ffFileName;
/* 166 */     } catch (MiddlewareException middlewareException) {
/*     */       
/* 168 */       middlewareException.printStackTrace();
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
/*     */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException, SQLException, MiddlewareException, ParseException {
/* 184 */     FileChannel fileChannel1 = null;
/* 185 */     FileChannel fileChannel2 = null;
/*     */ 
/*     */     
/* 188 */     setFileName(paramEntityItem);
/*     */     
/* 190 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/* 191 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*     */     
/* 193 */     createT006Feature(paramEntityItem, outputStreamWriter);
/* 194 */     createT632TypeModelFeatureRelation(paramEntityItem, outputStreamWriter);
/*     */     
/* 196 */     outputStreamWriter.close();
/*     */     
/* 198 */     this.dirDest = ABRServerProperties.getValue(this.abrcode, "_ftppath", "/Dgq");
/* 199 */     if (!this.dirDest.endsWith("/")) {
/* 200 */       this.dirDest += "/";
/*     */     }
/*     */     
/* 203 */     this.ffFTPPathName = this.dirDest + this.ffFileName;
/*     */     
/*     */     try {
/* 206 */       fileChannel1 = (new FileInputStream(this.ffPathName)).getChannel();
/* 207 */       fileChannel2 = (new FileOutputStream(this.ffFTPPathName)).getChannel();
/* 208 */       fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
/*     */     } finally {
/* 210 */       fileChannel1.close();
/* 211 */       fileChannel2.close();
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
/*     */ 
/*     */   
/*     */   private void createT006Feature(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 230 */     EANFlagAttribute eANFlagAttribute = null;
/*     */     
/* 232 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*     */     
/* 234 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 236 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*     */       
/* 238 */       eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/* 239 */       if (eANFlagAttribute != null) {
/* 240 */         if (eANFlagAttribute.isSelected("1999")) {
/* 241 */           for (byte b1 = 0; b1 < geoWWList.size(); b1++) {
/* 242 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, geoWWList.get(b1));
/*     */           }
/*     */         } else {
/* 245 */           if (eANFlagAttribute.isSelected("6199")) {
/* 246 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific");
/*     */           }
/* 248 */           if (eANFlagAttribute.isSelected("6200")) {
/* 249 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North");
/*     */           }
/* 251 */           if (eANFlagAttribute.isSelected("6198")) {
/* 252 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa");
/*     */           }
/* 254 */           if (eANFlagAttribute.isSelected("6204")) {
/* 255 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America");
/*     */           }
/* 257 */           if (eANFlagAttribute.isSelected("6221")) {
/* 258 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only");
/*     */           }
/*     */         } 
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createT006FeatureRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString) throws IOException, SQLException, MiddlewareException {
/* 282 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem1, "PRODSTRUCT", "MODEL");
/*     */     
/* 284 */     for (byte b = 0; b < vector.size(); b++) {
/* 285 */       StringBuffer stringBuffer = new StringBuffer();
/* 286 */       EntityItem entityItem = vector.elementAt(b);
/* 287 */       stringBuffer = new StringBuffer();
/* 288 */       String str1 = "";
/* 289 */       String str2 = "";
/* 290 */       String str3 = "";
/* 291 */       str2 = "";
/*     */       
/* 293 */       stringBuffer.append(getValue("IFTYPE", "F=CHKT631&UPGT005"));
/*     */       
/* 295 */       if (paramString.equals("Latin America")) {
/* 296 */         str1 = "601";
/* 297 */         str3 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/* 298 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/* 299 */         str1 = "999";
/* 300 */         str3 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/* 301 */       } else if (paramString.equals("Asia Pacific")) {
/* 302 */         str1 = "872";
/* 303 */         str3 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/* 304 */       } else if (paramString.equals("US Only")) {
/* 305 */         str1 = "897";
/* 306 */         str3 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/* 307 */       } else if (paramString.equals("Canada and Caribbean North")) {
/* 308 */         str1 = "649";
/* 309 */         str3 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*     */       } 
/*     */       
/* 312 */       stringBuffer.append(getValue("IOPUCTY", str1));
/* 313 */       stringBuffer.append(getValue("ISLMPAL", str3));
/* 314 */       stringBuffer.append(getValue("ISLMRFA", PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "")));
/* 315 */       String str4 = PokUtils.getAttributeValue(entityItem, "MACHTYPEATR", ",", "", false);
/* 316 */       str4 = str4 + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", ",", "", false);
/* 317 */       stringBuffer.append(getValue("ISLMPRN", str4));
/*     */       
/* 319 */       str2 = PokUtils.getAttributeValue(paramEntityItem1, "WITHDRAWDATEEFF_T", ",", "", false);
/* 320 */       if (str2.equals("")) {
/* 321 */         str2 = "2050-12-31";
/*     */       }
/* 323 */       stringBuffer.append(getValue("DSLMWDN", str2));
/*     */       
/* 325 */       stringBuffer.append(NEWLINE);
/* 326 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 327 */       paramOutputStreamWriter.flush();
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void createT632TypeModelFeatureRelation(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 347 */     this.m_elist = getEntityList(getFeatureVEName());
/*     */ 
/*     */     
/* 350 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/* 351 */     if (eANFlagAttribute != null && (
/* 352 */       eANFlagAttribute.isSelected("6221") || eANFlagAttribute.isSelected("1999"))) {
/*     */       
/* 354 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*     */       
/* 356 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 357 */         StringBuffer stringBuffer = new StringBuffer();
/*     */         
/* 359 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*     */         
/* 361 */         Vector<EntityItem> vector = entityItem.getDownLink();
/*     */         
/* 363 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/*     */           
/* 365 */           EntityItem entityItem1 = vector.elementAt(b1);
/*     */           
/* 367 */           stringBuffer = new StringBuffer();
/* 368 */           String str = "";
/*     */           
/* 370 */           stringBuffer.append(getValue("IFTYPE", "T=(CHK&UPG)T631"));
/* 371 */           stringBuffer.append(getValue("IOPUCTY", "897"));
/* 372 */           stringBuffer.append(getValue("ISLMPAL", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 373 */           stringBuffer.append(getValue("ISLMRFA", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 374 */           stringBuffer.append(getValue("ISLMTYP", PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", "", "")));
/* 375 */           stringBuffer.append(getValue("ISLMMOD", PokUtils.getAttributeValue(entityItem1, "MODELATR", "", "")));
/* 376 */           stringBuffer.append(getValue("ISLMFTR", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 377 */           str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", "", "");
/* 378 */           if (str.equals("")) {
/* 379 */             str = "2050-12-31";
/*     */           }
/*     */           
/* 382 */           stringBuffer.append(getValue("DSLMWDN", str));
/*     */           
/* 384 */           stringBuffer.append(NEWLINE);
/* 385 */           paramOutputStreamWriter.write(stringBuffer.toString());
/* 386 */           paramOutputStreamWriter.flush();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getValue(String paramString1, String paramString2) {
/* 395 */     if (paramString2 == null)
/* 396 */       paramString2 = ""; 
/* 397 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 398 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1).toString());
/* 399 */     if (i == j)
/* 400 */       return paramString2; 
/* 401 */     if (i > j) {
/* 402 */       return paramString2.substring(0, j);
/*     */     }
/* 404 */     return paramString2 + getBlank(j - i);
/*     */   }
/*     */   
/*     */   protected String getBlank(int paramInt) {
/* 408 */     StringBuffer stringBuffer = new StringBuffer();
/* 409 */     while (paramInt > 0) {
/* 410 */       stringBuffer.append(" ");
/* 411 */       paramInt--;
/*     */     } 
/* 413 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 421 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*     */     
/* 423 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, this.rootEntity
/* 424 */             .getEntityType(), this.rootEntity.getEntityID()) });
/*     */ 
/*     */     
/* 427 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 428 */         PokUtils.outputList(entityList));
/*     */     
/* 430 */     return entityList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFeatureVEName() {
/* 439 */     return "QSMRPQFULLVE1";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exeFtpShell(String paramString) {
/* 447 */     String str1 = ABRServerProperties.getValue(this.abrcode, "_script", null) + " -f " + paramString;
/* 448 */     String str2 = ABRServerProperties.getValue(this.abrcode, "_inipath", null);
/* 449 */     if (str2 != null)
/* 450 */       str1 = str1 + " -i " + str2; 
/* 451 */     if (this.dir != null)
/* 452 */       str1 = str1 + " -d " + this.dir; 
/* 453 */     String str3 = ABRServerProperties.getValue(this.abrcode, "_wdfilePrefix", null);
/* 454 */     if (str3 != null)
/* 455 */       str1 = str1 + " -p " + str3; 
/* 456 */     String str4 = ABRServerProperties.getValue(this.abrcode, "_wdtargetfilename", null);
/* 457 */     if (str4 != null)
/* 458 */       str1 = str1 + " -t " + str4; 
/* 459 */     String str5 = ABRServerProperties.getValue(this.abrcode, "_logpath", null);
/* 460 */     if (str5 != null)
/* 461 */       str1 = str1 + " -l " + str5; 
/* 462 */     String str6 = ABRServerProperties.getValue(this.abrcode, "_backuppath", null);
/* 463 */     if (str6 != null)
/* 464 */       str1 = str1 + " -b " + str6; 
/* 465 */     Runtime runtime = Runtime.getRuntime();
/* 466 */     String str7 = "";
/* 467 */     BufferedReader bufferedReader = null;
/* 468 */     BufferedInputStream bufferedInputStream = null;
/* 469 */     addDebug("cmd:" + str1);
/*     */     try {
/* 471 */       Process process = runtime.exec(str1);
/* 472 */       if (process.waitFor() != 0) {
/* 473 */         return false;
/*     */       }
/* 475 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 476 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 477 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 478 */         str7 = str7 + this.lineStr;
/* 479 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 480 */           return false;
/*     */         }
/*     */       } 
/* 483 */     } catch (Exception exception) {
/* 484 */       exception.printStackTrace();
/* 485 */       return false;
/*     */     } finally {
/* 487 */       if (bufferedReader != null) {
/*     */         try {
/* 489 */           bufferedReader.close();
/* 490 */           bufferedInputStream.close();
/* 491 */         } catch (IOException iOException) {
/* 492 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 496 */     return !(str7 == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 503 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 511 */     String str = getBundle().getString(paramString);
/* 512 */     if (paramArrayOfObject != null) {
/* 513 */       MessageFormat messageFormat = new MessageFormat(str);
/* 514 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 517 */     addOutput(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 524 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 531 */     addOutput(paramString);
/* 532 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 543 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 544 */     setReturnCode(-1);
/*     */ 
/*     */     
/* 547 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 548 */     Object[] arrayOfObject = new Object[2];
/* 549 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 550 */     arrayOfObject[1] = this.navName;
/*     */     
/* 552 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 560 */     String str = getBundle().getString(paramString2);
/*     */     
/* 562 */     if (paramArrayOfObject != null) {
/* 563 */       MessageFormat messageFormat = new MessageFormat(str);
/* 564 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 567 */     addOutput(paramString1 + " " + str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 612 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 621 */     return "QSMRPQWITHDRAWALFULLABR";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMRPQWITHDRAWALFULLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */