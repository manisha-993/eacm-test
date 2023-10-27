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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAPLGEOAnnElem
/*     */   extends SAPLElem
/*     */ {
/*     */   public SAPLGEOAnnElem(String paramString1, String paramString2) {
/*  64 */     super(paramString1, "ANNOUNCEMENT", paramString2, false);
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
/*     */   protected void addGEOElements(Vector paramVector, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  86 */     if (paramVector != null && paramVector.size() > 0) {
/*  87 */       Vector<EntityItem> vector = new Vector();
/*     */       
/*  89 */       for (byte b = 0; b < AVAIL_ORDER.length; b++) {
/*  90 */         Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(paramVector, "AVAILTYPE", AVAIL_ORDER[b]);
/*  91 */         if (vector1.size() > 0) {
/*  92 */           vector = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/*  93 */           vector1.clear();
/*  94 */           if (vector.size() > 0) {
/*  95 */             paramStringBuffer.append("SAPLGEOAnnElem: Found ANNOUNCEMENTs for AVAILTYPE " + AVAIL_ORDER[b] + NEWLINE);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 101 */       if (vector.size() > 0) {
/* 102 */         EntityItem entityItem = vector.firstElement();
/* 103 */         if (vector.size() > 1) {
/* 104 */           paramStringBuffer.append("SAPLGEOAnnElem: Error: more than one ANNOUNCEMENT found.  Using first one " + entityItem
/* 105 */               .getKey() + NEWLINE);
/*     */         }
/*     */         
/* 108 */         Element element = paramDocument.createElement(this.nodeName);
/* 109 */         addXMLAttrs(element);
/* 110 */         paramElement.appendChild(element);
/* 111 */         Node node = getContentNode(paramDocument, entityItem, paramElement);
/* 112 */         if (node != null) {
/* 113 */           element.appendChild(node);
/*     */         }
/* 115 */         vector.clear();
/*     */       } else {
/* 117 */         paramStringBuffer.append("SAPLGEOAnnElem: No ANNOUNCEMENTs found for node:" + this.nodeName + NEWLINE);
/*     */         
/* 119 */         Element element = paramDocument.createElement(this.nodeName);
/* 120 */         addXMLAttrs(element);
/* 121 */         paramElement.appendChild(element);
/* 122 */         if (this.attrCode != null) {
/* 123 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */         }
/*     */       } 
/*     */     } else {
/* 127 */       paramStringBuffer.append("SAPLGEOAnnElem: No AVAIL passed in for node:" + this.nodeName + NEWLINE);
/* 128 */       Element element = paramDocument.createElement(this.nodeName);
/* 129 */       addXMLAttrs(element);
/* 130 */       paramElement.appendChild(element);
/* 131 */       if (this.attrCode != null)
/* 132 */         element.appendChild(paramDocument.createTextNode("@@")); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLGEOAnnElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */