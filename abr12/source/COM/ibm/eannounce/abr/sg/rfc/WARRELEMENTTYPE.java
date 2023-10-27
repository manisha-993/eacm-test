/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementWrapper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ class WARRELEMENTTYPE
/*     */ {
/*     */   @XmlElement(name = "WARRACTION")
/*     */   private String WARRACTION;
/*     */   @XmlElement(name = "WARRENTITYTYPE")
/*     */   private String WARRENTITYTYPE;
/*     */   @XmlElement(name = "WARRENTITYID")
/*     */   private String WARRENTITYID;
/*     */   @XmlElement(name = "WARRID")
/*     */   private String WARRID;
/*     */   @XmlElement(name = "WARRPRIOD")
/*     */   private String WARRPRIOD;
/*     */   @XmlElement(name = "WARRDESC")
/*     */   private String WARRDESC;
/*     */   @XmlElement(name = "PUBFROM")
/*     */   private String PUBFROM;
/*     */   @XmlElement(name = "PUBTO")
/*     */   private String PUBTO;
/*     */   @XmlElement(name = "DEFWARR")
/*     */   private String DEFWARR;
/*     */   @XmlElementWrapper(name = "COUNTRYLIST")
/*     */   @XmlElement(name = "COUNTRYELEMENT")
/*     */   private List<COUNTRY> COUNTRYLIST;
/*     */   
/*     */   public String getWARRACTION() {
/* 493 */     return this.WARRACTION;
/*     */   }
/*     */   public void setWARRACTION(String paramString) {
/* 496 */     this.WARRACTION = paramString;
/*     */   }
/*     */   public String getWARRENTITYTYPE() {
/* 499 */     return this.WARRENTITYTYPE;
/*     */   }
/*     */   public void setWARRENTITYTYPE(String paramString) {
/* 502 */     this.WARRENTITYTYPE = paramString;
/*     */   }
/*     */   public String getWARRENTITYID() {
/* 505 */     return this.WARRENTITYID;
/*     */   }
/*     */   public void setWARRENTITYID(String paramString) {
/* 508 */     this.WARRENTITYID = paramString;
/*     */   }
/*     */   public String getWARRID() {
/* 511 */     return this.WARRID;
/*     */   }
/*     */   public void setWARRID(String paramString) {
/* 514 */     this.WARRID = paramString;
/*     */   }
/*     */   public String getWARRPRIOD() {
/* 517 */     return this.WARRPRIOD;
/*     */   }
/*     */   public void setWARRPRIOD(String paramString) {
/* 520 */     this.WARRPRIOD = paramString;
/*     */   }
/*     */   public String getWARRDESC() {
/* 523 */     return this.WARRDESC;
/*     */   }
/*     */   public void setWARRDESC(String paramString) {
/* 526 */     this.WARRDESC = paramString;
/*     */   }
/*     */   public String getPUBFROM() {
/* 529 */     return this.PUBFROM;
/*     */   }
/*     */   public void setPUBFROM(String paramString) {
/* 532 */     this.PUBFROM = paramString;
/*     */   }
/*     */   public String getPUBTO() {
/* 535 */     return this.PUBTO;
/*     */   }
/*     */   public void setPUBTO(String paramString) {
/* 538 */     this.PUBTO = paramString;
/*     */   }
/*     */   public String getDEFWARR() {
/* 541 */     return this.DEFWARR;
/*     */   }
/*     */   public void setDEFWARR(String paramString) {
/* 544 */     this.DEFWARR = paramString;
/*     */   }
/*     */   
/*     */   public List<COUNTRY> getCOUNTRYLIST() {
/* 548 */     return this.COUNTRYLIST;
/*     */   }
/*     */   public void setCOUNTRYLIST(List<COUNTRY> paramList) {
/* 551 */     this.COUNTRYLIST = paramList;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\WARRELEMENTTYPE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */