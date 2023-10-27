/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSCATNAVABR
/*     */   extends XMLMQRoot
/*     */ {
/*  58 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("CATNAV_UPDATE"); static {
/*  59 */     XMLMAP.addChild((XMLElem)new XMLVMElem("CATNAV_UPDATE", "1"));
/*     */     
/*  61 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  62 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  63 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  64 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*  65 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*  66 */     XMLMAP.addChild(new XMLElem("TYPE", "CATNAVTYPE"));
/*  67 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*     */     
/*  69 */     XMLMAP.addChild(new XMLElem("PUBLISH", "CATPUBLISH"));
/*  70 */     XMLMAP.addChild(new XMLElem("CATBR", "CATBR"));
/*  71 */     XMLMAP.addChild(new XMLElem("CATNAME", "CATNAME"));
/*     */     
/*  73 */     XMLElem xMLElem4 = new XMLElem("PROJECTLIST");
/*  74 */     XMLMAP.addChild(xMLElem4);
/*  75 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("PROJECTELEMENT");
/*  76 */     xMLElem4.addChild((XMLElem)xMLChgSetElem);
/*  77 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("PROJECT", "PROJCDNAMF", "PROJECTACTIVITY", 1));
/*     */ 
/*     */     
/*  80 */     xMLElem4 = new XMLElem("COUNTRYLIST");
/*  81 */     XMLMAP.addChild(xMLElem4);
/*  82 */     xMLElem4.addChild((XMLElem)new XMLCtryImgElem());
/*     */     
/*  84 */     xMLElem4 = new XMLElem("LANGUAGELIST");
/*  85 */     XMLMAP.addChild(xMLElem4);
/*     */     
/*  87 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/*  88 */     xMLElem4.addChild((XMLElem)xMLNLSElem);
/*     */     
/*  90 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*  91 */     xMLNLSElem.addChild(new XMLElem("CATFMLY", "CATFMLY"));
/*  92 */     xMLNLSElem.addChild(new XMLElem("CATSER", "CATSER"));
/*  93 */     xMLNLSElem.addChild(new XMLElem("CATOPTGRPNAM", "CATOPTGRPNAM"));
/*  94 */     xMLNLSElem.addChild(new XMLElem("CATNAME", "CATNAME"));
/*  95 */     xMLNLSElem.addChild(new XMLElem("CATNAVL1", "CATNAVL1"));
/*  96 */     xMLNLSElem.addChild(new XMLElem("CATNAVL2", "CATNAVL2"));
/*  97 */     xMLNLSElem.addChild(new XMLElem("CATNAVL3", "CATNAVL3"));
/*  98 */     xMLNLSElem.addChild(new XMLElem("CATNAVL4", "CATNAVL4"));
/*  99 */     xMLNLSElem.addChild(new XMLElem("PRICEDISCLAIMER", "PRICEDISCLAIMER"));
/* 100 */     xMLNLSElem.addChild(new XMLElem("IMAGEDISCLAIMER", "IMGDISCLAIMER"));
/* 101 */     xMLNLSElem.addChild(new XMLElem("FEATUREBENEFIT", "FBSTMT"));
/* 102 */     xMLNLSElem.addChild(new XMLElem("FAMILYDESCOVERRIDE", "CATFMLYDESC"));
/* 103 */     xMLNLSElem.addChild(new XMLElem("SERIESDESCOVERRIDE", "CATSERDESC"));
/* 104 */     xMLNLSElem.addChild(new XMLElem("SERIESHEADING", "CATSERHEAD"));
/* 105 */     xMLNLSElem.addChild(new XMLElem("LONGMKTGMSG", "LONGMKTGMSG"));
/*     */     
/* 107 */     xMLElem4 = new XMLElem("CATAUDIENCELIST");
/* 108 */     XMLMAP.addChild(xMLElem4);
/* 109 */     xMLChgSetElem = new XMLChgSetElem("CATAUDIENCEELEMENT");
/* 110 */     xMLElem4.addChild((XMLElem)xMLChgSetElem);
/* 111 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("CATAUDIENCE", "CATAUDIENCE", "ACTIVITY", 0));
/*     */     
/* 113 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("CATDETAILSLIST", "CATGROUP");
/* 114 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/* 115 */     XMLElem xMLElem5 = new XMLElem("CATDETAILSELEMENT");
/* 116 */     xMLGroupElem3.addChild(xMLElem5);
/* 117 */     xMLElem5.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 118 */     xMLElem5.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 119 */     xMLElem5.addChild(new XMLElem("CATSEQ", "CATSEQ"));
/* 120 */     XMLElem xMLElem3 = new XMLElem("CATDESCLIST");
/* 121 */     xMLElem5.addChild(xMLElem3);
/*     */     
/* 123 */     xMLNLSElem = new XMLNLSElem("CATDESCELEMENT");
/* 124 */     xMLElem3.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 126 */     xMLNLSElem.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 127 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 128 */     xMLNLSElem.addChild(new XMLElem("GROUPDESCRIPTION", "GROUPDESCRIPTION"));
/*     */     
/* 130 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem("CATATTRLIST", "CATATTR", "D:CATGROUPATTR:D", true);
/*     */     
/* 132 */     xMLElem5.addChild((XMLElem)xMLGroupElem2);
/*     */     
/* 134 */     XMLElem xMLElem6 = new XMLElem("CATATTRELEMENT");
/* 135 */     xMLGroupElem2.addChild(xMLElem6);
/*     */     
/* 137 */     xMLElem6.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 138 */     xMLElem6.addChild(new XMLElem("CATENTITYTYPE", "DAENTITYTYPE"));
/* 139 */     xMLElem6.addChild(new XMLElem("CATATTRIBUTECODE", "DAATTRIBUTECODE"));
/* 140 */     xMLElem6.addChild(new XMLElem("CATSEQ", "CATSEQ"));
/* 141 */     XMLElem xMLElem2 = new XMLElem("CATDESCLIST");
/* 142 */     xMLElem6.addChild(xMLElem2);
/*     */     
/* 144 */     xMLNLSElem = new XMLNLSElem("CATDESCELEMENT");
/* 145 */     xMLElem2.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 147 */     xMLNLSElem.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 148 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 149 */     xMLNLSElem.addChild(new XMLElem("ATTRIBUTEDESCRIPTION", "ATTRDESCRIPTION"));
/*     */     
/* 151 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("CATVAMLIST", "CATVAMATTR");
/* 152 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 153 */     XMLElem xMLElem7 = new XMLElem("CATVAMATTRELEMENT");
/* 154 */     xMLGroupElem1.addChild(xMLElem7);
/* 155 */     xMLElem7.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 156 */     xMLElem7.addChild(new XMLElem("CATENTITYTYPE", "DAENTITYTYPE"));
/* 157 */     xMLElem7.addChild(new XMLElem("CATATTRIBUTECODE", "DAATTRIBUTECODE"));
/* 158 */     xMLElem7.addChild(new XMLElem("CATCOLUMN", "CATCOLUMN"));
/* 159 */     xMLElem7.addChild(new XMLElem("CATSEQ", "CATSEQ"));
/*     */     
/* 161 */     XMLElem xMLElem1 = new XMLElem("CATDESCLIST");
/* 162 */     xMLElem7.addChild(xMLElem1);
/*     */     
/* 164 */     xMLNLSElem = new XMLNLSElem("CATDESCELEMENT");
/* 165 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 167 */     xMLNLSElem.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 168 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 169 */     xMLNLSElem.addChild(new XMLElem("ATTRIBUTEDESCRIPTION", "ATTRDESCRIPTION"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 177 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 183 */     return "ADSCATNAV";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 188 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 194 */     return "CATNAV_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 203 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSCATNAVABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */