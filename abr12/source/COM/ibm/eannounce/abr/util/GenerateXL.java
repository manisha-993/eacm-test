/*      */ package COM.ibm.eannounce.abr.util;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaColumnOrderGroup;
/*      */ import COM.ibm.eannounce.objects.MetaColumnOrderItem;
/*      */ import COM.ibm.eannounce.objects.SearchActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*      */ import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/*      */ import org.apache.poi.hssf.usermodel.HSSFDataFormat;
/*      */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*      */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*      */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GenerateXL
/*      */ {
/*  109 */   ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  110 */   String _strFlag = null;
/*  111 */   FileOutputStream out = null;
/*  112 */   EntityItem _ei = null;
/*  113 */   StringBuffer m_sb = new StringBuffer();
/*  114 */   HSSFWorkbook wb = new HSSFWorkbook();
/*  115 */   HSSFCellStyle wbcs = this.wb.createCellStyle();
/*  116 */   HSSFDataFormat wbdf = this.wb.createDataFormat();
/*      */ 
/*      */   
/*      */   public GenerateXL(EntityList paramEntityList, EntityItem paramEntityItem, String paramString1, Database paramDatabase, Profile paramProfile, String paramString2, String paramString3, boolean paramBoolean) throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  120 */     if (paramBoolean) {
/*  121 */       System.out.println("-------------Starting generate XL----------------");
/*  122 */       SearchActionItem searchActionItem = new SearchActionItem(null, paramDatabase, paramProfile, paramString2);
/*  123 */       EntityList entityList = new EntityList(paramDatabase, paramProfile, searchActionItem);
/*  124 */       EntityGroup entityGroup = entityList.getEntityGroup("RPTEXCEL");
/*      */       
/*  126 */       if (entityGroup != null) {
/*  127 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  128 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/*  130 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[4];
/*  131 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/*  132 */           arrayOfEANAttribute[1] = entityItem.getAttribute("RPTEXCELLAYOUT");
/*  133 */           arrayOfEANAttribute[2] = entityItem.getAttribute("PDHDOMAIN");
/*  134 */           arrayOfEANAttribute[3] = entityItem.getAttribute("RPTEXCELVE");
/*      */ 
/*      */           
/*  137 */           String str = null;
/*      */           
/*  139 */           if (arrayOfEANAttribute[3] == null) {
/*  140 */             str = " ";
/*      */           } else {
/*  142 */             str = arrayOfEANAttribute[3].toString();
/*  143 */             System.out.println("We using this VE to build the XL" + str + ": compared to MODEL's VE" + paramString3);
/*      */           } 
/*      */           
/*  146 */           if (str.equals(paramString3)) {
/*      */ 
/*      */             
/*  149 */             System.out.println("***RPTEXCEL: " + entityItem
/*  150 */                 .getEntityType() + entityItem
/*  151 */                 .getEntityID() + " atts[0]: " + arrayOfEANAttribute[0] + " atts[1]: " + arrayOfEANAttribute[1]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  157 */             String str1 = null;
/*  158 */             if (arrayOfEANAttribute[1] == null) {
/*  159 */               str1 = " ";
/*      */             } else {
/*  161 */               str1 = arrayOfEANAttribute[1].toString();
/*  162 */               System.out.println("**strValue from RPTEXCEL" + str1);
/*      */               
/*  164 */               StringTokenizer stringTokenizer = new StringTokenizer(str1, "|");
/*      */               
/*  166 */               int i = stringTokenizer.countTokens();
/*  167 */               byte b1 = 0;
/*      */               
/*  169 */               String[] arrayOfString = new String[i];
/*  170 */               System.out.println("ss: " + i);
/*      */               
/*  172 */               while (stringTokenizer.hasMoreTokens()) {
/*  173 */                 arrayOfString[b1] = stringTokenizer.nextToken();
/*  174 */                 this._strFlag = arrayOfString[b1].toString();
/*  175 */                 System.out.println("**_strFlag: " + this._strFlag);
/*  176 */                 b1++;
/*      */               } 
/*      */               
/*  179 */               System.out.println("***we're using eiParent to build entitylist" + paramEntityItem.getEntityType() + ": " + paramEntityItem.getEntityID());
/*  180 */               EntityItem[] arrayOfEntityItem = { paramEntityItem };
/*      */               
/*  182 */               ExtractActionItem extractActionItem = new ExtractActionItem(null, paramDatabase, paramProfile, str);
/*  183 */               EntityList entityList1 = new EntityList(paramDatabase, paramProfile, extractActionItem, arrayOfEntityItem, paramEntityItem.getEntityType());
/*      */               
/*  185 */               System.out.println("********processEntity*************");
/*  186 */               processEntity(entityList1, arrayOfString, paramEntityItem, paramString1, paramDatabase, paramProfile);
/*  187 */               System.out.println("********End of processEntity*************");
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  192 */             System.out.println("do nothing with this RPTEXCEL" + entityItem.getEntityType() + entityItem.getEntityID());
/*      */           } 
/*      */         } 
/*      */       } else {
/*  196 */         System.out.println("eg for XL entityis empty");
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  201 */     else if (paramEntityList != null) {
/*      */ 
/*      */       
/*  204 */       System.out.println("-------------Starting XL dump--------------");
/*      */       
/*  206 */       HSSFSheet hSSFSheet = this.wb.createSheet(paramString1);
/*  207 */       System.out.println("***Filename: " + paramString1);
/*      */       
/*  209 */       HSSFRow hSSFRow = null;
/*      */       
/*  211 */       HSSFCell hSSFCell = null;
/*      */       
/*  213 */       this.wbcs.setDataFormat(this.wbdf.getFormat("###,###,###,##0.0"));
/*      */       
/*  215 */       this.wb = new HSSFWorkbook();
/*      */       
/*  217 */       byte b1 = 0;
/*      */       
/*  219 */       for (byte b2 = 0; b2 < paramEntityList.getEntityGroupCount(); b2++) {
/*  220 */         EntityGroup entityGroup = paramEntityList.getEntityGroup(b2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  225 */         if (!entityGroup.isRelator() && !entityGroup.isAssoc()) {
/*      */ 
/*      */           
/*  228 */           String str1 = entityGroup.getLongDescription();
/*  229 */           str1 = str1.replace('/', ' ');
/*  230 */           String str2 = paramEntityItem.getEntityType();
/*  231 */           System.out.println("Working with _eg: " + b2 + ": " + entityGroup.getLongDescription() + "count: " + b1 + str2 + "tabName" + str1);
/*  232 */           hSSFSheet = this.wb.createSheet(paramString1);
/*  233 */           this.wb.setSheetName(b1, str1);
/*  234 */           b1++;
/*      */ 
/*      */           
/*  237 */           if (entityGroup.getEntityType().equals(str2)) {
/*  238 */             entityGroup = paramEntityList.getParentEntityGroup();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  244 */           if (entityGroup != null) {
/*  245 */             String str = entityGroup.getEntityType();
/*  246 */             entityGroup = new EntityGroup(null, paramDatabase, paramProfile, str, "Edit", true);
/*  247 */             MetaColumnOrderGroup metaColumnOrderGroup = entityGroup.getMetaColumnOrderGroup();
/*  248 */             System.out.println("1strEntityType" + str + "_eg: " + entityGroup);
/*      */             short s;
/*  250 */             for (s = 0; s < metaColumnOrderGroup.getMetaColumnOrderItemCount(); s = (short)(s + 1)) {
/*  251 */               MetaColumnOrderItem metaColumnOrderItem = metaColumnOrderGroup.getMetaColumnOrderItem(s);
/*  252 */               String str3 = metaColumnOrderItem.getAttributeCode();
/*  253 */               System.out.println("1strAttrCode" + str3);
/*      */               
/*  255 */               for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  256 */                 this._ei = entityGroup.getEntityItem(b);
/*  257 */                 System.out.println("1***_ei: " + this._ei.getEntityType() + this._ei.getEntityID() + ": " + entityGroup.getEntityItemCount() + "- " + b);
/*      */                 
/*  259 */                 if (this._ei != null) {
/*      */                   
/*  261 */                   System.out.println("***mcog:***" + metaColumnOrderGroup + "*** " + this._ei.getEntityType() + " entityid: " + this._ei.getEntityID() + ": mcoi.getAttributeCode(): " + metaColumnOrderItem.getAttributeCode() + "k: " + s);
/*  262 */                   EANAttribute eANAttribute = this._ei.getAttribute(str3);
/*      */                   
/*  264 */                   String str4 = metaColumnOrderItem.getAttributeDescription();
/*      */                   
/*  266 */                   if (str4.length() > 0 && str4.charAt(0) == '*') {
/*  267 */                     str4 = str4.substring(1);
/*  268 */                     System.out.println("strAttributeDesc" + str4);
/*      */                   } 
/*      */                   
/*  271 */                   short s1 = 0;
/*  272 */                   System.out.println("iCellNum: " + s1 + "k" + s + this._ei.getEntityType() + this._ei.getEntityID());
/*  273 */                   hSSFRow = hSSFSheet.createRow(s1);
/*  274 */                   hSSFSheet.setColumnWidth(s, 8000);
/*  275 */                   hSSFCell = hSSFRow.createCell(s);
/*  276 */                   hSSFCell.setCellValue(str4);
/*      */                   
/*  278 */                   s1 = (short)(s1 + 1);
/*      */ 
/*      */ 
/*      */                   
/*  282 */                   String str5 = null;
/*  283 */                   if (eANAttribute == null) {
/*  284 */                     str5 = " ";
/*      */                   } else {
/*  286 */                     str5 = eANAttribute.toString();
/*      */                     
/*  288 */                     if (str5.length() > 0 && str5.charAt(0) == '*') {
/*  289 */                       str5 = str5.substring(1);
/*      */                     }
/*      */                   } 
/*      */                   
/*  293 */                   int i = b + 1;
/*      */ 
/*      */                   
/*  296 */                   System.out.println("strValue: " + this._ei.getEntityType() + this._ei.getEntityID() + str5 + "iRow" + i);
/*  297 */                   hSSFRow = hSSFSheet.createRow(i);
/*  298 */                   hSSFSheet.setColumnWidth(s, 8000);
/*  299 */                   hSSFCell = hSSFRow.createCell(s);
/*  300 */                   hSSFCell.setCellValue(str5);
/*  301 */                   i++;
/*      */                 } else {
/*  303 */                   System.out.println("EntityItem is empty");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/*  312 */         String str = paramString1 + ".xls";
/*  313 */         File file = new File(str);
/*  314 */         this.out = new FileOutputStream(file);
/*  315 */         this.wb.write(this.out);
/*  316 */         this.out.close();
/*  317 */       } catch (IOException iOException) {
/*  318 */         iOException.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processEntity(EntityList paramEntityList, String[] paramArrayOfString, EntityItem paramEntityItem, String paramString, Database paramDatabase, Profile paramProfile) {
/*  327 */     if (paramArrayOfString != null) {
/*      */ 
/*      */ 
/*      */       
/*  331 */       HSSFSheet hSSFSheet = this.wb.createSheet(paramString);
/*  332 */       System.out.println("***Filename: " + paramString);
/*      */       
/*  334 */       HSSFRow hSSFRow = null;
/*      */       
/*  336 */       HSSFCell hSSFCell = null;
/*  337 */       this.wbcs.setDataFormat(this.wbdf.getFormat("###,###,###,##0.0"));
/*      */       
/*  339 */       this.wb = new HSSFWorkbook();
/*      */ 
/*      */       
/*  342 */       EntityGroup entityGroup = null;
/*  343 */       EntityItem entityItem1 = null;
/*  344 */       EntityItem entityItem2 = null;
/*  345 */       EntityItem entityItem3 = null;
/*  346 */       MetaColumnOrderGroup metaColumnOrderGroup = null;
/*  347 */       String str1 = null;
/*  348 */       String str2 = null;
/*  349 */       String str3 = null;
/*  350 */       String str4 = null;
/*  351 */       String str5 = null;
/*  352 */       String str6 = null;
/*  353 */       byte b1 = 0;
/*  354 */       short s = 0;
/*      */       
/*  356 */       int i = paramArrayOfString.length;
/*  357 */       System.out.println("ii: " + i);
/*  358 */       for (byte b2 = 0; b2 < i; b2++) {
/*  359 */         int j = paramArrayOfString[b2].indexOf(".");
/*  360 */         str5 = paramArrayOfString[b2].substring(0, j);
/*  361 */         boolean bool = stringCheck(str5);
/*  362 */         System.out.println("***sEg: " + str5 + "sEG's _ifRelator: " + bool);
/*  363 */         if (!bool) {
/*  364 */           entityGroup = paramEntityList.getEntityGroup(str5);
/*  365 */           System.out.println("***eg: " + entityGroup.getEntityType() + ": " + entityGroup);
/*      */         } else {
/*      */           
/*  368 */           int k = str5.length(); byte b;
/*  369 */           for (b = 0; b < k; b++) {
/*  370 */             int m = str5.indexOf(":");
/*  371 */             String str = str5.substring(0, m);
/*  372 */             entityGroup = paramEntityList.getEntityGroup(str);
/*  373 */             System.out.println("***eg: " + entityGroup.getEntityType() + ": " + entityGroup + "_Egrp" + str);
/*  374 */             str3 = str5.substring(m + 1);
/*  375 */             System.out.println("***_sfindRel: " + str3);
/*      */           } 
/*  377 */           for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  378 */             entityItem1 = entityGroup.getEntityItem(b);
/*  379 */             System.out.println("***_releitem: " + entityItem1.getEntityType() + entityItem1.getEntityID());
/*      */ 
/*      */             
/*  382 */             if (entityItem1 != null) {
/*  383 */               StringTokenizer stringTokenizer = new StringTokenizer(str3, ":");
/*      */               
/*  385 */               int m = stringTokenizer.countTokens();
/*  386 */               byte b3 = 0;
/*      */               
/*  388 */               String[] arrayOfString = new String[m];
/*  389 */               System.out.println("uu: " + m + " _sfindRel: " + str3);
/*      */ 
/*      */               
/*  392 */               if (m < 1) {
/*  393 */                 System.out.println("*** No relators: ");
/*      */               }
/*  395 */               if (m == 1) {
/*  396 */                 System.out.println("*** with uu less than 2: " + entityGroup + "_releitem" + entityItem1
/*      */ 
/*      */                     
/*  399 */                     .getEntityType() + entityItem1
/*  400 */                     .getEntityID() + "***_sfindRel" + str3);
/*      */ 
/*      */                 
/*  403 */                 entityItem2 = findLink(entityItem1, str3);
/*  404 */                 if (entityItem2 != null) {
/*  405 */                   entityGroup = paramEntityList.getEntityGroup(entityItem2.getEntityType());
/*      */                 }
/*      */               } 
/*      */               
/*  409 */               if (m > 1 && arrayOfString != null) {
/*  410 */                 while (stringTokenizer.hasMoreTokens()) {
/*  411 */                   arrayOfString[b3] = stringTokenizer.nextToken();
/*  412 */                   System.out.println("***_sEg: " + arrayOfString[b3] + "_sEg[0]: " + arrayOfString[0] + "_sEg[1]" + arrayOfString[1] + "_sEg[uu-1]" + arrayOfString[m - 1]);
/*  413 */                   b3++;
/*      */                 } 
/*      */                 
/*  416 */                 if (m == 2 && arrayOfString[m - 1] != null) {
/*      */                   
/*  418 */                   System.out.println("*** with uu == 2: " + entityGroup + "_releitem" + entityItem1
/*      */ 
/*      */                       
/*  421 */                       .getEntityType() + entityItem1
/*  422 */                       .getEntityID() + "_sEg[0]" + arrayOfString[0] + "_sEg[1]" + arrayOfString[1]);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  427 */                   EntityItem entityItem = findLink3(entityItem1, arrayOfString[0]);
/*  428 */                   if (entityItem != null) {
/*  429 */                     entityItem2 = findLink(entityItem, arrayOfString[1]);
/*  430 */                     if (entityItem2 != null) {
/*  431 */                       entityGroup = paramEntityList.getEntityGroup(entityItem2.getEntityType());
/*      */                     }
/*      */                   } 
/*  434 */                 } else if (m == 3 && arrayOfString[m - 1] != null) {
/*  435 */                   System.out.println("*** with uu == 3: " + entityGroup + "_releitem" + entityItem1
/*      */ 
/*      */                       
/*  438 */                       .getEntityType() + entityItem1
/*  439 */                       .getEntityID() + "***_sfindRel" + str3);
/*      */ 
/*      */                   
/*  442 */                   EntityItem entityItem = findLink3(entityItem1, arrayOfString[0]);
/*  443 */                   if (entityItem != null) {
/*  444 */                     EntityItem entityItem4 = findLink3(entityItem, arrayOfString[1]);
/*  445 */                     if (entityItem4 != null) {
/*  446 */                       entityItem2 = findLink(entityItem4, arrayOfString[m - 1]);
/*  447 */                       if (entityItem2 != null) {
/*  448 */                         entityGroup = paramEntityList.getEntityGroup(entityItem2.getEntityType());
/*      */                       }
/*      */                     } 
/*      */                   } 
/*  452 */                 } else if (m == 4 && arrayOfString[m - 1] != null) {
/*  453 */                   System.out.println("*** with uu == 4: " + entityGroup + "_releitem" + entityItem1
/*      */ 
/*      */                       
/*  456 */                       .getEntityType() + entityItem1
/*  457 */                       .getEntityID() + "***_sfindRel" + str3);
/*      */ 
/*      */                   
/*  460 */                   EntityItem entityItem = findLink3(entityItem1, arrayOfString[0]);
/*  461 */                   if (entityItem != null) {
/*  462 */                     EntityItem entityItem4 = findLink3(entityItem, arrayOfString[1]);
/*  463 */                     if (entityItem4 != null) {
/*  464 */                       EntityItem entityItem5 = findLink3(entityItem4, arrayOfString[2]);
/*  465 */                       if (entityItem5 != null) {
/*  466 */                         entityItem2 = findLink(entityItem5, arrayOfString[m - 1]);
/*  467 */                         if (entityItem2 != null) {
/*  468 */                           entityGroup = paramEntityList.getEntityGroup(entityItem2.getEntityType());
/*      */                         }
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*  473 */                 } else if (m == 5 && arrayOfString[m - 1] != null) {
/*  474 */                   System.out.println("*** with uu == 5: " + entityGroup + "_releitem" + entityItem1
/*      */ 
/*      */                       
/*  477 */                       .getEntityType() + entityItem1
/*  478 */                       .getEntityID() + "***_sfindRel" + str3);
/*      */ 
/*      */                   
/*  481 */                   EntityItem entityItem = findLink3(entityItem1, arrayOfString[0]);
/*  482 */                   if (entityItem != null) {
/*  483 */                     EntityItem entityItem4 = findLink3(entityItem, arrayOfString[1]);
/*  484 */                     if (entityItem4 != null) {
/*  485 */                       EntityItem entityItem5 = findLink3(entityItem4, arrayOfString[2]);
/*  486 */                       if (entityItem5 != null) {
/*  487 */                         EntityItem entityItem6 = findLink3(entityItem5, arrayOfString[3]);
/*  488 */                         if (entityItem6 != null) {
/*  489 */                           entityItem2 = findLink(entityItem6, arrayOfString[m - 1]);
/*  490 */                           if (entityItem2 != null) {
/*  491 */                             entityGroup = paramEntityList.getEntityGroup(entityItem2.getEntityType());
/*      */                           }
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*  497 */                 } else if (m == 6 && arrayOfString[m - 1] != null) {
/*  498 */                   System.out.println("*** with uu == 6: " + entityGroup + "_releitem" + entityItem1
/*      */ 
/*      */                       
/*  501 */                       .getEntityType() + entityItem1
/*  502 */                       .getEntityID() + "***_sfindRel" + str3);
/*      */ 
/*      */                   
/*  505 */                   EntityItem entityItem = findLink3(entityItem1, arrayOfString[0]);
/*  506 */                   if (entityItem != null) {
/*  507 */                     EntityItem entityItem4 = findLink3(entityItem, arrayOfString[1]);
/*  508 */                     if (entityItem4 != null) {
/*  509 */                       EntityItem entityItem5 = findLink3(entityItem4, arrayOfString[2]);
/*  510 */                       if (entityItem5 != null) {
/*  511 */                         EntityItem entityItem6 = findLink3(entityItem5, arrayOfString[3]);
/*  512 */                         if (entityItem6 != null) {
/*  513 */                           EntityItem entityItem7 = findLink3(entityItem6, arrayOfString[4]);
/*  514 */                           if (entityItem7 != null) {
/*  515 */                             entityItem2 = findLink(entityItem7, arrayOfString[m - 1]);
/*  516 */                             if (entityItem2 != null) {
/*  517 */                               entityGroup = paramEntityList.getEntityGroup(entityItem2.getEntityType());
/*      */                             }
/*      */                           } 
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*  524 */                 } else if (m == 7 && arrayOfString[m - 1] != null) {
/*  525 */                   System.out.println("*** with uu == 7: " + entityGroup + "_releitem" + entityItem1
/*      */ 
/*      */                       
/*  528 */                       .getEntityType() + entityItem1
/*  529 */                       .getEntityID() + "***_sfindRel" + str3);
/*      */ 
/*      */                   
/*  532 */                   EntityItem entityItem = findLink3(entityItem1, arrayOfString[0]);
/*  533 */                   if (entityItem != null) {
/*  534 */                     EntityItem entityItem4 = findLink3(entityItem, arrayOfString[1]);
/*  535 */                     if (entityItem4 != null) {
/*  536 */                       EntityItem entityItem5 = findLink3(entityItem4, arrayOfString[2]);
/*  537 */                       if (entityItem5 != null) {
/*  538 */                         EntityItem entityItem6 = findLink3(entityItem5, arrayOfString[3]);
/*  539 */                         if (entityItem6 != null) {
/*  540 */                           EntityItem entityItem7 = findLink3(entityItem6, arrayOfString[4]);
/*  541 */                           if (entityItem7 != null) {
/*  542 */                             EntityItem entityItem8 = findLink3(entityItem7, arrayOfString[5]);
/*  543 */                             if (entityItem8 != null) {
/*  544 */                               entityItem2 = findLink(entityItem8, arrayOfString[m - 1]);
/*  545 */                               if (entityItem2 != null) {
/*  546 */                                 entityGroup = paramEntityList.getEntityGroup(entityItem2.getEntityType());
/*      */                               }
/*      */                             } 
/*      */                           } 
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  560 */         str6 = paramArrayOfString[b2].substring(j + 1);
/*  561 */         System.out.println("***sAtt: " + str6 + "Count: " + b1);
/*      */         
/*  563 */         if (!entityGroup.isRelator() && !entityGroup.isAssoc()) {
/*      */           
/*      */           try {
/*  566 */             String str7 = entityGroup.getLongDescription();
/*  567 */             str7 = str7.replace('/', ' ');
/*  568 */             String str8 = paramEntityItem.getEntityType();
/*  569 */             System.out.println("Working with eg: " + entityGroup.getLongDescription() + "Count: " + b1 + str8 + "tabName" + str7);
/*  570 */             hSSFSheet = this.wb.createSheet(paramString);
/*      */             
/*  572 */             this.wb.setSheetName(b1, str7);
/*  573 */             b1++;
/*      */             
/*  575 */             if (str6.equals("*")) {
/*  576 */               System.out.println("sAtt equals to *: " + str6);
/*      */ 
/*      */               
/*  579 */               if (entityGroup.getEntityType().equals(str8)) {
/*  580 */                 entityGroup = paramEntityList.getParentEntityGroup();
/*      */               }
/*      */ 
/*      */               
/*  584 */               for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  585 */                 entityItem3 = entityGroup.getEntityItem(b);
/*  586 */                 System.out.println("***_ei: " + entityItem3.getEntityType() + entityItem3.getEntityID() + ": " + entityGroup.getEntityItemCount() + "- " + b);
/*      */                 
/*  588 */                 String str = entityGroup.getEntityType();
/*  589 */                 EntityGroup entityGroup1 = new EntityGroup(null, paramDatabase, paramProfile, str, "Edit", true);
/*  590 */                 metaColumnOrderGroup = entityGroup1.getMetaColumnOrderGroup();
/*  591 */                 System.out.println("strEntityType" + str + "eg1: " + entityGroup1);
/*      */                 short s1;
/*  593 */                 for (s1 = 0; s1 < metaColumnOrderGroup.getMetaColumnOrderItemCount(); s1 = (short)(s1 + 1)) {
/*  594 */                   MetaColumnOrderItem metaColumnOrderItem = metaColumnOrderGroup.getMetaColumnOrderItem(s1);
/*  595 */                   String str9 = metaColumnOrderItem.getAttributeCode();
/*  596 */                   System.out.println("strAttrCode" + str9);
/*      */ 
/*      */                   
/*  599 */                   if (entityItem3 != null) {
/*      */                     
/*  601 */                     System.out.println("***mcog:***" + entityItem3.getEntityType() + " entityid: " + entityItem3.getEntityID() + ": mcoi.getAttributeCode(): " + metaColumnOrderItem.getAttributeCode() + "k: " + s1);
/*  602 */                     EANAttribute eANAttribute = entityItem3.getAttribute(str9);
/*      */                     
/*  604 */                     String str10 = metaColumnOrderItem.getAttributeDescription();
/*      */                     
/*  606 */                     if (str10.length() > 0 && str10.charAt(0) == '*') {
/*  607 */                       str10 = str10.substring(1);
/*  608 */                       System.out.println("strAttributeDesc" + str10);
/*      */                     } 
/*      */                     
/*  611 */                     short s2 = 0;
/*  612 */                     hSSFRow = hSSFSheet.createRow(s2);
/*  613 */                     hSSFSheet.setColumnWidth(s1, 8000);
/*  614 */                     hSSFCell = hSSFRow.createCell(s1);
/*  615 */                     hSSFCell.setCellValue(str10);
/*  616 */                     s2 = (short)(s2 + 1);
/*      */ 
/*      */                     
/*  619 */                     String str11 = null;
/*  620 */                     if (eANAttribute == null) {
/*  621 */                       str11 = " ";
/*      */                     } else {
/*  623 */                       str11 = eANAttribute.toString();
/*      */                       
/*  625 */                       if (str11.length() > 0 && str11.charAt(0) == '*') {
/*  626 */                         str11 = str11.substring(1);
/*      */                       }
/*      */                     } 
/*      */                     
/*  630 */                     int k = b + 1;
/*      */ 
/*      */                     
/*  633 */                     System.out.println("strValue1: " + entityItem3.getEntityType() + entityItem3.getEntityID() + str11 + "iRow" + k);
/*  634 */                     hSSFRow = hSSFSheet.createRow(k);
/*  635 */                     hSSFSheet.setColumnWidth(s1, 8000);
/*  636 */                     hSSFCell = hSSFRow.createCell(s1);
/*  637 */                     hSSFCell.setCellValue(str11);
/*  638 */                     k++;
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } else {
/*  643 */               System.out.println("sAtt NOT equals to *: " + str6);
/*      */               
/*  645 */               StringTokenizer stringTokenizer = new StringTokenizer(str6, ";");
/*      */               
/*  647 */               int k = stringTokenizer.countTokens();
/*  648 */               byte b = 0;
/*      */               
/*  650 */               String[] arrayOfString = new String[k];
/*  651 */               System.out.println("yy: " + k + " atts: " + arrayOfString);
/*      */               
/*  653 */               while (stringTokenizer.hasMoreTokens()) {
/*  654 */                 arrayOfString[b] = stringTokenizer.nextToken();
/*  655 */                 System.out.println("***atts: " + arrayOfString[b]);
/*  656 */                 b++;
/*      */               } 
/*      */               
/*  659 */               if (entityGroup != null && arrayOfString != null) {
/*  660 */                 short s1 = -1;
/*  661 */                 int m = arrayOfString.length;
/*  662 */                 for (int n = m - 1; n >= 0; n--) {
/*  663 */                   System.out.print("atts[j]: (" + arrayOfString[n] + ")jj: " + m);
/*      */                   
/*  665 */                   if (!arrayOfString[n].equals("*")) {
/*      */                     
/*  667 */                     if (arrayOfString != null) {
/*  668 */                       System.out.println("we're here without a *: atts: " + arrayOfString + "atts.length: " + arrayOfString.length);
/*      */                       
/*  670 */                       StringTokenizer stringTokenizer1 = new StringTokenizer(arrayOfString[n], ":");
/*      */                       
/*  672 */                       int i1 = stringTokenizer1.countTokens();
/*  673 */                       byte b3 = 0;
/*      */                       
/*  675 */                       String[] arrayOfString1 = new String[i1];
/*  676 */                       System.out.println("oo: " + i1 + " _relatts: " + arrayOfString1);
/*      */                       
/*  678 */                       while (stringTokenizer1.hasMoreTokens()) {
/*  679 */                         arrayOfString1[b3] = stringTokenizer1.nextToken();
/*  680 */                         String str = arrayOfString1[b3];
/*  681 */                         b3++;
/*      */ 
/*      */                         
/*  684 */                         boolean bool1 = stringCheck(str);
/*  685 */                         System.out.println("***relatts: " + str + " ifRelator: " + bool1);
/*  686 */                         if (!bool1) {
/*  687 */                           str1 = findRelator(str);
/*  688 */                           System.out.println("<>sRel: " + str1);
/*      */                           continue;
/*      */                         } 
/*  691 */                         str2 = findEntity(str);
/*  692 */                         System.out.println("<>sEg1: " + str2);
/*  693 */                         str4 = findAtt(str);
/*  694 */                         System.out.println("<>sAtt1: " + str4);
/*      */ 
/*      */                         
/*  697 */                         StringTokenizer stringTokenizer2 = new StringTokenizer(str4, ",");
/*      */                         
/*  699 */                         int i2 = stringTokenizer2.countTokens();
/*  700 */                         byte b4 = 0;
/*      */                         
/*  702 */                         String[] arrayOfString2 = new String[i2];
/*  703 */                         System.out.println("zz: " + i2 + " flagatts: " + arrayOfString2);
/*      */                         
/*  705 */                         while (stringTokenizer2.hasMoreTokens()) {
/*  706 */                           arrayOfString2[b4] = stringTokenizer2.nextToken();
/*  707 */                           String str9 = arrayOfString2[b4];
/*  708 */                           b4++;
/*  709 */                           System.out.println("***_flagatts: " + str9);
/*  710 */                           EntityGroup entityGroup1 = paramEntityList.getEntityGroup(str2);
/*  711 */                           System.out.println("***sEg1: " + str2 + "egp_sEg1: " + entityGroup1 + "_z" + s1);
/*  712 */                           EANMetaAttribute eANMetaAttribute = entityGroup1.getMetaAttribute(str9);
/*  713 */                           s1 = (short)(s1 + 1);
/*      */                           short s2;
/*  715 */                           for (s2 = 0; s2 < entityGroup.getEntityItemCount(); s2 = (short)(s2 + 1)) {
/*  716 */                             EntityItem entityItem = entityGroup.getEntityItem(s2);
/*      */                             
/*  718 */                             if (i1 == 1) {
/*  719 */                               System.out.println("*** with oo == 1: " + entityItem
/*  720 */                                   .getEntityType() + entityItem
/*  721 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  726 */                               entityItem2 = entityItem;
/*  727 */                             } else if (i1 == 2) {
/*  728 */                               System.out.println("*** with oo == 2: " + entityItem
/*  729 */                                   .getEntityType() + entityItem
/*  730 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  735 */                               EntityItem[] arrayOfEntityItem = findLink2(entityItem, str2, str1);
/*  736 */                               int i3 = s2 + 1;
/*  737 */                               for (byte b5 = 0; b5 < arrayOfEntityItem.length; b5++) {
/*  738 */                                 entityItem2 = arrayOfEntityItem[b5];
/*      */                                 
/*  740 */                                 ProcessXL(hSSFSheet, hSSFRow, hSSFCell, entityItem2, entityGroup, eANMetaAttribute, str9, s2, s1, i3);
/*  741 */                                 i3++;
/*      */                               } 
/*  743 */                             } else if (i1 == 3) {
/*  744 */                               System.out.println("*** with oo == 3: " + entityItem
/*  745 */                                   .getEntityType() + entityItem
/*  746 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  751 */                               EntityItem entityItem4 = findLink3(entityItem, str1);
/*  752 */                               if (entityItem4 != null)
/*      */                               {
/*      */                                 
/*  755 */                                 entityItem2 = findLink(entityItem4, str2);
/*  756 */                                 int i3 = s2 + 1;
/*  757 */                                 ProcessXL(hSSFSheet, hSSFRow, hSSFCell, entityItem2, entityGroup, eANMetaAttribute, str9, s2, s1, i3);
/*  758 */                                 i3++;
/*      */ 
/*      */                               
/*      */                               }
/*      */ 
/*      */ 
/*      */                             
/*      */                             }
/*  766 */                             else if (i1 == 4) {
/*  767 */                               System.out.println("*** with oo == 4: " + entityItem
/*  768 */                                   .getEntityType() + entityItem
/*  769 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  774 */                               EntityItem entityItem4 = findLink3(entityItem, str1);
/*  775 */                               if (entityItem4 != null)
/*      */                               {
/*      */                                 
/*  778 */                                 entityItem2 = findLink(entityItem4, str2);
/*  779 */                                 int i3 = s2 + 1;
/*  780 */                                 ProcessXL(hSSFSheet, hSSFRow, hSSFCell, entityItem2, entityGroup, eANMetaAttribute, str9, s2, s1, i3);
/*  781 */                                 i3++;
/*      */ 
/*      */ 
/*      */                               
/*      */                               }
/*      */ 
/*      */ 
/*      */                             
/*      */                             }
/*  790 */                             else if (i1 == 5) {
/*  791 */                               System.out.println("*** with oo == 5: " + entityItem
/*  792 */                                   .getEntityType() + entityItem
/*  793 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  798 */                               EntityItem entityItem4 = findLink3(entityItem, str1);
/*  799 */                               if (entityItem4 != null)
/*      */                               
/*      */                               { 
/*  802 */                                 entityItem2 = findLink(entityItem4, str2);
/*  803 */                                 int i3 = s2 + 1;
/*  804 */                                 ProcessXL(hSSFSheet, hSSFRow, hSSFCell, entityItem2, entityGroup, eANMetaAttribute, str9, s2, s1, i3);
/*  805 */                                 i3++; } 
/*  806 */                             } else if (i1 == 6) {
/*  807 */                               System.out.println("*** with oo == 6: " + entityItem
/*  808 */                                   .getEntityType() + entityItem
/*  809 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  814 */                               EntityItem entityItem4 = _findLink(entityItem);
/*  815 */                               EntityItem entityItem5 = _findLink(entityItem4);
/*  816 */                               EntityItem entityItem6 = _findLink(entityItem5);
/*  817 */                               EntityItem entityItem7 = _findLink(entityItem6);
/*  818 */                               EntityItem[] arrayOfEntityItem = findLink2(entityItem7, str2, str1);
/*  819 */                               int i3 = s2 + 1;
/*  820 */                               for (byte b5 = 0; b5 < arrayOfEntityItem.length; b5++) {
/*  821 */                                 entityItem2 = arrayOfEntityItem[b5];
/*  822 */                                 ProcessXL(hSSFSheet, hSSFRow, hSSFCell, entityItem2, entityGroup, eANMetaAttribute, str9, s2, s1, i3);
/*  823 */                                 i3++;
/*      */                               } 
/*  825 */                             } else if (i1 == 7) {
/*  826 */                               System.out.println("*** with oo == 7: " + entityItem
/*  827 */                                   .getEntityType() + entityItem
/*  828 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  833 */                               EntityItem entityItem4 = _findLink(entityItem);
/*  834 */                               EntityItem entityItem5 = _findLink(entityItem4);
/*  835 */                               EntityItem entityItem6 = _findLink(entityItem5);
/*  836 */                               EntityItem entityItem7 = _findLink(entityItem6);
/*  837 */                               EntityItem entityItem8 = _findLink(entityItem7);
/*  838 */                               EntityItem[] arrayOfEntityItem = findLink2(entityItem8, str2, str1);
/*  839 */                               int i3 = s2 + 1;
/*  840 */                               for (byte b5 = 0; b5 < arrayOfEntityItem.length; b5++) {
/*  841 */                                 entityItem2 = arrayOfEntityItem[b5];
/*  842 */                                 ProcessXL(hSSFSheet, hSSFRow, hSSFCell, entityItem2, entityGroup, eANMetaAttribute, str9, s2, s1, i3);
/*  843 */                                 i3++;
/*      */                               } 
/*  845 */                             } else if (i1 == 8) {
/*  846 */                               System.out.println("*** with oo == 8: " + entityItem
/*  847 */                                   .getEntityType() + entityItem
/*  848 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  853 */                               EntityItem entityItem4 = _findLink(entityItem);
/*  854 */                               EntityItem entityItem5 = _findLink(entityItem4);
/*  855 */                               EntityItem entityItem6 = _findLink(entityItem5);
/*  856 */                               EntityItem entityItem7 = _findLink(entityItem6);
/*  857 */                               EntityItem entityItem8 = _findLink(entityItem7);
/*  858 */                               EntityItem entityItem9 = _findLink(entityItem8);
/*  859 */                               EntityItem[] arrayOfEntityItem = findLink2(entityItem9, str2, str1);
/*  860 */                               int i3 = s2 + 1;
/*  861 */                               for (byte b5 = 0; b5 < arrayOfEntityItem.length; b5++) {
/*  862 */                                 entityItem2 = arrayOfEntityItem[b5];
/*  863 */                                 ProcessXL(hSSFSheet, hSSFRow, hSSFCell, entityItem2, entityGroup, eANMetaAttribute, str9, s2, s1, i3);
/*  864 */                                 i3++;
/*      */                               } 
/*  866 */                             } else if (i1 == 9) {
/*  867 */                               System.out.println("*** with oo == 9: " + entityItem
/*  868 */                                   .getEntityType() + entityItem
/*  869 */                                   .getEntityID() + "***sEg1: " + str2 + "***sRel" + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */                               
/*  874 */                               EntityItem entityItem4 = _findLink(entityItem);
/*  875 */                               EntityItem entityItem5 = _findLink(entityItem4);
/*  876 */                               EntityItem entityItem6 = _findLink(entityItem5);
/*  877 */                               EntityItem entityItem7 = _findLink(entityItem6);
/*  878 */                               EntityItem entityItem8 = _findLink(entityItem7);
/*  879 */                               EntityItem entityItem9 = _findLink(entityItem8);
/*  880 */                               EntityItem entityItem10 = _findLink(entityItem9);
/*  881 */                               EntityItem[] arrayOfEntityItem = findLink2(entityItem10, str2, str1);
/*  882 */                               int i3 = s2 + 1;
/*  883 */                               for (byte b5 = 0; b5 < arrayOfEntityItem.length; b5++) {
/*  884 */                                 entityItem2 = arrayOfEntityItem[b5];
/*  885 */                                 ProcessXL(hSSFSheet, hSSFRow, hSSFCell, entityItem2, entityGroup, eANMetaAttribute, str9, s2, s1, i3);
/*  886 */                                 i3++;
/*      */                               } 
/*      */                             } else {
/*  889 */                               System.out.println("too many relators");
/*      */                             } 
/*      */                           } 
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } else {
/*      */                     
/*  897 */                     System.out.println("---------------------we're here *: -------------------------" + arrayOfString);
/*      */ 
/*      */                     
/*  900 */                     if (entityGroup != null) {
/*  901 */                       String str = entityGroup.getEntityType();
/*  902 */                       EntityGroup entityGroup1 = new EntityGroup(null, paramDatabase, paramProfile, str, "Edit", true);
/*  903 */                       metaColumnOrderGroup = entityGroup1.getMetaColumnOrderGroup();
/*  904 */                       System.out.println("strEntityType" + str + "eg1: " + entityGroup1);
/*      */ 
/*      */                       
/*  907 */                       for (byte b3 = 0; b3 < metaColumnOrderGroup.getMetaColumnOrderItemCount(); b3++) {
/*  908 */                         MetaColumnOrderItem metaColumnOrderItem = metaColumnOrderGroup.getMetaColumnOrderItem(b3);
/*  909 */                         s1 = (short)(s1 + 1);
/*  910 */                         String str9 = metaColumnOrderItem.getAttributeCode();
/*  911 */                         System.out.println("strAttrCode" + str9);
/*      */                         
/*      */                         short s2;
/*  914 */                         for (s2 = 0; s2 < entityGroup.getEntityItemCount(); s2 = (short)(s2 + 1)) {
/*  915 */                           entityItem2 = entityGroup.getEntityItem(s2);
/*  916 */                           s = s2;
/*  917 */                           System.out.println("***_eitem: " + entityItem2.getEntityType() + entityItem2.getEntityID() + ": " + entityGroup.getEntityItemCount() + "- " + s2);
/*      */                           
/*  919 */                           String str10 = metaColumnOrderItem.getAttributeDescription();
/*      */                           
/*  921 */                           if (str10.length() > 0 && str10.charAt(0) == '*') {
/*  922 */                             str10 = str10.substring(1);
/*  923 */                             System.out.println("_strAttributeDesc" + str10);
/*      */                           } 
/*      */                           
/*  926 */                           System.out.println(": strAttrCode: " + str9);
/*  927 */                           EANAttribute eANAttribute = entityItem2.getAttribute(str9);
/*      */                           
/*  929 */                           short s3 = 0;
/*  930 */                           System.out.println("_iCellNum: " + s3 + "_z" + s1);
/*  931 */                           hSSFRow = hSSFSheet.createRow(s3);
/*  932 */                           hSSFSheet.setColumnWidth(s1, 8000);
/*  933 */                           hSSFCell = hSSFRow.createCell(s1);
/*  934 */                           hSSFCell.setCellValue(str10);
/*      */                           
/*  936 */                           s3 = (short)(s3 + 1);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/*  941 */                           String str11 = null;
/*  942 */                           if (eANAttribute == null) {
/*  943 */                             System.out.println("att is null");
/*  944 */                             str11 = " ";
/*      */                           } else {
/*  946 */                             str11 = eANAttribute.toString();
/*      */                             
/*  948 */                             if (str11.length() > 0 && str11.charAt(0) == '*') {
/*  949 */                               str11 = str11.substring(1);
/*  950 */                               System.out.println("_strValue" + str11);
/*      */                             } 
/*  952 */                             System.out.println("****_strValue: ****" + entityItem2.getEntityType() + entityItem2.getEntityID() + str11);
/*      */                           } 
/*      */ 
/*      */                           
/*  956 */                           int i1 = s + 1;
/*      */                           
/*  958 */                           System.out.println("strValue1: " + entityItem2.getEntityType() + entityItem2.getEntityID() + str11 + "_iRow: " + i1 + "_z: " + s1);
/*  959 */                           hSSFRow = hSSFSheet.createRow(i1);
/*  960 */                           hSSFSheet.setColumnWidth(s, 8000);
/*  961 */                           hSSFCell = hSSFRow.createCell(s1);
/*  962 */                           hSSFCell.setCellValue(str11);
/*  963 */                           i1++;
/*      */                           
/*  965 */                           System.out.println("---------------------done with *: -------------------------");
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             try {
/*  974 */               String str = paramString + ".xls";
/*  975 */               File file = new File(str);
/*  976 */               this.out = new FileOutputStream(file);
/*  977 */               this.wb.write(this.out);
/*  978 */               this.out.close();
/*  979 */               System.out.println("-------------done generate XL----------------");
/*  980 */             } catch (IOException iOException) {
/*  981 */               iOException.printStackTrace();
/*      */             } 
/*  983 */           } catch (Exception exception) {
/*  984 */             System.out.println("Badness in mcog" + exception);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       return;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean stringCheck(String paramString) {
/*  993 */     int i = paramString.length();
/*  994 */     char[] arrayOfChar = { '.', ':' };
/*      */ 
/*      */     
/*  997 */     for (byte b = 0; b < i; b++) {
/*  998 */       for (byte b1 = 0; arrayOfChar.length > b1; b1++) {
/*      */         
/* 1000 */         if (paramString.charAt(b) == arrayOfChar[b1]) {
/* 1001 */           return (paramString.charAt(b) == arrayOfChar[b1]);
/*      */         }
/*      */       } 
/*      */     } 
/* 1005 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] getWBBytes() {
/* 1010 */     byte[] arrayOfByte = null;
/*      */     
/* 1012 */     arrayOfByte = getBytes();
/* 1013 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1017 */     return this.out.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void main(String[] paramArrayOfString) {}
/*      */   
/*      */   public byte[] getBytes() {
/* 1024 */     byte[] arrayOfByte = null;
/*      */     try {
/* 1026 */       this.wb.write(this.baos);
/* 1027 */       if (this.baos.size() > 0) {
/* 1028 */         arrayOfByte = this.baos.toByteArray();
/* 1029 */         System.out.println("baos's size is greater than 0");
/*      */       } else {
/* 1031 */         System.out.println("badness in baos");
/*      */       } 
/* 1033 */     } catch (Exception exception) {
/* 1034 */       System.out.println("Badness" + exception);
/*      */     } 
/* 1036 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String findRelator(String paramString) {
/* 1042 */     String str = null;
/* 1043 */     System.out.println("findRelator: " + paramString);
/*      */     
/* 1045 */     int i = paramString.indexOf(".");
/*      */     
/* 1047 */     if (i < 0) {
/* 1048 */       str = paramString;
/* 1049 */       System.out.println("***_sRel: " + str);
/* 1050 */       return str;
/*      */     } 
/* 1052 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   public String findEntity(String paramString) {
/* 1057 */     String str = null;
/* 1058 */     System.out.println("findEntity: " + paramString);
/*      */     
/* 1060 */     int i = paramString.indexOf(".");
/*      */     
/* 1062 */     if (i >= 0) {
/* 1063 */       str = paramString.substring(0, i);
/* 1064 */       System.out.println("***sEg1: " + str);
/* 1065 */       return str;
/*      */     } 
/* 1067 */     return str;
/*      */   }
/*      */   
/*      */   public String findAtt(String paramString) {
/* 1071 */     String str = null;
/* 1072 */     System.out.println("findAtt: " + paramString);
/*      */     
/* 1074 */     int i = paramString.indexOf(".");
/*      */     
/* 1076 */     if (i >= 0) {
/* 1077 */       str = paramString.substring(i + 1);
/* 1078 */       System.out.println("***sAtt1: " + str);
/* 1079 */       return str;
/*      */     } 
/* 1081 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public HSSFCell ProcessXL(HSSFSheet paramHSSFSheet, HSSFRow paramHSSFRow, HSSFCell paramHSSFCell, EntityItem paramEntityItem, EntityGroup paramEntityGroup, EANMetaAttribute paramEANMetaAttribute, String paramString, short paramShort1, short paramShort2, int paramInt) {
/* 1087 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 1088 */     System.out.println("***ProcessXL_eitem: " + paramEntityItem
/* 1089 */         .getEntityType() + paramEntityItem
/* 1090 */         .getEntityID() + "x: " + paramShort1 + "iRow: " + paramInt + paramEntityGroup
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1095 */         .getEntityType());
/* 1096 */     String str1 = paramEANMetaAttribute.toString();
/*      */     
/* 1098 */     if (str1.length() > 0 && str1.charAt(0) == '*') {
/* 1099 */       str1 = str1.substring(1);
/* 1100 */       System.out.println("_strAttributeDesc" + str1);
/*      */     } 
/*      */ 
/*      */     
/* 1104 */     short s = 0;
/* 1105 */     paramHSSFRow = paramHSSFSheet.createRow(s);
/* 1106 */     paramHSSFSheet.setColumnWidth(paramShort2, 8000);
/* 1107 */     paramHSSFCell = paramHSSFRow.createCell(paramShort2);
/* 1108 */     paramHSSFCell.setCellValue("*" + str1);
/* 1109 */     s = (short)(s + 1);
/*      */ 
/*      */ 
/*      */     
/* 1113 */     String str2 = null;
/* 1114 */     if (eANAttribute == null) {
/* 1115 */       System.out.println("att is null");
/* 1116 */       str2 = " ";
/*      */     } else {
/* 1118 */       str2 = eANAttribute.toString();
/*      */       
/* 1120 */       if (str2.length() > 0 && str2.charAt(0) == '*') {
/* 1121 */         str2 = str2.substring(1);
/* 1122 */         System.out.println("_strValue" + str2);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     paramHSSFRow = paramHSSFSheet.createRow(paramInt);
/* 1129 */     paramHSSFSheet.setColumnWidth(paramShort2, 8000);
/* 1130 */     paramHSSFCell = paramHSSFRow.createCell(paramShort2);
/* 1131 */     paramHSSFCell.setCellValue(str2);
/* 1132 */     return paramHSSFCell;
/*      */   }
/*      */   
/*      */   public EntityItem[] findLink2(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 1136 */     Vector<EntityItem> vector = new Vector();
/* 1137 */     EntityItem entityItem = null;
/* 1138 */     System.out.println("Starting findLink2: " + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "sEg1:" + paramString1 + "_sRel" + paramString2);
/*      */     
/* 1140 */     if (paramEntityItem.hasUpLinks()) {
/* 1141 */       for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*      */         
/* 1143 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getUpLink(b);
/* 1144 */         if (entityItem1 != null && entityItem1.getEntityType().equals(paramString2)) {
/* 1145 */           for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/*      */             
/* 1147 */             entityItem = (EntityItem)entityItem1.getUpLink(b1);
/*      */             
/* 1149 */             if (entityItem != null && entityItem.getEntityType().equals(paramString1)) {
/* 1150 */               vector.addElement(entityItem);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1158 */     if (paramEntityItem.hasDownLinks())
/*      */     {
/* 1160 */       for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*      */         
/* 1162 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 1163 */         if (entityItem1 != null && entityItem1.getEntityType().equals(paramString2)) {
/* 1164 */           for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/*      */             
/* 1166 */             entityItem = (EntityItem)entityItem1.getDownLink(b1);
/*      */             
/* 1168 */             if (entityItem != null && entityItem.getEntityType().equals(paramString1)) {
/* 1169 */               vector.addElement(entityItem);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1177 */     EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/* 1178 */     vector.toArray(arrayOfEntityItem);
/* 1179 */     return arrayOfEntityItem;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem findLink3(EntityItem paramEntityItem, String paramString) {
/* 1184 */     EntityItem entityItem1 = null;
/* 1185 */     EntityItem entityItem2 = null;
/* 1186 */     EntityItem entityItem3 = null;
/* 1187 */     EntityItem entityItem4 = null;
/* 1188 */     System.out.println("findLink3: " + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "sEg1:" + paramString);
/*      */     
/* 1190 */     if (paramEntityItem.hasUpLinks()) {
/* 1191 */       for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 1192 */         EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 1193 */         System.out.println("_ei - level 1" + entityItem.getEntityType() + entityItem.getEntityID());
/* 1194 */         if (entityItem != null && entityItem.getEntityType().equals(paramString)) {
/* 1195 */           return entityItem;
/*      */         }
/* 1197 */         if (entityItem.hasUpLinks()) {
/* 1198 */           for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 1199 */             entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/* 1200 */             System.out.println("findLink3 level 2 - 2: " + entityItem1.getEntityType() + entityItem1.getEntityID() + "sEg1:" + paramString);
/* 1201 */             if (entityItem1 != null && entityItem1.getEntityType().equals(paramString)) {
/* 1202 */               return entityItem1;
/*      */             }
/* 1204 */             if (entityItem1.hasUpLinks()) {
/* 1205 */               for (byte b2 = 0; b2 < entityItem1.getUpLinkCount(); b2++) {
/* 1206 */                 entityItem2 = (EntityItem)entityItem1.getUpLink(b2);
/* 1207 */                 System.out.println("findLink3 level -3 3: " + entityItem2.getEntityType() + entityItem2.getEntityID() + "sEg1:" + paramString);
/* 1208 */                 if (entityItem2 != null && entityItem2.getEntityType().equals(paramString)) {
/* 1209 */                   return entityItem2;
/*      */                 }
/* 1211 */                 if (entityItem2.hasUpLinks()) {
/* 1212 */                   for (byte b3 = 0; b3 < entityItem2.getUpLinkCount(); b3++) {
/* 1213 */                     entityItem3 = (EntityItem)entityItem2.getUpLink(b3);
/* 1214 */                     System.out.println("findLink3 level 4 - 4: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1215 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1216 */                       return entityItem3;
/*      */                     }
/* 1218 */                     if (entityItem3.hasUpLinks()) {
/* 1219 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1220 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1221 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1222 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1223 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1227 */                     if (entityItem3.hasDownLinks()) {
/* 1228 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1229 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1230 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1231 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1232 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/* 1238 */                 if (entityItem2.hasDownLinks()) {
/* 1239 */                   for (byte b3 = 0; b3 < entityItem2.getDownLinkCount(); b3++) {
/* 1240 */                     entityItem3 = (EntityItem)entityItem2.getDownLink(b3);
/* 1241 */                     System.out.println("findLink3 level 4 - 5: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1242 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1243 */                       return entityItem3;
/*      */                     }
/* 1245 */                     if (entityItem3.hasUpLinks()) {
/* 1246 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1247 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1248 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1249 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1250 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1254 */                     if (entityItem3.hasDownLinks()) {
/* 1255 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1256 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1257 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1258 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1259 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             }
/*      */             
/* 1268 */             if (entityItem1.hasDownLinks()) {
/* 1269 */               for (byte b2 = 0; b2 < entityItem1.getDownLinkCount(); b2++) {
/* 1270 */                 entityItem2 = (EntityItem)entityItem1.getDownLink(b2);
/* 1271 */                 System.out.println("findLink3 level 3 - 6: " + entityItem2.getEntityType() + entityItem2.getEntityID() + "sEg1:" + paramString);
/* 1272 */                 if (entityItem2 != null && entityItem2.getEntityType().equals(paramString)) {
/* 1273 */                   return entityItem2;
/*      */                 }
/* 1275 */                 if (entityItem2.hasUpLinks()) {
/* 1276 */                   for (byte b3 = 0; b3 < entityItem2.getUpLinkCount(); b3++) {
/* 1277 */                     entityItem3 = (EntityItem)entityItem2.getUpLink(b3);
/* 1278 */                     System.out.println("findLink3 level 4 - 7: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1279 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1280 */                       return entityItem3;
/*      */                     }
/* 1282 */                     if (entityItem3.hasUpLinks()) {
/* 1283 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1284 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1285 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1286 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1287 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1291 */                     if (entityItem3.hasDownLinks()) {
/* 1292 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1293 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1294 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1295 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1296 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/* 1302 */                 if (entityItem2.hasDownLinks()) {
/* 1303 */                   for (byte b3 = 0; b3 < entityItem2.getDownLinkCount(); b3++) {
/* 1304 */                     entityItem3 = (EntityItem)entityItem2.getDownLink(b3);
/* 1305 */                     System.out.println("findLink3 level 4 - 8: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1306 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1307 */                       return entityItem3;
/*      */                     }
/* 1309 */                     if (entityItem3.hasUpLinks()) {
/* 1310 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1311 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1312 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1313 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1314 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1318 */                     if (entityItem3.hasDownLinks()) {
/* 1319 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1320 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1321 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1322 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1323 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/* 1333 */         if (entityItem.hasDownLinks()) {
/* 1334 */           for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 1335 */             entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/* 1336 */             System.out.println("findLink3 level 2 - 9: " + entityItem1.getEntityType() + entityItem1.getEntityID() + "sEg1:" + paramString);
/* 1337 */             if (entityItem1 != null && entityItem1.getEntityType().equals(paramString)) {
/* 1338 */               return entityItem1;
/*      */             }
/* 1340 */             if (entityItem1.hasUpLinks()) {
/* 1341 */               for (byte b2 = 0; b2 < entityItem1.getUpLinkCount(); b2++) {
/* 1342 */                 entityItem2 = (EntityItem)entityItem1.getUpLink(b2);
/* 1343 */                 System.out.println("findLink3 level 3 - 10: " + entityItem2.getEntityType() + entityItem2.getEntityID() + "sEg1:" + paramString);
/* 1344 */                 if (entityItem2 != null && entityItem2.getEntityType().equals(paramString)) {
/* 1345 */                   return entityItem2;
/*      */                 }
/* 1347 */                 if (entityItem2.hasUpLinks()) {
/* 1348 */                   for (byte b3 = 0; b3 < entityItem2.getUpLinkCount(); b3++) {
/* 1349 */                     entityItem3 = (EntityItem)entityItem2.getUpLink(b3);
/* 1350 */                     System.out.println("findLink3 level 4 - 11: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1351 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1352 */                       return entityItem3;
/*      */                     }
/* 1354 */                     if (entityItem3.hasUpLinks()) {
/* 1355 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1356 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1357 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1358 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1359 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1363 */                     if (entityItem3.hasDownLinks()) {
/* 1364 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1365 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1366 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1367 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1368 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/* 1374 */                 if (entityItem2.hasDownLinks()) {
/* 1375 */                   for (byte b3 = 0; b3 < entityItem2.getDownLinkCount(); b3++) {
/* 1376 */                     entityItem3 = (EntityItem)entityItem2.getDownLink(b3);
/* 1377 */                     System.out.println("findLink3 level 4 - 12: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1378 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1379 */                       return entityItem3;
/*      */                     }
/* 1381 */                     if (entityItem3.hasUpLinks()) {
/* 1382 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1383 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1384 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1385 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1386 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1390 */                     if (entityItem3.hasDownLinks()) {
/* 1391 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1392 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1393 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1394 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1395 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             }
/*      */             
/* 1404 */             if (entityItem1.hasDownLinks()) {
/* 1405 */               for (byte b2 = 0; b2 < entityItem1.getDownLinkCount(); b2++) {
/* 1406 */                 entityItem2 = (EntityItem)entityItem1.getDownLink(b2);
/* 1407 */                 System.out.println("findLink3 level3 - 13: " + entityItem2.getEntityType() + entityItem2.getEntityID() + "sEg1:" + paramString);
/* 1408 */                 if (entityItem2 != null && entityItem2.getEntityType().equals(paramString)) {
/* 1409 */                   return entityItem2;
/*      */                 }
/* 1411 */                 if (entityItem2.hasUpLinks()) {
/* 1412 */                   for (byte b3 = 0; b3 < entityItem2.getUpLinkCount(); b3++) {
/* 1413 */                     entityItem3 = (EntityItem)entityItem2.getUpLink(b3);
/* 1414 */                     System.out.println("findLink3 level 4 - 14: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1415 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1416 */                       return entityItem3;
/*      */                     }
/* 1418 */                     if (entityItem3.hasUpLinks()) {
/* 1419 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1420 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1421 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1422 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1423 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1427 */                     if (entityItem3.hasDownLinks()) {
/* 1428 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1429 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1430 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1431 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1432 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/* 1438 */                 if (entityItem2.hasDownLinks()) {
/* 1439 */                   for (byte b3 = 0; b3 < entityItem2.getDownLinkCount(); b3++) {
/* 1440 */                     entityItem3 = (EntityItem)entityItem2.getDownLink(b3);
/* 1441 */                     System.out.println("findLink3 level 4 - 15: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1442 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1443 */                       return entityItem3;
/*      */                     }
/* 1445 */                     if (entityItem3.hasUpLinks()) {
/* 1446 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1447 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1448 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1449 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1450 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1454 */                     if (entityItem3.hasDownLinks()) {
/* 1455 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1456 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1457 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1458 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1459 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 1471 */     if (paramEntityItem.hasDownLinks()) {
/* 1472 */       for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 1473 */         EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 1474 */         System.out.println("eidownlink level 1" + entityItem.getEntityType() + entityItem.getEntityID());
/* 1475 */         if (entityItem != null && entityItem.getEntityType().equals(paramString)) {
/* 1476 */           System.out.println("findLink3 16: " + entityItem.getEntityType() + entityItem.getEntityID() + "sEg1:" + paramString);
/* 1477 */           return entityItem;
/*      */         } 
/* 1479 */         if (entityItem.hasUpLinks()) {
/* 1480 */           for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 1481 */             entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/* 1482 */             System.out.println("findLink3 level 2 - 17: " + entityItem1.getEntityType() + entityItem1.getEntityID() + "sEg1:" + paramString);
/* 1483 */             if (entityItem1 != null && entityItem1.getEntityType().equals(paramString)) {
/* 1484 */               return entityItem1;
/*      */             }
/* 1486 */             if (entityItem1.hasUpLinks()) {
/* 1487 */               for (byte b2 = 0; b2 < entityItem1.getUpLinkCount(); b2++) {
/* 1488 */                 entityItem2 = (EntityItem)entityItem1.getUpLink(b2);
/* 1489 */                 System.out.println("findLink3 level 3 - 18: " + entityItem2.getEntityType() + entityItem2.getEntityID() + "sEg1:" + paramString);
/* 1490 */                 if (entityItem2 != null && entityItem2.getEntityType().equals(paramString)) {
/* 1491 */                   return entityItem2;
/*      */                 }
/* 1493 */                 if (entityItem2.hasUpLinks()) {
/* 1494 */                   for (byte b3 = 0; b3 < entityItem2.getUpLinkCount(); b3++) {
/* 1495 */                     entityItem3 = (EntityItem)entityItem2.getUpLink(b3);
/* 1496 */                     System.out.println("findLink3 level 4 - 19: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1497 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1498 */                       return entityItem3;
/*      */                     }
/* 1500 */                     if (entityItem3.hasUpLinks()) {
/* 1501 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1502 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1503 */                         System.out.println("findLink3 level 5 - 20: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1504 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1505 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1509 */                     if (entityItem3.hasDownLinks()) {
/* 1510 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1511 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1512 */                         System.out.println("findLink3 level 5 - 21: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1513 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1514 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/* 1520 */                 if (entityItem2.hasDownLinks()) {
/* 1521 */                   byte b3; for (b3 = 0; b3 < entityItem2.getDownLinkCount(); b3++) {
/* 1522 */                     entityItem3 = (EntityItem)entityItem2.getDownLink(b3);
/* 1523 */                     System.out.println("findLink3 level 4 - 22: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1524 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1525 */                       return entityItem3;
/*      */                     }
/*      */                   } 
/* 1528 */                   if (entityItem3.hasUpLinks()) {
/* 1529 */                     for (b3 = 0; b3 < entityItem3.getUpLinkCount(); b3++) {
/* 1530 */                       entityItem4 = (EntityItem)entityItem3.getUpLink(b3);
/* 1531 */                       System.out.println("findLink3 level 5 - 23: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1532 */                       if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1533 */                         return entityItem4;
/*      */                       }
/*      */                     } 
/*      */                   }
/* 1537 */                   if (entityItem3.hasDownLinks()) {
/* 1538 */                     for (b3 = 0; b3 < entityItem3.getDownLinkCount(); b3++) {
/* 1539 */                       entityItem4 = (EntityItem)entityItem3.getDownLink(b3);
/* 1540 */                       System.out.println("findLink3 level 5 - 24: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1541 */                       if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1542 */                         return entityItem4;
/*      */                       }
/*      */                     } 
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             }
/*      */             
/* 1550 */             if (entityItem1.hasDownLinks()) {
/* 1551 */               for (byte b2 = 0; b2 < entityItem1.getDownLinkCount(); b2++) {
/* 1552 */                 entityItem2 = (EntityItem)entityItem1.getDownLink(b2);
/* 1553 */                 System.out.println("findLink3 level 3 - 25: " + entityItem2.getEntityType() + entityItem2.getEntityID() + "sEg1:" + paramString);
/* 1554 */                 if (entityItem2 != null && entityItem2.getEntityType().equals(paramString)) {
/* 1555 */                   return entityItem2;
/*      */                 }
/* 1557 */                 if (entityItem2.hasUpLinks()) {
/* 1558 */                   for (byte b3 = 0; b3 < entityItem2.getUpLinkCount(); b3++) {
/* 1559 */                     entityItem3 = (EntityItem)entityItem2.getUpLink(b3);
/* 1560 */                     System.out.println("findLink3 level 4 - 26: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1561 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1562 */                       return entityItem3;
/*      */                     }
/* 1564 */                     if (entityItem3.hasUpLinks()) {
/* 1565 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1566 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1567 */                         System.out.println("findLink3 level 5 - 27: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1568 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1569 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1573 */                     if (entityItem3.hasDownLinks()) {
/* 1574 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1575 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1576 */                         System.out.println("findLink3 level 5 - 28: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1577 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1578 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/* 1584 */                 if (entityItem2.hasDownLinks()) {
/* 1585 */                   for (byte b3 = 0; b3 < entityItem2.getDownLinkCount(); b3++) {
/* 1586 */                     entityItem3 = (EntityItem)entityItem2.getDownLink(b3);
/* 1587 */                     System.out.println("findLink3 level - 4 29: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1588 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1589 */                       return entityItem3;
/*      */                     }
/* 1591 */                     if (entityItem3.hasUpLinks()) {
/* 1592 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1593 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1594 */                         System.out.println("findLink3 level - 5 30: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1595 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1596 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1600 */                     if (entityItem3.hasDownLinks()) {
/* 1601 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1602 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1603 */                         System.out.println("findLink3 level - 5 31: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1604 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1605 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/* 1615 */         if (entityItem.hasDownLinks()) {
/* 1616 */           for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 1617 */             entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/* 1618 */             System.out.println("findLink3 level 2 - 32: " + entityItem1.getEntityType() + entityItem1.getEntityID() + "sEg1:" + paramString);
/* 1619 */             if (entityItem1 != null && entityItem1.getEntityType().equals(paramString)) {
/* 1620 */               return entityItem1;
/*      */             }
/* 1622 */             if (entityItem1.hasUpLinks()) {
/* 1623 */               for (byte b2 = 0; b2 < entityItem1.getUpLinkCount(); b2++) {
/* 1624 */                 entityItem2 = (EntityItem)entityItem1.getUpLink(b2);
/* 1625 */                 System.out.println("findLink3 level 3 - 33: " + entityItem2.getEntityType() + entityItem2.getEntityID() + "sEg1:" + paramString);
/* 1626 */                 if (entityItem2 != null && entityItem2.getEntityType().equals(paramString)) {
/* 1627 */                   return entityItem2;
/*      */                 }
/* 1629 */                 if (entityItem2.hasUpLinks()) {
/* 1630 */                   for (byte b3 = 0; b3 < entityItem2.getUpLinkCount(); b3++) {
/* 1631 */                     entityItem3 = (EntityItem)entityItem2.getUpLink(b3);
/* 1632 */                     System.out.println("findLink3 level 4 - 34: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1633 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1634 */                       return entityItem3;
/*      */                     }
/* 1636 */                     if (entityItem3.hasUpLinks()) {
/* 1637 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1638 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1639 */                         System.out.println("findLink3 level - 5 35: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1640 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1641 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1645 */                     if (entityItem3.hasDownLinks()) {
/* 1646 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1647 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1648 */                         System.out.println("findLink3 level - 5 36: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1649 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1650 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/* 1656 */                 if (entityItem2.hasDownLinks()) {
/* 1657 */                   for (byte b3 = 0; b3 < entityItem2.getDownLinkCount(); b3++) {
/* 1658 */                     entityItem3 = (EntityItem)entityItem2.getDownLink(b3);
/* 1659 */                     System.out.println("findLink3 level -4 37: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1660 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1661 */                       return entityItem3;
/*      */                     }
/* 1663 */                     if (entityItem3.hasUpLinks()) {
/* 1664 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1665 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1666 */                         System.out.println("findLink3 level - 5 38: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1667 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1668 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1672 */                     if (entityItem3.hasDownLinks()) {
/* 1673 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1674 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1675 */                         System.out.println("findLink3 level - 5 39: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1676 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1677 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             }
/*      */             
/* 1686 */             if (entityItem1.hasDownLinks()) {
/* 1687 */               for (byte b2 = 0; b2 < entityItem1.getDownLinkCount(); b2++) {
/* 1688 */                 entityItem2 = (EntityItem)entityItem1.getDownLink(b2);
/* 1689 */                 System.out.println("findLink3 level - 3 40: " + entityItem2.getEntityType() + entityItem2.getEntityID() + "sEg1:" + paramString);
/* 1690 */                 if (entityItem2 != null && entityItem2.getEntityType().equals(paramString)) {
/* 1691 */                   return entityItem2;
/*      */                 }
/* 1693 */                 if (entityItem2.hasUpLinks()) {
/* 1694 */                   for (byte b3 = 0; b3 < entityItem2.getUpLinkCount(); b3++) {
/* 1695 */                     entityItem3 = (EntityItem)entityItem2.getUpLink(b3);
/* 1696 */                     System.out.println("findLink3 level 4 - 41: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1697 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1698 */                       return entityItem3;
/*      */                     }
/* 1700 */                     if (entityItem3.hasUpLinks()) {
/* 1701 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1702 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1703 */                         System.out.println("findLink3 level - 5 42: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1704 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1705 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1709 */                     if (entityItem3.hasDownLinks()) {
/* 1710 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1711 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1712 */                         System.out.println("findLink3 level - 5 43: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1713 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1714 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/* 1720 */                 if (entityItem2.hasDownLinks()) {
/* 1721 */                   for (byte b3 = 0; b3 < entityItem2.getDownLinkCount(); b3++) {
/* 1722 */                     entityItem3 = (EntityItem)entityItem2.getDownLink(b3);
/* 1723 */                     System.out.println("findLink3 level 4 - 44: " + entityItem3.getEntityType() + entityItem3.getEntityID() + "sEg1:" + paramString);
/* 1724 */                     if (entityItem3 != null && entityItem3.getEntityType().equals(paramString)) {
/* 1725 */                       return entityItem3;
/*      */                     }
/* 1727 */                     if (entityItem3.hasUpLinks()) {
/* 1728 */                       for (byte b4 = 0; b4 < entityItem3.getUpLinkCount(); b4++) {
/* 1729 */                         entityItem4 = (EntityItem)entityItem3.getUpLink(b4);
/* 1730 */                         System.out.println("findLink3 level - 5 45: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1731 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1732 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/* 1736 */                     if (entityItem3.hasDownLinks()) {
/* 1737 */                       for (byte b4 = 0; b4 < entityItem3.getDownLinkCount(); b4++) {
/* 1738 */                         entityItem4 = (EntityItem)entityItem3.getDownLink(b4);
/* 1739 */                         System.out.println("findLink3 level - 5 46: " + entityItem4.getEntityType() + entityItem4.getEntityID() + "sEg1:" + paramString);
/* 1740 */                         if (entityItem4 != null && entityItem4.getEntityType().equals(paramString)) {
/* 1741 */                           return entityItem4;
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 1753 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem _findLink(EntityItem paramEntityItem) {
/* 1759 */     EntityItem entityItem = null;
/* 1760 */     System.out.println("_findLink: " + paramEntityItem.getEntityType() + paramEntityItem.getEntityID());
/*      */     
/* 1762 */     if (paramEntityItem.hasUpLinks()) {
/* 1763 */       for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 1764 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getUpLink(b);
/* 1765 */         for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/* 1766 */           entityItem = (EntityItem)entityItem1.getUpLink(b1);
/* 1767 */           System.out.println("_findLink eiReturn: " + entityItem.getEntityType() + entityItem.getEntityID());
/* 1768 */           if (entityItem != null) {
/* 1769 */             return entityItem;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 1774 */     if (paramEntityItem.hasDownLinks())
/*      */     {
/* 1776 */       for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 1777 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 1778 */         for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/* 1779 */           entityItem = (EntityItem)entityItem1.getDownLink(b1);
/* 1780 */           System.out.println("_findLink eiReturn: " + entityItem.getEntityType() + entityItem.getEntityID());
/* 1781 */           if (entityItem != null) {
/* 1782 */             return entityItem;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1788 */     return entityItem;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem findLink(EntityItem paramEntityItem, String paramString) {
/* 1793 */     EntityItem entityItem = null;
/* 1794 */     System.out.println("findLink: " + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "sEg1:" + paramString);
/*      */     
/* 1796 */     if (paramEntityItem.hasUpLinks()) {
/* 1797 */       for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 1798 */         entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 1799 */         System.out.println("findLink eiReturn: " + entityItem.getEntityType() + entityItem.getEntityID() + "sEg1:" + paramString);
/* 1800 */         if (entityItem != null && entityItem.getEntityType().equals(paramString)) {
/* 1801 */           return entityItem;
/*      */         }
/*      */       } 
/*      */     }
/* 1805 */     if (paramEntityItem.hasDownLinks())
/*      */     {
/* 1807 */       for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 1808 */         entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 1809 */         System.out.println("findLink eiReturn: " + entityItem.getEntityType() + entityItem.getEntityID() + "sEg1:" + paramString);
/* 1810 */         if (entityItem != null && entityItem.getEntityType().equals(paramString)) {
/* 1811 */           return entityItem;
/*      */         }
/*      */       } 
/*      */     }
/* 1815 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\GenerateXL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */