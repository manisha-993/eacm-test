/*    */ package COM.ibm.eannounce.abr.sg.translation;
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class FB_AddTranslationProcess
/*    */   extends AddTranslationProcess
/*    */ {
/*    */   public boolean isValid(EntityHandler paramEntityHandler, Date paramDate) throws Exception {
/* 10 */     Map map = paramEntityHandler.getAttributes(new String[] { "FBSTATUS", "PUBFROM", "PUBTO" });
/*    */     
/* 12 */     if (!isStatusFinal(map, "FBSTATUS")) {
/* 13 */       return false;
/*    */     }
/* 15 */     if (!isAfter(map, "PUBFROM", paramDate)) {
/* 16 */       return false;
/*    */     }
/* 18 */     if (!isBefore(map, "PUBTO", paramDate)) {
/* 19 */       return false;
/*    */     }
/* 21 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\FB_AddTranslationProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */