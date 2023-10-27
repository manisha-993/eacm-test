/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCtryTMFElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLRelatorElem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSSWPRODSTRUCTABR
/*     */   extends XMLMQRoot
/*     */ {
/*  67 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SWPRODSTRUCT_UPDATE");
/*     */   static {
/*  69 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  70 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  71 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  72 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  73 */     XMLMAP.addChild(new XMLElem("TMFENTITYTYPE", "ENTITYTYPE"));
/*  74 */     XMLMAP.addChild(new XMLElem("TMFENTITYID", "ENTITYID"));
/*     */     
/*  76 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("PARENTID", "ENTITY2ID", "MODEL"));
/*  77 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("CHILDID", "ENTITY1ID", "SWFEATURE"));
/*     */ 
/*     */     
/*  80 */     XMLMAP.addChild(new XMLElem("ORDERCODE"));
/*  81 */     XMLMAP.addChild(new XMLElem("SYSTEMMAX"));
/*  82 */     XMLMAP.addChild(new XMLElem("SYSTEMMIN"));
/*  83 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG"));
/*  84 */     XMLMAP.addChild(new XMLElem("FULFILLMENTSYSIND"));
/*  85 */     XMLMAP.addChild(new XMLElem("OSLIST"));
/*     */     
/*  87 */     XMLElem xMLElem = new XMLElem("COUNTRYLIST");
/*  88 */     XMLMAP.addChild(xMLElem);
/*  89 */     xMLElem.addChild((XMLElem)new XMLCtryTMFElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  96 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 102 */     return "ADSSWPRODSTRUCT";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 107 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 113 */     return "SWPRODSTRUCT";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 122 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSSWPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */