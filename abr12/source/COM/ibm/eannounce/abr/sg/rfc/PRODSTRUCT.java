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
/*     */ class PRODSTRUCT
/*     */ {
/*     */   @XmlElement(name = "ACTIVITY")
/*     */   private String ACTIVITY;
/*     */   @XmlElement(name = "ENTITYTYPE")
/*     */   private String ENTITYTYPE;
/*     */   @XmlElement(name = "ENTITYID")
/*     */   private String ENTITYID;
/*     */   @XmlElement(name = "STATUS")
/*     */   private String STATUS;
/*     */   @XmlElement(name = "MKTGNAME")
/*     */   private String MKTGNAME;
/*     */   @XmlElement(name = "MNATORYOPT")
/*     */   private String MNATORYOPT;
/*     */   @XmlElement(name = "OWNERID")
/*     */   private String OWNERID;
/*     */   @XmlElement(name = "WITHDRAWDATE")
/*     */   private String WITHDRAWDATE;
/*     */   @XmlElement(name = "WTHDRWEFFCTVDATE")
/*     */   private String WTHDRWEFFCTVDATE;
/*     */   @XmlElement(name = "FEATURECODE")
/*     */   private String FEATURECODE;
/*     */   @XmlElement(name = "FCMKTGSHRTDESC")
/*     */   private String FCMKTGSHRTDESC;
/*     */   @XmlElement(name = "SVCCAT")
/*     */   private String SVCCAT;
/*     */   @XmlElement(name = "SVCFCSUBCAT")
/*     */   private String SVCFCSUBCAT;
/*     */   @XmlElement(name = "EFFECTIVEDATE")
/*     */   private String EFFECTIVEDATE;
/*     */   @XmlElement(name = "ENDDATE")
/*     */   private String ENDDATE;
/*     */   
/*     */   public String getACTIVITY() {
/* 319 */     return this.ACTIVITY;
/*     */   }
/*     */   public String getENTITYTYPE() {
/* 322 */     return this.ENTITYTYPE;
/*     */   }
/*     */   public String getENTITYID() {
/* 325 */     return this.ENTITYID;
/*     */   }
/*     */   public String getSTATUS() {
/* 328 */     return this.STATUS;
/*     */   }
/*     */   public String getMKTGNAME() {
/* 331 */     return this.MKTGNAME;
/*     */   }
/*     */   public String getMNATORYOPT() {
/* 334 */     return this.MNATORYOPT;
/*     */   }
/*     */   public String getOWNERID() {
/* 337 */     return this.OWNERID;
/*     */   }
/*     */   public String getWITHDRAWDATE() {
/* 340 */     return this.WITHDRAWDATE;
/*     */   }
/*     */   public String getWTHDRWEFFCTVDATE() {
/* 343 */     return this.WTHDRWEFFCTVDATE;
/*     */   }
/*     */   public String getFEATURECODE() {
/* 346 */     return this.FEATURECODE;
/*     */   }
/*     */   public String getFCMKTGSHRTDESC() {
/* 349 */     return this.FCMKTGSHRTDESC;
/*     */   }
/*     */   public String getSVCCAT() {
/* 352 */     return this.SVCCAT;
/*     */   }
/*     */   public String getSVCFCSUBCAT() {
/* 355 */     return this.SVCFCSUBCAT;
/*     */   }
/*     */   public String getEFFECTIVEDATE() {
/* 358 */     return this.EFFECTIVEDATE;
/*     */   }
/*     */   public String getENDDATE() {
/* 361 */     return this.ENDDATE;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\PRODSTRUCT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */