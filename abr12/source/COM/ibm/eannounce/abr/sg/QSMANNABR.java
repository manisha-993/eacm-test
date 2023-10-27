/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.XLColumn;
/*      */ import COM.ibm.eannounce.abr.util.XLFixedColumn;
/*      */ import COM.ibm.eannounce.abr.util.XLIdColumn;
/*      */ import COM.ibm.eannounce.abr.util.XLRow;
/*      */ import COM.ibm.eannounce.abr.util.XLSysOrOptColumn;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import org.apache.poi.hssf.usermodel.HSSFCell;
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
/*      */ public class QSMANNABR
/*      */   implements QSMABRInterface
/*      */ {
/*      */   private static final String ANNTYPE_EOL = "14";
/*      */   private static final String ANNTYPE_NEW = "19";
/*      */   private static final String PLANNEDAVAIL = "146";
/*      */   private static final String LASTORDERAVAIL = "149";
/*      */   private static final String COFCAT_HW = "100";
/*      */   private static final String COFCAT_SW = "101";
/*  167 */   private static final Vector NEW_LSEO_COLUMN_VCT = new Vector();
/*      */   
/*      */   static {
/*  170 */     XLColumn xLColumn1 = new XLColumn("RFAnumber", "ANNOUNCEMENT", "ANNNUMBER");
/*  171 */     xLColumn1.setAlwaysShow();
/*  172 */     xLColumn1.setFFColumnLabel("RFANUM");
/*  173 */     xLColumn1.setColumnWidth(6);
/*  174 */     xLColumn1.setJustified(false);
/*  175 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn1);
/*      */     
/*  177 */     XLColumn xLColumn2 = new XLColumn("AnnDate", "ANNOUNCEMENT", "ANNDATE");
/*  178 */     xLColumn2.setFFColumnLabel("ANNDATE___");
/*  179 */     xLColumn2.setColumnWidth(10);
/*  180 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn2);
/*      */     
/*  182 */     XLColumn xLColumn3 = new XLColumn("GADate", "AVAIL", "EFFECTIVEDATE");
/*  183 */     xLColumn3.setFFColumnLabel("GADATE____");
/*  184 */     xLColumn3.setColumnWidth(10);
/*  185 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn3);
/*      */     
/*  187 */     XLFixedColumn xLFixedColumn1 = new XLFixedColumn("WDDate", "");
/*  188 */     xLFixedColumn1.setFFColumnLabel("WDDATE____");
/*  189 */     xLFixedColumn1.setColumnWidth(10);
/*  190 */     NEW_LSEO_COLUMN_VCT.addElement(xLFixedColumn1);
/*      */     
/*  192 */     XLColumn xLColumn5 = new XLColumn("IBMPartno", "LSEO", "SEOID");
/*  193 */     xLColumn5.setFFColumnLabel("IBMPART_____");
/*  194 */     xLColumn5.setColumnWidth(12);
/*  195 */     xLColumn5.setAlwaysShow();
/*  196 */     xLColumn5.setJustified(true);
/*  197 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn5);
/*      */     
/*  199 */     XLColumn xLColumn6 = new XLColumn("Description", "WWSEO", "PRCFILENAM");
/*  200 */     xLColumn6.setFFColumnLabel("DESCRIPTION___________________");
/*  201 */     xLColumn6.setColumnWidth(30);
/*  202 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn6);
/*      */     
/*  204 */     XLColumn xLColumn7 = new XLColumn("Div", "PROJ", "DIV");
/*  205 */     xLColumn7.setFFColumnLabel("DV");
/*  206 */     xLColumn7.setColumnWidth(2);
/*  207 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn7);
/*      */     
/*  209 */     XLColumn xLColumn8 = new XLColumn("SegA", "SGMNTACRNYM", "ACRNYM");
/*  210 */     xLColumn8.setFFColumnLabel("SGA");
/*  211 */     xLColumn8.setColumnWidth(3);
/*  212 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn8);
/*      */     
/*  214 */     XLColumn xLColumn9 = new XLColumn("ProdID", "WWSEO", "PRODID");
/*  215 */     xLColumn9.setFFColumnLabel("P");
/*  216 */     xLColumn9.setColumnWidth(1);
/*  217 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn9);
/*      */     
/*  219 */     xLColumn9 = new XLColumn("SysOrOpt", "WWSEO", "SEOORDERCODE");
/*  220 */     xLColumn9.setFFColumnLabel("I");
/*  221 */     xLColumn9.setColumnWidth(1);
/*  222 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn9);
/*      */     
/*  224 */     XLColumn xLColumn10 = new XLColumn("PlntOfMfg", "GEOMOD", "PLNTOFMFR");
/*  225 */     xLColumn10.setFFColumnLabel("PLT");
/*  226 */     xLColumn10.setColumnWidth(3);
/*  227 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn10);
/*      */     
/*  229 */     XLColumn xLColumn11 = new XLColumn("IndDefCat", "ANNOUNCEMENT", "INDDEFNCATG");
/*  230 */     xLColumn11.setFFColumnLabel("IDC");
/*  231 */     xLColumn11.setColumnWidth(3);
/*  232 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn11);
/*      */     
/*  234 */     xLColumn9 = new XLColumn("FtnClass", "MODEL", "FUNCCLS");
/*  235 */     xLColumn9.setFFColumnLabel("CLAS");
/*  236 */     xLColumn9.setColumnWidth(4);
/*  237 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn9);
/*      */     
/*  239 */     XLColumn xLColumn12 = new XLColumn("Brand", "GEOMOD", "EMEABRANDCD");
/*  240 */     xLColumn12.setFFColumnLabel("B");
/*  241 */     xLColumn12.setColumnWidth(1);
/*  242 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn12);
/*      */     
/*  244 */     xLColumn9 = new XLColumn("USPartNo", "LSEO", "SEOID");
/*  245 */     xLColumn9.setFFColumnLabel("USPN___");
/*  246 */     xLColumn9.setColumnWidth(7);
/*  247 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn9);
/*      */     
/*  249 */     xLColumn9 = new XLColumn("SPECBID", "WWSEO", "SPECBID");
/*  250 */     xLColumn9.setFFColumnLabel("S");
/*  251 */     xLColumn9.setColumnWidth(1);
/*  252 */     NEW_LSEO_COLUMN_VCT.addElement(xLColumn9);
/*      */     
/*  254 */     XLFixedColumn xLFixedColumn2 = new XLFixedColumn("SEOType", "LSEO");
/*  255 */     xLFixedColumn2.setFFColumnLabel("SEOTYPE___");
/*  256 */     xLFixedColumn2.setColumnWidth(10);
/*  257 */     xLFixedColumn2.setAlwaysShow();
/*  258 */     NEW_LSEO_COLUMN_VCT.addElement(xLFixedColumn2);
/*      */     
/*  260 */     XLIdColumn xLIdColumn = new XLIdColumn("EntityId", "ANNOUNCEMENT");
/*  261 */     xLIdColumn.setFFColumnLabel("ENTITYID__");
/*  262 */     xLIdColumn.setAlwaysShow();
/*  263 */     xLIdColumn.setJustified(false);
/*  264 */     xLIdColumn.setColumnWidth(10);
/*  265 */     NEW_LSEO_COLUMN_VCT.addElement(xLIdColumn);
/*      */   }
/*      */   
/*  268 */   private static final Vector NEW_LSEOBDL_COLUMN_VCT = new Vector();
/*      */   
/*      */   static {
/*  271 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn1);
/*      */     
/*  273 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn2);
/*      */     
/*  275 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn3);
/*      */     
/*  277 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLFixedColumn1);
/*      */     
/*  279 */     XLColumn xLColumn13 = new XLColumn("IBMPartno", "LSEOBUNDLE", "SEOID");
/*  280 */     xLColumn13.setAlwaysShow();
/*  281 */     xLColumn13.setFFColumnLabel("IBMPART_____");
/*  282 */     xLColumn13.setColumnWidth(12);
/*  283 */     xLColumn13.setJustified(true);
/*  284 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn13);
/*      */     
/*  286 */     XLColumn xLColumn14 = new XLColumn("Description", "LSEOBUNDLE", "PRCFILENAM");
/*  287 */     xLColumn14.setFFColumnLabel("DESCRIPTION___________________");
/*  288 */     xLColumn14.setColumnWidth(30);
/*  289 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn14);
/*      */     
/*  291 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn7);
/*      */     
/*  293 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn8);
/*      */     
/*  295 */     XLColumn xLColumn17 = new XLColumn("ProdID", "LSEOBUNDLE", "PRODID");
/*  296 */     xLColumn17.setFFColumnLabel("P");
/*  297 */     xLColumn17.setColumnWidth(1);
/*  298 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn17);
/*      */     
/*  300 */     XLSysOrOptColumn xLSysOrOptColumn = new XLSysOrOptColumn();
/*  301 */     xLSysOrOptColumn.setFFColumnLabel("I");
/*  302 */     xLSysOrOptColumn.setColumnWidth(1);
/*  303 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLSysOrOptColumn);
/*      */     
/*  305 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn10);
/*      */     
/*  307 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn11);
/*      */     
/*  309 */     XLColumn xLColumn16 = new XLColumn("FtnClass", "LSEOBUNDLE", "FUNCCLS");
/*  310 */     xLColumn16.setFFColumnLabel("CLAS");
/*  311 */     xLColumn16.setColumnWidth(4);
/*  312 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn16);
/*      */     
/*  314 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn12);
/*      */     
/*  316 */     XLFixedColumn xLFixedColumn3 = new XLFixedColumn("USPartNo", "");
/*  317 */     xLFixedColumn3.setFFColumnLabel("USPN___");
/*  318 */     xLFixedColumn3.setColumnWidth(7);
/*  319 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLFixedColumn3);
/*      */     
/*  321 */     XLColumn xLColumn15 = new XLColumn("SPECBID", "LSEOBUNDLE", "SPECBID");
/*  322 */     xLColumn15.setFFColumnLabel("S");
/*  323 */     xLColumn15.setColumnWidth(1);
/*  324 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLColumn15);
/*      */     
/*  326 */     XLFixedColumn xLFixedColumn4 = new XLFixedColumn("SEOType", "LSEOBUNDLE");
/*  327 */     xLFixedColumn4.setAlwaysShow();
/*  328 */     xLFixedColumn4.setFFColumnLabel("SEOTYPE___");
/*  329 */     xLFixedColumn4.setColumnWidth(10);
/*  330 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLFixedColumn4);
/*      */     
/*  332 */     NEW_LSEOBDL_COLUMN_VCT.addElement(xLIdColumn);
/*      */   }
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
/*  353 */   private static final Vector EOL_LSEO_COLUMN_VCT = new Vector(); static {
/*  354 */     EOL_LSEO_COLUMN_VCT.addElement(xLColumn1);
/*  355 */     EOL_LSEO_COLUMN_VCT.addElement(xLColumn2);
/*      */ 
/*      */     
/*  358 */     XLColumn xLColumn4 = new XLColumn("WDDate", "AVAIL", "EFFECTIVEDATE");
/*  359 */     xLColumn4.setFFColumnLabel("WDDATE____");
/*  360 */     xLColumn4.setColumnWidth(10);
/*      */     
/*  362 */     EOL_LSEO_COLUMN_VCT.addElement(xLColumn4);
/*  363 */     EOL_LSEO_COLUMN_VCT.addElement(xLColumn5);
/*  364 */     EOL_LSEO_COLUMN_VCT.addElement(xLColumn6);
/*  365 */     EOL_LSEO_COLUMN_VCT.addElement(xLFixedColumn2);
/*  366 */     EOL_LSEO_COLUMN_VCT.addElement(xLIdColumn);
/*      */   }
/*  368 */   private static final Vector EOL_LSEOBDL_COLUMN_VCT = new Vector(); static {
/*  369 */     EOL_LSEOBDL_COLUMN_VCT.addElement(xLColumn1);
/*  370 */     EOL_LSEOBDL_COLUMN_VCT.addElement(xLColumn2);
/*  371 */     EOL_LSEOBDL_COLUMN_VCT.addElement(xLColumn4);
/*  372 */     EOL_LSEOBDL_COLUMN_VCT.addElement(xLColumn13);
/*  373 */     EOL_LSEOBDL_COLUMN_VCT.addElement(xLColumn14);
/*  374 */     EOL_LSEOBDL_COLUMN_VCT.addElement(xLFixedColumn4);
/*  375 */     EOL_LSEOBDL_COLUMN_VCT.addElement(xLIdColumn);
/*      */   }
/*      */   
/*  378 */   private String annType = "19";
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
/*      */   private boolean isWW = false;
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
/*      */   public boolean canGenerateReport(EntityItem paramEntityItem, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/*  408 */     boolean bool = true;
/*  409 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "ANNSTATUS");
/*  410 */     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.generateReport: " + paramEntityItem.getKey() + " STATUS: " + 
/*  411 */         PokUtils.getAttributeValue(paramEntityItem, "ANNSTATUS", ", ", "", false) + " [" + str + "]");
/*  412 */     if (!"0020".equals(str)) {
/*      */       
/*  414 */       paramQSMRPTABRSTATUS.addError("NOT_FINAL_ERR", null);
/*  415 */       bool = false;
/*      */     } 
/*      */     
/*  418 */     this.annType = PokUtils.getAttributeFlagValue(paramEntityItem, "ANNTYPE");
/*  419 */     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.generateReport: " + paramEntityItem.getKey() + " ANNTYPE: " + 
/*  420 */         PokUtils.getAttributeValue(paramEntityItem, "ANNTYPE", ", ", "", false) + " [" + this.annType + "]");
/*  421 */     if (!"14".equals(this.annType) && !"19".equals(this.annType)) {
/*  422 */       bool = false;
/*  423 */       paramQSMRPTABRSTATUS.addOutput("Announcement is not 'New' or 'End of Life - Withdrawal from mktg'");
/*      */     } 
/*      */     
/*  426 */     HashSet<String> hashSet = new HashSet();
/*  427 */     hashSet.add("1999");
/*  428 */     this.isWW = PokUtils.contains(paramEntityItem, "GENAREASELECTION", hashSet);
/*  429 */     paramQSMRPTABRSTATUS.addDebug(paramEntityItem.getKey() + " GENAREASELECTION isWW: " + this.isWW);
/*      */     
/*  431 */     for (byte b = 0; b < QSMRPTABRSTATUS.GEOS.length; b++) {
/*  432 */       hashSet.add(QSMRPTABRSTATUS.GEOS[b]);
/*      */     }
/*      */     
/*  435 */     if (!PokUtils.contains(paramEntityItem, "GENAREASELECTION", hashSet)) {
/*  436 */       bool = false;
/*  437 */       paramQSMRPTABRSTATUS.addOutput("Announcement does not have required 'General Area Selection' value.");
/*  438 */       paramQSMRPTABRSTATUS.addDebug("GENAREASELECTION did not include " + hashSet + ", was " + 
/*  439 */           PokUtils.getAttributeValue(paramEntityItem, "GENAREASELECTION", ", ", "", false) + "[" + 
/*  440 */           PokUtils.getAttributeFlagValue(paramEntityItem, "GENAREASELECTION") + "]");
/*  441 */       paramQSMRPTABRSTATUS.setNoReport();
/*      */     } 
/*  443 */     hashSet.clear();
/*      */     
/*  445 */     return bool;
/*      */   }
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
/*      */   public boolean canGenerateReport(EntityList paramEntityList, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/*  497 */     boolean bool = false;
/*  498 */     String str = "149";
/*  499 */     if (isNewAnn()) {
/*  500 */       str = "146";
/*      */     }
/*      */ 
/*      */     
/*  504 */     Vector vector = PokUtils.getEntitiesWithMatchedAttr(paramEntityList.getEntityGroup("AVAIL"), "AVAILTYPE", str);
/*  505 */     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.canGenerateReport availtype:" + str + " has availvct.size: " + vector.size());
/*  506 */     if (vector.size() == 0) {
/*  507 */       if (isNewAnn()) {
/*  508 */         paramQSMRPTABRSTATUS.addOutput("No Planned Avails found for this Announcement");
/*      */       } else {
/*  510 */         paramQSMRPTABRSTATUS.addOutput("No Last Order Avails found for this Announcement");
/*      */       } 
/*      */     } else {
/*      */       byte b;
/*  514 */       label76: for (b = 0; b < QSMRPTABRSTATUS.GEOS.length; b++) {
/*  515 */         String str1 = QSMRPTABRSTATUS.GEOS[b];
/*      */ 
/*      */         
/*  518 */         Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "GENAREASELECTION", str1);
/*  519 */         paramQSMRPTABRSTATUS.addDebug("QSMANNABR.canGenerateReport GENAREASELECTION:" + str1 + " has geoavailvct.size: " + vector1
/*  520 */             .size());
/*      */         
/*  522 */         if (this.isWW) {
/*      */           
/*  524 */           Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector, "GENAREASELECTION", "1999");
/*  525 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.canGenerateReport GENAREASELECTION:1999 has wwgeoavailVct.size: " + vector2
/*  526 */               .size());
/*  527 */           if (vector2.size() > 0) {
/*  528 */             for (byte b1 = 0; b1 < vector2.size(); b1++) {
/*  529 */               EntityItem entityItem = vector2.elementAt(b1);
/*  530 */               if (vector1.contains(entityItem)) {
/*  531 */                 paramQSMRPTABRSTATUS.addDebug("QSMANNABR.canGenerateReport WW " + entityItem.getKey() + " was also found for " + str1);
/*      */               } else {
/*  533 */                 paramQSMRPTABRSTATUS.addDebug("QSMANNABR.canGenerateReport adding WW " + entityItem.getKey() + " to avails found for " + str1);
/*  534 */                 vector1.addElement(entityItem);
/*      */               } 
/*      */             } 
/*  537 */             vector2.clear();
/*      */           } 
/*      */         } 
/*      */         
/*  541 */         if (vector1.size() == 0) {
/*  542 */           paramQSMRPTABRSTATUS.addOutput("No Avails found for this Announcement with 'General Area Selection':" + str1 + (this.isWW ? "|1999" : ""));
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  547 */           Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "LSEOAVAIL", "LSEO");
/*  548 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.canGenerateReport lseoVct.size: " + vector2.size() + " for 'General Area Selection':" + str1);
/*      */ 
/*      */           
/*  551 */           for (byte b1 = 0; b1 < vector2.size(); b1++) {
/*  552 */             EntityItem entityItem = vector2.elementAt(b1);
/*  553 */             if (isNewAnn()) {
/*      */               
/*  555 */               Vector vector4 = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "WWSEO");
/*  556 */               Vector<EntityItem> vector5 = PokUtils.getAllLinkedEntities(vector4, "MODELWWSEO", "MODEL");
/*  557 */               for (byte b2 = 0; b2 < vector5.size(); b2++) {
/*  558 */                 EntityItem entityItem1 = vector5.elementAt(b2);
/*  559 */                 String str2 = PokUtils.getAttributeFlagValue(entityItem1, "COFCAT");
/*  560 */                 paramQSMRPTABRSTATUS.addDebug("QSMANNABR.canGenerateReport " + entityItem.getKey() + " " + entityItem1
/*  561 */                     .getKey() + " cofcat:" + str2);
/*  562 */                 if ("100".equals(str2) || "101".equals(str2)) {
/*  563 */                   bool = true;
/*      */                   break label76;
/*      */                 } 
/*      */               } 
/*  567 */               vector4.clear();
/*  568 */               vector5.clear();
/*      */             } else {
/*  570 */               bool = true;
/*      */               break label76;
/*      */             } 
/*      */           } 
/*  574 */           if (isNewAnn()) {
/*  575 */             paramQSMRPTABRSTATUS.addOutput("No LSEOs found for this Announcement for 'General Area Selection':" + str1 + " with Hardware or Software Models");
/*      */           } else {
/*      */             
/*  578 */             paramQSMRPTABRSTATUS.addOutput("No LSEOs found for this Announcement for 'General Area Selection':" + str1);
/*      */           } 
/*      */           
/*  581 */           vector2.clear();
/*      */           
/*  583 */           Vector vector3 = PokUtils.getAllLinkedEntities(vector1, "LSEOBUNDLEAVAIL", "LSEOBUNDLE");
/*  584 */           if (vector3.size() == 0) {
/*  585 */             paramQSMRPTABRSTATUS.addOutput("No LSEOBUNDLEs found for this Announcement for 'General Area Selection':" + str1);
/*      */           } else {
/*  587 */             bool = true;
/*      */             break;
/*      */           } 
/*  590 */           vector1.clear();
/*      */         } 
/*      */       } 
/*  593 */       vector.clear();
/*      */     } 
/*  595 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVeName() {
/*  603 */     if (isNewAnn()) {
/*  604 */       return "QSMANNNEW";
/*      */     }
/*  606 */     return "QSMANNEOL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  616 */     return "1.9";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnCount() {
/*  623 */     if ("19".equals(this.annType)) {
/*  624 */       return NEW_LSEO_COLUMN_VCT.size();
/*      */     }
/*  626 */     return EOL_LSEO_COLUMN_VCT.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getColumnLabel(int paramInt) {
/*  634 */     if ("19".equals(this.annType)) {
/*  635 */       return ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(paramInt)).getColumnLabel();
/*      */     }
/*  637 */     return ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(paramInt)).getColumnLabel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFFColumnLabel(int paramInt) {
/*  646 */     if ("19".equals(this.annType)) {
/*  647 */       return ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(paramInt)).getFFColumnLabel();
/*      */     }
/*  649 */     return ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(paramInt)).getFFColumnLabel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnWidth(int paramInt) {
/*  658 */     if ("19".equals(this.annType)) {
/*  659 */       return ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(paramInt)).getColumnWidth();
/*      */     }
/*  661 */     return ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(paramInt)).getColumnWidth();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnValue(HSSFCell paramHSSFCell, String paramString, Hashtable paramHashtable, int paramInt) {
/*  669 */     if ("19".equals(this.annType)) {
/*  670 */       if (paramString.equals("LSEO")) {
/*  671 */         ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(paramInt)).setColumnValue(paramHSSFCell, paramHashtable);
/*      */       } else {
/*  673 */         ((XLColumn)NEW_LSEOBDL_COLUMN_VCT.elementAt(paramInt)).setColumnValue(paramHSSFCell, paramHashtable);
/*      */       }
/*      */     
/*  676 */     } else if (paramString.equals("LSEO")) {
/*  677 */       ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(paramInt)).setColumnValue(paramHSSFCell, paramHashtable);
/*      */     } else {
/*  679 */       ((XLColumn)EOL_LSEOBDL_COLUMN_VCT.elementAt(paramInt)).setColumnValue(paramHSSFCell, paramHashtable);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getColumnValue(String paramString, Hashtable paramHashtable, int paramInt) {
/*  689 */     String str = "";
/*  690 */     if ("19".equals(this.annType)) {
/*  691 */       if (paramString.equals("LSEO")) {
/*  692 */         str = ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(paramInt)).getColumnValue(paramHashtable);
/*      */       } else {
/*  694 */         str = ((XLColumn)NEW_LSEOBDL_COLUMN_VCT.elementAt(paramInt)).getColumnValue(paramHashtable);
/*      */       }
/*      */     
/*  697 */     } else if (paramString.equals("LSEO")) {
/*  698 */       str = ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(paramInt)).getColumnValue(paramHashtable);
/*      */     } else {
/*  700 */       str = ((XLColumn)EOL_LSEOBDL_COLUMN_VCT.elementAt(paramInt)).getColumnValue(paramHashtable);
/*      */     } 
/*      */     
/*  703 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChanged(String paramString, Hashtable paramHashtable, int paramInt) {
/*  711 */     if ("19".equals(this.annType)) {
/*  712 */       if (paramString.equals("LSEO")) {
/*  713 */         return ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(paramInt)).isChanged(paramHashtable);
/*      */       }
/*  715 */       return ((XLColumn)NEW_LSEOBDL_COLUMN_VCT.elementAt(paramInt)).isChanged(paramHashtable);
/*      */     } 
/*      */     
/*  718 */     if (paramString.equals("LSEO")) {
/*  719 */       return ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(paramInt)).isChanged(paramHashtable);
/*      */     }
/*  721 */     return ((XLColumn)EOL_LSEOBDL_COLUMN_VCT.elementAt(paramInt)).isChanged(paramHashtable);
/*      */   }
/*      */   
/*      */   private boolean isNewAnn() {
/*  725 */     return "19".equals(this.annType);
/*      */   }
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
/*      */   public Vector getRowItems(EntityList paramEntityList, Hashtable paramHashtable, String paramString, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/*  758 */     Vector<XLRow> vector = new Vector();
/*  759 */     String str = "149";
/*  760 */     if (isNewAnn()) {
/*  761 */       str = "146";
/*      */     }
/*  763 */     Vector<EntityItem> vector1 = null;
/*  764 */     EntityItem entityItem = null;
/*  765 */     if (paramEntityList != null) {
/*  766 */       entityItem = paramEntityList.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  768 */       vector1 = PokUtils.getEntitiesWithMatchedAttr(paramEntityList.getEntityGroup("AVAIL"), "AVAILTYPE", str);
/*  769 */       paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems from list availtype:" + str + " availvct.size: " + vector1.size());
/*      */     } 
/*  771 */     if (paramHashtable != null) {
/*      */       
/*  773 */       Vector<DiffEntity> vector3 = (Vector)paramHashtable.get("AVAIL");
/*  774 */       vector1 = new Vector();
/*      */       
/*  776 */       for (byte b1 = 0; b1 < vector3.size(); b1++) {
/*  777 */         DiffEntity diffEntity = vector3.elementAt(b1);
/*  778 */         if (!diffEntity.isDeleted()) {
/*  779 */           vector1.addElement(diffEntity.getCurrentEntityItem());
/*      */         }
/*      */       } 
/*  782 */       Vector<DiffEntity> vector4 = (Vector)paramHashtable.get("ROOT");
/*  783 */       entityItem = ((DiffEntity)vector4.firstElement()).getCurrentEntityItem();
/*  784 */       paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems from diff availtype:" + str + " availvct.size: " + vector1.size());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  789 */     Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "GENAREASELECTION", "1999");
/*  790 */     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems GENAREASELECTION:1999 has wwavailvct.size: " + vector2
/*  791 */         .size());
/*  792 */     vector1 = PokUtils.getEntitiesWithMatchedAttr(vector1, "GENAREASELECTION", paramString);
/*  793 */     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems GENAREASELECTION:" + paramString + " availvct.size: " + vector1.size());
/*  794 */     if (this.isWW && vector2.size() > 0) {
/*  795 */       for (byte b1 = 0; b1 < vector2.size(); b1++) {
/*  796 */         EntityItem entityItem1 = vector2.elementAt(b1);
/*  797 */         if (vector1.contains(entityItem1)) {
/*  798 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems WW " + entityItem1.getKey() + " was also found for " + paramString);
/*      */         } else {
/*  800 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems adding WW " + entityItem1.getKey() + " to avails found for " + paramString);
/*  801 */           vector1.insertElementAt(entityItem1, 0);
/*      */         } 
/*      */       } 
/*      */     }
/*  805 */     vector2.clear();
/*  806 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */ 
/*      */     
/*  809 */     for (byte b = 0; b < vector1.size(); b++) {
/*      */       
/*  811 */       EntityItem entityItem1 = vector1.elementAt(b);
/*      */       
/*  813 */       String str1 = entityItem1.getKey() + ":GENAREA:" + PokUtils.getAttributeFlagValue(entityItem1, "GENAREASELECTION");
/*      */       
/*  815 */       Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "LSEOAVAIL", "LSEO");
/*  816 */       paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems " + entityItem1.getKey() + " lseoVct.size: " + vector3.size());
/*      */       
/*  818 */       for (byte b1 = 0; b1 < vector3.size(); b1++) {
/*  819 */         EntityItem entityItem2 = vector3.elementAt(b1);
/*  820 */         if (hashtable.containsKey(entityItem2.getKey())) {
/*  821 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems already completed " + entityItem2.getKey() + " for " + hashtable
/*  822 */               .get(entityItem2.getKey()) + " skipping " + str1);
/*      */         } else {
/*      */           
/*  825 */           hashtable.put(entityItem2.getKey(), str1);
/*  826 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems checking " + entityItem2.getKey() + " for " + str1);
/*  827 */           Vector<EntityItem> vector5 = PokUtils.getAllLinkedEntities(entityItem2, "WWSEOLSEO", "WWSEO");
/*  828 */           for (byte b3 = 0; b3 < vector5.size(); b3++) {
/*  829 */             EntityItem entityItem3 = vector5.elementAt(b3);
/*  830 */             if (isNewAnn()) {
/*      */               
/*  832 */               Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(entityItem3, "WWSEOPROJA", "PROJ");
/*  833 */               Vector<EntityItem> vector7 = PokUtils.getAllLinkedEntities(entityItem3, "MODELWWSEO", "MODEL");
/*  834 */               for (byte b4 = 0; b4 < vector7.size(); b4++) {
/*  835 */                 EntityItem entityItem4 = vector7.elementAt(b4);
/*  836 */                 String str2 = PokUtils.getAttributeFlagValue(entityItem4, "COFCAT");
/*  837 */                 paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems " + entityItem3.getKey() + " " + entityItem2.getKey() + " " + entityItem4
/*  838 */                     .getKey() + " cofcat:" + str2);
/*  839 */                 if ("100".equals(str2) || "101".equals(str2)) {
/*      */                   
/*  841 */                   XLRow xLRow = new XLRow((paramHashtable == null) ? entityItem2 : paramHashtable.get(entityItem2.getKey()));
/*  842 */                   vector.addElement(xLRow);
/*  843 */                   xLRow.addRowItem((paramHashtable == null) ? entityItem1 : paramHashtable
/*  844 */                       .get(entityItem1.getKey()));
/*  845 */                   xLRow.addRowItem((paramHashtable == null) ? entityItem3 : paramHashtable
/*  846 */                       .get(entityItem3.getKey()));
/*  847 */                   xLRow.addRowItem((paramHashtable == null) ? entityItem4 : paramHashtable
/*  848 */                       .get(entityItem4.getKey()));
/*  849 */                   xLRow.addRowItem((paramHashtable == null) ? entityItem : paramHashtable
/*  850 */                       .get(entityItem.getKey()));
/*  851 */                   Vector<EntityItem> vector8 = PokUtils.getAllLinkedEntities(entityItem4, "MODELGEOMOD", "GEOMOD");
/*      */                   
/*  853 */                   Vector<EntityItem> vector9 = PokUtils.getEntitiesWithMatchedAttr(vector8, "GENAREASELECTION", "1999");
/*  854 */                   paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems GEOMOD GENAREASELECTION:1999 has wwgeomodVct.size: " + vector9
/*  855 */                       .size());
/*  856 */                   if (vector9.size() == 0) {
/*      */                     
/*  858 */                     vector8 = PokUtils.getEntitiesWithMatchedAttr(vector8, "GENAREASELECTION", paramString);
/*  859 */                     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems GEOMOD GENAREASELECTION:" + paramString + " has geomodVct.size: " + vector8
/*  860 */                         .size());
/*      */                   } else {
/*  862 */                     vector8 = vector9;
/*      */                   } 
/*      */                   
/*  865 */                   if (vector8.size() > 0) {
/*  866 */                     if (vector8.size() > 1) {
/*  867 */                       for (byte b5 = 0; b5 < vector8.size(); b5++) {
/*  868 */                         paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems WARNING: Found more than one GEOMOD for " + entityItem4
/*  869 */                             .getKey() + " " + ((EntityItem)vector8.elementAt(b5)).getKey());
/*      */                       }
/*      */                     }
/*  872 */                     EntityItem entityItem5 = vector8.firstElement();
/*  873 */                     xLRow.addRowItem((paramHashtable == null) ? entityItem5 : paramHashtable
/*  874 */                         .get(entityItem5.getKey()));
/*  875 */                     vector8.clear();
/*      */                   } 
/*  877 */                   if (vector6.size() > 0) {
/*  878 */                     if (vector6.size() > 1) {
/*  879 */                       for (byte b5 = 0; b5 < vector6.size(); b5++) {
/*  880 */                         paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems WARNING: Found more than one PROJ for " + entityItem3
/*  881 */                             .getKey() + " " + ((EntityItem)vector6.elementAt(b5)).getKey());
/*      */                       }
/*      */                     }
/*  884 */                     EntityItem entityItem5 = vector6.firstElement();
/*  885 */                     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems using " + entityItem5.getKey());
/*  886 */                     xLRow.addRowItem((paramHashtable == null) ? entityItem5 : paramHashtable
/*  887 */                         .get(entityItem5.getKey()));
/*      */                     
/*  889 */                     Vector<EntityItem> vector10 = PokUtils.getAllLinkedEntities(entityItem5, "PROJSGMNTACRNYMA", "SGMNTACRNYM");
/*  890 */                     if (vector10.size() > 0) {
/*  891 */                       if (vector10.size() > 1) {
/*  892 */                         for (byte b5 = 0; b5 < vector10.size(); b5++) {
/*  893 */                           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems WARNING: Found more than one SGMNTACRNYM for " + entityItem5
/*  894 */                               .getKey() + " " + ((EntityItem)vector10.elementAt(b5)).getKey());
/*      */                         }
/*      */                       }
/*  897 */                       EntityItem entityItem6 = vector10.firstElement();
/*  898 */                       paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems using " + entityItem6.getKey());
/*  899 */                       xLRow.addRowItem((paramHashtable == null) ? entityItem6 : paramHashtable
/*  900 */                           .get(entityItem6.getKey()));
/*  901 */                       vector10.clear();
/*      */                     } 
/*      */                   } 
/*  904 */                   paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems NEWANN Adding " + xLRow);
/*      */                 } 
/*      */               } 
/*      */             } else {
/*      */               
/*  909 */               XLRow xLRow = new XLRow((paramHashtable == null) ? entityItem2 : paramHashtable.get(entityItem2.getKey()));
/*  910 */               vector.addElement(xLRow);
/*  911 */               xLRow.addRowItem((paramHashtable == null) ? entityItem1 : paramHashtable
/*  912 */                   .get(entityItem1.getKey()));
/*  913 */               xLRow.addRowItem((paramHashtable == null) ? entityItem3 : paramHashtable
/*  914 */                   .get(entityItem3.getKey()));
/*  915 */               xLRow.addRowItem((paramHashtable == null) ? entityItem : paramHashtable
/*  916 */                   .get(entityItem.getKey()));
/*  917 */               paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems EOLANN Adding " + xLRow);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  922 */       Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(entityItem1, "LSEOBUNDLEAVAIL", "LSEOBUNDLE");
/*  923 */       paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems " + entityItem1.getKey() + " lseobdlVct.size: " + vector4.size());
/*  924 */       for (byte b2 = 0; b2 < vector4.size(); b2++) {
/*  925 */         EntityItem entityItem2 = vector4.elementAt(b2);
/*  926 */         if (hashtable.containsKey(entityItem2.getKey())) {
/*  927 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems already completed " + entityItem2.getKey() + " for " + hashtable
/*  928 */               .get(entityItem2.getKey()) + " skipping " + str1);
/*      */         } else {
/*      */           
/*  931 */           hashtable.put(entityItem2.getKey(), str1);
/*  932 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems checking " + entityItem2.getKey() + " for " + str1);
/*      */           
/*  934 */           XLRow xLRow = new XLRow((paramHashtable == null) ? entityItem2 : paramHashtable.get(entityItem2.getKey()));
/*  935 */           vector.addElement(xLRow);
/*  936 */           xLRow.addRowItem((paramHashtable == null) ? entityItem1 : paramHashtable
/*  937 */               .get(entityItem1.getKey()));
/*  938 */           xLRow.addRowItem((paramHashtable == null) ? entityItem : paramHashtable
/*  939 */               .get(entityItem.getKey()));
/*  940 */           if (isNewAnn()) {
/*  941 */             Vector<EntityItem> vector5 = PokUtils.getAllLinkedEntities(entityItem2, "LSEOBUNDLEPROJA", "PROJ");
/*  942 */             if (vector5.size() > 0) {
/*  943 */               if (vector5.size() > 1) {
/*  944 */                 for (byte b3 = 0; b3 < vector5.size(); b3++) {
/*  945 */                   paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems WARNING: Found more than one PROJ for " + entityItem2
/*  946 */                       .getKey() + " " + ((EntityItem)vector5.elementAt(b3)).getKey());
/*      */                 }
/*      */               }
/*  949 */               EntityItem entityItem3 = vector5.firstElement();
/*  950 */               paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems using " + entityItem3.getKey());
/*  951 */               xLRow.addRowItem((paramHashtable == null) ? entityItem3 : paramHashtable
/*  952 */                   .get(entityItem3.getKey()));
/*      */               
/*  954 */               Vector<EntityItem> vector8 = PokUtils.getAllLinkedEntities(entityItem3, "PROJSGMNTACRNYMA", "SGMNTACRNYM");
/*  955 */               if (vector8.size() > 0) {
/*  956 */                 if (vector8.size() > 1) {
/*  957 */                   for (byte b3 = 0; b3 < vector8.size(); b3++) {
/*  958 */                     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems WARNING: Found more than one SGMNTACRNYM for " + entityItem3
/*  959 */                         .getKey() + " " + ((EntityItem)vector8.elementAt(b3)).getKey());
/*      */                   }
/*      */                 }
/*  962 */                 EntityItem entityItem4 = vector8.firstElement();
/*  963 */                 paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems using " + entityItem4.getKey());
/*  964 */                 xLRow.addRowItem((paramHashtable == null) ? entityItem4 : paramHashtable
/*  965 */                     .get(entityItem4.getKey()));
/*  966 */                 vector8.clear();
/*      */               } 
/*  968 */               vector5.clear();
/*      */             } 
/*  970 */             Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(entityItem2, "LSEOBUNDLEGEOMOD", "GEOMOD");
/*      */             
/*  972 */             Vector<EntityItem> vector7 = PokUtils.getEntitiesWithMatchedAttr(vector6, "GENAREASELECTION", "1999");
/*  973 */             paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems GEOMOD GENAREASELECTION:1999 has wwgeomodVct.size: " + vector7
/*  974 */                 .size());
/*  975 */             if (vector7.size() == 0) {
/*      */               
/*  977 */               vector6 = PokUtils.getEntitiesWithMatchedAttr(vector6, "GENAREASELECTION", paramString);
/*  978 */               paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems GEOMOD GENAREASELECTION:" + paramString + " has geomodVct.size: " + vector6
/*  979 */                   .size());
/*      */             } else {
/*  981 */               vector6 = vector7;
/*      */             } 
/*      */             
/*  984 */             if (vector6.size() > 0) {
/*  985 */               if (vector6.size() > 1) {
/*  986 */                 for (byte b3 = 0; b3 < vector6.size(); b3++) {
/*  987 */                   paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems WARNING: Found more than one GEOMOD for " + entityItem2
/*  988 */                       .getKey() + " " + ((EntityItem)vector6.elementAt(b3)).getKey());
/*      */                 }
/*      */               }
/*  991 */               EntityItem entityItem3 = vector6.firstElement();
/*  992 */               xLRow.addRowItem((paramHashtable == null) ? entityItem3 : paramHashtable
/*  993 */                   .get(entityItem3.getKey()));
/*  994 */               vector6.clear();
/*      */             } 
/*      */           } 
/*  997 */           paramQSMRPTABRSTATUS.addDebug("QSMANNABR.getRowItems Adding " + xLRow);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1001 */     hashtable.clear();
/*      */     
/* 1003 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean withinDateRange(EntityItem paramEntityItem, String paramString, QSMRPTABRSTATUS paramQSMRPTABRSTATUS) {
/* 1010 */     boolean bool = false;
/* 1011 */     Object[] arrayOfObject = new Object[5];
/* 1012 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "", false);
/* 1013 */     PDGUtility pDGUtility = new PDGUtility();
/* 1014 */     String str2 = pDGUtility.getDate(paramString, 30);
/* 1015 */     arrayOfObject[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 1016 */     arrayOfObject[1] = str1;
/* 1017 */     arrayOfObject[2] = "";
/* 1018 */     arrayOfObject[3] = paramString;
/* 1019 */     arrayOfObject[4] = str2;
/*      */     
/* 1021 */     paramQSMRPTABRSTATUS.addDebug("QSMANNABR.withinDateRange now " + paramString + " anndate " + str1 + " nowplus30 " + str2);
/* 1022 */     if (str2.compareTo(str1) >= 0 && str1.compareTo(paramString) >= 0) {
/* 1023 */       bool = true;
/*      */     } else {
/* 1025 */       arrayOfObject[2] = "not";
/*      */     } 
/*      */     
/* 1028 */     paramQSMRPTABRSTATUS.addOutput("DATE_RANGE_MSG", arrayOfObject);
/*      */     
/* 1030 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRowOne(EntityItem paramEntityItem) {
/* 1040 */     String str = PokUtils.getAttributeValue(paramEntityItem, "ANNNUMBER", "", "", false);
/* 1041 */     if ("19".equals(this.annType)) {
/* 1042 */       return XLColumn.formatToWidth(str, 6, false) + " New";
/*      */     }
/* 1044 */     return XLColumn.formatToWidth(str, 6, false) + " End";
/*      */   }
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
/*      */   public String getRowTwoPrefix() {
/* 1060 */     if ("19".equals(this.annType)) {
/* 1061 */       return XLColumn.formatToWidth("Date______", 10) + " " + 
/* 1062 */         XLColumn.formatToWidth("Time___________", 15);
/*      */     }
/* 1064 */     return XLColumn.formatToWidth("IFCDate___", 10) + " " + 
/* 1065 */       XLColumn.formatToWidth("IFCTime________", 15);
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMANNABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */