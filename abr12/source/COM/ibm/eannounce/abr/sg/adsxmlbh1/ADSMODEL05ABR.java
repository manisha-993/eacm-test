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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSMODEL05ABR
/*     */   extends XMLMQRoot
/*     */ {
/* 203 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODEL_UPDATE"); static {
/* 204 */     XMLMAP.addChild((XMLElem)new XMLVMElem("MODEL_UPDATE", "0"));
/*     */     
/* 206 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 207 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 208 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 209 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 210 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 211 */     XMLMAP.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
/* 212 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/* 213 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 214 */     XMLMAP.addChild(new XMLElem("CATEGORY", "COFCAT"));
/* 215 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT"));
/* 216 */     XMLMAP.addChild(new XMLElem("GROUP", "COFGRP"));
/* 217 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */ 
/*     */     
/* 229 */     XMLANNElem xMLANNElem = new XMLANNElem();
/* 230 */     XMLMAP.addChild((XMLElem)xMLANNElem);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     XMLGroupElem xMLGroupElem = new XMLGroupElem(null, "WEIGHTNDIMN");
/* 245 */     XMLMAP.addChild((XMLElem)xMLGroupElem);
/* 246 */     xMLGroupElem.addChild(new XMLElem("MEASUREMETRIC", "WGHTMTRIC|WGHTMTRICUNIT"));
/*     */ 
/*     */     
/* 249 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/*     */ 
/*     */     
/* 252 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */     
/* 254 */     XMLElem xMLElem = new XMLElem("LANGUAGELIST");
/* 255 */     XMLMAP.addChild(xMLElem);
/*     */ 
/*     */     
/* 258 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 259 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 261 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */     
/* 264 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/*     */ 
/*     */     
/* 267 */     xMLElem = new XMLElem("AVAILABILITYLIST");
/* 268 */     XMLMAP.addChild(xMLElem);
/* 269 */     xMLElem.addChild((XMLElem)new XMLAVAILElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 381 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 387 */     return "ADRMODEL";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 392 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 398 */     return "MODEL";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 407 */     return "1.4";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSMODEL05ABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */