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
/*    */ public class SAPLEnterpriseElem
/*    */   extends SAPLElem
/*    */ {
/*    */   public SAPLEnterpriseElem(String paramString) {
/* 38 */     super(paramString, (String)null, (String)null, false);
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
/* 62 */     Element element = paramDocument.createElement(this.nodeName);
/* 63 */     addXMLAttrs(element);
/* 64 */     String str = paramEntityList.getProfile().getEnterprise();
/*    */     
/* 66 */     element.appendChild(paramDocument.createTextNode(str));
/* 67 */     paramElement.appendChild(element);
/*    */ 
/*    */     
/* 70 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 71 */       SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 72 */       sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLEnterpriseElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */