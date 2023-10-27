/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLImageElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLImageElem() {
/*  63 */     super(null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  94 */     ResultSet resultSet = null;
/*  95 */     String str = "";
/*  96 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/*     */     try {
/*  98 */       resultSet = paramDatabase.callGBL7553(returnStatus, "blob", paramDiffEntity.getCurrentEntityItem().getProfile().getEnterprise(), paramDiffEntity.getCurrentEntityItem().getEntityType(), paramDiffEntity.getCurrentEntityItem().getEntityID(), " ", 1);
/*  99 */       while (resultSet.next()) {
/* 100 */         byte[] arrayOfByte = resultSet.getBytes("AttributeValue");
/* 101 */         BASE64Encoder bASE64Encoder = new BASE64Encoder();
/* 102 */         str = bASE64Encoder.encode(arrayOfByte);
/*     */       }
/*     */     
/* 105 */     } catch (Exception exception) {
/* 106 */       ABRUtil.append(paramStringBuffer, "XMLImageElem addElements()error: " + exception.getMessage());
/* 107 */       exception.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 110 */         if (resultSet != null) {
/* 111 */           resultSet.close();
/* 112 */           resultSet = null;
/*     */         } 
/* 114 */         paramDatabase.commit();
/* 115 */         paramDatabase.freeStatement();
/* 116 */         paramDatabase.isPending();
/* 117 */       } catch (Exception exception) {
/* 118 */         ABRUtil.append(paramStringBuffer, "XMLImageElem addElements(), unable to close. " + exception);
/*     */       } 
/*     */     } 
/* 121 */     createNodeSet(paramDocument, paramElement, str, paramStringBuffer);
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
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, String paramString, StringBuffer paramStringBuffer) {
/* 133 */     Element element = paramDocument.createElement("IMAGECONTENTS");
/*     */     
/* 135 */     element.appendChild(paramDocument.createTextNode("" + paramString));
/* 136 */     paramElement.appendChild(element);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLImageElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */