/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class WATTSCCNHWABR extends PokBaseABR {
/*  39 */   private ResourceBundle rsBundle = null;
/*  40 */   private String annDate = "";
/*  41 */   private StringBuffer rptSb = new StringBuffer();
/*  42 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  43 */   private StringBuffer userxmlSb = new StringBuffer();
/*  44 */   private String t2DTS = "&nbsp;";
/*  45 */   private Object[] args = (Object[])new String[10];
/*  46 */   private String pathName = null;
/*  47 */   private Vector finalEntities = new Vector();
/*     */   public static final String RPTPATH = "_rptpath";
/*     */   public static final String STATUS_FINAL = "0020";
/*     */   public static final String MODEL_HARDWARE = "100";
/*  51 */   public static final CharSequence GEO_CCN = "1464";
/*  52 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  53 */   protected static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*  55 */   private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("WATTSCCNHWABR");
/*     */ 
/*     */   
/*  58 */   protected static final Vector AVAILTYPE_FILTER = new Vector(2); static {
/*  59 */     AVAILTYPE_FILTER.add("146");
/*  60 */     AVAILTYPE_FILTER.add("143");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  67 */     String str1 = "";
/*     */     
/*     */     try {
/*  70 */       long l = System.currentTimeMillis();
/*     */       
/*  72 */       start_ABRBuild(false);
/*     */ 
/*     */       
/*  75 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), 
/*  76 */           ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */ 
/*     */       
/*  79 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */       
/*  83 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getVeName());
/*  84 */       this.m_elist = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, 
/*  85 */               getEntityType(), getEntityID()) });
/*     */       
/*  87 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  88 */       addDebug("DEBUG: rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*  89 */       String str = getAttributeValue(entityItem, "ANNNUMBER");
/*  90 */       addDebug("RFA number: " + str);
/*     */       
/*  92 */       str1 = getNavigationName(entityItem);
/*     */       
/*  94 */       if (getReturnCode() == 0) {
/*  95 */         processThis(this, this.m_prof, entityItem);
/*     */         
/*  97 */         boolean bool = generateTXTFile(str, this.finalEntities);
/*  98 */         if (bool)
/*     */         {
/* 100 */           sendMail(this.pathName);
/*     */         }
/*     */       } 
/*     */       
/* 104 */       addDebug("Total Time: " + 
/* 105 */           Stopwatch.format(System.currentTimeMillis() - l));
/*     */     }
/* 107 */     catch (Throwable throwable) {
/* 108 */       StringWriter stringWriter = new StringWriter();
/* 109 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 110 */       String str7 = "<pre>{0}</pre>";
/* 111 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/* 112 */       setReturnCode(-3);
/* 113 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 115 */       this.args[0] = throwable.getMessage();
/* 116 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 117 */       messageFormat1 = new MessageFormat(str7);
/* 118 */       this.args[0] = stringWriter.getBuffer().toString();
/* 119 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 120 */       logError("Exception: " + throwable.getMessage());
/* 121 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/* 123 */       if ("&nbsp;".equals(this.t2DTS)) {
/* 124 */         this.t2DTS = getNow();
/*     */       }
/* 126 */       setDGTitle(str1);
/* 127 */       setDGRptName(getShortClassName(getClass()));
/* 128 */       setDGRptClass(getABRCode());
/*     */       
/* 130 */       if (!isReadOnly()) {
/* 131 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     println(EACustom.getDocTypeHtml());
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
/* 150 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */ 
/*     */ 
/*     */     
/* 154 */     MessageFormat messageFormat = new MessageFormat(str2);
/* 155 */     this.args[0] = getShortClassName(getClass());
/* 156 */     this.args[1] = str1;
/* 157 */     String str3 = messageFormat.format(this.args);
/* 158 */     String str4 = buildAbrHeader();
/*     */ 
/*     */     
/* 161 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("RESULT_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/* 162 */     this.rptSb.insert(0, str5);
/*     */     
/* 164 */     println(this.rptSb.toString());
/* 165 */     printDGSubmitString();
/* 166 */     println(EACustom.getTOUDiv());
/* 167 */     buildReportFooter();
/*     */ 
/*     */     
/* 170 */     this.m_elist.dereference();
/* 171 */     this.m_elist = null;
/* 172 */     this.rsBundle = null;
/* 173 */     this.args = null;
/* 174 */     messageFormat = null;
/* 175 */     this.userxmlSb = null;
/* 176 */     this.rptSb = null;
/* 177 */     this.xmlgenSb = null;
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
/*     */   public void processThis(WATTSCCNHWABR paramWATTSCCNHWABR, Profile paramProfile, EntityItem paramEntityItem) throws TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException, IOException {
/* 196 */     Vector<EntityItem> vector = new Vector();
/* 197 */     Vector<WATTSCCNHWENTITY> vector1 = new Vector();
/* 198 */     Vector<String> vector2 = new Vector();
/* 199 */     Vector<String> vector3 = new Vector();
/* 200 */     Vector<String> vector4 = new Vector();
/*     */     
/*     */     try {
/* 203 */       addDebug("Starting process data from VE");
/*     */       
/* 205 */       EntityList entityList = paramWATTSCCNHWABR.getEntityList(paramProfile, getVeName(), paramEntityItem);
/* 206 */       addDebug("entityList: " + entityList);
/* 207 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 209 */       String str1 = PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN");
/* 210 */       addDebug("RootEntity:" + entityItem.getKey() + " PDHDOMAIN:" + str1);
/*     */       
/* 212 */       String str2 = PokUtils.getAttributeFlagValue(entityItem, "ANNSTATUS");
/* 213 */       if (!"0020".equals(str2)) {
/* 214 */         addDebug("skip " + entityItem.getKey() + " for entity status is not Final");
/*     */       } else {
/*     */         
/* 217 */         this.annDate = getAttributeValue(entityItem, "ANNDATE");
/* 218 */         addDebug("RootEntity:" + entityItem.getKey() + " ANNDATE:" + this.annDate);
/*     */         
/* 220 */         Vector<EntityItem> vector5 = PokUtils.getAllLinkedEntities(entityItem, "ANNAVAILA", "AVAIL");
/* 221 */         addDebug("avail size " + vector5.size());
/* 222 */         for (byte b = 0; b < vector5.size(); b++) {
/* 223 */           EntityItem entityItem1 = vector5.get(b);
/* 224 */           addDebug("avail number" + b + '\001');
/*     */           
/* 226 */           String str = getAttributeFlagValue(entityItem1, "AVAILTYPE");
/* 227 */           if (!AVAILTYPE_FILTER.contains(str)) {
/* 228 */             addDebug("skip " + entityItem1.getKey() + " for AVAILTYPE " + str);
/*     */           }
/*     */           else {
/*     */             
/* 232 */             String str3 = getAttributeFlagValue(entityItem1, "COUNTRYLIST");
/* 233 */             String[] arrayOfString = splitStr(str3, "|");
/* 234 */             Vector<String> vector6 = new Vector();
/* 235 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/* 236 */               vector6.add(arrayOfString[b1]);
/*     */             }
/*     */             
/* 239 */             if (!vector6.contains(GEO_CCN)) {
/* 240 */               addDebug("skip " + entityItem1.getKey() + " for don't have Canada Geo in CountryList");
/*     */             }
/*     */             else {
/*     */               
/* 244 */               String str4 = getAttributeFlagValue(entityItem1, "STATUS");
/* 245 */               if (!"0020".equals(str4)) {
/* 246 */                 addDebug("skip " + entityItem1.getKey() + " for entity status is not Final");
/*     */               }
/*     */               
/* 249 */               Vector<EntityItem> vector7 = PokUtils.getAllLinkedEntities(entityItem1, "MODELAVAIL", "MODEL");
/* 250 */               addDebug("model size " + vector7.size());
/*     */               
/* 252 */               Vector<EntityItem> vector8 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "PRODSTRUCT");
/* 253 */               addDebug("tmf size " + vector8.size());
/*     */               
/* 255 */               Vector<EntityItem> vector9 = PokUtils.getAllLinkedEntities(entityItem1, "MODELCONVERTAVAIL", "MODELCONVERT");
/* 256 */               addDebug("modelconvert size " + vector9.size());
/*     */               
/* 258 */               if (vector7.size() > 0 || vector8.size() > 0 || vector9.size() > 0) {
/* 259 */                 vector.add(entityItem1);
/*     */               } else {
/* 261 */                 addDebug("The " + entityItem1.getKey() + " didn't link model and tmf and modelconvert");
/*     */               } 
/*     */               byte b2;
/* 264 */               for (b2 = 0; b2 < vector7.size(); b2++) {
/* 265 */                 EntityItem entityItem2 = vector7.get(b2);
/*     */                 
/* 267 */                 String str5 = getAttributeFlagValue(entityItem2, "COFCAT");
/* 268 */                 String str6 = getAttributeFlagValue(entityItem2, "STATUS");
/* 269 */                 if ("100".equals(str5) && "0020".equals(str6)) {
/* 270 */                   if (!vector2.contains("" + entityItem2.getEntityID())) {
/* 271 */                     vector2.add("" + entityItem2.getEntityID());
/*     */                     
/* 273 */                     addDebug("HW Model: " + entityItem2.getKey());
/* 274 */                     WATTSCCNHWENTITY wATTSCCNHWENTITY = new WATTSCCNHWENTITY();
/* 275 */                     wATTSCCNHWENTITY.setType("MODEL");
/*     */                     
/* 277 */                     wATTSCCNHWENTITY.setMachtype(getAttributeFlagValue(entityItem2, "MACHTYPEATR"));
/* 278 */                     addDebug("MODEL MACHTYPEATR: " + 
/* 279 */                         getAttributeFlagValue(entityItem2, "MACHTYPEATR"));
/* 280 */                     wATTSCCNHWENTITY.setModel(getAttributeValue(entityItem2, "MODELATR"));
/* 281 */                     addDebug("MODEL MACHTYPEATR: " + 
/* 282 */                         getAttributeValue(entityItem2, "MODELATR"));
/* 283 */                     wATTSCCNHWENTITY.setAnnDate(dateFormat(this.annDate));
/*     */                     
/* 285 */                     vector1.add(wATTSCCNHWENTITY);
/*     */                   } else {
/* 287 */                     addDebug("duplicate " + entityItem2.getKey());
/*     */                   } 
/*     */                 } else {
/* 290 */                   addDebug("skip " + entityItem2.getKey() + " for not HWï¿½ï¿½MODEL or status is not final");
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/* 295 */               for (b2 = 0; b2 < vector8.size(); b2++) {
/* 296 */                 EntityItem entityItem2 = vector8.get(b2);
/*     */                 
/* 298 */                 String str5 = getAttributeFlagValue(entityItem2, "STATUS");
/* 299 */                 if ("0020".equals(str5)) {
/* 300 */                   if (!vector3.contains("" + entityItem2.getEntityID())) {
/* 301 */                     vector3.add("" + entityItem2.getEntityID());
/* 302 */                     addDebug("Prodstruct: " + entityItem2.getKey());
/*     */                     
/* 304 */                     EntityItem entityItem3 = getModelEntityFromTmf(entityItem2);
/* 305 */                     String str6 = getAttributeFlagValue(entityItem3, "STATUS");
/*     */                     
/* 307 */                     EntityItem entityItem4 = getFeatureEntityFromTmf(entityItem2);
/* 308 */                     String str7 = getAttributeFlagValue(entityItem4, "STATUS");
/*     */                     
/* 310 */                     if ("0020".equals(str7) && "0020".equals(str6)) {
/* 311 */                       WATTSCCNHWENTITY wATTSCCNHWENTITY = new WATTSCCNHWENTITY();
/* 312 */                       wATTSCCNHWENTITY.setType("FEATURE");
/*     */                       
/* 314 */                       wATTSCCNHWENTITY.setMachtype(getAttributeFlagValue(entityItem3, "MACHTYPEATR"));
/* 315 */                       addDebug("FEATURE MACHTYPEATR: " + getAttributeFlagValue(entityItem3, "MACHTYPEATR"));
/* 316 */                       wATTSCCNHWENTITY.setFeatureCode(getAttributeValue(entityItem4, "FEATURECODE"));
/* 317 */                       addDebug("FEATURE FEATURECODE: " + getAttributeValue(entityItem4, "FEATURECODE"));
/* 318 */                       wATTSCCNHWENTITY.setAnnDate(dateFormat(this.annDate));
/* 319 */                       vector1.add(wATTSCCNHWENTITY);
/*     */                     } else {
/* 321 */                       addDebug("skip " + entityItem2.getKey() + " for linked MODEL and FEATURE status is not all final");
/*     */                     } 
/*     */                   } else {
/* 324 */                     addDebug("duplicate " + entityItem2.getKey());
/*     */                   } 
/*     */                 } else {
/* 327 */                   addDebug("skip " + entityItem2.getKey() + " for status is not final");
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/* 332 */               for (b2 = 0; b2 < vector9.size(); b2++) {
/* 333 */                 EntityItem entityItem2 = vector9.get(b2);
/*     */                 
/* 335 */                 String str5 = getAttributeFlagValue(entityItem2, "STATUS");
/* 336 */                 if ("0020".equals(str5)) {
/* 337 */                   if (!vector4.contains("" + entityItem2
/* 338 */                       .getEntityID())) {
/* 339 */                     vector4.add("" + entityItem2.getEntityID());
/*     */                     
/* 341 */                     addDebug("Modelconvert: " + entityItem2.getKey());
/* 342 */                     WATTSCCNHWENTITY wATTSCCNHWENTITY = new WATTSCCNHWENTITY();
/* 343 */                     wATTSCCNHWENTITY.setType("MODELCONVERT");
/*     */                     
/* 345 */                     wATTSCCNHWENTITY.setMachtype(getAttributeValue(entityItem2, "FROMMACHTYPE"));
/* 346 */                     addDebug("MODELCONVERT FROMMACHTYPE: " + getAttributeValue(entityItem2, "FROMMACHTYPE"));
/* 347 */                     wATTSCCNHWENTITY.setModel(getAttributeValue(entityItem2, "FROMMODEL"));
/* 348 */                     addDebug("MODELCONVERT FROMMODEL: " + getAttributeValue(entityItem2, "FROMMODEL"));
/* 349 */                     wATTSCCNHWENTITY.setToMachtype(getAttributeValue(entityItem2, "TOMACHTYPE"));
/* 350 */                     addDebug("MODELCONVERT TOMACHTYPE: " + getAttributeValue(entityItem2, "TOMACHTYPE"));
/* 351 */                     wATTSCCNHWENTITY.setToModel(getAttributeValue(entityItem2, "TOMODEL"));
/* 352 */                     addDebug("MODELCONVERT TOMODEL: " + getAttributeValue(entityItem2, "TOMODEL"));
/* 353 */                     wATTSCCNHWENTITY.setAnnDate(dateFormat(this.annDate));
/*     */                     
/* 355 */                     vector1.add(wATTSCCNHWENTITY);
/*     */                   } else {
/* 357 */                     addDebug("duplicate " + entityItem2.getKey());
/*     */                   } 
/*     */                 } else {
/* 360 */                   addDebug("skip " + entityItem2.getKey() + " for status is not final");
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 367 */         long l = System.currentTimeMillis();
/* 368 */         this.finalEntities = getPriceInfo(vector1);
/* 369 */         addDebug("get price info Time: " + 
/* 370 */             Stopwatch.format(System.currentTimeMillis() - l));
/*     */       } 
/*     */     } finally {
/*     */       
/* 374 */       vector.clear();
/* 375 */       vector = null;
/* 376 */       vector1.clear();
/* 377 */       vector1 = null;
/* 378 */       vector2.clear();
/* 379 */       vector2 = null;
/* 380 */       vector3.clear();
/* 381 */       vector3 = null;
/* 382 */       vector4.clear();
/* 383 */       vector4 = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] splitStr(String paramString1, String paramString2) {
/* 389 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, paramString2);
/* 390 */     String[] arrayOfString = new String[stringTokenizer.countTokens()];
/* 391 */     byte b = 0;
/* 392 */     while (stringTokenizer.hasMoreTokens()) {
/* 393 */       arrayOfString[b] = stringTokenizer.nextToken();
/* 394 */       b++;
/*     */     } 
/* 396 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String setFileName(String paramString) {
/* 405 */     StringBuffer stringBuffer = new StringBuffer(paramString.trim());
/* 406 */     String str1 = getNow();
/*     */     
/* 408 */     str1 = str1.replace(' ', '_');
/* 409 */     stringBuffer.append(str1 + ".txt");
/* 410 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_rptpath", "/Dgq");
/* 411 */     if (!str2.endsWith("/")) {
/* 412 */       str2 = str2 + "/";
/*     */     }
/* 414 */     this.pathName = str2 + stringBuffer.toString();
/*     */     
/* 416 */     addDebug("**** ffPathName: " + this.pathName + " ffFileName: " + stringBuffer.toString());
/* 417 */     return this.pathName;
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
/*     */   private boolean generateTXTFile(String paramString, Vector<WATTSCCNHWENTITY> paramVector) throws IOException {
/*     */     try {
/* 430 */       setFileName(paramString);
/* 431 */       FileOutputStream fileOutputStream = new FileOutputStream(this.pathName);
/* 432 */       OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/* 433 */       StringBuffer stringBuffer = new StringBuffer();
/* 434 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 435 */         WATTSCCNHWENTITY wATTSCCNHWENTITY = paramVector.elementAt(b);
/* 436 */         String str1 = doubleFormat(wATTSCCNHWENTITY.getPriceValue());
/* 437 */         String str2 = wATTSCCNHWENTITY.getPriceType();
/* 438 */         if (str2 == null || str2.length() <= 0) {
/* 439 */           addDebug("filter unhandled pricetype");
/*     */         } else {
/* 441 */           if ("MODEL".equals(wATTSCCNHWENTITY.getType())) {
/* 442 */             stringBuffer.append(getValue(wATTSCCNHWENTITY.getMachtype(), 4));
/* 443 */             stringBuffer.append(" ");
/* 444 */             stringBuffer.append(getValue(wATTSCCNHWENTITY.getModel(), 3));
/*     */             
/* 446 */             stringBuffer.append("            ");
/*     */           }
/* 448 */           else if ("FEATURE".equals(wATTSCCNHWENTITY.getType())) {
/* 449 */             stringBuffer.append(getValue(wATTSCCNHWENTITY.getMachtype(), 4));
/* 450 */             stringBuffer.append(" ");
/* 451 */             stringBuffer.append(getValue(wATTSCCNHWENTITY.getFeatureCode(), 4));
/*     */             
/* 453 */             stringBuffer.append("           ");
/*     */           }
/* 455 */           else if ("MODELCONVERT".equals(wATTSCCNHWENTITY.getType())) {
/* 456 */             stringBuffer.append(getValue(wATTSCCNHWENTITY.getMachtype(), 4));
/* 457 */             stringBuffer.append(" ");
/* 458 */             stringBuffer.append(getValue(wATTSCCNHWENTITY.getModel(), 3));
/* 459 */             stringBuffer.append(" ");
/* 460 */             stringBuffer.append(getValue(wATTSCCNHWENTITY.getToMachtype(), 4));
/* 461 */             stringBuffer.append(" ");
/* 462 */             stringBuffer.append(getValue(wATTSCCNHWENTITY.getToModel(), 3));
/* 463 */             stringBuffer.append("   ");
/*     */           } else {
/*     */             
/* 466 */             addDebug("Entity type not processed");
/*     */           } 
/*     */           
/* 469 */           stringBuffer.append(getValue(wATTSCCNHWENTITY.getPriceType(), 3));
/* 470 */           stringBuffer.append("   ");
/*     */ 
/*     */           
/* 473 */           stringBuffer.append(getValue(str1, 12));
/* 474 */           stringBuffer.append("   ");
/* 475 */           stringBuffer.append(wATTSCCNHWENTITY.getAnnDate());
/* 476 */           stringBuffer.append("\r\n");
/*     */         } 
/*     */       } 
/* 479 */       outputStreamWriter.write(stringBuffer.toString());
/* 480 */       outputStreamWriter.flush();
/* 481 */       outputStreamWriter.close();
/* 482 */       this.userxmlSb.append("File generate success");
/* 483 */       return true;
/*     */     }
/* 485 */     catch (WATTSException wATTSException) {
/* 486 */       this.userxmlSb.append(wATTSException);
/* 487 */       return false;
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
/*     */   public String getValue(String paramString, int paramInt) throws WATTSException {
/* 500 */     StringBuffer stringBuffer = new StringBuffer();
/* 501 */     if (paramString.length() == paramInt)
/* 502 */       return paramString; 
/* 503 */     if (paramString.length() > paramInt) {
/* 504 */       throw new WATTSException(paramString + " length too long");
/*     */     }
/* 506 */     for (byte b = 0; b < paramInt - paramString.length(); b++) {
/* 507 */       stringBuffer.append(" ");
/*     */     }
/* 509 */     stringBuffer.append(paramString);
/* 510 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String dateFormat(String paramString) {
/* 521 */     SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
/* 522 */     SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
/* 523 */     String str = null;
/*     */     try {
/* 525 */       str = simpleDateFormat1.format(simpleDateFormat2.parse(paramString));
/* 526 */     } catch (ParseException parseException) {
/* 527 */       addDebug("date parse Exception: " + parseException);
/* 528 */       parseException.printStackTrace();
/*     */     } 
/* 530 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String doubleFormat(String paramString) {
/* 541 */     BigDecimal bigDecimal = new BigDecimal(paramString);
/* 542 */     bigDecimal = bigDecimal.setScale(2, 4);
/* 543 */     addDebug(paramString + " kep 2 decimal " + bigDecimal.toString());
/* 544 */     return bigDecimal.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMail(String paramString) throws Exception {
/* 555 */     FileInputStream fileInputStream = null;
/*     */     try {
/* 557 */       fileInputStream = new FileInputStream(paramString);
/* 558 */       int i = fileInputStream.available();
/* 559 */       byte[] arrayOfByte = new byte[i];
/* 560 */       fileInputStream.read(arrayOfByte);
/* 561 */       setAttachByteForDG(arrayOfByte);
/* 562 */       getABRItem().setFileExtension("txt");
/* 563 */       addDebug("Sending mail for file " + paramString);
/* 564 */     } catch (IOException iOException) {
/* 565 */       addDebug("sendMail IO Exception");
/*     */     }
/*     */     finally {
/*     */       
/* 569 */       if (fileInputStream != null) {
/* 570 */         fileInputStream.close();
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
/*     */   private EntityItem getModelEntityFromTmf(EntityItem paramEntityItem) {
/* 583 */     EntityItem entityItem = null;
/* 584 */     Vector<EntityItem> vector = paramEntityItem.getDownLink();
/* 585 */     if (vector != null && vector.size() > 0) {
/* 586 */       for (byte b = 0; b < vector.size(); b++) {
/* 587 */         EntityItem entityItem1 = vector.get(b);
/* 588 */         if ("MODEL".equals(entityItem1.getEntityType())) {
/* 589 */           entityItem = entityItem1;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 594 */     return entityItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityItem getFeatureEntityFromTmf(EntityItem paramEntityItem) {
/* 604 */     EntityItem entityItem = null;
/* 605 */     Vector<EntityItem> vector = paramEntityItem.getUpLink();
/* 606 */     if (vector != null && vector.size() > 0) {
/* 607 */       for (byte b = 0; b < vector.size(); b++) {
/* 608 */         EntityItem entityItem1 = vector.get(b);
/* 609 */         if ("FEATURE".equals(entityItem1.getEntityType())) {
/* 610 */           entityItem = entityItem1;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 615 */     return entityItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getAttributeFlagValue(EntityItem paramEntityItem, String paramString) {
/* 625 */     return PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 634 */     return "CCNWATTSHWANN";
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
/*     */   private Vector getPriceInfo(Vector<WATTSCCNHWENTITY> paramVector) throws SQLException, MiddlewareException {
/* 648 */     Vector<WATTSCCNHWENTITY> vector = new Vector();
/* 649 */     ResultSet resultSet = null;
/* 650 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 652 */       Connection connection = this.m_db.getODSConnection();
/* 653 */       byte b = 0; while (true) { if (b < paramVector.size())
/*     */         
/* 655 */         { WATTSCCNHWENTITY wATTSCCNHWENTITY = paramVector.elementAt(b);
/* 656 */           StringBuffer stringBuffer = new StringBuffer();
/* 657 */           if ("MODEL".equals(wATTSCCNHWENTITY.getType())) {
/* 658 */             addDebug("Get MODEL price info---------------");
/* 659 */             stringBuffer.append("select  price_value_usd, price_type from price.price where action='I' and currency ='USD' and offering_type='MODEL' and price_point_type='MOD' and machtypeatr='");
/* 660 */             stringBuffer.append(wATTSCCNHWENTITY.getMachtype());
/* 661 */             stringBuffer.append("' and modelatr='");
/* 662 */             stringBuffer.append(wATTSCCNHWENTITY.getModel());
/* 663 */             stringBuffer.append("' and start_date <= '");
/* 664 */             stringBuffer.append(this.annDate);
/* 665 */             stringBuffer.append("' and end_date >'");
/* 666 */             stringBuffer.append(this.annDate);
/* 667 */             stringBuffer.append("'");
/*     */             
/* 669 */             addDebug("SQL:" + stringBuffer);
/* 670 */             addDebug("MODEL info: machtypeatr:" + wATTSCCNHWENTITY.getMachtype() + ",modelatr:" + wATTSCCNHWENTITY.getModel());
/* 671 */           } else if ("FEATURE".equals(wATTSCCNHWENTITY.getType())) {
/* 672 */             addDebug("Get FEATURE price info---------------");
/* 673 */             stringBuffer.append("select  price_value_usd, price_type from price.price where action='I' and currency ='USD' and offering_type='FEATURE' and machtypeatr='");
/* 674 */             stringBuffer.append(wATTSCCNHWENTITY.getMachtype());
/* 675 */             stringBuffer.append("' and featurecode='");
/* 676 */             stringBuffer.append(wATTSCCNHWENTITY.getFeatureCode());
/* 677 */             stringBuffer.append("' and start_date <= '");
/* 678 */             stringBuffer.append(this.annDate);
/* 679 */             stringBuffer.append("' and end_date >'");
/* 680 */             stringBuffer.append(this.annDate);
/* 681 */             stringBuffer.append("'");
/*     */             
/* 683 */             addDebug("SQL:" + stringBuffer);
/* 684 */             addDebug("FEATURE info: machtypeatr:" + wATTSCCNHWENTITY.getMachtype() + ",featurecode:" + wATTSCCNHWENTITY.getFeatureCode());
/* 685 */           } else if ("MODELCONVERT".equals(wATTSCCNHWENTITY.getType())) {
/* 686 */             addDebug("Get MODELCONVERT price info---------------");
/* 687 */             stringBuffer.append("select  price_value_usd, price_type from price.price where action='I' and currency ='USD' and offering_type='MODELCONVERT' and machtypeatr='");
/* 688 */             stringBuffer.append(wATTSCCNHWENTITY.getToMachtype());
/* 689 */             stringBuffer.append("' and modelatr='");
/* 690 */             stringBuffer.append(wATTSCCNHWENTITY.getToModel());
/* 691 */             stringBuffer.append("' and from_machtypeatr='");
/* 692 */             stringBuffer.append(wATTSCCNHWENTITY.getMachtype());
/* 693 */             stringBuffer.append("' and from_modelatr='");
/* 694 */             stringBuffer.append(wATTSCCNHWENTITY.getModel());
/* 695 */             stringBuffer.append("' and start_date <= '");
/* 696 */             stringBuffer.append(this.annDate);
/* 697 */             stringBuffer.append("' and end_date >'");
/* 698 */             stringBuffer.append(this.annDate);
/* 699 */             stringBuffer.append("'");
/*     */             
/* 701 */             addDebug("SQL:" + stringBuffer);
/* 702 */             addDebug("MODELCONVERT info: from_machtypeatr:" + wATTSCCNHWENTITY.getMachtype() + ",from_model:" + wATTSCCNHWENTITY
/* 703 */                 .getModel() + ",to_machtypeatr:" + wATTSCCNHWENTITY.getToMachtype() + ",to_modelatr:" + wATTSCCNHWENTITY
/* 704 */                 .getToModel());
/*     */           } else {
/* 706 */             addDebug("can not handle entitytype " + wATTSCCNHWENTITY.getType());
/*     */             
/*     */             b++;
/*     */           } 
/* 710 */           preparedStatement = connection.prepareStatement(stringBuffer.toString());
/* 711 */           resultSet = preparedStatement.executeQuery();
/* 712 */           String str1 = null;
/* 713 */           String str2 = null;
/* 714 */           if (resultSet.next()) {
/* 715 */             str1 = resultSet.getString(1);
/* 716 */             str2 = convertPriceType(resultSet.getString(2));
/*     */           } else {
/* 718 */             str1 = "0.00";
/* 719 */             str2 = "  5";
/*     */           } 
/* 721 */           addDebug("price info: price_value " + str1 + " price_type " + str2);
/*     */           
/* 723 */           wATTSCCNHWENTITY.setPriceValue(str1);
/* 724 */           wATTSCCNHWENTITY.setPriceType(str2);
/* 725 */           vector.add(wATTSCCNHWENTITY);
/*     */           
/* 727 */           stringBuffer = null; }
/*     */         else { break; }
/*     */          b++; }
/* 730 */        return vector;
/*     */     } finally {
/*     */       
/* 733 */       if (preparedStatement != null) {
/*     */         try {
/* 735 */           preparedStatement.close();
/* 736 */         } catch (SQLException sQLException) {
/* 737 */           sQLException.printStackTrace();
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
/*     */   private String convertPriceType(String paramString) {
/* 750 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 751 */     hashMap.put("PUR", "  5");
/* 752 */     hashMap.put("MES", "  5");
/* 753 */     hashMap.put("WU3", "NRR");
/* 754 */     hashMap.put("WU4", "OR5");
/* 755 */     hashMap.put("WU1", "OR7");
/* 756 */     hashMap.put("EMC", "0BC");
/* 757 */     hashMap.put("EMG", "0HC");
/* 758 */     hashMap.put("EMF", "0MC");
/* 759 */     hashMap.put("EMD", "NOR");
/* 760 */     hashMap.put("EMH", "0RD");
/* 761 */     hashMap.put("EMS", "P01");
/* 762 */     hashMap.put("EMM", "P02");
/*     */     
/* 764 */     if (hashMap.containsKey(paramString)) {
/* 765 */       addDebug("convert WWPRT price type " + paramString + " to WTAAS price type " + hashMap.get(paramString));
/* 766 */       return (String)hashMap.get(paramString);
/*     */     } 
/* 768 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 773 */     return "WATTSSTATUSABR";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 777 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 784 */     addOutput(paramString);
/* 785 */     setReturnCode(-1);
/*     */   }
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 789 */     if (3 <= DEBUG_LVL) {
/* 790 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/*     */   protected void addDebugComment(int paramInt, String paramString) {
/* 802 */     if (paramInt <= DEBUG_LVL) {
/* 803 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/*     */   protected EntityList getEntityList(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 820 */     return this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, paramString), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*     */             
/* 822 */             .getEntityType(), paramEntityItem.getEntityID()) });
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
/*     */   protected EntityItem getEntityItem(Profile paramProfile, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 840 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*     */             
/* 842 */             .getEntityType(), paramEntityItem.getEntityID()) });
/* 843 */     return entityList.getParentEntityGroup().getEntityItem(0);
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
/*     */   protected EntityItem getEntityItem(Profile paramProfile, String paramString, int paramInt) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 861 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramString, paramInt) });
/*     */ 
/*     */     
/* 864 */     return entityList.getParentEntityGroup().getEntityItem(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Database getDB() {
/* 871 */     return this.m_db;
/*     */   }
/*     */   
/*     */   protected String getABRTime() {
/* 875 */     return String.valueOf(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOutput(String paramString) {
/* 882 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildAbrHeader() {
/* 892 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 899 */     MessageFormat messageFormat = new MessageFormat(str);
/* 900 */     this.args[0] = this.m_prof.getOPName();
/* 901 */     this.args[1] = this.m_prof.getRoleDescription();
/* 902 */     this.args[2] = this.m_prof.getWGName();
/* 903 */     this.args[3] = this.t2DTS;
/* 904 */     this.args[4] = "WATTS CCN HW ABR feed trigger<br/>" + this.xmlgenSb.toString();
/* 905 */     this.args[5] = getABRVersion();
/* 906 */     return messageFormat.format(this.args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 916 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 919 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 920 */     EANList eANList = entityGroup.getMetaAttribute();
/*     */     
/* 922 */     for (byte b = 0; b < eANList.size(); b++) {
/* 923 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 924 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute
/* 925 */             .getAttributeCode(), ", ", "", false));
/* 926 */       stringBuffer.append(" ");
/*     */     } 
/* 928 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WATTSCCNHWABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */