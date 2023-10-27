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
/*    */ public class RdhBomc_csap_mbom
/*    */ {
/*    */   @SerializedName("MATNR")
/*    */   private String matnr;
/*    */   @SerializedName("WERKS")
/*    */   private String werks;
/*    */   @SerializedName("STLAN")
/*    */   private String stlan;
/*    */   @SerializedName("STLAL")
/*    */   private String stlal;
/*    */   @SerializedName("DATUV")
/*    */   private String datuv;
/*    */   
/*    */   public String getMatnr() {
/* 28 */     return this.matnr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMatnr(String paramString) {
/* 35 */     this.matnr = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getWerks() {
/* 42 */     return this.werks;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWerks(String paramString) {
/* 49 */     this.werks = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStlan() {
/* 56 */     return this.stlan;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStlan(String paramString) {
/* 63 */     this.stlan = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDatuv() {
/* 70 */     return this.datuv;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDatuv(String paramString) {
/* 77 */     this.datuv = paramString;
/*    */   }
/*    */   
/*    */   public String getStlal() {
/* 81 */     return this.stlal;
/*    */   }
/*    */   
/*    */   public void setStlal(String paramString) {
/* 85 */     this.stlal = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhBomc_csap_mbom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */