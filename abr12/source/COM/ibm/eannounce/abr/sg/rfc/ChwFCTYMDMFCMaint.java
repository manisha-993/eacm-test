/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_FCTRANSACTION;
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
/*    */ public class ChwFCTYMDMFCMaint
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("TBL_FCTRANSACTION")
/*    */   private List<RdhChwFcProd_FCTRANSACTION> tbl_fctransaction;
/*    */   
/*    */   public ChwFCTYMDMFCMaint(FCTRANSACTION paramFCTRANSACTION) {
/* 25 */     super(paramFCTRANSACTION.getFROMMACHTYPE() + paramFCTRANSACTION.getTOMACHTYPE() + paramFCTRANSACTION.getTOFEATURECODE(), "RDH_YMDMFC_FCT"
/* 26 */         .toLowerCase(), null);
/* 27 */     this.pims_identity = "H";
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
/*    */     
/* 59 */     RdhChwFcProd_FCTRANSACTION rdhChwFcProd_FCTRANSACTION = new RdhChwFcProd_FCTRANSACTION();
/* 60 */     rdhChwFcProd_FCTRANSACTION.setFROMMACHTYPE(paramFCTRANSACTION.getFROMMACHTYPE());
/* 61 */     rdhChwFcProd_FCTRANSACTION.setTOMACHTYPE(paramFCTRANSACTION.getTOMACHTYPE());
/* 62 */     rdhChwFcProd_FCTRANSACTION.setFROMMODEL(paramFCTRANSACTION.getFROMMODEL());
/* 63 */     rdhChwFcProd_FCTRANSACTION.setTOMODEL(paramFCTRANSACTION.getTOMODEL());
/* 64 */     rdhChwFcProd_FCTRANSACTION.setFROMFEATURECODE(paramFCTRANSACTION.getFROMFEATURECODE());
/* 65 */     rdhChwFcProd_FCTRANSACTION.setTOFEATURECODE(paramFCTRANSACTION.getTOFEATURECODE());
/* 66 */     rdhChwFcProd_FCTRANSACTION.setFEATURETRANSCAT(paramFCTRANSACTION.getFEATURETRANSACTIONCATEGORY());
/* 67 */     if ("Yes".equals(paramFCTRANSACTION.getRETURNEDPARTSMES())) {
/* 68 */       rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES("1");
/* 69 */     } else if ("Feature conversion only".equals(paramFCTRANSACTION.getRETURNEDPARTSMES())) {
/* 70 */       rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES("1");
/* 71 */     } else if (paramFCTRANSACTION.getRETURNEDPARTSMES() == null || "".equals(paramFCTRANSACTION.getRETURNEDPARTSMES())) {
/* 72 */       rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES("");
/*    */     } else {
/* 74 */       rdhChwFcProd_FCTRANSACTION.setRETURNEDPARTSMES("0");
/*    */     } 
/* 76 */     this.tbl_fctransaction.add(rdhChwFcProd_FCTRANSACTION);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 84 */     this.tbl_fctransaction = new ArrayList<>();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwFCTYMDMFCMaint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */