/*    */ package COM.ibm.eannounce.abr.sg.translation;
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SVCMOD_AddTranslationProcess
/*    */   extends AddTranslationProcess
/*    */ {
/*    */   public boolean isValid(EntityHandler paramEntityHandler, Date paramDate) throws Exception {
/* 10 */     Map map = paramEntityHandler.getAttributes(new String[] { "STATUS", "ANNDATE", "WTHDRWEFFCTVDATE" });
/*    */ 
/*    */     
/* 13 */     if (!isStatusFinal(map, "STATUS")) {
/* 14 */       return false;
/*    */     }
/*    */     
/* 17 */     if (!isAfter(map, "ANNDATE", paramDate)) {
/* 18 */       return false;
/*    */     }
/* 20 */     if (!isBefore(map, "WTHDRWEFFCTVDATE", paramDate)) {
/* 21 */       return false;
/*    */     }
/* 23 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\SVCMOD_AddTranslationProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */