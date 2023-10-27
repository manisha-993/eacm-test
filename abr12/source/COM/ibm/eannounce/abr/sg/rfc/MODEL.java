/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
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
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlRootElement(name = "MODEL_UPDATE")
/*     */ public class MODEL
/*     */ {
/*     */   @XmlElement(name = "PDHDOMAIN")
/*     */   private String PDHDOMAIN;
/*     */   @XmlElement(name = "DTSOFMSG")
/*     */   private String DTSOFMSG;
/*     */   @XmlElement(name = "ACTIVITY")
/*     */   private String ACTIVITY;
/*     */   @XmlElement(name = "MODELENTITYTYPE")
/*     */   private String MODELENTITYTYPE;
/*     */   @XmlElement(name = "MODELENTITYID")
/*     */   private String MODELENTITYID;
/*     */   @XmlElement(name = "MACHTYPE")
/*     */   private String MACHTYPE;
/*     */   @XmlElement(name = "MODEL")
/*     */   private String MODEL;
/*     */   @XmlElement(name = "STATUS")
/*     */   private String STATUS;
/*     */   @XmlElement(name = "CATEGORY")
/*     */   private String CATEGORY;
/*     */   @XmlElement(name = "SUBCATEGORY")
/*     */   private String SUBCATEGORY;
/*     */   @XmlElement(name = "GROUP")
/*     */   private String GROUP;
/*     */   @XmlElement(name = "SUBGROUP")
/*     */   private String SUBGROUP;
/*     */   @XmlElement(name = "PRODHIERCD")
/*     */   private String PRODHIERCD;
/*     */   @XmlElement(name = "PRFTCTR")
/*     */   private String PRFTCTR;
/*     */   @XmlElement(name = "ACCTASGNGRP")
/*     */   private String ACCTASGNGRP;
/*     */   @XmlElement(name = "ORDERCODE")
/*     */   private String ORDERCODE;
/*     */   @XmlElement(name = "PRPQINDC")
/*     */   private String PRPQINDC;
/*     */   @XmlElement(name = "PHANTOMMODINDC")
/*     */   private String PHANTOMMODINDC;
/*     */   @XmlElement(name = "DEFAULTCUSTOMIZEABLE")
/*     */   private String DEFAULTCUSTOMIZEABLE;
/*     */   @XmlElement(name = "UNITCLASS")
/*     */   private String UNITCLASS;
/*     */   @XmlElement(name = "PRICEDIND")
/*     */   private String PRICEDIND;
/*     */   @XmlElement(name = "ZEROPRICE")
/*     */   private String ZEROPRICE;
/*     */   @XmlElement(name = "INSTALL")
/*     */   private String INSTALL;
/*     */   @XmlElement(name = "UNSPSC")
/*     */   private String UNSPSC;
/*     */   @XmlElement(name = "AMRTZTNLNGTH")
/*     */   private String AMRTZTNLNGTH;
/*     */   @XmlElement(name = "AMRTZTNSTRT")
/*     */   private String AMRTZTNSTRT;
/*     */   @XmlElement(name = "PRODID")
/*     */   private String PRODID;
/*     */   @XmlElement(name = "SWROYALBEARING")
/*     */   private String SWROYALBEARING;
/*     */   @XmlElement(name = "SOMFAMILY")
/*     */   private String SOMFAMILY;
/*     */   @XmlElement(name = "LIC")
/*     */   private String LIC;
/*     */   @XmlElement(name = "BPCERTSPECBID")
/*     */   private String BPCERTSPECBID;
/*     */   @XmlElement(name = "PRPQAPPRVTYPE")
/*     */   private String PRPQAPPRVTYPE;
/*     */   @XmlElement(name = "SPECBID")
/*     */   private String SPECBID;
/*     */   @XmlElement(name = "PRODSUPRTCD")
/*     */   private String PRODSUPRTCD;
/*     */   @XmlElement(name = "SYSTEMTYPE")
/*     */   private String SYSTEMTYPE;
/*     */   @XmlElement(name = "WWOCCODE")
/*     */   private String WWOCCODE;
/*     */   @XmlElement(name = "ACQRCOCD")
/*     */   private String ACQRCOCD;
/*     */   @XmlElement(name = "SDFCD")
/*     */   private String SDFCD;
/*     */   @XmlElement(name = "SVCLEVCD")
/*     */   private String SVCLEVCD;
/*     */   @XmlElement(name = "SVCPACMACHBRAND")
/*     */   private String SVCPACMACHBRAND;
/*     */   @XmlElement(name = "COVRPRIOD")
/*     */   private String COVRPRIOD;
/*     */   @XmlElementWrapper(name = "TAXCODELIST")
/*     */   @XmlElement(name = "TAXCODEELEMENT")
/*     */   private List<TAXCODE> TAXCODELIST;
/*     */   @XmlElementWrapper(name = "LANGUAGELIST")
/*     */   @XmlElement(name = "LANGUAGEELEMENT")
/*     */   private List<LANGUAGE> LANGUAGELIST;
/*     */   @XmlElementWrapper(name = "AVAILABILITYLIST")
/*     */   @XmlElement(name = "AVAILABILITYELEMENT")
/*     */   private List<AVAILABILITY> AVAILABILITYLIST;
/*     */   @XmlElementWrapper(name = "TAXCATEGORYLIST")
/*     */   @XmlElement(name = "TAXCATEGORYELEMENT")
/*     */   private List<TAXCATEGORY> TAXCATEGORYLIST;
/*     */   @XmlElementWrapper(name = "WARRLIST")
/*     */   @XmlElement(name = "WARRELEMENT")
/*     */   private List<WARRELEMENTTYPE> WARRLIST;
/*     */   @XmlElementWrapper(name = "RELEXPCAMTLIST")
/*     */   @XmlElement(name = "RELEXPCAMTELEMENT")
/*     */   private List<RELEXPCAMT> RELEXPCAMTLIST;
/*     */   @XmlElement(name = "INVNAME")
/*     */   private String INVNAME;
/*     */   @XmlElement(name = "MEASUREMETRIC")
/*     */   private String MEASUREMETRIC;
/*     */   @XmlElement(name = "WARRSVCCOVR")
/*     */   private String WARRSVCCOVR;
/*     */   
/*     */   public List<RELEXPCAMT> getRELEXPCAMTLIST() {
/* 135 */     return this.RELEXPCAMTLIST;
/*     */   }
/*     */   
/*     */   public void setRELEXPCAMTLIST(List<RELEXPCAMT> paramList) {
/* 139 */     this.RELEXPCAMTLIST = paramList;
/*     */   }
/*     */   
/*     */   public String getMEASUREMETRIC() {
/* 143 */     return this.MEASUREMETRIC;
/*     */   }
/*     */   
/*     */   public void setMEASUREMETRIC(String paramString) {
/* 147 */     this.MEASUREMETRIC = paramString;
/*     */   }
/*     */   
/*     */   public String getPDHDOMAIN() {
/* 151 */     return this.PDHDOMAIN;
/*     */   }
/*     */   public void setPDHDOMAIN(String paramString) {
/* 154 */     this.PDHDOMAIN = paramString;
/*     */   }
/*     */   public String getDTSOFMSG() {
/* 157 */     return this.DTSOFMSG;
/*     */   }
/*     */   public void setDTSOFMSG(String paramString) {
/* 160 */     this.DTSOFMSG = paramString;
/*     */   }
/*     */   public String getACTIVITY() {
/* 163 */     return this.ACTIVITY;
/*     */   }
/*     */   public void setACTIVITY(String paramString) {
/* 166 */     this.ACTIVITY = paramString;
/*     */   }
/*     */   public String getMODELENTITYTYPE() {
/* 169 */     return this.MODELENTITYTYPE;
/*     */   }
/*     */   public void setMODELENTITYTYPE(String paramString) {
/* 172 */     this.MODELENTITYTYPE = paramString;
/*     */   }
/*     */   public String getMODELENTITYID() {
/* 175 */     return this.MODELENTITYID;
/*     */   }
/*     */   public void setMODELENTITYID(String paramString) {
/* 178 */     this.MODELENTITYID = paramString;
/*     */   }
/*     */   public String getMACHTYPE() {
/* 181 */     return this.MACHTYPE;
/*     */   }
/*     */   public void setMACHTYPE(String paramString) {
/* 184 */     this.MACHTYPE = paramString;
/*     */   }
/*     */   public String getMODEL() {
/* 187 */     return this.MODEL;
/*     */   }
/*     */   public void setMODEL(String paramString) {
/* 190 */     this.MODEL = paramString;
/*     */   }
/*     */   public String getSTATUS() {
/* 193 */     return this.STATUS;
/*     */   }
/*     */   public void setSTATUS(String paramString) {
/* 196 */     this.STATUS = paramString;
/*     */   }
/*     */   public String getCATEGORY() {
/* 199 */     return this.CATEGORY;
/*     */   }
/*     */   public void setCATEGORY(String paramString) {
/* 202 */     this.CATEGORY = paramString;
/*     */   }
/*     */   public String getSUBCATEGORY() {
/* 205 */     return this.SUBCATEGORY;
/*     */   }
/*     */   public void setSUBCATEGORY(String paramString) {
/* 208 */     this.SUBCATEGORY = paramString;
/*     */   }
/*     */   public String getGROUP() {
/* 211 */     return this.GROUP;
/*     */   }
/*     */   public void setGROUP(String paramString) {
/* 214 */     this.GROUP = paramString;
/*     */   }
/*     */   public String getSUBGROUP() {
/* 217 */     return this.SUBGROUP;
/*     */   }
/*     */   public void setSUBGROUP(String paramString) {
/* 220 */     this.SUBGROUP = paramString;
/*     */   }
/*     */   public String getPRODHIERCD() {
/* 223 */     return this.PRODHIERCD;
/*     */   }
/*     */   public void setPRODHIERCD(String paramString) {
/* 226 */     this.PRODHIERCD = paramString;
/*     */   }
/*     */   public String getPRFTCTR() {
/* 229 */     return this.PRFTCTR;
/*     */   }
/*     */   public void setPRFTCTR(String paramString) {
/* 232 */     this.PRFTCTR = paramString;
/*     */   }
/*     */   public String getACCTASGNGRP() {
/* 235 */     return this.ACCTASGNGRP;
/*     */   }
/*     */   public void setACCTASGNGRP(String paramString) {
/* 238 */     this.ACCTASGNGRP = paramString;
/*     */   }
/*     */   public String getORDERCODE() {
/* 241 */     return this.ORDERCODE;
/*     */   }
/*     */   public String getPRPQINDC() {
/* 244 */     return this.PRPQINDC;
/*     */   }
/*     */   public void setPRPQINDC(String paramString) {
/* 247 */     this.PRPQINDC = paramString;
/*     */   }
/*     */   public String getPHANTOMMODINDC() {
/* 250 */     return this.PHANTOMMODINDC;
/*     */   }
/*     */   public void setPHANTOMMODINDC(String paramString) {
/* 253 */     this.PHANTOMMODINDC = paramString;
/*     */   }
/*     */   public String getDEFAULTCUSTOMIZEABLE() {
/* 256 */     return this.DEFAULTCUSTOMIZEABLE;
/*     */   }
/*     */   public void setDEFAULTCUSTOMIZEABLE(String paramString) {
/* 259 */     this.DEFAULTCUSTOMIZEABLE = paramString;
/*     */   }
/*     */   public void setORDERCODE(String paramString) {
/* 262 */     this.ORDERCODE = paramString;
/*     */   }
/*     */   public String getUNITCLASS() {
/* 265 */     return this.UNITCLASS;
/*     */   }
/*     */   public void setUNITCLASS(String paramString) {
/* 268 */     this.UNITCLASS = paramString;
/*     */   }
/*     */   public String getPRICEDIND() {
/* 271 */     return this.PRICEDIND;
/*     */   }
/*     */   public void setPRICEDIND(String paramString) {
/* 274 */     this.PRICEDIND = paramString;
/*     */   }
/*     */   public String getZEROPRICE() {
/* 277 */     return this.ZEROPRICE;
/*     */   }
/*     */   public void setZEROPRICE(String paramString) {
/* 280 */     this.ZEROPRICE = paramString;
/*     */   }
/*     */   public String getINSTALL() {
/* 283 */     return this.INSTALL;
/*     */   }
/*     */   public void setINSTALL(String paramString) {
/* 286 */     this.INSTALL = paramString;
/*     */   }
/*     */   public String getUNSPSC() {
/* 289 */     return this.UNSPSC;
/*     */   }
/*     */   public void setUNSPSC(String paramString) {
/* 292 */     this.UNSPSC = paramString;
/*     */   }
/*     */   public String getAMRTZTNLNGTH() {
/* 295 */     return this.AMRTZTNLNGTH;
/*     */   }
/*     */   public void setAMRTZTNLNGTH(String paramString) {
/* 298 */     this.AMRTZTNLNGTH = paramString;
/*     */   }
/*     */   public String getAMRTZTNSTRT() {
/* 301 */     return this.AMRTZTNSTRT;
/*     */   }
/*     */   public void setAMRTZTNSTRT(String paramString) {
/* 304 */     this.AMRTZTNSTRT = paramString;
/*     */   }
/*     */   public String getPRODID() {
/* 307 */     return this.PRODID;
/*     */   }
/*     */   public void setPRODID(String paramString) {
/* 310 */     this.PRODID = paramString;
/*     */   }
/*     */   public String getSWROYALBEARING() {
/* 313 */     return this.SWROYALBEARING;
/*     */   }
/*     */   public void setSWROYALBEARING(String paramString) {
/* 316 */     this.SWROYALBEARING = paramString;
/*     */   }
/*     */   public String getSOMFAMILY() {
/* 319 */     return this.SOMFAMILY;
/*     */   }
/*     */   public void setSOMFAMILY(String paramString) {
/* 322 */     this.SOMFAMILY = paramString;
/*     */   }
/*     */   public String getLIC() {
/* 325 */     return this.LIC;
/*     */   }
/*     */   public void setLIC(String paramString) {
/* 328 */     this.LIC = paramString;
/*     */   }
/*     */   public String getBPCERTSPECBID() {
/* 331 */     return this.BPCERTSPECBID;
/*     */   }
/*     */   public void setBPCERTSPECBID(String paramString) {
/* 334 */     this.BPCERTSPECBID = paramString;
/*     */   }
/*     */   public String getPRPQAPPRVTYPE() {
/* 337 */     return this.PRPQAPPRVTYPE;
/*     */   }
/*     */   public void setPRPQAPPRVTYPE(String paramString) {
/* 340 */     this.PRPQAPPRVTYPE = paramString;
/*     */   }
/*     */   public String getSPECBID() {
/* 343 */     return this.SPECBID;
/*     */   }
/*     */   public void setSPECBID(String paramString) {
/* 346 */     this.SPECBID = paramString;
/*     */   }
/*     */   public String getPRODSUPRTCD() {
/* 349 */     return this.PRODSUPRTCD;
/*     */   }
/*     */   public void setPRODSUPRTCD(String paramString) {
/* 352 */     this.PRODSUPRTCD = paramString;
/*     */   }
/*     */   public String getSYSTEMTYPE() {
/* 355 */     return this.SYSTEMTYPE;
/*     */   }
/*     */   public void setSYSTEMTYPE(String paramString) {
/* 358 */     this.SYSTEMTYPE = paramString;
/*     */   }
/*     */   public String getWWOCCODE() {
/* 361 */     return this.WWOCCODE;
/*     */   }
/*     */   public void setWWOCCODE(String paramString) {
/* 364 */     this.WWOCCODE = paramString;
/*     */   }
/*     */   public String getACQRCOCD() {
/* 367 */     return this.ACQRCOCD;
/*     */   }
/*     */   public void setACQRCOCD(String paramString) {
/* 370 */     this.ACQRCOCD = paramString;
/*     */   }
/*     */   public String getSDFCD() {
/* 373 */     return this.SDFCD;
/*     */   }
/*     */   public void setSDFCD(String paramString) {
/* 376 */     this.SDFCD = paramString;
/*     */   }
/*     */   public String getSVCLEVCD() {
/* 379 */     return this.SVCLEVCD;
/*     */   }
/*     */   public void setSVCLEVCD(String paramString) {
/* 382 */     this.SVCLEVCD = paramString;
/*     */   }
/*     */   public String getSVCPACMACHBRAND() {
/* 385 */     return this.SVCPACMACHBRAND;
/*     */   }
/*     */   public void setSVCPACMACHBRAND(String paramString) {
/* 388 */     this.SVCPACMACHBRAND = paramString;
/*     */   }
/*     */   public String getCOVRPRIOD() {
/* 391 */     return this.COVRPRIOD;
/*     */   }
/*     */   public void setCOVRPRIOD(String paramString) {
/* 394 */     this.COVRPRIOD = paramString;
/*     */   }
/*     */   public List<TAXCODE> getTAXCODELIST() {
/* 397 */     return this.TAXCODELIST;
/*     */   }
/*     */   public void setTAXCODELIST(List<TAXCODE> paramList) {
/* 400 */     this.TAXCODELIST = paramList;
/*     */   }
/*     */   public List<LANGUAGE> getLANGUAGELIST() {
/* 403 */     return this.LANGUAGELIST;
/*     */   }
/*     */   public void setLANGUAGELIST(List<LANGUAGE> paramList) {
/* 406 */     this.LANGUAGELIST = paramList;
/*     */   }
/*     */   public List<AVAILABILITY> getAVAILABILITYLIST() {
/* 409 */     return this.AVAILABILITYLIST;
/*     */   }
/*     */   public void setAVAILABILITYLIST(List<AVAILABILITY> paramList) {
/* 412 */     this.AVAILABILITYLIST = paramList;
/*     */   }
/*     */   public List<TAXCATEGORY> getTAXCATEGORYLIST() {
/* 415 */     return this.TAXCATEGORYLIST;
/*     */   }
/*     */   public void setTAXCATEGORYLIST(List<TAXCATEGORY> paramList) {
/* 418 */     this.TAXCATEGORYLIST = paramList;
/*     */   }
/*     */   public String getINVNAME() {
/* 421 */     return this.INVNAME;
/*     */   }
/*     */   public void setINVNAME(String paramString) {
/* 424 */     this.INVNAME = paramString;
/*     */   }
/*     */   public List<WARRELEMENTTYPE> getWARRLIST() {
/* 427 */     return this.WARRLIST;
/*     */   }
/*     */   public void setWARRLIST(List<WARRELEMENTTYPE> paramList) {
/* 430 */     this.WARRLIST = paramList;
/*     */   }
/*     */   
/*     */   public String getWARRSVCCOVR() {
/* 434 */     return this.WARRSVCCOVR;
/*     */   }
/*     */   
/*     */   public void setWARRSVCCOVR(String paramString) {
/* 438 */     this.WARRSVCCOVR = paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\MODEL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */