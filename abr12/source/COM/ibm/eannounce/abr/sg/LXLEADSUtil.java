/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.LinkActionItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.WorkflowException;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.LockException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
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
/*     */ public class LXLEADSUtil
/*     */ {
/*     */   protected static final String COFCAT_HW = "100";
/*     */   protected static final String COFCAT_SW = "101";
/*     */   protected static final String COFCAT_SVC = "102";
/*     */   protected static final String COFSUBCAT_HIPO = "125";
/*     */   protected static final String COFSUBCAT_Application = "127";
/*     */   protected static final String COFSUBCAT_Subscription = "133";
/*     */   protected static final HashSet SW_COFSUBCAT_SET;
/*     */   protected static final String COFGRP_BASE = "150";
/*     */   private static final String MODEL_SRCHACTION_NAME = "LDSRDMODEL";
/*     */   private static final String WWSEO_SRCHACTION_NAME = "LDSRDWWSEO";
/*     */   private static final String LSEO_SRCHACTION_NAME = "LDSRDLSEO";
/*     */   private static final String WWSEO_CREATEACTION_NAME = "LDCRWWSEO";
/*     */   private static final String LSEO_CREATEACTION_NAME = "LDCRLSEO";
/*     */   private static final String WWSEOPS_LINKACTION_NAME = "LDLINKPRODSTWWSEO";
/*     */   private static final String WWSEOSWPS_LINKACTION_NAME = "LDLINKSWPRODWWSEO";
/*     */   private static final String GENERALAREA_SRCHACTION_NAME = "LDSRDGENAREA";
/*     */   protected static final String PS_SRCHACTION_NAME = "LDSRDPRODSTRUCT";
/*     */   protected static final String SWPS_SRCHACTION_NAME = "LDSRDSWPRODSTRUCT";
/*  77 */   private static Hashtable SG_GENAREACODE_TBL = null;
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
/*  98 */   private static final Hashtable AUDIEN_TBL = new Hashtable<>(); static {
/*  99 */     AUDIEN_TBL.put("Enterprise Direct", "10062");
/* 100 */     AUDIEN_TBL.put("Odyssey", "10062");
/* 101 */     Vector<String> vector = new Vector(3);
/* 102 */     vector.add("10062");
/* 103 */     vector.add("10046");
/* 104 */     vector.add("10048");
/* 105 */     AUDIEN_TBL.put("Indirect", vector);
/* 106 */     SW_COFSUBCAT_SET = new HashSet();
/* 107 */     SW_COFSUBCAT_SET.add("125");
/* 108 */     SW_COFSUBCAT_SET.add("127");
/* 109 */     SW_COFSUBCAT_SET.add("133");
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
/*     */   protected static EntityItem searchForModel(LXABRSTATUS paramLXABRSTATUS, String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 136 */     EntityItem entityItem = null;
/* 137 */     if (paramString1 != null && paramString2 != null) {
/* 138 */       Vector<String> vector1 = new Vector(2);
/* 139 */       vector1.addElement("MACHTYPEATR");
/* 140 */       vector1.addElement("MODELATR");
/* 141 */       Vector<String> vector2 = new Vector(2);
/* 142 */       vector2.addElement(paramString1);
/* 143 */       vector2.addElement(paramString2);
/*     */       
/* 145 */       EntityItem[] arrayOfEntityItem = null;
/*     */       
/*     */       try {
/* 148 */         StringBuffer stringBuffer = new StringBuffer();
/* 149 */         arrayOfEntityItem = ABRUtil.doSearch(paramLXABRSTATUS.getDatabase(), paramLXABRSTATUS.getProfile(), "LDSRDMODEL", "MODEL", false, vector1, vector2, stringBuffer);
/*     */         
/* 151 */         if (stringBuffer.length() > 0) {
/* 152 */           paramLXABRSTATUS.addDebug(stringBuffer.toString());
/*     */         }
/* 154 */       } catch (SBRException sBRException) {
/*     */         
/* 156 */         StringWriter stringWriter = new StringWriter();
/* 157 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 158 */         paramLXABRSTATUS.addDebug("searchForModel SBRException: " + stringWriter.getBuffer().toString());
/*     */       } 
/* 160 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 161 */         if (arrayOfEntityItem.length == 1) {
/* 162 */           entityItem = arrayOfEntityItem[0];
/* 163 */           paramLXABRSTATUS.addDebug("LXLEADSUtil.searchForModel found single " + entityItem.getKey() + " for mt:" + paramString1 + " model:" + paramString2);
/*     */         } else {
/*     */           
/* 166 */           boolean bool = true;
/* 167 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 168 */             EntityItem entityItem1 = arrayOfEntityItem[b];
/*     */             
/* 170 */             String str = PokUtils.getAttributeFlagValue(entityItem1, "COFCAT");
/* 171 */             if ("100".equals(str)) {
/*     */               
/* 173 */               String str1 = PokUtils.getAttributeFlagValue(entityItem1, "COFGRP");
/* 174 */               paramLXABRSTATUS.addDebug("LXLEADSUtil.searchForModel found HW " + entityItem1.getKey() + " cofcatflag:" + str + " cofgrp:" + str1 + " for mt:" + paramString1 + " model:" + paramString2);
/*     */ 
/*     */               
/* 177 */               if ("150".equals(str1)) {
/* 178 */                 if (entityItem != null) {
/* 179 */                   bool = false;
/*     */                   break;
/*     */                 } 
/* 182 */                 entityItem = entityItem1;
/*     */               } 
/* 184 */             } else if ("101".equals(str)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 191 */               String str1 = PokUtils.getAttributeFlagValue(entityItem1, "COFGRP");
/* 192 */               String str2 = PokUtils.getAttributeFlagValue(entityItem1, "COFSUBCAT");
/* 193 */               paramLXABRSTATUS.addDebug("LXLEADSUtil.searchForModel found SW " + entityItem1.getKey() + " cofgrp:" + str1 + " cofsubcatflag: " + str2);
/*     */               
/* 195 */               if ("150".equals(str1) && SW_COFSUBCAT_SET
/* 196 */                 .contains(str2)) {
/*     */                 
/* 198 */                 if (entityItem != null) {
/* 199 */                   bool = false;
/*     */                   break;
/*     */                 } 
/* 202 */                 entityItem = entityItem1;
/*     */               } 
/* 204 */             } else if ("102".equals(str)) {
/* 205 */               paramLXABRSTATUS.addDebug("LXLEADSUtil.searchForModel found SVC " + entityItem1.getKey() + " cofcatflag:" + str + " for mt:" + paramString1 + " model:" + paramString2);
/*     */               
/* 207 */               if (entityItem != null) {
/* 208 */                 bool = false;
/*     */                 break;
/*     */               } 
/* 211 */               entityItem = entityItem1;
/*     */             } else {
/* 213 */               paramLXABRSTATUS.addDebug("LXLEADSUtil.searchForModel found UNKNOWN " + entityItem1.getKey() + " cofcatflag:" + str + " for mt:" + paramString1 + " model:" + paramString2);
/*     */             } 
/*     */           } 
/*     */           
/* 217 */           if (!bool) {
/* 218 */             StringBuffer stringBuffer = new StringBuffer();
/*     */             
/* 220 */             stringBuffer.append(paramLXABRSTATUS.getResourceMsg("ERROR_MODEL_MORETHAN_1", new Object[] { paramString1 + ":" + paramString2 }));
/*     */             
/* 222 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 223 */               stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*     */             }
/* 225 */             paramLXABRSTATUS.addError(stringBuffer.toString());
/* 226 */             entityItem = null;
/*     */           } 
/*     */         } 
/* 229 */         if (entityItem != null) {
/*     */           
/* 231 */           EntityGroup entityGroup = new EntityGroup(null, paramLXABRSTATUS.getDatabase(), paramLXABRSTATUS.getProfile(), "MODEL", "Edit");
/* 232 */           entityItem = new EntityItem(entityGroup, paramLXABRSTATUS.getProfile(), paramLXABRSTATUS.getDatabase(), "MODEL", entityItem.getEntityID());
/* 233 */           entityGroup.putEntityItem(entityItem);
/*     */         } else {
/* 235 */           entityItem = arrayOfEntityItem[0];
/*     */         } 
/*     */       } 
/* 238 */       vector1.clear();
/* 239 */       vector2.clear();
/*     */     } 
/*     */     
/* 242 */     return entityItem;
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
/*     */   protected static EntityItem searchForWWSEO(LXABRSTATUS paramLXABRSTATUS, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 258 */     EntityItem entityItem = null;
/* 259 */     Vector<String> vector1 = new Vector(1);
/* 260 */     vector1.addElement("SEOID");
/* 261 */     Vector<String> vector2 = new Vector(1);
/* 262 */     vector2.addElement(paramString);
/*     */     
/* 264 */     EntityItem[] arrayOfEntityItem = null;
/*     */     try {
/* 266 */       StringBuffer stringBuffer = new StringBuffer();
/* 267 */       arrayOfEntityItem = ABRUtil.doSearch(paramLXABRSTATUS.getDatabase(), paramLXABRSTATUS.getProfile(), "LDSRDWWSEO", "WWSEO", false, vector1, vector2, stringBuffer);
/*     */       
/* 269 */       if (stringBuffer.length() > 0) {
/* 270 */         paramLXABRSTATUS.addDebug(stringBuffer.toString());
/*     */       }
/* 272 */     } catch (SBRException sBRException) {
/*     */       
/* 274 */       StringWriter stringWriter = new StringWriter();
/* 275 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 276 */       paramLXABRSTATUS.addDebug("searchForWWSEO SBRException: " + stringWriter.getBuffer().toString());
/*     */     } 
/* 278 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 279 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 280 */         paramLXABRSTATUS.addDebug("LXLEADSUtil.searchForWWSEO found " + arrayOfEntityItem[b].getKey());
/*     */       }
/* 282 */       if (arrayOfEntityItem.length > 1) {
/* 283 */         StringBuffer stringBuffer = new StringBuffer();
/* 284 */         stringBuffer.append("More than one WWSEO found for " + paramString);
/* 285 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 286 */           stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*     */         }
/* 288 */         paramLXABRSTATUS.addError(stringBuffer.toString());
/*     */       } 
/* 290 */       entityItem = arrayOfEntityItem[0];
/*     */     } 
/*     */     
/* 293 */     vector1.clear();
/* 294 */     vector2.clear();
/* 295 */     return entityItem;
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
/*     */   protected static EntityItem searchForLSEO(LXABRSTATUS paramLXABRSTATUS, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 312 */     EntityItem entityItem = null;
/* 313 */     Vector<String> vector1 = new Vector(1);
/* 314 */     vector1.addElement("SEOID");
/* 315 */     Vector<String> vector2 = new Vector(1);
/* 316 */     vector2.addElement(paramString);
/*     */     
/* 318 */     EntityItem[] arrayOfEntityItem = null;
/*     */     try {
/* 320 */       StringBuffer stringBuffer = new StringBuffer();
/* 321 */       arrayOfEntityItem = ABRUtil.doSearch(paramLXABRSTATUS.getDatabase(), paramLXABRSTATUS.getProfile(), "LDSRDLSEO", "LSEO", false, vector1, vector2, stringBuffer);
/*     */       
/* 323 */       if (stringBuffer.length() > 0) {
/* 324 */         paramLXABRSTATUS.addDebug(stringBuffer.toString());
/*     */       }
/* 326 */     } catch (SBRException sBRException) {
/*     */       
/* 328 */       StringWriter stringWriter = new StringWriter();
/* 329 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 330 */       paramLXABRSTATUS.addDebug("searchForLSEO SBRException: " + stringWriter.getBuffer().toString());
/*     */     } 
/* 332 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 333 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 334 */         paramLXABRSTATUS.addDebug("LXLEADSUtil.searchForLSEO found " + arrayOfEntityItem[b].getKey());
/*     */       }
/* 336 */       if (arrayOfEntityItem.length > 1) {
/* 337 */         StringBuffer stringBuffer = new StringBuffer();
/* 338 */         stringBuffer.append("More than one LSEO found for " + paramString);
/* 339 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 340 */           stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*     */         }
/* 342 */         paramLXABRSTATUS.addError(stringBuffer.toString());
/*     */       } 
/* 344 */       entityItem = arrayOfEntityItem[0];
/*     */     } 
/* 346 */     vector1.clear();
/* 347 */     vector2.clear();
/* 348 */     return entityItem;
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
/*     */   protected static EntityItem createWWSEO(LXABRSTATUS paramLXABRSTATUS, EntityItem paramEntityItem, String paramString1, String paramString2, Object paramObject) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException {
/* 385 */     EntityItem entityItem = null;
/*     */     
/* 387 */     Vector<String> vector = new Vector();
/* 388 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 389 */     vector.addElement("SEOID");
/* 390 */     hashtable.put("SEOID", paramString1);
/* 391 */     vector.addElement("COMNAME");
/* 392 */     hashtable.put("COMNAME", paramString1);
/* 393 */     vector.addElement("SEOTECHDESC");
/* 394 */     hashtable.put("SEOTECHDESC", paramString2);
/* 395 */     vector.addElement("PROJCDNAM");
/*     */     
/* 397 */     String str1 = "722";
/* 398 */     if (paramLXABRSTATUS.getPROJCDNAME() != null) {
/* 399 */       str1 = paramLXABRSTATUS.getPROJCDNAME();
/*     */     }
/* 401 */     hashtable.put("PROJCDNAM", str1);
/* 402 */     vector.addElement("SEOORDERCODE");
/* 403 */     hashtable.put("SEOORDERCODE", "10");
/* 404 */     vector.addElement("SPECBID");
/* 405 */     hashtable.put("SPECBID", "11458");
/* 406 */     vector.addElement("UNSPSCCD");
/* 407 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "UNSPSCCD");
/* 408 */     paramLXABRSTATUS.addDebug(paramEntityItem.getKey() + " has UNSPSCCD " + str2);
/* 409 */     if (str2 == null) {
/* 410 */       paramLXABRSTATUS.addDebug(paramEntityItem.getKey() + " did not have UNSPSCCD attr meta: " + paramEntityItem
/* 411 */           .getEntityGroup().getMetaAttribute("UNSPSCCD"));
/* 412 */       str2 = "43171806";
/*     */     } 
/* 414 */     hashtable.put("UNSPSCCD", str2);
/* 415 */     vector.addElement("RATECARDCD");
/* 416 */     String str3 = PokUtils.getAttributeFlagValue(paramEntityItem, "RATECARDCD");
/* 417 */     paramLXABRSTATUS.addDebug(paramEntityItem.getKey() + " has RATECARDCD " + str3);
/* 418 */     if (str3 == null) {
/* 419 */       paramLXABRSTATUS.addDebug(paramEntityItem.getKey() + " did not have RATECARDCD attr meta: " + paramEntityItem
/* 420 */           .getEntityGroup().getMetaAttribute("RATECARDCD"));
/* 421 */       str3 = "SSM-0080";
/*     */     } 
/* 423 */     hashtable.put("RATECARDCD", str3);
/*     */     
/* 425 */     vector.addElement("PDHDOMAIN");
/* 426 */     hashtable.put("PDHDOMAIN", paramObject);
/*     */     
/* 428 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 430 */       entityItem = ABRUtil.createEntity(paramLXABRSTATUS.getDatabase(), paramLXABRSTATUS.getProfile(), "LDCRWWSEO", paramEntityItem, "WWSEO", vector, hashtable, stringBuffer);
/*     */     }
/* 432 */     catch (EANBusinessRuleException eANBusinessRuleException) {
/* 433 */       throw eANBusinessRuleException;
/*     */     } finally {
/* 435 */       if (stringBuffer.length() > 0) {
/* 436 */         paramLXABRSTATUS.addDebug(stringBuffer.toString());
/*     */       }
/* 438 */       if (entityItem == null) {
/* 439 */         paramLXABRSTATUS.addError("ERROR: Can not create WWSEO entity for seoid: " + paramString1);
/*     */       }
/*     */ 
/*     */       
/* 443 */       vector.clear();
/* 444 */       hashtable.clear();
/*     */     } 
/*     */     
/* 447 */     return entityItem;
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
/*     */   protected static EntityItem createLSEO(LXABRSTATUS paramLXABRSTATUS, EntityItem paramEntityItem, String paramString1, String paramString2, Vector paramVector, Object paramObject1, Object paramObject2) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException {
/* 483 */     EntityItem entityItem = null;
/*     */     
/* 485 */     Vector<String> vector = new Vector();
/* 486 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 487 */     vector.addElement("SEOID");
/* 488 */     hashtable.put("SEOID", paramString1);
/* 489 */     vector.addElement("COMNAME");
/* 490 */     hashtable.put("COMNAME", paramString1);
/* 491 */     vector.addElement("LSEOPUBDATEMTRGT");
/* 492 */     hashtable.put("LSEOPUBDATEMTRGT", paramString2);
/* 493 */     vector.addElement("AUDIEN");
/* 494 */     hashtable.put("AUDIEN", paramObject2);
/* 495 */     vector.addElement("ACCTASGNGRP");
/* 496 */     hashtable.put("ACCTASGNGRP", "01");
/* 497 */     vector.addElement("COUNTRYLIST");
/* 498 */     hashtable.put("COUNTRYLIST", paramVector);
/*     */     
/* 500 */     vector.addElement("PDHDOMAIN");
/* 501 */     hashtable.put("PDHDOMAIN", paramObject1);
/*     */     
/* 503 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 505 */       entityItem = ABRUtil.createEntity(paramLXABRSTATUS.getDatabase(), paramLXABRSTATUS.getProfile(), "LDCRLSEO", paramEntityItem, "LSEO", vector, hashtable, stringBuffer);
/*     */     }
/* 507 */     catch (EANBusinessRuleException eANBusinessRuleException) {
/* 508 */       throw eANBusinessRuleException;
/*     */     } finally {
/* 510 */       if (stringBuffer.length() > 0) {
/* 511 */         paramLXABRSTATUS.addDebug(stringBuffer.toString());
/*     */       }
/* 513 */       if (entityItem == null) {
/* 514 */         paramLXABRSTATUS.addError("ERROR: Can not create LSEO entity for seoid: " + paramString1);
/*     */       }
/*     */ 
/*     */       
/* 518 */       vector.clear();
/* 519 */       hashtable.clear();
/*     */     } 
/*     */     
/* 522 */     return entityItem;
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
/*     */   protected static void createFeatureRefs(LXABRSTATUS paramLXABRSTATUS, EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector paramVector, Hashtable paramHashtable) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/* 560 */     String str1 = "";
/* 561 */     String str2 = "";
/* 562 */     String str3 = "";
/* 563 */     String str4 = PokUtils.getAttributeFlagValue(paramEntityItem2, "COFCAT");
/*     */     
/* 565 */     if ("100".equals(str4)) {
/* 566 */       str1 = "LDLINKPRODSTWWSEO";
/* 567 */       str2 = "PRODSTRUCT";
/* 568 */       str3 = "CONFQTY";
/* 569 */     } else if ("101".equals(str4)) {
/* 570 */       str1 = "LDLINKSWPRODWWSEO";
/* 571 */       str2 = "SWPRODSTRUCT";
/* 572 */       str3 = "SWCONFQTY";
/* 573 */     } else if ("102".equals(str4)) {
/* 574 */       paramLXABRSTATUS.addDebug("LXLEADSUtil.createFeatureRefs Model is Service, no references created");
/*     */       
/*     */       return;
/*     */     } 
/* 578 */     LinkActionItem linkActionItem = new LinkActionItem(null, paramLXABRSTATUS.getDatabase(), paramLXABRSTATUS.getProfile(), str1);
/* 579 */     EntityItem[] arrayOfEntityItem1 = { paramEntityItem1 };
/* 580 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[paramVector.size()];
/*     */ 
/*     */     
/* 583 */     paramVector.copyInto((Object[])arrayOfEntityItem2);
/*     */ 
/*     */     
/* 586 */     linkActionItem.setParentEntityItems(arrayOfEntityItem1);
/* 587 */     linkActionItem.setChildEntityItems(arrayOfEntityItem2);
/* 588 */     paramLXABRSTATUS.getDatabase().executeAction(paramLXABRSTATUS.getProfile(), linkActionItem);
/*     */ 
/*     */ 
/*     */     
/* 592 */     Profile profile = paramLXABRSTATUS.getProfile().getNewInstance(paramLXABRSTATUS.getDatabase());
/* 593 */     String str5 = paramLXABRSTATUS.getDatabase().getDates().getNow();
/* 594 */     profile.setValOnEffOn(str5, str5);
/*     */     
/* 596 */     EntityList entityList = paramLXABRSTATUS.getDatabase().getEntityList(profile, new ExtractActionItem(null, paramLXABRSTATUS
/* 597 */           .getDatabase(), profile, "EXRPT3WWSEO3"), arrayOfEntityItem1);
/*     */ 
/*     */     
/* 600 */     paramLXABRSTATUS.addDebug("LXLEADSUtil.createFeatureRefs list using VE EXRPT3WWSEO3 after linkaction: " + str1 + "\n" + 
/* 601 */         PokUtils.outputList(entityList));
/* 602 */     EntityGroup entityGroup = entityList.getEntityGroup(str2);
/*     */     
/* 604 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 605 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*     */       
/* 607 */       String str = (String)paramHashtable.get(entityItem1.getKey());
/* 608 */       EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 609 */       paramLXABRSTATUS.addDebug("LXLEADSUtil " + entityItem1.getKey() + " use qty: " + str + " on " + entityItem2.getKey());
/* 610 */       if (str != null && !str.equals("1")) {
/* 611 */         StringBuffer stringBuffer = new StringBuffer();
/*     */         
/* 613 */         ABRUtil.setText(entityItem2, str3, str, stringBuffer);
/* 614 */         if (stringBuffer.length() > 0) {
/* 615 */           paramLXABRSTATUS.addDebug(stringBuffer.toString());
/*     */         }
/*     */         
/* 618 */         entityItem2.commit(paramLXABRSTATUS.getDatabase(), null);
/*     */       } 
/*     */     } 
/* 621 */     entityList.dereference();
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
/*     */   private static String getGenAreaCodeForCtry(Database paramDatabase, Profile paramProfile, String paramString) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 637 */     if (SG_GENAREACODE_TBL == null) {
/* 638 */       SG_GENAREACODE_TBL = new Hashtable<>();
/*     */       
/* 640 */       EntityGroup entityGroup = new EntityGroup(null, paramDatabase, paramProfile, "GENERALAREA", "Edit", false);
/* 641 */       EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)entityGroup.getMetaAttribute("GENAREACODE");
/* 642 */       if (eANMetaFlagAttribute != null) {
/* 643 */         for (byte b = 0; b < eANMetaFlagAttribute.getMetaFlagCount(); b++) {
/*     */           
/* 645 */           MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(b);
/* 646 */           if (metaFlag.isExpired()) {
/* 647 */             paramDatabase.debug(4, "LXLEADSUtil.getGenAreaCodeForCtry skipping expired flag: " + metaFlag + "[" + metaFlag
/* 648 */                 .getFlagCode() + "] for GENERALAREA.GENAREACODE");
/*     */           } else {
/*     */             
/* 651 */             SG_GENAREACODE_TBL.put(metaFlag.toString(), metaFlag.getFlagCode());
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 656 */     return (String)SG_GENAREACODE_TBL.get(paramString);
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
/*     */   public static EntityItem searchForGENERALAREA(Database paramDatabase, Profile paramProfile, String paramString, StringBuffer paramStringBuffer) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 677 */     EntityItem entityItem = null;
/*     */ 
/*     */     
/* 680 */     String str = getGenAreaCodeForCtry(paramDatabase, paramProfile, paramString);
/* 681 */     paramStringBuffer.append("LXLEADSUtil.searchForGENERALAREA country: " + paramString + " genAreaCodeFlg: " + str + "\n");
/* 682 */     paramDatabase.debug(4, "LXLEADSUtil.searchForGENERALAREA country: " + paramString + " genAreaCodeFlg: " + str);
/* 683 */     if (str != null) {
/* 684 */       Vector<String> vector1 = new Vector(1);
/* 685 */       vector1.addElement("GENAREACODE");
/* 686 */       Vector<String> vector2 = new Vector(1);
/* 687 */       vector2.addElement(str);
/* 688 */       EntityItem[] arrayOfEntityItem = null;
/*     */       try {
/* 690 */         arrayOfEntityItem = ABRUtil.doSearch(paramDatabase, paramProfile, "LDSRDGENAREA", "GENERALAREA", false, vector1, vector2, paramStringBuffer);
/*     */       }
/* 692 */       catch (SBRException sBRException) {
/*     */         
/* 694 */         StringWriter stringWriter = new StringWriter();
/* 695 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 696 */         paramStringBuffer.append("searchForGENERALAREA SBRException: " + stringWriter.getBuffer().toString() + "\n");
/*     */       } 
/* 698 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 699 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 700 */           paramStringBuffer.append("LXLEADSUtil.searchForGENERALAREA found " + arrayOfEntityItem[b].getKey() + "\n");
/*     */         }
/* 702 */         entityItem = arrayOfEntityItem[0];
/*     */       } 
/* 704 */       vector1.clear();
/* 705 */       vector2.clear();
/*     */     } else {
/* 707 */       paramStringBuffer.append("LXLEADSUtil.searchForGENERALAREA GENAREACODE table: " + SG_GENAREACODE_TBL + "\n");
/*     */     } 
/*     */     
/* 710 */     return entityItem;
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
/*     */   protected static Object deriveAudien(String paramString) {
/* 733 */     Object object = AUDIEN_TBL.get(paramString);
/* 734 */     if (object == null) {
/* 735 */       object = "10062";
/*     */     }
/* 737 */     return object;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\LXLEADSUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */