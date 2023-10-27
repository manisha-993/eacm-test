/*    */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLImageElem;
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
/*    */ public class ADSIMGABR
/*    */   extends XMLMQRoot
/*    */ {
/* 31 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("IMAGE_UPDATE"); static {
/* 32 */     XMLMAP.addChild((XMLElem)new XMLVMElem("IMAGE_UPDATE", "1"));
/*    */     
/* 34 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 35 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 36 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*    */     
/* 38 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 39 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 40 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*    */     
/* 42 */     XMLMAP.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));
/*    */     
/* 44 */     XMLMAP.addChild((XMLElem)new XMLImageElem());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 52 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVeName() {
/* 58 */     return "dummy";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStatusAttr() {
/* 63 */     return "STATUS";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 69 */     return "IMAGE_UPDATE";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 78 */     return "$Revision: 1.1 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSIMGABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */