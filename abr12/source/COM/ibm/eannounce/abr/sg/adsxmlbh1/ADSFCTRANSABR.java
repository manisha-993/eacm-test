/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLAVAILElemFCT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSFCTRANSABR
/*     */   extends XMLMQRoot
/*     */ {
/*  66 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("FCTRANSACTION_UPDATE"); static {
/*  67 */     XMLMAP.addChild((XMLElem)new XMLVMElem("FCTRANSACTION_UPDATE", "1"));
/*     */     
/*  69 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  70 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  71 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  72 */     XMLMAP.addChild(new XMLElem("FEATURECONVERSIONENTITYTYPE", "ENTITYTYPE"));
/*  73 */     XMLMAP.addChild(new XMLElem("FEATURECONVERSIONENTITYID", "ENTITYID"));
/*  74 */     XMLMAP.addChild(new XMLElem("FROMMACHTYPE", "FROMMACHTYPE"));
/*  75 */     XMLMAP.addChild((XMLElem)new XMLFCTRANSMODElem("FROMMODEL", "FROMMODEL", "***"));
/*  76 */     XMLMAP.addChild(new XMLElem("FROMFEATURECODE", "FROMFEATURECODE"));
/*  77 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("FROMMODELENTITYTYPE", "MODEL"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     XMLRelatorSearchElem xMLRelatorSearchElem = new XMLRelatorSearchElem("FROMMODELENTITYID", "FROMFEATUREENTITYID", "SRDPRODSTRUCT33", "PRODSTRUCT");
/*  88 */     xMLRelatorSearchElem.addSearchAttr("FROMMACHTYPE", "MODEL:MACHTYPEATR");
/*  89 */     xMLRelatorSearchElem.addSearchAttr("FROMMODEL", "MODEL:MODELATR");
/*  90 */     xMLRelatorSearchElem.addSearchAttr("FROMFEATURECODE", "FEATURE:FEATURECODE");
/*  91 */     XMLMAP.addChild((XMLElem)xMLRelatorSearchElem);
/*     */ 
/*     */     
/*  94 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("FROMFEATUREENTITYTYPE", "FEATURE"));
/*  95 */     XMLMAP.addChild(new XMLElem("TOMACHTYPE", "TOMACHTYPE"));
/*  96 */     XMLMAP.addChild((XMLElem)new XMLFCTRANSMODElem("TOMODEL", "TOMODEL", "***"));
/*  97 */     XMLMAP.addChild(new XMLElem("TOFEATURECODE", "TOFEATURECODE"));
/*  98 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("TOMODELENTITYTYPE", "MODEL"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     xMLRelatorSearchElem = new XMLRelatorSearchElem("TOMODELENTITYID", "TOFEATUREENTITYID", "SRDPRODSTRUCT33", "PRODSTRUCT");
/* 109 */     xMLRelatorSearchElem.addSearchAttr("TOMACHTYPE", "MODEL:MACHTYPEATR");
/* 110 */     xMLRelatorSearchElem.addSearchAttr("TOMODEL", "MODEL:MODELATR");
/* 111 */     xMLRelatorSearchElem.addSearchAttr("TOFEATURECODE", "FEATURE:FEATURECODE");
/* 112 */     XMLMAP.addChild((XMLElem)xMLRelatorSearchElem);
/*     */     
/* 114 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("TOFEATUREENTITYTYPE", "FEATURE"));
/* 115 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 116 */     XMLMAP.addChild(new XMLElem("ANNRFANUMBER", "ANNRFANUMBER"));
/* 117 */     XMLMAP.addChild(new XMLElem("WDRFANUMBER", "WDRFANUMBER"));
/* 118 */     XMLMAP.addChild(new XMLElem("ANNDATE", "ANNDATE"));
/* 119 */     XMLMAP.addChild(new XMLElem("WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE"));
/* 120 */     XMLMAP.addChild(new XMLElem("BOXSWAPREQUIREDFORUPGRADES", "BOXSWAP"));
/* 121 */     XMLMAP.addChild(new XMLElem("CUSTOMERSETUP", "INSTALL"));
/* 122 */     XMLMAP.addChild(new XMLElem("FEATURETRANSACTIONCATEGORY", "FTCAT"));
/* 123 */     XMLMAP.addChild(new XMLElem("FEATURETRANSACTIONSUBCATEGORY", "FTSUBCAT"));
/* 124 */     XMLMAP.addChild(new XMLElem("INSTALLABILITY", "INSTALLABILITY"));
/* 125 */     XMLMAP.addChild(new XMLElem("INTERNALNOTES", "INTERNALNOTES"));
/* 126 */     XMLMAP.addChild(new XMLElem("PARTSSHIPPEDINDICATOR", "PARTSSHIPPED"));
/* 127 */     XMLMAP.addChild(new XMLElem("QUANTITY", "TRANSACTQTY"));
/* 128 */     XMLMAP.addChild(new XMLElem("REMOVEQUANTITY", "TRANSACTREMOVEQTY"));
/* 129 */     XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES", "RETURNEDPARTS"));
/* 130 */     XMLMAP.addChild(new XMLElem("UPGRADETYPE", "UPGRADETYPE"));
/* 131 */     XMLMAP.addChild(new XMLElem("ZEROPRICEDINDICATOR", "ZEROPRICE"));
/*     */     
/* 133 */     XMLMAP.addChild((XMLElem)new XMLAVAILElemFCT());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 155 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 161 */     return "ADSFCTRANSACTION";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 166 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 172 */     return "FCTRANSACTION_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 181 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSFCTRANSABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */