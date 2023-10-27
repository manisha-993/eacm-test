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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlRootElement(name = "SVCMOD_UPDATE")
/*     */ public class SVCMOD
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
/*     */   @XmlElement(name = "PROJECT")
/*     */   private String PROJECT;
/*     */   @XmlElement(name = "DIVISION")
/*     */   private String DIVISION;
/*     */   @XmlElement(name = "PRFTCTR")
/*     */   private String PRFTCTR;
/*     */   @XmlElement(name = "PRODHIERCD")
/*     */   private String PRODHIERCD;
/*     */   @XmlElement(name = "ACCTASGNGRP")
/*     */   private String ACCTASGNGRP;
/*     */   @XmlElement(name = "NEC")
/*     */   private String NEC;
/*     */   @XmlElement(name = "TOS")
/*     */   private String TOS;
/*     */   @XmlElement(name = "SVCFFTYPE")
/*     */   private String SVCFFTYPE;
/*     */   @XmlElement(name = "OFERCONFIGTYPE")
/*     */   private String OFERCONFIGTYPE;
/*     */   @XmlElement(name = "ENDCUSTIDREQ")
/*     */   private String ENDCUSTIDREQ;
/*     */   @XmlElement(name = "FIXTERMPRIOD")
/*     */   private String FIXTERMPRIOD;
/*     */   @XmlElement(name = "PRORATEDOTCALLOW")
/*     */   private String PRORATEDOTCALLOW;
/*     */   @XmlElement(name = "SNGLLNEITEM")
/*     */   private String SNGLLNEITEM;
/*     */   @XmlElement(name = "SVCCHRGOPT")
/*     */   private String SVCCHRGOPT;
/*     */   @XmlElement(name = "TYPEOFWRK")
/*     */   private String TYPEOFWRK;
/*     */   @XmlElement(name = "UOMSI")
/*     */   private String UOMSI;
/*     */   @XmlElement(name = "UPGRADEYN")
/*     */   private String UPGRADEYN;
/*     */   @XmlElement(name = "WWOCCODE")
/*     */   private String WWOCCODE;
/*     */   @XmlElement(name = "UNSPSC")
/*     */   private String UNSPSC;
/*     */   @XmlElement(name = "UNUOM")
/*     */   private String UNUOM;
/*     */   @XmlElement(name = "PCTOFCMPLTINDC")
/*     */   private String PCTOFCMPLTINDC;
/*     */   @XmlElement(name = "SOPRELEVANT")
/*     */   private String SOPRELEVANT;
/*     */   @XmlElement(name = "SOPTASKTYPE")
/*     */   private String SOPTASKTYPE;
/*     */   @XmlElement(name = "ALTID")
/*     */   private String ALTID;
/*     */   @XmlElementWrapper(name = "LANGUAGELIST")
/*     */   @XmlElement(name = "LANGUAGEELEMENT")
/*     */   private List<LANGUAGE> LANGUAGELIST;
/*     */   @XmlElementWrapper(name = "AVAILABILITYLIST")
/*     */   @XmlElement(name = "AVAILABILITYELEMENT")
/*     */   private List<AVAILABILITY> AVAILABILITYLIST;
/*     */   @XmlElementWrapper(name = "TAXCATEGORYLIST")
/*     */   @XmlElement(name = "TAXCATEGORYELEMENT")
/*     */   private List<TAXCATEGORY> TAXCATEGORYLIST;
/*     */   @XmlElementWrapper(name = "TAXCODELIST")
/*     */   @XmlElement(name = "TAXCODEELEMENT")
/*     */   private List<TAXCODE> TAXCODELIST;
/*     */   @XmlElementWrapper(name = "CATATTRIBUTELIST")
/*     */   @XmlElement(name = "CATATTRIBUTEELEMENT")
/*     */   private List<CATATTRIBUTE> CATATTRIBUTELIST;
/*     */   @XmlElementWrapper(name = "UNBUNDCOMPLIST")
/*     */   @XmlElement(name = "UNBUNDCOMPELEMENT")
/*     */   private List<UNBUNDCOMP> UNBUNDCOMPLIST;
/*     */   @XmlElementWrapper(name = "CHRGCOMPLIST")
/*     */   @XmlElement(name = "CHRGCOMPELEMENT")
/*     */   private List<CHRGCOMP> CHRGCOMPLIST;
/*     */   @XmlElementWrapper(name = "SVCEXECTMELIST")
/*     */   @XmlElement(name = "SVCEXECTMEELEMENT")
/*     */   private List<SVCEXECTME> SVCEXECTMELIST;
/*     */   @XmlElementWrapper(name = "SVCSEOREFLIST")
/*     */   @XmlElement(name = "SVCSEOREFELEMENT")
/*     */   private List<SVCSEOREF> SVCSEOREFLIST;
/*     */   @XmlElementWrapper(name = "SVCMODREFLIST")
/*     */   @XmlElement(name = "SVCMODREFELEMENT")
/*     */   private List<SVCMODRE> SVCMODREFLIST;
/*     */   @XmlElementWrapper(name = "SVCSEOLIST")
/*     */   @XmlElement(name = "SVCSEOELEMENT")
/*     */   private List<SVCSEO> SVCSEOLIST;
/*     */   @XmlElementWrapper(name = "PRODSTRUCTLIST")
/*     */   @XmlElement(name = "PRODSTRUCTELEMENT")
/*     */   private List<PRODSTRUCT> PRODSTRUCTLIST;
/*     */   
/*     */   public String getPDHDOMAIN() {
/* 133 */     return this.PDHDOMAIN;
/*     */   }
/*     */   public String getMODELENTITYTYPE() {
/* 136 */     return this.MODELENTITYTYPE;
/*     */   }
/*     */   public String getMODELENTITYID() {
/* 139 */     return this.MODELENTITYID;
/*     */   }
/*     */   public String getMACHTYPE() {
/* 142 */     return this.MACHTYPE;
/*     */   }
/*     */   public String getMODEL() {
/* 145 */     return this.MODEL;
/*     */   }
/*     */   public String getSTATUS() {
/* 148 */     return this.STATUS;
/*     */   }
/*     */   public String getSUBCATEGORY() {
/* 151 */     return this.SUBCATEGORY;
/*     */   }
/*     */   public String getSUBGROUP() {
/* 154 */     return this.SUBGROUP;
/*     */   }
/*     */   public String getPROJECT() {
/* 157 */     return this.PROJECT;
/*     */   }
/*     */   public String getPRFTCTR() {
/* 160 */     return this.PRFTCTR;
/*     */   }
/*     */   public String getPRODHIERCD() {
/* 163 */     return this.PRODHIERCD;
/*     */   }
/*     */   public String getNEC() {
/* 166 */     return this.NEC;
/*     */   }
/*     */   public String getTOS() {
/* 169 */     return this.TOS;
/*     */   }
/*     */   public String getSVCFFTYPE() {
/* 172 */     return this.SVCFFTYPE;
/*     */   }
/*     */   public String getOFERCONFIGTYPE() {
/* 175 */     return this.OFERCONFIGTYPE;
/*     */   }
/*     */   public String getPRORATEDOTCALLOW() {
/* 178 */     return this.PRORATEDOTCALLOW;
/*     */   }
/*     */   public String getSNGLLNEITEM() {
/* 181 */     return this.SNGLLNEITEM;
/*     */   }
/*     */   public String getSVCCHRGOPT() {
/* 184 */     return this.SVCCHRGOPT;
/*     */   }
/*     */   public String getTYPEOFWRK() {
/* 187 */     return this.TYPEOFWRK;
/*     */   }
/*     */   public String getUOMSI() {
/* 190 */     return this.UOMSI;
/*     */   }
/*     */   public String getUPGRADEYN() {
/* 193 */     return this.UPGRADEYN;
/*     */   }
/*     */   public String getWWOCCODE() {
/* 196 */     return this.WWOCCODE;
/*     */   }
/*     */   public String getUNSPSC() {
/* 199 */     return this.UNSPSC;
/*     */   }
/*     */   public String getUNUOM() {
/* 202 */     return this.UNUOM;
/*     */   }
/*     */   public String getPCTOFCMPLTINDC() {
/* 205 */     return this.PCTOFCMPLTINDC;
/*     */   }
/*     */   public String getSOPRELEVANT() {
/* 208 */     return this.SOPRELEVANT;
/*     */   }
/*     */   public String getSOPTASKTYPE() {
/* 211 */     return this.SOPTASKTYPE;
/*     */   }
/*     */   public List<LANGUAGE> getLANGUAGELIST() {
/* 214 */     return this.LANGUAGELIST;
/*     */   }
/*     */   public List<TAXCATEGORY> getTAXCATEGORYLIST() {
/* 217 */     return this.TAXCATEGORYLIST;
/*     */   }
/*     */   public List<TAXCODE> getTAXCODELIST() {
/* 220 */     return this.TAXCODELIST;
/*     */   }
/*     */   public List<UNBUNDCOMP> getUNBUNDCOMPLIST() {
/* 223 */     return this.UNBUNDCOMPLIST;
/*     */   }
/*     */   public List<SVCEXECTME> getSVCEXECTMELIST() {
/* 226 */     return this.SVCEXECTMELIST;
/*     */   }
/*     */   public List<SVCSEOREF> getSVCSEOREFLIST() {
/* 229 */     return this.SVCSEOREFLIST;
/*     */   }
/*     */   public List<SVCMODRE> getSVCMODREFLIST() {
/* 232 */     return this.SVCMODREFLIST;
/*     */   }
/*     */   public List<SVCSEO> getSVCSEOLIST() {
/* 235 */     return this.SVCSEOLIST;
/*     */   }
/*     */   public List<PRODSTRUCT> getPRODSTRUCTLIST() {
/* 238 */     return this.PRODSTRUCTLIST;
/*     */   }
/*     */   public String getDTSOFMSG() {
/* 241 */     return this.DTSOFMSG;
/*     */   }
/*     */   public String getACTIVITY() {
/* 244 */     return this.ACTIVITY;
/*     */   }
/*     */   public String getCATEGORY() {
/* 247 */     return this.CATEGORY;
/*     */   }
/*     */   public String getGROUP() {
/* 250 */     return this.GROUP;
/*     */   }
/*     */   public String getDIVISION() {
/* 253 */     return this.DIVISION;
/*     */   }
/*     */   public String getACCTASGNGRP() {
/* 256 */     return this.ACCTASGNGRP;
/*     */   }
/*     */   public String getENDCUSTIDREQ() {
/* 259 */     return this.ENDCUSTIDREQ;
/*     */   }
/*     */   public String getFIXTERMPRIOD() {
/* 262 */     return this.FIXTERMPRIOD;
/*     */   }
/*     */   public String getALTID() {
/* 265 */     return this.ALTID;
/*     */   }
/*     */   public List<AVAILABILITY> getAVAILABILITYLIST() {
/* 268 */     return this.AVAILABILITYLIST;
/*     */   }
/*     */   public List<CATATTRIBUTE> getCATATTRIBUTELIST() {
/* 271 */     return this.CATATTRIBUTELIST;
/*     */   }
/*     */   public List<CHRGCOMP> getCHRGCOMPLIST() {
/* 274 */     return this.CHRGCOMPLIST;
/*     */   }
/*     */   public boolean hasProds() {
/* 277 */     if (getPRODSTRUCTLIST() != null && getPRODSTRUCTLIST().size() > 0)
/* 278 */       return true; 
/* 279 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\SVCMOD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */