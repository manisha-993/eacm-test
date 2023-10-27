/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhDepd_depdat
/*    */ {
/*    */   @SerializedName("DEP_TYPE")
/*    */   private String dep_type;
/*    */   @SerializedName("STATUS")
/*    */   private String status;
/*    */   
/*    */   public String getDep_type() {
/* 17 */     return this.dep_type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDep_type(String paramString) {
/* 24 */     this.dep_type = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStatus() {
/* 31 */     return this.status;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStatus(String paramString) {
/* 38 */     this.status = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhDepd_depdat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */