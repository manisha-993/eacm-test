/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhClas_cla_ch_atr
/*    */ {
/*    */   @SerializedName("CLASS")
/*    */   private String cLASS;
/*    */   @SerializedName("CLASS_TYPE")
/*    */   private String class_type;
/*    */   @SerializedName("CHARACT")
/*    */   private String charact;
/*    */   
/*    */   public String get_class() {
/* 17 */     return this.cLASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public void set_class(String paramString) {
/* 22 */     this.cLASS = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getClass_type() {
/* 27 */     return this.class_type;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setClass_type(String paramString) {
/* 32 */     this.class_type = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCharact() {
/* 37 */     return this.charact;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCharact(String paramString) {
/* 42 */     this.charact = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhClas_cla_ch_atr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */