/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCtryImgElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
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
/*     */ public class ADSCATNAVABR
/*     */   extends XMLMQRoot
/*     */ {
/*  61 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("CATNAV_UPDATE"); static {
/*  62 */     XMLMAP.addChild((XMLElem)new XMLVMElem("CATNAV_UPDATE", "1"));
/*     */     
/*  64 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  65 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  66 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  67 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*  68 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  69 */     XMLMAP.addChild(new XMLElem("TYPE", "CATNAVTYPE"));
/*  70 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*     */     
/*  72 */     XMLMAP.addChild(new XMLElem("PUBLISH", "CATPUBLISH"));
/*  73 */     XMLMAP.addChild(new XMLElem("CATBR", "CATBR"));
/*  74 */     XMLMAP.addChild(new XMLElem("CATNAME", "CATNAME"));
/*     */     
/*  76 */     XMLElem xMLElem4 = new XMLElem("PROJECTLIST");
/*  77 */     XMLMAP.addChild(xMLElem4);
/*  78 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("PROJECTELEMENT");
/*  79 */     xMLElem4.addChild((XMLElem)xMLChgSetElem);
/*  80 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("PROJECT", "PROJCDNAMF", "PROJECTACTIVITY", 1));
/*     */ 
/*     */     
/*  83 */     xMLElem4 = new XMLElem("COUNTRYLIST");
/*  84 */     XMLMAP.addChild(xMLElem4);
/*  85 */     xMLElem4.addChild((XMLElem)new XMLCtryImgElem());
/*     */     
/*  87 */     xMLElem4 = new XMLElem("LANGUAGELIST");
/*  88 */     XMLMAP.addChild(xMLElem4);
/*     */     
/*  90 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  91 */     xMLElem4.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  93 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  94 */     xMLNLSElem.addChild(new XMLElem("CATFMLY", "CATFMLY"));
/*  95 */     xMLNLSElem.addChild(new XMLElem("CATSER", "CATSER"));
/*  96 */     xMLNLSElem.addChild(new XMLElem("CATOPTGRPNAM", "CATOPTGRPNAM"));
/*  97 */     xMLNLSElem.addChild(new XMLElem("CATNAME", "CATNAME"));
/*  98 */     xMLNLSElem.addChild(new XMLElem("CATNAVL1", "CATNAVL1"));
/*  99 */     xMLNLSElem.addChild(new XMLElem("CATNAVL2", "CATNAVL2"));
/* 100 */     xMLNLSElem.addChild(new XMLElem("CATNAVL3", "CATNAVL3"));
/* 101 */     xMLNLSElem.addChild(new XMLElem("CATNAVL4", "CATNAVL4"));
/* 102 */     xMLNLSElem.addChild(new XMLElem("PRICEDISCLAIMER", "PRICEDISCLAIMER"));
/* 103 */     xMLNLSElem.addChild(new XMLElem("IMAGEDISCLAIMER", "IMGDISCLAIMER"));
/* 104 */     xMLNLSElem.addChild(new XMLElem("FEATUREBENEFIT", "FBSTMT"));
/* 105 */     xMLNLSElem.addChild(new XMLElem("FAMILYDESCOVERRIDE", "CATFMLYDESC"));
/* 106 */     xMLNLSElem.addChild(new XMLElem("SERIESDESCOVERRIDE", "CATSERDESC"));
/* 107 */     xMLNLSElem.addChild(new XMLElem("SERIESHEADING", "CATSERHEAD"));
/* 108 */     xMLNLSElem.addChild(new XMLElem("LONGMKTGMSG", "LONGMKTGMSG"));
/*     */     
/* 110 */     xMLElem4 = new XMLElem("CATAUDIENCELIST");
/* 111 */     XMLMAP.addChild(xMLElem4);
/* 112 */     xMLChgSetElem = new XMLChgSetElem("CATAUDIENCEELEMENT");
/* 113 */     xMLElem4.addChild((XMLElem)xMLChgSetElem);
/* 114 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("CATAUDIENCE", "CATAUDIENCE", "ACTIVITY", 0));
/*     */     
/* 116 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("CATDETAILSLIST", "CATGROUP");
/* 117 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/* 118 */     XMLElem xMLElem5 = new XMLElem("CATDETAILSELEMENT");
/* 119 */     xMLGroupElem3.addChild(xMLElem5);
/* 120 */     xMLElem5.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 121 */     xMLElem5.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 122 */     xMLElem5.addChild(new XMLElem("CATSEQ", "CATSEQ"));
/* 123 */     XMLElem xMLElem3 = new XMLElem("CATDESCLIST");
/* 124 */     xMLElem5.addChild(xMLElem3);
/*     */     
/* 126 */     xMLNLSElem = new XMLNLSElem("CATDESCELEMENT");
/* 127 */     xMLElem3.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 129 */     xMLNLSElem.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 130 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 131 */     xMLNLSElem.addChild(new XMLElem("GROUPDESCRIPTION", "GROUPDESCRIPTION"));
/*     */     
/* 133 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem("CATATTRLIST", "CATATTR", "D:CATGROUPATTR:D", true);
/*     */     
/* 135 */     xMLElem5.addChild((XMLElem)xMLGroupElem2);
/*     */     
/* 137 */     XMLElem xMLElem6 = new XMLElem("CATATTRELEMENT");
/* 138 */     xMLGroupElem2.addChild(xMLElem6);
/*     */     
/* 140 */     xMLElem6.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 141 */     xMLElem6.addChild(new XMLElem("CATENTITYTYPE", "DAENTITYTYPE"));
/* 142 */     xMLElem6.addChild(new XMLElem("CATATTRIBUTECODE", "DAATTRIBUTECODE"));
/* 143 */     xMLElem6.addChild(new XMLElem("CATSEQ", "CATSEQ"));
/* 144 */     XMLElem xMLElem2 = new XMLElem("CATDESCLIST");
/* 145 */     xMLElem6.addChild(xMLElem2);
/*     */     
/* 147 */     xMLNLSElem = new XMLNLSElem("CATDESCELEMENT");
/* 148 */     xMLElem2.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 150 */     xMLNLSElem.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 151 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 152 */     xMLNLSElem.addChild(new XMLElem("ATTRIBUTEDESCRIPTION", "ATTRDESCRIPTION"));
/*     */     
/* 154 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("CATVAMLIST", "CATVAMATTR");
/* 155 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 156 */     XMLElem xMLElem7 = new XMLElem("CATVAMATTRELEMENT");
/* 157 */     xMLGroupElem1.addChild(xMLElem7);
/* 158 */     xMLElem7.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 159 */     xMLElem7.addChild(new XMLElem("CATENTITYTYPE", "DAENTITYTYPE"));
/* 160 */     xMLElem7.addChild(new XMLElem("CATATTRIBUTECODE", "DAATTRIBUTECODE"));
/* 161 */     xMLElem7.addChild(new XMLElem("CATCOLUMN", "CATCOLUMN"));
/* 162 */     xMLElem7.addChild(new XMLElem("CATSEQ", "CATSEQ"));
/*     */     
/* 164 */     XMLElem xMLElem1 = new XMLElem("CATDESCLIST");
/* 165 */     xMLElem7.addChild(xMLElem1);
/*     */     
/* 167 */     xMLNLSElem = new XMLNLSElem("CATDESCELEMENT");
/* 168 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 170 */     xMLNLSElem.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 171 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 172 */     xMLNLSElem.addChild(new XMLElem("ATTRIBUTEDESCRIPTION", "ATTRDESCRIPTION"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 180 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 186 */     return "ADSCATNAV";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 191 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 197 */     return "CATNAV_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 206 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSCATNAVABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */