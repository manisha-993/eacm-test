/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ public class ChwReadSalesBom
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("MATERIAL")
/*    */   private String material;
/*    */   @SerializedName("PLANT")
/*    */   private String plant;
/*    */   @SerializedName("BOM_USAGE")
/*    */   private String bom_usage;
/*    */   
/*    */   public ChwReadSalesBom(String paramString1, String paramString2) {
/* 17 */     super(null, "CSAP_MAT_BOM_READ".toLowerCase(), null);
/* 18 */     this.pims_identity = "H";
/* 19 */     this.material = paramString1;
/* 20 */     this.plant = paramString2;
/* 21 */     this.bom_usage = "5";
/* 22 */     this.default_mandt = "10H";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwReadSalesBom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */