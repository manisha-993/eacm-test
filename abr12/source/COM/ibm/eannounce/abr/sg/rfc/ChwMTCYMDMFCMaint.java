/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_MODELCONVERT;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwMTCYMDMFCMaint
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("TBL_MODELCONVERT")
/*    */   private List<RdhChwFcProd_MODELCONVERT> tbl_modelconvert;
/*    */   
/*    */   public ChwMTCYMDMFCMaint(MODELCONVERT paramMODELCONVERT) {
/* 19 */     super((paramMODELCONVERT.getFROMMACHTYPE() == paramMODELCONVERT.getTOMACHTYPE()) ? (paramMODELCONVERT.getTOMACHTYPE() + "UPG") : (paramMODELCONVERT.getTOMACHTYPE() + "MTC"), "RDH_YMDMFC_MTC"
/* 20 */         .toLowerCase(), null);
/* 21 */     this.pims_identity = "H";
/*    */     
/* 23 */     if (paramMODELCONVERT.getAVAILABILITYLIST() != null && !paramMODELCONVERT.getAVAILABILITYLIST().isEmpty())
/*    */     {
/* 25 */       for (AVAILABILITYLIST aVAILABILITYLIST : paramMODELCONVERT.getAVAILABILITYLIST()) {
/*    */         
/* 27 */         if (aVAILABILITYLIST.getAVAILABILITYELEMENT() != null && !aVAILABILITYLIST.getAVAILABILITYELEMENT().isEmpty())
/*    */         {
/* 29 */           for (AVAILABILITYELEMENT aVAILABILITYELEMENT : aVAILABILITYLIST.getAVAILABILITYELEMENT()) {
/*    */             
/* 31 */             RdhChwFcProd_MODELCONVERT rdhChwFcProd_MODELCONVERT = new RdhChwFcProd_MODELCONVERT();
/* 32 */             rdhChwFcProd_MODELCONVERT.setMODELUPGRADEENTITYTYPE(paramMODELCONVERT.getMODELUPGRADEENTITYTYPE());
/* 33 */             rdhChwFcProd_MODELCONVERT.setMODELUPGRADEENTITYID(paramMODELCONVERT.getMODELUPGRADEENTITYID());
/* 34 */             rdhChwFcProd_MODELCONVERT.setFROMMACHTYPE(paramMODELCONVERT.getFROMMACHTYPE());
/* 35 */             rdhChwFcProd_MODELCONVERT.setTOMACHTYPE(paramMODELCONVERT.getTOMACHTYPE());
/* 36 */             rdhChwFcProd_MODELCONVERT.setCOUNTRY_FC(aVAILABILITYELEMENT.getCOUNTRY_FC());
/* 37 */             rdhChwFcProd_MODELCONVERT.setAVAILABILITYACTION(aVAILABILITYELEMENT.getAVAILABILITYACTION());
/* 38 */             rdhChwFcProd_MODELCONVERT.setANNDATE(aVAILABILITYELEMENT.getANNDATE().replaceAll("-", ""));
/* 39 */             rdhChwFcProd_MODELCONVERT.setFIRSTORDER(aVAILABILITYELEMENT.getFIRSTORDER().replaceAll("-", ""));
/* 40 */             rdhChwFcProd_MODELCONVERT.setPLANNEDAVAILABILITY(aVAILABILITYELEMENT.getPLANNEDAVAILABILITY().replaceAll("-", ""));
/* 41 */             rdhChwFcProd_MODELCONVERT.setWDANNDATE(aVAILABILITYELEMENT.getWDANNDATE().replaceAll("-", ""));
/* 42 */             rdhChwFcProd_MODELCONVERT.setLASTORDER(aVAILABILITYELEMENT.getLASTORDER().replaceAll("-", ""));
/* 43 */             rdhChwFcProd_MODELCONVERT.setPUBFROM(aVAILABILITYELEMENT.getPUBFROM().replaceAll("-", ""));
/* 44 */             rdhChwFcProd_MODELCONVERT.setPUBTO(aVAILABILITYELEMENT.getPUBTO().replaceAll("-", ""));
/* 45 */             this.tbl_modelconvert.add(rdhChwFcProd_MODELCONVERT);
/*    */           } 
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 56 */     this.tbl_modelconvert = new ArrayList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 61 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwMTCYMDMFCMaint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */