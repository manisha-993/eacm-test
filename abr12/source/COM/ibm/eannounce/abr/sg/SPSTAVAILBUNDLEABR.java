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
/*     */ public class SPSTAVAILBUNDLEABR
/*     */   extends SPSTABR
/*     */ {
/*     */   protected void checkRelatedEntities(Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 138 */     NodeList nodeList = paramElement.getElementsByTagName("BUNDLELIST");
/*     */     
/* 140 */     this.spstAbr.verifyChildNodes(paramElement, "BUNDLELIST", "BUNDLEELEMENT", 1);
/*     */     
/* 142 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 143 */       Node node = nodeList.item(b);
/* 144 */       if (node.getNodeType() == 1) {
/*     */ 
/*     */         
/* 147 */         NodeList nodeList1 = node.getChildNodes();
/* 148 */         for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 149 */           Node node1 = nodeList1.item(b1);
/* 150 */           if (node1.getNodeType() == 1) {
/*     */ 
/*     */             
/* 153 */             Element element = (Element)node1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 164 */             checkSingleFlagAttr(element, "BHACCTASGNGRP", "BHACCTASGNGRP", "S");
/* 165 */             checkSingleFlagAttr(element, "ACCTASGNGRP", "ACCTASGNGRP", "S");
/* 166 */             checkSingleFlagAttr(element, "PRFTCTR", "PRFTCTR", "C");
/* 167 */             checkSingleFlagAttr(element, "SEOBHPRODHIERCD", "BHPRODHIERCD", "L");
/*     */ 
/*     */             
/* 170 */             this.spstAbr.verifyChildNodes(element, "SEOLIST", "SEOID", 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 178 */             this.spstAbr.verifyChildNodes(element, "AUDLIST", "AUDIENCE", 1);
/* 179 */             checkMultiFlagAttr(element, "AUDLIST", "AUDIENCE", "AUDIEN");
/*     */ 
/*     */             
/* 182 */             this.spstAbr.verifyChildNodes(element, "COUNTRYLIST", "COUNTRY", 1);
/* 183 */             checkMultiFlagAttr(element, "COUNTRYLIST", "COUNTRY", "COUNTRYLIST");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public String getVersion() {
/* 190 */     return "1.0";
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 194 */     return "Service Pacs from SPST" + ((this.spstAbr.getReturnCode() == 0) ? " created" : " - LSEOBUNDLE");
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 198 */     return "ServicePac Created - LSEOBUNDLE";
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
/*     */   protected void generateEntities(Vector paramVector, Hashtable paramHashtable, Element paramElement, StringBuffer paramStringBuffer) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/* 224 */     EntityItem entityItem = null;
/*     */     
/* 226 */     NodeList nodeList = paramElement.getElementsByTagName("BUNDLELIST");
/*     */     
/* 228 */     for (byte b = 0; b < nodeList.getLength(); b++) {
/* 229 */       Node node = nodeList.item(b);
/* 230 */       if (node.getNodeType() == 1) {
/*     */ 
/*     */         
/* 233 */         NodeList nodeList1 = node.getChildNodes();
/*     */         
/* 235 */         byte b1 = 0; while (true) { if (b1 < nodeList1.getLength()) {
/* 236 */             Node node1 = nodeList1.item(b1);
/* 237 */             if (node1.getNodeType() == 1) {
/*     */ 
/*     */               
/* 240 */               Element element = (Element)node1;
/* 241 */               String str = this.spstAbr.getNodeValue(element, "BDLSEOID", false);
/* 242 */               EntityItem entityItem1 = searchForWWSEO(str);
/* 243 */               EntityItem entityItem2 = searchForLSEO(str);
/* 244 */               EntityItem entityItem3 = searchForLSEOBUNDLE(str);
/* 245 */               StringBuffer stringBuffer = new StringBuffer();
/*     */               
/* 247 */               if (entityItem1 != null)
/* 248 */               { this.spstAbr.addError("ERROR_LSEOBUNDLE_EXIST_WWSEO", (Object[])new String[] { str, str });
/*     */                  }
/*     */               
/* 251 */               else if (entityItem2 != null)
/* 252 */               { this.spstAbr.addError("ERROR_LSEOBUNDLE_EXIST_LSEO", (Object[])new String[] { str, str });
/*     */                  }
/*     */               
/* 255 */               else if (entityItem3 != null)
/* 256 */               { if (!isValidNewAvail(entityItem3, (Vector)paramHashtable.get("COUNTRYLIST"), "146"))
/* 257 */                 { this.spstAbr.addError("ERROR_LSEOBUNDLE_EXIST", (Object[])new String[] { str }); }
/*     */                 else
/*     */                 
/* 260 */                 { this.spstAbr.addOutput("LINK_BUNDLE", (Object[])new String[] { str, (String)paramHashtable.get("COMNAME"), paramStringBuffer.toString() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 333 */                   LinkActionItem linkActionItem = new LinkActionItem(null, this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "LINKAVAILLSEOBUNDLE");
/* 334 */                   entityItem = createOrRefAvail(entityItem3, entityItem, "CRPEERAVAIL10", paramVector, paramHashtable, linkActionItem);
/* 335 */                   NodeList nodeList2 = element.getElementsByTagName("SEOLIST");
/* 336 */                   StringBuffer stringBuffer1 = new StringBuffer();
/* 337 */                   boolean bool = false; }  } else { Vector<String> vector = new Vector(); Hashtable<Object, Object> hashtable = new Hashtable<>(); vector.addElement("SEOID"); hashtable.put("SEOID", str); vector.addElement("COMNAME"); hashtable.put("COMNAME", str); vector.addElement("BUNDLMKTGDESC"); String str1 = this.spstAbr.getNodeValue(element, "BUNDLMKTGDESC", false); if (str1 != null && str1.length() > 254) { this.spstAbr.addError("BUNDLMKTGDESC's length > 254, failed this BUNDLE: " + str1); } else { hashtable.put("BUNDLMKTGDESC", "LSEOBUNDLE MARKET temp"); vector.addElement("BHACCTASGNGRP"); hashtable.put("BHACCTASGNGRP", getSingleFlag(element, "BHACCTASGNGRP")); vector.addElement("ACCTASGNGRP"); hashtable.put("ACCTASGNGRP", getSingleFlag(element, "ACCTASGNGRP")); vector.addElement("PRFTCTR"); hashtable.put("PRFTCTR", getSingleFlag(element, "PRFTCTR")); vector.addElement("SAPASSORTMODULE"); hashtable.put("SAPASSORTMODULE", this.spstAbr.getNodeValue(element, "SAPASSORTMODULE", false)); vector.addElement("PRCFILENAM"); hashtable.put("PRCFILENAM", this.spstAbr.getNodeValue(element, "PRCFILENAM", false)); vector.addElement("BUNDLPUBDATEMTRGT"); hashtable.put("BUNDLPUBDATEMTRGT", this.spstAbr.getNodeValue(element, "BUNDLPUBDATEMTRGT", false)); vector.addElement("BUNDLUNPUBDATEMTRGT"); hashtable.put("BUNDLUNPUBDATEMTRGT", this.spstAbr.getNodeValue(element, "BUNDLUNPUBDATEMTRGT", false)); vector.addElement("PRODHIERCD"); hashtable.put("PRODHIERCD", this.spstAbr.getNodeValue(element, "PRODHIERCD", false)); vector.addElement("BHPRODHIERCD"); hashtable.put("BHPRODHIERCD", getSingleFlag(element, "SEOBHPRODHIERCD")); vector.addElement("AUDIEN"); hashtable.put("AUDIEN", generateMultiFlags(element, "AUDLIST", "AUDIENCE", new StringBuffer())); Vector vector1 = generateMultiFlags(element, "COUNTRYLIST", "COUNTRY", stringBuffer); vector.addElement("COUNTRYLIST"); hashtable.put("COUNTRYLIST", vector1); entityItem3 = createLSEOBUNDLE(this.spstAbr, vector, hashtable); this.spstAbr.setTextValue(this.spstAbr.getProfile(), "BUNDLMKTGDESC", str1, entityItem3); this.spstAbr.updatePDH(this.spstAbr.getProfile()); vector.clear(); hashtable.clear(); vector = null; hashtable = null; LinkActionItem linkActionItem = new LinkActionItem(null, this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "LINKAVAILLSEOBUNDLE"); entityItem = createOrRefAvail(entityItem3, entityItem, "CRPEERAVAIL10", paramVector, paramHashtable, linkActionItem); NodeList nodeList2 = element.getElementsByTagName("SEOLIST"); StringBuffer stringBuffer1 = new StringBuffer(); boolean bool = false; }
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
/* 380 */     entityItem = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SPSTAVAILBUNDLEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */