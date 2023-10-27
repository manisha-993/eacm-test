/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.TreeMap;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMLAVAILElemFCT extends XMLElem {
/*  27 */   private static String countryStr = "1427,1428,1429,1430,1431,1432,1433,1434,1435,1436,1437,1438,1439,1440,1441,1442,1443,1444,1445,1446,1447,1448,1449,1450,1451,1452,1453,1454,1455,1456,1457,1458,1459,1460,1461,1462,1463,1464,1465,1466,1467,1468,1469,1470,1471,1472,1473,1474,1475,1476,1477,1478,1479,1480,1481,1482,1483,1484,1485,1486,1487,1488,1489,1490,1491,1492,1493,1494,1495,1497,1498,1499,1500,1501,1502,1503,1504,1505,1506,1507,1508,1509,1510,1511,1512,1513,1514,1515,1516,1517,1518,1519,1520,1521,1522,1523,1524,1525,1526,1527,1528,1529,1530,1531,1532,1533,1534,1535,1536,1537,1538,1539,1540,1541,1542,1543,1544,1545,1546,1547,1548,1549,1550,1551,1552,1553,1554,1555,1556,1557,1558,1559,1560,1561,1562,1563,1564,1565,1566,1567,1568,1569,1570,1571,1572,1573,1574,1575,1576,1577,1578,1580,1581,1582,1583,1584,1585,1586,1587,1588,1589,1590,1591,1592,1593,1594,1595,1596,1597,1598,1599,1600,1601,1602,1603,1604,1605,1606,1607,1608,1609,1610,1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1622,1623,1624,1625,1626,1627,1628,1629,1630,1631,1632,1633,1634,1635,1636,1637,1638,1639,1640,1641,1642,1643,1644,1645,1646,1647,1648,1649,1650,1651,1652,1653,1654,1655,1656,1657,1658,1659,1660,1661,1662,1663,1664,1665,1666,1668,1669,1670";
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLAVAILElemFCT() {
/*  32 */     super("COUNTRYELEMENT");
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
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  55 */     Element element = paramDocument.createElement("COUNTRYLIST");
/*  56 */     addXMLAttrs(element);
/*  57 */     paramElement.appendChild(element);
/*     */     
/*  59 */     Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramStringBuffer);
/*     */     
/*  61 */     if (vector.size() > 0) {
/*     */ 
/*     */ 
/*     */       
/*  65 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  66 */       for (byte b = 0; b < vector.size(); b++) {
/*  67 */         DiffEntity diffEntity = vector.elementAt(b);
/*  68 */         buildCtryAudRecs(treeMap, diffEntity, paramStringBuffer);
/*     */       } 
/*     */ 
/*     */       
/*  72 */       Collection collection = treeMap.values();
/*  73 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*     */ 
/*     */       
/*  76 */       while (iterator.hasNext()) {
/*  77 */         CtryAudRecord ctryAudRecord = iterator.next();
/*     */ 
/*     */         
/*  80 */         if (ctryAudRecord.isDisplayable()) {
/*  81 */           createNodeSet(paramDocument, element, ctryAudRecord, paramStringBuffer);
/*     */         } else {
/*  83 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*     */         } 
/*  85 */         ctryAudRecord.dereference();
/*     */       } 
/*     */ 
/*     */       
/*  89 */       treeMap.clear();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  96 */       String[] arrayOfString = countryStr.split(",");
/*     */       
/*  98 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*     */         
/* 100 */         Element element1 = paramDocument.createElement(this.nodeName);
/* 101 */         addXMLAttrs(element1);
/* 102 */         element.appendChild(element1);
/*     */ 
/*     */         
/* 105 */         Element element2 = paramDocument.createElement("COUNTRYACTION");
/* 106 */         element2.appendChild(paramDocument.createTextNode("Update"));
/* 107 */         element1.appendChild(element2);
/*     */         
/* 109 */         element2 = paramDocument.createElement("COUNTRY_FC");
/* 110 */         element2.appendChild(paramDocument.createTextNode(arrayOfString[b]));
/* 111 */         element1.appendChild(element2);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no planned AVAILs found" + NEWLINE);
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
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/* 132 */     Element element1 = paramDocument.createElement(this.nodeName);
/* 133 */     addXMLAttrs(element1);
/* 134 */     paramElement.appendChild(element1);
/*     */ 
/*     */     
/* 137 */     Element element2 = paramDocument.createElement("COUNTRYACTION");
/* 138 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/* 139 */     element1.appendChild(element2);
/*     */     
/* 141 */     element2 = paramDocument.createElement("COUNTRY_FC");
/* 142 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/* 143 */     element1.appendChild(element2);
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
/*     */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 165 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 172 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 173 */     if (paramDiffEntity.isDeleted()) {
/*     */       
/* 175 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 176 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted avail: ctryAtt " + 
/* 177 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 178 */       if (eANFlagAttribute != null) {
/* 179 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 180 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 182 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 183 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 184 */             String str2 = str1;
/* 185 */             if (paramTreeMap.containsKey(str2)) {
/*     */               
/* 187 */               CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/* 188 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*     */             } else {
/*     */               
/* 191 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 192 */               ctryAudRecord.setAction("Delete");
/* 193 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 194 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 195 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 200 */     } else if (paramDiffEntity.isNew()) {
/*     */       
/* 202 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 203 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new avail:  ctryAtt and anncodeAtt " + 
/* 204 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/* 205 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/* 206 */       if (eANFlagAttribute != null) {
/* 207 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 208 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 210 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 211 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 212 */             String str2 = str1;
/* 213 */             if (paramTreeMap.containsKey(str2)) {
/* 214 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/* 215 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*     */               
/* 217 */               ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*     */             } else {
/* 219 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 220 */               ctryAudRecord.setAction("Update");
/* 221 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 222 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 223 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 229 */       HashSet<String> hashSet1 = new HashSet();
/* 230 */       HashSet<String> hashSet2 = new HashSet();
/*     */       
/* 232 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 233 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for curr avail: fAtt and curranncodeAtt " + 
/* 234 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/* 235 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/* 236 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 238 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 239 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 241 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 242 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 248 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 249 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for prev avail:  fAtt and prevanncodeAtt " + 
/* 250 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + 
/* 251 */           PokUtils.getAttributeFlagValue(entityItem2, "ANNCODENAME") + NEWLINE);
/* 252 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 254 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 255 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 257 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 258 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 264 */       Iterator<String> iterator = hashSet2.iterator();
/* 265 */       while (iterator.hasNext()) {
/* 266 */         String str1 = iterator.next();
/* 267 */         if (!hashSet1.contains(str1)) {
/*     */           
/* 269 */           String str = str1;
/* 270 */           if (paramTreeMap.containsKey(str)) {
/* 271 */             CtryAudRecord ctryAudRecord2 = paramTreeMap.get(str);
/* 272 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*     */             
/* 274 */             ctryAudRecord2.setUpdateAvail(paramDiffEntity); continue;
/*     */           } 
/* 276 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(paramDiffEntity, str1);
/* 277 */           ctryAudRecord1.setAction("Update");
/* 278 */           paramTreeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/* 279 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/* 280 */               .getKey() + NEWLINE);
/*     */           
/*     */           continue;
/*     */         } 
/* 284 */         String str2 = str1;
/* 285 */         if (paramTreeMap.containsKey(str2)) {
/* 286 */           CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str2);
/* 287 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctry but new aud on " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*     */           continue;
/*     */         } 
/* 290 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 291 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 292 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 293 */             .getKey() + NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/* 297 */       iterator = hashSet1.iterator();
/* 298 */       while (iterator.hasNext()) {
/* 299 */         String str = iterator.next();
/* 300 */         if (!hashSet2.contains(str)) {
/*     */           
/* 302 */           String str1 = str;
/* 303 */           if (paramTreeMap.containsKey(str1)) {
/* 304 */             CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str1);
/* 305 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str1 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*     */             continue;
/*     */           } 
/* 308 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str);
/* 309 */           ctryAudRecord.setAction("Delete");
/* 310 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 311 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 312 */               .getKey() + NEWLINE);
/*     */         } 
/*     */       } 
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
/*     */   private Vector getPlannedAvails(Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/* 326 */     Vector<DiffEntity> vector1 = new Vector(1);
/* 327 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*     */     
/* 329 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 330 */         .size())) + NEWLINE);
/* 331 */     if (vector2 == null) {
/* 332 */       return vector1;
/*     */     }
/*     */ 
/*     */     
/* 336 */     for (byte b = 0; b < vector2.size(); b++) {
/* 337 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 338 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 339 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/* 340 */       if (diffEntity.isDeleted()) {
/* 341 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 342 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/* 343 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 344 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 345 */           vector1.add(diffEntity);
/*     */         }
/*     */       } else {
/* 348 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 349 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/* 350 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 351 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 352 */           vector1.add(diffEntity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 357 */     return vector1;
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
/*     */   private boolean isDerivefromModel(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 378 */     boolean bool = true;
/*     */     
/* 380 */     if (paramDiffEntity != null && (
/* 381 */       paramDiffEntity.getEntityType().equals("MODEL") || paramDiffEntity.getEntityType().equals("SVCMOD"))) {
/* 382 */       String str = "2010-03-01";
/* 383 */       Vector<DiffEntity> vector = (Vector)paramHashtable.get("AVAIL");
/* 384 */       ABRUtil.append(paramStringBuffer, "DerivefromModel.getAvails looking for AVAILTYPE: 146 in AVAIL allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 385 */           .size())) + NEWLINE);
/* 386 */       if (vector != null)
/*     */       {
/* 388 */         for (byte b = 0; b < vector.size(); b++) {
/* 389 */           DiffEntity diffEntity = vector.elementAt(b);
/* 390 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 391 */           if (!diffEntity.isDeleted()) {
/* 392 */             ABRUtil.append(paramStringBuffer, "XMLANNElem.DerivefromModel.getAvails checking[" + b + "]:New or Update" + diffEntity
/* 393 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*     */             
/* 395 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 396 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 397 */               bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 414 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CtryAudRecord
/*     */   {
/* 424 */     private String action = "@@";
/*     */     
/*     */     private String country;
/* 427 */     private String availStatus = "@@";
/* 428 */     private String pubfrom = "@@";
/* 429 */     private String pubto = "@@";
/* 430 */     private String endofservice = "@@"; private DiffEntity availDiff;
/*     */     
/*     */     boolean isDisplayable() {
/* 433 */       return !this.action.equals("@@");
/*     */     }
/*     */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/* 436 */       this.country = param1String;
/* 437 */       this.availDiff = param1DiffEntity;
/*     */     } void setAction(String param1String) {
/* 439 */       this.action = param1String;
/*     */     } void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 441 */       this.availDiff = param1DiffEntity;
/* 442 */       setAction("Update");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, StringBuffer param1StringBuffer) {
/* 467 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + XMLElem.NEWLINE);
/*     */       
/* 469 */       EntityItem entityItem1 = this.availDiff.getCurrentEntityItem();
/* 470 */       EntityItem entityItem2 = this.availDiff.getPriorEntityItem();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 495 */       if (entityItem1 != null) {
/* 496 */         this.availStatus = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 497 */         if (this.availStatus == null) {
/* 498 */           this.availStatus = "@@";
/*     */         }
/*     */       } 
/*     */       
/* 502 */       String str1 = "@@";
/* 503 */       if (entityItem2 != null) {
/* 504 */         str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 505 */         if (str1 == null) {
/* 506 */           str1 = "@@";
/*     */         }
/*     */       } 
/* 509 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields curstatus: " + this.availStatus + " prevstatus: " + str1 + XMLElem.NEWLINE);
/*     */ 
/*     */       
/* 512 */       if (!str1.equals(this.availStatus)) {
/* 513 */         setAction("Update");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 519 */       if (isNewCountry(param1DiffEntity1, param1StringBuffer)) {
/* 520 */         setAction("Update");
/*     */       }
/*     */       
/* 523 */       this.pubfrom = derivePubFrom(param1DiffEntity1, false, param1StringBuffer);
/* 524 */       String str2 = derivePubFrom(param1DiffEntity1, true, param1StringBuffer);
/*     */       
/* 526 */       if (!this.pubfrom.equals(str2)) {
/* 527 */         setAction("Update");
/*     */       }
/*     */       
/* 530 */       this.pubto = derivePubTo(param1DiffEntity2, false, param1StringBuffer);
/* 531 */       String str3 = derivePubTo(param1DiffEntity2, true, param1StringBuffer);
/* 532 */       if (!this.pubto.equals(str3)) {
/* 533 */         setAction("Update");
/*     */       }
/*     */       
/* 536 */       this.endofservice = deriveENDOFSERVICE(param1DiffEntity3, false, param1StringBuffer);
/* 537 */       String str4 = deriveENDOFSERVICE(param1DiffEntity3, true, param1StringBuffer);
/* 538 */       if (!this.endofservice.equals(str4)) {
/* 539 */         setAction("Update");
/*     */       }
/* 541 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields pubfrom: " + this.pubfrom + " pubto: " + this.pubto + " endofservice:" + this.endofservice + XMLElem.NEWLINE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isNewCountry(DiffEntity param1DiffEntity, StringBuffer param1StringBuffer) {
/* 550 */       boolean bool = false;
/* 551 */       if (param1DiffEntity != null && param1DiffEntity.isNew()) {
/* 552 */         bool = true;
/* 553 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields isNewAvail" + param1DiffEntity.getKey() + XMLElem.NEWLINE);
/* 554 */       } else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 555 */         EANFlagAttribute eANFlagAttribute1 = null;
/* 556 */         EANFlagAttribute eANFlagAttribute2 = null;
/* 557 */         EntityItem entityItem1 = param1DiffEntity.getCurrentEntityItem();
/* 558 */         EntityItem entityItem2 = param1DiffEntity.getPriorEntityItem();
/* 559 */         if (entityItem1 != null) {
/* 560 */           eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*     */         }
/* 562 */         if (entityItem2 != null) {
/* 563 */           eANFlagAttribute2 = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*     */         }
/* 565 */         if (eANFlagAttribute2 != null && !eANFlagAttribute2.isSelected(this.country) && eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(this.country)) {
/* 566 */           bool = true;
/* 567 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields isNewCountry" + param1DiffEntity.getKey() + XMLElem.NEWLINE);
/*     */         } 
/*     */       } 
/* 570 */       return bool;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String deriveENDOFSERVICE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 582 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService  eofAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 583 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */ 
/*     */       
/* 586 */       String str = "@@";
/* 587 */       if (param1Boolean) {
/*     */         
/* 589 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 590 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 591 */           if (entityItem != null) {
/* 592 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 593 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 594 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */               
/* 596 */               ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofavail thedate: " + str + " COUNTRYLIST: " + 
/* 597 */                   PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */             } 
/*     */           } else {
/* 600 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofAvail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 607 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 608 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 609 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 610 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 611 */           str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */         }
/* 613 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofavail thedate: " + str + " COUNTRYLIST: " + 
/* 614 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 619 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String derivePubTo(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 631 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo  loAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 632 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */ 
/*     */       
/* 635 */       String str = "@@";
/* 636 */       if (param1Boolean) {
/*     */         
/* 638 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 639 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 640 */           if (entityItem != null) {
/* 641 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 642 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 643 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 645 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/* 646 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } else {
/* 648 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 654 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 655 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 656 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 657 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 658 */           str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */         }
/* 660 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/* 661 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 666 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String derivePubFrom(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 677 */       String str = "@@";
/* 678 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 679 */           .getKey()) + "findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */       
/* 681 */       if (param1Boolean) {
/*     */ 
/*     */         
/* 684 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 685 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 686 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 687 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 688 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */           }
/* 690 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom foavail thedate: " + str + XMLElem.NEWLINE);
/*     */         } 
/*     */         
/* 693 */         if ("@@".equals(str))
/*     */         {
/* 695 */           if (!this.availDiff.isNew() && this.availDiff != null) {
/* 696 */             EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 697 */             Vector<EntityItem> vector = entityItem.getDownLink();
/* 698 */             for (byte b = 0; b < vector.size(); b++) {
/* 699 */               EntityItem entityItem1 = vector.elementAt(b);
/* 700 */               if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 701 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 702 */                 EntityItem entityItem2 = vector1.elementAt(0);
/* 703 */                 str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 704 */                 ABRUtil.append(param1StringBuffer, "CtryAudRecord.getANNOUNCEMENT looking for downlink of AVAILANNA : Announcement " + (
/* 705 */                     (vector1.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2.getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } else {
/* 711 */         if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 712 */           EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 713 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 714 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 715 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */           }
/* 717 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*     */               
/* 719 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */         } 
/* 721 */         if ("@@".equals(str) && 
/* 722 */           !this.availDiff.isDeleted() && this.availDiff != null) {
/* 723 */           EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 724 */           Vector<EntityItem> vector = entityItem.getDownLink();
/* 725 */           for (byte b = 0; b < vector.size(); b++) {
/* 726 */             EntityItem entityItem1 = vector.elementAt(b);
/* 727 */             if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 728 */               Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 729 */               EntityItem entityItem2 = vector1.elementAt(0);
/* 730 */               str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 731 */               ABRUtil.append(param1StringBuffer, "CtryAudRecord.getANNOUNCEMENT looking for downlink of AVAILANNA : Announcement " + (
/* 732 */                   (vector.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2.getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 738 */       return str;
/*     */     }
/*     */     
/* 741 */     String getAction() { return this.action; } String getCountry() {
/* 742 */       return this.country;
/*     */     }
/* 744 */     String getPubFrom() { return this.pubfrom; }
/* 745 */     String getPubTo() { return this.pubto; }
/* 746 */     String getEndOfService() { return this.endofservice; } String getAvailStatus() {
/* 747 */       return this.availStatus;
/*     */     }
/* 749 */     boolean isDeleted() { return "Delete".equals(this.action); } String getKey() {
/* 750 */       return this.country;
/*     */     } void dereference() {
/* 752 */       this.availDiff = null;
/* 753 */       this.action = null;
/* 754 */       this.country = null;
/* 755 */       this.availStatus = null;
/*     */       
/* 757 */       this.pubfrom = null;
/* 758 */       this.pubto = null;
/* 759 */       this.endofservice = null;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 763 */       return this.availDiff.getKey() + " " + getKey() + " action:" + this.action;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLAVAILElemFCT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */