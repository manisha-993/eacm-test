/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAPLGEOAvailElem
/*     */   extends SAPLElem
/*     */ {
/*     */   public SAPLGEOAvailElem(String paramString1, String paramString2) {
/*  52 */     super(paramString1, "AVAIL", paramString2, false);
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
/*     */   protected void addGEOElements(Vector paramVector, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  73 */     if (paramVector != null && paramVector.size() > 0) {
/*  74 */       Vector<EntityItem> vector = new Vector(); byte b;
/*  75 */       for (b = 0; b < AVAIL_ORDER.length; b++) {
/*  76 */         vector = PokUtils.getEntitiesWithMatchedAttr(paramVector, "AVAILTYPE", AVAIL_ORDER[b]);
/*  77 */         if (vector.size() > 0) {
/*     */           break;
/*     */         }
/*  80 */         paramStringBuffer.append("SAPLGEOAvailElem: No AVAIL of AVAILTYPE[" + AVAIL_ORDER[b] + "] found for node:" + this.nodeName + NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/*  84 */       if (vector.size() == 0) {
/*  85 */         Element element = paramDocument.createElement(this.nodeName);
/*  86 */         addXMLAttrs(element);
/*  87 */         paramElement.appendChild(element);
/*  88 */         if (this.attrCode != null) {
/*  89 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */         }
/*     */       } 
/*  92 */       for (b = 0; b < vector.size(); b++) {
/*  93 */         EntityItem entityItem = vector.elementAt(b);
/*  94 */         Element element = paramDocument.createElement(this.nodeName);
/*  95 */         addXMLAttrs(element);
/*  96 */         paramElement.appendChild(element);
/*  97 */         Node node = getContentNode(paramDocument, entityItem, paramElement);
/*  98 */         if (node != null) {
/*  99 */           element.appendChild(node);
/*     */         }
/*     */       } 
/* 102 */       vector.clear();
/*     */     } else {
/* 104 */       paramStringBuffer.append("SAPLGEOAvailElem: No AVAIL passed in for node:" + this.nodeName + NEWLINE);
/* 105 */       Element element = paramDocument.createElement(this.nodeName);
/* 106 */       addXMLAttrs(element);
/* 107 */       paramElement.appendChild(element);
/* 108 */       if (this.attrCode != null)
/* 109 */         element.appendChild(paramDocument.createTextNode("@@")); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLGEOAvailElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */