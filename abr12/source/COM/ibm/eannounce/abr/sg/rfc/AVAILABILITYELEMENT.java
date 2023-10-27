/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import org.codehaus.jackson.map.annotate.JsonSerialize;
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
/*     */ @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ class AVAILABILITYELEMENT
/*     */ {
/*     */   @XmlElement(name = "COUNTRY_FC")
/*     */   private String COUNTRY_FC;
/*     */   @XmlElement(name = "AVAILABILITYACTION")
/*     */   private String AVAILABILITYACTION;
/*     */   @XmlElement(name = "ANNDATE")
/*     */   private String ANNDATE;
/*     */   @XmlElement(name = "FIRSTORDER")
/*     */   private String FIRSTORDER;
/*     */   @XmlElement(name = "PLANNEDAVAILABILITY")
/*     */   private String PLANNEDAVAILABILITY;
/*     */   @XmlElement(name = "PUBFROM")
/*     */   private String PUBFROM;
/*     */   @XmlElement(name = "PUBTO")
/*     */   private String PUBTO;
/*     */   @XmlElement(name = "WDANNDATE")
/*     */   private String WDANNDATE;
/*     */   @XmlElement(name = "LASTORDER")
/*     */   private String LASTORDER;
/*     */   
/*     */   public String getCOUNTRY_FC() {
/* 170 */     return this.COUNTRY_FC;
/*     */   }
/*     */   
/*     */   public void setCOUNTRY_FC(String paramString) {
/* 174 */     this.COUNTRY_FC = paramString;
/*     */   }
/*     */   
/*     */   public String getAVAILABILITYACTION() {
/* 178 */     return this.AVAILABILITYACTION;
/*     */   }
/*     */   
/*     */   public void setAVAILABILITYACTION(String paramString) {
/* 182 */     this.AVAILABILITYACTION = paramString;
/*     */   }
/*     */   
/*     */   public String getANNDATE() {
/* 186 */     return this.ANNDATE;
/*     */   }
/*     */   
/*     */   public void setANNDATE(String paramString) {
/* 190 */     this.ANNDATE = paramString;
/*     */   }
/*     */   
/*     */   public String getFIRSTORDER() {
/* 194 */     return this.FIRSTORDER;
/*     */   }
/*     */   
/*     */   public void setFIRSTORDER(String paramString) {
/* 198 */     this.FIRSTORDER = paramString;
/*     */   }
/*     */   
/*     */   public String getPLANNEDAVAILABILITY() {
/* 202 */     return this.PLANNEDAVAILABILITY;
/*     */   }
/*     */   
/*     */   public void setPLANNEDAVAILABILITY(String paramString) {
/* 206 */     this.PLANNEDAVAILABILITY = paramString;
/*     */   }
/*     */   
/*     */   public String getPUBFROM() {
/* 210 */     return this.PUBFROM;
/*     */   }
/*     */   
/*     */   public void setPUBFROM(String paramString) {
/* 214 */     this.PUBFROM = paramString;
/*     */   }
/*     */   
/*     */   public String getPUBTO() {
/* 218 */     return this.PUBTO;
/*     */   }
/*     */   
/*     */   public void setPUBTO(String paramString) {
/* 222 */     this.PUBTO = paramString;
/*     */   }
/*     */   
/*     */   public String getWDANNDATE() {
/* 226 */     return this.WDANNDATE;
/*     */   }
/*     */   
/*     */   public void setWDANNDATE(String paramString) {
/* 230 */     this.WDANNDATE = paramString;
/*     */   }
/*     */   
/*     */   public String getLASTORDER() {
/* 234 */     return this.LASTORDER;
/*     */   }
/*     */   
/*     */   public void setLASTORDER(String paramString) {
/* 238 */     this.LASTORDER = paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\AVAILABILITYELEMENT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */