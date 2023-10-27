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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CATNAVABRSTATUS
/*    */   extends DQABRSTATUS
/*    */ {
/*    */   protected boolean isVEneeded(String paramString) {
/* 36 */     return false;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 54 */     setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
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
/* 67 */     addDebug(" status now final");
/* 68 */     setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 77 */     addDebug("No checking required");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 87 */     return "CATNAV ABR.";
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
/* 98 */     return "$Revision: 1.2 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\CATNAVABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */