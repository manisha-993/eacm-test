/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementWrapper;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlRootElement(name = "FEATURE_UPDATE")
/*     */ public class FEATURE
/*     */ {
/*     */   @XmlElement(name = "PDHDOMAIN")
/*     */   private String PDHDOMAIN;
/*     */   @XmlElement(name = "DTSOFMSG")
/*     */   private String DTSOFMSG;
/*     */   @XmlElement(name = "ACTIVITY")
/*     */   private String ACTIVITY;
/*     */   @XmlElement(name = "ENTITYTYPE")
/*     */   private String ENTITYTYPE;
/*     */   @XmlElement(name = "ENTITYID")
/*     */   private String ENTITYID;
/*     */   @XmlElement(name = "FEATURECODE")
/*     */   private String FEATURECODE;
/*     */   @XmlElement(name = "FCTYPE")
/*     */   private String FCTYPE;
/*     */   @XmlElement(name = "STATUS")
/*     */   private String STATUS;
/*     */   @XmlElement(name = "PRICEDFEATURE")
/*     */   private String PRICEDFEATURE;
/*     */   @XmlElement(name = "ZEROPRICE")
/*     */   private String ZEROPRICE;
/*     */   @XmlElement(name = "CHARGEOPTION")
/*     */   private String CHARGEOPTION;
/*     */   @XmlElement(name = "FCCAT")
/*     */   private String FCCAT;
/*     */   @XmlElement(name = "FCSUBCAT")
/*     */   private String FCSUBCAT;
/*     */   @XmlElement(name = "FCGRP")
/*     */   private String FCGRP;
/*     */   @XmlElement(name = "CONFIGURATORFLAG")
/*     */   private String CONFIGURATORFLAG;
/*     */   @XmlElement(name = "LICNSOPTTYPE")
/*     */   private String LICNSOPTTYPE;
/*     */   @XmlElement(name = "MAINTPRICE")
/*     */   private String MAINTPRICE;
/*     */   @XmlElement(name = "HWORINFOFEATURE")
/*     */   private String HWORINFOFEATURE;
/*     */   @XmlElement(name = "LICENSETYPE")
/*     */   private String LICENSETYPE;
/*     */   @XmlElement(name = "FIRSTANNDATE")
/*     */   private String FIRSTANNDATE;
/*     */   @XmlElement(name = "WTHDRWEFFCTVDATE")
/*     */   private String WTHDRWEFFCTVDATE;
/*     */   @XmlElementWrapper(name = "LANGUAGELIST")
/*     */   @XmlElement(name = "LANGUAGEELEMENT")
/*     */   public List<LANGUAGEELEMENT_FEATURE> LANGUAGELIST;
/*     */   @XmlElementWrapper(name = "COUNTRYLIST")
/*     */   @XmlElement(name = "COUNTRYELEMENT")
/*     */   private List<COUNTRYELEMENT_FEATURE> COUNTRYLIST;
/*     */   @XmlElementWrapper(name = "CATATTRIBUTELIST")
/*     */   @XmlElement(name = "CATATTRIBUTEELEMENT")
/*     */   private List<CATATTRIBUTEELEMENT_FEATURE> CATATTRIBUTELIST;
/*     */   
/*     */   public String getPDHDOMAIN() {
/*  71 */     return this.PDHDOMAIN;
/*     */   }
/*     */   public String getDTSOFMSG() {
/*  74 */     return this.DTSOFMSG;
/*     */   }
/*     */   public String getACTIVITY() {
/*  77 */     return this.ACTIVITY;
/*     */   }
/*     */   public String getENTITYTYPE() {
/*  80 */     return this.ENTITYTYPE;
/*     */   }
/*     */   public String getENTITYID() {
/*  83 */     return this.ENTITYID;
/*     */   }
/*     */   public String getFEATURECODE() {
/*  86 */     return this.FEATURECODE;
/*     */   }
/*     */   public String getFCTYPE() {
/*  89 */     return this.FCTYPE;
/*     */   }
/*     */   public String getSTATUS() {
/*  92 */     return this.STATUS;
/*     */   }
/*     */   public String getPRICEDFEATURE() {
/*  95 */     return this.PRICEDFEATURE;
/*     */   }
/*     */   public String getZEROPRICE() {
/*  98 */     return this.ZEROPRICE;
/*     */   }
/*     */   public String getCHARGEOPTION() {
/* 101 */     return this.CHARGEOPTION;
/*     */   }
/*     */   public String getFCCAT() {
/* 104 */     return this.FCCAT;
/*     */   }
/*     */   public String getFCSUBCAT() {
/* 107 */     return this.FCSUBCAT;
/*     */   }
/*     */   public String getFCGRP() {
/* 110 */     return this.FCGRP;
/*     */   }
/*     */   public String getCONFIGURATORFLAG() {
/* 113 */     return this.CONFIGURATORFLAG;
/*     */   }
/*     */   public String getLICNSOPTTYPE() {
/* 116 */     return this.LICNSOPTTYPE;
/*     */   }
/*     */   public String getMAINTPRICE() {
/* 119 */     return this.MAINTPRICE;
/*     */   }
/*     */   public String getHWORINFOFEATURE() {
/* 122 */     return this.HWORINFOFEATURE;
/*     */   }
/*     */   public String getLICENSETYPE() {
/* 125 */     return this.LICENSETYPE;
/*     */   }
/*     */   public String getFIRSTANNDATE() {
/* 128 */     return this.FIRSTANNDATE;
/*     */   }
/*     */   public String getWTHDRWEFFCTVDATE() {
/* 131 */     return this.WTHDRWEFFCTVDATE;
/*     */   }
/*     */   public List<LANGUAGEELEMENT_FEATURE> getLANGUAGELIST() {
/* 134 */     return this.LANGUAGELIST;
/*     */   }
/*     */   public List<COUNTRYELEMENT_FEATURE> getCOUNTRYLIST() {
/* 137 */     return this.COUNTRYLIST;
/*     */   }
/*     */   public List<CATATTRIBUTEELEMENT_FEATURE> getCATATTRIBUTELIST() {
/* 140 */     return this.CATATTRIBUTELIST;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\FEATURE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */