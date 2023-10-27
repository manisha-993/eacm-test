/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhDepa_chval_gdep
/*    */ {
/*    */   @SerializedName("CHARACT")
/*    */   private String charact;
/*    */   @SerializedName("VALUE")
/*    */   private String value;
/*    */   @SerializedName("DEPENDENCY")
/*    */   private String dependency;
/*    */   
/*    */   public String getCharact() {
/* 20 */     return this.charact;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCharact(String paramString) {
/* 27 */     this.charact = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 34 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(String paramString) {
/* 41 */     this.value = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDependency() {
/* 48 */     return this.dependency;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDependency(String paramString) {
/* 55 */     this.dependency = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhDepa_chval_gdep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */