/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCtryElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLSearchElem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSMODELCONVERTABR
/*     */   extends XMLMQRoot
/*     */ {
/*  62 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODELCONVERT_UPDATE");
/*     */   static {
/*  64 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  65 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  66 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  67 */     XMLMAP.addChild(new XMLElem("MODELUPGRADEENTITYTYPE", "ENTITYTYPE"));
/*  68 */     XMLMAP.addChild(new XMLElem("MODELUPGRADEENTITYID", "ENTITYID"));
/*  69 */     XMLMAP.addChild(new XMLElem("FROMMACHTYPE", "FROMMACHTYPE"));
/*  70 */     XMLMAP.addChild(new XMLElem("FROMMODEL", "FROMMODEL"));
/*  71 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("FROMMODELTYPE", "MODEL"));
/*     */     
/*  73 */     XMLSearchElem xMLSearchElem = new XMLSearchElem("FROMMODELENTITYID", "SRDMODEL4", "MODEL");
/*  74 */     xMLSearchElem.addSearchAttr("FROMMACHTYPE", "MACHTYPEATR");
/*  75 */     xMLSearchElem.addSearchAttr("FROMMODEL", "MODELATR");
/*  76 */     XMLMAP.addChild((XMLElem)xMLSearchElem);
/*     */     
/*  78 */     XMLMAP.addChild(new XMLElem("TOMACHTYPE", "TOMACHTYPE"));
/*  79 */     XMLMAP.addChild(new XMLElem("TOMODEL", "TOMODEL"));
/*  80 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("TOMODELTYPE", "MODEL"));
/*     */     
/*  82 */     xMLSearchElem = new XMLSearchElem("TOMODELENTITYID", "SRDMODEL4", "MODEL");
/*  83 */     xMLSearchElem.addSearchAttr("TOMACHTYPE", "MACHTYPEATR");
/*  84 */     xMLSearchElem.addSearchAttr("TOMODEL", "MODELATR");
/*  85 */     XMLMAP.addChild((XMLElem)xMLSearchElem);
/*     */     
/*  87 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  88 */     XMLMAP.addChild(new XMLElem("CUSTOMERSETUP", "INSTALL"));
/*  89 */     XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES", "RETURNEDPARTS"));
/*  90 */     XMLMAP.addChild(new XMLElem("UPGRADETYPE", "UPGRADETYPE"));
/*  91 */     XMLElem xMLElem = new XMLElem("COUNTRYLIST");
/*  92 */     XMLMAP.addChild(xMLElem);
/*  93 */     xMLElem.addChild((XMLElem)new XMLCtryElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 100 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 106 */     return "ADSMODELCONVERT";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 111 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 117 */     return "MODELCONVERT";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 126 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSMODELCONVERTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */