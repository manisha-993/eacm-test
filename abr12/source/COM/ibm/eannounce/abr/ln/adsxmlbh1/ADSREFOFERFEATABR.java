/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSREFOFERFEATABR
/*     */   extends XMLMQRoot
/*     */ {
/*  42 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("REFOFERFEAT_UPDATE"); static {
/*  43 */     XMLMAP.addChild((XMLElem)new XMLVMElem("REFOFERFEAT_UPDATE", "1"));
/*     */     
/*  45 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  46 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  47 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  48 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  49 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*     */     
/*  51 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  52 */     XMLMAP.addChild(new XMLElem("FEATID", "FEATID"));
/*  53 */     XMLMAP.addChild(new XMLElem("MKTGDIV", "MKTGDIV"));
/*     */     
/*  55 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR"));
/*     */ 
/*     */     
/*  58 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  59 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  61 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/*  62 */     xMLElem.addChild((XMLElem)xMLNLSElem1);
/*     */     
/*  64 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */ 
/*     */     
/*  68 */     xMLNLSElem1.addChild(new XMLElem("MFRFEATID", "MFRFEATID"));
/*  69 */     xMLNLSElem1.addChild(new XMLElem("MFRFEATDESC", "MFRFEATDESC"));
/*  70 */     xMLNLSElem1.addChild(new XMLElem("MFRFEATLNGDESC", "MFRFEATLNGDESC"));
/*     */ 
/*     */ 
/*     */     
/*  74 */     XMLGroupElem xMLGroupElem = new XMLGroupElem("RELATEDREFOFERLIST", "REFOFER", "U:REFOFERREFOFERFEAT:U", true);
/*  75 */     XMLMAP.addChild((XMLElem)xMLGroupElem);
/*     */     
/*  77 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("RELATEDREFOFERELEMENT");
/*  78 */     xMLGroupElem.addChild((XMLElem)xMLNLSElem2);
/*     */     
/*  80 */     xMLNLSElem2.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  81 */     xMLNLSElem2.addChild(new XMLElem("PRODUCTID", "PRODUCTID"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
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
/*  97 */     return "ADSREFOFERFEAT";
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
/* 108 */     return "REFOFERFEAT_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 117 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSREFOFERFEATABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */