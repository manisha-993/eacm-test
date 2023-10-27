/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLANNElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLAVAILElem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSSVCMODABR
/*     */   extends XMLMQRoot
/*     */ {
/*  92 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODEL_UPDATE");
/*     */   static {
/*  94 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  95 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  96 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  97 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/*  98 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/*  99 */     XMLMAP.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
/* 100 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/* 101 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 102 */     XMLMAP.addChild(new XMLElem("CATEGORY", "SVCMODCATG"));
/* 103 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "SVCMODSUBCATG"));
/* 104 */     XMLMAP.addChild(new XMLElem("GROUP", "SVCMODGRP"));
/* 105 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "SVCMODSUBGRP"));
/* 106 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */     
/* 108 */     XMLANNElem xMLANNElem = new XMLANNElem();
/* 109 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */     
/* 111 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/* 112 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/* 114 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 115 */     XMLMAP.addChild(xMLElem1);
/*     */ 
/*     */     
/* 118 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 119 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 121 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 122 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/*     */     
/* 124 */     xMLElem1 = new XMLElem("AVAILABILITYLIST");
/* 125 */     XMLMAP.addChild(xMLElem1);
/* 126 */     xMLElem1.addChild((XMLElem)new XMLAVAILElem());
/*     */ 
/*     */     
/* 129 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 130 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 132 */     XMLElem xMLElem2 = new XMLElem("TAXCATEGORYELEMENT");
/* 133 */     xMLGroupElem1.addChild(xMLElem2);
/*     */     
/* 135 */     xMLElem2.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/* 136 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYCOUNTRY", "CNTRY", 1));
/* 137 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYSALESORG", "SLEORG"));
/* 138 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/* 139 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SVCMODTAXRELEVANCE", "U:SVCMODTAXRELEVANCE");
/* 140 */     xMLElem2.addChild((XMLElem)xMLGroupElem2);
/* 141 */     xMLGroupElem2.addChild(new XMLElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 149 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 155 */     return "ADSSVCMOD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 160 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 166 */     return "SVCMOD";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 175 */     return "$Revision: 1.10 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSSVCMODABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */