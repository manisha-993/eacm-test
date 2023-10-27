/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class PIABRSTATUS
/*     */   extends PokBaseABR {
/*  29 */   private StringBuffer rptSb = new StringBuffer();
/*  30 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  31 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  32 */   private int abr_debuglvl = 0;
/*  33 */   private String navName = "";
/*  34 */   private String fileprefix = null;
/*  35 */   private String ffFileName = null;
/*  36 */   private String ffPathName = null;
/*  37 */   private Hashtable metaTbl = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   String geoStr = null;
/*     */   
/*     */   boolean hasData = false;
/*     */   
/*  48 */   private static final Hashtable COLUMN_LENGTH = new Hashtable<>(); public static final String RPTPATH = "_rptpath"; public static final String GEOS = "_geos"; static {
/*  49 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/*  50 */     COLUMN_LENGTH.put("MKTGNAME", "254");
/*     */   }
/*     */   public static final String ISLMPRN = "ISLMPRN"; public static final String MKTGNAME = "MKTGNAME"; public static final String BLANKCHARACTER = ",";
/*     */   
/*     */   public String getDescription() {
/*  55 */     return "PIABRSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/*  61 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  67 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  69 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     String str3 = "";
/*  76 */     boolean bool1 = true;
/*  77 */     boolean bool2 = true;
/*     */ 
/*     */     
/*  80 */     String str4 = "";
/*  81 */     String str5 = "";
/*     */     
/*  83 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  86 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  87 */       arrayOfString[0] = getShortClassName(getClass());
/*  88 */       arrayOfString[1] = "ABR";
/*  89 */       str3 = messageFormat.format(arrayOfString);
/*  90 */       setDGTitle("PIABRSTATUS report");
/*  91 */       setDGString(getABRReturnCode());
/*  92 */       setDGRptName("PIABRSTATUS");
/*  93 */       setDGRptClass("PIABRSTATUS");
/*     */       
/*  95 */       setReturnCode(0);
/*     */       
/*  97 */       start_ABRBuild(false);
/*     */       
/*  99 */       this
/* 100 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/* 104 */       this.m_elist = getEntityList(getVEName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 112 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/* 114 */       this.navName = getNavigationName();
/* 115 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/* 116 */       addDebug("navName=" + this.navName);
/* 117 */       addDebug("rootDesc" + str5);
/*     */ 
/*     */       
/* 120 */       generateFlatFile(entityItem);
/* 121 */       sendMail(this.ffPathName);
/*     */ 
/*     */     
/*     */     }
/* 125 */     catch (Exception exception) {
/*     */       
/* 127 */       exception.printStackTrace();
/*     */       
/* 129 */       setReturnCode(-1);
/* 130 */       StringWriter stringWriter = new StringWriter();
/* 131 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 132 */       String str7 = "<pre>{0}</pre>";
/* 133 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 134 */       setReturnCode(-3);
/* 135 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 137 */       arrayOfString[0] = exception.getMessage();
/* 138 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 139 */       messageFormat = new MessageFormat(str7);
/* 140 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 141 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 142 */       logError("Exception: " + exception.getMessage());
/* 143 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 145 */       setCreateDGEntity(true);
/* 146 */       bool1 = false;
/*     */     } finally {
/*     */       
/* 149 */       StringBuffer stringBuffer = new StringBuffer();
/* 150 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 151 */       arrayOfString[0] = this.m_prof.getOPName();
/* 152 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 153 */       arrayOfString[2] = this.m_prof.getWGName();
/* 154 */       arrayOfString[3] = getNow();
/* 155 */       stringBuffer.append(bool1 ? "generated the Pipackage report file successful " : "generated the Pipackage report file faild");
/*     */       
/* 157 */       stringBuffer.append(",");
/* 158 */       if (!this.hasData) {
/* 159 */         stringBuffer.append("No EMEA Data!");
/*     */       } else {
/* 161 */         stringBuffer.append("Generated the Pipackage report file successful ");
/* 162 */       }  arrayOfString[4] = stringBuffer.toString();
/* 163 */       arrayOfString[5] = str4 + " " + getABRVersion();
/*     */       
/* 165 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 167 */       println(EACustom.getDocTypeHtml());
/* 168 */       println(this.rptSb.toString());
/* 169 */       printDGSubmitString();
/*     */       
/* 171 */       println(EACustom.getTOUDiv());
/* 172 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFileName() {
/* 183 */     this.fileprefix = ABRServerProperties.getFilePrefix(this.m_abri.getABRCode());
/*     */ 
/*     */     
/* 186 */     StringBuffer stringBuffer = new StringBuffer(this.fileprefix.trim());
/* 187 */     String str1 = getNow();
/*     */     
/* 189 */     str1 = str1.replace(' ', '_');
/* 190 */     stringBuffer.append(str1 + ".csv");
/* 191 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_rptpath", "/Dgq");
/* 192 */     if (!str2.endsWith("/")) {
/* 193 */       str2 = str2 + "/";
/*     */     }
/* 195 */     this.ffFileName = stringBuffer.toString();
/* 196 */     this.ffPathName = str2 + this.ffFileName;
/* 197 */     addDebug("**** mmiotto ffPathName: " + this.ffPathName + " ffFileName: " + this.ffFileName);
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException {
/* 202 */     setFileName();
/* 203 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*     */ 
/*     */ 
/*     */     
/* 207 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/* 208 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 209 */     String str1 = "";
/* 210 */     String str2 = "";
/* 211 */     String str3 = "";
/* 212 */     String str4 = "";
/* 213 */     String str5 = "";
/* 214 */     String str6 = "";
/* 215 */     String str7 = "";
/* 216 */     String str8 = null;
/* 217 */     String str9 = "Product Categories:" + NEWLINE;
/* 218 */     String str10 = "General Availability Date:" + NEWLINE;
/* 219 */     String str11 = "RFA Number:" + PokUtils.getAttributeValue(paramEntityItem, "ANNNUMBER", ",", "", false) + NEWLINE;
/* 220 */     str11 = str11 + "Annoucement Date:" + NEWLINE;
/* 221 */     str9 = str9 + "\"" + PokUtils.getAttributeValue(paramEntityItem, "PRODCATEGORY", ",", "", false) + "\"" + NEWLINE;
/* 222 */     str11 = str11 + PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", ",", "", false) + "\n";
/* 223 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 224 */     String str12 = "PiPackage Notes:\nPRODUCTS/MATERIALS/UPGRADES:\nMaterial/Product,Description,CPU-Indicator (for Models only)\n";
/* 225 */     StringBuffer stringBuffer2 = new StringBuffer("Models:,MKTGNAME\n");
/* 226 */     StringBuffer stringBuffer3 = new StringBuffer("Features:,MKTGNAME\n");
/* 227 */     StringBuffer stringBuffer4 = new StringBuffer("Type Model Upgrades:\n");
/* 228 */     addDebug("*****mlm availGrp.getEntityItemCount() = " + entityGroup.getEntityItemCount());
/* 229 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 230 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 231 */       String str = PokUtils.getAttributeValue(entityItem, "GENAREASELECTION", "", "");
/* 232 */       addDebug("*****mlm strAvailGenAreaSel:" + str);
/* 233 */       if (fliter(str)) {
/* 234 */         if (str8 == null) {
/* 235 */           str8 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false);
/*     */         } else {
/* 237 */           str8 = getEarliestDate(str8, 
/* 238 */               PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false));
/*     */         } 
/* 240 */         this.hasData = true;
/* 241 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/* 242 */         addDebug("*****mlm modelVect = " + vector1);
/* 243 */         addDebug("*****mlm modelVect Size = " + vector1.size());
/* 244 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 251 */           EntityItem entityItem1 = vector1.elementAt(b1);
/* 252 */           String str13 = PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", "", "");
/* 253 */           String str14 = PokUtils.getAttributeValue(entityItem1, "MODELATR", "", "");
/*     */           
/* 255 */           stringBuffer2.append(getValue("ISLMPRN", str13 + str14));
/* 256 */           stringBuffer2.append(",");
/* 257 */           String str15 = getValue("MKTGNAME", PokUtils.getAttributeValue(entityItem1, "MKTGNAME", "", ""));
/* 258 */           stringBuffer2.append(str15);
/*     */           
/* 260 */           str3 = PokUtils.getAttributeValue(entityItem1, "SYSIDUNIT", "", "");
/* 261 */           if (str3 != null && str3.equals("SIU-CPU")) {
/* 262 */             stringBuffer2.append(",");
/* 263 */             stringBuffer2.append("Y");
/*     */           } 
/* 265 */           stringBuffer2.append(NEWLINE);
/* 266 */           addDebug("*****mlm eimodel = " + entityItem1);
/* 267 */           addDebug("*****mlm eimodel.entityid= " + entityItem1.getEntityID());
/* 268 */           addDebug("*****mlm eimodel.entitytype = " + entityItem1.getEntityType());
/* 269 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem1, "PRODSTRUCT", "FEATURE");
/* 270 */           addDebug("*****mlm featureVect = " + vector);
/* 271 */           addDebug("******mlm featureVect.size() = " + vector.size());
/* 272 */           String str16 = null;
/* 273 */           for (byte b3 = 0; b3 < vector.size(); b3++) {
/* 274 */             EntityItem entityItem2 = vector.get(b3);
/*     */             
/* 276 */             str16 = getValue("ISLMPRN", 
/* 277 */                 PokUtils.getAttributeValue(entityItem2, "FEATURECODE", "", "", false));
/* 278 */             String str17 = getValue("MKTGNAME", 
/* 279 */                 PokUtils.getAttributeValue(entityItem2, "MKTGNAME", "", ""));
/* 280 */             String str18 = getValue("ISLMPRN", 
/* 281 */                 PokUtils.getAttributeValue(entityItem2, "FCTYPE", "", "", false));
/* 282 */             if (str16 != null && !str16.trim().equals("") && !str16.startsWith("8P") && str17 != null && 
/* 283 */               !str17.trim().equals("") && str18 != null && 
/* 284 */               !str18.trim().equals("Secondary FC") && !str18.trim().equals("Secondary FC") && !str18.trim().equals("RPQ-ILISTED") && !str18.trim().equals("RPQ-PLISTED") && !str18.trim().equals("RPQ-RLISTED")) {
/*     */ 
/*     */               
/* 287 */               stringBuffer3.append(str13 + str16);
/* 288 */               stringBuffer3.append(",");
/* 289 */               stringBuffer3.append(str17);
/* 290 */               stringBuffer3.append(NEWLINE);
/* 291 */               addDebug("*****mlm eiFeature = " + entityItem2);
/* 292 */               addDebug("*****mlm eiFeature.entityid= " + entityItem2.getEntityID());
/* 293 */               addDebug("*****mlm eiFeature.entitytype = " + entityItem2.getEntityType());
/*     */             } 
/*     */           } 
/*     */         } 
/* 297 */         Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem, "MODELCONVERTAVAIL", "MODELCONVERT");
/* 298 */         addDebug("*****mlm modelconvertvect = " + vector2);
/* 299 */         addDebug("******mlm modelconvertvect.size() = " + vector2.size());
/* 300 */         for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 301 */           EntityItem entityItem1 = vector2.elementAt(b2);
/* 302 */           addDebug("*****mlm modelconvert.entityid= " + entityItem1.getEntityID());
/* 303 */           addDebug("*****mlm modelconvert.entitytype = " + entityItem1.getEntityType());
/*     */           
/* 305 */           str5 = "";
/* 306 */           str6 = "";
/* 307 */           str7 = "";
/* 308 */           str5 = PokUtils.getAttributeValue(entityItem1, "FROMMACHTYPE", "", "", false);
/* 309 */           str5 = str5 + PokUtils.getAttributeValue(entityItem1, "FROMMODEL", "", "", false);
/* 310 */           stringBuffer4.append(getValue("ISLMPRN", str5));
/* 311 */           stringBuffer4.append(",");
/* 312 */           str5 = PokUtils.getAttributeValue(entityItem1, "TOMACHTYPE", "", "", false);
/* 313 */           str5 = str5 + PokUtils.getAttributeValue(entityItem1, "TOMODEL", "", "", false);
/* 314 */           stringBuffer4.append(getValue("ISLMPRN", str5));
/* 315 */           stringBuffer4.append(NEWLINE);
/*     */         } 
/* 317 */         addDebug("*****SB***:" + stringBuffer1.toString());
/*     */       } 
/*     */     } 
/* 320 */     str10 = str10 + str8 + NEWLINE;
/* 321 */     stringBuffer1.append(str11);
/* 322 */     stringBuffer1.append(str10);
/* 323 */     stringBuffer1.append(str9);
/* 324 */     stringBuffer1.append(str12);
/* 325 */     stringBuffer1.append(stringBuffer2);
/* 326 */     stringBuffer1.append(stringBuffer3);
/* 327 */     stringBuffer1.append(stringBuffer4);
/* 328 */     outputStreamWriter.write(stringBuffer1.toString());
/* 329 */     outputStreamWriter.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getVEName() {
/* 334 */     return "EXTPIVE";
/*     */   }
/*     */   
/*     */   public boolean fliter(String paramString) {
/* 338 */     addDebug("geos:" + paramString);
/* 339 */     if (this.geoStr == null) {
/* 340 */       this.geoStr = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_geos", "ALL");
/* 341 */       addDebug("geoStr:" + this.geoStr);
/*     */     } 
/* 343 */     if (paramString.equals("ALL")) {
/* 344 */       return true;
/*     */     }
/* 346 */     return (this.geoStr.indexOf(paramString) > -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEarliestDate(String paramString1, String paramString2) {
/* 351 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*     */     try {
/* 353 */       Date date1 = simpleDateFormat.parse(paramString1);
/* 354 */       Date date2 = simpleDateFormat.parse(paramString2);
/* 355 */       if (date1.getTime() > date2.getTime()) {
/* 356 */         return paramString2;
/*     */       }
/*     */       
/* 359 */       return paramString1;
/*     */     }
/* 361 */     catch (Exception exception) {
/* 362 */       exception.printStackTrace();
/*     */       
/* 364 */       return paramString1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 373 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 382 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 385 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 386 */     if (eANList == null) {
/* 387 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 388 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 390 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 392 */     for (byte b = 0; b < eANList.size(); b++) {
/* 393 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 394 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 395 */       if (b + 1 < eANList.size()) {
/* 396 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 399 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 407 */     addDebug("*****mlmVE name is " + this.m_abri.getVEName());
/* 408 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*     */     
/* 410 */     addDebug("*****mlmCreating Entity List");
/* 411 */     addDebug("*****mlmProfile is " + this.m_prof);
/* 412 */     addDebug("*****mlmRole is " + this.m_prof.getRoleCode() + " : " + this.m_prof.getRoleDescription());
/* 413 */     addDebug("*****mlmExtract action Item is" + extractActionItem);
/* 414 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, 
/* 415 */             getEntityType(), getEntityID()) });
/*     */ 
/*     */     
/* 418 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 419 */         PokUtils.outputList(entityList));
/*     */     
/* 421 */     return entityList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 428 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */   
/*     */   protected String getValue(String paramString1, String paramString2) {
/* 432 */     if (paramString2 == null)
/* 433 */       paramString2 = ""; 
/* 434 */     if (paramString2.indexOf(",") > -1) {
/* 435 */       paramString2 = "\"" + paramString2 + "\"";
/*     */     }
/* 437 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 438 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1).toString());
/* 439 */     if (i == j)
/* 440 */       return paramString2; 
/* 441 */     if (i > j) {
/* 442 */       return paramString2.substring(0, j);
/*     */     }
/* 444 */     return paramString2;
/*     */   }
/*     */   
/*     */   protected String getBlank(int paramInt) {
/* 448 */     StringBuffer stringBuffer = new StringBuffer();
/* 449 */     while (paramInt > 0) {
/* 450 */       stringBuffer.append(" ");
/* 451 */       paramInt--;
/*     */     } 
/* 453 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMail(String paramString) throws Exception {
/* 461 */     FileInputStream fileInputStream = null;
/*     */     try {
/* 463 */       fileInputStream = new FileInputStream(paramString);
/* 464 */       int i = fileInputStream.available();
/* 465 */       byte[] arrayOfByte = new byte[i];
/* 466 */       fileInputStream.read(arrayOfByte);
/* 467 */       setAttachByteForDG(arrayOfByte);
/* 468 */       getABRItem().setFileExtension("csv");
/* 469 */       addDebug("Sending mail for file " + paramString);
/* 470 */     } catch (IOException iOException) {
/*     */       
/* 472 */       addDebug("sendMail IO Exception");
/*     */     }
/*     */     finally {
/*     */       
/* 476 */       if (fileInputStream != null)
/* 477 */         fileInputStream.close(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\PIABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */