/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ public class GARSYMDMSalesBom_STPO {
/*    */   @SerializedName("MATNR")
/*    */   private String matnr;
/*    */   @SerializedName("WERKS")
/*    */   private String werks;
/*    */   @SerializedName("IDNRK")
/*    */   private String idnrk;
/*    */   
/*    */   public String getMatnr() {
/* 14 */     return this.matnr;
/*    */   }
/*    */   
/*    */   public void setMatnr(String paramString) {
/* 18 */     this.matnr = paramString;
/*    */   }
/*    */   
/*    */   public String getWerks() {
/* 22 */     return this.werks;
/*    */   }
/*    */   
/*    */   public void setWerks(String paramString) {
/* 26 */     this.werks = paramString;
/*    */   }
/*    */   
/*    */   public String getIdnrk() {
/* 30 */     return this.idnrk;
/*    */   }
/*    */   
/*    */   public void setIdnrk(String paramString) {
/* 34 */     this.idnrk = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\GARSYMDMSalesBom_STPO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */