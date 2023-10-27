/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PRODINFO
/*     */ {
/*     */   private String MODELID;
/*     */   private String PRODUCTVRM;
/*     */   private String MODELATR;
/*     */   private String MACHTYPEATR;
/*     */   private String MINVNAME;
/*     */   private String EDUCALLOWMHGHSCH;
/*     */   private String swFeatureId;
/*     */   private String PRICEDFEATURE;
/*     */   private String CHARGEOPTION;
/*     */   private String SFINVNAME;
/*     */   private String FEATURECODE;
/*     */   
/*     */   public String getMODELID() {
/*  80 */     return this.MODELID;
/*     */   }
/*     */   public void setMODELID(String paramString) {
/*  83 */     this.MODELID = paramString;
/*     */   }
/*     */   public String getPRODUCTVRM() {
/*  86 */     return this.PRODUCTVRM;
/*     */   }
/*     */   public void setPRODUCTVRM(String paramString) {
/*  89 */     this.PRODUCTVRM = paramString;
/*     */   }
/*     */   public String getMODELATR() {
/*  92 */     return this.MODELATR;
/*     */   }
/*     */   public void setMODELATR(String paramString) {
/*  95 */     this.MODELATR = paramString;
/*     */   }
/*     */   
/*     */   public String getMACHTYPEATR() {
/*  99 */     return this.MACHTYPEATR;
/*     */   }
/*     */   public void setMACHTYPEATR(String paramString) {
/* 102 */     this.MACHTYPEATR = paramString;
/*     */   }
/*     */   public String getMINVNAME() {
/* 105 */     return this.MINVNAME;
/*     */   }
/*     */   public void setMINVNAME(String paramString) {
/* 108 */     this.MINVNAME = paramString;
/*     */   }
/*     */   public String getEDUCALLOWMHGHSCH() {
/* 111 */     return this.EDUCALLOWMHGHSCH;
/*     */   }
/*     */   public void setEDUCALLOWMHGHSCH(String paramString) {
/* 114 */     this.EDUCALLOWMHGHSCH = paramString;
/*     */   }
/*     */   public String getSwFeatureId() {
/* 117 */     return this.swFeatureId;
/*     */   }
/*     */   public void setSwFeatureId(String paramString) {
/* 120 */     this.swFeatureId = paramString;
/*     */   }
/*     */   public String getPRICEDFEATURE() {
/* 123 */     return this.PRICEDFEATURE;
/*     */   }
/*     */   public void setPRICEDFEATURE(String paramString) {
/* 126 */     this.PRICEDFEATURE = paramString;
/*     */   }
/*     */   public String getCHARGEOPTION() {
/* 129 */     return this.CHARGEOPTION;
/*     */   }
/*     */   public void setCHARGEOPTION(String paramString) {
/* 132 */     this.CHARGEOPTION = paramString;
/*     */   }
/*     */   public String getSFINVNAME() {
/* 135 */     return this.SFINVNAME;
/*     */   }
/*     */   public void setSFINVNAME(String paramString) {
/* 138 */     this.SFINVNAME = paramString;
/*     */   }
/*     */   public String getFEATURECODE() {
/* 141 */     return this.FEATURECODE;
/*     */   }
/*     */   public void setFEATURECODE(String paramString) {
/* 144 */     this.FEATURECODE = paramString;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 148 */     if (paramObject == null) {
/* 149 */       return false;
/*     */     }
/* 151 */     if (paramObject == this) {
/* 152 */       return true;
/*     */     }
/* 154 */     if (getClass() != paramObject.getClass()) {
/* 155 */       return false;
/*     */     }
/*     */     
/* 158 */     PRODINFO pRODINFO = (PRODINFO)paramObject;
/* 159 */     return this.MODELID.equals(pRODINFO.getMODELID());
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\PRODINFO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */