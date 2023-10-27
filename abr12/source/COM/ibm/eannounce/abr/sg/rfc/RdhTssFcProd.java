/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhTssFcProd_B;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhTssFcProd_C;
/*     */ import COM.ibm.eannounce.abr.util.RfcConfigProperties;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RdhTssFcProd
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("Z_GEO")
/*     */   private String z_geo;
/*     */   @SerializedName("TBL_SVCMOD_B")
/*     */   private List<RdhTssFcProd_B> tbl_svcmod_b;
/*     */   @SerializedName("TBL_SVCMOD_C")
/*     */   private List<RdhTssFcProd_C> tbl_svcmod_c;
/*     */   
/*     */   public RdhTssFcProd(SVCMOD paramSVCMOD) {
/*  23 */     super(paramSVCMOD.getMACHTYPE() + paramSVCMOD.getMODEL(), "RDH_YMDMFC_SVCPROD"
/*  24 */         .toLowerCase(), null);
/*     */     
/*  26 */     if (paramSVCMOD.getAVAILABILITYLIST() != null && !paramSVCMOD.getAVAILABILITYLIST().isEmpty())
/*     */     {
/*  28 */       for (AVAILABILITY aVAILABILITY : paramSVCMOD.getAVAILABILITYLIST()) {
/*     */         
/*  30 */         RdhTssFcProd_B rdhTssFcProd_B = new RdhTssFcProd_B();
/*  31 */         rdhTssFcProd_B.setMachType(paramSVCMOD.getMACHTYPE());
/*  32 */         rdhTssFcProd_B.setModel(paramSVCMOD.getMODEL());
/*  33 */         rdhTssFcProd_B.setAvailabilityAction(aVAILABILITY.getAVAILABILITYACTION());
/*  34 */         rdhTssFcProd_B.setCountry_fc(
/*  35 */             RfcConfigProperties.getCountry(aVAILABILITY.getCOUNTRY_FC()));
/*  36 */         rdhTssFcProd_B.setAnnDate(aVAILABILITY.getANNDATE().replaceAll("-", ""));
/*  37 */         rdhTssFcProd_B.setFirstOrder(aVAILABILITY.getFIRSTORDER().replaceAll("-", ""));
/*  38 */         rdhTssFcProd_B.setPlanedAvailability(aVAILABILITY.getPLANNEDAVAILABILITY().replaceAll("-", ""));
/*  39 */         rdhTssFcProd_B.setPubFrom(aVAILABILITY.getPUBFROM().replaceAll("-", ""));
/*  40 */         rdhTssFcProd_B.setPubTo(aVAILABILITY.getPUBTO().replaceAll("-", ""));
/*  41 */         rdhTssFcProd_B.setWdAnnDate(aVAILABILITY.getWDANNDATE().replaceAll("-", ""));
/*  42 */         rdhTssFcProd_B.setLastOrder(aVAILABILITY.getLASTORDER().replaceAll("-", ""));
/*  43 */         rdhTssFcProd_B.setEosAnnDate(aVAILABILITY.getEOSANNDATE().replaceAll("-", ""));
/*  44 */         rdhTssFcProd_B.setEndOfServiceDate(aVAILABILITY.getENDOFSERVICEDATE().replaceAll("-", ""));
/*  45 */         this.tbl_svcmod_b.add(rdhTssFcProd_B);
/*     */       } 
/*     */     }
/*     */     
/*  49 */     if (paramSVCMOD.getCHRGCOMPLIST() != null && !paramSVCMOD.getCHRGCOMPLIST().isEmpty())
/*     */     {
/*  51 */       for (CHRGCOMP cHRGCOMP : paramSVCMOD.getCHRGCOMPLIST()) {
/*     */         
/*  53 */         if (cHRGCOMP.getPRICEPOINTLIST() != null && !cHRGCOMP.getPRICEPOINTLIST().isEmpty())
/*     */         {
/*  55 */           for (PRICEPOINT pRICEPOINT : cHRGCOMP.getPRICEPOINTLIST()) {
/*     */             
/*  57 */             if ((((pRICEPOINT.getCNTRYEFFLIST() != null) ? 1 : 0) & (!pRICEPOINT.getCNTRYEFFLIST().isEmpty() ? 1 : 0)) != 0)
/*     */             {
/*  59 */               for (CNTRYEFF cNTRYEFF : pRICEPOINT.getCNTRYEFFLIST()) {
/*     */                 
/*  61 */                 if (cNTRYEFF.getCOUNTRYLIST() != null && !cNTRYEFF.getCOUNTRYLIST().isEmpty())
/*     */                 {
/*  63 */                   for (COUNTRY cOUNTRY : cNTRYEFF.getCOUNTRYLIST()) {
/*     */                     
/*  65 */                     RdhTssFcProd_C rdhTssFcProd_C = new RdhTssFcProd_C();
/*  66 */                     rdhTssFcProd_C.setMachType(paramSVCMOD.getMACHTYPE());
/*  67 */                     rdhTssFcProd_C.setModel(paramSVCMOD.getMODEL());
/*  68 */                     rdhTssFcProd_C.setChrgComId(cHRGCOMP.getCHRGCOMPID());
/*  69 */                     rdhTssFcProd_C.setEntityType(pRICEPOINT.getENTITYTYPE());
/*  70 */                     rdhTssFcProd_C.setEntityId(pRICEPOINT.getENTITYID());
/*  71 */                     rdhTssFcProd_C.setPrcPtId(pRICEPOINT.getPRCPTID());
/*  72 */                     rdhTssFcProd_C.setCountryList_fc(
/*  73 */                         RfcConfigProperties.getCountry(cOUNTRY.getCOUNTRY_FC()));
/*  74 */                     rdhTssFcProd_C.setEffectiveDate(cNTRYEFF.getEFFECTIVEDATE().replaceAll("-", ""));
/*  75 */                     rdhTssFcProd_C.setEndDate(cNTRYEFF.getENDDATE().replaceAll("-", ""));
/*  76 */                     this.tbl_svcmod_c.add(rdhTssFcProd_C);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canRun() {
/*  88 */     if (this.tbl_svcmod_c == null || this.tbl_svcmod_c.size() == 0 || this.tbl_svcmod_b == null || this.tbl_svcmod_b.size() == 0)
/*  89 */       return false; 
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/*  95 */     this.z_geo = "WW";
/*  96 */     this.tbl_svcmod_b = new ArrayList<>();
/*  97 */     this.tbl_svcmod_c = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhTssFcProd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */