/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class XMLTAXElem
/*     */   extends XMLElem
/*     */ {
/* 135 */   public static Map map = new HashMap<>(); static {
/* 136 */     map.put("MSWH", "a");
/* 137 */     map.put("MAIN", "b");
/* 138 */     map.put("CABL", "c");
/* 139 */     map.put("PMG", "d");
/* 140 */     map.put("MANL", "e");
/* 141 */     map.put("MATM", "f");
/* 142 */     map.put("PLA", "g");
/* 143 */     map.put("ALP", "h");
/* 144 */     map.put("CSW", "i");
/* 145 */     map.put("EDUC", "j");
/* 146 */     map.put("MNPM", "k");
/* 147 */     map.put("***", "l");
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
/*     */   public XMLTAXElem(String paramString1, String paramString2, int paramInt) {
/* 160 */     super(paramString1, paramString2, paramInt);
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
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 187 */     Element element = paramDocument.createElement(this.nodeName);
/* 188 */     addXMLAttrs(element);
/* 189 */     if (paramElement == null) {
/* 190 */       paramDocument.appendChild(element);
/*     */     } else {
/* 192 */       paramElement.appendChild(element);
/*     */     } 
/*     */     
/* 195 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 196 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 197 */     EntityItem entityItem3 = entityItem1;
/* 198 */     if (paramDiffEntity.isDeleted()) {
/* 199 */       entityItem3 = entityItem2;
/*     */     }
/* 201 */     NodeList nodeList = paramElement.getChildNodes();
/* 202 */     int i = nodeList.getLength();
/* 203 */     boolean bool = false;
/* 204 */     for (byte b1 = 0; b1 < i; b1++) {
/*     */       
/* 206 */       String str = nodeList.item(b1).getNodeName();
/* 207 */       if ("COUNTRYLIST".equals(str)) {
/*     */         
/* 209 */         Node node1 = nodeList.item(b1);
/* 210 */         NodeList nodeList1 = node1.getChildNodes();
/* 211 */         for (byte b = 0; b < nodeList1.getLength(); b++) {
/*     */           
/* 213 */           if (nodeList1.item(b).getNodeName().equals("COUNTRYELEMENT")) {
/* 214 */             Node node2 = nodeList1.item(b);
/* 215 */             NodeList nodeList2 = node2.getChildNodes();
/*     */             
/* 217 */             for (byte b3 = 0; b3 < nodeList2.getLength(); b3++) {
/* 218 */               if (nodeList2.item(b3).getNodeName().equals("COUNTRY_FC")) {
/* 219 */                 String str1 = nodeList2.item(b3).getFirstChild().getNodeValue();
/* 220 */                 if ("1464".equals(str1)) {
/* 221 */                   bool = true;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 232 */     Node node = getContentNode(paramDocument, paramDiffEntity, paramElement, paramStringBuffer);
/*     */     
/* 234 */     if (node != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 241 */       if (bool)
/*     */       {
/* 243 */         if (node.getNodeValue() == null || "@@".equals(node.getNodeValue().trim())) {
/* 244 */           node = paramDocument.createTextNode("1");
/*     */         }
/* 246 */         else if (map.get(node.getNodeValue()) != null) {
/* 247 */           node = paramDocument.createTextNode(map.get(node.getNodeValue()).toString());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 252 */       element.appendChild(node);
/*     */     } 
/*     */ 
/*     */     
/* 256 */     for (byte b2 = 0; b2 < this.childVct.size(); b2++) {
/* 257 */       XMLElem xMLElem = this.childVct.elementAt(b2);
/* 258 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */ 
/*     */     
/* 262 */     if (!element.hasChildNodes())
/*     */     {
/*     */       
/* 265 */       element.appendChild(paramDocument.createTextNode("@@"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLTAXElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */