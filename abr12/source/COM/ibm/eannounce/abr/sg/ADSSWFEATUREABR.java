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
/*     */ public class ADSSWFEATUREABR
/*     */   extends XMLMQRoot
/*     */ {
/*  55 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SWFEATURE_UPDATE");
/*     */   static {
/*  57 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  58 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  59 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  60 */     XMLMAP.addChild(new XMLElem("FEATUREENTITYTYPE", "ENTITYTYPE"));
/*  61 */     XMLMAP.addChild(new XMLElem("FEATUREENTITYID", "ENTITYID"));
/*  62 */     XMLMAP.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/*  63 */     XMLMAP.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*  64 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  65 */     XMLMAP.addChild(new XMLElem("PRICEDFEATURE", "PRICEDFEATURE"));
/*  66 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/*  67 */     XMLMAP.addChild(new XMLElem("TANDC", "CHARGEOPTION"));
/*  68 */     XMLMAP.addChild(new XMLElem("FCCAT", "SWFCCAT"));
/*  69 */     XMLMAP.addChild(new XMLElem("FCSUBCAT", "SWFCSUBCAT"));
/*  70 */     XMLMAP.addChild(new XMLElem("FCGRP", "SWFCGRP"));
/*  71 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG"));
/*     */     
/*  73 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  74 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  76 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  77 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  79 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  80 */     xMLNLSElem.addChild(new XMLElem("FCMKTGNAME", "SWFEATDESC"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  87 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  93 */     return "ADSSWFEATURE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/*  98 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 104 */     return "SWFEATURE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 113 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSSWFEATUREABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */