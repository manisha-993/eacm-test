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
/*    */ public class RdhPrdl_asmod
/*    */ {
/*    */   @SerializedName("SPOSI")
/*    */   private String sposi;
/*    */   @SerializedName("DATUV")
/*    */   private String datuv;
/*    */   @SerializedName("DATUB")
/*    */   private String datub;
/*    */   @SerializedName("LVORM")
/*    */   private String lvorm;
/*    */   
/*    */   public String getSposi() {
/* 26 */     return this.sposi;
/*    */   }
/*    */   
/*    */   public void setSposi(String paramString) {
/* 30 */     this.sposi = paramString;
/*    */   }
/*    */   
/*    */   public String getDatuv() {
/* 34 */     return this.datuv;
/*    */   }
/*    */   
/*    */   public void setDatuv(String paramString) {
/* 38 */     this.datuv = paramString;
/*    */   }
/*    */   
/*    */   public String getDatub() {
/* 42 */     return this.datub;
/*    */   }
/*    */   
/*    */   public void setDatub(String paramString) {
/* 46 */     this.datub = paramString;
/*    */   }
/*    */   
/*    */   public String getLvorm() {
/* 50 */     return this.lvorm;
/*    */   }
/*    */   
/*    */   public void setLvorm(String paramString) {
/* 54 */     this.lvorm = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhPrdl_asmod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */