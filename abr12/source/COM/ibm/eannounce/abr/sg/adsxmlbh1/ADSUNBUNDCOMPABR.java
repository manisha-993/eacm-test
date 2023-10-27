/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSUNBUNDCOMPABR
/*     */   extends XMLMQRoot
/*     */ {
/*  50 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("UNBUNDCOMP_UPDATE"); static {
/*  51 */     XMLMAP.addChild((XMLElem)new XMLVMElem("UNBUNDCOMP_UPDATE", "1"));
/*     */     
/*  53 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  54 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  55 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  56 */     XMLMAP.addChild(new XMLElem("UNBUNDCOMPENTITYTYPE", "ENTITYTYPE"));
/*  57 */     XMLMAP.addChild(new XMLElem("UNBUNDCOMPENTITYID", "ENTITYID"));
/*  58 */     XMLMAP.addChild(new XMLElem("UNBUNDCOMPID", "UNBUNDCOMPID"));
/*  59 */     XMLMAP.addChild(new XMLElem("SHRTNAM", "SHRTNAM"));
/*     */     
/*  61 */     XMLMAP.addChild(new XMLElem("BHPRODHIERCD", "BHPRODHIERCD"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     XMLMAP.addChild(new XMLElem("BHACCTASGNGRP", "BHACCTASGNGRP", 2));
/*  70 */     XMLMAP.addChild(new XMLElem("AMRTZTNLNGTH", "AMRTZTNLNGTH"));
/*  71 */     XMLMAP.addChild(new XMLElem("AMRTZTNSTRT", "AMRTZTNSTRT"));
/*     */     
/*  73 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  80 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  86 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/*  91 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/*  97 */     return "UNBUNDCOMP_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 106 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSUNBUNDCOMPABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */