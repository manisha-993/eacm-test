/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSREFOFERABR
/*     */   extends XMLMQRoot
/*     */ {
/*  36 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("REFOFER_UPDATE"); static {
/*  37 */     XMLMAP.addChild((XMLElem)new XMLVMElem("REFOFER_UPDATE", "1"));
/*     */     
/*  39 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  40 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  41 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  42 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  43 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*     */     
/*  45 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  46 */     XMLMAP.addChild(new XMLElem("PRODUCTID", "PRODUCTID"));
/*  47 */     XMLMAP.addChild(new XMLElem("MFRPRODTYPE", "MFRPRODTYPE"));
/*  48 */     XMLMAP.addChild(new XMLElem("MFRPRODID", "MFRPRODID"));
/*  49 */     XMLMAP.addChild(new XMLElem("MKTGDIV", "MKTGDIV"));
/*     */     
/*  51 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR"));
/*     */     
/*  53 */     XMLMAP.addChild(new XMLElem("CATGSHRTDESC", "CATGSHRTDESC"));
/*  54 */     XMLMAP.addChild(new XMLElem("STRTOFSVC", "STRTOFSVC"));
/*  55 */     XMLMAP.addChild(new XMLElem("ENDOFSVC", "ENDOFSVC"));
/*  56 */     XMLMAP.addChild(new XMLElem("VENDNAM", "VENDNAM"));
/*  57 */     XMLMAP.addChild(new XMLElem("MACHRATECATG", "MACHRATECATG"));
/*     */     
/*  59 */     XMLMAP.addChild(new XMLElem("CECSPRODKEY", "CECSPRODKEY"));
/*  60 */     XMLMAP.addChild(new XMLElem("MAINTANNBILLELIGINDC", "MAINTANNBILLELIGINDC"));
/*  61 */     XMLMAP.addChild(new XMLElem("SYSIDUNIT", "SYSIDUNIT"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     XMLMAP.addChild(new XMLElem("PRODSUPRTCD", "PRODSUPRTCD"));
/*     */ 
/*     */     
/*  70 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/*  71 */     XMLMAP.addChild(xMLElem1);
/*     */     
/*  73 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  74 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  76 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  77 */     xMLNLSElem.addChild(new XMLElem("MFRPRODDESC", "MFRPRODDESC"));
/*     */ 
/*     */     
/*  80 */     XMLElem xMLElem2 = new XMLElem("COUNTRYLIST");
/*  81 */     XMLMAP.addChild(xMLElem2);
/*     */     
/*  83 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("COUNTRYELEMENT");
/*  84 */     xMLElem2.addChild((XMLElem)xMLChgSetElem);
/*     */     
/*  86 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "ACTION", 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  93 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  99 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 104 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 110 */     return "REFOFER_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 119 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSREFOFERABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */