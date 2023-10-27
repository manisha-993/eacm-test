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
/*    */ 
/*    */ public class ADSDELFCTRANSABR
/*    */   extends XMLMQDelete
/*    */ {
/* 48 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("FCTRANSACTION_DELETE");
/*    */   static {
/* 50 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("ACTIVITY", "Delete"));
/* 51 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFABR"));
/* 52 */     XMLMAP.addChild(new XMLElem("FCTRANSACTIONENTITYTYPE", "ENTITYTYPE"));
/* 53 */     XMLMAP.addChild(new XMLElem("FCTRANSACTIONENTITYID", "ENTITYID"));
/* 54 */     XMLMAP.addChild((XMLElem)new XMLValFromElem("DTSOFUPDATE"));
/* 55 */     XMLMAP.addChild(new XMLElem("FROMMACHTYPE", "FROMMACHTYPE"));
/* 56 */     XMLMAP.addChild(new XMLElem("FROMMODEL", "FROMMODEL"));
/* 57 */     XMLMAP.addChild(new XMLElem("FROMFEATURECODE", "FROMFEATURECODE"));
/* 58 */     XMLMAP.addChild(new XMLElem("TOMACHTYPE", "TOMACHTYPE"));
/* 59 */     XMLMAP.addChild(new XMLElem("TOMODEL", "TOMODEL"));
/* 60 */     XMLMAP.addChild(new XMLElem("TOFEATURECODE", "TOFEATURECODE"));
/* 61 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 68 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 75 */     return "FCTRANSACTION";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 84 */     return "1.2";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSDELFCTRANSABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */