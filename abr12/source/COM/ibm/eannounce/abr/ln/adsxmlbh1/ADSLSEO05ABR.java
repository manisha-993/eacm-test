/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLANNElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLLSEOAVAILElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
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
/*     */ public class ADSLSEO05ABR
/*     */   extends XMLMQRoot
/*     */ {
/*  89 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SEO_UPDATE"); static {
/*  90 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SEO_UPDATE", "0"));
/*     */     
/*  92 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  93 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  94 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  95 */     XMLMAP.addChild(new XMLElem("SEOENTITYTYPE", "ENTITYTYPE"));
/*  96 */     XMLMAP.addChild(new XMLElem("SEOENTITYID", "ENTITYID"));
/*     */     
/*  98 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "MODEL", "U:WWSEOLSEO:U:WWSEO:U:MODELWWSEO:U");
/*  99 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 100 */     xMLGroupElem1.addChild(new XMLElem("PARENTENTITYTYPE", "ENTITYTYPE"));
/* 101 */     xMLGroupElem1.addChild(new XMLElem("PARENTENTITYID", "ENTITYID"));
/* 102 */     xMLGroupElem1.addChild(new XMLElem("PARENTMODEL", "MODELATR"));
/* 103 */     xMLGroupElem1.addChild(new XMLElem("PARENTMACHTPE", "MACHTYPEATR"));
/*     */     
/* 105 */     XMLMAP.addChild(new XMLElem("SEOID", "SEOID"));
/* 106 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*     */     
/* 108 */     xMLGroupElem1.addChild(new XMLElem("CATEGORY", "COFCAT"));
/* 109 */     xMLGroupElem1.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT"));
/* 110 */     xMLGroupElem1.addChild(new XMLElem("GROUP", "COFGRP"));
/* 111 */     xMLGroupElem1.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/*     */     
/* 113 */     XMLMAP.addChild(new XMLElem("PRFCNTR", "PRFTCTR", 1));
/*     */     
/* 115 */     XMLANNElem xMLANNElem = new XMLANNElem();
/* 116 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */     
/* 118 */     XMLMAP.addChild(new XMLElem("PRDHIERCD", "BHPRODHIERCD"));
/* 119 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/* 121 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "WWSEO");
/* 122 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 123 */     xMLGroupElem2.addChild(new XMLElem("SPECIALBID", "SPECBID"));
/*     */ 
/*     */     
/* 126 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 127 */     xMLGroupElem2.addChild(xMLElem1);
/*     */ 
/*     */     
/* 130 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 131 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 133 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 134 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "PRCFILENAM"));
/*     */ 
/*     */     
/* 137 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 138 */     XMLMAP.addChild(xMLElem2);
/* 139 */     xMLElem2.addChild((XMLElem)new XMLLSEOAVAILElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 147 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 153 */     return "ADRLSEO";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 158 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 164 */     return "LSEO";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 173 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSLSEO05ABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */