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
/*     */ public class SPSTWDAVAILABR
/*     */   extends SPSTABR
/*     */ {
/*     */   public void validateData(SPSTABRSTATUS paramSPSTABRSTATUS, Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 111 */     super.validateData(paramSPSTABRSTATUS, paramElement);
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 115 */     return "1.0";
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 119 */     return "Last Order Avail is created and the MODELs, LSEOs & LSEOBUNDLEs are linked to this Avail.";
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 123 */     return "Last Order Avail Created - AVAIL";
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
/*     */   protected void checkRelatedEntities(Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 155 */     EntityItem entityItem = null;
/* 156 */     String str1 = (String)paramHashtable.get("EFFECTIVEDATE");
/* 157 */     NodeList nodeList1 = paramElement.getElementsByTagName("MODELLIST");
/*     */     
/* 159 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 160 */     StringBuffer stringBuffer2 = new StringBuffer();
/* 161 */     StringBuffer stringBuffer3 = new StringBuffer();
/* 162 */     for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 163 */       Node node = nodeList1.item(b1);
/* 164 */       if (node.getNodeType() == 1) {
/*     */ 
/*     */         
/* 167 */         NodeList nodeList = ((Element)node).getElementsByTagName("MODELELEMENT");
/* 168 */         for (byte b = 0; b < nodeList.getLength(); b++) {
/* 169 */           Node node1 = nodeList.item(b);
/* 170 */           if (node1.getNodeType() == 1) {
/*     */ 
/*     */             
/* 173 */             Element element = (Element)node1;
/* 174 */             String str5 = this.spstAbr.getNodeValue(element, "MACHTYPE", false);
/* 175 */             String str6 = this.spstAbr.getNodeValue(element, "MODELATR", false);
/*     */             
/* 177 */             EntityItem entityItem1 = searchForModel(str5, str6);
/*     */             
/* 179 */             if (entityItem1 != null) {
/* 180 */               if (isValidNewAvail(entityItem1, (Vector)paramHashtable.get("COUNTRYLIST"), "149")) {
/* 181 */                 LinkActionItem linkActionItem = new LinkActionItem(null, this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "LINKAVAILMODEL");
/*     */                 
/* 183 */                 entityItem = createOrRefAvail(entityItem1, entityItem, "CRPEERAVAIL", paramVector, paramHashtable, linkActionItem);
/*     */                 
/* 185 */                 this.spstAbr.setTextValue(this.spstAbr.getProfile(), "WTHDRWEFFCTVDATE", str1, entityItem1);
/* 186 */                 this.spstAbr.setTextValue(this.spstAbr.getProfile(), "WITHDRAWDATE", str1, entityItem1);
/* 187 */                 stringBuffer1.append(" " + str5 + str6 + " ");
/*     */               } else {
/* 189 */                 this.spstAbr.addError("ERROR_EXIST_AVAIL", (Object[])new String[] { "MODEL-" + str5 + str6 });
/*     */               } 
/*     */             } else {
/* 192 */               this.spstAbr.addError("ERROR_MODEL_NOT_FOUND", (Object[])new String[] { str5, str6 });
/*     */             } 
/* 194 */             entityItem1 = null;
/* 195 */             String str7 = this.spstAbr.getNodeValue(element, "SEOID", false);
/* 196 */             EntityItem entityItem2 = searchForLSEO(str7);
/* 197 */             if (entityItem2 != null) {
/* 198 */               if (isValidNewAvail(entityItem2, (Vector)paramHashtable.get("COUNTRYLIST"), "149")) {
/* 199 */                 LinkActionItem linkActionItem = new LinkActionItem(null, this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "LINKAVAILLSEO");
/* 200 */                 entityItem = createOrRefAvail(entityItem2, entityItem, "CRPEERAVAIL9", paramVector, paramHashtable, linkActionItem);
/* 201 */                 this.spstAbr.setTextValue(this.spstAbr.getProfile(), "LSEOUNPUBDATEMTRGT", str1, entityItem2);
/* 202 */                 stringBuffer2.append(" " + str7 + " ");
/*     */               } else {
/* 204 */                 this.spstAbr.addError("ERROR_EXIST_AVAIL", (Object[])new String[] { "LSEO-" + str7 });
/*     */               } 
/*     */             } else {
/* 207 */               this.spstAbr.addError("ERROR_LSEO_NOT_FOUND2", (Object[])new String[] { str7 });
/*     */             } 
/* 209 */             entityItem2 = null;
/*     */           } 
/*     */         } 
/*     */       } 
/* 213 */     }  NodeList nodeList2 = paramElement.getElementsByTagName("BUNDLELIST");
/* 214 */     for (byte b2 = 0; b2 < nodeList2.getLength(); b2++) {
/* 215 */       Node node = nodeList2.item(b2);
/* 216 */       if (node.getNodeType() == 1) {
/*     */ 
/*     */         
/* 219 */         NodeList nodeList = ((Element)node).getChildNodes();
/* 220 */         for (byte b = 0; b < nodeList.getLength(); b++) {
/* 221 */           Node node1 = nodeList.item(b);
/* 222 */           if (node1.getNodeType() == 1) {
/*     */ 
/*     */             
/* 225 */             Element element = (Element)node1;
/* 226 */             String str = this.spstAbr.getNodeValue(element, "BDLSEOID", false);
/* 227 */             EntityItem entityItem1 = searchForLSEOBUNDLE(str);
/* 228 */             if (entityItem1 != null) {
/* 229 */               if (isValidNewAvail(entityItem1, (Vector)paramHashtable.get("COUNTRYLIST"), "149")) {
/* 230 */                 LinkActionItem linkActionItem = new LinkActionItem(null, this.spstAbr.getDatabase(), this.spstAbr.getProfile(), "LINKAVAILLSEOBUNDLE");
/* 231 */                 entityItem = createOrRefAvail(entityItem1, entityItem, "CRPEERAVAIL10", paramVector, paramHashtable, linkActionItem);
/* 232 */                 this.spstAbr.setTextValue(this.spstAbr.getProfile(), "BUNDLUNPUBDATEMTRGT", str1, entityItem1);
/* 233 */                 stringBuffer3.append(" " + str + " ");
/*     */               } else {
/* 235 */                 this.spstAbr.addError("ERROR_EXIST_AVAIL", (Object[])new String[] { "LSEOBUNDLE-" + str });
/*     */               } 
/*     */             } else {
/* 238 */               this.spstAbr.addError("ERROR_LSEOBUNDLE_NOT_FOUND", (Object[])new String[] { str });
/*     */             } 
/* 240 */             entityItem1 = null;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 248 */     String str2 = stringBuffer1.toString();
/* 249 */     if (str2.length() > 0) {
/* 250 */       str2 = "<br />&nbsp;&nbsp;&nbsp;MODEL - " + str2;
/*     */     }
/* 252 */     String str3 = stringBuffer2.toString();
/* 253 */     if (str3.length() > 0) {
/* 254 */       str3 = "<br />&nbsp;&nbsp;&nbsp;LSEO - " + str3;
/*     */     }
/* 256 */     String str4 = stringBuffer3.toString();
/* 257 */     if (str4.length() > 0) {
/* 258 */       str4 = "<br />&nbsp;&nbsp;&nbsp;LSEOBUNDLE -" + str4;
/*     */     }
/* 260 */     if (entityItem != null) {
/* 261 */       this.spstAbr.addOutput("CREATED_WD_AVAIL", (Object[])new String[] { (String)paramHashtable
/* 262 */             .get("COMNAME"), paramStringBuffer.toString(), str2, str3, str4 });
/*     */     }
/* 264 */     entityItem = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SPSTWDAVAILABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */