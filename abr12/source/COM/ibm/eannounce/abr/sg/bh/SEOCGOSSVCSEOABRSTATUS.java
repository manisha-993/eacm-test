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
/*    */ 
/*    */ 
/*    */ public class SEOCGOSSVCSEOABRSTATUS
/*    */   extends DQABRSTATUS
/*    */ {
/*    */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 38 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", getRFRQueuedValue("COMPATGENABR"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 47 */     addDebug(" status now final");
/* 48 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", getQueuedValue("COMPATGENABR"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isVEneeded(String paramString) {
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 60 */     addDebug("No checking required");
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 64 */     return "SEOCGOSSVCSEO ABR";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getABRVersion() {
/* 73 */     return "$Revision: 1.3 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SEOCGOSSVCSEOABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */