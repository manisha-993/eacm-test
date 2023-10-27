/*     */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*     */ 
/*     */ import com.google.gson.annotations.SerializedName;
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
/*     */ public class RdhChwFcProd_FCTRANSACTION_C
/*     */ {
/*     */   @SerializedName("FROMMACHTYPE")
/*     */   private String FROMMACHTYPE;
/*     */   @SerializedName("TOMACHTYPE")
/*     */   private String TOMACHTYPE;
/*     */   @SerializedName("COUNTRY_FC")
/*     */   private String COUNTRY_FC;
/*     */   @SerializedName("AVAILABILITYACTION")
/*     */   private String AVAILABILITYACTION;
/*     */   @SerializedName("ANNDATE")
/*     */   private String ANNDATE;
/*     */   @SerializedName("FIRSTORDER")
/*     */   private String FIRSTORDER;
/*     */   
/*     */   public String getFROMMACHTYPE() {
/*  33 */     return this.FROMMACHTYPE; } @SerializedName("PLANNEDAVAILABILITY") private String PLANNEDAVAILABILITY; @SerializedName("PUBFROM") private String PUBFROM; @SerializedName("PUBTO") private String PUBTO; @SerializedName("WDANNDATE") private String WDANNDATE; @SerializedName("LASTORDER")
/*     */   private String LASTORDER; @SerializedName("EOSANNDATE")
/*     */   private String EOSANNDATE; @SerializedName("ENDOFSERVICEDATE")
/*  36 */   private String ENDOFSERVICEDATE; public void setFROMMACHTYPE(String paramString) { this.FROMMACHTYPE = paramString; }
/*     */   
/*     */   public String getTOMACHTYPE() {
/*  39 */     return this.TOMACHTYPE;
/*     */   }
/*     */   public void setTOMACHTYPE(String paramString) {
/*  42 */     this.TOMACHTYPE = paramString;
/*     */   }
/*     */   public String getCOUNTRY_FC() {
/*  45 */     return this.COUNTRY_FC;
/*     */   }
/*     */   public void setCOUNTRY_FC(String paramString) {
/*  48 */     this.COUNTRY_FC = paramString;
/*     */   }
/*     */   public String getAVAILABILITYACTION() {
/*  51 */     return this.AVAILABILITYACTION;
/*     */   }
/*     */   public void setAVAILABILITYACTION(String paramString) {
/*  54 */     this.AVAILABILITYACTION = paramString;
/*     */   }
/*     */   public String getANNDATE() {
/*  57 */     return this.ANNDATE;
/*     */   }
/*     */   public void setANNDATE(String paramString) {
/*  60 */     this.ANNDATE = paramString;
/*     */   }
/*     */   public String getFIRSTORDER() {
/*  63 */     return this.FIRSTORDER;
/*     */   }
/*     */   public void setFIRSTORDER(String paramString) {
/*  66 */     this.FIRSTORDER = paramString;
/*     */   }
/*     */   public String getPLANNEDAVAILABILITY() {
/*  69 */     return this.PLANNEDAVAILABILITY;
/*     */   }
/*     */   public void setPLANNEDAVAILABILITY(String paramString) {
/*  72 */     this.PLANNEDAVAILABILITY = paramString;
/*     */   }
/*     */   public String getPUBFROM() {
/*  75 */     return this.PUBFROM;
/*     */   }
/*     */   public void setPUBFROM(String paramString) {
/*  78 */     this.PUBFROM = paramString;
/*     */   }
/*     */   public String getPUBTO() {
/*  81 */     return this.PUBTO;
/*     */   }
/*     */   public void setPUBTO(String paramString) {
/*  84 */     this.PUBTO = paramString;
/*     */   }
/*     */   public String getWDANNDATE() {
/*  87 */     return this.WDANNDATE;
/*     */   }
/*     */   public void setWDANNDATE(String paramString) {
/*  90 */     this.WDANNDATE = paramString;
/*     */   }
/*     */   public String getLASTORDER() {
/*  93 */     return this.LASTORDER;
/*     */   }
/*     */   public void setLASTORDER(String paramString) {
/*  96 */     this.LASTORDER = paramString;
/*     */   }
/*     */   public String getEOSANNDATE() {
/*  99 */     return this.EOSANNDATE;
/*     */   }
/*     */   public void setEOSANNDATE(String paramString) {
/* 102 */     this.EOSANNDATE = paramString;
/*     */   }
/*     */   public String getENDOFSERVICEDATE() {
/* 105 */     return this.ENDOFSERVICEDATE;
/*     */   }
/*     */   public void setENDOFSERVICEDATE(String paramString) {
/* 108 */     this.ENDOFSERVICEDATE = paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhChwFcProd_FCTRANSACTION_C.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */