/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import COM.ibm.eannounce.objects.EntityList;
/*    */ import COM.ibm.opicmpdh.middleware.Database;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*    */ import java.io.IOException;
/*    */ import java.rmi.RemoteException;
/*    */ import java.sql.SQLException;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SAPLItemElem
/*    */   extends SAPLElem
/*    */ {
/*    */   public SAPLItemElem(String paramString1, String paramString2, String paramString3) {
/* 38 */     super(paramString1, paramString2, paramString3, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addElements(EntityItem paramEntityItem, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 58 */     Element element = paramDocument.createElement(this.nodeName);
/* 59 */     addXMLAttrs(element);
/* 60 */     paramElement.appendChild(element);
/*    */     
/* 62 */     if (paramEntityItem != null) {
/* 63 */       Node node = getContentNode(paramDocument, paramEntityItem, paramElement);
/* 64 */       if (node != null) {
/* 65 */         element.appendChild(node);
/*    */       }
/*    */     } else {
/* 68 */       paramStringBuffer.append("SAPLItemElem: EntityItem was null for " + this.etype + " for node:" + this.nodeName + NEWLINE);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 92 */     addElements((EntityItem)null, paramDocument, paramElement, paramStringBuffer);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLItemElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */