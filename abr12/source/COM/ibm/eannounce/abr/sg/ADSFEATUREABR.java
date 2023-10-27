/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  57 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("FEATURE_UPDATE");
/*     */   static {
/*  59 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  60 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  61 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  62 */     XMLMAP.addChild(new XMLElem("FEATUREENTITYTYPE", "ENTITYTYPE"));
/*  63 */     XMLMAP.addChild(new XMLElem("FEATUREENTITYID", "ENTITYID"));
/*  64 */     XMLMAP.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/*  65 */     XMLMAP.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*  66 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  67 */     XMLMAP.addChild(new XMLElem("PRICEDFEATURE", "PRICEDFEATURE"));
/*  68 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/*  69 */     XMLMAP.addChild(new XMLElem("TANDC"));
/*  70 */     XMLMAP.addChild(new XMLElem("FCCAT", "HWFCCAT"));
/*  71 */     XMLMAP.addChild(new XMLElem("FCSUBCAT", "HWFCSUBCAT"));
/*  72 */     XMLMAP.addChild(new XMLElem("FCGRP"));
/*  73 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG"));
/*     */     
/*  75 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  76 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  78 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  79 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  81 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  82 */     xMLNLSElem.addChild(new XMLElem("FCMKTGNAME", "MKTGNAME"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  89 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  95 */     return "ADSFEATURE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 100 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 106 */     return "FEATURE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 115 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSFEATUREABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */