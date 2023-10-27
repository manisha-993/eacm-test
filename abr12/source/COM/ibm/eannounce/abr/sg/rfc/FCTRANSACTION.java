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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlRootElement(name = "FCTRANSACTION_UPDATE")
/*     */ public class FCTRANSACTION
/*     */ {
/*     */   @XmlElement(name = "PDHDOMAIN")
/*     */   private String PDHDOMAIN;
/*     */   @XmlElement(name = "DTSOFMSG")
/*     */   private String DTSOFMSG;
/*     */   @XmlElement(name = "ACTIVITY")
/*     */   private String ACTIVITY;
/*     */   @XmlElement(name = "FEATURECONVERSIONENTITYTYPE")
/*     */   private String FEATURECONVERSIONENTITYTYPE;
/*     */   @XmlElement(name = "FEATURECONVERSIONENTITYID")
/*     */   private String FEATURECONVERSIONENTITYID;
/*     */   @XmlElement(name = "FROMMACHTYPE")
/*     */   private String FROMMACHTYPE;
/*     */   @XmlElement(name = "FROMMODEL")
/*     */   private String FROMMODEL;
/*     */   @XmlElement(name = "FROMFEATURECODE")
/*     */   private String FROMFEATURECODE;
/*     */   @XmlElement(name = "FROMMODELENTITYTYPE")
/*     */   private String FROMMODELENTITYTYPE;
/*     */   @XmlElement(name = "FROMMODELENTITYID")
/*     */   private String FROMMODELENTITYID;
/*     */   @XmlElement(name = "FROMFEATUREENTITYID")
/*     */   private String FROMFEATUREENTITYID;
/*     */   @XmlElement(name = "FROMFEATUREENTITYTYPE")
/*     */   private String FROMFEATUREENTITYTYPE;
/*     */   @XmlElement(name = "TOMACHTYPE")
/*     */   private String TOMACHTYPE;
/*     */   @XmlElement(name = "TOMODEL")
/*     */   private String TOMODEL;
/*     */   @XmlElement(name = "TOFEATURECODE")
/*     */   private String TOFEATURECODE;
/*     */   @XmlElement(name = "TOMODELENTITYTYPE")
/*     */   private String TOMODELENTITYTYPE;
/*     */   @XmlElement(name = "TOMODELENTITYID")
/*     */   private String TOMODELENTITYID;
/*     */   @XmlElement(name = "TOFEATUREENTITYID")
/*     */   private String TOFEATUREENTITYID;
/*     */   @XmlElement(name = "TOFEATUREENTITYTYPE")
/*     */   private String TOFEATUREENTITYTYPE;
/*     */   @XmlElement(name = "STATUS")
/*     */   private String STATUS;
/*     */   @XmlElement(name = "ANNRFANUMBER")
/*     */   private String ANNRFANUMBER;
/*     */   @XmlElement(name = "WDRFANUMBER")
/*     */   private String WDRFANUMBER;
/*     */   @XmlElement(name = "ANNDATE")
/*     */   private String ANNDATE;
/*     */   @XmlElement(name = "WTHDRWEFFCTVDATE")
/*     */   private String WTHDRWEFFCTVDATE;
/*     */   @XmlElement(name = "BOXSWAPREQUIREDFORUPGRADES")
/*     */   private String BOXSWAPREQUIREDFORUPGRADES;
/*     */   @XmlElement(name = "CUSTOMERSETUP")
/*     */   private String CUSTOMERSETUP;
/*     */   @XmlElement(name = "FEATURETRANSACTIONCATEGORY")
/*     */   private String FEATURETRANSACTIONCATEGORY;
/*     */   @XmlElement(name = "FEATURETRANSACTIONSUBCATEGORY")
/*     */   private String FEATURETRANSACTIONSUBCATEGORY;
/*     */   @XmlElement(name = "INSTALLABILITY")
/*     */   private String INSTALLABILITY;
/*     */   @XmlElement(name = "INTERNALNOTES")
/*     */   private String INTERNALNOTES;
/*     */   @XmlElement(name = "PARTSSHIPPEDINDICATOR")
/*     */   private String PARTSSHIPPEDINDICATOR;
/*     */   @XmlElement(name = "QUANTITY")
/*     */   private String QUANTITY;
/*     */   @XmlElement(name = "REMOVEQUANTITY")
/*     */   private String REMOVEQUANTITY;
/*     */   @XmlElement(name = "RETURNEDPARTSMES")
/*     */   private String RETURNEDPARTSMES;
/*     */   @XmlElement(name = "UPGRADETYPE")
/*     */   private String UPGRADETYPE;
/*     */   @XmlElement(name = "ZEROPRICEDINDICATOR")
/*     */   private String ZEROPRICEDINDICATOR;
/*     */   @XmlElementWrapper(name = "COUNTRYLIST")
/*     */   @XmlElement(name = "COUNTRYELEMENT")
/*     */   private List<COUNTRY> COUNTRYLIST;
/*     */   
/*     */   public String getPDHDOMAIN() {
/* 101 */     return this.PDHDOMAIN;
/*     */   }
/*     */   
/*     */   public void setPDHDOMAIN(String paramString) {
/* 105 */     this.PDHDOMAIN = paramString;
/*     */   }
/*     */   
/*     */   public String getDTSOFMSG() {
/* 109 */     return this.DTSOFMSG;
/*     */   }
/*     */   
/*     */   public void setDTSOFMSG(String paramString) {
/* 113 */     this.DTSOFMSG = paramString;
/*     */   }
/*     */   
/*     */   public String getACTIVITY() {
/* 117 */     return this.ACTIVITY;
/*     */   }
/*     */   
/*     */   public void setACTIVITY(String paramString) {
/* 121 */     this.ACTIVITY = paramString;
/*     */   }
/*     */   
/*     */   public String getFEATURECONVERSIONENTITYTYPE() {
/* 125 */     return this.FEATURECONVERSIONENTITYTYPE;
/*     */   }
/*     */   
/*     */   public void setFEATURECONVERSIONENTITYTYPE(String paramString) {
/* 129 */     this.FEATURECONVERSIONENTITYTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getFEATURECONVERSIONENTITYID() {
/* 133 */     return this.FEATURECONVERSIONENTITYID;
/*     */   }
/*     */   
/*     */   public void setFEATURECONVERSIONENTITYID(String paramString) {
/* 137 */     this.FEATURECONVERSIONENTITYID = paramString;
/*     */   }
/*     */   
/*     */   public String getFROMMACHTYPE() {
/* 141 */     return this.FROMMACHTYPE;
/*     */   }
/*     */   
/*     */   public void setFROMMACHTYPE(String paramString) {
/* 145 */     this.FROMMACHTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getFROMMODEL() {
/* 149 */     return this.FROMMODEL;
/*     */   }
/*     */   
/*     */   public void setFROMMODEL(String paramString) {
/* 153 */     this.FROMMODEL = paramString;
/*     */   }
/*     */   
/*     */   public String getFROMFEATURECODE() {
/* 157 */     return this.FROMFEATURECODE;
/*     */   }
/*     */   
/*     */   public void setFROMFEATURECODE(String paramString) {
/* 161 */     this.FROMFEATURECODE = paramString;
/*     */   }
/*     */   
/*     */   public String getFROMMODELENTITYTYPE() {
/* 165 */     return this.FROMMODELENTITYTYPE;
/*     */   }
/*     */   
/*     */   public void setFROMMODELENTITYTYPE(String paramString) {
/* 169 */     this.FROMMODELENTITYTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getFROMMODELENTITYID() {
/* 173 */     return this.FROMMODELENTITYID;
/*     */   }
/*     */   
/*     */   public void setFROMMODELENTITYID(String paramString) {
/* 177 */     this.FROMMODELENTITYID = paramString;
/*     */   }
/*     */   
/*     */   public String getFROMFEATUREENTITYID() {
/* 181 */     return this.FROMFEATUREENTITYID;
/*     */   }
/*     */   
/*     */   public void setFROMFEATUREENTITYID(String paramString) {
/* 185 */     this.FROMFEATUREENTITYID = paramString;
/*     */   }
/*     */   
/*     */   public String getFROMFEATUREENTITYTYPE() {
/* 189 */     return this.FROMFEATUREENTITYTYPE;
/*     */   }
/*     */   
/*     */   public void setFROMFEATUREENTITYTYPE(String paramString) {
/* 193 */     this.FROMFEATUREENTITYTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getTOMACHTYPE() {
/* 197 */     return this.TOMACHTYPE;
/*     */   }
/*     */   
/*     */   public void setTOMACHTYPE(String paramString) {
/* 201 */     this.TOMACHTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getTOMODEL() {
/* 205 */     return this.TOMODEL;
/*     */   }
/*     */   
/*     */   public void setTOMODEL(String paramString) {
/* 209 */     this.TOMODEL = paramString;
/*     */   }
/*     */   
/*     */   public String getTOFEATURECODE() {
/* 213 */     return this.TOFEATURECODE;
/*     */   }
/*     */   
/*     */   public void setTOFEATURECODE(String paramString) {
/* 217 */     this.TOFEATURECODE = paramString;
/*     */   }
/*     */   
/*     */   public String getTOMODELENTITYTYPE() {
/* 221 */     return this.TOMODELENTITYTYPE;
/*     */   }
/*     */   
/*     */   public void setTOMODELENTITYTYPE(String paramString) {
/* 225 */     this.TOMODELENTITYTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getTOMODELENTITYID() {
/* 229 */     return this.TOMODELENTITYID;
/*     */   }
/*     */   
/*     */   public void setTOMODELENTITYID(String paramString) {
/* 233 */     this.TOMODELENTITYID = paramString;
/*     */   }
/*     */   
/*     */   public String getTOFEATUREENTITYID() {
/* 237 */     return this.TOFEATUREENTITYID;
/*     */   }
/*     */   
/*     */   public void setTOFEATUREENTITYID(String paramString) {
/* 241 */     this.TOFEATUREENTITYID = paramString;
/*     */   }
/*     */   
/*     */   public String getTOFEATUREENTITYTYPE() {
/* 245 */     return this.TOFEATUREENTITYTYPE;
/*     */   }
/*     */   
/*     */   public void setTOFEATUREENTITYTYPE(String paramString) {
/* 249 */     this.TOFEATUREENTITYTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getSTATUS() {
/* 253 */     return this.STATUS;
/*     */   }
/*     */   
/*     */   public void setSTATUS(String paramString) {
/* 257 */     this.STATUS = paramString;
/*     */   }
/*     */   
/*     */   public String getANNRFANUMBER() {
/* 261 */     return this.ANNRFANUMBER;
/*     */   }
/*     */   
/*     */   public void setANNRFANUMBER(String paramString) {
/* 265 */     this.ANNRFANUMBER = paramString;
/*     */   }
/*     */   
/*     */   public String getWDRFANUMBER() {
/* 269 */     return this.WDRFANUMBER;
/*     */   }
/*     */   
/*     */   public void setWDRFANUMBER(String paramString) {
/* 273 */     this.WDRFANUMBER = paramString;
/*     */   }
/*     */   
/*     */   public String getANNDATE() {
/* 277 */     return this.ANNDATE;
/*     */   }
/*     */   
/*     */   public void setANNDATE(String paramString) {
/* 281 */     this.ANNDATE = paramString;
/*     */   }
/*     */   
/*     */   public String getWTHDRWEFFCTVDATE() {
/* 285 */     return this.WTHDRWEFFCTVDATE;
/*     */   }
/*     */   
/*     */   public void setWTHDRWEFFCTVDATE(String paramString) {
/* 289 */     this.WTHDRWEFFCTVDATE = paramString;
/*     */   }
/*     */   
/*     */   public String getBOXSWAPREQUIREDFORUPGRADES() {
/* 293 */     return this.BOXSWAPREQUIREDFORUPGRADES;
/*     */   }
/*     */   
/*     */   public void setBOXSWAPREQUIREDFORUPGRADES(String paramString) {
/* 297 */     this.BOXSWAPREQUIREDFORUPGRADES = paramString;
/*     */   }
/*     */   
/*     */   public String getCUSTOMERSETUP() {
/* 301 */     return this.CUSTOMERSETUP;
/*     */   }
/*     */   
/*     */   public void setCUSTOMERSETUP(String paramString) {
/* 305 */     this.CUSTOMERSETUP = paramString;
/*     */   }
/*     */   
/*     */   public String getFEATURETRANSACTIONCATEGORY() {
/* 309 */     return this.FEATURETRANSACTIONCATEGORY;
/*     */   }
/*     */   
/*     */   public void setFEATURETRANSACTIONCATEGORY(String paramString) {
/* 313 */     this.FEATURETRANSACTIONCATEGORY = paramString;
/*     */   }
/*     */   
/*     */   public String getFEATURETRANSACTIONSUBCATEGORY() {
/* 317 */     return this.FEATURETRANSACTIONSUBCATEGORY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFEATURETRANSACTIONSUBCATEGORY(String paramString) {
/* 322 */     this.FEATURETRANSACTIONSUBCATEGORY = paramString;
/*     */   }
/*     */   
/*     */   public String getINSTALLABILITY() {
/* 326 */     return this.INSTALLABILITY;
/*     */   }
/*     */   
/*     */   public void setINSTALLABILITY(String paramString) {
/* 330 */     this.INSTALLABILITY = paramString;
/*     */   }
/*     */   
/*     */   public String getINTERNALNOTES() {
/* 334 */     return this.INTERNALNOTES;
/*     */   }
/*     */   
/*     */   public void setINTERNALNOTES(String paramString) {
/* 338 */     this.INTERNALNOTES = paramString;
/*     */   }
/*     */   
/*     */   public String getPARTSSHIPPEDINDICATOR() {
/* 342 */     return this.PARTSSHIPPEDINDICATOR;
/*     */   }
/*     */   
/*     */   public void setPARTSSHIPPEDINDICATOR(String paramString) {
/* 346 */     this.PARTSSHIPPEDINDICATOR = paramString;
/*     */   }
/*     */   
/*     */   public String getQUANTITY() {
/* 350 */     return this.QUANTITY;
/*     */   }
/*     */   
/*     */   public void setQUANTITY(String paramString) {
/* 354 */     this.QUANTITY = paramString;
/*     */   }
/*     */   
/*     */   public String getREMOVEQUANTITY() {
/* 358 */     return this.REMOVEQUANTITY;
/*     */   }
/*     */   
/*     */   public void setREMOVEQUANTITY(String paramString) {
/* 362 */     this.REMOVEQUANTITY = paramString;
/*     */   }
/*     */   
/*     */   public String getRETURNEDPARTSMES() {
/* 366 */     return this.RETURNEDPARTSMES;
/*     */   }
/*     */   
/*     */   public void setRETURNEDPARTSMES(String paramString) {
/* 370 */     this.RETURNEDPARTSMES = paramString;
/*     */   }
/*     */   
/*     */   public String getUPGRADETYPE() {
/* 374 */     return this.UPGRADETYPE;
/*     */   }
/*     */   
/*     */   public void setUPGRADETYPE(String paramString) {
/* 378 */     this.UPGRADETYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getZEROPRICEDINDICATOR() {
/* 382 */     return this.ZEROPRICEDINDICATOR;
/*     */   }
/*     */   
/*     */   public void setZEROPRICEDINDICATOR(String paramString) {
/* 386 */     this.ZEROPRICEDINDICATOR = paramString;
/*     */   }
/*     */   
/*     */   public List<COUNTRY> getCOUNTRYLIST() {
/* 390 */     return this.COUNTRYLIST;
/*     */   }
/*     */   
/*     */   public void setCOUNTRYLIST(List<COUNTRY> paramList) {
/* 394 */     this.COUNTRYLIST = paramList;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\FCTRANSACTION.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */