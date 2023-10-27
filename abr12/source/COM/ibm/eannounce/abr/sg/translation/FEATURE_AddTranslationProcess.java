/*    */ package COM.ibm.eannounce.abr.sg.translation;
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class FEATURE_AddTranslationProcess
/*    */   extends AddTranslationProcess
/*    */ {
/*    */   public boolean isValid(EntityHandler paramEntityHandler, Date paramDate) throws Exception {
/* 11 */     Map map = paramEntityHandler.getAttributes(new String[] { "STATUS", "FIRSTANNDATE", "WITHDRAWDATEEFF_T" });
/*    */ 
/*    */     
/* 14 */     if (!isStatusFinal(map, "STATUS")) {
/* 15 */       return false;
/*    */     }
/* 17 */     if (!isAfter(map, "FIRSTANNDATE", paramDate)) {
/* 18 */       return false;
/*    */     }
/* 20 */     if (!isBefore(map, "WITHDRAWDATEEFF_T", paramDate)) {
/* 21 */       return false;
/*    */     }
/* 23 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\FEATURE_AddTranslationProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */