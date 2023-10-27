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
/*     */ @XmlRootElement(name = "TMF_UPDATE")
/*     */ public class TMF_UPDATE
/*     */ {
/*     */   @XmlElement(name = "PDHDOMAIN")
/*     */   private String PDHDOMAIN;
/*     */   @XmlElement(name = "DTSOFMSG")
/*     */   private String DTSOFMSG;
/*     */   @XmlElement(name = "ACTIVITY")
/*     */   private String ACTIVITY;
/*     */   @XmlElement(name = "STATUS")
/*     */   private String STATUS;
/*     */   @XmlElement(name = "ENTITYTYPE")
/*     */   private String ENTITYTYPE;
/*     */   @XmlElement(name = "ENTITYID")
/*     */   private String ENTITYID;
/*     */   @XmlElement(name = "MODELENTITYTYPE")
/*     */   private String MODELENTITYTYPE;
/*     */   @XmlElement(name = "MODELENTITYID")
/*     */   private String MODELENTITYID;
/*     */   @XmlElement(name = "MACHTYPE")
/*     */   private String MACHTYPE;
/*     */   @XmlElement(name = "MODEL")
/*     */   private String MODEL;
/*     */   @XmlElement(name = "FEATUREENTITYTYPE")
/*     */   private String FEATUREENTITYTYPE;
/*     */   @XmlElement(name = "FEATUREENTITYID")
/*     */   private String FEATUREENTITYID;
/*     */   @XmlElement(name = "FEATURECODE")
/*     */   private String FEATURECODE;
/*     */   @XmlElement(name = "FCCAT")
/*     */   private String FCCAT;
/*     */   @XmlElement(name = "FCTYPE")
/*     */   private String FCTYPE;
/*     */   @XmlElement(name = "ORDERCODE")
/*     */   private String ORDERCODE;
/*     */   @XmlElement(name = "SYSTEMMAX")
/*     */   private String SYSTEMMAX;
/*     */   @XmlElement(name = "SYSTEMMIN")
/*     */   private String SYSTEMMIN;
/*     */   @XmlElement(name = "CONFIGURATORFLAG")
/*     */   private String CONFIGURATORFLAG;
/*     */   @XmlElement(name = "BULKMESINDC")
/*     */   private String BULKMESINDC;
/*     */   @XmlElement(name = "INSTALL")
/*     */   private String INSTALL;
/*     */   @XmlElement(name = "NOCSTSHIP")
/*     */   private String NOCSTSHIP;
/*     */   @XmlElement(name = "WARRSVCCOVR")
/*     */   private String WARRSVCCOVR;
/*     */   @XmlElement(name = "RETURNEDPARTS")
/*     */   private String RETURNEDPARTS;
/*     */   @XmlElementWrapper(name = "OSLIST")
/*     */   @XmlElement(name = "OSELEMENT")
/*     */   private List<OSELEMENT_TMF> OSLIST;
/*     */   @XmlElementWrapper(name = "LANGUAGELIST")
/*     */   @XmlElement(name = "LANGUAGEELEMENT")
/*     */   private List<LANGUAGEELEMENT_TMF> LANGUAGELIST;
/*     */   @XmlElementWrapper(name = "AVAILABILITYLIST")
/*     */   @XmlElement(name = "AVAILABILITYELEMENT")
/*     */   private List<AVAILABILITYELEMENT_TMF> AVAILABILITYLIST;
/*     */   @XmlElementWrapper(name = "AUDIENCELIST")
/*     */   @XmlElement(name = "AUDIENCEELEMENT")
/*     */   private List<AUDIENCEELEMENT_TMF> AUDIENCELIST;
/*     */   @XmlElementWrapper(name = "CATALOGOVERRIDELIST")
/*     */   @XmlElement(name = "CATALOGOVERRIDEELEMENT")
/*     */   private List<CATALOGOVERRIDEELEMENT_TMF> CATALOGOVERRIDELIST;
/*     */   @XmlElementWrapper(name = "WARRLIST")
/*     */   @XmlElement(name = "WARRELEMENT")
/*     */   private List<WARRELEMENT_TMF> WARRLIST;
/*     */   
/*     */   public String getPDHDOMAIN() {
/*  86 */     return this.PDHDOMAIN;
/*     */   }
/*     */   public String getDTSOFMSG() {
/*  89 */     return this.DTSOFMSG;
/*     */   }
/*     */   public String getACTIVITY() {
/*  92 */     return this.ACTIVITY;
/*     */   }
/*     */   public String getSTATUS() {
/*  95 */     return this.STATUS;
/*     */   }
/*     */   public String getENTITYTYPE() {
/*  98 */     return this.ENTITYTYPE;
/*     */   }
/*     */   public String getENTITYID() {
/* 101 */     return this.ENTITYID;
/*     */   }
/*     */   public String getMODELENTITYTYPE() {
/* 104 */     return this.MODELENTITYTYPE;
/*     */   }
/*     */   public String getMODELENTITYID() {
/* 107 */     return this.MODELENTITYID;
/*     */   }
/*     */   public String getMACHTYPE() {
/* 110 */     return this.MACHTYPE;
/*     */   }
/*     */   public String getMODEL() {
/* 113 */     return this.MODEL;
/*     */   }
/*     */   public String getFEATUREENTITYTYPE() {
/* 116 */     return this.FEATUREENTITYTYPE;
/*     */   }
/*     */   public String getFEATUREENTITYID() {
/* 119 */     return this.FEATUREENTITYID;
/*     */   }
/*     */   public String getFEATURECODE() {
/* 122 */     return this.FEATURECODE;
/*     */   }
/*     */   public String getFCCAT() {
/* 125 */     return this.FCCAT;
/*     */   }
/*     */   public String getFCTYPE() {
/* 128 */     return this.FCTYPE;
/*     */   }
/*     */   public String getORDERCODE() {
/* 131 */     return this.ORDERCODE;
/*     */   }
/*     */   public String getSYSTEMMAX() {
/* 134 */     return this.SYSTEMMAX;
/*     */   }
/*     */   public String getSYSTEMMIN() {
/* 137 */     return this.SYSTEMMIN;
/*     */   }
/*     */   public String getCONFIGURATORFLAG() {
/* 140 */     return this.CONFIGURATORFLAG;
/*     */   }
/*     */   public String getBULKMESINDC() {
/* 143 */     return this.BULKMESINDC;
/*     */   }
/*     */   public String getINSTALL() {
/* 146 */     return this.INSTALL;
/*     */   }
/*     */   public String getNOCSTSHIP() {
/* 149 */     return this.NOCSTSHIP;
/*     */   }
/*     */   public String getWARRSVCCOVR() {
/* 152 */     return this.WARRSVCCOVR;
/*     */   }
/*     */   public List<OSELEMENT_TMF> getOSLIST() {
/* 155 */     return this.OSLIST;
/*     */   }
/*     */   public List<LANGUAGEELEMENT_TMF> getLANGUAGELIST() {
/* 158 */     return this.LANGUAGELIST;
/*     */   }
/*     */   public List<AVAILABILITYELEMENT_TMF> getAVAILABILITYLIST() {
/* 161 */     return this.AVAILABILITYLIST;
/*     */   }
/*     */   public List<AUDIENCEELEMENT_TMF> getAUDIENCELIST() {
/* 164 */     return this.AUDIENCELIST;
/*     */   }
/*     */   public List<CATALOGOVERRIDEELEMENT_TMF> getCATALOGOVERRIDELIST() {
/* 167 */     return this.CATALOGOVERRIDELIST;
/*     */   }
/*     */   public List<WARRELEMENT_TMF> getWARRLIST() {
/* 170 */     return this.WARRLIST;
/*     */   }
/*     */   public String getRETURNEDPARTS() {
/* 173 */     return this.RETURNEDPARTS;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\TMF_UPDATE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */