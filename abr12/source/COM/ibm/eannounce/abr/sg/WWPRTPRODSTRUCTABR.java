/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLEnterpriseElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLIdElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLNotificationElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
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
/*     */ public class WWPRTPRODSTRUCTABR
/*     */   extends EPIMSABRBase
/*     */ {
/*     */   private static final Vector FIRSTFINAL_XMLMAP_VCT;
/*     */   private static final Vector CHGFINAL_XMLMAP_VCT;
/*  70 */   private static final Hashtable ATTR_OF_INTEREST_TBL = new Hashtable<>(); static {
/*  71 */     loadAttrOfInterest("WWPRTPRODSTRUCTABR_AOI.properties", ATTR_OF_INTEREST_TBL);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     FIRSTFINAL_XMLMAP_VCT = new Vector();
/*  77 */     SAPLElem sAPLElem = new SAPLElem("WWPRTNotification");
/*     */     
/*  79 */     FIRSTFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/*  81 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "RPQ"));
/*  82 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/*  83 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("Id"));
/*  84 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Change", "no"));
/*  85 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/*  86 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */     
/*  88 */     CHGFINAL_XMLMAP_VCT = new Vector();
/*  89 */     sAPLElem = new SAPLElem("WWPRTNotification");
/*     */     
/*  91 */     CHGFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/*  93 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "RPQ"));
/*  94 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/*  95 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("Id"));
/*  96 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Change", "yes"));
/*  97 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/*  98 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String PROPERTIES_FNAME = "WWPRTPRODSTRUCTABR_AOI.properties";
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/* 105 */     Vector<String> vector = new Vector(1);
/* 106 */     vector.add("WWPRTMQSERIES");
/* 107 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getXMLMap(boolean paramBoolean) {
/* 115 */     if (paramBoolean) {
/* 116 */       return FIRSTFINAL_XMLMAP_VCT;
/*     */     }
/* 118 */     return CHGFINAL_XMLMAP_VCT;
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
/*     */   protected void execute() throws Exception {
/* 172 */     EntityItem entityItem = this.epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
/*     */     
/* 174 */     String str1 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "STATUS");
/* 175 */     String str2 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*     */     
/* 177 */     addDebug("execute: " + entityItem.getKey() + " STATUS: " + 
/* 178 */         PokUtils.getAttributeValue(entityItem, "STATUS", ", ", "", false) + " [" + str1 + "] sysfeedFlag: " + str2);
/*     */ 
/*     */     
/* 181 */     if ("Yes".equals(str2)) {
/* 182 */       resendSystemFeed(entityItem, str1);
/*     */       
/*     */       return;
/*     */     } 
/* 186 */     if (!"0020".equals(str1)) {
/* 187 */       addError("ERROR_NOT_FINAL", null);
/*     */       
/*     */       return;
/*     */     } 
/* 191 */     if (this.epimsAbr.isFirstFinal()) {
/* 192 */       addDebug("Only one transition to Final found, must be first.");
/*     */       
/* 194 */       notifyAndSetStatus(null);
/*     */     } else {
/* 196 */       addDebug("More than one transition to Final found, check for change of interest.");
/*     */       
/* 198 */       if (changeOfInterest()) {
/*     */         
/* 200 */         notifyAndSetStatus(null);
/*     */       } else {
/* 202 */         addDebug("No change of interest found");
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
/*     */   protected void handleResend(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 235 */     if (!"0020".equals(paramString)) {
/*     */       
/* 237 */       addError("RPQ_NOT_FINAL", null);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 244 */     if (!checkRPQ()) {
/* 245 */       addError("NOT_RPQ", null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 250 */     notifyAndSetStatus(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkRPQ() throws SQLException, MiddlewareException {
/* 260 */     String str1 = this.epimsAbr.getLastFinalDTS();
/*     */     
/* 262 */     boolean bool = false;
/*     */     
/* 264 */     Profile profile = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 265 */     profile.setValOnEffOn(str1, str1);
/*     */     
/* 267 */     String str2 = getVeName();
/*     */     
/* 269 */     EntityList entityList = this.epimsAbr.getDB().getEntityList(profile, new ExtractActionItem(null, this.epimsAbr
/* 270 */           .getDB(), profile, str2), new EntityItem[] { new EntityItem(null, profile, this.epimsAbr
/* 271 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 274 */     addDebug("checkRPQ dts: " + str1 + " extract: " + str2 + NEWLINE + 
/* 275 */         PokUtils.outputList(entityList));
/*     */ 
/*     */     
/* 278 */     EntityGroup entityGroup = entityList.getEntityGroup("FEATURE");
/* 279 */     EntityItem entityItem = entityGroup.getEntityItem(0);
/* 280 */     String str3 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "FCTYPE");
/* 281 */     addDebug(entityItem.getKey() + " FCTYPE " + str3);
/* 282 */     if ("120".equals(str3) || "130".equals(str3) || "402".equals(str3)) {
/* 283 */       addDebug(entityItem.getKey() + " is RPQ or placeholder");
/* 284 */       bool = true;
/*     */     } 
/*     */     
/* 287 */     entityList.dereference();
/*     */     
/* 289 */     return bool;
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
/*     */   protected Hashtable getStringRep(EntityList paramEntityList, Hashtable paramHashtable) {
/* 313 */     addDebug(NEWLINE + "getStringRep: entered for " + paramEntityList.getProfile().getValOn());
/* 314 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 315 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 316 */     String[] arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType()); byte b;
/* 317 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 319 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 320 */       String str = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 321 */       addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 322 */       hashtable.put(entityItem.getKey(), str);
/*     */     } 
/*     */     
/* 325 */     entityGroup = paramEntityList.getEntityGroup("FEATURE");
/* 326 */     arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 327 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 329 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 330 */       String str = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 331 */       addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 332 */       hashtable.put(entityItem.getKey(), str);
/*     */     } 
/*     */     
/* 335 */     return hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVeName() {
/* 341 */     return "EXRPT3FM";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 350 */     return "1.5";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWPRTPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */