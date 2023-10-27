/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCtryElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLRelatorSearchElem;
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
/*  73 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("FCTRANSACTION_UPDATE");
/*     */   static {
/*  75 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  76 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  77 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  78 */     XMLMAP.addChild(new XMLElem("FEATURECONVERSIONENTITYTYPE", "ENTITYTYPE"));
/*  79 */     XMLMAP.addChild(new XMLElem("FEATURECONVERSIONENTITYID", "ENTITYID"));
/*  80 */     XMLMAP.addChild(new XMLElem("FROMMACHTYPE", "FROMMACHTYPE"));
/*  81 */     XMLMAP.addChild(new XMLElem("FROMMODEL", "FROMMODEL"));
/*  82 */     XMLMAP.addChild(new XMLElem("FROMFEATURECODE", "FROMFEATURECODE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     XMLRelatorSearchElem xMLRelatorSearchElem = new XMLRelatorSearchElem("FROMMODELENTITYID", "FROMFEATUREENTITYID", "SRDPRODSTRUCT33", "PRODSTRUCT");
/*  93 */     xMLRelatorSearchElem.addSearchAttr("FROMMACHTYPE", "MODEL:MACHTYPEATR");
/*  94 */     xMLRelatorSearchElem.addSearchAttr("FROMMODEL", "MODEL:MODELATR");
/*  95 */     xMLRelatorSearchElem.addSearchAttr("FROMFEATURECODE", "FEATURE:FEATURECODE");
/*  96 */     XMLMAP.addChild((XMLElem)xMLRelatorSearchElem);
/*     */ 
/*     */     
/*  99 */     XMLMAP.addChild(new XMLElem("TOMACHTYPE", "TOMACHTYPE"));
/* 100 */     XMLMAP.addChild(new XMLElem("TOMODEL", "TOMODEL"));
/* 101 */     XMLMAP.addChild(new XMLElem("TOFEATURECODE", "TOFEATURECODE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     xMLRelatorSearchElem = new XMLRelatorSearchElem("TOMODELENTITYID", "TOFEATUREENTITYID", "SRDPRODSTRUCT33", "PRODSTRUCT");
/* 112 */     xMLRelatorSearchElem.addSearchAttr("TOMACHTYPE", "MODEL:MACHTYPEATR");
/* 113 */     xMLRelatorSearchElem.addSearchAttr("TOMODEL", "MODEL:MODELATR");
/* 114 */     xMLRelatorSearchElem.addSearchAttr("TOFEATURECODE", "FEATURE:FEATURECODE");
/* 115 */     XMLMAP.addChild((XMLElem)xMLRelatorSearchElem);
/*     */     
/* 117 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 118 */     XMLMAP.addChild(new XMLElem("BOXSWAPREQUIREDFORUPGRADES", "BOXSWAP"));
/* 119 */     XMLMAP.addChild(new XMLElem("CUSTOMERSETUP", "INSTALL"));
/* 120 */     XMLMAP.addChild(new XMLElem("FEATURETRANSACTIONCATEGORY", "FTCAT"));
/* 121 */     XMLMAP.addChild(new XMLElem("FEATURETRANSACTIONSUBCATEGORY", "FTSUBCAT"));
/* 122 */     XMLMAP.addChild(new XMLElem("INSTALLABILITY", "INSTALLABILITY"));
/* 123 */     XMLMAP.addChild(new XMLElem("INTERNALNOTES", "INTERNALNOTES"));
/* 124 */     XMLMAP.addChild(new XMLElem("PARTSSHIPPEDINDICATOR", "PARTSSHIPPED"));
/* 125 */     XMLMAP.addChild(new XMLElem("QUANTITY", "TRANSACTQTY"));
/* 126 */     XMLMAP.addChild(new XMLElem("REMOVEQUANTITY", "TRANSACTREMOVEQTY"));
/* 127 */     XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES", "RETURNEDPARTS"));
/* 128 */     XMLMAP.addChild(new XMLElem("UPGRADETYPE", "UPGRADETYPE"));
/* 129 */     XMLMAP.addChild(new XMLElem("ZEROPRICEDINDICATOR", "ZEROPRICE"));
/* 130 */     XMLElem xMLElem = new XMLElem("COUNTRYLIST");
/* 131 */     XMLMAP.addChild(xMLElem);
/* 132 */     xMLElem.addChild((XMLElem)new XMLCtryElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 139 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 145 */     return "ADSFCTRANSACTION";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 150 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 156 */     return "FCTRANSACTION";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 165 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSFCTRANSABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */