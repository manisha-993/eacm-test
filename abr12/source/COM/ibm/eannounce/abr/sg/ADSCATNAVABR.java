/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCtryImgElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
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
/*     */ public class ADSCATNAVABR
/*     */   extends XMLMQChanges
/*     */ {
/*  99 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("CATNAV_UPDATE");
/*     */   static {
/* 101 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 102 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 103 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 104 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 105 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 106 */     XMLMAP.addChild(new XMLElem("TYPE", "CATNAVTYPE"));
/*     */     
/* 108 */     XMLMAP.addChild(new XMLElem("PUBLISH", "CATPUBLISH"));
/* 109 */     XMLMAP.addChild(new XMLElem("PRICEDISCLAIMER", "PRICEDISCLAIMER"));
/* 110 */     XMLMAP.addChild(new XMLElem("IMAGEDISCLAIMER", "IMGDISCLAIMER"));
/* 111 */     XMLMAP.addChild(new XMLElem("FEATUREBENEFIT", "FBSTMT"));
/* 112 */     XMLMAP.addChild(new XMLElem("FAMILYDESCOVERRIDE", "CATFMLYDESC"));
/* 113 */     XMLMAP.addChild(new XMLElem("SERIESDESCOVERRIDE", "CATSERDESC"));
/* 114 */     XMLMAP.addChild(new XMLElem("SERIESHEADING", "CATSERHEAD"));
/*     */     
/* 116 */     XMLElem xMLElem = new XMLElem("PROJECTLIST");
/* 117 */     XMLMAP.addChild(xMLElem);
/* 118 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("PROJECTELEMENT");
/* 119 */     xMLElem.addChild((XMLElem)xMLChgSetElem);
/* 120 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("PROJECT", "PROJCDNAMF", "PROJECTACTIVITY", 1));
/*     */     
/* 122 */     xMLElem = new XMLElem("AUDIENCELIST");
/* 123 */     XMLMAP.addChild(xMLElem);
/* 124 */     xMLChgSetElem = new XMLChgSetElem("AUDIENCEELEMENT");
/* 125 */     xMLElem.addChild((XMLElem)xMLChgSetElem);
/* 126 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTIVITY", 1));
/*     */     
/* 128 */     xMLElem = new XMLElem("COUNTRYLIST");
/* 129 */     XMLMAP.addChild(xMLElem);
/* 130 */     xMLElem.addChild((XMLElem)new XMLCtryImgElem());
/*     */     
/* 132 */     xMLElem = new XMLElem("LANGUAGELIST");
/* 133 */     XMLMAP.addChild(xMLElem);
/*     */     
/* 135 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 136 */     xMLElem.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 138 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 139 */     xMLNLSElem.addChild(new XMLElem("CATBR", "CATBR"));
/* 140 */     xMLNLSElem.addChild(new XMLElem("CATFMLY", "CATFMLY"));
/* 141 */     xMLNLSElem.addChild(new XMLElem("CATSER", "CATSER"));
/* 142 */     xMLNLSElem.addChild(new XMLElem("CATOPTGRPNAM", "CATOPTGRPNAM"));
/* 143 */     xMLNLSElem.addChild(new XMLElem("CATNAME", "CATNAME"));
/* 144 */     xMLNLSElem.addChild(new XMLElem("LONGMKTGMESSAGE", "LONGMKTGMSG"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 151 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 157 */     return "ADSCATNAV";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 163 */     return "CATNAV";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 172 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSCATNAVABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */