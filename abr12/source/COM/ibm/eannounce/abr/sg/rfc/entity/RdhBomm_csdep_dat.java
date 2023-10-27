/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhBomm_csdep_dat
/*    */ {
/*    */   @SerializedName("OBJECT_ID")
/*    */   private String object_id;
/*    */   @SerializedName("IDENTIFIER")
/*    */   private String identifier;
/*    */   @SerializedName("DEP_INTERN")
/*    */   private String dep_intern;
/*    */   @SerializedName("STATUS")
/*    */   private String status;
/*    */   
/*    */   public String getObject_id() {
/* 25 */     return this.object_id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setObject_id(String paramString) {
/* 32 */     this.object_id = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getIdentifier() {
/* 39 */     return this.identifier;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setIdentifier(String paramString) {
/* 46 */     this.identifier = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDep_intern() {
/* 53 */     return this.dep_intern;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDep_intern(String paramString) {
/* 60 */     this.dep_intern = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStatus() {
/* 67 */     return this.status;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStatus(String paramString) {
/* 74 */     this.status = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhBomm_csdep_dat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */