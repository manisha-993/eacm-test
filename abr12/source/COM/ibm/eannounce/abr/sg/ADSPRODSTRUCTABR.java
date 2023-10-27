/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCtryTMFElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLRelatorElem;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
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
/*     */ public class ADSPRODSTRUCTABR
/*     */   extends XMLMQRoot
/*     */ {
/*  70 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("PRODSTRUCT_UPDATE");
/*     */   static {
/*  72 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  73 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  74 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  75 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  76 */     XMLMAP.addChild(new XMLElem("TMFENTITYTYPE", "ENTITYTYPE"));
/*  77 */     XMLMAP.addChild(new XMLElem("TMFENTITYID", "ENTITYID"));
/*     */     
/*  79 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("PARENTID", "ENTITY2ID", "MODEL"));
/*  80 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("CHILDID", "ENTITY1ID", "FEATURE"));
/*     */ 
/*     */ 
/*     */     
/*  84 */     XMLMAP.addChild(new XMLElem("ORDERCODE", "ORDERCODE", 1));
/*  85 */     XMLMAP.addChild(new XMLElem("SYSTEMMAX", "SYSTEMMAX"));
/*  86 */     XMLMAP.addChild(new XMLElem("SYSTEMMIN", "SYSTEMMIN"));
/*  87 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG"));
/*  88 */     XMLMAP.addChild(new XMLElem("FULFILLMENTSYSIND", "FLFILSYSINDC"));
/*     */     
/*  90 */     XMLElem xMLElem = new XMLElem("OSLIST");
/*  91 */     XMLMAP.addChild(xMLElem);
/*  92 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("OSELEMENT");
/*  93 */     xMLElem.addChild((XMLElem)xMLChgSetElem);
/*  94 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("OS", "OSLEVEL", "OSACTION", 1));
/*     */     
/*  96 */     xMLElem = new XMLElem("COUNTRYLIST");
/*  97 */     XMLMAP.addChild(xMLElem);
/*  98 */     xMLElem.addChild((XMLElem)new XMLCtryTMFElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean createXML(EntityItem paramEntityItem) {
/* 107 */     return !"0040".equals(PokUtils.getAttributeFlagValue(paramEntityItem, "CONFIGURATORFLAG"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 114 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 120 */     return "ADSPRODSTRUCT";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 125 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 131 */     return "PRODSTRUCT";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 140 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */