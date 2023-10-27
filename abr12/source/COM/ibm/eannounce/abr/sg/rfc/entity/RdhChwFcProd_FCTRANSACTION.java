/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhChwFcProd_FCTRANSACTION
/*    */ {
/*    */   @SerializedName("FROMMACHTYPE")
/*    */   private String FROMMACHTYPE;
/*    */   @SerializedName("TOMACHTYPE")
/*    */   private String TOMACHTYPE;
/*    */   @SerializedName("FROMMODEL")
/*    */   private String FROMMODEL;
/*    */   @SerializedName("TOMODEL")
/*    */   private String TOMODEL;
/*    */   
/*    */   public String getFROMMACHTYPE() {
/* 23 */     return this.FROMMACHTYPE; } @SerializedName("FROMFEATURECODE") private String FROMFEATURECODE; @SerializedName("TOFEATURECODE")
/*    */   private String TOFEATURECODE; @SerializedName("FEATURETRANSCAT")
/*    */   private String FEATURETRANSCAT; @SerializedName("RETURNEDPARTSMES")
/* 26 */   private String RETURNEDPARTSMES; public void setFROMMACHTYPE(String paramString) { this.FROMMACHTYPE = paramString; }
/*    */   
/*    */   public String getTOMACHTYPE() {
/* 29 */     return this.TOMACHTYPE;
/*    */   }
/*    */   public void setTOMACHTYPE(String paramString) {
/* 32 */     this.TOMACHTYPE = paramString;
/*    */   }
/*    */   public String getFROMMODEL() {
/* 35 */     return this.FROMMODEL;
/*    */   }
/*    */   public void setFROMMODEL(String paramString) {
/* 38 */     this.FROMMODEL = paramString;
/*    */   }
/*    */   public String getTOMODEL() {
/* 41 */     return this.TOMODEL;
/*    */   }
/*    */   public void setTOMODEL(String paramString) {
/* 44 */     this.TOMODEL = paramString;
/*    */   }
/*    */   public String getFROMFEATURECODE() {
/* 47 */     return this.FROMFEATURECODE;
/*    */   }
/*    */   public void setFROMFEATURECODE(String paramString) {
/* 50 */     this.FROMFEATURECODE = paramString;
/*    */   }
/*    */   public String getTOFEATURECODE() {
/* 53 */     return this.TOFEATURECODE;
/*    */   }
/*    */   public void setTOFEATURECODE(String paramString) {
/* 56 */     this.TOFEATURECODE = paramString;
/*    */   }
/*    */   public String getFEATURETRANSCAT() {
/* 59 */     return this.FEATURETRANSCAT;
/*    */   }
/*    */   public void setFEATURETRANSCAT(String paramString) {
/* 62 */     this.FEATURETRANSCAT = paramString;
/*    */   }
/*    */   public String getRETURNEDPARTSMES() {
/* 65 */     return this.RETURNEDPARTSMES;
/*    */   }
/*    */   public void setRETURNEDPARTSMES(String paramString) {
/* 68 */     this.RETURNEDPARTSMES = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhChwFcProd_FCTRANSACTION.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */