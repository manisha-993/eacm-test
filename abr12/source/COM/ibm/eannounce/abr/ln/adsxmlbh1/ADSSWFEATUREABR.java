/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
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
/*     */ public class ADSSWFEATUREABR
/*     */   extends XMLMQRoot
/*     */ {
/*  56 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("FEATURE_UPDATE"); static {
/*  57 */     XMLMAP.addChild((XMLElem)new XMLVMElem("FEATURE_UPDATE", "1"));
/*     */     
/*  59 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  60 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  61 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  62 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*  63 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  64 */     XMLMAP.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/*  65 */     XMLMAP.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*  66 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  67 */     XMLMAP.addChild(new XMLElem("PRICEDFEATURE", "PRICEDFEATURE"));
/*  68 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/*  69 */     XMLMAP.addChild(new XMLElem("CHARGEOPTION", "CHARGEOPTION"));
/*  70 */     XMLMAP.addChild(new XMLElem("FCCAT", "SWFCCAT"));
/*  71 */     XMLMAP.addChild(new XMLElem("FCSUBCAT", "SWFCSUBCAT"));
/*  72 */     XMLMAP.addChild(new XMLElem("FCGRP", "SWFCGRP"));
/*  73 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG"));
/*     */     
/*  75 */     XMLMAP.addChild(new XMLElem("LICNSOPTTYPE", "LICNSOPTTYPE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     XMLMAP.addChild(new XMLElem("MAINTPRICE"));
/*  82 */     XMLMAP.addChild(new XMLElem("LICENSETYPE", "LICENSETYPE"));
/*     */     
/*  84 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  85 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  87 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  88 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  90 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  91 */     xMLNLSElem.addChild(new XMLElem("MKTGDESC", "SWFEATDESC"));
/*  92 */     xMLNLSElem.addChild(new XMLElem("MKTGNAME"));
/*  93 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/*  94 */     xMLNLSElem.addChild(new XMLElem("BHINVNAME", "BHINVNAME"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 101 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 107 */     return "ADSSWFEATURE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 112 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 118 */     return "FEATURE_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 127 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSSWFEATUREABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */