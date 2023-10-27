/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhChar_char_descr
/*    */ {
/*    */   @SerializedName("CHARACT")
/*    */   private String charact;
/*    */   @SerializedName("CHDESCR")
/*    */   private String chdescr;
/*    */   @SerializedName("LANGUAGE")
/*    */   private String language;
/*    */   
/*    */   public String getCharact() {
/* 17 */     return this.charact;
/*    */   }
/*    */   
/*    */   public void setCharact(String paramString) {
/* 21 */     this.charact = paramString;
/*    */   }
/*    */   
/*    */   public String getChdescr() {
/* 25 */     return this.chdescr;
/*    */   }
/*    */   
/*    */   public void setChdescr(String paramString) {
/* 29 */     this.chdescr = paramString;
/*    */   }
/*    */   
/*    */   public String getLanguage() {
/* 33 */     return this.language;
/*    */   }
/*    */   
/*    */   public void setLanguage(String paramString) {
/* 37 */     this.language = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhChar_char_descr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */