/*    */ package COM.ibm.eannounce.abr.sg;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.util.XMLElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLFixedElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLValFromElem;
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
/*    */ public class ADSDELSWFEATUREABR
/*    */   extends XMLMQDelete
/*    */ {
/* 43 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SWFEATURE_DELETE");
/*    */   static {
/* 45 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("ACTIVITY", "Delete"));
/* 46 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFABR"));
/* 47 */     XMLMAP.addChild(new XMLElem("SWFEATUREENTITYTYPE", "ENTITYTYPE"));
/* 48 */     XMLMAP.addChild(new XMLElem("SWFEATUREENTITYID", "ENTITYID"));
/* 49 */     XMLMAP.addChild((XMLElem)new XMLValFromElem("DTSOFUPDATE"));
/* 50 */     XMLMAP.addChild(new XMLElem("SWFEATURECODE", "FEATURECODE"));
/* 51 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 58 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 65 */     return "SWFEATURE";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 74 */     return "1.2";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSDELSWFEATUREABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */