/*      */ package COM.ibm.eannounce.abr.sg.rfc;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANEntity;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import com.ibm.pprds.epimshw.HWPIMSNotFoundInMastException;
/*      */ import com.ibm.pprds.epimshw.util.ConfigManager;
/*      */ import com.ibm.pprds.epimshw.util.DateUtility;
/*      */ import com.ibm.rdh.chw.entity.AUOMaterial;
/*      */ import com.ibm.rdh.chw.entity.CHWAnnouncement;
/*      */ import com.ibm.rdh.chw.entity.CHWGeoAnn;
/*      */ import com.ibm.rdh.chw.entity.CntryTax;
/*      */ import com.ibm.rdh.chw.entity.DepData;
/*      */ import com.ibm.rdh.chw.entity.LifecycleAnnounceData;
/*      */ import com.ibm.rdh.chw.entity.LifecycleData;
/*      */ import com.ibm.rdh.chw.entity.PlannedSalesStatus;
/*      */ import com.ibm.rdh.chw.entity.RevData;
/*      */ import com.ibm.rdh.chw.entity.RevProfile;
/*      */ import com.ibm.rdh.chw.entity.TypeFeature;
/*      */ import com.ibm.rdh.chw.entity.TypeModel;
/*      */ import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
/*      */ import com.ibm.rdh.rfc.proxy.RdhRestProxy;
/*      */ import com.ibm.rdh.rfc.proxy.RetriableRfcProxy;
/*      */ import com.ibm.rdh.rfc.proxy.RfcProxy;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.FileLock;
/*      */ import java.nio.channels.OverlappingFileLockException;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class RfcAbrAdapter
/*      */   implements RfcAbr
/*      */ {
/*      */   protected static final String SPLIT = "-";
/*      */   protected static final String MODEL = "MODEL";
/*      */   protected static final String FEATURE = "FEATURE";
/*      */   protected static final String SWFEATURE = "SWFEATURE";
/*      */   protected static final String TMF = "PRODSTRUCT";
/*      */   protected static final String FCTRANSACTION = "FCTRANSACTION";
/*      */   protected static final String MODELCONVERT = "MODELCONVERT";
/*      */   protected static final String AVAIL = "AVAIL";
/*      */   protected static final String ANNOUNCEMENT = "ANNOUNCEMENT";
/*      */   protected static final String MACHTYPE = "MACHTYPE";
/*      */   protected static final String GENERALAREA = "GENERALAREA";
/*      */   protected static final String GBT = "GBT";
/*      */   protected static final String TAXCATG = "TAXCATG";
/*      */   protected static final String MODTAXRELEVANCE = "MODTAXRELEVANCE";
/*      */   protected static final String SGMNTACRNYM = "SGMNTACRNYM";
/*      */   protected static final String REVPROF = "REVPROF";
/*      */   protected static final String AUOMTRL = "AUOMTRL";
/*      */   protected static final String REVPROFAUOMTRL = "REVPROFAUOMTRL";
/*      */   protected static final String MACHTYPE_PROMOTED = "PROMOTED";
/*      */   protected static final String MACHTYPE_MACHTYPEATR = "MACHTYPEATR";
/*      */   protected static final String MACHTYPE_RPQRANGE = "RPQRANGE";
/*      */   protected static final String MACHTYPE_RANGENAME = "RANGENAME";
/*      */   protected static final String MACHTYPE_FEATURECOUNT = "FEATURECOUNT";
/*      */   protected static final String MODEL_MACHTYPEATR = "MACHTYPEATR";
/*      */   protected static final String MODEL_COFCAT = "COFCAT";
/*      */   protected static final String MODEL_MODELATR = "MODELATR";
/*      */   protected static final String MODEL_MKTGNAME = "MKTGNAME";
/*      */   protected static final String MODEL_INVNAME = "INVNAME";
/*      */   protected static final String MODEL_ACCTASGNGRP = "ACCTASGNGRP";
/*      */   protected static final String MODEL_PRFTCTR = "PRFTCTR";
/*      */   protected static final String MODEL_INSTALL = "INSTALL";
/*      */   protected static final String MODEL_LICNSINTERCD = "LICNSINTERCD";
/*      */   protected static final String MODEL_SYSTEMTYPE = "SYSTEMTYPE";
/*      */   protected static final String MODEL_SYSIDUNIT = "SYSIDUNIT";
/*      */   protected static final String MODEL_MODELORDERCODE = "MODELORDERCODE";
/*      */   protected static final String MODEL_ANNDATE = "ANNDATE";
/*      */   protected static final String FEATURE_FEATURECODE = "FEATURECODE";
/*      */   protected static final String FEATURE_INVNAME = "INVNAME";
/*      */   protected static final String FEATURE_HWFCCAT = "HWFCCAT";
/*      */   protected static final String FEATURE_PRICEDFEATURE = "PRICEDFEATURE";
/*      */   protected static final String FEATURE_ZEROPRICE = "ZEROPRICE";
/*      */   protected static final String FEATURE_FCTYPE = "FCTYPE";
/*      */   protected static final String FIRSTANNDATE = "FIRSTANNDATE";
/*      */   protected static final String FEATURE_TYPERANGE = "TYPERANGE";
/*      */   protected static final String TMF_INSTALL = "INSTALL";
/*      */   protected static final String TMF_REMOVEPRICE = "REMOVEPRICE";
/*      */   protected static final String TMF_RETURNEDPARTS = "RETURNEDPARTS";
/*      */   protected static final String TMF_ANNDATE = "ANNDATE";
/*      */   protected static final String TMF_WITHDRAWDATE = "WITHDRAWDATE";
/*      */   protected static final String MODELCONVERT_FROMMACHTYPE = "FROMMACHTYPE";
/*      */   protected static final String MODELCONVERT_FROMMODEL = "FROMMODEL";
/*      */   protected static final String MODELCONVERT_TOMACHTYPE = "TOMACHTYPE";
/*      */   protected static final String MODELCONVERT_TOMODEL = "TOMODEL";
/*      */   protected static final String AVAIL_COUNTRYLIST = "COUNTRYLIST";
/*      */   protected static final String AVAIL_AVAILTYPE = "AVAILTYPE";
/*      */   protected static final String AVAIL_EFFECTIVEDATE = "EFFECTIVEDATE";
/*      */   protected static final String FCTRANSACTION_WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";
/*      */   protected static final String ANNOUNCEMENT_ANNTYPE = "ANNTYPE";
/*      */   protected static final String ANNDATE = "ANNDATE";
/*      */   protected static final String PDHDOMAIN = "PDHDOMAIN";
/*      */   protected static final String TYPERANGEL = "TYPERANGEL";
/*      */   protected static final String GENERALAREA_SLEORG = "SLEORG";
/*      */   protected static final String GENERALAREA_GENAREACODE = "GENAREACODE";
/*      */   protected static final String GENERALAREA_GENAREANAME = "GENAREANAME";
/*      */   protected static final String GENERALAREA_GENAREAPARENT = "GENAREAPARENT";
/*      */   protected static final String GENERALAREA_CBSLEGACYPLNTCD = "CBSLEGACYPLNTCD";
/*      */   protected static final String GENERALAREA_CBSRETURNPNTCD = "CBSRETURNPNTCD";
/*      */   protected static final String GBT_SAPPRIMBRANDCD = "SAPPRIMBRANDCD";
/*      */   protected static final String GBT_SAPPRODFMLYCD = "SAPPRODFMLYCD";
/*      */   protected static final String TAXCATG_COUNTRYLIST = "COUNTRYLIST";
/*      */   protected static final String TAXCATG_TAXCATGATR = "TAXCATGATR";
/*      */   protected static final String MODTAXRELEVANCE_TAXCLS = "TAXCLS";
/*      */   protected static final String SGMNTACRNYM_DIV = "DIV";
/*      */   protected static final String ATTR_SYSFEEDRESEND = "SYSFEEDRESEND";
/*      */   protected static final String ATTR_STATUS = "STATUS";
/*      */   protected static final String WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";
/*      */   protected static final String ANNTYPE_lONG_NEW = "New";
/*      */   protected static final String MACHTYPE_PROMOTED_YES = "PRYES";
/*      */   protected static final String MACHTYPE_RPQRANGE_YES = "Yes";
/*      */   protected static final String PLANNEDAVAIL = "146";
/*      */   protected static final String LASTORDERAVAIL = "149";
/*      */   protected static final String EOMAVAIL = "200";
/*      */   protected static final String STATUS_PASSED = "0030";
/*      */   protected static final String HARDWARE = "100";
/*      */   protected static final String SYSFEEDRESEND_YES = "Yes";
/*      */   protected static final String SYSFEEDRESEND_NO = "No";
/*      */   protected static final String STATUS_FINAL = "0020";
/*      */   protected static final String GENAREANAME_US = "1652";
/*      */   protected static final String GENAREANAME_ZA = "1624";
/*      */   protected static final String NETPRICEMES_YES = "NETPRICEMESYES";
/*      */   protected static final String ATTR_FLAG = "F";
/*      */   protected static final String ATTR_TEXT = "T";
/*      */   protected static final String ATTR_MULTI_FLAG = "MF";
/*      */   protected static final String ATTR_MULTI_TEXT = "MT";
/*      */   public static final String LOCAL_REAL_PATH = "./properties/dev";
/*      */   public static final String STRING_SEPARATOR = "-";
/*      */   public static final String ROLE_CODE = "BHFEED";
/*      */   protected static final String RFCABRSTATUS = "RFCABRSTATUS";
/*  288 */   public String uniqueID = null;
/*  289 */   protected String m_strEpoch = "1980-01-01-00.00.00.000000";
/*      */   
/*  291 */   public static String PREANNOUNCE = "YA";
/*  292 */   public static String ANNOUNCE = "Z0";
/*  293 */   public static String WDFM = "ZJ";
/*      */ 
/*      */   
/*      */   private static final String PRIMARY_FC = "100";
/*      */   
/*      */   private static final String SECONDARY_FC = "110";
/*      */   
/*      */   protected static final String FCTYPE_RPQ_ILISTED = "120";
/*      */   
/*  302 */   protected static final Set FCTYPE_SET = new HashSet(); static {
/*  303 */     FCTYPE_SET.add("100");
/*  304 */     FCTYPE_SET.add("110");
/*      */   }
/*      */   
/*  307 */   private static Hashtable<String, List<String>> requiredTypeAttrsTbl = new Hashtable<>(); protected RfcProxy rdhRestProxy;
/*      */   static {
/*  309 */     requiredTypeAttrsTbl.put("MODEL", new ArrayList<>(
/*  310 */           Arrays.asList(new String[] { "MACHTYPEATR", "MKTGNAME", "BHPRODHIERCD", "INVNAME", "ANNDATE" })));
/*  311 */     requiredTypeAttrsTbl.put("MODELCONVERT", new ArrayList<>(Arrays.asList(new String[] { "ANNDATE" })));
/*  312 */     requiredTypeAttrsTbl.put("ANNOUNCEMENT", new ArrayList<>(Arrays.asList(new String[] { "ANNNUMBER", "ANNTYPE", "ANNDATE" })));
/*  313 */     requiredTypeAttrsTbl.put("AUOMTRL", new ArrayList<>(Arrays.asList(new String[] { "MATERIAL", "AUODESC" })));
/*  314 */     requiredTypeAttrsTbl.put("REVPROFAUOMTRL", new ArrayList<>(Arrays.asList(new String[] { "PERCENTAGE" })));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected RFCABRSTATUS abr;
/*      */   
/*      */   protected EntityList entityList;
/*      */   
/*      */   protected ConfigManager configManager;
/*      */ 
/*      */   
/*      */   public RfcAbrAdapter(RFCABRSTATUS paramRFCABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/*  327 */     this.configManager = ConfigManager.getConfigManager();
/*  328 */     this.configManager.put("system.realPath", "./properties/dev");
/*  329 */     this.configManager.addAllConfigFiles();
/*      */     
/*  331 */     this.abr = paramRFCABRSTATUS;
/*  332 */     BasicRfcLogger basicRfcLogger = new BasicRfcLogger(paramRFCABRSTATUS);
/*  333 */     this.rdhRestProxy = (new RetriableRfcProxy(new RdhRestProxy(basicRfcLogger), basicRfcLogger)).getRfcProxy();
/*      */     
/*  335 */     String str = this.abr.getCurrentTime();
/*  336 */     Profile profile = this.abr.switchRole("BHFEED");
/*  337 */     profile.setValOnEffOn(str, str);
/*  338 */     profile.setEndOfDay(str);
/*  339 */     profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*  340 */     profile.setLoginTime(str);
/*  341 */     this.entityList = getEntityList(this.abr.getDatabase(), profile, getVeName(), this.abr.getEntityType(), this.abr.getEntityID());
/*  342 */     this.abr.addDebug("EntityList for " + this.abr.getProfile().getValOn() + " extract " + getVeName() + " contains the following entities: \n" + 
/*  343 */         PokUtils.outputList(this.entityList));
/*      */   }
/*      */   
/*      */   public String getVeName() {
/*  347 */     return "dummy";
/*      */   }
/*      */ 
/*      */   
/*      */   protected EntityList getEntityList(Database paramDatabase, Profile paramProfile, String paramString1, String paramString2, int paramInt) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  352 */     return paramDatabase.getEntityList(paramProfile, new ExtractActionItem(null, paramDatabase, paramProfile, paramString1), new EntityItem[] { new EntityItem(null, paramProfile, paramString2, paramInt) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityList getEntityList(Profile paramProfile) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  359 */     return getEntityList(this.abr.getDatabase(), paramProfile, getVeName(), this.abr.getEntityType(), this.abr.getEntityID());
/*      */   }
/*      */   
/*      */   protected EntityItem getRooEntityItem() {
/*  363 */     return this.entityList.getParentEntityGroup().getEntityItem(0);
/*      */   }
/*      */   
/*      */   protected EntityItem[] getEntityItems(String paramString) {
/*  367 */     EntityItem[] arrayOfEntityItem = null;
/*  368 */     EntityGroup entityGroup = this.entityList.getEntityGroup(paramString);
/*  369 */     if (entityGroup != null) {
/*  370 */       arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */     }
/*  372 */     return arrayOfEntityItem;
/*      */   }
/*      */   
/*      */   protected EntityItem[] getEntityItems(EntityList paramEntityList, String paramString) {
/*  376 */     EntityItem[] arrayOfEntityItem = null;
/*  377 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString);
/*  378 */     if (entityGroup != null) {
/*  379 */       arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */     }
/*  381 */     return arrayOfEntityItem;
/*      */   }
/*      */   
/*      */   protected List<EntityItem> getLinkedRelator(EntityItem paramEntityItem, String paramString) {
/*  385 */     ArrayList<EntityItem> arrayList = new ArrayList(); byte b;
/*  386 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*  387 */       EANEntity eANEntity = paramEntityItem.getUpLink(b);
/*  388 */       if (eANEntity.getEntityType().equals(paramString)) {
/*  389 */         arrayList.add((EntityItem)eANEntity);
/*      */       }
/*      */     } 
/*  392 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*  393 */       EANEntity eANEntity = paramEntityItem.getDownLink(b);
/*  394 */       if (eANEntity.getEntityType().equals(paramString)) {
/*  395 */         arrayList.add((EntityItem)eANEntity);
/*      */       }
/*      */     } 
/*  398 */     return arrayList;
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
/*      */   protected String getAttributeFlagValue(EntityItem paramEntityItem, String paramString) throws RfcAbrException {
/*  412 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*  413 */     List list = requiredTypeAttrsTbl.get(paramEntityItem.getEntityType());
/*  414 */     if (list != null && list.contains(paramString) && (
/*  415 */       str == null || "".equals(str))) {
/*  416 */       throw new RfcAbrException("For entity:" + paramEntityItem.getKey() + ", " + paramString + " value can not be empty");
/*      */     }
/*      */     
/*  419 */     if (str == null) {
/*  420 */       str = "";
/*      */     }
/*  422 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getAttributeShortValue(EntityItem paramEntityItem, String paramString) {
/*  433 */     String str = "";
/*  434 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*  435 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/*  436 */     if (eANMetaAttribute == null) {
/*  437 */       str = "Error: Attribute " + paramString + " not found in " + paramEntityItem.getEntityType() + " META data.";
/*      */     }
/*  439 */     else if (eANMetaAttribute.getAttributeType().equals("U") || eANMetaAttribute.getAttributeType().equals("S")) {
/*  440 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/*  441 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/*  443 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  444 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  446 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  447 */             str = arrayOfMetaFlag[b].getShortDescription();
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  452 */     } else if (eANMetaAttribute.getAttributeType().equals("F")) {
/*      */       
/*  454 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/*  455 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*  456 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/*  458 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  459 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  461 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  462 */             if (stringBuffer.length() > 0) {
/*  463 */               stringBuffer.append("|");
/*      */             }
/*  465 */             stringBuffer.append(arrayOfMetaFlag[b].getShortDescription());
/*      */           } 
/*      */         } 
/*  468 */         if (stringBuffer.length() > 0) {
/*  469 */           str = stringBuffer.toString();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  474 */     return str;
/*      */   }
/*      */   
/*      */   protected String getAttributeValue(EntityItem paramEntityItem, String paramString) throws RfcAbrException {
/*  478 */     String str = PokUtils.getAttributeValue(paramEntityItem, paramString, "|", "", false);
/*  479 */     List list = requiredTypeAttrsTbl.get(paramEntityItem.getEntityType());
/*  480 */     if (list != null && list.contains(paramString) && (
/*  481 */       str == null || "".equals(str))) {
/*  482 */       throw new RfcAbrException("For entity:" + paramEntityItem.getKey() + ", " + paramString + " value can not be empty");
/*      */     }
/*      */     
/*  485 */     return str;
/*      */   }
/*      */   
/*      */   protected Vector getAttributeMultiValue(EntityItem paramEntityItem, String paramString) throws RfcAbrException {
/*  489 */     Vector<String> vector = new Vector();
/*  490 */     String str = getAttributeValue(paramEntityItem, paramString);
/*  491 */     if (str != null) {
/*  492 */       StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  493 */       while (stringTokenizer.hasMoreTokens()) {
/*  494 */         vector.add(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*  497 */     return vector;
/*      */   }
/*      */   
/*      */   protected Vector getAttributeMultiFlagValue(EntityItem paramEntityItem, String paramString) throws RfcAbrException {
/*  501 */     Vector<String> vector = new Vector();
/*  502 */     String str = getAttributeFlagValue(paramEntityItem, paramString);
/*  503 */     if (str != null) {
/*  504 */       StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  505 */       while (stringTokenizer.hasMoreTokens()) {
/*  506 */         vector.add(stringTokenizer.nextToken());
/*      */       }
/*      */     } 
/*  509 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setFlagValue(String paramString1, String paramString2, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  514 */     this.abr.setFlagValue(paramString1, paramString2, paramEntityItem);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setTextValue(String paramString1, String paramString2, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  519 */     this.abr.setTextValue(paramString1, paramString2, paramEntityItem);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setLongTextValue(String paramString1, String paramString2, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  524 */     this.abr.setLongTextValue(paramString1, paramString2, paramEntityItem);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AttributeChangeHistoryGroup getAttributeHistory(EntityItem paramEntityItem, String paramString) throws MiddlewareRequestException {
/*  529 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/*  530 */     if (eANAttribute != null) {
/*  531 */       return new AttributeChangeHistoryGroup(this.abr.getDatabase(), this.abr.getProfile(), eANAttribute);
/*      */     }
/*  533 */     this.abr.addDebug(paramString + " of " + paramEntityItem.getKey() + "  was null");
/*  534 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isDiff(EntityItem paramEntityItem1, EntityItem paramEntityItem2, List<String> paramList) throws RfcAbrException {
/*  539 */     for (String str1 : paramList) {
/*  540 */       String str2 = getAttributeValue(paramEntityItem1, str1);
/*  541 */       String str3 = getAttributeValue(paramEntityItem2, str1);
/*  542 */       if (!str2.equals(str3)) {
/*  543 */         this.abr.addDebug("isDiff " + paramEntityItem1.getKey() + " Attribute " + str1 + " value " + str2 + " is different with " + paramEntityItem2
/*  544 */             .getKey() + " value " + str3);
/*  545 */         return true;
/*      */       } 
/*      */     } 
/*  548 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/*  555 */     if (paramAttributeChangeHistoryGroup != null) {
/*  556 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  557 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  558 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/*  559 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  563 */     return false;
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
/*      */   protected String getLatestValFromForAttributeValue(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws RfcAbrException {
/*  576 */     String str1 = null;
/*  577 */     String str2 = "";
/*  578 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/*  579 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/*  580 */       for (int j = i - 1; j >= 0; j--) {
/*  581 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/*  582 */         if (attributeChangeHistoryItem != null && 
/*  583 */           paramString.equals(attributeChangeHistoryItem.getFlagCode())) {
/*  584 */           str2 = attributeChangeHistoryItem.getChangeDate();
/*  585 */           if (str1 == null) {
/*  586 */             str1 = str2;
/*      */           }
/*  588 */           else if (str2.compareTo(str1) > 0) {
/*  589 */             str1 = str2;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  595 */       return str1;
/*      */     } 
/*  597 */     throw new RfcAbrException("Error: A request is not valid since there is no history");
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isXccOnlyDiv(String paramString) {
/*  602 */     String str = this.configManager.getString("epimshw.xccOnlyDiv", true);
/*  603 */     StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*  604 */     while (stringTokenizer.hasMoreElements()) {
/*  605 */       String str1 = stringTokenizer.nextToken();
/*  606 */       if (paramString != null && paramString.equalsIgnoreCase(str1)) {
/*  607 */         return true;
/*      */       }
/*      */     } 
/*  610 */     return false;
/*      */   }
/*      */   
/*      */   protected String getOpwgId() {
/*  614 */     return Integer.toString(this.abr.getProfile().getOPWGID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityItem getEntityItemAtT1(Vector<EntityItem> paramVector, EntityItem paramEntityItem) {
/*  625 */     for (byte b = 0; b < paramVector.size(); b++) {
/*  626 */       EntityItem entityItem = paramVector.get(b);
/*  627 */       if (entityItem.getKey().equals(paramEntityItem.getKey())) {
/*  628 */         return entityItem;
/*      */       }
/*      */     } 
/*  631 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAnnLifecyle(String paramString1, String paramString2, Date paramDate, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception {
/*  638 */     FileChannel fileChannel = null;
/*  639 */     FileLock fileLock = null;
/*      */     
/*      */     try {
/*  642 */       File file = new File("./locks/UpdateAnnLifecyle" + paramString1 + ".lock");
/*  643 */       (new File(file.getParent())).mkdirs();
/*  644 */       FileOutputStream fileOutputStream = new FileOutputStream(file);
/*  645 */       fileChannel = fileOutputStream.getChannel();
/*      */       while (true) {
/*  647 */         FileLock fileLock1 = fileChannel.tryLock();
/*  648 */         if (fileLock1 != null) {
/*  649 */           this.abr.addDebug("updateAnnLifecyle get lock");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  667 */           Date date = new Date();
/*      */           
/*  669 */           if (!paramString4.equals("MIG")) {
/*      */ 
/*      */ 
/*      */             
/*  673 */             String str = getOpwgId();
/*  674 */             LifecycleData lifecycleData = this.rdhRestProxy.r200(paramString2, paramString1, paramString3, "ann", paramString5, paramString6);
/*  675 */             this.abr.addOutput("[R200] Read lifecycle row PreAnn for sales " + paramString6);
/*      */             
/*  677 */             this.abr.addDebug("updateAnnLifecyle getPreAnnounceValidFrom:" + lifecycleData.getPreAnnounceValidFrom() + " getAnnounceValidFrom:" + lifecycleData
/*  678 */                 .getAnnounceValidFrom() + " getWdfmValidFrom:" + lifecycleData
/*  679 */                 .getWdfmValidFrom() + " getAnnounceValidTo:" + lifecycleData.getAnnounceValidTo());
/*      */             
/*  681 */             if (lifecycleData.getPreAnnounceValidFrom() == null && lifecycleData.getAnnounceValidFrom() == null && lifecycleData
/*  682 */               .getWdfmValidFrom() == null) {
/*      */               
/*  684 */               if (DateUtility.isAfterToday(paramDate)) {
/*  685 */                 this.rdhRestProxy.r197(paramString2, paramString1, PREANNOUNCE, date, 
/*  686 */                     DateUtility.getDateMinusOne(paramDate), str, paramString3, "ann", paramString5, paramString6);
/*      */                 
/*  688 */                 this.abr.addOutput("[R197] Create lifecycle row for PreAnn status valid from today to AnnDate - 1");
/*      */                 
/*  690 */                 this.rdhRestProxy.r197(paramString2, paramString1, ANNOUNCE, paramDate, DateUtility.getInfinityDate(), str, paramString3, "ann", paramString5, paramString6);
/*      */                 
/*  692 */                 this.abr.addOutput("[R197] Create lifecycle row for Announced status valid from today to 99991231");
/*      */                 break;
/*      */               } 
/*  695 */               this.rdhRestProxy.r197(paramString2, paramString1, ANNOUNCE, paramDate, DateUtility.getInfinityDate(), str, paramString3, "ann", paramString5, paramString6);
/*      */               
/*  697 */               this.abr.addOutput("[R197] Create lifecycle row for Announced status valid from today to 99991231");
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */ 
/*      */             
/*  704 */             if (lifecycleData.getPreAnnounceValidFrom() != null && (lifecycleData.getAnnounceValidFrom() == null || 
/*  705 */               !DateUtility.isSameDay(paramDate, lifecycleData.getAnnounceValidFrom()))) {
/*  706 */               if (DateUtility.dateIsTodayOrBefore(paramDate, lifecycleData.getPreAnnounceValidFrom())) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  711 */                 this.rdhRestProxy.r199(paramString2, paramString1, PREANNOUNCE, lifecycleData.getPreAnnounceValidTo(), str, paramString3, "ann", paramString5, paramString6);
/*      */                 
/*  713 */                 this.abr.addOutput("[R199] Delete lifecycle row for PreAnn status");
/*      */               } else {
/*      */                 
/*  716 */                 this.rdhRestProxy.r199(paramString2, paramString1, PREANNOUNCE, lifecycleData.getPreAnnounceValidTo(), str, paramString3, "ann", paramString5, paramString6);
/*      */                 
/*  718 */                 this.abr.addOutput("[R199] Delete lifecycle row for PreAnn status");
/*  719 */                 this.rdhRestProxy.r197(paramString2, paramString1, PREANNOUNCE, lifecycleData.getPreAnnounceValidFrom(), 
/*  720 */                     DateUtility.getDateMinusOne(paramDate), str, paramString3, "ann", paramString5, paramString6);
/*      */                 
/*  722 */                 this.abr.addOutput("[R197] Create lifecycle row for PreAnn status valid from prior date to AnnDate - 1");
/*      */               } 
/*      */             }
/*      */             
/*  726 */             this.abr.addDebug("updateAnnLifecyle annDate " + paramDate + " annValudFrom" + lifecycleData
/*  727 */                 .getAnnounceValidFrom() + "DateUtility.isSameDay(lcd.getAnnounceValidFrom(), annDate)" + 
/*      */                 
/*  729 */                 DateUtility.isSameDay(lifecycleData.getAnnounceValidFrom(), paramDate));
/*  730 */             if (!DateUtility.isSameDay(lifecycleData.getAnnounceValidFrom(), paramDate)) {
/*      */ 
/*      */               
/*  733 */               Date date1 = null;
/*  734 */               if (lifecycleData.getAnnounceValidTo() == null) {
/*  735 */                 if (lifecycleData.getWdfmValidFrom() != null) {
/*  736 */                   date1 = DateUtility.getDateMinusOne(lifecycleData.getWdfmValidFrom());
/*      */                 } else {
/*  738 */                   date1 = DateUtility.getInfinityDate();
/*      */                 } 
/*      */                 
/*  741 */                 if (lifecycleData.getWdfmValidFrom() != null && !lifecycleData.getWdfmValidFrom().equals(paramDate)) {
/*  742 */                   this.rdhRestProxy.r197(paramString2, paramString1, ANNOUNCE, paramDate, date1, str, paramString3, "ann", paramString5, paramString6);
/*  743 */                   this.abr.addOutput("[R197] Create lifecycle row for Announced status valid from date to AnnDate");
/*      */                 } 
/*      */               } else {
/*      */                 
/*  747 */                 this.rdhRestProxy.r198(paramString2, paramString1, ANNOUNCE, paramDate, lifecycleData.getAnnounceValidTo(), str, paramString3, "ann", paramString5, paramString6);
/*      */                 
/*  749 */                 this.abr.addOutput("[R198] Update lifecycle row for Announced status valid from date to AnnDate");
/*      */               } 
/*      */               
/*  752 */               if (DateUtility.isAfterToday(paramDate) && lifecycleData.getPreAnnounceValidFrom() == null) {
/*  753 */                 this.rdhRestProxy.r197(paramString2, paramString1, PREANNOUNCE, date, 
/*  754 */                     DateUtility.getDateMinusOne(paramDate), str, paramString3, "ann", paramString5, paramString6);
/*      */                 
/*  756 */                 this.abr.addOutput("[R197] Create lifecycle row for PreAnn status valid from today to AnnDate - 1");
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/*  766 */         this.abr.addDebug("updateAnnLifecyle fileLock == null");
/*  767 */         Thread.sleep(5000L);
/*      */       }
/*      */     
/*      */     }
/*  771 */     catch (OverlappingFileLockException overlappingFileLockException) {
/*  772 */       this.abr.addDebug("updateAnnLifecyle other ABR is running updateAnnLifecyle");
/*  773 */       Thread.sleep(5000L);
/*  774 */     } catch (FileNotFoundException fileNotFoundException) {
/*  775 */       this.abr.addDebug("updateAnnLifecyle file not found!");
/*  776 */     } catch (IOException iOException) {
/*  777 */       this.abr.addDebug("updateAnnLifecyle IOException");
/*  778 */     } catch (InterruptedException interruptedException) {
/*  779 */       this.abr.addDebug("updateAnnLifecyle IOException");
/*      */     } finally {
/*  781 */       if (fileLock != null) {
/*      */         try {
/*  783 */           fileLock.release();
/*  784 */           fileLock = null;
/*  785 */         } catch (IOException iOException) {
/*  786 */           this.abr.addDebug("file lock release IOException");
/*      */         } 
/*      */       }
/*  789 */       if (fileChannel != null) {
/*      */         try {
/*  791 */           fileChannel.close();
/*  792 */           fileChannel = null;
/*  793 */         } catch (IOException iOException) {
/*  794 */           this.abr.addDebug("file channel release IOException");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void callr130WithAccessAuthority(String paramString1, String paramString2, CHWAnnouncement paramCHWAnnouncement, String paramString3) throws Exception {
/*  802 */     callr130WithAccessAuthority(paramString1, null, paramString2, paramCHWAnnouncement, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void callr130WithAccessAuthority(String paramString1, String paramString2, String paramString3, CHWAnnouncement paramCHWAnnouncement, String paramString4) throws Exception {
/*  808 */     FileChannel fileChannel = null;
/*  809 */     FileLock fileLock = null;
/*      */     try {
/*  811 */       File file = new File("./locks/R130" + paramString1 + paramString3 + ".lock");
/*  812 */       (new File(file.getParent())).mkdirs();
/*  813 */       FileOutputStream fileOutputStream = new FileOutputStream(file);
/*  814 */       fileChannel = fileOutputStream.getChannel();
/*      */       while (true) {
/*  816 */         FileLock fileLock1 = fileChannel.tryLock();
/*  817 */         if (fileLock1 != null) {
/*  818 */           this.abr.addDebug("call SAP_Class_Maintain get lock");
/*  819 */           this.rdhRestProxy.r130(paramString1, paramString2, paramString3, paramCHWAnnouncement, paramString4);
/*      */           break;
/*      */         } 
/*  822 */         this.abr.addDebug("R130" + paramString1 + paramString3 + " fileLock == null");
/*  823 */         Thread.sleep(5000L);
/*      */       }
/*      */     
/*  826 */     } catch (OverlappingFileLockException overlappingFileLockException) {
/*  827 */       this.abr.addDebug("SAP_Class_Maintain other ABR is running callr130WithAccessAuthority");
/*  828 */       Thread.sleep(5000L);
/*  829 */     } catch (FileNotFoundException fileNotFoundException) {
/*  830 */       this.abr.addDebug("SAP_Class_Maintain lock file not found!");
/*  831 */     } catch (IOException iOException) {
/*  832 */       this.abr.addDebug("SAP_Class_Maintain IOException");
/*  833 */     } catch (InterruptedException interruptedException) {
/*  834 */       this.abr.addDebug("SAP_Class_Maintain InterruptedException");
/*      */     } finally {
/*  836 */       if (fileLock != null) {
/*      */         try {
/*  838 */           fileLock.release();
/*  839 */           fileLock = null;
/*  840 */         } catch (IOException iOException) {
/*  841 */           this.abr.addDebug("file lock release IOException");
/*      */         } 
/*      */       }
/*  844 */       if (fileChannel != null) {
/*      */         try {
/*  846 */           fileChannel.close();
/*  847 */           fileChannel = null;
/*  848 */         } catch (IOException iOException) {
/*  849 */           this.abr.addDebug("file channel release IOException");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void callr127WithAccessAuthority(TypeFeature paramTypeFeature, String paramString1, CHWAnnouncement paramCHWAnnouncement, String paramString2) throws Exception {
/*  858 */     FileChannel fileChannel = null;
/*  859 */     FileLock fileLock = null;
/*      */     try {
/*  861 */       File file = new File("./locks/R127" + paramTypeFeature.getType() + paramString1 + ".lock");
/*  862 */       (new File(file.getParent())).mkdirs();
/*  863 */       FileOutputStream fileOutputStream = new FileOutputStream(file);
/*  864 */       fileChannel = fileOutputStream.getChannel();
/*      */       while (true) {
/*  866 */         FileLock fileLock1 = fileChannel.tryLock();
/*  867 */         if (fileLock1 != null) {
/*  868 */           this.abr.addDebug("call SAP_Class_Maintain get lock");
/*  869 */           this.rdhRestProxy.r127(paramTypeFeature, paramString1, paramCHWAnnouncement, paramString2);
/*      */           break;
/*      */         } 
/*  872 */         this.abr.addDebug("R127" + paramTypeFeature.getType() + paramString1 + " fileLock == null");
/*  873 */         Thread.sleep(5000L);
/*      */       }
/*      */     
/*  876 */     } catch (OverlappingFileLockException overlappingFileLockException) {
/*  877 */       this.abr.addDebug("SAP_Class_Maintain other ABR is running callr127WithAccessAuthority");
/*  878 */       Thread.sleep(5000L);
/*  879 */     } catch (FileNotFoundException fileNotFoundException) {
/*  880 */       this.abr.addDebug("SAP_Class_Maintain lock file not found!");
/*  881 */     } catch (IOException iOException) {
/*  882 */       this.abr.addDebug("SAP_Class_Maintain lock IOException");
/*  883 */     } catch (InterruptedException interruptedException) {
/*  884 */       this.abr.addDebug("SAP_Class_Maintain InterruptedException");
/*      */     } finally {
/*  886 */       if (fileLock != null) {
/*      */         try {
/*  888 */           fileLock.release();
/*  889 */           fileLock = null;
/*  890 */         } catch (IOException iOException) {
/*  891 */           this.abr.addDebug("file lock release IOException");
/*      */         } 
/*      */       }
/*  894 */       if (fileChannel != null) {
/*      */         try {
/*  896 */           fileChannel.close();
/*  897 */           fileChannel = null;
/*  898 */         } catch (IOException iOException) {
/*  899 */           this.abr.addDebug("file channel release IOException");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void callr131WithAccessAuthority(String paramString1, String paramString2, CHWAnnouncement paramCHWAnnouncement, String paramString3) throws Exception {
/*  908 */     FileChannel fileChannel = null;
/*  909 */     FileLock fileLock = null;
/*      */     try {
/*  911 */       File file = new File("./locks/R131" + paramString1 + paramString2 + ".lock");
/*  912 */       (new File(file.getParent())).mkdirs();
/*  913 */       FileOutputStream fileOutputStream = new FileOutputStream(file);
/*  914 */       fileChannel = fileOutputStream.getChannel();
/*      */       while (true) {
/*  916 */         FileLock fileLock1 = fileChannel.tryLock();
/*  917 */         if (fileLock1 != null) {
/*  918 */           this.abr.addDebug("Call SAP_Class_Maintain get lock");
/*  919 */           this.rdhRestProxy.r131(paramString1, paramString2, paramCHWAnnouncement, paramString3);
/*      */           break;
/*      */         } 
/*  922 */         this.abr.addDebug("R131" + paramString1 + paramString2 + " fileLock == null");
/*  923 */         Thread.sleep(5000L);
/*      */       }
/*      */     
/*  926 */     } catch (OverlappingFileLockException overlappingFileLockException) {
/*  927 */       this.abr.addDebug("Call SAP_Class_Maintain other ABR is calling callr131WithAccessAuthority");
/*  928 */       Thread.sleep(5000L);
/*  929 */     } catch (FileNotFoundException fileNotFoundException) {
/*  930 */       this.abr.addDebug("Call SAP_Class_Maintain lock_file not found!");
/*  931 */     } catch (IOException iOException) {
/*  932 */       this.abr.addDebug("Call SAP_Class_Maintain lock IOException");
/*  933 */     } catch (InterruptedException interruptedException) {
/*  934 */       this.abr.addDebug("Call SAP_Class_Maintain lock InterruptedException");
/*      */     } finally {
/*  936 */       if (fileLock != null) {
/*      */         try {
/*  938 */           fileLock.release();
/*  939 */           fileLock = null;
/*  940 */         } catch (IOException iOException) {
/*  941 */           this.abr.addDebug("Call SAP_Class_Maintain file lock release IOException");
/*      */         } 
/*      */       }
/*  944 */       if (fileChannel != null) {
/*      */         try {
/*  946 */           fileChannel.close();
/*  947 */           fileChannel = null;
/*  948 */         } catch (IOException iOException) {
/*  949 */           this.abr.addDebug("Call SAP_Class_Maintain file channel release IOException");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateWDFMLifecyle(LifecycleData paramLifecycleData, String paramString1, String paramString2, Date paramDate, String paramString3, String paramString4, String paramString5) throws Exception {
/*  957 */     if (paramDate == null && paramLifecycleData.getWdfmValidFrom() != null) {
/*      */       
/*  959 */       this.rdhRestProxy.r199(paramString2, paramString1, WDFM, paramLifecycleData.getWdfmValidTo(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */       
/*  961 */       this.abr.addOutput("[R199] Delete lifecycle row for WDFM");
/*  962 */       if (paramLifecycleData.getAnnounceValidFrom() == null) {
/*  963 */         this.rdhRestProxy.r197(paramString2, paramString1, ANNOUNCE, paramLifecycleData.getWdfmValidFrom(), DateUtility.getInfinityDate(), 
/*  964 */             getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*  965 */         this.abr.addOutput("[R197] Create lifecycle row for Announced valid from WDFM valid from (before delete) to 99991231");
/*      */       } 
/*      */ 
/*      */       
/*  969 */       if (paramLifecycleData.getAnnounceValidFrom() != null) {
/*      */ 
/*      */ 
/*      */         
/*  973 */         deleteAnnouncedDate(paramLifecycleData, paramString1, paramString2, paramString3, paramString4, paramString5);
/*  974 */         this.rdhRestProxy.r197(paramString2, paramString1, ANNOUNCE, paramLifecycleData.getAnnounceValidFrom(), 
/*  975 */             DateUtility.getInfinityDate(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*  976 */         this.abr.addOutput("[R197] Create lifecycle row for Announced valid from prior date to 99991231");
/*      */       } 
/*  978 */     } else if (paramDate != null && paramLifecycleData.getWdfmValidFrom() == null) {
/*  979 */       if (paramLifecycleData.getAnnounceValidFrom() != null && paramDate.equals(paramLifecycleData.getAnnounceValidFrom())) {
/*      */ 
/*      */ 
/*      */         
/*  983 */         deleteAnnouncedDate(paramLifecycleData, paramString1, paramString2, paramString3, paramString4, paramString5);
/*  984 */         this.rdhRestProxy.r197(paramString2, paramString1, WDFM, paramDate, DateUtility.getInfinityDate(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */         
/*  986 */         this.abr.addOutput("[R197] Create lifecycle row for WDFM valid from WDFMDate to 99991231");
/*      */       } else {
/*  988 */         if (paramLifecycleData.getAnnounceValidFrom() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  993 */           deleteAnnouncedDate(paramLifecycleData, paramString1, paramString2, paramString3, paramString4, paramString5);
/*  994 */           this.rdhRestProxy.r197(paramString2, paramString1, ANNOUNCE, paramLifecycleData.getAnnounceValidFrom(), 
/*  995 */               DateUtility.getDateMinusOne(paramDate), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */           
/*  997 */           this.abr.addOutput("[R197] Create lifecycle row for Announced valid from prior date to WDFMDate - 1");
/*      */         } 
/*      */         
/* 1000 */         this.rdhRestProxy.r197(paramString2, paramString1, WDFM, paramDate, DateUtility.getInfinityDate(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */         
/* 1002 */         this.abr.addOutput("[R197] Create lifecycle row for WDFM valid from WDFMDate to 99991231");
/*      */       } 
/* 1004 */     } else if (paramDate != null && paramLifecycleData.getWdfmValidFrom() != null && 
/* 1005 */       !paramDate.equals(paramLifecycleData.getWdfmValidFrom())) {
/*      */       
/* 1007 */       if (paramLifecycleData.getAnnounceValidFrom() == null) {
/* 1008 */         if (DateUtility.dateIsAfter(paramDate, paramLifecycleData.getWdfmValidFrom())) {
/* 1009 */           this.rdhRestProxy.r197(paramString2, paramString1, ANNOUNCE, paramLifecycleData.getWdfmValidFrom(), 
/* 1010 */               DateUtility.getDateMinusOne(paramDate), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */           
/* 1012 */           this.abr.addOutput("[R197] Create lifecycle row for Announce from WDFM valid from (before change) to WDFMDate - 1");
/*      */         } 
/*      */         
/* 1015 */         this.rdhRestProxy.r198(paramString2, paramString1, WDFM, paramDate, DateUtility.getInfinityDate(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */         
/* 1017 */         this.abr.addOutput("[R198] Update lifecycle row for WDFM status valid from WDFMDate to 99991231");
/* 1018 */         if (paramLifecycleData.getPreAnnounceValidFrom() != null) {
/*      */           
/* 1020 */           this.rdhRestProxy.r199(paramString2, paramString1, PREANNOUNCE, paramLifecycleData.getPreAnnounceValidTo(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */           
/* 1022 */           this.abr.addOutput("[R199] Delete lifecycle row for Preannounced");
/* 1023 */           if (DateUtility.dateIsBefore(paramLifecycleData.getPreAnnounceValidFrom(), paramDate)) {
/* 1024 */             this.rdhRestProxy.r197(paramString2, paramString1, PREANNOUNCE, paramLifecycleData.getPreAnnounceValidFrom(), 
/* 1025 */                 DateUtility.getDateMinusOne(paramLifecycleData.getWdfmValidFrom()), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */             
/* 1027 */             this.abr.addOutput("[R197] Create lifecycle row for Preannounced valid from current Preannounced valid from to WDFMDate - 1");
/*      */           }
/*      */         
/*      */         } 
/* 1031 */       } else if (paramLifecycleData.getAnnounceValidFrom() != null && paramDate
/* 1032 */         .equals(paramLifecycleData.getAnnounceValidFrom())) {
/*      */ 
/*      */ 
/*      */         
/* 1036 */         deleteAnnouncedDate(paramLifecycleData, paramString1, paramString2, paramString3, paramString4, paramString5);
/* 1037 */         this.rdhRestProxy.r198(paramString2, paramString1, WDFM, paramDate, DateUtility.getInfinityDate(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */         
/* 1039 */         this.abr.addOutput("[R198] Update lifecycle row for WDFM status valid from WDFMDate to 99991231");
/*      */       } else {
/* 1041 */         if (paramLifecycleData.getAnnounceValidFrom() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1046 */           deleteAnnouncedDate(paramLifecycleData, paramString1, paramString2, paramString3, paramString4, paramString5);
/* 1047 */           this.rdhRestProxy.r197(paramString2, paramString1, ANNOUNCE, paramLifecycleData.getAnnounceValidFrom(), 
/* 1048 */               DateUtility.getDateMinusOne(paramDate), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */           
/* 1050 */           this.abr.addOutput("[R197] Create lifecycle row for Announced valid from prior date to WDFMDate");
/*      */         } 
/*      */         
/* 1053 */         this.rdhRestProxy.r198(paramString2, paramString1, WDFM, paramDate, DateUtility.getInfinityDate(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */         
/* 1055 */         this.abr.addOutput("[R198] Update lifecycle row for WDFM status valid from WDFMDate to 99991231");
/*      */       } 
/*      */     } 
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
/*      */   private void deleteAnnouncedDate(LifecycleData paramLifecycleData, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception {
/* 1075 */     if (paramLifecycleData.getAnnounceDataList().size() > 1) {
/* 1076 */       for (LifecycleAnnounceData lifecycleAnnounceData : paramLifecycleData.getAnnounceDataList()) {
/* 1077 */         this.rdhRestProxy.r199(paramString2, paramString1, ANNOUNCE, lifecycleAnnounceData.getValidTo(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */         
/* 1079 */         this.abr.addOutput("Warning delete duplicate Z0(ANNOUNCE) record. [R199] Delete lifecycle row for Announced " + paramString2 + "/" + paramString1 + "/" + lifecycleAnnounceData
/* 1080 */             .getValidTo() + "/" + paramString5);
/*      */       } 
/*      */     } else {
/* 1083 */       this.rdhRestProxy.r199(paramString2, paramString1, ANNOUNCE, paramLifecycleData.getAnnounceValidTo(), getOpwgId(), paramString3, "wdfm", paramString4, paramString5);
/*      */       
/* 1085 */       this.abr.addOutput("[R199] Delete lifecycle row for Announced");
/*      */     } 
/*      */   }
/*      */   
/*      */   protected List<SalesOrgPlants> getAllSalesOrgPlant(Vector<EntityItem> paramVector) throws RfcAbrException {
/* 1090 */     ArrayList<SalesOrgPlants> arrayList = new ArrayList();
/* 1091 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1092 */       EntityItem entityItem = paramVector.get(b);
/* 1093 */       String str = getAttributeValue(entityItem, "SLEORG");
/* 1094 */       if (str != null && !"".equals(str)) {
/* 1095 */         SalesOrgPlants salesOrgPlants = new SalesOrgPlants();
/* 1096 */         salesOrgPlants.setGenAreaCode(getAttributeValue(entityItem, "GENAREACODE"));
/* 1097 */         salesOrgPlants.setGenAreaName(getAttributeFlagValue(entityItem, "GENAREANAME"));
/* 1098 */         salesOrgPlants.setGeo(getAttributeValue(entityItem, "GENAREAPARENT"));
/* 1099 */         salesOrgPlants.setSalesorg(str);
/* 1100 */         salesOrgPlants.setPlants(getAttributeMultiFlagValue(entityItem, "CBSLEGACYPLNTCD"));
/* 1101 */         salesOrgPlants.setReturnPlants(getAttributeMultiFlagValue(entityItem, "CBSRETURNPNTCD"));
/* 1102 */         arrayList.add(salesOrgPlants);
/*      */       } else {
/* 1104 */         this.abr.addDebug("getAllSalesOrgPlant SalesOrg value is null for " + entityItem.getKey());
/*      */       } 
/*      */     } 
/* 1107 */     this.abr.addDebug("getAllSalesOrgPlant GENERALAREA size: " + paramVector.size() + " SalesorgPlants size: " + arrayList
/* 1108 */         .size());
/* 1109 */     return arrayList;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<SalesOrgPlants> getAllSalesOrgPlantByCountryList(List<SalesOrgPlants> paramList, List<String> paramList1) throws RfcAbrException {
/* 1114 */     ArrayList<SalesOrgPlants> arrayList = new ArrayList();
/* 1115 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 1116 */       if (paramList1.contains(salesOrgPlants.getGenAreaName())) {
/* 1117 */         arrayList.add(salesOrgPlants);
/*      */       }
/*      */     } 
/* 1120 */     this.abr.addDebug("getAllSalesOrgPlantByCountryList SalesOrgPlants size: " + arrayList.size() + " for country list: " + paramList1);
/*      */     
/* 1122 */     return arrayList;
/*      */   }
/*      */   
/*      */   protected Set<String> getAllPlant(List<SalesOrgPlants> paramList) {
/* 1126 */     HashSet<String> hashSet = new HashSet();
/* 1127 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 1128 */       Vector<String> vector = salesOrgPlants.getPlants();
/* 1129 */       for (String str : vector) {
/* 1130 */         hashSet.add(str);
/*      */       }
/* 1132 */       if (vector.size() == 0) {
/* 1133 */         this.abr.addDebug("getAllPlant No plant found for country code: " + salesOrgPlants.getGenAreaCode());
/*      */       }
/*      */     } 
/* 1136 */     this.abr.addDebug("getAllPlant plants size: " + hashSet.size() + " values: " + hashSet);
/* 1137 */     return hashSet;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getSegmentAcronymForAnn(EntityItem paramEntityItem) throws RfcAbrException {
/* 1213 */     String str1 = "";
/* 1214 */     String str2 = getAttributeValue(paramEntityItem, "PDHDOMAIN");
/* 1215 */     if ("iSeries".equals(str2)) {
/* 1216 */       str1 = "AS4";
/* 1217 */     } else if ("pSeries".equals(str2)) {
/* 1218 */       str1 = "RS6";
/* 1219 */     } else if ("zSeries".equals(str2)) {
/* 1220 */       str1 = "LSC";
/*      */     } 
/* 1222 */     return str1;
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
/*      */   protected List<String> getNewCountries(Vector paramVector, EntityItem paramEntityItem) throws RfcAbrException {
/* 1234 */     List<String> list1 = getEntitiesAttributeValues(paramVector, "COUNTRYLIST", "MF");
/* 1235 */     List<String> list2 = getEntitiyAttributeValues(paramEntityItem, "COUNTRYLIST", "MF");
/* 1236 */     this.abr.addDebug("isTypeModelGeoPromoted T1 all Country size:" + list1.size() + " values: " + list1);
/* 1237 */     this.abr.addDebug("isTypeModelGeoPromoted T2 avail Country size:" + list2.size() + " values: " + list2);
/* 1238 */     list2.removeAll(list1);
/* 1239 */     this.abr.addDebug("isTypeModelGeoPromoted new county size:" + list2.size() + " values:" + list2);
/* 1240 */     return list2;
/*      */   }
/*      */   
/*      */   protected String getDiv(EntityItem paramEntityItem) throws RfcAbrException {
/* 1244 */     String str = null;
/* 1245 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELSGMTACRONYMA", "SGMNTACRNYM");
/* 1246 */     for (byte b = 0; b < vector.size(); b++) {
/* 1247 */       EntityItem entityItem = vector.get(b);
/* 1248 */       str = getAttributeFlagValue(entityItem, "DIV");
/* 1249 */       if (str != null && !"".equals(str)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1254 */     if (str == null || "".equals(str)) {
/* 1255 */       throw new RfcAbrException("Can not extract DIV value, but it is a required value for web service");
/*      */     }
/* 1257 */     return str;
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
/*      */   protected String getProdHireCd(EntityItem paramEntityItem) throws RfcAbrException {
/* 1271 */     String str = "";
/* 1272 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELGBTA", "GBT");
/* 1273 */     byte b = 0; if (b < vector.size()) {
/* 1274 */       EntityItem entityItem = vector.get(b);
/* 1275 */       str = getAttributeValue(entityItem, "SAPPRIMBRANDCD") + getAttributeValue(entityItem, "SAPPRODFMLYCD");
/*      */     } 
/*      */     
/* 1278 */     return str;
/*      */   }
/*      */   
/*      */   protected Vector getTaxListBySalesOrgPlants(SalesOrgPlants paramSalesOrgPlants) {
/* 1282 */     Vector<CntryTax> vector = new Vector();
/* 1283 */     String str1 = paramSalesOrgPlants.getSalesorg();
/* 1284 */     String str2 = paramSalesOrgPlants.getGenAreaCode();
/* 1285 */     String str3 = getTaxCatgBySalesOrgAndCountry(str1, str2);
/* 1286 */     if (str3 != null) {
/* 1287 */       CntryTax cntryTax = new CntryTax();
/* 1288 */       cntryTax.setCountry(str2);
/* 1289 */       cntryTax.setTaxCategory(str3);
/* 1290 */       String str = "US".equals(str2) ? "H" : "1";
/* 1291 */       cntryTax.setClassification(str);
/* 1292 */       vector.add(cntryTax);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1307 */     this.abr.addDebug("getTaxListBySalesOrgPlants TaxList size: " + vector.size() + " for country: " + str2 + " salesOrg: " + str1);
/*      */     
/* 1309 */     return vector;
/*      */   }
/*      */   
/*      */   protected String getTaxCatgBySalesOrgAndCountry(String paramString1, String paramString2) {
/* 1313 */     String str = "eacm.salesOrgCountry_" + paramString1 + "_" + paramString2;
/* 1314 */     this.abr.addDebug("getTaxCatgBySalesOrgAndCountry key " + str);
/* 1315 */     return this.configManager.getString(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<String> getEntitiesAttributeValues(Vector<EntityItem> paramVector, String paramString1, String paramString2) throws RfcAbrException {
/* 1323 */     ArrayList<String> arrayList = new ArrayList();
/* 1324 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1325 */       EntityItem entityItem = paramVector.get(b);
/* 1326 */       arrayList.addAll(getEntitiyAttributeValues(entityItem, paramString1, paramString2));
/*      */     } 
/* 1328 */     return arrayList;
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
/*      */   protected void updateRevenueProfileBom(String paramString1, String paramString2, Vector paramVector1, Vector paramVector2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Vector paramVector3) throws Exception {
/*      */     try {
/* 1356 */       Vector vector = this.rdhRestProxy.r193(paramString1, paramString4, paramString7);
/* 1357 */       this.abr.addOutput("[R193] Read revenue profile BOM for plant " + paramString7 + " type " + paramString4);
/* 1358 */       if (vector == null || vector.size() == 0) {
/* 1359 */         this.abr.addDebug("updateRevenueProfileBom exist in MAST but no conponent data return from r193");
/*      */         
/* 1361 */         this.rdhRestProxy.r196(paramString1, paramString2, paramVector1, paramVector2, paramString3, paramString4, paramString6, paramString7);
/*      */         
/* 1363 */         this.abr.addOutput("[R196] Update revenue profile BOM for plant " + paramString7);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1369 */         Vector vector1 = getAuoMaterailIntersection(vector, paramVector1);
/* 1370 */         this.abr.addDebug("updateRevenueProfileBom return data size: " + vector.size() + " from r193, AuoMaterials size: " + paramVector1
/* 1371 */             .size());
/* 1372 */         if (paramVector3 != null && paramVector3.size() > 0) {
/* 1373 */           Vector vector2 = getAuoMaterailIntersection(vector, paramVector3);
/*      */           
/* 1375 */           this.abr.addDebug("updateRevenueProfileBom deleted AuoMaterials size: " + paramVector3.size() + ", existInRDHDeletedInEACMAuoMaterials size: " + vector2
/*      */               
/* 1377 */               .size());
/* 1378 */           vector1.addAll(vector2);
/*      */         } 
/*      */         
/* 1381 */         this.rdhRestProxy.r195(paramString1, paramString2, vector1, paramVector2, paramString3, paramString4, paramString6, paramString7);
/*      */         
/* 1383 */         this.abr.addOutput("[R195] Delete revenue profile BOM components for plant " + paramString7);
/* 1384 */         this.rdhRestProxy.r196(paramString1, paramString2, paramVector1, paramVector2, paramString3, paramString4, paramString6, paramString7);
/*      */         
/* 1386 */         this.abr.addOutput("[R196] Update revenue profile BOM for plant " + paramString7);
/*      */       } 
/* 1388 */     } catch (HWPIMSNotFoundInMastException hWPIMSNotFoundInMastException) {
/* 1389 */       this.abr.addDebug("updateRevenueProfileBom not found in MAST table");
/*      */       
/* 1391 */       this.rdhRestProxy.r194(paramString1, paramString2, paramVector1, paramVector2, paramString3, paramString4, paramString6, paramString7);
/*      */       
/* 1393 */       this.abr.addOutput("[R194] Create revenue profile BOM for plant " + paramString7);
/*      */     } 
/*      */   }
/*      */   
/*      */   private Vector getAuoMaterailIntersection(Vector paramVector1, Vector paramVector2) {
/* 1398 */     Vector<RevData> vector = new Vector();
/* 1399 */     if (paramVector1 != null && paramVector2 != null) {
/* 1400 */       for (RevData revData1 : paramVector1) {
/* 1401 */         RevData revData2 = revData1;
/* 1402 */         for (AUOMaterial aUOMaterial1 : paramVector2) {
/* 1403 */           AUOMaterial aUOMaterial2 = aUOMaterial1;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1408 */           if (revData2.getComponent().trim().equals(aUOMaterial2.getMaterial())) {
/* 1409 */             vector.add(revData1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1415 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<String> getEntitiyAttributeValues(EntityItem paramEntityItem, String paramString1, String paramString2) throws RfcAbrException {
/* 1423 */     ArrayList<String> arrayList = new ArrayList();
/* 1424 */     if ("F".equals(paramString2)) {
/* 1425 */       String str = getAttributeFlagValue(paramEntityItem, paramString1);
/* 1426 */       arrayList.add(str);
/* 1427 */     } else if ("T".equals(paramString2)) {
/* 1428 */       String str = getAttributeValue(paramEntityItem, paramString1);
/* 1429 */       arrayList.add(str);
/* 1430 */     } else if ("MF".equals(paramString2)) {
/* 1431 */       Vector<String> vector = getAttributeMultiFlagValue(paramEntityItem, paramString1);
/* 1432 */       for (byte b = 0; b < vector.size(); b++) {
/* 1433 */         String str = vector.get(b);
/* 1434 */         arrayList.add(str);
/*      */       } 
/* 1436 */     } else if ("MT".equals(paramString2)) {
/* 1437 */       Vector<String> vector = getAttributeMultiValue(paramEntityItem, paramString1);
/* 1438 */       for (byte b = 0; b < vector.size(); b++) {
/* 1439 */         String str = vector.get(b);
/* 1440 */         arrayList.add(str);
/*      */       } 
/*      */     } else {
/* 1443 */       throw new RfcAbrException("Unknow attribute type:" + paramString2);
/*      */     } 
/* 1445 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getComponentsintypeMTCwithtypeNEW(Vector paramVector1, Vector paramVector2) {
/* 1451 */     Vector<DepData> vector = new Vector();
/* 1452 */     Vector<String> vector1 = new Vector();
/* 1453 */     Enumeration<DepData> enumeration1 = paramVector2.elements();
/* 1454 */     Enumeration<DepData> enumeration2 = paramVector1.elements();
/* 1455 */     this.abr.addDebug("Vecor Size for typeMTC" + paramVector1.size());
/* 1456 */     this.abr.addDebug("Vecor Size for typeNEW" + paramVector2.size());
/*      */     
/* 1458 */     DepData depData = new DepData();
/* 1459 */     while (enumeration1.hasMoreElements()) {
/* 1460 */       depData = enumeration1.nextElement();
/* 1461 */       String str = depData.getComponent();
/* 1462 */       this.abr.addDebug("vector typeNEW component value" + depData.getComponent());
/* 1463 */       vector1.addElement(str);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1468 */     while (enumeration2.hasMoreElements()) {
/* 1469 */       DepData depData1 = enumeration2.nextElement();
/* 1470 */       String str = depData1.getComponent();
/* 1471 */       this.abr.addDebug("vector typeMTC component value" + depData1.getComponent());
/* 1472 */       if (vector1.contains(str)) {
/* 1473 */         if (vector != null && vector.size() == 0)
/* 1474 */           vector = null;  continue;
/*      */       } 
/* 1476 */       if (vector == null) {
/* 1477 */         vector = new Vector();
/*      */       }
/* 1479 */       vector.add(depData1);
/*      */     } 
/*      */     
/* 1482 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getComponentsintypeNEW(Vector paramVector1, Vector paramVector2) {
/* 1488 */     this.abr.addDebug("Vecor Size for typeMTC" + paramVector1.size());
/* 1489 */     this.abr.addDebug("Vecor Size for typeNEW" + paramVector2.size());
/* 1490 */     Vector<DepData> vector = new Vector();
/* 1491 */     Vector<String> vector1 = new Vector();
/* 1492 */     Enumeration<DepData> enumeration1 = paramVector1.elements();
/* 1493 */     Enumeration<DepData> enumeration2 = paramVector2.elements();
/*      */     
/* 1495 */     while (enumeration1.hasMoreElements()) {
/* 1496 */       DepData depData = enumeration1.nextElement();
/* 1497 */       String str = depData.getComponent();
/* 1498 */       vector1.addElement(str);
/* 1499 */       this.abr.addDebug("vector typeMTC component value" + depData.getComponent());
/*      */     } 
/*      */     
/* 1502 */     while (enumeration2.hasMoreElements()) {
/* 1503 */       DepData depData = enumeration2.nextElement();
/* 1504 */       String str = depData.getComponent();
/* 1505 */       this.abr.addDebug("vector typeNEW component value" + depData.getComponent());
/* 1506 */       if (vector1.contains(str)) {
/* 1507 */         if (vector != null && vector.size() == 0)
/* 1508 */           vector = null; 
/*      */         continue;
/*      */       } 
/* 1511 */       if (vector == null) {
/* 1512 */         vector = new Vector();
/*      */       }
/* 1514 */       vector.add(depData);
/*      */     } 
/*      */     
/* 1517 */     return vector;
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
/*      */   protected RevProfile getRevProfile(EntityItem paramEntityItem) throws RfcAbrException {
/* 1529 */     RevProfile revProfile = new RevProfile();
/* 1530 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODREVPROFILE", "REVPROF");
/* 1531 */     this.abr.addDebug("vector size of revprofileItems=" + vector.size());
/* 1532 */     EntityItem entityItem1 = null;
/* 1533 */     EntityItem entityItem2 = null;
/* 1534 */     Vector<AUOMaterial> vector1 = new Vector();
/* 1535 */     if (vector != null && vector.size() > 0) {
/* 1536 */       entityItem1 = vector.get(0);
/* 1537 */       String str = getAttributeValue(entityItem1, "REVPROFILE");
/* 1538 */       this.abr.addDebug("revFile= " + str + "REVPROFILE=" + entityItem1.getEntityID());
/* 1539 */       revProfile.setRevenueProfile(str);
/*      */       
/* 1541 */       List<EntityItem> list = getLinkedRelator(entityItem1, "REVPROFAUOMTRL");
/* 1542 */       for (EntityItem entityItem : list) {
/* 1543 */         AUOMaterial aUOMaterial = null;
/* 1544 */         String str1 = getAttributeValue(entityItem, "PERCENTAGE");
/* 1545 */         entityItem2 = (EntityItem)entityItem.getDownLink(0);
/* 1546 */         String str2 = getAttributeValue(entityItem2, "MATERIAL");
/* 1547 */         this.abr.addDebug("MATERIAL= " + str2 + ";PERCENTAGE=" + str1);
/* 1548 */         aUOMaterial = new AUOMaterial(str2, str1);
/* 1549 */         vector1.add(aUOMaterial);
/*      */       } 
/* 1551 */       revProfile.setAuoMaterials(vector1);
/*      */     } 
/* 1553 */     return revProfile;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<EntityItem> getMACHTYPEsByType(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 1558 */     ArrayList<EntityItem> arrayList = new ArrayList();
/* 1559 */     String str1 = "SRDMACHTYPE1";
/* 1560 */     String str2 = "MACHTYPE";
/* 1561 */     StringBuffer stringBuffer = new StringBuffer();
/* 1562 */     Vector<String> vector1 = new Vector();
/* 1563 */     Vector<String> vector2 = new Vector();
/* 1564 */     vector1.add("MACHTYPEATR");
/* 1565 */     vector2.add(paramString);
/* 1566 */     this.abr.addDebug("getMachtypesByType searchAction " + str1 + " srchType " + str2 + " MACHTYPEATR " + paramString + " with role=" + this.abr
/* 1567 */         .getProfile().getRoleCode());
/* 1568 */     EntityItem[] arrayOfEntityItem = ABRUtil.doSearch(this.abr.getDatabase(), this.abr.getProfile(), str1, str2, false, vector1, vector2, stringBuffer);
/*      */     
/* 1570 */     this.abr.addDebug("getMachtypesByType " + stringBuffer.toString());
/* 1571 */     if (arrayOfEntityItem != null) {
/* 1572 */       for (EntityItem entityItem : arrayOfEntityItem) {
/* 1573 */         arrayList.add(entityItem);
/*      */       }
/*      */     }
/* 1576 */     this.abr.addDebug("getMachtypesByType MACHTYPE size " + arrayList.size());
/* 1577 */     return arrayList;
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
/*      */   protected String getLoadingGroup(EntityItem paramEntityItem) throws RfcAbrException {
/* 1592 */     String str = null;
/* 1593 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELGEOMOD", "GEOMOD");
/* 1594 */     if (vector.size() > 0) {
/* 1595 */       EntityItem entityItem = vector.get(0);
/* 1596 */       String str1 = getAttributeShortValue(entityItem, "PLNTOFMFR");
/* 1597 */       str = getLoadingGroupByPlantOfMfr(str1);
/* 1598 */       this.abr.addDebug("getLoadingGroup ladgr " + str + " for PLNTOFMFR " + str1 + " of " + entityItem.getKey() + " " + paramEntityItem
/* 1599 */           .getKey());
/*      */     } 
/* 1601 */     if (str == null || "".equals(str.trim())) {
/* 1602 */       str = "H001";
/* 1603 */       this.abr.addDebug("getLoadingGroup ladgr is null, set H001 as default");
/*      */     } 
/* 1605 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getLoadingGroupByPlantOfMfr(String paramString) {
/* 1615 */     String str = "eacm.plantOfMfr_" + paramString;
/* 1616 */     this.abr.addDebug("getLoadingGroupByPlantOfMfr key " + str);
/* 1617 */     return this.configManager.getString(str);
/*      */   }
/*      */   
/*      */   protected boolean isRevProfOrAuoMtrlChanged(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws RfcAbrException {
/* 1621 */     ArrayList<String> arrayList1 = new ArrayList();
/* 1622 */     ArrayList<String> arrayList2 = new ArrayList();
/* 1623 */     RevProfile revProfile1 = getRevProfile(paramEntityItem1);
/* 1624 */     RevProfile revProfile2 = getRevProfile(paramEntityItem2);
/*      */     
/* 1626 */     String str1 = revProfile1.getRevenueProfile();
/* 1627 */     String str2 = revProfile2.getRevenueProfile();
/* 1628 */     Vector<AUOMaterial> vector1 = revProfile1.getAuoMaterials();
/* 1629 */     if (vector1 != null && vector1.size() > 0) {
/* 1630 */       for (byte b = 0; b < vector1.size(); b++) {
/* 1631 */         AUOMaterial aUOMaterial = vector1.get(b);
/* 1632 */         String str = str1 + "|" + aUOMaterial.getPercentage() + "|" + aUOMaterial.getMaterial();
/* 1633 */         arrayList1.add(str);
/*      */       } 
/*      */     } else {
/* 1636 */       arrayList1.add(str1);
/*      */     } 
/* 1638 */     Vector<AUOMaterial> vector2 = revProfile2.getAuoMaterials();
/* 1639 */     if (vector2 != null && vector2.size() > 0) {
/* 1640 */       for (byte b = 0; b < vector2.size(); b++) {
/* 1641 */         AUOMaterial aUOMaterial = vector2.get(b);
/* 1642 */         String str = str2 + "|" + aUOMaterial.getPercentage() + "|" + aUOMaterial.getMaterial();
/* 1643 */         arrayList2.add(str);
/*      */       } 
/*      */     } else {
/* 1646 */       arrayList2.add(str2);
/*      */     } 
/* 1648 */     this.abr.addDebug("isRevProfOrAuoMtrlChanged T1 " + arrayList1);
/* 1649 */     this.abr.addDebug("isRevProfOrAuoMtrlChanged T2 " + arrayList2);
/* 1650 */     boolean bool = (!arrayList1.containsAll(arrayList2) || !arrayList2.containsAll(arrayList1)) ? true : false;
/* 1651 */     this.abr.addDebug("isRevProfOrAuoMtrlChanged  " + bool);
/* 1652 */     return bool;
/*      */   }
/*      */   
/*      */   protected boolean needReleaseParkingTable() {
/* 1656 */     if ("n".equalsIgnoreCase(this.configManager.getString("eacm.releaseParkingTable_" + this.abr.getEntityType()))) {
/* 1657 */       this.abr.addDebug("needReleaseParkingTable  false");
/* 1658 */       return false;
/*      */     } 
/* 1660 */     this.abr.addDebug("needReleaseParkingTable  true");
/* 1661 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Vector getDeletedAuoMaterials(Vector paramVector1, Vector paramVector2) {
/* 1672 */     Vector vector = new Vector();
/* 1673 */     if (paramVector1 != null && paramVector1.size() > 0) {
/* 1674 */       for (Object object : paramVector1) {
/* 1675 */         vector.add(object);
/*      */       }
/* 1677 */       for (AUOMaterial aUOMaterial1 : paramVector1) {
/* 1678 */         AUOMaterial aUOMaterial2 = aUOMaterial1;
/* 1679 */         if (paramVector2 != null && paramVector2.size() > 0) {
/* 1680 */           for (AUOMaterial aUOMaterial3 : paramVector2) {
/* 1681 */             AUOMaterial aUOMaterial4 = aUOMaterial3;
/* 1682 */             String str1 = aUOMaterial2.getMaterial();
/* 1683 */             String str2 = aUOMaterial4.getMaterial();
/* 1684 */             String str3 = aUOMaterial2.getPercentage();
/* 1685 */             String str4 = aUOMaterial4.getPercentage();
/* 1686 */             if (isSameString(str1, str2) && isSameString(str3, str4)) {
/* 1687 */               vector.remove(aUOMaterial1);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1694 */     return vector;
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
/*      */   protected Vector searchAllGeneralAreas() throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1706 */     Vector<EntityItem> vector = new Vector();
/* 1707 */     List<Integer> list = searchAllGeneralAreaEntityIds();
/* 1708 */     for (Integer integer : list) {
/* 1709 */       if (integer != null && integer.intValue() > 0) {
/*      */         
/* 1711 */         EntityItem entityItem = getEntityList(this.abr.getDatabase(), this.abr.getProfile(), "dummy", "GENERALAREA", integer.intValue()).getParentEntityGroup().getEntityItem(0);
/* 1712 */         vector.add(entityItem);
/*      */       } 
/*      */     } 
/* 1715 */     this.abr.addDebug("searchAllGeneralAreas GENERALAREA size " + vector.size());
/* 1716 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Integer> searchAllGeneralAreaEntityIds() {
/* 1725 */     ArrayList<Integer> arrayList = new ArrayList();
/* 1726 */     PreparedStatement preparedStatement = null;
/* 1727 */     String str = "SELECT entityid FROM opicm.entity WHERE entitytype='GENERALAREA' and valto > CURRENT TIMESTAMP AND effto > CURRENT TIMESTAMP WITH UR";
/*      */     try {
/* 1729 */       preparedStatement = this.abr.getDatabase().getPDHConnection().prepareStatement(str);
/* 1730 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 1731 */       while (resultSet.next()) {
/* 1732 */         arrayList.add(Integer.valueOf(resultSet.getInt(1)));
/*      */       }
/* 1734 */     } catch (MiddlewareException middlewareException) {
/* 1735 */       middlewareException.printStackTrace();
/* 1736 */       this.abr.addDebug("searchAllGENERALAREAEntityIds MiddlewareException on " + middlewareException.getMessage());
/* 1737 */     } catch (SQLException sQLException) {
/* 1738 */       sQLException.printStackTrace();
/* 1739 */       this.abr.addDebug("searchAllGENERALAREAEntityIds SQLException on " + sQLException.getMessage());
/*      */     } finally {
/* 1741 */       if (preparedStatement != null) {
/*      */         try {
/* 1743 */           preparedStatement.close();
/* 1744 */         } catch (SQLException sQLException) {
/* 1745 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 1749 */     this.abr.addDebug("searchAllGENERALAREAEntityIds GENERALAREA id size " + arrayList.size());
/* 1750 */     return arrayList;
/*      */   }
/*      */   
/*      */   private boolean isSameString(String paramString1, String paramString2) {
/* 1754 */     boolean bool = false;
/* 1755 */     if (paramString1 == null && paramString2 == null) {
/* 1756 */       bool = true;
/* 1757 */     } else if (paramString1 != null && paramString1.equals(paramString2)) {
/* 1758 */       bool = true;
/* 1759 */     } else if (paramString2 != null && paramString2.equals(paramString1)) {
/* 1760 */       bool = true;
/*      */     } 
/* 1762 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isGENERALAREAContainsZAButNoUS(Vector<EntityItem> paramVector) throws RfcAbrException {
/* 1773 */     boolean bool1 = false;
/* 1774 */     boolean bool2 = false;
/* 1775 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1776 */       EntityItem entityItem = paramVector.get(b);
/* 1777 */       String str = getAttributeFlagValue(entityItem, "GENAREANAME");
/* 1778 */       if ("1624".equals(str)) {
/* 1779 */         bool1 = true;
/*      */       }
/* 1781 */       if ("1652".equals(str)) {
/* 1782 */         bool2 = true;
/*      */       }
/*      */     } 
/* 1785 */     if (bool1 && !bool2) {
/* 1786 */       return true;
/*      */     }
/* 1788 */     return false;
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
/*      */   protected void processTaxSupportForUSWhenProductIsSetForZA(TypeModel paramTypeModel, CHWAnnouncement paramCHWAnnouncement, CHWGeoAnn paramCHWGeoAnn, String paramString1, String paramString2, boolean paramBoolean, TypeModelUPGGeo paramTypeModelUPGGeo, String paramString3, String paramString4, PlannedSalesStatus paramPlannedSalesStatus) throws Exception {
/* 1807 */     Integer integer = getGENERALAREAEntityIdByGENAREANAME("1652");
/* 1808 */     if (integer != null) {
/*      */       
/* 1810 */       EntityItem entityItem = getEntityList(this.abr.getDatabase(), this.abr.getProfile(), "dummy", "GENERALAREA", integer.intValue()).getParentEntityGroup().getEntityItem(0);
/* 1811 */       Vector<String> vector = getAttributeMultiFlagValue(entityItem, "CBSLEGACYPLNTCD");
/* 1812 */       String str1 = getAttributeValue(entityItem, "SLEORG");
/* 1813 */       String str2 = getAttributeValue(entityItem, "GENAREACODE");
/* 1814 */       SalesOrgPlants salesOrgPlants = new SalesOrgPlants();
/* 1815 */       salesOrgPlants.setSalesorg(str1);
/* 1816 */       salesOrgPlants.setGenAreaCode(str2);
/* 1817 */       Vector vector1 = getTaxListBySalesOrgPlants(salesOrgPlants);
/* 1818 */       for (byte b = 0; b < vector.size(); b++) {
/* 1819 */         String str = vector.get(b);
/* 1820 */         this.abr.addDebug("processTaxSupportForUSWhenProductIsSetForZA for plant: " + str + " salesOrg: " + str1);
/*      */         
/* 1822 */         if (str.equals("1999")) {
/* 1823 */           this.abr.addDebug("processTaxSupportForUSWhenProductIsSetForZA skip plant for 1999");
/*      */         
/*      */         }
/* 1826 */         else if ("r117".equalsIgnoreCase(paramString4)) {
/* 1827 */           this.rdhRestProxy.r117(paramCHWAnnouncement, paramTypeModel.getType() + paramTypeModel.getModel(), paramTypeModel.getDiv(), paramTypeModel
/* 1828 */               .getAcctAsgnGrp(), paramPlannedSalesStatus, true, paramString1, paramString2, str1, paramTypeModel
/* 1829 */               .getProductHierarchy(), vector1, str, paramCHWGeoAnn);
/* 1830 */           this.abr.addOutput("[R177] Create 300 classification for type UF for UPG for plant " + str);
/*      */         }
/* 1832 */         else if (paramBoolean) {
/* 1833 */           this.rdhRestProxy.r102(paramCHWAnnouncement, paramTypeModel, str, "MTC", paramTypeModelUPGGeo, paramString3, paramString1, paramString2, str1, vector1, paramCHWGeoAnn, null);
/*      */           
/* 1835 */           this.abr.addOutput("[R102] Create generic plant " + str + " view for type MTC material");
/*      */         } else {
/* 1837 */           this.rdhRestProxy.r102(paramCHWAnnouncement, paramTypeModel, str, "NEW", null, null, paramString1, paramString2, str1, vector1, paramCHWGeoAnn, null);
/*      */           
/* 1839 */           this.rdhRestProxy.r102(paramCHWAnnouncement, paramTypeModel, str, "UPG", null, null, paramString1, paramString2, str1, vector1, paramCHWGeoAnn, null);
/*      */           
/* 1841 */           this.abr.addOutput("[R102] Create generic plant " + str + " view for type NEW/UPG material");
/*      */         } 
/*      */       } 
/*      */     } 
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
/*      */   private Integer getGENERALAREAEntityIdByGENAREANAME(String paramString) {
/* 1856 */     Integer integer = null;
/* 1857 */     Statement statement = null;
/* 1858 */     String str = "select entityid from opicm.flag where entitytype = 'GENERALAREA' AND attributecode='GENAREANAME' AND ATTRIBUTEVALUE='" + paramString + "' and valto > current timestamp and effto > current timestamp with ur";
/*      */     
/*      */     try {
/* 1861 */       statement = this.abr.getDatabase().getPDHConnection().createStatement();
/* 1862 */       ResultSet resultSet = statement.executeQuery(str);
/* 1863 */       if (resultSet.next()) {
/* 1864 */         integer = Integer.valueOf(resultSet.getInt(1));
/*      */       }
/* 1866 */     } catch (MiddlewareException middlewareException) {
/* 1867 */       this.abr.addDebug("MiddlewareException on ? " + middlewareException);
/* 1868 */       middlewareException.printStackTrace();
/* 1869 */     } catch (SQLException sQLException) {
/* 1870 */       this.abr.addDebug("SQLException on ? " + sQLException);
/* 1871 */       sQLException.printStackTrace();
/*      */     } finally {
/* 1873 */       if (statement != null) {
/*      */         try {
/* 1875 */           statement.close();
/* 1876 */         } catch (SQLException sQLException) {
/* 1877 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 1881 */     this.abr.addDebug("getGENERALAREAEntityIdByGENAREANAME entityid: " + integer + " GENAREANAME: " + paramString);
/* 1882 */     return integer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isHIPOModel(String paramString1, String paramString2) {
/* 1893 */     boolean bool = false;
/* 1894 */     if (("5313".equals(paramString1) && "HPO".equals(paramString2)) || ("5372".equals(paramString1) && "IS5".equals(paramString2))) {
/* 1895 */       bool = true;
/*      */     }
/* 1897 */     return bool;
/*      */   }
/*      */   
/*      */   protected boolean isNetPriceMES(EntityItem paramEntityItem) throws RfcAbrException {
/* 1901 */     boolean bool = false;
/* 1902 */     String str = getAttributeFlagValue(paramEntityItem, "NETPRICEMES");
/* 1903 */     if ("NETPRICEMESYES".equals(str)) {
/* 1904 */       bool = true;
/*      */     }
/* 1906 */     this.abr.addDebug("isNetPriceMES NETPRICEMES: " + str + " isNetPriceMES: " + bool + " for " + paramEntityItem
/* 1907 */         .getKey());
/* 1908 */     return bool;
/*      */   }
/*      */   
/*      */   protected String getUniqueId(String paramString) {
/* 1912 */     if (this.uniqueID == null)
/* 1913 */       this.uniqueID = UniqueIdGenerator.getUniqueIdGenerator(paramString).getUniqueID(this.abr); 
/* 1914 */     return this.uniqueID;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RfcAbrAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */