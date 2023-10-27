/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSMODELCONVERTABR
/*     */   extends XMLMQRoot
/*     */ {
/*  55 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODELCONVERT_UPDATE"); static {
/*  56 */     XMLMAP.addChild((XMLElem)new XMLVMElem("MODELCONVERT_UPDATE", "1"));
/*     */     
/*  58 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/*  59 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  60 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*  61 */     XMLMAP.addChild(new XMLElem("MODELUPGRADEENTITYTYPE", "ENTITYTYPE"));
/*  62 */     XMLMAP.addChild(new XMLElem("MODELUPGRADEENTITYID", "ENTITYID"));
/*  63 */     XMLMAP.addChild(new XMLElem("FROMMACHTYPE", "FROMMACHTYPE"));
/*  64 */     XMLMAP.addChild(new XMLElem("FROMMODEL", "FROMMODEL"));
/*  65 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("FROMMODELTYPE", "MODEL"));
/*     */     
/*  67 */     XMLSearchElem xMLSearchElem = new XMLSearchElem("FROMMODELENTITYID", "SRDMODEL4", "MODEL");
/*  68 */     xMLSearchElem.addSearchAttr("FROMMACHTYPE", "MACHTYPEATR");
/*  69 */     xMLSearchElem.addSearchAttr("FROMMODEL", "MODELATR");
/*  70 */     XMLMAP.addChild((XMLElem)xMLSearchElem);
/*     */     
/*  72 */     XMLMAP.addChild(new XMLElem("TOMACHTYPE", "TOMACHTYPE"));
/*  73 */     XMLMAP.addChild(new XMLElem("TOMODEL", "TOMODEL"));
/*  74 */     XMLMAP.addChild((XMLElem)new XMLFixedElem("TOMODELTYPE", "MODEL"));
/*     */     
/*  76 */     xMLSearchElem = new XMLSearchElem("TOMODELENTITYID", "SRDMODEL4", "MODEL");
/*  77 */     xMLSearchElem.addSearchAttr("TOMACHTYPE", "MACHTYPEATR");
/*  78 */     xMLSearchElem.addSearchAttr("TOMODEL", "MODELATR");
/*  79 */     XMLMAP.addChild((XMLElem)xMLSearchElem);
/*     */ 
/*     */     
/*  82 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/*  83 */     XMLMAP.addChild(new XMLElem("CUSTOMERSETUP", "INSTALL"));
/*  84 */     XMLMAP.addChild(new XMLElem("RETURNEDPARTSMES", "RETURNEDPARTS"));
/*  85 */     XMLMAP.addChild(new XMLElem("UPGRADETYPE", "UPGRADETYPE"));
/*     */ 
/*     */     
/*  88 */     XMLElem xMLElem = new XMLElem("AVAILABILITYLIST");
/*  89 */     XMLMAP.addChild(xMLElem);
/*  90 */     xMLElem.addChild((XMLElem)new XMLFCTRANSAVAILElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  98 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 104 */     return "ADSMODELCONVERT";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 109 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 115 */     return "MODELCONVERT_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 124 */     return "1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSMODELCONVERTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */