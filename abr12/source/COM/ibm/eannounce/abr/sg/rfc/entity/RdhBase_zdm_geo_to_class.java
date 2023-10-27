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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhBase_zdm_geo_to_class
/*    */ {
/*    */   @SerializedName("Z_GEO")
/*    */   private String z_geo;
/*    */   @SerializedName("Z_CLASS")
/*    */   private String z_class;
/*    */   
/*    */   public RdhBase_zdm_geo_to_class(String paramString) {
/* 25 */     this.z_geo = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getZ_geo() {
/* 30 */     return this.z_geo;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setZ_geo(String paramString) {
/* 35 */     this.z_geo = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getZ_class() {
/* 43 */     return this.z_class;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setZ_class(String paramString) {
/* 51 */     this.z_class = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhBase_zdm_geo_to_class.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */