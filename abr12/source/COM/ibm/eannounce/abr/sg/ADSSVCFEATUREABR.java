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
/*     */ public class ADSSVCFEATUREABR
/*     */   extends XMLMQRoot
/*     */ {
/*  45 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SVCFEATURE_UPDATE");
/*     */   static {
/*  47 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  48 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  49 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  50 */     XMLMAP.addChild(new XMLElem("FEATUREENTITYTYPE", "ENTITYTYPE"));
/*  51 */     XMLMAP.addChild(new XMLElem("FEATUREENTITYID", "ENTITYID"));
/*  52 */     XMLMAP.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/*  53 */     XMLMAP.addChild(new XMLElem("FCTYPE", "SVCFCTYPE"));
/*  54 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  55 */     XMLMAP.addChild(new XMLElem("PRICEDFEATURE", "PRICEDFEATURE"));
/*  56 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/*  57 */     XMLMAP.addChild(new XMLElem("TANDC"));
/*  58 */     XMLMAP.addChild(new XMLElem("FCCAT", "SVCFCCAT"));
/*  59 */     XMLMAP.addChild(new XMLElem("FCSUBCAT", "SVCFCSUBCAT"));
/*  60 */     XMLMAP.addChild(new XMLElem("FCGRP"));
/*  61 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG"));
/*     */     
/*  63 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  64 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  66 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  67 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  69 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  70 */     xMLNLSElem.addChild(new XMLElem("FCMKTGNAME", "FCMKTGDESC"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  77 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  83 */     return "ADSSVCFEATURE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/*  88 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/*  94 */     return "SVCFEATURE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 103 */     return "1.6";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSSVCFEATUREABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */