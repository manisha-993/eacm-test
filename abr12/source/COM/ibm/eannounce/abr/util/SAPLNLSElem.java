/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
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
/*     */ public class SAPLNLSElem
/*     */   extends SAPLElem
/*     */ {
/*     */   public SAPLNLSElem(String paramString) {
/*  66 */     super(paramString, (String)null, (String)null, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 102 */     Profile profile = paramEntityList.getProfile();
/*     */     
/* 104 */     profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 105 */     paramStringBuffer.append("SAPLNLSElem.addElements profile.getReadLanguage() " + profile.getReadLanguage() + NEWLINE);
/*     */     
/* 107 */     super.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, paramStringBuffer);
/*     */ 
/*     */     
/* 110 */     for (byte b = 0; b < profile.getReadLanguages().size(); b++) {
/* 111 */       NLSItem nLSItem = profile.getReadLanguage(b);
/* 112 */       if (nLSItem.getNLSID() != 1) {
/*     */ 
/*     */ 
/*     */         
/* 116 */         paramStringBuffer.append("SAPLNLSElem.addElements checking profile.getReadLanguage(" + b + ") " + nLSItem + NEWLINE);
/* 117 */         profile.setReadLanguage(b);
/*     */         
/* 119 */         if (hasNodeValueForNLS(paramEntityList, paramStringBuffer)) {
/*     */           
/* 121 */           super.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, paramStringBuffer);
/*     */         } else {
/* 123 */           paramStringBuffer.append("SAPLNLSElem.addElements profile.getReadLanguage(" + b + ") " + nLSItem + " does not have any node values" + NEWLINE);
/*     */         } 
/*     */       } 
/*     */     } 
/* 127 */     profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLNLSElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */