/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.OutputData;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.HashMap;
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
/*    */ public class ChwGetMaxClass300Suffix
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("CLASS_NAME_STEM")
/*    */   private String class_name_stem;
/*    */   @SerializedName("MAX_SUFFIX")
/*    */   private int max_suffix;
/*    */   
/*    */   public ChwGetMaxClass300Suffix(String paramString1, String paramString2) {
/* 29 */     super(paramString1, "RDH_GET_MAX_CLASS300_SUFFIX"
/* 30 */         .toLowerCase(), null);
/* 31 */     this.pims_identity = "H";
/* 32 */     this.class_name_stem = paramString2;
/* 33 */     this.default_mandt = "10H";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveRfcResults(OutputData paramOutputData) {
/* 47 */     super.saveRfcResults(paramOutputData);
/* 48 */     HashMap hashMap = paramOutputData.getRETURN_OBJ();
/* 49 */     if (hashMap != null) {
/*    */       
/* 51 */       HashMap hashMap1 = (HashMap)hashMap.get("RETURNMAP");
/* 52 */       if (hashMap1 != null) {
/*    */         
/* 54 */         String str = (String)hashMap1.get("MAX_SUFFIX");
/* 55 */         if (str != null)
/*    */         {
/* 57 */           this.max_suffix = Integer.parseInt(str);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMax_suffix() {
/* 68 */     return this.max_suffix;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isValidRfcrc() {
/* 77 */     return (getRfcrc() == 4 || getRfcrc() == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwGetMaxClass300Suffix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */