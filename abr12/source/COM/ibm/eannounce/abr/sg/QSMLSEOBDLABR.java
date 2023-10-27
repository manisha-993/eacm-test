/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XLColumn;
/*     */ import COM.ibm.eannounce.abr.util.XLFixedColumn;
/*     */ import COM.ibm.eannounce.abr.util.XLIdColumn;
/*     */ import COM.ibm.eannounce.abr.util.XLRow;
/*     */ import COM.ibm.eannounce.abr.util.XLSysOrOptColumn;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QSMLSEOBDLABR
/*     */   implements QSMABRInterface
/*     */ {
/* 182 */   private static final Vector COLUMN_VCT = new Vector();
/*     */   
/*     */   static {
/* 185 */     XLColumn xLColumn3 = new XLColumn("RFAnumber", "LSEOBUNDLE", "QSMRFANUMBER");
/* 186 */     xLColumn3.setAlwaysShow();
/* 187 */     xLColumn3.setFFColumnLabel("RFANUM");
/* 188 */     xLColumn3.setColumnWidth(6);
/* 189 */     xLColumn3.setJustified(false);
/* 190 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 192 */     xLColumn3 = new XLColumn("AnnDate", "LSEOBUNDLE", "BUNDLPUBDATEMTRGT");
/* 193 */     xLColumn3.setFFColumnLabel("ANNDATE___");
/* 194 */     xLColumn3.setColumnWidth(10);
/* 195 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 197 */     xLColumn3 = new XLColumn("GADate", "LSEOBUNDLE", "BUNDLPUBDATEMTRGT");
/* 198 */     xLColumn3.setFFColumnLabel("GADATE____");
/* 199 */     xLColumn3.setColumnWidth(10);
/* 200 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 202 */     xLColumn3 = new XLColumn("WDDate", "LSEOBUNDLE", "BUNDLUNPUBDATEMTRGT");
/* 203 */     xLColumn3.setFFColumnLabel("WDDATE____");
/* 204 */     xLColumn3.setColumnWidth(10);
/* 205 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 207 */     xLColumn3 = new XLColumn("IBMPartno", "LSEOBUNDLE", "SEOID");
/* 208 */     xLColumn3.setFFColumnLabel("IBMPART_____");
/* 209 */     xLColumn3.setColumnWidth(12);
/* 210 */     xLColumn3.setAlwaysShow();
/* 211 */     xLColumn3.setJustified(true);
/* 212 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 214 */     xLColumn3 = new XLColumn("Description", "LSEOBUNDLE", "PRCFILENAM");
/* 215 */     xLColumn3.setFFColumnLabel("DESCRIPTION___________________");
/* 216 */     xLColumn3.setColumnWidth(30);
/* 217 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 219 */     xLColumn3 = new XLColumn("Div", "PROJ", "DIV");
/* 220 */     xLColumn3.setFFColumnLabel("DV");
/* 221 */     xLColumn3.setColumnWidth(2);
/* 222 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 224 */     xLColumn3 = new XLColumn("SegA", "SGMNTACRNYM", "ACRNYM");
/* 225 */     xLColumn3.setFFColumnLabel("SGA");
/* 226 */     xLColumn3.setColumnWidth(3);
/* 227 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 229 */     xLColumn3 = new XLColumn("ProdID", "LSEOBUNDLE", "PRODID");
/* 230 */     xLColumn3.setFFColumnLabel("P");
/* 231 */     xLColumn3.setColumnWidth(1);
/* 232 */     COLUMN_VCT.addElement(xLColumn3);
/*     */     
/* 234 */     XLSysOrOptColumn xLSysOrOptColumn = new XLSysOrOptColumn();
/* 235 */     xLSysOrOptColumn.setFFColumnLabel("I");
/* 236 */     xLSysOrOptColumn.setColumnWidth(1);
/* 237 */     COLUMN_VCT.addElement(xLSysOrOptColumn);
/*     */     
/* 239 */     XLColumn xLColumn2 = new XLColumn("PlntOfMfg", "GEOMOD", "PLNTOFMFR");
/* 240 */     xLColumn2.setFFColumnLabel("PLT");
/* 241 */     xLColumn2.setColumnWidth(3);
/* 242 */     COLUMN_VCT.addElement(xLColumn2);
/*     */     
/* 244 */     xLColumn2 = new XLColumn("IndDefCat", "LSEOBUNDLE", "INDDEFNCATG");
/* 245 */     xLColumn2.setFFColumnLabel("IDC");
/* 246 */     xLColumn2.setColumnWidth(3);
/* 247 */     COLUMN_VCT.addElement(xLColumn2);
/*     */     
/* 249 */     xLColumn2 = new XLColumn("FtnClass", "LSEOBUNDLE", "FUNCCLS");
/* 250 */     xLColumn2.setFFColumnLabel("CLAS");
/* 251 */     xLColumn2.setColumnWidth(4);
/* 252 */     COLUMN_VCT.addElement(xLColumn2);
/*     */     
/* 254 */     xLColumn2 = new XLColumn("Brand", "GEOMOD", "EMEABRANDCD");
/* 255 */     xLColumn2.setFFColumnLabel("B");
/* 256 */     xLColumn2.setColumnWidth(1);
/* 257 */     COLUMN_VCT.addElement(xLColumn2);
/*     */     
/* 259 */     XLFixedColumn xLFixedColumn2 = new XLFixedColumn("USPartNo", "");
/* 260 */     xLFixedColumn2.setFFColumnLabel("USPN___");
/* 261 */     xLFixedColumn2.setColumnWidth(7);
/* 262 */     COLUMN_VCT.addElement(xLFixedColumn2);
/*     */     
/* 264 */     XLColumn xLColumn1 = new XLColumn("SPECBID", "LSEOBUNDLE", "SPECBID");
/* 265 */     xLColumn1.setFFColumnLabel("S");
/* 266 */     xLColumn1.setColumnWidth(1);
/* 267 */     COLUMN_VCT.addElement(xLColumn1);
/*     */     
/* 269 */     XLFixedColumn xLFixedColumn1 = new XLFixedColumn("SEOType", "LSEOBUNDLE");
/* 270 */     xLFixedColumn1.setFFColumnLabel("SEOTYPE___");
/* 271 */     xLFixedColumn1.setColumnWidth(10);
/* 272 */     xLFixedColumn1.setAlwaysShow();
/* 273 */     COLUMN_VCT.addElement(xLFixedColumn1);
/*     */     
/* 275 */     XLIdColumn xLIdColumn = new XLIdColumn("EntityId", "LSEOBUNDLE");
/* 276 */     xLIdColumn.setFFColumnLabel("ENTITYID__");
/* 277 */     xLIdColumn.setAlwaysShow();
/* 278 */     xLIdColumn.setJustified(false);
/* 279 */     xLIdColumn.setColumnWidth(10);
/* 280 */     COLUMN_VCT.addElement(xLIdColumn);
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
/* 312 */     boolean bool = true;
/* 313 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "STATUS");
/* 314 */     paramQSMRPTABRSTATUS.addDebug("QSMLSEOBDLABR.generateReport: " + paramEntityItem.getKey() + " STATUS: " + 
/* 315 */         PokUtils.getAttributeValue(paramEntityItem, "STATUS", ", ", "", false) + " [" + str1 + "]");
/* 316 */     if (!"0020".equals(str1)) {
/*     */       
/* 318 */       paramQSMRPTABRSTATUS.addError("NOT_FINAL_ERR", null);
/* 319 */       bool = false;
/*     */     } 
/*     */     
/* 322 */     HashSet<String> hashSet = new HashSet();
/* 323 */     for (byte b = 0; b < QSMRPTABRSTATUS.GEOS.length; b++) {
/* 324 */       hashSet.add(QSMRPTABRSTATUS.GEOS[b]);
/*     */     }
/* 326 */     hashSet.add("1999");
/* 327 */     if (!PokUtils.contains(paramEntityItem, "GENAREASELECTION", hashSet)) {
/* 328 */       bool = false;
/* 329 */       paramQSMRPTABRSTATUS.addOutput("LSEOBUNDLE does not have required 'General Area Selection' value.");
/* 330 */       paramQSMRPTABRSTATUS.addDebug("GENAREASELECTION did not include " + hashSet + ", [" + 
/* 331 */           PokUtils.getAttributeValue(paramEntityItem, "GENAREASELECTION", ", ", "", false) + "]");
/* 332 */       paramQSMRPTABRSTATUS.setNoReport();
/*     */     } 
/* 334 */     hashSet.clear();
/*     */     
/* 336 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "SPECBID");
/* 337 */     paramQSMRPTABRSTATUS.addDebug(paramEntityItem.getKey() + " SPECBID: " + str2);
/* 338 */     if ("11457".equals(str2) || str2 == null) {
/* 339 */       paramQSMRPTABRSTATUS.addOutput("LSEOBUNDLE is not a special bid.");
/* 340 */       bool = false;
/*     */     } 
/*     */     
/* 343 */     return bool;
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
/*     */   public boolean canGenerateReport(EntityList paramEntityList, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/* 355 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 363 */     return "QSMLSEOBDL";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 372 */     return "1.8";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 379 */     return COLUMN_VCT.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnLabel(int paramInt) {
/* 386 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).getColumnLabel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFFColumnLabel(int paramInt) {
/* 394 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).getFFColumnLabel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnWidth(int paramInt) {
/* 402 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).getColumnWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnValue(HSSFCell paramHSSFCell, String paramString, Hashtable paramHashtable, int paramInt) {
/* 408 */     ((XLColumn)COLUMN_VCT.elementAt(paramInt)).setColumnValue(paramHSSFCell, paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnValue(String paramString, Hashtable paramHashtable, int paramInt) {
/* 415 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).getColumnValue(paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChanged(String paramString, Hashtable paramHashtable, int paramInt) {
/* 423 */     return ((XLColumn)COLUMN_VCT.elementAt(paramInt)).isChanged(paramHashtable);
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
/*     */   public Vector getRowItems(EntityList paramEntityList, Hashtable paramHashtable, String paramString, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/* 436 */     Vector<XLRow> vector = new Vector();
/*     */     
/* 438 */     EntityItem entityItem = null;
/* 439 */     if (paramHashtable != null) {
/* 440 */       Vector<DiffEntity> vector4 = (Vector)paramHashtable.get("ROOT");
/* 441 */       entityItem = ((DiffEntity)vector4.firstElement()).getCurrentEntityItem();
/*     */     } else {
/* 443 */       entityItem = paramEntityList.getParentEntityGroup().getEntityItem(0);
/*     */     } 
/*     */     
/* 446 */     paramQSMRPTABRSTATUS.addDebug("QSMLSEOBDLABR.getRowItems " + entityItem.getKey());
/*     */     
/* 448 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "LSEOBUNDLEPROJA", "PROJ");
/*     */     
/* 450 */     XLRow xLRow = new XLRow((paramHashtable == null) ? entityItem : paramHashtable.get(entityItem.getKey()));
/* 451 */     vector.addElement(xLRow);
/*     */     
/* 453 */     Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem, "LSEOBUNDLEGEOMOD", "GEOMOD");
/*     */     
/* 455 */     Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector2, "GENAREASELECTION", "1999");
/* 456 */     paramQSMRPTABRSTATUS.addDebug("QSMLSEOBDLABR.getRowItems GENAREASELECTION:1999 has wwgeomodVct.size: " + vector3
/* 457 */         .size());
/* 458 */     if (vector3.size() == 0) {
/*     */       
/* 460 */       vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "GENAREASELECTION", paramString);
/* 461 */       paramQSMRPTABRSTATUS.addDebug("QSMLSEOBDLABR.getRowItems GEOMOD GENAREASELECTION:" + paramString + " has geomodVct.size: " + vector2
/* 462 */           .size());
/*     */     } else {
/* 464 */       vector2 = vector3;
/*     */     } 
/* 466 */     if (vector2.size() > 0) {
/* 467 */       if (vector2.size() > 1) {
/* 468 */         for (byte b = 0; b < vector2.size(); b++) {
/* 469 */           paramQSMRPTABRSTATUS.addDebug("QSMLSEOBDLABR.getRowItems WARNING: Found more than one GEOMOD " + ((EntityItem)vector2
/* 470 */               .elementAt(b)).getKey());
/*     */         }
/*     */       }
/* 473 */       EntityItem entityItem1 = vector2.firstElement();
/* 474 */       xLRow.addRowItem((paramHashtable == null) ? entityItem1 : paramHashtable
/* 475 */           .get(entityItem1.getKey()));
/* 476 */       vector2.clear();
/*     */     } 
/*     */     
/* 479 */     if (vector1.size() > 0) {
/* 480 */       EntityItem entityItem1 = vector1.firstElement();
/* 481 */       xLRow.addRowItem((paramHashtable == null) ? entityItem1 : paramHashtable
/* 482 */           .get(entityItem1.getKey()));
/*     */       
/* 484 */       Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(entityItem1, "PROJSGMNTACRNYMA", "SGMNTACRNYM");
/* 485 */       if (vector4.size() > 0) {
/* 486 */         EntityItem entityItem2 = vector4.firstElement();
/* 487 */         xLRow.addRowItem((paramHashtable == null) ? entityItem2 : paramHashtable
/* 488 */             .get(entityItem2.getKey()));
/* 489 */         vector4.clear();
/*     */       } 
/*     */     } 
/* 492 */     paramQSMRPTABRSTATUS.addDebug("QSMLSEOBDLABR.getRowItems Adding " + xLRow);
/*     */     
/* 494 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean withinDateRange(EntityItem paramEntityItem, String paramString, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/* 503 */     boolean bool = false;
/* 504 */     Object[] arrayOfObject = new Object[5];
/* 505 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "BUNDLPUBDATEMTRGT", "", "1980-01-01", false);
/* 506 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "BUNDLUNPUBDATEMTRGT", "", "9999-12-31", false);
/* 507 */     PDGUtility pDGUtility = new PDGUtility();
/* 508 */     String str3 = pDGUtility.getDate(paramString, 30);
/* 509 */     arrayOfObject[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "BUNDLPUBDATEMTRGT", "BUNDLPUBDATEMTRGT");
/* 510 */     arrayOfObject[1] = str1;
/* 511 */     arrayOfObject[2] = "";
/* 512 */     arrayOfObject[3] = paramString;
/* 513 */     arrayOfObject[4] = str3;
/*     */     
/* 515 */     paramQSMRPTABRSTATUS.addDebug("QSMLSEOBDLABR.withinDateRange now " + paramString + " pubdate " + str1 + " unpubdate " + str2 + " nowPlus30 " + str3);
/* 516 */     if (str3.compareTo(str1) >= 0 && str1.compareTo(paramString) >= 0) {
/* 517 */       bool = true;
/*     */     } else {
/* 519 */       arrayOfObject[2] = "not";
/*     */     } 
/*     */     
/* 522 */     paramQSMRPTABRSTATUS.addOutput("DATE_RANGE_MSG", arrayOfObject);
/* 523 */     arrayOfObject[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "BUNDLUNPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT");
/* 524 */     arrayOfObject[1] = str2;
/* 525 */     arrayOfObject[2] = "";
/* 526 */     if (str3.compareTo(str2) >= 0 && str2.compareTo(paramString) >= 0) {
/* 527 */       bool = true;
/*     */     } else {
/* 529 */       arrayOfObject[2] = "not";
/*     */     } 
/*     */     
/* 532 */     paramQSMRPTABRSTATUS.addOutput("DATE_RANGE_MSG", arrayOfObject);
/* 533 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRowOne(EntityItem paramEntityItem) {
/* 542 */     String str = PokUtils.getAttributeValue(paramEntityItem, "QSMRFANUMBER", "", "", false);
/* 543 */     return XLColumn.formatToWidth(str, 6, false) + " SBD";
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
/* 558 */     return XLColumn.formatToWidth("Date______", 10) + " " + 
/* 559 */       XLColumn.formatToWidth("Time___________", 15);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMLSEOBDLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */