/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLFCTRANSAVAILElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLSearchElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLStatusElem;
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
/*     */ public class ADSMODELCONVERTABR
/*     */   extends XMLMQRoot
/*     */ {
/*  52 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODELCONVERT_UPDATE"); static {
/*  53 */     XMLMAP.addChild((XMLElem)new XMLVMElem("MODELCONVERT_UPDATE", "1"));
/*     */     
/*  55 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  56 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  57 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  58 */     XMLMAP.addChild(new XMLElem("MODELUPGRADEENTITYTYPE", "ENTITYTYPE"));
/*  59 */     XMLMAP.addChild(new XMLElem("MODELUPGRADEENTITYID", "ENTITYID"));
/*  60 */     XMLMAP.addChild(new XMLElem("FROMMACHTYPE", "FROMMACHTYPE"));
/*  61 */     XMLMAP.addChild(new XMLElem("FROMMODEL", "FROMMODEL"));
/*  62 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("FROMMODELTYPE", "MODEL"));
/*     */     
/*  64 */     XMLSearchElem xMLSearchElem = new XMLSearchElem("FROMMODELENTITYID", "SRDMODEL4", "MODEL");
/*  65 */     xMLSearchElem.addSearchAttr("FROMMACHTYPE", "MACHTYPEATR");
/*  66 */     xMLSearchElem.addSearchAttr("FROMMODEL", "MODELATR");
/*  67 */     XMLMAP.addChild((XMLElem)xMLSearchElem);
/*     */     
/*  69 */     XMLMAP.addChild(new XMLElem("TOMACHTYPE", "TOMACHTYPE"));
/*  70 */     XMLMAP.addChild(new XMLElem("TOMODEL", "TOMODEL"));
/*  71 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("TOMODELTYPE", "MODEL"));
/*     */     
/*  73 */     xMLSearchElem = new XMLSearchElem("TOMODELENTITYID", "SRDMODEL4", "MODEL");
/*  74 */     xMLSearchElem.addSearchAttr("TOMACHTYPE", "MACHTYPEATR");
/*  75 */     xMLSearchElem.addSearchAttr("TOMODEL", "MODELATR");
/*  76 */     XMLMAP.addChild((XMLElem)xMLSearchElem);
/*     */ 
/*     */     
/*  79 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/*  80 */     XMLMAP.addChild(new XMLElem("CUSTOMERSETUP", "INSTALL"));
/*  81 */     XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES", "RETURNEDPARTS"));
/*  82 */     XMLMAP.addChild(new XMLElem("UPGRADETYPE", "UPGRADETYPE"));
/*     */ 
/*     */     
/*  85 */     XMLElem xMLElem = new XMLElem("AVAILABILITYLIST");
/*  86 */     XMLMAP.addChild(xMLElem);
/*  87 */     xMLElem.addChild((XMLElem)new XMLFCTRANSAVAILElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  95 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 101 */     return "ADSMODELCONVERT";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 106 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 112 */     return "MODELCONVERT_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 121 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSMODELCONVERTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */