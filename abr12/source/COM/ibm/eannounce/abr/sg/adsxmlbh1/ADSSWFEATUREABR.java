/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSSWFEATUREABR
/*     */   extends XMLMQRoot
/*     */ {
/*  53 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("FEATURE_UPDATE"); static {
/*  54 */     XMLMAP.addChild((XMLElem)new XMLVMElem("FEATURE_UPDATE", "1"));
/*     */     
/*  56 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  57 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  58 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  59 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*  60 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  61 */     XMLMAP.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/*  62 */     XMLMAP.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*  63 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  64 */     XMLMAP.addChild(new XMLElem("PRICEDFEATURE", "PRICEDFEATURE"));
/*  65 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/*  66 */     XMLMAP.addChild(new XMLElem("CHARGEOPTION", "CHARGEOPTION"));
/*  67 */     XMLMAP.addChild(new XMLElem("FCCAT", "SWFCCAT"));
/*  68 */     XMLMAP.addChild(new XMLElem("FCSUBCAT", "SWFCSUBCAT"));
/*  69 */     XMLMAP.addChild(new XMLElem("FCGRP", "SWFCGRP"));
/*  70 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG"));
/*     */     
/*  72 */     XMLMAP.addChild(new XMLElem("LICNSOPTTYPE", "LICNSOPTTYPE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     XMLMAP.addChild(new XMLElem("MAINTPRICE"));
/*  79 */     XMLMAP.addChild(new XMLElem("LICENSETYPE", "LICENSETYPE"));
/*     */     
/*  81 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  82 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  84 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  85 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  87 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  88 */     xMLNLSElem.addChild(new XMLElem("MKTGDESC", "SWFEATDESC"));
/*  89 */     xMLNLSElem.addChild(new XMLElem("MKTGNAME"));
/*  90 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/*  91 */     xMLNLSElem.addChild(new XMLElem("BHINVNAME", "BHINVNAME"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  98 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 104 */     return "ADSSWFEATURE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 109 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 115 */     return "FEATURE_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 124 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSSWFEATUREABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */