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
/*     */ public class ADSWARRABR
/*     */   extends XMLMQRoot
/*     */ {
/*  54 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("WARR_UPDATE"); static {
/*  55 */     XMLMAP.addChild((XMLElem)new XMLVMElem("WARR_UPDATE", "1"));
/*     */     
/*  57 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  58 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  59 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  60 */     XMLMAP.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
/*  61 */     XMLMAP.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
/*  62 */     XMLMAP.addChild(new XMLElem("WARRID", "WARRID"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     XMLMAP.addChild(new XMLElem("WARRDATERULEKEY", "WARRDATERULEKEY"));
/*  70 */     XMLMAP.addChild(new XMLElem("COVRHR", "COVRHR"));
/*     */ 
/*     */     
/*  73 */     XMLMAP.addChild(new XMLElem("RESPPROF", "RESPPROF"));
/*  74 */     XMLMAP.addChild(new XMLElem("WARRPRIOD", "WARRPRIOD"));
/*  75 */     XMLMAP.addChild(new XMLElem("WARRPRIODUOM", "WARRPRIODUOM"));
/*  76 */     XMLMAP.addChild(new XMLElem("WARRTYPE", "WARRTYPE"));
/*     */     
/*  78 */     XMLMAP.addChild(new XMLElem("WARRCATG", "WARRCATG"));
/*     */     
/*  80 */     XMLMAP.addChild(new XMLElem("BHWARRCATEGORY", "BHWARRCATEGORY", 2));
/*     */ 
/*     */     
/*  83 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  84 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  86 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  87 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  89 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  90 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/*  91 */     xMLNLSElem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*  92 */     xMLNLSElem.addChild(new XMLElem("WARRDESC", "WARRDESC"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 100 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 106 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 111 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 117 */     return "WARR_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 126 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSWARRABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */