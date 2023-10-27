/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.RowSelectableTable;
/*     */ import COM.ibm.eannounce.objects.SearchActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
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
/*     */ public class SAPLCHQISOElem
/*     */   extends SAPLElem
/*     */ {
/*     */   public SAPLCHQISOElem(String paramString) {
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  92 */     String str = getCHQISO(paramDatabase, paramEntityList.getProfile());
/*     */ 
/*     */     
/*  95 */     Element element = paramDocument.createElement(this.nodeName);
/*  96 */     addXMLAttrs(element);
/*  97 */     element.appendChild(paramDocument.createTextNode(str));
/*  98 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 101 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 102 */       SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 103 */       sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
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
/*     */   protected boolean hasNodeValueForNLS(EntityList paramEntityList, StringBuffer paramStringBuffer) {
/* 115 */     return false;
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
/*     */   private String getCHQISO(Database paramDatabase, Profile paramProfile) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 145 */     String str1 = "SRDCHQISONLSIDMAP";
/* 146 */     NLSItem nLSItem = paramProfile.getReadLanguage();
/* 147 */     String str2 = "" + nLSItem.getNLSID();
/* 148 */     String str3 = "No CHQISONLSIDMAP match found for " + nLSItem + " (" + str2 + ")";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     SearchActionItem searchActionItem = new SearchActionItem(null, paramDatabase, paramProfile, str1);
/* 155 */     searchActionItem.setCheckLimit(false);
/*     */     
/* 157 */     RowSelectableTable rowSelectableTable = searchActionItem.getDynaSearchTable(paramDatabase);
/* 158 */     if (rowSelectableTable == null) {
/* 159 */       throw new IOException("Error using " + str1 + " search action.  No searchtable returned.");
/*     */     }
/*     */     
/* 162 */     String str4 = "CHQISONLSIDMAP:CHQNLSID";
/* 163 */     int i = rowSelectableTable.getRowIndex(str4);
/* 164 */     if (i < 0) {
/* 165 */       i = rowSelectableTable.getRowIndex(str4 + ":C");
/*     */     }
/* 167 */     if (i < 0) {
/* 168 */       i = rowSelectableTable.getRowIndex(str4 + ":R");
/*     */     }
/* 170 */     if (i != -1) {
/* 171 */       rowSelectableTable.put(i, 1, str2);
/*     */     } else {
/* 173 */       throw new IOException("Error can't find " + str4 + " in searchtable.");
/*     */     } 
/*     */     
/* 176 */     rowSelectableTable.commit(paramDatabase);
/*     */     
/* 178 */     EntityList entityList = searchActionItem.executeAction(paramDatabase, paramProfile);
/*     */     
/* 180 */     EntityGroup entityGroup = entityList.getEntityGroup("CHQISONLSIDMAP");
/*     */     
/* 182 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0)
/*     */     {
/*     */       
/* 185 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 186 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 187 */         if (str2.equals(PokUtils.getAttributeValue(entityItem, "CHQNLSID", "", "", false))) {
/*     */           
/* 189 */           entityGroup = new EntityGroup(null, paramDatabase, paramProfile, entityItem.getEntityType(), "Edit", false);
/* 190 */           entityItem = new EntityItem(entityGroup, paramProfile, paramDatabase, entityItem.getEntityType(), entityItem.getEntityID());
/*     */           
/* 192 */           str3 = PokUtils.getAttributeValue(entityItem, "CHQISOLANG", "", "@@", false);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     try {
/* 198 */       entityList.dereference();
/* 199 */     } catch (Exception exception) {
/*     */       
/* 201 */       System.out.println(exception);
/*     */     } 
/*     */     
/* 204 */     return str3;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLCHQISOElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */