/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhClas_cla_descr
/*    */ {
/*    */   @SerializedName("CLASS")
/*    */   private String cLASS;
/*    */   @SerializedName("CLASS_TYPE")
/*    */   private String class_type;
/*    */   @SerializedName("LANGUAGE")
/*    */   private String language;
/*    */   @SerializedName("CATCHWORD")
/*    */   private String catchword;
/*    */   
/*    */   public String get_class() {
/* 19 */     return this.cLASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public void set_class(String paramString) {
/* 24 */     this.cLASS = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getClass_type() {
/* 29 */     return this.class_type;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setClass_type(String paramString) {
/* 34 */     this.class_type = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLanguage() {
/* 39 */     return this.language;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLanguage(String paramString) {
/* 44 */     this.language = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCatchword() {
/* 49 */     return this.catchword;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCatchword(String paramString) {
/* 54 */     this.catchword = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhClas_cla_descr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */