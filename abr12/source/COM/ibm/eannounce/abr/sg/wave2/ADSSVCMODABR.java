/*     */ package COM.ibm.eannounce.abr.sg.wave2;
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
/*  99 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODEL_UPDATE");
/*     */   static {
/* 101 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 102 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 103 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 104 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 105 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 106 */     XMLMAP.addChild(new XMLElem("MACHTYPE", "SMACHTYPEATR"));
/* 107 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/* 108 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 109 */     XMLMAP.addChild(new XMLElem("CATEGORY", "SVCMODCATG"));
/* 110 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "SVCMODSUBCATG"));
/* 111 */     XMLMAP.addChild(new XMLElem("GROUP", "SVCMODGRP"));
/* 112 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "SVCMODSUBGRP"));
/* 113 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */     
/* 115 */     XMLANNElem xMLANNElem = new XMLANNElem();
/* 116 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */     
/* 118 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/* 119 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/* 121 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 122 */     XMLMAP.addChild(xMLElem1);
/*     */ 
/*     */     
/* 125 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 126 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 128 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 129 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/*     */     
/* 131 */     xMLElem1 = new XMLElem("AVAILABILITYLIST");
/* 132 */     XMLMAP.addChild(xMLElem1);
/* 133 */     xMLElem1.addChild((XMLElem)new XMLAVAILElem());
/*     */ 
/*     */     
/* 136 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 137 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 139 */     XMLElem xMLElem2 = new XMLElem("TAXCATEGORYELEMENT");
/* 140 */     xMLGroupElem1.addChild(xMLElem2);
/*     */     
/* 142 */     xMLElem2.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/* 143 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYCOUNTRY", "CNTRY", 1));
/* 144 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYSALESORG", "SLEORG"));
/* 145 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/* 146 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SVCMODTAXRELEVANCE", "U:SVCMODTAXRELEVANCE");
/* 147 */     xMLElem2.addChild((XMLElem)xMLGroupElem2);
/* 148 */     xMLGroupElem2.addChild(new XMLElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 156 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 162 */     return "ADSSVCMOD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 167 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 173 */     return "SVCMOD";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 182 */     return "$Revision: 1.3 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\wave2\ADSSVCMODABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */