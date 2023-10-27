/*    */ package COM.ibm.eannounce.abr.sg.translation;
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SWFEATURE_AddTranslationProcess
/*    */   extends AddTranslationProcess
/*    */ {
/*    */   public boolean isValid(EntityHandler paramEntityHandler, Date paramDate) throws Exception {
/* 10 */     Map map = paramEntityHandler.getAttributes(new String[] { "STATUS", "WITHDRAWDATEEFF_T" });
/*    */ 
/*    */     
/* 13 */     if (!isStatusFinal(map, "STATUS")) {
/* 14 */       return false;
/*    */     }
/*    */     
/* 17 */     if (!isBefore(map, "WITHDRAWDATEEFF_T", paramDate)) {
/* 18 */       return false;
/*    */     }
/* 20 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\SWFEATURE_AddTranslationProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */