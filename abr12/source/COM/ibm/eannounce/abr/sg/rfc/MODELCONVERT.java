/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
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
/*     */ @XmlRootElement(name = "MODELCONVERT_UPDATE")
/*     */ public class MODELCONVERT
/*     */ {
/*     */   @XmlElement(name = "MODELUPGRADEENTITYTYPE")
/*     */   private String MODELUPGRADEENTITYTYPE;
/*     */   @XmlElement(name = "MODELUPGRADEENTITYID")
/*     */   private String MODELUPGRADEENTITYID;
/*     */   @XmlElement(name = "FROMMACHTYPE")
/*     */   private String FROMMACHTYPE;
/*     */   @XmlElement(name = "FROMMODEL")
/*     */   private String FROMMODEL;
/*     */   @XmlElement(name = "TOMODEL")
/*     */   private String TOMODEL;
/*     */   @XmlElement(name = "TOMODELTYPE")
/*     */   private String TOMODELTYPE;
/*     */   @XmlElement(name = "TOMACHTYPE")
/*     */   private String TOMACHTYPE;
/*     */   @XmlElement(name = "PDHDOMAIN")
/*     */   private String PDHDOMAIN;
/*     */   @XmlElement(name = "AVAILABILITYLIST")
/*     */   private List<AVAILABILITYLIST> AVAILABILITYLIST;
/*     */   
/*     */   public String getPDHDOMAIN() {
/*  41 */     return this.PDHDOMAIN;
/*     */   }
/*     */   
/*     */   public void setPDHDOMAIN(String paramString) {
/*  45 */     this.PDHDOMAIN = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMODELUPGRADEENTITYTYPE() {
/*  52 */     return this.MODELUPGRADEENTITYTYPE;
/*     */   }
/*     */   
/*     */   public void setMODELUPGRADEENTITYTYPE(String paramString) {
/*  56 */     this.MODELUPGRADEENTITYTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getMODELUPGRADEENTITYID() {
/*  60 */     return this.MODELUPGRADEENTITYID;
/*     */   }
/*     */   
/*     */   public void setMODELUPGRADEENTITYID(String paramString) {
/*  64 */     this.MODELUPGRADEENTITYID = paramString;
/*     */   }
/*     */   
/*     */   public String getFROMMACHTYPE() {
/*  68 */     return this.FROMMACHTYPE;
/*     */   }
/*     */   
/*     */   public void setFROMMACHTYPE(String paramString) {
/*  72 */     this.FROMMACHTYPE = paramString;
/*     */   }
/*     */   
/*     */   public String getTOMACHTYPE() {
/*  76 */     return this.TOMACHTYPE;
/*     */   }
/*     */   
/*     */   public void setTOMACHTYPE(String paramString) {
/*  80 */     this.TOMACHTYPE = paramString;
/*     */   }
/*     */   
/*     */   public List<AVAILABILITYLIST> getAVAILABILITYLIST() {
/*  84 */     return this.AVAILABILITYLIST;
/*     */   }
/*     */   
/*     */   public void setAVAILABILITYLIST(List<AVAILABILITYLIST> paramList) {
/*  88 */     this.AVAILABILITYLIST = paramList;
/*     */   }
/*     */   
/*     */   public String getFROMMODEL() {
/*  92 */     return this.FROMMODEL;
/*     */   }
/*     */   
/*     */   public void setFROMMODEL(String paramString) {
/*  96 */     this.FROMMODEL = paramString;
/*     */   }
/*     */   
/*     */   public String getTOMODEL() {
/* 100 */     return this.TOMODEL;
/*     */   }
/*     */   
/*     */   public void setTOMODEL(String paramString) {
/* 104 */     this.TOMODEL = paramString;
/*     */   }
/*     */   
/*     */   public String getTOMODELTYPE() {
/* 108 */     return this.TOMODELTYPE;
/*     */   }
/*     */   
/*     */   public void setTOMODELTYPE(String paramString) {
/* 112 */     this.TOMODELTYPE = paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\MODELCONVERT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */