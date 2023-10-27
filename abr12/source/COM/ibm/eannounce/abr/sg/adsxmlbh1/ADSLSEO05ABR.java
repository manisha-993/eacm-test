/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSLSEO05ABR
/*     */   extends XMLMQRoot
/*     */ {
/*  86 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SEO_UPDATE"); static {
/*  87 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SEO_UPDATE", "0"));
/*     */     
/*  89 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  90 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  91 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  92 */     XMLMAP.addChild(new XMLElem("SEOENTITYTYPE", "ENTITYTYPE"));
/*  93 */     XMLMAP.addChild(new XMLElem("SEOENTITYID", "ENTITYID"));
/*     */     
/*  95 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "MODEL", "U:WWSEOLSEO:U:WWSEO:U:MODELWWSEO:U");
/*  96 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*  97 */     xMLGroupElem1.addChild(new XMLElem("PARENTENTITYTYPE", "ENTITYTYPE"));
/*  98 */     xMLGroupElem1.addChild(new XMLElem("PARENTENTITYID", "ENTITYID"));
/*  99 */     xMLGroupElem1.addChild(new XMLElem("PARENTMODEL", "MODELATR"));
/* 100 */     xMLGroupElem1.addChild(new XMLElem("PARENTMACHTPE", "MACHTYPEATR"));
/*     */     
/* 102 */     XMLMAP.addChild(new XMLElem("SEOID", "SEOID"));
/* 103 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*     */     
/* 105 */     xMLGroupElem1.addChild(new XMLElem("CATEGORY", "COFCAT"));
/* 106 */     xMLGroupElem1.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT"));
/* 107 */     xMLGroupElem1.addChild(new XMLElem("GROUP", "COFGRP"));
/* 108 */     xMLGroupElem1.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/*     */     
/* 110 */     XMLMAP.addChild(new XMLElem("PRFCNTR", "PRFTCTR", 1));
/*     */     
/* 112 */     XMLANNElem xMLANNElem = new XMLANNElem();
/* 113 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */     
/* 115 */     XMLMAP.addChild(new XMLElem("PRDHIERCD", "BHPRODHIERCD"));
/* 116 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/* 118 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "WWSEO");
/* 119 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 120 */     xMLGroupElem2.addChild(new XMLElem("SPECIALBID", "SPECBID"));
/*     */ 
/*     */     
/* 123 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 124 */     xMLGroupElem2.addChild(xMLElem1);
/*     */ 
/*     */     
/* 127 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 128 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 130 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 131 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "PRCFILENAM"));
/*     */ 
/*     */     
/* 134 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 135 */     XMLMAP.addChild(xMLElem2);
/* 136 */     xMLElem2.addChild((XMLElem)new XMLLSEOAVAILElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 144 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 150 */     return "ADRLSEO";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 155 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 161 */     return "LSEO";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 170 */     return "$Revision: 1.4 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSLSEO05ABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */