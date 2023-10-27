/*       */ package COM.ibm.eannounce.abr.sg;
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
/*       */ public class AUTOGENRpt
/*       */ {
/*       */   public static final String VERSION = "1.62";
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
/*       */   public static final boolean GMLFORMAT = true;
/*       */   public static final boolean XMLFORMAT = false;
/*       */   private static final int NEWMODELS = 1;
/*       */   private static final int EXISTINGMODELS = 2;
/*       */   private static final int NEWFC = 1;
/*       */   private static final int EXISTINGFC = 2;
/*       */   private EntityList list;
/*       */   private GeneralAreaList gal;
/*       */   private Database dbCurrent;
/*   291 */   private EntityItem rootEntity = null;
/*   292 */   private String annDate = "";
/*   293 */   private String annCodeName = "";
/*   294 */   private String annType = "";
/*   295 */   private String annTypeDesc = "";
/*       */   
/*   297 */   private Vector availV = null;
/*   298 */   private Vector availVector = null;
/*       */   
/*       */   private Hashtable usGeoHT;
/*       */   
/*       */   private Hashtable apGeoHT;
/*       */   
/*       */   private Hashtable laGeoHT;
/*       */   private Hashtable canGeoHT;
/*       */   private Hashtable emeaGeoHT;
/*       */   private Hashtable geoHT;
/*   308 */   private String brand = "";
/*   309 */   private int format = 0;
/*   310 */   private String inventoryGroup = "";
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
/*       */   private TreeMap charges_NewModels_TM;
/*       */   private TreeMap charges_NewFC_TM;
/*       */   private TreeMap charges_ExistingFC_TM;
/*       */   private TreeMap charges_NewModels_NewFC_TM;
/*       */   private TreeMap charges_NewModels_ExistingFC_TM;
/*       */   private TreeMap charges_ExistingModels_NewFC_TM;
/*       */   private TreeMap charges_ExistingModels_ExistingFC_TM;
/*       */   private TreeMap charges_Feature_Conversions_TM;
/*       */   private TreeMap salesManual_TM;
/*       */   private TreeMap salesManualSpecifyFeatures_TM;
/*       */   private TreeMap salesManualSpecialFeaturesInitialOrder_TM;
/*       */   private TreeMap salesManualSpecialFeaturesOther_TM;
/*       */   private TreeMap supportedDevices_TM;
/*       */   private TreeMap featureMatrix_TM;
/*       */   private StringBuffer headerSB;
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
/*       */   private static final int I_32 = 32;
/*       */   private static final int I_35 = 35;
/*       */   private static final int I_41 = 41;
/*       */   private static final int I_50 = 50;
/*       */   private static final int I_51 = 51;
/*       */   private static final int I_58 = 58;
/*       */   private static final int I_68 = 68;
/*       */   private static final int I_69 = 69;
/*       */   private static final int I_70 = 70;
/*       */   private static final int I_79 = 79;
/*       */   private static final int I_N3 = -3;
/*   389 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   390 */   static final String NEWLINE = new String(FOOL_JTEST);
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
/*   401 */     this.list = paramEntityList;
/*   402 */     this.gal = paramGeneralAreaList;
/*   403 */     this.dbCurrent = paramDatabase;
/*       */ 
/*       */ 
/*       */     
/*   407 */     this.usGeoHT = new Hashtable<>();
/*   408 */     this.apGeoHT = new Hashtable<>();
/*   409 */     this.laGeoHT = new Hashtable<>();
/*   410 */     this.canGeoHT = new Hashtable<>();
/*   411 */     this.emeaGeoHT = new Hashtable<>();
/*       */     
/*   413 */     this.geoHT = new Hashtable<>();
/*       */     
/*   415 */     this.machineTypeTS = new TreeSet();
/*   416 */     this.featureHT = new Hashtable<>();
/*       */     
/*   418 */     this.productNumber_NewModels_TM = new TreeMap<>();
/*   419 */     this.productNumber_NewModels_HT = new Hashtable<>();
/*   420 */     this.productNumber_NewFC_TM = new TreeMap<>();
/*   421 */     this.productNumber_ExistingFC_TM = new TreeMap<>();
/*   422 */     this.productNumber_NewModels_NewFC_TM = new TreeMap<>();
/*   423 */     this.productNumber_NewModels_ExistingFC_TM = new TreeMap<>();
/*   424 */     this.productNumber_ExistingModels_NewFC_TM = new TreeMap<>();
/*   425 */     this.productNumber_ExistingModels_ExistingFC_TM = new TreeMap<>();
/*   426 */     this.productNumber_MTM_Conversions_TM = new TreeMap<>();
/*   427 */     this.productNumber_Model_Conversions_TM = new TreeMap<>();
/*   428 */     this.productNumber_Feature_Conversions_TM = new TreeMap<>();
/*   429 */     this.featureVector = new Vector();
/*       */     
/*   431 */     this.charges_NewModels_TM = new TreeMap<>();
/*   432 */     this.charges_NewFC_TM = new TreeMap<>();
/*   433 */     this.charges_ExistingFC_TM = new TreeMap<>();
/*   434 */     this.charges_NewModels_NewFC_TM = new TreeMap<>();
/*   435 */     this.charges_NewModels_ExistingFC_TM = new TreeMap<>();
/*   436 */     this.charges_ExistingModels_NewFC_TM = new TreeMap<>();
/*   437 */     this.charges_ExistingModels_ExistingFC_TM = new TreeMap<>();
/*   438 */     this.charges_Feature_Conversions_TM = new TreeMap<>();
/*       */     
/*   440 */     this.salesManual_TM = new TreeMap<>();
/*   441 */     this.salesManualSpecifyFeatures_TM = new TreeMap<>();
/*   442 */     this.salesManualSpecialFeaturesInitialOrder_TM = new TreeMap<>();
/*   443 */     this.salesManualSpecialFeaturesOther_TM = new TreeMap<>();
/*       */     
/*   445 */     this.supportedDevices_TM = new TreeMap<>();
/*       */     
/*   447 */     this.featureMatrix_TM = new TreeMap<>();
/*       */     
/*   449 */     this.headerSB = new StringBuffer();
/*       */     
/*   451 */     this.featureMatrixError = new TreeSet();
/*       */ 
/*       */     
/*   454 */     this.debug = false;
/*   455 */     this.debugBuff = new StringBuffer();
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
/*   466 */     this.debug = true;
/*   467 */     return init(paramStringBuffer);
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
/*   479 */     EntityGroup entityGroup = this.list.getParentEntityGroup();
/*       */     
/*   481 */     if (null == entityGroup || entityGroup.getEntityItemCount() > 1)
/*       */     {
/*   483 */       return false;
/*       */     }
/*       */     
/*   486 */     this.rootEntity = entityGroup.getEntityItem(0);
/*   487 */     if (isSelected(this.rootEntity, "BRAND", "0010")) {
/*       */       
/*   489 */       this.brand = "iSeries";
/*   490 */       this.format = 1;
/*       */     }
/*   492 */     else if (isSelected(this.rootEntity, "BRAND", "0020")) {
/*       */       
/*   494 */       this.brand = "pSeries";
/*   495 */       this.format = 2;
/*       */     }
/*   497 */     else if (isSelected(this.rootEntity, "BRAND", "0030")) {
/*       */       
/*   499 */       this.brand = "totalStorage";
/*   500 */       this.format = 2;
/*       */     }
/*   502 */     else if (isSelected(this.rootEntity, "BRAND", "0040")) {
/*       */       
/*   504 */       this.brand = "xSeries";
/*   505 */       this.format = 2;
/*       */     }
/*   507 */     else if (isSelected(this.rootEntity, "BRAND", "0050")) {
/*       */       
/*   509 */       this.brand = "zSeries";
/*   510 */       this.format = 1;
/*       */     }
/*       */     else {
/*       */       
/*   514 */       paramStringBuffer.append("<p><b>Unsupported brand.</b></p>" + NEWLINE);
/*   515 */       log("Unsupported brand");
/*   516 */       return false;
/*       */     } 
/*       */     
/*   519 */     if (isSelected(this.rootEntity, "ANNTYPE", "19")) {
/*       */       
/*   521 */       this.annType = "new";
/*   522 */       this.annTypeDesc = "New";
/*       */     }
/*   524 */     else if (isSelected(this.rootEntity, "ANNTYPE", "14")) {
/*       */       
/*   526 */       this.annType = "withdraw";
/*   527 */       this.annTypeDesc = "End Of Life - Withdrawal from mktg";
/*       */     }
/*   529 */     else if (isSelected(this.rootEntity, "ANNTYPE", "12")) {
/*       */       
/*   531 */       this.annType = "withdraw";
/*   532 */       this.annTypeDesc = "End Of Life - Change to End Of Service Date";
/*       */     }
/*   534 */     else if (isSelected(this.rootEntity, "ANNTYPE", "13")) {
/*       */       
/*   536 */       this.annType = "withdraw";
/*   537 */       this.annTypeDesc = "End Of Life - Discontinuance of service";
/*       */     }
/*   539 */     else if (isSelected(this.rootEntity, "ANNTYPE", "16")) {
/*       */       
/*   541 */       this.annType = "withdraw";
/*   542 */       this.annTypeDesc = "End Of Life - Both";
/*       */     }
/*       */     else {
/*       */       
/*   546 */       paramStringBuffer.append("<p><b>Unsupported Annoucement Type.</b><br />" + NEWLINE);
/*   547 */       paramStringBuffer.append("<b>Supported Announcement Types Are: New, End Of Life.</b></p>" + NEWLINE);
/*   548 */       return false;
/*       */     } 
/*       */     
/*   551 */     this.annDate = getAttributeValue(this.rootEntity, "ANNDATE", "", "", false);
/*   552 */     this.annCodeName = getAttributeValue(this.rootEntity, "ANNCODENAME", "", "", false);
/*   553 */     this.inventoryGroup = getAttributeValue(this.rootEntity, "INVENTORYGROUP", "", "", false);
/*       */     
/*   555 */     log("Announcement EID = " + (new Integer(this.rootEntity.getEntityID())).toString());
/*   556 */     log("ANNDATE is " + this.annDate + " and ANNCODENAME is " + this.annCodeName);
/*   557 */     log("Brand = " + getAttributeValue(this.rootEntity, "BRAND", "", "", false));
/*       */     
/*   559 */     this.availV = getAllLinkedEntities(entityGroup, "ANNAVAILA", "AVAIL");
/*   560 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*   561 */     if (this.annType.equals("new")) {
/*       */       
/*   563 */       hashtable.put("AVAILTYPE", "146");
/*   564 */       this.availVector = getEntitiesWithMatchedAttr(this.availV, hashtable);
/*       */     }
/*   566 */     else if (this.annType.equals("withdraw")) {
/*       */       
/*   568 */       hashtable.put("AVAILTYPE", "149");
/*   569 */       this.availVector = getEntitiesWithMatchedAttr(this.availV, hashtable);
/*   570 */       hashtable.clear();
/*   571 */       hashtable.put("AVAILTYPE", "151");
/*   572 */       this.availVector.addAll(getEntitiesWithMatchedAttr(this.availV, hashtable));
/*   573 */       hashtable.clear();
/*   574 */       hashtable.put("AVAILTYPE", "152");
/*   575 */       this.availVector.addAll(getEntitiesWithMatchedAttr(this.availV, hashtable));
/*   576 */       hashtable.clear();
/*   577 */       hashtable.put("AVAILTYPE", "153");
/*   578 */       this.availVector.addAll(getEntitiesWithMatchedAttr(this.availV, hashtable));
/*       */     } 
/*       */     
/*   581 */     if (this.availVector.size() == 0) {
/*       */       
/*   583 */       if (this.annType.equals("new")) {
/*       */         
/*   585 */         paramStringBuffer.append("<p><b>Announcement Type = " + this.annTypeDesc + " but no AVAIL Entities with Avail Type = Planned Availability are found.</b></p>");
/*       */       }
/*   587 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*   589 */         paramStringBuffer.append("<p><b>Announcement Type = " + this.annTypeDesc + " but no AVAIL Entities with Avail Type = Last Order or End of Service or End of Dev Support or Last Initial Order are found.</b></p>");
/*       */       } 
/*   591 */       return false;
/*       */     } 
/*       */     
/*   594 */     hashtable.clear();
/*   595 */     hashtable = null;
/*   596 */     this.availV.clear();
/*   597 */     this.availV = null;
/*       */     
/*   599 */     getListOfMTs();
/*       */     
/*   601 */     productNumber_NewModels();
/*   602 */     if (1 == this.format) {
/*       */       
/*   604 */       productNumber_FeatureCodes1();
/*       */     }
/*   606 */     else if (2 == this.format) {
/*       */       
/*   608 */       if (this.annType.equals("new")) {
/*       */         
/*   610 */         productNumber_FeatureCodes2();
/*       */       }
/*   612 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*   614 */         productNumber_FeatureCodes1();
/*       */       } 
/*       */     } 
/*   617 */     productNumber_MTM_Model_Conversions();
/*   618 */     productNumber_Feature_Conversions();
/*       */     
/*   620 */     if (this.annType.equals("new")) {
/*       */       
/*   622 */       charges_NewModels();
/*   623 */       if (1 == this.format) {
/*       */         
/*   625 */         charges_FeatureCodes1();
/*       */       }
/*   627 */       else if (2 == this.format) {
/*       */         
/*   629 */         charges_FeatureCodes2();
/*       */       } 
/*   631 */       salesManual();
/*   632 */       supportedDevices();
/*   633 */       featureMatrix();
/*       */     } 
/*       */     
/*   636 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void getListOfMTs() {
/*   645 */     String str = "";
/*       */     
/*   647 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*   649 */     hashtable.clear();
/*   650 */     hashtable.put("COFCAT", "100");
/*   651 */     hashtable.put("COFSUBCAT", "126");
/*   652 */     hashtable.put("COFGRP", "150");
/*       */     
/*   654 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*   655 */     while (iterator.hasNext()) {
/*       */       
/*   657 */       EntityItem entityItem = iterator.next();
/*   658 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*   659 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*   661 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*   663 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*   664 */         if (null != eANEntity)
/*       */         {
/*   666 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*   668 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*   669 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*   670 */             if (null != eANEntity1) {
/*       */               
/*   672 */               str = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*   673 */               if (null == str)
/*       */               {
/*   675 */                 str = " ";
/*       */               }
/*   677 */               str = str.trim();
/*   678 */               str = setString(str, 4);
/*       */               
/*   680 */               this.machineTypeTS.add(str);
/*       */             } 
/*       */           } 
/*       */         }
/*       */       } 
/*       */     } 
/*   686 */     hashtable.clear();
/*   687 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void productNumber_NewModels() {
/*   697 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*   699 */     hashtable.put("COFCAT", "100");
/*   700 */     hashtable.put("COFSUBCAT", "126");
/*   701 */     hashtable.put("COFGRP", "150");
/*       */ 
/*       */     
/*   704 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*   705 */     while (iterator.hasNext()) {
/*       */       
/*   707 */       EntityItem entityItem = iterator.next();
/*   708 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*   709 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/*   710 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*   712 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*   714 */         populate_PN_NewModels_TM(entityItem, entityItem1);
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
/*   765 */     String str5 = paramEntityItem2.getKey();
/*   766 */     updateGeoHT(paramEntityItem1, str5);
/*       */     
/*   768 */     String str1 = getAttributeFlagValue(paramEntityItem2, "MACHTYPEATR");
/*   769 */     if (null == str1)
/*       */     {
/*   771 */       str1 = " ";
/*       */     }
/*   773 */     str1 = str1.trim();
/*   774 */     str1 = setString(str1, 4);
/*   775 */     String str2 = str1;
/*   776 */     String str3 = getAttributeValue(paramEntityItem2, "MODELATR", "", "000", false);
/*   777 */     str3 = str3.trim();
/*   778 */     str3 = setString(str3, 3);
/*   779 */     str1 = str1 + "<:>" + str3;
/*       */     
/*   781 */     if (1 == this.format) {
/*       */       
/*   783 */       String str = getAttributeValue(paramEntityItem2, "INVNAME", "", "", false);
/*   784 */       str = str.trim();
/*   785 */       if (str.equals("")) {
/*       */         
/*   787 */         str = getAttributeValue(paramEntityItem2, "INTERNALNAME", "", "", false);
/*   788 */         if (str.length() > 28)
/*       */         {
/*   790 */           str = str.substring(0, 28);
/*       */         }
/*   792 */         str = str.trim();
/*   793 */         str = str.toUpperCase();
/*       */       } 
/*       */ 
/*       */ 
/*       */       
/*   798 */       str = setString(str, 28);
/*   799 */       str1 = str1 + "<:>" + str;
/*       */     }
/*   801 */     else if (2 == this.format) {
/*       */       
/*   803 */       String str = getAttributeValue(paramEntityItem2, "MKTGNAME", "", "", false);
/*   804 */       str = str.trim();
/*   805 */       if (str.equals(""))
/*       */       {
/*   807 */         this.featureMatrixError.add("10<:>Product Number<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Marketing Name");
/*       */       }
/*   809 */       str1 = str1 + "<:>" + str;
/*       */     } 
/*       */ 
/*       */     
/*   813 */     if (isSelected(paramEntityItem2, "INSTALL", "5671")) {
/*       */       
/*   815 */       str1 = str1 + "<:>Yes";
/*   816 */       this.productNumber_NewModels_HT.put(str3, "Yes");
/*       */     }
/*   818 */     else if (isSelected(paramEntityItem2, "INSTALL", "5672")) {
/*       */       
/*   820 */       str1 = str1 + "<:>No ";
/*   821 */       this.productNumber_NewModels_HT.put(str3, "No ");
/*       */     }
/*   823 */     else if (isSelected(paramEntityItem2, "INSTALL", "5673")) {
/*       */       
/*   825 */       str1 = str1 + "<:>N/A";
/*   826 */       this.productNumber_NewModels_HT.put(str3, "N/A");
/*       */     }
/*       */     else {
/*       */       
/*   830 */       str1 = str1 + "<:>   ";
/*   831 */       this.productNumber_NewModels_HT.put(str3, "   ");
/*       */     } 
/*       */     
/*   834 */     if (getAttributeValue(paramEntityItem2, "INSTALL", "", "", false).equals(""))
/*       */     {
/*   836 */       if (1 == this.format)
/*       */       {
/*   838 */         this.featureMatrixError.add("10<:>Product Number<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Customer Install");
/*       */       }
/*       */     }
/*       */ 
/*       */     
/*   843 */     String str4 = getGeo(str5);
/*       */     
/*   845 */     addToTreeMap(str1, str4, this.productNumber_NewModels_TM);
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
/*   866 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*   867 */     String str = "";
/*   868 */     boolean bool = true;
/*       */     
/*   870 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*   871 */     while (iterator.hasNext()) {
/*       */       
/*   873 */       EntityItem entityItem = iterator.next();
/*   874 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*   875 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*   878 */         EntityItem entityItem1 = vector.get(b);
/*   879 */         String str1 = entityItem1.getKey();
/*   880 */         String str2 = getAttributeValue(entityItem, "EFFECTIVEDATE", "", "", false);
/*   881 */         updateGeoHT(entityItem, str1);
/*       */         
/*   883 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*   884 */         if (null != eANEntity) {
/*       */ 
/*       */           
/*   887 */           hashtable.clear();
/*   888 */           hashtable.put("COFCAT", "100");
/*   889 */           hashtable.put("COFSUBCAT", "126");
/*   890 */           hashtable.put("COFGRP", "150");
/*   891 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*   893 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*   894 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*   895 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*   906 */               hashtable.clear();
/*   907 */               hashtable.put("PRICEDFEATURE", "120");
/*       */ 
/*       */ 
/*       */               
/*   911 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*   912 */               String str3 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*   913 */               if (null == str3)
/*       */               {
/*   915 */                 str3 = " ";
/*       */               }
/*   917 */               str3 = str3.trim();
/*   918 */               str3 = setString(str3, 4);
/*   919 */               str = str3;
/*   920 */               String str4 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*   921 */               str4 = str4.trim();
/*   922 */               str4 = setString(str4, 4);
/*   923 */               str3 = str3 + "<:>" + str4;
/*   924 */               String str5 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*   925 */               str5 = str5.trim();
/*   926 */               str5 = setString(str5, 3);
/*   927 */               str3 = str3 + "<:>" + str5;
/*       */               
/*   929 */               String str6 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/*   930 */               if (this.annDate.equals(str6)) {
/*       */                 
/*   932 */                 bool = true;
/*       */ 
/*       */               
/*       */               }
/*   936 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/*   938 */                 bool = true;
/*       */               }
/*       */               else {
/*       */                 
/*   942 */                 bool = false;
/*       */               } 
/*       */ 
/*       */               
/*   946 */               if (str4.equals("    "))
/*       */               {
/*   948 */                 this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/*   951 */               if (1 == this.format) {
/*       */                 
/*   953 */                 String str8 = getAttributeValue(entityItem1, "INVNAME", "", "", false);
/*   954 */                 str8 = str8.trim();
/*   955 */                 if (str8.equals("")) {
/*       */                   
/*   957 */                   str8 = getAttributeValue(entityItem3, "INVNAME", "", "", false);
/*   958 */                   str8 = str8.trim();
/*   959 */                   if (str8.equals("")) {
/*       */                     
/*   961 */                     str8 = getAttributeValue(entityItem3, "COMNAME", "", "", false);
/*   962 */                     if (str8.length() > 28)
/*       */                     {
/*   964 */                       str8 = str8.substring(0, 28);
/*       */                     }
/*   966 */                     str8 = str8.trim();
/*   967 */                     str8 = str8.toUpperCase();
/*       */                   } 
/*       */                 } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/*   975 */                 str8 = setString(str8, 28);
/*   976 */                 str3 = str3 + "<:>" + str8;
/*       */               }
/*   978 */               else if (2 == this.format) {
/*       */                 
/*   980 */                 String str8 = getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/*   981 */                 str8 = str8.trim();
/*   982 */                 if (str8.equals("")) {
/*       */                   
/*   984 */                   str8 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false);
/*   985 */                   str8 = str8.trim();
/*   986 */                   if (str8.equals("")) {
/*       */                     
/*   988 */                     this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Marketing Name");
/*   989 */                     this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>FEATURE " + str4 + "<:>Marketing Name");
/*       */                   } 
/*       */                 } 
/*   992 */                 str3 = str3 + "<:>" + str8;
/*       */               } 
/*       */ 
/*       */               
/*   996 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*   998 */                 str3 = str3 + "<:>Both   ";
/*       */               }
/*  1000 */               else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  1002 */                 str3 = str3 + "<:>MES    ";
/*       */               }
/*  1004 */               else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  1006 */                 str3 = str3 + "<:>Initial";
/*       */               }
/*  1008 */               else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  1010 */                 str3 = str3 + "<:>Support";
/*       */               }
/*       */               else {
/*       */                 
/*  1014 */                 str3 = str3 + "<:>       ";
/*       */               } 
/*       */               
/*  1017 */               if (getAttributeValue(entityItem1, "ORDERCODE", "", "", false).equals(""))
/*       */               {
/*  1019 */                 if (1 == this.format)
/*       */                 {
/*  1021 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */                 }
/*       */               }
/*       */ 
/*       */               
/*  1026 */               if (isSelected(entityItem1, "INSTALL", "5671")) {
/*       */                 
/*  1028 */                 str3 = str3 + "<:>Yes";
/*       */               }
/*  1030 */               else if (isSelected(entityItem1, "INSTALL", "5672")) {
/*       */                 
/*  1032 */                 str3 = str3 + "<:>No ";
/*       */               }
/*  1034 */               else if (isSelected(entityItem1, "INSTALL", "5673")) {
/*       */                 
/*  1036 */                 str3 = str3 + "<:>N/A";
/*       */               }
/*       */               else {
/*       */                 
/*  1040 */                 str3 = str3 + "<:>   ";
/*       */               } 
/*       */               
/*  1043 */               if (getAttributeValue(entityItem1, "INSTALL", "", "", false).equals(""))
/*       */               {
/*  1045 */                 if (1 == this.format)
/*       */                 {
/*  1047 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str5 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Customer Install");
/*       */                 }
/*       */               }
/*       */               
/*  1051 */               if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */                 
/*  1053 */                 str3 = str3 + "<:>Yes";
/*       */               }
/*  1055 */               else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */                 
/*  1057 */                 str3 = str3 + "<:>No ";
/*       */               }
/*       */               else {
/*       */                 
/*  1061 */                 str3 = str3 + "<:>   ";
/*       */               } 
/*       */               
/*  1064 */               str3 = str3 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*  1065 */               str3 = str3 + "<:>" + str2;
/*       */               
/*  1067 */               String str7 = getGeo(str1);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  1082 */               if (bool) {
/*       */                 
/*  1084 */                 addToTreeMap(str3, str7, this.productNumber_NewFC_TM);
/*       */               }
/*       */               else {
/*       */                 
/*  1088 */                 addToTreeMap(str3, str7, this.productNumber_ExistingFC_TM);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  1096 */     hashtable.clear();
/*  1097 */     hashtable = null;
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
/*       */   private void productNumber_FeatureCodes2() {
/*  1117 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  1118 */     String str = "";
/*  1119 */     boolean bool = true;
/*  1120 */     boolean bool1 = true;
/*       */     
/*  1122 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  1123 */     while (iterator.hasNext()) {
/*       */       
/*  1125 */       EntityItem entityItem = iterator.next();
/*  1126 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  1127 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  1130 */         EntityItem entityItem1 = vector.get(b);
/*  1131 */         String str1 = entityItem1.getKey();
/*  1132 */         updateGeoHT(entityItem, str1);
/*       */         
/*  1134 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*  1135 */         if (null != eANEntity) {
/*       */ 
/*       */           
/*  1138 */           hashtable.clear();
/*  1139 */           hashtable.put("COFCAT", "100");
/*  1140 */           hashtable.put("COFSUBCAT", "126");
/*  1141 */           hashtable.put("COFGRP", "150");
/*  1142 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*  1144 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*  1145 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  1146 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  1158 */               hashtable.clear();
/*  1159 */               hashtable.put("PRICEDFEATURE", "120");
/*       */ 
/*       */ 
/*       */               
/*  1163 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*  1164 */               String str2 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  1165 */               if (null == str2)
/*       */               {
/*  1167 */                 str2 = " ";
/*       */               }
/*  1169 */               str2 = str2.trim();
/*  1170 */               str2 = setString(str2, 4);
/*  1171 */               str = str2;
/*  1172 */               String str3 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  1173 */               str3 = str3.trim();
/*  1174 */               str3 = setString(str3, 4);
/*  1175 */               str2 = str2 + "<:>" + str3;
/*  1176 */               String str4 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  1177 */               str4 = str4.trim();
/*  1178 */               str4 = setString(str4, 3);
/*  1179 */               str2 = str2 + "<:>" + str4;
/*       */               
/*  1181 */               bool1 = this.productNumber_NewModels_HT.containsKey(str4);
/*       */               
/*  1183 */               String str5 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/*  1184 */               if (this.annDate.equals(str5)) {
/*       */                 
/*  1186 */                 bool = true;
/*       */ 
/*       */               
/*       */               }
/*  1190 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/*  1192 */                 bool = true;
/*       */               }
/*       */               else {
/*       */                 
/*  1196 */                 bool = false;
/*       */               } 
/*       */ 
/*       */               
/*  1200 */               if (str3.equals("    "))
/*       */               {
/*  1202 */                 this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str4 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/*  1205 */               String str6 = getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/*  1206 */               str6 = str6.trim();
/*  1207 */               if (str6.equals("")) {
/*       */                 
/*  1209 */                 str6 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false);
/*  1210 */                 str6 = str6.trim();
/*  1211 */                 if (str6.equals("")) {
/*       */                   
/*  1213 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str4 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Marketing Name");
/*  1214 */                   this.featureMatrixError.add("10<:>Product Number<:>" + str + "<:>" + str4 + "<:>FEATURE " + str3 + "<:>Marketing Name");
/*       */                 } 
/*       */               } 
/*  1217 */               str2 = str2 + "<:>" + str6;
/*       */ 
/*       */               
/*  1220 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  1222 */                 str2 = str2 + "<:>Both   ";
/*       */               }
/*  1224 */               else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  1226 */                 str2 = str2 + "<:>MES    ";
/*       */               }
/*  1228 */               else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  1230 */                 str2 = str2 + "<:>Initial";
/*       */               }
/*  1232 */               else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  1234 */                 str2 = str2 + "<:>Support";
/*       */               }
/*       */               else {
/*       */                 
/*  1238 */                 str2 = str2 + "<:>       ";
/*       */               } 
/*       */ 
/*       */               
/*  1242 */               if (isSelected(entityItem1, "INSTALL", "5671")) {
/*       */                 
/*  1244 */                 str2 = str2 + "<:>Yes";
/*       */               }
/*  1246 */               else if (isSelected(entityItem1, "INSTALL", "5672")) {
/*       */                 
/*  1248 */                 str2 = str2 + "<:>No ";
/*       */               }
/*  1250 */               else if (isSelected(entityItem1, "INSTALL", "5673")) {
/*       */                 
/*  1252 */                 str2 = str2 + "<:>N/A";
/*       */               }
/*       */               else {
/*       */                 
/*  1256 */                 str2 = str2 + "<:>   ";
/*       */               } 
/*       */               
/*  1259 */               if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */                 
/*  1261 */                 str2 = str2 + "<:>Yes";
/*       */               }
/*  1263 */               else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */                 
/*  1265 */                 str2 = str2 + "<:>No ";
/*       */               }
/*       */               else {
/*       */                 
/*  1269 */                 str2 = str2 + "<:>   ";
/*       */               } 
/*       */               
/*  1272 */               str2 = str2 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */               
/*  1274 */               String str7 = getGeo(str1);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  1289 */               if (bool1 && bool) {
/*       */                 
/*  1291 */                 addToTreeMap(str2, str7, this.productNumber_NewModels_NewFC_TM);
/*       */               }
/*  1293 */               else if (bool1 && !bool) {
/*       */                 
/*  1295 */                 addToTreeMap(str2, str7, this.productNumber_NewModels_ExistingFC_TM);
/*       */               }
/*  1297 */               else if (!bool1 && bool) {
/*       */                 
/*  1299 */                 addToTreeMap(str2, str7, this.productNumber_ExistingModels_NewFC_TM);
/*       */               }
/*  1301 */               else if (!bool1 && !bool) {
/*       */                 
/*  1303 */                 addToTreeMap(str2, str7, this.productNumber_ExistingModels_ExistingFC_TM);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  1311 */     hashtable.clear();
/*  1312 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void productNumber_MTM_Model_Conversions() {
/*  1321 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  1322 */     while (iterator.hasNext()) {
/*       */       
/*  1324 */       EntityItem entityItem = iterator.next();
/*  1325 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELCONVERTAVAIL", "MODELCONVERT");
/*  1326 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  1332 */         EntityItem entityItem1 = vector.get(b);
/*  1333 */         String str5 = entityItem1.getKey();
/*  1334 */         updateGeoHT(entityItem, str5);
/*       */         
/*  1336 */         String str1 = getAttributeValue(entityItem1, "TOMACHTYPE", "", "", false);
/*  1337 */         str1 = str1.trim();
/*  1338 */         String str2 = setString(str1, 4);
/*  1339 */         str1 = getAttributeValue(entityItem1, "FROMMACHTYPE", "", "", false);
/*  1340 */         str1 = str1.trim();
/*  1341 */         String str3 = setString(str1, 4);
/*  1342 */         String str4 = "";
/*  1343 */         if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */           
/*  1345 */           str4 = "Yes";
/*       */         }
/*  1347 */         else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */           
/*  1349 */           str4 = "No ";
/*       */         }
/*       */         else {
/*       */           
/*  1353 */           str4 = "   ";
/*       */         } 
/*       */         
/*  1356 */         if (str3.equals(str2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  1366 */           String str7 = str3;
/*  1367 */           str1 = getAttributeValue(entityItem1, "TOMODEL", "", "", false);
/*  1368 */           str1 = str1.trim();
/*  1369 */           str7 = str7 + "<:>" + setString(str1, 3);
/*  1370 */           str1 = getAttributeValue(entityItem1, "FROMMODEL", "", "", false);
/*  1371 */           str1 = str1.trim();
/*  1372 */           str7 = str7 + "<:>" + setString(str1, 3);
/*  1373 */           str7 = str7 + "<:>" + str4;
/*       */           
/*  1375 */           String str6 = getGeo(str5);
/*  1376 */           addToTreeMap(str7, str6, this.productNumber_Model_Conversions_TM);
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
/*  1390 */           String str7 = str2;
/*  1391 */           str1 = getAttributeValue(entityItem1, "TOMODEL", "", "", false);
/*  1392 */           str1 = str1.trim();
/*  1393 */           str7 = str7 + "<:>" + setString(str1, 3);
/*  1394 */           str7 = str7 + "<:>" + str3;
/*  1395 */           str1 = getAttributeValue(entityItem1, "FROMMODEL", "", "", false);
/*  1396 */           str1 = str1.trim();
/*  1397 */           str7 = str7 + "<:>" + setString(str1, 3);
/*  1398 */           str7 = str7 + "<:>" + str4;
/*       */           
/*  1400 */           String str6 = getGeo(str5);
/*  1401 */           addToTreeMap(str7, str6, this.productNumber_MTM_Conversions_TM);
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
/*  1414 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */ 
/*       */     
/*  1417 */     Vector<EntityItem> vector = getAllLinkedEntities(this.availVector, "OOFAVAIL", "PRODSTRUCT");
/*  1418 */     for (byte b = 0; b < vector.size(); b++) {
/*       */       
/*  1420 */       EntityItem entityItem = vector.get(b);
/*  1421 */       EANEntity eANEntity = getUpLinkEntityItem(entityItem, "FEATURE");
/*       */       
/*  1423 */       if (null != eANEntity)
/*       */       {
/*  1425 */         this.featureVector.add(eANEntity.getKey());
/*       */       }
/*       */     } 
/*       */ 
/*       */     
/*  1430 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  1431 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */       
/*  1435 */       EntityItem entityItem = iterator.next();
/*  1436 */       Vector vector3 = getAllLinkedEntities(entityItem, "FEATURETRNAVAIL", "FCTRANSACTION");
/*  1437 */       hashtable.clear();
/*  1438 */       hashtable.put("FTCAT", "404");
/*  1439 */       Vector<EntityItem> vector1 = getEntitiesWithMatchedAttr(vector3, hashtable);
/*  1440 */       hashtable.clear();
/*  1441 */       hashtable.put("FTCAT", "406");
/*  1442 */       Vector vector2 = getEntitiesWithMatchedAttr(vector3, hashtable);
/*  1443 */       vector1.addAll(vector2);
/*  1444 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*       */         
/*  1446 */         EntityItem entityItem1 = vector1.get(b1);
/*  1447 */         populate_PN_Feature_Conversions_TM(entityItem, entityItem1);
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  1452 */     iterator = this.availVector.iterator();
/*  1453 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */       
/*  1457 */       EntityItem entityItem = iterator.next();
/*  1458 */       Vector vector3 = getAllLinkedEntities(entityItem, "MODELCONVERTAVAIL", "MODELCONVERT");
/*  1459 */       Vector vector4 = getAllLinkedEntities(vector3, "MODELCONVFCTRANSAC", "FCTRANSACTION");
/*  1460 */       hashtable.clear();
/*  1461 */       hashtable.put("FTCAT", "404");
/*  1462 */       Vector<EntityItem> vector1 = getEntitiesWithMatchedAttr(vector4, hashtable);
/*  1463 */       hashtable.clear();
/*  1464 */       hashtable.put("FTCAT", "406");
/*  1465 */       Vector vector2 = getEntitiesWithMatchedAttr(vector4, hashtable);
/*  1466 */       vector1.addAll(vector2);
/*  1467 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*       */         
/*  1469 */         EntityItem entityItem1 = vector1.get(b1);
/*  1470 */         populate_PN_Feature_Conversions_TM(entityItem, entityItem1);
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
/*  1521 */     String str13 = paramEntityItem2.getKey();
/*  1522 */     updateGeoHT(paramEntityItem1, str13);
/*       */     
/*  1524 */     String str1 = getAttributeValue(paramEntityItem2, "TOMODEL", "", "", false);
/*  1525 */     str1 = str1.trim();
/*  1526 */     str1 = setString(str1, 3);
/*  1527 */     String str2 = str1;
/*  1528 */     String str3 = getAttributeValue(paramEntityItem2, "TOMACHTYPE", "", "", false);
/*  1529 */     str3 = str3.trim();
/*  1530 */     str3 = setString(str3, 4);
/*  1531 */     str2 = str2 + "<:>" + str3;
/*  1532 */     String str4 = getAttributeValue(paramEntityItem2, "TOFEATURECODE", "", "", false);
/*  1533 */     str4 = str4.trim();
/*  1534 */     str4 = setString(str4, 4);
/*  1535 */     str2 = str2 + "<:>" + str4;
/*  1536 */     String str5 = getAttributeValue(paramEntityItem2, "FROMMODEL", "", "", false);
/*  1537 */     str5 = str5.trim();
/*  1538 */     str5 = setString(str5, 3);
/*       */     
/*  1540 */     str2 = str2 + "<:> ";
/*  1541 */     String str6 = getAttributeValue(paramEntityItem2, "FROMMACHTYPE", "", "", false);
/*  1542 */     str6 = str6.trim();
/*  1543 */     str6 = setString(str6, 4);
/*       */     
/*  1545 */     str2 = str2 + "<:> ";
/*  1546 */     String str7 = getAttributeValue(paramEntityItem2, "FROMFEATURECODE", "", "", false);
/*  1547 */     str7 = str7.trim();
/*  1548 */     str7 = setString(str7, 4);
/*  1549 */     str2 = str2 + "<:>" + str7;
/*  1550 */     String str8 = getAttributeValue(paramEntityItem1, "EFFECTIVEDATE", "", "", false);
/*       */     
/*  1552 */     String str9 = str3;
/*  1553 */     str9 = str9 + "<:>" + str1;
/*  1554 */     str9 = str9 + "<:>" + str4;
/*  1555 */     str9 = str9 + "<:>" + str6;
/*  1556 */     str9 = str9 + "<:>" + str5;
/*  1557 */     str9 = str9 + "<:>" + str7;
/*       */     
/*  1559 */     Vector<EntityItem> vector1 = getFeatureEntities(str3, str1, str4);
/*  1560 */     Vector<EntityItem> vector2 = getFeatureEntities(str6, str5, str7);
/*       */     
/*  1562 */     String str10 = "";
/*  1563 */     if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5100")) {
/*       */       
/*  1565 */       str10 = "Yes";
/*       */     }
/*  1567 */     else if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5101")) {
/*       */       
/*  1569 */       str10 = "No ";
/*       */     }
/*       */     else {
/*       */       
/*  1573 */       str10 = "   ";
/*       */     } 
/*       */     
/*  1576 */     String str11 = getGeo(str13);
/*       */     
/*  1578 */     String str12 = "";
/*       */     
/*  1580 */     if (vector1.size() == 0) {
/*       */       
/*  1582 */       str12 = "UNKNOWN HWFCCAT(FEATURE)";
/*       */       
/*  1584 */       str2 = " <:>" + str2;
/*  1585 */       str2 = str2 + "<:>" + str10;
/*  1586 */       str2 = str2 + "<:>";
/*  1587 */       str2 = str2 + "<:>" + str8;
/*  1588 */       addToTreeMap(str2, str11, this.productNumber_Feature_Conversions_TM);
/*       */     }
/*       */     else {
/*       */       
/*  1592 */       for (byte b = 0; b < vector1.size(); b++) {
/*       */         
/*  1594 */         EntityItem entityItem = vector1.get(b);
/*  1595 */         str12 = getAttributeValue(entityItem, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
/*       */         
/*  1597 */         str2 = " <:>" + str2;
/*  1598 */         str2 = str2 + "<:>" + str10;
/*  1599 */         str2 = str2 + "<:>" + getAttributeValue(entityItem, "EDITORNOTE", "", "", false).trim();
/*  1600 */         str2 = str2 + "<:>" + str8;
/*  1601 */         addToTreeMap(str2, str11, this.productNumber_Feature_Conversions_TM);
/*       */       } 
/*       */     } 
/*       */     
/*  1605 */     if (vector2.size() == 0 && vector1.size() == 0) {
/*       */       
/*  1607 */       str12 = "UNKNOWN HWFCCAT(FEATURE)";
/*  1608 */       str9 = str12 + "<:>" + str9;
/*  1609 */       str9 = str9 + "<:><:>";
/*  1610 */       str9 = str9 + "<:>" + str10;
/*  1611 */       str9 = str9 + "<:>";
/*  1612 */       addToTreeMap(str9, str11, this.charges_Feature_Conversions_TM);
/*  1613 */       log("In 1");
/*       */     } 
/*       */     
/*  1616 */     if (vector2.size() == 0 && vector1.size() > 0) {
/*       */       
/*  1618 */       for (byte b = 0; b < vector1.size(); b++) {
/*       */         
/*  1620 */         EntityItem entityItem = vector1.get(b);
/*  1621 */         str12 = getAttributeValue(entityItem, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
/*  1622 */         str9 = str12 + "<:>" + str9;
/*  1623 */         str9 = str9 + "<:>" + getAttributeValue(entityItem, "MKTGNAME", "", "", false);
/*  1624 */         str9 = str9 + "<:>";
/*  1625 */         str9 = str9 + "<:>" + str10;
/*  1626 */         str9 = str9 + "<:>" + getAttributeValue(entityItem, "EDITORNOTE", "", "", false).trim();
/*  1627 */         addToTreeMap(str9, str11, this.charges_Feature_Conversions_TM);
/*       */       } 
/*  1629 */       log("In 2");
/*       */     } 
/*       */     
/*  1632 */     if (vector2.size() > 0 && vector1.size() == 0) {
/*       */       
/*  1634 */       for (byte b = 0; b < vector2.size(); b++) {
/*       */         
/*  1636 */         EntityItem entityItem = vector2.get(b);
/*  1637 */         str12 = "UNKNOWN HWFCCAT(FEATURE)";
/*  1638 */         str9 = str12 + "<:>" + str9;
/*  1639 */         str9 = str9 + "<:>";
/*  1640 */         str9 = str9 + "<:>" + getAttributeValue(entityItem, "MKTGNAME", "", "", false);
/*  1641 */         str9 = str9 + "<:>" + str10;
/*  1642 */         str9 = str9 + "<:>";
/*  1643 */         addToTreeMap(str9, str11, this.charges_Feature_Conversions_TM);
/*       */       } 
/*  1645 */       log("In 3");
/*       */     } 
/*       */     
/*  1648 */     if (vector2.size() > 0 && vector1.size() > 0) {
/*       */       
/*  1650 */       for (byte b = 0; b < vector2.size(); b++) {
/*       */         
/*  1652 */         EntityItem entityItem = vector2.get(b);
/*  1653 */         String str = getAttributeValue(entityItem, "MKTGNAME", "", "", false);
/*       */         
/*  1655 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*       */           
/*  1657 */           EntityItem entityItem1 = vector1.get(b1);
/*  1658 */           str12 = getAttributeValue(entityItem1, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
/*  1659 */           str9 = str12 + "<:>" + str9;
/*  1660 */           str9 = str9 + "<:>" + getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/*  1661 */           str9 = str9 + "<:>" + str;
/*  1662 */           str9 = str9 + "<:>" + str10;
/*  1663 */           str9 = str9 + "<:>" + getAttributeValue(entityItem1, "EDITORNOTE", "", "", false).trim();
/*  1664 */           addToTreeMap(str9, str11, this.charges_Feature_Conversions_TM);
/*       */         } 
/*       */       } 
/*  1667 */       log("In 4");
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
/*  1681 */     Vector vector = new Vector();
/*  1682 */     Profile profile = this.list.getProfile();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     try {
/*  1688 */       String str = "SRDFEATURE";
/*       */       
/*  1690 */       SearchActionItem searchActionItem = new SearchActionItem(null, this.dbCurrent, profile, str);
/*  1691 */       RowSelectableTable rowSelectableTable = searchActionItem.getDynaSearchTable(this.dbCurrent);
/*       */       
/*  1693 */       int i = rowSelectableTable.getRowIndex("FEATURE:FEATURECODE");
/*  1694 */       if (i < 0)
/*       */       {
/*  1696 */         i = rowSelectableTable.getRowIndex("FEATURE:FEATURECODE:C");
/*       */       }
/*  1698 */       if (i < 0)
/*       */       {
/*  1700 */         i = rowSelectableTable.getRowIndex("FEATURE:FEATURECODE:R");
/*       */       }
/*  1702 */       if (i != -1 && paramString3.length() > 0)
/*       */       {
/*  1704 */         rowSelectableTable.put(i, 1, paramString3);
/*       */       }
/*       */       
/*  1707 */       rowSelectableTable.commit(this.dbCurrent);
/*       */       
/*  1709 */       EntityList entityList = searchActionItem.executeAction(this.dbCurrent, profile);
/*       */       
/*  1711 */       EntityGroup entityGroup = entityList.getEntityGroup("FEATURE");
/*       */ 
/*       */       
/*  1714 */       if (null == entityGroup)
/*       */       {
/*  1716 */         return vector;
/*       */       }
/*       */       
/*  1719 */       if (entityGroup.getEntityItemCount() > 0)
/*       */       {
/*  1721 */         Vector<EntityItem> vector1 = new Vector();
/*       */         
/*  1723 */         if (entityGroup.getEntityItemCount() > 50) {
/*       */           
/*  1725 */           int j = entityGroup.getEntityItemCount() / 50;
/*  1726 */           byte b1 = 0;
/*  1727 */           for (byte b2 = 0; b2 <= j; b2++)
/*       */           {
/*  1729 */             vector1.clear();
/*  1730 */             for (byte b = 0; b < 50; b++) {
/*       */ 
/*       */               
/*  1733 */               if (b1 == entityGroup.getEntityItemCount()) {
/*       */                 break;
/*       */               }
/*       */ 
/*       */               
/*  1738 */               EntityItem entityItem = entityGroup.getEntityItem(b1++);
/*       */               
/*  1740 */               vector1.addElement(entityItem);
/*       */             } 
/*  1742 */             if (vector1.size() > 0)
/*       */             {
/*  1744 */               getFeatureEntities(vector1, vector, paramString1, paramString2);
/*       */             }
/*       */           }
/*       */         
/*       */         }
/*       */         else {
/*       */           
/*  1751 */           for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*       */             
/*  1753 */             EntityItem entityItem = entityGroup.getEntityItem(b);
/*       */             
/*  1755 */             vector1.addElement(entityItem);
/*       */           } 
/*       */           
/*  1758 */           getFeatureEntities(vector1, vector, paramString1, paramString2);
/*       */         } 
/*       */         
/*  1761 */         vector1.clear();
/*  1762 */         vector1 = null;
/*       */       }
/*       */     
/*  1765 */     } catch (Exception exception) {
/*       */       
/*  1767 */       exception.printStackTrace();
/*  1768 */       log(exception.toString());
/*       */     } 
/*       */     
/*  1771 */     return vector;
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
/*  1786 */     String str = "EXRPT3FM";
/*       */     
/*  1788 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector1.size()];
/*       */     
/*  1790 */     log("In getFeatureEntities(Vector tmpVct, Vector featureVct, String machType, String model)"); byte b;
/*  1791 */     for (b = 0; b < paramVector1.size(); b++)
/*       */     {
/*  1793 */       arrayOfEntityItem[b] = paramVector1.elementAt(b);
/*       */     }
/*       */     
/*  1796 */     EntityList entityList = null;
/*  1797 */     if (paramVector1.size() > 0) {
/*       */ 
/*       */ 
/*       */       
/*  1801 */       Profile profile = this.list.getProfile();
/*  1802 */       entityList = this.dbCurrent.getEntityList(profile, new ExtractActionItem(null, this.dbCurrent, profile, str), arrayOfEntityItem);
/*       */ 
/*       */ 
/*       */       
/*  1806 */       log("I am here 1");
/*  1807 */       if (entityList.getEntityGroupCount() == 0)
/*       */       {
/*  1809 */         log("Extract was not found for " + str);
/*       */       }
/*       */       
/*  1812 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/*       */       
/*  1814 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*       */         
/*  1816 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/*  1817 */         log("I am here 2");
/*  1818 */         if (checkModel(entityItem, paramString2, paramString1))
/*       */         {
/*  1820 */           paramVector2.addElement(entityItem);
/*       */         }
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  1826 */     for (b = 0; b < arrayOfEntityItem.length; b++)
/*       */     {
/*  1828 */       arrayOfEntityItem[b] = null;
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
/*  1842 */     boolean bool = false;
/*       */     
/*  1844 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "PRODSTRUCT", "MODEL");
/*       */     
/*  1846 */     for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */       
/*  1849 */       EntityItem entityItem = vector.get(b);
/*  1850 */       String str2 = getAttributeFlagValue(entityItem, "MACHTYPEATR");
/*  1851 */       if (null == str2)
/*       */       {
/*  1853 */         str2 = " ";
/*       */       }
/*       */       
/*  1856 */       String str1 = getAttributeValue(entityItem, "MODELATR", "", "", false);
/*       */       
/*  1858 */       if (paramString2.equals(str2) && paramString1.equals(str1)) {
/*       */         
/*  1860 */         bool = true;
/*       */         
/*       */         break;
/*       */       } 
/*       */     } 
/*  1865 */     return bool;
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
/*  1876 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  1878 */     hashtable.put("COFCAT", "100");
/*  1879 */     hashtable.put("COFSUBCAT", "126");
/*  1880 */     hashtable.put("COFGRP", "150");
/*       */ 
/*       */     
/*  1883 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  1884 */     while (iterator.hasNext()) {
/*       */       
/*  1886 */       EntityItem entityItem = iterator.next();
/*  1887 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*  1888 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/*  1889 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  1891 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*  1893 */         populate_Charges_NewModels_TM(entityItem, entityItem1);
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
/*  1923 */     hashtable.clear();
/*  1924 */     hashtable = null;
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
/*  1946 */     String str5 = paramEntityItem2.getKey();
/*  1947 */     updateGeoHT(paramEntityItem1, str5);
/*  1948 */     String str1 = getAttributeFlagValue(paramEntityItem2, "MACHTYPEATR");
/*  1949 */     if (null == str1)
/*       */     {
/*  1951 */       str1 = " ";
/*       */     }
/*  1953 */     str1 = str1.trim();
/*  1954 */     str1 = setString(str1, 4);
/*  1955 */     String str2 = str1;
/*  1956 */     String str3 = getAttributeValue(paramEntityItem2, "MODELATR", "", "000", false);
/*  1957 */     str3 = str3.trim();
/*  1958 */     str3 = setString(str3, 3);
/*  1959 */     str1 = str1 + "<:>" + str3;
/*       */     
/*  1961 */     if (1 == this.format) {
/*       */       
/*  1963 */       String str = getAttributeValue(paramEntityItem2, "INVNAME", "", "", false);
/*  1964 */       str = str.trim();
/*  1965 */       if (str.equals("")) {
/*       */         
/*  1967 */         str = getAttributeValue(paramEntityItem2, "INTERNALNAME", "", "", false);
/*  1968 */         if (str.length() > 28)
/*       */         {
/*  1970 */           str = str.substring(0, 28);
/*       */         }
/*  1972 */         str = str.trim();
/*  1973 */         str = str.toUpperCase();
/*       */       } 
/*       */ 
/*       */ 
/*       */       
/*  1978 */       str = setString(str, 28);
/*  1979 */       str1 = str1 + "<:>" + str;
/*       */     }
/*  1981 */     else if (2 == this.format) {
/*       */       
/*  1983 */       String str = getAttributeValue(paramEntityItem2, "MKTGNAME", "", "", false);
/*  1984 */       str = str.trim();
/*  1985 */       if (str.equals(""))
/*       */       {
/*  1987 */         this.featureMatrixError.add("20<:>Charges<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Marketing Name");
/*       */       }
/*  1989 */       str1 = str1 + "<:>" + str;
/*       */     } 
/*       */ 
/*       */     
/*  1993 */     if (isSelected(paramEntityItem2, "MODELORDERCODE", "100")) {
/*       */       
/*  1995 */       str1 = str1 + "<:>Initial";
/*       */     }
/*  1997 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "110")) {
/*       */       
/*  1999 */       str1 = str1 + "<:>MES    ";
/*       */     }
/*  2001 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "120")) {
/*       */       
/*  2003 */       str1 = str1 + "<:>Both   ";
/*       */     }
/*       */     else {
/*       */       
/*  2007 */       str1 = str1 + "<:>       ";
/*       */     } 
/*       */     
/*  2010 */     if (getAttributeValue(paramEntityItem2, "MODELORDERCODE", "", "", false).equals(""))
/*       */     {
/*  2012 */       this.featureMatrixError.add("20<:>Charges<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Model Order Code");
/*       */     }
/*       */ 
/*       */     
/*  2016 */     if (isSelected(paramEntityItem2, "INSTALL", "5671")) {
/*       */       
/*  2018 */       str1 = str1 + "<:>Yes";
/*  2019 */       this.productNumber_NewModels_HT.put(str3, "Yes");
/*       */     }
/*  2021 */     else if (isSelected(paramEntityItem2, "INSTALL", "5672")) {
/*       */       
/*  2023 */       str1 = str1 + "<:>No ";
/*  2024 */       this.productNumber_NewModels_HT.put(str3, "No ");
/*       */     }
/*  2026 */     else if (isSelected(paramEntityItem2, "INSTALL", "5673")) {
/*       */       
/*  2028 */       str1 = str1 + "<:>N/A";
/*  2029 */       this.productNumber_NewModels_HT.put(str3, "N/A");
/*       */     }
/*       */     else {
/*       */       
/*  2033 */       str1 = str1 + "<:>   ";
/*  2034 */       this.productNumber_NewModels_HT.put(str3, "   ");
/*       */     } 
/*       */     
/*  2037 */     if (getAttributeValue(paramEntityItem2, "INSTALL", "", "", false).equals(""))
/*       */     {
/*  2039 */       this.featureMatrixError.add("20<:>Charges<:>" + str2 + "<:>" + str3 + "<:>MODEL<:>Customer Install");
/*       */     }
/*       */ 
/*       */     
/*  2043 */     String str4 = getGeo(str5);
/*       */     
/*  2045 */     addToTreeMap(str1, str4, this.charges_NewModels_TM);
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
/*  2069 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  2071 */     String str = "";
/*       */     
/*  2073 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  2074 */     while (iterator.hasNext()) {
/*       */       
/*  2076 */       EntityItem entityItem = iterator.next();
/*  2077 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  2078 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  2081 */         EntityItem entityItem1 = vector.get(b);
/*  2082 */         String str1 = entityItem1.getKey();
/*  2083 */         updateGeoHT(entityItem, str1);
/*       */         
/*  2085 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*  2086 */         if (null != eANEntity) {
/*       */ 
/*       */           
/*  2089 */           hashtable.clear();
/*  2090 */           hashtable.put("COFCAT", "100");
/*  2091 */           hashtable.put("COFSUBCAT", "126");
/*  2092 */           hashtable.put("COFGRP", "150");
/*  2093 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*  2095 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*  2096 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  2097 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  2104 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*  2105 */               String str7 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  2106 */               str7 = str7.trim();
/*  2107 */               str7 = setString(str7, 4);
/*       */               
/*  2109 */               String str2 = str7;
/*       */               
/*  2111 */               str = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  2112 */               if (null == str)
/*       */               {
/*  2114 */                 str = " ";
/*       */               }
/*  2116 */               str = str.trim();
/*  2117 */               str = setString(str, 4);
/*       */               
/*  2119 */               str2 = str2 + "<:>" + str;
/*  2120 */               String str3 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  2121 */               str3 = str3.trim();
/*  2122 */               if (str3.equals(""))
/*       */               {
/*  2124 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>MODEL<:>Machine Model");
/*       */               }
/*       */               
/*  2127 */               str3 = setString(str3, 3);
/*  2128 */               str2 = str2 + "<:>" + str3;
/*  2129 */               if (str7.equals("    "))
/*       */               {
/*  2131 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/*  2134 */               String str4 = getAttributeValue(entityItem1, "INVNAME", "", "", false);
/*  2135 */               str4 = str4.trim();
/*  2136 */               if (str4.equals("")) {
/*       */                 
/*  2138 */                 str4 = getAttributeValue(entityItem3, "INVNAME", "", "", false);
/*  2139 */                 str4 = str4.trim();
/*  2140 */                 if (str4.equals("")) {
/*       */                   
/*  2142 */                   str4 = getAttributeValue(entityItem3, "COMNAME", "", "", false);
/*  2143 */                   if (str4.length() > 28)
/*       */                   {
/*  2145 */                     str4 = str4.substring(0, 28);
/*       */                   }
/*  2147 */                   str4 = str4.trim();
/*  2148 */                   str4 = str4.toUpperCase();
/*       */                 } 
/*       */               } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  2156 */               str4 = setString(str4, 28);
/*  2157 */               str2 = str2 + "<:>" + str4;
/*       */ 
/*       */               
/*  2160 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  2162 */                 str2 = str2 + "<:>Both   ";
/*       */               }
/*  2164 */               else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  2166 */                 str2 = str2 + "<:>MES    ";
/*       */               }
/*  2168 */               else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  2170 */                 str2 = str2 + "<:>Initial";
/*       */               }
/*  2172 */               else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  2174 */                 str2 = str2 + "<:>Support";
/*       */               }
/*       */               else {
/*       */                 
/*  2178 */                 str2 = str2 + "<:>       ";
/*       */               } 
/*       */               
/*  2181 */               if (getAttributeValue(entityItem1, "ORDERCODE", "", "", false).equals(""))
/*       */               {
/*  2183 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */               }
/*       */ 
/*       */               
/*  2187 */               if (isSelected(entityItem1, "INSTALL", "5671")) {
/*       */                 
/*  2189 */                 str2 = str2 + "<:>Yes";
/*       */               }
/*  2191 */               else if (isSelected(entityItem1, "INSTALL", "5672")) {
/*       */                 
/*  2193 */                 str2 = str2 + "<:>No ";
/*       */               }
/*  2195 */               else if (isSelected(entityItem1, "INSTALL", "5673")) {
/*       */                 
/*  2197 */                 str2 = str2 + "<:>N/A";
/*       */               }
/*       */               else {
/*       */                 
/*  2201 */                 str2 = str2 + "<:>   ";
/*       */               } 
/*       */               
/*  2204 */               if (getAttributeValue(entityItem1, "INSTALL", "", "", false).equals(""))
/*       */               {
/*  2206 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Customer Install");
/*       */               }
/*       */               
/*  2209 */               if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */                 
/*  2211 */                 str2 = str2 + "<:>Yes";
/*       */               }
/*  2213 */               else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */                 
/*  2215 */                 str2 = str2 + "<:>No ";
/*       */               }
/*       */               else {
/*       */                 
/*  2219 */                 str2 = str2 + "<:>   ";
/*       */               } 
/*       */               
/*  2222 */               if (getAttributeValue(entityItem1, "RETURNEDPARTS", "", "", false).equals(""))
/*       */               {
/*  2224 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Returned Parts MES");
/*       */               }
/*       */               
/*  2227 */               str2 = str2 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */               
/*  2229 */               String str5 = getGeo(str1);
/*  2230 */               String str6 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/*       */               
/*  2232 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals(""))
/*       */               {
/*  2234 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str7 + "<:>Priced Feature Indicator");
/*       */               }
/*  2236 */               if (getAttributeValue(entityItem3, "ZEROPRICE", "", "", false).equals(""))
/*       */               {
/*  2238 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str7 + "<:>Zero Priced Indicator");
/*       */               }
/*       */               
/*  2241 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals("")) {
/*       */ 
/*       */                 
/*  2244 */                 str2 = str2 + "<:>        <:>    <:>    <:>    ";
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
/*  2266 */                 hashtable.clear();
/*  2267 */                 hashtable.put("PRICEDFEATURE", "100");
/*       */                 
/*  2269 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                   
/*  2277 */                   str2 = str2 + "<:> $XXXX  <:>    <:>    <:>    ";
/*       */                 }
/*  2279 */                 hashtable.clear();
/*  2280 */                 hashtable.put("PRICEDFEATURE", "120");
/*       */                 
/*  2282 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/*  2284 */                   str2 = str2 + "<:>    NC  <:>    <:>    <:>    ";
/*       */                 }
/*       */               } 
/*       */ 
/*       */               
/*  2289 */               if (this.annDate.equals(str6)) {
/*       */                 
/*  2291 */                 addToTreeMap(str2, str5, this.charges_NewFC_TM);
/*       */ 
/*       */               
/*       */               }
/*  2295 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/*  2297 */                 addToTreeMap(str2, str5, this.charges_NewFC_TM);
/*       */               }
/*       */               else {
/*       */                 
/*  2301 */                 addToTreeMap(str2, str5, this.charges_ExistingFC_TM);
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
/*       */   private void charges_FeatureCodes2() {
/*  2329 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  2330 */     String str = "";
/*  2331 */     boolean bool = true;
/*  2332 */     boolean bool1 = true;
/*       */     
/*  2334 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  2335 */     while (iterator.hasNext()) {
/*       */       
/*  2337 */       EntityItem entityItem = iterator.next();
/*  2338 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  2339 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  2342 */         EntityItem entityItem1 = vector.get(b);
/*  2343 */         String str1 = entityItem1.getKey();
/*  2344 */         updateGeoHT(entityItem, str1);
/*       */         
/*  2346 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*  2347 */         if (null != eANEntity) {
/*       */ 
/*       */           
/*  2350 */           hashtable.clear();
/*  2351 */           hashtable.put("COFCAT", "100");
/*  2352 */           hashtable.put("COFSUBCAT", "126");
/*  2353 */           hashtable.put("COFGRP", "150");
/*  2354 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*  2356 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*  2357 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  2358 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  2365 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*  2366 */               String str7 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  2367 */               if (null == str7)
/*       */               {
/*  2369 */                 str7 = " ";
/*       */               }
/*  2371 */               str7 = str7.trim();
/*  2372 */               str7 = setString(str7, 4);
/*  2373 */               str = str7;
/*  2374 */               String str2 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  2375 */               str2 = str2.trim();
/*       */               
/*  2377 */               str2 = setString(str2, 4);
/*  2378 */               str7 = str7 + "<:>" + str2;
/*  2379 */               String str3 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  2380 */               str3 = str3.trim();
/*  2381 */               if (str3.equals(""))
/*       */               {
/*  2383 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>MODEL<:>Machine Model");
/*       */               }
/*       */               
/*  2386 */               str3 = setString(str3, 3);
/*  2387 */               str7 = str7 + "<:>" + str3;
/*  2388 */               if (str2.equals("    "))
/*       */               {
/*  2390 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*       */               
/*  2393 */               bool1 = this.productNumber_NewModels_HT.containsKey(str3);
/*       */               
/*  2395 */               String str4 = getAttributeValue(entityItem3, "FIRSTANNDATE", "", "", false);
/*  2396 */               if (this.annDate.equals(str4)) {
/*       */                 
/*  2398 */                 bool = true;
/*       */ 
/*       */               
/*       */               }
/*  2402 */               else if (isNewFeature(entityItem3)) {
/*       */                 
/*  2404 */                 bool = true;
/*       */               }
/*       */               else {
/*       */                 
/*  2408 */                 bool = false;
/*       */               } 
/*       */ 
/*       */               
/*  2412 */               String str5 = getAttributeValue(entityItem1, "MKTGNAME", "", "", false);
/*  2413 */               str5 = str5.trim();
/*  2414 */               if (str5.equals("")) {
/*       */                 
/*  2416 */                 str5 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false);
/*  2417 */                 str5 = str5.trim();
/*  2418 */                 if (str5.equals("")) {
/*       */                   
/*  2420 */                   this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Marketing Name");
/*  2421 */                   this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str2 + "<:>Marketing Name");
/*       */                 } 
/*       */               } 
/*  2424 */               str7 = str7 + "<:>" + str5;
/*       */ 
/*       */               
/*  2427 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  2429 */                 str7 = str7 + "<:>Both   ";
/*       */               }
/*  2431 */               else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  2433 */                 str7 = str7 + "<:>MES    ";
/*       */               }
/*  2435 */               else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  2437 */                 str7 = str7 + "<:>Initial";
/*       */               }
/*  2439 */               else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  2441 */                 str7 = str7 + "<:>Support";
/*       */               }
/*       */               else {
/*       */                 
/*  2445 */                 str7 = str7 + "<:>       ";
/*       */               } 
/*       */               
/*  2448 */               if (getAttributeValue(entityItem1, "ORDERCODE", "", "", false).equals(""))
/*       */               {
/*  2450 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */               }
/*       */ 
/*       */               
/*  2454 */               if (isSelected(entityItem1, "INSTALL", "5671")) {
/*       */                 
/*  2456 */                 str7 = str7 + "<:>Yes";
/*       */               }
/*  2458 */               else if (isSelected(entityItem1, "INSTALL", "5672")) {
/*       */                 
/*  2460 */                 str7 = str7 + "<:>No ";
/*       */               }
/*  2462 */               else if (isSelected(entityItem1, "INSTALL", "5673")) {
/*       */                 
/*  2464 */                 str7 = str7 + "<:>N/A";
/*       */               }
/*       */               else {
/*       */                 
/*  2468 */                 str7 = str7 + "<:>   ";
/*       */               } 
/*       */               
/*  2471 */               if (getAttributeValue(entityItem1, "INSTALL", "", "", false).equals(""))
/*       */               {
/*  2473 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Customer Install");
/*       */               }
/*       */               
/*  2476 */               if (isSelected(entityItem1, "RETURNEDPARTS", "5100")) {
/*       */                 
/*  2478 */                 str7 = str7 + "<:>Yes";
/*       */               }
/*  2480 */               else if (isSelected(entityItem1, "RETURNEDPARTS", "5101")) {
/*       */                 
/*  2482 */                 str7 = str7 + "<:>No ";
/*       */               }
/*       */               else {
/*       */                 
/*  2486 */                 str7 = str7 + "<:>   ";
/*       */               } 
/*       */               
/*  2489 */               if (getAttributeValue(entityItem1, "RETURNEDPARTS", "", "", false).equals(""))
/*       */               {
/*  2491 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Returned Parts MES");
/*       */               }
/*       */               
/*  2494 */               str7 = str7 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */               
/*  2496 */               String str6 = getGeo(str1);
/*       */               
/*  2498 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals(""))
/*       */               {
/*  2500 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str2 + "<:>Priced Feature Indicator");
/*       */               }
/*  2502 */               if (getAttributeValue(entityItem3, "ZEROPRICE", "", "", false).equals(""))
/*       */               {
/*  2504 */                 this.featureMatrixError.add("20<:>Charges<:>" + str + "<:>" + str3 + "<:>FEATURE " + str2 + "<:>Zero Priced Indicator");
/*       */               }
/*       */               
/*  2507 */               if (getAttributeValue(entityItem3, "PRICEDFEATURE", "", "", false).equals("")) {
/*       */ 
/*       */                 
/*  2510 */                 str7 = str7 + "<:>       ";
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
/*  2532 */                 hashtable.clear();
/*  2533 */                 hashtable.put("PRICEDFEATURE", "100");
/*       */                 
/*  2535 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/*  2537 */                   str7 = str7 + "<:>XXXX.XX";
/*       */                 }
/*  2539 */                 hashtable.clear();
/*  2540 */                 hashtable.put("PRICEDFEATURE", "120");
/*       */                 
/*  2542 */                 if (isEntityWithMatchedAttr(entityItem3, hashtable))
/*       */                 {
/*  2544 */                   str7 = str7 + "<:>     NC";
/*       */                 }
/*       */               } 
/*       */ 
/*       */               
/*  2549 */               if (bool1 && bool) {
/*       */                 
/*  2551 */                 addToTreeMap(str7, str6, this.charges_NewModels_NewFC_TM);
/*       */               }
/*  2553 */               else if (bool1 && !bool) {
/*       */                 
/*  2555 */                 addToTreeMap(str7, str6, this.charges_NewModels_ExistingFC_TM);
/*       */               }
/*  2557 */               else if (!bool1 && bool) {
/*       */                 
/*  2559 */                 addToTreeMap(str7, str6, this.charges_ExistingModels_NewFC_TM);
/*       */               }
/*  2561 */               else if (!bool1 && !bool) {
/*       */                 
/*  2563 */                 addToTreeMap(str7, str6, this.charges_ExistingModels_ExistingFC_TM);
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
/*  2579 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  2581 */     hashtable.put("COFCAT", "100");
/*  2582 */     hashtable.put("COFSUBCAT", "126");
/*  2583 */     hashtable.put("COFGRP", "150");
/*       */ 
/*       */     
/*  2586 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  2587 */     while (iterator.hasNext()) {
/*       */       
/*  2589 */       EntityItem entityItem = iterator.next();
/*  2590 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  2591 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  2593 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*  2595 */         for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/*       */           
/*  2597 */           EANEntity eANEntity = entityItem1.getDownLink(b1);
/*  2598 */           if (eANEntity.getEntityType().equals("MODEL"))
/*       */           {
/*  2600 */             if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */               
/*  2602 */               EntityItem entityItem2 = (EntityItem)eANEntity;
/*  2603 */               for (byte b2 = 0; b2 < entityItem1.getUpLinkCount(); b2++) {
/*       */                 
/*  2605 */                 EANEntity eANEntity1 = entityItem1.getUpLink(b2);
/*  2606 */                 EntityItem entityItem3 = (EntityItem)eANEntity1;
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/*  2611 */                 populate_SalesManual_TM(entityItem, entityItem1, entityItem2, entityItem3);
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
/*       */   private void populate_SalesManual_TM(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, EntityItem paramEntityItem4) {
/*  2654 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  2655 */     String str9 = paramEntityItem4.getKey();
/*  2656 */     updateGeoHT(paramEntityItem1, str9);
/*       */     
/*  2658 */     String str1 = getAttributeFlagValue(paramEntityItem3, "MACHTYPEATR");
/*  2659 */     if (null == str1)
/*       */     {
/*  2661 */       str1 = " ";
/*       */     }
/*  2663 */     str1 = str1.trim();
/*  2664 */     str1 = setString(str1, 4);
/*  2665 */     String str2 = str1;
/*  2666 */     String str3 = getAttributeValue(paramEntityItem3, "MODELATR", "", "000", false);
/*  2667 */     str3 = str3.trim();
/*  2668 */     str3 = setString(str3, 3);
/*  2669 */     str1 = str1 + "<:>" + str3;
/*  2670 */     String str4 = getAttributeValue(paramEntityItem4, "FEATURECODE", "", "", false);
/*  2671 */     str4 = str4.trim();
/*  2672 */     if (str4.equals(""))
/*       */     {
/*  2674 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>FEATURE<:>Feature Code");
/*       */     }
/*  2676 */     str4 = setString(str4, 4);
/*  2677 */     str1 = str1 + "<:>" + str4;
/*       */     
/*  2679 */     String str5 = "";
/*  2680 */     String str6 = "";
/*  2681 */     if (1 == this.format) {
/*       */       
/*  2683 */       str5 = getAttributeValue(paramEntityItem4, "INVNAME", "", "", false).trim();
/*  2684 */       if (str5.equals("")) {
/*       */         
/*  2686 */         str5 = getAttributeValue(paramEntityItem4, "COMNAME", "", "", false);
/*  2687 */         if (str5.length() > 28)
/*       */         {
/*  2689 */           str5 = str5.substring(0, 28);
/*       */         }
/*  2691 */         str5 = str5.trim();
/*  2692 */         str5 = str5.toUpperCase();
/*       */       } 
/*       */ 
/*       */       
/*  2696 */       str1 = str1 + "<:>" + str5;
/*       */     }
/*  2698 */     else if (2 == this.format) {
/*       */       
/*  2700 */       str6 = getAttributeValue(paramEntityItem4, "MKTGNAME", "", "", false).trim();
/*  2701 */       if (str6.equals(""))
/*       */       {
/*  2703 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>FEATURE " + str4 + "<:>Marketing Name");
/*       */       }
/*  2705 */       str1 = str1 + "<:>" + str6;
/*       */     } 
/*       */     
/*  2708 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem4, "DESCRIPTION", "", "", false).trim();
/*       */     
/*  2710 */     if (getAttributeValue(paramEntityItem4, "DESCRIPTION", "", "", false).trim().equals(""))
/*       */     {
/*  2712 */       if (2 == this.format)
/*       */       {
/*  2714 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>FEATURE " + str4 + "<:>Description");
/*       */       }
/*       */     }
/*       */     
/*  2718 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "SYSTEMMIN", "", "", false).trim();
/*       */     
/*  2720 */     if (getAttributeValue(paramEntityItem2, "SYSTEMMIN", "", "", false).trim().equals(""))
/*       */     {
/*  2722 */       if (2 == this.format)
/*       */       {
/*  2724 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Minimum Required");
/*       */       }
/*       */     }
/*       */     
/*  2728 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "SYSTEMMAX", "", "", false).trim();
/*       */     
/*  2730 */     if (getAttributeValue(paramEntityItem2, "SYSTEMMAX", "", "", false).trim().equals(""))
/*       */     {
/*  2732 */       if (2 == this.format)
/*       */       {
/*  2734 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Maximum Allowed");
/*       */       }
/*       */     }
/*       */     
/*  2738 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "INITORDERMAX", "", "", false).trim();
/*       */     
/*  2740 */     if (getAttributeValue(paramEntityItem2, "INITORDERMAX", "", "", false).trim().equals(""))
/*       */     {
/*  2742 */       if (2 == this.format)
/*       */       {
/*  2744 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Initial Order Maximum");
/*       */       }
/*       */     }
/*       */     
/*  2748 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "OSLEVELCOMPLEMENT", ", ", "", false).trim();
/*       */     
/*  2750 */     if (getAttributeValue(paramEntityItem2, "OSLEVELCOMPLEMENT", "", "", false).trim().equals(""))
/*       */     {
/*  2752 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>OS Level Complement");
/*       */     }
/*       */ 
/*       */     
/*  2756 */     if (isSelected(paramEntityItem2, "ORDERCODE", "5955")) {
/*       */       
/*  2758 */       str1 = str1 + "<:>Both";
/*       */     }
/*  2760 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "5956")) {
/*       */       
/*  2762 */       str1 = str1 + "<:>MES";
/*       */     }
/*  2764 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "5957")) {
/*       */       
/*  2766 */       str1 = str1 + "<:>Initial";
/*       */     }
/*  2768 */     else if (isSelected(paramEntityItem2, "ORDERCODE", "5958")) {
/*       */       
/*  2770 */       str1 = str1 + "<:>Support";
/*       */     }
/*       */     else {
/*       */       
/*  2774 */       str1 = str1 + "<:>";
/*       */     } 
/*       */     
/*  2777 */     if (getAttributeValue(paramEntityItem2, "ORDERCODE", "", "", false).equals(""))
/*       */     {
/*  2779 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */     }
/*       */ 
/*       */     
/*  2783 */     if (isSelected(paramEntityItem2, "INSTALL", "5671")) {
/*       */       
/*  2785 */       str1 = str1 + "<:>Yes";
/*       */     }
/*  2787 */     else if (isSelected(paramEntityItem2, "INSTALL", "5672")) {
/*       */       
/*  2789 */       str1 = str1 + "<:>No ";
/*       */     }
/*  2791 */     else if (isSelected(paramEntityItem2, "INSTALL", "5673")) {
/*       */       
/*  2793 */       str1 = str1 + "<:>N/A";
/*       */     }
/*       */     else {
/*       */       
/*  2797 */       str1 = str1 + "<:>Does not apply";
/*       */     } 
/*       */     
/*  2800 */     if (getAttributeValue(paramEntityItem2, "INSTALL", "", "", false).equals(""))
/*       */     {
/*  2802 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Customer Install");
/*       */     }
/*       */ 
/*       */     
/*  2806 */     if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5100")) {
/*       */       
/*  2808 */       str1 = str1 + "<:>Yes";
/*       */     }
/*  2810 */     else if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5101")) {
/*       */       
/*  2812 */       str1 = str1 + "<:>No";
/*       */     }
/*  2814 */     else if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5102")) {
/*       */       
/*  2816 */       str1 = str1 + "<:>Does not apply";
/*       */     }
/*  2818 */     else if (isSelected(paramEntityItem2, "RETURNEDPARTS", "5103")) {
/*       */       
/*  2820 */       str1 = str1 + "<:>Feature conversion only";
/*       */     }
/*       */     else {
/*       */       
/*  2824 */       str1 = str1 + "<:>";
/*       */     } 
/*       */     
/*  2827 */     if (getAttributeValue(paramEntityItem2, "RETURNEDPARTS", "", "", false).equals(""))
/*       */     {
/*  2829 */       if (2 == this.format)
/*       */       {
/*  2831 */         this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>PRODSTRUCT " + getAttributeValue(paramEntityItem2, "COMNAME", "", "", false) + "<:>Returned Parts MES");
/*       */       }
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  2837 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem2, "EDITORNOTE", "", "", false).trim();
/*  2838 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem4, "EDITORNOTE", "", "", false).trim();
/*       */     
/*  2840 */     String str7 = getGeo(str9);
/*       */     
/*  2842 */     addToTreeMap(str1, str7, this.salesManual_TM);
/*       */     
/*  2844 */     str1 = getAttributeFlagValue(paramEntityItem3, "MACHTYPEATR");
/*  2845 */     if (null == str1)
/*       */     {
/*  2847 */       str1 = " ";
/*       */     }
/*  2849 */     str1 = str1.trim();
/*  2850 */     str1 = setString(str1, 4);
/*  2851 */     String str8 = getAttributeValue(paramEntityItem4, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
/*  2852 */     if (str8.equals("UNKNOWN HWFCCAT(FEATURE)"))
/*       */     {
/*  2854 */       this.featureMatrixError.add("30<:>Sales Manual<:>" + str2 + "<:>" + str3 + "<:>FEATURE " + str4 + "<:>HW Feature Category");
/*       */     }
/*  2856 */     str1 = str1 + "<:>" + str8;
/*  2857 */     str1 = str1 + "<:>" + str4;
/*  2858 */     if (1 == this.format) {
/*       */       
/*  2860 */       str1 = str1 + "<:>" + str5;
/*       */     }
/*  2862 */     else if (2 == this.format) {
/*       */       
/*  2864 */       str1 = str1 + "<:>" + str6;
/*       */     } 
/*  2866 */     str1 = str1 + "<:>" + getAttributeValue(paramEntityItem4, "EDITORNOTE", "", "", false).trim();
/*       */ 
/*       */ 
/*       */     
/*  2870 */     hashtable.clear();
/*  2871 */     hashtable.put("PRICEDFEATURE", "120");
/*  2872 */     hashtable.put("ZEROPRICE", "120");
/*  2873 */     if (isEntityWithMatchedAttr(paramEntityItem4, hashtable))
/*       */     {
/*  2875 */       addToTreeMap(str1, str7, this.salesManualSpecifyFeatures_TM);
/*       */     }
/*       */     
/*  2878 */     hashtable.clear();
/*  2879 */     hashtable.put("PRICEDFEATURE", "120");
/*  2880 */     if (!isEntityWithMatchedAttr(paramEntityItem4, hashtable))
/*       */     {
/*       */       
/*  2883 */       if (isSelected(paramEntityItem2, "ORDERCODE", "5957")) {
/*       */         
/*  2885 */         addToTreeMap(str1, str7, this.salesManualSpecialFeaturesInitialOrder_TM);
/*       */       }
/*  2887 */       else if (isSelected(paramEntityItem2, "ORDERCODE", "5955") || 
/*  2888 */         isSelected(paramEntityItem2, "ORDERCODE", "5956") || 
/*  2889 */         isSelected(paramEntityItem2, "ORDERCODE", "5958")) {
/*       */         
/*  2891 */         addToTreeMap(str1, str7, this.salesManualSpecialFeaturesOther_TM);
/*       */       } 
/*       */     }
/*  2894 */     hashtable.clear();
/*  2895 */     hashtable = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void supportedDevices() {
/*  2905 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  2907 */     hashtable.put("COFCAT", "100");
/*  2908 */     hashtable.put("COFSUBCAT", "126");
/*  2909 */     hashtable.put("COFGRP", "150");
/*       */     
/*  2911 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  2912 */     while (iterator.hasNext()) {
/*       */       
/*  2914 */       EntityItem entityItem = iterator.next();
/*  2915 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*  2916 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/*  2917 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  2919 */         EntityItem entityItem1 = vector.get(b);
/*  2920 */         Vector vector1 = getAllLinkedEntities(entityItem1, "DEVSUPPORT", "SUPPDEVICE");
/*  2921 */         populate_SupportedDevices_TM(entityItem, entityItem1, vector1);
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  2926 */     iterator = this.availVector.iterator();
/*  2927 */     while (iterator.hasNext()) {
/*       */       
/*  2929 */       EntityItem entityItem = iterator.next();
/*  2930 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  2931 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/*  2933 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/*  2935 */         for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/*       */           
/*  2937 */           EANEntity eANEntity = entityItem1.getDownLink(b1);
/*  2938 */           if (eANEntity.getEntityType().equals("MODEL"))
/*       */           {
/*  2940 */             if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */               
/*  2942 */               EntityItem entityItem2 = (EntityItem)eANEntity;
/*  2943 */               Vector vector1 = getAllLinkedEntities(entityItem2, "DEVSUPPORT", "SUPPDEVICE");
/*  2944 */               populate_SupportedDevices_TM(entityItem, entityItem2, vector1);
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
/*       */   private void populate_SupportedDevices_TM(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector<EntityItem> paramVector) {
/*  2970 */     for (byte b = 0; b < paramVector.size(); b++) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  2979 */       String str7 = paramEntityItem2.getKey();
/*  2980 */       updateGeoHT(paramEntityItem1, str7);
/*       */       
/*  2982 */       EntityItem entityItem = paramVector.get(b);
/*       */       
/*  2984 */       String str1 = getAttributeFlagValue(paramEntityItem2, "MACHTYPEATR");
/*  2985 */       if (null == str1)
/*       */       {
/*  2987 */         str1 = " ";
/*       */       }
/*  2989 */       str1 = str1.trim();
/*  2990 */       str1 = setString(str1, 4);
/*  2991 */       String str2 = str1;
/*       */       
/*  2993 */       str1 = str1 + "<:>" + getAttributeValue(entityItem, "FMGROUP", "", "", false).trim();
/*  2994 */       String str3 = getAttributeValue(entityItem, "MACHTYPESD", "", "0000", false);
/*  2995 */       str3 = str3.trim();
/*  2996 */       str3 = setString(str3, 4);
/*  2997 */       str1 = str1 + "<:>" + str3;
/*  2998 */       String str4 = getAttributeValue(entityItem, "MODELATR", "", "000", false);
/*  2999 */       str4 = str4.trim();
/*  3000 */       str4 = setString(str4, 3);
/*  3001 */       str1 = str1 + "<:>" + str4;
/*  3002 */       str1 = str1 + "<:>" + getAttributeValue(entityItem, "MKTGNAME", "", "", false).trim();
/*  3003 */       String str5 = getAttributeValue(paramEntityItem2, "MODELATR", "", "000", false);
/*  3004 */       str5 = str5.trim();
/*  3005 */       str5 = setString(str5, 3);
/*  3006 */       str1 = str1 + "<:>" + str5;
/*  3007 */       String str6 = getGeo(str7);
/*       */       
/*  3009 */       if (getAttributeValue(entityItem, "FMGROUP", "", "", false).trim().equals(""))
/*       */       {
/*  3011 */         this.featureMatrixError.add("40<:>External Machine Type (Support Devices)<:>" + str2 + "<:>" + str5 + "<:>SUPPDEVICE: " + str3 + "-" + str4 + "<:>FM Group");
/*       */       }
/*       */       
/*  3014 */       if (getAttributeValue(entityItem, "MKTGNAME", "", "", false).trim().equals(""))
/*       */       {
/*  3016 */         this.featureMatrixError.add("40<:>External Machine Type (Support Devices)<:>" + str2 + "<:>" + str5 + "<:>SUPPDEVICE: " + str3 + "-" + str4 + "<:>Marketing Name");
/*       */       }
/*       */       
/*  3019 */       addToTreeMap(str1, str6, this.supportedDevices_TM);
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
/*  3041 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */     
/*  3043 */     hashtable.put("COFCAT", "100");
/*  3044 */     hashtable.put("COFSUBCAT", "126");
/*  3045 */     hashtable.put("COFGRP", "150");
/*       */     
/*  3047 */     String str1 = "";
/*  3048 */     String str2 = "";
/*       */ 
/*       */     
/*  3051 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/*  3052 */     while (iterator.hasNext()) {
/*       */       
/*  3054 */       EntityItem entityItem = iterator.next();
/*  3055 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*  3056 */       vector = getEntitiesWithMatchedAttr(vector, hashtable);
/*  3057 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  3060 */         EntityItem entityItem1 = vector.get(b);
/*  3061 */         String str = entityItem1.getKey();
/*  3062 */         updateGeoHT(entityItem, str);
/*       */         
/*  3064 */         EANEntity eANEntity = getUpLinkEntityItem(entityItem1, "PRODSTRUCT");
/*  3065 */         if (null != eANEntity) {
/*       */           
/*  3067 */           EntityItem entityItem2 = (EntityItem)eANEntity;
/*  3068 */           EANEntity eANEntity1 = getUpLinkEntityItem(entityItem2, "FEATURE");
/*  3069 */           if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*  3075 */             EntityItem entityItem3 = (EntityItem)eANEntity1;
/*       */             
/*  3077 */             String str7 = getAttributeFlagValue(entityItem1, "MACHTYPEATR");
/*  3078 */             if (null == str7)
/*       */             {
/*  3080 */               str7 = " ";
/*       */             }
/*  3082 */             str7 = str7.trim();
/*  3083 */             str7 = setString(str7, 4);
/*  3084 */             String str3 = str7;
/*  3085 */             String str4 = getAttributeValue(entityItem1, "MODELATR", "", "000", false);
/*  3086 */             str4 = str4.trim();
/*  3087 */             str4 = setString(str4, 3);
/*       */             
/*  3089 */             str7 = str7 + "<:>" + str4;
/*  3090 */             String str5 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  3091 */             str5 = str5.trim();
/*  3092 */             if (str5.equals(""))
/*       */             {
/*  3094 */               this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>FEATURE<:>Feature Code");
/*       */             }
/*  3096 */             str5 = setString(str5, 4);
/*  3097 */             str7 = str7 + "<:>" + str5;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*  3103 */             if (isSelected(entityItem2, "ORDERCODE", "5955")) {
/*       */               
/*  3105 */               if (2 == this.format)
/*       */               {
/*  3107 */                 str7 = str7 + "<:>A";
/*       */               }
/*       */               else
/*       */               {
/*  3111 */                 str7 = str7 + "<:>B";
/*       */               }
/*       */             
/*  3114 */             } else if (isSelected(entityItem2, "ORDERCODE", "5956")) {
/*       */               
/*  3116 */               if (2 == this.format)
/*       */               {
/*  3118 */                 str7 = str7 + "<:>A";
/*       */               }
/*       */               else
/*       */               {
/*  3122 */                 str7 = str7 + "<:>M";
/*       */               }
/*       */             
/*  3125 */             } else if (isSelected(entityItem2, "ORDERCODE", "5957")) {
/*       */               
/*  3127 */               if (2 == this.format)
/*       */               {
/*  3129 */                 str7 = str7 + "<:>A";
/*       */               }
/*       */               else
/*       */               {
/*  3133 */                 str7 = str7 + "<:>I";
/*       */               }
/*       */             
/*  3136 */             } else if (isSelected(entityItem2, "ORDERCODE", "5958")) {
/*       */               
/*  3138 */               str7 = str7 + "<:>S";
/*       */             }
/*  3140 */             else if (isSelected(entityItem2, "ORDERCODE", "5959")) {
/*       */               
/*  3142 */               if (2 == this.format)
/*       */               {
/*  3144 */                 str7 = str7 + "<:>N";
/*       */               }
/*       */               else
/*       */               {
/*  3148 */                 str7 = str7 + "<:> ";
/*       */               }
/*       */             
/*       */             } else {
/*       */               
/*  3153 */               str7 = str7 + "<:> ";
/*       */             } 
/*       */             
/*  3156 */             if (getAttributeValue(entityItem2, "ORDERCODE", "", "", false).equals(""))
/*       */             {
/*  3158 */               this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>PRODSTRUCT " + getAttributeValue(entityItem2, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */             }
/*       */             
/*  3161 */             if (1 == this.format) {
/*       */               
/*  3163 */               str1 = getAttributeValue(entityItem3, "INVNAME", "", "", false).trim();
/*  3164 */               if (str1.equals("")) {
/*       */                 
/*  3166 */                 str1 = getAttributeValue(entityItem3, "COMNAME", "", "", false);
/*  3167 */                 if (str1.length() > 28)
/*       */                 {
/*  3169 */                   str1 = str1.substring(0, 28);
/*       */                 }
/*  3171 */                 str1 = str1.trim();
/*  3172 */                 str1 = str1.toUpperCase();
/*       */               } 
/*       */ 
/*       */               
/*  3176 */               str7 = str7 + "<:>" + str1;
/*       */             }
/*  3178 */             else if (2 == this.format) {
/*       */               
/*  3180 */               str2 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false).trim();
/*  3181 */               if (str2.equals(""))
/*       */               {
/*  3183 */                 this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>FEATURE " + str5 + "<:>Marketing Name");
/*       */               }
/*  3185 */               str7 = str7 + "<:>" + str2;
/*       */             } 
/*       */             
/*  3188 */             str7 = str7 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */             
/*  3190 */             String str6 = getGeo(str);
/*       */             
/*  3192 */             addToTreeMap(str7, str6, this.featureMatrix_TM);
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  3199 */     iterator = this.availVector.iterator();
/*  3200 */     while (iterator.hasNext()) {
/*       */       
/*  3202 */       EntityItem entityItem = iterator.next();
/*  3203 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  3204 */       for (byte b = 0; b < vector.size(); b++) {
/*       */ 
/*       */         
/*  3207 */         EntityItem entityItem1 = vector.get(b);
/*  3208 */         String str = entityItem1.getKey();
/*  3209 */         updateGeoHT(entityItem, str);
/*       */         
/*  3211 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/*  3212 */         if (null != eANEntity)
/*       */         {
/*  3214 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/*  3216 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/*  3217 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/*  3218 */             if (null != eANEntity1) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  3224 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/*       */               
/*  3226 */               String str7 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/*  3227 */               if (null == str7)
/*       */               {
/*  3229 */                 str7 = " ";
/*       */               }
/*  3231 */               str7 = str7.trim();
/*  3232 */               str7 = setString(str7, 4);
/*  3233 */               String str3 = str7;
/*  3234 */               String str4 = getAttributeValue(entityItem2, "MODELATR", "", "000", false);
/*  3235 */               str4 = str4.trim();
/*  3236 */               str4 = setString(str4, 3);
/*       */               
/*  3238 */               str7 = str7 + "<:>" + str4;
/*  3239 */               String str5 = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/*  3240 */               str5 = str5.trim();
/*  3241 */               if (str5.equals(""))
/*       */               {
/*  3243 */                 this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>FEATURE<:>Feature Code");
/*       */               }
/*  3245 */               str5 = setString(str5, 4);
/*  3246 */               str7 = str7 + "<:>" + str5;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               
/*  3252 */               if (isSelected(entityItem1, "ORDERCODE", "5955")) {
/*       */                 
/*  3254 */                 if (2 == this.format)
/*       */                 {
/*  3256 */                   str7 = str7 + "<:>A";
/*       */                 }
/*       */                 else
/*       */                 {
/*  3260 */                   str7 = str7 + "<:>B";
/*       */                 }
/*       */               
/*  3263 */               } else if (isSelected(entityItem1, "ORDERCODE", "5956")) {
/*       */                 
/*  3265 */                 if (2 == this.format)
/*       */                 {
/*  3267 */                   str7 = str7 + "<:>A";
/*       */                 }
/*       */                 else
/*       */                 {
/*  3271 */                   str7 = str7 + "<:>M";
/*       */                 }
/*       */               
/*  3274 */               } else if (isSelected(entityItem1, "ORDERCODE", "5957")) {
/*       */                 
/*  3276 */                 if (2 == this.format)
/*       */                 {
/*  3278 */                   str7 = str7 + "<:>A";
/*       */                 }
/*       */                 else
/*       */                 {
/*  3282 */                   str7 = str7 + "<:>I";
/*       */                 }
/*       */               
/*  3285 */               } else if (isSelected(entityItem1, "ORDERCODE", "5958")) {
/*       */                 
/*  3287 */                 str7 = str7 + "<:>S";
/*       */               }
/*  3289 */               else if (isSelected(entityItem1, "ORDERCODE", "5959")) {
/*       */                 
/*  3291 */                 if (2 == this.format)
/*       */                 {
/*  3293 */                   str7 = str7 + "<:>N";
/*       */                 }
/*       */                 else
/*       */                 {
/*  3297 */                   str7 = str7 + "<:> ";
/*       */                 }
/*       */               
/*       */               } else {
/*       */                 
/*  3302 */                 str7 = str7 + "<:> ";
/*       */               } 
/*       */               
/*  3305 */               if (getAttributeValue(entityItem1, "ORDERCODE", "", "", false).equals(""))
/*       */               {
/*  3307 */                 this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>PRODSTRUCT " + getAttributeValue(entityItem1, "COMNAME", "", "", false) + "<:>Original Order Code");
/*       */               }
/*       */               
/*  3310 */               if (1 == this.format) {
/*       */                 
/*  3312 */                 str1 = getAttributeValue(entityItem3, "INVNAME", "", "", false).trim();
/*  3313 */                 if (str1.equals("")) {
/*       */                   
/*  3315 */                   str1 = getAttributeValue(entityItem3, "COMNAME", "", "", false);
/*  3316 */                   if (str1.length() > 28)
/*       */                   {
/*  3318 */                     str1 = str1.substring(0, 28);
/*       */                   }
/*  3320 */                   str1 = str1.trim();
/*  3321 */                   str1 = str1.toUpperCase();
/*       */                 } 
/*       */ 
/*       */                 
/*  3325 */                 str7 = str7 + "<:>" + str1;
/*       */               }
/*  3327 */               else if (2 == this.format) {
/*       */                 
/*  3329 */                 str2 = getAttributeValue(entityItem3, "MKTGNAME", "", "", false).trim();
/*  3330 */                 if (str2.equals(""))
/*       */                 {
/*  3332 */                   this.featureMatrixError.add("50<:>Feature Matrix<:>" + str3 + "<:>" + str4 + "<:>FEATURE " + str5 + "<:>Marketing Name");
/*       */                 }
/*  3334 */                 str7 = str7 + "<:>" + str2;
/*       */               } 
/*       */               
/*  3337 */               str7 = str7 + "<:>" + getAttributeValue(entityItem3, "EDITORNOTE", "", "", false).trim();
/*       */               
/*  3339 */               String str6 = getGeo(str);
/*       */               
/*  3341 */               addToTreeMap(str7, str6, this.featureMatrix_TM);
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
/*       */   public void retrieveAnswer(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  3358 */     paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:PRODNUM.txt: FOR :Product Number-->" + NEWLINE);
/*  3359 */     retrieveProductNumber(paramBoolean, paramStringBuffer);
/*  3360 */     paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:FEATCONV.txt: FOR :Conversions-->" + NEWLINE);
/*  3361 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  3362 */     retrievePNMTMConversions(paramBoolean, paramStringBuffer);
/*  3363 */     retrievePNModelConversions(paramBoolean, paramStringBuffer);
/*  3364 */     if (1 == this.format) {
/*       */       
/*  3366 */       if (this.annType.equals("new"))
/*       */       {
/*  3368 */         retrievePNFeatureConversionsFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*  3370 */       else if (this.annType.equals("withdraw"))
/*       */       {
/*  3372 */         retrievePNFeatureConversionsForWithdrawFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*       */     
/*  3375 */     } else if (2 == this.format) {
/*       */       
/*  3377 */       if (this.annType.equals("new")) {
/*       */         
/*  3379 */         retrievePNFeatureConversionsFormat2(paramBoolean, paramStringBuffer);
/*       */       }
/*  3381 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*  3383 */         retrievePNFeatureConversionsForWithdrawFormat2(paramBoolean, paramStringBuffer);
/*       */       } 
/*       */     } 
/*  3386 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
/*       */     
/*  3388 */     if (this.annType.equals("new")) {
/*       */ 
/*       */       
/*  3391 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:CHARGES.txt: FOR :Charges-->" + NEWLINE);
/*  3392 */       retrieveCharges(paramBoolean, paramStringBuffer);
/*       */       
/*  3394 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:SALESMAN.txt: FOR :Sales Manual-->" + NEWLINE);
/*  3395 */       retrieveSalesManual(paramBoolean, paramStringBuffer);
/*       */       
/*  3397 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:EXTDEVICE.txt: FOR :Supported Devices-->" + NEWLINE);
/*  3398 */       retrieveSupportedDevices(paramBoolean, paramStringBuffer);
/*       */       
/*  3400 */       paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:MATRIX.txt: FOR :Feature Matrix-->" + NEWLINE);
/*  3401 */       retrieveFeatureMatrix(paramBoolean, paramStringBuffer);
/*       */     } 
/*       */     
/*  3404 */     paramStringBuffer.append(".* <!--STARTFILEBREAKFORMAIL:RFAERROR.txt: FOR :Matrix RFA Error-->" + NEWLINE);
/*  3405 */     retrieveFeatureMatrixError(paramStringBuffer);
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
/*  3416 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  3417 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  3418 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */ 
/*       */ 
/*       */     
/*  3422 */     if (this.productNumber_NewModels_TM.size() + this.productNumber_NewFC_TM.size() + this.productNumber_ExistingFC_TM
/*  3423 */       .size() + this.productNumber_MTM_Conversions_TM.size() + this.productNumber_Model_Conversions_TM
/*  3424 */       .size() + this.productNumber_Feature_Conversions_TM.size() + this.productNumber_NewModels_NewFC_TM
/*  3425 */       .size() + this.productNumber_NewModels_ExistingFC_TM.size() + this.productNumber_ExistingModels_NewFC_TM
/*  3426 */       .size() + this.productNumber_ExistingModels_ExistingFC_TM.size() > 0) {
/*       */       
/*  3428 */       log("annType = " + this.annType);
/*  3429 */       log("productNumber_NewModels_TM.size() = " + this.productNumber_NewModels_TM.size());
/*  3430 */       log("productNumber_NewFC_TM.size() = " + this.productNumber_NewFC_TM.size());
/*  3431 */       log("productNumber_ExistingFC_TM.size() = " + this.productNumber_ExistingFC_TM.size());
/*  3432 */       log("productNumber_NewModels_NewFC_TM.size() = " + this.productNumber_NewModels_NewFC_TM.size());
/*  3433 */       log("productNumber_NewModels_ExistingFC_TM.size() = " + this.productNumber_NewModels_ExistingFC_TM.size());
/*  3434 */       log("productNumber_ExistingModels_NewFC_TM.size() = " + this.productNumber_ExistingModels_NewFC_TM.size());
/*  3435 */       log("productNumber_ExistingModels_ExistingFC_TM.size() = " + this.productNumber_ExistingModels_ExistingFC_TM.size());
/*       */       
/*  3437 */       if (1 == this.format) {
/*       */         
/*  3439 */         retrievePNNewModelsFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*  3441 */       else if (2 == this.format) {
/*       */         
/*  3443 */         retrievePNNewModelsFormat2(paramBoolean, paramStringBuffer);
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  3452 */       if (this.annType.equals("withdraw"))
/*       */       {
/*  3454 */         if (1 == this.format)
/*       */         {
/*  3456 */           retrievePNFeaturesWithdrawFormat1(paramBoolean, paramStringBuffer);
/*       */         }
/*  3458 */         else if (2 == this.format)
/*       */         {
/*  3460 */           retrievePNFeaturesWithdrawFormat2(paramBoolean, paramStringBuffer);
/*       */         
/*       */         }
/*       */       
/*       */       }
/*  3465 */       else if (1 == this.format)
/*       */       {
/*  3467 */         retrievePNFeaturesFormat1(1, this.productNumber_NewFC_TM, paramBoolean, paramStringBuffer);
/*  3468 */         retrievePNFeaturesFormat1(2, this.productNumber_ExistingFC_TM, paramBoolean, paramStringBuffer);
/*       */       }
/*  3470 */       else if (2 == this.format)
/*       */       {
/*  3472 */         retrievePNFeaturesFormat2(1, 1, this.productNumber_NewModels_NewFC_TM, paramBoolean, paramStringBuffer);
/*  3473 */         retrievePNFeaturesFormat2(1, 2, this.productNumber_NewModels_ExistingFC_TM, paramBoolean, paramStringBuffer);
/*  3474 */         retrievePNFeaturesFormat2(2, 1, this.productNumber_ExistingModels_NewFC_TM, paramBoolean, paramStringBuffer);
/*  3475 */         retrievePNFeaturesFormat2(2, 2, this.productNumber_ExistingModels_ExistingFC_TM, paramBoolean, paramStringBuffer);
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/*  3481 */     else if (paramBoolean == true) {
/*       */       
/*  3483 */       paramStringBuffer.append(":p.No answer data found for Product Number section." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  3487 */       paramStringBuffer.append("<p>No answer data found for Product Number section.</p>" + NEWLINE);
/*       */     } 
/*       */     
/*  3490 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
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
/*  3501 */     if (this.productNumber_NewModels_TM.size() > 0) {
/*       */       
/*  3503 */       String str1 = "";
/*  3504 */       String str2 = "";
/*       */       
/*  3506 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  3510 */         if (this.annType.equals("new")) {
/*       */           
/*  3512 */           paramStringBuffer.append(":h3.Models" + NEWLINE);
/*  3513 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  3514 */           paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*  3515 */           paramStringBuffer.append("                              Type         Model" + NEWLINE);
/*  3516 */           paramStringBuffer.append("Description                   Number       Number       CSU" + NEWLINE);
/*  3517 */           paramStringBuffer.append("----------------------------  ------       ------       ---" + NEWLINE);
/*       */         }
/*  3519 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  3521 */           paramStringBuffer.append(":h3.Model Withdrawals" + NEWLINE);
/*  3522 */           paramStringBuffer.append(":p.The following Machine Type Models are being withdrawn:" + NEWLINE);
/*  3523 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  3524 */           paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*  3525 */           paramStringBuffer.append("                              Type         Model" + NEWLINE);
/*  3526 */           paramStringBuffer.append("Description                   Number       Number" + NEWLINE);
/*  3527 */           paramStringBuffer.append("----------------------------  ------       ------" + NEWLINE);
/*       */         } 
/*       */         
/*  3530 */         Set set = this.productNumber_NewModels_TM.keySet();
/*  3531 */         Iterator<String> iterator = set.iterator();
/*  3532 */         while (iterator.hasNext()) {
/*       */           
/*  3534 */           String str = iterator.next();
/*  3535 */           str2 = (String)this.productNumber_NewModels_TM.get(str);
/*  3536 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  3538 */           paramStringBuffer.append(parseString(str, 3));
/*  3539 */           paramStringBuffer.append("   ");
/*  3540 */           paramStringBuffer.append(parseString(str, 1));
/*  3541 */           paramStringBuffer.append("         ");
/*  3542 */           paramStringBuffer.append(parseString(str, 2));
/*  3543 */           if (this.annType.equals("new")) {
/*       */             
/*  3545 */             paramStringBuffer.append("         ");
/*  3546 */             paramStringBuffer.append(parseString(str, 4));
/*       */           } 
/*  3548 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  3550 */           str1 = str2;
/*       */         } 
/*  3552 */         if (!str2.equals("WW"))
/*       */         {
/*  3554 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*  3556 */         if (paramBoolean == true)
/*       */         {
/*  3558 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  3572 */     String str1 = "";
/*  3573 */     String str2 = "";
/*       */     
/*  3575 */     if (this.productNumber_NewModels_TM.size() > 0)
/*       */     {
/*  3577 */       if (2 == this.format && this.annType.equals("withdraw")) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  3584 */         if (this.brand.equals("pSeries")) {
/*       */           
/*  3586 */           paramStringBuffer.append(":p.The following RS/6000 or pSeries Machine Type Models are being withdrawn:" + NEWLINE);
/*       */         }
/*  3588 */         else if (this.brand.equals("xSeries")) {
/*       */           
/*  3590 */           paramStringBuffer.append(":p.The following xSeries Machine Type Models are being withdrawn:" + NEWLINE);
/*       */ 
/*       */         
/*       */         }
/*  3594 */         else if (this.brand.equals("totalStorage")) {
/*       */           
/*  3596 */           paramStringBuffer.append(":p.The following Total Storage Machine Type Models are being withdrawn:" + NEWLINE);
/*       */         } 
/*       */         
/*  3599 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/*  3601 */         paramStringBuffer.append("Description                                            MT  Model" + NEWLINE);
/*  3602 */         paramStringBuffer.append("----------------------------------------------------- ---- -----" + NEWLINE);
/*  3603 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*  3604 */         boolean bool1 = false;
/*  3605 */         boolean bool2 = false;
/*  3606 */         Set set = this.productNumber_NewModels_TM.keySet();
/*  3607 */         Iterator<String> iterator = set.iterator();
/*  3608 */         while (iterator.hasNext()) {
/*       */           
/*  3610 */           String str = iterator.next();
/*  3611 */           str2 = (String)this.productNumber_NewModels_TM.get(str);
/*       */           
/*  3613 */           bool1 = false;
/*  3614 */           bool2 = false;
/*  3615 */           setGeoTags2(str1, str2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  3650 */           if (this.brand.equals("pSeries")) {
/*       */             
/*  3652 */             paramStringBuffer.append("RS/6000 or pSeries");
/*       */           }
/*  3654 */           else if (this.brand.equals("totalStorage")) {
/*       */             
/*  3656 */             paramStringBuffer.append("Total Storage     ");
/*       */           }
/*  3658 */           else if (this.brand.equals("xSeries")) {
/*       */             
/*  3660 */             paramStringBuffer.append("xSeries           ");
/*       */           } 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  3666 */           paramStringBuffer.append(setString("", 35));
/*  3667 */           paramStringBuffer.append(" ");
/*  3668 */           paramStringBuffer.append(parseString(str, 1));
/*  3669 */           paramStringBuffer.append("-");
/*  3670 */           paramStringBuffer.append(parseString(str, 2));
/*  3671 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  3673 */           str1 = str2;
/*       */         } 
/*  3675 */         if (!str2.equals("WW"))
/*       */         {
/*  3677 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*  3679 */         if (paramBoolean == true)
/*       */         {
/*  3681 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  3697 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */ 
/*       */     
/*  3700 */     Set<String> set = paramTreeMap.keySet();
/*  3701 */     Iterator<String> iterator = set.iterator();
/*  3702 */     while (iterator.hasNext()) {
/*       */       
/*  3704 */       String str1 = iterator.next();
/*       */ 
/*       */       
/*  3707 */       String str2 = parseString(str1, 2) + "<:>" + parseString(str1, 1) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 5) + "<:>" + parseString(str1, 6) + "<:>" + parseString(str1, 7) + "<:>" + parseString(str1, 8);
/*  3708 */       treeMap.put(str2, paramTreeMap.get(str1));
/*       */     } 
/*       */     
/*  3711 */     if (treeMap.size() > 0) {
/*       */       
/*  3713 */       String str1 = "";
/*  3714 */       String str2 = "";
/*  3715 */       String str3 = "";
/*  3716 */       String str4 = "";
/*  3717 */       String str5 = "";
/*  3718 */       String str6 = "";
/*       */       
/*  3720 */       switch (paramInt) {
/*       */         
/*       */         case 1:
/*  3723 */           paramStringBuffer.append(":h3.New Features" + NEWLINE);
/*       */ 
/*       */           
/*  3726 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  3727 */           paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */           break;
/*       */         
/*       */         case 2:
/*  3731 */           paramStringBuffer.append(":h3.Existing Features" + NEWLINE);
/*       */ 
/*       */           
/*  3734 */           paramStringBuffer.append(":xmp." + NEWLINE);
/*  3735 */           paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */           break;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  3742 */       if (1 == this.format) {
/*       */         
/*  3744 */         paramStringBuffer.append("                                                    Initial/" + NEWLINE);
/*  3745 */         paramStringBuffer.append("                                                    MES/" + NEWLINE);
/*  3746 */         paramStringBuffer.append("                                                    Both/" + NEWLINE);
/*  3747 */         paramStringBuffer.append("Description                   Type  Model  Feature  Support  CSU" + NEWLINE);
/*  3748 */         paramStringBuffer.append("----------------------------  ----  -----  -------  -------  ---" + NEWLINE);
/*  3749 */         set = treeMap.keySet();
/*  3750 */         iterator = set.iterator();
/*  3751 */         while (iterator.hasNext()) {
/*       */           
/*  3753 */           String str = iterator.next();
/*  3754 */           str2 = (String)treeMap.get(str);
/*  3755 */           str4 = parseString(str, 1);
/*  3756 */           str6 = parseString(str, 2);
/*  3757 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  3758 */           if (parseString(str, 8).length() > 0)
/*       */           {
/*  3760 */             if (!str4.equals(str3)) {
/*       */               
/*  3762 */               String[] arrayOfString = getStringTokens(parseString(str, 8), NEWLINE);
/*  3763 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*       */                 
/*  3765 */                 if (arrayOfString[b].length() > 58) {
/*       */                   
/*  3767 */                   String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  3768 */                   for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                   {
/*  3770 */                     paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                   }
/*       */                 }
/*       */                 else {
/*       */                   
/*  3775 */                   paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
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
/*  3788 */           paramStringBuffer.append(parseString(str, 4));
/*       */           
/*  3790 */           paramStringBuffer.append("  ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  3800 */           paramStringBuffer.append(parseString(str, 2));
/*       */           
/*  3802 */           paramStringBuffer.append("   ");
/*  3803 */           paramStringBuffer.append(parseString(str, 3));
/*  3804 */           paramStringBuffer.append("     ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  3814 */           paramStringBuffer.append(parseString(str, 1));
/*       */           
/*  3816 */           paramStringBuffer.append("   ");
/*  3817 */           paramStringBuffer.append(parseString(str, 5));
/*  3818 */           paramStringBuffer.append("  ");
/*  3819 */           paramStringBuffer.append(parseString(str, 6));
/*  3820 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  3822 */           str1 = str2;
/*  3823 */           str3 = str4;
/*  3824 */           str5 = str6;
/*       */         } 
/*  3826 */         if (!str2.equals("WW"))
/*       */         {
/*  3828 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */         
/*  3831 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  3847 */       treeMap.clear();
/*  3848 */       treeMap = null;
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
/*  4119 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */ 
/*       */     
/*  4122 */     Set<String> set = paramTreeMap.keySet();
/*  4123 */     Iterator<String> iterator = set.iterator();
/*  4124 */     while (iterator.hasNext()) {
/*       */       
/*  4126 */       String str1 = iterator.next();
/*       */ 
/*       */       
/*  4129 */       String str2 = parseString(str1, 2) + "<:>" + parseString(str1, 1) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 5) + "<:>" + parseString(str1, 6) + "<:>" + parseString(str1, 7) + "<:>" + parseString(str1, 8);
/*  4130 */       treeMap.put(str2, paramTreeMap.get(str1));
/*       */     } 
/*       */     
/*  4133 */     if (treeMap.size() > 0) {
/*       */       String[] arrayOfString1; byte b;
/*  4135 */       String str1 = "";
/*  4136 */       String str2 = "";
/*  4137 */       String str3 = "";
/*  4138 */       String str4 = "";
/*  4139 */       String str5 = "";
/*  4140 */       String str6 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  4148 */       TreeSet<String> treeSet1 = new TreeSet();
/*  4149 */       TreeSet<String> treeSet2 = new TreeSet();
/*  4150 */       Set set1 = treeMap.keySet();
/*  4151 */       Iterator<String> iterator1 = set1.iterator();
/*  4152 */       while (iterator1.hasNext()) {
/*       */         
/*  4154 */         String str9 = iterator1.next();
/*  4155 */         String str10 = parseString(str9, 2);
/*  4156 */         String str11 = parseString(str9, 3);
/*  4157 */         treeSet1.add(str10);
/*  4158 */         treeSet2.add(str10 + "<:>" + str11);
/*       */       } 
/*       */       
/*  4161 */       String str7 = "";
/*  4162 */       iterator1 = treeSet1.iterator();
/*  4163 */       while (iterator1.hasNext())
/*       */       {
/*  4165 */         str7 = str7 + (String)iterator1.next() + ", ";
/*       */       }
/*  4167 */       if (str7.length() > 1)
/*       */       {
/*  4169 */         str7 = str7.substring(0, str7.length() - 2);
/*       */       }
/*       */       
/*  4172 */       String str8 = "";
/*  4173 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  4175 */         if (1 == paramInt2)
/*       */         {
/*  4177 */           str8 = "of the IBM RS/6000 or pSeries " + str7 + " machine type:";
/*       */         }
/*  4179 */         else if (2 == paramInt2)
/*       */         {
/*  4181 */           str8 = "the " + str7 + " machine type:";
/*       */         }
/*       */       
/*  4184 */       } else if (this.brand.equals("xSeries")) {
/*       */         
/*  4186 */         if (1 == paramInt2)
/*       */         {
/*  4188 */           str8 = "of the IBM xSeries " + str7 + " machine type:";
/*       */         }
/*  4190 */         else if (2 == paramInt2)
/*       */         {
/*  4192 */           str8 = "the " + str7 + " machine type:";
/*       */ 
/*       */ 
/*       */         
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*  4202 */       else if (this.brand.equals("totalStorage")) {
/*       */         
/*  4204 */         if (1 == paramInt2) {
/*       */           
/*  4206 */           str8 = "of the Total Storage " + str7 + " machine type:";
/*       */         }
/*  4208 */         else if (2 == paramInt2) {
/*       */           
/*  4210 */           str8 = "the " + str7 + " machine type:";
/*       */         } 
/*       */       } 
/*       */       
/*  4214 */       switch (paramInt2) {
/*       */         
/*       */         case 1:
/*  4217 */           str8 = ":p.The following are newly announced features on the specified models " + str8;
/*  4218 */           arrayOfString1 = extractStringLines(str8, 70);
/*  4219 */           for (b = 0; b < arrayOfString1.length; b++)
/*       */           {
/*  4221 */             paramStringBuffer.append(arrayOfString1[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */         
/*       */         case 2:
/*  4226 */           str8 = ":p.The following are features already announced for " + str8;
/*  4227 */           arrayOfString1 = extractStringLines(str8, 70);
/*  4228 */           for (b = 0; b < arrayOfString1.length; b++)
/*       */           {
/*  4230 */             paramStringBuffer.append(arrayOfString1[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  4238 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/*  4239 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*       */       
/*  4241 */       paramStringBuffer.append("Description                                         MT  Model Feature" + NEWLINE);
/*  4242 */       paramStringBuffer.append("-------------------------------------------------- ---- ----- -------" + NEWLINE);
/*  4243 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  4244 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */       
/*  4246 */       if (1 == paramInt1)
/*       */       {
/*  4248 */         if (treeSet2.size() > 0) {
/*       */           
/*  4250 */           Iterator<String> iterator2 = treeSet2.iterator();
/*  4251 */           paramStringBuffer.append(".pa" + NEWLINE);
/*       */           
/*  4253 */           while (iterator2.hasNext()) {
/*       */             
/*  4255 */             String str = iterator2.next();
/*       */             
/*  4257 */             if (this.brand.equals("pSeries")) {
/*       */               
/*  4259 */               paramStringBuffer.append("RS/6000 or pSeries");
/*  4260 */               paramStringBuffer.append(setString("", 32));
/*       */             }
/*  4262 */             else if (this.brand.equals("xSeries")) {
/*       */               
/*  4264 */               paramStringBuffer.append("xSeries           ");
/*  4265 */               paramStringBuffer.append(setString("", 32));
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*       */             }
/*  4272 */             else if (this.brand.equals("totalStorage")) {
/*       */               
/*  4274 */               paramStringBuffer.append("Total Storage     ");
/*  4275 */               paramStringBuffer.append(setString("", 32));
/*       */             } 
/*       */             
/*  4278 */             paramStringBuffer.append(" ");
/*  4279 */             paramStringBuffer.append(parseString(str, 1));
/*  4280 */             paramStringBuffer.append("  ");
/*  4281 */             paramStringBuffer.append(parseString(str, 2));
/*  4282 */             paramStringBuffer.append(NEWLINE);
/*       */           } 
/*       */         } 
/*       */       }
/*       */ 
/*       */       
/*  4288 */       String[] arrayOfString2 = null;
/*  4289 */       boolean bool1 = false;
/*  4290 */       boolean bool2 = false;
/*  4291 */       set = treeMap.keySet();
/*  4292 */       iterator = set.iterator();
/*  4293 */       while (iterator.hasNext()) {
/*       */         
/*  4295 */         String str = iterator.next();
/*  4296 */         str2 = (String)treeMap.get(str);
/*  4297 */         str4 = parseString(str, 1);
/*  4298 */         str6 = parseString(str, 2);
/*  4299 */         if (!str4.equals(str3) || !str6.equals(str5)) {
/*       */           
/*  4301 */           arrayOfString2 = extractStringLines(parseString(str, 4), 50);
/*  4302 */           bool2 = true;
/*       */         } 
/*  4304 */         setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */         
/*  4306 */         if (parseString(str, 8).length() > 0)
/*       */         {
/*  4308 */           if (!str4.equals(str3)) {
/*       */             
/*  4310 */             String[] arrayOfString = getStringTokens(parseString(str, 8), NEWLINE);
/*  4311 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */               
/*  4313 */               if (arrayOfString[b1].length() > 58) {
/*       */                 
/*  4315 */                 String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  4316 */                 for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */                 {
/*  4318 */                   paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  4323 */                 paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         }
/*       */         
/*  4329 */         if (bool2) {
/*       */           
/*  4331 */           if (1 == arrayOfString2.length) {
/*       */             
/*  4333 */             paramStringBuffer.append(setString(arrayOfString2[0], 50));
/*  4334 */             paramStringBuffer.append(" ");
/*       */           }
/*       */           else {
/*       */             
/*  4338 */             for (byte b1 = 0; b1 < arrayOfString2.length; b1++) {
/*       */               
/*  4340 */               paramStringBuffer.append(setString(arrayOfString2[b1], 50));
/*  4341 */               if (b1 < arrayOfString2.length - 1)
/*       */               {
/*  4343 */                 paramStringBuffer.append(NEWLINE);
/*       */               }
/*       */             } 
/*  4346 */             paramStringBuffer.append(" ");
/*       */           } 
/*  4348 */           bool2 = false;
/*       */         }
/*       */         else {
/*       */           
/*  4352 */           paramStringBuffer.append(setString("", 51));
/*       */         } 
/*       */         
/*  4355 */         if (!str6.equals(str5) || !str4.equals(str3)) {
/*       */           
/*  4357 */           paramStringBuffer.append(parseString(str, 2));
/*  4358 */           paramStringBuffer.append("  ");
/*       */         }
/*       */         else {
/*       */           
/*  4362 */           paramStringBuffer.append(setString("", 6));
/*       */         } 
/*  4364 */         paramStringBuffer.append(parseString(str, 3));
/*  4365 */         paramStringBuffer.append("     ");
/*  4366 */         if (!str4.equals(str3)) {
/*       */           
/*  4368 */           paramStringBuffer.append(parseString(str, 1));
/*  4369 */           paramStringBuffer.append(NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/*  4373 */           paramStringBuffer.append(NEWLINE);
/*       */         } 
/*       */         
/*  4376 */         str1 = str2;
/*  4377 */         str3 = str4;
/*  4378 */         str5 = str6;
/*       */       } 
/*  4380 */       if (!str2.equals("WW"))
/*       */       {
/*  4382 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/*  4385 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  4386 */       paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */       
/*  4388 */       treeMap.clear();
/*  4389 */       treeMap = null;
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
/*  4530 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */ 
/*       */     
/*  4533 */     Set<String> set = this.productNumber_NewFC_TM.keySet();
/*  4534 */     Iterator<String> iterator = set.iterator();
/*  4535 */     while (iterator.hasNext()) {
/*       */       
/*  4537 */       String str1 = iterator.next();
/*       */       
/*  4539 */       String str2 = parseString(str1, 2) + "<:>" + parseString(str1, 1) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 8) + "<:>" + parseString(str1, 9);
/*  4540 */       treeMap.put(str2, this.productNumber_NewFC_TM.get(str1));
/*       */     } 
/*       */     
/*  4543 */     set = this.productNumber_ExistingFC_TM.keySet();
/*  4544 */     iterator = set.iterator();
/*  4545 */     while (iterator.hasNext()) {
/*       */       
/*  4547 */       String str1 = iterator.next();
/*       */       
/*  4549 */       String str2 = parseString(str1, 2) + "<:>" + parseString(str1, 1) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 8) + "<:>" + parseString(str1, 9);
/*  4550 */       treeMap.put(str2, this.productNumber_ExistingFC_TM.get(str1));
/*       */     } 
/*       */     
/*  4553 */     if (treeMap.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  4559 */       TreeSet<String> treeSet = new TreeSet();
/*  4560 */       set = treeMap.keySet();
/*  4561 */       iterator = set.iterator();
/*  4562 */       while (iterator.hasNext()) {
/*       */         
/*  4564 */         String str = iterator.next();
/*  4565 */         treeSet.add(parseString(str, 6));
/*       */       } 
/*       */       
/*  4568 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  4569 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  4571 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  4574 */       byte b1 = 0;
/*  4575 */       Iterator<String> iterator1 = treeSet.iterator();
/*  4576 */       while (iterator1.hasNext()) {
/*       */         
/*  4578 */         String str = iterator1.next();
/*       */         
/*  4580 */         iterator = set.iterator();
/*  4581 */         while (iterator.hasNext()) {
/*       */           
/*  4583 */           String str1 = iterator.next();
/*  4584 */           String str2 = parseString(str1, 6);
/*       */           
/*  4586 */           if (str.equals(str2))
/*       */           {
/*  4588 */             arrayOfTreeMap[b1].put(str1, treeMap.get(str1));
/*       */           }
/*       */         } 
/*  4591 */         b1++;
/*       */       } 
/*       */       
/*  4594 */       iterator1 = treeSet.iterator();
/*  4595 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  4597 */         retrievePNFeaturesWithdrawFormat1(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
/*       */       }
/*       */       
/*  4600 */       for (b2 = 0; b2 < treeSet.size(); b2++) {
/*       */         
/*  4602 */         arrayOfTreeMap[b2].clear();
/*  4603 */         arrayOfTreeMap[b2] = null;
/*       */       } 
/*  4605 */       treeSet.clear();
/*  4606 */       treeSet = null;
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
/*  4619 */     if (paramTreeMap.size() > 0) {
/*       */       
/*  4621 */       String str1 = "";
/*  4622 */       String str2 = "";
/*  4623 */       String str3 = "";
/*  4624 */       String str4 = "";
/*  4625 */       String str5 = "";
/*  4626 */       String str6 = "";
/*  4627 */       String str7 = parseString((String)paramTreeMap.firstKey(), 6);
/*       */       
/*  4629 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  4633 */         paramStringBuffer.append(":h3.Effective " + formatDate(str7) + NEWLINE);
/*  4634 */         paramStringBuffer.append(":h5.Feature Withdrawals" + NEWLINE);
/*  4635 */         paramStringBuffer.append(":p.The following features are being withdrawn:" + NEWLINE);
/*  4636 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  4637 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*  4638 */         paramStringBuffer.append("Description                   Type  Model  Feature" + NEWLINE);
/*  4639 */         paramStringBuffer.append("----------------------------  ----  -----  -------" + NEWLINE);
/*  4640 */         Set set = paramTreeMap.keySet();
/*  4641 */         Iterator<String> iterator = set.iterator();
/*  4642 */         while (iterator.hasNext()) {
/*       */           
/*  4644 */           String str = iterator.next();
/*  4645 */           str2 = (String)paramTreeMap.get(str);
/*  4646 */           str4 = parseString(str, 1);
/*  4647 */           str6 = parseString(str, 2);
/*  4648 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  4649 */           if (parseString(str, 5).length() > 0)
/*       */           {
/*  4651 */             if (!str4.equals(str3)) {
/*       */               
/*  4653 */               String[] arrayOfString = getStringTokens(parseString(str, 5), NEWLINE);
/*  4654 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*       */                 
/*  4656 */                 if (arrayOfString[b].length() > 58) {
/*       */                   
/*  4658 */                   String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  4659 */                   for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                   {
/*  4661 */                     paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                   }
/*       */                 }
/*       */                 else {
/*       */                   
/*  4666 */                   paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
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
/*  4679 */           paramStringBuffer.append(parseString(str, 4));
/*       */           
/*  4681 */           paramStringBuffer.append("  ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  4691 */           paramStringBuffer.append(parseString(str, 2));
/*       */           
/*  4693 */           paramStringBuffer.append("   ");
/*  4694 */           paramStringBuffer.append(parseString(str, 3));
/*  4695 */           paramStringBuffer.append("     ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  4705 */           paramStringBuffer.append(parseString(str, 1));
/*       */           
/*  4707 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  4709 */           str1 = str2;
/*  4710 */           str3 = str4;
/*  4711 */           str5 = str6;
/*       */         } 
/*  4713 */         if (!str2.equals("WW"))
/*       */         {
/*  4715 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */         
/*  4718 */         paramStringBuffer.append(":exmp." + NEWLINE);
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
/*  4731 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */ 
/*       */     
/*  4734 */     Set<String> set = this.productNumber_NewFC_TM.keySet();
/*  4735 */     Iterator<String> iterator = set.iterator();
/*  4736 */     while (iterator.hasNext()) {
/*       */       
/*  4738 */       String str1 = iterator.next();
/*       */ 
/*       */       
/*  4741 */       String str2 = parseString(str1, 2) + "<:>" + parseString(str1, 1) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 5) + "<:>" + parseString(str1, 6) + "<:>" + parseString(str1, 7) + "<:>" + parseString(str1, 8);
/*  4742 */       treeMap.put(str2, this.productNumber_NewFC_TM.get(str1));
/*       */     } 
/*       */     
/*  4745 */     set = this.productNumber_ExistingFC_TM.keySet();
/*  4746 */     iterator = set.iterator();
/*  4747 */     while (iterator.hasNext()) {
/*       */       
/*  4749 */       String str1 = iterator.next();
/*       */ 
/*       */       
/*  4752 */       String str2 = parseString(str1, 2) + "<:>" + parseString(str1, 1) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 5) + "<:>" + parseString(str1, 6) + "<:>" + parseString(str1, 7) + "<:>" + parseString(str1, 8);
/*  4753 */       treeMap.put(str2, this.productNumber_ExistingFC_TM.get(str1));
/*       */     } 
/*       */     
/*  4756 */     if (treeMap.size() > 0) {
/*       */       
/*  4758 */       String str1 = "";
/*  4759 */       String str2 = "";
/*  4760 */       String str3 = "";
/*  4761 */       String str4 = "";
/*  4762 */       String str5 = "";
/*  4763 */       String str6 = "";
/*       */       
/*  4765 */       paramStringBuffer.append(":p.The following features are being withdrawn on the specified models" + NEWLINE);
/*  4766 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  4768 */         paramStringBuffer.append("of the IBM RS/6000 or pSeries machine types:" + NEWLINE + NEWLINE);
/*       */       }
/*  4770 */       else if (this.brand.equals("xSeries")) {
/*       */         
/*  4772 */         paramStringBuffer.append("of the IBM xSeries machine types:" + NEWLINE + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*  4776 */       else if (this.brand.equals("totalStorage")) {
/*       */         
/*  4778 */         paramStringBuffer.append("of the IBM Total Storage machine types:" + NEWLINE + NEWLINE);
/*       */       } 
/*       */       
/*  4781 */       if (2 == this.format) {
/*       */         
/*  4783 */         String[] arrayOfString = null;
/*  4784 */         byte b = 0;
/*  4785 */         paramStringBuffer.append(".RH ON" + NEWLINE);
/*  4786 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/*  4788 */         paramStringBuffer.append("Description                                         MT  Model Feature" + NEWLINE);
/*  4789 */         paramStringBuffer.append("-------------------------------------------------- ---- ----- -------" + NEWLINE);
/*  4790 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*  4791 */         paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */         
/*  4793 */         set = treeMap.keySet();
/*  4794 */         iterator = set.iterator();
/*  4795 */         while (iterator.hasNext()) {
/*       */           
/*  4797 */           String str = iterator.next();
/*  4798 */           str2 = (String)treeMap.get(str);
/*  4799 */           str4 = parseString(str, 1);
/*  4800 */           str6 = parseString(str, 2);
/*  4801 */           if (!str4.equals(str3)) {
/*       */             
/*  4803 */             arrayOfString = extractStringLines(parseString(str, 4), 50);
/*  4804 */             b = 0;
/*       */           } 
/*  4806 */           setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */           
/*  4808 */           if (parseString(str, 8).length() > 0)
/*       */           {
/*  4810 */             if (!str4.equals(str3)) {
/*       */               
/*  4812 */               String[] arrayOfString1 = getStringTokens(parseString(str, 8), NEWLINE);
/*  4813 */               for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */                 
/*  4815 */                 if (arrayOfString1[b1].length() > 58) {
/*       */                   
/*  4817 */                   String[] arrayOfString2 = extractStringLines(arrayOfString1[b1], 58);
/*  4818 */                   for (byte b2 = 0; b2 < arrayOfString2.length; b2++)
/*       */                   {
/*  4820 */                     paramStringBuffer.append(":hp2." + arrayOfString2[b2] + ":ehp2." + NEWLINE);
/*       */                   }
/*       */                 }
/*       */                 else {
/*       */                   
/*  4825 */                   paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                 } 
/*       */               } 
/*       */             } 
/*       */           }
/*       */           
/*  4831 */           if (b < arrayOfString.length) {
/*       */             
/*  4833 */             paramStringBuffer.append(setString(arrayOfString[b], 50));
/*  4834 */             paramStringBuffer.append(" ");
/*  4835 */             b++;
/*       */           }
/*       */           else {
/*       */             
/*  4839 */             paramStringBuffer.append(setString("", 51));
/*       */           } 
/*  4841 */           if (!str6.equals(str5) || !str4.equals(str3)) {
/*       */             
/*  4843 */             paramStringBuffer.append(parseString(str, 2));
/*  4844 */             paramStringBuffer.append("  ");
/*       */           }
/*       */           else {
/*       */             
/*  4848 */             paramStringBuffer.append(setString("", 6));
/*       */           } 
/*  4850 */           paramStringBuffer.append(parseString(str, 3));
/*  4851 */           paramStringBuffer.append("     ");
/*  4852 */           if (!str4.equals(str3)) {
/*       */             
/*  4854 */             paramStringBuffer.append(parseString(str, 1));
/*  4855 */             paramStringBuffer.append(NEWLINE);
/*       */           }
/*       */           else {
/*       */             
/*  4859 */             paramStringBuffer.append(NEWLINE);
/*       */           } 
/*  4861 */           while (b < arrayOfString.length) {
/*       */             
/*  4863 */             paramStringBuffer.append(setString(arrayOfString[b], 50));
/*  4864 */             paramStringBuffer.append(NEWLINE);
/*  4865 */             b++;
/*       */           } 
/*       */           
/*  4868 */           str1 = str2;
/*  4869 */           str3 = str4;
/*  4870 */           str5 = str6;
/*       */         } 
/*  4872 */         if (!str2.equals("WW"))
/*       */         {
/*  4874 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */         
/*  4877 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*  4878 */         paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
/*       */       } 
/*  4880 */       treeMap.clear();
/*  4881 */       treeMap = null;
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
/*  4893 */     if (this.productNumber_MTM_Conversions_TM.size() > 0) {
/*       */       
/*  4895 */       String str1 = "";
/*  4896 */       String str2 = "";
/*  4897 */       if (paramBoolean == true) {
/*       */         
/*  4899 */         if (this.annType.equals("new")) {
/*       */           
/*  4901 */           paramStringBuffer.append(":h3.Type/Model Conversions" + NEWLINE);
/*       */         }
/*  4903 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  4905 */           paramStringBuffer.append(":h3.Type/Model Conversion Withdrawals" + NEWLINE);
/*  4906 */           paramStringBuffer.append(":p.The following Type/Model Conversions are being withdrawn:" + NEWLINE);
/*       */         } 
/*  4908 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  4909 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*  4913 */       else if (this.annType.equals("new")) {
/*       */         
/*  4915 */         paramStringBuffer.append("<h3>Type/Model Conversions</h3>" + NEWLINE);
/*       */       }
/*  4917 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*  4919 */         paramStringBuffer.append("<h3>Type/Model Conversion Withdrawals</h3>" + NEWLINE);
/*  4920 */         paramStringBuffer.append("<p>The following Type/Model Conversions are being withdrawn:</p>" + NEWLINE);
/*       */       } 
/*       */ 
/*       */       
/*  4924 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  4929 */         if (this.annType.equals("new")) {
/*       */           
/*  4931 */           paramStringBuffer.append("   From       To      Parts" + NEWLINE);
/*  4932 */           paramStringBuffer.append("Type Model Type Model Returned" + NEWLINE);
/*  4933 */           paramStringBuffer.append("---- ----- ---- ----- --------" + NEWLINE);
/*       */         }
/*  4935 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  4937 */           paramStringBuffer.append("   From       To" + NEWLINE);
/*  4938 */           paramStringBuffer.append("Type Model Type Model" + NEWLINE);
/*  4939 */           paramStringBuffer.append("---- ----- ---- -----" + NEWLINE);
/*       */         } 
/*  4941 */         Set set = this.productNumber_MTM_Conversions_TM.keySet();
/*  4942 */         Iterator<String> iterator = set.iterator();
/*  4943 */         while (iterator.hasNext()) {
/*       */           
/*  4945 */           String str = iterator.next();
/*  4946 */           str2 = (String)this.productNumber_MTM_Conversions_TM.get(str);
/*  4947 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  4949 */           paramStringBuffer.append(parseString(str, 3));
/*  4950 */           paramStringBuffer.append("  ");
/*  4951 */           paramStringBuffer.append(parseString(str, 4));
/*  4952 */           paramStringBuffer.append("  ");
/*  4953 */           paramStringBuffer.append(parseString(str, 1));
/*  4954 */           paramStringBuffer.append("  ");
/*  4955 */           paramStringBuffer.append(parseString(str, 2));
/*  4956 */           if (this.annType.equals("new")) {
/*       */             
/*  4958 */             paramStringBuffer.append("    ");
/*  4959 */             paramStringBuffer.append(parseString(str, 5));
/*       */           } 
/*  4961 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  4963 */           str1 = str2;
/*       */         } 
/*  4965 */         if (!str2.equals("WW"))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  4971 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  4977 */         if (paramBoolean == true)
/*       */         {
/*  4979 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */ 
/*       */         
/*       */         }
/*       */ 
/*       */       
/*       */       }
/*  4986 */       else if (2 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  4990 */         paramStringBuffer.append("   From       To" + NEWLINE);
/*  4991 */         paramStringBuffer.append("Type Model Type Model" + NEWLINE);
/*  4992 */         paramStringBuffer.append("---- ----- ---- -----" + NEWLINE);
/*  4993 */         Set set = this.productNumber_MTM_Conversions_TM.keySet();
/*  4994 */         Iterator<String> iterator = set.iterator();
/*  4995 */         while (iterator.hasNext()) {
/*       */           
/*  4997 */           String str = iterator.next();
/*  4998 */           str2 = (String)this.productNumber_MTM_Conversions_TM.get(str);
/*  4999 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  5001 */           paramStringBuffer.append(parseString(str, 3));
/*  5002 */           paramStringBuffer.append("  ");
/*  5003 */           paramStringBuffer.append(parseString(str, 4));
/*  5004 */           paramStringBuffer.append("  ");
/*  5005 */           paramStringBuffer.append(parseString(str, 1));
/*  5006 */           paramStringBuffer.append("  ");
/*  5007 */           paramStringBuffer.append(parseString(str, 2));
/*  5008 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  5010 */           str1 = str2;
/*       */         } 
/*  5012 */         if (!str2.equals("WW"))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5018 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5024 */         if (paramBoolean == true)
/*       */         {
/*  5026 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  5044 */     if (this.productNumber_Model_Conversions_TM.size() > 0) {
/*       */       
/*  5046 */       String str1 = "";
/*  5047 */       String str2 = "";
/*  5048 */       if (paramBoolean == true) {
/*       */         
/*  5050 */         if (this.annType.equals("new")) {
/*       */           
/*  5052 */           paramStringBuffer.append(":h3.Model Conversions" + NEWLINE);
/*       */         }
/*  5054 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  5056 */           paramStringBuffer.append(":h3.Model Conversion Withdrawals" + NEWLINE);
/*  5057 */           paramStringBuffer.append(":p.The following Model Converions on the specified machine type are being withdrawn:" + NEWLINE);
/*       */         } 
/*  5059 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  5060 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*  5064 */       else if (this.annType.equals("new")) {
/*       */         
/*  5066 */         paramStringBuffer.append("<h3>Model Conversions</h3>" + NEWLINE);
/*       */       }
/*  5068 */       else if (this.annType.equals("withdraw")) {
/*       */         
/*  5070 */         paramStringBuffer.append("<h3>Model Conversion Withdrawals</h3>" + NEWLINE);
/*  5071 */         paramStringBuffer.append("<p>The following Model Converions on the specified machine type are being withdrawn:</p>" + NEWLINE);
/*       */       } 
/*       */ 
/*       */       
/*  5075 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  5079 */         if (this.annType.equals("new")) {
/*       */           
/*  5081 */           paramStringBuffer.append("      From   To      Parts" + NEWLINE);
/*  5082 */           paramStringBuffer.append("Type  Model  Model   Returned" + NEWLINE);
/*  5083 */           paramStringBuffer.append("----  -----  -----   --------" + NEWLINE);
/*       */         }
/*  5085 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  5087 */           paramStringBuffer.append("      From   To" + NEWLINE);
/*  5088 */           paramStringBuffer.append("Type  Model  Model" + NEWLINE);
/*  5089 */           paramStringBuffer.append("----  -----  -----" + NEWLINE);
/*       */         } 
/*  5091 */         Set set = this.productNumber_Model_Conversions_TM.keySet();
/*  5092 */         Iterator<String> iterator = set.iterator();
/*  5093 */         while (iterator.hasNext()) {
/*       */           
/*  5095 */           String str = iterator.next();
/*  5096 */           str2 = (String)this.productNumber_Model_Conversions_TM.get(str);
/*  5097 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  5099 */           paramStringBuffer.append(parseString(str, 1));
/*  5100 */           paramStringBuffer.append("   ");
/*  5101 */           paramStringBuffer.append(parseString(str, 3));
/*  5102 */           paramStringBuffer.append("    ");
/*  5103 */           paramStringBuffer.append(parseString(str, 2));
/*  5104 */           if (this.annType.equals("new")) {
/*       */             
/*  5106 */             paramStringBuffer.append("      ");
/*  5107 */             paramStringBuffer.append(parseString(str, 4));
/*       */           } 
/*  5109 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  5111 */           str1 = str2;
/*       */         } 
/*  5113 */         if (!str2.equals("WW"))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5119 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5125 */         if (paramBoolean == true)
/*       */         {
/*  5127 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */ 
/*       */         
/*       */         }
/*       */ 
/*       */       
/*       */       }
/*  5134 */       else if (2 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  5138 */         if (this.annType.equals("new")) {
/*       */           
/*  5140 */           paramStringBuffer.append("      From   To      Parts" + NEWLINE);
/*  5141 */           paramStringBuffer.append("Type  Model  Model   Returned" + NEWLINE);
/*  5142 */           paramStringBuffer.append("----  -----  -----   --------" + NEWLINE);
/*       */         }
/*  5144 */         else if (this.annType.equals("withdraw")) {
/*       */           
/*  5146 */           paramStringBuffer.append("      From   To" + NEWLINE);
/*  5147 */           paramStringBuffer.append("Type  Model  Model" + NEWLINE);
/*  5148 */           paramStringBuffer.append("----  -----  -----" + NEWLINE);
/*       */         } 
/*  5150 */         Set set = this.productNumber_Model_Conversions_TM.keySet();
/*  5151 */         Iterator<String> iterator = set.iterator();
/*  5152 */         while (iterator.hasNext()) {
/*       */           
/*  5154 */           String str = iterator.next();
/*  5155 */           str2 = (String)this.productNumber_Model_Conversions_TM.get(str);
/*  5156 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  5158 */           paramStringBuffer.append(parseString(str, 1));
/*  5159 */           paramStringBuffer.append("   ");
/*  5160 */           paramStringBuffer.append(parseString(str, 3));
/*  5161 */           paramStringBuffer.append("    ");
/*  5162 */           paramStringBuffer.append(parseString(str, 2));
/*  5163 */           if (this.annType.equals("new")) {
/*       */             
/*  5165 */             paramStringBuffer.append("      ");
/*  5166 */             paramStringBuffer.append(parseString(str, 4));
/*       */           } 
/*  5168 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  5170 */           str1 = str2;
/*       */         } 
/*  5172 */         if (!str2.equals("WW"))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5178 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5184 */         if (paramBoolean == true)
/*       */         {
/*  5186 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  5204 */     if (this.productNumber_Feature_Conversions_TM.size() > 0) {
/*       */       
/*  5206 */       String str1 = "";
/*  5207 */       String str2 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5212 */       paramStringBuffer.append(":h3.Conversions" + NEWLINE);
/*  5213 */       paramStringBuffer.append(":h5.Feature Conversions" + NEWLINE);
/*  5214 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*  5215 */       paramStringBuffer.append(".kp off" + NEWLINE);
/*  5216 */       paramStringBuffer.append("               Parts       Continuous    Machine" + NEWLINE);
/*  5217 */       paramStringBuffer.append("From:   To:    Returned    Maintenance   Type     Model" + NEWLINE);
/*  5218 */       paramStringBuffer.append("----    ---    --------    -----------   -------  -----" + NEWLINE);
/*  5219 */       Set set = this.productNumber_Feature_Conversions_TM.keySet();
/*  5220 */       Iterator<String> iterator = set.iterator();
/*  5221 */       while (iterator.hasNext()) {
/*       */         
/*  5223 */         String str = iterator.next();
/*  5224 */         str2 = (String)this.productNumber_Feature_Conversions_TM.get(str);
/*  5225 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  5226 */         if (parseString(str, 9).length() > 0) {
/*       */           
/*  5228 */           if (!paramBoolean) {
/*       */ 
/*       */             
/*  5231 */             String[] arrayOfString = getStringTokens(parseString(str, 9), NEWLINE);
/*  5232 */             for (byte b = 0; b < arrayOfString.length; b++)
/*       */             {
/*  5234 */               paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */             }
/*       */           } 
/*       */           
/*  5238 */           if (paramBoolean == true) {
/*       */             
/*  5240 */             String[] arrayOfString = getStringTokens(parseString(str, 9), NEWLINE);
/*  5241 */             for (byte b = 0; b < arrayOfString.length; b++) {
/*       */               
/*  5243 */               if (arrayOfString[b].length() > 58) {
/*       */                 
/*  5245 */                 String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  5246 */                 for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                 {
/*  5248 */                   paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  5253 */                 paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         
/*  5259 */         paramStringBuffer.append(parseString(str, 7));
/*  5260 */         paramStringBuffer.append("    ");
/*  5261 */         paramStringBuffer.append(parseString(str, 4));
/*  5262 */         paramStringBuffer.append("     ");
/*  5263 */         paramStringBuffer.append(parseString(str, 8));
/*  5264 */         paramStringBuffer.append("                      ");
/*  5265 */         paramStringBuffer.append(parseString(str, 3));
/*  5266 */         paramStringBuffer.append("     ");
/*  5267 */         paramStringBuffer.append(parseString(str, 2));
/*  5268 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  5270 */         str1 = str2;
/*       */       } 
/*  5272 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5278 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5284 */       if (paramBoolean == true)
/*       */       {
/*  5286 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  5303 */     if (this.charges_Feature_Conversions_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5314 */       paramStringBuffer.append(":h3.Feature Conversions" + NEWLINE);
/*  5315 */       paramStringBuffer.append(":p." + NEWLINE);
/*  5316 */       paramStringBuffer.append("The existing components being replaced during a model or feature" + NEWLINE);
/*  5317 */       paramStringBuffer.append("conversion become the property of IBM and must be returned." + NEWLINE);
/*  5318 */       paramStringBuffer.append(":p." + NEWLINE);
/*  5319 */       paramStringBuffer.append("Feature conversions are always implemented on a \"quantity of one for" + NEWLINE);
/*  5320 */       paramStringBuffer.append("quantity of one\" basis. Multiple existing features may not be converted" + NEWLINE);
/*  5321 */       paramStringBuffer.append("to a single new feature. Single existing features may not be converted" + NEWLINE);
/*  5322 */       paramStringBuffer.append("to multiple new features." + NEWLINE);
/*  5323 */       paramStringBuffer.append(":p." + NEWLINE);
/*  5324 */       paramStringBuffer.append("The following conversions are available to customers:" + NEWLINE);
/*  5325 */       paramStringBuffer.append(":p." + NEWLINE);
/*       */ 
/*       */       
/*  5328 */       String str1 = "";
/*  5329 */       String str2 = "";
/*  5330 */       TreeSet<String> treeSet = new TreeSet();
/*  5331 */       Set<String> set = this.charges_Feature_Conversions_TM.keySet();
/*  5332 */       Iterator<String> iterator1 = set.iterator();
/*  5333 */       while (iterator1.hasNext()) {
/*       */         
/*  5335 */         String str = iterator1.next();
/*  5336 */         str1 = parseString(str, 5);
/*  5337 */         str2 = parseString(str, 6);
/*  5338 */         treeSet.add(str1 + "-" + str2);
/*       */       } 
/*       */       
/*  5341 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  5342 */       for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  5344 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  5347 */       byte b1 = 0;
/*  5348 */       Iterator<String> iterator2 = treeSet.iterator();
/*  5349 */       while (iterator2.hasNext()) {
/*       */         
/*  5351 */         String str = iterator2.next();
/*       */         
/*  5353 */         iterator1 = set.iterator();
/*  5354 */         while (iterator1.hasNext()) {
/*       */ 
/*       */           
/*  5357 */           String str4 = iterator1.next();
/*  5358 */           str1 = parseString(str4, 5);
/*  5359 */           str2 = parseString(str4, 6);
/*  5360 */           String str3 = str1 + "-" + str2;
/*       */           
/*  5362 */           if (str.equals(str3))
/*       */           {
/*  5364 */             arrayOfTreeMap[b1].put(str4, this.charges_Feature_Conversions_TM.get(str4));
/*       */           }
/*       */         } 
/*  5367 */         b1++;
/*       */       } 
/*       */       
/*  5370 */       b1 = 0;
/*  5371 */       iterator2 = treeSet.iterator();
/*  5372 */       while (iterator2.hasNext()) {
/*       */         
/*  5374 */         String str = iterator2.next();
/*  5375 */         retrievePNFeatureConversionsFormat2(paramBoolean, paramStringBuffer, str, arrayOfTreeMap[b1]);
/*  5376 */         b1++;
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
/*  5398 */     paramStringBuffer.append(":h4.Feature conversions for " + paramString + NEWLINE);
/*  5399 */     paramStringBuffer.append(".sk 1" + NEWLINE);
/*  5400 */     paramStringBuffer.append(":p." + NEWLINE);
/*       */ 
/*       */     
/*  5403 */     TreeSet<String> treeSet = new TreeSet();
/*  5404 */     Set<String> set = paramTreeMap.keySet();
/*  5405 */     Iterator<String> iterator1 = set.iterator();
/*  5406 */     while (iterator1.hasNext()) {
/*       */       
/*  5408 */       String str = iterator1.next();
/*  5409 */       treeSet.add(parseString(str, 1));
/*       */     } 
/*       */     
/*  5412 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  5413 */     for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/*  5415 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/*  5418 */     byte b1 = 0;
/*  5419 */     Iterator<String> iterator2 = treeSet.iterator();
/*  5420 */     while (iterator2.hasNext()) {
/*       */       
/*  5422 */       String str = iterator2.next();
/*       */       
/*  5424 */       iterator1 = set.iterator();
/*  5425 */       while (iterator1.hasNext()) {
/*       */         
/*  5427 */         String str1 = iterator1.next();
/*       */         
/*  5429 */         if (str.equals(parseString(str1, 1)))
/*       */         {
/*  5431 */           arrayOfTreeMap[b1].put(str1, paramTreeMap.get(str1));
/*       */         }
/*       */       } 
/*  5434 */       b1++;
/*       */     } 
/*       */     
/*  5437 */     for (b2 = 0; b2 < arrayOfTreeMap.length; b2++)
/*       */     {
/*  5439 */       retrievePNFeatureConversionsFormat2(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
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
/*  5452 */     String str1 = "";
/*  5453 */     String str2 = "";
/*  5454 */     String str3 = parseString((String)paramTreeMap.firstKey(), 1);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5460 */     paramStringBuffer.append(":h5.Feature conversions for " + str3 + " features:" + NEWLINE);
/*  5461 */     paramStringBuffer.append(":xmp." + NEWLINE);
/*       */     
/*  5463 */     paramStringBuffer.append("                                                            RETURN" + NEWLINE);
/*  5464 */     paramStringBuffer.append("From FC:                      To FC:                        PARTS" + NEWLINE);
/*  5465 */     paramStringBuffer.append("---------------------------   ---------------------------   ------" + NEWLINE);
/*  5466 */     paramStringBuffer.append(":exmp." + NEWLINE);
/*  5467 */     int i = 0;
/*  5468 */     byte b = 0;
/*  5469 */     boolean bool = false;
/*  5470 */     Set set = paramTreeMap.keySet();
/*  5471 */     Iterator<String> iterator = set.iterator();
/*  5472 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5478 */       String str6 = iterator.next();
/*  5479 */       str2 = (String)paramTreeMap.get(str6);
/*  5480 */       setGeoTagsFeatConv(str1, str2, paramBoolean, paramStringBuffer);
/*  5481 */       if (parseString(str6, 11).length() > 0) {
/*       */         
/*  5483 */         if (!paramBoolean) {
/*       */ 
/*       */           
/*  5486 */           String[] arrayOfString = getStringTokens(parseString(str6, 11), NEWLINE);
/*  5487 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++)
/*       */           {
/*  5489 */             paramStringBuffer.append(arrayOfString[b1] + NEWLINE);
/*       */           }
/*       */         } 
/*       */         
/*  5493 */         if (paramBoolean == true) {
/*       */           
/*  5495 */           String[] arrayOfString = getStringTokens(parseString(str6, 11), NEWLINE);
/*  5496 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */             
/*  5498 */             if (arrayOfString[b1].length() > 58) {
/*       */               
/*  5500 */               String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  5501 */               for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */               {
/*  5503 */                 paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */               }
/*       */             }
/*       */             else {
/*       */               
/*  5508 */               paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       
/*  5514 */       String str4 = parseString(str6, 7) + " - " + parseString(str6, 9);
/*  5515 */       String str5 = parseString(str6, 4) + " - " + parseString(str6, 8);
/*  5516 */       String[] arrayOfString1 = extractStringLines(str4, 27);
/*  5517 */       String[] arrayOfString2 = extractStringLines(str5, 27);
/*  5518 */       i = 0;
/*  5519 */       if (arrayOfString1.length > arrayOfString2.length) {
/*       */         
/*  5521 */         i = arrayOfString1.length;
/*       */       }
/*       */       else {
/*       */         
/*  5525 */         i = arrayOfString2.length;
/*       */       } 
/*  5527 */       b = 0;
/*  5528 */       bool = false;
/*       */       
/*       */       while (true) {
/*  5531 */         if (false == bool) {
/*       */ 
/*       */           
/*  5534 */           paramStringBuffer.append(setString(arrayOfString1[b], 27));
/*  5535 */           paramStringBuffer.append("   ");
/*  5536 */           paramStringBuffer.append(setString(arrayOfString2[b], 27));
/*  5537 */           paramStringBuffer.append("    ");
/*  5538 */           paramStringBuffer.append(parseString(str6, 10));
/*  5539 */           paramStringBuffer.append(NEWLINE);
/*  5540 */           bool = true;
/*  5541 */           b++;
/*       */         }
/*       */         else {
/*       */           
/*  5545 */           if (b < arrayOfString1.length) {
/*       */             
/*  5547 */             paramStringBuffer.append(setString(arrayOfString1[b], 27));
/*       */           }
/*       */           else {
/*       */             
/*  5551 */             paramStringBuffer.append(setString(" ", 27));
/*       */           } 
/*  5553 */           paramStringBuffer.append("   ");
/*  5554 */           if (b < arrayOfString2.length) {
/*       */             
/*  5556 */             paramStringBuffer.append(setString(arrayOfString2[b], 27));
/*       */           }
/*       */           else {
/*       */             
/*  5560 */             paramStringBuffer.append(setString(" ", 27));
/*       */           } 
/*  5562 */           paramStringBuffer.append(NEWLINE);
/*  5563 */           b++;
/*       */         } 
/*  5565 */         if (b >= i)
/*  5566 */           str1 = str2; 
/*       */       } 
/*  5568 */     }  if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5574 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5580 */     if (paramBoolean == true)
/*       */     {
/*  5582 */       paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  5598 */     if (this.productNumber_Feature_Conversions_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5604 */       TreeSet<String> treeSet = new TreeSet();
/*  5605 */       Set<String> set = this.productNumber_Feature_Conversions_TM.keySet();
/*  5606 */       Iterator<String> iterator2 = set.iterator();
/*  5607 */       while (iterator2.hasNext()) {
/*       */         
/*  5609 */         String str = iterator2.next();
/*  5610 */         treeSet.add(parseString(str, 10));
/*       */       } 
/*       */       
/*  5613 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  5614 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  5616 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  5619 */       byte b1 = 0;
/*  5620 */       Iterator<String> iterator1 = treeSet.iterator();
/*  5621 */       while (iterator1.hasNext()) {
/*       */         
/*  5623 */         String str = iterator1.next();
/*       */         
/*  5625 */         iterator2 = set.iterator();
/*  5626 */         while (iterator2.hasNext()) {
/*       */           
/*  5628 */           String str1 = iterator2.next();
/*  5629 */           String str2 = parseString(str1, 10);
/*       */           
/*  5631 */           if (str.equals(str2)) {
/*       */ 
/*       */ 
/*       */             
/*  5635 */             String str3 = parseString(str1, 1) + "<:>" + parseString(str1, 2) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 5) + "<:>" + parseString(str1, 6) + "<:>" + parseString(str1, 7) + "<:>" + parseString(str1, 9) + "<:>" + parseString(str1, 10);
/*  5636 */             arrayOfTreeMap[b1].put(str3, this.productNumber_Feature_Conversions_TM.get(str1));
/*       */           } 
/*       */         } 
/*  5639 */         b1++;
/*       */       } 
/*       */       
/*  5642 */       iterator1 = treeSet.iterator();
/*  5643 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  5645 */         retrievePNFeatureConversionsForWithdrawFormat1(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
/*       */       }
/*       */       
/*  5648 */       for (b2 = 0; b2 < treeSet.size(); b2++) {
/*       */         
/*  5650 */         arrayOfTreeMap[b2].clear();
/*  5651 */         arrayOfTreeMap[b2] = null;
/*       */       } 
/*  5653 */       treeSet.clear();
/*  5654 */       treeSet = null;
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
/*  5669 */     String str1 = "";
/*  5670 */     String str2 = "";
/*  5671 */     String str3 = parseString((String)paramTreeMap.firstKey(), 9);
/*       */     
/*  5673 */     paramStringBuffer.append(":h3.Effective " + formatDate(str3) + NEWLINE);
/*  5674 */     paramStringBuffer.append(":h5.Feature Conversion Withdrawals" + NEWLINE);
/*  5675 */     paramStringBuffer.append(":xmp." + NEWLINE);
/*  5676 */     paramStringBuffer.append(".kp off" + NEWLINE);
/*  5677 */     paramStringBuffer.append("              Machine" + NEWLINE);
/*  5678 */     paramStringBuffer.append("From:   To:   Type     Model" + NEWLINE);
/*  5679 */     paramStringBuffer.append("----    ---   -------  -----" + NEWLINE);
/*  5680 */     Set set = paramTreeMap.keySet();
/*  5681 */     Iterator<String> iterator = set.iterator();
/*  5682 */     while (iterator.hasNext()) {
/*       */       
/*  5684 */       String str = iterator.next();
/*  5685 */       str2 = (String)paramTreeMap.get(str);
/*  5686 */       setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  5687 */       if (parseString(str, 8).length() > 0) {
/*       */         
/*  5689 */         if (!paramBoolean) {
/*       */ 
/*       */           
/*  5692 */           String[] arrayOfString = getStringTokens(parseString(str, 8), NEWLINE);
/*  5693 */           for (byte b = 0; b < arrayOfString.length; b++)
/*       */           {
/*  5695 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/*       */         } 
/*       */         
/*  5699 */         if (paramBoolean == true) {
/*       */           
/*  5701 */           String[] arrayOfString = getStringTokens(parseString(str, 9), NEWLINE);
/*  5702 */           for (byte b = 0; b < arrayOfString.length; b++) {
/*       */             
/*  5704 */             if (arrayOfString[b].length() > 58) {
/*       */               
/*  5706 */               String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  5707 */               for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */               {
/*  5709 */                 paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */               }
/*       */             }
/*       */             else {
/*       */               
/*  5714 */               paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       
/*  5720 */       paramStringBuffer.append(parseString(str, 7));
/*  5721 */       paramStringBuffer.append("    ");
/*  5722 */       paramStringBuffer.append(parseString(str, 4));
/*  5723 */       paramStringBuffer.append("   ");
/*  5724 */       paramStringBuffer.append(parseString(str, 3));
/*  5725 */       paramStringBuffer.append("     ");
/*  5726 */       paramStringBuffer.append(parseString(str, 2));
/*  5727 */       paramStringBuffer.append(NEWLINE);
/*       */       
/*  5729 */       str1 = str2;
/*       */     } 
/*  5731 */     if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5737 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5743 */     if (paramBoolean == true)
/*       */     {
/*  5745 */       paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  5761 */     if (this.charges_Feature_Conversions_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5772 */       paramStringBuffer.append(":h3.Feature Conversion Withdrawals" + NEWLINE);
/*  5773 */       paramStringBuffer.append(":p." + NEWLINE);
/*       */ 
/*       */       
/*  5776 */       String str1 = "";
/*  5777 */       String str2 = "";
/*  5778 */       TreeSet<String> treeSet = new TreeSet();
/*  5779 */       Set<String> set = this.charges_Feature_Conversions_TM.keySet();
/*  5780 */       Iterator<String> iterator1 = set.iterator();
/*  5781 */       while (iterator1.hasNext()) {
/*       */         
/*  5783 */         String str = iterator1.next();
/*  5784 */         str1 = parseString(str, 5);
/*  5785 */         str2 = parseString(str, 6);
/*  5786 */         treeSet.add(str1 + "-" + str2);
/*       */       } 
/*       */       
/*  5789 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  5790 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  5792 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  5795 */       byte b1 = 0;
/*  5796 */       Iterator<String> iterator2 = treeSet.iterator();
/*  5797 */       while (iterator2.hasNext()) {
/*       */         
/*  5799 */         String str = iterator2.next();
/*       */         
/*  5801 */         iterator1 = set.iterator();
/*  5802 */         while (iterator1.hasNext()) {
/*       */ 
/*       */           
/*  5805 */           String str4 = iterator1.next();
/*  5806 */           str1 = parseString(str4, 5);
/*  5807 */           str2 = parseString(str4, 6);
/*  5808 */           String str3 = str1 + "-" + str2;
/*       */           
/*  5810 */           if (str.equals(str3))
/*       */           {
/*  5812 */             arrayOfTreeMap[b1].put(str4, this.charges_Feature_Conversions_TM.get(str4));
/*       */           }
/*       */         } 
/*  5815 */         b1++;
/*       */       } 
/*       */       
/*  5818 */       b1 = 0;
/*  5819 */       iterator2 = treeSet.iterator();
/*  5820 */       while (iterator2.hasNext()) {
/*       */         
/*  5822 */         String str = iterator2.next();
/*  5823 */         retrievePNFeatureConversionsForWithdrawFormat2(paramBoolean, paramStringBuffer, str, arrayOfTreeMap[b1]);
/*  5824 */         b1++;
/*       */       } 
/*       */       
/*  5827 */       for (b2 = 0; b2 < treeSet.size(); b2++) {
/*       */         
/*  5829 */         arrayOfTreeMap[b2].clear();
/*  5830 */         arrayOfTreeMap[b2] = null;
/*       */       } 
/*  5832 */       treeSet.clear();
/*  5833 */       treeSet = null;
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
/*  5854 */     paramStringBuffer.append("The following feature conversions for the " + paramString + " are being withdrawn:" + NEWLINE);
/*  5855 */     paramStringBuffer.append(".sk 1" + NEWLINE);
/*       */ 
/*       */     
/*  5858 */     TreeSet<String> treeSet = new TreeSet();
/*  5859 */     Set<String> set = paramTreeMap.keySet();
/*  5860 */     Iterator<String> iterator1 = set.iterator();
/*  5861 */     while (iterator1.hasNext()) {
/*       */       
/*  5863 */       String str = iterator1.next();
/*  5864 */       treeSet.add(parseString(str, 1));
/*       */     } 
/*       */     
/*  5867 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  5868 */     for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/*  5870 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/*  5873 */     byte b1 = 0;
/*  5874 */     Iterator<String> iterator2 = treeSet.iterator();
/*  5875 */     while (iterator2.hasNext()) {
/*       */       
/*  5877 */       String str = iterator2.next();
/*       */       
/*  5879 */       iterator1 = set.iterator();
/*  5880 */       while (iterator1.hasNext()) {
/*       */         
/*  5882 */         String str1 = iterator1.next();
/*       */         
/*  5884 */         if (str.equals(parseString(str1, 1)))
/*       */         {
/*  5886 */           arrayOfTreeMap[b1].put(str1, paramTreeMap.get(str1));
/*       */         }
/*       */       } 
/*  5889 */       b1++;
/*       */     } 
/*       */     
/*  5892 */     for (b2 = 0; b2 < arrayOfTreeMap.length; b2++)
/*       */     {
/*  5894 */       retrievePNFeatureConversionsForWithdrawFormat2(paramBoolean, paramStringBuffer, arrayOfTreeMap[b2]);
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
/*  5907 */     String str1 = "";
/*  5908 */     String str2 = "";
/*  5909 */     String str3 = parseString((String)paramTreeMap.firstKey(), 1);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5915 */     paramStringBuffer.append(":h5.Feature conversions for " + str3 + " features:" + NEWLINE);
/*  5916 */     paramStringBuffer.append(":xmp." + NEWLINE);
/*       */     
/*  5918 */     paramStringBuffer.append("                                                            RETURN" + NEWLINE);
/*  5919 */     paramStringBuffer.append("From FC:                      To FC:                        PARTS" + NEWLINE);
/*  5920 */     paramStringBuffer.append("---------------------------   ---------------------------   ------" + NEWLINE);
/*  5921 */     paramStringBuffer.append(":exmp." + NEWLINE);
/*  5922 */     int i = 0;
/*  5923 */     byte b = 0;
/*  5924 */     boolean bool = false;
/*  5925 */     Set set = paramTreeMap.keySet();
/*  5926 */     Iterator<String> iterator = set.iterator();
/*  5927 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5933 */       String str6 = iterator.next();
/*  5934 */       str2 = (String)paramTreeMap.get(str6);
/*  5935 */       setGeoTagsFeatConv(str1, str2, paramBoolean, paramStringBuffer);
/*  5936 */       if (parseString(str6, 11).length() > 0) {
/*       */         
/*  5938 */         if (!paramBoolean) {
/*       */ 
/*       */           
/*  5941 */           String[] arrayOfString = getStringTokens(parseString(str6, 11), NEWLINE);
/*  5942 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++)
/*       */           {
/*  5944 */             paramStringBuffer.append(arrayOfString[b1] + NEWLINE);
/*       */           }
/*       */         } 
/*       */         
/*  5948 */         if (paramBoolean == true) {
/*       */           
/*  5950 */           String[] arrayOfString = getStringTokens(parseString(str6, 11), NEWLINE);
/*  5951 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */             
/*  5953 */             if (arrayOfString[b1].length() > 58) {
/*       */               
/*  5955 */               String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  5956 */               for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */               {
/*  5958 */                 paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */               }
/*       */             }
/*       */             else {
/*       */               
/*  5963 */               paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       
/*  5969 */       String str4 = parseString(str6, 7) + " - " + parseString(str6, 9);
/*  5970 */       String str5 = parseString(str6, 4) + " - " + parseString(str6, 8);
/*  5971 */       String[] arrayOfString1 = extractStringLines(str4, 27);
/*  5972 */       String[] arrayOfString2 = extractStringLines(str5, 27);
/*  5973 */       i = 0;
/*  5974 */       if (arrayOfString1.length > arrayOfString2.length) {
/*       */         
/*  5976 */         i = arrayOfString1.length;
/*       */       }
/*       */       else {
/*       */         
/*  5980 */         i = arrayOfString2.length;
/*       */       } 
/*  5982 */       b = 0;
/*  5983 */       bool = false;
/*       */       
/*       */       while (true) {
/*  5986 */         if (false == bool) {
/*       */ 
/*       */           
/*  5989 */           paramStringBuffer.append(setString(arrayOfString1[b], 27));
/*  5990 */           paramStringBuffer.append("   ");
/*  5991 */           paramStringBuffer.append(setString(arrayOfString2[b], 27));
/*  5992 */           paramStringBuffer.append("    ");
/*  5993 */           paramStringBuffer.append(parseString(str6, 10));
/*  5994 */           paramStringBuffer.append(NEWLINE);
/*  5995 */           bool = true;
/*  5996 */           b++;
/*       */         }
/*       */         else {
/*       */           
/*  6000 */           if (b < arrayOfString1.length) {
/*       */             
/*  6002 */             paramStringBuffer.append(setString(arrayOfString1[b], 27));
/*       */           }
/*       */           else {
/*       */             
/*  6006 */             paramStringBuffer.append(setString(" ", 27));
/*       */           } 
/*  6008 */           paramStringBuffer.append("   ");
/*  6009 */           if (b < arrayOfString2.length) {
/*       */             
/*  6011 */             paramStringBuffer.append(setString(arrayOfString2[b], 27));
/*       */           }
/*       */           else {
/*       */             
/*  6015 */             paramStringBuffer.append(setString(" ", 27));
/*       */           } 
/*  6017 */           paramStringBuffer.append(NEWLINE);
/*  6018 */           b++;
/*       */         } 
/*  6020 */         if (b >= i)
/*  6021 */           str1 = str2; 
/*       */       } 
/*  6023 */     }  if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  6029 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6035 */     if (paramBoolean == true)
/*       */     {
/*  6037 */       paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  6053 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  6054 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  6055 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */ 
/*       */ 
/*       */     
/*  6059 */     if (this.charges_NewModels_TM.size() + this.charges_NewFC_TM.size() + this.charges_ExistingFC_TM
/*  6060 */       .size() + this.productNumber_MTM_Conversions_TM.size() + this.productNumber_Model_Conversions_TM
/*  6061 */       .size() + this.charges_Feature_Conversions_TM.size() + this.charges_NewModels_NewFC_TM
/*  6062 */       .size() + this.charges_NewModels_ExistingFC_TM.size() + this.charges_ExistingModels_NewFC_TM
/*  6063 */       .size() + this.charges_ExistingModels_ExistingFC_TM.size() > 0) {
/*       */       
/*  6065 */       log("annType = " + this.annType);
/*  6066 */       log("charges_NewModels_TM.size() = " + this.charges_NewModels_TM.size());
/*  6067 */       log("charges_NewFC_TM.size() = " + this.charges_NewFC_TM.size());
/*  6068 */       log("charges_ExistingFC_TM.size() = " + this.charges_ExistingFC_TM.size());
/*  6069 */       log("charges_NewModels_NewFC_TM.size() = " + this.charges_NewModels_NewFC_TM.size());
/*  6070 */       log("charges_NewModels_ExistingFC_TM.size() = " + this.charges_NewModels_ExistingFC_TM.size());
/*  6071 */       log("charges_ExistingModels_NewFC_TM.size() = " + this.charges_ExistingModels_NewFC_TM.size());
/*  6072 */       log("charges_ExistingModels_ExistingFC_TM.size() = " + this.charges_ExistingModels_ExistingFC_TM.size());
/*       */       
/*  6074 */       if (1 == this.format)
/*       */       {
/*  6076 */         retrieveChargesNewModelsFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/*  6079 */       if (this.charges_NewFC_TM.size() + this.charges_ExistingFC_TM.size() > 0)
/*       */       {
/*  6081 */         if (1 == this.format)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6089 */           paramStringBuffer.append(":h3.Features and Specify Codes" + NEWLINE);
/*       */         }
/*       */       }
/*       */       
/*  6093 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6104 */         TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  6105 */         treeMap.putAll(this.charges_NewFC_TM);
/*  6106 */         treeMap.putAll(this.charges_ExistingFC_TM);
/*  6107 */         retrieveChargesFeaturesFormat1(treeMap, paramBoolean, paramStringBuffer);
/*  6108 */         treeMap.clear();
/*  6109 */         treeMap = null;
/*       */       }
/*  6111 */       else if (2 == this.format) {
/*       */         
/*  6113 */         retrieveChargesFeaturesFormat2(paramBoolean, paramStringBuffer);
/*       */       } 
/*       */       
/*  6116 */       retrieveChargesMTMConversions(paramBoolean, paramStringBuffer);
/*  6117 */       retrieveChargesModelConversions(paramBoolean, paramStringBuffer);
/*  6118 */       if (1 == this.format)
/*       */       {
/*  6120 */         retrieveChargesFeatureConverstionsFormat1(paramBoolean, paramStringBuffer);
/*       */       }
/*  6122 */       else if (2 == this.format)
/*       */       {
/*  6124 */         retrieveChargesFeatureConverstionsFormat2(paramBoolean, paramStringBuffer);
/*       */       
/*       */       }
/*       */     
/*       */     }
/*  6129 */     else if (paramBoolean == true) {
/*       */       
/*  6131 */       paramStringBuffer.append(":p.No answer data found for Charges section." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  6135 */       paramStringBuffer.append("<p>No answer data found for Charges section.</p>" + NEWLINE);
/*       */     } 
/*       */     
/*  6138 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
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
/*  6149 */     if (this.charges_NewModels_TM.size() > 0) {
/*       */       
/*  6151 */       String str1 = "";
/*  6152 */       String str2 = "";
/*       */       
/*  6154 */       paramStringBuffer.append(":h3.Models" + NEWLINE);
/*       */       
/*  6156 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  6160 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  6161 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*  6162 */         paramStringBuffer.append("                                           Purchase        ESA" + NEWLINE);
/*  6163 */         paramStringBuffer.append("Description                                Price     MMMC  24X7  CSU" + NEWLINE);
/*  6164 */         paramStringBuffer.append("-----------------------------------------  --------  ----  ----  ---" + NEWLINE);
/*  6165 */         Set set = this.charges_NewModels_TM.keySet();
/*  6166 */         Iterator<String> iterator = set.iterator();
/*  6167 */         while (iterator.hasNext()) {
/*       */           
/*  6169 */           String str = iterator.next();
/*  6170 */           str2 = (String)this.charges_NewModels_TM.get(str);
/*  6171 */           setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           
/*  6173 */           paramStringBuffer.append(setString(parseString(str, 3), 41));
/*  6174 */           paramStringBuffer.append("  ");
/*  6175 */           paramStringBuffer.append("   $XXXX");
/*  6176 */           paramStringBuffer.append("  ");
/*  6177 */           paramStringBuffer.append("$XXX");
/*  6178 */           paramStringBuffer.append("  ");
/*  6179 */           paramStringBuffer.append("$XXX");
/*  6180 */           paramStringBuffer.append("  ");
/*  6181 */           paramStringBuffer.append(parseString(str, 5));
/*  6182 */           paramStringBuffer.append(NEWLINE);
/*       */           
/*  6184 */           str1 = str2;
/*       */         } 
/*  6186 */         if (!str2.equals("WW"))
/*       */         {
/*  6188 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*  6190 */         if (paramBoolean == true)
/*       */         {
/*  6192 */           paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  6331 */     if (paramTreeMap.size() > 0) {
/*       */       
/*  6333 */       String str1 = "";
/*  6334 */       String str2 = "";
/*  6335 */       String str3 = "";
/*  6336 */       String str4 = "";
/*  6337 */       String str5 = "";
/*  6338 */       String str6 = "";
/*       */       
/*  6340 */       if (1 == this.format) {
/*       */ 
/*       */ 
/*       */         
/*  6344 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  6345 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*  6346 */         paramStringBuffer.append("                             Feature  Purchase        ESA   ESA" + NEWLINE);
/*  6347 */         paramStringBuffer.append("Description                  Number   Price     MMMC  24X7  9X5   CSU" + NEWLINE);
/*  6348 */         paramStringBuffer.append("---------------------------- -------  --------  ----  ----  ----  ---" + NEWLINE);
/*  6349 */         Set set = paramTreeMap.keySet();
/*  6350 */         Iterator<String> iterator = set.iterator();
/*  6351 */         while (iterator.hasNext()) {
/*       */           
/*  6353 */           String str = iterator.next();
/*  6354 */           str2 = (String)paramTreeMap.get(str);
/*  6355 */           str4 = parseString(str, 1);
/*  6356 */           str6 = parseString(str, 2);
/*  6357 */           if (!str4.equals(str3))
/*       */           {
/*  6359 */             setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */           }
/*  6361 */           if (parseString(str, 8).length() > 0)
/*       */           {
/*  6363 */             if (!str4.equals(str3)) {
/*       */               
/*  6365 */               String[] arrayOfString = getStringTokens(parseString(str, 8), NEWLINE);
/*  6366 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*       */                 
/*  6368 */                 if (arrayOfString[b].length() > 58) {
/*       */                   
/*  6370 */                   String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  6371 */                   for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                   {
/*  6373 */                     paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                   }
/*       */                 }
/*       */                 else {
/*       */                   
/*  6378 */                   paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */                 } 
/*       */               } 
/*       */             } 
/*       */           }
/*       */           
/*  6384 */           if (!str4.equals(str3)) {
/*       */             
/*  6386 */             paramStringBuffer.append(parseString(str, 4));
/*  6387 */             paramStringBuffer.append("  ");
/*  6388 */             paramStringBuffer.append(parseString(str, 1));
/*  6389 */             paramStringBuffer.append("    ");
/*  6390 */             paramStringBuffer.append(parseString(str, 9));
/*  6391 */             paramStringBuffer.append("  ");
/*  6392 */             paramStringBuffer.append(parseString(str, 10));
/*  6393 */             paramStringBuffer.append("  ");
/*  6394 */             paramStringBuffer.append(parseString(str, 11));
/*  6395 */             paramStringBuffer.append("  ");
/*  6396 */             paramStringBuffer.append(parseString(str, 12));
/*  6397 */             paramStringBuffer.append("  ");
/*  6398 */             paramStringBuffer.append(parseString(str, 6));
/*  6399 */             paramStringBuffer.append(NEWLINE);
/*       */           } 
/*       */           
/*  6402 */           str1 = str2;
/*  6403 */           str3 = str4;
/*  6404 */           str5 = str6;
/*       */         } 
/*  6406 */         if (!str2.equals("WW"))
/*       */         {
/*  6408 */           bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */         }
/*       */         
/*  6411 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  6437 */     Iterator<String> iterator = this.machineTypeTS.iterator();
/*       */     
/*  6439 */     while (iterator.hasNext()) {
/*       */       
/*  6441 */       String str = iterator.next();
/*       */       
/*  6443 */       retrieveChargesFeaturesFormat2_MTM(str, paramBoolean, paramStringBuffer);
/*  6444 */       retrieveChargesFeaturesFormat2_Feature(str, paramBoolean, paramStringBuffer);
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
/*  6457 */     TreeMap<Object, Object> treeMap1 = new TreeMap<>();
/*  6458 */     TreeMap<Object, Object> treeMap2 = new TreeMap<>();
/*  6459 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/*  6461 */     if (this.charges_NewModels_NewFC_TM.size() > 0) {
/*       */       
/*  6463 */       Set set = this.charges_NewModels_NewFC_TM.keySet();
/*  6464 */       Iterator<String> iterator = set.iterator();
/*  6465 */       while (iterator.hasNext()) {
/*       */         
/*  6467 */         String str = iterator.next();
/*       */         
/*  6469 */         if (paramString.equals(parseString(str, 1))) {
/*       */           
/*  6471 */           String str1 = parseString(str, 3);
/*  6472 */           treeSet.add(str1);
/*  6473 */           treeMap1.put(str, this.charges_NewModels_NewFC_TM.get(str));
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  6478 */     if (this.charges_NewModels_ExistingFC_TM.size() > 0) {
/*       */       
/*  6480 */       Set set = this.charges_NewModels_ExistingFC_TM.keySet();
/*  6481 */       Iterator<String> iterator = set.iterator();
/*  6482 */       while (iterator.hasNext()) {
/*       */         
/*  6484 */         String str = iterator.next();
/*       */         
/*  6486 */         if (paramString.equals(parseString(str, 1))) {
/*       */           
/*  6488 */           String str1 = parseString(str, 3);
/*  6489 */           treeSet.add(str1);
/*  6490 */           treeMap2.put(str, this.charges_NewModels_ExistingFC_TM.get(str));
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  6495 */     retrieveChargesFeaturesFormat2_MTM(paramString, treeSet, treeMap1, treeMap2, paramBoolean, paramStringBuffer);
/*       */     
/*  6497 */     treeSet.clear();
/*  6498 */     treeSet = null;
/*  6499 */     treeMap1.clear();
/*  6500 */     treeMap1 = null;
/*  6501 */     treeMap2.clear();
/*  6502 */     treeMap2 = null;
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
/*       */   private void retrieveChargesFeaturesFormat2_MTM(String paramString, TreeSet paramTreeSet, TreeMap paramTreeMap1, TreeMap paramTreeMap2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  6517 */     if (paramTreeMap1.size() > 0 || paramTreeMap2.size() > 0) {
/*       */ 
/*       */       
/*  6520 */       String str = "";
/*  6521 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  6523 */         str = "of the IBM RS/6000 or pSeries " + paramString + " machine type:";
/*       */       }
/*  6525 */       else if (this.brand.equals("xSeries")) {
/*       */         
/*  6527 */         str = "of the IBM xSeries " + paramString + " machine type:";
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*  6533 */       else if (this.brand.equals("totalStorage")) {
/*       */         
/*  6535 */         str = "of the Total Storage " + paramString + " machine type:";
/*       */       } 
/*       */       
/*  6538 */       str = ":p.The following are newly announced features on the specified models " + str;
/*  6539 */       String[] arrayOfString = extractStringLines(str, 70); byte b;
/*  6540 */       for (b = 0; b < arrayOfString.length; b++)
/*       */       {
/*  6542 */         paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */       }
/*       */       
/*  6545 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/*  6546 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*       */       
/*  6548 */       paramStringBuffer.append("                                             Minimum" + NEWLINE);
/*  6549 */       paramStringBuffer.append("                                             Maint.  Initial/" + NEWLINE);
/*  6550 */       paramStringBuffer.append("                     Model  Feature Purchase Charge  MES/         RP" + NEWLINE);
/*  6551 */       paramStringBuffer.append("Description          Number Numbers Price    Monthly Both     CSU MES" + NEWLINE);
/*  6552 */       paramStringBuffer.append("-------------------- ------ ------- -------- ------- -------- --- ---" + NEWLINE);
/*  6553 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  6554 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */       
/*  6556 */       if (paramTreeSet.size() > 0) {
/*       */         
/*  6558 */         String str1 = "";
/*  6559 */         Iterator<String> iterator = paramTreeSet.iterator();
/*       */         
/*  6561 */         paramStringBuffer.append(".pa" + NEWLINE);
/*  6562 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/*  6564 */         if (this.brand.equals("pSeries")) {
/*       */           
/*  6566 */           str1 = "RS/6000 or pSeries MT " + paramString;
/*  6567 */           paramStringBuffer.append(str1 + NEWLINE);
/*       */         }
/*  6569 */         else if (this.brand.equals("xSeries")) {
/*       */           
/*  6571 */           str1 = "xSeries " + paramString;
/*  6572 */           paramStringBuffer.append(str1 + NEWLINE);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*       */         }
/*  6579 */         else if (this.brand.equals("totalStorage")) {
/*       */           
/*  6581 */           str1 = "Total Storage " + paramString;
/*  6582 */           paramStringBuffer.append(str1 + NEWLINE);
/*       */         } 
/*       */         
/*  6585 */         while (iterator.hasNext()) {
/*       */           
/*  6587 */           String str2 = iterator.next();
/*       */           
/*  6589 */           paramStringBuffer.append(setString("", 22));
/*  6590 */           paramStringBuffer.append(str2);
/*  6591 */           paramStringBuffer.append(setString("", 12));
/*  6592 */           paramStringBuffer.append("XXXX.XX");
/*  6593 */           paramStringBuffer.append(" ");
/*  6594 */           paramStringBuffer.append("XXXX.XX");
/*  6595 */           paramStringBuffer.append(setString("", 10));
/*  6596 */           paramStringBuffer.append(this.productNumber_NewModels_HT.get(str2));
/*  6597 */           paramStringBuffer.append(NEWLINE);
/*       */         } 
/*  6599 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       } 
/*       */ 
/*       */       
/*  6603 */       retrieveChargesFeaturesFormat2_MTM(paramString, paramTreeMap1, paramBoolean, paramStringBuffer);
/*       */       
/*  6605 */       if (paramTreeMap2.size() > 0) {
/*       */         
/*  6607 */         str = ":p.The following are features already announced for the " + paramString + " machine type:";
/*  6608 */         arrayOfString = extractStringLines(str, 70);
/*  6609 */         for (b = 0; b < arrayOfString.length; b++)
/*       */         {
/*  6611 */           paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */         }
/*       */         
/*  6614 */         paramStringBuffer.append(".RH ON" + NEWLINE);
/*  6615 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/*  6617 */         paramStringBuffer.append("                                             Minimum" + NEWLINE);
/*  6618 */         paramStringBuffer.append("                                             Maint.  Initial/" + NEWLINE);
/*  6619 */         paramStringBuffer.append("                     Model  Feature Purchase Charge  MES/         RP" + NEWLINE);
/*  6620 */         paramStringBuffer.append("Description          Number Numbers Price    Monthly Both     CSU MES" + NEWLINE);
/*  6621 */         paramStringBuffer.append("-------------------- ------ ------- -------- ------- -------- --- ---" + NEWLINE);
/*  6622 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*  6623 */         paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */       } 
/*       */       
/*  6626 */       retrieveChargesFeaturesFormat2_MTM(paramString, paramTreeMap2, paramBoolean, paramStringBuffer);
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
/*  6640 */     if (paramTreeMap.size() > 0) {
/*       */       
/*  6642 */       String str1 = "";
/*  6643 */       String str2 = "";
/*  6644 */       String str3 = "";
/*  6645 */       String str4 = "";
/*  6646 */       String str5 = "";
/*  6647 */       String str6 = "";
/*       */       
/*  6649 */       String[] arrayOfString = null;
/*  6650 */       boolean bool = false;
/*  6651 */       Set set = paramTreeMap.keySet();
/*  6652 */       Iterator<String> iterator = set.iterator();
/*  6653 */       while (iterator.hasNext()) {
/*       */         
/*  6655 */         String str = iterator.next();
/*  6656 */         str2 = (String)paramTreeMap.get(str);
/*  6657 */         str4 = parseString(str, 2);
/*  6658 */         str6 = paramString;
/*  6659 */         if (!str4.equals(str3) || !str6.equals(str5)) {
/*       */           
/*  6661 */           arrayOfString = extractStringLines(parseString(str, 4), 51);
/*  6662 */           bool = true;
/*       */         } 
/*  6664 */         setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */         
/*  6666 */         if (parseString(str, 8).length() > 0)
/*       */         {
/*  6668 */           if (!str4.equals(str3)) {
/*       */             
/*  6670 */             String[] arrayOfString1 = getStringTokens(parseString(str, 8), NEWLINE);
/*  6671 */             for (byte b = 0; b < arrayOfString1.length; b++) {
/*       */               
/*  6673 */               if (arrayOfString1[b].length() > 58) {
/*       */                 
/*  6675 */                 String[] arrayOfString2 = extractStringLines(arrayOfString1[b], 58);
/*  6676 */                 for (byte b1 = 0; b1 < arrayOfString2.length; b1++)
/*       */                 {
/*  6678 */                   paramStringBuffer.append(":hp2." + arrayOfString2[b1] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  6683 */                 paramStringBuffer.append(":hp2." + arrayOfString1[b] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         }
/*       */         
/*  6689 */         if (bool) {
/*       */           
/*  6691 */           for (byte b = 0; b < arrayOfString.length; b++)
/*       */           {
/*  6693 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           }
/*  6695 */           bool = false;
/*       */         } 
/*       */         
/*  6698 */         paramStringBuffer.append(setString("", 22));
/*  6699 */         paramStringBuffer.append(parseString(str, 3));
/*  6700 */         paramStringBuffer.append("      ");
/*       */         
/*  6702 */         if (!str4.equals(str3)) {
/*       */           
/*  6704 */           paramStringBuffer.append(parseString(str, 2));
/*       */         }
/*       */         else {
/*       */           
/*  6708 */           paramStringBuffer.append("    ");
/*       */         } 
/*  6710 */         paramStringBuffer.append("  ");
/*       */         
/*  6712 */         if (!str4.equals(str3)) {
/*       */           
/*  6714 */           paramStringBuffer.append(parseString(str, 9));
/*       */         }
/*       */         else {
/*       */           
/*  6718 */           paramStringBuffer.append("       ");
/*       */         } 
/*  6720 */         paramStringBuffer.append("         ");
/*  6721 */         paramStringBuffer.append(parseString(str, 5));
/*  6722 */         paramStringBuffer.append("  ");
/*  6723 */         paramStringBuffer.append(parseString(str, 6));
/*  6724 */         paramStringBuffer.append(" ");
/*  6725 */         paramStringBuffer.append(parseString(str, 7));
/*  6726 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  6728 */         str1 = str2;
/*  6729 */         str3 = str4;
/*  6730 */         str5 = str6;
/*       */       } 
/*  6732 */       if (!str2.equals("WW"))
/*       */       {
/*  6734 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/*  6737 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  6738 */       paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
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
/*  6751 */     TreeMap<Object, Object> treeMap1 = new TreeMap<>();
/*  6752 */     TreeMap<Object, Object> treeMap2 = new TreeMap<>();
/*       */     
/*  6754 */     if (this.charges_ExistingModels_NewFC_TM.size() > 0) {
/*       */       
/*  6756 */       Set set = this.charges_ExistingModels_NewFC_TM.keySet();
/*  6757 */       Iterator<String> iterator = set.iterator();
/*  6758 */       while (iterator.hasNext()) {
/*       */         
/*  6760 */         String str = iterator.next();
/*       */         
/*  6762 */         if (paramString.equals(parseString(str, 1)))
/*       */         {
/*  6764 */           treeMap1.put(str, this.charges_ExistingModels_NewFC_TM.get(str));
/*       */         }
/*       */       } 
/*       */     } 
/*       */     
/*  6769 */     if (this.charges_ExistingModels_ExistingFC_TM.size() > 0) {
/*       */       
/*  6771 */       Set set = this.charges_ExistingModels_ExistingFC_TM.keySet();
/*  6772 */       Iterator<String> iterator = set.iterator();
/*  6773 */       while (iterator.hasNext()) {
/*       */         
/*  6775 */         String str = iterator.next();
/*       */         
/*  6777 */         if (paramString.equals(parseString(str, 1)))
/*       */         {
/*  6779 */           treeMap2.put(str, this.charges_ExistingModels_ExistingFC_TM.get(str));
/*       */         }
/*       */       } 
/*       */     } 
/*       */     
/*  6784 */     retrieveChargesFeaturesFormat2_Feature(1, treeMap1, paramString, paramBoolean, paramStringBuffer);
/*  6785 */     retrieveChargesFeaturesFormat2_Feature(2, treeMap2, paramString, paramBoolean, paramStringBuffer);
/*       */     
/*  6787 */     treeMap1.clear();
/*  6788 */     treeMap1 = null;
/*  6789 */     treeMap2.clear();
/*  6790 */     treeMap2 = null;
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
/*  6804 */     if (paramTreeMap.size() > 0) {
/*       */       String[] arrayOfString1; byte b;
/*  6806 */       String str1 = "";
/*  6807 */       String str2 = "";
/*  6808 */       String str3 = "";
/*  6809 */       String str4 = "";
/*  6810 */       String str5 = "";
/*  6811 */       String str6 = "";
/*  6812 */       String str7 = "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  6820 */       TreeSet<String> treeSet = new TreeSet();
/*  6821 */       Set set2 = paramTreeMap.keySet();
/*  6822 */       Iterator<String> iterator2 = set2.iterator();
/*  6823 */       while (iterator2.hasNext()) {
/*       */         
/*  6825 */         String str8 = iterator2.next();
/*  6826 */         String str9 = parseString(str8, 3);
/*  6827 */         treeSet.add(str9);
/*       */       } 
/*       */ 
/*       */       
/*  6831 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  6833 */         str7 = "of the IBM RS/6000 or pSeries " + paramString + " machine type:";
/*       */       }
/*  6835 */       else if (this.brand.equals("xSeries")) {
/*       */         
/*  6837 */         str7 = "of the IBM xSeries " + paramString + " machine type:";
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*  6843 */       else if (this.brand.equals("totalStorage")) {
/*       */         
/*  6845 */         str7 = "of the Total Storage " + paramString + " machine type:";
/*       */       } 
/*       */       
/*  6848 */       switch (paramInt) {
/*       */         
/*       */         case 1:
/*  6851 */           str7 = ":p.The following are newly announced features on the specified models " + str7;
/*  6852 */           arrayOfString1 = extractStringLines(str7, 70);
/*  6853 */           for (b = 0; b < arrayOfString1.length; b++)
/*       */           {
/*  6855 */             paramStringBuffer.append(arrayOfString1[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */         
/*       */         case 2:
/*  6860 */           str7 = ":p.The following are features already announced for the " + paramString + " machine type:";
/*  6861 */           arrayOfString1 = extractStringLines(str7, 70);
/*  6862 */           for (b = 0; b < arrayOfString1.length; b++)
/*       */           {
/*  6864 */             paramStringBuffer.append(arrayOfString1[b] + NEWLINE);
/*       */           }
/*       */           break;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  6872 */       paramStringBuffer.append(".RH ON" + NEWLINE);
/*  6873 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*       */       
/*  6875 */       paramStringBuffer.append("                                                     Initial/" + NEWLINE);
/*  6876 */       paramStringBuffer.append("                                                     MES/" + NEWLINE);
/*  6877 */       paramStringBuffer.append("Description                  Model  Feature Purchase Both/        RP" + NEWLINE);
/*  6878 */       paramStringBuffer.append("Machine Type ");
/*  6879 */       paramStringBuffer.append(setString(paramString, 4));
/*  6880 */       paramStringBuffer.append("            number numbers price    Support  CSU MES" + NEWLINE);
/*  6881 */       paramStringBuffer.append("---------------------------- ------ ------- -------- -------- --- ---" + NEWLINE);
/*  6882 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  6883 */       paramStringBuffer.append(".RH OFF" + NEWLINE);
/*       */       
/*  6885 */       String[] arrayOfString2 = null;
/*  6886 */       boolean bool1 = false;
/*  6887 */       boolean bool2 = false;
/*  6888 */       Set set1 = paramTreeMap.keySet();
/*  6889 */       Iterator<String> iterator1 = set1.iterator();
/*  6890 */       while (iterator1.hasNext()) {
/*       */         
/*  6892 */         String str = iterator1.next();
/*  6893 */         str2 = (String)paramTreeMap.get(str);
/*  6894 */         str4 = parseString(str, 2);
/*  6895 */         str6 = paramString;
/*  6896 */         if (!str4.equals(str3) || !str6.equals(str5)) {
/*       */           
/*  6898 */           arrayOfString2 = extractStringLines(parseString(str, 4), 51);
/*  6899 */           bool2 = true;
/*       */         } 
/*  6901 */         setGeoTags3(str1, str2, str5, str6, str3, str4, paramBoolean, paramStringBuffer);
/*       */         
/*  6903 */         if (parseString(str, 8).length() > 0)
/*       */         {
/*  6905 */           if (!str4.equals(str3)) {
/*       */             
/*  6907 */             String[] arrayOfString = getStringTokens(parseString(str, 8), NEWLINE);
/*  6908 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */               
/*  6910 */               if (arrayOfString[b1].length() > 58) {
/*       */                 
/*  6912 */                 String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  6913 */                 for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */                 {
/*  6915 */                   paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  6920 */                 paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         }
/*       */         
/*  6926 */         if (bool2) {
/*       */           
/*  6928 */           for (byte b1 = 0; b1 < arrayOfString2.length; b1++)
/*       */           {
/*  6930 */             paramStringBuffer.append(arrayOfString2[b1] + NEWLINE);
/*       */           }
/*  6932 */           bool2 = false;
/*       */         } 
/*       */         
/*  6935 */         paramStringBuffer.append(setString("", 30));
/*  6936 */         paramStringBuffer.append(parseString(str, 3));
/*  6937 */         paramStringBuffer.append("      ");
/*       */         
/*  6939 */         if (!str4.equals(str3)) {
/*       */           
/*  6941 */           paramStringBuffer.append(parseString(str, 2));
/*       */         }
/*       */         else {
/*       */           
/*  6945 */           paramStringBuffer.append("    ");
/*       */         } 
/*  6947 */         paramStringBuffer.append("  ");
/*       */         
/*  6949 */         if (!str4.equals(str3)) {
/*       */           
/*  6951 */           paramStringBuffer.append(parseString(str, 9));
/*       */         }
/*       */         else {
/*       */           
/*  6955 */           paramStringBuffer.append("       ");
/*       */         } 
/*  6957 */         paramStringBuffer.append(" ");
/*  6958 */         paramStringBuffer.append(parseString(str, 5));
/*  6959 */         paramStringBuffer.append("  ");
/*  6960 */         paramStringBuffer.append(parseString(str, 6));
/*  6961 */         paramStringBuffer.append(" ");
/*  6962 */         paramStringBuffer.append(parseString(str, 7));
/*  6963 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  6965 */         str1 = str2;
/*  6966 */         str3 = str4;
/*  6967 */         str5 = str6;
/*       */       } 
/*  6969 */       if (!str2.equals("WW"))
/*       */       {
/*  6971 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       
/*  6974 */       paramStringBuffer.append(":exmp." + NEWLINE);
/*  6975 */       paramStringBuffer.append(".RH CANCEL" + NEWLINE + NEWLINE);
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
/*  6987 */     if (this.productNumber_MTM_Conversions_TM.size() > 0) {
/*       */       
/*  6989 */       String str1 = "";
/*  6990 */       String str2 = "";
/*       */ 
/*       */       
/*  6993 */       if (paramBoolean == true) {
/*       */         
/*  6995 */         paramStringBuffer.append(":h3.Type/Model Conversions" + NEWLINE);
/*  6996 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  6997 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  7001 */         paramStringBuffer.append("<h3>Type/Model Conversions</h3>" + NEWLINE);
/*       */       } 
/*       */       
/*  7004 */       paramStringBuffer.append("   From       To      Parts     Purchase" + NEWLINE);
/*  7005 */       paramStringBuffer.append("Type Model Type Model Returned  Price" + NEWLINE);
/*  7006 */       paramStringBuffer.append("---- ----- ---- ----- --------  --------" + NEWLINE);
/*  7007 */       Set set = this.productNumber_MTM_Conversions_TM.keySet();
/*  7008 */       Iterator<String> iterator = set.iterator();
/*  7009 */       while (iterator.hasNext()) {
/*       */         
/*  7011 */         String str = iterator.next();
/*  7012 */         str2 = (String)this.productNumber_MTM_Conversions_TM.get(str);
/*  7013 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */         
/*  7015 */         paramStringBuffer.append(parseString(str, 3));
/*  7016 */         paramStringBuffer.append("  ");
/*  7017 */         paramStringBuffer.append(parseString(str, 4));
/*  7018 */         paramStringBuffer.append("  ");
/*  7019 */         paramStringBuffer.append(parseString(str, 1));
/*  7020 */         paramStringBuffer.append("  ");
/*  7021 */         paramStringBuffer.append(parseString(str, 2));
/*  7022 */         paramStringBuffer.append("     ");
/*  7023 */         paramStringBuffer.append(parseString(str, 5));
/*  7024 */         paramStringBuffer.append("       ");
/*  7025 */         paramStringBuffer.append("$XXXX");
/*  7026 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  7028 */         str1 = str2;
/*       */       } 
/*  7030 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7036 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7042 */       if (paramBoolean == true)
/*       */       {
/*  7044 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  7061 */     if (this.productNumber_Model_Conversions_TM.size() > 0) {
/*       */       
/*  7063 */       String str1 = "";
/*  7064 */       String str2 = "";
/*       */ 
/*       */       
/*  7067 */       if (paramBoolean == true) {
/*       */         
/*  7069 */         paramStringBuffer.append(":h3.Model Conversions" + NEWLINE);
/*  7070 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*  7071 */         paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  7075 */         paramStringBuffer.append("<h3>Model Conversions</h3>" + NEWLINE);
/*       */       } 
/*       */       
/*  7078 */       paramStringBuffer.append("      From   To      Parts     Purchase" + NEWLINE);
/*  7079 */       paramStringBuffer.append("Type  Model  Model   Returned  Price" + NEWLINE);
/*  7080 */       paramStringBuffer.append("----  -----  -----   --------  --------" + NEWLINE);
/*  7081 */       Set set = this.productNumber_Model_Conversions_TM.keySet();
/*  7082 */       Iterator<String> iterator = set.iterator();
/*  7083 */       while (iterator.hasNext()) {
/*       */         
/*  7085 */         String str = iterator.next();
/*  7086 */         str2 = (String)this.productNumber_Model_Conversions_TM.get(str);
/*  7087 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */         
/*  7089 */         paramStringBuffer.append(parseString(str, 1));
/*  7090 */         paramStringBuffer.append("   ");
/*  7091 */         paramStringBuffer.append(parseString(str, 3));
/*  7092 */         paramStringBuffer.append("    ");
/*  7093 */         paramStringBuffer.append(parseString(str, 2));
/*  7094 */         paramStringBuffer.append("       ");
/*  7095 */         paramStringBuffer.append(parseString(str, 4));
/*  7096 */         paramStringBuffer.append("       ");
/*  7097 */         paramStringBuffer.append("$XXXX");
/*  7098 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  7100 */         str1 = str2;
/*       */       } 
/*  7102 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7108 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7114 */       if (paramBoolean == true)
/*       */       {
/*  7116 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  7133 */     if (this.charges_Feature_Conversions_TM.size() > 0) {
/*       */       
/*  7135 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  7136 */       String str1 = "";
/*  7137 */       String str2 = "";
/*  7138 */       String str3 = "";
/*  7139 */       String str4 = "";
/*       */       
/*  7141 */       Set set = this.charges_Feature_Conversions_TM.keySet();
/*  7142 */       Iterator<String> iterator = set.iterator();
/*  7143 */       while (iterator.hasNext()) {
/*       */         
/*  7145 */         str1 = iterator.next();
/*  7146 */         str2 = parseString(str1, 4);
/*  7147 */         str3 = parseString(str1, 7);
/*  7148 */         str4 = str2 + "<:>" + str3 + "<:>" + parseString(str1, 10) + "<:>" + parseString(str1, 11);
/*  7149 */         treeMap.put(str4, this.charges_Feature_Conversions_TM.get(str1));
/*       */       } 
/*       */       
/*  7152 */       retrieveChargesFeatureConverstionsFormat1(paramBoolean, paramStringBuffer, treeMap);
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
/*  7165 */     String str1 = "";
/*  7166 */     String str2 = "";
/*       */     
/*  7168 */     if (1 == this.format) {
/*       */ 
/*       */ 
/*       */       
/*  7172 */       paramStringBuffer.append(":h3.Conversions" + NEWLINE);
/*  7173 */       paramStringBuffer.append(":p." + NEWLINE);
/*  7174 */       paramStringBuffer.append(":h5.Feature Conversions" + NEWLINE);
/*  7175 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*  7176 */       paramStringBuffer.append(".kp off" + NEWLINE);
/*  7177 */       paramStringBuffer.append("               Parts       Purchase" + NEWLINE);
/*  7178 */       paramStringBuffer.append("From:   To:    Returned    Price" + NEWLINE);
/*  7179 */       paramStringBuffer.append("----    ---    --------    --------" + NEWLINE);
/*  7180 */       Set set = paramTreeMap.keySet();
/*  7181 */       Iterator<String> iterator = set.iterator();
/*  7182 */       while (iterator.hasNext()) {
/*       */         
/*  7184 */         String str = iterator.next();
/*  7185 */         str2 = (String)paramTreeMap.get(str);
/*  7186 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  7187 */         if (parseString(str, 4).length() > 0) {
/*       */           
/*  7189 */           if (!paramBoolean) {
/*       */ 
/*       */             
/*  7192 */             String[] arrayOfString = getStringTokens(parseString(str, 4), NEWLINE);
/*  7193 */             for (byte b = 0; b < arrayOfString.length; b++)
/*       */             {
/*  7195 */               paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */             }
/*       */           } 
/*       */           
/*  7199 */           if (paramBoolean == true) {
/*       */             
/*  7201 */             String[] arrayOfString = getStringTokens(parseString(str, 4), NEWLINE);
/*  7202 */             for (byte b = 0; b < arrayOfString.length; b++) {
/*       */               
/*  7204 */               if (arrayOfString[b].length() > 58) {
/*       */                 
/*  7206 */                 String[] arrayOfString1 = extractStringLines(arrayOfString[b], 58);
/*  7207 */                 for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */                 {
/*  7209 */                   paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  7214 */                 paramStringBuffer.append(":hp2." + arrayOfString[b] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         
/*  7220 */         paramStringBuffer.append(parseString(str, 2));
/*  7221 */         paramStringBuffer.append("    ");
/*  7222 */         paramStringBuffer.append(parseString(str, 1));
/*  7223 */         paramStringBuffer.append("      ");
/*  7224 */         paramStringBuffer.append(parseString(str, 3));
/*  7225 */         paramStringBuffer.append("         $XXXX");
/*  7226 */         paramStringBuffer.append(NEWLINE);
/*       */         
/*  7228 */         str1 = str2;
/*       */       } 
/*  7230 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7236 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7242 */       if (paramBoolean == true)
/*       */       {
/*  7244 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7250 */       paramStringBuffer.append(":p.:hp2.US, LA, CAN--->:ehp2." + NEWLINE);
/*  7251 */       paramStringBuffer.append(":p.:hp2.Parts Return::ehp2.  Parts removed or replaced as part of MES upgrades" + NEWLINE);
/*  7252 */       paramStringBuffer.append("or conversions become the property of IBM and must be returned." + NEWLINE);
/*  7253 */       paramStringBuffer.append(".br;:hp2.<---US, LA, CAN:ehp2." + NEWLINE + NEWLINE);
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
/*  7265 */     if (this.charges_Feature_Conversions_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7274 */       if (paramBoolean == true) {
/*       */         
/*  7276 */         paramStringBuffer.append(":h3.Feature Conversions" + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  7280 */         paramStringBuffer.append("<h3>Feature Conversions</h3>" + NEWLINE);
/*       */       } 
/*       */ 
/*       */       
/*  7284 */       TreeSet<String> treeSet = new TreeSet();
/*  7285 */       Set<String> set = this.charges_Feature_Conversions_TM.keySet();
/*  7286 */       Iterator<String> iterator1 = set.iterator();
/*  7287 */       while (iterator1.hasNext()) {
/*       */         
/*  7289 */         String str = iterator1.next();
/*  7290 */         treeSet.add(parseString(str, 1));
/*       */       } 
/*       */       
/*  7293 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()]; byte b2;
/*  7294 */       for (b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  7296 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  7299 */       byte b1 = 0;
/*  7300 */       Iterator<String> iterator2 = treeSet.iterator();
/*  7301 */       while (iterator2.hasNext()) {
/*       */         
/*  7303 */         String str = iterator2.next();
/*       */         
/*  7305 */         iterator1 = set.iterator();
/*  7306 */         while (iterator1.hasNext()) {
/*       */           
/*  7308 */           String str1 = iterator1.next();
/*       */           
/*  7310 */           if (str.equals(parseString(str1, 1)))
/*       */           {
/*  7312 */             arrayOfTreeMap[b1].put(str1, this.charges_Feature_Conversions_TM.get(str1));
/*       */           }
/*       */         } 
/*  7315 */         b1++;
/*       */       } 
/*       */       
/*  7318 */       for (b2 = 0; b2 < arrayOfTreeMap.length; b2++)
/*       */       {
/*  7320 */         retrieveChargesFeatureConversionsFormat2(paramStringBuffer, paramBoolean, arrayOfTreeMap[b2]);
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
/*  7334 */     String str1 = "";
/*  7335 */     String str2 = "";
/*  7336 */     String str3 = parseString((String)paramTreeMap.firstKey(), 1);
/*  7337 */     if (paramBoolean == true) {
/*       */       
/*  7339 */       paramStringBuffer.append(":p." + NEWLINE);
/*  7340 */       paramStringBuffer.append(":h4." + str3 + NEWLINE);
/*  7341 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*  7342 */       paramStringBuffer.append(".kp off" + NEWLINE + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  7346 */       paramStringBuffer.append("<h4>" + str3 + "</h4>" + NEWLINE);
/*       */     } 
/*       */     
/*  7349 */     if (2 == this.format) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7357 */       paramStringBuffer.append("                                                  Parts      Purchase" + NEWLINE);
/*  7358 */       paramStringBuffer.append("From FC                  To FC                    Returned   Price" + NEWLINE);
/*  7359 */       paramStringBuffer.append("------------------------ ------------------------ ---------  --------" + NEWLINE);
/*  7360 */       int i = 0;
/*  7361 */       byte b = 0;
/*  7362 */       boolean bool = false;
/*  7363 */       Set set = paramTreeMap.keySet();
/*  7364 */       Iterator<String> iterator = set.iterator();
/*  7365 */       while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7371 */         String str6 = iterator.next();
/*  7372 */         str2 = (String)paramTreeMap.get(str6);
/*  7373 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  7374 */         if (parseString(str6, 11).length() > 0) {
/*       */           
/*  7376 */           if (!paramBoolean) {
/*       */ 
/*       */             
/*  7379 */             String[] arrayOfString = getStringTokens(parseString(str6, 11), NEWLINE);
/*  7380 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++)
/*       */             {
/*  7382 */               paramStringBuffer.append(arrayOfString[b1] + NEWLINE);
/*       */             }
/*       */           } 
/*       */           
/*  7386 */           if (paramBoolean == true) {
/*       */             
/*  7388 */             String[] arrayOfString = getStringTokens(parseString(str6, 11), NEWLINE);
/*  7389 */             for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */               
/*  7391 */               if (arrayOfString[b1].length() > 58) {
/*       */                 
/*  7393 */                 String[] arrayOfString3 = extractStringLines(arrayOfString[b1], 58);
/*  7394 */                 for (byte b2 = 0; b2 < arrayOfString3.length; b2++)
/*       */                 {
/*  7396 */                   paramStringBuffer.append(":hp2." + arrayOfString3[b2] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  7401 */                 paramStringBuffer.append(":hp2." + arrayOfString[b1] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         
/*  7407 */         String str4 = parseString(str6, 7) + " - " + parseString(str6, 9);
/*  7408 */         String str5 = parseString(str6, 4) + " - " + parseString(str6, 8);
/*  7409 */         String[] arrayOfString1 = extractStringLines(str4, 24);
/*  7410 */         String[] arrayOfString2 = extractStringLines(str5, 24);
/*  7411 */         i = 0;
/*  7412 */         if (arrayOfString1.length > arrayOfString2.length) {
/*       */           
/*  7414 */           i = arrayOfString1.length;
/*       */         }
/*       */         else {
/*       */           
/*  7418 */           i = arrayOfString2.length;
/*       */         } 
/*  7420 */         b = 0;
/*  7421 */         bool = false;
/*       */         
/*       */         while (true) {
/*  7424 */           if (false == bool) {
/*       */ 
/*       */             
/*  7427 */             paramStringBuffer.append(setString(arrayOfString1[b], 24));
/*  7428 */             paramStringBuffer.append(" ");
/*  7429 */             paramStringBuffer.append(setString(arrayOfString2[b], 24));
/*  7430 */             paramStringBuffer.append("    ");
/*  7431 */             paramStringBuffer.append(parseString(str6, 10));
/*  7432 */             paramStringBuffer.append("        $XXXX");
/*  7433 */             paramStringBuffer.append(NEWLINE);
/*  7434 */             bool = true;
/*  7435 */             b++;
/*       */           }
/*       */           else {
/*       */             
/*  7439 */             if (b < arrayOfString1.length) {
/*       */               
/*  7441 */               paramStringBuffer.append(setString(arrayOfString1[b], 24));
/*       */             }
/*       */             else {
/*       */               
/*  7445 */               paramStringBuffer.append(setString(" ", 24));
/*       */             } 
/*  7447 */             paramStringBuffer.append(" ");
/*  7448 */             if (b < arrayOfString2.length) {
/*       */               
/*  7450 */               paramStringBuffer.append(setString(arrayOfString2[b], 24));
/*       */             }
/*       */             else {
/*       */               
/*  7454 */               paramStringBuffer.append(setString(" ", 24));
/*       */             } 
/*  7456 */             paramStringBuffer.append(NEWLINE);
/*  7457 */             b++;
/*       */           } 
/*  7459 */           if (b >= i)
/*  7460 */             str1 = str2; 
/*       */         } 
/*  7462 */       }  if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7468 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7474 */       if (paramBoolean == true)
/*       */       {
/*  7476 */         paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
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
/*  7493 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  7494 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  7495 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7502 */     if (this.salesManual_TM.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7508 */       TreeSet<String> treeSet = new TreeSet();
/*  7509 */       Set<String> set = this.salesManual_TM.keySet();
/*  7510 */       Iterator<String> iterator2 = set.iterator();
/*  7511 */       while (iterator2.hasNext()) {
/*       */         
/*  7513 */         String str = iterator2.next();
/*  7514 */         treeSet.add(parseString(str, 1));
/*       */       } 
/*       */       
/*  7517 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  7518 */       for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  7520 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  7523 */       byte b1 = 0;
/*  7524 */       Iterator<String> iterator1 = treeSet.iterator();
/*  7525 */       while (iterator1.hasNext()) {
/*       */         
/*  7527 */         String str = iterator1.next();
/*       */         
/*  7529 */         iterator2 = set.iterator();
/*  7530 */         while (iterator2.hasNext()) {
/*       */           
/*  7532 */           String str1 = iterator2.next();
/*       */           
/*  7534 */           if (str.equals(parseString(str1, 1)))
/*       */           {
/*  7536 */             arrayOfTreeMap[b1].put(str1, this.salesManual_TM.get(str1));
/*       */           }
/*       */         } 
/*       */         
/*  7540 */         retrieveSalesManual(arrayOfTreeMap[b1], str, paramBoolean, paramStringBuffer);
/*  7541 */         b1++;
/*       */       } 
/*       */     } 
/*  7544 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
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
/*  7557 */     paramStringBuffer.append(NEWLINE + ":xmp." + NEWLINE);
/*  7558 */     paramStringBuffer.append(".kp off" + NEWLINE);
/*  7559 */     paramStringBuffer.append("*********************************************************************" + NEWLINE);
/*  7560 */     paramStringBuffer.append("NOTE TO THE EDITOR" + NEWLINE);
/*  7561 */     paramStringBuffer.append("THE FEATURE SECTION OF THE SALES MANUAL IS AUTOMATICALLY GENERATED" + NEWLINE);
/*  7562 */     paramStringBuffer.append("FROM THE PLAN OF RECORD. PLEASE DO THE FOLLOWING:" + NEWLINE);
/*  7563 */     paramStringBuffer.append("1) ADD THE FEATURE IN NUMERIC ORDER UNDER THE HEADING GIVEN. IF THE" + NEWLINE);
/*  7564 */     paramStringBuffer.append("WORD \"NONE\" APPEARS DO NOT ADD THE WORD \"NONE.\"" + NEWLINE);
/*  7565 */     paramStringBuffer.append("2) IF DESCRIPTIVE INFORMATION IS PROVIDED SUCH AS:" + NEWLINE);
/*  7566 */     paramStringBuffer.append("\"The following is a list of all feature codes.................\"" + NEWLINE);
/*  7567 */     paramStringBuffer.append("REPLACE THE EXISTING DESCRIPTIVE INFORMATION." + NEWLINE);
/*  7568 */     paramStringBuffer.append("IF SUCH DESCRIPTIVE INFORMATION IS NOT PROVIDED DO NOT ADD THESE" + NEWLINE);
/*  7569 */     paramStringBuffer.append("WORDS." + NEWLINE);
/*  7570 */     paramStringBuffer.append("*********************************************************************" + NEWLINE);
/*  7571 */     paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*  7572 */     retrieveSalesManualSpecifyFeatures(paramString, paramBoolean, paramStringBuffer);
/*  7573 */     retrieveSalesManualSpecialFeatures(paramString, paramBoolean, paramStringBuffer);
/*  7574 */     retrieveSalesManualFeatureDescriptions(paramTreeMap, paramString, paramBoolean, paramStringBuffer);
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
/*  7586 */     if (paramBoolean == true) {
/*       */       
/*  7588 */       paramStringBuffer.append(":h2.Specify Features for Machine Type " + paramString + NEWLINE);
/*  7589 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  7593 */       paramStringBuffer.append("<h2>Specify Features for Machine Type " + paramString + "</h2>" + NEWLINE);
/*  7594 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     } 
/*       */     
/*  7597 */     retrieveSalesManualFeatures(paramString, this.salesManualSpecifyFeatures_TM, paramBoolean, paramStringBuffer);
/*  7598 */     paramStringBuffer.append(":eul." + NEWLINE);
/*  7599 */     paramStringBuffer.append(".sk 1" + NEWLINE + NEWLINE);
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
/*  7611 */     if (paramBoolean == true) {
/*       */       
/*  7613 */       paramStringBuffer.append(":h2.Special Features for Machine Type " + paramString + NEWLINE);
/*  7614 */       paramStringBuffer.append(":h3.Special Features - Initial Orders" + NEWLINE);
/*  7615 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  7619 */       paramStringBuffer.append("<h2>Special Features for Machine Type " + paramString + "</h2>" + NEWLINE);
/*  7620 */       paramStringBuffer.append("<h3>Special Features - Initial Orders</h3>" + NEWLINE);
/*  7621 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     } 
/*       */     
/*  7624 */     retrieveSalesManualFeatures(paramString, this.salesManualSpecialFeaturesInitialOrder_TM, paramBoolean, paramStringBuffer);
/*  7625 */     paramStringBuffer.append(":eul." + NEWLINE);
/*  7626 */     paramStringBuffer.append(".sk 1" + NEWLINE + NEWLINE);
/*       */     
/*  7628 */     if (paramBoolean == true) {
/*       */       
/*  7630 */       paramStringBuffer.append(":h3.Special Features - Plant and/or Field Installable" + NEWLINE);
/*  7631 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  7635 */       paramStringBuffer.append("<h3>Special Features - Plant and/or Field Installable</h3>" + NEWLINE);
/*  7636 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */     } 
/*       */     
/*  7639 */     retrieveSalesManualFeatures(paramString, this.salesManualSpecialFeaturesOther_TM, paramBoolean, paramStringBuffer);
/*  7640 */     paramStringBuffer.append(":eul." + NEWLINE);
/*  7641 */     paramStringBuffer.append(".************************************************************" + NEWLINE + NEWLINE);
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
/*  7654 */     if (paramTreeMap.size() > 0) {
/*       */ 
/*       */       
/*  7657 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  7658 */       Set<String> set = paramTreeMap.keySet();
/*  7659 */       Iterator<String> iterator = set.iterator();
/*  7660 */       while (iterator.hasNext()) {
/*       */         
/*  7662 */         String str1 = iterator.next();
/*  7663 */         String str2 = (String)paramTreeMap.get(str1);
/*       */         
/*  7665 */         if (paramString.equals(parseString(str1, 1)))
/*       */         {
/*  7667 */           treeMap.put(str1, str2);
/*       */         }
/*       */       } 
/*       */       
/*  7671 */       if (treeMap.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7677 */         TreeSet<String> treeSet = new TreeSet();
/*  7678 */         set = treeMap.keySet();
/*  7679 */         iterator = set.iterator();
/*  7680 */         while (iterator.hasNext()) {
/*       */           
/*  7682 */           String str = iterator.next();
/*  7683 */           treeSet.add(parseString(str, 2));
/*       */         } 
/*       */         
/*  7686 */         TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  7687 */         for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */         {
/*  7689 */           arrayOfTreeMap[b2] = new TreeMap<>();
/*       */         }
/*       */         
/*  7692 */         byte b1 = 0;
/*  7693 */         Iterator<String> iterator1 = treeSet.iterator();
/*  7694 */         while (iterator1.hasNext())
/*       */         {
/*  7696 */           String str = iterator1.next();
/*       */           
/*  7698 */           iterator = set.iterator();
/*  7699 */           while (iterator.hasNext()) {
/*       */             
/*  7701 */             String str1 = iterator.next();
/*       */             
/*  7703 */             if (str.equals(parseString(str1, 2)))
/*       */             {
/*  7705 */               arrayOfTreeMap[b1].put(str1, treeMap.get(str1));
/*       */             }
/*       */           } 
/*       */           
/*  7709 */           retrieveSalesManualFeatures(arrayOfTreeMap[b1], str, paramBoolean, paramStringBuffer);
/*  7710 */           b1++;
/*       */         }
/*       */       
/*       */       } else {
/*       */         
/*  7715 */         paramStringBuffer.append(":li.NONE" + NEWLINE);
/*       */       
/*       */       }
/*       */     
/*       */     }
/*       */     else {
/*       */       
/*  7722 */       paramStringBuffer.append(":li.NONE" + NEWLINE);
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
/*  7738 */     if (paramTreeMap.size() > 0) {
/*       */ 
/*       */ 
/*       */       
/*  7742 */       String str1 = "";
/*  7743 */       String str2 = "";
/*       */       
/*  7745 */       paramStringBuffer.append(":li." + paramString + NEWLINE);
/*  7746 */       paramStringBuffer.append(":ul c." + NEWLINE);
/*       */       
/*  7748 */       Set set = paramTreeMap.keySet();
/*  7749 */       Iterator<String> iterator = set.iterator();
/*  7750 */       while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */         
/*  7754 */         String str4 = iterator.next();
/*  7755 */         str2 = (String)paramTreeMap.get(str4);
/*  7756 */         setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*  7757 */         if (parseString(str4, 5).length() > 0) {
/*       */           
/*  7759 */           if (!paramBoolean) {
/*       */ 
/*       */             
/*  7762 */             String[] arrayOfString1 = getStringTokens(parseString(str4, 5), NEWLINE);
/*  7763 */             for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */             {
/*  7765 */               paramStringBuffer.append(arrayOfString1[b1] + NEWLINE);
/*       */             }
/*       */           } 
/*       */           
/*  7769 */           if (paramBoolean == true) {
/*       */             
/*  7771 */             String[] arrayOfString1 = getStringTokens(parseString(str4, 5), NEWLINE);
/*  7772 */             for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */               
/*  7774 */               if (arrayOfString1[b1].length() > 68) {
/*       */                 
/*  7776 */                 String[] arrayOfString2 = extractStringLines(arrayOfString1[b1], 68);
/*  7777 */                 for (byte b2 = 0; b2 < arrayOfString2.length; b2++)
/*       */                 {
/*  7779 */                   paramStringBuffer.append(":hp2." + arrayOfString2[b2] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  7784 */                 paramStringBuffer.append(":hp2." + arrayOfString1[b1] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */         
/*  7790 */         String str3 = ":li.(#" + parseString(str4, 3) + ") - " + parseString(str4, 4);
/*  7791 */         String[] arrayOfString = extractStringLines(str3, 79);
/*  7792 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*       */           
/*  7794 */           paramStringBuffer.append(arrayOfString[b]);
/*  7795 */           paramStringBuffer.append(NEWLINE);
/*       */         } 
/*  7797 */         str1 = str2;
/*       */       } 
/*  7799 */       if (!str2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7805 */         bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7811 */       paramStringBuffer.append(":eul." + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  7815 */       paramStringBuffer.append(":li.NONE" + NEWLINE);
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
/*  7831 */     if (paramBoolean == true) {
/*       */       
/*  7833 */       paramStringBuffer.append(":h2.Feature Descriptions" + NEWLINE);
/*  7834 */       paramStringBuffer.append(":p.The following is a list of all feature codes in numeric order for" + NEWLINE);
/*  7835 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  7837 */         paramStringBuffer.append("the IBM RS/6000 or pSeries " + paramString + " machine type." + NEWLINE);
/*       */       }
/*  7839 */       else if (this.brand.equals("iSeries") || this.brand.equals("totalStorage") || this.brand.equals("xSeries") || this.brand.equals("zSeries")) {
/*       */         
/*  7841 */         paramStringBuffer.append(paramString + " machine type." + NEWLINE);
/*       */       } 
/*  7843 */       paramStringBuffer.append(NEWLINE);
/*  7844 */       paramStringBuffer.append(":p.Attributes, as defined in the following feature descriptions," + NEWLINE);
/*  7845 */       paramStringBuffer.append("state the interaction of requirements among features." + NEWLINE);
/*  7846 */       paramStringBuffer.append(NEWLINE);
/*  7847 */       paramStringBuffer.append(":p.Minimums and maximums are the absolute limits for a single" + NEWLINE);
/*  7848 */       paramStringBuffer.append("feature without regard to interaction with other features.  The" + NEWLINE);
/*  7849 */       paramStringBuffer.append("maximum valid quantity for MES orders may be different than for" + NEWLINE);
/*  7850 */       paramStringBuffer.append("initial orders.  The maximums listed below refer to the largest" + NEWLINE);
/*  7851 */       paramStringBuffer.append("quantity of these two possibilities." + NEWLINE);
/*  7852 */       paramStringBuffer.append(NEWLINE);
/*  7853 */       paramStringBuffer.append(":p.The order type defines if a feature is orderable only on" + NEWLINE);
/*  7854 */       paramStringBuffer.append("initial orders, only on MES orders, on both initial and MES" + NEWLINE);
/*  7855 */       paramStringBuffer.append("orders, or if a feature is supported on a model due to a model" + NEWLINE);
/*  7856 */       paramStringBuffer.append("conversion.  Supported features cannot be ordered on the" + NEWLINE);
/*  7857 */       paramStringBuffer.append("converted model, only left on or removed from the converted" + NEWLINE);
/*  7858 */       paramStringBuffer.append("model." + NEWLINE + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  7862 */       paramStringBuffer.append("<h2>Sales Manual</h2>" + NEWLINE);
/*  7863 */       paramStringBuffer.append("<h2>Feature Descriptions</h2>" + NEWLINE);
/*  7864 */       paramStringBuffer.append("The following is a list of all feature codes in numeric order for" + NEWLINE);
/*  7865 */       if (this.brand.equals("pSeries")) {
/*       */         
/*  7867 */         paramStringBuffer.append("the IBM RS/6000 or pSeries " + paramString + "machine type." + NEWLINE);
/*       */       }
/*  7869 */       else if (this.brand.equals("iSeries") || this.brand.equals("totalStorage") || this.brand.equals("xSeries") || this.brand.equals("zSeries")) {
/*       */         
/*  7871 */         paramStringBuffer.append(paramString + " machine type." + NEWLINE);
/*       */       } 
/*  7873 */       paramStringBuffer.append(NEWLINE);
/*  7874 */       paramStringBuffer.append("Attributes, as defined in the following feature descriptions," + NEWLINE);
/*  7875 */       paramStringBuffer.append("state the interaction of requirements among features." + NEWLINE);
/*  7876 */       paramStringBuffer.append(NEWLINE);
/*  7877 */       paramStringBuffer.append("Minimums and maximums are the absolute limits for a single" + NEWLINE);
/*  7878 */       paramStringBuffer.append("feature without regard to interaction with other features.  The" + NEWLINE);
/*  7879 */       paramStringBuffer.append("maximum valid quantity for MES orders may be different than for" + NEWLINE);
/*  7880 */       paramStringBuffer.append("initial orders.  The maximums listed below refer to the largest" + NEWLINE);
/*  7881 */       paramStringBuffer.append("quantity of these two possibilities." + NEWLINE);
/*  7882 */       paramStringBuffer.append(NEWLINE);
/*  7883 */       paramStringBuffer.append("The order type defines if a feature is orderable only on" + NEWLINE);
/*  7884 */       paramStringBuffer.append("initial orders, only on MES orders, on both initial and MES" + NEWLINE);
/*  7885 */       paramStringBuffer.append("orders, or if a feature is supported on a model due to a model" + NEWLINE);
/*  7886 */       paramStringBuffer.append("conversion.  Supported features cannot be ordered on the" + NEWLINE);
/*  7887 */       paramStringBuffer.append("converted model, only left on or removed from the converted" + NEWLINE);
/*  7888 */       paramStringBuffer.append("model." + NEWLINE + NEWLINE);
/*       */     } 
/*  7890 */     if (paramTreeMap.size() > 0) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  7896 */       TreeSet<String> treeSet = new TreeSet();
/*  7897 */       Set<String> set = paramTreeMap.keySet();
/*  7898 */       Iterator<String> iterator2 = set.iterator();
/*  7899 */       while (iterator2.hasNext()) {
/*       */         
/*  7901 */         String str = iterator2.next();
/*  7902 */         treeSet.add(parseString(str, 3));
/*       */       } 
/*       */       
/*  7905 */       TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  7906 */       for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */       {
/*  7908 */         arrayOfTreeMap[b2] = new TreeMap<>();
/*       */       }
/*       */       
/*  7911 */       byte b1 = 0;
/*  7912 */       Iterator<String> iterator1 = treeSet.iterator();
/*  7913 */       while (iterator1.hasNext()) {
/*       */         
/*  7915 */         String str = iterator1.next();
/*       */         
/*  7917 */         iterator2 = set.iterator();
/*  7918 */         while (iterator2.hasNext()) {
/*       */           
/*  7920 */           String str1 = iterator2.next();
/*       */           
/*  7922 */           if (str.equals(parseString(str1, 3)))
/*       */           {
/*  7924 */             arrayOfTreeMap[b1].put(str1, paramTreeMap.get(str1));
/*       */           }
/*       */         } 
/*       */         
/*  7928 */         retrieveSalesManualFeatureDescriptions(arrayOfTreeMap[b1], paramStringBuffer);
/*  7929 */         b1++;
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
/*  7942 */     boolean bool = true;
/*  7943 */     String str = "";
/*       */     
/*  7945 */     if (paramTreeMap.size() > 0) {
/*       */       
/*  7947 */       Set set = paramTreeMap.keySet();
/*  7948 */       Iterator<String> iterator = set.iterator();
/*  7949 */       while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7955 */         String str4 = iterator.next();
/*  7956 */         str = (String)paramTreeMap.get(str4);
/*       */         
/*  7958 */         if (bool) {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  7963 */           if (!str.equals("WW")) {
/*       */             
/*  7965 */             paramStringBuffer.append(":p.:hp2.");
/*  7966 */             paramStringBuffer.append(str);
/*  7967 */             paramStringBuffer.append("--->:ehp2." + NEWLINE);
/*  7968 */             paramStringBuffer.append(".br" + NEWLINE);
/*       */           } 
/*       */           
/*  7971 */           if (parseString(str4, 14).length() > 0) {
/*       */             
/*  7973 */             String[] arrayOfString2 = getStringTokens(parseString(str4, 14), NEWLINE);
/*  7974 */             for (byte b2 = 0; b2 < arrayOfString2.length; b2++) {
/*       */               
/*  7976 */               if (arrayOfString2[b2].length() > 68) {
/*       */                 
/*  7978 */                 String[] arrayOfString3 = extractStringLines(arrayOfString2[b2], 68);
/*  7979 */                 for (byte b3 = 0; b3 < arrayOfString3.length; b3++)
/*       */                 {
/*  7981 */                   paramStringBuffer.append(":hp2." + arrayOfString3[b3] + ":ehp2." + NEWLINE);
/*       */                 }
/*       */               }
/*       */               else {
/*       */                 
/*  7986 */                 paramStringBuffer.append(":hp2." + arrayOfString2[b2] + ":ehp2." + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  7994 */           String str5 = ":h5.(#" + parseString(str4, 3) + ") - " + parseString(str4, 4);
/*  7995 */           String[] arrayOfString1 = extractStringLines(str5, 79);
/*  7996 */           for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */             
/*  7998 */             paramStringBuffer.append(arrayOfString1[b1]);
/*  7999 */             paramStringBuffer.append(NEWLINE);
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
/*  8015 */           String str6 = parseString(str4, 5);
/*  8016 */           if (str6.equals("")) {
/*       */             
/*  8018 */             paramStringBuffer.append(".*   DESCRIPTION FILE NOT FOUND" + NEWLINE);
/*  8019 */             paramStringBuffer.append(".*" + NEWLINE);
/*  8020 */             paramStringBuffer.append(".* BEGIN FEATURE TEMPLATE" + NEWLINE);
/*  8021 */             paramStringBuffer.append(".sk1" + NEWLINE);
/*  8022 */             paramStringBuffer.append(":ul c." + NEWLINE);
/*  8023 */             paramStringBuffer.append(".kp on" + NEWLINE);
/*  8024 */             if (2 == this.format)
/*       */             {
/*  8026 */               paramStringBuffer.append(":li.Attributes provided:" + NEWLINE);
/*  8027 */               paramStringBuffer.append(":li.Attributes required:" + NEWLINE);
/*       */             }
/*       */           
/*       */           } else {
/*       */             
/*  8032 */             String[] arrayOfString2 = getStringTokens(parseString(str4, 5), NEWLINE);
/*  8033 */             for (byte b2 = 0; b2 < arrayOfString2.length; b2++) {
/*       */               
/*  8035 */               if (arrayOfString2[b2].length() > 79) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/*  8053 */                 if (!arrayOfString2[b2].substring(0, 2).equals(".*"))
/*       */                 {
/*  8055 */                   String[] arrayOfString3 = extractStringLines(arrayOfString2[b2], 79);
/*  8056 */                   for (byte b3 = 0; b3 < arrayOfString3.length; b3++)
/*       */                   {
/*  8058 */                     paramStringBuffer.append(arrayOfString3[b3] + NEWLINE);
/*       */                   
/*       */                   }
/*       */                 }
/*       */               
/*       */               }
/*  8064 */               else if (!arrayOfString2[b2].substring(0, 2).equals(".*")) {
/*       */                 
/*  8066 */                 paramStringBuffer.append(arrayOfString2[b2] + NEWLINE);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */ 
/*       */           
/*  8072 */           paramStringBuffer.append(".kp off" + NEWLINE);
/*  8073 */           paramStringBuffer.append(".* MODEL SPECIFIC INFO SHOULD BE ADDED BELOW THIS LINE" + NEWLINE);
/*       */           
/*  8075 */           bool = false;
/*       */         } 
/*       */ 
/*       */         
/*  8079 */         paramStringBuffer.append(".kp on" + NEWLINE);
/*  8080 */         paramStringBuffer.append(":li.For ");
/*  8081 */         paramStringBuffer.append(parseString(str4, 1) + "-");
/*  8082 */         paramStringBuffer.append(parseString(str4, 2) + ": ");
/*  8083 */         paramStringBuffer.append("(#");
/*  8084 */         paramStringBuffer.append(parseString(str4, 3) + ")" + NEWLINE);
/*  8085 */         paramStringBuffer.append(":ul c." + NEWLINE);
/*  8086 */         if (1 == this.format) {
/*       */           
/*  8088 */           String str5 = parseString(str4, 6);
/*  8089 */           String str6 = parseString(str4, 7);
/*  8090 */           String str7 = parseString(str4, 8);
/*       */           
/*  8092 */           if (!str5.equals(""))
/*       */           {
/*  8094 */             paramStringBuffer.append(":li.Minimum required: " + str5 + NEWLINE);
/*       */           }
/*       */           
/*  8097 */           if (!str6.equals(""))
/*       */           {
/*  8099 */             paramStringBuffer.append(":li.Maximum allowed: " + str6 + "   ");
/*  8100 */             if (!str7.equals(""))
/*       */             {
/*  8102 */               paramStringBuffer.append("(Initial order maximum: " + str7 + " )" + NEWLINE);
/*       */             }
/*       */             else
/*       */             {
/*  8106 */               paramStringBuffer.append(NEWLINE);
/*       */             
/*       */             }
/*       */           
/*       */           }
/*  8111 */           else if (!str7.equals(""))
/*       */           {
/*  8113 */             paramStringBuffer.append(":li.(Initial order maximum: " + str7 + " )" + NEWLINE);
/*       */           }
/*       */         
/*       */         }
/*  8117 */         else if (2 == this.format) {
/*       */           
/*  8119 */           paramStringBuffer.append(":li.Minimum required: " + parseString(str4, 6) + NEWLINE);
/*  8120 */           paramStringBuffer.append(":li.Maximum allowed: " + parseString(str4, 7) + "   ");
/*  8121 */           paramStringBuffer.append("(Initial order maximum: " + parseString(str4, 8) + " )" + NEWLINE);
/*       */         } 
/*       */         
/*  8124 */         String str1 = ":li.OS level required: " + parseString(str4, 9);
/*  8125 */         String[] arrayOfString = getStringTokens(str1, NEWLINE);
/*  8126 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*       */           
/*  8128 */           if (arrayOfString[b].length() > 79) {
/*       */             
/*  8130 */             String[] arrayOfString1 = extractStringLines(arrayOfString[b], 79);
/*  8131 */             for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*       */             {
/*  8133 */               paramStringBuffer.append(arrayOfString1[b1] + NEWLINE);
/*       */             }
/*       */           }
/*       */           else {
/*       */             
/*  8138 */             paramStringBuffer.append(arrayOfString[b] + NEWLINE);
/*       */           } 
/*       */         } 
/*  8141 */         paramStringBuffer.append(":li.Initial Order/MES/Both/Supported: " + parseString(str4, 10) + NEWLINE);
/*  8142 */         paramStringBuffer.append(":li.CSU: " + parseString(str4, 11) + NEWLINE);
/*  8143 */         String str3 = parseString(str4, 12);
/*  8144 */         if (1 == this.format) {
/*       */           
/*  8146 */           if (!str3.equals(""))
/*       */           {
/*  8148 */             paramStringBuffer.append(":li.Return parts MES: " + parseString(str4, 12) + NEWLINE);
/*       */           }
/*       */         }
/*  8151 */         else if (2 == this.format) {
/*       */           
/*  8153 */           paramStringBuffer.append(":li.Return parts MES: " + parseString(str4, 12) + NEWLINE);
/*       */         } 
/*  8155 */         paramStringBuffer.append(":eul." + NEWLINE);
/*       */ 
/*       */         
/*  8158 */         String str2 = parseString(str4, 13);
/*  8159 */         if (str2.length() > 0) {
/*       */ 
/*       */           
/*  8162 */           str2 = ":NOTE." + str2;
/*  8163 */           String[] arrayOfString1 = getStringTokens(str2, NEWLINE);
/*  8164 */           for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*       */             
/*  8166 */             if (arrayOfString1[b1].length() > 79) {
/*       */               
/*  8168 */               String[] arrayOfString2 = extractStringLines(arrayOfString1[b1], 79);
/*  8169 */               for (byte b2 = 0; b2 < arrayOfString2.length; b2++)
/*       */               {
/*  8171 */                 paramStringBuffer.append(arrayOfString2[b2] + NEWLINE);
/*       */               }
/*       */             }
/*       */             else {
/*       */               
/*  8176 */               paramStringBuffer.append(arrayOfString1[b1] + NEWLINE);
/*       */             } 
/*       */           } 
/*       */         } 
/*  8180 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*       */       } 
/*       */       
/*  8183 */       paramStringBuffer.append(":eul." + NEWLINE);
/*  8184 */       if (!str.equals("WW")) {
/*       */         
/*  8186 */         paramStringBuffer.append(".br;:hp2.<---");
/*  8187 */         paramStringBuffer.append(str);
/*  8188 */         paramStringBuffer.append(":ehp2." + NEWLINE);
/*       */       } 
/*  8190 */       paramStringBuffer.append(".*" + NEWLINE);
/*  8191 */       paramStringBuffer.append(".******** END OF MODEL SPECIFIC INFO ************************" + NEWLINE);
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
/*  8210 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  8211 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  8212 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */     
/*  8214 */     TreeSet<String> treeSet = new TreeSet();
/*  8215 */     Set<String> set = this.supportedDevices_TM.keySet();
/*  8216 */     Iterator<String> iterator1 = set.iterator();
/*  8217 */     while (iterator1.hasNext()) {
/*       */       
/*  8219 */       String str = iterator1.next();
/*       */       
/*  8221 */       treeSet.add(parseString(str, 1));
/*       */     } 
/*       */     
/*  8224 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  8225 */     for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/*  8227 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/*  8230 */     byte b1 = 0;
/*  8231 */     Iterator<String> iterator2 = treeSet.iterator();
/*  8232 */     while (iterator2.hasNext()) {
/*       */       
/*  8234 */       String str = iterator2.next();
/*       */       
/*  8236 */       iterator1 = set.iterator();
/*  8237 */       while (iterator1.hasNext()) {
/*       */         
/*  8239 */         String str1 = iterator1.next();
/*       */         
/*  8241 */         if (str.equals(parseString(str1, 1))) {
/*       */           
/*  8243 */           String str2 = parseString(str1, 2) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 5) + "<:>" + parseString(str1, 6);
/*  8244 */           arrayOfTreeMap[b1].put(str2, this.supportedDevices_TM.get(str1));
/*       */         } 
/*       */       } 
/*  8247 */       b1++;
/*       */     } 
/*       */     
/*  8250 */     if (1 == this.format)
/*       */     {
/*  8252 */       paramStringBuffer.append(":h2.External Machine Type (Support Devices)" + NEWLINE);
/*       */     }
/*       */     
/*  8255 */     if (0 == arrayOfTreeMap.length)
/*       */     {
/*  8257 */       if (true == paramBoolean) {
/*       */         
/*  8259 */         paramStringBuffer.append(":p.No answer data found for External Machine Type (Support Devices) section." + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  8263 */         paramStringBuffer.append("<p>No answer data found for External Machine Type (Support Devices) section.</p>" + NEWLINE);
/*       */       } 
/*       */     }
/*       */     
/*  8267 */     iterator2 = treeSet.iterator();
/*  8268 */     b1 = 0;
/*  8269 */     while (iterator2.hasNext()) {
/*       */       
/*  8271 */       String str = iterator2.next();
/*  8272 */       if (true == paramBoolean) {
/*       */         
/*  8274 */         paramStringBuffer.append(NEWLINE + ":p.The following external machine types are supported on the" + NEWLINE);
/*  8275 */         paramStringBuffer.append("indicated models for MT " + str + "." + NEWLINE);
/*  8276 */         paramStringBuffer.append(":p.This list is not all inclusive.  Many devices are supported through" + NEWLINE);
/*  8277 */         paramStringBuffer.append("standard ports.  Please refer to the sales manual" + NEWLINE);
/*  8278 */         paramStringBuffer.append("of the external machine type and the list of supported devices in the" + NEWLINE);
/*  8279 */         if (this.brand.equals("pSeries")) {
/*       */           
/*  8281 */           paramStringBuffer.append("appropriate section of the AIX sales manual" + NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/*  8285 */           paramStringBuffer.append("appropriate section of the sales manual" + NEWLINE);
/*       */         } 
/*  8287 */         paramStringBuffer.append("for further attach support information." + NEWLINE + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  8291 */         paramStringBuffer.append("<p>The following external machine types are supported on the indicated models for MT " + str + ".</p>" + NEWLINE);
/*  8292 */         paramStringBuffer.append("<p>This list is not all inclusive.  Many devices are supported through" + NEWLINE);
/*  8293 */         paramStringBuffer.append("standard ports.  Please refer to the sales manual" + NEWLINE);
/*  8294 */         paramStringBuffer.append("of the external machine type and the list of supported devices in the" + NEWLINE);
/*  8295 */         if (this.brand.equals("pSeries")) {
/*       */           
/*  8297 */           paramStringBuffer.append("appropriate section of the AIX sales manual" + NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/*  8301 */           paramStringBuffer.append("appropriate section of the sales manual" + NEWLINE);
/*       */         } 
/*  8303 */         paramStringBuffer.append("for further attach support information.</p>" + NEWLINE);
/*       */       } 
/*  8305 */       retrieveSupportedDevices(paramStringBuffer, paramBoolean, arrayOfTreeMap[b1]);
/*  8306 */       b1++;
/*       */     } 
/*  8308 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
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
/*  8324 */     TreeSet<String> treeSet = new TreeSet();
/*  8325 */     Set set = paramTreeMap.keySet();
/*  8326 */     Iterator<String> iterator = set.iterator();
/*  8327 */     while (iterator.hasNext()) {
/*       */       
/*  8329 */       String str = iterator.next();
/*  8330 */       str = parseString(str, 5);
/*  8331 */       treeSet.add(str);
/*       */     } 
/*       */ 
/*       */     
/*  8335 */     int[] arrayOfInt = new int[10];
/*  8336 */     arrayOfInt[0] = 11;
/*  8337 */     arrayOfInt[1] = 13;
/*  8338 */     arrayOfInt[2] = 15;
/*  8339 */     arrayOfInt[3] = 17;
/*  8340 */     arrayOfInt[4] = 19;
/*  8341 */     arrayOfInt[5] = 21;
/*  8342 */     arrayOfInt[6] = 23;
/*  8343 */     arrayOfInt[7] = 25;
/*  8344 */     arrayOfInt[8] = 27;
/*  8345 */     arrayOfInt[9] = 29;
/*       */     
/*  8347 */     Object[] arrayOfObject = treeSet.toArray();
/*       */     
/*  8349 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */     
/*  8351 */     byte b1 = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8356 */     for (byte b2 = 0; b2 < arrayOfObject.length; b2++) {
/*       */       
/*  8358 */       if (b1 < 10) {
/*       */         
/*  8360 */         treeMap.put(arrayOfObject[b2], new Integer(arrayOfInt[b2 % 10]));
/*  8361 */         b1++;
/*       */       } 
/*  8363 */       if (b1 == 10 || b2 == arrayOfObject.length - 1) {
/*       */         
/*  8365 */         b1 = 0;
/*  8366 */         retrieveSupportedDevices(paramStringBuffer, treeMap, paramTreeMap, paramBoolean);
/*  8367 */         treeMap.clear();
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
/*  8392 */     paramStringBuffer.append(":xmp." + NEWLINE);
/*  8393 */     paramStringBuffer.append(".kp off" + NEWLINE);
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8398 */     this.headerSB.delete(0, this.headerSB.length());
/*       */     
/*  8400 */     Set set = paramTreeMap1.keySet();
/*  8401 */     Object[] arrayOfObject = set.toArray();
/*  8402 */     int i = 69 - 9 + 2 * paramTreeMap1.size() + 1;
/*       */     
/*  8404 */     printChar(paramStringBuffer, 9, " ", false);
/*  8405 */     printChar(this.headerSB, 9, " ", false); byte b;
/*  8406 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/*  8408 */       String str = (String)arrayOfObject[b];
/*  8409 */       paramStringBuffer.append("|");
/*  8410 */       this.headerSB.append("|");
/*       */       
/*  8412 */       paramStringBuffer.append(str.charAt(0));
/*  8413 */       this.headerSB.append(str.charAt(0));
/*       */     } 
/*  8415 */     paramStringBuffer.append("|");
/*  8416 */     this.headerSB.append("|");
/*  8417 */     paramStringBuffer.append("  X = SUPPORTED DEVICE" + NEWLINE);
/*  8418 */     this.headerSB.append("  X = SUPPORTED DEVICE" + NEWLINE);
/*       */     
/*  8420 */     printChar(paramStringBuffer, 9, " ", false);
/*  8421 */     printChar(this.headerSB, 9, " ", false);
/*  8422 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/*  8424 */       String str = (String)arrayOfObject[b];
/*  8425 */       paramStringBuffer.append("|");
/*  8426 */       this.headerSB.append("|");
/*       */       
/*  8428 */       paramStringBuffer.append(str.charAt(1));
/*  8429 */       this.headerSB.append(str.charAt(1));
/*       */     } 
/*  8431 */     paramStringBuffer.append("|" + NEWLINE);
/*  8432 */     this.headerSB.append("|" + NEWLINE);
/*       */     
/*  8434 */     printChar(paramStringBuffer, 9, " ", false);
/*  8435 */     printChar(this.headerSB, 9, " ", false);
/*  8436 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/*  8438 */       String str = (String)arrayOfObject[b];
/*  8439 */       paramStringBuffer.append("|");
/*  8440 */       this.headerSB.append("|");
/*       */       
/*  8442 */       paramStringBuffer.append(str.charAt(2));
/*  8443 */       this.headerSB.append(str.charAt(2));
/*       */     } 
/*  8445 */     paramStringBuffer.append("|" + NEWLINE);
/*  8446 */     this.headerSB.append("|" + NEWLINE);
/*       */     
/*  8448 */     printChar(paramStringBuffer, 9, " ", false);
/*  8449 */     printChar(this.headerSB, 9, " ", false);
/*  8450 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/*  8451 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/*  8452 */     paramStringBuffer.append(NEWLINE);
/*  8453 */     this.headerSB.append(NEWLINE);
/*       */     
/*  8455 */     paramStringBuffer.append("  MT-MOD ");
/*  8456 */     this.headerSB.append("  MT-MOD ");
/*  8457 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/*  8458 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/*  8459 */     paramStringBuffer.append("        DESCRIPTION" + NEWLINE);
/*  8460 */     this.headerSB.append("        DESCRIPTION" + NEWLINE);
/*       */     
/*  8462 */     printChar(paramStringBuffer, 9, "-", false);
/*  8463 */     printChar(this.headerSB, 9, "-", false);
/*  8464 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/*  8465 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/*  8466 */     printChar(paramStringBuffer, i, "-", false);
/*  8467 */     printChar(this.headerSB, i, "-", false);
/*  8468 */     paramStringBuffer.append(NEWLINE);
/*  8469 */     this.headerSB.append(NEWLINE);
/*       */     
/*  8471 */     retrieveSupportedDevices(paramTreeMap1, paramTreeMap2, i, paramStringBuffer, paramBoolean);
/*       */     
/*  8473 */     paramStringBuffer.append(":exmp." + NEWLINE);
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
/*  8487 */     int i = 0;
/*       */     
/*  8489 */     int j = 0;
/*  8490 */     boolean bool = true;
/*  8491 */     byte b = 0;
/*       */     
/*  8493 */     String str1 = "";
/*  8494 */     String str2 = "";
/*  8495 */     String str3 = "";
/*  8496 */     String str4 = "";
/*       */     
/*  8498 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/*  8500 */     Set set = paramTreeMap2.keySet();
/*       */     
/*  8502 */     Iterator<String> iterator = set.iterator();
/*       */     
/*  8504 */     while (iterator.hasNext()) {
/*       */       
/*  8506 */       String str5 = iterator.next();
/*  8507 */       String str6 = parseString(str5, 5);
/*  8508 */       if (paramTreeMap1.containsKey(str6)) {
/*       */         
/*  8510 */         String str = parseString(str5, 1) + "<:>" + parseString(str5, 2) + "<:>" + parseString(str5, 3) + "<:>" + parseString(str5, 4) + "<:>" + paramTreeMap2.get(str5);
/*  8511 */         treeSet.add(str);
/*       */       } 
/*       */     } 
/*       */     
/*  8515 */     iterator = treeSet.iterator();
/*       */     
/*  8517 */     while (iterator.hasNext()) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  8522 */       String str = iterator.next();
/*       */       
/*  8524 */       i = 10;
/*       */       
/*  8526 */       Set set1 = paramTreeMap2.keySet();
/*  8527 */       Iterator<String> iterator1 = set1.iterator();
/*  8528 */       while (iterator1.hasNext()) {
/*       */         
/*  8530 */         String str5 = iterator1.next();
/*  8531 */         String str6 = parseString(str5, 1) + "<:>" + parseString(str5, 2) + "<:>" + parseString(str5, 3) + "<:>" + parseString(str5, 4) + "<:>" + paramTreeMap2.get(str5);
/*  8532 */         String str7 = parseString(str5, 5);
/*  8533 */         if (str.equals(str6) && paramTreeMap1.containsKey(str7)) {
/*       */           
/*  8535 */           str2 = parseString(str6, 5);
/*       */           
/*  8537 */           str4 = parseString(str, 1);
/*       */           
/*  8539 */           if (bool) {
/*       */             
/*  8541 */             if (!str4.equals(str3)) {
/*       */               
/*  8543 */               if (50 == b) {
/*       */                 
/*  8545 */                 printEndGeoTags(str1, paramBoolean, paramStringBuffer);
/*  8546 */                 paramStringBuffer.append(":exmp." + NEWLINE + NEWLINE);
/*  8547 */                 b = 0;
/*  8548 */                 paramStringBuffer.append(":xmp." + NEWLINE);
/*  8549 */                 paramStringBuffer.append(".kp off" + NEWLINE);
/*  8550 */                 paramStringBuffer.append(this.headerSB.toString());
/*       */               }
/*       */               else {
/*       */                 
/*  8554 */                 printEndGeoTags(str1, paramBoolean, paramStringBuffer);
/*       */               } 
/*  8556 */               printChar(paramStringBuffer, 9, " ", false);
/*  8557 */               printChar(paramStringBuffer, 2 * paramTreeMap1.size() + 1, "|", " ", false);
/*  8558 */               paramStringBuffer.append(NEWLINE);
/*       */               
/*  8560 */               printChar(paramStringBuffer, 9, "-", false);
/*  8561 */               printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/*  8562 */               printChar(paramStringBuffer, 8, "-", false);
/*       */               
/*  8564 */               paramStringBuffer.append(str4);
/*  8565 */               printChar(paramStringBuffer, paramInt - 8 + str4.length(), "-", false);
/*  8566 */               paramStringBuffer.append(NEWLINE);
/*  8567 */               printChar(paramStringBuffer, 9, "-", " ", false);
/*  8568 */               printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/*  8569 */               printChar(paramStringBuffer, paramInt, "-", " ", false);
/*  8570 */               paramStringBuffer.append(NEWLINE);
/*  8571 */               printBeginGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */             
/*       */             }
/*  8575 */             else if (50 == b) {
/*       */               
/*  8577 */               setGeoTags(str1, str2, paramBoolean, paramStringBuffer, this.headerSB);
/*  8578 */               b = 0;
/*       */             }
/*       */             else {
/*       */               
/*  8582 */               setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */             } 
/*       */ 
/*       */             
/*  8586 */             paramStringBuffer.append(parseString(str, 2) + "-" + parseString(str, 3) + " ");
/*       */             
/*  8588 */             bool = false;
/*  8589 */             b++;
/*       */           } 
/*       */           
/*  8592 */           str1 = str2;
/*  8593 */           str3 = str4;
/*       */           
/*  8595 */           Integer integer = (Integer)paramTreeMap1.get(str7);
/*  8596 */           j = integer.intValue();
/*  8597 */           printChar(paramStringBuffer, j - i, "|", " ", false);
/*  8598 */           i = j;
/*  8599 */           paramStringBuffer.append("X");
/*  8600 */           i++;
/*       */         } 
/*       */       } 
/*  8603 */       printChar(paramStringBuffer, 9 + 2 * paramTreeMap1.size() + 2 - i, "|", " ", false);
/*       */       
/*  8605 */       String[] arrayOfString = extractStringLines(parseString(str, 4), paramInt - 1);
/*  8606 */       for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */         
/*  8608 */         if (0 == b1) {
/*       */           
/*  8610 */           paramStringBuffer.append(" " + arrayOfString[b1] + NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/*  8614 */           printChar(paramStringBuffer, 9, " ", false);
/*  8615 */           printChar(paramStringBuffer, 2 * paramTreeMap1.size() + 1, "|", " ", false);
/*  8616 */           paramStringBuffer.append(" " + arrayOfString[b1] + NEWLINE);
/*       */         } 
/*       */       } 
/*       */       
/*  8620 */       bool = true;
/*       */     } 
/*  8622 */     if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  8628 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8634 */     paramStringBuffer.append(NEWLINE);
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
/*       */   private void retrieveFeatureMatrix(boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  8658 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  8659 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  8660 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */     
/*  8662 */     TreeSet<String> treeSet = new TreeSet();
/*  8663 */     Set<String> set = this.featureMatrix_TM.keySet();
/*  8664 */     Iterator<String> iterator1 = set.iterator();
/*  8665 */     while (iterator1.hasNext()) {
/*       */       
/*  8667 */       String str = iterator1.next();
/*       */       
/*  8669 */       treeSet.add(parseString(str, 1));
/*       */     } 
/*       */     
/*  8672 */     TreeMap[] arrayOfTreeMap = new TreeMap[treeSet.size()];
/*  8673 */     for (byte b2 = 0; b2 < treeSet.size(); b2++)
/*       */     {
/*  8675 */       arrayOfTreeMap[b2] = new TreeMap<>();
/*       */     }
/*       */     
/*  8678 */     byte b1 = 0;
/*  8679 */     Iterator<String> iterator2 = treeSet.iterator();
/*  8680 */     while (iterator2.hasNext()) {
/*       */       
/*  8682 */       String str = iterator2.next();
/*       */       
/*  8684 */       iterator1 = set.iterator();
/*  8685 */       while (iterator1.hasNext()) {
/*       */         
/*  8687 */         String str1 = iterator1.next();
/*       */         
/*  8689 */         if (str.equals(parseString(str1, 1))) {
/*       */           
/*  8691 */           String str2 = parseString(str1, 2) + "<:>" + parseString(str1, 3) + "<:>" + parseString(str1, 4) + "<:>" + parseString(str1, 5) + "<:>" + parseString(str1, 6);
/*  8692 */           arrayOfTreeMap[b1].put(str2, this.featureMatrix_TM.get(str1));
/*       */         } 
/*       */       } 
/*  8695 */       b1++;
/*       */     } 
/*       */     
/*  8698 */     if (1 == this.format)
/*       */     {
/*  8700 */       paramStringBuffer.append(":h2.Feature Matrix" + NEWLINE);
/*       */     }
/*       */     
/*  8703 */     if (0 == arrayOfTreeMap.length)
/*       */     {
/*  8705 */       if (true == paramBoolean) {
/*       */         
/*  8707 */         paramStringBuffer.append(":p.No answer data found for Feature Matrix section." + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  8711 */         paramStringBuffer.append("<p>No answer data found for Feature Matrix section.</p>" + NEWLINE);
/*       */       } 
/*       */     }
/*       */     
/*  8715 */     iterator2 = treeSet.iterator();
/*  8716 */     b1 = 0;
/*  8717 */     while (iterator2.hasNext()) {
/*       */       
/*  8719 */       String str = iterator2.next();
/*  8720 */       if (true == paramBoolean) {
/*       */         
/*  8722 */         paramStringBuffer.append(NEWLINE + ":xmp." + NEWLINE);
/*  8723 */         paramStringBuffer.append(".kp off" + NEWLINE);
/*  8724 */         paramStringBuffer.append("*********************************************************************" + NEWLINE);
/*  8725 */         paramStringBuffer.append("NOTE TO THE EDITOR" + NEWLINE);
/*  8726 */         paramStringBuffer.append("THE FEATURE AVAILABILITY MATRIX SECTION OF THE SALES MANUAL" + NEWLINE);
/*  8727 */         paramStringBuffer.append("IS AUTOMATICALLY GENERATED FROM THE PLAN OF RECORD." + NEWLINE);
/*  8728 */         paramStringBuffer.append("PLEASE DO THE FOLLOWING:" + NEWLINE);
/*  8729 */         paramStringBuffer.append("1) ADD THE FEATURE IN NUMERIC ORDER INTO THE FEATURE AVAILABILITY" + NEWLINE);
/*  8730 */         paramStringBuffer.append("MATRIX." + NEWLINE);
/*  8731 */         paramStringBuffer.append("2) IF DESCRIPTIVE INFORMATION IS PROVIDED SUCH AS:" + NEWLINE);
/*  8732 */         paramStringBuffer.append("\"The following feature availability matrix for................\"" + NEWLINE);
/*  8733 */         paramStringBuffer.append("REPLACE THE EXISTING DESCRIPTIVE INFORMATION." + NEWLINE);
/*  8734 */         paramStringBuffer.append("IF SUCH DESCRIPTIVE INFORMATION IS NOT PROVIDED DO NOT ADD THESE" + NEWLINE);
/*  8735 */         paramStringBuffer.append("WORDS." + NEWLINE);
/*  8736 */         paramStringBuffer.append("3) IF THERE IS NO FEATURE AVAILABILITY MATRIX DO NOT INCLUDE THIS" + NEWLINE);
/*  8737 */         paramStringBuffer.append("SECTION." + NEWLINE);
/*  8738 */         paramStringBuffer.append("*********************************************************************" + NEWLINE);
/*  8739 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*  8740 */         if (2 == this.format)
/*       */         {
/*  8742 */           paramStringBuffer.append(NEWLINE + ":p.The following feature availability matrix for MT " + str + NEWLINE);
/*  8743 */           paramStringBuffer.append("uses the letter \"A\"" + NEWLINE);
/*  8744 */           paramStringBuffer.append("to indicate features that are available and orderable on the specified" + NEWLINE);
/*  8745 */           paramStringBuffer.append("models.  \"S\" indicates a feature that is supported on the new model" + NEWLINE);
/*  8746 */           paramStringBuffer.append("during a model conversion; these features will" + NEWLINE);
/*  8747 */           paramStringBuffer.append("work on the new model, but additional quantities of these" + NEWLINE);
/*  8748 */           paramStringBuffer.append("features cannot be ordered on the new model; they can only be removed." + NEWLINE);
/*  8749 */           paramStringBuffer.append("\"N\" indicates that the feature is not supported" + NEWLINE);
/*  8750 */           paramStringBuffer.append("on the new model and must be removed during the model conversion." + NEWLINE);
/*  8751 */           paramStringBuffer.append("As additional features are announced, supported, or withdrawn," + NEWLINE);
/*  8752 */           paramStringBuffer.append("this list will be updated.  Please check with your Marketing" + NEWLINE);
/*  8753 */           paramStringBuffer.append("Representative for additional information." + NEWLINE + NEWLINE);
/*       */         }
/*       */         else
/*       */         {
/*  8757 */           paramStringBuffer.append(NEWLINE + ":p.The following feature availability matrix for MT " + str + NEWLINE + NEWLINE);
/*       */         
/*       */         }
/*       */       
/*       */       }
/*  8762 */       else if (2 == this.format) {
/*       */         
/*  8764 */         paramStringBuffer.append("<p>The following feature availability matrix for MT " + str + NEWLINE);
/*  8765 */         paramStringBuffer.append("uses the letter &quot;A&quot;" + NEWLINE);
/*  8766 */         paramStringBuffer.append("to indicate features that are available and orderable on the specified" + NEWLINE);
/*  8767 */         paramStringBuffer.append("models. &quot;S&quot; indicates a feature that is supported on the new model" + NEWLINE);
/*  8768 */         paramStringBuffer.append("during a model conversion; these features will" + NEWLINE);
/*  8769 */         paramStringBuffer.append("work on the new model, but additional quantities of these" + NEWLINE);
/*  8770 */         paramStringBuffer.append("features cannot be ordered on the new model; they can only be removed." + NEWLINE);
/*  8771 */         paramStringBuffer.append("&quot;N&quot; indicates that the feature is not supported" + NEWLINE);
/*  8772 */         paramStringBuffer.append("on the new model and must be removed during the model conversion." + NEWLINE);
/*  8773 */         paramStringBuffer.append("As additional features are announced, supported, or withdrawn," + NEWLINE);
/*  8774 */         paramStringBuffer.append("this list will be updated.  Please check with your Marketing" + NEWLINE);
/*  8775 */         paramStringBuffer.append("Representative for additional information.</p>" + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/*  8779 */         paramStringBuffer.append("<p>The following feature availability matrix for MT " + str + "</p>" + NEWLINE);
/*       */       } 
/*       */       
/*  8782 */       retrieveFeatureMatrix(paramStringBuffer, paramBoolean, arrayOfTreeMap[b1]);
/*  8783 */       b1++;
/*       */     } 
/*  8785 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
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
/*  8801 */     TreeSet<String> treeSet = new TreeSet();
/*  8802 */     Set set = paramTreeMap.keySet();
/*  8803 */     Iterator<String> iterator = set.iterator();
/*  8804 */     while (iterator.hasNext()) {
/*       */       
/*  8806 */       String str = iterator.next();
/*  8807 */       str = parseString(str, 1);
/*  8808 */       treeSet.add(str);
/*       */     } 
/*       */ 
/*       */     
/*  8812 */     int[] arrayOfInt = new int[10];
/*  8813 */     arrayOfInt[0] = 10;
/*  8814 */     arrayOfInt[1] = 12;
/*  8815 */     arrayOfInt[2] = 14;
/*  8816 */     arrayOfInt[3] = 16;
/*  8817 */     arrayOfInt[4] = 18;
/*  8818 */     arrayOfInt[5] = 20;
/*  8819 */     arrayOfInt[6] = 22;
/*  8820 */     arrayOfInt[7] = 24;
/*  8821 */     arrayOfInt[8] = 26;
/*  8822 */     arrayOfInt[9] = 28;
/*       */     
/*  8824 */     Object[] arrayOfObject = treeSet.toArray();
/*       */     
/*  8826 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */     
/*  8828 */     byte b1 = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8833 */     for (byte b2 = 0; b2 < arrayOfObject.length; b2++) {
/*       */       
/*  8835 */       if (b1 < 10) {
/*       */         
/*  8837 */         treeMap.put(arrayOfObject[b2], new Integer(arrayOfInt[b2 % 10]));
/*  8838 */         b1++;
/*       */       } 
/*  8840 */       if (b1 == 10 || b2 == arrayOfObject.length - 1) {
/*       */         
/*  8842 */         b1 = 0;
/*  8843 */         retrieveFeatureMatrix(paramStringBuffer, treeMap, paramTreeMap, paramBoolean);
/*  8844 */         treeMap.clear();
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
/*  8865 */     Set set = paramTreeMap1.keySet();
/*  8866 */     Object[] arrayOfObject = set.toArray();
/*  8867 */     int i = 69 - 8 + 2 * paramTreeMap1.size() + 1;
/*       */     
/*  8869 */     paramStringBuffer.append(":xmp." + NEWLINE);
/*  8870 */     paramStringBuffer.append(".kp off" + NEWLINE);
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8875 */     this.headerSB.delete(0, this.headerSB.length());
/*       */     
/*  8877 */     printChar(paramStringBuffer, 8, " ", false);
/*  8878 */     printChar(this.headerSB, 8, " ", false); byte b;
/*  8879 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/*  8881 */       String str = (String)arrayOfObject[b];
/*  8882 */       paramStringBuffer.append("|");
/*  8883 */       this.headerSB.append("|");
/*       */       
/*  8885 */       paramStringBuffer.append(str.charAt(0));
/*  8886 */       this.headerSB.append(str.charAt(0));
/*       */     } 
/*  8888 */     paramStringBuffer.append("|");
/*  8889 */     this.headerSB.append("|");
/*  8890 */     if (2 == this.format) {
/*       */       
/*  8892 */       paramStringBuffer.append(" A = AVAILABLE  S = SUPPORTED" + NEWLINE);
/*  8893 */       this.headerSB.append(" A = AVAILABLE  S = SUPPORTED" + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  8897 */       paramStringBuffer.append(" I = Initial" + NEWLINE);
/*  8898 */       this.headerSB.append(" I = Initial" + NEWLINE);
/*       */     } 
/*       */     
/*  8901 */     printChar(paramStringBuffer, 8, " ", false);
/*  8902 */     printChar(this.headerSB, 8, " ", false);
/*  8903 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/*  8905 */       String str = (String)arrayOfObject[b];
/*  8906 */       paramStringBuffer.append("|");
/*  8907 */       this.headerSB.append("|");
/*       */       
/*  8909 */       paramStringBuffer.append(str.charAt(1));
/*  8910 */       this.headerSB.append(str.charAt(1));
/*       */     } 
/*  8912 */     paramStringBuffer.append("|");
/*  8913 */     this.headerSB.append("|");
/*  8914 */     if (2 == this.format) {
/*       */       
/*  8916 */       paramStringBuffer.append(" N = NOT SUPPORTED, MUST BE REMOVED" + NEWLINE);
/*  8917 */       this.headerSB.append(" N = NOT SUPPORTED, MUST BE REMOVED" + NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  8921 */       paramStringBuffer.append(" M = MES" + NEWLINE);
/*  8922 */       this.headerSB.append(" M = MES" + NEWLINE);
/*       */     } 
/*       */     
/*  8925 */     printChar(paramStringBuffer, 8, " ", false);
/*  8926 */     printChar(this.headerSB, 8, " ", false);
/*  8927 */     for (b = 0; b < paramTreeMap1.size(); b++) {
/*       */       
/*  8929 */       String str = (String)arrayOfObject[b];
/*  8930 */       paramStringBuffer.append("|");
/*  8931 */       this.headerSB.append("|");
/*       */       
/*  8933 */       paramStringBuffer.append(str.charAt(2));
/*  8934 */       this.headerSB.append(str.charAt(2));
/*       */     } 
/*  8936 */     paramStringBuffer.append("|");
/*  8937 */     this.headerSB.append("|");
/*  8938 */     if (2 == this.format) {
/*       */       
/*  8940 */       paramStringBuffer.append(NEWLINE);
/*  8941 */       this.headerSB.append(NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  8945 */       paramStringBuffer.append(" B = Both (Initial and MES)" + NEWLINE);
/*  8946 */       this.headerSB.append(" B = Both (Initial and MES)" + NEWLINE);
/*       */     } 
/*       */     
/*  8949 */     printChar(paramStringBuffer, 8, " ", false);
/*  8950 */     printChar(this.headerSB, 8, " ", false);
/*  8951 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/*  8952 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/*  8953 */     if (2 == this.format) {
/*       */       
/*  8955 */       paramStringBuffer.append(NEWLINE);
/*  8956 */       this.headerSB.append(NEWLINE);
/*       */     }
/*       */     else {
/*       */       
/*  8960 */       paramStringBuffer.append(" S = SUPPORTED" + NEWLINE);
/*  8961 */       this.headerSB.append(" S = SUPPORTED" + NEWLINE);
/*       */     } 
/*       */     
/*  8964 */     paramStringBuffer.append("FEAT/PN ");
/*  8965 */     this.headerSB.append("FEAT/PN ");
/*  8966 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/*  8967 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", " ", false);
/*  8968 */     paramStringBuffer.append("        DESCRIPTION" + NEWLINE);
/*  8969 */     this.headerSB.append("        DESCRIPTION" + NEWLINE);
/*       */     
/*  8971 */     printChar(paramStringBuffer, 8, "-", false);
/*  8972 */     printChar(this.headerSB, 8, "-", false);
/*  8973 */     printChar(paramStringBuffer, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/*  8974 */     printChar(this.headerSB, paramTreeMap1.size() * 2 + 1, "|", "-", false);
/*  8975 */     printChar(paramStringBuffer, i, "-", false);
/*  8976 */     printChar(this.headerSB, i, "-", false);
/*  8977 */     paramStringBuffer.append(NEWLINE);
/*  8978 */     this.headerSB.append(NEWLINE);
/*       */     
/*  8980 */     retrieveFeatureMatrix(paramTreeMap1, paramTreeMap2, i, paramStringBuffer, paramBoolean);
/*       */     
/*  8982 */     paramStringBuffer.append(":exmp." + NEWLINE);
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
/*  8996 */     int i = 0;
/*       */     
/*  8998 */     int j = 0;
/*  8999 */     boolean bool = true;
/*  9000 */     byte b = 0;
/*       */     
/*  9002 */     String str1 = "";
/*  9003 */     String str2 = "";
/*       */     
/*  9005 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/*  9007 */     Set set = paramTreeMap2.keySet();
/*       */     
/*  9009 */     Iterator<String> iterator = set.iterator();
/*       */     
/*  9011 */     while (iterator.hasNext()) {
/*       */       
/*  9013 */       String str3 = iterator.next();
/*  9014 */       String str4 = parseString(str3, 1);
/*  9015 */       if (paramTreeMap1.containsKey(str4)) {
/*       */ 
/*       */         
/*  9018 */         String str = parseString(str3, 2) + "<:>" + parseString(str3, 4) + "<:>" + paramTreeMap2.get(str3);
/*  9019 */         treeSet.add(str);
/*       */       } 
/*       */     } 
/*       */     
/*  9023 */     iterator = treeSet.iterator();
/*       */     
/*  9025 */     while (iterator.hasNext()) {
/*       */       
/*  9027 */       String str = iterator.next();
/*       */ 
/*       */       
/*  9030 */       Set set1 = paramTreeMap2.keySet();
/*  9031 */       Iterator<String> iterator1 = set1.iterator();
/*  9032 */       i = 9;
/*  9033 */       while (iterator1.hasNext()) {
/*       */         
/*  9035 */         String str3 = iterator1.next();
/*       */         
/*  9037 */         String str4 = parseString(str3, 2) + "<:>" + parseString(str3, 4) + "<:>" + paramTreeMap2.get(str3);
/*  9038 */         String str5 = parseString(str3, 1);
/*  9039 */         String str6 = parseString(str3, 3);
/*  9040 */         String str7 = parseString(str3, 5);
/*  9041 */         if (str.equals(str4) && paramTreeMap1.containsKey(str5)) {
/*       */           
/*  9043 */           str2 = parseString(str4, 3);
/*       */           
/*  9045 */           if (bool) {
/*       */             
/*  9047 */             if (50 == b) {
/*       */               
/*  9049 */               setGeoTags(str1, str2, paramBoolean, paramStringBuffer, this.headerSB);
/*  9050 */               b = 0;
/*       */             }
/*       */             else {
/*       */               
/*  9054 */               setGeoTags(str1, str2, paramBoolean, paramStringBuffer);
/*       */             } 
/*       */             
/*  9057 */             if (str7.length() > 0) {
/*       */               
/*  9059 */               if (!paramBoolean) {
/*       */ 
/*       */                 
/*  9062 */                 String[] arrayOfString1 = getStringTokens(str7, NEWLINE);
/*  9063 */                 for (byte b2 = 0; b2 < arrayOfString1.length; b2++)
/*       */                 {
/*  9065 */                   paramStringBuffer.append(arrayOfString1[b2] + NEWLINE);
/*       */                 }
/*       */               } 
/*       */               
/*  9069 */               if (paramBoolean == true) {
/*       */                 
/*  9071 */                 String[] arrayOfString1 = getStringTokens(str7, NEWLINE);
/*  9072 */                 for (byte b2 = 0; b2 < arrayOfString1.length; b2++) {
/*       */                   
/*  9074 */                   if (arrayOfString1[b2].length() > 58) {
/*       */                     
/*  9076 */                     String[] arrayOfString2 = extractStringLines(arrayOfString1[b2], 58);
/*  9077 */                     for (byte b3 = 0; b3 < arrayOfString2.length; b3++)
/*       */                     {
/*  9079 */                       paramStringBuffer.append(":hp2." + arrayOfString2[b3] + ":ehp2." + NEWLINE);
/*       */                     }
/*       */                   }
/*       */                   else {
/*       */                     
/*  9084 */                     paramStringBuffer.append(":hp2." + arrayOfString1[b2] + ":ehp2." + NEWLINE);
/*       */                   } 
/*       */                 } 
/*       */               } 
/*       */             } 
/*  9089 */             paramStringBuffer.append(parseString(str, 1) + "    ");
/*  9090 */             bool = false;
/*  9091 */             b++;
/*       */           } 
/*       */           
/*  9094 */           str1 = str2;
/*       */           
/*  9096 */           Integer integer = (Integer)paramTreeMap1.get(str5);
/*  9097 */           j = integer.intValue();
/*  9098 */           printChar(paramStringBuffer, j - i, "|", " ", false);
/*  9099 */           i = j;
/*  9100 */           paramStringBuffer.append(str6);
/*  9101 */           i++;
/*       */         } 
/*       */       } 
/*  9104 */       printChar(paramStringBuffer, 8 + 2 * paramTreeMap1.size() + 2 - i, "|", " ", false);
/*       */       
/*  9106 */       String[] arrayOfString = extractStringLines(parseString(str, 2), paramInt - 1);
/*  9107 */       for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*       */         
/*  9109 */         if (0 == b1) {
/*       */           
/*  9111 */           paramStringBuffer.append(" " + arrayOfString[b1] + NEWLINE);
/*       */         }
/*       */         else {
/*       */           
/*  9115 */           printChar(paramStringBuffer, 8, " ", false);
/*  9116 */           printChar(paramStringBuffer, 2 * paramTreeMap1.size() + 1, "|", " ", false);
/*  9117 */           paramStringBuffer.append(" " + arrayOfString[b1] + NEWLINE);
/*       */         } 
/*       */       } 
/*       */       
/*  9121 */       bool = true;
/*       */     } 
/*  9123 */     if (!str2.equals("WW"))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9129 */       bldEndGeoTags(str2, paramBoolean, paramStringBuffer);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9135 */     paramStringBuffer.append(NEWLINE);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void retrieveFeatureMatrixError(StringBuffer paramStringBuffer) {
/*  9145 */     String str1 = "";
/*  9146 */     String str2 = "";
/*  9147 */     String str3 = "";
/*  9148 */     String str4 = "";
/*  9149 */     String str5 = "";
/*  9150 */     String str6 = "";
/*  9151 */     String str7 = "";
/*  9152 */     String str8 = "";
/*       */ 
/*       */     
/*  9155 */     paramStringBuffer.append(".* <pre>" + NEWLINE);
/*  9156 */     paramStringBuffer.append(".* " + myDate() + NEWLINE);
/*  9157 */     paramStringBuffer.append(".* " + this.inventoryGroup + NEWLINE);
/*       */     
/*  9159 */     if (this.featureMatrixError.size() > 0 && 1 == this.format)
/*       */     {
/*  9161 */       paramStringBuffer.append(":h2.Feature Matrix Error" + NEWLINE);
/*       */     }
/*       */     
/*  9164 */     Iterator<String> iterator = this.featureMatrixError.iterator();
/*  9165 */     while (iterator.hasNext()) {
/*       */       
/*  9167 */       String str = iterator.next();
/*  9168 */       str2 = parseString(str, 2);
/*  9169 */       str5 = parseString(str, 3);
/*  9170 */       str6 = parseString(str, 4);
/*  9171 */       str4 = str5 + "-" + str6;
/*  9172 */       str7 = parseString(str, 5);
/*  9173 */       str8 = parseString(str, 6);
/*       */       
/*  9175 */       if (!str2.equals(str1) || !str4.equals(str3)) {
/*       */         
/*  9177 */         paramStringBuffer.append(NEWLINE + "Report Name: " + str2 + NEWLINE);
/*  9178 */         paramStringBuffer.append("Machine Type:        " + str5 + NEWLINE);
/*  9179 */         paramStringBuffer.append("Model:               " + str6 + NEWLINE);
/*  9180 */         paramStringBuffer.append("Missing Attributes:" + NEWLINE);
/*       */       } 
/*       */       
/*  9183 */       paramStringBuffer.append("   Entity: " + str7 + ", Attribute: " + str8 + NEWLINE);
/*       */       
/*  9185 */       str1 = str2;
/*  9186 */       str3 = str4;
/*       */     } 
/*  9188 */     paramStringBuffer.append(".* </pre>" + NEWLINE);
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
/*  9199 */     if (null == this.geoHT.get(paramString))
/*       */     {
/*  9201 */       this.geoHT.put(paramString, new StringBuffer("NNNNN"));
/*       */     }
/*  9203 */     if (checkRfaGeoUS(paramEntityItem))
/*       */     {
/*  9205 */       updateGeo(paramString, "US");
/*       */     }
/*  9207 */     if (checkRfaGeoAP(paramEntityItem))
/*       */     {
/*  9209 */       updateGeo(paramString, "AP");
/*       */     }
/*  9211 */     if (checkRfaGeoLA(paramEntityItem))
/*       */     {
/*  9213 */       updateGeo(paramString, "LA");
/*       */     }
/*  9215 */     if (checkRfaGeoCAN(paramEntityItem))
/*       */     {
/*  9217 */       updateGeo(paramString, "CAN");
/*       */     }
/*  9219 */     if (checkRfaGeoEMEA(paramEntityItem))
/*       */     {
/*  9221 */       updateGeo(paramString, "EMEA");
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
/*  9233 */     String str1 = paramEntityItem.getKey();
/*  9234 */     String str2 = (String)this.usGeoHT.get(str1);
/*       */     
/*  9236 */     if (null == str2) {
/*       */       
/*  9238 */       if (this.gal.isRfaGeoUS(paramEntityItem)) {
/*       */         
/*  9240 */         this.usGeoHT.put(str1, "Y");
/*  9241 */         return true;
/*       */       } 
/*       */ 
/*       */       
/*  9245 */       this.usGeoHT.put(str1, "F");
/*  9246 */       return false;
/*       */     } 
/*       */     
/*  9249 */     if (str2.equals("Y"))
/*       */     {
/*  9251 */       return true;
/*       */     }
/*       */ 
/*       */     
/*  9255 */     return false;
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
/*  9267 */     String str1 = paramEntityItem.getKey();
/*  9268 */     String str2 = (String)this.apGeoHT.get(str1);
/*       */     
/*  9270 */     if (null == str2) {
/*       */       
/*  9272 */       if (this.gal.isRfaGeoAP(paramEntityItem)) {
/*       */         
/*  9274 */         this.apGeoHT.put(str1, "Y");
/*  9275 */         return true;
/*       */       } 
/*       */ 
/*       */       
/*  9279 */       this.apGeoHT.put(str1, "F");
/*  9280 */       return false;
/*       */     } 
/*       */     
/*  9283 */     if (str2.equals("Y"))
/*       */     {
/*  9285 */       return true;
/*       */     }
/*       */ 
/*       */     
/*  9289 */     return false;
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
/*  9301 */     String str1 = paramEntityItem.getKey();
/*  9302 */     String str2 = (String)this.laGeoHT.get(str1);
/*       */     
/*  9304 */     if (null == str2) {
/*       */       
/*  9306 */       if (this.gal.isRfaGeoLA(paramEntityItem)) {
/*       */         
/*  9308 */         this.laGeoHT.put(str1, "Y");
/*  9309 */         return true;
/*       */       } 
/*       */ 
/*       */       
/*  9313 */       this.laGeoHT.put(str1, "F");
/*  9314 */       return false;
/*       */     } 
/*       */     
/*  9317 */     if (str2.equals("Y"))
/*       */     {
/*  9319 */       return true;
/*       */     }
/*       */ 
/*       */     
/*  9323 */     return false;
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
/*  9335 */     String str1 = paramEntityItem.getKey();
/*  9336 */     String str2 = (String)this.canGeoHT.get(str1);
/*       */     
/*  9338 */     if (null == str2) {
/*       */       
/*  9340 */       if (this.gal.isRfaGeoCAN(paramEntityItem)) {
/*       */         
/*  9342 */         this.canGeoHT.put(str1, "Y");
/*  9343 */         return true;
/*       */       } 
/*       */ 
/*       */       
/*  9347 */       this.canGeoHT.put(str1, "F");
/*  9348 */       return false;
/*       */     } 
/*       */     
/*  9351 */     if (str2.equals("Y"))
/*       */     {
/*  9353 */       return true;
/*       */     }
/*       */ 
/*       */     
/*  9357 */     return false;
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
/*  9369 */     String str1 = paramEntityItem.getKey();
/*  9370 */     String str2 = (String)this.emeaGeoHT.get(str1);
/*       */     
/*  9372 */     if (null == str2) {
/*       */       
/*  9374 */       if (this.gal.isRfaGeoEMEA(paramEntityItem)) {
/*       */         
/*  9376 */         this.emeaGeoHT.put(str1, "Y");
/*  9377 */         return true;
/*       */       } 
/*       */ 
/*       */       
/*  9381 */       this.emeaGeoHT.put(str1, "F");
/*  9382 */       return false;
/*       */     } 
/*       */     
/*  9385 */     if (str2.equals("Y"))
/*       */     {
/*  9387 */       return true;
/*       */     }
/*       */ 
/*       */     
/*  9391 */     return false;
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
/*  9403 */     StringBuffer stringBuffer = (StringBuffer)this.geoHT.get(paramString1);
/*       */     
/*  9405 */     if (paramString2.equalsIgnoreCase("US"))
/*       */     {
/*  9407 */       stringBuffer.setCharAt(0, 'Y');
/*       */     }
/*  9409 */     if (paramString2.equalsIgnoreCase("AP"))
/*       */     {
/*  9411 */       stringBuffer.setCharAt(1, 'Y');
/*       */     }
/*  9413 */     if (paramString2.equalsIgnoreCase("LA"))
/*       */     {
/*  9415 */       stringBuffer.setCharAt(2, 'Y');
/*       */     }
/*  9417 */     if (paramString2.equalsIgnoreCase("CAN"))
/*       */     {
/*  9419 */       stringBuffer.setCharAt(3, 'Y');
/*       */     }
/*  9421 */     if (paramString2.equalsIgnoreCase("EMEA"))
/*       */     {
/*  9423 */       stringBuffer.setCharAt(4, 'Y');
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
/*  9435 */     String str = "";
/*       */     
/*  9437 */     boolean bool1 = false;
/*  9438 */     boolean bool2 = false;
/*  9439 */     boolean bool3 = false;
/*  9440 */     boolean bool4 = false;
/*  9441 */     boolean bool5 = false;
/*       */     
/*  9443 */     StringBuffer stringBuffer = (StringBuffer)this.geoHT.get(paramString);
/*  9444 */     log(stringBuffer.toString());
/*  9445 */     if (stringBuffer.charAt(0) == 'Y')
/*       */     {
/*  9447 */       bool5 = true;
/*       */     }
/*  9449 */     if (stringBuffer.charAt(1) == 'Y')
/*       */     {
/*  9451 */       bool1 = true;
/*       */     }
/*  9453 */     if (stringBuffer.charAt(2) == 'Y')
/*       */     {
/*  9455 */       bool4 = true;
/*       */     }
/*  9457 */     if (stringBuffer.charAt(3) == 'Y')
/*       */     {
/*  9459 */       bool2 = true;
/*       */     }
/*  9461 */     if (stringBuffer.charAt(4) == 'Y')
/*       */     {
/*  9463 */       bool3 = true;
/*       */     }
/*       */     
/*  9466 */     if (bool1 && bool2 && bool3 && bool4 && bool5)
/*       */     {
/*  9468 */       return "WW";
/*       */     }
/*       */     
/*  9471 */     if (bool5)
/*       */     {
/*  9473 */       str = "US, ";
/*       */     }
/*  9475 */     if (bool1)
/*       */     {
/*  9477 */       str = str + "AP, ";
/*       */     }
/*  9479 */     if (bool4)
/*       */     {
/*  9481 */       str = str + "LA, ";
/*       */     }
/*  9483 */     if (bool2)
/*       */     {
/*  9485 */       str = str + "CAN, ";
/*       */     }
/*  9487 */     if (bool3)
/*       */     {
/*  9489 */       str = str + "EMEA, ";
/*       */     }
/*       */     
/*  9492 */     if (str.length() > 0)
/*       */     {
/*  9494 */       return str.substring(0, str.length() - 2);
/*       */     }
/*       */ 
/*       */     
/*  9498 */     return "No GEO Found";
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
/*  9515 */     boolean bool = true;
/*  9516 */     for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/*       */       
/*  9518 */       String str = enumeration.nextElement();
/*       */       
/*  9520 */       bool = (bool && entityMatchesAttr(paramEntityItem, str, paramHashtable.get(str).toString())) ? true : false;
/*       */     } 
/*  9522 */     if (bool)
/*       */     {
/*  9524 */       return true;
/*       */     }
/*       */ 
/*       */     
/*  9528 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void addToTreeMap(String paramString1, String paramString2, TreeMap<String, String> paramTreeMap) {
/*  9536 */     paramTreeMap.put(paramString1, paramString2);
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
/*  9548 */     EANEntity eANEntity = null;
/*       */     
/*  9550 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*       */       
/*  9552 */       EANEntity eANEntity1 = paramEntityItem.getUpLink(b);
/*  9553 */       if (eANEntity1.getEntityType().equals(paramString)) {
/*       */         
/*  9555 */         eANEntity = eANEntity1;
/*       */         
/*       */         break;
/*       */       } 
/*       */     } 
/*  9560 */     return eANEntity;
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
/*  9572 */     EANEntity eANEntity = null;
/*       */     
/*  9574 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*       */       
/*  9576 */       EANEntity eANEntity1 = paramEntityItem.getDownLink(b);
/*  9577 */       if (eANEntity1.getEntityType().equals(paramString)) {
/*       */         
/*  9579 */         eANEntity = eANEntity1;
/*       */         
/*       */         break;
/*       */       } 
/*       */     } 
/*  9584 */     return eANEntity;
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
/*       */   private String parseString(String paramString, int paramInt) {
/*  9596 */     int i = paramInt - 1;
/*  9597 */     String str = "";
/*  9598 */     int j = -3;
/*  9599 */     Vector<Integer> vector = new Vector(1);
/*       */     
/*  9601 */     if (0 == paramInt || paramInt < 0)
/*       */     {
/*  9603 */       return paramString;
/*       */     }
/*       */     
/*  9606 */     if (0 == i)
/*       */     {
/*  9608 */       return paramString.substring(0, paramString.indexOf("<:>"));
/*       */     }
/*       */ 
/*       */     
/*       */     while (true) {
/*  9613 */       j = paramString.indexOf("<:>", j + 3);
/*       */       
/*  9615 */       if (j < 0) {
/*       */         break;
/*       */       }
/*       */ 
/*       */       
/*  9620 */       vector.addElement(new Integer(j));
/*       */     } 
/*       */     
/*  9623 */     if (i > vector.size())
/*       */     {
/*  9625 */       return paramString;
/*       */     }
/*       */     
/*  9628 */     if (i == vector.size()) {
/*       */       
/*  9630 */       str = paramString.substring(((Integer)vector.get(i - 1)).intValue() + 3);
/*       */     }
/*       */     else {
/*       */       
/*  9634 */       str = paramString.substring(((Integer)vector.get(i - 1)).intValue() + 3, ((Integer)vector.get(i)).intValue());
/*       */     } 
/*       */     
/*  9637 */     return str;
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
/*  9650 */     int j = 0;
/*  9651 */     int k = 0;
/*  9652 */     int m = 0;
/*  9653 */     byte b1 = 20;
/*  9654 */     byte b2 = 0;
/*       */     
/*  9656 */     byte b3 = 0;
/*       */     
/*  9658 */     byte b4 = 0;
/*       */     
/*  9660 */     paramString = paramString.trim();
/*  9661 */     if (0 == paramString.length()) {
/*       */       
/*  9663 */       String[] arrayOfString = new String[1];
/*  9664 */       arrayOfString[0] = " ";
/*  9665 */       return arrayOfString;
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9673 */     String[] arrayOfString1 = new String[b1]; byte b5;
/*  9674 */     for (b5 = 0; b5 < b1; b5++)
/*       */     {
/*  9676 */       arrayOfString1[b5] = new String();
/*       */     }
/*       */     
/*  9679 */     int i = paramString.length();
/*       */     
/*  9681 */     while (j < i) {
/*       */       
/*  9683 */       if (paramString.charAt(j) == ' ')
/*       */       {
/*  9685 */         m = j;
/*       */       }
/*       */       
/*  9688 */       if (j == k + paramInt)
/*       */       {
/*  9690 */         if (0 == m) {
/*       */           
/*  9692 */           arrayOfString1[b2] = paramString.substring(k, j).trim();
/*       */           
/*  9694 */           k = j;
/*  9695 */           b2++;
/*       */ 
/*       */         
/*       */         }
/*  9699 */         else if (k == m + 1) {
/*       */           
/*  9701 */           arrayOfString1[b2] = paramString.substring(k, j).trim();
/*  9702 */           k = j;
/*  9703 */           b2++;
/*       */         }
/*       */         else {
/*       */           
/*  9707 */           arrayOfString1[b2] = paramString.substring(k, m + 1).trim();
/*  9708 */           k = m + 1;
/*  9709 */           b2++;
/*       */         } 
/*       */       }
/*       */       
/*  9713 */       j++;
/*       */     } 
/*       */     
/*  9716 */     arrayOfString1[b2] = paramString.substring(k).trim();
/*       */     
/*  9718 */     b3 = 0;
/*       */     
/*  9720 */     for (b5 = 0; b5 < 20; b5++) {
/*       */       
/*  9722 */       if (arrayOfString1[b5].length() > 0)
/*       */       {
/*  9724 */         b3++;
/*       */       }
/*       */     } 
/*       */     
/*  9728 */     String[] arrayOfString2 = new String[b3];
/*  9729 */     for (b5 = 0; b5 < b3; b5++)
/*       */     {
/*  9731 */       arrayOfString2[b5] = new String();
/*       */     }
/*       */     
/*  9734 */     b4 = 0;
/*  9735 */     for (b5 = 0; b5 < b1; b5++) {
/*       */       
/*  9737 */       if (arrayOfString1[b5].length() > 0)
/*       */       {
/*  9739 */         arrayOfString2[b4++] = arrayOfString1[b5];
/*       */       }
/*       */     } 
/*       */     
/*  9743 */     return arrayOfString2;
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
/*  9755 */     StringTokenizer stringTokenizer1 = new StringTokenizer(paramString1, paramString2);
/*  9756 */     StringTokenizer stringTokenizer2 = new StringTokenizer(paramString1, paramString2);
/*  9757 */     byte b = 0;
/*       */ 
/*       */     
/*  9760 */     while (stringTokenizer1.hasMoreTokens()) {
/*       */       
/*  9762 */       b++;
/*  9763 */       stringTokenizer1.nextToken();
/*       */     } 
/*       */     
/*  9766 */     String[] arrayOfString = new String[b];
/*       */     
/*  9768 */     b = 0;
/*  9769 */     while (stringTokenizer2.hasMoreTokens()) {
/*       */       
/*  9771 */       arrayOfString[b] = stringTokenizer2.nextToken();
/*  9772 */       b++;
/*       */     } 
/*       */     
/*  9775 */     return arrayOfString;
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
/*  9788 */     if (!paramBoolean) {
/*       */       
/*  9790 */       for (byte b = 0; b < paramInt; b++)
/*       */       {
/*  9792 */         paramStringBuffer.append(paramString);
/*       */       }
/*       */     }
/*       */     else {
/*       */       
/*  9797 */       paramStringBuffer.append("|");
/*  9798 */       for (byte b = 0; b < paramInt - 2; b++)
/*       */       {
/*  9800 */         paramStringBuffer.append(paramString);
/*       */       }
/*  9802 */       paramStringBuffer.append("|");
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
/*  9817 */     boolean bool = true;
/*       */     
/*  9819 */     if (!paramBoolean) {
/*       */       
/*  9821 */       for (byte b = 0; b < paramInt; b++) {
/*       */         
/*  9823 */         if (bool)
/*       */         {
/*  9825 */           paramStringBuffer.append(paramString1);
/*  9826 */           bool = false;
/*       */         }
/*       */         else
/*       */         {
/*  9830 */           paramStringBuffer.append(paramString2);
/*  9831 */           bool = true;
/*       */         }
/*       */       
/*       */       } 
/*       */     } else {
/*       */       
/*  9837 */       paramStringBuffer.append("|");
/*  9838 */       for (byte b = 0; b < paramInt - 2; b++) {
/*       */         
/*  9840 */         if (bool) {
/*       */           
/*  9842 */           paramStringBuffer.append(paramString1);
/*  9843 */           bool = false;
/*       */         }
/*       */         else {
/*       */           
/*  9847 */           paramStringBuffer.append(paramString2);
/*  9848 */           bool = true;
/*       */         } 
/*       */       } 
/*  9851 */       paramStringBuffer.append("|");
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
/*  9862 */     Date date = new Date();
/*  9863 */     String str = new String();
/*       */     
/*  9865 */     StringBuffer stringBuffer = new StringBuffer();
/*       */     
/*  9867 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
/*  9868 */     str = simpleDateFormat.format(date);
/*       */     
/*  9870 */     stringBuffer.append("Created on ");
/*  9871 */     stringBuffer.append(str);
/*       */     
/*  9873 */     if (stringBuffer.length() > 22)
/*       */     {
/*  9875 */       stringBuffer.insert(22, "at ");
/*       */     }
/*       */     
/*  9878 */     return stringBuffer.toString();
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
/*  9891 */     int i = paramString.length();
/*       */     
/*  9893 */     if (paramInt == 0)
/*       */     {
/*  9895 */       return new String();
/*       */     }
/*       */     
/*  9898 */     if (i > paramInt)
/*       */     {
/*  9900 */       return paramString.substring(0, paramInt);
/*       */     }
/*       */ 
/*       */     
/*  9904 */     StringBuffer stringBuffer = new StringBuffer();
/*  9905 */     stringBuffer.append(paramString);
/*  9906 */     for (int j = i; j < paramInt; j++)
/*       */     {
/*  9908 */       stringBuffer.append(" ");
/*       */     }
/*  9910 */     return stringBuffer.toString();
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTags(String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  9916 */     if (paramString1.equals("")) {
/*       */       
/*  9918 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9924 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  9933 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9939 */       if (!paramString1.equals("WW"))
/*       */       {
/*  9941 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer);
/*       */       }
/*  9943 */       if (!paramString2.equals("WW"))
/*       */       {
/*  9945 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
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
/*  9957 */     if (paramString1.equals("")) {
/*       */       
/*  9959 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9965 */         paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/*  9966 */         paramStringBuffer1.append(":xmp." + NEWLINE);
/*  9967 */         paramStringBuffer1.append(".kp off" + NEWLINE);
/*  9968 */         paramStringBuffer1.append(paramStringBuffer2.toString());
/*  9969 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer1);
/*       */ 
/*       */       
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*       */         
/*  9977 */         paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/*  9978 */         paramStringBuffer1.append(":xmp." + NEWLINE);
/*  9979 */         paramStringBuffer1.append(".kp off" + NEWLINE);
/*  9980 */         paramStringBuffer1.append(paramStringBuffer2.toString());
/*       */       
/*       */       }
/*       */     
/*       */     }
/*  9985 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9991 */       if (!paramString1.equals("WW")) {
/*       */         
/*  9993 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer1);
/*  9994 */         paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/*  9995 */         paramStringBuffer1.append(":xmp." + NEWLINE);
/*  9996 */         paramStringBuffer1.append(".kp off" + NEWLINE);
/*  9997 */         paramStringBuffer1.append(paramStringBuffer2.toString());
/*       */       }
/*       */       else {
/*       */         
/* 10001 */         paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/* 10002 */         paramStringBuffer1.append(":xmp." + NEWLINE);
/* 10003 */         paramStringBuffer1.append(".kp off" + NEWLINE);
/* 10004 */         paramStringBuffer1.append(paramStringBuffer2.toString());
/*       */       } 
/* 10006 */       if (!paramString2.equals("WW"))
/*       */       {
/* 10008 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer1);
/*       */ 
/*       */       
/*       */       }
/*       */     
/*       */     }
/*       */     else {
/*       */ 
/*       */       
/* 10017 */       paramStringBuffer1.append(":exmp." + NEWLINE + NEWLINE);
/* 10018 */       paramStringBuffer1.append(":xmp." + NEWLINE);
/* 10019 */       paramStringBuffer1.append(".kp off" + NEWLINE);
/* 10020 */       paramStringBuffer1.append(paramStringBuffer2.toString());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTags2(String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10027 */     if (paramString1.equals("")) {
/*       */       
/* 10029 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10035 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/* 10037 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */       
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*       */         
/* 10045 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/* 10051 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 10057 */       if (!paramString1.equals("WW")) {
/*       */         
/* 10059 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer);
/* 10060 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 10064 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       } 
/* 10066 */       if (!paramString2.equals("WW"))
/*       */       {
/* 10068 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/* 10070 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       else
/*       */       {
/* 10074 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/*       */     else {
/*       */ 
/*       */       
/* 10084 */       paramStringBuffer.append(":exmp." + NEWLINE);
/* 10085 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTags3(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10094 */     if (paramString1.equals("")) {
/*       */       
/* 10096 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10102 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/* 10104 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */       
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*       */         
/* 10112 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/* 10118 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 10124 */       if (!paramString1.equals("WW")) {
/*       */         
/* 10126 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer);
/* 10127 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 10131 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       } 
/* 10133 */       if (!paramString2.equals("WW"))
/*       */       {
/* 10135 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/* 10137 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       else
/*       */       {
/* 10141 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 10151 */     else if (!paramString4.equals(paramString3) || !paramString6.equals(paramString5)) {
/*       */       
/* 10153 */       paramStringBuffer.append(":exmp." + NEWLINE);
/* 10154 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void setGeoTagsFeatConv(String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10163 */     if (paramString1.equals("")) {
/*       */       
/* 10165 */       if (!paramString2.equals("WW"))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10171 */         paramStringBuffer.append(".sk 1" + NEWLINE);
/* 10172 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/* 10174 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */ 
/*       */       
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*       */         
/* 10182 */         paramStringBuffer.append(".sk 1" + NEWLINE);
/* 10183 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/* 10189 */     else if (!paramString1.equals(paramString2)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 10195 */       if (!paramString1.equals("WW")) {
/*       */         
/* 10197 */         bldEndGeoTags(paramString1, paramBoolean, paramStringBuffer);
/* 10198 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       }
/*       */       else {
/*       */         
/* 10202 */         paramStringBuffer.append(":exmp." + NEWLINE);
/*       */       } 
/* 10204 */       if (!paramString2.equals("WW"))
/*       */       {
/* 10206 */         paramStringBuffer.append(".sk 1" + NEWLINE);
/* 10207 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */         
/* 10209 */         bldBgnGeoTags(paramString2, paramBoolean, paramStringBuffer);
/*       */       }
/*       */       else
/*       */       {
/* 10213 */         paramStringBuffer.append(".sk 1" + NEWLINE);
/* 10214 */         paramStringBuffer.append(":xmp." + NEWLINE);
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/*       */     else {
/*       */ 
/*       */       
/* 10224 */       paramStringBuffer.append(":exmp." + NEWLINE);
/* 10225 */       paramStringBuffer.append(".sk 1" + NEWLINE);
/* 10226 */       paramStringBuffer.append(":xmp." + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void bldBgnGeoTags(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10234 */     if (paramBoolean == true) {
/*       */       
/* 10236 */       paramStringBuffer.append(":p.:hp2.");
/*       */     }
/*       */     else {
/*       */       
/* 10240 */       paramStringBuffer.append("<b>");
/*       */     } 
/*       */     
/* 10243 */     paramStringBuffer.append(paramString);
/*       */     
/* 10245 */     if (paramBoolean == true) {
/*       */       
/* 10247 */       paramStringBuffer.append("--->:ehp2." + NEWLINE);
/*       */       
/* 10249 */       if (2 == this.format)
/*       */       {
/* 10251 */         paramStringBuffer.append(".br" + NEWLINE);
/*       */       }
/*       */     }
/*       */     else {
/*       */       
/* 10256 */       paramStringBuffer.append("---&gt</b>" + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   private void bldEndGeoTags(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10262 */     if (paramBoolean == true) {
/*       */       
/* 10264 */       paramStringBuffer.append(".br;:hp2.<---");
/*       */     }
/*       */     else {
/*       */       
/* 10268 */       paramStringBuffer.append("<b>&lt---");
/*       */     } 
/*       */     
/* 10271 */     paramStringBuffer.append(paramString);
/*       */     
/* 10273 */     if (paramBoolean == true) {
/*       */       
/* 10275 */       paramStringBuffer.append(":ehp2." + NEWLINE);
/*       */     
/*       */     }
/*       */     else {
/*       */       
/* 10280 */       paramStringBuffer.append("</b>" + NEWLINE);
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   private void printBeginGeoTags(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10286 */     if (!paramString.equals("WW") && !paramString.equals("")) {
/*       */       
/* 10288 */       if (paramBoolean == true) {
/*       */         
/* 10290 */         paramStringBuffer.append(":p.:hp2.");
/*       */       
/*       */       }
/*       */       else {
/*       */         
/* 10295 */         paramStringBuffer.append("<b>");
/*       */       } 
/*       */       
/* 10298 */       paramStringBuffer.append(paramString);
/*       */       
/* 10300 */       if (paramBoolean == true) {
/*       */         
/* 10302 */         paramStringBuffer.append("--->:ehp2." + NEWLINE);
/*       */         
/* 10304 */         if (2 == this.format)
/*       */         {
/* 10306 */           paramStringBuffer.append(".br" + NEWLINE);
/*       */         }
/*       */       }
/*       */       else {
/*       */         
/* 10311 */         paramStringBuffer.append("---&gt</b>" + NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void printEndGeoTags(String paramString, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 10319 */     if (!paramString.equals("WW") && !paramString.equals("")) {
/*       */       
/* 10321 */       if (paramBoolean == true) {
/*       */         
/* 10323 */         paramStringBuffer.append(".br;:hp2.<---");
/*       */       
/*       */       }
/*       */       else {
/*       */         
/* 10328 */         paramStringBuffer.append("<b>&lt---");
/*       */       } 
/*       */       
/* 10331 */       paramStringBuffer.append(paramString);
/*       */       
/* 10333 */       if (paramBoolean == true) {
/*       */         
/* 10335 */         paramStringBuffer.append(":ehp2." + NEWLINE);
/*       */       
/*       */       }
/*       */       else {
/*       */ 
/*       */         
/* 10341 */         paramStringBuffer.append("</b>" + NEWLINE);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   private void log(String paramString) {
/* 10348 */     if (this.debug) {
/*       */       
/* 10350 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
/* 10351 */       String str = simpleDateFormat.format(new Date());
/* 10352 */       this.debugBuff.append("</br>" + str + "  " + paramString + NEWLINE);
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
/* 10363 */     return this.debugBuff;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void cleanUp() {
/* 10372 */     Iterator<String> iterator = this.machineTypeTS.iterator();
/*       */     
/* 10374 */     while (iterator.hasNext()) {
/*       */       
/* 10376 */       String str = iterator.next();
/* 10377 */       log("machinetype = " + str);
/*       */     } 
/*       */     
/* 10380 */     this.usGeoHT.clear();
/* 10381 */     this.usGeoHT = null;
/* 10382 */     this.apGeoHT.clear();
/* 10383 */     this.apGeoHT = null;
/* 10384 */     this.laGeoHT.clear();
/* 10385 */     this.laGeoHT = null;
/* 10386 */     this.canGeoHT.clear();
/* 10387 */     this.canGeoHT = null;
/* 10388 */     this.emeaGeoHT.clear();
/* 10389 */     this.emeaGeoHT = null;
/*       */     
/* 10391 */     this.geoHT.clear();
/* 10392 */     this.geoHT = null;
/*       */     
/* 10394 */     this.machineTypeTS.clear();
/* 10395 */     this.machineTypeTS = null;
/* 10396 */     this.featureHT.clear();
/* 10397 */     this.featureHT = null;
/*       */     
/* 10399 */     this.productNumber_NewModels_TM.clear();
/* 10400 */     this.productNumber_NewModels_TM = null;
/* 10401 */     this.productNumber_NewModels_HT.clear();
/* 10402 */     this.productNumber_NewModels_HT = null;
/* 10403 */     this.productNumber_NewFC_TM.clear();
/* 10404 */     this.productNumber_NewFC_TM = null;
/* 10405 */     this.productNumber_ExistingFC_TM.clear();
/* 10406 */     this.productNumber_ExistingFC_TM = null;
/* 10407 */     this.productNumber_NewModels_NewFC_TM.clear();
/* 10408 */     this.productNumber_NewModels_NewFC_TM = null;
/* 10409 */     this.productNumber_NewModels_ExistingFC_TM.clear();
/* 10410 */     this.productNumber_NewModels_ExistingFC_TM = null;
/* 10411 */     this.productNumber_ExistingModels_NewFC_TM.clear();
/* 10412 */     this.productNumber_ExistingModels_NewFC_TM = null;
/* 10413 */     this.productNumber_ExistingModels_ExistingFC_TM.clear();
/* 10414 */     this.productNumber_ExistingModels_ExistingFC_TM = null;
/*       */     
/* 10416 */     this.productNumber_MTM_Conversions_TM.clear();
/* 10417 */     this.productNumber_MTM_Conversions_TM = null;
/* 10418 */     this.productNumber_Model_Conversions_TM.clear();
/* 10419 */     this.productNumber_Model_Conversions_TM = null;
/* 10420 */     this.productNumber_Feature_Conversions_TM.clear();
/* 10421 */     this.productNumber_Feature_Conversions_TM = null;
/* 10422 */     this.featureVector.clear();
/* 10423 */     this.featureVector = null;
/*       */     
/* 10425 */     this.charges_NewModels_TM.clear();
/* 10426 */     this.charges_NewModels_TM = null;
/* 10427 */     this.charges_NewFC_TM.clear();
/* 10428 */     this.charges_NewFC_TM = null;
/* 10429 */     this.charges_ExistingFC_TM.clear();
/* 10430 */     this.charges_ExistingFC_TM = null;
/* 10431 */     this.charges_NewModels_NewFC_TM.clear();
/* 10432 */     this.charges_NewModels_NewFC_TM = null;
/* 10433 */     this.charges_NewModels_ExistingFC_TM.clear();
/* 10434 */     this.charges_NewModels_ExistingFC_TM = null;
/* 10435 */     this.charges_ExistingModels_NewFC_TM.clear();
/* 10436 */     this.charges_ExistingModels_NewFC_TM = null;
/* 10437 */     this.charges_ExistingModels_ExistingFC_TM.clear();
/* 10438 */     this.charges_ExistingModels_ExistingFC_TM = null;
/* 10439 */     this.charges_Feature_Conversions_TM.clear();
/* 10440 */     this.charges_Feature_Conversions_TM = null;
/*       */     
/* 10442 */     this.salesManual_TM.clear();
/* 10443 */     this.salesManual_TM = null;
/* 10444 */     this.salesManualSpecifyFeatures_TM.clear();
/* 10445 */     this.salesManualSpecifyFeatures_TM = null;
/* 10446 */     this.salesManualSpecialFeaturesInitialOrder_TM.clear();
/* 10447 */     this.salesManualSpecialFeaturesInitialOrder_TM = null;
/* 10448 */     this.salesManualSpecialFeaturesOther_TM.clear();
/* 10449 */     this.salesManualSpecialFeaturesOther_TM = null;
/*       */     
/* 10451 */     this.supportedDevices_TM.clear();
/* 10452 */     this.supportedDevices_TM = null;
/*       */     
/* 10454 */     this.featureMatrix_TM.clear();
/* 10455 */     this.featureMatrix_TM = null;
/*       */     
/* 10457 */     this.headerSB.delete(0, this.headerSB.length());
/* 10458 */     this.headerSB = null;
/*       */     
/* 10460 */     this.featureMatrixError.clear();
/* 10461 */     this.featureMatrixError = null;
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
/* 10475 */     return getAttributeValue(paramEntityItem, paramString1, paramString2, paramString3, true, "eannounce");
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
/* 10491 */     return getAttributeValue(paramEntityItem, paramString1, paramString2, paramString3, paramBoolean, "eannounce");
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
/* 10508 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 10509 */     StringBuffer stringBuffer = new StringBuffer();
/* 10510 */     EANAttribute eANAttribute = null;
/* 10511 */     if (eANMetaAttribute == null) {
/* 10512 */       return "<font color=\"red\">Attribute &quot;" + paramString1 + "&quot; NOT found in &quot;" + paramEntityItem
/* 10513 */         .getEntityType() + "&quot; META data.</font>";
/*       */     }
/* 10515 */     eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 10516 */     if (eANAttribute == null) {
/* 10517 */       return paramString3;
/*       */     }
/* 10519 */     if (eANAttribute instanceof EANFlagAttribute) {
/*       */ 
/*       */       
/* 10522 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 10523 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*       */ 
/*       */         
/* 10526 */         if (arrayOfMetaFlag[b].isSelected()) {
/*       */           
/* 10528 */           if (stringBuffer.length() > 0) {
/* 10529 */             stringBuffer.append(paramString2);
/*       */           }
/* 10531 */           if (paramBoolean) {
/* 10532 */             stringBuffer.append(convertToHTML(arrayOfMetaFlag[b].toString()));
/*       */           } else {
/* 10534 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/* 10535 */           }  if (eANMetaAttribute.getAttributeType().equals("U")) {
/*       */             break;
/*       */           }
/*       */         } 
/*       */       } 
/* 10540 */     } else if (eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/*       */ 
/*       */       
/* 10543 */       if (eANMetaAttribute.getAttributeType().equals("T") || eANMetaAttribute.getAttributeType().equals("L") || eANMetaAttribute
/* 10544 */         .getAttributeType().equals("I")) {
/*       */ 
/*       */         
/* 10547 */         if (paramBoolean) {
/* 10548 */           stringBuffer.append(convertToHTML(eANAttribute.get().toString()));
/*       */         } else {
/* 10550 */           stringBuffer.append(eANAttribute.get().toString());
/*       */         } 
/*       */       } else {
/* 10553 */         stringBuffer.append(eANAttribute.get().toString());
/*       */       } 
/* 10555 */     } else if (eANAttribute instanceof EANBlobAttribute) {
/*       */ 
/*       */       
/* 10558 */       if (eANMetaAttribute.getAttributeType().equals("B")) {
/*       */         
/* 10560 */         EANBlobAttribute eANBlobAttribute = (EANBlobAttribute)eANAttribute;
/*       */ 
/*       */ 
/*       */         
/* 10564 */         if (eANBlobAttribute.getBlobExtension().toUpperCase().endsWith(".GIF") || eANBlobAttribute
/* 10565 */           .getBlobExtension().toUpperCase().endsWith(".JPG"))
/*       */         {
/* 10567 */           stringBuffer.append("<img src='/" + paramString4 + "/GetBlobAttribute?entityID=" + paramEntityItem.getEntityID() + "&entityType=" + paramEntityItem
/* 10568 */               .getEntityType() + "&attributeCode=" + paramString1 + "' alt='image' />");
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
/* 10586 */           stringBuffer.append("<form action=\"/" + paramString4 + "/PokXMLDownload\" name=\"" + paramEntityItem.getEntityType() + paramEntityItem
/* 10587 */               .getEntityID() + paramString1 + "\" method=\"post\"> " + NEWLINE);
/* 10588 */           stringBuffer.append("<input type=\"hidden\" name=\"entityType\" value=\"" + paramEntityItem.getEntityType() + "\" />" + NEWLINE);
/* 10589 */           stringBuffer.append("<input type=\"hidden\" name=\"entityID\" value=\"" + paramEntityItem.getEntityID() + "\" />" + NEWLINE);
/* 10590 */           stringBuffer.append("<input type=\"hidden\" name=\"downloadType\" value=\"blob\" />" + NEWLINE);
/* 10591 */           stringBuffer.append("<input type=\"hidden\" name=\"attributeCode\" value=\"" + paramString1 + "\" />" + NEWLINE);
/* 10592 */           stringBuffer.append("<input type=\"submit\" value=\"Download\" />" + NEWLINE);
/* 10593 */           stringBuffer.append("</form>" + NEWLINE);
/*       */         
/*       */         }
/*       */       
/*       */       }
/*       */       else {
/*       */         
/* 10600 */         stringBuffer.append("<font color=\"red\">Blob Attribute type &quot;" + eANMetaAttribute.getAttributeType() + "&quot; for " + paramString1 + " NOT yet supported</font>");
/*       */       } 
/*       */     } 
/*       */     
/* 10604 */     if (stringBuffer.length() == 0) {
/* 10605 */       return paramString3;
/*       */     }
/* 10607 */     return stringBuffer.toString();
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
/* 10618 */     StringBuffer stringBuffer = new StringBuffer();
/* 10619 */     StringCharacterIterator stringCharacterIterator = null;
/* 10620 */     char c = ' ';
/* 10621 */     if (paramString == null) {
/* 10622 */       return null;
/*       */     }
/* 10624 */     stringCharacterIterator = new StringCharacterIterator(paramString);
/* 10625 */     c = stringCharacterIterator.first();
/* 10626 */     while (c != '￿') {
/*       */       
/* 10628 */       switch (c) {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*       */         case '"':
/*       */         case '<':
/*       */         case '>':
/* 10636 */           stringBuffer.append("&#" + c + ";");
/*       */           break;
/*       */         case '\n':
/* 10639 */           stringBuffer.append("<br />");
/*       */           break;
/*       */         default:
/* 10642 */           if (Character.isSpaceChar(c)) {
/*       */             
/* 10644 */             stringBuffer.append("&#32;");
/*       */             
/*       */             break;
/*       */           } 
/*       */           
/* 10649 */           stringBuffer.append(c);
/*       */           break;
/*       */       } 
/* 10652 */       c = stringCharacterIterator.next();
/*       */     } 
/*       */     
/* 10655 */     return stringBuffer.toString();
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
/* 10667 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString);
/*       */     
/* 10669 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 10670 */     if (eANAttribute == null) {
/* 10671 */       return null;
/*       */     }
/* 10673 */     if (eANAttribute instanceof EANFlagAttribute) {
/*       */       
/* 10675 */       StringBuffer stringBuffer = new StringBuffer();
/*       */ 
/*       */       
/* 10678 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 10679 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*       */ 
/*       */         
/* 10682 */         if (arrayOfMetaFlag[b].isSelected()) {
/*       */           
/* 10684 */           if (stringBuffer.length() > 0)
/* 10685 */             stringBuffer.append("|"); 
/* 10686 */           stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/* 10687 */           if (eANMetaAttribute.getAttributeType().equals("U"))
/*       */             break; 
/*       */         } 
/*       */       } 
/* 10691 */       return stringBuffer.toString();
/*       */     } 
/*       */     
/* 10694 */     return null;
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
/* 10711 */     Vector vector = new Vector(1);
/* 10712 */     if (paramEntityGroup == null) {
/* 10713 */       return vector;
/*       */     }
/* 10715 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*       */       
/* 10717 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 10718 */       getLinkedEntities(entityItem, paramString1, paramString2, vector);
/*       */     } 
/*       */     
/* 10721 */     return vector;
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
/* 10738 */     Vector vector = new Vector(1);
/*       */     
/* 10740 */     Iterator<EntityItem> iterator = paramVector.iterator();
/* 10741 */     while (iterator.hasNext()) {
/*       */       
/* 10743 */       EntityItem entityItem = iterator.next();
/* 10744 */       getLinkedEntities(entityItem, paramString1, paramString2, vector);
/*       */     } 
/*       */     
/* 10747 */     return vector;
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
/* 10764 */     Vector vector = new Vector(1);
/*       */     
/* 10766 */     getLinkedEntities(paramEntityItem, paramString1, paramString2, vector);
/*       */     
/* 10768 */     return vector;
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
/* 10784 */     if (paramEntityItem == null) {
/*       */       return;
/*       */     }
/*       */     byte b;
/* 10788 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*       */       
/* 10790 */       EANEntity eANEntity = paramEntityItem.getUpLink(b);
/* 10791 */       if (eANEntity.getEntityType().equals(paramString1))
/*       */       {
/*       */         
/* 10794 */         for (byte b1 = 0; b1 < eANEntity.getUpLinkCount(); b1++) {
/*       */           
/* 10796 */           EANEntity eANEntity1 = eANEntity.getUpLink(b1);
/* 10797 */           if (eANEntity1.getEntityType().equals(paramString2) && !paramVector.contains(eANEntity1)) {
/* 10798 */             paramVector.addElement(eANEntity1);
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
/* 10811 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*       */       
/* 10813 */       EANEntity eANEntity = paramEntityItem.getDownLink(b);
/* 10814 */       if (eANEntity.getEntityType().equals(paramString1))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10824 */         for (byte b1 = 0; b1 < eANEntity.getDownLinkCount(); b1++) {
/*       */           
/* 10826 */           EANEntity eANEntity1 = eANEntity.getDownLink(b1);
/* 10827 */           if (eANEntity1.getEntityType().equals(paramString2) && !paramVector.contains(eANEntity1)) {
/* 10828 */             paramVector.addElement(eANEntity1);
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
/* 10847 */     Vector<EntityItem> vector = new Vector(1);
/*       */     
/* 10849 */     Iterator<EntityItem> iterator = paramVector.iterator();
/* 10850 */     while (iterator.hasNext()) {
/*       */       
/* 10852 */       EntityItem entityItem = iterator.next();
/* 10853 */       if (entityMatchesAttr(entityItem, paramString1, paramString2)) {
/* 10854 */         vector.addElement(entityItem);
/*       */       }
/*       */     } 
/* 10857 */     return vector;
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
/* 10873 */     Vector<EntityItem> vector = new Vector(1);
/* 10874 */     if (paramEntityGroup == null) {
/* 10875 */       return vector;
/*       */     }
/*       */     
/* 10878 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*       */       
/* 10880 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 10881 */       if (entityMatchesAttr(entityItem, paramString1, paramString2)) {
/* 10882 */         vector.addElement(entityItem);
/*       */       }
/*       */     } 
/* 10885 */     return vector;
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
/* 10900 */     Vector<EntityItem> vector = new Vector(1);
/*       */     
/* 10902 */     Iterator<EntityItem> iterator = paramVector.iterator();
/* 10903 */     while (iterator.hasNext()) {
/*       */       
/* 10905 */       EntityItem entityItem = iterator.next();
/* 10906 */       boolean bool = true;
/* 10907 */       for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/*       */         
/* 10909 */         String str = enumeration.nextElement();
/*       */         
/* 10911 */         bool = (bool && entityMatchesAttr(entityItem, str, paramHashtable.get(str).toString())) ? true : false;
/*       */       } 
/* 10913 */       if (bool) {
/* 10914 */         vector.addElement(entityItem);
/*       */       }
/*       */     } 
/* 10917 */     return vector;
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
/* 10932 */     Vector<EntityItem> vector = new Vector(1);
/* 10933 */     if (paramEntityGroup == null) {
/* 10934 */       return vector;
/*       */     }
/*       */     
/* 10937 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*       */       
/* 10939 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 10940 */       boolean bool = true;
/* 10941 */       for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/*       */         
/* 10943 */         String str = enumeration.nextElement();
/*       */         
/* 10945 */         bool = (bool && entityMatchesAttr(entityItem, str, paramHashtable.get(str).toString())) ? true : false;
/*       */       } 
/* 10947 */       if (bool) {
/* 10948 */         vector.addElement(entityItem);
/*       */       }
/*       */     } 
/* 10951 */     return vector;
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
/* 10967 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 10968 */     if (eANAttribute == null) {
/* 10969 */       return false;
/*       */     }
/* 10971 */     if (eANAttribute instanceof EANFlagAttribute) {
/*       */       
/* 10973 */       Vector<String> vector = new Vector(1);
/* 10974 */       String[] arrayOfString = null;
/* 10975 */       byte b1 = 0;
/*       */       
/* 10977 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get(); byte b2;
/* 10978 */       for (b2 = 0; b2 < arrayOfMetaFlag.length; b2++) {
/*       */ 
/*       */         
/* 10981 */         if (arrayOfMetaFlag[b2].isSelected())
/*       */         {
/* 10983 */           vector.addElement(arrayOfMetaFlag[b2].getFlagCode());
/*       */         }
/*       */       } 
/*       */ 
/*       */       
/* 10988 */       arrayOfString = convertToArray(paramString2);
/* 10989 */       for (b2 = 0; b2 < arrayOfString.length; b2++) {
/*       */         
/* 10991 */         if (vector.contains(arrayOfString[b2]))
/* 10992 */           b1++; 
/*       */       } 
/* 10994 */       if (b1 == arrayOfString.length) {
/* 10995 */         return true;
/*       */       }
/*       */     }
/* 10998 */     else if (eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/*       */ 
/*       */       
/* 11001 */       return eANAttribute.get().toString().equals(paramString2);
/*       */     } 
/*       */     
/* 11004 */     return false;
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
/* 11016 */     EANAttribute eANAttribute = null;
/* 11017 */     if (paramEntityItem == null)
/* 11018 */       return false; 
/* 11019 */     if (paramString1 == null)
/* 11020 */       return false; 
/* 11021 */     if (paramString2 == null) {
/* 11022 */       return false;
/*       */     }
/*       */     
/* 11025 */     eANAttribute = paramEntityItem.getAttribute(paramString1);
/*       */     
/* 11027 */     if (eANAttribute instanceof EANFlagAttribute) {
/* 11028 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 11029 */       EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 11030 */       MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(paramString2);
/* 11031 */       if (metaFlag == null)
/* 11032 */         return false; 
/* 11033 */       return eANFlagAttribute.isSelected(metaFlag);
/*       */     } 
/* 11035 */     return false;
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
/* 11047 */     Vector<String> vector = new Vector();
/* 11048 */     String[] arrayOfString = null;
/*       */     
/* 11050 */     if (paramString != null) {
/*       */       
/* 11052 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString, "|");
/* 11053 */       while (stringTokenizer.hasMoreTokens())
/*       */       {
/* 11055 */         vector.addElement(stringTokenizer.nextToken());
/*       */       }
/*       */     } 
/* 11058 */     arrayOfString = new String[vector.size()];
/* 11059 */     vector.copyInto((Object[])arrayOfString);
/* 11060 */     return arrayOfString;
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
/* 11072 */     StringBuffer stringBuffer = new StringBuffer();
/* 11073 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 11074 */     if (entityGroup != null) {
/*       */       
/* 11076 */       stringBuffer.append(entityGroup.getEntityType() + " : " + entityGroup.getEntityItemCount() + " Parent entity items. ");
/* 11077 */       if (entityGroup.getEntityItemCount() > 0) {
/*       */         
/* 11079 */         stringBuffer.append("IDs(");
/* 11080 */         for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++)
/*       */         {
/* 11082 */           stringBuffer.append(" " + entityGroup.getEntityItem(b1).getEntityID());
/*       */         }
/* 11084 */         stringBuffer.append(")");
/*       */       } 
/* 11086 */       stringBuffer.append(NEWLINE);
/*       */     } 
/*       */     
/* 11089 */     for (byte b = 0; b < paramEntityList.getEntityGroupCount(); b++) {
/*       */       
/* 11091 */       EntityGroup entityGroup1 = paramEntityList.getEntityGroup(b);
/* 11092 */       stringBuffer.append(entityGroup1.getEntityType() + " : " + entityGroup1.getEntityItemCount() + " entity items. ");
/* 11093 */       if (entityGroup1.getEntityItemCount() > 0) {
/*       */         
/* 11095 */         stringBuffer.append("IDs(");
/* 11096 */         for (byte b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++)
/*       */         {
/* 11098 */           stringBuffer.append(" " + entityGroup1.getEntityItem(b1).getEntityID());
/*       */         }
/* 11100 */         stringBuffer.append(")");
/*       */       } 
/* 11102 */       stringBuffer.append(NEWLINE);
/*       */     } 
/* 11104 */     return stringBuffer.toString();
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
/* 11120 */     boolean bool = true;
/*       */ 
/*       */ 
/*       */     
/* 11124 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*       */ 
/*       */     
/* 11127 */     hashtable.clear();
/* 11128 */     hashtable.put("COFCAT", "100");
/* 11129 */     hashtable.put("COFSUBCAT", "126");
/* 11130 */     hashtable.put("COFGRP", "150");
/*       */     
/* 11132 */     log(paramEntityItem.toString());
/*       */     
/* 11134 */     String str1 = "EXRPT3FM";
/* 11135 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 11136 */     arrayOfEntityItem[0] = paramEntityItem;
/* 11137 */     EntityList entityList = null;
/*       */     
/* 11139 */     String str2 = paramEntityItem.getKey();
/* 11140 */     String str3 = getAttributeValue(paramEntityItem, "FEATURECODE", "", "", false);
/* 11141 */     str3 = str3.trim();
/* 11142 */     str3 = setString(str3, 4);
/*       */     
/* 11144 */     if (this.featureHT.containsKey(str2)) {
/*       */       
/* 11146 */       String str = (String)this.featureHT.get(str2);
/* 11147 */       log("---> found it " + paramEntityItem.toString());
/* 11148 */       if (str.equals("New"))
/*       */       {
/* 11150 */         return true;
/*       */       }
/*       */ 
/*       */       
/* 11154 */       return false;
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/* 11159 */     String str4 = "";
/* 11160 */     TreeSet<String> treeSet = new TreeSet();
/*       */     
/* 11162 */     Iterator<EntityItem> iterator = this.availVector.iterator();
/* 11163 */     while (iterator.hasNext()) {
/*       */       
/* 11165 */       EntityItem entityItem = iterator.next();
/* 11166 */       Vector<EntityItem> vector = getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/* 11167 */       for (byte b = 0; b < vector.size(); b++) {
/*       */         
/* 11169 */         EntityItem entityItem1 = vector.get(b);
/*       */         
/* 11171 */         EANEntity eANEntity = getDownLinkEntityItem(entityItem1, "MODEL");
/* 11172 */         if (null != eANEntity)
/*       */         {
/* 11174 */           if (isEntityWithMatchedAttr((EntityItem)eANEntity, hashtable)) {
/*       */             
/* 11176 */             EntityItem entityItem2 = (EntityItem)eANEntity;
/* 11177 */             EANEntity eANEntity1 = getUpLinkEntityItem(entityItem1, "FEATURE");
/* 11178 */             if (null != eANEntity1) {
/*       */               
/* 11180 */               EntityItem entityItem3 = (EntityItem)eANEntity1;
/* 11181 */               String str = getAttributeValue(entityItem3, "FEATURECODE", "", "", false);
/* 11182 */               str = str.trim();
/* 11183 */               str = setString(str, 4);
/*       */               
/* 11185 */               if (str.equals(str3)) {
/*       */                 
/* 11187 */                 str4 = getAttributeFlagValue(entityItem2, "MACHTYPEATR");
/* 11188 */                 if (null == str4)
/*       */                 {
/* 11190 */                   str4 = " ";
/*       */                 }
/* 11192 */                 str4 = str4.trim();
/* 11193 */                 str4 = setString(str4, 4);
/* 11194 */                 treeSet.add(str4);
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
/* 11205 */       Vector<EntityItem> vector = new Vector();
/* 11206 */       Profile profile = this.list.getProfile();
/* 11207 */       entityList = this.dbCurrent.getEntityList(profile, new ExtractActionItem(null, this.dbCurrent, profile, str1), arrayOfEntityItem);
/*       */ 
/*       */ 
/*       */       
/* 11211 */       if (entityList.getEntityGroupCount() == 0)
/*       */       {
/* 11213 */         log("Extract was not found for " + str1);
/*       */       }
/*       */       
/* 11216 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/*       */       
/* 11218 */       paramEntityItem = entityGroup.getEntityItem(0);
/*       */       byte b;
/* 11220 */       for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*       */         
/* 11222 */         EANEntity eANEntity = paramEntityItem.getDownLink(b);
/*       */         
/* 11224 */         if (eANEntity.getEntityType().equals("PRODSTRUCT")) {
/*       */           
/* 11226 */           EntityItem entityItem = (EntityItem)eANEntity;
/* 11227 */           EANEntity eANEntity1 = getDownLinkEntityItem(entityItem, "MODEL");
/* 11228 */           log(eANEntity.toString());
/* 11229 */           if (null != eANEntity1)
/*       */           {
/* 11231 */             if (isEntityWithMatchedAttr((EntityItem)eANEntity1, hashtable)) {
/*       */               
/* 11233 */               EntityItem entityItem1 = (EntityItem)eANEntity1;
/* 11234 */               String str = getAttributeFlagValue(entityItem1, "MACHTYPEATR");
/* 11235 */               if (null == str)
/*       */               {
/* 11237 */                 str = " ";
/*       */               }
/* 11239 */               str = str.trim();
/* 11240 */               str = setString(str, 4);
/*       */               
/* 11242 */               if (treeSet.contains(str))
/*       */               {
/* 11244 */                 vector.add(entityItem);
/*       */               }
/*       */             } 
/*       */           }
/*       */         } 
/*       */       } 
/*       */       
/* 11251 */       if (vector.size() == 0) {
/*       */         
/* 11253 */         this.featureHT.put(str2, "New");
/* 11254 */         return true;
/*       */       } 
/*       */       
/* 11257 */       for (b = 0; b < vector.size(); b++) {
/*       */         
/* 11259 */         EntityItem entityItem = vector.get(b);
/* 11260 */         String str = getAttributeValue(entityItem, "ANNDATE", "", "", false);
/* 11261 */         if (str.equals("")) {
/*       */           
/* 11263 */           EANEntity eANEntity = getDownLinkEntityItem(entityItem, "MODEL");
/* 11264 */           if (null != eANEntity) {
/*       */             
/* 11266 */             EntityItem entityItem1 = (EntityItem)eANEntity;
/* 11267 */             str = getAttributeValue(entityItem1, "ANNDATE", "", "", false);
/*       */           } 
/*       */         } 
/*       */         
/* 11271 */         if (this.annDate.compareTo(str) > 0) {
/*       */           
/* 11273 */           this.featureHT.put(str2, "Old");
/* 11274 */           return false;
/*       */         } 
/*       */       } 
/*       */       
/* 11278 */       this.featureHT.put(str2, "New");
/* 11279 */       bool = true;
/*       */     }
/* 11281 */     catch (Exception exception) {
/*       */       
/* 11283 */       exception.printStackTrace();
/* 11284 */       log(exception.toString());
/*       */     } 
/*       */     
/* 11287 */     hashtable.clear();
/* 11288 */     hashtable = null;
/* 11289 */     treeSet.clear();
/* 11290 */     treeSet = null;
/*       */     
/* 11292 */     return bool;
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
/* 11303 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*       */ 
/*       */     
/*       */     try {
/* 11307 */       Date date = simpleDateFormat.parse(paramString);
/*       */       
/* 11309 */       simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
/* 11310 */       return simpleDateFormat.format(date).toString();
/*       */     }
/* 11312 */     catch (Exception exception) {
/*       */       
/* 11314 */       return "Exception in formatDate(): " + exception;
/*       */     } 
/*       */   }
/*       */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AUTOGENRpt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */