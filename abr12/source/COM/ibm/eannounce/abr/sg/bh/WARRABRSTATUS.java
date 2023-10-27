/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WARRABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   protected boolean isVEneeded(String paramString) {
/*  33 */     return false;
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  48 */     if (doR10processing()) {
/*  49 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
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
/*  67 */     addDebug(" status now final");
/*  68 */     if (doR10processing()) {
/*  69 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  79 */     addDebug("No checking required");
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
/*  90 */     return "WARR ABR.";
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
/* 101 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\WARRABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */