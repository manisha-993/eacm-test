/*    */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*    */ public class ADSIMGABR
/*    */   extends XMLMQRoot
/*    */ {
/* 28 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("IMAGE_UPDATE"); static {
/* 29 */     XMLMAP.addChild((XMLElem)new XMLVMElem("IMAGE_UPDATE", "1"));
/*    */     
/* 31 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 32 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 33 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*    */     
/* 35 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 36 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 37 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*    */     
/* 39 */     XMLMAP.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));
/*    */     
/* 41 */     XMLMAP.addChild((XMLElem)new XMLImageElem());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 49 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVeName() {
/* 55 */     return "dummy";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStatusAttr() {
/* 60 */     return "STATUS";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 66 */     return "IMAGE_UPDATE";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 75 */     return "$Revision: 1.5 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSIMGABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */