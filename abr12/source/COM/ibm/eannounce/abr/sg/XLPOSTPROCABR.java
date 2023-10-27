/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*     */ import COM.ibm.opicmpdh.translation.PackageID;
/*     */ import COM.ibm.opicmpdh.translation.Translation;
/*     */ import COM.ibm.opicmpdh.translation.TranslationEntity;
/*     */ import COM.ibm.opicmpdh.translation.TranslationPackage;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class XLPOSTPROCABR
/*     */   extends PokBaseABR
/*     */ {
/*  52 */   private static final Map ENTITY_STATUS_ATTRIBUTE = new HashMap<>();
/*     */   
/*  54 */   private static final Map ENTITY_WITHDRAW_ATTRIBUTE = new HashMap<>();
/*     */ 
/*     */   
/*     */   private static final String STATUS_FINAL = "Final";
/*     */   
/*  59 */   private static final String ADSFEED_VALUE = ABRServerProperties.getABRQueuedValue("XLPOSTPROCABR");
/*     */   
/*  61 */   private static final DateFormat WITHDRAW_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
/*     */ 
/*     */   
/*  64 */   private static final DateFormat DB_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
/*     */ 
/*     */   
/*  67 */   private static final int[] NLSIDS = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
/*     */   
/*  69 */   private static final Map report = new HashMap<>();
/*     */   
/*  71 */   private static final Map errorReport = new HashMap<>();
/*     */   private Date nowAsDate;
/*     */   private int nlsidFound;
/*     */   
/*     */   static {
/*  76 */     ENTITY_STATUS_ATTRIBUTE.put("CATNAV", "STATUS");
/*  77 */     ENTITY_STATUS_ATTRIBUTE.put("FB", "FBSTATUS");
/*  78 */     ENTITY_STATUS_ATTRIBUTE.put("FEATURE", "STATUS");
/*  79 */     ENTITY_STATUS_ATTRIBUTE.put("IPSCFEAT", "STATUS");
/*  80 */     ENTITY_STATUS_ATTRIBUTE.put("LSEO", "STATUS");
/*  81 */     ENTITY_STATUS_ATTRIBUTE.put("LSEOBUNDLE", "STATUS");
/*  82 */     ENTITY_STATUS_ATTRIBUTE.put("MM", "MMSTATUS");
/*  83 */     ENTITY_STATUS_ATTRIBUTE.put("MODEL", "STATUS");
/*  84 */     ENTITY_STATUS_ATTRIBUTE.put("SVCFEATURE", "STATUS");
/*  85 */     ENTITY_STATUS_ATTRIBUTE.put("SVCMOD", "STATUS");
/*  86 */     ENTITY_STATUS_ATTRIBUTE.put("SWFEATURE", "STATUS");
/*     */     
/*  88 */     ENTITY_WITHDRAW_ATTRIBUTE.put("CATNAV", "PUBTO");
/*  89 */     ENTITY_WITHDRAW_ATTRIBUTE.put("FB", "PUBTO");
/*  90 */     ENTITY_WITHDRAW_ATTRIBUTE.put("FEATURE", "WITHDRAWDATEEFF_T");
/*  91 */     ENTITY_WITHDRAW_ATTRIBUTE.put("IPSCFEAT", "WITHDRAWDATEEFF_T");
/*  92 */     ENTITY_WITHDRAW_ATTRIBUTE.put("LSEO", "LSEOUNPUBDATEMTRGT");
/*  93 */     ENTITY_WITHDRAW_ATTRIBUTE.put("LSEOBUNDLE", "BUNDLUNPUBDATEMTRGT");
/*  94 */     ENTITY_WITHDRAW_ATTRIBUTE.put("MM", "PUBTO");
/*  95 */     ENTITY_WITHDRAW_ATTRIBUTE.put("MODEL", "WTHDRWEFFCTVDATE");
/*  96 */     ENTITY_WITHDRAW_ATTRIBUTE.put("SVCFEATURE", "WITHDRAWDATEEFF_T");
/*  97 */     ENTITY_WITHDRAW_ATTRIBUTE.put("SVCMOD", "WTHDRWEFFCTVDATE");
/*  98 */     ENTITY_WITHDRAW_ATTRIBUTE.put("SWFEATURE", "WITHDRAWDATEEFF_T");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getNowAsDate() {
/* 108 */     if (this.nowAsDate == null) {
/*     */       try {
/* 110 */         this.nowAsDate = DB_TIMESTAMP_FORMAT.parse(getNow());
/* 111 */       } catch (ParseException parseException) {
/* 112 */         handleException(parseException);
/*     */       } 
/*     */     }
/* 115 */     return this.nowAsDate;
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
/*     */   private PackageID getPackageID(int paramInt) {
/*     */     try {
/* 129 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri
/* 130 */           .getEntityType(), "Edit");
/*     */       
/* 132 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 134 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("BILLINGCODE");
/* 135 */       String str = "";
/* 136 */       if (eANFlagAttribute != null) {
/* 137 */         str = eANFlagAttribute.getFirstActiveFlagCode();
/*     */       }
/* 139 */       if (str.equals("PCD")) {
/* 140 */         str = "";
/*     */       }
/* 142 */       return new PackageID(getEntityType(), getEntityID(), paramInt, "N/A", this.m_strNow, str);
/*     */     
/*     */     }
/* 145 */     catch (Exception exception) {
/* 146 */       handleException(exception);
/*     */ 
/*     */       
/* 149 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private EntityList getEntityList(PackageID paramPackageID) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 154 */     D.ebug("Getting entity list for PackageID nlsid=" + paramPackageID.getNLSID() + ", language=" + paramPackageID.getLanguage());
/*     */ 
/*     */     
/* 157 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getEntityType(), "Edit");
/*     */     
/* 159 */     EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, getEntityType(), getEntityID());
/* 160 */     EntityItem[] arrayOfEntityItem = { entityItem };
/*     */     
/* 162 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("TRANSEXTRACTATTR");
/*     */     
/* 164 */     String str = (eANFlagAttribute == null) ? "EXTXLATEGRP1" : eANFlagAttribute.getFirstActiveFlagCode();
/* 165 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, str);
/*     */     
/* 167 */     EntityList entityList = new EntityList(this.m_db, this.m_prof, extractActionItem, arrayOfEntityItem, "XLATEGRP");
/*     */     
/* 169 */     entityList.getProfile().setReadLanguage(new NLSItem(paramPackageID
/* 170 */           .getNLSID(), paramPackageID.getLanguage()));
/* 171 */     return entityList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 177 */     this.nlsidFound = -1;
/*     */     try {
/* 179 */       setReturnCode(0);
/* 180 */       start_ABRBuild(false);
/* 181 */       setNow();
/* 182 */       setControlBlock();
/* 183 */       buildRptHeader();
/* 184 */       for (byte b = 0; b < NLSIDS.length; b++) {
/* 185 */         D.ebug("Checking NLSID " + NLSIDS[b]);
/* 186 */         if (this.nlsidFound != -1)
/* 187 */           break;  handleLanguage(NLSIDS[b]);
/*     */       }
/*     */     
/* 190 */     } catch (Exception exception) {
/* 191 */       handleException(exception);
/*     */     } finally {
/* 193 */       if (this.nlsidFound == -1) {
/* 194 */         println("<p>Error: No NLSID found!</p>");
/*     */       } else {
/* 196 */         println("<h2>Not Queued Entities</h2>");
/* 197 */         Vector vector1 = (Vector)errorReport.get(new Integer(this.nlsidFound));
/* 198 */         if (vector1 != null) {
/* 199 */           Enumeration<StringBuffer> enumeration = vector1.elements();
/* 200 */           while (enumeration.hasMoreElements()) {
/* 201 */             StringBuffer stringBuffer = enumeration.nextElement();
/* 202 */             println(stringBuffer.toString());
/*     */           } 
/*     */         } 
/* 205 */         println("<br>");
/* 206 */         println("<h2>Queued Entities</h2>");
/* 207 */         Vector vector2 = (Vector)report.get(new Integer(this.nlsidFound));
/* 208 */         if (vector2 != null) {
/* 209 */           Enumeration<StringBuffer> enumeration = vector2.elements();
/* 210 */           while (enumeration.hasMoreElements()) {
/* 211 */             StringBuffer stringBuffer = enumeration.nextElement();
/* 212 */             println(stringBuffer.toString());
/*     */           } 
/*     */         } 
/*     */       } 
/* 216 */       setDGString(getABRReturnCode());
/* 217 */       setDGRptName(getShortClassName(getClass()));
/* 218 */       setDGRptClass(getABRCode());
/*     */       
/* 220 */       if (!isReadOnly()) {
/* 221 */         clearSoftLock();
/*     */       }
/* 223 */       printDGSubmitString();
/* 224 */       println(EACustom.getTOUDiv());
/* 225 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleLanguage(int paramInt) throws SQLException, MiddlewareException {
/* 231 */     PackageID packageID = getPackageID(paramInt);
/* 232 */     EntityList entityList = null;
/*     */     try {
/* 234 */       entityList = getEntityList(packageID);
/* 235 */     } catch (Exception exception) {
/* 236 */       D.ebug("Ignoring NLSID " + paramInt + ". Reason: " + exception.getClass() + " : " + exception.getMessage());
/*     */       
/*     */       return;
/*     */     } 
/* 240 */     TranslationPackage translationPackage = Translation.getETSPackage(this.m_db, this.m_prof, packageID);
/*     */     
/* 242 */     if (translationPackage == null) {
/* 243 */       D.ebug("Ignoring NLSID " + paramInt + ". Reason: TranslationPackage is null");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 248 */     Enumeration<TranslationEntity> enumeration = translationPackage.getDataRequest().getEntityElements();
/* 249 */     while (enumeration.hasMoreElements()) {
/* 250 */       TranslationEntity translationEntity = enumeration.nextElement();
/* 251 */       handleTranslationEntity(paramInt, translationEntity, entityList);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleTranslationEntity(int paramInt, TranslationEntity paramTranslationEntity, EntityList paramEntityList) throws MiddlewareException, SQLException {
/* 262 */     String str1 = paramTranslationEntity.getEntityType();
/* 263 */     int i = paramTranslationEntity.getEntityID();
/*     */ 
/*     */     
/* 266 */     String str2 = (String)ENTITY_STATUS_ATTRIBUTE.get(str1);
/*     */     
/* 268 */     String str3 = (String)ENTITY_WITHDRAW_ATTRIBUTE.get(str1);
/*     */     
/* 270 */     D.ebug("Processing entity " + str1 + " (" + i + ")");
/* 271 */     D.ebug("statusAttributeName = " + str2);
/* 272 */     D.ebug("withdrawAttributeName = " + str3);
/*     */     
/* 274 */     if (str2 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 278 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(str1);
/* 279 */     EntityItem entityItem = entityGroup.getEntityItem(str1, i);
/*     */     
/* 281 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 283 */     printEntityStart(stringBuffer, entityItem);
/* 284 */     boolean bool = false;
/* 285 */     String str4 = null;
/*     */ 
/*     */     
/* 288 */     String str5 = PokUtils.getAttributeValue(entityItem, str2, "", "", false);
/*     */     
/* 290 */     EANAttribute eANAttribute = null;
/* 291 */     String str6 = null;
/*     */     
/* 293 */     D.ebug("statusValue = " + str5);
/* 294 */     if ("Final".equals(str5)) {
/* 295 */       eANAttribute = entityItem.getAttribute(str3);
/* 296 */       if (eANAttribute == null) {
/* 297 */         D.ebug("withdraw date is null");
/* 298 */         bool = true;
/*     */       } else {
/* 300 */         str6 = PokUtils.getAttributeValue(entityItem, str3, "", "", false);
/*     */         
/* 302 */         D.ebug("withdrawDateValue = " + str6);
/*     */         try {
/* 304 */           Date date = WITHDRAW_DATE_FORMAT.parse(str6);
/*     */           
/* 306 */           if (date.after(getNowAsDate())) {
/* 307 */             bool = true;
/*     */           } else {
/* 309 */             str4 = "Withdrawal Date is past";
/*     */           } 
/* 311 */         } catch (ParseException parseException) {
/* 312 */           handleException(parseException);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 316 */       str4 = "Status is not final";
/*     */     } 
/*     */     
/* 319 */     if (bool) {
/* 320 */       D.ebug("nlsidFound = " + paramInt);
/* 321 */       this.nlsidFound = paramInt;
/*     */ 
/*     */       
/* 324 */       setFlagValue(entityItem, "ADSABRSTATUS", ADSFEED_VALUE);
/* 325 */       printEntityEnd(stringBuffer);
/* 326 */       addEntityReport(paramInt, stringBuffer);
/*     */     } else {
/* 328 */       printAttribute(stringBuffer, "Status", entityItem.getAttribute(str2), str5);
/* 329 */       printAttribute(stringBuffer, "Withdraw date", eANAttribute, str6);
/* 330 */       printErrorMessage(stringBuffer, str4);
/* 331 */       printEntityEnd(stringBuffer);
/* 332 */       addEntityErrorReport(paramInt, stringBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 339 */     return "1.0";
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 343 */     return "XLPOSTPROCABR";
/*     */   }
/*     */   
/*     */   private void handleException(Exception paramException) {
/* 347 */     println("<p><h3 style=\"color:#c00; font-weight:bold;\">Exception: " + paramException.getMessage() + "</h3>");
/* 348 */     paramException.printStackTrace(getPrintWriter());
/* 349 */     println("</p>");
/* 350 */     paramException.printStackTrace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 358 */     String str1 = getVersion();
/* 359 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 360 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */     
/* 362 */     println(EACustom.getDocTypeHtml());
/* 363 */     println("<head>");
/* 364 */     println(EACustom.getMetaTags("XLPOSTPROCABR.java"));
/* 365 */     println(EACustom.getCSS());
/* 366 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 367 */     println("</head>");
/* 368 */     println("<body id=\"ibm-com\">");
/* 369 */     println(EACustom.getMastheadDiv());
/*     */     
/* 371 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/* 372 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 374 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 376 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 378 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 380 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 382 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private void addEntityReport(int paramInt, StringBuffer paramStringBuffer) {
/* 387 */     Integer integer = new Integer(paramInt);
/* 388 */     Vector<StringBuffer> vector = (Vector)report.get(integer);
/* 389 */     if (vector == null) {
/* 390 */       vector = new Vector();
/* 391 */       report.put(integer, vector);
/*     */     } 
/* 393 */     vector.add(paramStringBuffer);
/*     */   }
/*     */   
/*     */   private void addEntityErrorReport(int paramInt, StringBuffer paramStringBuffer) {
/* 397 */     Integer integer = new Integer(paramInt);
/* 398 */     Vector<StringBuffer> vector = (Vector)errorReport.get(integer);
/* 399 */     if (vector == null) {
/* 400 */       vector = new Vector();
/* 401 */       errorReport.put(integer, vector);
/*     */     } 
/* 403 */     vector.add(paramStringBuffer);
/*     */   }
/*     */   
/*     */   private void printEntityStart(StringBuffer paramStringBuffer, EntityItem paramEntityItem) {
/* 407 */     paramStringBuffer.append("<p>");
/* 408 */     paramStringBuffer.append("Entity type: " + paramEntityItem.getEntityType() + "<br>");
/* 409 */     paramStringBuffer.append("Entity ID: " + paramEntityItem.getEntityID() + "<br>");
/* 410 */     paramStringBuffer.append("Entity description: " + paramEntityItem.getLongDescription() + "<br>");
/*     */   }
/*     */   
/*     */   private void printEntityEnd(StringBuffer paramStringBuffer) {
/* 414 */     paramStringBuffer.append("<br></p>");
/*     */   }
/*     */   
/*     */   private void printAttribute(StringBuffer paramStringBuffer, String paramString1, EANAttribute paramEANAttribute, String paramString2) {
/* 418 */     if (paramEANAttribute == null) {
/* 419 */       paramStringBuffer.append(paramString1 + " (null): " + paramString2 + "<br>");
/*     */     } else {
/* 421 */       paramStringBuffer.append(paramString1 + " (" + paramEANAttribute.getLongDescription() + "): " + paramString2 + "<br>");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void printErrorMessage(StringBuffer paramStringBuffer, String paramString) {
/* 426 */     paramStringBuffer.append("<b>Error: " + paramString + "</b><br>");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\XLPOSTPROCABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */