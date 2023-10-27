/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import COM.ibm.opicmpdh.middleware.Database;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*    */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*    */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*    */ import java.io.IOException;
/*    */ import java.rmi.RemoteException;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Vector;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class XMLRELATElem
/*    */   extends XMLElem {
/*    */   public XMLRELATElem(String paramString1, String paramString2, String paramString3) {
/* 23 */     super(paramString1);
/* 24 */     this.attrcode1 = paramString2;
/* 25 */     this.attrcode2 = paramString3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String attrcode1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String attrcode2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 45 */     Element element = paramDocument.createElement(this.nodeName);
/* 46 */     addXMLAttrs(element);
/*    */     
/* 48 */     if (paramElement == null) {
/* 49 */       paramDocument.appendChild(element);
/*    */     } else {
/* 51 */       paramElement.appendChild(element);
/*    */     } 
/*    */     
/* 54 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 55 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 56 */     EntityItem entityItem3 = entityItem1;
/* 57 */     if (paramDiffEntity.isDeleted()) {
/* 58 */       entityItem3 = entityItem2;
/*    */     }
/*    */     
/* 61 */     if ("OFFERING_ID".equals(this.nodeName)) {
/*    */       
/* 63 */       EntityItem entityItem = getModelEntityFromTmf(entityItem3);
/* 64 */       ABRUtil.append(paramStringBuffer, "SVCMOD Entity" + entityItem.getKey() + NEWLINE);
/* 65 */       String str1 = PokUtils.getAttributeValue(entityItem, this.attrcode1, ", ", "@@", false);
/* 66 */       String str2 = PokUtils.getAttributeValue(entityItem, this.attrcode2, ", ", "@@", false);
/*    */ 
/*    */       
/* 69 */       element.appendChild(paramDocument.createTextNode(str1 + str2));
/* 70 */       paramElement.appendChild(element);
/*    */     } 
/*    */     
/* 73 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 74 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 75 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private EntityItem getModelEntityFromTmf(EntityItem paramEntityItem) {
/* 82 */     EntityItem entityItem = null;
/* 83 */     Vector<EntityItem> vector = paramEntityItem.getDownLink();
/* 84 */     if (vector != null && vector.size() > 0) {
/* 85 */       for (byte b = 0; b < vector.size(); b++) {
/* 86 */         EntityItem entityItem1 = vector.get(b);
/* 87 */         if ("SVCMOD".equals(entityItem1.getEntityType())) {
/* 88 */           entityItem = entityItem1;
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     }
/* 93 */     return entityItem;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLRELATElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */