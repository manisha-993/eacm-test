/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
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
/*     */ public class ADSGBTABR
/*     */   extends XMLMQRoot
/*     */ {
/*  38 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("GBT_UPDATE"); static {
/*  39 */     XMLMAP.addChild((XMLElem)new XMLVMElem("GBT_UPDATE", "1"));
/*     */     
/*  41 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  42 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/*  44 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*  45 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */     
/*  47 */     XMLMAP.addChild(new XMLElem("SIEBELPRODLEV", "SIEBELPRODLEV"));
/*  48 */     XMLMAP.addChild(new XMLElem("SAPPRIMBRANDCD", "SAPPRIMBRANDCD"));
/*  49 */     XMLMAP.addChild(new XMLElem("SAPPRODFMLYCD", "SAPPRODFMLYCD"));
/*  50 */     XMLMAP.addChild(new XMLElem("LEVEL17", "LEVEL17"));
/*  51 */     XMLMAP.addChild(new XMLElem("WWOCPARNTS", "WWOCPARNTS"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));
/*  60 */     XMLMAP.addChild(new XMLElem("STATUS", "STATUS", 1));
/*  61 */     XMLMAP.addChild(new XMLElem("GBTDESC", "GBTDESC"));
/*  62 */     XMLMAP.addChild(new XMLElem("RECTYPE", "RECTYPE"));
/*  63 */     XMLMAP.addChild(new XMLElem("PRODTYPE", "PRODTYPE"));
/*  64 */     XMLMAP.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/*  65 */     XMLMAP.addChild(new XMLElem("DELDATE", "DELDATE"));
/*  66 */     XMLMAP.addChild(new XMLElem("MAPTOCD", "MAPTOCD"));
/*  67 */     XMLMAP.addChild(new XMLElem("OMBRANDCD", "OMBRANDCD"));
/*  68 */     XMLMAP.addChild(new XMLElem("OMPRODFMLYCD", "OMPRODFMLYCD"));
/*  69 */     XMLMAP.addChild(new XMLElem("BPDBBRANDCD", "BPDBBRANDCD"));
/*  70 */     XMLMAP.addChild(new XMLElem("MKTGRPTCD", "MKTGRPTCD"));
/*  71 */     XMLMAP.addChild(new XMLElem("IGSSHADOWLOB", "IGSSHADOWLOB"));
/*  72 */     XMLMAP.addChild(new XMLElem("TPRSSREVDIV", "TPRSSREVDIV"));
/*  73 */     XMLMAP.addChild(new XMLElem("INTERNALNOTES", "INTERNALNOTES"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  81 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  87 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/*  92 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/*  98 */     return "GBT_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 107 */     return "$Revision: 1.10 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSGBTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */