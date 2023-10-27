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
/*    */ public class RdhChar_chv_descr
/*    */ {
/*    */   @SerializedName("CHARACT")
/*    */   private String charact;
/*    */   @SerializedName("FLDELETE")
/*    */   private String fldelete;
/*    */   @SerializedName("VALUE")
/*    */   private String value;
/*    */   @SerializedName("LANGUAGE")
/*    */   private String language;
/*    */   @SerializedName("VALDESCR")
/*    */   private String valdescr;
/*    */   
/*    */   public String getCharact() {
/* 26 */     return this.charact;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCharact(String paramString) {
/* 33 */     this.charact = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFldelete() {
/* 40 */     return this.fldelete;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFldelete(String paramString) {
/* 47 */     this.fldelete = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 54 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(String paramString) {
/* 61 */     this.value = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLanguage() {
/* 68 */     return this.language;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLanguage(String paramString) {
/* 75 */     this.language = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValdescr() {
/* 82 */     return this.valdescr;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValdescr(String paramString) {
/* 89 */     if (paramString != null && paramString.length() > 30)
/*    */     {
/* 91 */       paramString = paramString.substring(0, 30);
/*    */     }
/* 93 */     this.valdescr = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhChar_chv_descr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */