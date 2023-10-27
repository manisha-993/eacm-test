/*    */ package COM.ibm.eannounce.abr.sg.wave2;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
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
/*    */ 
/*    */ public class ADSSLEORGNPLNTCODEABR
/*    */   extends XMLMQRoot
/*    */ {
/* 30 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SLEORGNPLNTCODE_UPDATE");
/*    */   static {
/* 32 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 33 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 34 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 35 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 36 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*    */     
/* 38 */     XMLElem xMLElem = new XMLElem("COUNTRY_FC", "COUNTRYLIST", 1);
/* 39 */     XMLMAP.addChild(xMLElem);
/* 40 */     XMLMAP.addChild(new XMLElem("MODCATG", "MODCATG", 0));
/* 41 */     XMLMAP.addChild(new XMLElem("PLNTCD", "PLNTCD", 2));
/* 42 */     XMLMAP.addChild(new XMLElem("SLEORG", "SLEORG"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 50 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVeName() {
/* 56 */     return "dummy";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStatusAttr() {
/* 61 */     return "STATUS";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 67 */     return "SLEORGNPLNTCODE";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 76 */     return "$Revision: 1.2 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\wave2\ADSSLEORGNPLNTCODEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */