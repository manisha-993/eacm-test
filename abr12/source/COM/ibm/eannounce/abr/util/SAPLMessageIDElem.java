/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
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
/*     */ public class SAPLMessageIDElem
/*     */   extends SAPLElem
/*     */ {
/*     */   private static final int IDLEN = 10;
/*     */   
/*     */   public SAPLMessageIDElem() {
/*  35 */     super("ebi:MessageID", (String)null, (String)null, false);
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  59 */     Element element = paramDocument.createElement(this.nodeName);
/*  60 */     addXMLAttrs(element);
/*  61 */     String str = getIncrement(paramDatabase, paramEntityList, paramStringBuffer);
/*  62 */     element.appendChild(paramDocument.createTextNode(str));
/*  63 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/*  66 */     for (byte b = 0; b < this.childVct.size(); b++) {
/*  67 */       SAPLElem sAPLElem = this.childVct.elementAt(b);
/*  68 */       sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getIncrement(Database paramDatabase, EntityList paramEntityList, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  94 */     int i = paramDatabase.getNextEntityID(paramEntityList.getProfile(), "CHQSAPLHIGHID");
/*     */     
/*  96 */     StringBuffer stringBuffer = new StringBuffer("" + i);
/*  97 */     while (stringBuffer.length() < 10) {
/*  98 */       stringBuffer.insert(0, "0");
/*     */     }
/*     */     
/* 101 */     if (stringBuffer.length() > 10) {
/* 102 */       int j = stringBuffer.length() - 10;
/* 103 */       String str = stringBuffer.substring(j);
/* 104 */       paramStringBuffer.append("SAPLMessageIDElem:getIncrement: Warning: id len exceeded max[10]," + stringBuffer + " was truncated." + NEWLINE);
/*     */       
/* 106 */       stringBuffer = new StringBuffer(str);
/*     */     } 
/*     */     
/* 109 */     return "EA" + stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLMessageIDElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */