/*     */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*     */ 
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ 
/*     */ public class RdhChwFcProd_TMF {
/*     */   @SerializedName("MACHTYPE")
/*     */   private String machType;
/*     */   @SerializedName("MODEL")
/*     */   private String model;
/*     */   @SerializedName("FEATURECODE")
/*     */   private String featureCode;
/*     */   @SerializedName("FEATUREENTITYTYPE")
/*     */   private String featureEntityType;
/*     */   @SerializedName("FEATUREENTITYID")
/*     */   private String featureEntityId;
/*     */   @SerializedName("COUNTRY_FC")
/*     */   private String country_fc;
/*     */   @SerializedName("ANNDATE")
/*     */   private String annDate;
/*     */   @SerializedName("FIRSTORDER")
/*     */   private String firstOrder;
/*     */   @SerializedName("PLANNEDAVAILABILITY")
/*     */   private String plannedAvailability;
/*     */   @SerializedName("PUBFROM")
/*     */   private String pubFrom;
/*     */   @SerializedName("PUBTO")
/*     */   private String pubTo;
/*     */   @SerializedName("WDANNDATE")
/*     */   private String wdAnnDate;
/*     */   @SerializedName("LASTORDER")
/*     */   private String lastOrder;
/*     */   @SerializedName("EOSANNDATE")
/*     */   private String eosAnnDate;
/*     */   @SerializedName("ENDOFSERVICEDATE")
/*     */   private String endOfServiceDate;
/*     */   @SerializedName("NOCSTSHIP")
/*     */   private String noCstShip;
/*     */   @SerializedName("INSTALL")
/*     */   private String install;
/*     */   @SerializedName("CONFIGURATORFLAG")
/*     */   private String configuratorFlag;
/*     */   @SerializedName("BULKMESINDC")
/*     */   private String bulkMesIndc;
/*     */   @SerializedName("ORDERCODE")
/*     */   private String orderCode;
/*     */   @SerializedName("SYSTEMMAX")
/*     */   private String systemMax;
/*     */   @SerializedName("RETURNEDPARTS")
/*     */   private String returnedParts;
/*     */   
/*     */   public String getMachType() {
/*  52 */     return this.machType;
/*     */   }
/*     */   public String getModel() {
/*  55 */     return this.model;
/*     */   }
/*     */   public String getFeatureCode() {
/*  58 */     return this.featureCode;
/*     */   }
/*     */   public String getFeatureEntityType() {
/*  61 */     return this.featureEntityType;
/*     */   }
/*     */   public String getCountry_fc() {
/*  64 */     return this.country_fc;
/*     */   }
/*     */   public String getAnnDate() {
/*  67 */     return this.annDate;
/*     */   }
/*     */   public String getFirstOrder() {
/*  70 */     return this.firstOrder;
/*     */   }
/*     */   public String getPlannedAvailability() {
/*  73 */     return this.plannedAvailability;
/*     */   }
/*     */   public String getPubFrom() {
/*  76 */     return this.pubFrom;
/*     */   }
/*     */   public String getPubTo() {
/*  79 */     return this.pubTo;
/*     */   }
/*     */   public String getWdAnnDate() {
/*  82 */     return this.wdAnnDate;
/*     */   }
/*     */   public String getLastOrder() {
/*  85 */     return this.lastOrder;
/*     */   }
/*     */   public String getEosAnnDate() {
/*  88 */     return this.eosAnnDate;
/*     */   }
/*     */   public String getEndOfServiceDate() {
/*  91 */     return this.endOfServiceDate;
/*     */   }
/*     */   public String getNoCstShip() {
/*  94 */     return this.noCstShip;
/*     */   }
/*     */   public String getInstall() {
/*  97 */     return this.install;
/*     */   }
/*     */   public String getConfiguratorFlag() {
/* 100 */     return this.configuratorFlag;
/*     */   }
/*     */   public String getBulkMesIndc() {
/* 103 */     return this.bulkMesIndc;
/*     */   }
/*     */   public String getOrderCode() {
/* 106 */     return this.orderCode;
/*     */   }
/*     */   public String getSystemMax() {
/* 109 */     return this.systemMax;
/*     */   }
/*     */   public void setMachType(String paramString) {
/* 112 */     this.machType = paramString;
/*     */   }
/*     */   public void setModel(String paramString) {
/* 115 */     this.model = paramString;
/*     */   }
/*     */   public void setFeatureCode(String paramString) {
/* 118 */     this.featureCode = paramString;
/*     */   }
/*     */   public void setFeatureEntityType(String paramString) {
/* 121 */     this.featureEntityType = paramString;
/*     */   }
/*     */   public String getFeatureEntityId() {
/* 124 */     return this.featureEntityId;
/*     */   }
/*     */   public void setFeatureEntityId(String paramString) {
/* 127 */     this.featureEntityId = paramString;
/*     */   }
/*     */   public void setCountry_fc(String paramString) {
/* 130 */     this.country_fc = paramString;
/*     */   }
/*     */   public void setAnnDate(String paramString) {
/* 133 */     this.annDate = paramString;
/*     */   }
/*     */   public void setFirstOrder(String paramString) {
/* 136 */     this.firstOrder = paramString;
/*     */   }
/*     */   public void setPlannedAvailability(String paramString) {
/* 139 */     this.plannedAvailability = paramString;
/*     */   }
/*     */   public void setPubFrom(String paramString) {
/* 142 */     this.pubFrom = paramString;
/*     */   }
/*     */   public void setPubTo(String paramString) {
/* 145 */     this.pubTo = paramString;
/*     */   }
/*     */   public void setWdAnnDate(String paramString) {
/* 148 */     this.wdAnnDate = paramString;
/*     */   }
/*     */   public void setLastOrder(String paramString) {
/* 151 */     this.lastOrder = paramString;
/*     */   }
/*     */   public void setEosAnnDate(String paramString) {
/* 154 */     this.eosAnnDate = paramString;
/*     */   }
/*     */   public void setEndOfServiceDate(String paramString) {
/* 157 */     this.endOfServiceDate = paramString;
/*     */   }
/*     */   public void setNoCstShip(String paramString) {
/* 160 */     this.noCstShip = paramString;
/*     */   }
/*     */   public void setInstall(String paramString) {
/* 163 */     this.install = paramString;
/*     */   }
/*     */   public void setConfiguratorFlag(String paramString) {
/* 166 */     this.configuratorFlag = paramString;
/*     */   }
/*     */   public void setBulkMesIndc(String paramString) {
/* 169 */     this.bulkMesIndc = paramString;
/*     */   }
/*     */   public void setOrderCode(String paramString) {
/* 172 */     this.orderCode = paramString;
/*     */   }
/*     */   public void setSystemMax(String paramString) {
/* 175 */     this.systemMax = paramString;
/*     */   }
/*     */   public String getReturnedParts() {
/* 178 */     return this.returnedParts;
/*     */   }
/*     */   public void setReturnedParts(String paramString) {
/* 181 */     this.returnedParts = paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhChwFcProd_TMF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */