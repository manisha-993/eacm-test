/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLANNElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLAVAILElem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSSVCMOD05ABR
/*     */   extends XMLMQRoot
/*     */ {
/* 115 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODEL_UPDATE"); static {
/* 116 */     XMLMAP.addChild((XMLElem)new XMLVMElem("MODEL_UPDATE", "0"));
/*     */     
/* 118 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 119 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 120 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 121 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 122 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 123 */     XMLMAP.addChild(new XMLElem("MACHTYPE", "SMACHTYPEATR"));
/* 124 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/* 125 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 126 */     XMLMAP.addChild(new XMLElem("CATEGORY", "SVCMODCATG"));
/* 127 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "SVCMODSUBCATG"));
/* 128 */     XMLMAP.addChild(new XMLElem("GROUP", "SVCMODGRP"));
/* 129 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "SVCMODSUBGRP"));
/* 130 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */     
/* 132 */     XMLANNElem xMLANNElem = new XMLANNElem();
/* 133 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */     
/* 135 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/* 136 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/* 138 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 139 */     XMLMAP.addChild(xMLElem1);
/*     */ 
/*     */     
/* 142 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 143 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 145 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 146 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/*     */     
/* 148 */     xMLElem1 = new XMLElem("AVAILABILITYLIST");
/* 149 */     XMLMAP.addChild(xMLElem1);
/* 150 */     xMLElem1.addChild((XMLElem)new XMLAVAILElem());
/*     */ 
/*     */     
/* 153 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 154 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 156 */     XMLElem xMLElem2 = new XMLElem("TAXCATEGORYELEMENT");
/* 157 */     xMLGroupElem1.addChild(xMLElem2);
/*     */     
/* 159 */     xMLElem2.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/* 160 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYCOUNTRY", "CNTRY", 1));
/* 161 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYSALESORG", "SLEORG"));
/* 162 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/* 163 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SVCMODTAXRELEVANCE", "U:SVCMODTAXRELEVANCE");
/* 164 */     xMLElem2.addChild((XMLElem)xMLGroupElem2);
/* 165 */     xMLGroupElem2.addChild(new XMLElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 173 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 179 */     return "ADRSVCMOD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 184 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 190 */     return "SVCMOD";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 199 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSSVCMOD05ABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */