/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSSVCLEVABR
/*     */   extends XMLMQRoot
/*     */ {
/*  62 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SVCLEV_UPDATE"); static {
/*  63 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SVCLEV_UPDATE", "1"));
/*     */ 
/*     */     
/*  66 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  67 */     XMLMAP.addChild(new XMLElem("SVCLEVCD", "SVCLEVCD"));
/*  68 */     XMLMAP.addChild(new XMLElem("COVRSHRTDESC", "COVRSHRTDESC"));
/*     */     
/*  70 */     XMLMAP.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/*  71 */     XMLMAP.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*  72 */     XMLMAP.addChild(new XMLElem("SVCDELIVMETH", "SVCDELIVMETH"));
/*  73 */     XMLMAP.addChild(new XMLElem("FIXTME", "FIXTME"));
/*  74 */     XMLMAP.addChild(new XMLElem("FIXTMEUOM", "FIXTMEUOM"));
/*     */     
/*  76 */     XMLMAP.addChild(new XMLElem("FIXTMEOBJIVE", "FIXTMEOBJIVE", 2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     XMLMAP.addChild(new XMLElem("ONSITERESPTME", "ONSITERESPTME"));
/*  84 */     XMLMAP.addChild(new XMLElem("ONSITERESPTMEUOM", "ONSITERESPTMEUOM"));
/*  85 */     XMLMAP.addChild(new XMLElem("ONSITERESPTMEOBJIVE", "ONSITERESPTMEOBJIVE"));
/*     */     
/*  87 */     XMLMAP.addChild(new XMLElem("CONTTME", "CONTTME"));
/*  88 */     XMLMAP.addChild(new XMLElem("CONTTMEUOM", "CONTTMEUOM"));
/*  89 */     XMLMAP.addChild(new XMLElem("CONTTMEOBJIVE", "CONTTMEOBJIVE"));
/*  90 */     XMLMAP.addChild(new XMLElem("TRNARNDTME", "TRNARNDTME"));
/*  91 */     XMLMAP.addChild(new XMLElem("TRNARNDTMEUOM", "TRNARNDTMEUOM"));
/*  92 */     XMLMAP.addChild(new XMLElem("TRNARNDTMEOBJIVE", "TRNARNDTMEOBJIVE"));
/*  93 */     XMLMAP.addChild(new XMLElem("PARTARRVTME", "PARTARRVTME"));
/*  94 */     XMLMAP.addChild(new XMLElem("PARTARRVTMEUOM", "PARTARRVTMEUOM"));
/*  95 */     XMLMAP.addChild(new XMLElem("PARTARRVTMEOBJIVE", "PARTARRVTMEOBJIVE"));
/*     */     
/*  97 */     XMLElem xMLElem2 = new XMLElem("LANGUAGELIST");
/*  98 */     XMLMAP.addChild(xMLElem2);
/*     */     
/* 100 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 101 */     xMLElem2.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 103 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 105 */     xMLNLSElem.addChild(new XMLElem("NCKNME", "NCKNME"));
/* 106 */     xMLNLSElem.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 107 */     xMLNLSElem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */ 
/*     */     
/* 111 */     XMLGroupElem xMLGroupElem = new XMLGroupElem("SLCNTRYCONDLIST", "SLCNTRYCOND", "D:SVCLEVSLCNTRYCOND:D", true);
/* 112 */     XMLMAP.addChild((XMLElem)xMLGroupElem);
/*     */     
/* 114 */     XMLElem xMLElem3 = new XMLElem("SLCNTRYCONDELEMENT");
/* 115 */     xMLGroupElem.addChild(xMLElem3);
/*     */     
/* 117 */     xMLElem3.addChild((XMLElem)new XMLActivityElem("SLCNTRYCONDACTION"));
/*     */     
/* 119 */     xMLElem3.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 120 */     xMLElem3.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 121 */     XMLElem xMLElem1 = new XMLElem("COUNTRYLIST");
/* 122 */     xMLElem3.addChild(xMLElem1);
/*     */     
/* 124 */     XMLChgSetElem xMLChgSetElem = new XMLChgSetElem("COUNTRYELEMENT");
/* 125 */     xMLElem1.addChild((XMLElem)xMLChgSetElem);
/*     */     
/* 127 */     xMLChgSetElem.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */     
/* 130 */     xMLElem3.addChild(new XMLElem("COVRLNGDESC", "COVRLNGDESC"));
/* 131 */     xMLElem3.addChild(new XMLElem("MONSTRTTME", "MONSTRTTME"));
/* 132 */     xMLElem3.addChild(new XMLElem("MONENDTME", "MONENDTME"));
/* 133 */     xMLElem3.addChild(new XMLElem("TUESDYSTRTTME", "TUESDYSTRTTME"));
/* 134 */     xMLElem3.addChild(new XMLElem("TUESDYENDTME", "TUESDYENDTME"));
/* 135 */     xMLElem3.addChild(new XMLElem("WEDSTRTTME", "WEDSTRTTME"));
/* 136 */     xMLElem3.addChild(new XMLElem("WEDENDTME", "WEDENDTME"));
/* 137 */     xMLElem3.addChild(new XMLElem("THURSSTRTTME", "THURSSTRTTME"));
/* 138 */     xMLElem3.addChild(new XMLElem("THURSENDTME", "THURSENDTME"));
/* 139 */     xMLElem3.addChild(new XMLElem("FRISTRTTME", "FRISTRTTME"));
/* 140 */     xMLElem3.addChild(new XMLElem("FRIENDTME", "FRIENDTME"));
/* 141 */     xMLElem3.addChild(new XMLElem("SATSTRTTME", "SATSTRTTME"));
/* 142 */     xMLElem3.addChild(new XMLElem("SATENDTME", "SATENDTME"));
/* 143 */     xMLElem3.addChild(new XMLElem("SUNSTRTTME", "SUNSTRTTME"));
/* 144 */     xMLElem3.addChild(new XMLElem("SUNENDTME", "SUNENDTME"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 152 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 159 */     return "ADSSVCLEV";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 166 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 174 */     return "SVCLEV_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 183 */     return "$Revision: 1.15 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSSVCLEVABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */