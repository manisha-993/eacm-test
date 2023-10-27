/*    */ package COM.ibm.eannounce.abr.sg;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLCtryTMFElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLRelatorElem;
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
/*    */ public class ADSSVCPRODSTRUCTABR
/*    */   extends XMLMQRoot
/*    */ {
/* 42 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SVCPRODSTRUCT_UPDATE");
/*    */   static {
/* 44 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 45 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 46 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 47 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 48 */     XMLMAP.addChild(new XMLElem("TMFENTITYTYPE", "ENTITYTYPE"));
/* 49 */     XMLMAP.addChild(new XMLElem("TMFENTITYID", "ENTITYID"));
/*    */     
/* 51 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("PARENTID", "ENTITY2ID", "MODEL"));
/* 52 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("CHILDID", "ENTITY1ID", "SVCFEATURE"));
/*    */ 
/*    */ 
/*    */     
/* 56 */     XMLElem xMLElem = new XMLElem("COUNTRYLIST");
/* 57 */     XMLMAP.addChild(xMLElem);
/* 58 */     xMLElem.addChild((XMLElem)new XMLCtryTMFElem());
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
/*    */   public String getVeName() {
/* 71 */     return "ADSSVCPRODSTRUCT";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStatusAttr() {
/* 76 */     return "STATUS";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 82 */     return "SVCPRODSTRUCT";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 91 */     return "1.5";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSSVCPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */