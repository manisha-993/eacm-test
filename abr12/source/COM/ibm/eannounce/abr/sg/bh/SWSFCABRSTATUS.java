/*    */ package COM.ibm.eannounce.abr.sg.bh;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class SWSFCABRSTATUS extends DQABRSTATUS {
/*    */   protected boolean isVEneeded(String paramString) {
/* 10 */     return false;
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
/*    */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
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
/*    */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 36 */     addDebug(" status now final");
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
/*    */ 
/*    */   
/*    */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 56 */     addDebug("No checking required");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 67 */     return "SWSFEATURE ABR";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getABRVersion() {
/* 77 */     return "1.0";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SWSFCABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */