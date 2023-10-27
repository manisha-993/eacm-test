/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
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
/*     */ public class ADSUNBUNDCOMPABR
/*     */   extends XMLMQRoot
/*     */ {
/*  53 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("UNBUNDCOMP_UPDATE"); static {
/*  54 */     XMLMAP.addChild((XMLElem)new XMLVMElem("UNBUNDCOMP_UPDATE", "1"));
/*     */     
/*  56 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  57 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  58 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  59 */     XMLMAP.addChild(new XMLElem("UNBUNDCOMPENTITYTYPE", "ENTITYTYPE"));
/*  60 */     XMLMAP.addChild(new XMLElem("UNBUNDCOMPENTITYID", "ENTITYID"));
/*  61 */     XMLMAP.addChild(new XMLElem("UNBUNDCOMPID", "UNBUNDCOMPID"));
/*  62 */     XMLMAP.addChild(new XMLElem("SHRTNAM", "SHRTNAM"));
/*     */     
/*  64 */     XMLMAP.addChild(new XMLElem("BHPRODHIERCD", "BHPRODHIERCD"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     XMLMAP.addChild(new XMLElem("BHACCTASGNGRP", "BHACCTASGNGRP", 2));
/*  73 */     XMLMAP.addChild(new XMLElem("AMRTZTNLNGTH", "AMRTZTNLNGTH"));
/*  74 */     XMLMAP.addChild(new XMLElem("AMRTZTNSTRT", "AMRTZTNSTRT"));
/*     */     
/*  76 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  83 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  89 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/*  94 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 100 */     return "UNBUNDCOMP_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 109 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSUNBUNDCOMPABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */