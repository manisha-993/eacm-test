/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ public class Rdhzdmprktbl
/*    */ {
/*    */   @SerializedName("ZDMCLASS")
/*    */   private String zdmclass;
/*    */   @SerializedName("ZDMRELNUM")
/*    */   private String zdmrelnum;
/*    */   @SerializedName("ZDMSTATUS")
/*    */   private String zdmstatus;
/*    */   
/*    */   public String getZdmclass() {
/* 15 */     return this.zdmclass;
/*    */   }
/*    */   public void setZdmclass(String paramString) {
/* 18 */     this.zdmclass = paramString;
/*    */   }
/*    */   public String getZdmrelnum() {
/* 21 */     return this.zdmrelnum;
/*    */   }
/*    */   public void setZdmrelnum(String paramString) {
/* 24 */     this.zdmrelnum = paramString;
/*    */   }
/*    */   public String getZdmstatus() {
/* 27 */     return this.zdmstatus;
/*    */   }
/*    */   public void setZdmstatus(String paramString) {
/* 30 */     this.zdmstatus = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\Rdhzdmprktbl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */