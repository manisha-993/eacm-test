/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANTextAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LSEOABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/* 142 */   private Object[] args = new Object[5];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/* 148 */     return true;
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
/*     */   protected void doAlreadyFinalProcessing() {
/* 167 */     doFinalProcessing();
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 196 */     checkAssortModule();
/* 197 */     doFinalProcessing();
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
/*     */   private void doFinalProcessing() {
/* 222 */     setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", "0020");
/*     */     
/* 224 */     String str = "";
/* 225 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEO");
/*     */     
/* 227 */     if (entityGroup.getEntityItemCount() > 0) {
/* 228 */       EntityItem entityItem = entityGroup.getEntityItem(0);
/* 229 */       str = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/* 230 */       addDebug(entityItem.getKey() + " SPECBID: " + str);
/*     */     } 
/*     */     
/* 233 */     if ("11457".equals(str)) {
/* 234 */       entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT"); byte b1;
/* 235 */       for (b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 236 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 237 */         String str1 = getAttributeFlagEnabledValue(entityItem, "ANNSTATUS");
/* 238 */         String str2 = getAttributeFlagEnabledValue(entityItem, "ANNTYPE");
/* 239 */         addDebug(entityItem.getKey() + " status " + str1 + " type " + str2);
/* 240 */         if (str1 == null || str1.length() == 0) {
/* 241 */           str1 = "0020";
/*     */         }
/* 243 */         if ("0020".equals(str1) && "19".equals(str2)) {
/* 244 */           addDebug(entityItem.getKey() + " is Final and New");
/* 245 */           setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020", entityItem);
/*     */         } 
/*     */       } 
/*     */       
/* 249 */       entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 250 */       for (b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 251 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 252 */         String str1 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 253 */         addDebug(entityItem.getKey() + " type " + str1);
/*     */         
/* 255 */         if ("146".equals(str1) || "149".equals(str1)) {
/* 256 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 257 */           for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 258 */             EntityItem entityItem1 = vector.elementAt(b2);
/* 259 */             String str2 = getAttributeFlagEnabledValue(entityItem1, "ANNSTATUS");
/* 260 */             String str3 = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/* 261 */             addDebug(entityItem1.getKey() + " status " + str2 + " type " + str3);
/* 262 */             if (str2 == null || str2.length() == 0) {
/* 263 */               str2 = "0020";
/*     */             }
/*     */             
/* 266 */             if ("0020".equals(str2)) {
/* 267 */               if ("146".equals(str1) && "19".equals(str3)) {
/* 268 */                 addDebug(entityItem1.getKey() + " is Final and New");
/*     */ 
/*     */                 
/* 271 */                 setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem1);
/*     */               } 
/* 273 */               if ("149".equals(str1) && "14".equals(str3)) {
/* 274 */                 addDebug(entityItem1.getKey() + " is Final and EOL");
/*     */ 
/*     */                 
/* 277 */                 setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem1);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 285 */       setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020");
/*     */ 
/*     */ 
/*     */       
/* 289 */       setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"));
/*     */     } 
/*     */     
/* 292 */     entityGroup = this.m_elist.getEntityGroup("LSEOBUNDLE");
/* 293 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 294 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 295 */       String str1 = getAttributeFlagEnabledValue(entityItem, "STATUS");
/* 296 */       addDebug(entityItem.getKey() + " status " + str1);
/* 297 */       if (str1 == null || str1.length() == 0) {
/* 298 */         str1 = "0020";
/*     */       }
/* 300 */       if ("0020".equals(str1)) {
/* 301 */         setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", "0020", entityItem);
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
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 315 */     int i = getCount("WWSEOLSEO");
/* 316 */     if (i == 1) {
/* 317 */       EntityItem entityItem = this.m_elist.getEntityGroup("WWSEO").getEntityItem(0);
/*     */ 
/*     */       
/* 320 */       String str1 = getAttributeFlagEnabledValue(entityItem, "STATUS");
/* 321 */       addDebug(entityItem.getKey() + " check status " + str1);
/* 322 */       if (str1 == null) {
/* 323 */         str1 = "0020";
/*     */       }
/*     */       
/* 326 */       if (!"0020".equals(str1) && !"0040".equals(str1)) {
/* 327 */         addDebug(entityItem.getKey() + " is not Final or R4R");
/*     */         
/* 329 */         this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 330 */         this.args[1] = getNavigationName(entityItem);
/* 331 */         addError("NOT_R4R_FINAL_ERR", this.args);
/*     */       } 
/*     */       
/* 334 */       String str2 = getAttributeFlagEnabledValue(entityItem, "SPECBID");
/* 335 */       addDebug(entityItem.getKey() + " SPECBID: " + str2);
/* 336 */       if ("11457".equals(str2)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 341 */         Vector vector3 = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOAVAIL", "AVAIL");
/* 342 */         Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(vector3, "AVAILTYPE", "146");
/* 343 */         if (vector4.size() == 0) {
/* 344 */           this.args[0] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/* 345 */           addError("NOT_SPECBID_AVAIL_ERR", this.args);
/*     */         } 
/*     */ 
/*     */         
/* 349 */         checkCountry("LSEOAVAIL", "D", true);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 354 */         EANTextAttribute eANTextAttribute = (EANTextAttribute)paramEntityItem.getAttribute("SAPASSORTMODULE");
/*     */         
/* 356 */         if (eANTextAttribute == null || !eANTextAttribute.containsNLS(1)) {
/* 357 */           this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "SAPASSORTMODULE", "SAPASSORTMODULE");
/* 358 */           addError("NOT_SPECBID_VALUE_ERR", this.args);
/*     */         } 
/* 360 */         vector3.clear();
/* 361 */         vector4.clear();
/*     */       } else {
/* 363 */         int m = getCount("LSEOAVAIL");
/*     */ 
/*     */ 
/*     */         
/* 367 */         if (m != 0) {
/* 368 */           this.args[0] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/* 369 */           addError("SPECBID_AVAIL_ERR", this.args);
/*     */         } 
/*     */       } 
/*     */       
/* 373 */       String str3 = this.m_db.getDates().getNow().substring(0, 10);
/*     */ 
/*     */ 
/*     */       
/* 377 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOPRODSTRUCT", "PRODSTRUCT"); int j;
/* 378 */       for (j = 0; j < vector1.size(); j++) {
/* 379 */         EntityItem entityItem1 = vector1.elementAt(j);
/* 380 */         String str = PokUtils.getAttributeValue(entityItem1, "WITHDRAWDATE", ", ", "", false);
/* 381 */         addDebug(entityItem1.getKey() + " WITHDRAWDATE: " + str + " strNow: " + str3);
/* 382 */         if (str.length() > 0 && str.compareTo(str3) <= 0) {
/* 383 */           this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 384 */           this.args[1] = getNavigationName(entityItem1);
/* 385 */           addError("WITHDRAWN_ERR", this.args);
/*     */         } 
/*     */       } 
/* 388 */       vector1.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 393 */       checkWDAvails(paramEntityItem, "LSEOPRODSTRUCT", "PRODSTRUCT", "OOFAVAIL", str3);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 398 */       checkWDAvails(paramEntityItem, "LSEOSWPRODSTRUCT", "SWPRODSTRUCT", "SWPRODSTRUCTAVAIL", str3);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 403 */       j = this.m_elist.getEntityGroup("WWSEOPRODSTRUCT").getEntityItemCount();
/* 404 */       int k = this.m_elist.getEntityGroup("LSEOPRODSTRUCT").getEntityItemCount();
/* 405 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem, "MODELWWSEO", "MODEL");
/* 406 */       for (byte b = 0; b < vector2.size(); b++) {
/* 407 */         EntityItem entityItem1 = vector2.elementAt(b);
/* 408 */         String str = getAttributeFlagEnabledValue(entityItem1, "COFCAT");
/* 409 */         addDebug(entityItem1.getKey() + " COFCAT: " + str + " wwseoPS: " + j + " lseoPS: " + k);
/*     */         
/* 411 */         if ("100".equals(str) && 
/* 412 */           j + k == 0) {
/* 413 */           this.args[0] = this.m_elist.getEntityGroup("PRODSTRUCT").getLongDescription();
/*     */           
/* 415 */           addError("MINIMUM_ERR", this.args);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 420 */       vector2.clear();
/*     */       
/* 422 */       if ("0040".equals(paramString)) {
/*     */         
/* 424 */         if ("11457".equals(str2)) {
/*     */ 
/*     */ 
/*     */           
/* 428 */           EANTextAttribute eANTextAttribute = (EANTextAttribute)paramEntityItem.getAttribute("SAPASSORTMODULE");
/*     */           
/* 430 */           if (eANTextAttribute == null || !eANTextAttribute.containsNLS(1)) {
/* 431 */             this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "SAPASSORTMODULE", "SAPASSORTMODULE");
/* 432 */             addError("NOT_SPECBID_VALUE_ERR", this.args);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 439 */         checkStatus(entityItem);
/*     */ 
/*     */ 
/*     */         
/* 443 */         Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
/* 444 */         addDebug("PRODSTRUCT from LSEOPRODSTRUCT found: " + vector.size());
/* 445 */         checkStatus(vector);
/* 446 */         vector.clear();
/*     */ 
/*     */ 
/*     */         
/* 450 */         vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOSWPRODSTRUCT", "SWPRODSTRUCT");
/* 451 */         addDebug("SWPRODSTRUCT from LSEOSWPRODSTRUCT found: " + vector.size());
/* 452 */         checkStatus(vector);
/* 453 */         vector.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 459 */         checkAvailDates(paramEntityItem);
/*     */       } 
/*     */     } else {
/* 462 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEO");
/* 463 */       this.args[0] = entityGroup.getLongDescription();
/*     */ 
/*     */ 
/*     */       
/* 467 */       addError("REQUIRES_ONE_PARENT_ERR", this.args);
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
/*     */   private void checkAvailDates(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 482 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOAVAIL", "AVAIL");
/* 483 */     String str1 = getAttributeValue(paramEntityItem, "LSEOPUBDATEMTRGT", "");
/* 484 */     String str2 = getAttributeValue(paramEntityItem, "LSEOUNPUBDATEMTRGT", "");
/* 485 */     addDebug("checkAvailDates " + paramEntityItem.getKey() + " LSEOPUBDATEMTRGT: " + str1 + " LSEOUNPUBDATEMTRGT: " + str2 + " availVct: " + vector
/* 486 */         .size());
/*     */     
/* 488 */     for (byte b = 0; b < vector.size(); b++) {
/* 489 */       EntityItem entityItem = vector.elementAt(b);
/* 490 */       String str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 491 */       String str4 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 492 */       addDebug("checkAvailDates " + entityItem.getKey() + " EFFECTIVEDATE: " + str3 + " AVAILTYPE: " + str4);
/*     */ 
/*     */       
/* 495 */       if (str1.trim().length() > 0 && (
/* 496 */         "146".equals(str4) || "143".equals(str4)) && str3
/* 497 */         .length() > 0 && str3.compareTo(str1) < 0) {
/*     */ 
/*     */         
/* 500 */         this.args[0] = entityItem.getEntityGroup().getLongDescription() + " " + getNavigationName(entityItem);
/* 501 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "LSEOPUBDATEMTRGT", "LSEOPUBDATEMTRGT");
/* 502 */         this.args[2] = "";
/* 503 */         this.args[3] = "";
/* 504 */         addError("EARLY_DATE_ERR", this.args);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 509 */       if (str2.trim().length() > 0 && 
/* 510 */         "149".equals(str4) && str3.length() > 0 && str3.compareTo(str2) > 0) {
/*     */ 
/*     */         
/* 513 */         this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 514 */         this.args[1] = getNavigationName(entityItem);
/* 515 */         this.args[2] = "";
/* 516 */         this.args[3] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "LSEOUNPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT");
/* 517 */         this.args[4] = "";
/* 518 */         addError("LATER_DATE_ERR", this.args);
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
/*     */   public String getDescription() {
/* 531 */     return "LSEO ABR.";
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
/*     */   public String getABRVersion() {
/* 543 */     return "1.36";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\LSEOABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */