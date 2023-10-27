/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MQUsage;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class XMLFiterMQIDL
/*     */ {
/*  77 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  78 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private StringBuffer rptSb = new StringBuffer();
/*     */ 
/*     */ 
/*     */   
/*  87 */   private Vector succQueueNameVct = new Vector();
/*     */ 
/*     */   
/*  90 */   private static final Vector FLAGVALUE_COL = new Vector(); private static final String XMLENTITYTYPE = "XMLENTITYTYPE"; private static final String MQUEUE_ATTR = "XMLABRPROPFILE"; private static final String UPDATE_SUFFIX = "_UPDATE"; private static final String NAMESPACE_PREFIX = "'declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/"; private static final String FILTER_SQL_BASE = "select xmlentitytype,xmlentityid,xmlmessage,xmlcachedts from cache.XMLIDLCACHE where XMLCACHEVALIDTO > current timestamp"; static {
/*  91 */     FILTER_TBL = new Hashtable<>();
/*  92 */     ENTITY_ROOT_MAP = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     FILTER_TBL.put("CATNAV", new String[] { "FLFILSYSINDC|F:FLFILSYSINDC|F" });
/*  98 */     FILTER_TBL.put("FCTRANSACTION", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "MACHTYPEATR|U:TOMACHTYPE|X", "MODELATR|T:TOMODEL|X", "WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
/*     */ 
/*     */     
/* 101 */     FILTER_TBL
/* 102 */       .put("FEATURE", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "FCTYPE|U:FCTYPE|X", "COUNTRYLIST|F:COUNTRYLIST/COUNTRYELEMENT/COUNTRYCODE|X", "WITHDRAWDATEEFF_T|D:WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
/*     */ 
/*     */     
/* 105 */     FILTER_TBL.put("SWFEATURE", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "FCTYPE|U:FCTYPE|X", "WITHDRAWDATEEFF_T|D:WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
/*     */     
/* 107 */     FILTER_TBL.put("GBT", new String[0]);
/* 108 */     FILTER_TBL.put("IMG", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X" });
/* 109 */     FILTER_TBL.put("LSEOBUNDLE", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "FLFILSYSINDC|F:FLFILSYSINDC|F", "BUNDLETYPE|F:BUNDLETYPE|X", "BUNDLUNPUBDATEMTRGT|D:WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D", "SPECBID|U:SPECIALBID|X", "COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X", "DIVTEXT|F:DIVISION|X" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     FILTER_TBL.put("MODEL", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "FLFILSYSINDC|F:FLFILSYSINDC|F", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D", "WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D", "SPECBID|U:SPECBID|X", "COFCAT|U:CATEGORY|X", "COFSUBCAT|U:SUBCATEGORY|X", "COFGRP|U:GROUP|X", "COFSUBGRP|U:SUBGROUP|X", "COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X", "DIVTEXT|F:DIVISION|X" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     FILTER_TBL.put("MODELCONVERT", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "MACHTYPEATR|U:TOMACHTYPE|X", "MODELATR|T:TOMODEL|X", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D", "WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D", "COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X" });
/*     */ 
/*     */     
/* 123 */     FILTER_TBL.put("LSEO", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "FLFILSYSINDC|F:FLFILSYSINDC|F", "LSEOUNPUBDATEMTRGT|D:WTHDRWEFFCTVDATE|D", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D", "SPECBID|U:SPECIALBID|X", "COFCAT|U:CATEGORY|X", "COFSUBCAT|U:SUBCATEGORY|X", "COFGRP|U:GROUP|X", "COFSUBGRP|U:SUBGROUP|X", "COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X", "DIVTEXT|F:DIVISION|X" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     FILTER_TBL.put("SLEORGNPLNTCODE", new String[0]);
/*     */ 
/*     */ 
/*     */     
/* 134 */     FILTER_TBL.put("SVCLEV", new String[0]);
/* 135 */     FILTER_TBL.put("SVCMOD", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D", "SVCMODCATG|U:CATEGORY|X", "SVCMODSUBCATG|U:SUBCATEGORY|X", "SVCMODGRP|U:GROUP|X", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D", "SVCMODSUBGRP|U:SUBGROUP|X", "COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X", "DIVTEXT|F:DIVISION|X" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     FILTER_TBL.put("PRODSTRUCT", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "MACHTYPEATR|U:MACHTYPE|X", "MODELATR|T:MODEL|X", "FCTYPE|U:FCTYPE|X", "WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D", "FLFILSYSINDC|F:FLFILSYSINDC|F", "COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
/*     */ 
/*     */     
/* 143 */     FILTER_TBL.put("SWPRODSTRUCT", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X", "MACHTYPEATR|U:MACHTYPE|X", "MODELATR|T:MODEL|X", "FCTYPE|U:FCTYPE|X", "WTHDRWEFFCTVDATE|D:WTHDRWEFFCTVDATE|D", "COUNTRYLIST|F:AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC|X", "WITHDRAWDATEMIN|A:WTHDRWEFFCTVDATE|D" });
/*     */ 
/*     */ 
/*     */     
/* 147 */     FILTER_TBL.put("WARR", new String[0]);
/* 148 */     FILTER_TBL.put("REVUNBUNDCOMP", new String[0]);
/*     */ 
/*     */     
/* 151 */     FILTER_TBL.put("REFOFER", new String[] { "PDHDOMAIN|F:PDHDOMAIN|X" });
/* 152 */     FILTER_TBL.put("REFOFERFEAT", new String[0]);
/*     */ 
/*     */     
/* 155 */     ENTITY_ROOT_MAP.put("LSEO", "SEO");
/* 156 */     ENTITY_ROOT_MAP.put("SWFEATURE", "FEATURE");
/* 157 */     ENTITY_ROOT_MAP.put("REVUNBUNDCOMP", "UNBUNDCOMP");
/* 158 */     ENTITY_ROOT_MAP.put("PRODSTRUCT", "TMF");
/* 159 */     ENTITY_ROOT_MAP.put("SWPRODSTRUCT", "TMF");
/* 160 */     ENTITY_ROOT_MAP.put("IMG", "IMAGE");
/*     */ 
/*     */     
/* 163 */     FLAGVALUE_COL.add("COUNTRYLIST/COUNTRYELEMENT/COUNTRYCODE");
/* 164 */     FLAGVALUE_COL.add("AVAILABILITYLIST/AVAILABILITYELEMENT/COUNTRY_FC");
/*     */     
/* 166 */     FLAGVALUE_COL.add("COUNTRY_FC");
/* 167 */     FLAGVALUE_COL.add("FLFILSYSINDC");
/*     */   }
/*     */   private static final String XML_TITLE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"; private ADSIDLSTATUS abr; private Profile profile; private Database m_db; private static final Hashtable FILTER_TBL; private static final Hashtable ENTITY_ROOT_MAP;
/*     */   public XMLFiterMQIDL(ADSIDLSTATUS paramADSIDLSTATUS) {
/* 171 */     this.abr = paramADSIDLSTATUS;
/* 172 */     this.profile = paramADSIDLSTATUS.getProfile();
/* 173 */     this.m_db = paramADSIDLSTATUS.getDatabase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLFiterMQIDL(Database paramDatabase) {
/* 180 */     this.m_db = paramDatabase;
/*     */   }
/*     */   
/*     */   private Vector getAllFilters(EntityItem paramEntityItem, String paramString) {
/* 184 */     Vector<String> vector1 = new Vector();
/* 185 */     Vector<String> vector2 = new Vector();
/*     */     
/* 187 */     String[] arrayOfString = (String[])FILTER_TBL.get(paramString);
/* 188 */     if (arrayOfString != null) {
/* 189 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 190 */         String str1 = getFilterSql(arrayOfString[b], paramEntityItem);
/*     */         
/* 192 */         if (isValidCond(str1)) {
/* 193 */           if (arrayOfString[b].endsWith("X")) {
/* 194 */             vector2.add(str1);
/*     */           } else {
/* 196 */             vector1.add(str1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 201 */     String str = compositeFilterCond(vector2).toString();
/* 202 */     if (isValidCond(str)) {
/* 203 */       String str1 = compositeFilterforXml(str, paramString);
/* 204 */       vector1.add(str1);
/*     */     } 
/* 206 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFilterSql(String paramString, EntityItem paramEntityItem) {
/* 212 */     String[] arrayOfString1 = getArraybasedOnDelimiter(paramString, ":");
/* 213 */     String[] arrayOfString2 = getArraybasedOnDelimiter(arrayOfString1[0], "|");
/* 214 */     String[] arrayOfString3 = getArraybasedOnDelimiter(arrayOfString1[1], "|");
/* 215 */     String str = getAttributeValue(paramEntityItem, arrayOfString2[0], arrayOfString3[0]);
/* 216 */     if (isValidCond(str)) {
/* 217 */       this.rptSb.append("<br/>" + arrayOfString2[0] + " = " + str + "<br/>");
/*     */ 
/*     */       
/* 220 */       if ("F".equals(arrayOfString2[1])) {
/* 221 */         return getConditionforMultiFlag(paramEntityItem, str, arrayOfString3[0], "X"
/* 222 */             .equals(arrayOfString3[1]));
/*     */       }
/* 224 */       return getEvaluationStr(arrayOfString3[0], str, "D".equals(arrayOfString2[1]), "X".equals(arrayOfString3[1]), "A".equals(arrayOfString2[1]));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getAttributeValue(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 238 */     String str = null;
/* 239 */     if (paramString2 != null && FLAGVALUE_COL.contains(paramString2)) {
/* 240 */       str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/*     */     } else {
/* 242 */       str = PokUtils.getAttributeValue(paramEntityItem, paramString1, "|", null, false);
/*     */     } 
/*     */ 
/*     */     
/* 246 */     if ("COFGRP".equalsIgnoreCase(paramString1) && !isValidCond(str)) {
/* 247 */       str = "Base";
/*     */     }
/* 249 */     if (("COFGRP".equalsIgnoreCase(paramString1) || "COFSUBGRP".equalsIgnoreCase(paramString1)) && "Service"
/* 250 */       .equalsIgnoreCase(PokUtils.getAttributeValue(paramEntityItem, "COFCAT", "", null, false)))
/*     */     {
/* 252 */       return null;
/*     */     }
/* 254 */     if ("DIVTEXT".equalsIgnoreCase(paramString1)) {
/* 255 */       str = replace(str, ",", "|");
/*     */     }
/*     */     
/* 258 */     return str;
/*     */   }
/*     */   
/*     */   public String replace(String paramString1, String paramString2, String paramString3) {
/* 262 */     if (paramString1 == null || paramString2 == null || paramString3 == null) return null;
/*     */ 
/*     */     
/* 265 */     StringBuffer stringBuffer = new StringBuffer(paramString1);
/*     */     try {
/*     */       int i;
/* 268 */       while ((i = paramString1.indexOf(paramString2)) != -1) {
/* 269 */         stringBuffer.replace(i, i + paramString2.length(), paramString3);
/* 270 */         paramString1 = stringBuffer.toString();
/*     */       }
/*     */     
/* 273 */     } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
/* 274 */       this.rptSb.append("<br />" + stringIndexOutOfBoundsException.getMessage() + "<br />");
/* 275 */       return null;
/*     */     } 
/* 277 */     return paramString1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getArraybasedOnDelimiter(String paramString1, String paramString2) {
/* 283 */     int i = paramString1.indexOf(paramString2);
/* 284 */     String[] arrayOfString = new String[2];
/* 285 */     arrayOfString[0] = paramString1.substring(0, i);
/* 286 */     arrayOfString[1] = paramString1.substring(i + 1);
/* 287 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   private String generateSqlBasedFilter(EntityItem paramEntityItem) {
/* 291 */     String str = PokUtils.getAttributeValue(paramEntityItem, "XMLENTITYTYPE", "", null, false);
/*     */ 
/*     */     
/* 294 */     this.rptSb.append("<br /><h2>Looking for " + str + " with the following filters:</h2><br />");
/* 295 */     StringBuffer stringBuffer = new StringBuffer("select xmlentitytype,xmlentityid,xmlmessage,xmlcachedts from cache.XMLIDLCACHE where XMLCACHEVALIDTO > current timestamp");
/*     */     
/* 297 */     if (isValidCond(str)) {
/* 298 */       stringBuffer
/* 299 */         .append(" and " + 
/* 300 */           getEvaluationStr("XMLENTITYTYPE", str, false, false, false));
/*     */       
/* 302 */       String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "XMLSTATUS");
/* 303 */       if (isValidCond(str1) && "XSTATUS02".equalsIgnoreCase(str1)) {
/* 304 */         stringBuffer.append(" and STATUS = '0020' ");
/* 305 */         this.rptSb.append("<br/>STATUS = '0020'<br/>");
/*     */       } 
/*     */       
/* 308 */       String str2 = getAttributeValue(paramEntityItem, "XMLIDLREQDTS", null);
/* 309 */       if (str2 == null) {
/* 310 */         str2 = "1980-01-01 00:00:00.000000";
/*     */       } else {
/* 312 */         this.rptSb.append("<br/>XMLIDLREQDTS = " + str2 + "<br/>");
/*     */       } 
/*     */       
/* 315 */       stringBuffer.append(" and XMLCACHEDTS >= '" + str2 + "' ");
/* 316 */       if ("REFOFER".equals(str)) {
/* 317 */         String str5 = PokUtils.getAttributeValue(paramEntityItem, "DCG", "", null, false);
/* 318 */         if ("Y".equals(str5)) {
/* 319 */           stringBuffer.append(" and length(trim(xmlcast(xmlquery('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/REFOFER_UPDATE\"; $i/REFOFER_UPDATE/PRODUCTID/text()' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\") as varchar(8))))=7 ");
/*     */         }
/*     */       } 
/*     */       
/* 323 */       String str3 = PokUtils.getAttributeValue(paramEntityItem, "OLDINDC", "", null, false);
/* 324 */       if (str3 == null || "".equals(str3)) {
/* 325 */         stringBuffer.append(" and (OLDINDC != 'Y' or OLDINDC is null) ");
/*     */       }
/*     */       
/* 328 */       Vector vector = getAllFilters(paramEntityItem, str);
/* 329 */       StringBuffer stringBuffer1 = compositeFilterCond(vector);
/* 330 */       String str4 = stringBuffer1.toString();
/* 331 */       if (isValidCond(str4))
/* 332 */         stringBuffer.append(" and " + str4); 
/*     */     } else {
/* 334 */       this.rptSb.append("<br />no entitytype was specified, will get all records (XMLCACHEVALIDTO &gt; current timestamp in Cache.<br />");
/*     */     } 
/*     */     
/* 337 */     stringBuffer.append(" ORDER BY XMLCACHEDTS with ur");
/* 338 */     this.rptSb.append(NEWLINE);
/*     */     
/* 340 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private StringBuffer compositeFilterCond(Vector<String> paramVector) {
/* 344 */     StringBuffer stringBuffer = new StringBuffer("");
/* 345 */     if (paramVector != null && paramVector.size() > 0) {
/* 346 */       stringBuffer.append(paramVector.get(0));
/* 347 */       if (paramVector.size() > 1) {
/* 348 */         for (byte b = 1; b < paramVector.size(); b++) {
/* 349 */           stringBuffer.append(" and " + (String)paramVector.get(b));
/*     */         }
/*     */       }
/*     */     } 
/* 353 */     return stringBuffer;
/*     */   }
/*     */   
/*     */   private boolean isValidCond(String paramString) {
/* 357 */     return (paramString != null && paramString.trim().length() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getEvaluationStr(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 362 */     if (paramBoolean3)
/* 363 */       return " " + paramString1 + " >= '" + paramString2 + "'"; 
/* 364 */     if (paramBoolean1)
/* 365 */       return " " + paramString1 + " <= '" + paramString2 + "'"; 
/* 366 */     if (paramBoolean2) {
/* 367 */       return " " + paramString1 + "/text() = \"" + paramString2 + "\"";
/*     */     }
/* 369 */     return " " + paramString1 + " = '" + paramString2 + "'";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getConditionforMultiFlag(EntityItem paramEntityItem, String paramString1, String paramString2, boolean paramBoolean) {
/* 374 */     StringBuffer stringBuffer = new StringBuffer("");
/* 375 */     if (paramString1 != null) {
/* 376 */       String[] arrayOfString = PokUtils.convertToArray(paramString1);
/* 377 */       stringBuffer.append("(");
/* 378 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 379 */         if (b > 0)
/* 380 */           stringBuffer.append(" or "); 
/* 381 */         if (paramBoolean) {
/* 382 */           stringBuffer.append(paramString2);
/* 383 */           stringBuffer.append("/text() = \"" + arrayOfString[b] + "\"");
/*     */         } else {
/* 385 */           stringBuffer.append(paramString2);
/* 386 */           stringBuffer.append(" like '%|" + arrayOfString[b] + "|%' or ");
/* 387 */           stringBuffer.append(paramString2);
/* 388 */           stringBuffer.append(" like '" + arrayOfString[b] + "|%' or ");
/* 389 */           stringBuffer.append(paramString2);
/* 390 */           stringBuffer.append(" like '%|" + arrayOfString[b] + "' or ");
/* 391 */           stringBuffer.append(paramString2);
/* 392 */           stringBuffer.append(" = '" + arrayOfString[b] + "'");
/*     */         } 
/*     */       } 
/* 395 */       stringBuffer.append(")");
/*     */     } 
/* 397 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getFullXmlAndSendToQue(EntityItem paramEntityItem) throws Exception {
/* 403 */     String str1 = generateSqlBasedFilter(paramEntityItem);
/* 404 */     log(str1);
/* 405 */     PreparedStatement preparedStatement = null;
/* 406 */     ResultSet resultSet = null;
/* 407 */     this.succQueueNameVct.clear();
/*     */     
/* 409 */     Vector<String> vector = new Vector();
/* 410 */     Vector<ResourceBundle> vector1 = new Vector();
/* 411 */     Vector<Hashtable> vector2 = getQueueInfors(paramEntityItem, vector, vector1);
/* 412 */     if (vector2 == null || vector2.size() == 0) {
/* 413 */       this.rptSb.append("<br/>no queue was set, will quit <br/>");
/* 414 */       log("no queue was set!");
/* 415 */       log("ENTITYTYPE = " + paramEntityItem.getEntityType());
/* 416 */       log("ENTITYID = " + paramEntityItem.getEntityID());
/*     */       return;
/*     */     } 
/* 419 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "XMLENTITYTYPE", "", null, false);
/*     */     
/* 421 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "XMLIDLMAXMSG", "", null, false);
/*     */     
/* 423 */     int i = -1;
/* 424 */     if (isValidCond(str3)) {
/*     */       try {
/* 426 */         i = Integer.parseInt(str3);
/* 427 */         this.rptSb.append("<br/>XMLIDLMAXMSG = " + str3 + " <br/>");
/* 428 */         log("XMLIDLMAXMSG = " + str3);
/* 429 */       } catch (Exception exception) {
/* 430 */         log("XMLIDLMAXMSG can not be parsed to integer");
/* 431 */         this.rptSb.append("<br/>XMLIDLMAXMSG can not be parsed to integer<br/>");
/* 432 */         log(exception.getMessage());
/* 433 */         throw exception;
/*     */       } 
/*     */     }
/*     */     try {
/* 437 */       preparedStatement = this.m_db.getODSConnection().prepareStatement(str1);
/* 438 */       resultSet = preparedStatement.executeQuery();
/* 439 */       StringBuffer stringBuffer = new StringBuffer();
/* 440 */       byte b = 0;
/* 441 */       String str4 = "";
/*     */       
/* 443 */       ArrayList arrayList = new ArrayList();
/*     */       
/* 445 */       EntityChangeHistoryGroup entityChangeHistoryGroup = paramEntityItem.getThisChangeHistoryGroup(this.m_db);
/* 446 */       if (entityChangeHistoryGroup != null) {
/* 447 */         int k = entityChangeHistoryGroup.getChangeHistoryItemCount();
/* 448 */         this.abr.addDebug(paramEntityItem.getKey() + " getChangeHistoryItemCount: " + k);
/* 449 */         if (k > 0) {
/* 450 */           str4 = entityChangeHistoryGroup.getChangeHistoryItem(k - 1).getChangeDate();
/* 451 */           this.abr.addDebug("setupDTS : " + str4);
/*     */         } 
/*     */       } else {
/*     */         
/* 455 */         this.abr.addDebug(paramEntityItem.getKey() + "can not find EntitychangeHistoryGroup! SETUPDTS is getnow().");
/*     */       } 
/* 457 */       if ("".equals(str4)) {
/* 458 */         DatePackage datePackage = this.m_db.getDates();
/* 459 */         str4 = datePackage.getNow();
/*     */       } 
/*     */ 
/*     */       
/* 463 */       String str5 = paramEntityItem.getEntityType();
/* 464 */       int j = paramEntityItem.getEntityID();
/* 465 */       String str6 = getMQCID(str2);
/* 466 */       String str7 = "";
/* 467 */       String str8 = "";
/* 468 */       String str9 = PokUtils.getAttributeFlagValue(paramEntityItem, "XMLABRPROPFILE");
/* 469 */       if (str9 == null) {
/* 470 */         str9 = "";
/*     */       }
/* 472 */       this.abr.addDebug("Total: " + resultSet.getFetchSize() + " setupDTS: " + str4 + " setupType: " + str5 + " setupID: " + j + " entityType: " + str2 + " MQPropFile: " + str9);
/*     */       
/* 474 */       String str10 = null;
/* 475 */       while (resultSet.next()) {
/* 476 */         String str11 = resultSet.getString(3);
/* 477 */         int k = resultSet.getInt(2);
/*     */         
/* 479 */         String str12 = "";
/* 480 */         if (str11 == null) {
/* 481 */           str12 = resultSet.getString(4);
/*     */         } else {
/* 483 */           String str14 = "<DTSOFMSG>";
/* 484 */           String str15 = "</DTSOFMSG>";
/* 485 */           int m = str11.indexOf(str14);
/* 486 */           int n = str11.indexOf(str15);
/* 487 */           if (m > -1 && n > -1 && n > m) {
/* 488 */             str12 = str11.substring(m + str14.length(), n);
/*     */           } else {
/* 490 */             str12 = resultSet.getString(4);
/*     */           } 
/*     */         } 
/* 493 */         if (str11 == null) {
/* 494 */           log("Can not get the xml for " + k);
/*     */           
/* 496 */           str7 = "F";
/* 497 */           str8 = "Can not get the xml from xmlidlcache for " + k;
/*     */           
/* 499 */           StringTokenizer stringTokenizer = new StringTokenizer(str9, "|", false);
/* 500 */           while (stringTokenizer.hasMoreElements()) {
/* 501 */             String str = stringTokenizer.nextToken();
/* 502 */             putMSGList(arrayList, str5, j, str4, "", str6, str2, k, str12, str7, str8, str);
/*     */           } 
/*     */           continue;
/*     */         } 
/* 506 */         String str13 = changeFormatOfTime(resultSet.getString(4));
/* 507 */         str10 = str13;
/* 508 */         str11 = removeIllString(str11);
/* 509 */         for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 510 */           ResourceBundle resourceBundle; String str; Hashtable<String, String> hashtable = vector2.get(b1);
/*     */         } 
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
/*     */         
/* 555 */         if (b)
/* 556 */           stringBuffer.append(","); 
/* 557 */         stringBuffer.append(k);
/* 558 */         b++;
/* 559 */         if (b % 1000 == 0)
/* 560 */           this.abr.setTextValue(paramEntityItem, "XMLIDLREQDTS", str13); 
/* 561 */         if (i >= 0 && b == i) {
/* 562 */           this.abr.setTextValue(paramEntityItem, "XMLIDLREQDTS", str13);
/* 563 */           log("The count of msg has reached the max msg definition in the setup entity: " + i);
/*     */           break;
/*     */         } 
/*     */       } 
/* 567 */       if (str10 != null)
/* 568 */         this.abr.setTextValue(paramEntityItem, "XMLIDLREQDTS", str10); 
/* 569 */       if (arrayList.size() > 0)
/*     */       {
/* 571 */         inertMSGLOG(arrayList, str5, j, str4, str2);
/*     */       }
/*     */ 
/*     */       
/* 575 */       this.rptSb.append("<br/><br/><h3>" + b + " entities were sent. </h3><br/>");
/*     */       
/* 577 */       this.rptSb.append("<!--");
/* 578 */       this.rptSb.append("<br/>" + stringBuffer + "<br/>");
/* 579 */       this.rptSb.append("-->");
/*     */     } finally {
/* 581 */       if (resultSet != null) {
/*     */         try {
/* 583 */           resultSet.close();
/* 584 */         } catch (Exception exception) {
/* 585 */           System.err
/* 586 */             .println("getFullXmlAndSendToQue(), unable to close result. " + exception);
/*     */         } 
/*     */         
/* 589 */         resultSet = null;
/*     */       } 
/*     */       
/* 592 */       if (preparedStatement != null) {
/*     */         try {
/* 594 */           preparedStatement.close();
/* 595 */         } catch (Exception exception) {
/* 596 */           System.err
/* 597 */             .println("getFullXmlAndSendToQue(), unable to close ps. " + exception);
/*     */         } 
/*     */         
/* 600 */         preparedStatement = null;
/*     */       } 
/* 602 */       this.m_db.commit();
/* 603 */       this.m_db.freeStatement();
/* 604 */       this.m_db.isPending();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String changeFormatOfTime(String paramString) {
/* 610 */     String str1 = paramString.substring(0, 23);
/* 611 */     String str2 = paramString.substring(23);
/* 612 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
/*     */     try {
/* 614 */       Date date = simpleDateFormat.parse(str1);
/* 615 */       SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
/* 616 */       return simpleDateFormat1.format(date) + str2;
/* 617 */     } catch (Exception exception) {
/* 618 */       this.rptSb.append("<br/><h2>Error: Parse Date Error.</h2><br/>");
/* 619 */       this.rptSb.append("<!--" + exception.getMessage() + "-->");
/* 620 */       log(exception.getMessage());
/* 621 */       exception.printStackTrace();
/*     */       
/* 623 */       return null;
/*     */     } 
/*     */   }
/*     */   private Vector getQueueInfors(EntityItem paramEntityItem, Vector<String> paramVector1, Vector<ResourceBundle> paramVector2) {
/* 627 */     Vector<Hashtable> vector = new Vector();
/*     */     
/* 629 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "XMLABRPROPFILE");
/* 630 */     this.rptSb.append("<br/><h2>Queue Information: </h2><br/>");
/* 631 */     this.rptSb.append("<br/>" + str + "<br/>");
/* 632 */     if (str != null) {
/* 633 */       String[] arrayOfString = PokUtils.convertToArray(str);
/* 634 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*     */         try {
/* 636 */           ResourceBundle resourceBundle = ResourceBundle.getBundle(arrayOfString[b], 
/* 637 */               getLocale(this.profile.getReadLanguage().getNLSID()));
/*     */           
/* 639 */           Hashtable hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/* 640 */           vector.add(hashtable);
/* 641 */           paramVector1.add(arrayOfString[b]);
/* 642 */           paramVector2.add(resourceBundle);
/* 643 */         } catch (Exception exception) {
/* 644 */           this.rptSb.append("<br/>Can not find the Queue information for " + arrayOfString[b] + "<br/>");
/* 645 */           this.rptSb.append("<!--" + exception.getMessage() + "-->");
/* 646 */           this.abr.addError("Prop file " + arrayOfString[b] + " " + paramEntityItem.getKey() + " not found");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 651 */     return vector;
/*     */   }
/*     */   
/*     */   private String compositeFilterforXml(String paramString1, String paramString2) {
/* 655 */     if (isValidCond(paramString1)) {
/* 656 */       StringBuffer stringBuffer = new StringBuffer("");
/* 657 */       String str1 = (String)ENTITY_ROOT_MAP.get(paramString2);
/* 658 */       if (str1 == null) {
/* 659 */         str1 = paramString2;
/*     */       }
/* 661 */       String str2 = str1 + "_UPDATE";
/* 662 */       stringBuffer
/* 663 */         .append("xmlexists(")
/* 664 */         .append("'declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/")
/* 665 */         .append(str2)
/* 666 */         .append("\"; $i/")
/* 667 */         .append(str2)
/* 668 */         .append("[")
/* 669 */         .append(paramString1)
/* 670 */         .append("]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\")");
/*     */       
/* 672 */       return stringBuffer.toString();
/*     */     } 
/* 674 */     return null;
/*     */   }
/*     */   
/*     */   public static Locale getLocale(int paramInt) {
/* 678 */     Locale locale = null;
/* 679 */     switch (paramInt)
/*     */     
/*     */     { case 1:
/* 682 */         locale = Locale.US;
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
/* 706 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*     */   }
/*     */   
/*     */   private String getMQCID(String paramString) {
/* 710 */     String str = (String)ENTITY_ROOT_MAP.get(paramString);
/* 711 */     if (str == null) {
/* 712 */       str = paramString;
/*     */     }
/* 714 */     return str + "_UPDATE";
/*     */   }
/*     */   
/*     */   private String removeIllString(String paramString) {
/* 718 */     int i = paramString.indexOf("?>");
/* 719 */     if (i > 0) {
/* 720 */       paramString = paramString.substring(i + 2);
/*     */     }
/* 722 */     return paramString;
/*     */   }
/*     */   
/*     */   public String getReport() {
/* 726 */     return this.rptSb.toString();
/*     */   }
/*     */   
/*     */   private void log(String paramString) {
/* 730 */     this.abr.addDebug(paramString);
/*     */   }
/*     */   
/*     */   private void putMSGList(ArrayList<String[]> paramArrayList, String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2, String paramString6, String paramString7, String paramString8, String paramString9) throws MiddlewareException, SQLException {
/* 734 */     paramArrayList.add(new String[] { Integer.toString(paramInt2), paramString3, paramString4, paramString6, paramString7, paramString8, paramString9 });
/*     */     
/* 736 */     if (paramArrayList.size() >= 100) {
/* 737 */       inertMSGLOG(paramArrayList, paramString1, paramInt1, paramString2, paramString5);
/*     */     }
/*     */   }
/*     */   
/*     */   private void inertMSGLOG(ArrayList paramArrayList, String paramString1, int paramInt, String paramString2, String paramString3) throws MiddlewareException, SQLException {
/* 742 */     long l = System.currentTimeMillis();
/* 743 */     Iterator<String[]> iterator = paramArrayList.iterator();
/*     */     
/* 745 */     PreparedStatement preparedStatement = null;
/* 746 */     StringBuffer stringBuffer = new StringBuffer();
/* 747 */     stringBuffer
/* 748 */       .append("INSERT INTO cache.XMLMSGLOG (SETUPENTITYTYPE, SETUPENTITYID, SETUPDTS, SENDMSGDTS, MSGTYPE, MSGCOUNT, ENTITYTYPE, ENTITYID, DTSOFMSG, MSGSTATUS, REASON, MQPROPFILE) ");
/* 749 */     stringBuffer.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/*     */     
/*     */     try {
/* 753 */       preparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/* 754 */       while (iterator.hasNext()) {
/* 755 */         String[] arrayOfString = iterator.next();
/* 756 */         int i = Integer.parseInt(arrayOfString[0]);
/* 757 */         preparedStatement.setString(1, paramString1);
/* 758 */         preparedStatement.setInt(2, paramInt);
/* 759 */         preparedStatement.setString(3, paramString2);
/* 760 */         preparedStatement.setString(4, arrayOfString[1]);
/* 761 */         preparedStatement.setString(5, arrayOfString[2]);
/* 762 */         preparedStatement.setInt(6, 1);
/* 763 */         preparedStatement.setString(7, paramString3);
/* 764 */         preparedStatement.setInt(8, i);
/* 765 */         preparedStatement.setString(9, arrayOfString[3]);
/* 766 */         preparedStatement.setString(10, arrayOfString[4]);
/* 767 */         preparedStatement.setString(11, arrayOfString[5]);
/* 768 */         preparedStatement.setString(12, arrayOfString[6]);
/* 769 */         preparedStatement.addBatch();
/* 770 */         this.abr.addDebug("SETUPENTITYTYPE:" + paramString1 + "-" + paramString1.length() + "!SETUPENTITYID:" + paramInt + "-" + (paramInt + "")
/* 771 */             .length() + "!SETUPDTS:" + paramString2 + "-" + paramString2
/* 772 */             .length() + "!SENDMSGDTS:" + arrayOfString[1] + "-" + arrayOfString[1]
/* 773 */             .length() + "!MSGTYPE:" + arrayOfString[2] + "-" + arrayOfString[2]
/* 774 */             .length() + "!ENTITYTYPE:" + paramString3 + "-" + paramString3
/* 775 */             .length() + "!ENTITYID:" + i + "-" + (i + "")
/* 776 */             .length() + "!DTSOFMSG:" + arrayOfString[3] + "-" + arrayOfString[3]
/* 777 */             .length() + "!MSGSTATUS:" + arrayOfString[4] + "-" + arrayOfString[4]
/* 778 */             .length() + "!REASON:" + arrayOfString[5] + "-" + arrayOfString[5]
/* 779 */             .length() + "!MQPROPFILE:" + arrayOfString[6] + "-" + arrayOfString[6]
/* 780 */             .length() + "!");
/*     */       } 
/*     */       
/* 783 */       preparedStatement.executeBatch();
/*     */       
/* 785 */       if (this.m_db.getODSConnection() != null) {
/* 786 */         this.m_db.getODSConnection().commit();
/*     */       }
/*     */       
/* 789 */       this.abr.addDebug(paramArrayList.size() + " records was inserted into table. Total Time: " + 
/*     */           
/* 791 */           Stopwatch.format(System.currentTimeMillis() - l));
/* 792 */     } catch (MiddlewareException middlewareException) {
/* 793 */       this.abr.addDebug("MiddlewareException on ? " + middlewareException);
/* 794 */       middlewareException.printStackTrace();
/* 795 */       throw middlewareException;
/* 796 */     } catch (SQLException sQLException) {
/* 797 */       this.abr.addDebug("SQLException on ? " + sQLException);
/* 798 */       sQLException.printStackTrace();
/* 799 */       throw sQLException;
/*     */     } finally {
/* 801 */       paramArrayList.clear();
/* 802 */       if (preparedStatement != null)
/*     */         try {
/* 804 */           preparedStatement.close();
/* 805 */         } catch (SQLException sQLException) {
/* 806 */           sQLException.printStackTrace();
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\XMLFiterMQIDL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */