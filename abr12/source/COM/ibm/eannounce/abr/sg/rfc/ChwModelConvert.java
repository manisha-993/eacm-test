/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.ModelConvertTable;
/*    */ import COM.ibm.eannounce.abr.util.RfcConfigProperties;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwModelConvert
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("TBL_MODELCONVERT")
/*    */   private List<ModelConvertTable> tbl_modelconvert;
/*    */   
/*    */   public ChwModelConvert(MODELCONVERT paramMODELCONVERT) {
/* 19 */     super(paramMODELCONVERT.getTOMACHTYPE().equals(paramMODELCONVERT.getFROMMACHTYPE()) ? (paramMODELCONVERT.getTOMACHTYPE() + "UPG") : (paramMODELCONVERT.getTOMACHTYPE() + "MTC"), "RDH_YMDMFC_MTC"
/*    */         
/* 21 */         .toLowerCase(), null);
/* 22 */     this.pims_identity = "H";
/*    */     
/* 24 */     List<AVAILABILITYLIST> list = paramMODELCONVERT.getAVAILABILITYLIST();
/*    */     
/* 26 */     if (list != null && !list.isEmpty()) {
/*    */       
/* 28 */       List<AVAILABILITYELEMENT> list1 = ((AVAILABILITYLIST)list.get(0)).getAVAILABILITYELEMENT();
/* 29 */       if (list1 != null && !list1.isEmpty()) {
/* 30 */         for (AVAILABILITYELEMENT aVAILABILITYELEMENT : list1) {
/* 31 */           if (RfcConfigProperties.getCountry(aVAILABILITYELEMENT.getCOUNTRY_FC()) == null)
/*    */             continue; 
/* 33 */           ModelConvertTable modelConvertTable = new ModelConvertTable();
/* 34 */           modelConvertTable.setMODELUPGRADEENTITYTYPE(paramMODELCONVERT.getMODELUPGRADEENTITYTYPE());
/* 35 */           modelConvertTable.setMODELUPGRADEENTITYID(paramMODELCONVERT.getMODELUPGRADEENTITYID());
/* 36 */           modelConvertTable.setFROMMACHTYPE(paramMODELCONVERT.getFROMMACHTYPE());
/* 37 */           modelConvertTable.setTOMACHTYPE(paramMODELCONVERT.getTOMACHTYPE());
/* 38 */           modelConvertTable.setCOUNTRY_FC(RfcConfigProperties.getCountry(aVAILABILITYELEMENT.getCOUNTRY_FC()));
/* 39 */           modelConvertTable.setAVAILABILITYACTION(aVAILABILITYELEMENT.getAVAILABILITYACTION());
/*    */           
/* 41 */           modelConvertTable.setANNDATE(aVAILABILITYELEMENT.getANNDATE().replaceAll("-", ""));
/* 42 */           modelConvertTable.setFIRSTORDER(aVAILABILITYELEMENT.getFIRSTORDER().replaceAll("-", ""));
/* 43 */           modelConvertTable.setPLANNEDAVAILABILITY(aVAILABILITYELEMENT.getPLANNEDAVAILABILITY().replaceAll("-", ""));
/* 44 */           modelConvertTable.setPUBFROM(aVAILABILITYELEMENT.getPUBFROM().replaceAll("-", ""));
/* 45 */           modelConvertTable.setPUBTO(aVAILABILITYELEMENT.getPUBTO().replaceAll("-", ""));
/* 46 */           modelConvertTable.setWDANNDATE(aVAILABILITYELEMENT.getWDANNDATE().replaceAll("-", ""));
/* 47 */           modelConvertTable.setLASTORDER(aVAILABILITYELEMENT.getLASTORDER().replaceAll("-", ""));
/* 48 */           this.tbl_modelconvert.add(modelConvertTable);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 59 */     this.tbl_modelconvert = new ArrayList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwModelConvert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */