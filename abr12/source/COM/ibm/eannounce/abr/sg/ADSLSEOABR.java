/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLANNElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
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
/*     */ public class ADSLSEOABR
/*     */   extends XMLMQRoot
/*     */ {
/*  64 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SEO_UPDATE");
/*     */   static {
/*  66 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  67 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  68 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  69 */     XMLMAP.addChild(new XMLElem("SEOENTITYTYPE", "ENTITYTYPE"));
/*  70 */     XMLMAP.addChild(new XMLElem("SEOENTITYID", "ENTITYID"));
/*     */     
/*  72 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "MODEL");
/*  73 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*  74 */     xMLGroupElem1.addChild(new XMLElem("PARENTENTITYTYPE", "ENTITYTYPE"));
/*  75 */     xMLGroupElem1.addChild(new XMLElem("PARENTENTITYID", "ENTITYID"));
/*  76 */     xMLGroupElem1.addChild(new XMLElem("PARENTMODEL", "MODELATR"));
/*  77 */     xMLGroupElem1.addChild(new XMLElem("PARENTMACHTPE", "MACHTYPEATR"));
/*     */     
/*  79 */     XMLMAP.addChild(new XMLElem("SEOID", "SEOID"));
/*  80 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*     */     
/*  82 */     xMLGroupElem1.addChild(new XMLElem("CATEGORY", "COFCAT"));
/*  83 */     xMLGroupElem1.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT"));
/*  84 */     xMLGroupElem1.addChild(new XMLElem("GROUP", "COFGRP"));
/*  85 */     xMLGroupElem1.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/*     */     
/*  87 */     XMLMAP.addChild(new XMLElem("PRFCNTR", "PRFTCTR", 1));
/*     */     
/*  89 */     XMLANNElem xMLANNElem = new XMLANNElem();
/*  90 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */     
/*  92 */     XMLMAP.addChild(new XMLElem("PRDHIERCD", "BHPRODHIERCD", 1));
/*  93 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/*  95 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "WWSEO");
/*  96 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/*  97 */     xMLGroupElem2.addChild(new XMLElem("SPECIALBID", "SPECBID"));
/*     */ 
/*     */     
/* 100 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/* 101 */     xMLGroupElem2.addChild(xMLElem);
/*     */ 
/*     */     
/* 104 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 105 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 107 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 108 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "PRCFILENAM"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 116 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 122 */     return "ADSLSEO";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 127 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 133 */     return "LSEO";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 142 */     return "$Revision: 1.6 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSLSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */