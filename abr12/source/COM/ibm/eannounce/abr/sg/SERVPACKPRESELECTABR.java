/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SERVPACKPRESELECTABR
/*     */   extends SVCPACPRESELBase
/*     */ {
/* 102 */   private PDGUtility m_utility = new PDGUtility();
/* 103 */   private EntityItem curCatLgSvcItem = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MEM_LIMIT = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeThis() throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException {
/* 115 */     EntityItem[] arrayOfEntityItem = searchForCATLGSVCPACPRESEL();
/*     */     
/* 117 */     if (getReturnCode() == 0) {
/*     */ 
/*     */       
/* 120 */       Vector<EntityItem> vector1 = new Vector();
/* 121 */       Vector<EntityItem> vector2 = new Vector();
/*     */       
/* 123 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 124 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 125 */         this.curCatLgSvcItem = arrayOfEntityItem[b];
/* 126 */         String str1 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATSEOID", "", "", false);
/* 127 */         String str2 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATMACHTYPE", "", "", false);
/* 128 */         String str3 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "SVCPACTYPE", "", "", false);
/* 129 */         String str4 = getAttributeFlagEnabledValue(this.curCatLgSvcItem, "PRESELSEOTYPE");
/*     */         
/* 131 */         addDebug("checking " + this.curCatLgSvcItem.getKey() + " " + "CATSEOID" + ": " + str1 + " " + "CATMACHTYPE" + ": " + str2 + " " + "SVCPACTYPE" + ": " + str3 + " " + "PRESELSEOTYPE" + ":" + str4);
/*     */ 
/*     */ 
/*     */         
/* 135 */         if (str1.length() == 0) {
/*     */           
/* 137 */           this.args[0] = getLD_NDN(this.curCatLgSvcItem);
/* 138 */           this.args[1] = PokUtils.getAttributeDescription(this.curCatLgSvcItem.getEntityGroup(), "CATSEOID", "CATSEOID");
/* 139 */           addError("BLANK_ERR", this.args);
/*     */         
/*     */         }
/* 142 */         else if (str3.length() == 0) {
/*     */           
/* 144 */           this.args[0] = getLD_NDN(this.curCatLgSvcItem);
/* 145 */           this.args[1] = PokUtils.getAttributeDescription(this.curCatLgSvcItem.getEntityGroup(), "SVCPACTYPE", "SVCPACTYPE");
/* 146 */           addError("BLANK_ERR", this.args);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 151 */           if (str4 == null || str4.length() == 0) {
/* 152 */             str4 = "PRE1";
/*     */           }
/*     */           
/* 155 */           if ("PRE1".equals(str4)) {
/*     */             
/* 157 */             String str = findSVCPACTYPEFlag(hashtable, str3, "COFSUBCAT");
/* 158 */             if (str == null) {
/*     */               
/* 160 */               this.args[0] = "COFSUBCAT";
/* 161 */               this.args[1] = str3;
/* 162 */               addError("FLAG_ERR", this.args);
/*     */             }
/*     */             else {
/*     */               
/* 166 */               vector1.add(this.curCatLgSvcItem);
/*     */             } 
/*     */           } else {
/* 169 */             findSVCPACTYPEFlag(hashtable, str3, "SVCPACBNDLTYPE");
/* 170 */             vector2.add(this.curCatLgSvcItem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       if (vector1.size() > 0) {
/* 176 */         doLSEOSvcChunks(vector1, hashtable);
/* 177 */         vector1.clear();
/*     */         
/* 179 */         addOutput("");
/*     */       } 
/*     */ 
/*     */       
/* 183 */       if (vector2.size() > 0) {
/* 184 */         doLSEOBDLSvcChunks(vector2, hashtable);
/* 185 */         vector2.clear();
/*     */       } 
/*     */       
/* 188 */       hashtable.clear();
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
/*     */   private void doLSEOBDLSvcChunks(Vector<EntityItem> paramVector, Hashtable paramHashtable) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 201 */     addHeading(2, "LSEOBUNDLE ServicePac Checks");
/* 202 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 203 */     Vector<EntityItem> vector = new Vector();
/* 204 */     if (paramVector.size() > 10) {
/* 205 */       int i = paramVector.size() / 10;
/* 206 */       byte b1 = 0;
/* 207 */       for (byte b2 = 0; b2 <= i; b2++) {
/* 208 */         hashtable.clear();
/* 209 */         for (byte b = 0; b < 10 && 
/* 210 */           b1 != paramVector.size(); b++) {
/*     */ 
/*     */ 
/*     */           
/* 214 */           this.curCatLgSvcItem = paramVector.elementAt(b1++);
/* 215 */           String str = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATSEOID", "", "", false);
/*     */           
/* 217 */           addDebug("checking " + this.curCatLgSvcItem.getKey() + " " + "CATSEOID" + ": " + str);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 222 */           Vector vector1 = findLSEOBDLForCheck1(str);
/* 223 */           if (vector1.size() > 0) {
/*     */             
/* 225 */             hashtable.put(this.curCatLgSvcItem.getKey(), vector1);
/* 226 */             vector.add(this.curCatLgSvcItem);
/*     */           } 
/*     */         } 
/*     */         
/* 230 */         if (hashtable.size() > 0) {
/* 231 */           doLSEOBDLSvcChecks(hashtable, vector, paramHashtable);
/* 232 */           vector.clear();
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 238 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 239 */         this.curCatLgSvcItem = paramVector.elementAt(b);
/* 240 */         String str = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATSEOID", "", "", false);
/* 241 */         addDebug("checking " + this.curCatLgSvcItem.getKey() + " " + "CATSEOID" + ": " + str);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 246 */         Vector vector1 = findLSEOBDLForCheck1(str);
/* 247 */         if (vector1.size() > 0) {
/*     */           
/* 249 */           hashtable.put(this.curCatLgSvcItem.getKey(), vector1);
/* 250 */           vector.add(this.curCatLgSvcItem);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 255 */       if (hashtable.size() > 0) {
/* 256 */         doLSEOBDLSvcChecks(hashtable, vector, paramHashtable);
/* 257 */         vector.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     hashtable.clear();
/* 262 */     hashtable = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doLSEOSvcChunks(Vector<EntityItem> paramVector, Hashtable paramHashtable) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 273 */     addHeading(2, "LSEO ServicePac Checks");
/* 274 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 275 */     Vector<EntityItem> vector = new Vector();
/* 276 */     if (paramVector.size() > 10) {
/* 277 */       int i = paramVector.size() / 10;
/* 278 */       byte b1 = 0;
/* 279 */       for (byte b2 = 0; b2 <= i; b2++) {
/* 280 */         hashtable.clear();
/* 281 */         for (byte b = 0; b < 10 && 
/* 282 */           b1 != paramVector.size(); b++) {
/*     */ 
/*     */ 
/*     */           
/* 286 */           this.curCatLgSvcItem = paramVector.elementAt(b1++);
/* 287 */           String str = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATSEOID", "", "", false);
/*     */           
/* 289 */           addDebug("checking " + this.curCatLgSvcItem.getKey() + " " + "CATSEOID" + ": " + str);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 294 */           Vector vector1 = findLSEOForCheck1(str);
/* 295 */           if (vector1.size() > 0) {
/*     */             
/* 297 */             hashtable.put(this.curCatLgSvcItem.getKey(), vector1);
/* 298 */             vector.add(this.curCatLgSvcItem);
/*     */           } 
/*     */         } 
/*     */         
/* 302 */         if (hashtable.size() > 0) {
/* 303 */           doLSEOSvcChecks(hashtable, vector, paramHashtable);
/* 304 */           vector.clear();
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 310 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 311 */         this.curCatLgSvcItem = paramVector.elementAt(b);
/* 312 */         String str = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATSEOID", "", "", false);
/* 313 */         addDebug("checking " + this.curCatLgSvcItem.getKey() + " " + "CATSEOID" + ": " + str);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 318 */         Vector vector1 = findLSEOForCheck1(str);
/* 319 */         if (vector1.size() > 0) {
/*     */           
/* 321 */           hashtable.put(this.curCatLgSvcItem.getKey(), vector1);
/* 322 */           vector.add(this.curCatLgSvcItem);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 327 */       if (hashtable.size() > 0) {
/* 328 */         doLSEOSvcChecks(hashtable, vector, paramHashtable);
/* 329 */         vector.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 333 */     hashtable.clear();
/* 334 */     hashtable = null;
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
/*     */   private void doLSEOSvcChecks(Hashtable paramHashtable1, Vector<EntityItem> paramVector, Hashtable paramHashtable2) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 351 */     if (paramHashtable1.size() > 0) {
/*     */       
/* 353 */       ExtractActionItem extractActionItem1 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTSERVPACKPDG1");
/*     */       
/* 355 */       Vector vector = new Vector();
/* 356 */       for (byte b1 = 0; b1 < paramVector.size(); b1++) {
/* 357 */         this.curCatLgSvcItem = paramVector.elementAt(b1);
/* 358 */         Vector vector1 = (Vector)paramHashtable1.get(this.curCatLgSvcItem.getKey());
/* 359 */         if (vector1 != null)
/*     */         {
/*     */ 
/*     */           
/* 363 */           vector.addAll(vector1); } 
/*     */       } 
/* 365 */       EntityItem[] arrayOfEntityItem = null;
/* 366 */       if (vector.size() > 0) {
/* 367 */         arrayOfEntityItem = new EntityItem[vector.size()];
/* 368 */         vector.copyInto((Object[])arrayOfEntityItem);
/* 369 */         vector.clear();
/*     */       } 
/* 371 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, extractActionItem1, arrayOfEntityItem);
/* 372 */       addDebug("Extract for LSEOs EXTSERVPACKPDG1 " + PokUtils.outputList(entityList));
/*     */       
/* 374 */       ExtractActionItem extractActionItem2 = new ExtractActionItem(null, this.m_db, this.m_prof, "SERVPACLSEO");
/*     */       
/* 376 */       for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/* 377 */         this.curCatLgSvcItem = paramVector.elementAt(b2);
/* 378 */         Vector vector1 = (Vector)paramHashtable1.get(this.curCatLgSvcItem.getKey());
/* 379 */         if (vector1 == null) {
/*     */           
/* 381 */           addDebug("skipping further processing of " + this.curCatLgSvcItem.getKey());
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 386 */           boolean bool = doLSEOChecks(paramHashtable2, vector1, entityList, extractActionItem2);
/* 387 */           if (!bool) {
/*     */             
/* 389 */             this.args[0] = getLD_NDN(this.curCatLgSvcItem);
/* 390 */             addMessage("", "NO_ERRORS", this.args);
/*     */           } 
/*     */           
/* 393 */           vector1.clear();
/* 394 */           this.curCatLgSvcItem = null;
/*     */         } 
/*     */       } 
/* 397 */       entityList.dereference();
/* 398 */       paramHashtable1.clear();
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
/*     */   private void doLSEOBDLSvcChecks(Hashtable paramHashtable1, Vector<EntityItem> paramVector, Hashtable paramHashtable2) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 415 */     if (paramHashtable1.size() > 0) {
/* 416 */       ExtractActionItem extractActionItem1 = new ExtractActionItem(null, this.m_db, this.m_prof, "SERVPACLSEOBDL");
/* 417 */       ExtractActionItem extractActionItem2 = new ExtractActionItem(null, this.m_db, this.m_prof, "dummy");
/*     */ 
/*     */       
/* 420 */       Vector vector = new Vector();
/* 421 */       for (byte b1 = 0; b1 < paramVector.size(); b1++) {
/* 422 */         this.curCatLgSvcItem = paramVector.elementAt(b1);
/* 423 */         Vector vector1 = (Vector)paramHashtable1.get(this.curCatLgSvcItem.getKey());
/* 424 */         if (vector1 != null)
/*     */         {
/*     */ 
/*     */           
/* 428 */           vector.addAll(vector1); } 
/*     */       } 
/* 430 */       EntityItem[] arrayOfEntityItem = null;
/* 431 */       if (vector.size() > 0) {
/* 432 */         arrayOfEntityItem = new EntityItem[vector.size()];
/* 433 */         vector.copyInto((Object[])arrayOfEntityItem);
/* 434 */         vector.clear();
/*     */       } 
/* 436 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, extractActionItem2, arrayOfEntityItem);
/* 437 */       addDebug("Extract for LSEOBUNDLEs " + PokUtils.outputList(entityList));
/*     */       
/* 439 */       for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/* 440 */         this.curCatLgSvcItem = paramVector.elementAt(b2);
/* 441 */         Vector vector1 = (Vector)paramHashtable1.get(this.curCatLgSvcItem.getKey());
/* 442 */         if (vector1 == null) {
/*     */           
/* 444 */           addDebug("skipping further processing of " + this.curCatLgSvcItem.getKey());
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 449 */           boolean bool = doLSEOBDLChecks(paramHashtable2, vector1, entityList, extractActionItem1);
/* 450 */           if (!bool) {
/*     */             
/* 452 */             this.args[0] = getLD_NDN(this.curCatLgSvcItem);
/* 453 */             addMessage("", "NO_ERRORS", this.args);
/*     */           } 
/*     */           
/* 456 */           vector1.clear();
/* 457 */           this.curCatLgSvcItem = null;
/*     */         } 
/*     */       } 
/* 460 */       entityList.dereference();
/* 461 */       paramHashtable1.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void verifyRequest() {
/* 469 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */     
/* 471 */     this.offCountryFlag = PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY");
/* 472 */     this.offCountry = PokUtils.getAttributeValue(entityItem, "OFFCOUNTRY", "", "<em>** Not Populated **</em>", false);
/*     */     
/* 474 */     addDebug("verifyRequest: " + entityItem.getKey() + " offCountryFlag " + this.offCountryFlag + " offCountry " + this.offCountry);
/*     */     
/* 476 */     if (this.offCountryFlag == null) {
/*     */       
/* 478 */       this.args[0] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "OFFCOUNTRY", "OFFCOUNTRY");
/* 479 */       addError("MISSING_ERR", this.args);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doLSEOChecks(Hashtable paramHashtable, Vector<EntityItem> paramVector, EntityList paramEntityList, ExtractActionItem paramExtractActionItem) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 510 */     boolean bool = false;
/*     */     
/* 512 */     String str1 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATSEOID", "", "", false);
/* 513 */     String str2 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATMACHTYPE", "", "", false);
/* 514 */     String str3 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "SVCPACTYPE", "", "", false);
/*     */ 
/*     */     
/* 517 */     String str4 = (String)paramHashtable.get("COFSUBCAT" + str3);
/*     */     
/* 519 */     addDebug("doLSEOChecks " + this.curCatLgSvcItem.getKey() + " " + "CATSEOID" + ": " + str1 + " " + "CATMACHTYPE" + ": " + str2 + " " + "SVCPACTYPE" + ": " + str3 + " modelFlag " + str4);
/*     */ 
/*     */ 
/*     */     
/* 523 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 524 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 525 */       EntityItem entityItem = paramVector.elementAt(b);
/* 526 */       entityItem = entityGroup.getEntityItem(entityItem.getKey());
/* 527 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "WWSEO");
/* 528 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(vector, "MODELWWSEO", "MODEL");
/* 529 */       addDebug("doLSEOChecks checking " + entityItem.getKey() + " wwseovct " + vector.size() + " mdlvct " + vector1.size());
/*     */ 
/*     */ 
/*     */       
/* 533 */       boolean bool1 = false;
/* 534 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 535 */         EntityItem entityItem1 = vector1.elementAt(b1);
/* 536 */         String str5 = PokUtils.getAttributeFlagValue(entityItem1, "COFCAT");
/* 537 */         String str6 = PokUtils.getAttributeFlagValue(entityItem1, "COFSUBCAT");
/* 538 */         addDebug("doLSEOChecks checking " + entityItem1.getKey() + " COFCAT:" + str5 + " COFSUBCAT:" + str6);
/*     */         
/* 540 */         if ("102".equals(str5) && str4
/* 541 */           .equals(str6)) {
/* 542 */           bool1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 547 */       if (!bool1) {
/* 548 */         bool = true;
/*     */         
/* 550 */         this.args[0] = getLD_NDN(entityItem);
/* 551 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityList.getEntityGroup("MODEL"), "COFCAT", "COFCAT");
/* 552 */         this.args[2] = PokUtils.getAttributeDescription(paramEntityList.getEntityGroup("MODEL"), "COFSUBCAT", "COFSUBCAT");
/* 553 */         this.args[3] = str3;
/* 554 */         String str = "Not found";
/* 555 */         if (vector1.size() > 0) {
/* 556 */           str = getLD_NDN(vector1.elementAt(0));
/*     */         }
/* 558 */         this.args[4] = str;
/* 559 */         addError("LSEO_MDL_ERR", this.args);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 566 */         EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/* 567 */         vector.copyInto((Object[])arrayOfEntityItem);
/*     */         
/* 569 */         boolean bool2 = verifySEOtechCompat(arrayOfEntityItem, str2, paramExtractActionItem);
/* 570 */         if (!bool2) {
/* 571 */           bool = true;
/*     */           
/* 573 */           this.args[0] = getLD_NDN(entityItem);
/* 574 */           this.args[1] = str2;
/* 575 */           addError("MACHTYPE_MSG", this.args);
/*     */         } 
/*     */         
/* 578 */         vector.clear();
/* 579 */         vector1.clear();
/*     */       } 
/*     */     } 
/* 582 */     return bool;
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
/*     */   private boolean doLSEOBDLChecks(Hashtable paramHashtable, Vector<EntityItem> paramVector, EntityList paramEntityList, ExtractActionItem paramExtractActionItem) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 612 */     boolean bool = false;
/*     */     
/* 614 */     String str1 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATSEOID", "", "", false);
/* 615 */     String str2 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "CATMACHTYPE", "", "", false);
/* 616 */     String str3 = PokUtils.getAttributeValue(this.curCatLgSvcItem, "SVCPACTYPE", "", "", false);
/*     */ 
/*     */     
/* 619 */     String str4 = (String)paramHashtable.get("SVCPACBNDLTYPE" + str3);
/*     */     
/* 621 */     addDebug("doLSEOBDLChecks " + this.curCatLgSvcItem.getKey() + " " + "CATSEOID" + ": " + str1 + " " + "CATMACHTYPE" + ": " + str2 + " " + "SVCPACTYPE" + ": " + str3 + " svcpacbdltypeFlag " + str4);
/*     */ 
/*     */ 
/*     */     
/* 625 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 626 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 627 */       EntityItem entityItem = paramVector.elementAt(b);
/* 628 */       entityItem = entityGroup.getEntityItem(entityItem.getKey());
/*     */       
/* 630 */       String str5 = getAttributeFlagEnabledValue(entityItem, "BUNDLETYPE");
/* 631 */       String str6 = getAttributeFlagEnabledValue(entityItem, "SVCPACBNDLTYPE");
/* 632 */       addDebug("doLSEOBDLChecks " + entityItem.getKey() + " bdltype " + str5 + " svcpacbdltype " + str6);
/*     */       
/* 634 */       if (str4 == null) {
/* 635 */         bool = true;
/*     */         
/* 637 */         this.args[0] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "SVCPACBNDLTYPE", "SVCPACBNDLTYPE");
/* 638 */         this.args[1] = str3;
/* 639 */         addError("FLAG_ERR", this.args);
/* 640 */         str4 = "";
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 645 */       if (!"102".equals(str5) || 
/* 646 */         !str4.equals(str6)) {
/* 647 */         bool = true;
/*     */         
/* 649 */         this.args[0] = getLD_NDN(entityItem);
/* 650 */         this.args[1] = getLD_Value(entityItem, "BUNDLETYPE");
/* 651 */         this.args[2] = getLD_Value(entityItem, "SVCPACBNDLTYPE");
/* 652 */         addError("SVCPAC_ERR", this.args);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 658 */         boolean bool1 = verifySEOtechCompat(new EntityItem[] { new EntityItem(null, this.m_prof, entityItem
/* 659 */                 .getEntityType(), entityItem
/* 660 */                 .getEntityID()) }str2, paramExtractActionItem);
/* 661 */         if (!bool1) {
/* 662 */           bool = true;
/*     */           
/* 664 */           this.args[0] = getLD_NDN(entityItem);
/* 665 */           this.args[1] = str2;
/* 666 */           addError("MACHTYPE_MSG", this.args);
/*     */         } 
/*     */       } 
/*     */     } 
/* 670 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void listAllCatLgSvcpacAttr() {
/* 676 */     if (this.curCatLgSvcItem != null) {
/*     */       
/* 678 */       String str = "";
/*     */       try {
/* 680 */         str = getLD_NDN(this.curCatLgSvcItem);
/* 681 */       } catch (Exception exception) {}
/* 682 */       StringBuffer stringBuffer = new StringBuffer("<table width='100%'><caption>" + str + "</caption>");
/* 683 */       stringBuffer.append("<tr " + PokUtils.getColumnHeaderRowCSS() + "><th>Attribute</th><th>Value</th></tr>" + NEWLINE);
/* 684 */       byte b1 = 0;
/* 685 */       for (byte b2 = 0; b2 < this.curCatLgSvcItem.getAttributeCount(); b2++) {
/* 686 */         EANAttribute eANAttribute = this.curCatLgSvcItem.getAttribute(b2);
/* 687 */         String str1 = eANAttribute.getAttributeCode();
/* 688 */         String str2 = PokUtils.getAttributeDescription(this.curCatLgSvcItem.getEntityGroup(), str1, str1);
/* 689 */         String str3 = PokUtils.getAttributeValue(this.curCatLgSvcItem, str1, ", ", "<em>** Not Populated **</em>", true);
/* 690 */         stringBuffer.append("<tr class=\"" + ((b1++ % 2 != 0) ? "even" : "odd") + "\">");
/* 691 */         stringBuffer.append("<td>" + str2 + "</td><td>" + str3 + "</td>");
/* 692 */         stringBuffer.append("</tr>" + NEWLINE);
/*     */       } 
/* 694 */       stringBuffer.append("</table>");
/* 695 */       addOutput(stringBuffer.toString());
/*     */       
/* 697 */       this.curCatLgSvcItem = null;
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
/*     */   private String findSVCPACTYPEFlag(Hashtable<String, String> paramHashtable, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 715 */     String str = (String)paramHashtable.get(paramString2 + paramString1);
/* 716 */     if (str == null) {
/*     */       
/* 718 */       String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(this.m_db, this.m_prof, paramString2, paramString1);
/* 719 */       if (arrayOfString != null && arrayOfString.length > 0) {
/* 720 */         str = arrayOfString[0];
/* 721 */         paramHashtable.put(paramString2 + paramString1, str);
/* 722 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 723 */           addDebug("findSVCPACTYPEFlag  " + paramString2 + " exact match " + paramString1 + " found " + arrayOfString[b]);
/*     */         }
/*     */       } else {
/*     */         
/* 727 */         arrayOfString = this.m_utility.getFlagCodeForLikedDesc(this.m_db, this.m_prof, paramString2, paramString1);
/* 728 */         if (arrayOfString != null && arrayOfString.length > 0) {
/* 729 */           str = arrayOfString[0];
/* 730 */           paramHashtable.put(paramString2 + paramString1, str);
/* 731 */           for (byte b = 0; b < arrayOfString.length; b++) {
/* 732 */             addDebug("findSVCPACTYPEFlag " + paramString2 + " like match " + paramString1 + " found " + arrayOfString[b]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 737 */     return str;
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
/*     */   private EntityItem[] searchForCATLGSVCPACPRESEL() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 755 */     addDebug("searchForCATLGSVCPACPRESEL  OFFCOUNTRY " + this.offCountryFlag);
/* 756 */     EntityItem[] arrayOfEntityItem = null;
/* 757 */     Vector<String> vector1 = new Vector(2);
/* 758 */     vector1.addElement("PRESELINDC");
/* 759 */     vector1.addElement("OFFCOUNTRY");
/*     */     
/* 761 */     Vector<String> vector2 = new Vector(2);
/* 762 */     vector2.addElement("Yes");
/* 763 */     vector2.addElement(this.offCountryFlag);
/*     */     
/*     */     try {
/* 766 */       StringBuffer stringBuffer = new StringBuffer();
/* 767 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, "SRDCATLGSVCPACPRESEL1", "CATLGSVCPACPRESEL", false, vector1, vector2, stringBuffer);
/*     */       
/* 769 */       if (stringBuffer.length() > 0) {
/* 770 */         addDebug(stringBuffer.toString());
/*     */       }
/* 772 */     } catch (SBRException sBRException) {
/*     */       
/* 774 */       StringWriter stringWriter = new StringWriter();
/* 775 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 776 */       addDebug("searchForCATLGSVCPACPRESEL SBRException: " + stringWriter.getBuffer().toString());
/*     */     } 
/* 778 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 779 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 780 */         addDebug("searchForCATLGSVCPACPRESEL found " + arrayOfEntityItem[b].getKey());
/*     */       }
/*     */     } else {
/* 783 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 785 */       this.args[0] = getLD_Value(entityItem, "OFFCOUNTRY");
/* 786 */       addError("CATLGSVCPACPRESEL_ERR", this.args);
/*     */     } 
/*     */     
/* 789 */     vector1.clear();
/* 790 */     vector2.clear();
/* 791 */     return arrayOfEntityItem;
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
/* 804 */     listAllCatLgSvcpacAttr();
/*     */     
/* 806 */     super.addError(paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dereference() {
/* 813 */     this.m_utility.dereference();
/* 814 */     this.m_utility = null;
/*     */     
/* 816 */     this.curCatLgSvcItem = null;
/*     */     
/* 818 */     super.dereference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 827 */     return "Service Pack Preselect ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 837 */     return "1.20";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 847 */     return "SERVPACKPRESELECTABR.java,v 1.18 2010/02/08 14:51:46 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 857 */     return "1.20";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SERVPACKPRESELECTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */