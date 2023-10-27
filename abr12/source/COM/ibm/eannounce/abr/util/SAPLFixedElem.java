/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
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
/*    */ 
/*    */ public class SAPLFixedElem
/*    */   extends SAPLElem
/*    */ {
/*    */   private String value;
/*    */   
/*    */   public SAPLFixedElem(String paramString1, String paramString2) {
/* 42 */     super(paramString1, (String)null, (String)null, false);
/* 43 */     this.value = paramString2;
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
/*    */   
/*    */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 67 */     Element element = paramDocument.createElement(this.nodeName);
/* 68 */     addXMLAttrs(element);
/*    */     
/* 70 */     element.appendChild(paramDocument.createTextNode(this.value));
/* 71 */     paramElement.appendChild(element);
/*    */ 
/*    */     
/* 74 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 75 */       SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 76 */       sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLFixedElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */