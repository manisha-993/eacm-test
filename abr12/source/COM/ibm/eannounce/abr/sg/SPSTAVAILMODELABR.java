/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.LinkActionItem;
/*     */ import COM.ibm.eannounce.objects.WorkflowException;
/*     */ import COM.ibm.opicmpdh.middleware.LockException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSTAVAILMODELABR
/*     */   extends SPSTABR
/*     */ {
/* 157 */   private static Hashtable SPACTERM_TAB = new Hashtable<>();
/*     */   
/*     */   private static final int TAXGRP_ENTITYID = 3;
/*     */ 
/*     */   
/*     */   static {
/* 163 */     SPACTERM_TAB.put("1", new Integer(1265461));
/* 164 */     SPACTERM_TAB.put("2", new Integer(1265460));
/* 165 */     SPACTERM_TAB.put("3", new Integer(1265459));
/* 166 */     SPACTERM_TAB.put("4", new Integer(1265458));
/* 167 */     SPACTERM_TAB.put("5", new Integer(1265457));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateData(SPSTABRSTATUS paramSPSTABRSTATUS, Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 173 */     super.validateData(paramSPSTABRSTATUS, paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkRelatedEntities(Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 214 */     NodeList nodeList = paramElement.getElementsByTagName("MODELLIST");
/*     */     
/* 216 */     this.spstAbr.verifyChildNodes(paramElement, "MODELLIST", "MODELELEMENT", 1);
/*     */     
/* 218 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 219 */       Node node = nodeList.item(b);
/* 220 */       if (node.getNodeType() == 1) {
/*     */ 
/*     */         
/* 223 */         NodeList nodeList1 = node.getChildNodes();
/* 224 */         for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 225 */           Node node1 = nodeList1.item(b1);
/* 226 */           if (node1.getNodeType() == 1) {
/*     */ 
/*     */             
/* 229 */             Element element = (Element)node1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 243 */             checkSingleFlagAttr(element, "MACHTYPE", "MACHTYPEATR", "L");
/* 244 */             checkSingleFlagAttr(element, "COFSUBCAT", "COFSUBCAT", "S");
/* 245 */             checkSingleFlagAttr(element, "BHACCTASGNGRP", "BHACCTASGNGRP", "S");
/* 246 */             checkSingleFlagAttr(element, "ACCTASGNGRP", "ACCTASGNGRP", "S");
/* 247 */             checkSingleFlagAttr(element, "PRFTCTR", "PRFTCTR", "C");
/* 248 */             checkSingleFlagAttr(element, "SVCTYPEINDC", "SVCTYPEINDC", "L");
/* 249 */             checkSingleFlagAttr(element, "BHPRODHIERCD", "BHPRODHIERCD", "L");
/* 250 */             checkSingleFlagAttr(element, "AMRTZTNLNGTH", "AMRTZTNLNGTH", "S");
/* 251 */             checkSingleFlagAttr(element, "AMRTZTNSTRT", "AMRTZTNSTRT", "S");
/* 252 */             checkSingleFlagAttr(element, "SVCLEVCD", "SVCLEVCD", "S");
/*     */ 
/*     */             
/* 255 */             NodeList nodeList2 = element.getElementsByTagName("SEOELEMENT");
/* 256 */             for (byte b2 = 0; b2 < nodeList2.getLength(); b2++) {
/* 257 */               Node node2 = nodeList2.item(b2);
/* 258 */               if (node2.getNodeType() == 1) {
/*     */ 
/*     */                 
/* 261 */                 Element element1 = (Element)node2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 272 */                 checkSingleFlagAttr(element1, "SEOBHPRODHIERCD", "BHPRODHIERCD", "L");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 279 */                 this.spstAbr.verifyChildNodes(element1, "AUDLIST", "AUDIENCE", 1);
/* 280 */                 checkMultiFlagAttr(element1, "AUDLIST", "AUDIENCE", "AUDIEN");
/* 281 */                 this.spstAbr.verifyChildNodes(element1, "COUNTRYLIST", "COUNTRY", 1);
/* 282 */                 checkMultiFlagAttr(element1, "COUNTRYLIST", "COUNTRY", "COUNTRYLIST");
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 287 */             this.spstAbr.verifyChildNodes(element, "AUDLIST", "AUDIENCE", 1);
/* 288 */             checkMultiFlagAttr(element, "AUDLIST", "AUDIENCE", "AUDIEN");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateEntities(Vector paramVector, Hashtable paramHashtable, Element paramElement, StringBuffer paramStringBuffer) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/* 313 */     EntityItem entityItem = null;
/*     */     
/* 315 */     NodeList nodeList = paramElement.getElementsByTagName("MODELLIST");
/*     */     
/* 317 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 318 */       Node node = nodeList.item(b);
/* 319 */       if (node.getNodeType() == 1) {
/*     */ 
/*     */         
/* 322 */         NodeList nodeList1 = node.getChildNodes();
/* 323 */         byte b1 = 0; while (true) { if (b1 < nodeList1.getLength()) {
/* 324 */             Node node1 = nodeList1.item(b1);
/* 325 */             if (node1.getNodeType() == 1) {
/*     */ 
/*     */               
/* 328 */               Element element = (Element)node1;
/* 329 */               String str1 = getSingleFlag(element, "MACHTYPE");
/* 330 */               String str2 = this.spstAbr.getNodeValue(element, "MODELATR", false);
/* 331 */               String str3 = this.spstAbr.getNodeValue(element, "MKTGNAME", false);
/* 332 */               String str4 = getSingleFlag(element, "BHACCTASGNGRP");
/* 333 */               String str5 = this.spstAbr.getNodeValue(element, "ACCTASGNGRP", false);
/* 334 */               String str6 = getSingleFlag(element, "PRFTCTR");
/* 335 */               String str7 = this.spstAbr.getNodeValue(element, "ANNDATE", false);
/* 336 */               String str8 = this.spstAbr.getNodeValue(element, "WITHDRAWDATE", false);
/* 337 */               System.gc();
/* 338 */               long l = Runtime.getRuntime().freeMemory();
/* 339 */               this.spstAbr.addDebug("memory before add to map:" + l);
/* 340 */               EntityItem entityItem1 = searchForModel(str1, str2);
/*     */               
/* 342 */               boolean bool = false;
/* 343 */               if (entityItem1 != null)
/* 344 */               { bool = true;
/* 345 */                 if (!isValidNewAvail(entityItem1, (Vector)paramHashtable.get("COUNTRYLIST"), "146"))
/* 346 */                 { this.spstAbr.addError("ERROR_MODEL", new Object[] { str1, str2 }); }
/*     */                 else
/*     */                 
/* 349 */                 { this.spstAbr.addOutput("LINK_MODEL", (Object[])new String[] { str1, str2, (String)paramHashtable.get("COMNAME"), paramStringBuffer.toString() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 472 */                   LinkActionItem linkActionItem = new LinkActionItem(null, this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "LINKAVAILMODEL");
/*     */                   
/* 474 */                   entityItem = createOrRefAvail(entityItem1, entityItem, "CRPEERAVAIL", paramVector, paramHashtable, linkActionItem);
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 479 */                   doReferenceModelTaxCatg(entityItem1, (Vector)paramHashtable.get("COUNTRYLIST"));
/*     */                   
/* 481 */                   NodeList nodeList2 = element.getElementsByTagName("SEOELEMENT");
/* 482 */                   StringBuffer stringBuffer1 = new StringBuffer();
/* 483 */                   StringBuffer stringBuffer2 = new StringBuffer();
/* 484 */                   boolean bool1 = false; }  } else { Vector<String> vector = new Vector(); Hashtable<Object, Object> hashtable = new Hashtable<>(); vector.addElement("MACHTYPEATR"); hashtable.put("MACHTYPEATR", str1); vector.addElement("MODELATR"); hashtable.put("MODELATR", str2); vector.addElement("COFCAT"); hashtable.put("COFCAT", "102"); vector.addElement("COFSUBCAT"); hashtable.put("COFSUBCAT", "194"); vector.addElement("COFGRP"); hashtable.put("COFGRP", "150"); vector.addElement("COFSUBGRP"); hashtable.put("COFSUBGRP", "010"); vector.addElement("MKTGNAME"); if (str3 != null && str3.length() > 254) { this.spstAbr.addError("MKTGNAME's length > 254, failed this model: " + str3); } else { hashtable.put("MKTGNAME", str3); vector.addElement("INVNAME"); hashtable.put("INVNAME", this.spstAbr.getNodeValue(element, "INVNAME", false)); vector.addElement("BHACCTASGNGRP"); hashtable.put("BHACCTASGNGRP", str4); vector.addElement("ACCTASGNGRP"); hashtable.put("ACCTASGNGRP", str5); vector.addElement("ACCTGMACHTYPE"); hashtable.put("ACCTGMACHTYPE", this.spstAbr.getNodeValue(element, "ACCTGMACHTYPE", false)); vector.addElement("PRFTCTR"); hashtable.put("PRFTCTR", str6); vector.addElement("SVCTYPEINDC"); hashtable.put("SVCTYPEINDC", getSingleFlag(element, "SVCTYPEINDC")); vector.addElement("SAPASSORTMODULE"); hashtable.put("SAPASSORTMODULE", this.spstAbr.getNodeValue(element, "SAPASSORTMODULE", false)); vector.addElement("BHPRODHIERCD"); hashtable.put("BHPRODHIERCD", getSingleFlag(element, "BHPRODHIERCD")); vector.addElement("AMRTZTNLNGTH"); hashtable.put("AMRTZTNLNGTH", getSingleFlag(element, "AMRTZTNLNGTH")); vector.addElement("AMRTZTNSTRT"); hashtable.put("AMRTZTNSTRT", getSingleFlag(element, "AMRTZTNSTRT")); vector.addElement("SVCLEVCD"); hashtable.put("SVCLEVCD", getSingleFlag(element, "SVCLEVCD")); vector.addElement("SDFCD"); hashtable.put("SDFCD", this.spstAbr.getNodeValue(element, "SDFCD", false)); vector.addElement("ANNDATE"); hashtable.put("ANNDATE", str7); vector.addElement("GENAVAILDATE"); hashtable.put("GENAVAILDATE", this.spstAbr.getNodeValue(element, "GENAVAILDATE", false)); vector.addElement("WITHDRAWDATE"); hashtable.put("WITHDRAWDATE", str8); vector.addElement("WTHDRWEFFCTVDATE"); hashtable.put("WTHDRWEFFCTVDATE", this.spstAbr.getNodeValue(element, "WTHDRWEFFCTVDATE", false)); vector.addElement("AUDIEN"); hashtable.put("AUDIEN", generateMultiFlags(element, "AUDLIST", "AUDIENCE", new StringBuffer())); entityItem1 = createMODEL(this.spstAbr, vector, hashtable); String str = this.spstAbr.getNodeValue(element, "SPACTERM", false); int i = getSVCEntityId(str); if (i != -1) { link("MODELSVC", "MODEL", entityItem1.getEntityID(), "SVC", i); } else { this.spstAbr.addDebug("SPACTERM element does not exist or invalid value for it, just skip it"); }  link("MODELTAXGRP", "MODEL", entityItem1.getEntityID(), "TAXGRP", 3); this.spstAbr.setFlagValue(this.spstAbr.getProfile(), "COFGRP", "010", entityItem1); this.spstAbr.setFlagValue(this.spstAbr.getProfile(), "COFSUBCAT", getSingleFlag(element, "COFSUBCAT"), entityItem1); this.spstAbr.setTextValue(this.spstAbr.getProfile(), "MODMKTGDESC", str3, entityItem1); this.spstAbr.setTextValue(this.spstAbr.getProfile(), "INTERNALNAME", str3, entityItem1); this.spstAbr.updatePDH(this.spstAbr.getProfile()); vector.clear(); vector = null; hashtable.clear(); hashtable = null; LinkActionItem linkActionItem = new LinkActionItem(null, this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "LINKAVAILMODEL"); entityItem = createOrRefAvail(entityItem1, entityItem, "CRPEERAVAIL", paramVector, paramHashtable, linkActionItem); doReferenceModelTaxCatg(entityItem1, (Vector)paramHashtable.get("COUNTRYLIST")); NodeList nodeList2 = element.getElementsByTagName("SEOELEMENT"); StringBuffer stringBuffer1 = new StringBuffer(); StringBuffer stringBuffer2 = new StringBuffer(); boolean bool1 = false; }
/*     */                  }
/*     */             
/*     */             } 
/*     */           } else {
/*     */             break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           b1++; }
/*     */       
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 625 */     entityItem = null;
/*     */   }
/*     */   private int getSVCEntityId(String paramString) {
/* 628 */     int i = -1;
/* 629 */     if (paramString != null) {
/* 630 */       Object object = SPACTERM_TAB.get(paramString);
/* 631 */       if (object != null) {
/* 632 */         i = ((Integer)object).intValue();
/*     */       }
/*     */     } 
/* 635 */     return i;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 639 */     return "1.0";
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 643 */     return "Service Pacs from SPST" + ((this.spstAbr.getReturnCode() == 0) ? " created" : " - MODEL, SEO");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 648 */     return "ServicePac Created â MODEL, SEO";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SPSTAVAILMODELABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */