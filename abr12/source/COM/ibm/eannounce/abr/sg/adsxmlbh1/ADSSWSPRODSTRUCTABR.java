/*    */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLRELATElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLSWSTMFAVAILElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLStatusElem;
/*    */ import COM.ibm.eannounce.abr.util.XMLVMElem;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ADSSWSPRODSTRUCTABR
/*    */   extends XMLMQRoot
/*    */ {
/* 17 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SWSPRODSTRUCT_UPDATE"); static {
/* 18 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SWSPRODSTRUCT_UPDATE", "1"));
/*    */     
/* 20 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 21 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 22 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*    */ 
/*    */     
/* 25 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 26 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 27 */     XMLMAP.addChild((XMLElem)new XMLRELATElem("OFFERING_ID", "SMACHTYPEATR", "MODELATR"));
/* 28 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "SVCMOD", "D:SVCMOD");
/* 29 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 30 */     xMLGroupElem1.addChild(new XMLElem("OFFERING_MARKETING_NM", "INVNAME"));
/*    */     
/* 32 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SWSFEATURE", "U:SWSFEATURE");
/* 33 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 34 */     xMLGroupElem2.addChild(new XMLElem("FEATURE_CD", "FEATURECODE"));
/*    */ 
/*    */     
/* 37 */     XMLMAP.addChild(new XMLElem("QTY_MIN_NUM", "QTYMINNUM"));
/* 38 */     XMLMAP.addChild(new XMLElem("QTY_MAX_NUM", "QTYMAXNUM"));
/* 39 */     XMLMAP.addChild(new XMLElem("FEATURE_S_NM", "TMFMKTGSHRTDESC"));
/* 40 */     XMLMAP.addChild(new XMLElem("FEATURE_L_NM", "TMFMKTGLONGDESC"));
/* 41 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 42 */     XMLMAP.addChild(new XMLElem("MAP_TO_PRICE_TYPE_CD", "MAPTOPRICETYPECD"));
/* 43 */     XMLMAP.addChild(new XMLElem("FEATURE_CATEG_CD", "FEATURECATEGCD"));
/* 44 */     XMLMAP.addChild(new XMLElem("FEATURE_MODIF_IND", "FEATUREMODIFIND"));
/* 45 */     XMLMAP.addChild(new XMLElem("SERVICE_LEVEL_MAPPING_CD", "SERVICELEVELMAPPINGCD"));
/*    */     
/* 47 */     XMLElem xMLElem = new XMLElem("AVAILABILITYLIST");
/* 48 */     XMLMAP.addChild(xMLElem);
/* 49 */     xMLElem.addChild((XMLElem)new XMLSWSTMFAVAILElem());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElem getXMLMap() {
/* 56 */     return XMLMAP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVeName() {
/* 62 */     return "ADSSWSPRODSTRUCT";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStatusAttr() {
/* 69 */     return "STATUS";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMQCID() {
/* 76 */     return "SWSPRODSTRUCT_UPDATE";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 85 */     return "$Revision: 1.0 $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSSWSPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */