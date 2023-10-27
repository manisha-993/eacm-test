/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.hula.eDoc;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnDataRow;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
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
/*     */ public abstract class XMLMQChanges
/*     */   extends XMLMQRoot
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  58 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "ADSTYPE");
/*     */ 
/*     */     
/*  61 */     String str2 = "";
/*  62 */     if (str1 != null) {
/*  63 */       str2 = (String)ADSABRSTATUS.ADSTYPES_TBL.get(str1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     Vector<String> vector = getAffectedRoots(paramADSABRSTATUS, getVeName(), paramProfile2, str2, paramProfile1
/*  70 */         .getValOn(), paramProfile2.getValOn());
/*     */     
/*  72 */     if (vector.size() == 0) {
/*     */       
/*  74 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", str2);
/*     */     } else {
/*     */       
/*  77 */       EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/*  78 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  79 */         arrayOfEntityItem[b1] = new EntityItem(null, paramProfile2, str2, 
/*  80 */             Integer.parseInt(vector.elementAt(b1)));
/*     */       }
/*     */ 
/*     */       
/*  84 */       EntityList entityList = paramADSABRSTATUS.getDB().getEntityList(paramProfile2, new ExtractActionItem(null, paramADSABRSTATUS
/*  85 */             .getDB(), paramProfile2, "dummy"), arrayOfEntityItem);
/*     */       
/*  87 */       EntityGroup entityGroup = entityList.getParentEntityGroup(); byte b2;
/*  88 */       for (b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*  89 */         EntityItem entityItem = entityGroup.getEntityItem(b2);
/*  90 */         paramADSABRSTATUS.addDebug("XMLMQChanges checking root " + entityItem.getKey());
/*  91 */         if (paramADSABRSTATUS.domainNeedsChecks(entityItem)) {
/*  92 */           super.processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, entityItem);
/*     */         } else {
/*  94 */           paramADSABRSTATUS.addXMLGenMsg("DOMAIN_NOT_LISTED", entityItem.getKey());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  99 */       entityList.dereference();
/* 100 */       for (b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/* 101 */         arrayOfEntityItem[b2] = null;
/*     */       }
/* 103 */       arrayOfEntityItem = null;
/* 104 */       vector.clear();
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
/*     */   protected Vector getAffectedRoots(ADSABRSTATUS paramADSABRSTATUS, String paramString1, Profile paramProfile, String paramString2, String paramString3, String paramString4) throws SQLException, MiddlewareException {
/* 120 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, paramADSABRSTATUS.getDB(), paramProfile, paramString1);
/* 121 */     eDoc eDoc = null;
/* 122 */     Vector<String> vector1 = new Vector();
/* 123 */     Vector<String> vector2 = new Vector();
/*     */     
/* 125 */     ReturnDataResultSet returnDataResultSet = null;
/*     */     
/* 127 */     paramADSABRSTATUS.addDebug("XMLMQChanges.getAffectedRoots() Running edoc with root: " + paramString2 + " extract: " + paramString1 + " fromTime " + paramString3 + " toTime " + paramString4);
/* 128 */     eDoc = new eDoc(paramADSABRSTATUS.getDB(), paramProfile, extractActionItem, paramString2, paramString3, paramString4);
/*     */ 
/*     */     
/* 131 */     returnDataResultSet = eDoc.getTransactions();
/* 132 */     for (byte b = 0; b < returnDataResultSet.getRowCount(); b++) {
/*     */       
/* 134 */       ReturnDataRow returnDataRow = returnDataResultSet.getRow(b);
/* 135 */       StringBuffer stringBuffer = new StringBuffer();
/* 136 */       String str1 = null;
/* 137 */       String str2 = null;
/* 138 */       boolean bool1 = false;
/* 139 */       String str3 = null;
/* 140 */       String str4 = null;
/* 141 */       boolean bool2 = false;
/*     */       
/* 143 */       for (byte b1 = 1; b1 < returnDataRow.getColumnCount(); b1++) {
/* 144 */         stringBuffer.append("[" + b + "][" + b1 + "] " + returnDataResultSet.getColumn(b, b1) + " ");
/*     */       }
/* 146 */       paramADSABRSTATUS.addDebug(stringBuffer.toString());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       str2 = returnDataResultSet.getColumn(b, 1);
/* 153 */       if (!str2.equals(paramString2)) {
/* 154 */         paramADSABRSTATUS.addDebug(str2 + returnDataResultSet.getColumn(b, 2) + " was not expected root " + paramString2 + ", skipping it");
/*     */       }
/*     */       else {
/*     */         
/* 158 */         str1 = returnDataResultSet.getColumn(b, 2);
/* 159 */         bool1 = returnDataResultSet.getColumn(b, 3).equals("OFF");
/* 160 */         str3 = returnDataResultSet.getColumn(b, 4);
/* 161 */         str4 = returnDataResultSet.getColumn(b, 5);
/* 162 */         bool2 = returnDataResultSet.getColumn(b, 6).equals("OFF");
/* 163 */         if (vector2.contains(str1) || ((bool1 || bool2) && str2
/* 164 */           .equals(paramString2) && str3.equals(paramString2))) {
/* 165 */           paramADSABRSTATUS.addDebug(str2 + str1 + " was deleted, skipping it");
/*     */           
/* 167 */           if (!vector2.contains(str1)) {
/* 168 */             vector2.addElement(str1);
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 173 */         else if (!vector1.contains(str1)) {
/* 174 */           vector1.addElement(str1);
/*     */         } 
/*     */       } 
/* 177 */     }  paramADSABRSTATUS.addDebug("XMLMQChanges.getAffectedRoots() root id size: " + vector1.size() + " vct: " + vector1);
/*     */     
/* 179 */     vector2.clear();
/* 180 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 189 */     return "1.4";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\XMLMQChanges.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */