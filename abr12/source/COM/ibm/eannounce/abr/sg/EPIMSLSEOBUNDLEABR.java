/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLEnterpriseElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLIdElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLNotificationElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EPIMSLSEOBUNDLEABR
/*     */   extends EPIMSABRBase
/*     */ {
/*     */   private static final Vector FIRSTFINAL_XMLMAP_VCT;
/*     */   private static final Vector CHGFINAL_XMLMAP_VCT;
/* 153 */   private static final Hashtable ATTR_OF_INTEREST_TBL = new Hashtable<>(); static {
/* 154 */     loadAttrOfInterest("EPIMSLSEOBUNDLEABR_AOI.properties", ATTR_OF_INTEREST_TBL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     FIRSTFINAL_XMLMAP_VCT = new Vector();
/* 166 */     SAPLElem sAPLElem = new SAPLElem("LseoBundleFinalNotification");
/*     */     
/* 168 */     FIRSTFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/* 170 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "LSEOBUNDLE"));
/* 171 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/* 172 */     sAPLElem.addChild(new SAPLElem("LseoBundleId", "LSEOBUNDLE", "SEOID", true));
/* 173 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/* 174 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */     
/* 176 */     CHGFINAL_XMLMAP_VCT = new Vector();
/* 177 */     sAPLElem = new SAPLElem("LseoBundleChangedNotification");
/*     */     
/* 179 */     CHGFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/* 181 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "LSEOBUNDLE"));
/* 182 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/* 183 */     sAPLElem.addChild(new SAPLElem("LseoBundleId", "LSEOBUNDLE", "SEOID", true));
/* 184 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/* 185 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */   }
/*     */   
/*     */   private static final String PROPERTIES_FNAME = "EPIMSLSEOBUNDLEABR_AOI.properties";
/* 189 */   private EntityList lastFinalList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/* 195 */     Vector<String> vector = new Vector(1);
/* 196 */     vector.add("EPIMSMQSERIES");
/* 197 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getXMLMap(boolean paramBoolean) {
/* 205 */     if (paramBoolean) {
/* 206 */       return FIRSTFINAL_XMLMAP_VCT;
/*     */     }
/* 208 */     return CHGFINAL_XMLMAP_VCT;
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
/*     */   protected void execute() throws Exception {
/* 236 */     EntityItem entityItem = this.epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
/*     */     
/* 238 */     String str1 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "STATUS");
/* 239 */     String str2 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*     */     
/* 241 */     addDebug("execute: " + entityItem.getKey() + " STATUS: " + 
/* 242 */         PokUtils.getAttributeValue(entityItem, "STATUS", ", ", "", false) + " [" + str1 + "] sysfeedFlag: " + str2);
/*     */ 
/*     */ 
/*     */     
/* 246 */     if (!validCountry()) {
/*     */       
/* 248 */       String str = this.epimsAbr.getBundle().getString("COUNTRY_NOT_LISTED");
/* 249 */       addOutput(str);
/* 250 */       this.lastFinalList.dereference();
/*     */       
/*     */       return;
/*     */     } 
/* 254 */     if ("Yes".equals(str2)) {
/* 255 */       resendSystemFeed(entityItem, str1);
/*     */       
/*     */       return;
/*     */     } 
/* 259 */     if (!"0020".equals(str1)) {
/*     */       
/* 261 */       addError("ERROR_NOT_FINAL", null);
/*     */       
/*     */       return;
/*     */     } 
/* 265 */     if (this.epimsAbr.isFirstFinal()) {
/* 266 */       addDebug("Only one transition to Final found, must be first.");
/*     */       
/* 268 */       notifyAndSetStatus(null);
/*     */     } else {
/* 270 */       addDebug("More than one transition to Final found, check for change of interest.");
/*     */       
/* 272 */       if (changeOfInterest()) {
/*     */ 
/*     */         
/* 275 */         notifyAndSetStatus(null);
/*     */       } else {
/*     */         
/* 278 */         String str = this.epimsAbr.getBundle().getString("NO_CHG_FOUND");
/* 279 */         addOutput(str);
/* 280 */         D.ebug("EPIMSABRSTATUS:LSEOBUNDLE " + str);
/*     */       } 
/*     */     } 
/* 283 */     this.lastFinalList.dereference();
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
/*     */   protected void handleResend(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 300 */     if (!"0020".equals(paramString)) {
/*     */       
/* 302 */       addError("RESEND_NOT_FINAL", null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 307 */     notifyAndSetStatus(null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean changeOfInterest() throws SQLException, MiddlewareException {
/* 570 */     boolean bool = false;
/* 571 */     String str1 = this.epimsAbr.getLastFinalDTS();
/* 572 */     String str2 = this.epimsAbr.getPriorFinalDTS();
/*     */     
/* 574 */     if (str1.equals(str2)) {
/* 575 */       addDebug("changeOfInterest: Only one transition to Final found, no changes can exist.");
/* 576 */       return bool;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 585 */     String str3 = getVeName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 596 */     Profile profile = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 597 */     profile.setValOnEffOn(str2, str2);
/*     */     
/* 599 */     EntityList entityList = this.epimsAbr.getDB().getEntityList(profile, new ExtractActionItem(null, this.epimsAbr
/* 600 */           .getDB(), profile, str3), new EntityItem[] { new EntityItem(null, profile, this.epimsAbr
/* 601 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 604 */     addDebug("changeOfInterest dts: " + str2 + " extract: " + str3 + NEWLINE + 
/* 605 */         PokUtils.outputList(entityList));
/*     */ 
/*     */     
/* 608 */     Hashtable hashtable1 = getStringRep(this.lastFinalList, ATTR_OF_INTEREST_TBL);
/* 609 */     Hashtable hashtable2 = getStringRep(entityList, ATTR_OF_INTEREST_TBL);
/*     */     
/* 611 */     bool = changeOfInterest(hashtable1, hashtable2);
/*     */     
/* 613 */     entityList.dereference();
/*     */     
/* 615 */     hashtable1.clear();
/* 616 */     hashtable2.clear();
/*     */     
/* 618 */     return bool;
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
/*     */   private boolean validCountry() throws SQLException, MiddlewareException {
/* 641 */     boolean bool = false;
/* 642 */     String str1 = this.epimsAbr.getLastFinalDTS();
/*     */ 
/*     */     
/* 645 */     Profile profile = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 646 */     profile.setValOnEffOn(str1, str1);
/*     */ 
/*     */ 
/*     */     
/* 650 */     String str2 = getVeName();
/* 651 */     this.lastFinalList = this.epimsAbr.getDB().getEntityList(profile, new ExtractActionItem(null, this.epimsAbr
/* 652 */           .getDB(), profile, str2), new EntityItem[] { new EntityItem(null, profile, this.epimsAbr
/* 653 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 656 */     addDebug("validCountry dts: " + str1 + " extract: " + str2 + NEWLINE + 
/* 657 */         PokUtils.outputList(this.lastFinalList));
/*     */ 
/*     */     
/* 660 */     EntityItem entityItem = this.lastFinalList.getParentEntityGroup().getEntityItem(0);
/* 661 */     String str3 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "SPECBID");
/* 662 */     addDebug(entityItem.getKey() + " SPECBID: " + str3);
/* 663 */     if ("11457".equals(str3)) {
/*     */       
/* 665 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem, "LSEOBUNDLEAVAIL", "AVAIL");
/* 666 */       Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 667 */       addDebug(" not a specbid- chk avails availVct.size " + vector.size() + " plannedavailVector.size " + vector1.size());
/* 668 */       for (byte b = 0; b < vector1.size(); b++) {
/* 669 */         EntityItem entityItem1 = vector1.elementAt(b);
/* 670 */         bool = this.epimsAbr.checkABRCountryList(entityItem1);
/* 671 */         if (bool) {
/*     */           break;
/*     */         }
/*     */       } 
/* 675 */       vector.clear();
/* 676 */       vector1.clear();
/*     */     } else {
/* 678 */       bool = this.epimsAbr.checkABRCountryList(entityItem);
/*     */     } 
/*     */     
/* 681 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVeName() {
/* 687 */     return "EPIMSLSEOBUNDLEVE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 696 */     return "1.12";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\EPIMSLSEOBUNDLEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */