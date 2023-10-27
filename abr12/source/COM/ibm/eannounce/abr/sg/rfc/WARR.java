/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlRootElement(name = "WARR_UPDATE")
/*     */ public class WARR
/*     */ {
/*     */   @XmlElement(name = "DTSOFMSG")
/*     */   private String DTSOFMSG;
/*     */   @XmlElement(name = "ACTIVITY")
/*     */   private String ACTIVITY;
/*     */   @XmlElement(name = "STATUS")
/*     */   private String STATUS;
/*     */   @XmlElement(name = "WARRENTITYTYPE")
/*     */   private String WARRENTITYTYPE;
/*     */   @XmlElement(name = "WARRENTITYID")
/*     */   private String WARRENTITYID;
/*     */   @XmlElement(name = "WARRID")
/*     */   private String WARRID;
/*     */   @XmlElement(name = "WARRDATERULEKEY")
/*     */   private String WARRDATERULEKEY;
/*     */   @XmlElement(name = "COVRHR")
/*     */   private String COVRHR;
/*     */   @XmlElement(name = "RESPPROF")
/*     */   private String RESPPROF;
/*     */   @XmlElement(name = "WARRPRIOD")
/*     */   private String WARRPRIOD;
/*     */   @XmlElement(name = "WARRPRIODUOM")
/*     */   private String WARRPRIODUOM;
/*     */   @XmlElement(name = "WARRTYPE")
/*     */   private String WARRTYPE;
/*     */   @XmlElement(name = "WARRCATG")
/*     */   private String WARRCATG;
/*     */   @XmlElement(name = "BHWARRCATEGORY")
/*     */   private String BHWARRCATEGORY;
/*     */   @XmlElement(name = "OEMESAPRTSLBR")
/*     */   private String OEMESAPRTSLBR;
/*     */   @XmlElement(name = "OEMESAPRTSONY")
/*     */   private String OEMESAPRTSONY;
/*     */   @XmlElement(name = "TIERWSU")
/*     */   private String TIERWSU;
/*     */   @XmlElement(name = "TECHADV")
/*     */   private String TECHADV;
/*     */   @XmlElement(name = "REMCODLOAD")
/*     */   private String REMCODLOAD;
/*     */   @XmlElement(name = "ENHCOMRES")
/*     */   private String ENHCOMRES;
/*     */   @XmlElement(name = "PREDSUPP")
/*     */   private String PREDSUPP;
/*     */   @XmlElement(name = "TIERMAIN")
/*     */   private String TIERMAIN;
/*     */   @XmlElement(name = "SVC1")
/*     */   private String SVC1;
/*     */   @XmlElement(name = "SVC2")
/*     */   private String SVC2;
/*     */   @XmlElement(name = "SVC3")
/*     */   private String SVC3;
/*     */   @XmlElement(name = "SVC4")
/*     */   private String SVC4;
/*     */   @XmlElementWrapper(name = "LANGUAGELIST")
/*     */   @XmlElement(name = "LANGUAGEELEMENT")
/*     */   private List<LANGUAGEELEMENT_WARR> LANGUAGELIST;
/*     */   
/*     */   public String getDTSOFMSG() {
/*  78 */     return this.DTSOFMSG;
/*     */   }
/*     */   
/*     */   public void setDTSOFMSG(String paramString) {
/*  82 */     this.DTSOFMSG = paramString;
/*     */   }
/*     */   
/*     */   public String getACTIVITY() {
/*  86 */     return this.ACTIVITY;
/*     */   }
/*     */   
/*     */   public void setACTIVITY(String paramString) {
/*  90 */     this.ACTIVITY = paramString;
/*     */   }
/*     */   
/*     */   public String getSTATUS() {
/*  94 */     return this.STATUS;
/*     */   }
/*     */   
/*     */   public void setSTATUS(String paramString) {
/*  98 */     this.STATUS = paramString;
/*     */   }
/*     */   
/*     */   public String getWARRENTITYTYPE() {
/* 102 */     return this.WARRENTITYTYPE;
/*     */   }
/*     */   
/*     */   public void setWARRENTITYTYPE(String paramString) {
/* 106 */     this.WARRENTITYTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getWARRENTITYID() {
/* 110 */     return this.WARRENTITYID;
/*     */   }
/*     */   
/*     */   public void setWARRENTITYID(String paramString) {
/* 114 */     this.WARRENTITYID = paramString;
/*     */   }
/*     */   
/*     */   public String getWARRID() {
/* 118 */     return this.WARRID;
/*     */   }
/*     */   
/*     */   public void setWARRID(String paramString) {
/* 122 */     this.WARRID = paramString;
/*     */   }
/*     */   
/*     */   public String getWARRDATERULEKEY() {
/* 126 */     return this.WARRDATERULEKEY;
/*     */   }
/*     */   
/*     */   public void setWARRDATERULEKEY(String paramString) {
/* 130 */     this.WARRDATERULEKEY = paramString;
/*     */   }
/*     */   
/*     */   public String getCOVRHR() {
/* 134 */     return this.COVRHR;
/*     */   }
/*     */   
/*     */   public void setCOVRHR(String paramString) {
/* 138 */     this.COVRHR = paramString;
/*     */   }
/*     */   
/*     */   public String getRESPPROF() {
/* 142 */     return this.RESPPROF;
/*     */   }
/*     */   
/*     */   public void setRESPPROF(String paramString) {
/* 146 */     this.RESPPROF = paramString;
/*     */   }
/*     */   
/*     */   public String getWARRPRIOD() {
/* 150 */     return this.WARRPRIOD;
/*     */   }
/*     */   
/*     */   public void setWARRPRIOD(String paramString) {
/* 154 */     this.WARRPRIOD = paramString;
/*     */   }
/*     */   
/*     */   public String getWARRPRIODUOM() {
/* 158 */     return this.WARRPRIODUOM;
/*     */   }
/*     */   
/*     */   public void setWARRPRIODUOM(String paramString) {
/* 162 */     this.WARRPRIODUOM = paramString;
/*     */   }
/*     */   
/*     */   public String getWARRTYPE() {
/* 166 */     return this.WARRTYPE;
/*     */   }
/*     */   
/*     */   public void setWARRTYPE(String paramString) {
/* 170 */     this.WARRTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getWARRCATG() {
/* 174 */     return this.WARRCATG;
/*     */   }
/*     */   
/*     */   public void setWARRCATG(String paramString) {
/* 178 */     this.WARRCATG = paramString;
/*     */   }
/*     */   
/*     */   public String getBHWARRCATEGORY() {
/* 182 */     return this.BHWARRCATEGORY;
/*     */   }
/*     */   
/*     */   public void setBHWARRCATEGORY(String paramString) {
/* 186 */     this.BHWARRCATEGORY = paramString;
/*     */   }
/*     */   
/*     */   public String getOEMESAPRTSLBR() {
/* 190 */     return this.OEMESAPRTSLBR;
/*     */   }
/*     */   
/*     */   public void setOEMESAPRTSLBR(String paramString) {
/* 194 */     this.OEMESAPRTSLBR = paramString;
/*     */   }
/*     */   
/*     */   public String getOEMESAPRTSONY() {
/* 198 */     return this.OEMESAPRTSONY;
/*     */   }
/*     */   
/*     */   public void setOEMESAPRTSONY(String paramString) {
/* 202 */     this.OEMESAPRTSONY = paramString;
/*     */   }
/*     */   
/*     */   public String getTIERWSU() {
/* 206 */     return this.TIERWSU;
/*     */   }
/*     */   
/*     */   public void setTIERWSU(String paramString) {
/* 210 */     this.TIERWSU = paramString;
/*     */   }
/*     */   
/*     */   public String getTECHADV() {
/* 214 */     return this.TECHADV;
/*     */   }
/*     */   
/*     */   public void setTECHADV(String paramString) {
/* 218 */     this.TECHADV = paramString;
/*     */   }
/*     */   
/*     */   public String getREMCODLOAD() {
/* 222 */     return this.REMCODLOAD;
/*     */   }
/*     */   
/*     */   public void setREMCODLOAD(String paramString) {
/* 226 */     this.REMCODLOAD = paramString;
/*     */   }
/*     */   
/*     */   public String getENHCOMRES() {
/* 230 */     return this.ENHCOMRES;
/*     */   }
/*     */   
/*     */   public void setENHCOMRES(String paramString) {
/* 234 */     this.ENHCOMRES = paramString;
/*     */   }
/*     */   
/*     */   public String getPREDSUPP() {
/* 238 */     return this.PREDSUPP;
/*     */   }
/*     */   
/*     */   public void setPREDSUPP(String paramString) {
/* 242 */     this.PREDSUPP = paramString;
/*     */   }
/*     */   
/*     */   public String getTIERMAIN() {
/* 246 */     return this.TIERMAIN;
/*     */   }
/*     */   
/*     */   public void setTIERMAIN(String paramString) {
/* 250 */     this.TIERMAIN = paramString;
/*     */   }
/*     */   
/*     */   public List<LANGUAGEELEMENT_WARR> getLANGUAGELIST() {
/* 254 */     return this.LANGUAGELIST;
/*     */   }
/*     */   
/*     */   public void setLANGUAGELIST(List<LANGUAGEELEMENT_WARR> paramList) {
/* 258 */     this.LANGUAGELIST = paramList;
/*     */   }
/*     */   
/*     */   public String getSVC1() {
/* 262 */     return this.SVC1;
/*     */   }
/*     */   
/*     */   public void setSVC1(String paramString) {
/* 266 */     this.SVC1 = paramString;
/*     */   }
/*     */   
/*     */   public String getSVC2() {
/* 270 */     return this.SVC2;
/*     */   }
/*     */   
/*     */   public void setSVC2(String paramString) {
/* 274 */     this.SVC2 = paramString;
/*     */   }
/*     */   
/*     */   public String getSVC3() {
/* 278 */     return this.SVC3;
/*     */   }
/*     */   
/*     */   public void setSVC3(String paramString) {
/* 282 */     this.SVC3 = paramString;
/*     */   }
/*     */   
/*     */   public String getSVC4() {
/* 286 */     return this.SVC4;
/*     */   }
/*     */   
/*     */   public void setSVC4(String paramString) {
/* 290 */     this.SVC4 = paramString;
/*     */   }
/*     */   
/*     */   @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   static class LANGUAGEELEMENT_WARR
/*     */   {
/*     */     @XmlElement(name = "NLSID")
/*     */     private String NLSID;
/*     */     @XmlElement(name = "INVNAME")
/*     */     private String INVNAME;
/*     */     @XmlElement(name = "MKTGNAME")
/*     */     private String MKTGNAME;
/*     */     @XmlElement(name = "WARRDESC")
/*     */     private String WARRDESC;
/*     */     
/*     */     public String getNLSID() {
/* 307 */       return this.NLSID;
/*     */     }
/*     */     public void setNLSID(String param1String) {
/* 310 */       this.NLSID = param1String;
/*     */     }
/*     */     public String getINVNAME() {
/* 313 */       return this.INVNAME;
/*     */     }
/*     */     public void setINVNAME(String param1String) {
/* 316 */       this.INVNAME = param1String;
/*     */     }
/*     */     public String getMKTGNAME() {
/* 319 */       return this.MKTGNAME;
/*     */     }
/*     */     public void setMKTGNAME(String param1String) {
/* 322 */       this.MKTGNAME = param1String;
/*     */     }
/*     */     public String getWARRDESC() {
/* 325 */       return this.WARRDESC;
/*     */     }
/*     */     public void setWARRDESC(String param1String) {
/* 328 */       this.WARRDESC = param1String;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\WARR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */