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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwBapiClassCharRead
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("OBJ_ID")
/*    */   private String obj_id;
/*    */   @SerializedName("FEATURE_CODE")
/*    */   private String feature_code;
/*    */   @SerializedName("TARGET_INDC")
/*    */   private String target_indc;
/*    */   @SerializedName("CHAR_TYPE")
/*    */   private String char_type;
/*    */   private String suffix;
/*    */   
/*    */   public String getSuffix() {
/* 38 */     return this.suffix;
/*    */   }
/*    */   
/*    */   public void setSuffix(String paramString) {
/* 42 */     this.suffix = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ChwBapiClassCharRead(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 48 */     super(paramString1, "BAPI_CLASS_CHAR_READ".toLowerCase(), null);
/* 49 */     this.pims_identity = "H";
/* 50 */     this.obj_id = paramString1;
/* 51 */     this.default_mandt = "10H";
/*    */     
/* 53 */     this.feature_code = paramString2;
/* 54 */     this.target_indc = paramString3;
/* 55 */     this.char_type = paramString4;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 63 */     if (checkFieldsNotEmplyOrNull(this.feature_code, "feature_code") && 
/* 64 */       checkFieldsNotEmplyOrNull(this.target_indc, "target_indc")) {
/* 65 */       return checkFieldsNotEmplyOrNull(this.char_type, "char_type");
/*    */     }
/*    */     
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveRfcResults(OutputData paramOutputData) {
/* 88 */     super.saveRfcResults(paramOutputData);
/* 89 */     HashMap hashMap = paramOutputData.getRETURN_OBJ();
/* 90 */     if (hashMap != null) {
/*    */       
/* 92 */       HashMap hashMap1 = (HashMap)hashMap.get("RETURNMAP");
/* 93 */       if (hashMap1 != null) {
/*    */         
/* 95 */         String str = (String)hashMap1.get("SUFFIX");
/* 96 */         if (str != null)
/*    */         {
/* 98 */           this.suffix = str;
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwBapiClassCharRead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */