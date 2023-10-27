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
/*    */ 
/*    */ 
/*    */ public class ADSDELSVCFEATUREABR
/*    */   extends XMLMQDelete
/*    */ {
/* 45 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SVCFEATURE_DELETE");
/*    */   static {
/* 47 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("ACTIVITY", "Delete"));
/* 48 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFABR"));
/* 49 */     XMLMAP.addChild(new XMLElem("FEATUREENTITYTYPE", "ENTITYTYPE"));
/* 50 */     XMLMAP.addChild(new XMLElem("FEATUREENTITYID", "ENTITYID"));
/* 51 */     XMLMAP.addChild((XMLElem)new XMLValFromElem("DTSOFUPDATE"));
/* 52 */     XMLMAP.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/* 53 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 61 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 68 */     return "SVCFEATURE";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 77 */     return "1.3";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSDELSVCFEATUREABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */