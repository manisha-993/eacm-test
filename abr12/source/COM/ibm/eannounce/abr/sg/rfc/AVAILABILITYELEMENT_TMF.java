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
/*     */ @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ class AVAILABILITYELEMENT_TMF
/*     */ {
/*     */   @XmlElement(name = "AVAILABILITYACTION")
/*     */   private String AVAILABILITYACTION;
/*     */   @XmlElement(name = "STATUS")
/*     */   private String STATUS;
/*     */   @XmlElement(name = "COUNTRY_FC")
/*     */   private String COUNTRY_FC;
/*     */   @XmlElement(name = "ANNDATE")
/*     */   private String ANNDATE;
/*     */   @XmlElement(name = "ANNNUMBER")
/*     */   private String ANNNUMBER;
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
/*     */   @XmlElement(name = "ENDOFMARKETANNNUMBER")
/*     */   private String ENDOFMARKETANNNUMBER;
/*     */   @XmlElement(name = "LASTORDER")
/*     */   private String LASTORDER;
/*     */   @XmlElement(name = "EOSANNDATE")
/*     */   private String EOSANNDATE;
/*     */   @XmlElement(name = "ENDOFSERVICEANNNUMBER")
/*     */   private String ENDOFSERVICEANNNUMBER;
/*     */   @XmlElement(name = "ENDOFSERVICEDATE")
/*     */   private String ENDOFSERVICEDATE;
/*     */   @XmlElementWrapper(name = "SLEORGGRPLIST")
/*     */   @XmlElement(name = "SLEORGGRPELEMENT")
/*     */   private List<SLEORGGRPELEMENT_TMF> SLEORGGRPLIST;
/*     */   @XmlElementWrapper(name = "SLEORGNPLNTCODELIST")
/*     */   @XmlElement(name = "SLEORGNPLNTCODEELEMENT")
/*     */   private List<SLEORGNPLNTCODEELEMENT_TMF> SLEORGNPLNTCODELIST;
/*     */   
/*     */   public String getAVAILABILITYACTION() {
/* 257 */     return this.AVAILABILITYACTION;
/*     */   }
/*     */   public String getSTATUS() {
/* 260 */     return this.STATUS;
/*     */   }
/*     */   public String getCOUNTRY_FC() {
/* 263 */     return this.COUNTRY_FC;
/*     */   }
/*     */   public String getANNDATE() {
/* 266 */     return this.ANNDATE;
/*     */   }
/*     */   public String getANNNUMBER() {
/* 269 */     return this.ANNNUMBER;
/*     */   }
/*     */   public String getFIRSTORDER() {
/* 272 */     return this.FIRSTORDER;
/*     */   }
/*     */   public String getPLANNEDAVAILABILITY() {
/* 275 */     return this.PLANNEDAVAILABILITY;
/*     */   }
/*     */   public String getPUBFROM() {
/* 278 */     return this.PUBFROM;
/*     */   }
/*     */   public String getPUBTO() {
/* 281 */     return this.PUBTO;
/*     */   }
/*     */   public String getWDANNDATE() {
/* 284 */     return this.WDANNDATE;
/*     */   }
/*     */   public String getENDOFMARKETANNNUMBER() {
/* 287 */     return this.ENDOFMARKETANNNUMBER;
/*     */   }
/*     */   public String getLASTORDER() {
/* 290 */     return this.LASTORDER;
/*     */   }
/*     */   public String getEOSANNDATE() {
/* 293 */     return this.EOSANNDATE;
/*     */   }
/*     */   public String getENDOFSERVICEDATE() {
/* 296 */     return this.ENDOFSERVICEDATE;
/*     */   }
/*     */   public String getENDOFSERVICEANNNUMBER() {
/* 299 */     return this.ENDOFSERVICEANNNUMBER;
/*     */   }
/*     */   public List<SLEORGGRPELEMENT_TMF> getSLEORGGRPLIST() {
/* 302 */     return this.SLEORGGRPLIST;
/*     */   }
/*     */   public List<SLEORGNPLNTCODEELEMENT_TMF> getSLEORGNPLNTCODELIST() {
/* 305 */     return this.SLEORGNPLNTCODELIST;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\AVAILABILITYELEMENT_TMF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */