/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhDepd_depdescr
/*    */ {
/*    */   @SerializedName("LANGUAGE")
/*    */   private String language;
/*    */   @SerializedName("DESCRIPT")
/*    */   private String descript;
/*    */   
/*    */   public String getLanguage() {
/* 17 */     return this.language;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLanguage(String paramString) {
/* 24 */     this.language = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescript() {
/* 31 */     return this.descript;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDescript(String paramString) {
/* 38 */     this.descript = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhDepd_depdescr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */