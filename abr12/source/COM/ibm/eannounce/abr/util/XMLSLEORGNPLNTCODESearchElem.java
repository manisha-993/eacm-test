/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
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
/*     */ public class XMLSLEORGNPLNTCODESearchElem
/*     */   extends XMLElem
/*     */ {
/*  64 */   private String SLEORGNPLNTCODE_SEARCH_ACTION = "SRDSLEORG";
/*  65 */   private String SLEORGNPLNTCODE_ENTITY = "SLEORGNPLNTCODE";
/*  66 */   private String SLEORGNPLNTCODE_SEARCH_ATTRIBURE = "MODCATG";
/*  67 */   private EntityItem[] SLEORGNPLNTCODEItem = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSLEORGNPLNTCODESearchElem() {
/*  78 */     super(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getCOFCAT(EntityItem paramEntityItem) {
/*  87 */     String str = "@@";
/*  88 */     if (paramEntityItem != null) {
/*  89 */       String str1 = paramEntityItem.getEntityType();
/*  90 */       String str2 = null;
/*  91 */       if ("MODEL".equals(str1)) {
/*  92 */         str2 = "COFCAT";
/*  93 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str2);
/*  94 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */           
/*  96 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  97 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */             
/*  99 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 100 */               str = arrayOfMetaFlag[b].toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 107 */               if ("Service".equalsIgnoreCase(str))
/*     */               {
/* 109 */                 str = "Servicepac";
/*     */               }
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 115 */       } else if ("PRODSTRUCT".equals(str1)) {
/* 116 */         str = "Hardware";
/* 117 */       } else if ("SWPRODSTRUCT".equals(str1)) {
/* 118 */         str = "Software";
/*     */       } 
/*     */     } 
/* 121 */     return str;
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
/*     */   protected Hashtable doSearch(Database paramDatabase, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 136 */     String str = getCOFCAT(paramEntityItem);
/* 137 */     ABRUtil.append(paramStringBuffer, "XMLSLEORGNPLNTCODESearchElem.doSearch COFCAT" + str + NEWLINE);
/* 138 */     Vector<String> vector1 = new Vector();
/* 139 */     vector1.add(this.SLEORGNPLNTCODE_SEARCH_ATTRIBURE);
/* 140 */     Vector<String> vector2 = new Vector();
/* 141 */     vector2.add(str);
/* 142 */     ABRUtil.append(paramStringBuffer, "XMLSLEORGNPLNTCODESearchElem.doSearch searchAction=" + this.SLEORGNPLNTCODE_SEARCH_ACTION + NEWLINE);
/* 143 */     ABRUtil.append(paramStringBuffer, "XMLSLEORGNPLNTCODESearchElem.doSearch srchType=" + this.SLEORGNPLNTCODE_ENTITY + NEWLINE);
/* 144 */     ABRUtil.append(paramStringBuffer, "XMLSLEORGNPLNTCODESearchElem.doSearch attribute=" + this.SLEORGNPLNTCODE_SEARCH_ATTRIBURE + NEWLINE);
/* 145 */     ABRUtil.append(paramStringBuffer, "XMLSLEORGNPLNTCODESearchElem.doSearch value=" + str + NEWLINE);
/* 146 */     this.SLEORGNPLNTCODEItem = ABRUtil.doSearch(paramDatabase, paramEntityItem.getProfile(), this.SLEORGNPLNTCODE_SEARCH_ACTION, this.SLEORGNPLNTCODE_ENTITY, false, vector1, vector2, paramStringBuffer);
/*     */     
/* 148 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 149 */     if (this.SLEORGNPLNTCODEItem != null) {
/* 150 */       for (byte b = 0; b < this.SLEORGNPLNTCODEItem.length; b++) {
/* 151 */         EntityItem entityItem = this.SLEORGNPLNTCODEItem[b];
/*     */         
/* 153 */         int i = entityItem.getEntityID();
/* 154 */         String str1 = entityItem.getEntityType();
/* 155 */         String str2 = getCounryList(paramDatabase, paramEntityItem, i, str1);
/* 156 */         String str3 = PokUtils.getAttributeValue(entityItem, "SLEORGGRP", ";", "", false);
/* 157 */         Object object = hashtable.get(str2);
/* 158 */         if (object == null) {
/* 159 */           Vector[] arrayOfVector = new Vector[2];
/* 160 */           arrayOfVector[0] = new Vector();
/* 161 */           arrayOfVector[1] = new Vector();
/* 162 */           Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 163 */           StringTokenizer stringTokenizer = new StringTokenizer(str3, ";");
/* 164 */           while (stringTokenizer.hasMoreTokens()) {
/* 165 */             String str4 = stringTokenizer.nextToken();
/* 166 */             hashtable1.put(str4, "");
/*     */           } 
/* 168 */           arrayOfVector[0].addElement(hashtable1);
/* 169 */           arrayOfVector[1].add(entityItem);
/* 170 */           hashtable.put(str2, arrayOfVector);
/*     */         } else {
/* 172 */           Vector[] arrayOfVector = (Vector[])hashtable.get(str2);
/* 173 */           Hashtable<String, String> hashtable1 = arrayOfVector[0].get(0);
/* 174 */           StringTokenizer stringTokenizer = new StringTokenizer(str3, ",");
/* 175 */           while (stringTokenizer.hasMoreTokens()) {
/* 176 */             String str4 = stringTokenizer.nextToken();
/* 177 */             hashtable1.put(str4, "");
/*     */           } 
/* 179 */           arrayOfVector[0].addElement(hashtable1);
/* 180 */           arrayOfVector[1].add(entityItem);
/* 181 */           hashtable.put(str2, arrayOfVector);
/*     */         } 
/*     */       } 
/*     */     }
/* 185 */     return hashtable;
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
/*     */   private String getCounryList(Database paramDatabase, EntityItem paramEntityItem, int paramInt, String paramString) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 199 */     String str = "";
/* 200 */     EntityList entityList = paramDatabase.getEntityList(paramEntityItem.getProfile(), new ExtractActionItem(null, paramDatabase, paramEntityItem
/* 201 */           .getProfile(), "dummy"), new EntityItem[] { new EntityItem(null, paramEntityItem
/* 202 */             .getProfile(), paramString, paramInt) });
/* 203 */     EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*     */     
/* 205 */     str = PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST");
/* 206 */     if (str == null) str = "@@"; 
/* 207 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLSLEORGNPLNTCODESearchElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */