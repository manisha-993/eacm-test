/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class SWSPRODSTRUCTABRSTATUS extends DQABRSTATUS {
/*  11 */   private Object[] args = new Object[3];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/*  17 */     return true;
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  31 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     String str = PokUtils.getAttributeFlagValue(entityItem, "LIFECYCLE");
/*     */     
/*  39 */     if (str == null || str.length() == 0) {
/*  40 */       str = "LF01";
/*     */     }
/*  42 */     if ("LF01".equals(str) || "LF02"
/*  43 */       .equals(str)) {
/*  44 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SWSTMFAVAIL", "AVAIL");
/*  45 */       for (byte b = 0; b < vector.size(); b++) {
/*  46 */         EntityItem entityItem1 = vector.elementAt(b);
/*  47 */         if (statusIsRFRorFinal(entityItem1)) {
/*  48 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*     */           break;
/*     */         } 
/*     */       } 
/*  52 */       vector.clear();
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  70 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SWSTMFAVAIL", "AVAIL");
/*  77 */     for (byte b = 0; b < vector.size(); b++) {
/*  78 */       EntityItem entityItem1 = vector.elementAt(b);
/*  79 */       if (statusIsFinal(entityItem1)) {
/*  80 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*     */         break;
/*     */       } 
/*     */     } 
/*  84 */     vector.clear();
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
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  97 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*     */     
/*  99 */     int i = getCheck_W_E_E(paramString);
/* 100 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("SVCMOD").getEntityItem(0);
/* 101 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("SWSFEATURE").getEntityItem(0);
/*     */     
/* 103 */     checkStatusVsDQ(entityItem1, "STATUS", paramEntityItem, 4);
/* 104 */     checkStatusVsDQ(entityItem2, "STATUS", paramEntityItem, 4);
/*     */     
/* 106 */     checkAvail(paramEntityItem);
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
/*     */   private void checkAvail(EntityItem paramEntityItem) {
/* 122 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Planned Avail Checks:");
/*     */     
/* 124 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "SWSTMFAVAIL", "AVAIL");
/* 125 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/*     */     
/* 127 */     checkPlannedAvailsExist(vector2, 3);
/* 128 */     checkPlannedAvailsType(vector2, 3);
/* 129 */     checkPlannedAvailsStatus(vector2, paramEntityItem, 3);
/*     */     
/* 131 */     vector2.clear();
/* 132 */     vector1.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkPlannedAvailsType(Vector paramVector, int paramInt) {
/* 138 */     if (paramVector != null && paramVector.size() > 0) {
/* 139 */       boolean bool = false;
/* 140 */       for (EntityItem entityItem : paramVector) {
/*     */         
/* 142 */         String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 143 */         addDebug("check plannedavail " + entityItem.getKey() + " AVAILANNTYPE: " + str);
/* 144 */         if (!"NORFA".equalsIgnoreCase(str)) {
/* 145 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 149 */       if (bool) {
/* 150 */         this.args[0] = "Planned Availability type";
/* 151 */         this.args[1] = "NORFA";
/* 152 */         createMessage(paramInt, "MUST_BE_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLCRFRWFName() {
/* 162 */     return "WFLCSWSPRODSTRFR"; } protected String getLCFinalWFName() {
/* 163 */     return "WFLCSWSPRODSTFINAL";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 172 */     return "SWSPRODSTRUCT ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 183 */     return "1.0";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SWSPRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */