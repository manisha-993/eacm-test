/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
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
/*     */ public class SAPLGEOFilteredElem
/*     */   extends SAPLElem
/*     */ {
/*     */   private String filterAttr;
/*     */   private String filterValue;
/*     */   
/*     */   public SAPLGEOFilteredElem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt) {
/*  54 */     super(paramString1, paramString2, paramString3, false, paramInt);
/*  55 */     this.filterAttr = paramString4;
/*  56 */     this.filterValue = paramString5;
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
/*     */   public SAPLGEOFilteredElem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/*  70 */     super(paramString1, paramString2, paramString3, false);
/*  71 */     this.filterAttr = paramString4;
/*  72 */     this.filterValue = paramString5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getEntities(EntityGroup paramEntityGroup) {
/*  82 */     return PokUtils.getEntitiesWithMatchedAttr(paramEntityGroup, this.filterAttr, this.filterValue);
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
/*     */   protected void addGEOElements(Vector paramVector, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 102 */     if (paramVector != null && paramVector.size() > 0) {
/* 103 */       Vector<EntityItem> vector = PokUtils.getEntitiesWithMatchedAttr(paramVector, this.filterAttr, this.filterValue);
/*     */       
/* 105 */       if (vector.size() == 0) {
/* 106 */         paramStringBuffer.append("SAPLGEOFilteredElem: No " + this.etype + " items found for node:" + this.nodeName + " filterattr:" + this.filterAttr + " filterval:" + this.filterValue + NEWLINE);
/*     */         
/* 108 */         Element element = paramDocument.createElement(this.nodeName);
/* 109 */         addXMLAttrs(element);
/* 110 */         paramElement.appendChild(element);
/* 111 */         if (this.attrCode != null) {
/* 112 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */         }
/*     */       } 
/* 115 */       for (byte b = 0; b < vector.size(); b++) {
/* 116 */         EntityItem entityItem = vector.elementAt(b);
/* 117 */         Element element = paramDocument.createElement(this.nodeName);
/* 118 */         addXMLAttrs(element);
/* 119 */         paramElement.appendChild(element);
/* 120 */         Node node = getContentNode(paramDocument, entityItem, paramElement);
/* 121 */         if (node != null) {
/* 122 */           element.appendChild(node);
/*     */         }
/*     */       } 
/* 125 */       vector.clear();
/*     */     } else {
/* 127 */       paramStringBuffer.append("SAPLGEOFilteredElem: No " + this.etype + " passed in for node:" + this.nodeName + NEWLINE);
/* 128 */       Element element = paramDocument.createElement(this.nodeName);
/* 129 */       addXMLAttrs(element);
/* 130 */       paramElement.appendChild(element);
/* 131 */       if (this.attrCode != null)
/* 132 */         element.appendChild(paramDocument.createTextNode("@@")); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLGEOFilteredElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */