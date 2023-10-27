/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class MAINTPRODSTRUCTABRSTATUS extends DQABRSTATUS {
/*  10 */   private Object[] args = new Object[3];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/*  16 */     return true;
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
/*  30 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  36 */     String str = PokUtils.getAttributeFlagValue(entityItem, "LIFECYCLE");
/*     */     
/*  38 */     if (str == null || str.length() == 0) {
/*  39 */       str = "LF01";
/*     */     }
/*  41 */     if ("LF01".equals(str) || "LF02"
/*  42 */       .equals(str)) {
/*  43 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "MAINTMFAVAIL", "AVAIL");
/*  44 */       for (byte b = 0; b < vector.size(); b++) {
/*  45 */         EntityItem entityItem1 = vector.elementAt(b);
/*  46 */         if (statusIsRFRorFinal(entityItem1)) {
/*  47 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*     */           break;
/*     */         } 
/*     */       } 
/*  51 */       vector.clear();
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
/*  69 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "MAINTMFAVAIL", "AVAIL");
/*  76 */     for (byte b = 0; b < vector.size(); b++) {
/*  77 */       EntityItem entityItem1 = vector.elementAt(b);
/*  78 */       if (statusIsFinal(entityItem1)) {
/*  79 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*     */         break;
/*     */       } 
/*     */     } 
/*  83 */     vector.clear();
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
/*  96 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*     */     
/*  98 */     int i = getCheck_W_E_E(paramString);
/*  99 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("SVCMOD").getEntityItem(0);
/* 100 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("MAINTFEATURE").getEntityItem(0);
/*     */     
/* 102 */     checkStatusVsDQ(entityItem1, "STATUS", paramEntityItem, 4);
/* 103 */     checkStatusVsDQ(entityItem2, "STATUS", paramEntityItem, 4);
/*     */     
/* 105 */     checkAvail(paramEntityItem);
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
/* 121 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Planned Avail Checks:");
/*     */     
/* 123 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "MAINTMFAVAIL", "AVAIL");
/* 124 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/*     */     
/* 126 */     checkPlannedAvailsExist(vector2, 3);
/*     */     
/* 128 */     checkPlannedAvailsStatus(vector2, paramEntityItem, 3);
/*     */     
/* 130 */     vector2.clear();
/* 131 */     vector1.clear();
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
/* 142 */     return "MAINTPRODSTRUCT ABR";
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
/* 153 */     return "1.0";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\MAINTPRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */