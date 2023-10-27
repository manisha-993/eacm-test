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
/*    */ 
/*    */ 
/*    */ public class ADSDELMODELCONVERTABR
/*    */   extends XMLMQDelete
/*    */ {
/* 47 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODELCONVERT_DELETE");
/*    */   static {
/* 49 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("ACTIVITY", "Delete"));
/* 50 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFABR"));
/* 51 */     XMLMAP.addChild(new XMLElem("MODELCONVERTENTITYTYPE", "ENTITYTYPE"));
/* 52 */     XMLMAP.addChild(new XMLElem("MODELCONVERTENTITYID", "ENTITYID"));
/* 53 */     XMLMAP.addChild((XMLElem)new XMLValFromElem("DTSOFUPDATE"));
/* 54 */     XMLMAP.addChild(new XMLElem("FROMMACHTYPE", "FROMMACHTYPE"));
/* 55 */     XMLMAP.addChild(new XMLElem("FROMMODEL", "FROMMODEL"));
/* 56 */     XMLMAP.addChild(new XMLElem("TOMACHTYPE", "TOMACHTYPE"));
/* 57 */     XMLMAP.addChild(new XMLElem("TOMODEL", "TOMODEL"));
/* 58 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 65 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 72 */     return "MODELCONVERT";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 81 */     return "1.2";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSDELMODELCONVERTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */