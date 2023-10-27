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
/*    */ public class ADSDELMODELABR
/*    */   extends XMLMQDelete
/*    */ {
/* 46 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODEL_DELETE");
/*    */   static {
/* 48 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("ACTIVITY", "Delete"));
/* 49 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFABR"));
/* 50 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 51 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 52 */     XMLMAP.addChild((XMLElem)new XMLValFromElem("DTSOFUPDATE"));
/* 53 */     XMLMAP.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
/* 54 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/* 55 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 62 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 69 */     return "MODEL";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 78 */     return "1.3";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSDELMODELABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */