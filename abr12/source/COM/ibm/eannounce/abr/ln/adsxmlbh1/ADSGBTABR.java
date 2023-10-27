/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
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
/*     */ public class ADSGBTABR
/*     */   extends XMLMQRoot
/*     */ {
/*  35 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("GBT_UPDATE"); static {
/*  36 */     XMLMAP.addChild((XMLElem)new XMLVMElem("GBT_UPDATE", "1"));
/*     */     
/*  38 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*  39 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/*  41 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/*  42 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */     
/*  44 */     XMLMAP.addChild(new XMLElem("SIEBELPRODLEV", "SIEBELPRODLEV"));
/*  45 */     XMLMAP.addChild(new XMLElem("SAPPRIMBRANDCD", "SAPPRIMBRANDCD"));
/*  46 */     XMLMAP.addChild(new XMLElem("SAPPRODFMLYCD", "SAPPRODFMLYCD"));
/*  47 */     XMLMAP.addChild(new XMLElem("WWOCPARNTS", "WWOCPARNTS"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));
/*  56 */     XMLMAP.addChild(new XMLElem("GBTDESC", "GBTDESC"));
/*  57 */     XMLMAP.addChild(new XMLElem("RECTYPE", "RECTYPE"));
/*  58 */     XMLMAP.addChild(new XMLElem("PRODTYPE", "PRODTYPE"));
/*  59 */     XMLMAP.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/*  60 */     XMLMAP.addChild(new XMLElem("DELDATE", "DELDATE"));
/*  61 */     XMLMAP.addChild(new XMLElem("MAPTOCD", "MAPTOCD"));
/*  62 */     XMLMAP.addChild(new XMLElem("OMBRANDCD", "OMBRANDCD"));
/*  63 */     XMLMAP.addChild(new XMLElem("OMPRODFMLYCD", "OMPRODFMLYCD"));
/*  64 */     XMLMAP.addChild(new XMLElem("BPDBBRANDCD", "BPDBBRANDCD"));
/*  65 */     XMLMAP.addChild(new XMLElem("MKTGRPTCD", "MKTGRPTCD"));
/*  66 */     XMLMAP.addChild(new XMLElem("IGSSHADOWLOB", "IGSSHADOWLOB"));
/*  67 */     XMLMAP.addChild(new XMLElem("TPRSSREVDIV", "TPRSSREVDIV"));
/*  68 */     XMLMAP.addChild(new XMLElem("INTERNALNOTES", "INTERNALNOTES"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/*  76 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/*  82 */     return "dummy";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/*  87 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/*  93 */     return "GBT_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 102 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSGBTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */