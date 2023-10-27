/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLFCTRANSMODElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLRelatorSearchElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLVMElem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSFCTRANSABR
/*     */   extends XMLMQRoot
/*     */ {
/*  59 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("FCTRANSACTION_UPDATE"); static {
/*  60 */     XMLMAP.addChild((XMLElem)new XMLVMElem("FCTRANSACTION_UPDATE", "1"));
/*     */     
/*  62 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  63 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  64 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  65 */     XMLMAP.addChild(new XMLElem("FEATURECONVERSIONENTITYTYPE", "ENTITYTYPE"));
/*  66 */     XMLMAP.addChild(new XMLElem("FEATURECONVERSIONENTITYID", "ENTITYID"));
/*  67 */     XMLMAP.addChild(new XMLElem("FROMMACHTYPE", "FROMMACHTYPE"));
/*  68 */     XMLMAP.addChild((XMLElem)new XMLFCTRANSMODElem("FROMMODEL", "FROMMODEL", "***"));
/*  69 */     XMLMAP.addChild(new XMLElem("FROMFEATURECODE", "FROMFEATURECODE"));
/*  70 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("FROMMODELENTITYTYPE", "MODEL"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     XMLRelatorSearchElem xMLRelatorSearchElem = new XMLRelatorSearchElem("FROMMODELENTITYID", "FROMFEATUREENTITYID", "SRDPRODSTRUCT33", "PRODSTRUCT");
/*  81 */     xMLRelatorSearchElem.addSearchAttr("FROMMACHTYPE", "MODEL:MACHTYPEATR");
/*  82 */     xMLRelatorSearchElem.addSearchAttr("FROMMODEL", "MODEL:MODELATR");
/*  83 */     xMLRelatorSearchElem.addSearchAttr("FROMFEATURECODE", "FEATURE:FEATURECODE");
/*  84 */     XMLMAP.addChild((XMLElem)xMLRelatorSearchElem);
/*     */ 
/*     */     
/*  87 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("FROMFEATUREENTITYTYPE", "FEATURE"));
/*  88 */     XMLMAP.addChild(new XMLElem("TOMACHTYPE", "TOMACHTYPE"));
/*  89 */     XMLMAP.addChild((XMLElem)new XMLFCTRANSMODElem("TOMODEL", "TOMODEL", "***"));
/*  90 */     XMLMAP.addChild(new XMLElem("TOFEATURECODE", "TOFEATURECODE"));
/*  91 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("TOMODELENTITYTYPE", "MODEL"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     xMLRelatorSearchElem = new XMLRelatorSearchElem("TOMODELENTITYID", "TOFEATUREENTITYID", "SRDPRODSTRUCT33", "PRODSTRUCT");
/* 102 */     xMLRelatorSearchElem.addSearchAttr("TOMACHTYPE", "MODEL:MACHTYPEATR");
/* 103 */     xMLRelatorSearchElem.addSearchAttr("TOMODEL", "MODEL:MODELATR");
/* 104 */     xMLRelatorSearchElem.addSearchAttr("TOFEATURECODE", "FEATURE:FEATURECODE");
/* 105 */     XMLMAP.addChild((XMLElem)xMLRelatorSearchElem);
/*     */     
/* 107 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("TOFEATUREENTITYTYPE", "FEATURE"));
/* 108 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 109 */     XMLMAP.addChild(new XMLElem("BOXSWAPREQUIREDFORUPGRADES", "BOXSWAP"));
/* 110 */     XMLMAP.addChild(new XMLElem("CUSTOMERSETUP", "INSTALL"));
/* 111 */     XMLMAP.addChild(new XMLElem("FEATURETRANSACTIONCATEGORY", "FTCAT"));
/* 112 */     XMLMAP.addChild(new XMLElem("FEATURETRANSACTIONSUBCATEGORY", "FTSUBCAT"));
/* 113 */     XMLMAP.addChild(new XMLElem("INSTALLABILITY", "INSTALLABILITY"));
/* 114 */     XMLMAP.addChild(new XMLElem("INTERNALNOTES", "INTERNALNOTES"));
/* 115 */     XMLMAP.addChild(new XMLElem("PARTSSHIPPEDINDICATOR", "PARTSSHIPPED"));
/* 116 */     XMLMAP.addChild(new XMLElem("QUANTITY", "TRANSACTQTY"));
/* 117 */     XMLMAP.addChild(new XMLElem("REMOVEQUANTITY", "TRANSACTREMOVEQTY"));
/* 118 */     XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES", "RETURNEDPARTS"));
/* 119 */     XMLMAP.addChild(new XMLElem("UPGRADETYPE", "UPGRADETYPE"));
/* 120 */     XMLMAP.addChild(new XMLElem("ZEROPRICEDINDICATOR", "ZEROPRICE"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 134 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 140 */     return "ADSFCTRANSACTION";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 145 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 151 */     return "FCTRANSACTION_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 160 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSFCTRANSABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */