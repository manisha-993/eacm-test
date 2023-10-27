/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class MAINTPRODSTRUCTABRSTATUS extends DQABRSTATUS {
/*   6 */   private Object[] args = new Object[3];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/*  12 */     return true;
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*     */ 
/*     */ 
/*     */ 
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
/*  41 */     setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", "0020");
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
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  62 */     if ("0010".equals(paramString) || "0050".equals(paramString)) {
/*     */       
/*  64 */       EntityItem entityItem1 = this.m_elist.getEntityGroup("SVCMOD").getEntityItem(0);
/*  65 */       EntityItem entityItem2 = this.m_elist.getEntityGroup("MAINTFEATURE").getEntityItem(0);
/*     */ 
/*     */ 
/*     */       
/*  69 */       String str = getAttributeFlagEnabledValue(entityItem1, "STATUS");
/*  70 */       addDebug(entityItem1.getKey() + " check status " + str);
/*  71 */       if (str == null) {
/*  72 */         str = "0020";
/*     */       }
/*     */       
/*  75 */       if (!"0020".equals(str) && !"0040".equals(str)) {
/*  76 */         addDebug(entityItem1.getKey() + " is not Final or R4R");
/*     */         
/*  78 */         this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/*  79 */         this.args[1] = getNavigationName(entityItem1);
/*  80 */         addError("NOT_R4R_FINAL_ERR", this.args);
/*     */       } 
/*     */ 
/*     */       
/*  84 */       str = getAttributeFlagEnabledValue(entityItem2, "STATUS");
/*  85 */       addDebug(entityItem2.getKey() + " check status " + str);
/*  86 */       if (str == null) {
/*  87 */         str = "0020";
/*     */       }
/*     */       
/*  90 */       if (!"0020".equals(str) && !"0040".equals(str)) {
/*  91 */         addDebug(entityItem2.getKey() + " is not Final or R4R");
/*     */         
/*  93 */         this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/*  94 */         this.args[1] = getNavigationName(entityItem2);
/*  95 */         addError("NOT_R4R_FINAL_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */       
/* 103 */       checkStatus("SVCMOD");
/*     */ 
/*     */ 
/*     */       
/* 107 */       checkStatus("MAINTFEATURE");
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
/*     */   public String getDescription() {
/* 120 */     return "MAINTPRODSTRUCT ABR";
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
/* 131 */     return "1.0";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MAINTPRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */