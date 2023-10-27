/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementWrapper;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlRootElement(name = "SVCLEV_UPDATE")
/*     */ public class SVCLEV
/*     */ {
/*     */   @XmlElement(name = "DTSOFMSG")
/*     */   private String DTSOFMSG;
/*     */   @XmlElement(name = "SVCLEVCD")
/*     */   private String SVCLEVCD;
/*     */   @XmlElement(name = "COVRSHRTDESC")
/*     */   private String COVRSHRTDESC;
/*     */   @XmlElement(name = "EFFECTIVEDATE")
/*     */   private String EFFECTIVEDATE;
/*     */   @XmlElement(name = "ENDDATE")
/*     */   private String ENDDATE;
/*     */   @XmlElement(name = "SVCDELIVMETH")
/*     */   private String SVCDELIVMETH;
/*     */   @XmlElement(name = "FIXTME")
/*     */   private String FIXTME;
/*     */   @XmlElement(name = "FIXTMEUOM")
/*     */   private String FIXTMEUOM;
/*     */   @XmlElement(name = "FIXTMEOBJIVE")
/*     */   private String FIXTMEOBJIVE;
/*     */   @XmlElement(name = "ONSITERESPTME")
/*     */   private String ONSITERESPTME;
/*     */   @XmlElement(name = "ONSITERESPTMEUOM")
/*     */   private String ONSITERESPTMEUOM;
/*     */   @XmlElement(name = "ONSITERESPTMEOBJIVE")
/*     */   private String ONSITERESPTMEOBJIVE;
/*     */   @XmlElement(name = "CONTTME")
/*     */   private String CONTTME;
/*     */   @XmlElement(name = "CONTTMEUOM")
/*     */   private String CONTTMEUOM;
/*     */   @XmlElement(name = "CONTTMEOBJIVE")
/*     */   private String CONTTMEOBJIVE;
/*     */   @XmlElement(name = "TRNARNDTME")
/*     */   private String TRNARNDTME;
/*     */   @XmlElement(name = "TRNARNDTMEUOM")
/*     */   private String TRNARNDTMEUOM;
/*     */   @XmlElement(name = "TRNARNDTMEOBJIVE")
/*     */   private String TRNARNDTMEOBJIVE;
/*     */   @XmlElement(name = "PARTARRVTME")
/*     */   private String PARTARRVTME;
/*     */   @XmlElement(name = "PARTARRVTMEUOM")
/*     */   private String PARTARRVTMEUOM;
/*     */   @XmlElement(name = "PARTARRVTMEOBJIVE")
/*     */   private String PARTARRVTMEOBJIVE;
/*     */   @XmlElementWrapper(name = "LANGUAGELIST")
/*     */   @XmlElement(name = "LANGUAGEELEMENT")
/*     */   private List<LANGUAGE> LANGUAGELIST;
/*     */   
/*     */   public String getDTSOFMSG() {
/*  77 */     return this.DTSOFMSG;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDTSOFMSG(String paramString) {
/*  82 */     this.DTSOFMSG = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSVCLEVCD() {
/*  87 */     return this.SVCLEVCD;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSVCLEVCD(String paramString) {
/*  92 */     this.SVCLEVCD = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCOVRSHRTDESC() {
/*  97 */     return this.COVRSHRTDESC;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCOVRSHRTDESC(String paramString) {
/* 102 */     this.COVRSHRTDESC = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEFFECTIVEDATE() {
/* 107 */     return this.EFFECTIVEDATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEFFECTIVEDATE(String paramString) {
/* 112 */     this.EFFECTIVEDATE = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getENDDATE() {
/* 117 */     return this.ENDDATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setENDDATE(String paramString) {
/* 122 */     this.ENDDATE = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSVCDELIVMETH() {
/* 127 */     return this.SVCDELIVMETH;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSVCDELIVMETH(String paramString) {
/* 132 */     this.SVCDELIVMETH = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFIXTME() {
/* 137 */     return this.FIXTME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFIXTME(String paramString) {
/* 142 */     this.FIXTME = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFIXTMEUOM() {
/* 147 */     return this.FIXTMEUOM;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFIXTMEUOM(String paramString) {
/* 152 */     this.FIXTMEUOM = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFIXTMEOBJIVE() {
/* 157 */     return this.FIXTMEOBJIVE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFIXTMEOBJIVE(String paramString) {
/* 162 */     this.FIXTMEOBJIVE = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getONSITERESPTME() {
/* 167 */     return this.ONSITERESPTME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setONSITERESPTME(String paramString) {
/* 172 */     this.ONSITERESPTME = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getONSITERESPTMEUOM() {
/* 177 */     return this.ONSITERESPTMEUOM;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setONSITERESPTMEUOM(String paramString) {
/* 182 */     this.ONSITERESPTMEUOM = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getONSITERESPTMEOBJIVE() {
/* 187 */     return this.ONSITERESPTMEOBJIVE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setONSITERESPTMEOBJIVE(String paramString) {
/* 192 */     this.ONSITERESPTMEOBJIVE = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCONTTME() {
/* 197 */     return this.CONTTME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCONTTME(String paramString) {
/* 202 */     this.CONTTME = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCONTTMEUOM() {
/* 207 */     return this.CONTTMEUOM;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCONTTMEUOM(String paramString) {
/* 212 */     this.CONTTMEUOM = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCONTTMEOBJIVE() {
/* 217 */     return this.CONTTMEOBJIVE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCONTTMEOBJIVE(String paramString) {
/* 222 */     this.CONTTMEOBJIVE = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTRNARNDTME() {
/* 227 */     return this.TRNARNDTME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTRNARNDTME(String paramString) {
/* 232 */     this.TRNARNDTME = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTRNARNDTMEUOM() {
/* 237 */     return this.TRNARNDTMEUOM;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTRNARNDTMEUOM(String paramString) {
/* 242 */     this.TRNARNDTMEUOM = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTRNARNDTMEOBJIVE() {
/* 247 */     return this.TRNARNDTMEOBJIVE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTRNARNDTMEOBJIVE(String paramString) {
/* 252 */     this.TRNARNDTMEOBJIVE = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPARTARRVTME() {
/* 257 */     return this.PARTARRVTME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPARTARRVTME(String paramString) {
/* 262 */     this.PARTARRVTME = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPARTARRVTMEUOM() {
/* 267 */     return this.PARTARRVTMEUOM;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPARTARRVTMEUOM(String paramString) {
/* 272 */     this.PARTARRVTMEUOM = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPARTARRVTMEOBJIVE() {
/* 277 */     return this.PARTARRVTMEOBJIVE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPARTARRVTMEOBJIVE(String paramString) {
/* 282 */     this.PARTARRVTMEOBJIVE = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<LANGUAGE> getLANGUAGELIST() {
/* 287 */     return this.LANGUAGELIST;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLANGUAGELIST(List<LANGUAGE> paramList) {
/* 292 */     this.LANGUAGELIST = paramList;
/*     */   }
/*     */ 
/*     */   
/*     */   @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   class SLCNTRYCOND
/*     */   {
/*     */     @XmlElement(name = "SLCNTRYCONDACTION")
/*     */     private String SLCNTRYCONDACTION;
/*     */     
/*     */     @XmlElement(name = "ENTITYTYPE")
/*     */     private String ENTITYTYPE;
/*     */     @XmlElement(name = "ENTITYID")
/*     */     private String ENTITYID;
/*     */     
/*     */     public String getSLCNTRYCONDACTION() {
/* 309 */       return this.SLCNTRYCONDACTION;
/*     */     }
/*     */     public void setSLCNTRYCONDACTION(String param1String) {
/* 312 */       this.SLCNTRYCONDACTION = param1String;
/*     */     }
/*     */     public String getENTITYTYPE() {
/* 315 */       return this.ENTITYTYPE;
/*     */     }
/*     */     public void setENTITYTYPE(String param1String) {
/* 318 */       this.ENTITYTYPE = param1String;
/*     */     }
/*     */     public String getENTITYID() {
/* 321 */       return this.ENTITYID;
/*     */     }
/*     */     public void setENTITYID(String param1String) {
/* 324 */       this.ENTITYID = param1String;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\SVCLEV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */