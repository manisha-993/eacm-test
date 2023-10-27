/*     */ package COM.ibm.eannounce.abr.sg.wave2;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLANNElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLLSEOAVAILElem;
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
/*  73 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SEO_UPDATE");
/*     */   static {
/*  75 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  76 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  77 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  78 */     XMLMAP.addChild(new XMLElem("SEOENTITYTYPE", "ENTITYTYPE"));
/*  79 */     XMLMAP.addChild(new XMLElem("SEOENTITYID", "ENTITYID"));
/*     */     
/*  81 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "MODEL");
/*  82 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*  83 */     xMLGroupElem1.addChild(new XMLElem("PARENTENTITYTYPE", "ENTITYTYPE"));
/*  84 */     xMLGroupElem1.addChild(new XMLElem("PARENTENTITYID", "ENTITYID"));
/*  85 */     xMLGroupElem1.addChild(new XMLElem("PARENTMODEL", "MODELATR"));
/*  86 */     xMLGroupElem1.addChild(new XMLElem("PARENTMACHTPE", "MACHTYPEATR"));
/*     */     
/*  88 */     XMLMAP.addChild(new XMLElem("SEOID", "SEOID"));
/*  89 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*     */     
/*  91 */     xMLGroupElem1.addChild(new XMLElem("CATEGORY", "COFCAT"));
/*  92 */     xMLGroupElem1.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT"));
/*  93 */     xMLGroupElem1.addChild(new XMLElem("GROUP", "COFGRP"));
/*  94 */     xMLGroupElem1.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/*     */     
/*  96 */     XMLMAP.addChild(new XMLElem("PRFCNTR", "PRFTCTR", 1));
/*     */     
/*  98 */     XMLANNElem xMLANNElem = new XMLANNElem();
/*  99 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */     
/* 101 */     XMLMAP.addChild(new XMLElem("PRDHIERCD", "BHPRODHIERCD"));
/* 102 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/* 104 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "WWSEO");
/* 105 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 106 */     xMLGroupElem2.addChild(new XMLElem("SPECIALBID", "SPECBID"));
/*     */ 
/*     */     
/* 109 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 110 */     xMLGroupElem2.addChild(xMLElem1);
/*     */ 
/*     */     
/* 113 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 114 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 116 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 117 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "PRCFILENAM"));
/*     */ 
/*     */     
/* 120 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 121 */     XMLMAP.addChild(xMLElem2);
/* 122 */     xMLElem2.addChild((XMLElem)new XMLLSEOAVAILElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 130 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 136 */     return "ADSLSEO";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 141 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 147 */     return "LSEO";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 156 */     return "$Revision: 1.4 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\wave2\ADSLSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */