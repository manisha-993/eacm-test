/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_FEATURE;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_Model;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhChwFcProd_TMF;
/*     */ import COM.ibm.eannounce.abr.util.RfcConfigProperties;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RdhChwFcProd
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("TBL_MODEL")
/*     */   private List<RdhChwFcProd_Model> tbl_model;
/*     */   @SerializedName("TBL_TMF_C")
/*     */   private List<RdhChwFcProd_TMF> tbl_tmf_c;
/*     */   @SerializedName("TBL_FEATURE")
/*     */   private List<RdhChwFcProd_FEATURE> tbl_feature;
/*     */   
/*     */   public RdhChwFcProd(MODEL paramMODEL, String paramString) {
/*  24 */     super(paramString, "RDH_YMDMFC_PROD"
/*  25 */         .toLowerCase(), null);
/*  26 */     this.pims_identity = "H";
/*     */     
/*  28 */     if (paramMODEL.getAVAILABILITYLIST() != null && !paramMODEL.getAVAILABILITYLIST().isEmpty())
/*     */     {
/*  30 */       for (AVAILABILITY aVAILABILITY : paramMODEL.getAVAILABILITYLIST()) {
/*     */         
/*  32 */         if (RfcConfigProperties.getCountry(aVAILABILITY.getCOUNTRY_FC()) == null)
/*     */           continue; 
/*  34 */         RdhChwFcProd_Model rdhChwFcProd_Model = new RdhChwFcProd_Model();
/*  35 */         rdhChwFcProd_Model.setMachType(paramMODEL.getMACHTYPE());
/*  36 */         rdhChwFcProd_Model.setModel(paramMODEL.getMODEL());
/*  37 */         rdhChwFcProd_Model.setCountry_fc(
/*  38 */             RfcConfigProperties.getCountry(aVAILABILITY.getCOUNTRY_FC()));
/*  39 */         rdhChwFcProd_Model.setAvailabilityAction(aVAILABILITY.getAVAILABILITYACTION());
/*  40 */         rdhChwFcProd_Model.setAnnDate(aVAILABILITY.getANNDATE().replaceAll("-", ""));
/*  41 */         rdhChwFcProd_Model.setFirstOrder(aVAILABILITY.getFIRSTORDER().replaceAll("-", ""));
/*  42 */         rdhChwFcProd_Model.setPlannedAvailability(aVAILABILITY.getPLANNEDAVAILABILITY().replaceAll("-", ""));
/*  43 */         rdhChwFcProd_Model.setPubFrom(aVAILABILITY.getPUBFROM().replaceAll("-", ""));
/*  44 */         rdhChwFcProd_Model.setPubTo(aVAILABILITY.getPUBTO().replaceAll("-", ""));
/*  45 */         rdhChwFcProd_Model.setWdAnnDate(aVAILABILITY.getWDANNDATE().replaceAll("-", ""));
/*  46 */         rdhChwFcProd_Model.setLastOrder(aVAILABILITY.getLASTORDER().replaceAll("-", ""));
/*  47 */         rdhChwFcProd_Model.setEosAnnDate(aVAILABILITY.getEOSANNDATE().replaceAll("-", ""));
/*  48 */         rdhChwFcProd_Model.setEndOfServiceDate(aVAILABILITY.getENDOFSERVICEDATE().replaceAll("-", ""));
/*  49 */         rdhChwFcProd_Model.setCategory(paramMODEL.getCATEGORY());
/*  50 */         rdhChwFcProd_Model.setOrderCode(paramMODEL.getORDERCODE());
/*  51 */         this.tbl_model.add(rdhChwFcProd_Model);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public RdhChwFcProd(TMF_UPDATE paramTMF_UPDATE, String paramString) {
/*  57 */     super(paramString, "RDH_YMDMFC_PROD"
/*  58 */         .toLowerCase(), null);
/*  59 */     this.pims_identity = "H";
/*     */     
/*  61 */     if (paramTMF_UPDATE.getAVAILABILITYLIST() != null && !paramTMF_UPDATE.getAVAILABILITYLIST().isEmpty())
/*     */     {
/*  63 */       for (AVAILABILITYELEMENT_TMF aVAILABILITYELEMENT_TMF : paramTMF_UPDATE.getAVAILABILITYLIST()) {
/*     */         
/*  65 */         if (RfcConfigProperties.getCountry(aVAILABILITYELEMENT_TMF.getCOUNTRY_FC()) == null) {
/*     */           continue;
/*     */         }
/*  68 */         RdhChwFcProd_TMF rdhChwFcProd_TMF = new RdhChwFcProd_TMF();
/*  69 */         rdhChwFcProd_TMF.setMachType(paramTMF_UPDATE.getMACHTYPE());
/*  70 */         rdhChwFcProd_TMF.setModel(paramTMF_UPDATE.getMODEL());
/*  71 */         rdhChwFcProd_TMF.setFeatureCode(paramTMF_UPDATE.getFEATURECODE());
/*  72 */         rdhChwFcProd_TMF.setFeatureEntityType(paramTMF_UPDATE.getFEATUREENTITYTYPE());
/*     */         
/*  74 */         rdhChwFcProd_TMF.setFeatureEntityId(paramTMF_UPDATE.getFEATUREENTITYID());
/*     */         
/*  76 */         rdhChwFcProd_TMF.setCountry_fc(
/*  77 */             RfcConfigProperties.getCountry(aVAILABILITYELEMENT_TMF.getCOUNTRY_FC()));
/*  78 */         rdhChwFcProd_TMF.setAnnDate(aVAILABILITYELEMENT_TMF.getANNDATE().replaceAll("-", ""));
/*  79 */         rdhChwFcProd_TMF.setFirstOrder(aVAILABILITYELEMENT_TMF.getFIRSTORDER().replaceAll("-", ""));
/*  80 */         rdhChwFcProd_TMF.setPlannedAvailability(aVAILABILITYELEMENT_TMF.getPLANNEDAVAILABILITY().replaceAll("-", ""));
/*  81 */         rdhChwFcProd_TMF.setPubFrom(aVAILABILITYELEMENT_TMF.getPUBFROM().replaceAll("-", ""));
/*  82 */         rdhChwFcProd_TMF.setPubTo(aVAILABILITYELEMENT_TMF.getPUBTO().replaceAll("-", ""));
/*  83 */         rdhChwFcProd_TMF.setWdAnnDate(aVAILABILITYELEMENT_TMF.getWDANNDATE().replaceAll("-", ""));
/*  84 */         rdhChwFcProd_TMF.setLastOrder(aVAILABILITYELEMENT_TMF.getLASTORDER().replaceAll("-", ""));
/*  85 */         rdhChwFcProd_TMF.setEosAnnDate(aVAILABILITYELEMENT_TMF.getEOSANNDATE().replaceAll("-", ""));
/*  86 */         rdhChwFcProd_TMF.setEndOfServiceDate(aVAILABILITYELEMENT_TMF.getENDOFSERVICEDATE().replaceAll("-", ""));
/*  87 */         rdhChwFcProd_TMF.setNoCstShip(paramTMF_UPDATE.getNOCSTSHIP());
/*  88 */         if ("Does not apply".equalsIgnoreCase(paramTMF_UPDATE.getINSTALL())) {
/*  89 */           rdhChwFcProd_TMF.setInstall("NA");
/*  90 */         } else if ("CIF".equalsIgnoreCase(paramTMF_UPDATE.getINSTALL())) {
/*  91 */           rdhChwFcProd_TMF.setInstall("CSU");
/*  92 */         } else if ("CE".equalsIgnoreCase(paramTMF_UPDATE.getINSTALL())) {
/*  93 */           rdhChwFcProd_TMF.setInstall("IBI");
/*     */         } else {
/*  95 */           rdhChwFcProd_TMF.setInstall("");
/*     */         } 
/*  97 */         rdhChwFcProd_TMF.setConfiguratorFlag(paramTMF_UPDATE.getCONFIGURATORFLAG());
/*     */         
/*  99 */         if ("Yes".equalsIgnoreCase(paramTMF_UPDATE.getBULKMESINDC())) {
/* 100 */           rdhChwFcProd_TMF.setBulkMesIndc("1");
/* 101 */         } else if ("No".equalsIgnoreCase(paramTMF_UPDATE.getBULKMESINDC())) {
/* 102 */           rdhChwFcProd_TMF.setBulkMesIndc("0");
/*     */         } else {
/* 104 */           rdhChwFcProd_TMF.setBulkMesIndc("");
/*     */         } 
/* 106 */         rdhChwFcProd_TMF.setOrderCode(paramTMF_UPDATE.getORDERCODE());
/* 107 */         rdhChwFcProd_TMF.setSystemMax(paramTMF_UPDATE.getSYSTEMMAX());
/*     */         
/* 109 */         if ("Yes".equalsIgnoreCase(paramTMF_UPDATE.getRETURNEDPARTS())) {
/* 110 */           rdhChwFcProd_TMF.setReturnedParts("1");
/* 111 */         } else if ("Feature conversion only".equalsIgnoreCase(paramTMF_UPDATE.getRETURNEDPARTS())) {
/* 112 */           rdhChwFcProd_TMF.setReturnedParts("1");
/* 113 */         } else if (paramTMF_UPDATE.getRETURNEDPARTS() == null || "".equals(paramTMF_UPDATE.getRETURNEDPARTS())) {
/* 114 */           rdhChwFcProd_TMF.setReturnedParts("");
/*     */         } else {
/* 116 */           rdhChwFcProd_TMF.setReturnedParts("0");
/*     */         } 
/* 118 */         this.tbl_tmf_c.add(rdhChwFcProd_TMF);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public RdhChwFcProd(FEATURE paramFEATURE, String paramString) {
/* 124 */     super(paramString, "RDH_YMDMFC_PROD"
/* 125 */         .toLowerCase(), null);
/* 126 */     this.pims_identity = "H";
/*     */     
/* 128 */     RdhChwFcProd_FEATURE rdhChwFcProd_FEATURE = new RdhChwFcProd_FEATURE();
/* 129 */     rdhChwFcProd_FEATURE.setFeatureCode(paramFEATURE.getFEATURECODE());
/* 130 */     rdhChwFcProd_FEATURE.setFeatureEntityType(paramFEATURE.getENTITYTYPE());
/* 131 */     rdhChwFcProd_FEATURE.setFeatureEntityID(paramFEATURE.getENTITYID());
/* 132 */     if (paramFEATURE.getLANGUAGELIST() != null) {
/* 133 */       for (LANGUAGEELEMENT_FEATURE lANGUAGEELEMENT_FEATURE : paramFEATURE.getLANGUAGELIST()) {
/* 134 */         if ("1".equals(lANGUAGEELEMENT_FEATURE.getNLSID())) {
/* 135 */           rdhChwFcProd_FEATURE.setMktgDesc(lANGUAGEELEMENT_FEATURE.getMKTGNAME());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 140 */     rdhChwFcProd_FEATURE.setFcType(paramFEATURE.getFCTYPE());
/* 141 */     rdhChwFcProd_FEATURE.setFcSubcat(paramFEATURE.getFCSUBCAT());
/* 142 */     if ("No".equals(paramFEATURE.getPRICEDFEATURE())) {
/* 143 */       rdhChwFcProd_FEATURE.setPricedFeature("0");
/* 144 */     } else if ("Yes".equals(paramFEATURE.getPRICEDFEATURE())) {
/* 145 */       if ("Yes".equals(paramFEATURE.getZEROPRICE())) {
/* 146 */         rdhChwFcProd_FEATURE.setPricedFeature("0");
/* 147 */       } else if ("".equals(paramFEATURE.getZEROPRICE()) || paramFEATURE.getZEROPRICE() == null) {
/* 148 */         rdhChwFcProd_FEATURE.setPricedFeature("1");
/* 149 */       } else if ("No".equals(paramFEATURE.getZEROPRICE())) {
/* 150 */         rdhChwFcProd_FEATURE.setPricedFeature("1");
/*     */       } 
/*     */     } else {
/* 153 */       rdhChwFcProd_FEATURE.setPricedFeature("");
/*     */     } 
/*     */     
/* 156 */     rdhChwFcProd_FEATURE.setFcCat(paramFEATURE.getFCCAT());
/* 157 */     rdhChwFcProd_FEATURE.setConfiguratorFlag(paramFEATURE.getCONFIGURATORFLAG());
/* 158 */     rdhChwFcProd_FEATURE.setChargeOption(paramFEATURE.getCHARGEOPTION());
/* 159 */     rdhChwFcProd_FEATURE.setLcnsOptType(paramFEATURE.getLICNSOPTTYPE());
/* 160 */     rdhChwFcProd_FEATURE.setChar_type(paramFEATURE.getFCTYPE());
/* 161 */     this.tbl_feature.add(rdhChwFcProd_FEATURE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 166 */     this.tbl_model = new ArrayList<>();
/* 167 */     this.tbl_tmf_c = new ArrayList<>();
/* 168 */     this.tbl_feature = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 173 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhChwFcProd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */