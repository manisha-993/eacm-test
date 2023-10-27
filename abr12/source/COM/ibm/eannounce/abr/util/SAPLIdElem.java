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
/*    */ public class SAPLIdElem
/*    */   extends SAPLElem
/*    */ {
/* 32 */   private String attrName = null;
/* 33 */   private String nodeVal = null;
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
/*    */   public SAPLIdElem(String paramString1, String paramString2, String paramString3) {
/* 47 */     super(paramString1, (String)null, (String)null, false);
/* 48 */     this.attrName = paramString3;
/* 49 */     this.nodeVal = paramString2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SAPLIdElem(String paramString) {
/* 60 */     super(paramString, (String)null, (String)null, false);
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
/* 84 */     Element element = paramDocument.createElement(this.nodeName);
/* 85 */     addXMLAttrs(element);
/* 86 */     String str = "" + paramEntityList.getParentEntityGroup().getEntityItem(0).getEntityID();
/* 87 */     if (this.attrName != null) {
/* 88 */       element.setAttribute(this.attrName, str);
/* 89 */       str = this.nodeVal;
/*    */     } 
/*    */     
/* 92 */     element.appendChild(paramDocument.createTextNode(str));
/* 93 */     paramElement.appendChild(element);
/*    */ 
/*    */     
/* 96 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 97 */       SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 98 */       sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLIdElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */