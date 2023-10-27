/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLEnterpriseElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLIdElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLNotificationElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANEntity;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWPRTLSEOBUNDLEABR
/*     */   extends EPIMSABRBase
/*     */ {
/*     */   private static final Vector FIRSTFINAL_XMLMAP_VCT;
/*     */   private static final Vector CHGFINAL_XMLMAP_VCT;
/*  74 */   private static final Hashtable ATTR_OF_INTEREST_TBL = new Hashtable<>(); static {
/*  75 */     loadAttrOfInterest("WWPRTLSEOBUNDLEABR_AOI.properties", ATTR_OF_INTEREST_TBL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     FIRSTFINAL_XMLMAP_VCT = new Vector();
/*  83 */     SAPLElem sAPLElem = new SAPLElem("WWPRTNotification");
/*     */     
/*  85 */     FIRSTFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/*  87 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "LSEOBUNDLE"));
/*  88 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/*  89 */     sAPLElem.addChild(new SAPLElem("Id", "LSEOBUNDLE", "SEOID", true));
/*  90 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Change", "no"));
/*  91 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/*  92 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */     
/*  94 */     CHGFINAL_XMLMAP_VCT = new Vector();
/*  95 */     sAPLElem = new SAPLElem("WWPRTNotification");
/*     */     
/*  97 */     CHGFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/*  99 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "LSEOBUNDLE"));
/* 100 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/* 101 */     sAPLElem.addChild(new SAPLElem("Id", "LSEOBUNDLE", "SEOID", true));
/* 102 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Change", "yes"));
/* 103 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/* 104 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String PROPERTIES_FNAME = "WWPRTLSEOBUNDLEABR_AOI.properties";
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/* 111 */     Vector<String> vector = new Vector(1);
/* 112 */     vector.add("WWPRTMQSERIES");
/* 113 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getXMLMap(boolean paramBoolean) {
/* 120 */     if (paramBoolean) {
/* 121 */       return FIRSTFINAL_XMLMAP_VCT;
/*     */     }
/* 123 */     return CHGFINAL_XMLMAP_VCT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute() throws Exception {
/* 209 */     EntityItem entityItem = this.epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
/*     */     
/* 211 */     String str1 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "STATUS");
/* 212 */     String str2 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*     */     
/* 214 */     addDebug("execute: " + entityItem.getKey() + " STATUS: " + 
/* 215 */         PokUtils.getAttributeValue(entityItem, "STATUS", ", ", "", false) + " [" + str1 + "] sysfeedFlag: " + str2);
/*     */ 
/*     */     
/* 218 */     if ("Yes".equals(str2)) {
/* 219 */       resendSystemFeed(entityItem, str1);
/*     */       
/*     */       return;
/*     */     } 
/* 223 */     if (!"0020".equals(str1)) {
/* 224 */       addError("ERROR_NOT_FINAL", null);
/*     */       
/*     */       return;
/*     */     } 
/* 228 */     if (this.epimsAbr.isFirstFinal()) {
/* 229 */       addDebug("Only one transition to Final found, must be first.");
/*     */       
/* 231 */       notifyAndSetStatus(null);
/*     */     } else {
/* 233 */       addDebug("More than one transition to Final found, check for change of interest.");
/*     */       
/* 235 */       if (changeOfInterest()) {
/*     */ 
/*     */         
/* 238 */         notifyAndSetStatus(null);
/*     */       } else {
/* 240 */         addDebug("No change of interest found");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleResend(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 274 */     String str = this.epimsAbr.getAttributeFlagEnabledValue(paramEntityItem, "SPECBID");
/* 275 */     addDebug(paramEntityItem.getKey() + " specbid: " + str);
/*     */     
/* 277 */     if (!"11458".equals(str)) {
/* 278 */       addError("NOT_SPECBID", null);
/*     */       
/*     */       return;
/*     */     } 
/* 282 */     if (!"0020".equals(paramString)) {
/*     */       
/* 284 */       addError("RESEND_NOT_FINAL", null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 289 */     notifyAndSetStatus(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable getAttrOfInterest() {
/* 295 */     return ATTR_OF_INTEREST_TBL;
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
/*     */   protected Hashtable getStringRep(EntityList paramEntityList, Hashtable paramHashtable) {
/* 329 */     addDebug(NEWLINE + "getStringRep: entered for " + paramEntityList.getProfile().getValOn());
/* 330 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*     */     
/* 332 */     EntityGroup entityGroup = paramEntityList.getEntityGroup("LSEOBUNDLELSEO");
/* 333 */     for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*     */       
/* 335 */       EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 336 */       addDebug("getStringRep: put " + entityItem.getKey());
/* 337 */       hashtable.put(entityItem.getKey(), entityItem.getKey());
/*     */     } 
/*     */     
/* 340 */     entityGroup = paramEntityList.getEntityGroup("LSEO");
/* 341 */     String[] arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType()); byte b2;
/* 342 */     for (b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*     */       
/* 344 */       EntityItem entityItem = entityGroup.getEntityItem(b2);
/* 345 */       String str = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 346 */       addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 347 */       hashtable.put(entityItem.getKey(), str);
/*     */     } 
/*     */     
/* 350 */     entityGroup = paramEntityList.getEntityGroup("FEATURE");
/* 351 */     for (b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*     */       
/* 353 */       EntityItem entityItem = entityGroup.getEntityItem(b2);
/* 354 */       String str = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "PRICEDFEATURE");
/* 355 */       addDebug(entityItem.getKey() + " PRICEDFEATURE: " + str);
/* 356 */       if ("100".equals(str)) {
/* 357 */         arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 358 */         String str1 = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 359 */         addDebug("getStringRep: put " + entityItem.getKey() + " " + str1);
/* 360 */         hashtable.put(entityItem.getKey(), str1);
/*     */ 
/*     */         
/* 363 */         Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "LSEOPRODSTRUCT"); byte b;
/* 364 */         for (b = 0; b < vector.size(); b++) {
/* 365 */           EntityItem entityItem1 = vector.elementAt(b);
/* 366 */           arrayOfString = (String[])paramHashtable.get(entityItem1.getEntityType());
/* 367 */           str1 = this.epimsAbr.generateString(entityItem1, arrayOfString);
/* 368 */           addDebug("getStringRep: put " + entityItem1.getKey() + " " + str1);
/* 369 */           hashtable.put(entityItem1.getKey(), str1);
/*     */         } 
/* 371 */         vector.clear();
/* 372 */         vector = getAllLinkedEntities(entityItem, "WWSEOPRODSTRUCT");
/* 373 */         for (b = 0; b < vector.size(); b++) {
/* 374 */           EntityItem entityItem1 = vector.elementAt(b);
/* 375 */           arrayOfString = (String[])paramHashtable.get(entityItem1.getEntityType());
/* 376 */           str1 = this.epimsAbr.generateString(entityItem1, arrayOfString);
/* 377 */           addDebug("getStringRep: put " + entityItem1.getKey() + " " + str1);
/* 378 */           hashtable.put(entityItem1.getKey(), str1);
/*     */         } 
/* 380 */         vector.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 384 */     return hashtable;
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
/*     */   private Vector getAllLinkedEntities(EntityItem paramEntityItem, String paramString) {
/* 397 */     Vector<EANEntity> vector = new Vector(1);
/*     */     
/* 399 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*     */       
/* 401 */       EANEntity eANEntity = paramEntityItem.getDownLink(b);
/* 402 */       if (eANEntity.getEntityType().equals("PRODSTRUCT"))
/*     */       {
/*     */         
/* 405 */         for (byte b1 = 0; b1 < eANEntity.getUpLinkCount(); b1++) {
/*     */           
/* 407 */           EANEntity eANEntity1 = eANEntity.getUpLink(b1);
/* 408 */           if (eANEntity1.getEntityType().equals(paramString) && !vector.contains(eANEntity1)) {
/* 409 */             vector.addElement(eANEntity1);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 414 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVeName() {
/* 420 */     return "WWPRTLSEOBDLVE1";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 429 */     return "1.6";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWPRTLSEOBUNDLEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */