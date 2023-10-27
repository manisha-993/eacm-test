/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XLColumn;
/*     */ import COM.ibm.eannounce.abr.util.XLFixedColumn;
/*     */ import COM.ibm.eannounce.abr.util.XLIdColumn;
/*     */ import COM.ibm.eannounce.abr.util.XLRow;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QSMLSEOABR
/*     */   implements QSMABRInterface
/*     */ {
/*     */   private static final String COFCAT_HW = "100";
/*     */   private static final String COFCAT_SW = "101";
/* 146 */   private static final Vector COLUMN_VCT = new Vector();
/*     */   
/*     */   static {
/* 149 */     XLColumn xLColumn = new XLColumn("RFAnumber", "LSEO", "QSMRFANUMBER");
/* 150 */     xLColumn.setAlwaysShow();
/* 151 */     xLColumn.setFFColumnLabel("RFANUM");
/* 152 */     xLColumn.setColumnWidth(6);
/* 153 */     xLColumn.setJustified(false);
/* 154 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 156 */     xLColumn = new XLColumn("AnnDate", "LSEO", "LSEOPUBDATEMTRGT");
/* 157 */     xLColumn.setFFColumnLabel("ANNDATE___");
/* 158 */     xLColumn.setColumnWidth(10);
/* 159 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 161 */     xLColumn = new XLColumn("GADate", "LSEO", "LSEOPUBDATEMTRGT");
/* 162 */     xLColumn.setFFColumnLabel("GADATE____");
/* 163 */     xLColumn.setColumnWidth(10);
/* 164 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 166 */     xLColumn = new XLColumn("WDDate", "LSEO", "LSEOUNPUBDATEMTRGT");
/* 167 */     xLColumn.setFFColumnLabel("WDDATE____");
/* 168 */     xLColumn.setColumnWidth(10);
/* 169 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 171 */     xLColumn = new XLColumn("IBMPartno", "LSEO", "SEOID");
/* 172 */     xLColumn.setFFColumnLabel("IBMPART_____");
/* 173 */     xLColumn.setColumnWidth(12);
/* 174 */     xLColumn.setAlwaysShow();
/* 175 */     xLColumn.setJustified(true);
/* 176 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 178 */     xLColumn = new XLColumn("Description", "WWSEO", "PRCFILENAM");
/* 179 */     xLColumn.setFFColumnLabel("DESCRIPTION___________________");
/* 180 */     xLColumn.setColumnWidth(30);
/* 181 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 183 */     xLColumn = new XLColumn("Div", "PROJ", "DIV");
/* 184 */     xLColumn.setFFColumnLabel("DV");
/* 185 */     xLColumn.setColumnWidth(2);
/* 186 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 188 */     xLColumn = new XLColumn("SegA", "SGMNTACRNYM", "ACRNYM");
/* 189 */     xLColumn.setFFColumnLabel("SGA");
/* 190 */     xLColumn.setColumnWidth(3);
/* 191 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 193 */     xLColumn = new XLColumn("ProdID", "WWSEO", "PRODID");
/* 194 */     xLColumn.setFFColumnLabel("P");
/* 195 */     xLColumn.setColumnWidth(1);
/* 196 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 198 */     xLColumn = new XLColumn("SysOrOpt", "WWSEO", "SEOORDERCODE");
/* 199 */     xLColumn.setFFColumnLabel("I");
/* 200 */     xLColumn.setColumnWidth(1);
/* 201 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 203 */     xLColumn = new XLColumn("PlntOfMfg", "GEOMOD", "PLNTOFMFR");
/* 204 */     xLColumn.setFFColumnLabel("PLT");
/* 205 */     xLColumn.setColumnWidth(3);
/* 206 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 208 */     xLColumn = new XLColumn("IndDefCat", "LSEO", "INDDEFNCATG");
/* 209 */     xLColumn.setFFColumnLabel("IDC");
/* 210 */     xLColumn.setColumnWidth(3);
/* 211 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 213 */     xLColumn = new XLColumn("FtnClass", "MODEL", "FUNCCLS");
/* 214 */     xLColumn.setFFColumnLabel("CLAS");
/* 215 */     xLColumn.setColumnWidth(4);
/* 216 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 218 */     xLColumn = new XLColumn("Brand", "GEOMOD", "EMEABRANDCD");
/* 219 */     xLColumn.setFFColumnLabel("B");
/* 220 */     xLColumn.setColumnWidth(1);
/* 221 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 223 */     xLColumn = new XLColumn("USPartNo", "LSEO", "SEOID");
/* 224 */     xLColumn.setFFColumnLabel("USPN___");
/* 225 */     xLColumn.setColumnWidth(7);
/* 226 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 228 */     xLColumn = new XLColumn("SPECBID", "WWSEO", "SPECBID");
/* 229 */     xLColumn.setFFColumnLabel("S");
/* 230 */     xLColumn.setColumnWidth(1);
/* 231 */     COLUMN_VCT.addElement(xLColumn);
/*     */     
/* 233 */     XLFixedColumn xLFixedColumn = new XLFixedColumn("SEOType", "LSEO");
/* 234 */     xLFixedColumn.setFFColumnLabel("SEOTYPE___");
/* 235 */     xLFixedColumn.setColumnWidth(10);
/* 236 */     xLFixedColumn.setAlwaysShow();
/* 237 */     COLUMN_VCT.addElement(xLFixedColumn);
/*     */     
/* 239 */     XLIdColumn xLIdColumn = new XLIdColumn("EntityId", "LSEO");
/* 240 */     xLIdColumn.setFFColumnLabel("ENTITYID__");
/* 241 */     xLIdColumn.setAlwaysShow();
/* 242 */     xLIdColumn.setJustified(false);
/* 243 */     xLIdColumn.setColumnWidth(10);
/* 244 */     COLUMN_VCT.addElement(xLIdColumn);
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
/*     */   public boolean canGenerateReport(EntityItem paramEntityItem, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/* 276 */     boolean bool = true;
/* 277 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "STATUS");
/* 278 */     paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.generateReport: " + paramEntityItem.getKey() + " STATUS: " + 
/* 279 */         PokUtils.getAttributeValue(paramEntityItem, "STATUS", ", ", "", false) + " [" + str + "]");
/* 280 */     if (!"0020".equals(str)) {
/*     */       
/* 282 */       paramQSMRPTABRSTATUS.addError("NOT_FINAL_ERR", null);
/* 283 */       bool = false;
/*     */     } 
/*     */     
/* 286 */     HashSet<String> hashSet = new HashSet();
/* 287 */     for (byte b = 0; b < QSMRPTABRSTATUS.GEOS.length; b++) {
/* 288 */       hashSet.add(QSMRPTABRSTATUS.GEOS[b]);
/*     */     }
/* 290 */     hashSet.add("1999");
/*     */     
/* 292 */     if (!PokUtils.contains(paramEntityItem, "GENAREASELECTION", hashSet)) {
/* 293 */       bool = false;
/* 294 */       paramQSMRPTABRSTATUS.addOutput("LSEO does not have required 'General Area Selection' value.");
/* 295 */       paramQSMRPTABRSTATUS.addDebug("GENAREASELECTION did not include " + hashSet + ", [" + 
/* 296 */           PokUtils.getAttributeValue(paramEntityItem, "GENAREASELECTION", ", ", "", false) + "]");
/* 297 */       paramQSMRPTABRSTATUS.setNoReport();
/*     */     } 
/* 299 */     hashSet.clear();
/*     */     
/* 301 */     return bool;
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
/*     */   public boolean canGenerateReport(EntityList paramEntityList, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/* 316 */     boolean bool = false;
/* 317 */     EntityItem entityItem = paramEntityList.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */ 
/*     */     
/* 321 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "WWSEO");
/* 322 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "MODELWWSEO", "MODEL"); byte b;
/* 323 */     for (b = 0; b < vector2.size(); b++) {
/* 324 */       EntityItem entityItem1 = vector2.elementAt(b);
/* 325 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "COFCAT");
/* 326 */       paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.canGenerateReport " + entityItem.getKey() + " " + entityItem1
/* 327 */           .getKey() + " cofcat:" + str);
/* 328 */       if ("100".equals(str) || "101".equals(str)) {
/* 329 */         bool = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 334 */     if (!bool) {
/* 335 */       paramQSMRPTABRSTATUS.addOutput("Hardware or Software Model was not found.");
/*     */     }
/*     */     
/* 338 */     vector2.clear();
/*     */     
/* 340 */     for (b = 0; b < vector1.size(); b++) {
/* 341 */       EntityItem entityItem1 = vector1.elementAt(b);
/* 342 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "SPECBID");
/* 343 */       paramQSMRPTABRSTATUS.addDebug(entityItem1.getKey() + " SPECBID: " + str);
/* 344 */       if ("11457".equals(str) || str == null) {
/* 345 */         paramQSMRPTABRSTATUS.addOutput("WWSEO is not a special bid.");
/* 346 */         bool = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 351 */     vector1.clear();
/*     */     
/* 353 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 361 */     return "QSMLSEO";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 370 */     return "1.8";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 377 */     return COLUMN_VCT.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnLabel(int paramInt) {
/* 384 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).getColumnLabel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFFColumnLabel(int paramInt) {
/* 392 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).getFFColumnLabel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnWidth(int paramInt) {
/* 400 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).getColumnWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnValue(HSSFCell paramHSSFCell, String paramString, Hashtable paramHashtable, int paramInt) {
/* 406 */     ((XLColumn)COLUMN_VCT.elementAt(paramInt)).setColumnValue(paramHSSFCell, paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnValue(String paramString, Hashtable paramHashtable, int paramInt) {
/* 413 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).getColumnValue(paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChanged(String paramString, Hashtable paramHashtable, int paramInt) {
/* 420 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).isChanged(paramHashtable);
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
/*     */   public Vector getRowItems(EntityList paramEntityList, Hashtable paramHashtable, String paramString, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/* 434 */     Vector<XLRow> vector = new Vector();
/*     */     
/* 436 */     EntityItem entityItem = null;
/* 437 */     if (paramHashtable != null) {
/* 438 */       Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("ROOT");
/* 439 */       entityItem = ((DiffEntity)vector2.firstElement()).getCurrentEntityItem();
/*     */     } else {
/* 441 */       entityItem = paramEntityList.getParentEntityGroup().getEntityItem(0);
/*     */     } 
/*     */     
/* 444 */     paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.getRowItems " + entityItem.getKey());
/*     */     
/* 446 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "WWSEO");
/* 447 */     for (byte b = 0; b < vector1.size(); b++) {
/* 448 */       EntityItem entityItem1 = vector1.elementAt(b);
/*     */ 
/*     */       
/* 451 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem1, "WWSEOPROJA", "PROJ");
/* 452 */       Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "MODELWWSEO", "MODEL");
/* 453 */       for (byte b1 = 0; b1 < vector3.size(); b1++) {
/* 454 */         EntityItem entityItem2 = vector3.elementAt(b1);
/* 455 */         String str = PokUtils.getAttributeFlagValue(entityItem2, "COFCAT");
/* 456 */         paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.getRowItems " + entityItem.getKey() + " " + entityItem2
/* 457 */             .getKey() + " cofcat:" + str);
/* 458 */         if ("100".equals(str) || "101".equals(str)) {
/*     */           
/* 460 */           XLRow xLRow = new XLRow((paramHashtable == null) ? entityItem : paramHashtable.get(entityItem.getKey()));
/* 461 */           vector.addElement(xLRow);
/*     */           
/* 463 */           xLRow.addRowItem((paramHashtable == null) ? entityItem1 : paramHashtable
/* 464 */               .get(entityItem1.getKey()));
/* 465 */           xLRow.addRowItem((paramHashtable == null) ? entityItem2 : paramHashtable
/* 466 */               .get(entityItem2.getKey()));
/*     */           
/* 468 */           Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(entityItem2, "MODELGEOMOD", "GEOMOD");
/*     */           
/* 470 */           Vector<EntityItem> vector5 = PokUtils.getEntitiesWithMatchedAttr(vector4, "GENAREASELECTION", "1999");
/* 471 */           paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.getRowItems GENAREASELECTION:1999 has wwgeomodVct.size: " + vector5
/* 472 */               .size());
/*     */           
/* 474 */           if (vector5.size() == 0) {
/*     */             
/* 476 */             vector4 = PokUtils.getEntitiesWithMatchedAttr(vector4, "GENAREASELECTION", paramString);
/* 477 */             paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.getRowItems GEOMOD GENAREASELECTION:" + paramString + " has geomodVct.size: " + vector4
/* 478 */                 .size());
/*     */           } else {
/* 480 */             vector4 = vector5;
/*     */           } 
/* 482 */           if (vector4.size() > 0) {
/* 483 */             if (vector4.size() > 1) {
/* 484 */               for (byte b2 = 0; b2 < vector4.size(); b2++) {
/* 485 */                 paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.getRowItems WARNING: Found more than one GEOMOD for " + entityItem2
/* 486 */                     .getKey() + " " + ((EntityItem)vector4.elementAt(b2)).getKey());
/*     */               }
/*     */             }
/* 489 */             EntityItem entityItem3 = vector4.firstElement();
/* 490 */             xLRow.addRowItem((paramHashtable == null) ? entityItem3 : paramHashtable
/* 491 */                 .get(entityItem3.getKey()));
/* 492 */             vector4.clear();
/*     */           } 
/*     */           
/* 495 */           if (vector2.size() > 0) {
/* 496 */             EntityItem entityItem3 = vector2.firstElement();
/* 497 */             xLRow.addRowItem((paramHashtable == null) ? entityItem3 : paramHashtable
/* 498 */                 .get(entityItem3.getKey()));
/*     */             
/* 500 */             Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(entityItem3, "PROJSGMNTACRNYMA", "SGMNTACRNYM");
/* 501 */             if (vector6.size() > 0) {
/* 502 */               EntityItem entityItem4 = vector6.firstElement();
/* 503 */               xLRow.addRowItem((paramHashtable == null) ? entityItem4 : paramHashtable
/* 504 */                   .get(entityItem4.getKey()));
/* 505 */               vector6.clear();
/*     */             } 
/*     */           } 
/* 508 */           paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.getRowItems Adding " + xLRow);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 513 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean withinDateRange(EntityItem paramEntityItem, String paramString, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/* 522 */     boolean bool = false;
/* 523 */     Object[] arrayOfObject = new Object[5];
/* 524 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "LSEOPUBDATEMTRGT", "", "1980-01-01", false);
/* 525 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "LSEOUNPUBDATEMTRGT", "", "9999-12-31", false);
/* 526 */     PDGUtility pDGUtility = new PDGUtility();
/* 527 */     String str3 = pDGUtility.getDate(paramString, 30);
/*     */     
/* 529 */     arrayOfObject[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "LSEOPUBDATEMTRGT", "LSEOPUBDATEMTRGT");
/* 530 */     arrayOfObject[1] = str1;
/* 531 */     arrayOfObject[2] = "";
/* 532 */     arrayOfObject[3] = paramString;
/* 533 */     arrayOfObject[4] = str3;
/*     */     
/* 535 */     paramQSMRPTABRSTATUS.addDebug("QSMLSEOABR.withinDateRange now " + paramString + " pubdate " + str1 + " unpubdate " + str2 + " nowPlus30 " + str3);
/* 536 */     if (str3.compareTo(str1) >= 0 && str1.compareTo(paramString) >= 0) {
/* 537 */       bool = true;
/*     */     } else {
/* 539 */       arrayOfObject[2] = "not";
/*     */     } 
/*     */     
/* 542 */     paramQSMRPTABRSTATUS.addOutput("DATE_RANGE_MSG", arrayOfObject);
/* 543 */     arrayOfObject[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "LSEOUNPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT");
/* 544 */     arrayOfObject[1] = str2;
/* 545 */     arrayOfObject[2] = "";
/* 546 */     if (str3.compareTo(str2) >= 0 && str2.compareTo(paramString) >= 0) {
/* 547 */       bool = true;
/*     */     } else {
/* 549 */       arrayOfObject[2] = "not";
/*     */     } 
/*     */     
/* 552 */     paramQSMRPTABRSTATUS.addOutput("DATE_RANGE_MSG", arrayOfObject);
/*     */     
/* 554 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRowOne(EntityItem paramEntityItem) {
/* 562 */     String str = PokUtils.getAttributeValue(paramEntityItem, "QSMRFANUMBER", "", "", false);
/* 563 */     return XLColumn.formatToWidth(str, 6, false) + " SBD";
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
/*     */   public String getRowTwoPrefix() {
/* 578 */     return XLColumn.formatToWidth("Date______", 10) + " " + 
/* 579 */       XLColumn.formatToWidth("Time___________", 15);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMLSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */