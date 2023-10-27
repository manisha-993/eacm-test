/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
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
/*     */ public class ADSFEATUREABR
/*     */   extends XMLMQRoot
/*     */ {
/*  59 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("FEATURE_UPDATE"); static {
/*  60 */     XMLMAP.addChild((XMLElem)new XMLVMElem("FEATURE_UPDATE", "1"));
/*     */     
/*  62 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  63 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  64 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  65 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*  66 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  67 */     XMLMAP.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/*  68 */     XMLMAP.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*  69 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  70 */     XMLMAP.addChild(new XMLElem("PRICEDFEATURE", "PRICEDFEATURE"));
/*  71 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/*  72 */     XMLMAP.addChild(new XMLElem("CHARGEOPTION"));
/*  73 */     XMLMAP.addChild(new XMLElem("FCCAT", "HWFCCAT"));
/*  74 */     XMLMAP.addChild(new XMLElem("FCSUBCAT", "HWFCSUBCAT"));
/*  75 */     XMLMAP.addChild(new XMLElem("FCGRP"));
/*  76 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG", 2));
/*     */     
/*  78 */     XMLMAP.addChild(new XMLElem("LICNSOPTTYPE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     XMLMAP.addChild(new XMLElem("MAINTPRICE", "MAINTPRICE"));
/*     */     
/*  86 */     XMLMAP.addChild(new XMLElem("HWORINFOFEATURE", "HWORINFOFEATURE"));
/*  87 */     XMLMAP.addChild(new XMLElem("LICENSETYPE"));
/*  88 */     XMLMAP.addChild(new XMLElem("FIRSTANNDATE", "FIRSTANNDATE"));
/*  89 */     XMLMAP.addChild(new XMLElem("WTHDRWEFFCTVDATE", "WITHDRAWDATEEFF_T"));
/*  90 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  91 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  93 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/*  94 */     xMLElem.addChild((XMLElem)xMLNLSElem1);
/*     */     
/*  96 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/*  97 */     xMLNLSElem1.addChild(new XMLElem("MKTGDESC"));
/*  98 */     xMLNLSElem1.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*  99 */     xMLNLSElem1.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 100 */     xMLNLSElem1.addChild(new XMLElem("BHINVNAME", "BHINVNAME"));
/*     */     
/* 102 */     xMLElem = new XMLElem("COUNTRYLIST");
/* 103 */     XMLMAP.addChild(xMLElem);
/*     */     
/* 105 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("COUNTRYELEMENT");
/* 106 */     xMLElem.addChild((XMLElem)xMLChgSetElem);
/*     */ 
/*     */     
/* 109 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("COUNTRYCODE", "COUNTRYLIST", "ACTIVITY", 1));
/*     */     
/* 111 */     XMLGroupElem xMLGroupElem = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 112 */     XMLMAP.addChild((XMLElem)xMLGroupElem);
/*     */     
/* 114 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("CATATTRIBUTEELEMENT");
/* 115 */     xMLGroupElem.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 117 */     xMLNLSElem2.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/* 118 */     xMLNLSElem2.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 119 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 120 */     xMLNLSElem2.addChild(new XMLElem("CATATTRIBUTEVALUE", "DAATTRIBUTEVALUE"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 128 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 134 */     return "ADSFEATURE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 139 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 145 */     return "FEATURE_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 154 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSFEATUREABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */