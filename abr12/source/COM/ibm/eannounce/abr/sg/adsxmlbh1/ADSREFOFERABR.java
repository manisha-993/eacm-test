/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLVMElem;
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
/*     */ public class ADSREFOFERABR
/*     */   extends XMLMQRoot
/*     */ {
/*  33 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("REFOFER_UPDATE"); static {
/*  34 */     XMLMAP.addChild((XMLElem)new XMLVMElem("REFOFER_UPDATE", "1"));
/*     */     
/*  36 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  37 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  38 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  39 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  40 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*     */     
/*  42 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  43 */     XMLMAP.addChild(new XMLElem("PRODUCTID", "PRODUCTID"));
/*  44 */     XMLMAP.addChild(new XMLElem("MFRPRODTYPE", "MFRPRODTYPE"));
/*  45 */     XMLMAP.addChild(new XMLElem("MFRPRODID", "MFRPRODID"));
/*  46 */     XMLMAP.addChild(new XMLElem("MKTGDIV", "MKTGDIV"));
/*     */     
/*  48 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR"));
/*     */     
/*  50 */     XMLMAP.addChild(new XMLElem("CATGSHRTDESC", "CATGSHRTDESC"));
/*  51 */     XMLMAP.addChild(new XMLElem("STRTOFSVC", "STRTOFSVC"));
/*  52 */     XMLMAP.addChild(new XMLElem("ENDOFSVC", "ENDOFSVC"));
/*  53 */     XMLMAP.addChild(new XMLElem("VENDNAM", "VENDNAM"));
/*  54 */     XMLMAP.addChild(new XMLElem("MACHRATECATG", "MACHRATECATG"));
/*     */     
/*  56 */     XMLMAP.addChild(new XMLElem("CECSPRODKEY", "CECSPRODKEY"));
/*  57 */     XMLMAP.addChild(new XMLElem("MAINTANNBILLELIGINDC", "MAINTANNBILLELIGINDC"));
/*  58 */     XMLMAP.addChild(new XMLElem("SYSIDUNIT", "SYSIDUNIT"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     XMLMAP.addChild(new XMLElem("PRODSUPRTCD", "PRODSUPRTCD"));
/*     */     
/*  66 */     XMLMAP.addChild(new XMLElem("DOMAIN", "DOMAIN"));
/*     */     
/*  68 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/*  69 */     XMLMAP.addChild(xMLElem1);
/*     */     
/*  71 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  72 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  74 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  75 */     xMLNLSElem.addChild(new XMLElem("MFRPRODDESC", "MFRPRODDESC"));
/*     */ 
/*     */     
/*  78 */     XMLElem xMLElem2 = new XMLElem("COUNTRYLIST");
/*  79 */     XMLMAP.addChild(xMLElem2);
/*     */     
/*  81 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("COUNTRYELEMENT");
/*  82 */     xMLElem2.addChild((XMLElem)xMLChgSetElem);
/*     */     
/*  84 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "ACTION", 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  91 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  97 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 102 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 108 */     return "REFOFER_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 117 */     return "$Revision: 1.3 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSREFOFERABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */