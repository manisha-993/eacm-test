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
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
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
/*     */ 
/*     */ 
/*     */ public class WWPRTLSEOABR
/*     */   extends EPIMSABRBase
/*     */ {
/*     */   private static final Vector FIRSTFINAL_XMLMAP_VCT;
/*     */   private static final Vector CHGFINAL_XMLMAP_VCT;
/*  78 */   private static final Hashtable ATTR_OF_INTEREST_TBL = new Hashtable<>(); static {
/*  79 */     loadAttrOfInterest("WWPRTLSEOABR_AOI.properties", ATTR_OF_INTEREST_TBL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     FIRSTFINAL_XMLMAP_VCT = new Vector();
/*  87 */     SAPLElem sAPLElem = new SAPLElem("WWPRTNotification");
/*     */     
/*  89 */     FIRSTFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/*  91 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "LSEO"));
/*  92 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/*  93 */     sAPLElem.addChild(new SAPLElem("Id", "LSEO", "SEOID", true));
/*  94 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Change", "no"));
/*  95 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/*  96 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */     
/*  98 */     CHGFINAL_XMLMAP_VCT = new Vector();
/*  99 */     sAPLElem = new SAPLElem("WWPRTNotification");
/*     */     
/* 101 */     CHGFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/* 103 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "LSEO"));
/* 104 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/* 105 */     sAPLElem.addChild(new SAPLElem("Id", "LSEO", "SEOID", true));
/* 106 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Change", "yes"));
/* 107 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/* 108 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String PROPERTIES_FNAME = "WWPRTLSEOABR_AOI.properties";
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/* 115 */     Vector<String> vector = new Vector(1);
/* 116 */     vector.add("WWPRTMQSERIES");
/* 117 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getXMLMap(boolean paramBoolean) {
/* 125 */     if (paramBoolean) {
/* 126 */       return FIRSTFINAL_XMLMAP_VCT;
/*     */     }
/* 128 */     return CHGFINAL_XMLMAP_VCT;
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
/*     */   protected void execute() throws Exception {
/* 212 */     EntityItem entityItem = this.epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
/*     */     
/* 214 */     String str1 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "STATUS");
/* 215 */     String str2 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*     */     
/* 217 */     addDebug("execute: " + entityItem.getKey() + " STATUS: " + 
/* 218 */         PokUtils.getAttributeValue(entityItem, "STATUS", ", ", "", false) + " [" + str1 + "] sysfeedFlag: " + str2);
/*     */ 
/*     */     
/* 221 */     if ("Yes".equals(str2)) {
/* 222 */       resendSystemFeed(entityItem, str1);
/*     */       
/*     */       return;
/*     */     } 
/* 226 */     if (!"0020".equals(str1)) {
/* 227 */       addError("ERROR_NOT_FINAL", null);
/*     */       
/*     */       return;
/*     */     } 
/* 231 */     if (this.epimsAbr.isFirstFinal()) {
/* 232 */       addDebug("Only one transition to Final found, must be first.");
/*     */       
/* 234 */       notifyAndSetStatus(null);
/*     */     } else {
/* 236 */       addDebug("More than one transition to Final found, check for change of interest.");
/*     */       
/* 238 */       if (changeOfInterest()) {
/*     */ 
/*     */         
/* 241 */         notifyAndSetStatus(null);
/*     */       } else {
/* 243 */         addDebug("No change of interest found");
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
/*     */   protected void handleResend(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 274 */     if (!"0020".equals(paramString)) {
/*     */       
/* 276 */       addError("RESEND_NOT_FINAL", null);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 283 */     if (!checkForSpecBid()) {
/* 284 */       addError("NOT_SPECBID", null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 289 */     notifyAndSetStatus(null);
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
/*     */   private boolean checkForSpecBid() throws SQLException, MiddlewareException {
/* 301 */     String str1 = this.epimsAbr.getLastFinalDTS();
/*     */     
/* 303 */     boolean bool = false;
/*     */     
/* 305 */     Profile profile = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 306 */     profile.setValOnEffOn(str1, str1);
/*     */     
/* 308 */     String str2 = getVeName();
/*     */     
/* 310 */     EntityList entityList = this.epimsAbr.getDB().getEntityList(profile, new ExtractActionItem(null, this.epimsAbr
/* 311 */           .getDB(), profile, str2), new EntityItem[] { new EntityItem(null, profile, this.epimsAbr
/* 312 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 315 */     addDebug("checkForSpecBid dts: " + str1 + " extract: " + str2 + NEWLINE + 
/* 316 */         PokUtils.outputList(entityList));
/*     */ 
/*     */     
/* 319 */     EntityGroup entityGroup = entityList.getEntityGroup("WWSEO");
/* 320 */     if (entityGroup.getEntityItemCount() > 0) {
/* 321 */       EntityItem entityItem = entityGroup.getEntityItem(0);
/* 322 */       String str = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "SPECBID");
/* 323 */       bool = "11458".equals(str);
/* 324 */       addDebug("checkForSpecBid " + entityItem.getKey() + " specbid: " + str);
/*     */     } 
/*     */     
/* 327 */     entityList.dereference();
/*     */     
/* 329 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable getAttrOfInterest() {
/* 335 */     return ATTR_OF_INTEREST_TBL;
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
/*     */   protected Hashtable getStringRep(EntityList paramEntityList, Hashtable paramHashtable) {
/* 368 */     addDebug(NEWLINE + "getStringRep: entered for " + paramEntityList.getProfile().getValOn());
/* 369 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 370 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 371 */     String[] arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 372 */     if (arrayOfString == null)
/* 373 */       addDebug("getStringRep: No list of 'attr of interest' found for " + entityGroup.getEntityType()); 
/*     */     byte b;
/* 375 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 377 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 378 */       String str = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 379 */       addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 380 */       hashtable.put(entityItem.getKey(), str);
/*     */     } 
/*     */ 
/*     */     
/* 384 */     entityGroup = paramEntityList.getEntityGroup("FEATURE");
/*     */     
/* 386 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 388 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 389 */       String str = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "PRICEDFEATURE");
/* 390 */       addDebug(entityItem.getKey() + " PRICEDFEATURE: " + str);
/* 391 */       if ("100".equals(str)) {
/* 392 */         arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 393 */         String str1 = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 394 */         addDebug("getStringRep: put " + entityItem.getKey() + " " + str1);
/* 395 */         hashtable.put(entityItem.getKey(), str1);
/*     */ 
/*     */         
/* 398 */         Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "LSEOPRODSTRUCT"); byte b1;
/* 399 */         for (b1 = 0; b1 < vector.size(); b1++) {
/* 400 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 401 */           arrayOfString = (String[])paramHashtable.get(entityItem1.getEntityType());
/* 402 */           str1 = this.epimsAbr.generateString(entityItem1, arrayOfString);
/* 403 */           addDebug("getStringRep: put " + entityItem1.getKey() + " " + str1);
/* 404 */           hashtable.put(entityItem1.getKey(), str1);
/*     */         } 
/* 406 */         vector.clear();
/* 407 */         vector = getAllLinkedEntities(entityItem, "WWSEOPRODSTRUCT");
/* 408 */         for (b1 = 0; b1 < vector.size(); b1++) {
/* 409 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 410 */           arrayOfString = (String[])paramHashtable.get(entityItem1.getEntityType());
/* 411 */           str1 = this.epimsAbr.generateString(entityItem1, arrayOfString);
/* 412 */           addDebug("getStringRep: put " + entityItem1.getKey() + " " + str1);
/* 413 */           hashtable.put(entityItem1.getKey(), str1);
/*     */         } 
/* 415 */         vector.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 419 */     return hashtable;
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
/* 432 */     Vector<EANEntity> vector = new Vector(1);
/*     */     
/* 434 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*     */       
/* 436 */       EANEntity eANEntity = paramEntityItem.getDownLink(b);
/* 437 */       if (eANEntity.getEntityType().equals("PRODSTRUCT"))
/*     */       {
/*     */         
/* 440 */         for (byte b1 = 0; b1 < eANEntity.getUpLinkCount(); b1++) {
/*     */           
/* 442 */           EANEntity eANEntity1 = eANEntity.getUpLink(b1);
/* 443 */           if (eANEntity1.getEntityType().equals(paramString) && !vector.contains(eANEntity1)) {
/* 444 */             vector.addElement(eANEntity1);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 449 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVeName() {
/* 455 */     return "WWPRTLSEOVE1";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 464 */     return "1.6";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWPRTLSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */