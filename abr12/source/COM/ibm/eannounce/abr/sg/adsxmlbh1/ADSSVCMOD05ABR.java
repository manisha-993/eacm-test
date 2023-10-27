/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSSVCMOD05ABR
/*     */   extends XMLMQRoot
/*     */ {
/* 112 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODEL_UPDATE"); static {
/* 113 */     XMLMAP.addChild((XMLElem)new XMLVMElem("MODEL_UPDATE", "0"));
/*     */     
/* 115 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 116 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 117 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 118 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 119 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 120 */     XMLMAP.addChild(new XMLElem("MACHTYPE", "SMACHTYPEATR"));
/* 121 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/* 122 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 123 */     XMLMAP.addChild(new XMLElem("CATEGORY", "SVCMODCATG"));
/* 124 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "SVCMODSUBCATG"));
/* 125 */     XMLMAP.addChild(new XMLElem("GROUP", "SVCMODGRP"));
/* 126 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "SVCMODSUBGRP"));
/* 127 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */     
/* 129 */     XMLANNElem xMLANNElem = new XMLANNElem();
/* 130 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */     
/* 132 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/* 133 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/* 135 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 136 */     XMLMAP.addChild(xMLElem1);
/*     */ 
/*     */     
/* 139 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 140 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 142 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 143 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/*     */     
/* 145 */     xMLElem1 = new XMLElem("AVAILABILITYLIST");
/* 146 */     XMLMAP.addChild(xMLElem1);
/* 147 */     xMLElem1.addChild((XMLElem)new XMLAVAILElem());
/*     */ 
/*     */     
/* 150 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 151 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 153 */     XMLElem xMLElem2 = new XMLElem("TAXCATEGORYELEMENT");
/* 154 */     xMLGroupElem1.addChild(xMLElem2);
/*     */     
/* 156 */     xMLElem2.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/* 157 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYCOUNTRY", "CNTRY", 1));
/* 158 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYSALESORG", "SLEORG"));
/* 159 */     xMLElem2.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/* 160 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SVCMODTAXRELEVANCE", "U:SVCMODTAXRELEVANCE");
/* 161 */     xMLElem2.addChild((XMLElem)xMLGroupElem2);
/* 162 */     xMLGroupElem2.addChild(new XMLElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 170 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 176 */     return "ADRSVCMOD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 181 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 187 */     return "SVCMOD";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 196 */     return "$Revision: 1.4 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSSVCMOD05ABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */