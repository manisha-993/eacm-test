/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ public class ChwBulkYMDMProd_FEATURE
/*    */ {
/*    */   @SerializedName("ENTITYTYPE")
/*    */   private String entityType;
/*    */   @SerializedName("ENTITYID")
/*    */   private String entityID;
/*    */   @SerializedName("FEATURECODE")
/*    */   private String featureCode;
/*    */   
/*    */   public String getEntityType() {
/* 15 */     return this.entityType;
/*    */   }
/*    */   
/*    */   public void setEntityType(String paramString) {
/* 19 */     this.entityType = paramString;
/*    */   }
/*    */   
/*    */   public String getEntityID() {
/* 23 */     return this.entityID;
/*    */   }
/*    */   
/*    */   public void setEntityID(String paramString) {
/* 27 */     this.entityID = paramString;
/*    */   }
/*    */   
/*    */   public String getFeatureCode() {
/* 31 */     return this.featureCode;
/*    */   }
/*    */   
/*    */   public void setFeatureCode(String paramString) {
/* 35 */     this.featureCode = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\ChwBulkYMDMProd_FEATURE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */