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
/*  57 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("WARR_UPDATE"); static {
/*  58 */     XMLMAP.addChild((XMLElem)new XMLVMElem("WARR_UPDATE", "1"));
/*     */     
/*  60 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  61 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  62 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  63 */     XMLMAP.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
/*  64 */     XMLMAP.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
/*  65 */     XMLMAP.addChild(new XMLElem("WARRID", "WARRID"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     XMLMAP.addChild(new XMLElem("WARRDATERULEKEY", "WARRDATERULEKEY"));
/*  73 */     XMLMAP.addChild(new XMLElem("COVRHR", "COVRHR"));
/*     */ 
/*     */     
/*  76 */     XMLMAP.addChild(new XMLElem("RESPPROF", "RESPPROF"));
/*  77 */     XMLMAP.addChild(new XMLElem("WARRPRIOD", "WARRPRIOD"));
/*  78 */     XMLMAP.addChild(new XMLElem("WARRPRIODUOM", "WARRPRIODUOM"));
/*  79 */     XMLMAP.addChild(new XMLElem("WARRTYPE", "WARRTYPE"));
/*     */     
/*  81 */     XMLMAP.addChild(new XMLElem("WARRCATG", "WARRCATG"));
/*     */     
/*  83 */     XMLMAP.addChild(new XMLElem("BHWARRCATEGORY", "BHWARRCATEGORY", 2));
/*  84 */     XMLMAP.addChild(new XMLElem("OEMESAPRTSLBR", "OEMESAPRTSLBR"));
/*  85 */     XMLMAP.addChild(new XMLElem("OEMESAPRTSONY", "OEMESAPRTSONY"));
/*  86 */     XMLMAP.addChild(new XMLElem("TIERWSU", "TIERWSU"));
/*  87 */     XMLMAP.addChild(new XMLElem("TECHADV", "TECHADV"));
/*  88 */     XMLMAP.addChild(new XMLElem("REMCODLOAD", "REMCODLOAD"));
/*  89 */     XMLMAP.addChild(new XMLElem("ENHCOMRES", "ENHCOMRES"));
/*  90 */     XMLMAP.addChild(new XMLElem("PREDSUPP", "PREDSUPP"));
/*  91 */     XMLMAP.addChild(new XMLElem("TIERMAIN", "TIERMAIN"));
/*  92 */     XMLMAP.addChild(new XMLElem("SVC1", "SVC1"));
/*  93 */     XMLMAP.addChild(new XMLElem("SVC2", "SVC2"));
/*  94 */     XMLMAP.addChild(new XMLElem("SVC3", "SVC3"));
/*  95 */     XMLMAP.addChild(new XMLElem("SVC4", "SVC4"));
/*  96 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/*  97 */     XMLMAP.addChild(xMLElem);
/*     */     
/*  99 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 100 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 102 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 103 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 104 */     xMLNLSElem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/* 105 */     xMLNLSElem.addChild(new XMLElem("WARRDESC", "WARRDESC"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 113 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 119 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 124 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 130 */     return "WARR_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 139 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSWARRABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */