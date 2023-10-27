/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.Rdhzdmprktbl;
/*    */ import COM.ibm.eannounce.abr.util.RfcConfigProperties;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public class UpdateParkStatus
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("ZDMPRKTBL")
/* 27 */   List<Rdhzdmprktbl> rdhzdmprktbls = null;
/*    */   @SerializedName("Z_GEO")
/* 29 */   String zgeo = null;
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
/*    */   public UpdateParkStatus(String paramString1, String paramString2) {
/* 42 */     super(paramString2, "Z_DM_SAP_PARK_STATUS ".toLowerCase(), null);
/* 43 */     this.rdhzdmprktbls = new ArrayList<>();
/* 44 */     Rdhzdmprktbl rdhzdmprktbl = new Rdhzdmprktbl();
/* 45 */     rdhzdmprktbl = new Rdhzdmprktbl();
/* 46 */     rdhzdmprktbl.setZdmclass(paramString1);
/* 47 */     rdhzdmprktbl.setZdmrelnum(paramString2);
/* 48 */     if ("MD_TSS_IERP".equals(paramString1)) {
/* 49 */       rdhzdmprktbl.setZdmstatus(RfcConfigProperties.getTssZdmstatus());
/*    */     } else {
/* 51 */       rdhzdmprktbl.setZdmstatus(RfcConfigProperties.getZdmstatus());
/*    */     } 
/* 53 */     this.rdhzdmprktbls.add(rdhzdmprktbl);
/* 54 */     this.zgeo = "WW";
/*    */     
/* 56 */     if ("MD_CHW_IERP".equals(paramString1)) {
/* 57 */       this.pims_identity = "H";
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 67 */     super.setDefaultValues();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 75 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\UpdateParkStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */