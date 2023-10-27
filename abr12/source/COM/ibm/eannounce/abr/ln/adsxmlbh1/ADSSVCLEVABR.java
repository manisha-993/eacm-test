/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSSVCLEVABR
/*     */   extends XMLMQRoot
/*     */ {
/*  65 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SVCLEV_UPDATE"); static {
/*  66 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SVCLEV_UPDATE", "1"));
/*     */ 
/*     */     
/*  69 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  70 */     XMLMAP.addChild(new XMLElem("SVCLEVCD", "SVCLEVCD"));
/*  71 */     XMLMAP.addChild(new XMLElem("COVRSHRTDESC", "COVRSHRTDESC"));
/*     */     
/*  73 */     XMLMAP.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/*  74 */     XMLMAP.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*  75 */     XMLMAP.addChild(new XMLElem("SVCDELIVMETH", "SVCDELIVMETH"));
/*  76 */     XMLMAP.addChild(new XMLElem("FIXTME", "FIXTME"));
/*  77 */     XMLMAP.addChild(new XMLElem("FIXTMEUOM", "FIXTMEUOM"));
/*     */     
/*  79 */     XMLMAP.addChild(new XMLElem("FIXTMEOBJIVE", "FIXTMEOBJIVE", 2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     XMLMAP.addChild(new XMLElem("ONSITERESPTME", "ONSITERESPTME"));
/*  87 */     XMLMAP.addChild(new XMLElem("ONSITERESPTMEUOM", "ONSITERESPTMEUOM"));
/*  88 */     XMLMAP.addChild(new XMLElem("ONSITERESPTMEOBJIVE", "ONSITERESPTMEOBJIVE"));
/*     */     
/*  90 */     XMLMAP.addChild(new XMLElem("CONTTME", "CONTTME"));
/*  91 */     XMLMAP.addChild(new XMLElem("CONTTMEUOM", "CONTTMEUOM"));
/*  92 */     XMLMAP.addChild(new XMLElem("CONTTMEOBJIVE", "CONTTMEOBJIVE"));
/*  93 */     XMLMAP.addChild(new XMLElem("TRNARNDTME", "TRNARNDTME"));
/*  94 */     XMLMAP.addChild(new XMLElem("TRNARNDTMEUOM", "TRNARNDTMEUOM"));
/*  95 */     XMLMAP.addChild(new XMLElem("TRNARNDTMEOBJIVE", "TRNARNDTMEOBJIVE"));
/*  96 */     XMLMAP.addChild(new XMLElem("PARTARRVTME", "PARTARRVTME"));
/*  97 */     XMLMAP.addChild(new XMLElem("PARTARRVTMEUOM", "PARTARRVTMEUOM"));
/*  98 */     XMLMAP.addChild(new XMLElem("PARTARRVTMEOBJIVE", "PARTARRVTMEOBJIVE"));
/*     */     
/* 100 */     XMLElem xMLElem2 = new XMLElem("LANGUAGELIST");
/* 101 */     XMLMAP.addChild(xMLElem2);
/*     */     
/* 103 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 104 */     xMLElem2.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 106 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 108 */     xMLNLSElem.addChild(new XMLElem("NCKNME", "NCKNME"));
/* 109 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 110 */     xMLNLSElem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */ 
/*     */     
/* 114 */     XMLGroupElem xMLGroupElem = new XMLGroupElem("SLCNTRYCONDLIST", "SLCNTRYCOND", "D:SVCLEVSLCNTRYCOND:D", true);
/* 115 */     XMLMAP.addChild((XMLElem)xMLGroupElem);
/*     */     
/* 117 */     XMLElem xMLElem3 = new XMLElem("SLCNTRYCONDELEMENT");
/* 118 */     xMLGroupElem.addChild(xMLElem3);
/*     */     
/* 120 */     xMLElem3.addChild((XMLElem)new XMLActivityElem("SLCNTRYCONDACTION"));
/*     */     
/* 122 */     xMLElem3.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 123 */     xMLElem3.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 124 */     XMLElem xMLElem1 = new XMLElem("COUNTRYLIST");
/* 125 */     xMLElem3.addChild(xMLElem1);
/*     */     
/* 127 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("COUNTRYELEMENT");
/* 128 */     xMLElem1.addChild((XMLElem)xMLChgSetElem);
/*     */     
/* 130 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */     
/* 133 */     xMLElem3.addChild(new XMLElem("COVRLNGDESC", "COVRLNGDESC"));
/* 134 */     xMLElem3.addChild(new XMLElem("MONSTRTTME", "MONSTRTTME"));
/* 135 */     xMLElem3.addChild(new XMLElem("MONENDTME", "MONENDTME"));
/* 136 */     xMLElem3.addChild(new XMLElem("TUESDYSTRTTME", "TUESDYSTRTTME"));
/* 137 */     xMLElem3.addChild(new XMLElem("TUESDYENDTME", "TUESDYENDTME"));
/* 138 */     xMLElem3.addChild(new XMLElem("WEDSTRTTME", "WEDSTRTTME"));
/* 139 */     xMLElem3.addChild(new XMLElem("WEDENDTME", "WEDENDTME"));
/* 140 */     xMLElem3.addChild(new XMLElem("THURSSTRTTME", "THURSSTRTTME"));
/* 141 */     xMLElem3.addChild(new XMLElem("THURSENDTME", "THURSENDTME"));
/* 142 */     xMLElem3.addChild(new XMLElem("FRISTRTTME", "FRISTRTTME"));
/* 143 */     xMLElem3.addChild(new XMLElem("FRIENDTME", "FRIENDTME"));
/* 144 */     xMLElem3.addChild(new XMLElem("SATSTRTTME", "SATSTRTTME"));
/* 145 */     xMLElem3.addChild(new XMLElem("SATENDTME", "SATENDTME"));
/* 146 */     xMLElem3.addChild(new XMLElem("SUNSTRTTME", "SUNSTRTTME"));
/* 147 */     xMLElem3.addChild(new XMLElem("SUNENDTME", "SUNENDTME"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 155 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 162 */     return "ADSSVCLEV";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 169 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 177 */     return "SVCLEV_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 186 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSSVCLEVABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */