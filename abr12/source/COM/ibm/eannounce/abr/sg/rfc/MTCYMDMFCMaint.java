/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.MTCYMDMFCMaint_Model;
/*    */ import COM.ibm.eannounce.abr.util.RfcConfigProperties;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MTCYMDMFCMaint
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("TBL_MODELCONVERT")
/*    */   private List<MTCYMDMFCMaint_Model> tbl_model;
/*    */   
/*    */   public MTCYMDMFCMaint(MODELCONVERT paramMODELCONVERT) {
/* 16 */     super(paramMODELCONVERT.getFROMMACHTYPE() + paramMODELCONVERT.getFROMMODEL() + paramMODELCONVERT.getTOMACHTYPE() + paramMODELCONVERT.getTOMODEL(), "RDH_YMDMFC_MTC"
/* 17 */         .toLowerCase(), null);
/* 18 */     this.pims_identity = "H";
/*    */     
/* 20 */     if (paramMODELCONVERT.getAVAILABILITYLIST() != null && !paramMODELCONVERT.getAVAILABILITYLIST().isEmpty() && (
/* 21 */       (AVAILABILITYLIST)paramMODELCONVERT.getAVAILABILITYLIST().get(0)).getAVAILABILITYELEMENT() != null) {
/* 22 */       for (AVAILABILITYELEMENT aVAILABILITYELEMENT : ((AVAILABILITYLIST)paramMODELCONVERT.getAVAILABILITYLIST().get(0)).getAVAILABILITYELEMENT()) {
/* 23 */         MTCYMDMFCMaint_Model mTCYMDMFCMaint_Model = new MTCYMDMFCMaint_Model();
/* 24 */         mTCYMDMFCMaint_Model.setModelEntitytype(paramMODELCONVERT.getMODELUPGRADEENTITYTYPE());
/* 25 */         mTCYMDMFCMaint_Model.setModelEntityid(paramMODELCONVERT.getMODELUPGRADEENTITYID());
/* 26 */         mTCYMDMFCMaint_Model.setFromMachtype(paramMODELCONVERT.getFROMMACHTYPE());
/* 27 */         mTCYMDMFCMaint_Model.setToMachtype(paramMODELCONVERT.getTOMACHTYPE());
/* 28 */         mTCYMDMFCMaint_Model.setCountry_fc(
/* 29 */             RfcConfigProperties.getCountry(aVAILABILITYELEMENT.getCOUNTRY_FC()));
/* 30 */         mTCYMDMFCMaint_Model.setAvailAction(aVAILABILITYELEMENT.getAVAILABILITYACTION());
/* 31 */         mTCYMDMFCMaint_Model.setAnnDate(getDate(aVAILABILITYELEMENT.getANNDATE()));
/* 32 */         mTCYMDMFCMaint_Model.setFirstOrder(getDate(aVAILABILITYELEMENT.getFIRSTORDER()));
/* 33 */         mTCYMDMFCMaint_Model.setPlanAvail(getDate(aVAILABILITYELEMENT.getPLANNEDAVAILABILITY()));
/* 34 */         mTCYMDMFCMaint_Model.setPubFrom(getDate(aVAILABILITYELEMENT.getPUBFROM()));
/* 35 */         mTCYMDMFCMaint_Model.setPubTo(getDate(aVAILABILITYELEMENT.getPUBTO()));
/* 36 */         mTCYMDMFCMaint_Model.setWdAnndate(getDate(aVAILABILITYELEMENT.getWDANNDATE()));
/* 37 */         mTCYMDMFCMaint_Model.setLastOrder(getDate(aVAILABILITYELEMENT.getLASTORDER()));
/*    */         
/* 39 */         this.tbl_model.add(mTCYMDMFCMaint_Model);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public String getDate(String paramString) {
/* 45 */     if (paramString == null) {
/* 46 */       return paramString;
/*    */     }
/* 48 */     return paramString.replaceAll("-", "");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 54 */     this.tbl_model = new ArrayList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 59 */     return true;
/*    */   }
/*    */   public List<MTCYMDMFCMaint_Model> getTbl_model() {
/* 62 */     return this.tbl_model;
/*    */   }
/*    */   public void setTbl_model(List<MTCYMDMFCMaint_Model> paramList) {
/* 65 */     this.tbl_model = paramList;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\MTCYMDMFCMaint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */