/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import com.ibm.pprds.epimshw.util.RfcLogger;
/*    */ 
/*    */ public class BasicRfcLogger
/*    */   implements RfcLogger {
/*    */   private RFCABRSTATUS abr;
/*    */   
/*    */   public BasicRfcLogger(RFCABRSTATUS paramRFCABRSTATUS) {
/* 10 */     this.abr = paramRFCABRSTATUS;
/*    */   }
/*    */ 
/*    */   
/*    */   public void info(String paramString) {
/* 15 */     this.abr.addDebug(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public void warn(String paramString) {
/* 20 */     this.abr.addDebug(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public void error(String paramString) {
/* 25 */     this.abr.addDebug(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\BasicRfcLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */