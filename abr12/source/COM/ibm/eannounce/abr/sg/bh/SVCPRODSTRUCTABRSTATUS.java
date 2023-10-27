/*    */ package COM.ibm.eannounce.abr.sg.bh;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SVCPRODSTRUCTABRSTATUS
/*    */   extends DQABRSTATUS
/*    */ {
/*    */   protected boolean isVEneeded(String paramString) {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 45 */     EntityItem entityItem = this.m_elist.getEntityGroup("SVCMOD").getEntityItem(0);
/* 46 */     setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"), entityItem);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 59 */     addDebug(" status now final");
/*    */     
/* 61 */     EntityItem entityItem = this.m_elist.getEntityGroup("SVCMOD").getEntityItem(0);
/* 62 */     setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"), entityItem);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 72 */     addDebug("No checking required");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 82 */     return "SLEORGNPLNTCODE ABR.";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getABRVersion() {
/* 93 */     return "$Revision: 1.7 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SVCPRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */