/*    */ package COM.ibm.eannounce.abr.sg.translation;
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LSEOBUNDLE_AddTranslationProcess
/*    */   extends AddTranslationProcess
/*    */ {
/*    */   public boolean isValid(EntityHandler paramEntityHandler, Date paramDate) throws Exception {
/* 10 */     Map map = paramEntityHandler.getAttributes(new String[] { "STATUS", "BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT" });
/*    */ 
/*    */     
/* 13 */     if (!isStatusFinal(map, "STATUS")) {
/* 14 */       return false;
/*    */     }
/* 16 */     if (!isAfter(map, "BUNDLPUBDATEMTRGT", paramDate)) {
/* 17 */       return false;
/*    */     }
/* 19 */     if (!isBefore(map, "BUNDLUNPUBDATEMTRGT", paramDate)) {
/* 20 */       return false;
/*    */     }
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\LSEOBUNDLE_AddTranslationProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */