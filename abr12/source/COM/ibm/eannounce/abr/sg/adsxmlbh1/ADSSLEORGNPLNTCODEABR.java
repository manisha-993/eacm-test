/*    */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLVMElem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ADSSLEORGNPLNTCODEABR
/*    */   extends XMLMQRoot
/*    */ {
/* 30 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SLEORGNPLNTCODE_UPDATE"); static {
/* 31 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SLEORGNPLNTCODE_UPDATE", "1"));
/*    */     
/* 33 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 34 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 35 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 36 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 37 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*    */     
/* 39 */     XMLElem xMLElem = new XMLElem("COUNTRY_FC", "COUNTRYLIST", 1);
/* 40 */     XMLMAP.addChild(xMLElem);
/* 41 */     XMLMAP.addChild(new XMLElem("MODCATG", "MODCATG", 0));
/* 42 */     XMLMAP.addChild(new XMLElem("PLNTCD", "PLNTCD", 2));
/* 43 */     XMLMAP.addChild(new XMLElem("SLEORG", "SLEORG"));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     XMLMAP.addChild(new XMLElem("SLEORGGRP", "SLEORGGRP"));
/* 50 */     XMLMAP.addChild(new XMLElem("BHRELNO", "BHRELNO"));
/* 51 */     XMLMAP.addChild(new XMLElem("LEGACYSLEORG", "LEGACYSLEORG"));
/* 52 */     XMLMAP.addChild(new XMLElem("LEGACYPLNTCD", "LEGACYPLNTCD"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 60 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVeName() {
/* 66 */     return "dummy";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStatusAttr() {
/* 71 */     return "STATUS";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 77 */     return "SLEORGNPLNTCODE_UPDATE";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 86 */     return "$Revision: 1.5 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSSLEORGNPLNTCODEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */