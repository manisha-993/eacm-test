/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSREFOFERFEATABR
/*     */   extends XMLMQRoot
/*     */ {
/*  39 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("REFOFERFEAT_UPDATE"); static {
/*  40 */     XMLMAP.addChild((XMLElem)new XMLVMElem("REFOFERFEAT_UPDATE", "1"));
/*     */     
/*  42 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  43 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  44 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  45 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  46 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*     */     
/*  48 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  49 */     XMLMAP.addChild(new XMLElem("FEATID", "FEATID"));
/*  50 */     XMLMAP.addChild(new XMLElem("MKTGDIV", "MKTGDIV"));
/*     */     
/*  52 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR"));
/*     */ 
/*     */     
/*  55 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  56 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  58 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/*  59 */     xMLElem.addChild((XMLElem)xMLNLSElem1);
/*     */     
/*  61 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */ 
/*     */     
/*  65 */     xMLNLSElem1.addChild(new XMLElem("MFRFEATID", "MFRFEATID"));
/*  66 */     xMLNLSElem1.addChild(new XMLElem("MFRFEATDESC", "MFRFEATDESC"));
/*  67 */     xMLNLSElem1.addChild(new XMLElem("MFRFEATLNGDESC", "MFRFEATLNGDESC"));
/*     */ 
/*     */ 
/*     */     
/*  71 */     XMLGroupElem xMLGroupElem = new XMLGroupElem("RELATEDREFOFERLIST", "REFOFER", "U:REFOFERREFOFERFEAT:U", true);
/*  72 */     XMLMAP.addChild((XMLElem)xMLGroupElem);
/*     */     
/*  74 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("RELATEDREFOFERELEMENT");
/*  75 */     xMLGroupElem.addChild((XMLElem)xMLNLSElem2);
/*     */     
/*  77 */     xMLNLSElem2.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  78 */     xMLNLSElem2.addChild(new XMLElem("PRODUCTID", "PRODUCTID"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  88 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  94 */     return "ADSREFOFERFEAT";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/*  99 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 105 */     return "REFOFERFEAT_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 114 */     return "$Revision: 1.5 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSREFOFERFEATABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */