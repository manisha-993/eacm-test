/*       */ package COM.ibm.eannounce.abr.sgv30b;
/*       */ 
/*       */ import COM.ibm.eannounce.objects.EANAttribute;
/*       */ import COM.ibm.eannounce.objects.EANBlobAttribute;
/*       */ import COM.ibm.eannounce.objects.EANEntity;
/*       */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*       */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*       */ import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
/*       */ import COM.ibm.eannounce.objects.EntityGroup;
/*       */ import COM.ibm.eannounce.objects.EntityItem;
/*       */ import COM.ibm.eannounce.objects.EntityList;
/*       */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*       */ import COM.ibm.eannounce.objects.GeneralAreaList;
/*       */ import COM.ibm.eannounce.objects.MetaFlag;
/*       */ import COM.ibm.eannounce.objects.RowSelectableTable;
/*       */ import COM.ibm.eannounce.objects.SearchActionItem;
/*       */ import COM.ibm.opicmpdh.middleware.Database;
/*       */ import COM.ibm.opicmpdh.middleware.Profile;
/*       */ import java.text.SimpleDateFormat;
/*       */ import java.text.StringCharacterIterator;
/*       */ import java.util.Date;
/*       */ import java.util.Enumeration;
/*       */ import java.util.Hashtable;
/*       */ import java.util.Iterator;
/*       */ import java.util.Set;
/*       */ import java.util.StringTokenizer;
/*       */ import java.util.TreeMap;
/*       */ import java.util.TreeSet;
/*       */ import java.util.Vector;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public class AUTOGENRpt
/*       */ {
/*       */   public static final String VERSION = "$Revision: 1.21 $";
/*       */   private static final String DELIMITER = "|";
/*       */   private static final int MW_VENTITY_LIMIT = 50;
/*       */   private static final int ROW_LIMIT = 50;
/*       */   private static final String ANNOUNCEMENT_TYPE_NEW = "new";
/*       */   private static final String ANNOUNCEMENT_TYPE_WITHDRAW = "withdraw";
/*       */   private static final String ISERIES = "iSeries";
/*       */   private static final String PSERIES = "pSeries";
/*       */   private static final String TS = "totalStorage";
/*       */   private static final String XSERIES = "xSeries";
/*       */   private static final String ZSERIES = "zSeries";
/*       */   private static final int FORMAT1 = 1;
/*       */   private static final int FORMAT2 = 2;
/*       */   private static final int FORMAT3 = 3;
/*       */   public static final boolean GMLFORMAT = true;
/*       */   public static final boolean XMLFORMAT = false;
/*       */   private static final int NEWMODELS = 1;
/*       */   private static final int EXISTINGMODELS = 2;
/*       */   private static final int NEWFC = 1;
/*       */   private static final int EXISTINGFC = 2;
/*       */   private EntityList list;
/*       */   private GeneralAreaList gal;
/*       */   private Database dbCurrent;
/*   339 */   private EntityItem rootEntity = null;
/*   340 */   private String annDate = "";
/*   341 */   private String annCodeName = "";
/*   342 */   private String annType = "";
/*   343 */   private String annTypeDesc = "";
/*       */   
/*   345 */   private Vector availV = null;
/*   346 */   private Vector availVector = null;
/*       */   
/*       */   private Hashtable usGeoHT;
/*       */   
/*       */   private Hashtable apGeoHT;
/*       */   
/*       */   private Hashtable laGeoHT;
/*       */   private Hashtable canGeoHT;
/*       */   private Hashtable emeaGeoHT;
/*       */   private Hashtable geoHT;
/*   356 */   private String brand = "";
/*   357 */   private int format = 0;
/*   358 */   private String inventoryGroup = "";
/*   359 */   private String inventoryGroupFlag = "";
/*       */   
/*       */   private TreeSet machineTypeTS;
/*       */   
/*       */   private Hashtable featureHT;
/*       */   
/*       */   private TreeMap productNumber_NewModels_TM;
/*       */   
/*       */   private Hashtable productNumber_NewModels_HT;
/*       */   
/*       */   private TreeMap productNumber_NewFC_TM;
/*       */   
/*       */   private TreeMap productNumber_ExistingFC_TM;
/*       */   
/*       */   private TreeMap productNumber_NewModels_NewFC_TM;
/*       */   
/*       */   private TreeMap productNumber_NewModels_ExistingFC_TM;
/*       */   
/*       */   private TreeMap productNumber_ExistingModels_NewFC_TM;
/*       */   
/*       */   private TreeMap productNumber_ExistingModels_ExistingFC_TM;
/*       */   
/*       */   private TreeMap productNumber_MTM_Conversions_TM;
/*       */   
/*       */   private TreeMap productNumber_Model_Conversions_TM;
/*       */   
/*       */   private TreeMap productNumber_Feature_Conversions_TM;
/*       */   
/*       */   private Vector featureVector;
/*       */   
/*       */   private TreeMap charges_NewModels_TM;
/*       */   
/*       */   private TreeMap charges_NewFC_TM;
/*       */   
/*       */   private TreeMap charges_ExistingFC_TM;
/*       */   
/*       */   private TreeMap charges_NewModels_NewFC_TM;
/*       */   
/*       */   private TreeMap charges_NewModels_ExistingFC_TM;
/*       */   private TreeMap charges_ExistingModels_NewFC_TM;
/*       */   private TreeMap charges_ExistingModels_ExistingFC_TM;
/*       */   private TreeMap charges_Feature_Conversions_TM;
/*       */   private TreeMap q779_NewModels_TM;
/*       */   private TreeMap q779_NewFeatures_TM;
/*       */   private TreeMap salesManual_TM;
/*       */   private TreeMap salesManualSpecifyFeatures_TM;
/*       */   private TreeMap salesManualSpecialFeaturesInitialOrder_TM;
/*       */   private TreeMap salesManualSpecialFeaturesOther_TM;
/*       */   private TreeMap supportedDevices_TM;
/*       */   private TreeMap featureMatrix_TM;
/*       */   private StringBuffer headerSB;
/*       */   private Vector lseoVector;
/*       */   private TreeSet mtmTS;
/*       */   private TreeMap seoTable_TM;
/*       */   private TreeMap seoDescription_TM;
/*       */   private TreeSet featureMatrixError;
/*       */   private boolean debug;
/*       */   private StringBuffer debugBuff;
/*       */   private static final int I_10 = 10;
/*       */   private static final int I_11 = 11;
/*       */   private static final int I_12 = 12;
/*       */   private static final int I_13 = 13;
/*       */   private static final int I_14 = 14;
/*       */   private static final int I_15 = 15;
/*       */   private static final int I_16 = 16;
/*       */   private static final int I_17 = 17;
/*       */   private static final int I_18 = 18;
/*       */   private static final int I_19 = 19;
/*       */   private static final int I_20 = 20;
/*       */   private static final int I_21 = 21;
/*       */   private static final int I_22 = 22;
/*       */   private static final int I_23 = 23;
/*       */   private static final int I_24 = 24;
/*       */   private static final int I_25 = 25;
/*       */   private static final int I_26 = 26;
/*       */   private static final int I_27 = 27;
/*       */   private static final int I_28 = 28;
/*       */   private static final int I_29 = 29;
/*       */   private static final int I_30 = 30;
/*       */   private static final int I_37 = 37;
/*       */   private static final int I_38 = 38;
/*       */   private static final int I_41 = 41;
/*       */   private static final int I_50 = 50;
/*       */   private static final int I_51 = 51;
/*       */   private static final int I_53 = 53;
/*       */   private static final int I_58 = 58;
/*       */   private static final int I_68 = 68;
/*       */   private static final int I_69 = 69;
/*       */   private static final int I_70 = 70;
/*       */   private static final int I_79 = 79;
/*   449 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   450 */   static final String NEWLINE = new String(FOOL_JTEST);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public AUTOGENRpt(EntityList paramEntityList, GeneralAreaList paramGeneralAreaList, Database paramDatabase) {
/*   461 */     this.list = paramEntityList;
/*   462 */     this.gal = paramGeneralAreaList;
/*   463 */     this.dbCurrent = paramDatabase;
/*       */ 
/*       */ 
/*       */     
/*   467 */     this.usGeoHT = new Hashtable<>();
/*   468 */     this.apGeoHT = new Hashtable<>();
/*   469 */     this.laGeoHT = new Hashtable<>();
/*   470 */     this.canGeoHT = new Hashtable<>();
/*   471 */     this.emeaGeoHT = new Hashtable<>();
/*       */     
/*   473 */     this.geoHT = new Hashtable<>();
/*       */     
/*   475 */     this.machineTypeTS = new TreeSet();
/*   476 */     this.featureHT = new Hashtable<>();
/*       */     
/*   478 */     this.productNumber_NewModels_TM = new TreeMap<>();
/*   479 */     this.productNumber_NewModels_HT = new Hashtable<>();
/*   480 */     this.productNumber_NewFC_TM = new TreeMap<>();
/*   481 */     this.productNumber_ExistingFC_TM = new TreeMap<>();
/*   482 */     this.productNumber_NewModels_NewFC_TM = new TreeMap<>();
/*   483 */     this.productNumber_NewModels_ExistingFC_TM = new TreeMap<>();
/*   484 */     this.productNumber_ExistingModels_NewFC_TM = new TreeMap<>();
/*   485 */     this.productNumber_ExistingModels_ExistingFC_TM = new TreeMap<>();
/*   486 */     this.productNumber_MTM_Conversions_TM = new TreeMap<>();
/*   487 */     this.productNumber_Model_Conversions_TM = new TreeMap<>();
/*   488 */     this.productNumber_Feature_Conversions_TM = new TreeMap<>();
/*   489 */     this.featureVector = new Vector();
/*       */     
/*   491 */     this.charges_NewModels_TM = new TreeMap<>();
/*   492 */     this.charges_NewFC_TM = new TreeMap<>();
/*   493 */     this.charges_ExistingFC_TM = new TreeMap<>();
/*   494 */     this.charges_NewModels_NewFC_TM = new TreeMap<>();
/*   495 */     this.charges_NewModels_ExistingFC_TM = new TreeMap<>();
/*   496 */     this.charges_ExistingModels_NewFC_TM = new TreeMap<>();
/*   497 */     this.charges_ExistingModels_ExistingFC_TM = new TreeMap<>();
/*   498 */     this.charges_Feature_Conversions_TM = new TreeMap<>();
/*       */     
/*   500 */     this.q779_NewModels_TM = new TreeMap<>();
/*   501 */     this.q779_NewFeatures_TM = new TreeMap<>();
/*       */     
/*   503 */     this.salesManual_TM = new TreeMap<>();
/*   504 */     this.salesManualSpecifyFeatures_TM = new TreeMap<>();
/*   505 */     this.salesManualSpecialFeaturesInitialOrder_TM = new TreeMap<>();
/*   506 */     this.salesManualSpecialFeaturesOther_TM = new TreeMap<>();
/*       */     
/*   508 */     this.supportedDevices_TM = new TreeMap<>();
/*       */     
/*   510 */     this.featureMatrix_TM = new TreeMap<>();
/*       */     
/*   512 */     this.headerSB = new StringBuffer();
/*       */     
/*   514 */     this.lseoVector = new Vector();
/*   515 */     this.mtmTS = new TreeSet();
/*   516 */     this.seoTable_TM = new TreeMap<>();
/*   517 */     this.seoDescription_TM = new TreeMap<>();
/*       */     
/*   519 */     this.featureMatrixError = new TreeSet();
/*       */ 
/*       */     
/*   522 */     this.debug = false;
/*   523 */     this.debugBuff = new StringBuffer();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean init_withDebug(StringBuffer paramStringBuffer) {
/*   534 */     this.debug = true;
/*   535 */     return init(paramStringBuffer);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean init(StringBuffer paramStringBuffer) {
/*   547 */     EntityGroup entityGroup = this.list.getParentEntityGroup();
/*       */     
/*   549 */     if (null == entityGroup || entityGroup.getEntityItemCount() > 1)
/*       */     {
/*   551 */       return false;
/*       */     }
/*       */     
/*   554 */     this.rootEntity = entityGroup.getEntityItem(0);
/*   555 */     if (isSelected(this.rootEntity, "BRAND", "0010")) {
/*       */       
/*   557 */       this.brand = "iSeries";
/*   558 */       this.format = 1;
/*       */     }
/*   560 */     else if (isSelected(this.rootEntity, "BRAND", "0020")) {
/*       */       
/*   562 */       this.brand = "pSeries";
/*   563 */       this.format = 2;
/*       */     }
/*   565 */     else if (isSelected(this.rootEntity, "BRAND", "0030")) {
/*       */       
/*   567 */       this.brand = "totalStorage";
/*   568 */       this.format = 2;
/*       */     }
/*   570 */     else if (isSelected(this.rootEntity, "BRAND", "0040")) {
/*       */       
/*   572 */       this.brand = "xSeries";
/*   573 */       this.format = 3;
/*       */     }
/*   575 */     else if (isSelected(this.rootEntity, "BRAND", "0050")) {
/*       */       
/*   577 */       this.brand = "zSeries";
/*   578 */       this.format = 1;
/*       */     }
/*       */     else {
/*       */       
/*   582 */       paramStringBuffer.append("<p><b>Unsupported brand.</b></p>" + NEWLINE);
/*   583 */       log("Unsupported brand");
/*   584 */       return false;
/*       */     } 
/*       */     
/*   587 */     if (isSelected(this.rootEntity, "ANNTYPE", "19")) {
/*       */       
/*   589 */       this.annType = "new";
/*   590 */       this.annTypeDesc = "New";
/*       */     }
/*   592 */     else if (isSelected(this.rootEntity, "ANNTYPE", "14")) {
/*       */       
/*   594 */       this.annType = "withdraw";
/*   595 */       this.annTypeDesc = "End Of Life - Withdrawal from mktg";
/*       */     }
/*   597 */     else if (isSelected(this.rootEntity, "ANNTYPE", "12")) {
/*       */       
/*   599 */       this.annType = "withdraw";
/*   600 */       this.annTypeDesc = "End Of Life - Change to End Of Service Date";
/*       */     }
/*   602 */     else if (isSelected(this.rootEntity, "ANNTYPE", "13")) {
/*       */       
/*   604 */       this.annType = "withdraw";
/*   605 */       this.annTypeDesc = "End Of Life - Discontinuance of service";
/*       */     }
/*   607 */     else if (isSelected(this.rootEntity, "ANNTYPE", "16")) {
/*       */       
/*   609 */       this.annType = "withdraw";
/*   610 */       this.annTypeDesc = "End Of Life - Both";
/*       */     }
/*       */     else {
/*       */       
/*   614 */       paramStringBuffer.append("<p><b>Unsupported Annoucement Type.</b><br />" + NEWLINE);
/*   615 */       paramStringBuffer.append("<b>Supported Announcement Types Are: New, End Of Life.</b></p>" + NEWLINE);
/*   616 */       return false;
/*       */     } 
/*       */     
/*   619 */     this.annDate = getAttributeValue(this.rootEntity, "ANNDATE", "", "", false);
/*   620 */     this.annCodeName = getAttributeValue(this.rootEntity, "ANNCODENAME", "", "", false);
/*   621 */     this.inventoryGroup = getAttributeValue(this.rootEntity, "INVENTORYGROUP", "", "", false);
/*   622 */     this.inventoryGroupFlag = getAttributeFlagValue(this.rootEntity, "INVENTORYGROUP");
/*       */     
/*   624 */     log("Announcement EID = " + (new Integer(this.rootEntity.getEntityID())).toString());
/*   625 */     log("ANNDATE is " + this.annDate + " and ANNCODENAME is " + this.annCodeName);
/*   626 */     log("Brand = " + getAttributeValue(this.rootEntity, "BRAND", "", "", false));
/*       */     
/*   628 */     this.availV = getAllLinkedEntities(entityGroup, "ANNAVAILA", "AVAIL");
/*   629 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*   630 */     if (this.annType.equals("new")) {
/*       */       
/*   632 */       hashtable.put("AVAILTYPE", "146");
/*   633 */       this.availVector = getEntitiesWithMatchedAttr(this.availV, hashtable);
/*       */     }
/*   635 */     else if (this.annType.equals("withdraw")) {
/*       */       
/*   637 */       hashtable.put("AVAILTYPE", "149");
/*   638 */       this.availVector = getEntitiesWithMatchedAttr(this.availV, hashtable);
/*   639 */       hashtable.clear();
/*   640 */       hashtable.put("AVAILTYPE", "151");
/*   641 */       this.availVector.addAll(getEntitiesWithMatchedAttr(this.availV, hashtable));
/*   642 */       hashtable.clear();
/*   643 */       hashtable.put("AVAILTYPE", "152");
/*   644 */       this.availVector.addAll(getEntitiesWithMatchedAttr(this.availV, hashtable));
/*   645 */       hashtable.clear();
/*   646 */       hashtable.put("AVAILTYPE", "153");
/*   647 */       this.availVector.addAll(getEntitiesWithMatchedAttr(this.availV, hashtable));
/*       */     } 
/*       */     
/*   650 */     if (this.availVector.size() == 0) {
/*       */       
/*   652 */       if (this.annType.equals("new")) {
/*       */         
/*   654 */         paramStringBuffer.append("<p><b>Announcement Type = " + this.annTypeDesc + " but no AVAIL Entities with Avail Type = Planned Availability are found.</b></p>");
/*       */       }
/*   656 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*   658 */         paramStringBuffer.append("<p><b>Announcement Type = " + this.annTypeDesc + " but no AVAIL Entities with Avail Type = Last Order or End of Service or End of Dev Support or Last Initial Order are found.</b></p>");
/*       */       } 
/*   660 */       return false;
/*       */     } 
/*       */     
/*   663 */     hashtable.clear();
/*   664 */     hashtable = null;
/*   665 */     this.availV.clear();
/*   666 */     this.availV = null;
/*       */     
/*   668 */     getListOfMTs();
/*       */     
/*   670 */     productNumber_NewModels();
/*   671 */     if (1 == this.format) {
/*       */       
/*   673 */       productNumber_FeatureCodes1();
/*       */     }
/*   675 */     else if (2 == this.format || 3 == this.format) {
/*       */       
/*   677 */       if (this.annType.equals("new")) {
/*       */         
/*   679 */         productNumber_FeatureCodes2();
/*       */       }
/*   681 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*   683 */         productNumber_FeatureCodes1();
/*       */       } 
/*       */     } 
/*   686 */     productNumber_MTM_Model_Conversions();
/*   687 */     productNumber_Feature_Conversions();
/*       */     
/*   689 */     if (this.annType.equals("new")) {
/*       */       
/*   691 */       charges_NewModels();
/*   692 */       if (1 == this.format) {
/*       */         
/*   694 */         charges_FeatureCodes1();
/*       */       }
/*   696 */       else if (2 == this.format || 3 == this.format) {
/*       */         
/*   698 */         charges_FeatureCodes2();
/*       */       } 
/*       */       
/*   701 */       if (this.brand.equals("xSeries")) {
/*       */         
/*   703 */         q779_NewModels();
/*   704 */         q779_NewFeatures();
/*       */       } 
/*       */       
/*   707 */       salesManual();
/*   708 */       supportedDevices();
/*   709 */       featureMatrix();
/*       */       
/*   711 */       if (this.brand.equals("xSeries")) {
/*       */         
/*   713 */         getGEOInfoForLSEO();
/*   714 */         getMTM();
/*   715 */         populateSEOTable();
/*   716 */         populateSEODescription();
/*       */       } 
/*       */     } 
/*       */     
/*   720 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getListOfMTs() {
/*   729 */     String str = "";
/*       */     
/*   731 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*   733 */     hashtable.clear();
/*   734 */     hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   739 */     hashtable.put("COFGRP", "150");
/*       */     
/*   741 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*   742 */     while (iterator.hasNext()) {
/*       */       
/*   744 */       EntityItem entityItem = iterator.next();
/*   745 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*   746 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*   748 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*   750 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*   751 */         if (null != eANEntity)
/*       */         {
/*   753 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*   755 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*   756 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*   757 */             if (null != eANEntity1) {
/*       */               
/*   759 */               str = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*   760 */               if (null == str)
/*       */               {
/*   762 */                 str = " ";
/*       */               }
/*   764 */               str = str.trim();
/*   765 */               str = setString(str, 4);
/*       */               
/*   767 */               this.machineTypeTS.add(str);
/*       */             } 
/*       */           } 
/*       */         }
/*       */       } 
/*       */     } 
/*   773 */     hashtable.clear();
/*   774 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void productNumber_NewModels() {
/*   784 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*   786 */     hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   791 */     hashtable.put("COFGRP", "150");
/*       */ 
/*       */     
/*   794 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*   795 */     while (iterator.hasNext()) {
/*       */       
/*   797 */       EntityItem entityItem = iterator.next();
/*   798 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*   799 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/*   800 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*   802 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*   804 */         populate_PN_NewModels_TM(entityItem, entityItem1);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populate_PN_NewModels_TM(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/*   855 */     String str5 = paramEntityItem2.getKey();
/*   856 */     updateGeoHT(paramEntityItem1, str5);
/*       */     
/*   858 */     String str1 = getAttributeFlagValue(paramEntityItem2, "MACHTYPEATR");
/*   859 */     if (null == str1)
/*       */     {
/*   861 */       str1 = " ";
/*       */     }
/*   863 */     str1 = str1.trim();
/*   864 */     str1 = setString(str1, 4);
/*   865 */     String str2 = str1;
/*   866 */     String str3 = getAttributeValue(paramEntityItem2, "MODELATR", "", "000", false);
/*   867 */     str3 = str3.trim();
/*   868 */     str3 = setString(str3, 3);
/*   869 */     str1 = str1 + "<:>" + str3;
/*       */     
/*   871 */     if (1 == this.format) {
/*       */       
/*   873 */       String str = getAttributeValue(paramEntityItem2, "INVNAME", "", "", false);
/*   874 */       str = str.trim();
/*   875 */       if (str.equals("")) {
/*       */         
/*   877 */         str = getAttributeValue(paramEntityItem2, "INTERNALNAME", "", "", false);
/*   878 */         if (str.length() > 28)
/*       */         {
/*   880 */           str = str.substring(0, 28);
/*       */         }
/*   882 */         str = str.trim();
/*   883 */         str = str.toUpperCase();
/*       */       } 
/*       */ 
/*       */ 
/*       */       
/*   888 */       str = setString(str, 28);
/*   889 */       str1 = str1 + "<:>" + str;
/*       */     }
/*   891 */     else if (2 == this.format) {
/*       */       
/*   893 */       String str = getAttributeValue(paramEntityItem2, "MKTGNAME", "", "", false);
/*   894 */       str = str.trim();
/*   895 */       if (str.equals(""))
/*       */       {
/*   897 */         this.featureMatrixError.add("10<:>Product Number<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Marketing Name");
/*       */       }
/*   899 */       str1 = str1 + "<:>" + str;
/*       */     }
/*   901 */     else if (3 == this.format) {
/*       */       
/*   903 */       String str = getAttributeValue(paramEntityItem2, "INVNAME", "", "", false);
/*   904 */       str = str.trim();
/*   905 */       if (str.equals(""))
/*       */       {
/*   907 */         this.featureMatrixError.add("10<:>Product Number<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Price File Name");
/*       */       }
/*       */       
/*   910 */       str = setString(str, 28);
/*   911 */       str1 = str1 + "<:>" + str;
/*       */     } 
/*       */ 
/*       */     
/*   915 */     if (isSelected(paramEntityItem2, "INSTALL", "5671")) {
/*       */       
/*   917 */       str1 = str1 + "<:>Yes";
/*   918 */       this.productNumber_NewModels_HT.put(str3, "Yes");
/*       */     }
/*   920 */     else if (isSelected(paramEntityItem2, "INSTALL", "5672")) {
/*       */       
/*   922 */       str1 = str1 + "<:>No ";
/*   923 */       this.productNumber_NewModels_HT.put(str3, "No ");
/*       */     }
/*   925 */     else if (isSelected(paramEntityItem2, "INSTALL", "5673")) {
/*       */       
/*   927 */       str1 = str1 + "<:>N/A";
/*   928 */       this.productNumber_NewModels_HT.put(str3, "N/A");
/*       */     }
/*       */     else {
/*       */       
/*   932 */       str1 = str1 + "<:>   ";
/*   933 */       this.productNumber_NewModels_HT.put(str3, "   ");
/*       */     } 
/*       */     
/*   936 */     if (getAttributeValue(paramEntityItem2, "INSTALL", "", "", false).equals(""))
/*       */     {
/*   938 */       if (1 == this.format)
/*       */       {
/*   940 */         this.featureMatrixError.add("10<:>Product Number<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Customer Install");
/*       */       }
/*       */     }
/*       */ 
/*       */     
/*   945 */     String str4 = getGeo(str5);
/*       */     
/*   947 */     addToTreeMap(str1, str4, this.productNumber_NewModels_TM);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void productNumber_FeatureCodes1() {
/*   968 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*   969 */     String str = "";
/*   970 */     boolean bool = true;
/*       */     
/*   972 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*   973 */     while (iterator.hasNext()) {
/*       */       
/*   975 */       EntityItem entityItem = iterator.next();
/*   976 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*   977 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*   980 */         EntityItem entityItem1 = vector.get(b);
/*   981 */         String str1 = entityItem1.getKey();
/*   982 */         String str2 = getAttributeValue(entityItem, "EFFECTIVEDATE", "", "", false);
/*   983 */         updateGeoHT(entityItem, str1);
/*       */         
/*   985 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*   986 */         if (null != eANEntity) {
/*       */ 
/*       */           
/*   989 */           hashtable.clear();
/*   990 */           hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*   995 */           hashtable.put("COFGRP", "150");
/*   996 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*   998 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*   999 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  1000 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  1011 */               hashtable.clear();
/*  1012 */               hashtable.put("PRICEDFEATURE", "120");
/*       */ 
/*       */ 
/*       */               
/*  1016 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*  1017 */               String str3 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  1018 */               if (null == str3)
/*       */               {
/*  1020 */                 str3 = " ";
/*       */               }
/*  1022 */               str3 = str3.trim();
/*  1023 */               str3 = setString(str3, 4);
/*  1024 */               str = str3;
/*  1025 */               String str4 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  1026 */               str4 = str4.trim();
/*  1027 */               str4 = setString(str4, 4);
/*  1028 */               str3 = str3 + "<:>" + str4;
/*  1029 */               String str5 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  1030 */               str5 = str5.trim();
/*  1031 */               str5 = setString(str5, 3);
/*  1032 */               str3 = str3 + "<:>" + str5;
/*       */               
/*  1034 */               String str6 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/*  1035 */               if (this.annDate.equals(str6)) {
/*       */                 
/*  1037 */                 bool = true;
/*       */ 
/*       */               
/*       */               }
/*  1041 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/*  1043 */                 bool = true;
/*       */               }
/*       */               else {
/*       */                 
/*  1047 */                 bool = false;
/*       */               } 
/*       */ 
/*       */               
/*  1051 */               if (str4.equals("    "))
/*       */               {
/*  1053 */                 this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/*  1056 */               if (1 == this.format) {
/*       */                 
/*  1058 */                 String str8 = getAttributeValue(entityItem1, "INVNAME", "", "", false);
/*  1059 */                 str8 = str8.trim();
/*  1060 */                 if (str8.equals("")) {
/*       */                   
/*  1062 */                   str8 = getAttributeValue(entityItem3, "INVNAME", "", "", false);
/*  1063 */                   str8 = str8.trim();
/*  1064 */                   if (str8.equals("")) {
/*       */                     
/*  1066 */                     str8 = getAttributeValue(entityItem3, "COMNAME", "", "", false);
/*  1067 */                     if (str8.length() > 28)
/*       */                     {
/*  1069 */                       str8 = str8.substring(0, 28);
/*       */                     }
/*  1071 */                     str8 = str8.trim();
/*  1072 */                     str8 = str8.toUpperCase();
/*       */                   } 
/*       */                 } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/*  1080 */                 str8 = setString(str8, 28);
/*  1081 */                 str3 = str3 + "<:>" + str8;
/*       */               }
/*  1083 */               else if (2 == this.format || 3 == this.format) {
/*       */                 
/*  1085 */                 String str8 = getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/*  1086 */                 str8 = str8.trim();
/*  1087 */                 if (str8.equals("")) {
/*       */                   
/*  1089 */                   str8 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false);
/*  1090 */                   str8 = str8.trim();
/*  1091 */                   if (str8.equals("")) {
/*       */                     
/*  1093 */                     this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Marketing Name");
/*  1094 */                     this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>FEATURE " + str4 + "<:>Marketing Name");
/*       */                   } 
/*       */                 } 
/*  1097 */                 str3 = str3 + "<:>" + str8;
/*       */               } 
/*       */ 
/*       */               
/*  1101 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  1103 */                 str3 = str3 + "<:>Both   ";
/*       */               }
/*  1105 */               else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  1107 */                 str3 = str3 + "<:>MES    ";
/*       */               }
/*  1109 */               else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  1111 */                 str3 = str3 + "<:>Initial";
/*       */               }
/*  1113 */               else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  1115 */                 str3 = str3 + "<:>Support";
/*       */               }
/*       */               else {
/*       */                 
/*  1119 */                 str3 = str3 + "<:>       ";
/*       */               } 
/*       */               
/*  1122 */               if (getAttributeValue(entityItem1, "ORDERCODE", "", "", false).equals(""))
/*       */               {
/*  1124 */                 if (1 == this.format)
/*       */                 {
/*  1126 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */                 }
/*       */               }
/*       */ 
/*       */               
/*  1131 */               if (isSelected(entityItem1, "INSTALL", "5671")) {
/*       */                 
/*  1133 */                 str3 = str3 + "<:>Yes";
/*       */               }
/*  1135 */               else if (isSelected(entityItem1, "INSTALL", "5672")) {
/*       */                 
/*  1137 */                 str3 = str3 + "<:>No ";
/*       */               }
/*  1139 */               else if (isSelected(entityItem1, "INSTALL", "5673")) {
/*       */                 
/*  1141 */                 str3 = str3 + "<:>N/A";
/*       */               }
/*       */               else {
/*       */                 
/*  1145 */                 str3 = str3 + "<:>   ";
/*       */               } 
/*       */               
/*  1148 */               if (getAttributeValue(entityItem1, "INSTALL", "", "", false).equals(""))
/*       */               {
/*  1150 */                 if (1 == this.format)
/*       */                 {
/*  1152 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Customer Install");
/*       */                 }
/*       */               }
/*       */               
/*  1156 */               if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */                 
/*  1158 */                 str3 = str3 + "<:>Yes";
/*       */               }
/*  1160 */               else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */                 
/*  1162 */                 str3 = str3 + "<:>No ";
/*       */               }
/*       */               else {
/*       */                 
/*  1166 */                 str3 = str3 + "<:>   ";
/*       */               } 
/*       */               
/*  1169 */               str3 = str3 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*  1170 */               str3 = str3 + "<:>" + str2;
/*       */               
/*  1172 */               String str7 = getGeo(str1);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  1187 */               if (bool) {
/*       */                 
/*  1189 */                 addToTreeMap(str3, str7, this.productNumber_NewFC_TM);
/*       */               }
/*       */               else {
/*       */                 
/*  1193 */                 addToTreeMap(str3, str7, this.productNumber_ExistingFC_TM);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  1201 */     hashtable.clear();
/*  1202 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void productNumber_FeatureCodes2() {
/*  1223 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  1224 */     String str = "";
/*  1225 */     boolean bool = true;
/*  1226 */     boolean bool1 = true;
/*       */     
/*  1228 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  1229 */     while (iterator.hasNext()) {
/*       */       
/*  1231 */       EntityItem entityItem = iterator.next();
/*  1232 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  1233 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  1236 */         EntityItem entityItem1 = vector.get(b);
/*  1237 */         String str1 = entityItem1.getKey();
/*  1238 */         updateGeoHT(entityItem, str1);
/*       */         
/*  1240 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*  1241 */         if (null != eANEntity) {
/*       */ 
/*       */           
/*  1244 */           hashtable.clear();
/*  1245 */           hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  1250 */           hashtable.put("COFGRP", "150");
/*  1251 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*  1253 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*  1254 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  1255 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  1268 */               hashtable.clear();
/*  1269 */               hashtable.put("PRICEDFEATURE", "120");
/*       */ 
/*       */ 
/*       */               
/*  1273 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*  1274 */               String str2 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  1275 */               if (null == str2)
/*       */               {
/*  1277 */                 str2 = " ";
/*       */               }
/*  1279 */               str2 = str2.trim();
/*  1280 */               str2 = setString(str2, 4);
/*  1281 */               str = str2;
/*  1282 */               String str3 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  1283 */               str3 = str3.trim();
/*  1284 */               str3 = setString(str3, 4);
/*  1285 */               str2 = str2 + "<:>" + str3;
/*  1286 */               String str4 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  1287 */               str4 = str4.trim();
/*  1288 */               str4 = setString(str4, 3);
/*  1289 */               str2 = str2 + "<:>" + str4;
/*       */               
/*  1291 */               bool1 = this.productNumber_NewModels_HT.containsKey(str4);
/*       */               
/*  1293 */               String str5 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/*  1294 */               if (this.annDate.equals(str5)) {
/*       */                 
/*  1296 */                 bool = true;
/*       */ 
/*       */               
/*       */               }
/*  1300 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/*  1302 */                 bool = true;
/*       */               }
/*       */               else {
/*       */                 
/*  1306 */                 bool = false;
/*       */               } 
/*       */ 
/*       */               
/*  1310 */               if (str3.equals("    "))
/*       */               {
/*  1312 */                 this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str4 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/*  1315 */               String str6 = getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/*  1316 */               str6 = str6.trim();
/*  1317 */               if (str6.equals("")) {
/*       */                 
/*  1319 */                 str6 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false);
/*  1320 */                 str6 = str6.trim();
/*  1321 */                 if (str6.equals("")) {
/*       */                   
/*  1323 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str4 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Marketing Name");
/*  1324 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str4 + "<:>FEATURE " + str3 + "<:>Marketing Name");
/*       */                 } 
/*       */               } 
/*  1327 */               str2 = str2 + "<:>" + str6;
/*       */ 
/*       */               
/*  1330 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  1332 */                 str2 = str2 + "<:>Both   ";
/*       */               }
/*  1334 */               else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  1336 */                 str2 = str2 + "<:>MES    ";
/*       */               }
/*  1338 */               else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  1340 */                 str2 = str2 + "<:>Initial";
/*       */               }
/*  1342 */               else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  1344 */                 str2 = str2 + "<:>Support";
/*       */               }
/*       */               else {
/*       */                 
/*  1348 */                 str2 = str2 + "<:>       ";
/*       */               } 
/*       */ 
/*       */               
/*  1352 */               if (isSelected(entityItem1, "INSTALL", "5671")) {
/*       */                 
/*  1354 */                 str2 = str2 + "<:>Yes";
/*       */               }
/*  1356 */               else if (isSelected(entityItem1, "INSTALL", "5672")) {
/*       */                 
/*  1358 */                 str2 = str2 + "<:>No ";
/*       */               }
/*  1360 */               else if (isSelected(entityItem1, "INSTALL", "5673")) {
/*       */                 
/*  1362 */                 str2 = str2 + "<:>N/A";
/*       */               }
/*       */               else {
/*       */                 
/*  1366 */                 str2 = str2 + "<:>   ";
/*       */               } 
/*       */               
/*  1369 */               if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */                 
/*  1371 */                 str2 = str2 + "<:>Yes";
/*       */               }
/*  1373 */               else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */                 
/*  1375 */                 str2 = str2 + "<:>No ";
/*       */               }
/*       */               else {
/*       */                 
/*  1379 */                 str2 = str2 + "<:>   ";
/*       */               } 
/*       */               
/*  1382 */               str2 = str2 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */               
/*  1384 */               if (2 == this.format) {
/*       */                 
/*  1386 */                 str6 = getAttributeValue(entityItem2, "MKTGNAME", "", "", false);
/*  1387 */                 str6 = str6.trim();
/*  1388 */                 if (str6.equals(""))
/*       */                 {
/*  1390 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str4 + "<:>MODEL<:>Marketing Name");
/*       */                 }
/*  1392 */                 str2 = str2 + "<:>" + str6;
/*       */               }
/*  1394 */               else if (3 == this.format) {
/*       */                 
/*  1396 */                 String str8 = getAttributeValue(entityItem2, "INVNAME", "", "", false);
/*  1397 */                 str8 = str8.trim();
/*  1398 */                 if (str8.equals(""))
/*       */                 {
/*  1400 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str4 + "<:>MODEL<:>Price File Name");
/*       */                 }
/*       */                 
/*  1403 */                 str8 = setString(str8, 28);
/*  1404 */                 str2 = str2 + "<:>" + str8;
/*       */               } 
/*       */               
/*  1407 */               String str7 = getGeo(str1);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  1422 */               if (bool1 && bool) {
/*       */                 
/*  1424 */                 addToTreeMap(str2, str7, this.productNumber_NewModels_NewFC_TM);
/*       */               }
/*  1426 */               else if (bool1 && !bool) {
/*       */                 
/*  1428 */                 addToTreeMap(str2, str7, this.productNumber_NewModels_ExistingFC_TM);
/*       */               }
/*  1430 */               else if (!bool1 && bool) {
/*       */                 
/*  1432 */                 addToTreeMap(str2, str7, this.productNumber_ExistingModels_NewFC_TM);
/*       */               }
/*  1434 */               else if (!bool1 && !bool) {
/*       */                 
/*  1436 */                 addToTreeMap(str2, str7, this.productNumber_ExistingModels_ExistingFC_TM);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  1444 */     hashtable.clear();
/*  1445 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void productNumber_MTM_Model_Conversions() {
/*  1454 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  1455 */     while (iterator.hasNext()) {
/*       */       
/*  1457 */       EntityItem entityItem = iterator.next();
/*  1458 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELCONVERTAVAIL", "MODELCONVERT");
/*  1459 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  1465 */         EntityItem entityItem1 = vector.get(b);
/*  1466 */         String str5 = entityItem1.getKey();
/*  1467 */         updateGeoHT(entityItem, str5);
/*       */         
/*  1469 */         String str1 = getAttributeValue(entityItem1, "TOMACHTYPE", "", "", false);
/*  1470 */         str1 = str1.trim();
/*  1471 */         String str2 = setString(str1, 4);
/*  1472 */         str1 = getAttributeValue(entityItem1, "FROMMACHTYPE", "", "", false);
/*  1473 */         str1 = str1.trim();
/*  1474 */         String str3 = setString(str1, 4);
/*  1475 */         String str4 = "";
/*  1476 */         if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */           
/*  1478 */           str4 = "Yes";
/*       */         }
/*  1480 */         else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */           
/*  1482 */           str4 = "No ";
/*       */         }
/*       */         else {
/*       */           
/*  1486 */           str4 = "   ";
/*       */         } 
/*       */         
/*  1489 */         if (str3.equals(str2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  1499 */           String str7 = str3;
/*  1500 */           str1 = getAttributeValue(entityItem1, "TOMODEL", "", "", false);
/*  1501 */           str1 = str1.trim();
/*  1502 */           str7 = str7 + "<:>" + setString(str1, 3);
/*  1503 */           str1 = getAttributeValue(entityItem1, "FROMMODEL", "", "", false);
/*  1504 */           str1 = str1.trim();
/*  1505 */           str7 = str7 + "<:>" + setString(str1, 3);
/*  1506 */           str7 = str7 + "<:>" + str4;
/*       */           
/*  1508 */           String str6 = getGeo(str5);
/*  1509 */           addToTreeMap(str7, str6, this.productNumber_Model_Conversions_TM);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*       */         }
/*       */         else {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  1523 */           String str7 = str2;
/*  1524 */           str1 = getAttributeValue(entityItem1, "TOMODEL", "", "", false);
/*  1525 */           str1 = str1.trim();
/*  1526 */           str7 = str7 + "<:>" + setString(str1, 3);
/*  1527 */           str7 = str7 + "<:>" + str3;
/*  1528 */           str1 = getAttributeValue(entityItem1, "FROMMODEL", "", "", false);
/*  1529 */           str1 = str1.trim();
/*  1530 */           str7 = str7 + "<:>" + setString(str1, 3);
/*  1531 */           str7 = str7 + "<:>" + str4;
/*       */           
/*  1533 */           String str6 = getGeo(str5);
/*  1534 */           addToTreeMap(str7, str6, this.productNumber_MTM_Conversions_TM);
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void productNumber_Feature_Conversions() {
/*  1547 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */ 
/*       */     
/*  1550 */     Vector<EntityItem> vector = getAllLinkedEntities(this.availVector, "OOFAVAIL", "PRODSTRUCT");
/*  1551 */     for (byte b = 0; b < vector.size(); b++) {
/*       */       
/*  1553 */       EntityItem entityItem = vector.get(b);
/*  1554 */       EANEntity eANEntity = getUpLinkEntityItem(entityItem, "FEATURE");
/*       */       
/*  1556 */       if (null != eANEntity)
/*       */       {
/*  1558 */         this.featureVector.add(eANEntity.getKey());
/*       */       }
/*       */     } 
/*       */ 
/*       */     
/*  1563 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  1564 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */       
/*  1568 */       EntityItem entityItem = iterator.next();
/*  1569 */       Vector vector3 = getAllLinkedEntities(entityItem, "FEATURETRNAVAIL", "FCTRANSACTION");
/*  1570 */       hashtable.clear();
/*  1571 */       hashtable.put("FTCAT", "404");
/*  1572 */       Vector<EntityItem> vector1 = getEntitiesWithMatchedAttr(vector3, hashtable);
/*  1573 */       hashtable.clear();
/*  1574 */       hashtable.put("FTCAT", "406");
/*  1575 */       Vector vector2 = getEntitiesWithMatchedAttr(vector3, hashtable);
/*  1576 */       vector1.addAll(vector2);
/*  1577 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*       */         
/*  1579 */         EntityItem entityItem1 = vector1.get(b1);
/*  1580 */         populate_PN_Feature_Conversions_TM(entityItem, entityItem1);
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  1585 */     iterator = this.availVector.iterator();
/*  1586 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */       
/*  1590 */       EntityItem entityItem = iterator.next();
/*  1591 */       Vector vector3 = getAllLinkedEntities(entityItem, "MODELCONVERTAVAIL", "MODELCONVERT");
/*  1592 */       Vector vector4 = getAllLinkedEntities(vector3, "MODELCONVFCTRANSAC", "FCTRANSACTION");
/*  1593 */       hashtable.clear();
/*  1594 */       hashtable.put("FTCAT", "404");
/*  1595 */       Vector<EntityItem> vector1 = getEntitiesWithMatchedAttr(vector4, hashtable);
/*  1596 */       hashtable.clear();
/*  1597 */       hashtable.put("FTCAT", "406");
/*  1598 */       Vector vector2 = getEntitiesWithMatchedAttr(vector4, hashtable);
/*  1599 */       vector1.addAll(vector2);
/*  1600 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*       */         
/*  1602 */         EntityItem entityItem1 = vector1.get(b1);
/*  1603 */         populate_PN_Feature_Conversions_TM(entityItem, entityItem1);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populate_PN_Feature_Conversions_TM(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/*  1654 */     String str13 = paramEntityItem2.getKey();
/*  1655 */     updateGeoHT(paramEntityItem1, str13);
/*       */     
/*  1657 */     String str1 = getAttributeValue(paramEntityItem2, "TOMODEL", "", "", false);
/*  1658 */     str1 = str1.trim();
/*  1659 */     str1 = setString(str1, 3);
/*  1660 */     String str2 = str1;
/*  1661 */     String str3 = getAttributeValue(paramEntityItem2, "TOMACHTYPE", "", "", false);
/*  1662 */     str3 = str3.trim();
/*  1663 */     str3 = setString(str3, 4);
/*  1664 */     str2 = str2 + "<:>" + str3;
/*  1665 */     String str4 = getAttributeValue(paramEntityItem2, "TOFEATURECODE", "", "", false);
/*  1666 */     str4 = str4.trim();
/*  1667 */     str4 = setString(str4, 4);
/*  1668 */     str2 = str2 + "<:>" + str4;
/*  1669 */     String str5 = getAttributeValue(paramEntityItem2, "FROMMODEL", "", "", false);
/*  1670 */     str5 = str5.trim();
/*  1671 */     str5 = setString(str5, 3);
/*       */     
/*  1673 */     str2 = str2 + "<:> ";
/*  1674 */     String str6 = getAttributeValue(paramEntityItem2, "FROMMACHTYPE", "", "", false);
/*  1675 */     str6 = str6.trim();
/*  1676 */     str6 = setString(str6, 4);
/*       */     
/*  1678 */     str2 = str2 + "<:> ";
/*  1679 */     String str7 = getAttributeValue(paramEntityItem2, "FROMFEATURECODE", "", "", false);
/*  1680 */     str7 = str7.trim();
/*  1681 */     str7 = setString(str7, 4);
/*  1682 */     str2 = str2 + "<:>" + str7;
/*  1683 */     String str8 = getAttributeValue(paramEntityItem1, "EFFECTIVEDATE", "", "", false);
/*       */     
/*  1685 */     String str9 = str3;
/*  1686 */     str9 = str9 + "<:>" + str1;
/*  1687 */     str9 = str9 + "<:>" + str4;
/*  1688 */     str9 = str9 + "<:>" + str6;
/*  1689 */     str9 = str9 + "<:>" + str5;
/*  1690 */     str9 = str9 + "<:>" + str7;
/*       */     
/*  1692 */     Vector<EntityItem> vector1 = getFeatureEntities(str3, str1, str4);
/*  1693 */     Vector<EntityItem> vector2 = getFeatureEntities(str6, str5, str7);
/*       */     
/*  1695 */     String str10 = "";
/*  1696 */     if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5100")) {
/*       */       
/*  1698 */       str10 = "Yes";
/*       */     }
/*  1700 */     else if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5101")) {
/*       */       
/*  1702 */       str10 = "No ";
/*       */     }
/*       */     else {
/*       */       
/*  1706 */       str10 = "   ";
/*       */     } 
/*       */     
/*  1709 */     String str11 = getGeo(str13);
/*       */     
/*  1711 */     String str12 = "";
/*       */     
/*  1713 */     if (vector1.size() == 0) {
/*       */       
/*  1715 */       str12 = "UNKNOWN HWFCCAT(FEATURE)";
/*       */       
/*  1717 */       str2 = " <:>" + str2;
/*  1718 */       str2 = str2 + "<:>" + str10;
/*  1719 */       str2 = str2 + "<:>";
/*  1720 */       str2 = str2 + "<:>" + str8;
/*  1721 */       addToTreeMap(str2, str11, this.productNumber_Feature_Conversions_TM);
/*       */     }
/*       */     else {
/*       */       
/*  1725 */       for (byte b = 0; b < vector1.size(); b++) {
/*       */         
/*  1727 */         EntityItem entityItem = vector1.get(b);
/*  1728 */         str12 = getAttributeValue(entityItem, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
/*       */         
/*  1730 */         str2 = " <:>" + str2;
/*  1731 */         str2 = str2 + "<:>" + str10;
/*  1732 */         str2 = str2 + "<:>" + getAttributeValue(entityItem, "EDITORNOTE", "", "", false).trim();
/*  1733 */         str2 = str2 + "<:>" + str8;
/*  1734 */         addToTreeMap(str2, str11, this.productNumber_Feature_Conversions_TM);
/*       */       } 
/*       */     } 
/*       */     
/*  1738 */     if (vector2.size() == 0 && vector1.size() == 0) {
/*       */       
/*  1740 */       str12 = "UNKNOWN HWFCCAT(FEATURE)";
/*  1741 */       str9 = str12 + "<:>" + str9;
/*  1742 */       str9 = str9 + "<:><:>";
/*  1743 */       str9 = str9 + "<:>" + str10;
/*  1744 */       str9 = str9 + "<:>";
/*  1745 */       addToTreeMap(str9, str11, this.charges_Feature_Conversions_TM);
/*  1746 */       log("In 1");
/*       */     } 
/*       */     
/*  1749 */     if (vector2.size() == 0 && vector1.size() > 0) {
/*       */       
/*  1751 */       for (byte b = 0; b < vector1.size(); b++) {
/*       */         
/*  1753 */         EntityItem entityItem = vector1.get(b);
/*  1754 */         str12 = getAttributeValue(entityItem, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
/*  1755 */         str9 = str12 + "<:>" + str9;
/*  1756 */         str9 = str9 + "<:>" + getAttributeValue(entityItem, "MKTGNAME", "", "", false);
/*  1757 */         str9 = str9 + "<:>";
/*  1758 */         str9 = str9 + "<:>" + str10;
/*  1759 */         str9 = str9 + "<:>" + getAttributeValue(entityItem, "EDITORNOTE", "", "", false).trim();
/*  1760 */         addToTreeMap(str9, str11, this.charges_Feature_Conversions_TM);
/*       */       } 
/*  1762 */       log("In 2");
/*       */     } 
/*       */     
/*  1765 */     if (vector2.size() > 0 && vector1.size() == 0) {
/*       */       
/*  1767 */       for (byte b = 0; b < vector2.size(); b++) {
/*       */         
/*  1769 */         EntityItem entityItem = vector2.get(b);
/*  1770 */         str12 = "UNKNOWN HWFCCAT(FEATURE)";
/*  1771 */         str9 = str12 + "<:>" + str9;
/*  1772 */         str9 = str9 + "<:>";
/*  1773 */         str9 = str9 + "<:>" + getAttributeValue(entityItem, "MKTGNAME", "", "", false);
/*  1774 */         str9 = str9 + "<:>" + str10;
/*  1775 */         str9 = str9 + "<:>";
/*  1776 */         addToTreeMap(str9, str11, this.charges_Feature_Conversions_TM);
/*       */       } 
/*  1778 */       log("In 3");
/*       */     } 
/*       */     
/*  1781 */     if (vector2.size() > 0 && vector1.size() > 0) {
/*       */       
/*  1783 */       for (byte b = 0; b < vector2.size(); b++) {
/*       */         
/*  1785 */         EntityItem entityItem = vector2.get(b);
/*  1786 */         String str = getAttributeValue(entityItem, "MKTGNAME", "", "", false);
/*       */         
/*  1788 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*       */           
/*  1790 */           EntityItem entityItem1 = vector1.get(b1);
/*  1791 */           str12 = getAttributeValue(entityItem1, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
/*  1792 */           str9 = str12 + "<:>" + str9;
/*  1793 */           str9 = str9 + "<:>" + getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/*  1794 */           str9 = str9 + "<:>" + str;
/*  1795 */           str9 = str9 + "<:>" + str10;
/*  1796 */           str9 = str9 + "<:>" + getAttributeValue(entityItem1, "EDITORNOTE", "", "", false).trim();
/*  1797 */           addToTreeMap(str9, str11, this.charges_Feature_Conversions_TM);
/*       */         } 
/*       */       } 
/*  1800 */       log("In 4");
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Vector getFeatureEntities(String paramString1, String paramString2, String paramString3) {
/*  1814 */     Vector vector = new Vector();
/*  1815 */     Profile profile = this.list.getProfile();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     try {
/*  1821 */       String str = "SRDFEATURE";
/*       */ 
/*       */ 
/*       */       
/*  1825 */       SearchActionItem searchActionItem = new SearchActionItem(null, this.dbCurrent, profile, str);
/*  1826 */       searchActionItem.setCheckLimit(false);
/*  1827 */       RowSelectableTable rowSelectableTable = searchActionItem.getDynaSearchTable(this.dbCurrent);
/*       */       
/*  1829 */       int i = rowSelectableTable.getRowIndex("FEATURE:FEATURECODE");
/*  1830 */       if (i < 0)
/*       */       {
/*  1832 */         i = rowSelectableTable.getRowIndex("FEATURE:FEATURECODE:C");
/*       */       }
/*  1834 */       if (i < 0)
/*       */       {
/*  1836 */         i = rowSelectableTable.getRowIndex("FEATURE:FEATURECODE:R");
/*       */       }
/*  1838 */       if (i != -1 && paramString3.length() > 0)
/*       */       {
/*  1840 */         rowSelectableTable.put(i, 1, paramString3);
/*       */       }
/*       */       
/*  1843 */       rowSelectableTable.commit(this.dbCurrent);
/*       */       
/*  1845 */       EntityList entityList = searchActionItem.executeAction(this.dbCurrent, profile);
/*       */       
/*  1847 */       EntityGroup entityGroup = entityList.getEntityGroup("FEATURE");
/*       */ 
/*       */       
/*  1850 */       if (null == entityGroup)
/*       */       {
/*  1852 */         return vector;
/*       */       }
/*       */       
/*  1855 */       if (entityGroup.getEntityItemCount() > 0)
/*       */       {
/*  1857 */         Vector<EntityItem> vector1 = new Vector();
/*       */         
/*  1859 */         if (entityGroup.getEntityItemCount() > 50) {
/*       */           
/*  1861 */           int j = entityGroup.getEntityItemCount() / 50;
/*  1862 */           byte b1 = 0;
/*  1863 */           for (byte b2 = 0; b2 <= j; b2++)
/*       */           {
/*  1865 */             vector1.clear();
/*  1866 */             for (byte b = 0; b < 50; b++) {
/*       */ 
/*       */               
/*  1869 */               if (b1 == entityGroup.getEntityItemCount()) {
/*       */                 break;
/*       */               }
/*       */ 
/*       */               
/*  1874 */               EntityItem entityItem = entityGroup.getEntityItem(b1++);
/*       */               
/*  1876 */               vector1.addElement(entityItem);
/*       */             } 
/*  1878 */             if (vector1.size() > 0)
/*       */             {
/*  1880 */               getFeatureEntities(vector1, vector, paramString1, paramString2);
/*       */             }
/*       */           }
/*       */         
/*       */         }
/*       */         else {
/*       */           
/*  1887 */           for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*       */             
/*  1889 */             EntityItem entityItem = entityGroup.getEntityItem(b);
/*       */             
/*  1891 */             vector1.addElement(entityItem);
/*       */           } 
/*       */           
/*  1894 */           getFeatureEntities(vector1, vector, paramString1, paramString2);
/*       */         } 
/*       */         
/*  1897 */         vector1.clear();
/*  1898 */         vector1 = null;
/*       */       }
/*       */     
/*  1901 */     } catch (Exception exception) {
/*       */       
/*  1903 */       exception.printStackTrace();
/*  1904 */       log(exception.toString());
/*       */     } 
/*       */     
/*  1907 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getFeatureEntities(Vector<EntityItem> paramVector1, Vector<EntityItem> paramVector2, String paramString1, String paramString2) throws Exception {
/*  1922 */     String str = "EXRPT3FM";
/*       */     
/*  1924 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector1.size()];
/*       */     
/*  1926 */     log("In getFeatureEntities(Vector tmpVct, Vector featureVct, String machType, String model)"); byte b;
/*  1927 */     for (b = 0; b < paramVector1.size(); b++)
/*       */     {
/*  1929 */       arrayOfEntityItem[b] = paramVector1.elementAt(b);
/*       */     }
/*       */     
/*  1932 */     EntityList entityList = null;
/*  1933 */     if (paramVector1.size() > 0) {
/*       */ 
/*       */ 
/*       */       
/*  1937 */       Profile profile = this.list.getProfile();
/*  1938 */       entityList = this.dbCurrent.getEntityList(profile, new ExtractActionItem(null, this.dbCurrent, profile, str), arrayOfEntityItem);
/*       */ 
/*       */ 
/*       */       
/*  1942 */       log("I am here 1");
/*  1943 */       if (entityList.getEntityGroupCount() == 0)
/*       */       {
/*  1945 */         log("Extract was not found for " + str);
/*       */       }
/*       */       
/*  1948 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/*       */       
/*  1950 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*       */         
/*  1952 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/*  1953 */         log("I am here 2");
/*  1954 */         if (checkModel(entityItem, paramString2, paramString1))
/*       */         {
/*  1956 */           paramVector2.addElement(entityItem);
/*       */         }
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  1962 */     for (b = 0; b < arrayOfEntityItem.length; b++)
/*       */     {
/*  1964 */       arrayOfEntityItem[b] = null;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean checkModel(EntityItem paramEntityItem, String paramString1, String paramString2) {
/*  1978 */     boolean bool = false;
/*       */     
/*  1980 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "PRODSTRUCT", "MODEL");
/*       */     
/*  1982 */     for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */       
/*  1985 */       EntityItem entityItem = vector.get(b);
/*  1986 */       String str2 = getAttributeFlagValue(entityItem, "MACHTYPEATR");
/*  1987 */       if (null == str2)
/*       */       {
/*  1989 */         str2 = " ";
/*       */       }
/*       */       
/*  1992 */       String str1 = getAttributeValue(entityItem, "MODELATR", "", "", false);
/*       */       
/*  1994 */       if (paramString2.equals(str2) && paramString1.equals(str1)) {
/*       */         
/*  1996 */         bool = true;
/*       */         
/*       */         break;
/*       */       } 
/*       */     } 
/*  2001 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void charges_NewModels() {
/*  2012 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  2014 */     hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  2019 */     hashtable.put("COFGRP", "150");
/*       */ 
/*       */     
/*  2022 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  2023 */     while (iterator.hasNext()) {
/*       */       
/*  2025 */       EntityItem entityItem = iterator.next();
/*  2026 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*  2027 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/*  2028 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  2030 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*  2032 */         populate_Charges_NewModels_TM(entityItem, entityItem1);
/*       */       } 
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  2062 */     hashtable.clear();
/*  2063 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populate_Charges_NewModels_TM(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/*  2085 */     String str5 = paramEntityItem2.getKey();
/*  2086 */     updateGeoHT(paramEntityItem1, str5);
/*  2087 */     String str1 = getAttributeFlagValue(paramEntityItem2, "MACHTYPEATR");
/*  2088 */     if (null == str1)
/*       */     {
/*  2090 */       str1 = " ";
/*       */     }
/*  2092 */     str1 = str1.trim();
/*  2093 */     str1 = setString(str1, 4);
/*  2094 */     String str2 = str1;
/*  2095 */     String str3 = getAttributeValue(paramEntityItem2, "MODELATR", "", "000", false);
/*  2096 */     str3 = str3.trim();
/*  2097 */     str3 = setString(str3, 3);
/*  2098 */     str1 = str1 + "<:>" + str3;
/*       */     
/*  2100 */     if (1 == this.format) {
/*       */       
/*  2102 */       String str = getAttributeValue(paramEntityItem2, "INVNAME", "", "", false);
/*  2103 */       str = str.trim();
/*  2104 */       if (str.equals("")) {
/*       */         
/*  2106 */         str = getAttributeValue(paramEntityItem2, "INTERNALNAME", "", "", false);
/*  2107 */         if (str.length() > 28)
/*       */         {
/*  2109 */           str = str.substring(0, 28);
/*       */         }
/*  2111 */         str = str.trim();
/*  2112 */         str = str.toUpperCase();
/*       */       } 
/*       */ 
/*       */ 
/*       */       
/*  2117 */       str = setString(str, 28);
/*  2118 */       str1 = str1 + "<:>" + str;
/*       */     }
/*  2120 */     else if (2 == this.format || 3 == this.format) {
/*       */       
/*  2122 */       String str = getAttributeValue(paramEntityItem2, "MKTGNAME", "", "", false);
/*  2123 */       str = str.trim();
/*  2124 */       if (str.equals(""))
/*       */       {
/*  2126 */         this.featureMatrixError.add("20<:>Charges<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Marketing Name");
/*       */       }
/*  2128 */       str1 = str1 + "<:>" + str;
/*       */     } 
/*       */ 
/*       */     
/*  2132 */     if (isSelected(paramEntityItem2, "MODELORDERCODE", "100")) {
/*       */       
/*  2134 */       str1 = str1 + "<:>Initial";
/*       */     }
/*  2136 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "110")) {
/*       */       
/*  2138 */       str1 = str1 + "<:>MES    ";
/*       */     }
/*  2140 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "120")) {
/*       */       
/*  2142 */       str1 = str1 + "<:>Both   ";
/*       */     }
/*       */     else {
/*       */       
/*  2146 */       str1 = str1 + "<:>       ";
/*       */     } 
/*       */     
/*  2149 */     if (getAttributeValue(paramEntityItem2, "MODELORDERCODE", "", "", false).equals(""))
/*       */     {
/*  2151 */       this.featureMatrixError.add("20<:>Charges<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Model Order Code");
/*       */     }
/*       */ 
/*       */     
/*  2155 */     if (isSelected(paramEntityItem2, "INSTALL", "5671")) {
/*       */       
/*  2157 */       str1 = str1 + "<:>Yes";
/*  2158 */       this.productNumber_NewModels_HT.put(str3, "Yes");
/*       */     }
/*  2160 */     else if (isSelected(paramEntityItem2, "INSTALL", "5672")) {
/*       */       
/*  2162 */       str1 = str1 + "<:>No ";
/*  2163 */       this.productNumber_NewModels_HT.put(str3, "No ");
/*       */     }
/*  2165 */     else if (isSelected(paramEntityItem2, "INSTALL", "5673")) {
/*       */       
/*  2167 */       str1 = str1 + "<:>N/A";
/*  2168 */       this.productNumber_NewModels_HT.put(str3, "N/A");
/*       */     }
/*       */     else {
/*       */       
/*  2172 */       str1 = str1 + "<:>   ";
/*  2173 */       this.productNumber_NewModels_HT.put(str3, "   ");
/*       */     } 
/*       */     
/*  2176 */     if (getAttributeValue(paramEntityItem2, "INSTALL", "", "", false).equals(""))
/*       */     {
/*  2178 */       this.featureMatrixError.add("20<:>Charges<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Customer Install");
/*       */     }
/*       */ 
/*       */     
/*  2182 */     String str4 = getGeo(str5);
/*       */     
/*  2184 */     addToTreeMap(str1, str4, this.charges_NewModels_TM);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void charges_FeatureCodes1() {
/*  2208 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  2210 */     String str = "";
/*       */     
/*  2212 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  2213 */     while (iterator.hasNext()) {
/*       */       
/*  2215 */       EntityItem entityItem = iterator.next();
/*  2216 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  2217 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  2220 */         EntityItem entityItem1 = vector.get(b);
/*  2221 */         String str1 = entityItem1.getKey();
/*  2222 */         updateGeoHT(entityItem, str1);
/*       */         
/*  2224 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*  2225 */         if (null != eANEntity) {
/*       */ 
/*       */           
/*  2228 */           hashtable.clear();
/*  2229 */           hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  2234 */           hashtable.put("COFGRP", "150");
/*  2235 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*  2237 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*  2238 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  2239 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  2246 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*  2247 */               String str7 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  2248 */               str7 = str7.trim();
/*  2249 */               str7 = setString(str7, 4);
/*       */               
/*  2251 */               String str2 = str7;
/*       */               
/*  2253 */               str = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  2254 */               if (null == str)
/*       */               {
/*  2256 */                 str = " ";
/*       */               }
/*  2258 */               str = str.trim();
/*  2259 */               str = setString(str, 4);
/*       */               
/*  2261 */               str2 = str2 + "<:>" + str;
/*  2262 */               String str3 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  2263 */               str3 = str3.trim();
/*  2264 */               if (str3.equals(""))
/*       */               {
/*  2266 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>MODEL<:>Machine Model");
/*       */               }
/*       */               
/*  2269 */               str3 = setString(str3, 3);
/*  2270 */               str2 = str2 + "<:>" + str3;
/*  2271 */               if (str7.equals("    "))
/*       */               {
/*  2273 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/*  2276 */               String str4 = getAttributeValue(entityItem1, "INVNAME", "", "", false);
/*  2277 */               str4 = str4.trim();
/*  2278 */               if (str4.equals("")) {
/*       */                 
/*  2280 */                 str4 = getAttributeValue(entityItem3, "INVNAME", "", "", false);
/*  2281 */                 str4 = str4.trim();
/*  2282 */                 if (str4.equals("")) {
/*       */                   
/*  2284 */                   str4 = getAttributeValue(entityItem3, "COMNAME", "", "", false);
/*  2285 */                   if (str4.length() > 28)
/*       */                   {
/*  2287 */                     str4 = str4.substring(0, 28);
/*       */                   }
/*  2289 */                   str4 = str4.trim();
/*  2290 */                   str4 = str4.toUpperCase();
/*       */                 } 
/*       */               } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  2298 */               str4 = setString(str4, 28);
/*  2299 */               str2 = str2 + "<:>" + str4;
/*       */ 
/*       */               
/*  2302 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  2304 */                 str2 = str2 + "<:>Both   ";
/*       */               }
/*  2306 */               else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  2308 */                 str2 = str2 + "<:>MES    ";
/*       */               }
/*  2310 */               else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  2312 */                 str2 = str2 + "<:>Initial";
/*       */               }
/*  2314 */               else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  2316 */                 str2 = str2 + "<:>Support";
/*       */               }
/*       */               else {
/*       */                 
/*  2320 */                 str2 = str2 + "<:>       ";
/*       */               } 
/*       */               
/*  2323 */               if (getAttributeValue(entityItem1, "ORDERCODE", "", "", false).equals(""))
/*       */               {
/*  2325 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */               }
/*       */ 
/*       */               
/*  2329 */               if (isSelected(entityItem1, "INSTALL", "5671")) {
/*       */                 
/*  2331 */                 str2 = str2 + "<:>Yes";
/*       */               }
/*  2333 */               else if (isSelected(entityItem1, "INSTALL", "5672")) {
/*       */                 
/*  2335 */                 str2 = str2 + "<:>No ";
/*       */               }
/*  2337 */               else if (isSelected(entityItem1, "INSTALL", "5673")) {
/*       */                 
/*  2339 */                 str2 = str2 + "<:>N/A";
/*       */               }
/*       */               else {
/*       */                 
/*  2343 */                 str2 = str2 + "<:>   ";
/*       */               } 
/*       */               
/*  2346 */               if (getAttributeValue(entityItem1, "INSTALL", "", "", false).equals(""))
/*       */               {
/*  2348 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Customer Install");
/*       */               }
/*       */               
/*  2351 */               if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */                 
/*  2353 */                 str2 = str2 + "<:>Yes";
/*       */               }
/*  2355 */               else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */                 
/*  2357 */                 str2 = str2 + "<:>No ";
/*       */               }
/*       */               else {
/*       */                 
/*  2361 */                 str2 = str2 + "<:>   ";
/*       */               } 
/*       */               
/*  2364 */               if (getAttributeValue(entityItem1, "RETURNEDPARTS", "", "", false).equals(""))
/*       */               {
/*  2366 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Returned Parts MES");
/*       */               }
/*       */               
/*  2369 */               str2 = str2 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */               
/*  2371 */               String str5 = getGeo(str1);
/*  2372 */               String str6 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/*       */               
/*  2374 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals(""))
/*       */               {
/*  2376 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str7 + "<:>Priced Feature Indicator");
/*       */               }
/*  2378 */               if (getAttributeValue(entityItem3, "ZEROPRICE", "", "", false).equals(""))
/*       */               {
/*  2380 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str7 + "<:>Zero Priced Indicator");
/*       */               }
/*       */               
/*  2383 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals("")) {
/*       */ 
/*       */                 
/*  2386 */                 str2 = str2 + "<:>        <:>    <:>    <:>    ";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*       */               }
/*       */               else {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/*  2408 */                 hashtable.clear();
/*  2409 */                 hashtable.put("PRICEDFEATURE", "100");
/*       */                 
/*  2411 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                   
/*  2419 */                   str2 = str2 + "<:> $XXXX  <:>    <:>    <:>    ";
/*       */                 }
/*  2421 */                 hashtable.clear();
/*  2422 */                 hashtable.put("PRICEDFEATURE", "120");
/*       */                 
/*  2424 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/*  2426 */                   str2 = str2 + "<:>    NC  <:>    <:>    <:>    ";
/*       */                 }
/*       */               } 
/*       */ 
/*       */               
/*  2431 */               if (this.annDate.equals(str6)) {
/*       */                 
/*  2433 */                 addToTreeMap(str2, str5, this.charges_NewFC_TM);
/*       */ 
/*       */               
/*       */               }
/*  2437 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/*  2439 */                 addToTreeMap(str2, str5, this.charges_NewFC_TM);
/*       */               }
/*       */               else {
/*       */                 
/*  2443 */                 addToTreeMap(str2, str5, this.charges_ExistingFC_TM);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void charges_FeatureCodes2() {
/*  2472 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  2473 */     String str = "";
/*  2474 */     boolean bool = true;
/*  2475 */     boolean bool1 = true;
/*       */     
/*  2477 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  2478 */     while (iterator.hasNext()) {
/*       */       
/*  2480 */       EntityItem entityItem = iterator.next();
/*  2481 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  2482 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  2485 */         EntityItem entityItem1 = vector.get(b);
/*  2486 */         String str1 = entityItem1.getKey();
/*  2487 */         updateGeoHT(entityItem, str1);
/*       */         
/*  2489 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*  2490 */         if (null != eANEntity) {
/*       */ 
/*       */           
/*  2493 */           hashtable.clear();
/*  2494 */           hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  2499 */           hashtable.put("COFGRP", "150");
/*  2500 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*  2502 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*  2503 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  2504 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  2511 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*  2512 */               String str7 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  2513 */               if (null == str7)
/*       */               {
/*  2515 */                 str7 = " ";
/*       */               }
/*  2517 */               str7 = str7.trim();
/*  2518 */               str7 = setString(str7, 4);
/*  2519 */               str = str7;
/*  2520 */               String str2 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  2521 */               str2 = str2.trim();
/*       */               
/*  2523 */               str2 = setString(str2, 4);
/*  2524 */               str7 = str7 + "<:>" + str2;
/*  2525 */               String str3 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  2526 */               str3 = str3.trim();
/*  2527 */               if (str3.equals(""))
/*       */               {
/*  2529 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>MODEL<:>Machine Model");
/*       */               }
/*       */               
/*  2532 */               str3 = setString(str3, 3);
/*  2533 */               str7 = str7 + "<:>" + str3;
/*  2534 */               if (str2.equals("    "))
/*       */               {
/*  2536 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/*  2539 */               bool1 = this.productNumber_NewModels_HT.containsKey(str3);
/*       */               
/*  2541 */               String str4 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/*  2542 */               if (this.annDate.equals(str4)) {
/*       */                 
/*  2544 */                 bool = true;
/*       */ 
/*       */               
/*       */               }
/*  2548 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/*  2550 */                 bool = true;
/*       */               }
/*       */               else {
/*       */                 
/*  2554 */                 bool = false;
/*       */               } 
/*       */ 
/*       */               
/*  2558 */               String str5 = getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/*  2559 */               str5 = str5.trim();
/*  2560 */               if (str5.equals("")) {
/*       */                 
/*  2562 */                 str5 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false);
/*  2563 */                 str5 = str5.trim();
/*  2564 */                 if (str5.equals("")) {
/*       */                   
/*  2566 */                   this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Marketing Name");
/*  2567 */                   this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str2 + "<:>Marketing Name");
/*       */                 } 
/*       */               } 
/*  2570 */               str7 = str7 + "<:>" + str5;
/*       */ 
/*       */               
/*  2573 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  2575 */                 str7 = str7 + "<:>Both   ";
/*       */               }
/*  2577 */               else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  2579 */                 str7 = str7 + "<:>MES    ";
/*       */               }
/*  2581 */               else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  2583 */                 str7 = str7 + "<:>Initial";
/*       */               }
/*  2585 */               else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  2587 */                 str7 = str7 + "<:>Support";
/*       */               }
/*       */               else {
/*       */                 
/*  2591 */                 str7 = str7 + "<:>       ";
/*       */               } 
/*       */               
/*  2594 */               if (getAttributeValue(entityItem1, "ORDERCODE", "", "", false).equals(""))
/*       */               {
/*  2596 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */               }
/*       */ 
/*       */               
/*  2600 */               if (isSelected(entityItem1, "INSTALL", "5671")) {
/*       */                 
/*  2602 */                 str7 = str7 + "<:>Yes";
/*       */               }
/*  2604 */               else if (isSelected(entityItem1, "INSTALL", "5672")) {
/*       */                 
/*  2606 */                 str7 = str7 + "<:>No ";
/*       */               }
/*  2608 */               else if (isSelected(entityItem1, "INSTALL", "5673")) {
/*       */                 
/*  2610 */                 str7 = str7 + "<:>N/A";
/*       */               }
/*       */               else {
/*       */                 
/*  2614 */                 str7 = str7 + "<:>   ";
/*       */               } 
/*       */               
/*  2617 */               if (getAttributeValue(entityItem1, "INSTALL", "", "", false).equals(""))
/*       */               {
/*  2619 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Customer Install");
/*       */               }
/*       */               
/*  2622 */               if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */                 
/*  2624 */                 str7 = str7 + "<:>Yes";
/*       */               }
/*  2626 */               else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */                 
/*  2628 */                 str7 = str7 + "<:>No ";
/*       */               }
/*       */               else {
/*       */                 
/*  2632 */                 str7 = str7 + "<:>   ";
/*       */               } 
/*       */               
/*  2635 */               if (getAttributeValue(entityItem1, "RETURNEDPARTS", "", "", false).equals(""))
/*       */               {
/*  2637 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Returned Parts MES");
/*       */               }
/*       */               
/*  2640 */               str7 = str7 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */               
/*  2642 */               String str6 = getGeo(str1);
/*       */               
/*  2644 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals(""))
/*       */               {
/*  2646 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str2 + "<:>Priced Feature Indicator");
/*       */               }
/*  2648 */               if (getAttributeValue(entityItem3, "ZEROPRICE", "", "", false).equals(""))
/*       */               {
/*  2650 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str2 + "<:>Zero Priced Indicator");
/*       */               }
/*       */               
/*  2653 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals("")) {
/*       */ 
/*       */                 
/*  2656 */                 str7 = str7 + "<:>       ";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*       */               }
/*       */               else {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/*  2678 */                 hashtable.clear();
/*  2679 */                 hashtable.put("PRICEDFEATURE", "100");
/*       */                 
/*  2681 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/*  2683 */                   str7 = str7 + "<:>XXXX.XX";
/*       */                 }
/*  2685 */                 hashtable.clear();
/*  2686 */                 hashtable.put("PRICEDFEATURE", "120");
/*       */                 
/*  2688 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/*  2690 */                   str7 = str7 + "<:>     NC";
/*       */                 }
/*       */               } 
/*       */ 
/*       */               
/*  2695 */               str5 = getAttributeValue(entityItem2, "MKTGNAME", "", "", false);
/*  2696 */               str5 = str5.trim();
/*  2697 */               if (str5.equals(""))
/*       */               {
/*  2699 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>MODEL<:>Marketing Name");
/*       */               }
/*  2701 */               str7 = str7 + "<:>" + str5;
/*       */               
/*  2703 */               if (bool1 && bool) {
/*       */                 
/*  2705 */                 addToTreeMap(str7, str6, this.charges_NewModels_NewFC_TM);
/*       */               }
/*  2707 */               else if (bool1 && !bool) {
/*       */                 
/*  2709 */                 addToTreeMap(str7, str6, this.charges_NewModels_ExistingFC_TM);
/*       */               }
/*  2711 */               else if (!bool1 && bool) {
/*       */                 
/*  2713 */                 addToTreeMap(str7, str6, this.charges_ExistingModels_NewFC_TM);
/*       */               }
/*  2715 */               else if (!bool1 && !bool) {
/*       */                 
/*  2717 */                 addToTreeMap(str7, str6, this.charges_ExistingModels_ExistingFC_TM);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void salesManual() {
/*  2733 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  2735 */     hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  2740 */     hashtable.put("COFGRP", "150");
/*       */ 
/*       */     
/*  2743 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  2744 */     while (iterator.hasNext()) {
/*       */       
/*  2746 */       EntityItem entityItem = iterator.next();
/*  2747 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  2748 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  2750 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*  2752 */         for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/*       */           
/*  2754 */           EANEntity eANEntity = entityItem1.getDownLink(b1);
/*  2755 */           if (eANEntity.getEntityType().equals("MODEL"))
/*       */           {
/*  2757 */             if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */               
/*  2759 */               EntityItem entityItem2 = (EntityItem)eANEntity;
/*  2760 */               for (byte b2 = 0; b2 < entityItem1.getUpLinkCount(); b2++) {
/*       */                 
/*  2762 */                 EANEntity eANEntity1 = entityItem1.getUpLink(b2);
/*  2763 */                 if (eANEntity1.getEntityType().equals("FEATURE")) {
/*       */                   
/*  2765 */                   EntityItem entityItem3 = (EntityItem)eANEntity1;
/*       */ 
/*       */ 
/*       */ 
/*       */                   
/*  2770 */                   populate_SalesManual_TM(entityItem, entityItem1, entityItem2, entityItem3);
/*       */                 } 
/*       */               } 
/*       */             } 
/*       */           }
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populate_SalesManual_TM(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, EntityItem paramEntityItem4) {
/*  2816 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  2817 */     String str9 = paramEntityItem4.getKey();
/*  2818 */     updateGeoHT(paramEntityItem1, str9);
/*       */     
/*  2820 */     String str1 = getAttributeFlagValue(paramEntityItem3, "MACHTYPEATR");
/*  2821 */     if (null == str1)
/*       */     {
/*  2823 */       str1 = " ";
/*       */     }
/*  2825 */     str1 = str1.trim();
/*  2826 */     str1 = setString(str1, 4);
/*  2827 */     String str2 = str1;
/*  2828 */     String str3 = getAttributeValue(paramEntityItem3, "MODELATR", "", "000", false);
/*  2829 */     str3 = str3.trim();
/*  2830 */     str3 = setString(str3, 3);
/*  2831 */     str1 = str1 + "<:>" + str3;
/*  2832 */     String str4 = getAttributeValue(paramEntityItem4, "FEATURECODE", "", "", false);
/*  2833 */     str4 = str4.trim();
/*  2834 */     if (str4.equals(""))
/*       */     {
/*  2836 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>FEATURE<:>Feature Code");
/*       */     }
/*  2838 */     str4 = setString(str4, 4);
/*  2839 */     str1 = str1 + "<:>" + str4;
/*       */     
/*  2841 */     String str5 = "";
/*  2842 */     String str6 = "";
/*  2843 */     if (1 == this.format) {
/*       */       
/*  2845 */       str5 = getAttributeValue(paramEntityItem4, "INVNAME", "", "", false).trim();
/*  2846 */       if (str5.equals("")) {
/*       */         
/*  2848 */         str5 = getAttributeValue(paramEntityItem4, "COMNAME", "", "", false);
/*  2849 */         if (str5.length() > 28)
/*       */         {
/*  2851 */           str5 = str5.substring(0, 28);
/*       */         }
/*  2853 */         str5 = str5.trim();
/*  2854 */         str5 = str5.toUpperCase();
/*       */       } 
/*       */ 
/*       */       
/*  2858 */       str1 = str1 + "<:>" + str5;
/*       */     }
/*  2860 */     else if (2 == this.format || 3 == this.format) {
/*       */       
/*  2862 */       str6 = getAttributeValue(paramEntityItem4, "MKTGNAME", "", "", false).trim();
/*  2863 */       if (str6.equals(""))
/*       */       {
/*  2865 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>FEATURE " + str4 + "<:>Marketing Name");
/*       */       }
/*  2867 */       str1 = str1 + "<:>" + str6;
/*       */     } 
/*       */     
/*  2870 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem4, "FCMKTGDESC", "", "", false).trim();
/*       */     
/*  2872 */     if (getAttributeValue(paramEntityItem4, "FCMKTGDESC", "", "", false).trim().equals(""))
/*       */     {
/*  2874 */       if (2 == this.format || 3 == this.format)
/*       */       {
/*  2876 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>FEATURE " + str4 + "<:>FC Marketing Description");
/*       */       }
/*       */     }
/*       */     
/*  2880 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "SYSTEMMIN", "", "", false).trim();
/*       */     
/*  2882 */     if (getAttributeValue(paramEntityItem2, "SYSTEMMIN", "", "", false).trim().equals(""))
/*       */     {
/*  2884 */       if (2 == this.format || 3 == this.format)
/*       */       {
/*  2886 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Minimum Required");
/*       */       }
/*       */     }
/*       */     
/*  2890 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "SYSTEMMAX", "", "", false).trim();
/*       */     
/*  2892 */     if (getAttributeValue(paramEntityItem2, "SYSTEMMAX", "", "", false).trim().equals(""))
/*       */     {
/*  2894 */       if (2 == this.format || 3 == this.format)
/*       */       {
/*  2896 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Maximum Allowed");
/*       */       }
/*       */     }
/*       */     
/*  2900 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "INITORDERMAX", "", "", false).trim();
/*       */     
/*  2902 */     if (getAttributeValue(paramEntityItem2, "INITORDERMAX", "", "", false).trim().equals(""))
/*       */     {
/*  2904 */       if (2 == this.format || 3 == this.format)
/*       */       {
/*  2906 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Initial Order Maximum");
/*       */       }
/*       */     }
/*       */     
/*  2910 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "OSLEVELCOMPLEMENT", ", ", "", false).trim();
/*       */     
/*  2912 */     if (getAttributeValue(paramEntityItem2, "OSLEVELCOMPLEMENT", "", "", false).trim().equals(""))
/*       */     {
/*  2914 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>OS Level Complement");
/*       */     }
/*       */ 
/*       */     
/*  2918 */     if (isSelected(paramEntityItem2, "ORDERCODE", "5955")) {
/*       */       
/*  2920 */       str1 = str1 + "<:>Both";
/*       */     }
/*  2922 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "5956")) {
/*       */       
/*  2924 */       str1 = str1 + "<:>MES";
/*       */     }
/*  2926 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "5957")) {
/*       */       
/*  2928 */       str1 = str1 + "<:>Initial";
/*       */     }
/*  2930 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "5958")) {
/*       */       
/*  2932 */       str1 = str1 + "<:>Support";
/*       */     }
/*       */     else {
/*       */       
/*  2936 */       str1 = str1 + "<:>";
/*       */     } 
/*       */     
/*  2939 */     if (getAttributeValue(paramEntityItem2, "ORDERCODE", "", "", false).equals(""))
/*       */     {
/*  2941 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */     }
/*       */ 
/*       */     
/*  2945 */     if (isSelected(paramEntityItem2, "INSTALL", "5671")) {
/*       */       
/*  2947 */       str1 = str1 + "<:>Yes";
/*       */     }
/*  2949 */     else if (isSelected(paramEntityItem2, "INSTALL", "5672")) {
/*       */       
/*  2951 */       str1 = str1 + "<:>No ";
/*       */     }
/*  2953 */     else if (isSelected(paramEntityItem2, "INSTALL", "5673")) {
/*       */       
/*  2955 */       str1 = str1 + "<:>N/A";
/*       */     }
/*       */     else {
/*       */       
/*  2959 */       str1 = str1 + "<:>Does not apply";
/*       */     } 
/*       */     
/*  2962 */     if (getAttributeValue(paramEntityItem2, "INSTALL", "", "", false).equals(""))
/*       */     {
/*  2964 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Customer Install");
/*       */     }
/*       */ 
/*       */     
/*  2968 */     if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5100")) {
/*       */       
/*  2970 */       str1 = str1 + "<:>Yes";
/*       */     }
/*  2972 */     else if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5101")) {
/*       */       
/*  2974 */       str1 = str1 + "<:>No";
/*       */     }
/*  2976 */     else if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5102")) {
/*       */       
/*  2978 */       str1 = str1 + "<:>Does not apply";
/*       */     }
/*  2980 */     else if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5103")) {
/*       */       
/*  2982 */       str1 = str1 + "<:>Feature conversion only";
/*       */     }
/*       */     else {
/*       */       
/*  2986 */       str1 = str1 + "<:>";
/*       */     } 
/*       */     
/*  2989 */     if (getAttributeValue(paramEntityItem2, "RETURNEDPARTS", "", "", false).equals(""))
/*       */     {
/*  2991 */       if (2 == this.format || 3 == this.format)
/*       */       {
/*  2993 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Returned Parts MES");
/*       */       }
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  2999 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "EDITORNOTE", "", "", false).trim();
/*  3000 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem4, "EDITORNOTE", "", "", false).trim();
/*  3001 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem4, "ATTPROVIDED", "", "", false).trim();
/*  3002 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem4, "ATTREQUIRED", "", "", false).trim();
/*       */     
/*  3004 */     String str7 = getGeo(str9);
/*       */     
/*  3006 */     addToTreeMap(str1, str7, this.salesManual_TM);
/*       */     
/*  3008 */     str1 = getAttributeFlagValue(paramEntityItem3, "MACHTYPEATR");
/*  3009 */     if (null == str1)
/*       */     {
/*  3011 */       str1 = " ";
/*       */     }
/*  3013 */     str1 = str1.trim();
/*  3014 */     str1 = setString(str1, 4);
/*  3015 */     String str8 = getAttributeValue(paramEntityItem4, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
/*  3016 */     if (str8.equals("UNKNOWN HWFCCAT(FEATURE)"))
/*       */     {
/*  3018 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>FEATURE " + str4 + "<:>HW Feature Category");
/*       */     }
/*  3020 */     str1 = str1 + "<:>" + str8;
/*  3021 */     str1 = str1 + "<:>" + str4;
/*  3022 */     if (1 == this.format) {
/*       */       
/*  3024 */       str1 = str1 + "<:>" + str5;
/*       */     }
/*  3026 */     else if (2 == this.format || 3 == this.format) {
/*       */       
/*  3028 */       str1 = str1 + "<:>" + str6;
/*       */     } 
/*  3030 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem4, "EDITORNOTE", "", "", false).trim();
/*       */ 
/*       */ 
/*       */     
/*  3034 */     hashtable.clear();
/*  3035 */     hashtable.put("PRICEDFEATURE", "120");
/*  3036 */     hashtable.put("ZEROPRICE", "120");
/*  3037 */     if (isEntityWithMatchedAttr(paramEntityItem4, hashtable))
/*       */     {
/*  3039 */       addToTreeMap(str1, str7, this.salesManualSpecifyFeatures_TM);
/*       */     }
/*       */     
/*  3042 */     hashtable.clear();
/*  3043 */     hashtable.put("PRICEDFEATURE", "120");
/*  3044 */     if (!isEntityWithMatchedAttr(paramEntityItem4, hashtable))
/*       */     {
/*       */       
/*  3047 */       if (isSelected(paramEntityItem2, "ORDERCODE", "5957")) {
/*       */         
/*  3049 */         addToTreeMap(str1, str7, this.salesManualSpecialFeaturesInitialOrder_TM);
/*       */       }
/*  3051 */       else if (isSelected(paramEntityItem2, "ORDERCODE", "5955") || 
/*  3052 */         isSelected(paramEntityItem2, "ORDERCODE", "5956") || 
/*  3053 */         isSelected(paramEntityItem2, "ORDERCODE", "5958")) {
/*       */         
/*  3055 */         addToTreeMap(str1, str7, this.salesManualSpecialFeaturesOther_TM);
/*       */       } 
/*       */     }
/*  3058 */     hashtable.clear();
/*  3059 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void supportedDevices() {
/*  3069 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  3071 */     hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3076 */     hashtable.put("COFGRP", "150");
/*       */     
/*  3078 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  3079 */     while (iterator.hasNext()) {
/*       */       
/*  3081 */       EntityItem entityItem = iterator.next();
/*  3082 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*  3083 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/*  3084 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  3086 */         EntityItem entityItem1 = vector.get(b);
/*  3087 */         Vector vector1 = getAllLinkedEntities(entityItem1, "MDLCGMDL", "MODELCG");
/*  3088 */         Vector vector2 = getAllLinkedEntities(vector1, "MDLCGMDLCGOS", "MODELCGOS");
/*  3089 */         Vector vector3 = getAllLinkedEntities(vector2, "MDLCGOSMDL", "MODEL");
/*  3090 */         populate_SupportedDevices_TM(entityItem, entityItem1, vector3);
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  3095 */     iterator = this.availVector.iterator();
/*  3096 */     while (iterator.hasNext()) {
/*       */       
/*  3098 */       EntityItem entityItem = iterator.next();
/*  3099 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  3100 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  3102 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*  3104 */         for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/*       */           
/*  3106 */           EANEntity eANEntity = entityItem1.getDownLink(b1);
/*  3107 */           if (eANEntity.getEntityType().equals("MODEL"))
/*       */           {
/*  3109 */             if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */               
/*  3111 */               EntityItem entityItem2 = (EntityItem)eANEntity;
/*  3112 */               Vector vector1 = getAllLinkedEntities(entityItem2, "MDLCGMDL", "MODELCG");
/*  3113 */               Vector vector2 = getAllLinkedEntities(vector1, "MDLCGMDLCGOS", "MODELCGOS");
/*  3114 */               Vector vector3 = getAllLinkedEntities(vector2, "MDLCGOSMDL", "MODEL");
/*  3115 */               populate_SupportedDevices_TM(entityItem, entityItem2, vector3);
/*       */             } 
/*       */           }
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populate_SupportedDevices_TM(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector<EntityItem> paramVector) {
/*  3152 */     for (byte b = 0; b < paramVector.size(); b++) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  3161 */       String str7 = paramEntityItem2.getKey();
/*  3162 */       updateGeoHT(paramEntityItem1, str7);
/*       */       
/*  3164 */       EntityItem entityItem = paramVector.get(b);
/*       */       
/*  3166 */       String str1 = getAttributeFlagValue(paramEntityItem2, "MACHTYPEATR");
/*  3167 */       if (null == str1)
/*       */       {
/*  3169 */         str1 = " ";
/*       */       }
/*  3171 */       str1 = str1.trim();
/*  3172 */       str1 = setString(str1, 4);
/*  3173 */       String str2 = str1;
/*       */       
/*  3175 */       str1 = str1 + "<:>" + getAttributeValue(entityItem, "COMPATDVCCAT", "", "", false).trim();
/*  3176 */       String str3 = getAttributeValue(entityItem, "MACHTYPEATR", "", "0000", false);
/*  3177 */       str3 = str3.trim();
/*  3178 */       str3 = setString(str3, 4);
/*  3179 */       str1 = str1 + "<:>" + str3;
/*  3180 */       String str4 = getAttributeValue(entityItem, "MODELATR", "", "000", false);
/*  3181 */       str4 = str4.trim();
/*  3182 */       str4 = setString(str4, 3);
/*  3183 */       str1 = str1 + "<:>" + str4;
/*  3184 */       str1 = str1 + "<:>" + getAttributeValue(entityItem, "MKTGNAME", "", "", false).trim();
/*  3185 */       String str5 = getAttributeValue(paramEntityItem2, "MODELATR", "", "000", false);
/*  3186 */       str5 = str5.trim();
/*  3187 */       str5 = setString(str5, 3);
/*  3188 */       str1 = str1 + "<:>" + str5;
/*  3189 */       String str6 = getGeo(str7);
/*       */       
/*  3191 */       if (getAttributeValue(entityItem, "COMPATDVCCAT", "", "", false).trim().equals(""))
/*       */       {
/*  3193 */         this.featureMatrixError.add("40<:>External Machine Type (Support Devices)<:>" + str2 + "<:>" + str5 + "<:>SUPPDEVICE: " + str3 + "-" + str4 + "<:>COMPATDVCCAT");
/*       */       }
/*       */       
/*  3196 */       if (getAttributeValue(entityItem, "MKTGNAME", "", "", false).trim().equals(""))
/*       */       {
/*  3198 */         this.featureMatrixError.add("40<:>External Machine Type (Support Devices)<:>" + str2 + "<:>" + str5 + "<:>SUPPDEVICE: " + str3 + "-" + str4 + "<:>Marketing Name");
/*       */       }
/*       */       
/*  3201 */       addToTreeMap(str1, str6, this.supportedDevices_TM);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void featureMatrix() {
/*  3223 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  3225 */     hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3230 */     hashtable.put("COFGRP", "150");
/*       */     
/*  3232 */     String str1 = "";
/*  3233 */     String str2 = "";
/*       */ 
/*       */     
/*  3236 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  3237 */     while (iterator.hasNext()) {
/*       */       
/*  3239 */       EntityItem entityItem = iterator.next();
/*  3240 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*  3241 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/*  3242 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  3245 */         EntityItem entityItem1 = vector.get(b);
/*  3246 */         String str = entityItem1.getKey();
/*  3247 */         updateGeoHT(entityItem, str);
/*       */         
/*  3249 */         EANEntity eANEntity = getUpLinkEntityItem(entityItem1, "PRODSTRUCT");
/*  3250 */         if (null != eANEntity) {
/*       */           
/*  3252 */           EntityItem entityItem2 = (EntityItem)eANEntity;
/*  3253 */           EANEntity eANEntity1 = getUpLinkEntityItem(entityItem2, "FEATURE");
/*  3254 */           if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*  3260 */             EntityItem entityItem3 = (EntityItem)eANEntity1;
/*       */             
/*  3262 */             String str7 = getAttributeFlagValue(entityItem1, "MACHTYPEATR");
/*  3263 */             if (null == str7)
/*       */             {
/*  3265 */               str7 = " ";
/*       */             }
/*  3267 */             str7 = str7.trim();
/*  3268 */             str7 = setString(str7, 4);
/*  3269 */             String str3 = str7;
/*  3270 */             String str4 = getAttributeValue(entityItem1, "MODELATR", "", "000", false);
/*  3271 */             str4 = str4.trim();
/*  3272 */             str4 = setString(str4, 3);
/*       */             
/*  3274 */             str7 = str7 + "<:>" + str4;
/*  3275 */             String str5 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  3276 */             str5 = str5.trim();
/*  3277 */             if (str5.equals(""))
/*       */             {
/*  3279 */               this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>FEATURE<:>Feature Code");
/*       */             }
/*  3281 */             str5 = setString(str5, 4);
/*  3282 */             str7 = str7 + "<:>" + str5;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*  3288 */             if (isSelected(entityItem2, "ORDERCODE", "5955")) {
/*       */               
/*  3290 */               if (2 == this.format)
/*       */               {
/*  3292 */                 str7 = str7 + "<:>A";
/*       */               }
/*       */               else
/*       */               {
/*  3296 */                 str7 = str7 + "<:>B";
/*       */               }
/*       */             
/*  3299 */             } else if (isSelected(entityItem2, "ORDERCODE", "5956")) {
/*       */               
/*  3301 */               if (2 == this.format)
/*       */               {
/*  3303 */                 str7 = str7 + "<:>A";
/*       */               }
/*       */               else
/*       */               {
/*  3307 */                 str7 = str7 + "<:>M";
/*       */               }
/*       */             
/*  3310 */             } else if (isSelected(entityItem2, "ORDERCODE", "5957")) {
/*       */               
/*  3312 */               if (2 == this.format)
/*       */               {
/*  3314 */                 str7 = str7 + "<:>A";
/*       */               }
/*       */               else
/*       */               {
/*  3318 */                 str7 = str7 + "<:>I";
/*       */               }
/*       */             
/*  3321 */             } else if (isSelected(entityItem2, "ORDERCODE", "5958")) {
/*       */               
/*  3323 */               str7 = str7 + "<:>S";
/*       */             }
/*  3325 */             else if (isSelected(entityItem2, "ORDERCODE", "5959")) {
/*       */               
/*  3327 */               if (2 == this.format)
/*       */               {
/*  3329 */                 str7 = str7 + "<:>N";
/*       */               }
/*       */               else
/*       */               {
/*  3333 */                 str7 = str7 + "<:> ";
/*       */               }
/*       */             
/*       */             } else {
/*       */               
/*  3338 */               str7 = str7 + "<:> ";
/*       */             } 
/*       */             
/*  3341 */             if (getAttributeValue(entityItem2, "ORDERCODE", "", "", false).equals(""))
/*       */             {
/*  3343 */               this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>PRODSTRUCT " + getAttributeValue(entityItem2, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */             }
/*       */             
/*  3346 */             if (1 == this.format) {
/*       */               
/*  3348 */               str1 = getAttributeValue(entityItem3, "INVNAME", "", "", false).trim();
/*  3349 */               if (str1.equals("")) {
/*       */                 
/*  3351 */                 str1 = getAttributeValue(entityItem3, "COMNAME", "", "", false);
/*  3352 */                 if (str1.length() > 28)
/*       */                 {
/*  3354 */                   str1 = str1.substring(0, 28);
/*       */                 }
/*  3356 */                 str1 = str1.trim();
/*  3357 */                 str1 = str1.toUpperCase();
/*       */               } 
/*       */ 
/*       */               
/*  3361 */               str7 = str7 + "<:>" + str1;
/*       */             }
/*  3363 */             else if (2 == this.format || 3 == this.format) {
/*       */               
/*  3365 */               str2 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false).trim();
/*  3366 */               if (str2.equals(""))
/*       */               {
/*  3368 */                 this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>FEATURE " + str5 + "<:>Marketing Name");
/*       */               }
/*  3370 */               str7 = str7 + "<:>" + str2;
/*       */             } 
/*       */             
/*  3373 */             str7 = str7 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */             
/*  3375 */             String str6 = getGeo(str);
/*       */             
/*  3377 */             addToTreeMap(str7, str6, this.featureMatrix_TM);
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  3384 */     iterator = this.availVector.iterator();
/*  3385 */     while (iterator.hasNext()) {
/*       */       
/*  3387 */       EntityItem entityItem = iterator.next();
/*  3388 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  3389 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  3392 */         EntityItem entityItem1 = vector.get(b);
/*  3393 */         String str = entityItem1.getKey();
/*  3394 */         updateGeoHT(entityItem, str);
/*       */         
/*  3396 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*  3397 */         if (null != eANEntity)
/*       */         {
/*  3399 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*  3401 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*  3402 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  3403 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  3409 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*       */               
/*  3411 */               String str7 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  3412 */               if (null == str7)
/*       */               {
/*  3414 */                 str7 = " ";
/*       */               }
/*  3416 */               str7 = str7.trim();
/*  3417 */               str7 = setString(str7, 4);
/*  3418 */               String str3 = str7;
/*  3419 */               String str4 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  3420 */               str4 = str4.trim();
/*  3421 */               str4 = setString(str4, 3);
/*       */               
/*  3423 */               str7 = str7 + "<:>" + str4;
/*  3424 */               String str5 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  3425 */               str5 = str5.trim();
/*  3426 */               if (str5.equals(""))
/*       */               {
/*  3428 */                 this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*  3430 */               str5 = setString(str5, 4);
/*  3431 */               str7 = str7 + "<:>" + str5;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  3437 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  3439 */                 if (2 == this.format)
/*       */                 {
/*  3441 */                   str7 = str7 + "<:>A";
/*       */                 }
/*       */                 else
/*       */                 {
/*  3445 */                   str7 = str7 + "<:>B";
/*       */                 }
/*       */               
/*  3448 */               } else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  3450 */                 if (2 == this.format)
/*       */                 {
/*  3452 */                   str7 = str7 + "<:>A";
/*       */                 }
/*       */                 else
/*       */                 {
/*  3456 */                   str7 = str7 + "<:>M";
/*       */                 }
/*       */               
/*  3459 */               } else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  3461 */                 if (2 == this.format)
/*       */                 {
/*  3463 */                   str7 = str7 + "<:>A";
/*       */                 }
/*       */                 else
/*       */                 {
/*  3467 */                   str7 = str7 + "<:>I";
/*       */                 }
/*       */               
/*  3470 */               } else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  3472 */                 str7 = str7 + "<:>S";
/*       */               }
/*  3474 */               else if (isSelected(entityItem1, "ORDERCODE", "5959")) {
/*       */                 
/*  3476 */                 if (2 == this.format)
/*       */                 {
/*  3478 */                   str7 = str7 + "<:>N";
/*       */                 }
/*       */                 else
/*       */                 {
/*  3482 */                   str7 = str7 + "<:> ";
/*       */                 }
/*       */               
/*       */               } else {
/*       */                 
/*  3487 */                 str7 = str7 + "<:> ";
/*       */               } 
/*       */               
/*  3490 */               if (getAttributeValue(entityItem1, "ORDERCODE", "", "", false).equals(""))
/*       */               {
/*  3492 */                 this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */               }
/*       */               
/*  3495 */               if (1 == this.format) {
/*       */                 
/*  3497 */                 str1 = getAttributeValue(entityItem3, "INVNAME", "", "", false).trim();
/*  3498 */                 if (str1.equals("")) {
/*       */                   
/*  3500 */                   str1 = getAttributeValue(entityItem3, "COMNAME", "", "", false);
/*  3501 */                   if (str1.length() > 28)
/*       */                   {
/*  3503 */                     str1 = str1.substring(0, 28);
/*       */                   }
/*  3505 */                   str1 = str1.trim();
/*  3506 */                   str1 = str1.toUpperCase();
/*       */                 } 
/*       */ 
/*       */                 
/*  3510 */                 str7 = str7 + "<:>" + str1;
/*       */               }
/*  3512 */               else if (2 == this.format || 3 == this.format) {
/*       */                 
/*  3514 */                 str2 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false).trim();
/*  3515 */                 if (str2.equals(""))
/*       */                 {
/*  3517 */                   this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>FEATURE " + str5 + "<:>Marketing Name");
/*       */                 }
/*  3519 */                 str7 = str7 + "<:>" + str2;
/*       */               } 
/*       */               
/*  3522 */               str7 = str7 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */               
/*  3524 */               String str6 = getGeo(str);
/*       */               
/*  3526 */               addToTreeMap(str7, str6, this.featureMatrix_TM);
/*       */             } 
/*       */           } 
/*       */         }
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getGEOInfoForLSEO() {
/*  3540 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  3541 */     while (iterator.hasNext()) {
/*       */       
/*  3543 */       EntityItem entityItem = iterator.next();
/*  3544 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "LSEOAVAIL", "LSEO");
/*  3545 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  3547 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*  3549 */         String str = entityItem1.getKey();
/*  3550 */         updateGeoHT(entityItem, str);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getMTM() {
/*  3561 */     String str1 = "";
/*  3562 */     String str2 = "";
/*       */ 
/*       */ 
/*       */     
/*  3566 */     this.lseoVector = getAllLinkedEntities(this.availVector, "LSEOAVAIL", "LSEO");
/*  3567 */     Vector vector = getAllLinkedEntities(this.lseoVector, "WWSEOLSEO", "WWSEO");
/*  3568 */     Vector<EntityItem> vector1 = getAllLinkedEntities(this.lseoVector, "LSEOPRODSTRUCT", "PRODSTRUCT");
/*  3569 */     if (vector.size() > 0)
/*       */     {
/*  3571 */       vector1.addAll(getAllLinkedEntities(vector, "WWSEOPRODSTRUCT", "PRODSTRUCT"));
/*       */     }
/*       */     
/*  3574 */     for (byte b = 0; b < vector1.size(); b++) {
/*       */       
/*  3576 */       EntityItem entityItem1 = vector1.get(b);
/*  3577 */       EANEntity eANEntity1 = getDownLinkEntityItem(entityItem1, "MODEL");
/*  3578 */       EntityItem entityItem2 = (EntityItem)eANEntity1;
/*  3579 */       EANEntity eANEntity2 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  3580 */       if (null != eANEntity2 && null != entityItem2) {
/*       */         
/*  3582 */         str1 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  3583 */         if (null == str1)
/*       */         {
/*  3585 */           str1 = " ";
/*       */         }
/*  3587 */         str1 = str1.trim();
/*  3588 */         str1 = setString(str1, 4);
/*  3589 */         str2 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  3590 */         str2 = str2.trim();
/*  3591 */         str2 = setString(str2, 3);
/*       */         
/*  3593 */         this.mtmTS.add(str1 + "-" + str2);
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  3598 */     vector.clear();
/*  3599 */     vector = null;
/*  3600 */     vector1.clear();
/*  3601 */     vector1 = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populateSEOTable() {
/*  3625 */     Iterator<String> iterator = this.mtmTS.iterator();
/*  3626 */     while (iterator.hasNext()) {
/*       */       
/*  3628 */       String str = iterator.next();
/*  3629 */       for (byte b = 0; b < this.lseoVector.size(); b++) {
/*       */         
/*  3631 */         EntityItem entityItem = this.lseoVector.get(b);
/*  3632 */         populateSEOTable(str, entityItem);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populateSEOTable(String paramString, EntityItem paramEntityItem) {
/*  3644 */     String str1 = "";
/*  3645 */     String str2 = "";
/*  3646 */     String str3 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3652 */     boolean bool = true;
/*  3653 */     String str4 = getGeo(paramEntityItem.getKey());
/*       */     
/*  3655 */     TreeSet treeSet = new TreeSet();
/*       */     
/*  3657 */     TreeSet[] arrayOfTreeSet = new TreeSet[4]; byte b;
/*  3658 */     for (b = 0; b < 4; b++)
/*       */     {
/*  3660 */       arrayOfTreeSet[b] = new TreeSet();
/*       */     }
/*       */ 
/*       */     
/*  3664 */     Vector<EntityItem> vector2 = getAllLinkedEntities(paramEntityItem, "WWSEOLSEO", "WWSEO");
/*  3665 */     Vector<EntityItem> vector1 = getAllLinkedEntities(paramEntityItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
/*  3666 */     if (vector2.size() > 0)
/*       */     {
/*  3668 */       vector1.addAll(getAllLinkedEntities(vector2.get(0), "WWSEOPRODSTRUCT", "PRODSTRUCT"));
/*       */     }
/*       */     
/*  3671 */     for (b = 0; b < vector1.size(); b++) {
/*       */       
/*  3673 */       EntityItem entityItem1 = vector1.get(b);
/*  3674 */       EANEntity eANEntity1 = getDownLinkEntityItem(entityItem1, "MODEL");
/*  3675 */       EntityItem entityItem2 = (EntityItem)eANEntity1;
/*  3676 */       EANEntity eANEntity2 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  3677 */       if (null != eANEntity2 && null != entityItem2) {
/*       */         
/*  3679 */         str1 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  3680 */         if (null == str1)
/*       */         {
/*  3682 */           str1 = " ";
/*       */         }
/*  3684 */         str1 = str1.trim();
/*  3685 */         str1 = setString(str1, 4);
/*  3686 */         str2 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  3687 */         str2 = str2.trim();
/*  3688 */         str2 = setString(str2, 3);
/*  3689 */         str3 = str1 + "-" + str2;
/*       */         
/*  3691 */         if (str3.equals(paramString)) {
/*       */           
/*  3693 */           getPROCForSEOTable((EntityItem)eANEntity2, arrayOfTreeSet[0]);
/*  3694 */           if (bool)
/*       */           {
/*  3696 */             getDDForSEOTable(vector2, arrayOfTreeSet[1]);
/*       */           }
/*  3698 */           getHDDForSEOTable((EntityItem)eANEntity2, arrayOfTreeSet[2]);
/*  3699 */           getOptcalDvcForSEOTable((EntityItem)eANEntity2, arrayOfTreeSet[3]);
/*  3700 */           bool = false;
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  3705 */     if (arrayOfTreeSet[0].size() > 1)
/*       */     {
/*  3707 */       arrayOfTreeSet[0].remove("   <:>   <:>  <:>  ");
/*       */     }
/*  3709 */     if (arrayOfTreeSet[1].size() > 1)
/*       */     {
/*  3711 */       arrayOfTreeSet[1].remove("   <:>  ");
/*       */     }
/*  3713 */     if (arrayOfTreeSet[2].size() > 1)
/*       */     {
/*  3715 */       arrayOfTreeSet[2].remove(setString(" ", 12));
/*       */     }
/*  3717 */     if (arrayOfTreeSet[3].size() > 1)
/*       */     {
/*  3719 */       arrayOfTreeSet[3].remove(setString(" ", 21));
/*       */     }
/*       */     
/*  3722 */     merge(str4, paramString, treeSet, arrayOfTreeSet);
/*       */     
/*  3724 */     Iterator<String> iterator = treeSet.iterator();
/*  3725 */     while (iterator.hasNext()) {
/*       */       
/*  3727 */       String str = iterator.next();
/*  3728 */       this.seoTable_TM.put(str, str4);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getPROCForSEOTable(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  3745 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREPROC", "PROC");
/*       */     
/*  3747 */     if (vector.size() > 0) {
/*       */       
/*  3749 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  3751 */         EntityItem entityItem = vector.get(b);
/*  3752 */         String str1 = getAttributeValue(entityItem, "CLOCKRATE", "", "", false);
/*  3753 */         str1 = str1.trim();
/*  3754 */         str1 = setString2(str1, 3);
/*  3755 */         String str2 = getAttributeValue(entityItem, "CLOCKRATEUNIT", "", "", false);
/*  3756 */         str2 = str2.trim();
/*  3757 */         str2 = setString(str2, 3);
/*  3758 */         String str3 = getAttributeValue(entityItem, "PROCL2CACHE", "", "", false);
/*  3759 */         str3 = str3.trim();
/*  3760 */         str3 = setString2(str3, 2);
/*  3761 */         String str4 = getAttributeValue(entityItem, "PROCL2CACHEUNIT", "", "", false);
/*  3762 */         str4 = str4.trim();
/*  3763 */         str4 = setString(str4, 2);
/*       */         
/*  3765 */         paramTreeSet.add(str1 + "<:>" + str2 + "<:>" + str3 + "<:>" + str4);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  3770 */       paramTreeSet.add("   <:>   <:>  <:>  ");
/*       */     } 
/*       */     
/*  3773 */     vector.clear();
/*  3774 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getDDForSEOTable(Vector<EntityItem> paramVector, TreeSet<String> paramTreeSet) {
/*  3791 */     if (paramVector.size() > 0) {
/*       */       
/*  3793 */       EntityItem entityItem = paramVector.get(0);
/*       */       
/*  3795 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "WWSEODERIVEDDATA", "DERIVEDDATA");
/*  3796 */       if (vector.size() > 0)
/*       */       {
/*  3798 */         EntityItem entityItem1 = vector.get(0);
/*       */         
/*  3800 */         String str1 = getAttributeValue(entityItem1, "MEMRYRAMSTD", "", "", false);
/*  3801 */         str1 = str1.trim();
/*  3802 */         str1 = setString2(str1, 3);
/*  3803 */         String str2 = getAttributeValue(entityItem1, "MEMRYRAMSTDUNIT", "", "", false);
/*  3804 */         str2 = str2.trim();
/*  3805 */         str2 = setString(str2, 2);
/*       */         
/*  3807 */         paramTreeSet.add(str1 + "<:>" + str2);
/*       */       }
/*       */       else
/*       */       {
/*  3811 */         paramTreeSet.add("   <:>  ");
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  3816 */       paramTreeSet.add("   <:>  ");
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getHDDForSEOTable(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  3833 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREHDD", "HDD");
/*       */     
/*  3835 */     if (vector.size() > 0) {
/*       */       
/*  3837 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  3839 */         EntityItem entityItem = vector.get(b);
/*  3840 */         String str1 = getAttributeValue(entityItem, "INTRFC", "", "", false);
/*  3841 */         str1 = str1.trim();
/*  3842 */         String str2 = getAttributeValue(entityItem, "HDDSZE", "", "", false);
/*  3843 */         str2 = str2.trim();
/*  3844 */         String str3 = getAttributeValue(entityItem, "HDDSZEUNIT", "", "", false);
/*  3845 */         str3 = str3.trim();
/*  3846 */         String str4 = str1 + " " + str2 + " " + str3;
/*  3847 */         str4 = str4.trim();
/*       */         
/*  3849 */         if (str4.length() < 12)
/*       */         {
/*  3851 */           str4 = setString(str4, 12);
/*       */         }
/*       */         
/*  3854 */         paramTreeSet.add(str4);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  3859 */       paramTreeSet.add(setString(" ", 12));
/*       */     } 
/*       */     
/*  3862 */     vector.clear();
/*  3863 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getOptcalDvcForSEOTable(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  3876 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREOPTCALDVC", "OPTCALDVC");
/*       */     
/*  3878 */     if (vector.size() > 0) {
/*       */       
/*  3880 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  3882 */         EntityItem entityItem = vector.get(b);
/*  3883 */         String str = getAttributeValue(entityItem, "OPTCALDRIVETYPE", "", "", false);
/*  3884 */         str = str.trim();
/*       */         
/*  3886 */         if (str.length() < 21)
/*       */         {
/*  3888 */           str = setString(str, 21);
/*       */         }
/*       */         
/*  3891 */         paramTreeSet.add(str);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  3896 */       paramTreeSet.add(setString(" ", 21));
/*       */     } 
/*       */     
/*  3899 */     vector.clear();
/*  3900 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populateSEODescription() {
/*  3924 */     Iterator<String> iterator = this.mtmTS.iterator();
/*  3925 */     while (iterator.hasNext()) {
/*       */       
/*  3927 */       String str = iterator.next();
/*  3928 */       for (byte b = 0; b < this.lseoVector.size(); b++) {
/*       */         
/*  3930 */         EntityItem entityItem = this.lseoVector.get(b);
/*  3931 */         populateSEODescription(str, entityItem);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populateSEODescription(String paramString, EntityItem paramEntityItem) {
/*  3942 */     String str1 = "";
/*  3943 */     String str2 = "";
/*  3944 */     String str3 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3949 */     boolean bool = true;
/*  3950 */     Vector vector = new Vector();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3955 */     TreeSet[] arrayOfTreeSet = new TreeSet[13]; byte b;
/*  3956 */     for (b = 0; b < 13; b++)
/*       */     {
/*  3958 */       arrayOfTreeSet[b] = new TreeSet();
/*       */     }
/*       */ 
/*       */     
/*  3962 */     Vector<EntityItem> vector2 = getAllLinkedEntities(paramEntityItem, "WWSEOLSEO", "WWSEO");
/*  3963 */     Vector<EntityItem> vector1 = getAllLinkedEntities(paramEntityItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
/*  3964 */     if (vector2.size() > 0)
/*       */     {
/*  3966 */       vector1.addAll(getAllLinkedEntities(vector2.get(0), "WWSEOPRODSTRUCT", "PRODSTRUCT"));
/*       */     }
/*       */     
/*  3969 */     for (b = 0; b < vector1.size(); b++) {
/*       */       
/*  3971 */       EntityItem entityItem1 = vector1.get(b);
/*  3972 */       EANEntity eANEntity1 = getDownLinkEntityItem(entityItem1, "MODEL");
/*  3973 */       EntityItem entityItem2 = (EntityItem)eANEntity1;
/*  3974 */       EANEntity eANEntity2 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  3975 */       if (null != eANEntity2 && null != entityItem2) {
/*       */         
/*  3977 */         EntityItem entityItem = (EntityItem)eANEntity2;
/*  3978 */         str1 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  3979 */         if (null == str1)
/*       */         {
/*  3981 */           str1 = " ";
/*       */         }
/*  3983 */         str1 = str1.trim();
/*  3984 */         str1 = setString(str1, 4);
/*  3985 */         str2 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  3986 */         str2 = str2.trim();
/*  3987 */         str2 = setString(str2, 3);
/*  3988 */         str3 = str1 + "-" + str2;
/*       */         
/*  3990 */         if (str3.equals(paramString)) {
/*       */           
/*  3992 */           getPROCForSEODescription(entityItem, arrayOfTreeSet[0]);
/*  3993 */           if (bool)
/*       */           {
/*  3995 */             getDDForSEODescription(vector2, arrayOfTreeSet[1]);
/*       */           }
/*  3997 */           getMemoryForSEODescription(entityItem, arrayOfTreeSet[2]);
/*  3998 */           getPlanarForSEODescription(entityItem, arrayOfTreeSet[3]);
/*  3999 */           getGrphAdapterForSEODescription(entityItem, arrayOfTreeSet[4]);
/*  4000 */           getHDCForSEODescription(entityItem, arrayOfTreeSet[5]);
/*  4001 */           getHDDForSEODescription(entityItem, arrayOfTreeSet[6]);
/*  4002 */           getMECHPKGForSEODescription(entityItem, arrayOfTreeSet[7]);
/*  4003 */           if (bool)
/*       */           {
/*  4005 */             getBaysAvailForSEODescription(vector2, arrayOfTreeSet[8]);
/*       */           }
/*  4007 */           getSysMgAdapterForSEODescription(entityItem, arrayOfTreeSet[9]);
/*  4008 */           getNICForSEODescription(entityItem, arrayOfTreeSet[10]);
/*  4009 */           getOptcalDvcForSEODescription(entityItem, vector);
/*  4010 */           getPwrSupplyForSEODescription(entityItem, arrayOfTreeSet[12]);
/*  4011 */           bool = false;
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  4016 */     getOptcalDvcForSEODescription(vector, arrayOfTreeSet[11]);
/*  4017 */     if (arrayOfTreeSet[0].size() > 1)
/*       */     {
/*  4019 */       arrayOfTreeSet[0].remove(setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     }
/*  4021 */     if (arrayOfTreeSet[1].size() > 1)
/*       */     {
/*  4023 */       arrayOfTreeSet[1].remove(setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     }
/*  4025 */     if (arrayOfTreeSet[2].size() > 1)
/*       */     {
/*  4027 */       arrayOfTreeSet[2].remove(setString(" ", 17));
/*       */     }
/*  4029 */     if (arrayOfTreeSet[3].size() > 1)
/*       */     {
/*  4031 */       arrayOfTreeSet[3].remove(setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17) + "<:><::>");
/*       */     }
/*  4033 */     if (arrayOfTreeSet[3].size() > 1)
/*       */     {
/*  4035 */       arrayOfTreeSet[3] = combineInfo1(arrayOfTreeSet[3]);
/*       */     }
/*  4037 */     if (arrayOfTreeSet[4].size() > 1)
/*       */     {
/*  4039 */       arrayOfTreeSet[4].remove(setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     }
/*  4041 */     if (arrayOfTreeSet[5].size() > 1)
/*       */     {
/*  4043 */       arrayOfTreeSet[5].remove(setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     }
/*  4045 */     if (arrayOfTreeSet[6].size() > 1)
/*       */     {
/*  4047 */       arrayOfTreeSet[6].remove(setString(" ", 17));
/*       */     }
/*  4049 */     if (arrayOfTreeSet[7].size() > 1)
/*       */     {
/*  4051 */       arrayOfTreeSet[7].remove(setString(" ", 17) + "<:><::>");
/*       */     }
/*  4053 */     if (arrayOfTreeSet[7].size() > 1)
/*       */     {
/*  4055 */       arrayOfTreeSet[7] = combineInfo2(arrayOfTreeSet[7]);
/*       */     }
/*  4057 */     if (arrayOfTreeSet[8].size() > 1)
/*       */     {
/*  4059 */       arrayOfTreeSet[8].remove("<::>");
/*       */     }
/*  4061 */     if (arrayOfTreeSet[9].size() > 1)
/*       */     {
/*  4063 */       arrayOfTreeSet[9].remove(setString(" ", 17));
/*       */     }
/*  4065 */     if (arrayOfTreeSet[10].size() > 1)
/*       */     {
/*  4067 */       arrayOfTreeSet[10].remove(setString(" ", 17));
/*       */     }
/*  4069 */     if (arrayOfTreeSet[11].size() > 1)
/*       */     {
/*  4071 */       arrayOfTreeSet[11].remove(setString(" ", 22) + "<::>" + setString(" ", 17));
/*       */     }
/*  4073 */     if (arrayOfTreeSet[12].size() > 1)
/*       */     {
/*  4075 */       arrayOfTreeSet[12].remove(setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     }
/*       */     
/*  4078 */     this.seoDescription_TM.put(paramString, arrayOfTreeSet);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getPROCForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4098 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREPROC", "PROC");
/*       */     
/*  4100 */     if (vector.size() > 0) {
/*       */       
/*  4102 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4104 */         EntityItem entityItem = vector.get(b);
/*  4105 */         String str1 = getAttributeValue(entityItem, "PROCSPEDMKTGNAM", "", "", false);
/*  4106 */         str1 = str1.trim();
/*  4107 */         if (str1.length() < 17)
/*       */         {
/*  4109 */           str1 = setString(str1, 17);
/*       */         }
/*  4111 */         String str2 = getAttributeValue(entityItem, "CLOCKRATE", "", "", false);
/*  4112 */         str2 = str2.trim();
/*  4113 */         String str3 = getAttributeValue(entityItem, "CLOCKRATEUNIT", "", "", false);
/*  4114 */         str3 = str3.trim();
/*  4115 */         String str4 = str2 + " " + str3;
/*  4116 */         str4 = setString(str4, 17);
/*  4117 */         String str5 = getAttributeValue(entityItem, "PROCBUSSPED", "", "", false);
/*  4118 */         str5 = str5.trim();
/*  4119 */         String str6 = getAttributeValue(entityItem, "PROCBUSSPEDUNIT", "", "", false);
/*  4120 */         str6 = str6.trim();
/*  4121 */         String str7 = str5 + " " + str6;
/*  4122 */         str7 = setString(str7, 17);
/*  4123 */         String str8 = getAttributeValue(entityItem, "PROCCONFIG", "", "", false);
/*  4124 */         str8 = str8.trim();
/*  4125 */         if (str8.length() < 17)
/*       */         {
/*  4127 */           str8 = setString(str8, 17);
/*       */         }
/*       */         
/*  4130 */         paramTreeSet.add(str1 + "<:>" + str4 + "<:>" + str7 + "<:>" + str8);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4135 */       paramTreeSet.add(setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     } 
/*       */     
/*  4138 */     vector.clear();
/*  4139 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getDDForSEODescription(Vector<EntityItem> paramVector, TreeSet<String> paramTreeSet) {
/*  4160 */     if (paramVector.size() > 0) {
/*       */       
/*  4162 */       EntityItem entityItem = paramVector.get(0);
/*       */       
/*  4164 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "WWSEODERIVEDDATA", "DERIVEDDATA");
/*  4165 */       if (vector.size() > 0)
/*       */       {
/*  4167 */         EntityItem entityItem1 = vector.get(0);
/*       */         
/*  4169 */         String str1 = getAttributeValue(entityItem1, "NOOFPROCSTD", "", "", false);
/*  4170 */         str1 = str1.trim();
/*  4171 */         str1 = setString(str1, 17);
/*  4172 */         String str2 = getAttributeValue(entityItem1, "TOTL2CACHESTD", "", "", false);
/*  4173 */         str2 = str2.trim();
/*  4174 */         String str3 = getAttributeValue(entityItem1, "TOTL2CACHESTDUNIT", "", "", false);
/*  4175 */         str3 = str3.trim();
/*  4176 */         String str4 = str2 + " " + str3;
/*  4177 */         str4 = setString(str4, 17);
/*  4178 */         String str5 = getAttributeValue(entityItem1, "TOTAVAILBAY", "", "", false);
/*  4179 */         str5 = str5.trim();
/*  4180 */         str5 = setString(str5, 17);
/*  4181 */         String str6 = getAttributeValue(entityItem1, "TOTAVAILCARDSLOT", "", "", false);
/*  4182 */         str6 = str6.trim();
/*  4183 */         str6 = setString(str6, 17);
/*       */         
/*  4185 */         paramTreeSet.add(str1 + "<:>" + str4 + "<:>" + str5 + "<:>" + str6);
/*       */       }
/*       */       else
/*       */       {
/*  4189 */         paramTreeSet.add(setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4194 */       paramTreeSet.add(setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getMemoryForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4210 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREMEMORY", "MEMORY");
/*       */     
/*  4212 */     if (vector.size() > 0) {
/*       */       
/*  4214 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4216 */         EntityItem entityItem = vector.get(b);
/*  4217 */         String str1 = getAttributeValue(entityItem, "MEMRYCAP", "", "", false);
/*  4218 */         str1 = str1.trim();
/*  4219 */         String str2 = getAttributeValue(entityItem, "CAPUNIT", "", "", false);
/*  4220 */         str2 = str2.trim();
/*  4221 */         String str3 = str1 + " " + str2;
/*  4222 */         str3 = setString(str3, 17);
/*       */         
/*  4224 */         paramTreeSet.add(str3);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4229 */       paramTreeSet.add(setString(" ", 17));
/*       */     } 
/*       */     
/*  4232 */     vector.clear();
/*  4233 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getPlanarForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4251 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREPLANAR", "PLANAR");
/*       */     
/*  4253 */     if (vector.size() > 0) {
/*       */       
/*  4255 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4257 */         EntityItem entityItem = vector.get(b);
/*  4258 */         String str1 = getAttributeValue(entityItem, "RAMSOCKETTOT", "", "", false);
/*  4259 */         str1 = str1.trim();
/*  4260 */         if (str1.length() < 17)
/*       */         {
/*  4262 */           str1 = setString(str1, 17);
/*       */         }
/*  4264 */         String str2 = getAttributeValue(entityItem, "MEMRYRAMMAX", "", "", false);
/*  4265 */         str2 = str2.trim();
/*  4266 */         String str3 = getAttributeValue(entityItem, "MEMRYRAMMAXUNIT", "", "", false);
/*  4267 */         str3 = str3.trim();
/*  4268 */         String str4 = str2 + " " + str3;
/*  4269 */         str4 = setString(str4, 17);
/*  4270 */         String str5 = getAttributeValue(entityItem, "TOTCARDSLOT", "", "", false);
/*  4271 */         str5 = str5.trim();
/*  4272 */         str5 = setString(str5, 17);
/*  4273 */         String str6 = getPlanarSlotInfo(entityItem);
/*       */         
/*  4275 */         paramTreeSet.add(str1 + "<:>" + str4 + "<:>" + str5 + "<:>" + str6);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4280 */       paramTreeSet.add(setString(" ", 17) + "<:>" + setString(" ", 17) + "<:>" + setString(" ", 17) + "<:><::>");
/*       */     } 
/*       */     
/*  4283 */     vector.clear();
/*  4284 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String getPlanarSlotInfo(EntityItem paramEntityItem) {
/*  4296 */     String str = "";
/*       */     
/*  4298 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "PLANARSLOT", "SLOT");
/*       */     
/*  4300 */     if (vector.size() > 0) {
/*       */       
/*  4302 */       boolean bool = true;
/*       */       
/*  4304 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  4306 */         EntityItem entityItem = vector.get(b);
/*  4307 */         String str1 = getAttributeValue(entityItem, "SLOTTYPE", "", "", false);
/*  4308 */         str1 = str1.trim();
/*  4309 */         if (str1.length() < 21)
/*       */         {
/*  4311 */           str1 = setString(str1, 21);
/*       */         }
/*  4313 */         String str2 = getAttributeValue(entityItem, "SLOTTOT", "", "", false);
/*  4314 */         str2 = str2.trim();
/*  4315 */         str2 = setString(str2, 17);
/*  4316 */         if (bool)
/*       */         {
/*  4318 */           str = str1 + "<::>" + str2;
/*  4319 */           bool = false;
/*       */         }
/*       */         else
/*       */         {
/*  4323 */           str = str + "<::>" + str1 + "<::>" + str2;
/*       */         }
/*       */       
/*       */       } 
/*       */     } else {
/*       */       
/*  4329 */       str = "<::>";
/*       */     } 
/*       */     
/*  4332 */     return str;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getGrphAdapterForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4348 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREGRPHADAPTER", "GRPHADAPTER");
/*       */     
/*  4350 */     if (vector.size() > 0) {
/*       */       
/*  4352 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4354 */         EntityItem entityItem = vector.get(b);
/*  4355 */         String str1 = getAttributeValue(entityItem, "GRPHADPTRTYPE", "", "", false);
/*  4356 */         str1 = str1.trim();
/*  4357 */         if (str1.length() < 17)
/*       */         {
/*  4359 */           str1 = setString(str1, 17);
/*       */         }
/*  4361 */         String str2 = getAttributeValue(entityItem, "RAMMAX", "", "", false);
/*  4362 */         str2 = str2.trim();
/*  4363 */         String str3 = getAttributeValue(entityItem, "RAMMAXUNIT", "", "", false);
/*  4364 */         str3 = str3.trim();
/*  4365 */         String str4 = str2 + " " + str3;
/*  4366 */         str4 = setString(str4, 17);
/*       */         
/*  4368 */         paramTreeSet.add(str1 + "<:>" + str4);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4373 */       paramTreeSet.add(setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     } 
/*       */     
/*  4376 */     vector.clear();
/*  4377 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getHDCForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4389 */     String str1 = setString(" ", 17);
/*  4390 */     String str2 = setString(" ", 17);
/*       */     
/*  4392 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREHDC", "HDC");
/*       */     
/*  4394 */     if (vector.size() > 0) {
/*       */       
/*  4396 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4398 */         EntityItem entityItem = vector.get(b);
/*  4399 */         String str = getAttributeFlagValue(entityItem, "CNTRLR");
/*  4400 */         if (null == str) {
/*       */           
/*  4402 */           paramTreeSet.add(setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */         }
/*       */         else {
/*       */           
/*  4406 */           str = str.trim();
/*  4407 */           if (str.equals("0020")) {
/*       */             
/*  4409 */             str1 = getAttributeValue(entityItem, "CNTRLR", "", "", false);
/*  4410 */             str1 = str1.trim();
/*  4411 */             if (str1.length() < 17)
/*       */             {
/*  4413 */               str1 = setString(str1, 17);
/*       */             }
/*       */           }
/*       */           else {
/*       */             
/*  4418 */             str2 = getAttributeValue(entityItem, "CNTRLR", "", "", false);
/*  4419 */             str2 = str2.trim();
/*  4420 */             if (str2.length() < 17)
/*       */             {
/*  4422 */               str2 = setString(str2, 17);
/*       */             }
/*       */           } 
/*       */         } 
/*       */         
/*  4427 */         paramTreeSet.add(str2 + "<:>" + str1);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4432 */       paramTreeSet.add(setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     } 
/*       */     
/*  4435 */     vector.clear();
/*  4436 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getHDDForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4452 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREHDD", "HDD");
/*       */     
/*  4454 */     if (vector.size() > 0) {
/*       */       
/*  4456 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4458 */         EntityItem entityItem = vector.get(b);
/*  4459 */         String str1 = getAttributeValue(entityItem, "INTRFC", "", "", false);
/*  4460 */         str1 = str1.trim();
/*  4461 */         String str2 = getAttributeValue(entityItem, "HDDSZE", "", "", false);
/*  4462 */         str2 = str2.trim();
/*  4463 */         String str3 = getAttributeValue(entityItem, "HDDSZEUNIT", "", "", false);
/*  4464 */         str3 = str3.trim();
/*  4465 */         String str4 = str1 + " " + str2 + " " + str3;
/*  4466 */         str4 = str4.trim();
/*       */         
/*  4468 */         if (str4.length() < 17)
/*       */         {
/*  4470 */           str4 = setString(str4, 17);
/*       */         }
/*       */         
/*  4473 */         paramTreeSet.add(str4);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4478 */       paramTreeSet.add(setString(" ", 17));
/*       */     } 
/*       */     
/*  4481 */     vector.clear();
/*  4482 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getMECHPKGForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4496 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREMECHPKG", "MECHPKG");
/*       */     
/*  4498 */     if (vector.size() > 0) {
/*       */       
/*  4500 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4502 */         EntityItem entityItem = vector.get(b);
/*  4503 */         String str1 = getAttributeValue(entityItem, "TOTBAY", "", "", false);
/*  4504 */         str1 = str1.trim();
/*  4505 */         str1 = setString(str1, 17);
/*  4506 */         String str2 = getMechpkgBayInfo(entityItem);
/*       */         
/*  4508 */         paramTreeSet.add(str1 + "<:>" + str2);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4513 */       paramTreeSet.add(setString(" ", 17) + "<:><::>");
/*       */     } 
/*       */     
/*  4516 */     vector.clear();
/*  4517 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String getMechpkgBayInfo(EntityItem paramEntityItem) {
/*  4529 */     String str = "";
/*       */     
/*  4531 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "MECHPKGBAY", "BAY");
/*       */     
/*  4533 */     if (vector.size() > 0) {
/*       */       
/*  4535 */       boolean bool = true;
/*       */       
/*  4537 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  4539 */         EntityItem entityItem = vector.get(b);
/*  4540 */         String str1 = getAttributeValue(entityItem, "BAYTYPE", "", "", false);
/*  4541 */         str1 = str1.trim();
/*  4542 */         if (str1.length() < 21)
/*       */         {
/*  4544 */           str1 = setString(str1, 21);
/*       */         }
/*  4546 */         String str2 = getAttributeValue(entityItem, "BAYTOT", "", "", false);
/*  4547 */         str2 = str2.trim();
/*  4548 */         str2 = setString(str2, 17);
/*  4549 */         if (bool)
/*       */         {
/*  4551 */           str = str1 + "<::>" + str2;
/*  4552 */           bool = false;
/*       */         }
/*       */         else
/*       */         {
/*  4556 */           str = str + "<::>" + str1 + "<::>" + str2;
/*       */         }
/*       */       
/*       */       } 
/*       */     } else {
/*       */       
/*  4562 */       str = "<::>";
/*       */     } 
/*       */     
/*  4565 */     return str;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getBaysAvailForSEODescription(Vector<EntityItem> paramVector, TreeSet<String> paramTreeSet) {
/*  4582 */     if (paramVector.size() > 0) {
/*       */       
/*  4584 */       EntityItem entityItem = paramVector.get(0);
/*  4585 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "WWSEOBAYSAVAIL", "BAYSAVAIL");
/*  4586 */       if (vector.size() > 0) {
/*       */         
/*  4588 */         for (byte b = 0; b < vector.size(); b++)
/*       */         {
/*  4590 */           EntityItem entityItem1 = vector.get(b);
/*  4591 */           String str1 = getAttributeValue(entityItem1, "BAYTYPE", "", "", false);
/*  4592 */           str1 = str1.trim();
/*  4593 */           if (str1.length() < 21)
/*       */           {
/*  4595 */             str1 = setString(str1, 21);
/*       */           }
/*  4597 */           String str2 = getAttributeValue(entityItem1, "BAYAVAIL", "", "", false);
/*  4598 */           str2 = str2.trim();
/*  4599 */           str2 = setString(str2, 17);
/*  4600 */           paramTreeSet.add(str1 + "<::>" + str2);
/*       */         }
/*       */       
/*       */       } else {
/*       */         
/*  4605 */         paramTreeSet.add("<::>");
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4610 */       paramTreeSet.add("<::>");
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getSysMgAdapterForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4624 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATURESYSMGADAPTR", "SYSMGADPTR");
/*       */     
/*  4626 */     if (vector.size() > 0) {
/*       */       
/*  4628 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4630 */         EntityItem entityItem = vector.get(b);
/*  4631 */         String str = getAttributeValue(entityItem, "SYSMGFEAT", ", ", "", false);
/*  4632 */         str = str.trim();
/*  4633 */         if (str.length() < 17)
/*       */         {
/*  4635 */           str = setString(str, 17);
/*       */         }
/*       */         
/*  4638 */         paramTreeSet.add(str);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4643 */       paramTreeSet.add(setString(" ", 17));
/*       */     } 
/*       */     
/*  4646 */     vector.clear();
/*  4647 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getNICForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4660 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATURENIC", "NIC");
/*       */     
/*  4662 */     if (vector.size() > 0) {
/*       */       
/*  4664 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4666 */         EntityItem entityItem = vector.get(b);
/*  4667 */         String str = getAttributeValue(entityItem, "NICDESC", ", ", "", false);
/*  4668 */         str = str.trim();
/*  4669 */         if (str.length() < 17)
/*       */         {
/*  4671 */           str = setString(str, 17);
/*       */         }
/*       */         
/*  4674 */         paramTreeSet.add(str);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4679 */       paramTreeSet.add(setString(" ", 17));
/*       */     } 
/*       */     
/*  4682 */     vector.clear();
/*  4683 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getOptcalDvcForSEODescription(EntityItem paramEntityItem, Vector<String> paramVector) {
/*  4696 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREOPTCALDVC", "OPTCALDVC");
/*       */     
/*  4698 */     for (byte b = 0; b < vector.size(); b++) {
/*       */       
/*  4700 */       EntityItem entityItem = vector.get(b);
/*  4701 */       String str = getAttributeValue(entityItem, "OPTCALDRIVETYPE", "", "", false);
/*  4702 */       str = str.trim();
/*       */       
/*  4704 */       if (str.length() < 22)
/*       */       {
/*  4706 */         str = setString(str, 22);
/*       */       }
/*       */       
/*  4709 */       paramVector.add(str);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getOptcalDvcForSEODescription(Vector<String> paramVector, TreeSet<String> paramTreeSet) {
/*  4721 */     TreeSet treeSet = new TreeSet();
/*       */     
/*  4723 */     if (paramVector.size() > 0) {
/*       */       
/*  4725 */       for (byte b = 0; b < paramVector.size(); b++)
/*       */       {
/*  4727 */         treeSet.add(paramVector.get(b));
/*       */       }
/*       */       
/*  4730 */       Iterator<String> iterator = treeSet.iterator();
/*  4731 */       while (iterator.hasNext())
/*       */       {
/*  4733 */         String str = iterator.next();
/*  4734 */         byte b1 = 0;
/*  4735 */         for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/*       */           
/*  4737 */           String str1 = paramVector.get(b2);
/*  4738 */           if (str.equals(str1))
/*       */           {
/*  4740 */             b1++;
/*       */           }
/*       */         } 
/*       */         
/*  4744 */         paramTreeSet.add(str + "<::>" + setString(Integer.toString(b1), 17));
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4749 */       paramTreeSet.add(setString(" ", 22) + "<::>" + setString(" ", 17));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getPwrSupplyForSEODescription(EntityItem paramEntityItem, TreeSet<String> paramTreeSet) {
/*  4766 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "FEATUREPWRSUPPLY", "PWRSUPPLY");
/*       */     
/*  4768 */     if (vector.size() > 0) {
/*       */       
/*  4770 */       for (byte b = 0; b < vector.size(); b++)
/*       */       {
/*  4772 */         EntityItem entityItem = vector.get(b);
/*  4773 */         String str1 = getAttributeValue(entityItem, "POWR", "", "", false);
/*  4774 */         str1 = str1.trim();
/*  4775 */         String str2 = getAttributeValue(entityItem, "POWRUNIT", "", "", false);
/*  4776 */         str2 = str2.trim();
/*  4777 */         String str3 = str1 + " " + str2;
/*  4778 */         str3 = setString(str3, 17);
/*  4779 */         String str4 = getAttributeValue(entityItem, "POWRSUPLYTYPE", "", "", false);
/*  4780 */         str4 = str4.trim();
/*  4781 */         if (str4.length() < 17)
/*       */         {
/*  4783 */           str4 = setString(str4, 17);
/*       */         }
/*       */         
/*  4786 */         paramTreeSet.add(str3 + "<:>" + str4);
/*       */       }
/*       */     
/*       */     } else {
/*       */       
/*  4791 */       paramTreeSet.add(setString(" ", 17) + "<:>" + setString(" ", 17));
/*       */     } 
/*       */     
/*  4794 */     vector.clear();
/*  4795 */     vector = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private TreeSet combineInfo1(TreeSet paramTreeSet) {
/*  4804 */     TreeSet<String> treeSet1 = new TreeSet();
/*  4805 */     TreeSet<String> treeSet2 = new TreeSet();
/*       */     
/*  4807 */     Iterator<String> iterator = paramTreeSet.iterator();
/*  4808 */     while (iterator.hasNext()) {
/*       */       
/*  4810 */       String str1 = iterator.next();
/*  4811 */       String str2 = parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 3, "<:>");
/*  4812 */       treeSet1.add(str2);
/*       */     } 
/*       */     
/*  4815 */     iterator = treeSet1.iterator();
/*  4816 */     while (iterator.hasNext()) {
/*       */       
/*  4818 */       String str1 = iterator.next();
/*       */       
/*  4820 */       String str2 = combineInfo1(str1, paramTreeSet);
/*  4821 */       treeSet2.add(str2);
/*       */     } 
/*       */ 
/*       */     
/*  4825 */     paramTreeSet.clear();
/*  4826 */     paramTreeSet = null;
/*       */     
/*  4828 */     return treeSet2;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String combineInfo1(String paramString, TreeSet paramTreeSet) {
/*  4838 */     String str = "";
/*       */     
/*  4840 */     Iterator<String> iterator = paramTreeSet.iterator();
/*  4841 */     while (iterator.hasNext()) {
/*       */       
/*  4843 */       String str1 = iterator.next();
/*  4844 */       String str2 = parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 3, "<:>");
/*  4845 */       if (paramString.equals(str2)) {
/*       */         
/*  4847 */         String str3 = parseString(str1, 4, "<:>");
/*  4848 */         int i = countDelims(str3, "<::>");
/*  4849 */         String str4 = "";
/*  4850 */         String str5 = "";
/*       */         
/*  4852 */         i++;
/*       */         
/*  4854 */         if (i > 1)
/*       */         {
/*  4856 */           for (byte b = 1; b <= i; b++) {
/*       */             
/*  4858 */             if (b % 2 != 0) {
/*       */               
/*  4860 */               str4 = parseString(str3, b, "<::>");
/*       */             }
/*       */             else {
/*       */               
/*  4864 */               str5 = parseString(str3, b, "<::>");
/*  4865 */               if (!str4.equals("") && !str5.equals(""))
/*       */               {
/*  4867 */                 if (str.equals("")) {
/*       */                   
/*  4869 */                   str = str4 + "<::>" + str5;
/*       */                 }
/*       */                 else {
/*       */                   
/*  4873 */                   str = str + "<::>" + str4 + "<::>" + str5;
/*       */                 } 
/*       */               }
/*       */             } 
/*       */           } 
/*       */         }
/*       */       } 
/*       */     } 
/*       */     
/*  4882 */     str = paramString + "<:>" + str;
/*       */     
/*  4884 */     return str;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private TreeSet combineInfo2(TreeSet paramTreeSet) {
/*  4893 */     TreeSet<String> treeSet1 = new TreeSet();
/*  4894 */     TreeSet<String> treeSet2 = new TreeSet();
/*       */     
/*  4896 */     Iterator<String> iterator = paramTreeSet.iterator();
/*  4897 */     while (iterator.hasNext()) {
/*       */       
/*  4899 */       String str1 = iterator.next();
/*  4900 */       String str2 = parseString(str1, 1, "<:>");
/*  4901 */       treeSet1.add(str2);
/*       */     } 
/*       */     
/*  4904 */     iterator = treeSet1.iterator();
/*  4905 */     while (iterator.hasNext()) {
/*       */       
/*  4907 */       String str1 = iterator.next();
/*       */       
/*  4909 */       String str2 = combineInfo2(str1, paramTreeSet);
/*  4910 */       treeSet2.add(str2);
/*       */     } 
/*       */ 
/*       */     
/*  4914 */     paramTreeSet.clear();
/*  4915 */     paramTreeSet = null;
/*       */     
/*  4917 */     return treeSet2;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String combineInfo2(String paramString, TreeSet paramTreeSet) {
/*  4927 */     String str = "";
/*       */     
/*  4929 */     Iterator<String> iterator = paramTreeSet.iterator();
/*  4930 */     while (iterator.hasNext()) {
/*       */       
/*  4932 */       String str1 = iterator.next();
/*  4933 */       String str2 = parseString(str1, 1, "<:>");
/*  4934 */       if (paramString.equals(str2)) {
/*       */         
/*  4936 */         String str3 = parseString(str1, 2, "<:>");
/*  4937 */         int i = countDelims(str3, "<::>");
/*  4938 */         String str4 = "";
/*  4939 */         String str5 = "";
/*       */         
/*  4941 */         i++;
/*       */         
/*  4943 */         if (i > 1)
/*       */         {
/*  4945 */           for (byte b = 1; b <= i; b++) {
/*       */             
/*  4947 */             if (b % 2 != 0) {
/*       */               
/*  4949 */               str4 = parseString(str3, b, "<::>");
/*       */             }
/*       */             else {
/*       */               
/*  4953 */               str5 = parseString(str3, b, "<::>");
/*  4954 */               if (!str4.equals("") && !str5.equals(""))
/*       */               {
/*  4956 */                 if (str.equals("")) {
/*       */                   
/*  4958 */                   str = str4 + "<::>" + str5;
/*       */                 }
/*       */                 else {
/*       */                   
/*  4962 */                   str = str + "<::>" + str4 + "<::>" + str5;
/*       */                 } 
/*       */               }
/*       */             } 
/*       */           } 
/*       */         }
/*       */       } 
/*       */     } 
/*       */     
/*  4971 */     str = paramString + "<:>" + str;
/*       */     
/*  4973 */     return str;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void merge(String paramString1, String paramString2, TreeSet<String> paramTreeSet, TreeSet[] paramArrayOfTreeSet) {
/*  4985 */     int i = paramArrayOfTreeSet[0].size();
/*  4986 */     int j = paramArrayOfTreeSet[1].size();
/*  4987 */     int k = paramArrayOfTreeSet[2].size();
/*  4988 */     int m = paramArrayOfTreeSet[3].size();
/*       */     
/*  4990 */     Object[] arrayOfObject1 = paramArrayOfTreeSet[0].toArray();
/*  4991 */     Object[] arrayOfObject2 = paramArrayOfTreeSet[1].toArray();
/*  4992 */     Object[] arrayOfObject3 = paramArrayOfTreeSet[2].toArray();
/*  4993 */     Object[] arrayOfObject4 = paramArrayOfTreeSet[3].toArray();
/*       */     
/*  4995 */     String str = "";
/*       */     
/*  4997 */     int n = i;
/*       */     
/*  4999 */     if (j > n)
/*       */     {
/*  5001 */       n = j;
/*       */     }
/*  5003 */     if (k > n)
/*       */     {
/*  5005 */       n = k;
/*       */     }
/*  5007 */     if (m > n)
/*       */     {
/*  5009 */       n = m;
/*       */     }
/*       */     
/*  5012 */     if (j > 0) {
/*       */       
/*  5014 */       str = (String)arrayOfObject2[0];
/*       */     }
/*       */     else {
/*       */       
/*  5018 */       str = "   <:>  ";
/*       */     } 
/*       */     
/*  5021 */     for (byte b = 0; b < n; b++) {
/*       */       
/*  5023 */       String str1 = paramString1 + "<:>" + paramString2;
/*  5024 */       if (b < i) {
/*       */         
/*  5026 */         str1 = str1 + "<:>" + (String)arrayOfObject1[b];
/*       */       }
/*       */       else {
/*       */         
/*  5030 */         str1 = str1 + "<:>   <:>   <:>  <:>  ";
/*       */       } 
/*       */       
/*  5033 */       if (b < j) {
/*       */         
/*  5035 */         str1 = str1 + "<:>" + (String)arrayOfObject2[b];
/*       */       }
/*       */       else {
/*       */         
/*  5039 */         str1 = str1 + "<:>   <:>  ";
/*       */       } 
/*       */       
/*  5042 */       if (b < k) {
/*       */         
/*  5044 */         str1 = str1 + "<:>" + (String)arrayOfObject3[b];
/*       */       }
/*       */       else {
/*       */         
/*  5048 */         str1 = str1 + "<:>" + setString(" ", 12);
/*       */       } 
/*       */       
/*  5051 */       if (b < m) {
/*       */         
/*  5053 */         str1 = str1 + "<:>" + (String)arrayOfObject4[b];
/*       */       }
/*       */       else {
/*       */         
/*  5057 */         str1 = str1 + "<:>" + setString(" ", 21);
/*       */       } 
/*       */       
/*  5060 */       paramTreeSet.add(str1);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void retrieveAnswer(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  5073 */     paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:PRODNUM.txt: FOR :Product Number-->" + NEWLINE);
/*  5074 */     retrieveProductNumber(paramBoolean, paramStringBuffer);
/*  5075 */     paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:FEATCONV.txt: FOR :Conversions-->" + NEWLINE);
/*  5076 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  5077 */     retrievePNMTMConversions(paramBoolean, paramStringBuffer);
/*  5078 */     retrievePNModelConversions(paramBoolean, paramStringBuffer);
/*  5079 */     if (1 == this.format) {
/*       */       
/*  5081 */       if (this.annType.equals("new"))
/*       */       {
/*  5083 */         retrievePNFeatureConversionsFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*  5085 */       else if (this.annType.equals("withdraw"))
/*       */       {
/*  5087 */         retrievePNFeatureConversionsForWithdrawFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*       */     
/*  5090 */     } else if (2 == this.format || 3 == this.format) {
/*       */       
/*  5092 */       if (this.annType.equals("new")) {
/*       */         
/*  5094 */         retrievePNFeatureConversionsFormat2(paramBoolean, paramStringBuffer);
/*       */       }
/*  5096 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*  5098 */         retrievePNFeatureConversionsForWithdrawFormat2(paramBoolean, paramStringBuffer);
/*       */       } 
/*       */     } 
/*  5101 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */     
/*  5103 */     if (this.annType.equals("new")) {
/*       */ 
/*       */       
/*  5106 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:CHARGES.txt: FOR :Charges-->" + NEWLINE);
/*  5107 */       retrieveCharges(paramBoolean, paramStringBuffer);
/*  5108 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:XCHARGES.txt: FOR :XCharges-->" + NEWLINE);
/*  5109 */       retrieveXCharges(paramBoolean, paramStringBuffer);
/*       */       
/*  5111 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:SALESMAN.txt: FOR :Sales Manual-->" + NEWLINE);
/*  5112 */       retrieveSalesManual(paramBoolean, paramStringBuffer);
/*       */       
/*  5114 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:EXTDEVICE.txt: FOR :Supported Devices-->" + NEWLINE);
/*  5115 */       retrieveSupportedDevices(paramBoolean, paramStringBuffer);
/*       */       
/*  5117 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:MATRIX.txt: FOR :Feature Matrix-->" + NEWLINE);
/*  5118 */       retrieveFeatureMatrix(paramBoolean, paramStringBuffer);
/*  5119 */       if (this.brand.equals("xSeries")) {
/*       */         
/*  5121 */         paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:SEOTABLE.txt: FOR :SEO Table-->" + NEWLINE);
/*  5122 */         retrieveSEOTable(paramStringBuffer);
/*  5123 */         paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:SEODESC.txt: FOR :SEO Description-->" + NEWLINE);
/*  5124 */         retrieveSEODescription(paramStringBuffer);
/*       */       } 
/*       */     } 
/*       */     
/*  5128 */     paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:RFAERROR.txt: FOR :Matrix RFA Error-->" + NEWLINE);
/*  5129 */     retrieveFeatureMatrixError(paramStringBuffer);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveProductNumber(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  5140 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  5141 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  5142 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */ 
/*       */ 
/*       */     
/*  5146 */     if (this.productNumber_NewModels_TM.size() + this.productNumber_NewFC_TM.size() + this.productNumber_ExistingFC_TM
/*  5147 */       .size() + this.productNumber_MTM_Conversions_TM.size() + this.productNumber_Model_Conversions_TM
/*  5148 */       .size() + this.productNumber_Feature_Conversions_TM.size() + this.productNumber_NewModels_NewFC_TM
/*  5149 */       .size() + this.productNumber_NewModels_ExistingFC_TM.size() + this.productNumber_ExistingModels_NewFC_TM
/*  5150 */       .size() + this.productNumber_ExistingModels_ExistingFC_TM.size() > 0) {
/*       */       
/*  5152 */       log("annType = " + this.annType);
/*  5153 */       log("productNumber_NewModels_TM.size() = " + this.productNumber_NewModels_TM.size());
/*  5154 */       log("productNumber_NewFC_TM.size() = " + this.productNumber_NewFC_TM.size());
/*  5155 */       log("productNumber_ExistingFC_TM.size() = " + this.productNumber_ExistingFC_TM.size());
/*  5156 */       log("productNumber_NewModels_NewFC_TM.size() = " + this.productNumber_NewModels_NewFC_TM.size());
/*  5157 */       log("productNumber_NewModels_ExistingFC_TM.size() = " + this.productNumber_NewModels_ExistingFC_TM.size());
/*  5158 */       log("productNumber_ExistingModels_NewFC_TM.size() = " + this.productNumber_ExistingModels_NewFC_TM.size());
/*  5159 */       log("productNumber_ExistingModels_ExistingFC_TM.size() = " + this.productNumber_ExistingModels_ExistingFC_TM.size());
/*       */       
/*  5161 */       if (1 == this.format) {
/*       */         
/*  5163 */         retrievePNNewModelsFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*  5165 */       else if (2 == this.format || 3 == this.format) {
/*       */         
/*  5167 */         retrievePNNewModelsFormat2(paramBoolean, paramStringBuffer);
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5176 */       if (this.annType.equals("withdraw"))
/*       */       {
/*  5178 */         if (1 == this.format)
/*       */         {
/*  5180 */           retrievePNFeaturesWithdrawFormat1(paramBoolean, paramStringBuffer);
/*       */         }
/*  5182 */         else if (2 == this.format || 3 == this.format)
/*       */         {
/*  5184 */           retrievePNFeaturesWithdrawFormat2(paramBoolean, paramStringBuffer);
/*       */         
/*       */         }
/*       */       
/*       */       }
/*  5189 */       else if (1 == this.format)
/*       */       {
/*  5191 */         retrievePNFeaturesFormat1(1, this.productNumber_NewFC_TM, paramBoolean, paramStringBuffer);
/*  5192 */         retrievePNFeaturesFormat1(2, this.productNumber_ExistingFC_TM, paramBoolean, paramStringBuffer);
/*       */       }
/*  5194 */       else if (2 == this.format || 3 == this.format)
/*       */       {
/*  5196 */         retrievePNFeaturesFormat2(1, 1, this.productNumber_NewModels_NewFC_TM, paramBoolean, paramStringBuffer);
/*  5197 */         retrievePNFeaturesFormat2(1, 2, this.productNumber_NewModels_ExistingFC_TM, paramBoolean, paramStringBuffer);
/*  5198 */         retrievePNFeaturesFormat2(2, 1, this.productNumber_ExistingModels_NewFC_TM, paramBoolean, paramStringBuffer);
/*  5199 */         retrievePNFeaturesFormat2(2, 2, this.productNumber_ExistingModels_ExistingFC_TM, paramBoolean, paramStringBuffer);
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/*  5205 */     else if (paramBoolean == true) {
/*       */       
/*  5207 */       paramStringBuffer.append(":p.No answer data found for Product Number section." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  5211 */       paramStringBuffer.append("<p>No answer data found for Product Number section.</p>" + NEWLINE);
/*       */     } 
/*       */     
/*  5214 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNNewModelsFormat1(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  5225 */     if (this.productNumber_NewModels_TM.size() > 0) {
/*       */       
/*  5227 */       String str1 = "";
/*  5228 */       String str2 = "";
/*       */       
/*  5230 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  5234 */         if (this.annType.equals("new")) {
/*       */           
/*  5236 */           paramStringBuffer.append(":h3.Models" + NEWLINE);
/*  5237 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  5238 */           paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*  5239 */           paramStringBuffer.append("                              Type         Model" + NEWLINE);
/*  5240 */           paramStringBuffer.append("Description                   Number       Number       CSU" + NEWLINE);
/*  5241 */           paramStringBuffer.append("----------------------------  ------       ------       ---" + NEWLINE);
/*       */         }
/*  5243 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  5245 */           paramStringBuffer.append(":h3.Model Withdrawals" + NEWLINE);
/*  5246 */           paramStringBuffer.append(":p.The following Machine Type Models are being withdrawn:" + NEWLINE);
/*  5247 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  5248 */           paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*  5249 */           paramStringBuffer.append("                              Type         Model" + NEWLINE);
/*  5250 */           paramStringBuffer.append("Description                   Number       Number" + NEWLINE);
/*  5251 */           paramStringBuffer.append("----------------------------  ------       ------" + NEWLINE);
/*       */         } 
/*       */         
/*  5254 */         Set set = this.productNumber_NewModels_TM.keySet();
/*  5255 */         Iterator<String> iterator = set.iterator();
/*  5256 */         while (iterator.hasNext()) {
/*       */           
/*  5258 */           String str = iterator.next();
/*  5259 */           str2 = (String)this.productNumber_NewModels_TM.get(str);
/*  5260 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  5262 */           paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  5263 */           paramStringBuffer.append("   ");
/*  5264 */           paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  5265 */           paramStringBuffer.append("         ");
/*  5266 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  5267 */           if (this.annType.equals("new")) {
/*       */             
/*  5269 */             paramStringBuffer.append("         ");
/*  5270 */             paramStringBuffer.append(parseString(str, 4, "<:>"));
/*       */           } 
/*  5272 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  5274 */           str1 = str2;
/*       */         } 
/*  5276 */         if (!str2.equals("WW"))
/*       */         {
/*  5278 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*  5280 */         if (paramBoolean == true)
/*       */         {
/*  5282 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */         }
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNNewModelsFormat2(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  5296 */     String str1 = "";
/*  5297 */     String str2 = "";
/*       */     
/*  5299 */     if (this.productNumber_NewModels_TM.size() > 0)
/*       */     {
/*  5301 */       if ((2 == this.format || 3 == this.format) && this.annType.equals("withdraw")) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5307 */         if (this.brand.equals("pSeries")) {
/*       */           
/*  5309 */           paramStringBuffer.append(":p.The following RS/6000 or pSeries Machine Type Models are being withdrawn:" + NEWLINE);
/*       */         }
/*  5311 */         else if (this.brand.equals("xSeries")) {
/*       */           
/*  5313 */           paramStringBuffer.append(":p.The following xSeries Machine Type Models are being withdrawn:" + NEWLINE);
/*       */ 
/*       */         
/*       */         }
/*  5317 */         else if (this.brand.equals("totalStorage")) {
/*       */           
/*  5319 */           paramStringBuffer.append(":p.The following Total Storage Machine Type Models are being withdrawn:" + NEWLINE);
/*       */         } 
/*       */ 
/*       */         
/*  5323 */         paramStringBuffer.append(".RH ON" + NEWLINE);
/*  5324 */         paramStringBuffer.append(".fo off" + NEWLINE);
/*  5325 */         paramStringBuffer.append(".in 2" + NEWLINE);
/*       */         
/*  5327 */         paramStringBuffer.append("Description                                            MT  Model" + NEWLINE);
/*  5328 */         paramStringBuffer.append("----------------------------------------------------- ---- -----" + NEWLINE);
/*  5329 */         paramStringBuffer.append(".fo on" + NEWLINE);
/*  5330 */         paramStringBuffer.append(".RH OFF" + NEWLINE);
/*  5331 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  5332 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*       */         
/*  5334 */         byte b = 0;
/*  5335 */         Set set = this.productNumber_NewModels_TM.keySet();
/*  5336 */         Iterator<String> iterator = set.iterator();
/*  5337 */         while (iterator.hasNext()) {
/*       */           
/*  5339 */           String str = iterator.next();
/*  5340 */           String[] arrayOfString = extractStringLines(parseString(str, 3, "<:>"), 53);
/*  5341 */           str2 = (String)this.productNumber_NewModels_TM.get(str);
/*  5342 */           b = 0;
/*  5343 */           setGeoTags2(str1, str2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*       */           do {
/*  5379 */             if (0 == b) {
/*       */               
/*  5381 */               paramStringBuffer.append(setString(arrayOfString[b], 53));
/*  5382 */               paramStringBuffer.append(" ");
/*  5383 */               paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  5384 */               paramStringBuffer.append("-");
/*  5385 */               paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  5386 */               paramStringBuffer.append(NEWLINE);
/*  5387 */               b++;
/*       */             }
/*       */             else {
/*       */               
/*  5391 */               paramStringBuffer.append(setString(arrayOfString[b], 53));
/*  5392 */               paramStringBuffer.append(NEWLINE);
/*  5393 */               b++;
/*       */             } 
/*  5395 */           } while (b < arrayOfString.length);
/*       */           
/*  5397 */           str1 = str2;
/*       */         } 
/*  5399 */         if (!str2.equals("WW"))
/*       */         {
/*  5401 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*  5403 */         if (paramBoolean == true) {
/*       */           
/*  5405 */           paramStringBuffer.append(":exmp." + NEWLINE);
/*  5406 */           paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */         } 
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeaturesFormat1(int paramInt, TreeMap paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  5422 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */ 
/*       */     
/*  5425 */     Set<String> set = paramTreeMap.keySet();
/*  5426 */     Iterator<String> iterator = set.iterator();
/*  5427 */     while (iterator.hasNext()) {
/*       */       
/*  5429 */       String str1 = iterator.next();
/*       */ 
/*       */       
/*  5432 */       String str2 = parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 5, "<:>") + "<:>" + parseString(str1, 6, "<:>") + "<:>" + parseString(str1, 7, "<:>") + "<:>" + parseString(str1, 8, "<:>");
/*  5433 */       treeMap.put(str2, paramTreeMap.get(str1));
/*       */     } 
/*       */     
/*  5436 */     if (treeMap.size() > 0) {
/*       */       
/*  5438 */       String str1 = "";
/*  5439 */       String str2 = "";
/*  5440 */       String str3 = "";
/*  5441 */       String str4 = "";
/*  5442 */       String str5 = "";
/*  5443 */       String str6 = "";
/*       */       
/*  5445 */       switch (paramInt) {
/*       */         
/*       */         case 1:
/*  5448 */           paramStringBuffer.append(":h3.New Features" + NEWLINE);
/*       */ 
/*       */           
/*  5451 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  5452 */           paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */           break;
/*       */         
/*       */         case 2:
/*  5456 */           paramStringBuffer.append(":h3.Existing Features" + NEWLINE);
/*       */ 
/*       */           
/*  5459 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  5460 */           paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */           break;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5467 */       if (1 == this.format) {
/*       */         
/*  5469 */         paramStringBuffer.append("                                                    Initial/" + NEWLINE);
/*  5470 */         paramStringBuffer.append("                                                    MES/" + NEWLINE);
/*  5471 */         paramStringBuffer.append("                                                    Both/" + NEWLINE);
/*  5472 */         paramStringBuffer.append("Description                   Type  Model  Feature  Support  CSU" + NEWLINE);
/*  5473 */         paramStringBuffer.append("----------------------------  ----  -----  -------  -------  ---" + NEWLINE);
/*  5474 */         set = treeMap.keySet();
/*  5475 */         iterator = set.iterator();
/*  5476 */         while (iterator.hasNext()) {
/*       */           
/*  5478 */           String str = iterator.next();
/*  5479 */           str2 = (String)treeMap.get(str);
/*  5480 */           str4 = parseString(str, 1, "<:>");
/*  5481 */           str6 = parseString(str, 2, "<:>");
/*  5482 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  5483 */           if (parseString(str, 8, "<:>").length() > 0)
/*       */           {
/*  5485 */             if (!str4.equals(str3)) {
/*       */               
/*  5487 */               String[] arrayOfString = getStringTokens(parseString(str, 8, "<:>"), NEWLINE);
/*  5488 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*       */                 
/*  5490 */                 if (arrayOfString[b].length() > 58) {
/*       */                   
/*  5492 */                   String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  5493 */                   for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                   {
/*  5495 */                     paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                   }
/*       */                 }
/*       */                 else {
/*       */                   
/*  5500 */                   paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */                 } 
/*       */               } 
/*       */             } 
/*       */           }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5513 */           paramStringBuffer.append(parseString(str, 4, "<:>"));
/*       */           
/*  5515 */           paramStringBuffer.append("  ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5525 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*       */           
/*  5527 */           paramStringBuffer.append("   ");
/*  5528 */           paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  5529 */           paramStringBuffer.append("     ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5539 */           paramStringBuffer.append(parseString(str, 1, "<:>"));
/*       */           
/*  5541 */           paramStringBuffer.append("   ");
/*  5542 */           paramStringBuffer.append(parseString(str, 5, "<:>"));
/*  5543 */           paramStringBuffer.append("  ");
/*  5544 */           paramStringBuffer.append(parseString(str, 6, "<:>"));
/*  5545 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  5547 */           str1 = str2;
/*  5548 */           str3 = str4;
/*  5549 */           str5 = str6;
/*       */         } 
/*  5551 */         if (!str2.equals("WW"))
/*       */         {
/*  5553 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */         
/*  5556 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5572 */       treeMap.clear();
/*  5573 */       treeMap = null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeaturesFormat2(int paramInt1, int paramInt2, TreeMap paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  5844 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */ 
/*       */     
/*  5847 */     Set<String> set = paramTreeMap.keySet();
/*  5848 */     Iterator<String> iterator = set.iterator();
/*  5849 */     while (iterator.hasNext()) {
/*       */       
/*  5851 */       String str1 = iterator.next();
/*       */ 
/*       */       
/*  5854 */       String str2 = parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 5, "<:>") + "<:>" + parseString(str1, 6, "<:>") + "<:>" + parseString(str1, 7, "<:>") + "<:>" + parseString(str1, 8, "<:>") + "<:>" + parseString(str1, 9, "<:>");
/*  5855 */       treeMap.put(str2, paramTreeMap.get(str1));
/*       */     } 
/*       */     
/*  5858 */     if (treeMap.size() > 0) {
/*       */       String[] arrayOfString1; byte b2;
/*  5860 */       String str1 = "";
/*  5861 */       String str2 = "";
/*  5862 */       String str3 = "";
/*  5863 */       String str4 = "";
/*  5864 */       String str5 = "";
/*  5865 */       String str6 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5870 */       byte b1 = 0;
/*       */ 
/*       */       
/*  5873 */       TreeSet<String> treeSet1 = new TreeSet();
/*  5874 */       TreeSet<String> treeSet2 = new TreeSet();
/*  5875 */       Set set1 = treeMap.keySet();
/*  5876 */       Iterator<String> iterator1 = set1.iterator();
/*  5877 */       while (iterator1.hasNext()) {
/*       */         
/*  5879 */         String str9 = iterator1.next();
/*  5880 */         String str10 = parseString(str9, 2, "<:>");
/*  5881 */         String str11 = parseString(str9, 3, "<:>");
/*  5882 */         String str12 = parseString(str9, 9, "<:>");
/*  5883 */         treeSet1.add(str10);
/*  5884 */         treeSet2.add(str10 + "<:>" + str11 + "<:>" + str12);
/*       */       } 
/*       */       
/*  5887 */       String str7 = "";
/*  5888 */       iterator1 = treeSet1.iterator();
/*  5889 */       while (iterator1.hasNext())
/*       */       {
/*  5891 */         str7 = str7 + (String)iterator1.next() + ", ";
/*       */       }
/*  5893 */       if (str7.length() > 1)
/*       */       {
/*  5895 */         str7 = str7.substring(0, str7.length() - 2);
/*       */       }
/*       */       
/*  5898 */       String str8 = "";
/*  5899 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  5901 */         if (1 == paramInt2)
/*       */         {
/*  5903 */           str8 = "of the IBM RS/6000 or pSeries " + str7 + " machine type:";
/*       */         }
/*  5905 */         else if (2 == paramInt2)
/*       */         {
/*  5907 */           str8 = "the " + str7 + " machine type:";
/*       */         }
/*       */       
/*  5910 */       } else if (this.brand.equals("xSeries")) {
/*       */         
/*  5912 */         if (1 == paramInt2)
/*       */         {
/*  5914 */           str8 = "of the IBM xSeries " + str7 + " machine type:";
/*       */         }
/*  5916 */         else if (2 == paramInt2)
/*       */         {
/*  5918 */           str8 = "the " + str7 + " machine type:";
/*       */ 
/*       */ 
/*       */         
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*  5928 */       else if (this.brand.equals("totalStorage")) {
/*       */         
/*  5930 */         if (1 == paramInt2) {
/*       */           
/*  5932 */           str8 = "of the Total Storage " + str7 + " machine type:";
/*       */         }
/*  5934 */         else if (2 == paramInt2) {
/*       */           
/*  5936 */           str8 = "the " + str7 + " machine type:";
/*       */         } 
/*       */       } 
/*       */       
/*  5940 */       switch (paramInt2) {
/*       */         
/*       */         case 1:
/*  5943 */           str8 = ":p.The following are newly announced features on the specified models " + str8;
/*  5944 */           arrayOfString1 = extractStringLines(str8, 70);
/*  5945 */           for (b2 = 0; b2 < arrayOfString1.length; b2++)
/*       */           {
/*  5947 */             paramStringBuffer.append(arrayOfString1[b2] + NEWLINE);
/*       */           }
/*       */           break;
/*       */         
/*       */         case 2:
/*  5952 */           str8 = ":p.The following are features already announced for " + str8;
/*  5953 */           arrayOfString1 = extractStringLines(str8, 70);
/*  5954 */           for (b2 = 0; b2 < arrayOfString1.length; b2++)
/*       */           {
/*  5956 */             paramStringBuffer.append(arrayOfString1[b2] + NEWLINE);
/*       */           }
/*       */           break;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5964 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/*       */       
/*  5966 */       paramStringBuffer.append(".fo off" + NEWLINE);
/*  5967 */       paramStringBuffer.append(".in 2" + NEWLINE);
/*       */       
/*  5969 */       paramStringBuffer.append("Description                                         MT  Model Feature" + NEWLINE);
/*  5970 */       paramStringBuffer.append("-------------------------------------------------- ---- ----- -------" + NEWLINE);
/*       */       
/*  5972 */       paramStringBuffer.append(".fo on" + NEWLINE);
/*  5973 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */       
/*  5975 */       if (1 == paramInt1)
/*       */       {
/*  5977 */         if (treeSet2.size() > 0) {
/*       */           
/*  5979 */           Iterator<String> iterator2 = treeSet2.iterator();
/*  5980 */           paramStringBuffer.append(".pa" + NEWLINE);
/*  5981 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  5982 */           paramStringBuffer.append(".kp off" + NEWLINE);
/*       */           
/*  5984 */           label126: while (iterator2.hasNext()) {
/*       */             
/*  5986 */             String str = iterator2.next();
/*  5987 */             String[] arrayOfString = extractStringLines(parseString(str, 3, "<:>"), 50);
/*  5988 */             b1 = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*       */             while (true) {
/*  6024 */               if (0 == b1) {
/*       */                 
/*  6026 */                 paramStringBuffer.append(setString(arrayOfString[b1], 50));
/*  6027 */                 paramStringBuffer.append(" ");
/*  6028 */                 paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  6029 */                 paramStringBuffer.append("  ");
/*  6030 */                 paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  6031 */                 paramStringBuffer.append(NEWLINE);
/*  6032 */                 b1++;
/*       */               }
/*       */               else {
/*       */                 
/*  6036 */                 paramStringBuffer.append(setString(arrayOfString[b1], 50));
/*  6037 */                 paramStringBuffer.append(NEWLINE);
/*  6038 */                 b1++;
/*       */               } 
/*  6040 */               if (b1 >= arrayOfString.length)
/*       */                 continue label126; 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       }
/*  6046 */       String[] arrayOfString2 = null;
/*  6047 */       b1 = 0;
/*  6048 */       boolean bool = false;
/*  6049 */       set = treeMap.keySet();
/*  6050 */       iterator = set.iterator();
/*  6051 */       if (1 != paramInt1) {
/*       */         
/*  6053 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  6054 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*       */       } 
/*  6056 */       while (iterator.hasNext()) {
/*       */         
/*  6058 */         String str = iterator.next();
/*  6059 */         str2 = (String)treeMap.get(str);
/*  6060 */         str4 = parseString(str, 1, "<:>");
/*  6061 */         str6 = parseString(str, 2, "<:>");
/*  6062 */         if (!str4.equals(str3) || !str6.equals(str5)) {
/*       */           
/*  6064 */           arrayOfString2 = extractStringLines(parseString(str, 4, "<:>"), 50);
/*  6065 */           bool = true;
/*       */         } 
/*  6067 */         setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */         
/*  6069 */         if (parseString(str, 8, "<:>").length() > 0)
/*       */         {
/*  6071 */           if (!str4.equals(str3)) {
/*       */             
/*  6073 */             String[] arrayOfString = getStringTokens(parseString(str, 8, "<:>"), NEWLINE);
/*  6074 */             for (byte b = 0; b < arrayOfString.length; b++) {
/*       */               
/*  6076 */               if (arrayOfString[b].length() > 58) {
/*       */                 
/*  6078 */                 String[] arrayOfString3 = extractStringLines(arrayOfString[b], 58);
/*  6079 */                 for (byte b3 = 0; b3 < arrayOfString3.length; b3++)
/*       */                 {
/*  6081 */                   paramStringBuffer.append(":hp2." + arrayOfString3[b3] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  6086 */                 paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         }
/*       */         
/*  6092 */         if (bool) {
/*       */           
/*  6094 */           if (1 == arrayOfString2.length) {
/*       */             
/*  6096 */             paramStringBuffer.append(setString(arrayOfString2[0], 50));
/*  6097 */             paramStringBuffer.append(" ");
/*       */           }
/*       */           else {
/*       */             
/*  6101 */             for (byte b = 0; b < arrayOfString2.length; b++) {
/*       */               
/*  6103 */               paramStringBuffer.append(setString(arrayOfString2[b], 50));
/*  6104 */               if (b < arrayOfString2.length - 1)
/*       */               {
/*  6106 */                 paramStringBuffer.append(NEWLINE);
/*       */               }
/*       */             } 
/*  6109 */             paramStringBuffer.append(" ");
/*       */           } 
/*  6111 */           bool = false;
/*       */         }
/*       */         else {
/*       */           
/*  6115 */           paramStringBuffer.append(setString("", 51));
/*       */         } 
/*       */         
/*  6118 */         if (!str6.equals(str5) || !str4.equals(str3)) {
/*       */           
/*  6120 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  6121 */           paramStringBuffer.append("  ");
/*       */         }
/*       */         else {
/*       */           
/*  6125 */           paramStringBuffer.append(setString("", 6));
/*       */         } 
/*  6127 */         paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  6128 */         paramStringBuffer.append("     ");
/*  6129 */         if (!str4.equals(str3)) {
/*       */           
/*  6131 */           paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  6132 */           paramStringBuffer.append(NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/*  6136 */           paramStringBuffer.append(NEWLINE);
/*       */         } 
/*       */         
/*  6139 */         str1 = str2;
/*  6140 */         str3 = str4;
/*  6141 */         str5 = str6;
/*       */       } 
/*  6143 */       if (!str2.equals("WW"))
/*       */       {
/*  6145 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/*  6148 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  6149 */       paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */       
/*  6151 */       treeMap.clear();
/*  6152 */       treeMap = null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeaturesWithdrawFormat1(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  6293 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */ 
/*       */     
/*  6296 */     Set<String> set = this.productNumber_NewFC_TM.keySet();
/*  6297 */     Iterator<String> iterator = set.iterator();
/*  6298 */     while (iterator.hasNext()) {
/*       */       
/*  6300 */       String str1 = iterator.next();
/*       */       
/*  6302 */       String str2 = parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 8, "<:>") + "<:>" + parseString(str1, 9, "<:>");
/*  6303 */       treeMap.put(str2, this.productNumber_NewFC_TM.get(str1));
/*       */     } 
/*       */     
/*  6306 */     set = this.productNumber_ExistingFC_TM.keySet();
/*  6307 */     iterator = set.iterator();
/*  6308 */     while (iterator.hasNext()) {
/*       */       
/*  6310 */       String str1 = iterator.next();
/*       */       
/*  6312 */       String str2 = parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 8, "<:>") + "<:>" + parseString(str1, 9, "<:>");
/*  6313 */       treeMap.put(str2, this.productNumber_ExistingFC_TM.get(str1));
/*       */     } 
/*       */     
/*  6316 */     if (treeMap.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  6322 */       TreeSet<String> treeSet = new TreeSet();
/*  6323 */       set = treeMap.keySet();
/*  6324 */       iterator = set.iterator();
/*  6325 */       while (iterator.hasNext()) {
/*       */         
/*  6327 */         String str = iterator.next();
/*  6328 */         treeSet.add(parseString(str, 6, "<:>"));
/*       */       } 
/*       */       
/*  6331 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  6332 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  6334 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  6337 */       byte b1 = 0;
/*  6338 */       Iterator<String> iterator1 = treeSet.iterator();
/*  6339 */       while (iterator1.hasNext()) {
/*       */         
/*  6341 */         String str = iterator1.next();
/*       */         
/*  6343 */         iterator = set.iterator();
/*  6344 */         while (iterator.hasNext()) {
/*       */           
/*  6346 */           String str1 = iterator.next();
/*  6347 */           String str2 = parseString(str1, 6, "<:>");
/*       */           
/*  6349 */           if (str.equals(str2))
/*       */           {
/*  6351 */             arrayOfTreeMap[b1].put(str1, treeMap.get(str1));
/*       */           }
/*       */         } 
/*  6354 */         b1++;
/*       */       } 
/*       */       
/*  6357 */       iterator1 = treeSet.iterator();
/*  6358 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  6360 */         retrievePNFeaturesWithdrawFormat1(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
/*       */       }
/*       */       
/*  6363 */       for (b2 = 0; b2 < treeSet.size(); b2++) {
/*       */         
/*  6365 */         arrayOfTreeMap[b2].clear();
/*  6366 */         arrayOfTreeMap[b2] = null;
/*       */       } 
/*  6368 */       treeSet.clear();
/*  6369 */       treeSet = null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeaturesWithdrawFormat1(boolean paramBoolean, StringBuffer paramStringBuffer, TreeMap paramTreeMap) {
/*  6382 */     if (paramTreeMap.size() > 0) {
/*       */       
/*  6384 */       String str1 = "";
/*  6385 */       String str2 = "";
/*  6386 */       String str3 = "";
/*  6387 */       String str4 = "";
/*  6388 */       String str5 = "";
/*  6389 */       String str6 = "";
/*  6390 */       String str7 = parseString((String)paramTreeMap.firstKey(), 6, "<:>");
/*       */       
/*  6392 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  6396 */         paramStringBuffer.append(":h3.Effective " + formatDate(str7) + NEWLINE);
/*  6397 */         paramStringBuffer.append(":h5.Feature Withdrawals" + NEWLINE);
/*  6398 */         paramStringBuffer.append(":p.The following features are being withdrawn:" + NEWLINE);
/*  6399 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  6400 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*  6401 */         paramStringBuffer.append("Description                   Type  Model  Feature" + NEWLINE);
/*  6402 */         paramStringBuffer.append("----------------------------  ----  -----  -------" + NEWLINE);
/*  6403 */         Set set = paramTreeMap.keySet();
/*  6404 */         Iterator<String> iterator = set.iterator();
/*  6405 */         while (iterator.hasNext()) {
/*       */           
/*  6407 */           String str = iterator.next();
/*  6408 */           str2 = (String)paramTreeMap.get(str);
/*  6409 */           str4 = parseString(str, 1, "<:>");
/*  6410 */           str6 = parseString(str, 2, "<:>");
/*  6411 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  6412 */           if (parseString(str, 5, "<:>").length() > 0)
/*       */           {
/*  6414 */             if (!str4.equals(str3)) {
/*       */               
/*  6416 */               String[] arrayOfString = getStringTokens(parseString(str, 5, "<:>"), NEWLINE);
/*  6417 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*       */                 
/*  6419 */                 if (arrayOfString[b].length() > 58) {
/*       */                   
/*  6421 */                   String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  6422 */                   for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                   {
/*  6424 */                     paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                   }
/*       */                 }
/*       */                 else {
/*       */                   
/*  6429 */                   paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */                 } 
/*       */               } 
/*       */             } 
/*       */           }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6442 */           paramStringBuffer.append(parseString(str, 4, "<:>"));
/*       */           
/*  6444 */           paramStringBuffer.append("  ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6454 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*       */           
/*  6456 */           paramStringBuffer.append("   ");
/*  6457 */           paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  6458 */           paramStringBuffer.append("     ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6468 */           paramStringBuffer.append(parseString(str, 1, "<:>"));
/*       */           
/*  6470 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  6472 */           str1 = str2;
/*  6473 */           str3 = str4;
/*  6474 */           str5 = str6;
/*       */         } 
/*  6476 */         if (!str2.equals("WW"))
/*       */         {
/*  6478 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */         
/*  6481 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeaturesWithdrawFormat2(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  6494 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */ 
/*       */     
/*  6497 */     Set<String> set = this.productNumber_NewFC_TM.keySet();
/*  6498 */     Iterator<String> iterator = set.iterator();
/*  6499 */     while (iterator.hasNext()) {
/*       */       
/*  6501 */       String str1 = iterator.next();
/*       */ 
/*       */       
/*  6504 */       String str2 = parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 5, "<:>") + "<:>" + parseString(str1, 6, "<:>") + "<:>" + parseString(str1, 7, "<:>") + "<:>" + parseString(str1, 8, "<:>");
/*  6505 */       treeMap.put(str2, this.productNumber_NewFC_TM.get(str1));
/*       */     } 
/*       */     
/*  6508 */     set = this.productNumber_ExistingFC_TM.keySet();
/*  6509 */     iterator = set.iterator();
/*  6510 */     while (iterator.hasNext()) {
/*       */       
/*  6512 */       String str1 = iterator.next();
/*       */ 
/*       */       
/*  6515 */       String str2 = parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 5, "<:>") + "<:>" + parseString(str1, 6, "<:>") + "<:>" + parseString(str1, 7, "<:>") + "<:>" + parseString(str1, 8, "<:>");
/*  6516 */       treeMap.put(str2, this.productNumber_ExistingFC_TM.get(str1));
/*       */     } 
/*       */     
/*  6519 */     if (treeMap.size() > 0) {
/*       */       
/*  6521 */       String str1 = "";
/*  6522 */       String str2 = "";
/*  6523 */       String str3 = "";
/*  6524 */       String str4 = "";
/*  6525 */       String str5 = "";
/*  6526 */       String str6 = "";
/*       */       
/*  6528 */       paramStringBuffer.append(":p.The following features are being withdrawn on the specified models" + NEWLINE);
/*  6529 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  6531 */         paramStringBuffer.append("of the IBM RS/6000 or pSeries machine types:" + NEWLINE + NEWLINE);
/*       */       }
/*  6533 */       else if (this.brand.equals("xSeries")) {
/*       */         
/*  6535 */         paramStringBuffer.append("of the IBM xSeries machine types:" + NEWLINE + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*  6539 */       else if (this.brand.equals("totalStorage")) {
/*       */         
/*  6541 */         paramStringBuffer.append("of the IBM Total Storage machine types:" + NEWLINE + NEWLINE);
/*       */       } 
/*       */       
/*  6544 */       if (2 == this.format || 3 == this.format) {
/*       */         
/*  6546 */         String[] arrayOfString = null;
/*  6547 */         byte b = 0;
/*  6548 */         paramStringBuffer.append(".RH ON" + NEWLINE);
/*       */         
/*  6550 */         paramStringBuffer.append(".fo off" + NEWLINE);
/*  6551 */         paramStringBuffer.append(".in 2" + NEWLINE);
/*       */         
/*  6553 */         paramStringBuffer.append("Description                                         MT  Model Feature" + NEWLINE);
/*  6554 */         paramStringBuffer.append("-------------------------------------------------- ---- ----- -------" + NEWLINE);
/*       */         
/*  6556 */         paramStringBuffer.append(".fo on" + NEWLINE);
/*  6557 */         paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */         
/*  6559 */         set = treeMap.keySet();
/*  6560 */         iterator = set.iterator();
/*  6561 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  6562 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*  6563 */         while (iterator.hasNext()) {
/*       */           
/*  6565 */           String str = iterator.next();
/*  6566 */           str2 = (String)treeMap.get(str);
/*  6567 */           str4 = parseString(str, 1, "<:>");
/*  6568 */           str6 = parseString(str, 2, "<:>");
/*  6569 */           if (!str4.equals(str3)) {
/*       */             
/*  6571 */             arrayOfString = extractStringLines(parseString(str, 4, "<:>"), 50);
/*  6572 */             b = 0;
/*       */           } 
/*  6574 */           setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */           
/*  6576 */           if (parseString(str, 8, "<:>").length() > 0)
/*       */           {
/*  6578 */             if (!str4.equals(str3)) {
/*       */               
/*  6580 */               String[] arrayOfString1 = getStringTokens(parseString(str, 8, "<:>"), NEWLINE);
/*  6581 */               for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */                 
/*  6583 */                 if (arrayOfString1[b1].length() > 58) {
/*       */                   
/*  6585 */                   String[] arrayOfString2 = extractStringLines(arrayOfString1[b1], 58);
/*  6586 */                   for (byte b2 = 0; b2 < arrayOfString2.length; b2++)
/*       */                   {
/*  6588 */                     paramStringBuffer.append(":hp2." + arrayOfString2[b2] + ":ehp2." + NEWLINE);
/*       */                   }
/*       */                 }
/*       */                 else {
/*       */                   
/*  6593 */                   paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                 } 
/*       */               } 
/*       */             } 
/*       */           }
/*       */           
/*  6599 */           if (b < arrayOfString.length) {
/*       */             
/*  6601 */             paramStringBuffer.append(setString(arrayOfString[b], 50));
/*  6602 */             paramStringBuffer.append(" ");
/*  6603 */             b++;
/*       */           }
/*       */           else {
/*       */             
/*  6607 */             paramStringBuffer.append(setString("", 51));
/*       */           } 
/*  6609 */           if (!str6.equals(str5) || !str4.equals(str3)) {
/*       */             
/*  6611 */             paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  6612 */             paramStringBuffer.append("  ");
/*       */           }
/*       */           else {
/*       */             
/*  6616 */             paramStringBuffer.append(setString("", 6));
/*       */           } 
/*  6618 */           paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  6619 */           paramStringBuffer.append("     ");
/*  6620 */           if (!str4.equals(str3)) {
/*       */             
/*  6622 */             paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  6623 */             paramStringBuffer.append(NEWLINE);
/*       */           }
/*       */           else {
/*       */             
/*  6627 */             paramStringBuffer.append(NEWLINE);
/*       */           } 
/*  6629 */           while (b < arrayOfString.length) {
/*       */             
/*  6631 */             paramStringBuffer.append(setString(arrayOfString[b], 50));
/*  6632 */             paramStringBuffer.append(NEWLINE);
/*  6633 */             b++;
/*       */           } 
/*       */           
/*  6636 */           str1 = str2;
/*  6637 */           str3 = str4;
/*  6638 */           str5 = str6;
/*       */         } 
/*  6640 */         if (!str2.equals("WW"))
/*       */         {
/*  6642 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */         
/*  6645 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*  6646 */         paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */       } 
/*  6648 */       treeMap.clear();
/*  6649 */       treeMap = null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNMTMConversions(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  6661 */     if (this.productNumber_MTM_Conversions_TM.size() > 0) {
/*       */       
/*  6663 */       String str1 = "";
/*  6664 */       String str2 = "";
/*  6665 */       if (paramBoolean == true) {
/*       */         
/*  6667 */         if (this.annType.equals("new")) {
/*       */           
/*  6669 */           paramStringBuffer.append(":h3.Type/Model Conversions" + NEWLINE);
/*       */         }
/*  6671 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  6673 */           paramStringBuffer.append(":h3.Type/Model Conversion Withdrawals" + NEWLINE);
/*  6674 */           paramStringBuffer.append(":p.The following Type/Model Conversions are being withdrawn:" + NEWLINE);
/*       */         } 
/*  6676 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  6677 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*  6681 */       else if (this.annType.equals("new")) {
/*       */         
/*  6683 */         paramStringBuffer.append("<h3>Type/Model Conversions</h3>" + NEWLINE);
/*       */       }
/*  6685 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*  6687 */         paramStringBuffer.append("<h3>Type/Model Conversion Withdrawals</h3>" + NEWLINE);
/*  6688 */         paramStringBuffer.append("<p>The following Type/Model Conversions are being withdrawn:</p>" + NEWLINE);
/*       */       } 
/*       */ 
/*       */       
/*  6692 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6697 */         if (this.annType.equals("new")) {
/*       */           
/*  6699 */           paramStringBuffer.append("   From       To      Parts" + NEWLINE);
/*  6700 */           paramStringBuffer.append("Type Model Type Model Returned" + NEWLINE);
/*  6701 */           paramStringBuffer.append("---- ----- ---- ----- --------" + NEWLINE);
/*       */         }
/*  6703 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  6705 */           paramStringBuffer.append("   From       To" + NEWLINE);
/*  6706 */           paramStringBuffer.append("Type Model Type Model" + NEWLINE);
/*  6707 */           paramStringBuffer.append("---- ----- ---- -----" + NEWLINE);
/*       */         } 
/*  6709 */         Set set = this.productNumber_MTM_Conversions_TM.keySet();
/*  6710 */         Iterator<String> iterator = set.iterator();
/*  6711 */         while (iterator.hasNext()) {
/*       */           
/*  6713 */           String str = iterator.next();
/*  6714 */           str2 = (String)this.productNumber_MTM_Conversions_TM.get(str);
/*  6715 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  6717 */           paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  6718 */           paramStringBuffer.append("  ");
/*  6719 */           paramStringBuffer.append(parseString(str, 4, "<:>"));
/*  6720 */           paramStringBuffer.append("  ");
/*  6721 */           paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  6722 */           paramStringBuffer.append("  ");
/*  6723 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  6724 */           if (this.annType.equals("new")) {
/*       */             
/*  6726 */             paramStringBuffer.append("    ");
/*  6727 */             paramStringBuffer.append(parseString(str, 5, "<:>"));
/*       */           } 
/*  6729 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  6731 */           str1 = str2;
/*       */         } 
/*  6733 */         if (!str2.equals("WW"))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6739 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6745 */         if (paramBoolean == true)
/*       */         {
/*  6747 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */ 
/*       */         
/*       */         }
/*       */ 
/*       */       
/*       */       }
/*  6754 */       else if (2 == this.format || 3 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  6758 */         paramStringBuffer.append("   From       To" + NEWLINE);
/*  6759 */         paramStringBuffer.append("Type Model Type Model" + NEWLINE);
/*  6760 */         paramStringBuffer.append("---- ----- ---- -----" + NEWLINE);
/*  6761 */         Set set = this.productNumber_MTM_Conversions_TM.keySet();
/*  6762 */         Iterator<String> iterator = set.iterator();
/*  6763 */         while (iterator.hasNext()) {
/*       */           
/*  6765 */           String str = iterator.next();
/*  6766 */           str2 = (String)this.productNumber_MTM_Conversions_TM.get(str);
/*  6767 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  6769 */           paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  6770 */           paramStringBuffer.append("  ");
/*  6771 */           paramStringBuffer.append(parseString(str, 4, "<:>"));
/*  6772 */           paramStringBuffer.append("  ");
/*  6773 */           paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  6774 */           paramStringBuffer.append("  ");
/*  6775 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  6776 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  6778 */           str1 = str2;
/*       */         } 
/*  6780 */         if (!str2.equals("WW"))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6786 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6792 */         if (paramBoolean == true)
/*       */         {
/*  6794 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */         }
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNModelConversions(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  6812 */     if (this.productNumber_Model_Conversions_TM.size() > 0) {
/*       */       
/*  6814 */       String str1 = "";
/*  6815 */       String str2 = "";
/*  6816 */       if (paramBoolean == true) {
/*       */         
/*  6818 */         if (this.annType.equals("new")) {
/*       */           
/*  6820 */           paramStringBuffer.append(":h3.Model Conversions" + NEWLINE);
/*       */         }
/*  6822 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  6824 */           paramStringBuffer.append(":h3.Model Conversion Withdrawals" + NEWLINE);
/*  6825 */           paramStringBuffer.append(":p.The following Model Converions on the specified machine type are being withdrawn:" + NEWLINE);
/*       */         } 
/*  6827 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  6828 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*  6832 */       else if (this.annType.equals("new")) {
/*       */         
/*  6834 */         paramStringBuffer.append("<h3>Model Conversions</h3>" + NEWLINE);
/*       */       }
/*  6836 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*  6838 */         paramStringBuffer.append("<h3>Model Conversion Withdrawals</h3>" + NEWLINE);
/*  6839 */         paramStringBuffer.append("<p>The following Model Converions on the specified machine type are being withdrawn:</p>" + NEWLINE);
/*       */       } 
/*       */ 
/*       */       
/*  6843 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  6847 */         if (this.annType.equals("new")) {
/*       */           
/*  6849 */           paramStringBuffer.append("      From   To      Parts" + NEWLINE);
/*  6850 */           paramStringBuffer.append("Type  Model  Model   Returned" + NEWLINE);
/*  6851 */           paramStringBuffer.append("----  -----  -----   --------" + NEWLINE);
/*       */         }
/*  6853 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  6855 */           paramStringBuffer.append("      From   To" + NEWLINE);
/*  6856 */           paramStringBuffer.append("Type  Model  Model" + NEWLINE);
/*  6857 */           paramStringBuffer.append("----  -----  -----" + NEWLINE);
/*       */         } 
/*  6859 */         Set set = this.productNumber_Model_Conversions_TM.keySet();
/*  6860 */         Iterator<String> iterator = set.iterator();
/*  6861 */         while (iterator.hasNext()) {
/*       */           
/*  6863 */           String str = iterator.next();
/*  6864 */           str2 = (String)this.productNumber_Model_Conversions_TM.get(str);
/*  6865 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  6867 */           paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  6868 */           paramStringBuffer.append("   ");
/*  6869 */           paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  6870 */           paramStringBuffer.append("    ");
/*  6871 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  6872 */           if (this.annType.equals("new")) {
/*       */             
/*  6874 */             paramStringBuffer.append("      ");
/*  6875 */             paramStringBuffer.append(parseString(str, 4, "<:>"));
/*       */           } 
/*  6877 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  6879 */           str1 = str2;
/*       */         } 
/*  6881 */         if (!str2.equals("WW"))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6887 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6893 */         if (paramBoolean == true)
/*       */         {
/*  6895 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */ 
/*       */         
/*       */         }
/*       */ 
/*       */       
/*       */       }
/*  6902 */       else if (2 == this.format || 3 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  6906 */         if (this.annType.equals("new")) {
/*       */           
/*  6908 */           paramStringBuffer.append("      From   To      Parts" + NEWLINE);
/*  6909 */           paramStringBuffer.append("Type  Model  Model   Returned" + NEWLINE);
/*  6910 */           paramStringBuffer.append("----  -----  -----   --------" + NEWLINE);
/*       */         }
/*  6912 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  6914 */           paramStringBuffer.append("      From   To" + NEWLINE);
/*  6915 */           paramStringBuffer.append("Type  Model  Model" + NEWLINE);
/*  6916 */           paramStringBuffer.append("----  -----  -----" + NEWLINE);
/*       */         } 
/*  6918 */         Set set = this.productNumber_Model_Conversions_TM.keySet();
/*  6919 */         Iterator<String> iterator = set.iterator();
/*  6920 */         while (iterator.hasNext()) {
/*       */           
/*  6922 */           String str = iterator.next();
/*  6923 */           str2 = (String)this.productNumber_Model_Conversions_TM.get(str);
/*  6924 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  6926 */           paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  6927 */           paramStringBuffer.append("   ");
/*  6928 */           paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  6929 */           paramStringBuffer.append("    ");
/*  6930 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  6931 */           if (this.annType.equals("new")) {
/*       */             
/*  6933 */             paramStringBuffer.append("      ");
/*  6934 */             paramStringBuffer.append(parseString(str, 4, "<:>"));
/*       */           } 
/*  6936 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  6938 */           str1 = str2;
/*       */         } 
/*  6940 */         if (!str2.equals("WW"))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6946 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6952 */         if (paramBoolean == true)
/*       */         {
/*  6954 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */         }
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsFormat1(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  6972 */     if (this.productNumber_Feature_Conversions_TM.size() > 0) {
/*       */       
/*  6974 */       String str1 = "";
/*  6975 */       String str2 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  6980 */       paramStringBuffer.append(":h3.Conversions" + NEWLINE);
/*  6981 */       paramStringBuffer.append(":h5.Feature Conversions" + NEWLINE);
/*  6982 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*  6983 */       paramStringBuffer.append(".kp off" + NEWLINE);
/*  6984 */       paramStringBuffer.append("               Parts       Continuous    Machine" + NEWLINE);
/*  6985 */       paramStringBuffer.append("From:   To:    Returned    Maintenance   Type     Model" + NEWLINE);
/*  6986 */       paramStringBuffer.append("----    ---    --------    -----------   -------  -----" + NEWLINE);
/*  6987 */       Set set = this.productNumber_Feature_Conversions_TM.keySet();
/*  6988 */       Iterator<String> iterator = set.iterator();
/*  6989 */       while (iterator.hasNext()) {
/*       */         
/*  6991 */         String str = iterator.next();
/*  6992 */         str2 = (String)this.productNumber_Feature_Conversions_TM.get(str);
/*  6993 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  6994 */         if (parseString(str, 9, "<:>").length() > 0) {
/*       */           
/*  6996 */           if (!paramBoolean) {
/*       */ 
/*       */             
/*  6999 */             String[] arrayOfString = getStringTokens(parseString(str, 9, "<:>"), NEWLINE);
/*  7000 */             for (byte b = 0; b < arrayOfString.length; b++)
/*       */             {
/*  7002 */               paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */             }
/*       */           } 
/*       */           
/*  7006 */           if (paramBoolean == true) {
/*       */             
/*  7008 */             String[] arrayOfString = getStringTokens(parseString(str, 9, "<:>"), NEWLINE);
/*  7009 */             for (byte b = 0; b < arrayOfString.length; b++) {
/*       */               
/*  7011 */               if (arrayOfString[b].length() > 58) {
/*       */                 
/*  7013 */                 String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  7014 */                 for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                 {
/*  7016 */                   paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  7021 */                 paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         
/*  7027 */         paramStringBuffer.append(parseString(str, 7, "<:>"));
/*  7028 */         paramStringBuffer.append("    ");
/*  7029 */         paramStringBuffer.append(parseString(str, 4, "<:>"));
/*  7030 */         paramStringBuffer.append("     ");
/*  7031 */         paramStringBuffer.append(parseString(str, 8, "<:>"));
/*  7032 */         paramStringBuffer.append("                      ");
/*  7033 */         paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  7034 */         paramStringBuffer.append("     ");
/*  7035 */         paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  7036 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  7038 */         str1 = str2;
/*       */       } 
/*  7040 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7046 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7052 */       if (paramBoolean == true)
/*       */       {
/*  7054 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsFormat2(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  7071 */     if (this.charges_Feature_Conversions_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7082 */       paramStringBuffer.append(":h3.Feature Conversions" + NEWLINE);
/*  7083 */       paramStringBuffer.append(":p." + NEWLINE);
/*  7084 */       paramStringBuffer.append("The existing components being replaced during a model or feature" + NEWLINE);
/*  7085 */       paramStringBuffer.append("conversion become the property of IBM and must be returned." + NEWLINE);
/*  7086 */       paramStringBuffer.append(":p." + NEWLINE);
/*  7087 */       paramStringBuffer.append("Feature conversions are always implemented on a \"quantity of one for" + NEWLINE);
/*  7088 */       paramStringBuffer.append("quantity of one\" basis. Multiple existing features may not be converted" + NEWLINE);
/*  7089 */       paramStringBuffer.append("to a single new feature. Single existing features may not be converted" + NEWLINE);
/*  7090 */       paramStringBuffer.append("to multiple new features." + NEWLINE);
/*  7091 */       paramStringBuffer.append(":p." + NEWLINE);
/*  7092 */       paramStringBuffer.append("The following conversions are available to customers:" + NEWLINE);
/*  7093 */       paramStringBuffer.append(":p." + NEWLINE);
/*       */ 
/*       */       
/*  7096 */       String str1 = "";
/*  7097 */       String str2 = "";
/*  7098 */       TreeSet<String> treeSet = new TreeSet();
/*  7099 */       Set<String> set = this.charges_Feature_Conversions_TM.keySet();
/*  7100 */       Iterator<String> iterator1 = set.iterator();
/*  7101 */       while (iterator1.hasNext()) {
/*       */         
/*  7103 */         String str = iterator1.next();
/*  7104 */         str1 = parseString(str, 5, "<:>");
/*  7105 */         str2 = parseString(str, 6, "<:>");
/*  7106 */         treeSet.add(str1 + "-" + str2);
/*       */       } 
/*       */       
/*  7109 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  7110 */       for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  7112 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  7115 */       byte b1 = 0;
/*  7116 */       Iterator<String> iterator2 = treeSet.iterator();
/*  7117 */       while (iterator2.hasNext()) {
/*       */         
/*  7119 */         String str = iterator2.next();
/*       */         
/*  7121 */         iterator1 = set.iterator();
/*  7122 */         while (iterator1.hasNext()) {
/*       */ 
/*       */           
/*  7125 */           String str4 = iterator1.next();
/*  7126 */           str1 = parseString(str4, 5, "<:>");
/*  7127 */           str2 = parseString(str4, 6, "<:>");
/*  7128 */           String str3 = str1 + "-" + str2;
/*       */           
/*  7130 */           if (str.equals(str3))
/*       */           {
/*  7132 */             arrayOfTreeMap[b1].put(str4, this.charges_Feature_Conversions_TM.get(str4));
/*       */           }
/*       */         } 
/*  7135 */         b1++;
/*       */       } 
/*       */       
/*  7138 */       b1 = 0;
/*  7139 */       iterator2 = treeSet.iterator();
/*  7140 */       while (iterator2.hasNext()) {
/*       */         
/*  7142 */         String str = iterator2.next();
/*  7143 */         retrievePNFeatureConversionsFormat2(paramBoolean, paramStringBuffer, str, arrayOfTreeMap[b1]);
/*  7144 */         b1++;
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsFormat2(boolean paramBoolean, StringBuffer paramStringBuffer, String paramString, TreeMap paramTreeMap) {
/*  7166 */     paramStringBuffer.append(":h4.Feature conversions for " + paramString + NEWLINE);
/*  7167 */     paramStringBuffer.append(".sk 1" + NEWLINE);
/*  7168 */     paramStringBuffer.append(":p." + NEWLINE);
/*       */ 
/*       */     
/*  7171 */     TreeSet<String> treeSet = new TreeSet();
/*  7172 */     Set<String> set = paramTreeMap.keySet();
/*  7173 */     Iterator<String> iterator1 = set.iterator();
/*  7174 */     while (iterator1.hasNext()) {
/*       */       
/*  7176 */       String str = iterator1.next();
/*  7177 */       treeSet.add(parseString(str, 1, "<:>"));
/*       */     } 
/*       */     
/*  7180 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  7181 */     for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/*  7183 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/*  7186 */     byte b1 = 0;
/*  7187 */     Iterator<String> iterator2 = treeSet.iterator();
/*  7188 */     while (iterator2.hasNext()) {
/*       */       
/*  7190 */       String str = iterator2.next();
/*       */       
/*  7192 */       iterator1 = set.iterator();
/*  7193 */       while (iterator1.hasNext()) {
/*       */         
/*  7195 */         String str1 = iterator1.next();
/*       */         
/*  7197 */         if (str.equals(parseString(str1, 1, "<:>")))
/*       */         {
/*  7199 */           arrayOfTreeMap[b1].put(str1, paramTreeMap.get(str1));
/*       */         }
/*       */       } 
/*  7202 */       b1++;
/*       */     } 
/*       */     
/*  7205 */     for (b2 = 0; b2 < arrayOfTreeMap.length; b2++)
/*       */     {
/*  7207 */       retrievePNFeatureConversionsFormat2(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsFormat2(boolean paramBoolean, StringBuffer paramStringBuffer, TreeMap paramTreeMap) {
/*  7220 */     String str1 = "";
/*  7221 */     String str2 = "";
/*  7222 */     String str3 = parseString((String)paramTreeMap.firstKey(), 1, "<:>");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7228 */     paramStringBuffer.append(":h5.Feature conversions for " + str3 + " features:" + NEWLINE);
/*  7229 */     paramStringBuffer.append(":xmp." + NEWLINE);
/*       */     
/*  7231 */     paramStringBuffer.append("                                                            RETURN" + NEWLINE);
/*  7232 */     paramStringBuffer.append("From FC:                      To FC:                        PARTS" + NEWLINE);
/*  7233 */     paramStringBuffer.append("---------------------------   ---------------------------   ------" + NEWLINE);
/*  7234 */     paramStringBuffer.append(":exmp." + NEWLINE);
/*  7235 */     int i = 0;
/*  7236 */     byte b = 0;
/*  7237 */     boolean bool = false;
/*  7238 */     Set set = paramTreeMap.keySet();
/*  7239 */     Iterator<String> iterator = set.iterator();
/*  7240 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7246 */       String str6 = iterator.next();
/*  7247 */       str2 = (String)paramTreeMap.get(str6);
/*  7248 */       setGeoTagsFeatConv(str1, str2, paramBoolean, paramStringBuffer);
/*  7249 */       if (parseString(str6, 11, "<:>").length() > 0) {
/*       */         
/*  7251 */         if (!paramBoolean) {
/*       */ 
/*       */           
/*  7254 */           String[] arrayOfString = getStringTokens(parseString(str6, 11, "<:>"), NEWLINE);
/*  7255 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++)
/*       */           {
/*  7257 */             paramStringBuffer.append(arrayOfString[b1] + NEWLINE);
/*       */           }
/*       */         } 
/*       */         
/*  7261 */         if (paramBoolean == true) {
/*       */           
/*  7263 */           String[] arrayOfString = getStringTokens(parseString(str6, 11, "<:>"), NEWLINE);
/*  7264 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */             
/*  7266 */             if (arrayOfString[b1].length() > 58) {
/*       */               
/*  7268 */               String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  7269 */               for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */               {
/*  7271 */                 paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */               }
/*       */             }
/*       */             else {
/*       */               
/*  7276 */               paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       
/*  7282 */       String str4 = parseString(str6, 7, "<:>") + " - " + parseString(str6, 9, "<:>");
/*  7283 */       String str5 = parseString(str6, 4, "<:>") + " - " + parseString(str6, 8, "<:>");
/*  7284 */       String[] arrayOfString1 = extractStringLines(str4, 27);
/*  7285 */       String[] arrayOfString2 = extractStringLines(str5, 27);
/*  7286 */       i = 0;
/*  7287 */       if (arrayOfString1.length > arrayOfString2.length) {
/*       */         
/*  7289 */         i = arrayOfString1.length;
/*       */       }
/*       */       else {
/*       */         
/*  7293 */         i = arrayOfString2.length;
/*       */       } 
/*  7295 */       b = 0;
/*  7296 */       bool = false;
/*       */       
/*       */       while (true) {
/*  7299 */         if (false == bool) {
/*       */ 
/*       */           
/*  7302 */           paramStringBuffer.append(setString(arrayOfString1[b], 27));
/*  7303 */           paramStringBuffer.append("   ");
/*  7304 */           paramStringBuffer.append(setString(arrayOfString2[b], 27));
/*  7305 */           paramStringBuffer.append("    ");
/*  7306 */           paramStringBuffer.append(parseString(str6, 10, "<:>"));
/*  7307 */           paramStringBuffer.append(NEWLINE);
/*  7308 */           bool = true;
/*  7309 */           b++;
/*       */         }
/*       */         else {
/*       */           
/*  7313 */           if (b < arrayOfString1.length) {
/*       */             
/*  7315 */             paramStringBuffer.append(setString(arrayOfString1[b], 27));
/*       */           }
/*       */           else {
/*       */             
/*  7319 */             paramStringBuffer.append(setString(" ", 27));
/*       */           } 
/*  7321 */           paramStringBuffer.append("   ");
/*  7322 */           if (b < arrayOfString2.length) {
/*       */             
/*  7324 */             paramStringBuffer.append(setString(arrayOfString2[b], 27));
/*       */           }
/*       */           else {
/*       */             
/*  7328 */             paramStringBuffer.append(setString(" ", 27));
/*       */           } 
/*  7330 */           paramStringBuffer.append(NEWLINE);
/*  7331 */           b++;
/*       */         } 
/*  7333 */         if (b >= i)
/*  7334 */           str1 = str2; 
/*       */       } 
/*  7336 */     }  if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7342 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7348 */     if (paramBoolean == true)
/*       */     {
/*  7350 */       paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsForWithdrawFormat1(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  7366 */     if (this.productNumber_Feature_Conversions_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7372 */       TreeSet<String> treeSet = new TreeSet();
/*  7373 */       Set<String> set = this.productNumber_Feature_Conversions_TM.keySet();
/*  7374 */       Iterator<String> iterator2 = set.iterator();
/*  7375 */       while (iterator2.hasNext()) {
/*       */         
/*  7377 */         String str = iterator2.next();
/*  7378 */         treeSet.add(parseString(str, 10, "<:>"));
/*       */       } 
/*       */       
/*  7381 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  7382 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  7384 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  7387 */       byte b1 = 0;
/*  7388 */       Iterator<String> iterator1 = treeSet.iterator();
/*  7389 */       while (iterator1.hasNext()) {
/*       */         
/*  7391 */         String str = iterator1.next();
/*       */         
/*  7393 */         iterator2 = set.iterator();
/*  7394 */         while (iterator2.hasNext()) {
/*       */           
/*  7396 */           String str1 = iterator2.next();
/*  7397 */           String str2 = parseString(str1, 10, "<:>");
/*       */           
/*  7399 */           if (str.equals(str2)) {
/*       */ 
/*       */ 
/*       */             
/*  7403 */             String str3 = parseString(str1, 1, "<:>") + "<:>" + parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 5, "<:>") + "<:>" + parseString(str1, 6, "<:>") + "<:>" + parseString(str1, 7, "<:>") + "<:>" + parseString(str1, 9, "<:>") + "<:>" + parseString(str1, 10, "<:>");
/*  7404 */             arrayOfTreeMap[b1].put(str3, this.productNumber_Feature_Conversions_TM.get(str1));
/*       */           } 
/*       */         } 
/*  7407 */         b1++;
/*       */       } 
/*       */       
/*  7410 */       iterator1 = treeSet.iterator();
/*  7411 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  7413 */         retrievePNFeatureConversionsForWithdrawFormat1(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
/*       */       }
/*       */       
/*  7416 */       for (b2 = 0; b2 < treeSet.size(); b2++) {
/*       */         
/*  7418 */         arrayOfTreeMap[b2].clear();
/*  7419 */         arrayOfTreeMap[b2] = null;
/*       */       } 
/*  7421 */       treeSet.clear();
/*  7422 */       treeSet = null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsForWithdrawFormat1(boolean paramBoolean, StringBuffer paramStringBuffer, TreeMap paramTreeMap) {
/*  7437 */     String str1 = "";
/*  7438 */     String str2 = "";
/*  7439 */     String str3 = parseString((String)paramTreeMap.firstKey(), 9, "<:>");
/*       */     
/*  7441 */     paramStringBuffer.append(":h3.Effective " + formatDate(str3) + NEWLINE);
/*  7442 */     paramStringBuffer.append(":h5.Feature Conversion Withdrawals" + NEWLINE);
/*  7443 */     paramStringBuffer.append(":xmp." + NEWLINE);
/*  7444 */     paramStringBuffer.append(".kp off" + NEWLINE);
/*  7445 */     paramStringBuffer.append("              Machine" + NEWLINE);
/*  7446 */     paramStringBuffer.append("From:   To:   Type     Model" + NEWLINE);
/*  7447 */     paramStringBuffer.append("----    ---   -------  -----" + NEWLINE);
/*  7448 */     Set set = paramTreeMap.keySet();
/*  7449 */     Iterator<String> iterator = set.iterator();
/*  7450 */     while (iterator.hasNext()) {
/*       */       
/*  7452 */       String str = iterator.next();
/*  7453 */       str2 = (String)paramTreeMap.get(str);
/*  7454 */       setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  7455 */       if (parseString(str, 8, "<:>").length() > 0) {
/*       */         
/*  7457 */         if (!paramBoolean) {
/*       */ 
/*       */           
/*  7460 */           String[] arrayOfString = getStringTokens(parseString(str, 8, "<:>"), NEWLINE);
/*  7461 */           for (byte b = 0; b < arrayOfString.length; b++)
/*       */           {
/*  7463 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/*       */         } 
/*       */         
/*  7467 */         if (paramBoolean == true) {
/*       */           
/*  7469 */           String[] arrayOfString = getStringTokens(parseString(str, 9, "<:>"), NEWLINE);
/*  7470 */           for (byte b = 0; b < arrayOfString.length; b++) {
/*       */             
/*  7472 */             if (arrayOfString[b].length() > 58) {
/*       */               
/*  7474 */               String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  7475 */               for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */               {
/*  7477 */                 paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */               }
/*       */             }
/*       */             else {
/*       */               
/*  7482 */               paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       
/*  7488 */       paramStringBuffer.append(parseString(str, 7, "<:>"));
/*  7489 */       paramStringBuffer.append("    ");
/*  7490 */       paramStringBuffer.append(parseString(str, 4, "<:>"));
/*  7491 */       paramStringBuffer.append("   ");
/*  7492 */       paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  7493 */       paramStringBuffer.append("     ");
/*  7494 */       paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  7495 */       paramStringBuffer.append(NEWLINE);
/*       */       
/*  7497 */       str1 = str2;
/*       */     } 
/*  7499 */     if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7505 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7511 */     if (paramBoolean == true)
/*       */     {
/*  7513 */       paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsForWithdrawFormat2(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  7529 */     if (this.charges_Feature_Conversions_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7540 */       paramStringBuffer.append(":h3.Feature Conversion Withdrawals" + NEWLINE);
/*  7541 */       paramStringBuffer.append(":p." + NEWLINE);
/*       */ 
/*       */       
/*  7544 */       String str1 = "";
/*  7545 */       String str2 = "";
/*  7546 */       TreeSet<String> treeSet = new TreeSet();
/*  7547 */       Set<String> set = this.charges_Feature_Conversions_TM.keySet();
/*  7548 */       Iterator<String> iterator1 = set.iterator();
/*  7549 */       while (iterator1.hasNext()) {
/*       */         
/*  7551 */         String str = iterator1.next();
/*  7552 */         str1 = parseString(str, 5, "<:>");
/*  7553 */         str2 = parseString(str, 6, "<:>");
/*  7554 */         treeSet.add(str1 + "-" + str2);
/*       */       } 
/*       */       
/*  7557 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  7558 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  7560 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  7563 */       byte b1 = 0;
/*  7564 */       Iterator<String> iterator2 = treeSet.iterator();
/*  7565 */       while (iterator2.hasNext()) {
/*       */         
/*  7567 */         String str = iterator2.next();
/*       */         
/*  7569 */         iterator1 = set.iterator();
/*  7570 */         while (iterator1.hasNext()) {
/*       */ 
/*       */           
/*  7573 */           String str4 = iterator1.next();
/*  7574 */           str1 = parseString(str4, 5, "<:>");
/*  7575 */           str2 = parseString(str4, 6, "<:>");
/*  7576 */           String str3 = str1 + "-" + str2;
/*       */           
/*  7578 */           if (str.equals(str3))
/*       */           {
/*  7580 */             arrayOfTreeMap[b1].put(str4, this.charges_Feature_Conversions_TM.get(str4));
/*       */           }
/*       */         } 
/*  7583 */         b1++;
/*       */       } 
/*       */       
/*  7586 */       b1 = 0;
/*  7587 */       iterator2 = treeSet.iterator();
/*  7588 */       while (iterator2.hasNext()) {
/*       */         
/*  7590 */         String str = iterator2.next();
/*  7591 */         retrievePNFeatureConversionsForWithdrawFormat2(paramBoolean, paramStringBuffer, str, arrayOfTreeMap[b1]);
/*  7592 */         b1++;
/*       */       } 
/*       */       
/*  7595 */       for (b2 = 0; b2 < treeSet.size(); b2++) {
/*       */         
/*  7597 */         arrayOfTreeMap[b2].clear();
/*  7598 */         arrayOfTreeMap[b2] = null;
/*       */       } 
/*  7600 */       treeSet.clear();
/*  7601 */       treeSet = null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsForWithdrawFormat2(boolean paramBoolean, StringBuffer paramStringBuffer, String paramString, TreeMap paramTreeMap) {
/*  7622 */     paramStringBuffer.append("The following feature conversions for the " + paramString + " are being withdrawn:" + NEWLINE);
/*  7623 */     paramStringBuffer.append(".sk 1" + NEWLINE);
/*       */ 
/*       */     
/*  7626 */     TreeSet<String> treeSet = new TreeSet();
/*  7627 */     Set<String> set = paramTreeMap.keySet();
/*  7628 */     Iterator<String> iterator1 = set.iterator();
/*  7629 */     while (iterator1.hasNext()) {
/*       */       
/*  7631 */       String str = iterator1.next();
/*  7632 */       treeSet.add(parseString(str, 1, "<:>"));
/*       */     } 
/*       */     
/*  7635 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  7636 */     for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/*  7638 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/*  7641 */     byte b1 = 0;
/*  7642 */     Iterator<String> iterator2 = treeSet.iterator();
/*  7643 */     while (iterator2.hasNext()) {
/*       */       
/*  7645 */       String str = iterator2.next();
/*       */       
/*  7647 */       iterator1 = set.iterator();
/*  7648 */       while (iterator1.hasNext()) {
/*       */         
/*  7650 */         String str1 = iterator1.next();
/*       */         
/*  7652 */         if (str.equals(parseString(str1, 1, "<:>")))
/*       */         {
/*  7654 */           arrayOfTreeMap[b1].put(str1, paramTreeMap.get(str1));
/*       */         }
/*       */       } 
/*  7657 */       b1++;
/*       */     } 
/*       */     
/*  7660 */     for (b2 = 0; b2 < arrayOfTreeMap.length; b2++)
/*       */     {
/*  7662 */       retrievePNFeatureConversionsForWithdrawFormat2(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrievePNFeatureConversionsForWithdrawFormat2(boolean paramBoolean, StringBuffer paramStringBuffer, TreeMap paramTreeMap) {
/*  7675 */     String str1 = "";
/*  7676 */     String str2 = "";
/*  7677 */     String str3 = parseString((String)paramTreeMap.firstKey(), 1, "<:>");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7683 */     paramStringBuffer.append(":h5.Feature conversions for " + str3 + " features:" + NEWLINE);
/*  7684 */     paramStringBuffer.append(":xmp." + NEWLINE);
/*       */     
/*  7686 */     paramStringBuffer.append("                                                            RETURN" + NEWLINE);
/*  7687 */     paramStringBuffer.append("From FC:                      To FC:                        PARTS" + NEWLINE);
/*  7688 */     paramStringBuffer.append("---------------------------   ---------------------------   ------" + NEWLINE);
/*  7689 */     paramStringBuffer.append(":exmp." + NEWLINE);
/*  7690 */     int i = 0;
/*  7691 */     byte b = 0;
/*  7692 */     boolean bool = false;
/*  7693 */     Set set = paramTreeMap.keySet();
/*  7694 */     Iterator<String> iterator = set.iterator();
/*  7695 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7701 */       String str6 = iterator.next();
/*  7702 */       str2 = (String)paramTreeMap.get(str6);
/*  7703 */       setGeoTagsFeatConv(str1, str2, paramBoolean, paramStringBuffer);
/*  7704 */       if (parseString(str6, 11, "<:>").length() > 0) {
/*       */         
/*  7706 */         if (!paramBoolean) {
/*       */ 
/*       */           
/*  7709 */           String[] arrayOfString = getStringTokens(parseString(str6, 11, "<:>"), NEWLINE);
/*  7710 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++)
/*       */           {
/*  7712 */             paramStringBuffer.append(arrayOfString[b1] + NEWLINE);
/*       */           }
/*       */         } 
/*       */         
/*  7716 */         if (paramBoolean == true) {
/*       */           
/*  7718 */           String[] arrayOfString = getStringTokens(parseString(str6, 11, "<:>"), NEWLINE);
/*  7719 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */             
/*  7721 */             if (arrayOfString[b1].length() > 58) {
/*       */               
/*  7723 */               String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  7724 */               for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */               {
/*  7726 */                 paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */               }
/*       */             }
/*       */             else {
/*       */               
/*  7731 */               paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       
/*  7737 */       String str4 = parseString(str6, 7, "<:>") + " - " + parseString(str6, 9, "<:>");
/*  7738 */       String str5 = parseString(str6, 4, "<:>") + " - " + parseString(str6, 8, "<:>");
/*  7739 */       String[] arrayOfString1 = extractStringLines(str4, 27);
/*  7740 */       String[] arrayOfString2 = extractStringLines(str5, 27);
/*  7741 */       i = 0;
/*  7742 */       if (arrayOfString1.length > arrayOfString2.length) {
/*       */         
/*  7744 */         i = arrayOfString1.length;
/*       */       }
/*       */       else {
/*       */         
/*  7748 */         i = arrayOfString2.length;
/*       */       } 
/*  7750 */       b = 0;
/*  7751 */       bool = false;
/*       */       
/*       */       while (true) {
/*  7754 */         if (false == bool) {
/*       */ 
/*       */           
/*  7757 */           paramStringBuffer.append(setString(arrayOfString1[b], 27));
/*  7758 */           paramStringBuffer.append("   ");
/*  7759 */           paramStringBuffer.append(setString(arrayOfString2[b], 27));
/*  7760 */           paramStringBuffer.append("    ");
/*  7761 */           paramStringBuffer.append(parseString(str6, 10, "<:>"));
/*  7762 */           paramStringBuffer.append(NEWLINE);
/*  7763 */           bool = true;
/*  7764 */           b++;
/*       */         }
/*       */         else {
/*       */           
/*  7768 */           if (b < arrayOfString1.length) {
/*       */             
/*  7770 */             paramStringBuffer.append(setString(arrayOfString1[b], 27));
/*       */           }
/*       */           else {
/*       */             
/*  7774 */             paramStringBuffer.append(setString(" ", 27));
/*       */           } 
/*  7776 */           paramStringBuffer.append("   ");
/*  7777 */           if (b < arrayOfString2.length) {
/*       */             
/*  7779 */             paramStringBuffer.append(setString(arrayOfString2[b], 27));
/*       */           }
/*       */           else {
/*       */             
/*  7783 */             paramStringBuffer.append(setString(" ", 27));
/*       */           } 
/*  7785 */           paramStringBuffer.append(NEWLINE);
/*  7786 */           b++;
/*       */         } 
/*  7788 */         if (b >= i)
/*  7789 */           str1 = str2; 
/*       */       } 
/*  7791 */     }  if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7797 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7803 */     if (paramBoolean == true)
/*       */     {
/*  7805 */       paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveCharges(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  7821 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  7822 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  7823 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */ 
/*       */ 
/*       */     
/*  7827 */     if (this.charges_NewModels_TM.size() + this.charges_NewFC_TM.size() + this.charges_ExistingFC_TM
/*  7828 */       .size() + this.productNumber_MTM_Conversions_TM.size() + this.productNumber_Model_Conversions_TM
/*  7829 */       .size() + this.charges_Feature_Conversions_TM.size() + this.charges_NewModels_NewFC_TM
/*  7830 */       .size() + this.charges_NewModels_ExistingFC_TM.size() + this.charges_ExistingModels_NewFC_TM
/*  7831 */       .size() + this.charges_ExistingModels_ExistingFC_TM.size() > 0) {
/*       */       
/*  7833 */       log("annType = " + this.annType);
/*  7834 */       log("charges_NewModels_TM.size() = " + this.charges_NewModels_TM.size());
/*  7835 */       log("charges_NewFC_TM.size() = " + this.charges_NewFC_TM.size());
/*  7836 */       log("charges_ExistingFC_TM.size() = " + this.charges_ExistingFC_TM.size());
/*  7837 */       log("charges_NewModels_NewFC_TM.size() = " + this.charges_NewModels_NewFC_TM.size());
/*  7838 */       log("charges_NewModels_ExistingFC_TM.size() = " + this.charges_NewModels_ExistingFC_TM.size());
/*  7839 */       log("charges_ExistingModels_NewFC_TM.size() = " + this.charges_ExistingModels_NewFC_TM.size());
/*  7840 */       log("charges_ExistingModels_ExistingFC_TM.size() = " + this.charges_ExistingModels_ExistingFC_TM.size());
/*       */       
/*  7842 */       if (1 == this.format)
/*       */       {
/*  7844 */         retrieveChargesNewModelsFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/*  7847 */       if (this.charges_NewFC_TM.size() + this.charges_ExistingFC_TM.size() > 0)
/*       */       {
/*  7849 */         if (1 == this.format)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  7857 */           paramStringBuffer.append(":h3.Features and Specify Codes" + NEWLINE);
/*       */         }
/*       */       }
/*       */       
/*  7861 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7872 */         TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  7873 */         treeMap.putAll(this.charges_NewFC_TM);
/*  7874 */         treeMap.putAll(this.charges_ExistingFC_TM);
/*  7875 */         retrieveChargesFeaturesFormat1(treeMap, paramBoolean, paramStringBuffer);
/*  7876 */         treeMap.clear();
/*  7877 */         treeMap = null;
/*       */       }
/*  7879 */       else if (2 == this.format || 3 == this.format) {
/*       */         
/*  7881 */         retrieveChargesFeaturesFormat2(paramBoolean, paramStringBuffer);
/*       */       } 
/*       */       
/*  7884 */       retrieveChargesMTMConversions(paramBoolean, paramStringBuffer);
/*  7885 */       retrieveChargesModelConversions(paramBoolean, paramStringBuffer);
/*  7886 */       if (1 == this.format)
/*       */       {
/*  7888 */         retrieveChargesFeatureConverstionsFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*  7890 */       else if (2 == this.format || 3 == this.format)
/*       */       {
/*  7892 */         retrieveChargesFeatureConverstionsFormat2(paramBoolean, paramStringBuffer);
/*       */       
/*       */       }
/*       */     
/*       */     }
/*  7897 */     else if (paramBoolean == true) {
/*       */       
/*  7899 */       paramStringBuffer.append(":p.No answer data found for Charges section." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  7903 */       paramStringBuffer.append("<p>No answer data found for Charges section.</p>" + NEWLINE);
/*       */     } 
/*       */     
/*  7906 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesNewModelsFormat1(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  7917 */     if (this.charges_NewModels_TM.size() > 0) {
/*       */       
/*  7919 */       String str1 = "";
/*  7920 */       String str2 = "";
/*       */       
/*  7922 */       paramStringBuffer.append(":h3.Models" + NEWLINE);
/*       */       
/*  7924 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  7928 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  7929 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*  7930 */         paramStringBuffer.append("                                           Purchase        ESA" + NEWLINE);
/*  7931 */         paramStringBuffer.append("Description                                Price     MMMC  24X7  CSU" + NEWLINE);
/*  7932 */         paramStringBuffer.append("-----------------------------------------  --------  ----  ----  ---" + NEWLINE);
/*  7933 */         Set set = this.charges_NewModels_TM.keySet();
/*  7934 */         Iterator<String> iterator = set.iterator();
/*  7935 */         while (iterator.hasNext()) {
/*       */           
/*  7937 */           String str = iterator.next();
/*  7938 */           str2 = (String)this.charges_NewModels_TM.get(str);
/*  7939 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  7941 */           paramStringBuffer.append(setString(parseString(str, 3, "<:>"), 41));
/*  7942 */           paramStringBuffer.append("  ");
/*  7943 */           paramStringBuffer.append("   $XXXX");
/*  7944 */           paramStringBuffer.append("  ");
/*  7945 */           paramStringBuffer.append("$XXX");
/*  7946 */           paramStringBuffer.append("  ");
/*  7947 */           paramStringBuffer.append("$XXX");
/*  7948 */           paramStringBuffer.append("  ");
/*  7949 */           paramStringBuffer.append(parseString(str, 5, "<:>"));
/*  7950 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  7952 */           str1 = str2;
/*       */         } 
/*  7954 */         if (!str2.equals("WW"))
/*       */         {
/*  7956 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*  7958 */         if (paramBoolean == true)
/*       */         {
/*  7960 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */         }
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat1(TreeMap paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8099 */     if (paramTreeMap.size() > 0) {
/*       */       
/*  8101 */       String str1 = "";
/*  8102 */       String str2 = "";
/*  8103 */       String str3 = "";
/*  8104 */       String str4 = "";
/*  8105 */       String str5 = "";
/*  8106 */       String str6 = "";
/*       */       
/*  8108 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  8112 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  8113 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*  8114 */         paramStringBuffer.append("                             Feature  Purchase        ESA   ESA" + NEWLINE);
/*  8115 */         paramStringBuffer.append("Description                  Number   Price     MMMC  24X7  9X5   CSU" + NEWLINE);
/*  8116 */         paramStringBuffer.append("---------------------------- -------  --------  ----  ----  ----  ---" + NEWLINE);
/*  8117 */         Set set = paramTreeMap.keySet();
/*  8118 */         Iterator<String> iterator = set.iterator();
/*  8119 */         while (iterator.hasNext()) {
/*       */           
/*  8121 */           String str = iterator.next();
/*  8122 */           str2 = (String)paramTreeMap.get(str);
/*  8123 */           str4 = parseString(str, 1, "<:>");
/*  8124 */           str6 = parseString(str, 2, "<:>");
/*  8125 */           if (!str4.equals(str3))
/*       */           {
/*  8127 */             setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           }
/*  8129 */           if (parseString(str, 8, "<:>").length() > 0)
/*       */           {
/*  8131 */             if (!str4.equals(str3)) {
/*       */               
/*  8133 */               String[] arrayOfString = getStringTokens(parseString(str, 8, "<:>"), NEWLINE);
/*  8134 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*       */                 
/*  8136 */                 if (arrayOfString[b].length() > 58) {
/*       */                   
/*  8138 */                   String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  8139 */                   for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                   {
/*  8141 */                     paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                   }
/*       */                 }
/*       */                 else {
/*       */                   
/*  8146 */                   paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */                 } 
/*       */               } 
/*       */             } 
/*       */           }
/*       */           
/*  8152 */           if (!str4.equals(str3)) {
/*       */             
/*  8154 */             paramStringBuffer.append(parseString(str, 4, "<:>"));
/*  8155 */             paramStringBuffer.append("  ");
/*  8156 */             paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  8157 */             paramStringBuffer.append("    ");
/*  8158 */             paramStringBuffer.append(parseString(str, 9, "<:>"));
/*  8159 */             paramStringBuffer.append("  ");
/*  8160 */             paramStringBuffer.append(parseString(str, 10, "<:>"));
/*  8161 */             paramStringBuffer.append("  ");
/*  8162 */             paramStringBuffer.append(parseString(str, 11, "<:>"));
/*  8163 */             paramStringBuffer.append("  ");
/*  8164 */             paramStringBuffer.append(parseString(str, 12, "<:>"));
/*  8165 */             paramStringBuffer.append("  ");
/*  8166 */             paramStringBuffer.append(parseString(str, 6, "<:>"));
/*  8167 */             paramStringBuffer.append(NEWLINE);
/*       */           } 
/*       */           
/*  8170 */           str1 = str2;
/*  8171 */           str3 = str4;
/*  8172 */           str5 = str6;
/*       */         } 
/*  8174 */         if (!str2.equals("WW"))
/*       */         {
/*  8176 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */         
/*  8179 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat2(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8205 */     Iterator<String> iterator = this.machineTypeTS.iterator();
/*       */     
/*  8207 */     while (iterator.hasNext()) {
/*       */       
/*  8209 */       String str = iterator.next();
/*       */       
/*  8211 */       if (2 == this.format) {
/*       */         
/*  8213 */         retrieveChargesFeaturesFormat2_MTM(str, paramBoolean, paramStringBuffer);
/*       */       }
/*  8215 */       else if (3 == this.format) {
/*       */         
/*  8217 */         retrieveChargesFeaturesFormat3_MTM(str, paramBoolean, paramStringBuffer);
/*       */       } 
/*  8219 */       retrieveChargesFeaturesFormat2_Feature(str, paramBoolean, paramStringBuffer);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat2_MTM(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8232 */     TreeMap<Object, Object> treeMap1 = new TreeMap<>();
/*  8233 */     TreeMap<Object, Object> treeMap2 = new TreeMap<>();
/*  8234 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/*  8236 */     if (this.charges_NewModels_NewFC_TM.size() > 0) {
/*       */       
/*  8238 */       Set set = this.charges_NewModels_NewFC_TM.keySet();
/*  8239 */       Iterator<String> iterator = set.iterator();
/*  8240 */       while (iterator.hasNext()) {
/*       */         
/*  8242 */         String str = iterator.next();
/*       */         
/*  8244 */         if (paramString.equals(parseString(str, 1, "<:>"))) {
/*       */           
/*  8246 */           String str1 = parseString(str, 3, "<:>");
/*  8247 */           String str2 = parseString(str, 10, "<:>");
/*  8248 */           treeSet.add(str1 + "<:>" + str2);
/*  8249 */           treeMap1.put(str, this.charges_NewModels_NewFC_TM.get(str));
/*       */         } 
/*       */       } 
/*       */     } 
/*  8253 */     retrieveChargesFeaturesFormat2_MTM(paramString, treeSet, 1, treeMap1, paramBoolean, paramStringBuffer);
/*  8254 */     treeSet.clear();
/*       */     
/*  8256 */     if (this.charges_NewModels_ExistingFC_TM.size() > 0) {
/*       */       
/*  8258 */       Set set = this.charges_NewModels_ExistingFC_TM.keySet();
/*  8259 */       Iterator<String> iterator = set.iterator();
/*  8260 */       while (iterator.hasNext()) {
/*       */         
/*  8262 */         String str = iterator.next();
/*       */         
/*  8264 */         if (paramString.equals(parseString(str, 1, "<:>"))) {
/*       */           
/*  8266 */           String str1 = parseString(str, 3, "<:>");
/*  8267 */           treeSet.add(str1);
/*  8268 */           treeMap2.put(str, this.charges_NewModels_ExistingFC_TM.get(str));
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  8273 */     retrieveChargesFeaturesFormat2_MTM(paramString, treeSet, 2, treeMap2, paramBoolean, paramStringBuffer);
/*       */     
/*  8275 */     treeSet.clear();
/*  8276 */     treeSet = null;
/*  8277 */     treeMap1.clear();
/*  8278 */     treeMap1 = null;
/*  8279 */     treeMap2.clear();
/*  8280 */     treeMap2 = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat2_MTM(String paramString, TreeSet paramTreeSet, int paramInt, TreeMap paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8295 */     if (paramTreeMap.size() > 0) {
/*       */       String[] arrayOfString;
/*       */       byte b;
/*  8298 */       String str = "";
/*       */       
/*  8300 */       switch (paramInt) {
/*       */         
/*       */         case 1:
/*  8303 */           if (this.brand.equals("pSeries")) {
/*       */             
/*  8305 */             str = "of the IBM RS/6000 or pSeries " + paramString + " machine type:";
/*       */           }
/*  8307 */           else if (this.brand.equals("xSeries")) {
/*       */             
/*  8309 */             str = "of the IBM xSeries " + paramString + " machine type:";
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*       */           }
/*  8315 */           else if (this.brand.equals("totalStorage")) {
/*       */             
/*  8317 */             str = "of the Total Storage " + paramString + " machine type:";
/*       */           } 
/*       */           
/*  8320 */           str = ":p.The following are newly announced features on the specified models " + str;
/*  8321 */           arrayOfString = extractStringLines(str, 70);
/*  8322 */           for (b = 0; b < arrayOfString.length; b++)
/*       */           {
/*  8324 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */         
/*       */         case 2:
/*  8329 */           str = ":p.The following are features already announced for the " + paramString + " machine type:";
/*  8330 */           arrayOfString = extractStringLines(str, 70);
/*  8331 */           for (b = 0; b < arrayOfString.length; b++)
/*       */           {
/*  8333 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  8341 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/*       */       
/*  8343 */       paramStringBuffer.append(".fo off" + NEWLINE);
/*  8344 */       paramStringBuffer.append(".in 2" + NEWLINE);
/*       */       
/*  8346 */       paramStringBuffer.append("                                             Minimum" + NEWLINE);
/*  8347 */       paramStringBuffer.append("                                             Maint.  Initial/" + NEWLINE);
/*  8348 */       paramStringBuffer.append("                     Model  Feature Purchase Charge  MES/         RP" + NEWLINE);
/*  8349 */       paramStringBuffer.append("Description          Number Numbers Price    Monthly Both     CSU MES" + NEWLINE);
/*  8350 */       paramStringBuffer.append("-------------------- ------ ------- -------- ------- -------- --- ---" + NEWLINE);
/*       */       
/*  8352 */       paramStringBuffer.append(".fo on" + NEWLINE);
/*  8353 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */       
/*  8355 */       if (paramTreeSet.size() > 0) {
/*       */         
/*  8357 */         Iterator<String> iterator = paramTreeSet.iterator();
/*       */         
/*  8359 */         paramStringBuffer.append(".pa" + NEWLINE);
/*  8360 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  8361 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*       */         
/*  8363 */         while (iterator.hasNext()) {
/*       */           
/*  8365 */           String str1 = iterator.next();
/*  8366 */           String str2 = parseString(str1, 1, "<:>");
/*  8367 */           String[] arrayOfString1 = extractStringLines(parseString(str1, 2, "<:>"), 51);
/*  8368 */           for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */             
/*  8370 */             paramStringBuffer.append(arrayOfString1[b1]);
/*  8371 */             paramStringBuffer.append(NEWLINE);
/*       */           } 
/*  8373 */           paramStringBuffer.append(setString("", 22));
/*  8374 */           paramStringBuffer.append(str2);
/*  8375 */           paramStringBuffer.append(setString("", 12));
/*  8376 */           paramStringBuffer.append("XXXX.XX");
/*  8377 */           paramStringBuffer.append(" ");
/*  8378 */           paramStringBuffer.append("XXXX.XX");
/*  8379 */           paramStringBuffer.append(setString("", 10));
/*  8380 */           paramStringBuffer.append(this.productNumber_NewModels_HT.get(str2));
/*  8381 */           paramStringBuffer.append(NEWLINE);
/*       */         } 
/*       */       } 
/*       */ 
/*       */ 
/*       */       
/*  8387 */       retrieveChargesFeaturesFormat2_MTM(paramString, paramTreeMap, paramBoolean, paramStringBuffer);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat2_MTM(String paramString, TreeMap paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8401 */     if (paramTreeMap.size() > 0) {
/*       */       
/*  8403 */       String str1 = "";
/*  8404 */       String str2 = "";
/*  8405 */       String str3 = "";
/*  8406 */       String str4 = "";
/*  8407 */       String str5 = "";
/*  8408 */       String str6 = "";
/*       */       
/*  8410 */       String[] arrayOfString = null;
/*  8411 */       boolean bool = false;
/*  8412 */       Set set = paramTreeMap.keySet();
/*  8413 */       Iterator<String> iterator = set.iterator();
/*       */       
/*  8415 */       while (iterator.hasNext()) {
/*       */         
/*  8417 */         String str = iterator.next();
/*  8418 */         str2 = (String)paramTreeMap.get(str);
/*  8419 */         str4 = parseString(str, 2, "<:>");
/*  8420 */         str6 = paramString;
/*  8421 */         if (!str4.equals(str3) || !str6.equals(str5)) {
/*       */           
/*  8423 */           arrayOfString = extractStringLines(parseString(str, 4, "<:>"), 51);
/*  8424 */           bool = true;
/*       */         } 
/*  8426 */         setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */         
/*  8428 */         if (parseString(str, 8, "<:>").length() > 0)
/*       */         {
/*  8430 */           if (!str4.equals(str3)) {
/*       */             
/*  8432 */             String[] arrayOfString1 = getStringTokens(parseString(str, 8, "<:>"), NEWLINE);
/*  8433 */             for (byte b = 0; b < arrayOfString1.length; b++) {
/*       */               
/*  8435 */               if (arrayOfString1[b].length() > 58) {
/*       */                 
/*  8437 */                 String[] arrayOfString2 = extractStringLines(arrayOfString1[b], 58);
/*  8438 */                 for (byte b1 = 0; b1 < arrayOfString2.length; b1++)
/*       */                 {
/*  8440 */                   paramStringBuffer.append(":hp2." + arrayOfString2[b1] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  8445 */                 paramStringBuffer.append(":hp2." + arrayOfString1[b] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         }
/*       */         
/*  8451 */         if (bool) {
/*       */           
/*  8453 */           for (byte b = 0; b < arrayOfString.length; b++)
/*       */           {
/*  8455 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/*  8457 */           bool = false;
/*       */         } 
/*       */         
/*  8460 */         paramStringBuffer.append(setString("", 22));
/*  8461 */         paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  8462 */         paramStringBuffer.append("      ");
/*       */         
/*  8464 */         if (!str4.equals(str3)) {
/*       */           
/*  8466 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*       */         }
/*       */         else {
/*       */           
/*  8470 */           paramStringBuffer.append("    ");
/*       */         } 
/*  8472 */         paramStringBuffer.append("  ");
/*       */         
/*  8474 */         if (!str4.equals(str3)) {
/*       */           
/*  8476 */           paramStringBuffer.append(parseString(str, 9, "<:>"));
/*       */         }
/*       */         else {
/*       */           
/*  8480 */           paramStringBuffer.append("       ");
/*       */         } 
/*  8482 */         paramStringBuffer.append("         ");
/*  8483 */         paramStringBuffer.append(parseString(str, 5, "<:>"));
/*  8484 */         paramStringBuffer.append("  ");
/*  8485 */         paramStringBuffer.append(parseString(str, 6, "<:>"));
/*  8486 */         paramStringBuffer.append(" ");
/*  8487 */         paramStringBuffer.append(parseString(str, 7, "<:>"));
/*  8488 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  8490 */         str1 = str2;
/*  8491 */         str3 = str4;
/*  8492 */         str5 = str6;
/*       */       } 
/*  8494 */       if (!str2.equals("WW"))
/*       */       {
/*  8496 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/*  8499 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  8500 */       paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat2_Feature(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8513 */     TreeMap<Object, Object> treeMap1 = new TreeMap<>();
/*  8514 */     TreeMap<Object, Object> treeMap2 = new TreeMap<>();
/*       */     
/*  8516 */     if (this.charges_ExistingModels_NewFC_TM.size() > 0) {
/*       */       
/*  8518 */       Set set = this.charges_ExistingModels_NewFC_TM.keySet();
/*  8519 */       Iterator<String> iterator = set.iterator();
/*  8520 */       while (iterator.hasNext()) {
/*       */         
/*  8522 */         String str = iterator.next();
/*       */         
/*  8524 */         if (paramString.equals(parseString(str, 1, "<:>")))
/*       */         {
/*  8526 */           treeMap1.put(str, this.charges_ExistingModels_NewFC_TM.get(str));
/*       */         }
/*       */       } 
/*       */     } 
/*       */     
/*  8531 */     if (this.charges_ExistingModels_ExistingFC_TM.size() > 0) {
/*       */       
/*  8533 */       Set set = this.charges_ExistingModels_ExistingFC_TM.keySet();
/*  8534 */       Iterator<String> iterator = set.iterator();
/*  8535 */       while (iterator.hasNext()) {
/*       */         
/*  8537 */         String str = iterator.next();
/*       */         
/*  8539 */         if (paramString.equals(parseString(str, 1, "<:>")))
/*       */         {
/*  8541 */           treeMap2.put(str, this.charges_ExistingModels_ExistingFC_TM.get(str));
/*       */         }
/*       */       } 
/*       */     } 
/*       */     
/*  8546 */     retrieveChargesFeaturesFormat2_Feature(1, treeMap1, paramString, paramBoolean, paramStringBuffer);
/*  8547 */     retrieveChargesFeaturesFormat2_Feature(2, treeMap2, paramString, paramBoolean, paramStringBuffer);
/*       */     
/*  8549 */     treeMap1.clear();
/*  8550 */     treeMap1 = null;
/*  8551 */     treeMap2.clear();
/*  8552 */     treeMap2 = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat2_Feature(int paramInt, TreeMap paramTreeMap, String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8566 */     if (paramTreeMap.size() > 0) {
/*       */       String[] arrayOfString1; byte b;
/*  8568 */       String str1 = "";
/*  8569 */       String str2 = "";
/*  8570 */       String str3 = "";
/*  8571 */       String str4 = "";
/*  8572 */       String str5 = "";
/*  8573 */       String str6 = "";
/*  8574 */       String str7 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  8582 */       TreeSet<String> treeSet = new TreeSet();
/*  8583 */       Set set2 = paramTreeMap.keySet();
/*  8584 */       Iterator<String> iterator2 = set2.iterator();
/*  8585 */       while (iterator2.hasNext()) {
/*       */         
/*  8587 */         String str8 = iterator2.next();
/*  8588 */         String str9 = parseString(str8, 3, "<:>");
/*  8589 */         treeSet.add(str9);
/*       */       } 
/*       */ 
/*       */       
/*  8593 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  8595 */         str7 = "of the IBM RS/6000 or pSeries " + paramString + " machine type:";
/*       */       }
/*  8597 */       else if (this.brand.equals("xSeries")) {
/*       */         
/*  8599 */         str7 = "of the IBM xSeries " + paramString + " machine type:";
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*  8605 */       else if (this.brand.equals("totalStorage")) {
/*       */         
/*  8607 */         str7 = "of the Total Storage " + paramString + " machine type:";
/*       */       } 
/*       */       
/*  8610 */       switch (paramInt) {
/*       */         
/*       */         case 1:
/*  8613 */           str7 = ":p.The following are newly announced features on the specified models " + str7;
/*  8614 */           arrayOfString1 = extractStringLines(str7, 70);
/*  8615 */           for (b = 0; b < arrayOfString1.length; b++)
/*       */           {
/*  8617 */             paramStringBuffer.append(arrayOfString1[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */         
/*       */         case 2:
/*  8622 */           str7 = ":p.The following are features already announced for the " + paramString + " machine type:";
/*  8623 */           arrayOfString1 = extractStringLines(str7, 70);
/*  8624 */           for (b = 0; b < arrayOfString1.length; b++)
/*       */           {
/*  8626 */             paramStringBuffer.append(arrayOfString1[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  8634 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/*       */       
/*  8636 */       paramStringBuffer.append(".fo off" + NEWLINE);
/*  8637 */       paramStringBuffer.append(".in 2" + NEWLINE);
/*       */       
/*  8639 */       paramStringBuffer.append("                                                     Initial/" + NEWLINE);
/*  8640 */       paramStringBuffer.append("                                                     MES/" + NEWLINE);
/*  8641 */       paramStringBuffer.append("Description                  Model  Feature Purchase Both/        RP" + NEWLINE);
/*       */ 
/*       */ 
/*       */       
/*  8645 */       paramStringBuffer.append("                             Number Numbers Price    Support  CSU MES" + NEWLINE);
/*  8646 */       paramStringBuffer.append("---------------------------- ------ ------- -------- -------- --- ---" + NEWLINE);
/*       */       
/*  8648 */       paramStringBuffer.append(".fo on" + NEWLINE);
/*  8649 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */       
/*  8651 */       String[] arrayOfString2 = null;
/*  8652 */       boolean bool1 = false;
/*  8653 */       boolean bool2 = false;
/*  8654 */       Set set1 = paramTreeMap.keySet();
/*  8655 */       Iterator<String> iterator1 = set1.iterator();
/*  8656 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*  8657 */       paramStringBuffer.append(".kp off" + NEWLINE);
/*  8658 */       while (iterator1.hasNext()) {
/*       */         
/*  8660 */         String str = iterator1.next();
/*  8661 */         str2 = (String)paramTreeMap.get(str);
/*  8662 */         str4 = parseString(str, 2, "<:>");
/*  8663 */         str6 = paramString;
/*  8664 */         if (!str4.equals(str3) || !str6.equals(str5)) {
/*       */           
/*  8666 */           arrayOfString2 = extractStringLines(parseString(str, 4, "<:>"), 51);
/*  8667 */           bool2 = true;
/*       */         } 
/*  8669 */         setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */         
/*  8671 */         if (parseString(str, 8, "<:>").length() > 0)
/*       */         {
/*  8673 */           if (!str4.equals(str3)) {
/*       */             
/*  8675 */             String[] arrayOfString = getStringTokens(parseString(str, 8, "<:>"), NEWLINE);
/*  8676 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */               
/*  8678 */               if (arrayOfString[b1].length() > 58) {
/*       */                 
/*  8680 */                 String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  8681 */                 for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */                 {
/*  8683 */                   paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  8688 */                 paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         }
/*       */         
/*  8694 */         if (bool2) {
/*       */           
/*  8696 */           for (byte b1 = 0; b1 < arrayOfString2.length; b1++)
/*       */           {
/*  8698 */             paramStringBuffer.append(arrayOfString2[b1] + NEWLINE);
/*       */           }
/*  8700 */           bool2 = false;
/*       */         } 
/*       */         
/*  8703 */         paramStringBuffer.append(setString("", 30));
/*  8704 */         paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  8705 */         paramStringBuffer.append("      ");
/*       */         
/*  8707 */         if (!str4.equals(str3)) {
/*       */           
/*  8709 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*       */         }
/*       */         else {
/*       */           
/*  8713 */           paramStringBuffer.append("    ");
/*       */         } 
/*  8715 */         paramStringBuffer.append("  ");
/*       */         
/*  8717 */         if (!str4.equals(str3)) {
/*       */           
/*  8719 */           paramStringBuffer.append(parseString(str, 9, "<:>"));
/*       */         }
/*       */         else {
/*       */           
/*  8723 */           paramStringBuffer.append("       ");
/*       */         } 
/*  8725 */         paramStringBuffer.append(" ");
/*  8726 */         paramStringBuffer.append(parseString(str, 5, "<:>"));
/*  8727 */         paramStringBuffer.append("  ");
/*  8728 */         paramStringBuffer.append(parseString(str, 6, "<:>"));
/*  8729 */         paramStringBuffer.append(" ");
/*  8730 */         paramStringBuffer.append(parseString(str, 7, "<:>"));
/*  8731 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  8733 */         str1 = str2;
/*  8734 */         str3 = str4;
/*  8735 */         str5 = str6;
/*       */       } 
/*  8737 */       if (!str2.equals("WW"))
/*       */       {
/*  8739 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/*  8742 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  8743 */       paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesMTMConversions(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8755 */     if (this.productNumber_MTM_Conversions_TM.size() > 0) {
/*       */       
/*  8757 */       String str1 = "";
/*  8758 */       String str2 = "";
/*       */ 
/*       */       
/*  8761 */       if (paramBoolean == true) {
/*       */         
/*  8763 */         paramStringBuffer.append(":h3.Type/Model Conversions" + NEWLINE);
/*  8764 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  8765 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  8769 */         paramStringBuffer.append("<h3>Type/Model Conversions</h3>" + NEWLINE);
/*       */       } 
/*       */       
/*  8772 */       paramStringBuffer.append("   From       To      Parts     Purchase" + NEWLINE);
/*  8773 */       paramStringBuffer.append("Type Model Type Model Returned  Price" + NEWLINE);
/*  8774 */       paramStringBuffer.append("---- ----- ---- ----- --------  --------" + NEWLINE);
/*  8775 */       Set set = this.productNumber_MTM_Conversions_TM.keySet();
/*  8776 */       Iterator<String> iterator = set.iterator();
/*  8777 */       while (iterator.hasNext()) {
/*       */         
/*  8779 */         String str = iterator.next();
/*  8780 */         str2 = (String)this.productNumber_MTM_Conversions_TM.get(str);
/*  8781 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */         
/*  8783 */         paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  8784 */         paramStringBuffer.append("  ");
/*  8785 */         paramStringBuffer.append(parseString(str, 4, "<:>"));
/*  8786 */         paramStringBuffer.append("  ");
/*  8787 */         paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  8788 */         paramStringBuffer.append("  ");
/*  8789 */         paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  8790 */         paramStringBuffer.append("     ");
/*  8791 */         paramStringBuffer.append(parseString(str, 5, "<:>"));
/*  8792 */         paramStringBuffer.append("       ");
/*  8793 */         paramStringBuffer.append("$XXXX");
/*  8794 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  8796 */         str1 = str2;
/*       */       } 
/*  8798 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  8804 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  8810 */       if (paramBoolean == true)
/*       */       {
/*  8812 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesModelConversions(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8829 */     if (this.productNumber_Model_Conversions_TM.size() > 0) {
/*       */       
/*  8831 */       String str1 = "";
/*  8832 */       String str2 = "";
/*       */ 
/*       */       
/*  8835 */       if (paramBoolean == true) {
/*       */         
/*  8837 */         paramStringBuffer.append(":h3.Model Conversions" + NEWLINE);
/*  8838 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  8839 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  8843 */         paramStringBuffer.append("<h3>Model Conversions</h3>" + NEWLINE);
/*       */       } 
/*       */       
/*  8846 */       paramStringBuffer.append("      From   To      Parts     Purchase" + NEWLINE);
/*  8847 */       paramStringBuffer.append("Type  Model  Model   Returned  Price" + NEWLINE);
/*  8848 */       paramStringBuffer.append("----  -----  -----   --------  --------" + NEWLINE);
/*  8849 */       Set set = this.productNumber_Model_Conversions_TM.keySet();
/*  8850 */       Iterator<String> iterator = set.iterator();
/*  8851 */       while (iterator.hasNext()) {
/*       */         
/*  8853 */         String str = iterator.next();
/*  8854 */         str2 = (String)this.productNumber_Model_Conversions_TM.get(str);
/*  8855 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */         
/*  8857 */         paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  8858 */         paramStringBuffer.append("   ");
/*  8859 */         paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  8860 */         paramStringBuffer.append("    ");
/*  8861 */         paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  8862 */         paramStringBuffer.append("       ");
/*  8863 */         paramStringBuffer.append(parseString(str, 4, "<:>"));
/*  8864 */         paramStringBuffer.append("       ");
/*  8865 */         paramStringBuffer.append("$XXXX");
/*  8866 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  8868 */         str1 = str2;
/*       */       } 
/*  8870 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  8876 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  8882 */       if (paramBoolean == true)
/*       */       {
/*  8884 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeatureConverstionsFormat1(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8901 */     if (this.charges_Feature_Conversions_TM.size() > 0) {
/*       */       
/*  8903 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  8904 */       String str1 = "";
/*  8905 */       String str2 = "";
/*  8906 */       String str3 = "";
/*  8907 */       String str4 = "";
/*       */       
/*  8909 */       Set set = this.charges_Feature_Conversions_TM.keySet();
/*  8910 */       Iterator<String> iterator = set.iterator();
/*  8911 */       while (iterator.hasNext()) {
/*       */         
/*  8913 */         str1 = iterator.next();
/*  8914 */         str2 = parseString(str1, 4, "<:>");
/*  8915 */         str3 = parseString(str1, 7, "<:>");
/*  8916 */         str4 = str2 + "<:>" + str3 + "<:>" + parseString(str1, 10, "<:>") + "<:>" + parseString(str1, 11, "<:>");
/*  8917 */         treeMap.put(str4, this.charges_Feature_Conversions_TM.get(str1));
/*       */       } 
/*       */       
/*  8920 */       retrieveChargesFeatureConverstionsFormat1(paramBoolean, paramStringBuffer, treeMap);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeatureConverstionsFormat1(boolean paramBoolean, StringBuffer paramStringBuffer, TreeMap paramTreeMap) {
/*  8933 */     String str1 = "";
/*  8934 */     String str2 = "";
/*       */     
/*  8936 */     if (1 == this.format) {
/*       */ 
/*       */ 
/*       */       
/*  8940 */       paramStringBuffer.append(":h3.Conversions" + NEWLINE);
/*  8941 */       paramStringBuffer.append(":p." + NEWLINE);
/*  8942 */       paramStringBuffer.append(":h5.Feature Conversions" + NEWLINE);
/*  8943 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*  8944 */       paramStringBuffer.append(".kp off" + NEWLINE);
/*  8945 */       paramStringBuffer.append("               Parts       Purchase" + NEWLINE);
/*  8946 */       paramStringBuffer.append("From:   To:    Returned    Price" + NEWLINE);
/*  8947 */       paramStringBuffer.append("----    ---    --------    --------" + NEWLINE);
/*  8948 */       Set set = paramTreeMap.keySet();
/*  8949 */       Iterator<String> iterator = set.iterator();
/*  8950 */       while (iterator.hasNext()) {
/*       */         
/*  8952 */         String str = iterator.next();
/*  8953 */         str2 = (String)paramTreeMap.get(str);
/*  8954 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  8955 */         if (parseString(str, 4, "<:>").length() > 0) {
/*       */           
/*  8957 */           if (!paramBoolean) {
/*       */ 
/*       */             
/*  8960 */             String[] arrayOfString = getStringTokens(parseString(str, 4, "<:>"), NEWLINE);
/*  8961 */             for (byte b = 0; b < arrayOfString.length; b++)
/*       */             {
/*  8963 */               paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */             }
/*       */           } 
/*       */           
/*  8967 */           if (paramBoolean == true) {
/*       */             
/*  8969 */             String[] arrayOfString = getStringTokens(parseString(str, 4, "<:>"), NEWLINE);
/*  8970 */             for (byte b = 0; b < arrayOfString.length; b++) {
/*       */               
/*  8972 */               if (arrayOfString[b].length() > 58) {
/*       */                 
/*  8974 */                 String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  8975 */                 for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                 {
/*  8977 */                   paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  8982 */                 paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         
/*  8988 */         paramStringBuffer.append(parseString(str, 2, "<:>"));
/*  8989 */         paramStringBuffer.append("    ");
/*  8990 */         paramStringBuffer.append(parseString(str, 1, "<:>"));
/*  8991 */         paramStringBuffer.append("      ");
/*  8992 */         paramStringBuffer.append(parseString(str, 3, "<:>"));
/*  8993 */         paramStringBuffer.append("         $XXXX");
/*  8994 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  8996 */         str1 = str2;
/*       */       } 
/*  8998 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9004 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9010 */       if (paramBoolean == true)
/*       */       {
/*  9012 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9018 */       paramStringBuffer.append(":p.:hp2.US, LA, CAN--->:ehp2." + NEWLINE);
/*  9019 */       paramStringBuffer.append(":p.:hp2.Parts Return::ehp2.  Parts removed or replaced as part of MES upgrades" + NEWLINE);
/*  9020 */       paramStringBuffer.append("or conversions become the property of IBM and must be returned." + NEWLINE);
/*  9021 */       paramStringBuffer.append(".br;:hp2.<---US, LA, CAN:ehp2." + NEWLINE + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeatureConverstionsFormat2(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9033 */     if (this.charges_Feature_Conversions_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9042 */       if (paramBoolean == true) {
/*       */         
/*  9044 */         paramStringBuffer.append(":h3.Feature Conversions" + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  9048 */         paramStringBuffer.append("<h3>Feature Conversions</h3>" + NEWLINE);
/*       */       } 
/*       */ 
/*       */       
/*  9052 */       TreeSet<String> treeSet = new TreeSet();
/*  9053 */       Set<String> set = this.charges_Feature_Conversions_TM.keySet();
/*  9054 */       Iterator<String> iterator1 = set.iterator();
/*  9055 */       while (iterator1.hasNext()) {
/*       */         
/*  9057 */         String str = iterator1.next();
/*  9058 */         treeSet.add(parseString(str, 1, "<:>"));
/*       */       } 
/*       */       
/*  9061 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  9062 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  9064 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  9067 */       byte b1 = 0;
/*  9068 */       Iterator<String> iterator2 = treeSet.iterator();
/*  9069 */       while (iterator2.hasNext()) {
/*       */         
/*  9071 */         String str = iterator2.next();
/*       */         
/*  9073 */         iterator1 = set.iterator();
/*  9074 */         while (iterator1.hasNext()) {
/*       */           
/*  9076 */           String str1 = iterator1.next();
/*       */           
/*  9078 */           if (str.equals(parseString(str1, 1, "<:>")))
/*       */           {
/*  9080 */             arrayOfTreeMap[b1].put(str1, this.charges_Feature_Conversions_TM.get(str1));
/*       */           }
/*       */         } 
/*  9083 */         b1++;
/*       */       } 
/*       */       
/*  9086 */       for (b2 = 0; b2 < arrayOfTreeMap.length; b2++)
/*       */       {
/*  9088 */         retrieveChargesFeatureConversionsFormat2(paramStringBuffer, paramBoolean, arrayOfTreeMap[b2]);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeatureConversionsFormat2(StringBuffer paramStringBuffer, boolean paramBoolean, TreeMap paramTreeMap) {
/*  9102 */     String str1 = "";
/*  9103 */     String str2 = "";
/*  9104 */     String str3 = parseString((String)paramTreeMap.firstKey(), 1, "<:>");
/*  9105 */     if (paramBoolean == true) {
/*       */       
/*  9107 */       paramStringBuffer.append(":p." + NEWLINE);
/*  9108 */       paramStringBuffer.append(":h4." + str3 + NEWLINE);
/*  9109 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*  9110 */       paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  9114 */       paramStringBuffer.append("<h4>" + str3 + "</h4>" + NEWLINE);
/*       */     } 
/*       */     
/*  9117 */     if (2 == this.format || 3 == this.format) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9125 */       paramStringBuffer.append("                                                  Parts      Purchase" + NEWLINE);
/*  9126 */       paramStringBuffer.append("From FC                  To FC                    Returned   Price" + NEWLINE);
/*  9127 */       paramStringBuffer.append("------------------------ ------------------------ ---------  --------" + NEWLINE);
/*  9128 */       int i = 0;
/*  9129 */       byte b = 0;
/*  9130 */       boolean bool = false;
/*  9131 */       Set set = paramTreeMap.keySet();
/*  9132 */       Iterator<String> iterator = set.iterator();
/*  9133 */       while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9139 */         String str6 = iterator.next();
/*  9140 */         str2 = (String)paramTreeMap.get(str6);
/*  9141 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  9142 */         if (parseString(str6, 11, "<:>").length() > 0) {
/*       */           
/*  9144 */           if (!paramBoolean) {
/*       */ 
/*       */             
/*  9147 */             String[] arrayOfString = getStringTokens(parseString(str6, 11, "<:>"), NEWLINE);
/*  9148 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++)
/*       */             {
/*  9150 */               paramStringBuffer.append(arrayOfString[b1] + NEWLINE);
/*       */             }
/*       */           } 
/*       */           
/*  9154 */           if (paramBoolean == true) {
/*       */             
/*  9156 */             String[] arrayOfString = getStringTokens(parseString(str6, 11, "<:>"), NEWLINE);
/*  9157 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */               
/*  9159 */               if (arrayOfString[b1].length() > 58) {
/*       */                 
/*  9161 */                 String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  9162 */                 for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */                 {
/*  9164 */                   paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  9169 */                 paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         
/*  9175 */         String str4 = parseString(str6, 7, "<:>") + " - " + parseString(str6, 9, "<:>");
/*  9176 */         String str5 = parseString(str6, 4, "<:>") + " - " + parseString(str6, 8, "<:>");
/*  9177 */         String[] arrayOfString1 = extractStringLines(str4, 24);
/*  9178 */         String[] arrayOfString2 = extractStringLines(str5, 24);
/*  9179 */         i = 0;
/*  9180 */         if (arrayOfString1.length > arrayOfString2.length) {
/*       */           
/*  9182 */           i = arrayOfString1.length;
/*       */         }
/*       */         else {
/*       */           
/*  9186 */           i = arrayOfString2.length;
/*       */         } 
/*  9188 */         b = 0;
/*  9189 */         bool = false;
/*       */         
/*       */         while (true) {
/*  9192 */           if (false == bool) {
/*       */ 
/*       */             
/*  9195 */             paramStringBuffer.append(setString(arrayOfString1[b], 24));
/*  9196 */             paramStringBuffer.append(" ");
/*  9197 */             paramStringBuffer.append(setString(arrayOfString2[b], 24));
/*  9198 */             paramStringBuffer.append("    ");
/*  9199 */             paramStringBuffer.append(parseString(str6, 10, "<:>"));
/*  9200 */             paramStringBuffer.append("        $XXXX");
/*  9201 */             paramStringBuffer.append(NEWLINE);
/*  9202 */             bool = true;
/*  9203 */             b++;
/*       */           }
/*       */           else {
/*       */             
/*  9207 */             if (b < arrayOfString1.length) {
/*       */               
/*  9209 */               paramStringBuffer.append(setString(arrayOfString1[b], 24));
/*       */             }
/*       */             else {
/*       */               
/*  9213 */               paramStringBuffer.append(setString(" ", 24));
/*       */             } 
/*  9215 */             paramStringBuffer.append(" ");
/*  9216 */             if (b < arrayOfString2.length) {
/*       */               
/*  9218 */               paramStringBuffer.append(setString(arrayOfString2[b], 24));
/*       */             }
/*       */             else {
/*       */               
/*  9222 */               paramStringBuffer.append(setString(" ", 24));
/*       */             } 
/*  9224 */             paramStringBuffer.append(NEWLINE);
/*  9225 */             b++;
/*       */           } 
/*  9227 */           if (b >= i)
/*  9228 */             str1 = str2; 
/*       */         } 
/*  9230 */       }  if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9236 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9242 */       if (paramBoolean == true)
/*       */       {
/*  9244 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSalesManual(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9261 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  9262 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  9263 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9270 */     if (this.salesManual_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9276 */       TreeSet<String> treeSet = new TreeSet();
/*  9277 */       Set<String> set = this.salesManual_TM.keySet();
/*  9278 */       Iterator<String> iterator2 = set.iterator();
/*  9279 */       while (iterator2.hasNext()) {
/*       */         
/*  9281 */         String str = iterator2.next();
/*  9282 */         treeSet.add(parseString(str, 1, "<:>"));
/*       */       } 
/*       */       
/*  9285 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  9286 */       for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  9288 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  9291 */       byte b1 = 0;
/*  9292 */       Iterator<String> iterator1 = treeSet.iterator();
/*  9293 */       while (iterator1.hasNext()) {
/*       */         
/*  9295 */         String str = iterator1.next();
/*       */         
/*  9297 */         iterator2 = set.iterator();
/*  9298 */         while (iterator2.hasNext()) {
/*       */           
/*  9300 */           String str1 = iterator2.next();
/*       */           
/*  9302 */           if (str.equals(parseString(str1, 1, "<:>")))
/*       */           {
/*  9304 */             arrayOfTreeMap[b1].put(str1, this.salesManual_TM.get(str1));
/*       */           }
/*       */         } 
/*       */         
/*  9308 */         retrieveSalesManual(arrayOfTreeMap[b1], str, paramBoolean, paramStringBuffer);
/*  9309 */         b1++;
/*       */       } 
/*       */     } 
/*  9312 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSalesManual(TreeMap paramTreeMap, String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9325 */     paramStringBuffer.append(NEWLINE + ":xmp." + NEWLINE);
/*  9326 */     paramStringBuffer.append(".kp off" + NEWLINE);
/*  9327 */     paramStringBuffer.append("*********************************************************************" + NEWLINE);
/*  9328 */     paramStringBuffer.append("NOTE TO THE EDITOR" + NEWLINE);
/*  9329 */     paramStringBuffer.append("THE FEATURE SECTION OF THE SALES MANUAL IS AUTOMATICALLY GENERATED" + NEWLINE);
/*  9330 */     paramStringBuffer.append("FROM THE PLAN OF RECORD. PLEASE DO THE FOLLOWING:" + NEWLINE);
/*  9331 */     paramStringBuffer.append("1) ADD THE FEATURE IN NUMERIC ORDER UNDER THE HEADING GIVEN. IF THE" + NEWLINE);
/*  9332 */     paramStringBuffer.append("WORD \"NONE\" APPEARS DO NOT ADD THE WORD \"NONE.\"" + NEWLINE);
/*  9333 */     paramStringBuffer.append("2) IF DESCRIPTIVE INFORMATION IS PROVIDED SUCH AS:" + NEWLINE);
/*  9334 */     paramStringBuffer.append("\"The following is a list of all feature codes.................\"" + NEWLINE);
/*  9335 */     paramStringBuffer.append("REPLACE THE EXISTING DESCRIPTIVE INFORMATION." + NEWLINE);
/*  9336 */     paramStringBuffer.append("IF SUCH DESCRIPTIVE INFORMATION IS NOT PROVIDED DO NOT ADD THESE" + NEWLINE);
/*  9337 */     paramStringBuffer.append("WORDS." + NEWLINE);
/*  9338 */     paramStringBuffer.append("*********************************************************************" + NEWLINE);
/*  9339 */     paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*  9340 */     retrieveSalesManualSpecifyFeatures(paramString, paramBoolean, paramStringBuffer);
/*  9341 */     retrieveSalesManualSpecialFeatures(paramString, paramBoolean, paramStringBuffer);
/*  9342 */     retrieveSalesManualFeatureDescriptions(paramTreeMap, paramString, paramBoolean, paramStringBuffer);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSalesManualSpecifyFeatures(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9354 */     if (paramBoolean == true) {
/*       */       
/*  9356 */       paramStringBuffer.append(":h2.Specify Features for Machine Type " + paramString + NEWLINE);
/*  9357 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  9361 */       paramStringBuffer.append("<h2>Specify Features for Machine Type " + paramString + "</h2>" + NEWLINE);
/*  9362 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     } 
/*       */     
/*  9365 */     retrieveSalesManualFeatures(paramString, this.salesManualSpecifyFeatures_TM, paramBoolean, paramStringBuffer);
/*  9366 */     paramStringBuffer.append(":eul." + NEWLINE);
/*  9367 */     paramStringBuffer.append(".sk 1" + NEWLINE + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSalesManualSpecialFeatures(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9379 */     if (paramBoolean == true) {
/*       */       
/*  9381 */       paramStringBuffer.append(":h2.Special Features for Machine Type " + paramString + NEWLINE);
/*  9382 */       paramStringBuffer.append(":h3.Special Features - Initial Orders" + NEWLINE);
/*  9383 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  9387 */       paramStringBuffer.append("<h2>Special Features for Machine Type " + paramString + "</h2>" + NEWLINE);
/*  9388 */       paramStringBuffer.append("<h3>Special Features - Initial Orders</h3>" + NEWLINE);
/*  9389 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     } 
/*       */     
/*  9392 */     retrieveSalesManualFeatures(paramString, this.salesManualSpecialFeaturesInitialOrder_TM, paramBoolean, paramStringBuffer);
/*  9393 */     paramStringBuffer.append(":eul." + NEWLINE);
/*  9394 */     paramStringBuffer.append(".sk 1" + NEWLINE + NEWLINE);
/*       */     
/*  9396 */     if (paramBoolean == true) {
/*       */       
/*  9398 */       paramStringBuffer.append(":h3.Special Features - Plant and/or Field Installable" + NEWLINE);
/*  9399 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  9403 */       paramStringBuffer.append("<h3>Special Features - Plant and/or Field Installable</h3>" + NEWLINE);
/*  9404 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     } 
/*       */     
/*  9407 */     retrieveSalesManualFeatures(paramString, this.salesManualSpecialFeaturesOther_TM, paramBoolean, paramStringBuffer);
/*  9408 */     paramStringBuffer.append(":eul." + NEWLINE);
/*  9409 */     paramStringBuffer.append(".************************************************************" + NEWLINE + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSalesManualFeatures(String paramString, TreeMap paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9422 */     if (paramTreeMap.size() > 0) {
/*       */ 
/*       */       
/*  9425 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  9426 */       Set<String> set = paramTreeMap.keySet();
/*  9427 */       Iterator<String> iterator = set.iterator();
/*  9428 */       while (iterator.hasNext()) {
/*       */         
/*  9430 */         String str1 = iterator.next();
/*  9431 */         String str2 = (String)paramTreeMap.get(str1);
/*       */         
/*  9433 */         if (paramString.equals(parseString(str1, 1, "<:>")))
/*       */         {
/*  9435 */           treeMap.put(str1, str2);
/*       */         }
/*       */       } 
/*       */       
/*  9439 */       if (treeMap.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9445 */         TreeSet<String> treeSet = new TreeSet();
/*  9446 */         set = treeMap.keySet();
/*  9447 */         iterator = set.iterator();
/*  9448 */         while (iterator.hasNext()) {
/*       */           
/*  9450 */           String str = iterator.next();
/*  9451 */           treeSet.add(parseString(str, 2, "<:>"));
/*       */         } 
/*       */         
/*  9454 */         TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  9455 */         for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */         {
/*  9457 */           arrayOfTreeMap[b2] = new TreeMap<>();
/*       */         }
/*       */         
/*  9460 */         byte b1 = 0;
/*  9461 */         Iterator<String> iterator1 = treeSet.iterator();
/*  9462 */         while (iterator1.hasNext())
/*       */         {
/*  9464 */           String str = iterator1.next();
/*       */           
/*  9466 */           iterator = set.iterator();
/*  9467 */           while (iterator.hasNext()) {
/*       */             
/*  9469 */             String str1 = iterator.next();
/*       */             
/*  9471 */             if (str.equals(parseString(str1, 2, "<:>")))
/*       */             {
/*  9473 */               arrayOfTreeMap[b1].put(str1, treeMap.get(str1));
/*       */             }
/*       */           } 
/*       */           
/*  9477 */           retrieveSalesManualFeatures(arrayOfTreeMap[b1], str, paramBoolean, paramStringBuffer);
/*  9478 */           b1++;
/*       */         }
/*       */       
/*       */       } else {
/*       */         
/*  9483 */         paramStringBuffer.append(":li.NONE" + NEWLINE);
/*       */       
/*       */       }
/*       */     
/*       */     }
/*       */     else {
/*       */       
/*  9490 */       paramStringBuffer.append(":li.NONE" + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSalesManualFeatures(TreeMap paramTreeMap, String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9506 */     if (paramTreeMap.size() > 0) {
/*       */ 
/*       */ 
/*       */       
/*  9510 */       String str1 = "";
/*  9511 */       String str2 = "";
/*       */       
/*  9513 */       paramStringBuffer.append(":li." + paramString + NEWLINE);
/*  9514 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */       
/*  9516 */       Set set = paramTreeMap.keySet();
/*  9517 */       Iterator<String> iterator = set.iterator();
/*  9518 */       while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */         
/*  9522 */         String str4 = iterator.next();
/*  9523 */         str2 = (String)paramTreeMap.get(str4);
/*  9524 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  9525 */         if (parseString(str4, 5, "<:>").length() > 0) {
/*       */           
/*  9527 */           if (!paramBoolean) {
/*       */ 
/*       */             
/*  9530 */             String[] arrayOfString1 = getStringTokens(parseString(str4, 5, "<:>"), NEWLINE);
/*  9531 */             for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */             {
/*  9533 */               paramStringBuffer.append(arrayOfString1[b1] + NEWLINE);
/*       */             }
/*       */           } 
/*       */           
/*  9537 */           if (paramBoolean == true) {
/*       */             
/*  9539 */             String[] arrayOfString1 = getStringTokens(parseString(str4, 5, "<:>"), NEWLINE);
/*  9540 */             for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */               
/*  9542 */               if (arrayOfString1[b1].length() > 68) {
/*       */                 
/*  9544 */                 String[] arrayOfString2 = extractStringLines(arrayOfString1[b1], 68);
/*  9545 */                 for (byte b2 = 0; b2 < arrayOfString2.length; b2++)
/*       */                 {
/*  9547 */                   paramStringBuffer.append(":hp2." + arrayOfString2[b2] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  9552 */                 paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         
/*  9558 */         String str3 = ":li.(#" + parseString(str4, 3, "<:>") + ") - " + parseString(str4, 4, "<:>");
/*  9559 */         String[] arrayOfString = extractStringLines(str3, 79);
/*  9560 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*       */           
/*  9562 */           paramStringBuffer.append(arrayOfString[b]);
/*  9563 */           paramStringBuffer.append(NEWLINE);
/*       */         } 
/*  9565 */         str1 = str2;
/*       */       } 
/*  9567 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9573 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9579 */       paramStringBuffer.append(":eul." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  9583 */       paramStringBuffer.append(":li.NONE" + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSalesManualFeatureDescriptions(TreeMap paramTreeMap, String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9599 */     if (paramBoolean == true) {
/*       */       
/*  9601 */       paramStringBuffer.append(":h2.Feature Descriptions" + NEWLINE);
/*  9602 */       paramStringBuffer.append(":p.The following is a list of all feature codes in numeric order for" + NEWLINE);
/*  9603 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  9605 */         paramStringBuffer.append("the IBM RS/6000 or pSeries " + paramString + " machine type." + NEWLINE);
/*       */       }
/*  9607 */       else if (this.brand.equals("iSeries") || this.brand.equals("totalStorage") || this.brand.equals("xSeries") || this.brand.equals("zSeries")) {
/*       */         
/*  9609 */         paramStringBuffer.append(paramString + " machine type." + NEWLINE);
/*       */       } 
/*  9611 */       paramStringBuffer.append(NEWLINE);
/*  9612 */       paramStringBuffer.append(":p.Attributes, as defined in the following feature descriptions," + NEWLINE);
/*  9613 */       paramStringBuffer.append("state the interaction of requirements among features." + NEWLINE);
/*  9614 */       paramStringBuffer.append(NEWLINE);
/*  9615 */       paramStringBuffer.append(":p.Minimums and maximums are the absolute limits for a single" + NEWLINE);
/*  9616 */       paramStringBuffer.append("feature without regard to interaction with other features.  The" + NEWLINE);
/*  9617 */       paramStringBuffer.append("maximum valid quantity for MES orders may be different than for" + NEWLINE);
/*  9618 */       paramStringBuffer.append("initial orders.  The maximums listed below refer to the largest" + NEWLINE);
/*  9619 */       paramStringBuffer.append("quantity of these two possibilities." + NEWLINE);
/*  9620 */       paramStringBuffer.append(NEWLINE);
/*  9621 */       paramStringBuffer.append(":p.The order type defines if a feature is orderable only on" + NEWLINE);
/*  9622 */       paramStringBuffer.append("initial orders, only on MES orders, on both initial and MES" + NEWLINE);
/*  9623 */       paramStringBuffer.append("orders, or if a feature is supported on a model due to a model" + NEWLINE);
/*  9624 */       paramStringBuffer.append("conversion.  Supported features cannot be ordered on the" + NEWLINE);
/*  9625 */       paramStringBuffer.append("converted model, only left on or removed from the converted" + NEWLINE);
/*  9626 */       paramStringBuffer.append("model." + NEWLINE + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  9630 */       paramStringBuffer.append("<h2>Sales Manual</h2>" + NEWLINE);
/*  9631 */       paramStringBuffer.append("<h2>Feature Descriptions</h2>" + NEWLINE);
/*  9632 */       paramStringBuffer.append("The following is a list of all feature codes in numeric order for" + NEWLINE);
/*  9633 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  9635 */         paramStringBuffer.append("the IBM RS/6000 or pSeries " + paramString + "machine type." + NEWLINE);
/*       */       }
/*  9637 */       else if (this.brand.equals("iSeries") || this.brand.equals("totalStorage") || this.brand.equals("xSeries") || this.brand.equals("zSeries")) {
/*       */         
/*  9639 */         paramStringBuffer.append(paramString + " machine type." + NEWLINE);
/*       */       } 
/*  9641 */       paramStringBuffer.append(NEWLINE);
/*  9642 */       paramStringBuffer.append("Attributes, as defined in the following feature descriptions," + NEWLINE);
/*  9643 */       paramStringBuffer.append("state the interaction of requirements among features." + NEWLINE);
/*  9644 */       paramStringBuffer.append(NEWLINE);
/*  9645 */       paramStringBuffer.append("Minimums and maximums are the absolute limits for a single" + NEWLINE);
/*  9646 */       paramStringBuffer.append("feature without regard to interaction with other features.  The" + NEWLINE);
/*  9647 */       paramStringBuffer.append("maximum valid quantity for MES orders may be different than for" + NEWLINE);
/*  9648 */       paramStringBuffer.append("initial orders.  The maximums listed below refer to the largest" + NEWLINE);
/*  9649 */       paramStringBuffer.append("quantity of these two possibilities." + NEWLINE);
/*  9650 */       paramStringBuffer.append(NEWLINE);
/*  9651 */       paramStringBuffer.append("The order type defines if a feature is orderable only on" + NEWLINE);
/*  9652 */       paramStringBuffer.append("initial orders, only on MES orders, on both initial and MES" + NEWLINE);
/*  9653 */       paramStringBuffer.append("orders, or if a feature is supported on a model due to a model" + NEWLINE);
/*  9654 */       paramStringBuffer.append("conversion.  Supported features cannot be ordered on the" + NEWLINE);
/*  9655 */       paramStringBuffer.append("converted model, only left on or removed from the converted" + NEWLINE);
/*  9656 */       paramStringBuffer.append("model." + NEWLINE + NEWLINE);
/*       */     } 
/*  9658 */     if (paramTreeMap.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9664 */       TreeSet<String> treeSet = new TreeSet();
/*  9665 */       Set<String> set = paramTreeMap.keySet();
/*  9666 */       Iterator<String> iterator2 = set.iterator();
/*  9667 */       while (iterator2.hasNext()) {
/*       */         
/*  9669 */         String str = iterator2.next();
/*  9670 */         treeSet.add(parseString(str, 3, "<:>"));
/*       */       } 
/*       */       
/*  9673 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  9674 */       for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  9676 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  9679 */       byte b1 = 0;
/*  9680 */       Iterator<String> iterator1 = treeSet.iterator();
/*  9681 */       while (iterator1.hasNext()) {
/*       */         
/*  9683 */         String str = iterator1.next();
/*       */         
/*  9685 */         iterator2 = set.iterator();
/*  9686 */         while (iterator2.hasNext()) {
/*       */           
/*  9688 */           String str1 = iterator2.next();
/*       */           
/*  9690 */           if (str.equals(parseString(str1, 3, "<:>")))
/*       */           {
/*  9692 */             arrayOfTreeMap[b1].put(str1, paramTreeMap.get(str1));
/*       */           }
/*       */         } 
/*       */         
/*  9696 */         retrieveSalesManualFeatureDescriptions(arrayOfTreeMap[b1], paramStringBuffer);
/*  9697 */         b1++;
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSalesManualFeatureDescriptions(TreeMap paramTreeMap, StringBuffer paramStringBuffer) {
/*  9710 */     boolean bool = true;
/*  9711 */     String str = "";
/*       */     
/*  9713 */     if (paramTreeMap.size() > 0) {
/*       */       
/*  9715 */       Set set = paramTreeMap.keySet();
/*  9716 */       Iterator<String> iterator = set.iterator();
/*  9717 */       while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9723 */         String str4 = iterator.next();
/*  9724 */         str = (String)paramTreeMap.get(str4);
/*       */         
/*  9726 */         if (bool) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  9733 */           if (!str.equals("WW")) {
/*       */             
/*  9735 */             paramStringBuffer.append(":p.:hp2.");
/*  9736 */             paramStringBuffer.append(str);
/*  9737 */             paramStringBuffer.append("--->:ehp2." + NEWLINE);
/*  9738 */             paramStringBuffer.append(".br" + NEWLINE);
/*       */           } 
/*       */           
/*  9741 */           if (parseString(str4, 14, "<:>").length() > 0) {
/*       */             
/*  9743 */             String[] arrayOfString2 = getStringTokens(parseString(str4, 14, "<:>"), NEWLINE);
/*  9744 */             for (byte b2 = 0; b2 < arrayOfString2.length; b2++) {
/*       */               
/*  9746 */               if (arrayOfString2[b2].length() > 68) {
/*       */                 
/*  9748 */                 String[] arrayOfString3 = extractStringLines(arrayOfString2[b2], 68);
/*  9749 */                 for (byte b3 = 0; b3 < arrayOfString3.length; b3++)
/*       */                 {
/*  9751 */                   paramStringBuffer.append(":hp2." + arrayOfString3[b3] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  9756 */                 paramStringBuffer.append(":hp2." + arrayOfString2[b2] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  9764 */           String str5 = ":h5.(#" + parseString(str4, 3, "<:>") + ") - " + parseString(str4, 4, "<:>");
/*  9765 */           String[] arrayOfString1 = extractStringLines(str5, 79); byte b1;
/*  9766 */           for (b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */             
/*  9768 */             paramStringBuffer.append(arrayOfString1[b1]);
/*  9769 */             paramStringBuffer.append(NEWLINE);
/*       */           } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  9785 */           String str6 = parseString(str4, 5, "<:>");
/*  9786 */           if (str6.equals("")) {
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*  9791 */             paramStringBuffer.append(".sk1" + NEWLINE);
/*  9792 */             paramStringBuffer.append(":ul c." + NEWLINE);
/*  9793 */             paramStringBuffer.append(".kp on" + NEWLINE);
/*       */ 
/*       */ 
/*       */           
/*       */           }
/*       */           else {
/*       */ 
/*       */ 
/*       */             
/*  9802 */             b1 = 0;
/*  9803 */             boolean bool2 = false;
/*  9804 */             boolean bool3 = false;
/*  9805 */             String[] arrayOfString2 = getStringTokens(parseString(str4, 5, "<:>"), NEWLINE);
/*  9806 */             for (byte b2 = 0; b2 < arrayOfString2.length; b2++) {
/*       */               
/*  9808 */               if (arrayOfString2[b2].length() > 79) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/*  9826 */                 if (!arrayOfString2[b2].substring(0, 2).equals(".*"))
/*       */                 {
/*  9828 */                   String[] arrayOfString3 = extractStringLines(arrayOfString2[b2], 79);
/*  9829 */                   for (byte b3 = 0; b3 < arrayOfString3.length; b3++)
/*       */                   {
/*  9831 */                     paramStringBuffer.append(arrayOfString3[b3] + NEWLINE);
/*       */                   
/*       */                   }
/*       */                 }
/*       */               
/*       */               }
/*  9837 */               else if (!arrayOfString2[b2].substring(0, 2).equals(".*")) {
/*       */                 
/*  9839 */                 paramStringBuffer.append(arrayOfString2[b2] + NEWLINE);
/*       */               } 
/*       */             } 
/*       */ 
/*       */             
/*  9844 */             boolean bool1 = textContainsGMLTag(parseString(str4, 5, "<:>"), ".sk1");
/*  9845 */             bool2 = textContainsGMLTag(parseString(str4, 5, "<:>"), ":ul");
/*  9846 */             bool3 = textContainsGMLTag(parseString(str4, 5, "<:>"), ".kp");
/*  9847 */             if (!bool1 && !bool2 && !bool3) {
/*       */               
/*  9849 */               paramStringBuffer.append(".sk1" + NEWLINE);
/*  9850 */               paramStringBuffer.append(":ul c." + NEWLINE);
/*  9851 */               paramStringBuffer.append(".kp on" + NEWLINE);
/*       */             } 
/*       */           } 
/*       */           
/*  9855 */           String str7 = parseString(str4, 15, "<:>");
/*  9856 */           String str8 = parseString(str4, 16, "<:>");
/*       */           
/*  9858 */           if (!str7.equals("")) {
/*       */             
/*  9860 */             if (!textContainsGMLTag(str7, ":li"))
/*       */             {
/*  9862 */               str7 = ":li.Attributes provided: " + str7;
/*       */             }
/*       */             
/*  9865 */             arrayOfString1 = extractStringLines(str7, 79);
/*  9866 */             for (b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */               
/*  9868 */               paramStringBuffer.append(arrayOfString1[b1]);
/*  9869 */               paramStringBuffer.append(NEWLINE);
/*       */             } 
/*       */           } 
/*       */           
/*  9873 */           if (!str8.equals("")) {
/*       */             
/*  9875 */             if (!textContainsGMLTag(str8, ":li"))
/*       */             {
/*  9877 */               str8 = ":li.Attributes required: " + str8;
/*       */             }
/*       */             
/*  9880 */             arrayOfString1 = extractStringLines(str8, 79);
/*  9881 */             for (b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */               
/*  9883 */               paramStringBuffer.append(arrayOfString1[b1]);
/*  9884 */               paramStringBuffer.append(NEWLINE);
/*       */             } 
/*       */           } 
/*       */           
/*  9888 */           paramStringBuffer.append(".kp off" + NEWLINE);
/*  9889 */           paramStringBuffer.append(".* MODEL SPECIFIC INFO SHOULD BE ADDED BELOW THIS LINE" + NEWLINE);
/*       */           
/*  9891 */           bool = false;
/*       */         } 
/*       */ 
/*       */         
/*  9895 */         paramStringBuffer.append(".kp on" + NEWLINE);
/*  9896 */         paramStringBuffer.append(":li.For ");
/*  9897 */         paramStringBuffer.append(parseString(str4, 1, "<:>") + "-");
/*  9898 */         paramStringBuffer.append(parseString(str4, 2, "<:>") + ": ");
/*  9899 */         paramStringBuffer.append("(#");
/*  9900 */         paramStringBuffer.append(parseString(str4, 3, "<:>") + ")" + NEWLINE);
/*  9901 */         paramStringBuffer.append(":ul c." + NEWLINE);
/*  9902 */         if (1 == this.format) {
/*       */           
/*  9904 */           String str5 = parseString(str4, 6, "<:>");
/*  9905 */           String str6 = parseString(str4, 7, "<:>");
/*  9906 */           String str7 = parseString(str4, 8, "<:>");
/*       */           
/*  9908 */           if (!str5.equals(""))
/*       */           {
/*  9910 */             paramStringBuffer.append(":li.Minimum required: " + str5 + NEWLINE);
/*       */           }
/*       */           
/*  9913 */           if (!str6.equals(""))
/*       */           {
/*  9915 */             paramStringBuffer.append(":li.Maximum allowed: " + str6 + "   ");
/*  9916 */             if (!str7.equals(""))
/*       */             {
/*  9918 */               paramStringBuffer.append("(Initial order maximum: " + str7 + " )" + NEWLINE);
/*       */             }
/*       */             else
/*       */             {
/*  9922 */               paramStringBuffer.append(NEWLINE);
/*       */             
/*       */             }
/*       */           
/*       */           }
/*  9927 */           else if (!str7.equals(""))
/*       */           {
/*  9929 */             paramStringBuffer.append(":li.(Initial order maximum: " + str7 + " )" + NEWLINE);
/*       */           }
/*       */         
/*       */         }
/*  9933 */         else if (2 == this.format || 3 == this.format) {
/*       */           
/*  9935 */           paramStringBuffer.append(":li.Minimum required: " + parseString(str4, 6, "<:>") + NEWLINE);
/*  9936 */           paramStringBuffer.append(":li.Maximum allowed: " + parseString(str4, 7, "<:>") + "   ");
/*  9937 */           paramStringBuffer.append("(Initial order maximum: " + parseString(str4, 8, "<:>") + " )" + NEWLINE);
/*       */         } 
/*       */         
/*  9940 */         String str1 = ":li.OS level required: " + parseString(str4, 9, "<:>");
/*  9941 */         String[] arrayOfString = getStringTokens(str1, NEWLINE);
/*  9942 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*       */           
/*  9944 */           if (arrayOfString[b].length() > 79) {
/*       */             
/*  9946 */             String[] arrayOfString1 = extractStringLines(arrayOfString[b], 79);
/*  9947 */             for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */             {
/*  9949 */               paramStringBuffer.append(arrayOfString1[b1] + NEWLINE);
/*       */             }
/*       */           }
/*       */           else {
/*       */             
/*  9954 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           } 
/*       */         } 
/*  9957 */         paramStringBuffer.append(":li.Initial Order/MES/Both/Supported: " + parseString(str4, 10, "<:>") + NEWLINE);
/*  9958 */         paramStringBuffer.append(":li.CSU: " + parseString(str4, 11, "<:>") + NEWLINE);
/*  9959 */         String str3 = parseString(str4, 12, "<:>");
/*  9960 */         if (1 == this.format) {
/*       */           
/*  9962 */           if (!str3.equals(""))
/*       */           {
/*  9964 */             paramStringBuffer.append(":li.Return parts MES: " + parseString(str4, 12, "<:>") + NEWLINE);
/*       */           }
/*       */         }
/*  9967 */         else if (2 == this.format || 3 == this.format) {
/*       */           
/*  9969 */           paramStringBuffer.append(":li.Return parts MES: " + parseString(str4, 12, "<:>") + NEWLINE);
/*       */         } 
/*  9971 */         paramStringBuffer.append(":eul." + NEWLINE);
/*       */ 
/*       */         
/*  9974 */         String str2 = parseString(str4, 13, "<:>");
/*  9975 */         if (str2.length() > 0) {
/*       */ 
/*       */           
/*  9978 */           str2 = ":NOTE." + str2;
/*  9979 */           String[] arrayOfString1 = getStringTokens(str2, NEWLINE);
/*  9980 */           for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */             
/*  9982 */             if (arrayOfString1[b1].length() > 79) {
/*       */               
/*  9984 */               String[] arrayOfString2 = extractStringLines(arrayOfString1[b1], 79);
/*  9985 */               for (byte b2 = 0; b2 < arrayOfString2.length; b2++)
/*       */               {
/*  9987 */                 paramStringBuffer.append(arrayOfString2[b2] + NEWLINE);
/*       */               }
/*       */             }
/*       */             else {
/*       */               
/*  9992 */               paramStringBuffer.append(arrayOfString1[b1] + NEWLINE);
/*       */             } 
/*       */           } 
/*       */         } 
/*  9996 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*       */       } 
/*       */       
/*  9999 */       paramStringBuffer.append(":eul." + NEWLINE);
/* 10000 */       if (!str.equals("WW")) {
/*       */         
/* 10002 */         paramStringBuffer.append(".br;:hp2.<---");
/* 10003 */         paramStringBuffer.append(str);
/* 10004 */         paramStringBuffer.append(":ehp2." + NEWLINE);
/*       */       } 
/* 10006 */       paramStringBuffer.append(".*" + NEWLINE);
/* 10007 */       paramStringBuffer.append(".******** END OF MODEL SPECIFIC INFO ************************" + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSupportedDevices(boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10026 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/* 10027 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/* 10028 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */     
/* 10030 */     TreeSet<String> treeSet = new TreeSet();
/* 10031 */     Set<String> set = this.supportedDevices_TM.keySet();
/* 10032 */     Iterator<String> iterator1 = set.iterator();
/* 10033 */     while (iterator1.hasNext()) {
/*       */       
/* 10035 */       String str = iterator1.next();
/*       */       
/* 10037 */       treeSet.add(parseString(str, 1, "<:>"));
/*       */     } 
/*       */     
/* 10040 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/* 10041 */     for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/* 10043 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/* 10046 */     byte b1 = 0;
/* 10047 */     Iterator<String> iterator2 = treeSet.iterator();
/* 10048 */     while (iterator2.hasNext()) {
/*       */       
/* 10050 */       String str = iterator2.next();
/*       */       
/* 10052 */       iterator1 = set.iterator();
/* 10053 */       while (iterator1.hasNext()) {
/*       */         
/* 10055 */         String str1 = iterator1.next();
/*       */         
/* 10057 */         if (str.equals(parseString(str1, 1, "<:>"))) {
/*       */           
/* 10059 */           String str2 = parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 5, "<:>") + "<:>" + parseString(str1, 6, "<:>");
/* 10060 */           arrayOfTreeMap[b1].put(str2, this.supportedDevices_TM.get(str1));
/*       */         } 
/*       */       } 
/* 10063 */       b1++;
/*       */     } 
/*       */     
/* 10066 */     if (1 == this.format)
/*       */     {
/* 10068 */       paramStringBuffer.append(":h2.External Machine Type (Support Devices)" + NEWLINE);
/*       */     }
/*       */     
/* 10071 */     if (0 == arrayOfTreeMap.length)
/*       */     {
/* 10073 */       if (true == paramBoolean) {
/*       */         
/* 10075 */         paramStringBuffer.append(":p.No answer data found for External Machine Type (Support Devices) section." + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 10079 */         paramStringBuffer.append("<p>No answer data found for External Machine Type (Support Devices) section.</p>" + NEWLINE);
/*       */       } 
/*       */     }
/*       */     
/* 10083 */     iterator2 = treeSet.iterator();
/* 10084 */     b1 = 0;
/* 10085 */     while (iterator2.hasNext()) {
/*       */       
/* 10087 */       String str = iterator2.next();
/* 10088 */       if (true == paramBoolean) {
/*       */         
/* 10090 */         paramStringBuffer.append(NEWLINE + ":p.The following external machine types are supported on the" + NEWLINE);
/* 10091 */         paramStringBuffer.append("indicated models for MT " + str + "." + NEWLINE);
/* 10092 */         paramStringBuffer.append(":p.This list is not all inclusive.  Many devices are supported through" + NEWLINE);
/* 10093 */         paramStringBuffer.append("standard ports.  Please refer to the sales manual" + NEWLINE);
/* 10094 */         paramStringBuffer.append("of the external machine type and the list of supported devices in the" + NEWLINE);
/* 10095 */         if (this.brand.equals("pSeries")) {
/*       */           
/* 10097 */           paramStringBuffer.append("appropriate section of the AIX sales manual" + NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/* 10101 */           paramStringBuffer.append("appropriate section of the sales manual" + NEWLINE);
/*       */         } 
/* 10103 */         paramStringBuffer.append("for further attach support information." + NEWLINE + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 10107 */         paramStringBuffer.append("<p>The following external machine types are supported on the indicated models for MT " + str + ".</p>" + NEWLINE);
/* 10108 */         paramStringBuffer.append("<p>This list is not all inclusive.  Many devices are supported through" + NEWLINE);
/* 10109 */         paramStringBuffer.append("standard ports.  Please refer to the sales manual" + NEWLINE);
/* 10110 */         paramStringBuffer.append("of the external machine type and the list of supported devices in the" + NEWLINE);
/* 10111 */         if (this.brand.equals("pSeries")) {
/*       */           
/* 10113 */           paramStringBuffer.append("appropriate section of the AIX sales manual" + NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/* 10117 */           paramStringBuffer.append("appropriate section of the sales manual" + NEWLINE);
/*       */         } 
/* 10119 */         paramStringBuffer.append("for further attach support information.</p>" + NEWLINE);
/*       */       } 
/* 10121 */       retrieveSupportedDevices(paramStringBuffer, paramBoolean, arrayOfTreeMap[b1]);
/* 10122 */       b1++;
/*       */     } 
/* 10124 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSupportedDevices(StringBuffer paramStringBuffer, boolean paramBoolean, TreeMap paramTreeMap) {
/* 10140 */     TreeSet<String> treeSet = new TreeSet();
/* 10141 */     Set set = paramTreeMap.keySet();
/* 10142 */     Iterator<String> iterator = set.iterator();
/* 10143 */     while (iterator.hasNext()) {
/*       */       
/* 10145 */       String str = iterator.next();
/* 10146 */       str = parseString(str, 5, "<:>");
/* 10147 */       treeSet.add(str);
/*       */     } 
/*       */ 
/*       */     
/* 10151 */     int[] arrayOfInt = new int[10];
/* 10152 */     arrayOfInt[0] = 11;
/* 10153 */     arrayOfInt[1] = 13;
/* 10154 */     arrayOfInt[2] = 15;
/* 10155 */     arrayOfInt[3] = 17;
/* 10156 */     arrayOfInt[4] = 19;
/* 10157 */     arrayOfInt[5] = 21;
/* 10158 */     arrayOfInt[6] = 23;
/* 10159 */     arrayOfInt[7] = 25;
/* 10160 */     arrayOfInt[8] = 27;
/* 10161 */     arrayOfInt[9] = 29;
/*       */     
/* 10163 */     Object[] arrayOfObject = treeSet.toArray();
/*       */     
/* 10165 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */     
/* 10167 */     byte b1 = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 10172 */     for (byte b2 = 0; b2 < arrayOfObject.length; b2++) {
/*       */       
/* 10174 */       if (b1 < 10) {
/*       */         
/* 10176 */         treeMap.put(arrayOfObject[b2], new Integer(arrayOfInt[b2 % 10]));
/* 10177 */         b1++;
/*       */       } 
/* 10179 */       if (b1 == 10 || b2 == arrayOfObject.length - 1) {
/*       */         
/* 10181 */         b1 = 0;
/* 10182 */         retrieveSupportedDevices(paramStringBuffer, treeMap, paramTreeMap, paramBoolean);
/* 10183 */         treeMap.clear();
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSupportedDevices(StringBuffer paramStringBuffer, TreeMap paramTreeMap1, TreeMap paramTreeMap2, boolean paramBoolean) {
/* 10208 */     paramStringBuffer.append(":xmp." + NEWLINE);
/* 10209 */     paramStringBuffer.append(".kp off" + NEWLINE);
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 10214 */     this.headerSB.delete(0, this.headerSB.length());
/*       */     
/* 10216 */     Set set = paramTreeMap1.keySet();
/* 10217 */     Object[] arrayOfObject = set.toArray();
/* 10218 */     int i = 69 - 9 + 2 * paramTreeMap1.size() + 1;
/*       */     
/* 10220 */     printChar(paramStringBuffer, 9, " ", false);
/* 10221 */     printChar(this.headerSB, 9, " ", false); byte b;
/* 10222 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/* 10224 */       String str = (String)arrayOfObject[b];
/* 10225 */       paramStringBuffer.append("|");
/* 10226 */       this.headerSB.append("|");
/*       */       
/* 10228 */       paramStringBuffer.append(str.charAt(0));
/* 10229 */       this.headerSB.append(str.charAt(0));
/*       */     } 
/* 10231 */     paramStringBuffer.append("|");
/* 10232 */     this.headerSB.append("|");
/* 10233 */     paramStringBuffer.append("  X = SUPPORTED DEVICE" + NEWLINE);
/* 10234 */     this.headerSB.append("  X = SUPPORTED DEVICE" + NEWLINE);
/*       */     
/* 10236 */     printChar(paramStringBuffer, 9, " ", false);
/* 10237 */     printChar(this.headerSB, 9, " ", false);
/* 10238 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/* 10240 */       String str = (String)arrayOfObject[b];
/* 10241 */       paramStringBuffer.append("|");
/* 10242 */       this.headerSB.append("|");
/*       */       
/* 10244 */       paramStringBuffer.append(str.charAt(1));
/* 10245 */       this.headerSB.append(str.charAt(1));
/*       */     } 
/* 10247 */     paramStringBuffer.append("|" + NEWLINE);
/* 10248 */     this.headerSB.append("|" + NEWLINE);
/*       */     
/* 10250 */     printChar(paramStringBuffer, 9, " ", false);
/* 10251 */     printChar(this.headerSB, 9, " ", false);
/* 10252 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/* 10254 */       String str = (String)arrayOfObject[b];
/* 10255 */       paramStringBuffer.append("|");
/* 10256 */       this.headerSB.append("|");
/*       */       
/* 10258 */       paramStringBuffer.append(str.charAt(2));
/* 10259 */       this.headerSB.append(str.charAt(2));
/*       */     } 
/* 10261 */     paramStringBuffer.append("|" + NEWLINE);
/* 10262 */     this.headerSB.append("|" + NEWLINE);
/*       */     
/* 10264 */     printChar(paramStringBuffer, 9, " ", false);
/* 10265 */     printChar(this.headerSB, 9, " ", false);
/* 10266 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/* 10267 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/* 10268 */     paramStringBuffer.append(NEWLINE);
/* 10269 */     this.headerSB.append(NEWLINE);
/*       */     
/* 10271 */     paramStringBuffer.append("  MT-MOD ");
/* 10272 */     this.headerSB.append("  MT-MOD ");
/* 10273 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/* 10274 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/* 10275 */     paramStringBuffer.append("        DESCRIPTION" + NEWLINE);
/* 10276 */     this.headerSB.append("        DESCRIPTION" + NEWLINE);
/*       */     
/* 10278 */     printChar(paramStringBuffer, 9, "-", false);
/* 10279 */     printChar(this.headerSB, 9, "-", false);
/* 10280 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/* 10281 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/* 10282 */     printChar(paramStringBuffer, i, "-", false);
/* 10283 */     printChar(this.headerSB, i, "-", false);
/* 10284 */     paramStringBuffer.append(NEWLINE);
/* 10285 */     this.headerSB.append(NEWLINE);
/*       */     
/* 10287 */     retrieveSupportedDevices(paramTreeMap1, paramTreeMap2, i, paramStringBuffer, paramBoolean);
/*       */     
/* 10289 */     paramStringBuffer.append(":exmp." + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSupportedDevices(TreeMap paramTreeMap1, TreeMap paramTreeMap2, int paramInt, StringBuffer paramStringBuffer, boolean paramBoolean) {
/* 10303 */     int i = 0;
/*       */     
/* 10305 */     int j = 0;
/* 10306 */     boolean bool = true;
/* 10307 */     byte b = 0;
/*       */     
/* 10309 */     String str1 = "";
/* 10310 */     String str2 = "";
/* 10311 */     String str3 = "";
/* 10312 */     String str4 = "";
/*       */     
/* 10314 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/* 10316 */     Set set = paramTreeMap2.keySet();
/*       */     
/* 10318 */     Iterator<String> iterator = set.iterator();
/*       */     
/* 10320 */     while (iterator.hasNext()) {
/*       */       
/* 10322 */       String str5 = iterator.next();
/* 10323 */       String str6 = parseString(str5, 5, "<:>");
/* 10324 */       if (paramTreeMap1.containsKey(str6)) {
/*       */         
/* 10326 */         String str = parseString(str5, 1, "<:>") + "<:>" + parseString(str5, 2, "<:>") + "<:>" + parseString(str5, 3, "<:>") + "<:>" + parseString(str5, 4, "<:>") + "<:>" + paramTreeMap2.get(str5);
/* 10327 */         treeSet.add(str);
/*       */       } 
/*       */     } 
/*       */     
/* 10331 */     iterator = treeSet.iterator();
/*       */     
/* 10333 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 10338 */       String str = iterator.next();
/*       */       
/* 10340 */       i = 10;
/*       */       
/* 10342 */       Set set1 = paramTreeMap2.keySet();
/* 10343 */       Iterator<String> iterator1 = set1.iterator();
/* 10344 */       while (iterator1.hasNext()) {
/*       */         
/* 10346 */         String str5 = iterator1.next();
/* 10347 */         String str6 = parseString(str5, 1, "<:>") + "<:>" + parseString(str5, 2, "<:>") + "<:>" + parseString(str5, 3, "<:>") + "<:>" + parseString(str5, 4, "<:>") + "<:>" + paramTreeMap2.get(str5);
/* 10348 */         String str7 = parseString(str5, 5, "<:>");
/* 10349 */         if (str.equals(str6) && paramTreeMap1.containsKey(str7)) {
/*       */           
/* 10351 */           str2 = parseString(str6, 5, "<:>");
/*       */           
/* 10353 */           str4 = parseString(str, 1, "<:>");
/*       */           
/* 10355 */           if (bool) {
/*       */             
/* 10357 */             if (!str4.equals(str3)) {
/*       */               
/* 10359 */               if (50 == b) {
/*       */                 
/* 10361 */                 printEndGeoTags(str1, paramBoolean, paramStringBuffer);
/* 10362 */                 paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/* 10363 */                 b = 0;
/* 10364 */                 paramStringBuffer.append(":xmp." + NEWLINE);
/* 10365 */                 paramStringBuffer.append(".kp off" + NEWLINE);
/* 10366 */                 paramStringBuffer.append(this.headerSB.toString());
/*       */               }
/*       */               else {
/*       */                 
/* 10370 */                 printEndGeoTags(str1, paramBoolean, paramStringBuffer);
/*       */               } 
/* 10372 */               printChar(paramStringBuffer, 9, " ", false);
/* 10373 */               printChar(paramStringBuffer, 2 * paramTreeMap1.size() + 1, "|", " ", false);
/* 10374 */               paramStringBuffer.append(NEWLINE);
/*       */               
/* 10376 */               printChar(paramStringBuffer, 9, "-", false);
/* 10377 */               printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/* 10378 */               printChar(paramStringBuffer, 8, "-", false);
/*       */               
/* 10380 */               paramStringBuffer.append(str4);
/* 10381 */               printChar(paramStringBuffer, paramInt - 8 + str4.length(), "-", false);
/* 10382 */               paramStringBuffer.append(NEWLINE);
/* 10383 */               printChar(paramStringBuffer, 9, "-", " ", false);
/* 10384 */               printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/* 10385 */               printChar(paramStringBuffer, paramInt, "-", " ", false);
/* 10386 */               paramStringBuffer.append(NEWLINE);
/* 10387 */               printBeginGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */             
/*       */             }
/* 10391 */             else if (50 == b) {
/*       */               
/* 10393 */               setGeoTags(str1, str2, paramBoolean, paramStringBuffer, this.headerSB);
/* 10394 */               b = 0;
/*       */             }
/*       */             else {
/*       */               
/* 10398 */               setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */             } 
/*       */ 
/*       */             
/* 10402 */             paramStringBuffer.append(parseString(str, 2, "<:>") + "-" + parseString(str, 3, "<:>") + " ");
/*       */             
/* 10404 */             bool = false;
/* 10405 */             b++;
/*       */           } 
/*       */           
/* 10408 */           str1 = str2;
/* 10409 */           str3 = str4;
/*       */           
/* 10411 */           Integer integer = (Integer)paramTreeMap1.get(str7);
/* 10412 */           j = integer.intValue();
/* 10413 */           printChar(paramStringBuffer, j - i, "|", " ", false);
/* 10414 */           i = j;
/* 10415 */           paramStringBuffer.append("X");
/* 10416 */           i++;
/*       */         } 
/*       */       } 
/* 10419 */       printChar(paramStringBuffer, 9 + 2 * paramTreeMap1.size() + 2 - i, "|", " ", false);
/*       */       
/* 10421 */       String[] arrayOfString = extractStringLines(parseString(str, 4, "<:>"), paramInt - 1);
/* 10422 */       for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */         
/* 10424 */         if (0 == b1) {
/*       */           
/* 10426 */           paramStringBuffer.append(" " + arrayOfString[b1] + NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/* 10430 */           printChar(paramStringBuffer, 9, " ", false);
/* 10431 */           printChar(paramStringBuffer, 2 * paramTreeMap1.size() + 1, "|", " ", false);
/* 10432 */           paramStringBuffer.append(" " + arrayOfString[b1] + NEWLINE);
/*       */         } 
/*       */       } 
/*       */       
/* 10436 */       bool = true;
/*       */     } 
/* 10438 */     if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 10444 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 10450 */     paramStringBuffer.append(NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveFeatureMatrix(boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10468 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/* 10469 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/* 10470 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */     
/* 10472 */     TreeSet<String> treeSet = new TreeSet();
/* 10473 */     Set<String> set = this.featureMatrix_TM.keySet();
/* 10474 */     Iterator<String> iterator1 = set.iterator();
/* 10475 */     while (iterator1.hasNext()) {
/*       */       
/* 10477 */       String str = iterator1.next();
/*       */       
/* 10479 */       treeSet.add(parseString(str, 1, "<:>"));
/*       */     } 
/*       */     
/* 10482 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/* 10483 */     for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/* 10485 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/* 10488 */     byte b1 = 0;
/* 10489 */     Iterator<String> iterator2 = treeSet.iterator();
/* 10490 */     while (iterator2.hasNext()) {
/*       */       
/* 10492 */       String str = iterator2.next();
/*       */       
/* 10494 */       iterator1 = set.iterator();
/* 10495 */       while (iterator1.hasNext()) {
/*       */         
/* 10497 */         String str1 = iterator1.next();
/*       */         
/* 10499 */         if (str.equals(parseString(str1, 1, "<:>"))) {
/*       */           
/* 10501 */           String str2 = parseString(str1, 2, "<:>") + "<:>" + parseString(str1, 3, "<:>") + "<:>" + parseString(str1, 4, "<:>") + "<:>" + parseString(str1, 5, "<:>") + "<:>" + parseString(str1, 6, "<:>");
/* 10502 */           arrayOfTreeMap[b1].put(str2, this.featureMatrix_TM.get(str1));
/*       */         } 
/*       */       } 
/* 10505 */       b1++;
/*       */     } 
/*       */     
/* 10508 */     if (1 == this.format)
/*       */     {
/* 10510 */       paramStringBuffer.append(":h2.Feature Matrix" + NEWLINE);
/*       */     }
/*       */     
/* 10513 */     if (0 == arrayOfTreeMap.length)
/*       */     {
/* 10515 */       if (true == paramBoolean) {
/*       */         
/* 10517 */         paramStringBuffer.append(":p.No answer data found for Feature Matrix section." + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 10521 */         paramStringBuffer.append("<p>No answer data found for Feature Matrix section.</p>" + NEWLINE);
/*       */       } 
/*       */     }
/*       */     
/* 10525 */     iterator2 = treeSet.iterator();
/* 10526 */     b1 = 0;
/* 10527 */     while (iterator2.hasNext()) {
/*       */       
/* 10529 */       String str = iterator2.next();
/* 10530 */       if (true == paramBoolean) {
/*       */         
/* 10532 */         paramStringBuffer.append(NEWLINE + ":xmp." + NEWLINE);
/* 10533 */         paramStringBuffer.append(".kp off" + NEWLINE);
/* 10534 */         paramStringBuffer.append("*********************************************************************" + NEWLINE);
/* 10535 */         paramStringBuffer.append("NOTE TO THE EDITOR" + NEWLINE);
/* 10536 */         paramStringBuffer.append("THE FEATURE AVAILABILITY MATRIX SECTION OF THE SALES MANUAL" + NEWLINE);
/* 10537 */         paramStringBuffer.append("IS AUTOMATICALLY GENERATED FROM THE PLAN OF RECORD." + NEWLINE);
/* 10538 */         paramStringBuffer.append("PLEASE DO THE FOLLOWING:" + NEWLINE);
/* 10539 */         paramStringBuffer.append("1) ADD THE FEATURE IN NUMERIC ORDER INTO THE FEATURE AVAILABILITY" + NEWLINE);
/* 10540 */         paramStringBuffer.append("MATRIX." + NEWLINE);
/* 10541 */         paramStringBuffer.append("2) IF DESCRIPTIVE INFORMATION IS PROVIDED SUCH AS:" + NEWLINE);
/* 10542 */         paramStringBuffer.append("\"The following feature availability matrix for................\"" + NEWLINE);
/* 10543 */         paramStringBuffer.append("REPLACE THE EXISTING DESCRIPTIVE INFORMATION." + NEWLINE);
/* 10544 */         paramStringBuffer.append("IF SUCH DESCRIPTIVE INFORMATION IS NOT PROVIDED DO NOT ADD THESE" + NEWLINE);
/* 10545 */         paramStringBuffer.append("WORDS." + NEWLINE);
/* 10546 */         paramStringBuffer.append("3) IF THERE IS NO FEATURE AVAILABILITY MATRIX DO NOT INCLUDE THIS" + NEWLINE);
/* 10547 */         paramStringBuffer.append("SECTION." + NEWLINE);
/* 10548 */         paramStringBuffer.append("*********************************************************************" + NEWLINE);
/* 10549 */         paramStringBuffer.append(":exmp." + NEWLINE);
/* 10550 */         if (2 == this.format)
/*       */         {
/* 10552 */           paramStringBuffer.append(NEWLINE + ":p.The following feature availability matrix for MT " + str + NEWLINE);
/* 10553 */           paramStringBuffer.append("uses the letter \"A\"" + NEWLINE);
/* 10554 */           paramStringBuffer.append("to indicate features that are available and orderable on the specified" + NEWLINE);
/* 10555 */           paramStringBuffer.append("models.  \"S\" indicates a feature that is supported on the new model" + NEWLINE);
/* 10556 */           paramStringBuffer.append("during a model conversion; these features will" + NEWLINE);
/* 10557 */           paramStringBuffer.append("work on the new model, but additional quantities of these" + NEWLINE);
/* 10558 */           paramStringBuffer.append("features cannot be ordered on the new model; they can only be removed." + NEWLINE);
/* 10559 */           paramStringBuffer.append("\"N\" indicates that the feature is not supported" + NEWLINE);
/* 10560 */           paramStringBuffer.append("on the new model and must be removed during the model conversion." + NEWLINE);
/* 10561 */           paramStringBuffer.append("As additional features are announced, supported, or withdrawn," + NEWLINE);
/* 10562 */           paramStringBuffer.append("this list will be updated.  Please check with your Marketing" + NEWLINE);
/* 10563 */           paramStringBuffer.append("Representative for additional information." + NEWLINE + NEWLINE);
/*       */         }
/*       */         else
/*       */         {
/* 10567 */           paramStringBuffer.append(NEWLINE + ":p.The following feature availability matrix for MT " + str + NEWLINE + NEWLINE);
/*       */         
/*       */         }
/*       */       
/*       */       }
/* 10572 */       else if (2 == this.format) {
/*       */         
/* 10574 */         paramStringBuffer.append("<p>The following feature availability matrix for MT " + str + NEWLINE);
/* 10575 */         paramStringBuffer.append("uses the letter &quot;A&quot;" + NEWLINE);
/* 10576 */         paramStringBuffer.append("to indicate features that are available and orderable on the specified" + NEWLINE);
/* 10577 */         paramStringBuffer.append("models. &quot;S&quot; indicates a feature that is supported on the new model" + NEWLINE);
/* 10578 */         paramStringBuffer.append("during a model conversion; these features will" + NEWLINE);
/* 10579 */         paramStringBuffer.append("work on the new model, but additional quantities of these" + NEWLINE);
/* 10580 */         paramStringBuffer.append("features cannot be ordered on the new model; they can only be removed." + NEWLINE);
/* 10581 */         paramStringBuffer.append("&quot;N&quot; indicates that the feature is not supported" + NEWLINE);
/* 10582 */         paramStringBuffer.append("on the new model and must be removed during the model conversion." + NEWLINE);
/* 10583 */         paramStringBuffer.append("As additional features are announced, supported, or withdrawn," + NEWLINE);
/* 10584 */         paramStringBuffer.append("this list will be updated.  Please check with your Marketing" + NEWLINE);
/* 10585 */         paramStringBuffer.append("Representative for additional information.</p>" + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 10589 */         paramStringBuffer.append("<p>The following feature availability matrix for MT " + str + "</p>" + NEWLINE);
/*       */       } 
/*       */       
/* 10592 */       retrieveFeatureMatrix(paramStringBuffer, paramBoolean, arrayOfTreeMap[b1]);
/* 10593 */       b1++;
/*       */     } 
/* 10595 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveFeatureMatrix(StringBuffer paramStringBuffer, boolean paramBoolean, TreeMap paramTreeMap) {
/* 10611 */     TreeSet<String> treeSet = new TreeSet();
/* 10612 */     Set set = paramTreeMap.keySet();
/* 10613 */     Iterator<String> iterator = set.iterator();
/* 10614 */     while (iterator.hasNext()) {
/*       */       
/* 10616 */       String str = iterator.next();
/* 10617 */       str = parseString(str, 1, "<:>");
/* 10618 */       treeSet.add(str);
/*       */     } 
/*       */ 
/*       */     
/* 10622 */     int[] arrayOfInt = new int[10];
/* 10623 */     arrayOfInt[0] = 10;
/* 10624 */     arrayOfInt[1] = 12;
/* 10625 */     arrayOfInt[2] = 14;
/* 10626 */     arrayOfInt[3] = 16;
/* 10627 */     arrayOfInt[4] = 18;
/* 10628 */     arrayOfInt[5] = 20;
/* 10629 */     arrayOfInt[6] = 22;
/* 10630 */     arrayOfInt[7] = 24;
/* 10631 */     arrayOfInt[8] = 26;
/* 10632 */     arrayOfInt[9] = 28;
/*       */     
/* 10634 */     Object[] arrayOfObject = treeSet.toArray();
/*       */     
/* 10636 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */     
/* 10638 */     byte b1 = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 10643 */     for (byte b2 = 0; b2 < arrayOfObject.length; b2++) {
/*       */       
/* 10645 */       if (b1 < 10) {
/*       */         
/* 10647 */         treeMap.put(arrayOfObject[b2], new Integer(arrayOfInt[b2 % 10]));
/* 10648 */         b1++;
/*       */       } 
/* 10650 */       if (b1 == 10 || b2 == arrayOfObject.length - 1) {
/*       */         
/* 10652 */         b1 = 0;
/* 10653 */         retrieveFeatureMatrix(paramStringBuffer, treeMap, paramTreeMap, paramBoolean);
/* 10654 */         treeMap.clear();
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveFeatureMatrix(StringBuffer paramStringBuffer, TreeMap paramTreeMap1, TreeMap paramTreeMap2, boolean paramBoolean) {
/* 10675 */     Set set = paramTreeMap1.keySet();
/* 10676 */     Object[] arrayOfObject = set.toArray();
/* 10677 */     int i = 69 - 8 + 2 * paramTreeMap1.size() + 1;
/*       */     
/* 10679 */     paramStringBuffer.append(":xmp." + NEWLINE);
/* 10680 */     paramStringBuffer.append(".kp off" + NEWLINE);
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 10685 */     this.headerSB.delete(0, this.headerSB.length());
/*       */     
/* 10687 */     printChar(paramStringBuffer, 8, " ", false);
/* 10688 */     printChar(this.headerSB, 8, " ", false); byte b;
/* 10689 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/* 10691 */       String str = (String)arrayOfObject[b];
/* 10692 */       paramStringBuffer.append("|");
/* 10693 */       this.headerSB.append("|");
/*       */       
/* 10695 */       paramStringBuffer.append(str.charAt(0));
/* 10696 */       this.headerSB.append(str.charAt(0));
/*       */     } 
/* 10698 */     paramStringBuffer.append("|");
/* 10699 */     this.headerSB.append("|");
/* 10700 */     if (2 == this.format) {
/*       */       
/* 10702 */       paramStringBuffer.append(" A = AVAILABLE  S = SUPPORTED" + NEWLINE);
/* 10703 */       this.headerSB.append(" A = AVAILABLE  S = SUPPORTED" + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/* 10707 */       paramStringBuffer.append(" I = Initial" + NEWLINE);
/* 10708 */       this.headerSB.append(" I = Initial" + NEWLINE);
/*       */     } 
/*       */     
/* 10711 */     printChar(paramStringBuffer, 8, " ", false);
/* 10712 */     printChar(this.headerSB, 8, " ", false);
/* 10713 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/* 10715 */       String str = (String)arrayOfObject[b];
/* 10716 */       paramStringBuffer.append("|");
/* 10717 */       this.headerSB.append("|");
/*       */       
/* 10719 */       paramStringBuffer.append(str.charAt(1));
/* 10720 */       this.headerSB.append(str.charAt(1));
/*       */     } 
/* 10722 */     paramStringBuffer.append("|");
/* 10723 */     this.headerSB.append("|");
/* 10724 */     if (2 == this.format) {
/*       */       
/* 10726 */       paramStringBuffer.append(" N = NOT SUPPORTED, MUST BE REMOVED" + NEWLINE);
/* 10727 */       this.headerSB.append(" N = NOT SUPPORTED, MUST BE REMOVED" + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/* 10731 */       paramStringBuffer.append(" M = MES" + NEWLINE);
/* 10732 */       this.headerSB.append(" M = MES" + NEWLINE);
/*       */     } 
/*       */     
/* 10735 */     printChar(paramStringBuffer, 8, " ", false);
/* 10736 */     printChar(this.headerSB, 8, " ", false);
/* 10737 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/* 10739 */       String str = (String)arrayOfObject[b];
/* 10740 */       paramStringBuffer.append("|");
/* 10741 */       this.headerSB.append("|");
/*       */       
/* 10743 */       paramStringBuffer.append(str.charAt(2));
/* 10744 */       this.headerSB.append(str.charAt(2));
/*       */     } 
/* 10746 */     paramStringBuffer.append("|");
/* 10747 */     this.headerSB.append("|");
/* 10748 */     if (2 == this.format) {
/*       */       
/* 10750 */       paramStringBuffer.append(NEWLINE);
/* 10751 */       this.headerSB.append(NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/* 10755 */       paramStringBuffer.append(" B = Both (Initial and MES)" + NEWLINE);
/* 10756 */       this.headerSB.append(" B = Both (Initial and MES)" + NEWLINE);
/*       */     } 
/*       */     
/* 10759 */     printChar(paramStringBuffer, 8, " ", false);
/* 10760 */     printChar(this.headerSB, 8, " ", false);
/* 10761 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/* 10762 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/* 10763 */     if (2 == this.format) {
/*       */       
/* 10765 */       paramStringBuffer.append(NEWLINE);
/* 10766 */       this.headerSB.append(NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/* 10770 */       paramStringBuffer.append(" S = SUPPORTED" + NEWLINE);
/* 10771 */       this.headerSB.append(" S = SUPPORTED" + NEWLINE);
/*       */     } 
/*       */     
/* 10774 */     paramStringBuffer.append("FEAT/PN ");
/* 10775 */     this.headerSB.append("FEAT/PN ");
/* 10776 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/* 10777 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/* 10778 */     paramStringBuffer.append("        DESCRIPTION" + NEWLINE);
/* 10779 */     this.headerSB.append("        DESCRIPTION" + NEWLINE);
/*       */     
/* 10781 */     printChar(paramStringBuffer, 8, "-", false);
/* 10782 */     printChar(this.headerSB, 8, "-", false);
/* 10783 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/* 10784 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/* 10785 */     printChar(paramStringBuffer, i, "-", false);
/* 10786 */     printChar(this.headerSB, i, "-", false);
/* 10787 */     paramStringBuffer.append(NEWLINE);
/* 10788 */     this.headerSB.append(NEWLINE);
/*       */     
/* 10790 */     retrieveFeatureMatrix(paramTreeMap1, paramTreeMap2, i, paramStringBuffer, paramBoolean);
/*       */     
/* 10792 */     paramStringBuffer.append(":exmp." + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveFeatureMatrix(TreeMap paramTreeMap1, TreeMap paramTreeMap2, int paramInt, StringBuffer paramStringBuffer, boolean paramBoolean) {
/* 10806 */     int i = 0;
/*       */     
/* 10808 */     int j = 0;
/* 10809 */     boolean bool = true;
/* 10810 */     byte b = 0;
/*       */     
/* 10812 */     String str1 = "";
/* 10813 */     String str2 = "";
/*       */     
/* 10815 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/* 10817 */     Set set = paramTreeMap2.keySet();
/*       */     
/* 10819 */     Iterator<String> iterator = set.iterator();
/*       */     
/* 10821 */     while (iterator.hasNext()) {
/*       */       
/* 10823 */       String str3 = iterator.next();
/* 10824 */       String str4 = parseString(str3, 1, "<:>");
/* 10825 */       if (paramTreeMap1.containsKey(str4)) {
/*       */ 
/*       */         
/* 10828 */         String str = parseString(str3, 2, "<:>") + "<:>" + parseString(str3, 4, "<:>") + "<:>" + paramTreeMap2.get(str3);
/* 10829 */         treeSet.add(str);
/*       */       } 
/*       */     } 
/*       */     
/* 10833 */     iterator = treeSet.iterator();
/*       */     
/* 10835 */     while (iterator.hasNext()) {
/*       */       
/* 10837 */       String str = iterator.next();
/*       */ 
/*       */       
/* 10840 */       Set set1 = paramTreeMap2.keySet();
/* 10841 */       Iterator<String> iterator1 = set1.iterator();
/* 10842 */       i = 9;
/* 10843 */       while (iterator1.hasNext()) {
/*       */         
/* 10845 */         String str3 = iterator1.next();
/*       */         
/* 10847 */         String str4 = parseString(str3, 2, "<:>") + "<:>" + parseString(str3, 4, "<:>") + "<:>" + paramTreeMap2.get(str3);
/* 10848 */         String str5 = parseString(str3, 1, "<:>");
/* 10849 */         String str6 = parseString(str3, 3, "<:>");
/* 10850 */         String str7 = parseString(str3, 5, "<:>");
/* 10851 */         if (str.equals(str4) && paramTreeMap1.containsKey(str5)) {
/*       */           
/* 10853 */           str2 = parseString(str4, 3, "<:>");
/*       */           
/* 10855 */           if (bool) {
/*       */             
/* 10857 */             if (50 == b) {
/*       */               
/* 10859 */               setGeoTags(str1, str2, paramBoolean, paramStringBuffer, this.headerSB);
/* 10860 */               b = 0;
/*       */             }
/*       */             else {
/*       */               
/* 10864 */               setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */             } 
/*       */             
/* 10867 */             if (str7.length() > 0) {
/*       */               
/* 10869 */               if (!paramBoolean) {
/*       */ 
/*       */                 
/* 10872 */                 String[] arrayOfString1 = getStringTokens(str7, NEWLINE);
/* 10873 */                 for (byte b2 = 0; b2 < arrayOfString1.length; b2++)
/*       */                 {
/* 10875 */                   paramStringBuffer.append(arrayOfString1[b2] + NEWLINE);
/*       */                 }
/*       */               } 
/*       */               
/* 10879 */               if (paramBoolean == true) {
/*       */                 
/* 10881 */                 String[] arrayOfString1 = getStringTokens(str7, NEWLINE);
/* 10882 */                 for (byte b2 = 0; b2 < arrayOfString1.length; b2++) {
/*       */                   
/* 10884 */                   if (arrayOfString1[b2].length() > 58) {
/*       */                     
/* 10886 */                     String[] arrayOfString2 = extractStringLines(arrayOfString1[b2], 58);
/* 10887 */                     for (byte b3 = 0; b3 < arrayOfString2.length; b3++)
/*       */                     {
/* 10889 */                       paramStringBuffer.append(":hp2." + arrayOfString2[b3] + ":ehp2." + NEWLINE);
/*       */                     }
/*       */                   }
/*       */                   else {
/*       */                     
/* 10894 */                     paramStringBuffer.append(":hp2." + arrayOfString1[b2] + ":ehp2." + NEWLINE);
/*       */                   } 
/*       */                 } 
/*       */               } 
/*       */             } 
/* 10899 */             paramStringBuffer.append(parseString(str, 1, "<:>") + "    ");
/* 10900 */             bool = false;
/* 10901 */             b++;
/*       */           } 
/*       */           
/* 10904 */           str1 = str2;
/*       */           
/* 10906 */           Integer integer = (Integer)paramTreeMap1.get(str5);
/* 10907 */           j = integer.intValue();
/* 10908 */           printChar(paramStringBuffer, j - i, "|", " ", false);
/* 10909 */           i = j;
/* 10910 */           paramStringBuffer.append(str6);
/* 10911 */           i++;
/*       */         } 
/*       */       } 
/* 10914 */       printChar(paramStringBuffer, 8 + 2 * paramTreeMap1.size() + 2 - i, "|", " ", false);
/*       */       
/* 10916 */       String[] arrayOfString = extractStringLines(parseString(str, 2, "<:>"), paramInt - 1);
/* 10917 */       for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */         
/* 10919 */         if (0 == b1) {
/*       */           
/* 10921 */           paramStringBuffer.append(" " + arrayOfString[b1] + NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/* 10925 */           printChar(paramStringBuffer, 8, " ", false);
/* 10926 */           printChar(paramStringBuffer, 2 * paramTreeMap1.size() + 1, "|", " ", false);
/* 10927 */           paramStringBuffer.append(" " + arrayOfString[b1] + NEWLINE);
/*       */         } 
/*       */       } 
/*       */       
/* 10931 */       bool = true;
/*       */     } 
/* 10933 */     if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 10939 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 10945 */     paramStringBuffer.append(NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSEOTable(StringBuffer paramStringBuffer) {
/* 10962 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/* 10963 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/* 10964 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */     
/* 10966 */     TreeSet<String> treeSet = new TreeSet();
/* 10967 */     Set<String> set = this.seoTable_TM.keySet();
/* 10968 */     Iterator<String> iterator2 = set.iterator();
/* 10969 */     while (iterator2.hasNext()) {
/*       */       
/* 10971 */       String str = iterator2.next();
/* 10972 */       treeSet.add(parseString(str, 1, "<:>"));
/*       */     } 
/*       */     
/* 10975 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/* 10976 */     for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/* 10978 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/* 10981 */     byte b1 = 0;
/* 10982 */     Iterator<String> iterator1 = treeSet.iterator();
/* 10983 */     while (iterator1.hasNext()) {
/*       */       
/* 10985 */       String str = iterator1.next();
/*       */       
/* 10987 */       iterator2 = set.iterator();
/* 10988 */       while (iterator2.hasNext()) {
/*       */         
/* 10990 */         String str1 = iterator2.next();
/* 10991 */         String str2 = parseString(str1, 1, "<:>");
/*       */         
/* 10993 */         if (str.equals(str2))
/*       */         {
/* 10995 */           arrayOfTreeMap[b1].put(str1, this.seoTable_TM.get(str1));
/*       */         }
/*       */       } 
/* 10998 */       b1++;
/*       */     } 
/*       */     
/* 11001 */     for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/* 11003 */       retrieveSEOTable(paramStringBuffer, arrayOfTreeMap[b2]);
/*       */     }
/*       */     
/* 11006 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */     
/* 11008 */     for (b2 = 0; b2 < treeSet.size(); b2++) {
/*       */       
/* 11010 */       arrayOfTreeMap[b2].clear();
/* 11011 */       arrayOfTreeMap[b2] = null;
/*       */     } 
/* 11013 */     treeSet.clear();
/* 11014 */     treeSet = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSEOTable(StringBuffer paramStringBuffer, TreeMap paramTreeMap) {
/* 11029 */     String str2 = (String)paramTreeMap.firstKey();
/* 11030 */     String str1 = (String)paramTreeMap.get(str2);
/*       */     
/* 11032 */     paramStringBuffer.append(":xmp." + NEWLINE + NEWLINE);
/* 11033 */     if (!str1.equals("WW"))
/*       */     {
/* 11035 */       paramStringBuffer.append(str1 + "--->" + NEWLINE + NEWLINE);
/*       */     }
/* 11037 */     showSEOTableHeader(paramStringBuffer);
/*       */     
/* 11039 */     Set set = paramTreeMap.keySet();
/* 11040 */     Iterator<String> iterator = set.iterator();
/*       */     
/* 11042 */     while (iterator.hasNext()) {
/*       */       
/* 11044 */       String[] arrayOfString1 = new String[0];
/* 11045 */       String[] arrayOfString2 = new String[0];
/* 11046 */       int i = 1;
/*       */ 
/*       */ 
/*       */       
/* 11050 */       String str5 = iterator.next();
/* 11051 */       String str6 = parseString(str5, 2, "<:>");
/* 11052 */       String str7 = parseString(str5, 3, "<:>") + " " + parseString(str5, 4, "<:>");
/* 11053 */       String str8 = parseString(str5, 7, "<:>") + " " + parseString(str5, 8, "<:>");
/* 11054 */       String str9 = parseString(str5, 9, "<:>");
/* 11055 */       if (str9.length() > 12) {
/*       */         
/* 11057 */         arrayOfString1 = extractStringLines(str9, 12);
/* 11058 */         i = arrayOfString1.length;
/*       */       } 
/*       */       
/* 11061 */       String str3 = parseString(str5, 10, "<:>");
/* 11062 */       if (str3.length() > 21) {
/*       */         
/* 11064 */         arrayOfString2 = extractStringLines(str3, 21);
/* 11065 */         if (arrayOfString2.length > i)
/*       */         {
/* 11067 */           i = arrayOfString2.length;
/*       */         }
/*       */       } 
/* 11070 */       String str4 = parseString(str5, 5, "<:>") + " " + parseString(str5, 6, "<:>");
/*       */       
/* 11072 */       for (byte b = 0; b < i; b++) {
/*       */         
/* 11074 */         if (b == 0) {
/*       */           
/* 11076 */           paramStringBuffer.append(" " + str6 + "  " + str7 + "   " + str8);
/* 11077 */           if (i == 1)
/*       */           {
/* 11079 */             paramStringBuffer.append(" " + str9 + " " + str3 + " " + str4);
/* 11080 */             paramStringBuffer.append(NEWLINE);
/*       */           }
/*       */           else
/*       */           {
/* 11084 */             if (b < arrayOfString1.length) {
/*       */               
/* 11086 */               paramStringBuffer.append(" " + arrayOfString1[b]);
/* 11087 */               printChar(paramStringBuffer, 12 - arrayOfString1[b].length(), " ", false);
/*       */             }
/*       */             else {
/*       */               
/* 11091 */               paramStringBuffer.append(" " + str9);
/*       */             } 
/*       */             
/* 11094 */             if (b < arrayOfString2.length) {
/*       */               
/* 11096 */               paramStringBuffer.append(" " + arrayOfString2[b]);
/* 11097 */               printChar(paramStringBuffer, 21 - arrayOfString2[b].length(), " ", false);
/*       */             }
/*       */             else {
/*       */               
/* 11101 */               paramStringBuffer.append(" " + str3);
/*       */             } 
/* 11103 */             paramStringBuffer.append(" " + str4 + NEWLINE);
/*       */           }
/*       */         
/*       */         } else {
/*       */           
/* 11108 */           printChar(paramStringBuffer, 27, " ", false);
/* 11109 */           if (b < arrayOfString1.length) {
/*       */             
/* 11111 */             paramStringBuffer.append(" " + arrayOfString1[b]);
/* 11112 */             printChar(paramStringBuffer, 12 - arrayOfString1[b].length(), " ", false);
/*       */           }
/*       */           else {
/*       */             
/* 11116 */             paramStringBuffer.append(" ");
/* 11117 */             printChar(paramStringBuffer, 12, " ", false);
/*       */           } 
/* 11119 */           if (b < arrayOfString2.length) {
/*       */             
/* 11121 */             paramStringBuffer.append(" " + arrayOfString2[b]);
/* 11122 */             printChar(paramStringBuffer, 21 - arrayOfString2[b].length(), " ", false);
/*       */           }
/*       */           else {
/*       */             
/* 11126 */             printChar(paramStringBuffer, 21, " ", false);
/*       */           } 
/* 11128 */           printChar(paramStringBuffer, 6, " ", false);
/* 11129 */           paramStringBuffer.append(NEWLINE);
/*       */         } 
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/* 11135 */     paramStringBuffer.append(NEWLINE);
/* 11136 */     if (!str1.equals("WW"))
/*       */     {
/* 11138 */       paramStringBuffer.append("<---" + str1 + NEWLINE + NEWLINE);
/*       */     }
/* 11140 */     paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void showSEOTableHeader(StringBuffer paramStringBuffer) {
/* 11150 */     paramStringBuffer.append("                            HDD          CD-                   L2" + NEWLINE);
/* 11151 */     paramStringBuffer.append("  Model    Processor Memory Interface    ROM HDD       Other   Cache" + NEWLINE);
/* 11152 */     paramStringBuffer.append("--------------------------------------------------------------------" + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSEODescription(StringBuffer paramStringBuffer) {
/* 11166 */     String str1 = "";
/* 11167 */     String str2 = "";
/* 11168 */     TreeSet[] arrayOfTreeSet1 = null;
/* 11169 */     TreeSet[] arrayOfTreeSet2 = null;
/*       */     
/* 11171 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/* 11172 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/* 11173 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/* 11174 */     int i = this.seoDescription_TM.size();
/* 11175 */     byte b = 1;
/* 11176 */     Set set = this.seoDescription_TM.keySet();
/* 11177 */     Iterator<String> iterator = set.iterator();
/* 11178 */     while (iterator.hasNext()) {
/*       */       
/* 11180 */       if (b % 2 != 0) {
/*       */         
/* 11182 */         str1 = iterator.next();
/* 11183 */         arrayOfTreeSet1 = (TreeSet[])this.seoDescription_TM.get(str1);
/* 11184 */         if (b == i)
/*       */         {
/* 11186 */           retrieveSEODescription(paramStringBuffer, str1, arrayOfTreeSet1, "", null);
/*       */         
/*       */         }
/*       */       }
/*       */       else {
/*       */         
/* 11192 */         str2 = iterator.next();
/* 11193 */         arrayOfTreeSet2 = (TreeSet[])this.seoDescription_TM.get(str2);
/* 11194 */         retrieveSEODescription(paramStringBuffer, str1, arrayOfTreeSet1, str2, arrayOfTreeSet2);
/*       */       } 
/*       */       
/* 11197 */       b++;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveSEODescription(StringBuffer paramStringBuffer, String paramString1, TreeSet[] paramArrayOfTreeSet1, String paramString2, TreeSet[] paramArrayOfTreeSet2) {
/* 11212 */     paramStringBuffer.append(":xmp." + NEWLINE + NEWLINE);
/* 11213 */     showColumnHeaderForSEODescription(paramStringBuffer, paramString1, paramString2);
/*       */     
/* 11215 */     if (paramArrayOfTreeSet2 != null) {
/*       */       
/* 11217 */       showProcForSEODescription1(paramStringBuffer, paramArrayOfTreeSet1[0], paramArrayOfTreeSet2[0]);
/* 11218 */       showInfoForSEODescription1(paramStringBuffer, 1, " Number standard -", paramArrayOfTreeSet1[1], paramArrayOfTreeSet2[1]);
/* 11219 */       showInfoForSEODescription1(paramStringBuffer, 4, " Maximum -", paramArrayOfTreeSet1[0], paramArrayOfTreeSet2[0]);
/* 11220 */       showInfoForSEODescription1(paramStringBuffer, 2, "L2 cache (full speed)", paramArrayOfTreeSet1[1], paramArrayOfTreeSet2[1]);
/* 11221 */       showInfoForSEODescription1(paramStringBuffer, 0, "Memory (400 MHz SDRAM)", paramArrayOfTreeSet1[2], paramArrayOfTreeSet2[2]);
/* 11222 */       showRowForSEODescription1(paramStringBuffer, " RDIMMs -", "", "");
/* 11223 */       showInfoForSEODescription1(paramStringBuffer, 1, " DIMM sockets -", paramArrayOfTreeSet1[3], paramArrayOfTreeSet2[3]);
/* 11224 */       showInfoForSEODescription1(paramStringBuffer, 2, " Address capability", paramArrayOfTreeSet1[3], paramArrayOfTreeSet2[3]);
/* 11225 */       showInfoForSEODescription1(paramStringBuffer, 1, "Video -", paramArrayOfTreeSet1[4], paramArrayOfTreeSet2[4]);
/* 11226 */       showInfoForSEODescription1(paramStringBuffer, 2, " Memory -", paramArrayOfTreeSet1[4], paramArrayOfTreeSet2[4]);
/* 11227 */       showInfoForSEODescription1(paramStringBuffer, 1, "HDD Controller", paramArrayOfTreeSet1[5], paramArrayOfTreeSet2[5]);
/* 11228 */       showRowForSEODescription1(paramStringBuffer, " Channels -", "", "");
/* 11229 */       showRowForSEODescription1(paramStringBuffer, " Connector internal -", "", "");
/* 11230 */       showRowForSEODescription1(paramStringBuffer, " Connector external -", "", "");
/* 11231 */       showInfoForSEODescription1(paramStringBuffer, 2, "IDE Controller", paramArrayOfTreeSet1[5], paramArrayOfTreeSet2[5]);
/* 11232 */       showRowForSEODescription1(paramStringBuffer, " Channels -", "", "");
/* 11233 */       showRowForSEODescription1(paramStringBuffer, " Connector internal -", "", "");
/* 11234 */       showRowForSEODescription1(paramStringBuffer, " Connector external -", "", "");
/* 11235 */       showInfoForSEODescription1(paramStringBuffer, 0, "HDD -", paramArrayOfTreeSet1[6], paramArrayOfTreeSet2[6]);
/* 11236 */       showInfoForSEODescription1(paramStringBuffer, 1, "Total bays -", paramArrayOfTreeSet1[7], paramArrayOfTreeSet2[7]);
/* 11237 */       showInfoForSEODescription2(paramStringBuffer, 2, paramArrayOfTreeSet1[7], paramArrayOfTreeSet2[7], true);
/* 11238 */       showInfoForSEODescription1(paramStringBuffer, 3, "Bays available", paramArrayOfTreeSet1[1], paramArrayOfTreeSet2[1]);
/* 11239 */       showInfoForSEODescription2(paramStringBuffer, 0, paramArrayOfTreeSet1[8], paramArrayOfTreeSet2[8], true);
/* 11240 */       showInfoForSEODescription1(paramStringBuffer, 3, "Total slots -", paramArrayOfTreeSet1[3], paramArrayOfTreeSet2[3]);
/* 11241 */       showInfoForSEODescription2(paramStringBuffer, 4, paramArrayOfTreeSet1[3], paramArrayOfTreeSet2[3], true);
/* 11242 */       showInfoForSEODescription1(paramStringBuffer, 4, "Slots available -", paramArrayOfTreeSet1[1], paramArrayOfTreeSet2[1]);
/* 11243 */       showInfoForSEODescription1(paramStringBuffer, 0, "Management proc. -", paramArrayOfTreeSet1[9], paramArrayOfTreeSet2[9]);
/* 11244 */       showInfoForSEODescription1(paramStringBuffer, 0, "Ethernet controller -", paramArrayOfTreeSet1[10], paramArrayOfTreeSet2[10]);
/* 11245 */       showInfoForSEODescription2(paramStringBuffer, 0, paramArrayOfTreeSet1[11], paramArrayOfTreeSet2[11], false);
/* 11246 */       showRowForSEODescription1(paramStringBuffer, "Diskette drive -", "", "");
/* 11247 */       showInfoForSEODescription1(paramStringBuffer, 1, "Power supply -", paramArrayOfTreeSet1[12], paramArrayOfTreeSet2[12]);
/* 11248 */       showRowForSEODescription1(paramStringBuffer, " Number standard -", "", "");
/* 11249 */       showInfoForSEODescription1(paramStringBuffer, 2, " Power Supply Type -", paramArrayOfTreeSet1[12], paramArrayOfTreeSet2[12]);
/* 11250 */       showRowForSEODescription1(paramStringBuffer, " Auto restart -", "", "");
/*       */     }
/*       */     else {
/*       */       
/* 11254 */       showProcForSEODescription1(paramStringBuffer, paramArrayOfTreeSet1[0], null);
/* 11255 */       showInfoForSEODescription1(paramStringBuffer, 1, " Number standard -", paramArrayOfTreeSet1[1], null);
/* 11256 */       showInfoForSEODescription1(paramStringBuffer, 4, " Maximum -", paramArrayOfTreeSet1[0], null);
/* 11257 */       showInfoForSEODescription1(paramStringBuffer, 2, "L2 cache (full speed)", paramArrayOfTreeSet1[1], null);
/* 11258 */       showInfoForSEODescription1(paramStringBuffer, 0, "Memory (400 MHz SDRAM)", paramArrayOfTreeSet1[2], null);
/* 11259 */       showRowForSEODescription1(paramStringBuffer, " RDIMMs -", "", "");
/* 11260 */       showInfoForSEODescription1(paramStringBuffer, 1, " DIMM sockets -", paramArrayOfTreeSet1[3], null);
/* 11261 */       showInfoForSEODescription1(paramStringBuffer, 2, " Address capability", paramArrayOfTreeSet1[3], null);
/* 11262 */       showInfoForSEODescription1(paramStringBuffer, 1, "Video -", paramArrayOfTreeSet1[4], null);
/* 11263 */       showInfoForSEODescription1(paramStringBuffer, 2, " Memory -", paramArrayOfTreeSet1[4], null);
/* 11264 */       showInfoForSEODescription1(paramStringBuffer, 1, "HDD Controller", paramArrayOfTreeSet1[5], null);
/* 11265 */       showRowForSEODescription1(paramStringBuffer, " Channels -", "", "");
/* 11266 */       showRowForSEODescription1(paramStringBuffer, " Connector internal -", "", "");
/* 11267 */       showRowForSEODescription1(paramStringBuffer, " Connector external -", "", "");
/* 11268 */       showInfoForSEODescription1(paramStringBuffer, 2, "IDE Controller", paramArrayOfTreeSet1[5], null);
/* 11269 */       showRowForSEODescription1(paramStringBuffer, " Channels -", "", "");
/* 11270 */       showRowForSEODescription1(paramStringBuffer, " Connector internal -", "", "");
/* 11271 */       showRowForSEODescription1(paramStringBuffer, " Connector external -", "", "");
/* 11272 */       showInfoForSEODescription1(paramStringBuffer, 0, "HDD -", paramArrayOfTreeSet1[6], null);
/* 11273 */       showInfoForSEODescription1(paramStringBuffer, 1, "Total bays -", paramArrayOfTreeSet1[7], null);
/* 11274 */       showInfoForSEODescription2(paramStringBuffer, 2, paramArrayOfTreeSet1[7], null, true);
/* 11275 */       showInfoForSEODescription1(paramStringBuffer, 3, "Bays available", paramArrayOfTreeSet1[1], null);
/* 11276 */       showInfoForSEODescription2(paramStringBuffer, 0, paramArrayOfTreeSet1[8], null, true);
/* 11277 */       showInfoForSEODescription1(paramStringBuffer, 3, "Total slots -", paramArrayOfTreeSet1[3], null);
/* 11278 */       showInfoForSEODescription2(paramStringBuffer, 4, paramArrayOfTreeSet1[3], null, true);
/* 11279 */       showInfoForSEODescription1(paramStringBuffer, 4, "Slots available -", paramArrayOfTreeSet1[1], null);
/* 11280 */       showInfoForSEODescription1(paramStringBuffer, 0, "Management proc. -", paramArrayOfTreeSet1[9], null);
/* 11281 */       showInfoForSEODescription1(paramStringBuffer, 0, "Ethernet controller -", paramArrayOfTreeSet1[10], null);
/* 11282 */       showInfoForSEODescription2(paramStringBuffer, 0, paramArrayOfTreeSet1[11], null, false);
/* 11283 */       showRowForSEODescription1(paramStringBuffer, "Diskette drive -", "", "");
/* 11284 */       showInfoForSEODescription1(paramStringBuffer, 1, "Power supply -", paramArrayOfTreeSet1[12], null);
/* 11285 */       showRowForSEODescription1(paramStringBuffer, " Number standard -", "", "");
/* 11286 */       showInfoForSEODescription1(paramStringBuffer, 2, " Power Supply Type -", paramArrayOfTreeSet1[12], null);
/* 11287 */       showRowForSEODescription1(paramStringBuffer, " Auto restart -", "", "");
/*       */     } 
/*       */     
/* 11290 */     paramStringBuffer.append(NEWLINE);
/* 11291 */     paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void showColumnHeaderForSEODescription(StringBuffer paramStringBuffer, String paramString1, String paramString2) {
/* 11303 */     if (paramString1.length() > 0) {
/*       */       
/* 11305 */       paramStringBuffer.append(setString(" ", 23));
/* 11306 */       paramStringBuffer.append(paramString1);
/*       */     } 
/* 11308 */     if (paramString2.length() > 0) {
/*       */       
/* 11310 */       paramStringBuffer.append(setString(" ", 10));
/* 11311 */       paramStringBuffer.append(paramString2);
/*       */     } 
/* 11313 */     paramStringBuffer.append(NEWLINE);
/*       */     
/* 11315 */     if (paramString1.length() > 0) {
/*       */       
/* 11317 */       paramStringBuffer.append(setString(" ", 23));
/* 11318 */       printChar(paramStringBuffer, 13, "-", false);
/*       */     } 
/* 11320 */     if (paramString2.length() > 0) {
/*       */       
/* 11322 */       paramStringBuffer.append(setString(" ", 5));
/* 11323 */       printChar(paramStringBuffer, 13, "-", false);
/*       */     } 
/* 11325 */     paramStringBuffer.append(NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void showProcForSEODescription1(StringBuffer paramStringBuffer, TreeSet paramTreeSet1, TreeSet paramTreeSet2) {
/* 11337 */     int i = 0;
/* 11338 */     int j = 0;
/* 11339 */     int k = 0;
/* 11340 */     String str1 = "";
/* 11341 */     Object[] arrayOfObject1 = null;
/* 11342 */     Object[] arrayOfObject2 = null;
/*       */     
/* 11344 */     String str2 = "";
/* 11345 */     String str3 = "";
/* 11346 */     String str4 = "";
/* 11347 */     String str5 = "";
/* 11348 */     String str6 = "";
/* 11349 */     String str7 = "";
/*       */     
/* 11351 */     if (paramTreeSet1 != null) {
/*       */       
/* 11353 */       i = paramTreeSet1.size();
/* 11354 */       arrayOfObject1 = paramTreeSet1.toArray();
/*       */     } 
/*       */     
/* 11357 */     if (paramTreeSet2 != null) {
/*       */       
/* 11359 */       j = paramTreeSet2.size();
/* 11360 */       arrayOfObject2 = paramTreeSet2.toArray();
/*       */     } 
/*       */     
/* 11363 */     if (i > j) {
/*       */       
/* 11365 */       k = i;
/*       */     }
/*       */     else {
/*       */       
/* 11369 */       k = j;
/*       */     } 
/*       */     
/* 11372 */     for (byte b = 0; b < k; b++) {
/*       */       
/* 11374 */       if (b < i) {
/*       */         
/* 11376 */         str1 = (String)arrayOfObject1[b];
/* 11377 */         str2 = parseString(str1, 1, "<:>");
/* 11378 */         str3 = parseString(str1, 2, "<:>");
/* 11379 */         str4 = parseString(str1, 3, "<:>");
/*       */       }
/*       */       else {
/*       */         
/* 11383 */         str2 = setString(" ", 17);
/* 11384 */         str3 = setString(" ", 17);
/* 11385 */         str4 = setString(" ", 17);
/*       */       } 
/*       */       
/* 11388 */       if (b < j) {
/*       */         
/* 11390 */         str1 = (String)arrayOfObject2[b];
/* 11391 */         str5 = parseString(str1, 1, "<:>");
/* 11392 */         str6 = parseString(str1, 2, "<:>");
/* 11393 */         str7 = parseString(str1, 3, "<:>");
/*       */       }
/*       */       else {
/*       */         
/* 11397 */         str5 = setString(" ", 17);
/* 11398 */         str6 = setString(" ", 17);
/* 11399 */         str7 = setString(" ", 17);
/*       */       } 
/*       */       
/* 11402 */       showRowForSEODescription1(paramStringBuffer, "Processor -", str2, str5);
/* 11403 */       showRowForSEODescription1(paramStringBuffer, " Internal speed -", str3, str6);
/* 11404 */       showRowForSEODescription1(paramStringBuffer, " External speed -", str4, str7);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void showInfoForSEODescription1(StringBuffer paramStringBuffer, int paramInt, String paramString, TreeSet paramTreeSet1, TreeSet paramTreeSet2) {
/* 11419 */     int i = 0;
/* 11420 */     int j = 0;
/* 11421 */     int k = 0;
/* 11422 */     String str1 = "";
/* 11423 */     Object[] arrayOfObject1 = null;
/* 11424 */     Object[] arrayOfObject2 = null;
/*       */     
/* 11426 */     String str2 = "";
/* 11427 */     String str3 = "";
/*       */     
/* 11429 */     if (paramTreeSet1 != null) {
/*       */       
/* 11431 */       i = paramTreeSet1.size();
/* 11432 */       arrayOfObject1 = paramTreeSet1.toArray();
/*       */     } 
/*       */     
/* 11435 */     if (paramTreeSet2 != null) {
/*       */       
/* 11437 */       j = paramTreeSet2.size();
/* 11438 */       arrayOfObject2 = paramTreeSet2.toArray();
/*       */     } 
/*       */     
/* 11441 */     if (i > j) {
/*       */       
/* 11443 */       k = i;
/*       */     }
/*       */     else {
/*       */       
/* 11447 */       k = j;
/*       */     } 
/*       */     
/* 11450 */     for (byte b = 0; b < k; b++) {
/*       */       
/* 11452 */       if (b < i) {
/*       */         
/* 11454 */         str1 = (String)arrayOfObject1[b];
/* 11455 */         str2 = parseString(str1, paramInt, "<:>");
/*       */       }
/*       */       else {
/*       */         
/* 11459 */         str2 = setString(" ", 17);
/*       */       } 
/*       */       
/* 11462 */       if (b < j) {
/*       */         
/* 11464 */         str1 = (String)arrayOfObject2[b];
/* 11465 */         str3 = parseString(str1, paramInt, "<:>");
/*       */       }
/*       */       else {
/*       */         
/* 11469 */         str3 = setString(" ", 17);
/*       */       } 
/*       */       
/* 11472 */       showRowForSEODescription1(paramStringBuffer, paramString, str2, str3);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void showInfoForSEODescription2(StringBuffer paramStringBuffer, int paramInt, TreeSet paramTreeSet1, TreeSet paramTreeSet2, boolean paramBoolean) {
/* 11487 */     int i = 0;
/* 11488 */     int j = 0;
/* 11489 */     int k = 0;
/* 11490 */     String str1 = "";
/* 11491 */     Object[] arrayOfObject1 = null;
/* 11492 */     Object[] arrayOfObject2 = null;
/*       */     
/* 11494 */     String str2 = "";
/* 11495 */     String str3 = "";
/*       */     
/* 11497 */     if (paramTreeSet1 != null) {
/*       */       
/* 11499 */       i = paramTreeSet1.size();
/* 11500 */       arrayOfObject1 = paramTreeSet1.toArray();
/*       */     } 
/*       */     
/* 11503 */     if (paramTreeSet2 != null) {
/*       */       
/* 11505 */       j = paramTreeSet2.size();
/* 11506 */       arrayOfObject2 = paramTreeSet2.toArray();
/*       */     } 
/*       */     
/* 11509 */     if (i > j) {
/*       */       
/* 11511 */       k = i;
/*       */     }
/*       */     else {
/*       */       
/* 11515 */       k = j;
/*       */     } 
/*       */     
/* 11518 */     for (byte b = 0; b < k; b++) {
/*       */       
/* 11520 */       if (b < i) {
/*       */         
/* 11522 */         str1 = (String)arrayOfObject1[b];
/* 11523 */         str2 = parseString(str1, paramInt, "<:>");
/*       */       }
/*       */       else {
/*       */         
/* 11527 */         str2 = "<:><::>";
/*       */       } 
/*       */       
/* 11530 */       if (b < j) {
/*       */         
/* 11532 */         str1 = (String)arrayOfObject2[b];
/* 11533 */         str3 = parseString(str1, paramInt, "<:>");
/*       */       }
/*       */       else {
/*       */         
/* 11537 */         str3 = "<:><::>";
/*       */       } 
/*       */       
/* 11540 */       showRowForSEODescription2(paramStringBuffer, str2, str3, paramInt, paramBoolean);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void showRowForSEODescription1(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3) {
/* 11554 */     String[] arrayOfString1 = new String[0];
/* 11555 */     String[] arrayOfString2 = new String[0];
/* 11556 */     int i = 1;
/*       */     
/* 11558 */     if (paramString2.length() > 17) {
/*       */       
/* 11560 */       arrayOfString1 = extractStringLines(paramString2, 17);
/* 11561 */       i = arrayOfString1.length;
/*       */     } 
/*       */     
/* 11564 */     if (paramString3.length() > 17) {
/*       */       
/* 11566 */       arrayOfString2 = extractStringLines(paramString3, 17);
/* 11567 */       if (arrayOfString2.length > i)
/*       */       {
/* 11569 */         i = arrayOfString2.length;
/*       */       }
/*       */     } 
/*       */     
/* 11573 */     for (byte b = 0; b < i; b++) {
/*       */       
/* 11575 */       if (b == 0) {
/*       */         
/* 11577 */         paramStringBuffer.append(paramString1);
/* 11578 */         printChar(paramStringBuffer, 22 - paramString1.length(), " ", false);
/* 11579 */         if (i == 1)
/*       */         {
/* 11581 */           paramStringBuffer.append(" " + paramString2 + " " + paramString3);
/* 11582 */           paramStringBuffer.append(NEWLINE);
/*       */         }
/*       */         else
/*       */         {
/* 11586 */           if (b < arrayOfString1.length) {
/*       */             
/* 11588 */             paramStringBuffer.append(" " + arrayOfString1[b]);
/* 11589 */             printChar(paramStringBuffer, 17 - arrayOfString1[b].length(), " ", false);
/*       */           }
/*       */           else {
/*       */             
/* 11593 */             paramStringBuffer.append(" " + paramString2);
/*       */           } 
/*       */           
/* 11596 */           if (b < arrayOfString2.length) {
/*       */             
/* 11598 */             paramStringBuffer.append(" " + arrayOfString2[b]);
/* 11599 */             printChar(paramStringBuffer, 17 - arrayOfString2[b].length(), " ", false);
/*       */           }
/*       */           else {
/*       */             
/* 11603 */             paramStringBuffer.append(" " + paramString3);
/*       */           } 
/* 11605 */           paramStringBuffer.append(NEWLINE);
/*       */         }
/*       */       
/*       */       } else {
/*       */         
/* 11610 */         printChar(paramStringBuffer, 22, " ", false);
/* 11611 */         if (b < arrayOfString1.length) {
/*       */           
/* 11613 */           paramStringBuffer.append(" " + arrayOfString1[b]);
/* 11614 */           printChar(paramStringBuffer, 17 - arrayOfString1[b].length(), " ", false);
/*       */         }
/*       */         else {
/*       */           
/* 11618 */           paramStringBuffer.append(" ");
/* 11619 */           printChar(paramStringBuffer, 17, " ", false);
/*       */         } 
/* 11621 */         if (b < arrayOfString2.length) {
/*       */           
/* 11623 */           paramStringBuffer.append(" " + arrayOfString2[b]);
/* 11624 */           printChar(paramStringBuffer, 17 - arrayOfString2[b].length(), " ", false);
/*       */         }
/*       */         else {
/*       */           
/* 11628 */           printChar(paramStringBuffer, 17, " ", false);
/*       */         } 
/* 11630 */         paramStringBuffer.append(NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void showRowForSEODescription2(StringBuffer paramStringBuffer, String paramString1, String paramString2, int paramInt, boolean paramBoolean) {
/* 11638 */     String str1 = "";
/* 11639 */     String str2 = "";
/* 11640 */     int i = 0;
/*       */     
/* 11642 */     Vector vector1 = new Vector();
/* 11643 */     Vector vector2 = new Vector();
/*       */     
/* 11645 */     if (paramString1.length() > 0)
/*       */     {
/* 11647 */       str1 = parseString(paramString1, paramInt, "<:>");
/*       */     }
/* 11649 */     if (paramString2.length() > 0)
/*       */     {
/* 11651 */       str2 = parseString(paramString2, paramInt, "<:>");
/*       */     }
/*       */     
/* 11654 */     TreeSet<String> treeSet = new TreeSet();
/* 11655 */     i = countDelims(str1, "<::>") + 1;
/* 11656 */     if (i > 1)
/*       */     {
/* 11658 */       for (byte b = 1; b <= i; b++) {
/*       */         
/* 11660 */         if (b % 2 != 0)
/*       */         {
/* 11662 */           if (!parseString(str1, b, "<::>").equals("") && !parseString(str1, b + 1, "<::>").equals(""))
/*       */           {
/* 11664 */             treeSet.add(parseString(str1, b, "<::>"));
/*       */           }
/*       */         }
/*       */       } 
/*       */     }
/* 11669 */     i = countDelims(str2, "<::>") + 1;
/* 11670 */     if (i > 1)
/*       */     {
/* 11672 */       for (byte b = 1; b <= i; b++) {
/*       */         
/* 11674 */         if (b % 2 != 0)
/*       */         {
/* 11676 */           if (!parseString(str2, b, "<::>").equals("") && !parseString(str2, b + 1, "<::>").equals(""))
/*       */           {
/* 11678 */             treeSet.add(parseString(str2, b, "<::>"));
/*       */           }
/*       */         }
/*       */       } 
/*       */     }
/*       */     
/* 11684 */     Iterator<String> iterator = treeSet.iterator();
/* 11685 */     while (iterator.hasNext()) {
/*       */       
/* 11687 */       int j = 0;
/* 11688 */       String str = iterator.next();
/* 11689 */       i = 0;
/*       */       
/* 11691 */       j = countTypes(str1, str, "<::>", vector1);
/* 11692 */       i = countTypes(str2, str, "<::>", vector2);
/* 11693 */       if (i > j)
/*       */       {
/* 11695 */         j = i;
/*       */       }
/*       */       
/* 11698 */       showRowForSEODescription2(paramStringBuffer, str, j, vector1, vector2, paramBoolean);
/*       */       
/* 11700 */       vector1.clear();
/* 11701 */       vector2.clear();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int countTypes(String paramString1, String paramString2, String paramString3, Vector<String> paramVector) {
/* 11716 */     int i = 0;
/* 11717 */     byte b = 0;
/*       */     
/* 11719 */     i = countDelims(paramString1, paramString3) + 1;
/*       */     
/* 11721 */     if (i > 1) {
/*       */       
/* 11723 */       String str = "";
/* 11724 */       for (byte b1 = 1; b1 <= i; b1++) {
/*       */         
/* 11726 */         if (b1 % 2 != 0) {
/*       */           
/* 11728 */           str = parseString(paramString1, b1, "<::>");
/*       */           
/* 11730 */           if (str.equals(paramString2))
/*       */           {
/* 11732 */             if (!str.equals("") && !parseString(paramString1, b1 + 1, "<::>").equals("")) {
/*       */               
/* 11734 */               b++;
/* 11735 */               paramVector.add(parseString(paramString1, b1 + 1, "<::>"));
/*       */             } 
/*       */           }
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/* 11742 */     return b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void showRowForSEODescription2(StringBuffer paramStringBuffer, String paramString, int paramInt, Vector<String> paramVector1, Vector<String> paramVector2, boolean paramBoolean) {
/* 11757 */     String str1 = "";
/* 11758 */     String str2 = "";
/*       */     
/* 11760 */     for (byte b = 0; b < paramInt; b++) {
/*       */       
/* 11762 */       if (b < paramVector1.size()) {
/*       */         
/* 11764 */         str1 = paramVector1.get(b);
/*       */       }
/*       */       else {
/*       */         
/* 11768 */         str1 = " ";
/*       */       } 
/* 11770 */       if (b < paramVector2.size()) {
/*       */         
/* 11772 */         str2 = paramVector2.get(b);
/*       */       }
/*       */       else {
/*       */         
/* 11776 */         str2 = " ";
/*       */       } 
/* 11778 */       showRowForSEODescription2(paramStringBuffer, paramString, str1, str2, paramBoolean);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void showRowForSEODescription2(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 11794 */     String arrayOfString[], str = "";
/*       */     
/* 11796 */     if (paramBoolean) {
/*       */       
/* 11798 */       arrayOfString = extractStringLines(paramString1, 21);
/* 11799 */       str = " ";
/*       */     }
/*       */     else {
/*       */       
/* 11803 */       arrayOfString = extractStringLines(paramString1, 22);
/*       */     } 
/* 11805 */     paramString2 = setString(paramString2, 17);
/* 11806 */     paramString3 = setString(paramString3, 17);
/* 11807 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*       */       
/* 11809 */       if (b == 0) {
/*       */         
/* 11811 */         paramStringBuffer.append(str + arrayOfString[b]);
/* 11812 */         if (paramBoolean) {
/*       */           
/* 11814 */           printChar(paramStringBuffer, 21 - arrayOfString[b].length(), " ", false);
/*       */         }
/*       */         else {
/*       */           
/* 11818 */           printChar(paramStringBuffer, 22 - arrayOfString[b].length(), " ", false);
/*       */         } 
/* 11820 */         paramStringBuffer.append(" " + paramString2);
/* 11821 */         paramStringBuffer.append(" " + paramString3 + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 11825 */         paramStringBuffer.append(str + arrayOfString[b] + NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveFeatureMatrixError(StringBuffer paramStringBuffer) {
/* 11837 */     String str1 = "";
/* 11838 */     String str2 = "";
/* 11839 */     String str3 = "";
/* 11840 */     String str4 = "";
/* 11841 */     String str5 = "";
/* 11842 */     String str6 = "";
/* 11843 */     String str7 = "";
/* 11844 */     String str8 = "";
/*       */ 
/*       */     
/* 11847 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/* 11848 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/* 11849 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */     
/* 11851 */     if (this.featureMatrixError.size() > 0 && 1 == this.format)
/*       */     {
/* 11853 */       paramStringBuffer.append(":h2.Feature Matrix Error" + NEWLINE);
/*       */     }
/*       */     
/* 11856 */     Iterator<String> iterator = this.featureMatrixError.iterator();
/* 11857 */     while (iterator.hasNext()) {
/*       */       
/* 11859 */       String str = iterator.next();
/* 11860 */       str2 = parseString(str, 2, "<:>");
/* 11861 */       str5 = parseString(str, 3, "<:>");
/* 11862 */       str6 = parseString(str, 4, "<:>");
/* 11863 */       str4 = str5 + "-" + str6;
/* 11864 */       str7 = parseString(str, 5, "<:>");
/* 11865 */       str8 = parseString(str, 6, "<:>");
/*       */       
/* 11867 */       if (!str2.equals(str1) || !str4.equals(str3)) {
/*       */         
/* 11869 */         paramStringBuffer.append(NEWLINE + "Report Name: " + str2 + NEWLINE);
/* 11870 */         paramStringBuffer.append("Machine Type:        " + str5 + NEWLINE);
/* 11871 */         paramStringBuffer.append("Model:               " + str6 + NEWLINE);
/* 11872 */         paramStringBuffer.append("Missing Attributes:" + NEWLINE);
/*       */       } 
/*       */       
/* 11875 */       paramStringBuffer.append("   Entity: " + str7 + ", Attribute: " + str8 + NEWLINE);
/*       */       
/* 11877 */       str1 = str2;
/* 11878 */       str3 = str4;
/*       */     } 
/* 11880 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void updateGeoHT(EntityItem paramEntityItem, String paramString) {
/* 11891 */     if (null == this.geoHT.get(paramString))
/*       */     {
/* 11893 */       this.geoHT.put(paramString, new StringBuffer("NNNNN"));
/*       */     }
/* 11895 */     if (checkRfaGeoUS(paramEntityItem))
/*       */     {
/* 11897 */       updateGeo(paramString, "US");
/*       */     }
/* 11899 */     if (checkRfaGeoAP(paramEntityItem))
/*       */     {
/* 11901 */       updateGeo(paramString, "AP");
/*       */     }
/* 11903 */     if (checkRfaGeoLA(paramEntityItem))
/*       */     {
/* 11905 */       updateGeo(paramString, "LA");
/*       */     }
/* 11907 */     if (checkRfaGeoCAN(paramEntityItem))
/*       */     {
/* 11909 */       updateGeo(paramString, "CAN");
/*       */     }
/* 11911 */     if (checkRfaGeoEMEA(paramEntityItem))
/*       */     {
/* 11913 */       updateGeo(paramString, "EMEA");
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean checkRfaGeoUS(EntityItem paramEntityItem) {
/* 11925 */     String str1 = paramEntityItem.getKey();
/* 11926 */     String str2 = (String)this.usGeoHT.get(str1);
/*       */     
/* 11928 */     if (null == str2) {
/*       */       
/* 11930 */       if (this.gal.isRfaGeoUS(paramEntityItem)) {
/*       */         
/* 11932 */         this.usGeoHT.put(str1, "Y");
/* 11933 */         return true;
/*       */       } 
/*       */ 
/*       */       
/* 11937 */       this.usGeoHT.put(str1, "F");
/* 11938 */       return false;
/*       */     } 
/*       */     
/* 11941 */     if (str2.equals("Y"))
/*       */     {
/* 11943 */       return true;
/*       */     }
/*       */ 
/*       */     
/* 11947 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean checkRfaGeoAP(EntityItem paramEntityItem) {
/* 11959 */     String str1 = paramEntityItem.getKey();
/* 11960 */     String str2 = (String)this.apGeoHT.get(str1);
/*       */     
/* 11962 */     if (null == str2) {
/*       */       
/* 11964 */       if (this.gal.isRfaGeoAP(paramEntityItem)) {
/*       */         
/* 11966 */         this.apGeoHT.put(str1, "Y");
/* 11967 */         return true;
/*       */       } 
/*       */ 
/*       */       
/* 11971 */       this.apGeoHT.put(str1, "F");
/* 11972 */       return false;
/*       */     } 
/*       */     
/* 11975 */     if (str2.equals("Y"))
/*       */     {
/* 11977 */       return true;
/*       */     }
/*       */ 
/*       */     
/* 11981 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean checkRfaGeoLA(EntityItem paramEntityItem) {
/* 11993 */     String str1 = paramEntityItem.getKey();
/* 11994 */     String str2 = (String)this.laGeoHT.get(str1);
/*       */     
/* 11996 */     if (null == str2) {
/*       */       
/* 11998 */       if (this.gal.isRfaGeoLA(paramEntityItem)) {
/*       */         
/* 12000 */         this.laGeoHT.put(str1, "Y");
/* 12001 */         return true;
/*       */       } 
/*       */ 
/*       */       
/* 12005 */       this.laGeoHT.put(str1, "F");
/* 12006 */       return false;
/*       */     } 
/*       */     
/* 12009 */     if (str2.equals("Y"))
/*       */     {
/* 12011 */       return true;
/*       */     }
/*       */ 
/*       */     
/* 12015 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean checkRfaGeoCAN(EntityItem paramEntityItem) {
/* 12027 */     String str1 = paramEntityItem.getKey();
/* 12028 */     String str2 = (String)this.canGeoHT.get(str1);
/*       */     
/* 12030 */     if (null == str2) {
/*       */       
/* 12032 */       if (this.gal.isRfaGeoCAN(paramEntityItem)) {
/*       */         
/* 12034 */         this.canGeoHT.put(str1, "Y");
/* 12035 */         return true;
/*       */       } 
/*       */ 
/*       */       
/* 12039 */       this.canGeoHT.put(str1, "F");
/* 12040 */       return false;
/*       */     } 
/*       */     
/* 12043 */     if (str2.equals("Y"))
/*       */     {
/* 12045 */       return true;
/*       */     }
/*       */ 
/*       */     
/* 12049 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean checkRfaGeoEMEA(EntityItem paramEntityItem) {
/* 12061 */     String str1 = paramEntityItem.getKey();
/* 12062 */     String str2 = (String)this.emeaGeoHT.get(str1);
/*       */     
/* 12064 */     if (null == str2) {
/*       */       
/* 12066 */       if (this.gal.isRfaGeoEMEA(paramEntityItem)) {
/*       */         
/* 12068 */         this.emeaGeoHT.put(str1, "Y");
/* 12069 */         return true;
/*       */       } 
/*       */ 
/*       */       
/* 12073 */       this.emeaGeoHT.put(str1, "F");
/* 12074 */       return false;
/*       */     } 
/*       */     
/* 12077 */     if (str2.equals("Y"))
/*       */     {
/* 12079 */       return true;
/*       */     }
/*       */ 
/*       */     
/* 12083 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void updateGeo(String paramString1, String paramString2) {
/* 12095 */     StringBuffer stringBuffer = (StringBuffer)this.geoHT.get(paramString1);
/*       */     
/* 12097 */     if (paramString2.equalsIgnoreCase("US"))
/*       */     {
/* 12099 */       stringBuffer.setCharAt(0, 'Y');
/*       */     }
/* 12101 */     if (paramString2.equalsIgnoreCase("AP"))
/*       */     {
/* 12103 */       stringBuffer.setCharAt(1, 'Y');
/*       */     }
/* 12105 */     if (paramString2.equalsIgnoreCase("LA"))
/*       */     {
/* 12107 */       stringBuffer.setCharAt(2, 'Y');
/*       */     }
/* 12109 */     if (paramString2.equalsIgnoreCase("CAN"))
/*       */     {
/* 12111 */       stringBuffer.setCharAt(3, 'Y');
/*       */     }
/* 12113 */     if (paramString2.equalsIgnoreCase("EMEA"))
/*       */     {
/* 12115 */       stringBuffer.setCharAt(4, 'Y');
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String getGeo(String paramString) {
/* 12127 */     String str = "";
/*       */     
/* 12129 */     boolean bool1 = false;
/* 12130 */     boolean bool2 = false;
/* 12131 */     boolean bool3 = false;
/* 12132 */     boolean bool4 = false;
/* 12133 */     boolean bool5 = false;
/*       */     
/* 12135 */     StringBuffer stringBuffer = (StringBuffer)this.geoHT.get(paramString);
/* 12136 */     log(stringBuffer.toString());
/* 12137 */     if (stringBuffer.charAt(0) == 'Y')
/*       */     {
/* 12139 */       bool5 = true;
/*       */     }
/* 12141 */     if (stringBuffer.charAt(1) == 'Y')
/*       */     {
/* 12143 */       bool1 = true;
/*       */     }
/* 12145 */     if (stringBuffer.charAt(2) == 'Y')
/*       */     {
/* 12147 */       bool4 = true;
/*       */     }
/* 12149 */     if (stringBuffer.charAt(3) == 'Y')
/*       */     {
/* 12151 */       bool2 = true;
/*       */     }
/* 12153 */     if (stringBuffer.charAt(4) == 'Y')
/*       */     {
/* 12155 */       bool3 = true;
/*       */     }
/*       */     
/* 12158 */     if (bool1 && bool2 && bool3 && bool4 && bool5)
/*       */     {
/* 12160 */       return "WW";
/*       */     }
/*       */     
/* 12163 */     if (bool5)
/*       */     {
/* 12165 */       str = "US, ";
/*       */     }
/* 12167 */     if (bool1)
/*       */     {
/* 12169 */       str = str + "AP, ";
/*       */     }
/* 12171 */     if (bool4)
/*       */     {
/* 12173 */       str = str + "LA, ";
/*       */     }
/* 12175 */     if (bool2)
/*       */     {
/* 12177 */       str = str + "CAN, ";
/*       */     }
/* 12179 */     if (bool3)
/*       */     {
/* 12181 */       str = str + "EMEA, ";
/*       */     }
/*       */     
/* 12184 */     if (str.length() > 0)
/*       */     {
/* 12186 */       return str.substring(0, str.length() - 2);
/*       */     }
/*       */ 
/*       */     
/* 12190 */     return "No GEO Found";
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean isEntityWithMatchedAttr(EntityItem paramEntityItem, Hashtable paramHashtable) {
/* 12207 */     boolean bool = true;
/* 12208 */     for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/*       */       
/* 12210 */       String str = enumeration.nextElement();
/*       */       
/* 12212 */       bool = (bool && entityMatchesAttr(paramEntityItem, str, paramHashtable.get(str).toString())) ? true : false;
/*       */     } 
/* 12214 */     if (bool)
/*       */     {
/* 12216 */       return true;
/*       */     }
/*       */ 
/*       */     
/* 12220 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void addToTreeMap(String paramString1, String paramString2, TreeMap<String, String> paramTreeMap) {
/* 12228 */     paramTreeMap.put(paramString1, paramString2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private EANEntity getUpLinkEntityItem(EntityItem paramEntityItem, String paramString) {
/* 12240 */     EANEntity eANEntity = null;
/*       */     
/* 12242 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*       */       
/* 12244 */       EANEntity eANEntity1 = paramEntityItem.getUpLink(b);
/* 12245 */       if (eANEntity1.getEntityType().equals(paramString)) {
/*       */         
/* 12247 */         eANEntity = eANEntity1;
/*       */         
/*       */         break;
/*       */       } 
/*       */     } 
/* 12252 */     return eANEntity;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private EANEntity getDownLinkEntityItem(EntityItem paramEntityItem, String paramString) {
/* 12264 */     EANEntity eANEntity = null;
/*       */     
/* 12266 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*       */       
/* 12268 */       EANEntity eANEntity1 = paramEntityItem.getDownLink(b);
/* 12269 */       if (eANEntity1.getEntityType().equals(paramString)) {
/*       */         
/* 12271 */         eANEntity = eANEntity1;
/*       */         
/*       */         break;
/*       */       } 
/*       */     } 
/* 12276 */     return eANEntity;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String parseString(String paramString1, int paramInt, String paramString2) {
/* 12342 */     if (paramString1.indexOf(paramString2) == -1) {
/* 12343 */       return paramString1;
/*       */     }
/* 12345 */     int i = paramInt - 1;
/* 12346 */     String str = "";
/* 12347 */     int j = -paramString2.length();
/* 12348 */     int k = paramString2.length();
/* 12349 */     Vector<Integer> vector = new Vector(1);
/* 12350 */     if (0 == paramInt || paramInt < 0)
/*       */     {
/* 12352 */       return paramString1;
/*       */     }
/*       */     
/* 12355 */     if (0 == i)
/*       */     {
/* 12357 */       return paramString1.substring(0, paramString1.indexOf(paramString2));
/*       */     }
/*       */ 
/*       */     
/*       */     while (true) {
/* 12362 */       j = paramString1.indexOf(paramString2, j + k);
/*       */       
/* 12364 */       if (j < 0) {
/*       */         break;
/*       */       }
/*       */ 
/*       */       
/* 12369 */       vector.addElement(new Integer(j));
/*       */     } 
/*       */     
/* 12372 */     if (i > vector.size())
/*       */     {
/* 12374 */       return paramString1;
/*       */     }
/*       */     
/* 12377 */     if (i == vector.size()) {
/*       */       
/* 12379 */       str = paramString1.substring(((Integer)vector.get(i - 1)).intValue() + k);
/*       */     }
/*       */     else {
/*       */       
/* 12383 */       str = paramString1.substring(((Integer)vector.get(i - 1)).intValue() + k, ((Integer)vector.get(i)).intValue());
/*       */     } 
/*       */     
/* 12386 */     return str;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int countDelims(String paramString1, String paramString2) {
/* 12398 */     byte b = 0;
/* 12399 */     int i = -paramString2.length();
/*       */ 
/*       */     
/*       */     while (true) {
/* 12403 */       i = paramString1.indexOf(paramString2, i + 3);
/*       */       
/* 12405 */       if (i < 0) {
/*       */         break;
/*       */       }
/*       */ 
/*       */       
/* 12410 */       b++;
/*       */     } 
/*       */     
/* 12413 */     return b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String[] extractStringLines(String paramString, int paramInt) {
/* 12426 */     int j = 0;
/* 12427 */     int k = 0;
/* 12428 */     int m = 0;
/*       */     
/* 12430 */     int n = paramString.length();
/* 12431 */     byte b1 = 0;
/*       */     
/* 12433 */     byte b2 = 0;
/*       */     
/* 12435 */     byte b3 = 0;
/*       */     
/* 12437 */     paramString = paramString.trim();
/* 12438 */     if (0 == paramString.length()) {
/*       */       
/* 12440 */       String[] arrayOfString = new String[1];
/* 12441 */       arrayOfString[0] = " ";
/* 12442 */       return arrayOfString;
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12450 */     String[] arrayOfString1 = new String[n]; byte b4;
/* 12451 */     for (b4 = 0; b4 < n; b4++)
/*       */     {
/* 12453 */       arrayOfString1[b4] = new String();
/*       */     }
/*       */     
/* 12456 */     int i = paramString.length();
/*       */     
/* 12458 */     while (j < i) {
/*       */       
/* 12460 */       if (paramString.charAt(j) == ' ')
/*       */       {
/* 12462 */         m = j;
/*       */       }
/*       */       
/* 12465 */       if (j == k + paramInt)
/*       */       {
/* 12467 */         if (0 == m) {
/*       */           
/* 12469 */           arrayOfString1[b1] = paramString.substring(k, j).trim();
/*       */           
/* 12471 */           k = j;
/* 12472 */           b1++;
/*       */ 
/*       */         
/*       */         }
/* 12476 */         else if (k == m + 1) {
/*       */           
/* 12478 */           arrayOfString1[b1] = paramString.substring(k, j).trim();
/* 12479 */           k = j;
/* 12480 */           b1++;
/*       */         }
/*       */         else {
/*       */           
/* 12484 */           arrayOfString1[b1] = paramString.substring(k, m + 1).trim();
/* 12485 */           k = m + 1;
/* 12486 */           b1++;
/*       */         } 
/*       */       }
/*       */       
/* 12490 */       j++;
/*       */     } 
/*       */     
/* 12493 */     arrayOfString1[b1] = paramString.substring(k).trim();
/*       */     
/* 12495 */     b2 = 0;
/*       */     
/* 12497 */     for (b4 = 0; b4 < n; b4++) {
/*       */       
/* 12499 */       if (arrayOfString1[b4].length() > 0)
/*       */       {
/* 12501 */         b2++;
/*       */       }
/*       */     } 
/*       */     
/* 12505 */     String[] arrayOfString2 = new String[b2];
/* 12506 */     for (b4 = 0; b4 < b2; b4++)
/*       */     {
/* 12508 */       arrayOfString2[b4] = new String();
/*       */     }
/*       */     
/* 12511 */     b3 = 0;
/* 12512 */     for (b4 = 0; b4 < n; b4++) {
/*       */       
/* 12514 */       if (arrayOfString1[b4].length() > 0)
/*       */       {
/* 12516 */         arrayOfString2[b3++] = arrayOfString1[b4];
/*       */       }
/*       */     } 
/*       */     
/* 12520 */     return arrayOfString2;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String[] getStringTokens(String paramString1, String paramString2) {
/* 12532 */     StringTokenizer stringTokenizer1 = new StringTokenizer(paramString1, paramString2);
/* 12533 */     StringTokenizer stringTokenizer2 = new StringTokenizer(paramString1, paramString2);
/* 12534 */     byte b = 0;
/*       */ 
/*       */     
/* 12537 */     while (stringTokenizer1.hasMoreTokens()) {
/*       */       
/* 12539 */       b++;
/* 12540 */       stringTokenizer1.nextToken();
/*       */     } 
/*       */     
/* 12543 */     String[] arrayOfString = new String[b];
/*       */     
/* 12545 */     b = 0;
/* 12546 */     while (stringTokenizer2.hasMoreTokens()) {
/*       */       
/* 12548 */       arrayOfString[b] = stringTokenizer2.nextToken();
/* 12549 */       b++;
/*       */     } 
/*       */     
/* 12552 */     return arrayOfString;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void printChar(StringBuffer paramStringBuffer, int paramInt, String paramString, boolean paramBoolean) {
/* 12565 */     if (!paramBoolean) {
/*       */       
/* 12567 */       for (byte b = 0; b < paramInt; b++)
/*       */       {
/* 12569 */         paramStringBuffer.append(paramString);
/*       */       }
/*       */     }
/*       */     else {
/*       */       
/* 12574 */       paramStringBuffer.append("|");
/* 12575 */       for (byte b = 0; b < paramInt - 2; b++)
/*       */       {
/* 12577 */         paramStringBuffer.append(paramString);
/*       */       }
/* 12579 */       paramStringBuffer.append("|");
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void printChar(StringBuffer paramStringBuffer, int paramInt, String paramString1, String paramString2, boolean paramBoolean) {
/* 12594 */     boolean bool = true;
/*       */     
/* 12596 */     if (!paramBoolean) {
/*       */       
/* 12598 */       for (byte b = 0; b < paramInt; b++) {
/*       */         
/* 12600 */         if (bool)
/*       */         {
/* 12602 */           paramStringBuffer.append(paramString1);
/* 12603 */           bool = false;
/*       */         }
/*       */         else
/*       */         {
/* 12607 */           paramStringBuffer.append(paramString2);
/* 12608 */           bool = true;
/*       */         }
/*       */       
/*       */       } 
/*       */     } else {
/*       */       
/* 12614 */       paramStringBuffer.append("|");
/* 12615 */       for (byte b = 0; b < paramInt - 2; b++) {
/*       */         
/* 12617 */         if (bool) {
/*       */           
/* 12619 */           paramStringBuffer.append(paramString1);
/* 12620 */           bool = false;
/*       */         }
/*       */         else {
/*       */           
/* 12624 */           paramStringBuffer.append(paramString2);
/* 12625 */           bool = true;
/*       */         } 
/*       */       } 
/* 12628 */       paramStringBuffer.append("|");
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String myDate() {
/* 12639 */     Date date = new Date();
/* 12640 */     String str = new String();
/*       */     
/* 12642 */     StringBuffer stringBuffer = new StringBuffer();
/*       */     
/* 12644 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
/* 12645 */     str = simpleDateFormat.format(date);
/*       */     
/* 12647 */     stringBuffer.append("Created on ");
/* 12648 */     stringBuffer.append(str);
/*       */     
/* 12650 */     if (stringBuffer.length() > 22)
/*       */     {
/* 12652 */       stringBuffer.insert(22, "at ");
/*       */     }
/*       */     
/* 12655 */     return stringBuffer.toString();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String setString(String paramString, int paramInt) {
/* 12668 */     int i = paramString.length();
/*       */     
/* 12670 */     if (paramInt == 0)
/*       */     {
/* 12672 */       return new String();
/*       */     }
/*       */     
/* 12675 */     if (i > paramInt)
/*       */     {
/* 12677 */       return paramString.substring(0, paramInt);
/*       */     }
/*       */ 
/*       */     
/* 12681 */     StringBuffer stringBuffer = new StringBuffer();
/* 12682 */     stringBuffer.append(paramString);
/* 12683 */     for (int j = i; j < paramInt; j++)
/*       */     {
/* 12685 */       stringBuffer.append(" ");
/*       */     }
/* 12687 */     return stringBuffer.toString();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String setString2(String paramString, int paramInt) {
/* 12701 */     int i = paramString.length();
/*       */     
/* 12703 */     if (paramInt == 0)
/*       */     {
/* 12705 */       return new String();
/*       */     }
/*       */     
/* 12708 */     if (i > paramInt)
/*       */     {
/* 12710 */       return paramString.substring(0, paramInt);
/*       */     }
/*       */ 
/*       */     
/* 12714 */     StringBuffer stringBuffer = new StringBuffer();
/* 12715 */     for (int j = i; j < paramInt; j++)
/*       */     {
/* 12717 */       stringBuffer.append(" ");
/*       */     }
/* 12719 */     stringBuffer.append(paramString);
/* 12720 */     return stringBuffer.toString();
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTags(String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 12726 */     if (paramString1.equals("")) {
/*       */       
/* 12728 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 12734 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 12743 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 12749 */       if (!paramString1.equals("WW"))
/*       */       {
/* 12751 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer);
/*       */       }
/* 12753 */       if (!paramString2.equals("WW"))
/*       */       {
/* 12755 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTags(String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2) {
/* 12767 */     if (paramString1.equals("")) {
/*       */       
/* 12769 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 12775 */         paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/* 12776 */         paramStringBuffer1.append(":xmp." + NEWLINE);
/* 12777 */         paramStringBuffer1.append(".kp off" + NEWLINE);
/* 12778 */         paramStringBuffer1.append(paramStringBuffer2.toString());
/* 12779 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer1);
/*       */ 
/*       */       
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*       */         
/* 12787 */         paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/* 12788 */         paramStringBuffer1.append(":xmp." + NEWLINE);
/* 12789 */         paramStringBuffer1.append(".kp off" + NEWLINE);
/* 12790 */         paramStringBuffer1.append(paramStringBuffer2.toString());
/*       */       
/*       */       }
/*       */     
/*       */     }
/* 12795 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 12801 */       if (!paramString1.equals("WW")) {
/*       */         
/* 12803 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer1);
/* 12804 */         paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/* 12805 */         paramStringBuffer1.append(":xmp." + NEWLINE);
/* 12806 */         paramStringBuffer1.append(".kp off" + NEWLINE);
/* 12807 */         paramStringBuffer1.append(paramStringBuffer2.toString());
/*       */       }
/*       */       else {
/*       */         
/* 12811 */         paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/* 12812 */         paramStringBuffer1.append(":xmp." + NEWLINE);
/* 12813 */         paramStringBuffer1.append(".kp off" + NEWLINE);
/* 12814 */         paramStringBuffer1.append(paramStringBuffer2.toString());
/*       */       } 
/* 12816 */       if (!paramString2.equals("WW"))
/*       */       {
/* 12818 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer1);
/*       */ 
/*       */       
/*       */       }
/*       */     
/*       */     }
/*       */     else {
/*       */ 
/*       */       
/* 12827 */       paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/* 12828 */       paramStringBuffer1.append(":xmp." + NEWLINE);
/* 12829 */       paramStringBuffer1.append(".kp off" + NEWLINE);
/* 12830 */       paramStringBuffer1.append(paramStringBuffer2.toString());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTags2(String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 12837 */     if (paramString1.equals("")) {
/*       */       
/* 12839 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 12847 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 12861 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 12867 */       if (!paramString1.equals("WW"))
/*       */       {
/* 12869 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 12876 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */         
/* 12880 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTags3(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 12904 */     if (paramString1.equals("")) {
/*       */       
/* 12906 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 12914 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 12928 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 12934 */       if (!paramString1.equals("WW"))
/*       */       {
/* 12936 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 12943 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */         
/* 12947 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 12961 */     else if (!paramString4.equals(paramString3) || !paramString6.equals(paramString5)) {
/*       */     
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTagsFeatConv(String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 12973 */     if (paramString1.equals("")) {
/*       */       
/* 12975 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 12981 */         paramStringBuffer.append(".sk 1" + NEWLINE);
/* 12982 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/* 12984 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */       
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*       */         
/* 12992 */         paramStringBuffer.append(".sk 1" + NEWLINE);
/* 12993 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/* 12999 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 13005 */       if (!paramString1.equals("WW")) {
/*       */         
/* 13007 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer);
/* 13008 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 13012 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       } 
/* 13014 */       if (!paramString2.equals("WW"))
/*       */       {
/* 13016 */         paramStringBuffer.append(".sk 1" + NEWLINE);
/* 13017 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/* 13019 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       else
/*       */       {
/* 13023 */         paramStringBuffer.append(".sk 1" + NEWLINE);
/* 13024 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/*       */     else {
/*       */ 
/*       */       
/* 13034 */       paramStringBuffer.append(":exmp." + NEWLINE);
/* 13035 */       paramStringBuffer.append(".sk 1" + NEWLINE);
/* 13036 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void bldBgnGeoTags(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 13044 */     if (paramBoolean == true) {
/*       */       
/* 13046 */       paramStringBuffer.append(":p.:hp2.");
/*       */     }
/*       */     else {
/*       */       
/* 13050 */       paramStringBuffer.append("<b>");
/*       */     } 
/*       */     
/* 13053 */     paramStringBuffer.append(paramString);
/*       */     
/* 13055 */     if (paramBoolean == true) {
/*       */       
/* 13057 */       paramStringBuffer.append("--->:ehp2." + NEWLINE);
/*       */       
/* 13059 */       if (2 == this.format || 3 == this.format)
/*       */       {
/* 13061 */         paramStringBuffer.append(".br" + NEWLINE);
/*       */       }
/*       */     }
/*       */     else {
/*       */       
/* 13066 */       paramStringBuffer.append("---&gt</b>" + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   private void bldEndGeoTags(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 13072 */     if (paramBoolean == true) {
/*       */       
/* 13074 */       paramStringBuffer.append(".br;:hp2.<---");
/*       */     }
/*       */     else {
/*       */       
/* 13078 */       paramStringBuffer.append("<b>&lt---");
/*       */     } 
/*       */     
/* 13081 */     paramStringBuffer.append(paramString);
/*       */     
/* 13083 */     if (paramBoolean == true) {
/*       */       
/* 13085 */       paramStringBuffer.append(":ehp2." + NEWLINE);
/*       */     
/*       */     }
/*       */     else {
/*       */       
/* 13090 */       paramStringBuffer.append("</b>" + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   private void printBeginGeoTags(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 13096 */     if (!paramString.equals("WW") && !paramString.equals("")) {
/*       */       
/* 13098 */       if (paramBoolean == true) {
/*       */         
/* 13100 */         paramStringBuffer.append(":p.:hp2.");
/*       */       
/*       */       }
/*       */       else {
/*       */         
/* 13105 */         paramStringBuffer.append("<b>");
/*       */       } 
/*       */       
/* 13108 */       paramStringBuffer.append(paramString);
/*       */       
/* 13110 */       if (paramBoolean == true) {
/*       */         
/* 13112 */         paramStringBuffer.append("--->:ehp2." + NEWLINE);
/*       */         
/* 13114 */         if (2 == this.format || 3 == this.format)
/*       */         {
/* 13116 */           paramStringBuffer.append(".br" + NEWLINE);
/*       */         }
/*       */       }
/*       */       else {
/*       */         
/* 13121 */         paramStringBuffer.append("---&gt</b>" + NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void printEndGeoTags(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 13129 */     if (!paramString.equals("WW") && !paramString.equals("")) {
/*       */       
/* 13131 */       if (paramBoolean == true) {
/*       */         
/* 13133 */         paramStringBuffer.append(".br;:hp2.<---");
/*       */       
/*       */       }
/*       */       else {
/*       */         
/* 13138 */         paramStringBuffer.append("<b>&lt---");
/*       */       } 
/*       */       
/* 13141 */       paramStringBuffer.append(paramString);
/*       */       
/* 13143 */       if (paramBoolean == true) {
/*       */         
/* 13145 */         paramStringBuffer.append(":ehp2." + NEWLINE);
/*       */       
/*       */       }
/*       */       else {
/*       */ 
/*       */         
/* 13151 */         paramStringBuffer.append("</b>" + NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   private void log(String paramString) {
/* 13158 */     if (this.debug) {
/*       */       
/* 13160 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
/* 13161 */       String str = simpleDateFormat.format(new Date());
/* 13162 */       this.debugBuff.append("</br>" + str + "  " + paramString + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public StringBuffer getDebugBuffer() {
/* 13173 */     return this.debugBuff;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void cleanUp() {
/* 13182 */     Iterator<String> iterator = this.machineTypeTS.iterator();
/*       */     
/* 13184 */     while (iterator.hasNext()) {
/*       */       
/* 13186 */       String str = iterator.next();
/* 13187 */       log("machinetype = " + str);
/*       */     } 
/*       */     
/* 13190 */     this.usGeoHT.clear();
/* 13191 */     this.usGeoHT = null;
/* 13192 */     this.apGeoHT.clear();
/* 13193 */     this.apGeoHT = null;
/* 13194 */     this.laGeoHT.clear();
/* 13195 */     this.laGeoHT = null;
/* 13196 */     this.canGeoHT.clear();
/* 13197 */     this.canGeoHT = null;
/* 13198 */     this.emeaGeoHT.clear();
/* 13199 */     this.emeaGeoHT = null;
/*       */     
/* 13201 */     this.geoHT.clear();
/* 13202 */     this.geoHT = null;
/*       */     
/* 13204 */     this.machineTypeTS.clear();
/* 13205 */     this.machineTypeTS = null;
/* 13206 */     this.featureHT.clear();
/* 13207 */     this.featureHT = null;
/*       */     
/* 13209 */     this.productNumber_NewModels_TM.clear();
/* 13210 */     this.productNumber_NewModels_TM = null;
/* 13211 */     this.productNumber_NewModels_HT.clear();
/* 13212 */     this.productNumber_NewModels_HT = null;
/* 13213 */     this.productNumber_NewFC_TM.clear();
/* 13214 */     this.productNumber_NewFC_TM = null;
/* 13215 */     this.productNumber_ExistingFC_TM.clear();
/* 13216 */     this.productNumber_ExistingFC_TM = null;
/* 13217 */     this.productNumber_NewModels_NewFC_TM.clear();
/* 13218 */     this.productNumber_NewModels_NewFC_TM = null;
/* 13219 */     this.productNumber_NewModels_ExistingFC_TM.clear();
/* 13220 */     this.productNumber_NewModels_ExistingFC_TM = null;
/* 13221 */     this.productNumber_ExistingModels_NewFC_TM.clear();
/* 13222 */     this.productNumber_ExistingModels_NewFC_TM = null;
/* 13223 */     this.productNumber_ExistingModels_ExistingFC_TM.clear();
/* 13224 */     this.productNumber_ExistingModels_ExistingFC_TM = null;
/*       */     
/* 13226 */     this.productNumber_MTM_Conversions_TM.clear();
/* 13227 */     this.productNumber_MTM_Conversions_TM = null;
/* 13228 */     this.productNumber_Model_Conversions_TM.clear();
/* 13229 */     this.productNumber_Model_Conversions_TM = null;
/* 13230 */     this.productNumber_Feature_Conversions_TM.clear();
/* 13231 */     this.productNumber_Feature_Conversions_TM = null;
/* 13232 */     this.featureVector.clear();
/* 13233 */     this.featureVector = null;
/*       */     
/* 13235 */     this.charges_NewModels_TM.clear();
/* 13236 */     this.charges_NewModels_TM = null;
/* 13237 */     this.charges_NewFC_TM.clear();
/* 13238 */     this.charges_NewFC_TM = null;
/* 13239 */     this.charges_ExistingFC_TM.clear();
/* 13240 */     this.charges_ExistingFC_TM = null;
/* 13241 */     this.charges_NewModels_NewFC_TM.clear();
/* 13242 */     this.charges_NewModels_NewFC_TM = null;
/* 13243 */     this.charges_NewModels_ExistingFC_TM.clear();
/* 13244 */     this.charges_NewModels_ExistingFC_TM = null;
/* 13245 */     this.charges_ExistingModels_NewFC_TM.clear();
/* 13246 */     this.charges_ExistingModels_NewFC_TM = null;
/* 13247 */     this.charges_ExistingModels_ExistingFC_TM.clear();
/* 13248 */     this.charges_ExistingModels_ExistingFC_TM = null;
/* 13249 */     this.charges_Feature_Conversions_TM.clear();
/* 13250 */     this.charges_Feature_Conversions_TM = null;
/*       */     
/* 13252 */     this.q779_NewModels_TM.clear();
/* 13253 */     this.q779_NewModels_TM = null;
/* 13254 */     this.q779_NewFeatures_TM.clear();
/* 13255 */     this.q779_NewFeatures_TM = null;
/*       */     
/* 13257 */     this.salesManual_TM.clear();
/* 13258 */     this.salesManual_TM = null;
/* 13259 */     this.salesManualSpecifyFeatures_TM.clear();
/* 13260 */     this.salesManualSpecifyFeatures_TM = null;
/* 13261 */     this.salesManualSpecialFeaturesInitialOrder_TM.clear();
/* 13262 */     this.salesManualSpecialFeaturesInitialOrder_TM = null;
/* 13263 */     this.salesManualSpecialFeaturesOther_TM.clear();
/* 13264 */     this.salesManualSpecialFeaturesOther_TM = null;
/*       */     
/* 13266 */     this.supportedDevices_TM.clear();
/* 13267 */     this.supportedDevices_TM = null;
/*       */     
/* 13269 */     this.featureMatrix_TM.clear();
/* 13270 */     this.featureMatrix_TM = null;
/*       */     
/* 13272 */     this.headerSB.delete(0, this.headerSB.length());
/* 13273 */     this.headerSB = null;
/*       */     
/* 13275 */     this.lseoVector.clear();
/* 13276 */     this.lseoVector = null;
/* 13277 */     this.mtmTS.clear();
/* 13278 */     this.mtmTS = null;
/* 13279 */     this.seoTable_TM.clear();
/* 13280 */     this.seoTable_TM = null;
/* 13281 */     this.seoDescription_TM.clear();
/* 13282 */     this.seoDescription_TM = null;
/*       */     
/* 13284 */     this.featureMatrixError.clear();
/* 13285 */     this.featureMatrixError = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getAttributeValue(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3) {
/* 13299 */     return getAttributeValue(paramEntityItem, paramString1, paramString2, paramString3, true, "eannounce");
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getAttributeValue(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 13315 */     return getAttributeValue(paramEntityItem, paramString1, paramString2, paramString3, paramBoolean, "eannounce");
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getAttributeValue(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4) {
/* 13332 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 13333 */     StringBuffer stringBuffer = new StringBuffer();
/* 13334 */     EANAttribute eANAttribute = null;
/* 13335 */     if (eANMetaAttribute == null) {
/* 13336 */       return "<font color=\"red\">Attribute &quot;" + paramString1 + "&quot; NOT found in &quot;" + paramEntityItem
/* 13337 */         .getEntityType() + "&quot; META data.</font>";
/*       */     }
/* 13339 */     eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 13340 */     if (eANAttribute == null) {
/* 13341 */       return paramString3;
/*       */     }
/* 13343 */     if (eANAttribute instanceof EANFlagAttribute) {
/*       */ 
/*       */       
/* 13346 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 13347 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*       */ 
/*       */         
/* 13350 */         if (arrayOfMetaFlag[b].isSelected()) {
/*       */           
/* 13352 */           if (stringBuffer.length() > 0) {
/* 13353 */             stringBuffer.append(paramString2);
/*       */           }
/* 13355 */           if (paramBoolean) {
/* 13356 */             stringBuffer.append(convertToHTML(arrayOfMetaFlag[b].toString()));
/*       */           } else {
/* 13358 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/* 13359 */           }  if (eANMetaAttribute.getAttributeType().equals("U")) {
/*       */             break;
/*       */           }
/*       */         } 
/*       */       } 
/* 13364 */     } else if (eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/*       */ 
/*       */       
/* 13367 */       if (eANMetaAttribute.getAttributeType().equals("T") || eANMetaAttribute.getAttributeType().equals("L") || eANMetaAttribute
/* 13368 */         .getAttributeType().equals("I")) {
/*       */ 
/*       */         
/* 13371 */         if (paramBoolean) {
/* 13372 */           stringBuffer.append(convertToHTML(eANAttribute.get().toString()));
/*       */         } else {
/* 13374 */           stringBuffer.append(eANAttribute.get().toString());
/*       */         } 
/*       */       } else {
/* 13377 */         stringBuffer.append(eANAttribute.get().toString());
/*       */       } 
/* 13379 */     } else if (eANAttribute instanceof EANBlobAttribute) {
/*       */ 
/*       */       
/* 13382 */       if (eANMetaAttribute.getAttributeType().equals("B")) {
/*       */         
/* 13384 */         EANBlobAttribute eANBlobAttribute = (EANBlobAttribute)eANAttribute;
/*       */ 
/*       */ 
/*       */         
/* 13388 */         if (eANBlobAttribute.getBlobExtension().toUpperCase().endsWith(".GIF") || eANBlobAttribute
/* 13389 */           .getBlobExtension().toUpperCase().endsWith(".JPG"))
/*       */         {
/* 13391 */           stringBuffer.append("<img src='/" + paramString4 + "/GetBlobAttribute?entityID=" + paramEntityItem.getEntityID() + "&entityType=" + paramEntityItem
/* 13392 */               .getEntityType() + "&attributeCode=" + paramString1 + "' alt='image' />");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*       */         }
/*       */         else
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 13410 */           stringBuffer.append("<form action=\"/" + paramString4 + "/PokXMLDownload\" name=\"" + paramEntityItem.getEntityType() + paramEntityItem
/* 13411 */               .getEntityID() + paramString1 + "\" method=\"post\"> " + NEWLINE);
/* 13412 */           stringBuffer.append("<input type=\"hidden\" name=\"entityType\" value=\"" + paramEntityItem.getEntityType() + "\" />" + NEWLINE);
/* 13413 */           stringBuffer.append("<input type=\"hidden\" name=\"entityID\" value=\"" + paramEntityItem.getEntityID() + "\" />" + NEWLINE);
/* 13414 */           stringBuffer.append("<input type=\"hidden\" name=\"downloadType\" value=\"blob\" />" + NEWLINE);
/* 13415 */           stringBuffer.append("<input type=\"hidden\" name=\"attributeCode\" value=\"" + paramString1 + "\" />" + NEWLINE);
/* 13416 */           stringBuffer.append("<input type=\"submit\" value=\"Download\" />" + NEWLINE);
/* 13417 */           stringBuffer.append("</form>" + NEWLINE);
/*       */         
/*       */         }
/*       */       
/*       */       }
/*       */       else {
/*       */         
/* 13424 */         stringBuffer.append("<font color=\"red\">Blob Attribute type &quot;" + eANMetaAttribute.getAttributeType() + "&quot; for " + paramString1 + " NOT yet supported</font>");
/*       */       } 
/*       */     } 
/*       */     
/* 13428 */     if (stringBuffer.length() == 0) {
/* 13429 */       return paramString3;
/*       */     }
/* 13431 */     return stringBuffer.toString();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String convertToHTML(String paramString) {
/* 13442 */     StringBuffer stringBuffer = new StringBuffer();
/* 13443 */     StringCharacterIterator stringCharacterIterator = null;
/* 13444 */     char c = ' ';
/* 13445 */     if (paramString == null) {
/* 13446 */       return null;
/*       */     }
/* 13448 */     stringCharacterIterator = new StringCharacterIterator(paramString);
/* 13449 */     c = stringCharacterIterator.first();
/* 13450 */     while (c != '￿') {
/*       */       
/* 13452 */       switch (c) {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*       */         case '"':
/*       */         case '<':
/*       */         case '>':
/* 13460 */           stringBuffer.append("&#" + c + ";");
/*       */           break;
/*       */         case '\n':
/* 13463 */           stringBuffer.append("<br />");
/*       */           break;
/*       */         default:
/* 13466 */           if (Character.isSpaceChar(c)) {
/*       */             
/* 13468 */             stringBuffer.append("&#32;");
/*       */             
/*       */             break;
/*       */           } 
/*       */           
/* 13473 */           stringBuffer.append(c);
/*       */           break;
/*       */       } 
/* 13476 */       c = stringCharacterIterator.next();
/*       */     } 
/*       */     
/* 13479 */     return stringBuffer.toString();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getAttributeFlagValue(EntityItem paramEntityItem, String paramString) {
/* 13491 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString);
/*       */     
/* 13493 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 13494 */     if (eANAttribute == null) {
/* 13495 */       return null;
/*       */     }
/* 13497 */     if (eANAttribute instanceof EANFlagAttribute) {
/*       */       
/* 13499 */       StringBuffer stringBuffer = new StringBuffer();
/*       */ 
/*       */       
/* 13502 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 13503 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*       */ 
/*       */         
/* 13506 */         if (arrayOfMetaFlag[b].isSelected()) {
/*       */           
/* 13508 */           if (stringBuffer.length() > 0)
/* 13509 */             stringBuffer.append("|"); 
/* 13510 */           stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/* 13511 */           if (eANMetaAttribute.getAttributeType().equals("U"))
/*       */             break; 
/*       */         } 
/*       */       } 
/* 13515 */       return stringBuffer.toString();
/*       */     } 
/*       */     
/* 13518 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getAllLinkedEntities(EntityGroup paramEntityGroup, String paramString1, String paramString2) {
/* 13535 */     Vector vector = new Vector(1);
/* 13536 */     if (paramEntityGroup == null) {
/* 13537 */       return vector;
/*       */     }
/* 13539 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*       */       
/* 13541 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 13542 */       getLinkedEntities(entityItem, paramString1, paramString2, vector);
/*       */     } 
/*       */     
/* 13545 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getAllLinkedEntities(Vector paramVector, String paramString1, String paramString2) {
/* 13562 */     Vector vector = new Vector(1);
/*       */     
/* 13564 */     Iterator<EntityItem> iterator = paramVector.iterator();
/* 13565 */     while (iterator.hasNext()) {
/*       */       
/* 13567 */       EntityItem entityItem = iterator.next();
/* 13568 */       getLinkedEntities(entityItem, paramString1, paramString2, vector);
/*       */     } 
/*       */     
/* 13571 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getAllLinkedEntities(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 13588 */     Vector vector = new Vector(1);
/*       */     
/* 13590 */     getLinkedEntities(paramEntityItem, paramString1, paramString2, vector);
/*       */     
/* 13592 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static void getLinkedEntities(EntityItem paramEntityItem, String paramString1, String paramString2, Vector<EANEntity> paramVector) {
/* 13608 */     if (paramEntityItem == null) {
/*       */       return;
/*       */     }
/*       */     byte b;
/* 13612 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*       */       
/* 13614 */       EANEntity eANEntity = paramEntityItem.getUpLink(b);
/* 13615 */       if (eANEntity.getEntityType().equals(paramString1))
/*       */       {
/*       */         
/* 13618 */         for (byte b1 = 0; b1 < eANEntity.getUpLinkCount(); b1++) {
/*       */           
/* 13620 */           EANEntity eANEntity1 = eANEntity.getUpLink(b1);
/* 13621 */           if (eANEntity1.getEntityType().equals(paramString2) && !paramVector.contains(eANEntity1)) {
/* 13622 */             paramVector.addElement(eANEntity1);
/*       */           }
/*       */         } 
/*       */       }
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 13635 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*       */       
/* 13637 */       EANEntity eANEntity = paramEntityItem.getDownLink(b);
/* 13638 */       if (eANEntity.getEntityType().equals(paramString1))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 13648 */         for (byte b1 = 0; b1 < eANEntity.getDownLinkCount(); b1++) {
/*       */           
/* 13650 */           EANEntity eANEntity1 = eANEntity.getDownLink(b1);
/* 13651 */           if (eANEntity1.getEntityType().equals(paramString2) && !paramVector.contains(eANEntity1)) {
/* 13652 */             paramVector.addElement(eANEntity1);
/*       */           }
/*       */         } 
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getEntitiesWithMatchedAttr(Vector paramVector, String paramString1, String paramString2) {
/* 13671 */     Vector<EntityItem> vector = new Vector(1);
/*       */     
/* 13673 */     Iterator<EntityItem> iterator = paramVector.iterator();
/* 13674 */     while (iterator.hasNext()) {
/*       */       
/* 13676 */       EntityItem entityItem = iterator.next();
/* 13677 */       if (entityMatchesAttr(entityItem, paramString1, paramString2)) {
/* 13678 */         vector.addElement(entityItem);
/*       */       }
/*       */     } 
/* 13681 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getEntitiesWithMatchedAttr(EntityGroup paramEntityGroup, String paramString1, String paramString2) {
/* 13697 */     Vector<EntityItem> vector = new Vector(1);
/* 13698 */     if (paramEntityGroup == null) {
/* 13699 */       return vector;
/*       */     }
/*       */     
/* 13702 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*       */       
/* 13704 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 13705 */       if (entityMatchesAttr(entityItem, paramString1, paramString2)) {
/* 13706 */         vector.addElement(entityItem);
/*       */       }
/*       */     } 
/* 13709 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getEntitiesWithMatchedAttr(Vector paramVector, Hashtable paramHashtable) {
/* 13724 */     Vector<EntityItem> vector = new Vector(1);
/*       */     
/* 13726 */     Iterator<EntityItem> iterator = paramVector.iterator();
/* 13727 */     while (iterator.hasNext()) {
/*       */       
/* 13729 */       EntityItem entityItem = iterator.next();
/* 13730 */       boolean bool = true;
/* 13731 */       for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/*       */         
/* 13733 */         String str = enumeration.nextElement();
/*       */         
/* 13735 */         bool = (bool && entityMatchesAttr(entityItem, str, paramHashtable.get(str).toString())) ? true : false;
/*       */       } 
/* 13737 */       if (bool) {
/* 13738 */         vector.addElement(entityItem);
/*       */       }
/*       */     } 
/* 13741 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getEntitiesWithMatchedAttr(EntityGroup paramEntityGroup, Hashtable paramHashtable) {
/* 13756 */     Vector<EntityItem> vector = new Vector(1);
/* 13757 */     if (paramEntityGroup == null) {
/* 13758 */       return vector;
/*       */     }
/*       */     
/* 13761 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*       */       
/* 13763 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 13764 */       boolean bool = true;
/* 13765 */       for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/*       */         
/* 13767 */         String str = enumeration.nextElement();
/*       */         
/* 13769 */         bool = (bool && entityMatchesAttr(entityItem, str, paramHashtable.get(str).toString())) ? true : false;
/*       */       } 
/* 13771 */       if (bool) {
/* 13772 */         vector.addElement(entityItem);
/*       */       }
/*       */     } 
/* 13775 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static boolean entityMatchesAttr(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 13791 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 13792 */     if (eANAttribute == null) {
/* 13793 */       return false;
/*       */     }
/* 13795 */     if (eANAttribute instanceof EANFlagAttribute) {
/*       */       
/* 13797 */       Vector<String> vector = new Vector(1);
/* 13798 */       String[] arrayOfString = null;
/* 13799 */       byte b1 = 0;
/*       */       
/* 13801 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get(); byte b2;
/* 13802 */       for (b2 = 0; b2 < arrayOfMetaFlag.length; b2++) {
/*       */ 
/*       */         
/* 13805 */         if (arrayOfMetaFlag[b2].isSelected())
/*       */         {
/* 13807 */           vector.addElement(arrayOfMetaFlag[b2].getFlagCode());
/*       */         }
/*       */       } 
/*       */ 
/*       */       
/* 13812 */       arrayOfString = convertToArray(paramString2);
/* 13813 */       for (b2 = 0; b2 < arrayOfString.length; b2++) {
/*       */         
/* 13815 */         if (vector.contains(arrayOfString[b2]))
/* 13816 */           b1++; 
/*       */       } 
/* 13818 */       if (b1 == arrayOfString.length) {
/* 13819 */         return true;
/*       */       }
/*       */     }
/* 13822 */     else if (eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/*       */ 
/*       */       
/* 13825 */       return eANAttribute.get().toString().equals(paramString2);
/*       */     } 
/*       */     
/* 13828 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isSelected(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 13840 */     EANAttribute eANAttribute = null;
/* 13841 */     if (paramEntityItem == null)
/* 13842 */       return false; 
/* 13843 */     if (paramString1 == null)
/* 13844 */       return false; 
/* 13845 */     if (paramString2 == null) {
/* 13846 */       return false;
/*       */     }
/*       */     
/* 13849 */     eANAttribute = paramEntityItem.getAttribute(paramString1);
/*       */     
/* 13851 */     if (eANAttribute instanceof EANFlagAttribute) {
/* 13852 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 13853 */       EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 13854 */       MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(paramString2);
/* 13855 */       if (metaFlag == null)
/* 13856 */         return false; 
/* 13857 */       return eANFlagAttribute.isSelected(metaFlag);
/*       */     } 
/* 13859 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String[] convertToArray(String paramString) {
/* 13871 */     Vector<String> vector = new Vector();
/* 13872 */     String[] arrayOfString = null;
/*       */     
/* 13874 */     if (paramString != null) {
/*       */       
/* 13876 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString, "|");
/* 13877 */       while (stringTokenizer.hasMoreTokens())
/*       */       {
/* 13879 */         vector.addElement(stringTokenizer.nextToken());
/*       */       }
/*       */     } 
/* 13882 */     arrayOfString = new String[vector.size()];
/* 13883 */     vector.copyInto((Object[])arrayOfString);
/* 13884 */     return arrayOfString;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String outputList(EntityList paramEntityList) {
/* 13896 */     StringBuffer stringBuffer = new StringBuffer();
/* 13897 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 13898 */     if (entityGroup != null) {
/*       */       
/* 13900 */       stringBuffer.append(entityGroup.getEntityType() + " : " + entityGroup.getEntityItemCount() + " Parent entity items. ");
/* 13901 */       if (entityGroup.getEntityItemCount() > 0) {
/*       */         
/* 13903 */         stringBuffer.append("IDs(");
/* 13904 */         for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++)
/*       */         {
/* 13906 */           stringBuffer.append(" " + entityGroup.getEntityItem(b1).getEntityID());
/*       */         }
/* 13908 */         stringBuffer.append(")");
/*       */       } 
/* 13910 */       stringBuffer.append(NEWLINE);
/*       */     } 
/*       */     
/* 13913 */     for (byte b = 0; b < paramEntityList.getEntityGroupCount(); b++) {
/*       */       
/* 13915 */       EntityGroup entityGroup1 = paramEntityList.getEntityGroup(b);
/* 13916 */       stringBuffer.append(entityGroup1.getEntityType() + " : " + entityGroup1.getEntityItemCount() + " entity items. ");
/* 13917 */       if (entityGroup1.getEntityItemCount() > 0) {
/*       */         
/* 13919 */         stringBuffer.append("IDs(");
/* 13920 */         for (byte b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++)
/*       */         {
/* 13922 */           stringBuffer.append(" " + entityGroup1.getEntityItem(b1).getEntityID());
/*       */         }
/* 13924 */         stringBuffer.append(")");
/*       */       } 
/* 13926 */       stringBuffer.append(NEWLINE);
/*       */     } 
/* 13928 */     return stringBuffer.toString();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean isNewFeature(EntityItem paramEntityItem) {
/* 13944 */     boolean bool = true;
/*       */ 
/*       */ 
/*       */     
/* 13948 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */ 
/*       */     
/* 13951 */     hashtable.clear();
/* 13952 */     hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 13957 */     hashtable.put("COFGRP", "150");
/*       */     
/* 13959 */     log(paramEntityItem.toString());
/*       */     
/* 13961 */     String str1 = "EXRPT3FM";
/* 13962 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 13963 */     arrayOfEntityItem[0] = paramEntityItem;
/* 13964 */     EntityList entityList = null;
/*       */     
/* 13966 */     String str2 = paramEntityItem.getKey();
/* 13967 */     String str3 = getAttributeValue(paramEntityItem, "FEATURECODE", "", "", false);
/* 13968 */     str3 = str3.trim();
/* 13969 */     str3 = setString(str3, 4);
/*       */     
/* 13971 */     if (this.featureHT.containsKey(str2)) {
/*       */       
/* 13973 */       String str = (String)this.featureHT.get(str2);
/* 13974 */       log("---> found it " + paramEntityItem.toString());
/* 13975 */       if (str.equals("New"))
/*       */       {
/* 13977 */         return true;
/*       */       }
/*       */ 
/*       */       
/* 13981 */       return false;
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/* 13986 */     String str4 = "";
/* 13987 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/* 13989 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/* 13990 */     while (iterator.hasNext()) {
/*       */       
/* 13992 */       EntityItem entityItem = iterator.next();
/* 13993 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/* 13994 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/* 13996 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/* 13998 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/* 13999 */         if (null != eANEntity)
/*       */         {
/* 14001 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/* 14003 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/* 14004 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/* 14005 */             if (null != eANEntity1) {
/*       */               
/* 14007 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/* 14008 */               String str = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/* 14009 */               str = str.trim();
/* 14010 */               str = setString(str, 4);
/*       */               
/* 14012 */               if (str.equals(str3)) {
/*       */                 
/* 14014 */                 str4 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/* 14015 */                 if (null == str4)
/*       */                 {
/* 14017 */                   str4 = " ";
/*       */                 }
/* 14019 */                 str4 = str4.trim();
/* 14020 */                 str4 = setString(str4, 4);
/* 14021 */                 treeSet.add(str4);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         }
/*       */       } 
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*       */     try {
/* 14032 */       Vector<EntityItem> vector = new Vector();
/* 14033 */       Profile profile = this.list.getProfile();
/* 14034 */       entityList = this.dbCurrent.getEntityList(profile, new ExtractActionItem(null, this.dbCurrent, profile, str1), arrayOfEntityItem);
/*       */ 
/*       */ 
/*       */       
/* 14038 */       if (entityList.getEntityGroupCount() == 0)
/*       */       {
/* 14040 */         log("Extract was not found for " + str1);
/*       */       }
/*       */       
/* 14043 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/*       */       
/* 14045 */       paramEntityItem = entityGroup.getEntityItem(0);
/*       */       byte b;
/* 14047 */       for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*       */         
/* 14049 */         EANEntity eANEntity = paramEntityItem.getDownLink(b);
/*       */         
/* 14051 */         if (eANEntity.getEntityType().equals("PRODSTRUCT")) {
/*       */           
/* 14053 */           EntityItem entityItem = (EntityItem)eANEntity;
/* 14054 */           EANEntity eANEntity1 = getDownLinkEntityItem(entityItem, "MODEL");
/* 14055 */           log(eANEntity.toString());
/* 14056 */           if (null != eANEntity1)
/*       */           {
/* 14058 */             if (isEntityWithMatchedAttr((EntityItem)eANEntity1, hashtable)) {
/*       */               
/* 14060 */               EntityItem entityItem1 = (EntityItem)eANEntity1;
/* 14061 */               String str = getAttributeFlagValue(entityItem1, "MACHTYPEATR");
/* 14062 */               if (null == str)
/*       */               {
/* 14064 */                 str = " ";
/*       */               }
/* 14066 */               str = str.trim();
/* 14067 */               str = setString(str, 4);
/*       */               
/* 14069 */               if (treeSet.contains(str))
/*       */               {
/* 14071 */                 vector.add(entityItem);
/*       */               }
/*       */             } 
/*       */           }
/*       */         } 
/*       */       } 
/*       */       
/* 14078 */       if (vector.size() == 0) {
/*       */         
/* 14080 */         this.featureHT.put(str2, "New");
/* 14081 */         return true;
/*       */       } 
/*       */       
/* 14084 */       for (b = 0; b < vector.size(); b++) {
/*       */         
/* 14086 */         EntityItem entityItem = vector.get(b);
/* 14087 */         String str = getAttributeValue(entityItem, "ANNDATE", "", "", false);
/* 14088 */         if (str.equals("")) {
/*       */           
/* 14090 */           EANEntity eANEntity = getDownLinkEntityItem(entityItem, "MODEL");
/* 14091 */           if (null != eANEntity) {
/*       */             
/* 14093 */             EntityItem entityItem1 = (EntityItem)eANEntity;
/* 14094 */             str = getAttributeValue(entityItem1, "ANNDATE", "", "", false);
/*       */           } 
/*       */         } 
/*       */         
/* 14098 */         if (this.annDate.compareTo(str) > 0) {
/*       */           
/* 14100 */           this.featureHT.put(str2, "Old");
/* 14101 */           return false;
/*       */         } 
/*       */       } 
/*       */       
/* 14105 */       this.featureHT.put(str2, "New");
/* 14106 */       bool = true;
/*       */     }
/* 14108 */     catch (Exception exception) {
/*       */       
/* 14110 */       exception.printStackTrace();
/* 14111 */       log(exception.toString());
/*       */     } 
/*       */     
/* 14114 */     hashtable.clear();
/* 14115 */     hashtable = null;
/* 14116 */     treeSet.clear();
/* 14117 */     treeSet = null;
/*       */     
/* 14119 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean textContainsGMLTag(String paramString1, String paramString2) {
/* 14131 */     boolean bool = false;
/* 14132 */     String[] arrayOfString = getStringTokens(paramString1, NEWLINE);
/* 14133 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*       */       
/* 14135 */       String str = arrayOfString[b].toLowerCase();
/* 14136 */       if (-1 != str.indexOf(paramString2)) {
/*       */         
/* 14138 */         bool = true;
/*       */         
/*       */         break;
/*       */       } 
/*       */     } 
/* 14143 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String formatDate(String paramString) {
/* 14154 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*       */ 
/*       */     
/*       */     try {
/* 14158 */       Date date = simpleDateFormat.parse(paramString);
/*       */       
/* 14160 */       simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
/* 14161 */       return simpleDateFormat.format(date).toString();
/*       */     }
/* 14163 */     catch (Exception exception) {
/*       */       
/* 14165 */       return "Exception in formatDate(): " + exception;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void q779_NewModels() {
/* 14176 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/* 14178 */     hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 14183 */     hashtable.put("COFGRP", "150");
/*       */ 
/*       */     
/* 14186 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/* 14187 */     while (iterator.hasNext()) {
/*       */       
/* 14189 */       EntityItem entityItem = iterator.next();
/* 14190 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/* 14191 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/* 14192 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/* 14194 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/* 14196 */         populate_Q779_NewModels_TM(entityItem, entityItem1);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void populate_Q779_NewModels_TM(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/* 14220 */     String str6 = paramEntityItem2.getKey();
/* 14221 */     updateGeoHT(paramEntityItem1, str6);
/*       */     
/* 14223 */     String str1 = getAttributeFlagValue(paramEntityItem2, "MACHTYPEATR");
/* 14224 */     if (null == str1)
/*       */     {
/* 14226 */       str1 = " ";
/*       */     }
/* 14228 */     str1 = str1.trim();
/* 14229 */     str1 = setString(str1, 4);
/* 14230 */     String str2 = str1;
/* 14231 */     String str3 = getAttributeValue(paramEntityItem2, "MODELATR", "", "000", false);
/* 14232 */     str3 = str3.trim();
/* 14233 */     str3 = setString(str3, 3);
/* 14234 */     str1 = str1 + "<:>" + str3;
/* 14235 */     str1 = str1 + "<:>IOR";
/*       */     
/* 14237 */     String str5 = getAttributeValue(paramEntityItem2, "MKTGNAME", "", "", false);
/* 14238 */     str5 = str5.trim();
/* 14239 */     if (str5.equals(""))
/*       */     {
/* 14241 */       this.featureMatrixError.add("21<:>XCHARGES<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Marketing Name");
/*       */     }
/* 14243 */     str1 = str1 + "<:>" + str5;
/*       */     
/* 14245 */     String str4 = getGeo(str6);
/*       */     
/* 14247 */     addToTreeMap(str1, str4, this.q779_NewModels_TM);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void q779_NewFeatures() {
/* 14264 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 14265 */     String str = "";
/* 14266 */     boolean bool = true;
/*       */     
/* 14268 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/* 14269 */     while (iterator.hasNext()) {
/*       */       
/* 14271 */       EntityItem entityItem = iterator.next();
/* 14272 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/* 14273 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/* 14276 */         EntityItem entityItem1 = vector.get(b);
/* 14277 */         String str1 = entityItem1.getKey();
/* 14278 */         updateGeoHT(entityItem, str1);
/*       */         
/* 14280 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/* 14281 */         if (null != eANEntity) {
/*       */ 
/*       */           
/* 14284 */           hashtable.clear();
/* 14285 */           hashtable.put("COFCAT", "100");
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 14290 */           hashtable.put("COFGRP", "150");
/* 14291 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/* 14293 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/* 14294 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/* 14295 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/* 14305 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/* 14306 */               String str2 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/* 14307 */               str2 = str2.trim();
/* 14308 */               str2 = setString(str2, 4);
/* 14309 */               String str3 = str2;
/*       */               
/* 14311 */               str = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/* 14312 */               if (null == str)
/*       */               {
/* 14314 */                 str = " ";
/*       */               }
/* 14316 */               str = str.trim();
/* 14317 */               str = setString(str, 4);
/* 14318 */               str2 = str2 + "<:>" + str;
/* 14319 */               String str4 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/* 14320 */               str4 = str4.trim();
/* 14321 */               str4 = setString(str4, 3);
/* 14322 */               str2 = str2 + "<:>" + str4;
/*       */               
/* 14324 */               String str5 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/* 14325 */               if (this.annDate.equals(str5)) {
/*       */                 
/* 14327 */                 bool = true;
/*       */ 
/*       */               
/*       */               }
/* 14331 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/* 14333 */                 bool = true;
/*       */               }
/*       */               else {
/*       */                 
/* 14337 */                 bool = false;
/*       */               } 
/*       */ 
/*       */               
/* 14341 */               if (str3.equals("    "))
/*       */               {
/* 14343 */                 this.featureMatrixError.add("21<:>XCHARGES<:>" + str + "<:>" + str4 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/* 14346 */               str2 = str2 + "<:>CCE/CCR";
/*       */               
/* 14348 */               String str6 = getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/* 14349 */               str6 = str6.trim();
/* 14350 */               if (str6.equals("")) {
/*       */                 
/* 14352 */                 str6 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false);
/* 14353 */                 str6 = str6.trim();
/* 14354 */                 if (str6.equals("")) {
/*       */                   
/* 14356 */                   this.featureMatrixError.add("21<:>XCHARGES<:>" + str + "<:>" + str4 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Marketing Name");
/* 14357 */                   this.featureMatrixError.add("21<:>XCHARGES<:>" + str + "<:>" + str4 + "<:>FEATURE " + str3 + "<:>Marketing Name");
/*       */                 } 
/*       */               } 
/* 14360 */               str2 = str2 + "<:>" + str6;
/*       */               
/* 14362 */               String str7 = getGeo(str1);
/*       */               
/* 14364 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals(""))
/*       */               {
/* 14366 */                 this.featureMatrixError.add("21<:>XCHARGES<:>" + str + "<:>" + str4 + "<:>FEATURE " + str3 + "<:>Priced Feature Indicator");
/*       */               }
/*       */               
/* 14369 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals("")) {
/*       */ 
/*       */                 
/* 14372 */                 str2 = str2 + "<:>       ";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*       */               }
/*       */               else {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/* 14394 */                 hashtable.clear();
/* 14395 */                 hashtable.put("PRICEDFEATURE", "100");
/*       */                 
/* 14397 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/* 14399 */                   str2 = str2 + "<:>XXXX.XX";
/*       */                 }
/* 14401 */                 hashtable.clear();
/* 14402 */                 hashtable.put("PRICEDFEATURE", "120");
/*       */                 
/* 14404 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/* 14406 */                   str2 = str2 + "<:>     NC";
/*       */                 }
/*       */               } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/* 14424 */               if (bool)
/*       */               {
/* 14426 */                 addToTreeMap(str2, str7, this.q779_NewFeatures_TM);
/*       */               }
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/* 14434 */     hashtable.clear();
/* 14435 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveXCharges(boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 14446 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/* 14447 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/* 14448 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */     
/* 14450 */     retrieveQ799NewModels(paramBoolean, paramStringBuffer);
/* 14451 */     retrieveQ799NewFeatures(paramBoolean, paramStringBuffer);
/*       */     
/* 14453 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveQ799NewModels(boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 14464 */     if (this.q779_NewModels_TM.size() > 0) {
/*       */       
/* 14466 */       String str1 = "";
/* 14467 */       String str2 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 14472 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/* 14473 */       paramStringBuffer.append(".fo off" + NEWLINE);
/* 14474 */       paramStringBuffer.append(".in 2" + NEWLINE);
/* 14475 */       paramStringBuffer.append("Machine/                                                 IBM" + NEWLINE);
/* 14476 */       paramStringBuffer.append("Model     SUPP.                                          List   " + NEWLINE);
/* 14477 */       paramStringBuffer.append("NUMBER    CAT.   DESCRIPTION                             Price* " + NEWLINE);
/* 14478 */       paramStringBuffer.append("--------  -----  --------------------------------------  ------" + NEWLINE);
/* 14479 */       paramStringBuffer.append(".fo on" + NEWLINE);
/* 14480 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/* 14481 */       paramStringBuffer.append(".pa" + NEWLINE);
/* 14482 */       paramStringBuffer.append(":xmp." + NEWLINE);
/* 14483 */       paramStringBuffer.append(".kp off" + NEWLINE);
/*       */       
/* 14485 */       Set set = this.q779_NewModels_TM.keySet();
/* 14486 */       Iterator<String> iterator = set.iterator();
/* 14487 */       while (iterator.hasNext()) {
/*       */ 
/*       */         
/* 14490 */         String str = iterator.next();
/* 14491 */         str2 = (String)this.q779_NewModels_TM.get(str);
/* 14492 */         String[] arrayOfString = extractStringLines(parseString(str, 4, "<:>"), 38);
/* 14493 */         byte b = 0;
/*       */         
/* 14495 */         setGeoTags2(str1, str2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */         
/*       */         do {
/* 14499 */           if (0 == b) {
/*       */             
/* 14501 */             paramStringBuffer.append(parseString(str, 1, "<:>"));
/* 14502 */             paramStringBuffer.append("-");
/* 14503 */             paramStringBuffer.append(parseString(str, 2, "<:>"));
/* 14504 */             paramStringBuffer.append("  ");
/* 14505 */             paramStringBuffer.append(parseString(str, 3, "<:>"));
/* 14506 */             paramStringBuffer.append("    ");
/* 14507 */             paramStringBuffer.append(setString(arrayOfString[b], 38));
/* 14508 */             paramStringBuffer.append("  ");
/* 14509 */             paramStringBuffer.append("xxxx.xx");
/* 14510 */             paramStringBuffer.append(NEWLINE);
/* 14511 */             b++;
/*       */           }
/*       */           else {
/*       */             
/* 14515 */             paramStringBuffer.append(setString(" ", 17));
/* 14516 */             paramStringBuffer.append(setString(arrayOfString[b], 38));
/* 14517 */             paramStringBuffer.append(NEWLINE);
/* 14518 */             b++;
/*       */           } 
/* 14520 */         } while (b < arrayOfString.length);
/*       */         
/* 14522 */         str1 = str2;
/*       */       } 
/* 14524 */       if (!str2.equals("WW"))
/*       */       {
/* 14526 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/* 14528 */       if (paramBoolean == true) {
/*       */         
/* 14530 */         paramStringBuffer.append(":exmp." + NEWLINE);
/* 14531 */         paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveQ799NewFeatures(boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 14544 */     if (this.q779_NewFeatures_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 14552 */       TreeSet<String> treeSet = new TreeSet();
/* 14553 */       Set<String> set = this.q779_NewFeatures_TM.keySet();
/* 14554 */       Iterator<String> iterator1 = set.iterator();
/* 14555 */       while (iterator1.hasNext()) {
/*       */         
/* 14557 */         String str = iterator1.next();
/* 14558 */         treeSet.add(parseString(str, 2, "<:>"));
/*       */       } 
/*       */       
/* 14561 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/* 14562 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/* 14564 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/* 14567 */       byte b1 = 0;
/* 14568 */       Iterator<String> iterator2 = treeSet.iterator();
/* 14569 */       while (iterator2.hasNext()) {
/*       */         
/* 14571 */         String str = iterator2.next();
/*       */         
/* 14573 */         iterator1 = set.iterator();
/* 14574 */         while (iterator1.hasNext()) {
/*       */           
/* 14576 */           String str1 = iterator1.next();
/* 14577 */           String str2 = parseString(str1, 2, "<:>");
/*       */           
/* 14579 */           if (str.equals(str2))
/*       */           {
/* 14581 */             arrayOfTreeMap[b1].put(str1, this.q779_NewFeatures_TM.get(str1));
/*       */           }
/*       */         } 
/* 14584 */         b1++;
/*       */       } 
/*       */       
/* 14587 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/* 14589 */         retrieveQ799NewFeatures(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
/*       */       }
/*       */ 
/*       */       
/* 14593 */       for (b2 = 0; b2 < treeSet.size(); b2++) {
/*       */         
/* 14595 */         arrayOfTreeMap[b2].clear();
/* 14596 */         arrayOfTreeMap[b2] = null;
/*       */       } 
/* 14598 */       treeSet.clear();
/* 14599 */       treeSet = null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveQ799NewFeatures(boolean paramBoolean, StringBuffer paramStringBuffer, TreeMap paramTreeMap) {
/* 14612 */     if (paramTreeMap.size() > 0) {
/*       */ 
/*       */       
/* 14615 */       String str2 = "";
/* 14616 */       String str3 = "";
/*       */       
/* 14618 */       String str4 = "";
/* 14619 */       String str5 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 14626 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */       
/* 14628 */       String str1 = parseString((String)paramTreeMap.firstKey(), 2, "<:>");
/* 14629 */       TreeSet<String> treeSet = new TreeSet();
/* 14630 */       Set<String> set = paramTreeMap.keySet();
/* 14631 */       Iterator<String> iterator1 = set.iterator();
/* 14632 */       while (iterator1.hasNext()) {
/*       */         
/* 14634 */         String str = iterator1.next();
/* 14635 */         treeSet.add(parseString(str, 3, "<:>"));
/*       */       } 
/* 14637 */       Iterator<String> iterator2 = treeSet.iterator();
/* 14638 */       while (iterator2.hasNext())
/*       */       {
/* 14640 */         str2 = str2 + (String)iterator2.next() + ", ";
/*       */       }
/* 14642 */       if (str2.length() > 1)
/*       */       {
/* 14644 */         str2 = str2.substring(0, str2.length() - 2);
/*       */       }
/*       */       
/* 14647 */       str3 = "Machine Type: " + str1 + "  Models: " + str2;
/*       */       
/* 14649 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/* 14650 */       paramStringBuffer.append(".fo off" + NEWLINE);
/* 14651 */       paramStringBuffer.append(".in 2" + NEWLINE);
/* 14652 */       paramStringBuffer.append("                                                         IBM" + NEWLINE);
/* 14653 */       paramStringBuffer.append("         SUPP.                                           List    PKG." + NEWLINE);
/* 14654 */       paramStringBuffer.append("Feature  CAT.     Description                            Price   QTY." + NEWLINE);
/* 14655 */       paramStringBuffer.append("-------  -------  -------------------------------------  -----   ----" + NEWLINE);
/* 14656 */       paramStringBuffer.append(".fo on" + NEWLINE);
/* 14657 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/* 14658 */       paramStringBuffer.append(".pa" + NEWLINE);
/* 14659 */       paramStringBuffer.append(":xmp." + NEWLINE);
/* 14660 */       paramStringBuffer.append(".kp off" + NEWLINE);
/*       */       
/* 14662 */       String[] arrayOfString = extractStringLines(str3, 69);
/* 14663 */       for (byte b = 0; b < arrayOfString.length; b++)
/*       */       {
/* 14665 */         paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */       }
/*       */       
/* 14668 */       set = paramTreeMap.keySet();
/* 14669 */       iterator1 = set.iterator();
/*       */       
/* 14671 */       while (iterator1.hasNext()) {
/*       */         
/* 14673 */         String str6 = iterator1.next();
/* 14674 */         String str7 = parseString(str6, 1, "<:>") + "<:>" + parseString(str6, 4, "<:>") + "<:>" + parseString(str6, 5, "<:>") + "<:>" + parseString(str6, 6, "<:>");
/*       */         
/* 14676 */         treeMap.put(str7, paramTreeMap.get(str6));
/*       */       } 
/*       */       
/* 14679 */       set = treeMap.keySet();
/* 14680 */       iterator1 = set.iterator();
/*       */       
/* 14682 */       while (iterator1.hasNext()) {
/*       */ 
/*       */         
/* 14685 */         String str = iterator1.next();
/* 14686 */         str5 = (String)treeMap.get(str);
/* 14687 */         String[] arrayOfString1 = extractStringLines(parseString(str, 3, "<:>"), 37);
/* 14688 */         byte b1 = 0;
/*       */         
/* 14690 */         setGeoTags2(str4, str5, paramBoolean, paramStringBuffer);
/*       */ 
/*       */         
/*       */         do {
/* 14694 */           if (0 == b1) {
/*       */             
/* 14696 */             paramStringBuffer.append(parseString(str, 1, "<:>"));
/* 14697 */             paramStringBuffer.append("     ");
/* 14698 */             paramStringBuffer.append(parseString(str, 2, "<:>"));
/* 14699 */             paramStringBuffer.append("  ");
/* 14700 */             paramStringBuffer.append(setString(arrayOfString1[b1], 37));
/* 14701 */             paramStringBuffer.append("  ");
/* 14702 */             paramStringBuffer.append(parseString(str, 4, "<:>"));
/* 14703 */             paramStringBuffer.append("  ");
/* 14704 */             paramStringBuffer.append("1");
/* 14705 */             paramStringBuffer.append(NEWLINE);
/* 14706 */             b1++;
/*       */           }
/*       */           else {
/*       */             
/* 14710 */             paramStringBuffer.append(setString(" ", 18));
/* 14711 */             paramStringBuffer.append(setString(arrayOfString1[b1], 37));
/* 14712 */             paramStringBuffer.append(NEWLINE);
/* 14713 */             b1++;
/*       */           } 
/* 14715 */         } while (b1 < arrayOfString1.length);
/*       */         
/* 14717 */         str4 = str5;
/*       */       } 
/* 14719 */       if (!str5.equals("WW"))
/*       */       {
/* 14721 */         bldEndGeoTags(str5, paramBoolean, paramStringBuffer);
/*       */       }
/* 14723 */       if (paramBoolean == true) {
/*       */         
/* 14725 */         paramStringBuffer.append(":exmp." + NEWLINE);
/* 14726 */         paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */       } 
/*       */       
/* 14729 */       treeMap.clear();
/* 14730 */       treeMap = null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat3_MTM(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 14743 */     TreeMap<Object, Object> treeMap1 = new TreeMap<>();
/* 14744 */     TreeMap<Object, Object> treeMap2 = new TreeMap<>();
/* 14745 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/* 14747 */     if (this.charges_NewModels_NewFC_TM.size() > 0) {
/*       */       
/* 14749 */       Set set = this.charges_NewModels_NewFC_TM.keySet();
/* 14750 */       Iterator<String> iterator = set.iterator();
/* 14751 */       while (iterator.hasNext()) {
/*       */         
/* 14753 */         String str = iterator.next();
/* 14754 */         if (paramString.equals(parseString(str, 1, "<:>"))) {
/*       */           
/* 14756 */           String str1 = parseString(str, 3, "<:>");
/* 14757 */           String str2 = parseString(str, 10, "<:>");
/* 14758 */           treeSet.add(str1 + "<:>" + str2);
/* 14759 */           treeMap1.put(str, this.charges_NewModels_NewFC_TM.get(str));
/*       */         } 
/*       */       } 
/*       */     } 
/* 14763 */     retrieveChargesFeaturesFormat3_MTM(paramString, treeSet, 1, treeMap1, paramBoolean, paramStringBuffer);
/* 14764 */     treeSet.clear();
/*       */     
/* 14766 */     if (this.charges_NewModels_ExistingFC_TM.size() > 0) {
/*       */       
/* 14768 */       Set set = this.charges_NewModels_ExistingFC_TM.keySet();
/* 14769 */       Iterator<String> iterator = set.iterator();
/* 14770 */       while (iterator.hasNext()) {
/*       */         
/* 14772 */         String str = iterator.next();
/*       */         
/* 14774 */         if (paramString.equals(parseString(str, 1, "<:>"))) {
/*       */           
/* 14776 */           String str1 = parseString(str, 3, "<:>");
/* 14777 */           treeSet.add(str1);
/* 14778 */           treeMap2.put(str, this.charges_NewModels_ExistingFC_TM.get(str));
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/* 14783 */     retrieveChargesFeaturesFormat3_MTM(paramString, treeSet, 2, treeMap2, paramBoolean, paramStringBuffer);
/*       */     
/* 14785 */     treeSet.clear();
/* 14786 */     treeSet = null;
/* 14787 */     treeMap1.clear();
/* 14788 */     treeMap1 = null;
/* 14789 */     treeMap2.clear();
/* 14790 */     treeMap2 = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat3_MTM(String paramString, TreeSet paramTreeSet, int paramInt, TreeMap paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 14805 */     if (paramTreeMap.size() > 0) {
/*       */       String[] arrayOfString;
/*       */       byte b;
/* 14808 */       String str = "";
/*       */       
/* 14810 */       switch (paramInt) {
/*       */         
/*       */         case 1:
/* 14813 */           if (this.brand.equals("pSeries")) {
/*       */             
/* 14815 */             str = "of the IBM RS/6000 or pSeries " + paramString + " machine type:";
/*       */           }
/* 14817 */           else if (this.brand.equals("xSeries")) {
/*       */             
/* 14819 */             str = "of the IBM xSeries " + paramString + " machine type:";
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*       */           }
/* 14825 */           else if (this.brand.equals("totalStorage")) {
/*       */             
/* 14827 */             str = "of the Total Storage " + paramString + " machine type:";
/*       */           } 
/*       */           
/* 14830 */           str = ":p.The following are newly announced features on the specified models " + str;
/* 14831 */           arrayOfString = extractStringLines(str, 70);
/* 14832 */           for (b = 0; b < arrayOfString.length; b++)
/*       */           {
/* 14834 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */         
/*       */         case 2:
/* 14839 */           str = ":p.The following are features already announced for the " + paramString + " machine type:";
/* 14840 */           arrayOfString = extractStringLines(str, 70);
/* 14841 */           for (b = 0; b < arrayOfString.length; b++)
/*       */           {
/* 14843 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 14851 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/*       */       
/* 14853 */       paramStringBuffer.append(".fo off" + NEWLINE);
/* 14854 */       paramStringBuffer.append(".in 2" + NEWLINE);
/*       */       
/* 14856 */       paramStringBuffer.append("                                                     Initial/" + NEWLINE);
/* 14857 */       paramStringBuffer.append("                                                     MES/" + NEWLINE);
/* 14858 */       paramStringBuffer.append("Description                  Model  Feature Purchase Both/        RP" + NEWLINE);
/* 14859 */       paramStringBuffer.append("                             Number Numbers Price    Support  CSU MES" + NEWLINE);
/* 14860 */       paramStringBuffer.append("---------------------------- ------ ------- -------- -------- --- ---" + NEWLINE);
/*       */       
/* 14862 */       paramStringBuffer.append(".fo on" + NEWLINE);
/* 14863 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */       
/* 14865 */       if (paramTreeSet.size() > 0) {
/*       */         
/* 14867 */         Iterator<String> iterator = paramTreeSet.iterator();
/*       */         
/* 14869 */         paramStringBuffer.append(".pa" + NEWLINE);
/* 14870 */         paramStringBuffer.append(":xmp." + NEWLINE);
/* 14871 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*       */         
/* 14873 */         while (iterator.hasNext()) {
/*       */           
/* 14875 */           String str1 = iterator.next();
/* 14876 */           String str2 = parseString(str1, 1, "<:>");
/* 14877 */           String[] arrayOfString1 = extractStringLines(parseString(str1, 2, "<:>"), 51);
/* 14878 */           for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */             
/* 14880 */             paramStringBuffer.append(arrayOfString1[b1]);
/* 14881 */             paramStringBuffer.append(NEWLINE);
/*       */           } 
/* 14883 */           paramStringBuffer.append(setString("", 30));
/* 14884 */           paramStringBuffer.append(str2);
/* 14885 */           paramStringBuffer.append(setString("", 12));
/* 14886 */           paramStringBuffer.append("XXXX.XX");
/* 14887 */           paramStringBuffer.append(setString("", 10));
/* 14888 */           paramStringBuffer.append(this.productNumber_NewModels_HT.get(str2));
/* 14889 */           paramStringBuffer.append(NEWLINE);
/*       */         } 
/*       */       } 
/*       */ 
/*       */ 
/*       */       
/* 14895 */       retrieveChargesFeaturesFormat3_MTM(paramString, paramTreeMap, paramBoolean, paramStringBuffer);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveChargesFeaturesFormat3_MTM(String paramString, TreeMap paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 14909 */     if (paramTreeMap.size() > 0) {
/*       */       
/* 14911 */       String str1 = "";
/* 14912 */       String str2 = "";
/* 14913 */       String str3 = "";
/* 14914 */       String str4 = "";
/* 14915 */       String str5 = "";
/* 14916 */       String str6 = "";
/*       */       
/* 14918 */       String[] arrayOfString = null;
/* 14919 */       boolean bool = false;
/* 14920 */       Set set = paramTreeMap.keySet();
/* 14921 */       Iterator<String> iterator = set.iterator();
/*       */       
/* 14923 */       while (iterator.hasNext()) {
/*       */         
/* 14925 */         String str = iterator.next();
/* 14926 */         str2 = (String)paramTreeMap.get(str);
/* 14927 */         str4 = parseString(str, 2, "<:>");
/* 14928 */         str6 = paramString;
/* 14929 */         if (!str4.equals(str3) || !str6.equals(str5)) {
/*       */           
/* 14931 */           arrayOfString = extractStringLines(parseString(str, 4, "<:>"), 51);
/* 14932 */           bool = true;
/*       */         } 
/* 14934 */         setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */         
/* 14936 */         if (parseString(str, 8, "<:>").length() > 0)
/*       */         {
/* 14938 */           if (!str4.equals(str3)) {
/*       */             
/* 14940 */             String[] arrayOfString1 = getStringTokens(parseString(str, 8, "<:>"), NEWLINE);
/* 14941 */             for (byte b = 0; b < arrayOfString1.length; b++) {
/*       */               
/* 14943 */               if (arrayOfString1[b].length() > 58) {
/*       */                 
/* 14945 */                 String[] arrayOfString2 = extractStringLines(arrayOfString1[b], 58);
/* 14946 */                 for (byte b1 = 0; b1 < arrayOfString2.length; b1++)
/*       */                 {
/* 14948 */                   paramStringBuffer.append(":hp2." + arrayOfString2[b1] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/* 14953 */                 paramStringBuffer.append(":hp2." + arrayOfString1[b] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         }
/*       */         
/* 14959 */         if (bool) {
/*       */           
/* 14961 */           for (byte b = 0; b < arrayOfString.length; b++)
/*       */           {
/* 14963 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/* 14965 */           bool = false;
/*       */         } 
/*       */         
/* 14968 */         paramStringBuffer.append(setString("", 30));
/* 14969 */         paramStringBuffer.append(parseString(str, 3, "<:>"));
/* 14970 */         paramStringBuffer.append("      ");
/*       */         
/* 14972 */         if (!str4.equals(str3)) {
/*       */           
/* 14974 */           paramStringBuffer.append(parseString(str, 2, "<:>"));
/*       */         }
/*       */         else {
/*       */           
/* 14978 */           paramStringBuffer.append("    ");
/*       */         } 
/* 14980 */         paramStringBuffer.append("  ");
/*       */         
/* 14982 */         if (!str4.equals(str3)) {
/*       */           
/* 14984 */           paramStringBuffer.append(parseString(str, 9, "<:>"));
/*       */         }
/*       */         else {
/*       */           
/* 14988 */           paramStringBuffer.append("       ");
/*       */         } 
/* 14990 */         paramStringBuffer.append(" ");
/* 14991 */         paramStringBuffer.append(parseString(str, 5, "<:>"));
/* 14992 */         paramStringBuffer.append("  ");
/* 14993 */         paramStringBuffer.append(parseString(str, 6, "<:>"));
/* 14994 */         paramStringBuffer.append(" ");
/* 14995 */         paramStringBuffer.append(parseString(str, 7, "<:>"));
/* 14996 */         paramStringBuffer.append(NEWLINE);
/*       */         
/* 14998 */         str1 = str2;
/* 14999 */         str3 = str4;
/* 15000 */         str5 = str6;
/*       */       } 
/* 15002 */       if (!str2.equals("WW"))
/*       */       {
/* 15004 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/* 15007 */       paramStringBuffer.append(":exmp." + NEWLINE);
/* 15008 */       paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */     } 
/*       */   }
/*       */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sgv30b\AUTOGENRpt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */