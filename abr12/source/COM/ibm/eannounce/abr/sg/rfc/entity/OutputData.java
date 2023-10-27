/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OutputData
/*    */ {
/*    */   public static final int ERROR_MSG_LIMIT = 320;
/*    */   private int RFCRC;
/*    */   private String ERROR_TEXT;
/*    */   private HashMap<String, HashMap<String, String>> RETURN_OBJ;
/*    */   private HashMap<String, List<HashMap<String, String>>> RETURN_MULTIPLE_OBJ;
/*    */   private String FL_WARNING;
/*    */   
/*    */   public String getFL_WARNING() {
/* 24 */     return this.FL_WARNING;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFL_WARNING(String paramString) {
/* 29 */     this.FL_WARNING = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRFCRC() {
/* 34 */     return this.RFCRC;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRFCRC(int paramInt) {
/* 39 */     this.RFCRC = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getERROR_TEXT() {
/* 44 */     if (this.ERROR_TEXT.length() > 320)
/* 45 */       return this.ERROR_TEXT.substring(0, 320); 
/* 46 */     return this.ERROR_TEXT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setERROR_TEXT(String paramString) {
/* 51 */     this.ERROR_TEXT = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public HashMap<String, HashMap<String, String>> getRETURN_OBJ() {
/* 56 */     return this.RETURN_OBJ;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRETURN_OBJ(HashMap<String, HashMap<String, String>> paramHashMap) {
/* 61 */     this.RETURN_OBJ = paramHashMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public HashMap<String, List<HashMap<String, String>>> getRETURN_MULTIPLE_OBJ() {
/* 66 */     return this.RETURN_MULTIPLE_OBJ;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRETURN_MULTIPLE_OBJ(HashMap<String, List<HashMap<String, String>>> paramHashMap) {
/* 71 */     this.RETURN_MULTIPLE_OBJ = paramHashMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\OutputData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */