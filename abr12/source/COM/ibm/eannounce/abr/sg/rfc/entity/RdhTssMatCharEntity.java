/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhTssMatCharEntity
/*    */ {
/*    */   @SerializedName("MATERIAL_NUMBER")
/*    */   private String material_number;
/*    */   @SerializedName("FEATURE_ID")
/*    */   private String feature_id;
/*    */   @SerializedName("FEATURE_NAME")
/*    */   private String feature_name;
/*    */   @SerializedName("MANOPT_FLAG")
/*    */   private String manopt_flag;
/*    */   @SerializedName("BUKRS")
/*    */   private String bukrs;
/*    */   @SerializedName("WITHDRAWDATE")
/*    */   private String withdrawdate;
/*    */   @SerializedName("WTHDRWEFFCTVDATE")
/*    */   private String wthdrweffctvdate;
/*    */   @SerializedName("EFFECTIVE_DATE")
/*    */   private String effective_date;
/*    */   @SerializedName("END_DATE")
/*    */   private String end_date;
/*    */   
/*    */   public String getMaterial_number() {
/* 29 */     return this.material_number;
/*    */   }
/*    */   public void setMaterial_number(String paramString) {
/* 32 */     this.material_number = paramString;
/*    */   }
/*    */   public String getFeature_id() {
/* 35 */     return this.feature_id;
/*    */   }
/*    */   public void setFeature_id(String paramString) {
/* 38 */     this.feature_id = paramString;
/*    */   }
/*    */   public String getFeature_name() {
/* 41 */     return this.feature_name;
/*    */   }
/*    */   public void setFeature_name(String paramString) {
/* 44 */     this.feature_name = paramString;
/*    */   }
/*    */   public String getManopt_flag() {
/* 47 */     return this.manopt_flag;
/*    */   }
/*    */   public void setManopt_flag(String paramString) {
/* 50 */     this.manopt_flag = paramString;
/*    */   }
/*    */   public String getBukrs() {
/* 53 */     return this.bukrs;
/*    */   }
/*    */   public void setBukrs(String paramString) {
/* 56 */     this.bukrs = paramString;
/*    */   }
/*    */   public String getWithdrawdate() {
/* 59 */     return this.withdrawdate;
/*    */   }
/*    */   public void setWithdrawdate(String paramString) {
/* 62 */     this.withdrawdate = paramString;
/*    */   }
/*    */   public String getWthdrweffctvdate() {
/* 65 */     return this.wthdrweffctvdate;
/*    */   }
/*    */   public void setWthdrweffctvdate(String paramString) {
/* 68 */     this.wthdrweffctvdate = paramString;
/*    */   }
/*    */   public String getEffective_date() {
/* 71 */     return this.effective_date;
/*    */   }
/*    */   public void setEffective_date(String paramString) {
/* 74 */     this.effective_date = paramString;
/*    */   }
/*    */   public String getEnd_date() {
/* 77 */     return this.end_date;
/*    */   }
/*    */   public void setEnd_date(String paramString) {
/* 80 */     this.end_date = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhTssMatCharEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */