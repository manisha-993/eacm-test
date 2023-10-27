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
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
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
/*     */ public class XMLMODELTAXElem
/*     */   extends XMLElem
/*     */ {
/* 135 */   public static Map map = new HashMap<>();
/*     */   static {
/* 137 */     map.put("MAIN", "b");
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
/*     */   public XMLMODELTAXElem(String paramString1, String paramString2, int paramInt) {
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
/* 201 */     EntityItem entityItem4 = entityItem3.getUpLink().get(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     NodeList nodeList = paramElement.getChildNodes();
/* 207 */     int i = nodeList.getLength();
/* 208 */     boolean bool = false;
/*     */     
/* 210 */     String str = PokUtils.getAttributeValue(entityItem4, "COFCAT", "", "");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     for (byte b1 = 0; b1 < i && 
/* 217 */       str.equals("Hardware"); b1++) {
/*     */ 
/*     */       
/* 220 */       String str1 = nodeList.item(b1).getNodeName();
/* 221 */       if ("COUNTRYLIST".equals(str1)) {
/*     */         
/* 223 */         Node node1 = nodeList.item(b1);
/* 224 */         NodeList nodeList1 = node1.getChildNodes();
/* 225 */         for (byte b = 0; b < nodeList1.getLength(); b++) {
/*     */           
/* 227 */           if (nodeList1.item(b).getNodeName().equals("COUNTRYELEMENT")) {
/* 228 */             Node node2 = nodeList1.item(b);
/* 229 */             NodeList nodeList2 = node2.getChildNodes();
/*     */             
/* 231 */             for (byte b3 = 0; b3 < nodeList2.getLength(); b3++) {
/* 232 */               if (nodeList2.item(b3).getNodeName().equals("COUNTRY_FC")) {
/* 233 */                 String str2 = nodeList2.item(b3).getFirstChild().getNodeValue();
/* 234 */                 if ("1464".equals(str2)) {
/* 235 */                   bool = true;
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
/* 246 */     Node node = getContentNode(paramDocument, paramDiffEntity, paramElement, paramStringBuffer);
/*     */     
/* 248 */     if (node != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 255 */       if (bool)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 260 */         if (map.get(node.getNodeValue()) != null) {
/* 261 */           node = paramDocument.createTextNode(map.get(node.getNodeValue()).toString());
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 266 */       element.appendChild(node);
/*     */     } 
/*     */ 
/*     */     
/* 270 */     for (byte b2 = 0; b2 < this.childVct.size(); b2++) {
/* 271 */       XMLElem xMLElem = this.childVct.elementAt(b2);
/* 272 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */ 
/*     */     
/* 276 */     if (!element.hasChildNodes())
/*     */     {
/*     */       
/* 279 */       element.appendChild(paramDocument.createTextNode("@@"));
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
/*     */   public EntityItem getRoot(Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/* 296 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLMODELTAXElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */