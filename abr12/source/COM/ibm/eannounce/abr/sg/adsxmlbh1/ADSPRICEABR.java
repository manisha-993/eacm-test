/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.IOException;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ADSPRICEABR
/*      */   extends XMLMQAdapter
/*      */ {
/*      */   private static final String NONEPDHDOMAIN;
/*      */   private static final String ALLPRICETYPE;
/*      */   private static final String NONEPRICETYPE;
/*      */   private static final String OFFERINGTYPE;
/*  127 */   private String MQCID = "";
/*      */ 
/*      */   
/*      */   private static final int PRICE_ENTITY_LIMIT;
/*      */   
/*      */   private static final int PRICE_MQ_LIMIT;
/*      */   
/*      */   protected static int PRICE_MESSAGE_COUNT;
/*      */   
/*      */   protected static int PRICE_RECORD_COUNT;
/*      */   
/*  138 */   private int max = -1;
/*  139 */   private long ALL_WWPRT_SEND_TIME = 0L;
/*  140 */   private boolean isSended = false; private static final String PRICE_WWPRT_SQL = "  SELECT                                                                       \r\n     PRICE.ID AS ID,                                                           \r\n     PRICE.PRICEXML AS PRICEXML                                                \r\n   FROM PRICE.PRICE AS PRICE                                                   \r\n   WHERE                                                                       \r\n     PRICE.ACTION = 'I'                                                        \r\n"; private static final String PRICE_WWPRT_SQL_LIMITED = "  SELECT                                                                       \r\n     PRICE.ID AS ID,                                                           \r\n    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n     PRICE.PRICEXML AS PRICEXML                                                \r\n   FROM PRICE.PRICE AS PRICE                                                   \r\n   WHERE                                                                       \r\n     PRICE.ACTION = 'I'                                                        \r\n"; private static String SQL_MODEL; private static String SQL_FEATURE; private static String SQL_SWFEATURE; private static String SQL_FCTRANSACTION; private static String SQL_MODELCONVERT; private static String SQL_LSEO; private static String SQL_ESW; public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException { String str1 = paramProfile1.getValOn(); String str2 = paramProfile2.getValOn(); paramADSABRSTATUS.addDebug("ADSPRICEABR process t1DTS=" + str1); paramADSABRSTATUS.addDebug("ADSPRICEABR process t2DTS=" + str2); PRICE_MESSAGE_COUNT = 0; PRICE_RECORD_COUNT = 0; String str3 = getDescription(paramEntityItem, "ADSPPFORMAT", "long"); if (str3 != null && "WWPRT".equals(str3)) { this.MQCID = "PRICE"; } else if ("EACM".equals(str3)) { this.MQCID = "PRODUCT_PRICE_UPDATE"; }  String str4 = getDescription(paramEntityItem, "ADSOFFTYPE", "long"); String str5 = getDescription(paramEntityItem, "ADSOFFCAT", "long"); String str6 = getMultiDescription(paramEntityItem, "ADSCOUNTRY", "long"); String str7 = getMultiDescription(paramEntityItem, "ADSPRICETYPE", "flag"); String str8 = getDescription(paramEntityItem, "ADSPPABRSTATUS", "long"); Hashtable hashtable = getDescriptionTable(paramEntityItem, "PDHDOMAIN", "long"); String str9 = PokUtils.getAttributeValue(paramEntityItem, "PUBTO", ",", "", false); String str10 = getMultiDescription(paramEntityItem, "ADSXPRICETYPE", "flag"); String str11 = PokUtils.getAttributeValue(paramEntityItem, "DIVTEXT", ",", "", false); String str12 = PokUtils.getAttributeValue(paramEntityItem, "PRICEIDLMAXMSG", ",", "", false); if (isValidCond(str12)) { this.max = Integer.parseInt(str12); paramADSABRSTATUS.addDebug("XMLIDLMAXMSG = " + str12); }  paramADSABRSTATUS.addDebug("ADSPRICEABR0 ADSPPFORMAT=" + str3); paramADSABRSTATUS.addDebug("ADSPRICEABR2 ADSOFFTYPE=" + str4); paramADSABRSTATUS.addDebug("ADSPRICEABR2 ADSOFFCAT=" + str5); paramADSABRSTATUS.addDebug("ADSPRICEABR3 ADSCOUNTRY=" + str6); paramADSABRSTATUS.addDebug("ADSPRICEABR4 ADSPRICETYPE=" + str7); paramADSABRSTATUS.addDebug("ADSPRICEABR5 ADSPPABRSTATUS=" + str8); paramADSABRSTATUS.addDebug("ADSPRICEABR6 ADSPDHDOMAIN=" + hashtable); paramADSABRSTATUS.addDebug("ADSPRICEABR7 ADSPUBTO=" + str9); paramADSABRSTATUS.addDebug("ADSPRICEABR8 ADSXPRICETYPE=" + str10); paramADSABRSTATUS.addDebug("ADSPRICEABR9 ADSDIVTEXT=" + str11); paramADSABRSTATUS.addDebug("ADSPRICEABR10 PRICEIDLMAXMSG=" + str12); Hashtable<Object, Object> hashtable1 = new Hashtable<>(); hashtable1 = getPriceTable(paramADSABRSTATUS, str3, str1, str4, str5, str6, str7, paramEntityItem, paramProfile2, hashtable, str9, str10, str11); paramADSABRSTATUS.addDebug("priceTable.size():" + hashtable1.size()); paramADSABRSTATUS.addDebug("isSended:" + this.isSended); if (hashtable1.size() == 0 && !this.isSended) { paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "PRICE"); } else if (hashtable1.size() != 0 || this.isSended != true) { if (str3 != null && "WWPRT".equals(str3)) { sendWWPRTXML(paramADSABRSTATUS, paramProfile2, paramEntityItem, hashtable1); } else { sendEACMXML(paramADSABRSTATUS, paramProfile2, paramEntityItem, hashtable1); }  paramADSABRSTATUS.addXMLGenMsg("SUCCESS", "total " + PRICE_MESSAGE_COUNT + " Messsages for the price."); paramADSABRSTATUS.addXMLGenMsg("SUCCESS", "total " + PRICE_RECORD_COUNT + " records for the price."); }  if (str3 != null && "WWPRT".equals(str3)) paramADSABRSTATUS.addDebug("Sending WWPRT MQ message total takes time: " + this.ALL_WWPRT_SEND_TIME + " ms");  } private Hashtable getPriceTable(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, EntityItem paramEntityItem, Profile paramProfile, Hashtable paramHashtable, String paramString7, String paramString8, String paramString9) throws SQLException, NumberFormatException, MiddlewareRequestException, MiddlewareException, DOMException, MissingResourceException, ParserConfigurationException, TransformerException { Hashtable<Object, Object> hashtable = new Hashtable<>(); StringBuffer stringBuffer = null; if (paramString1 != null && !"".equals(paramString1)) { if ("WWPRT".equals(paramString1)) { stringBuffer = new StringBuffer(); if (maxLimited()) { stringBuffer.append("  SELECT                                                                       \r\n     PRICE.ID AS ID,                                                           \r\n    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n     PRICE.PRICEXML AS PRICEXML                                                \r\n   FROM PRICE.PRICE AS PRICE                                                   \r\n   WHERE                                                                       \r\n     PRICE.ACTION = 'I'                                                        \r\n"); } else { stringBuffer.append("  SELECT                                                                       \r\n     PRICE.ID AS ID,                                                           \r\n     PRICE.PRICEXML AS PRICEXML                                                \r\n   FROM PRICE.PRICE AS PRICE                                                   \r\n   WHERE                                                                       \r\n     PRICE.ACTION = 'I'                                                        \r\n"); }  getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processWWPRTPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramEntityItem, paramProfile); } else if ("EACM".equals(paramString1)) { if ("MODEL".equals(paramString3)) { stringBuffer = new StringBuffer(); stringBuffer.append(SQL_MODEL); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, paramString9); } else if ("FEATURE".equals(paramString3)) { stringBuffer = new StringBuffer(); stringBuffer.append(SQL_FEATURE); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, paramString9); } else if ("SWFEATURE".equals(paramString3)) { stringBuffer = new StringBuffer(); stringBuffer.append(SQL_SWFEATURE); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, ""); } else if ("FCTRANSACTION".equals(paramString3)) { stringBuffer = new StringBuffer(); stringBuffer.append(SQL_FCTRANSACTION); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, ""); } else if ("MODELCONVERT".equals(paramString3)) { stringBuffer = new StringBuffer(); stringBuffer.append(SQL_MODELCONVERT); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, ""); } else if ("NULL".equals(paramString3)) { stringBuffer = new StringBuffer(); stringBuffer.append(SQL_LSEO); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, ""); } else if ("".equals(paramString3)) { stringBuffer = new StringBuffer(); stringBuffer.append(SQL_MODEL); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, paramString9); stringBuffer = new StringBuffer(); stringBuffer.append(SQL_FEATURE); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, paramString9); stringBuffer = new StringBuffer(); stringBuffer.append(SQL_SWFEATURE); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, ""); stringBuffer = new StringBuffer(); stringBuffer.append(SQL_MODELCONVERT); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, ""); stringBuffer = new StringBuffer(); stringBuffer.append(SQL_LSEO); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, ""); stringBuffer = new StringBuffer(); stringBuffer.append(SQL_ESW); getQuerySql(paramADSABRSTATUS, paramString1, paramString2, paramString3, paramString5, paramString6, paramProfile, paramString7, paramString8, stringBuffer); hashtable = processEACMPrice(hashtable, paramADSABRSTATUS, stringBuffer.toString(), paramString1, paramString4, paramEntityItem, paramProfile, paramHashtable, ""); }  } else { return new Hashtable<>(); }  } else { return new Hashtable<>(); }  return hashtable; } private static String offeringtypes = "";
/*      */   private Hashtable processWWPRTPrice(Hashtable<String, Vector<String>> paramHashtable, ADSABRSTATUS paramADSABRSTATUS, String paramString, EntityItem paramEntityItem, Profile paramProfile) throws MiddlewareException, SQLException, DOMException, MissingResourceException, ParserConfigurationException, TransformerException { Connection connection = null; PreparedStatement preparedStatement = null; ResultSet resultSet = null; connection = paramADSABRSTATUS.getDB().getODSConnection(); preparedStatement = connection.prepareStatement(paramString); paramADSABRSTATUS.addDebug("processWWPRTPrice sql:" + paramString); resultSet = preparedStatement.executeQuery(); String str1 = ""; boolean bool = false; String str2 = ""; String str3 = ""; Vector<String> vector = null; byte b1 = 0; byte b2 = 0; while (resultSet.next()) { if (b1 >= PRICE_ENTITY_LIMIT) { paramADSABRSTATUS.addDebug("ADSPRICEABR WWPRT format clear priceTable result_size=" + b1 + " \r\n"); bool = true; b1 = 0; }  str2 = convertValue(resultSet.getString("ID")); str3 = resultSet.getString("PRICEXML"); paramADSABRSTATUS.addDebug("processWWPRTPrice PRICEXML:" + str3); str1 = str2.trim(); vector = (Vector)paramHashtable.get(str1); if (vector != null && vector.size() > 0 && !bool) { vector.add(str3); paramHashtable.put(str1, vector); } else { bool = clearPriceTable(paramADSABRSTATUS, "WWPRT", paramEntityItem, paramProfile, paramHashtable, bool); vector = new Vector<>(); vector.add(str3); paramHashtable.put(str1, vector); }  b1++; b2++; if (maxLimited() && b2 >= this.max) { String str = resultSet.getString("INSERT_TS"); paramADSABRSTATUS.setT2DTS(str); paramADSABRSTATUS.addDebug("The count of msg has reached the max msg definition in the setup entity: " + this.max); paramADSABRSTATUS.addDebug("Set the last run date as : " + str); return paramHashtable; }  }  if (resultSet != null) { resultSet.close(); resultSet = null; }  if (preparedStatement != null) { preparedStatement.close(); preparedStatement = null; }  return paramHashtable; }
/*  142 */   private Hashtable processEACMPrice(Hashtable<String, Vector<PriceInfo>> paramHashtable1, ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2, String paramString3, EntityItem paramEntityItem, Profile paramProfile, Hashtable paramHashtable2, String paramString4) throws MiddlewareException, SQLException, DOMException, MissingResourceException, ParserConfigurationException, TransformerException { paramADSABRSTATUS.addDebug("SQL:" + paramString1); Connection connection = null; PreparedStatement preparedStatement = null; ResultSet resultSet = null; connection = paramADSABRSTATUS.getDB().getODSConnection(); preparedStatement = connection.prepareStatement(paramString1); resultSet = preparedStatement.executeQuery(); byte b1 = 0; String str1 = ""; boolean bool = false; String str2 = ""; String str3 = ""; String str4 = ""; String str5 = ""; String str6 = ""; String str7 = ""; String str8 = ""; String str9 = ""; String str10 = ""; String str11 = ""; String str12 = ""; String str13 = ""; String str14 = ""; String str15 = ""; String str16 = ""; String str17 = ""; String str18 = ""; String str19 = ""; String str20 = ""; String str21 = ""; String str22 = ""; String str23 = ""; String str24 = ""; String str25 = ""; String str26 = ""; String str27 = ""; String str28 = ""; int i = 0; String str29 = ""; String str30 = ""; String str31 = ""; String str32 = ""; String str33 = ""; String str34 = ""; String str35 = ""; boolean bool1 = false; PriceInfo priceInfo = null; Vector<PriceInfo> vector = null; byte b2 = 0; byte b3 = 0; Hashtable hashtable = getDIVTEXTTable(paramString4); while (resultSet.next()) { if (b2 >= PRICE_ENTITY_LIMIT) { paramADSABRSTATUS.addDebug("ADSPRICEABR EACM format clear priceTable result_size=" + b2 + " \r\n"); bool = true; b2 = 0; }  str2 = convertValue(resultSet.getString("PRODUCTOFFERINGTYPE")); str3 = convertValue(resultSet.getString("OFFERING_TYPE")); str4 = convertValue(resultSet.getString("MACHTYPEATR")); str5 = convertValue(resultSet.getString("MODELATR")); str6 = convertValue(resultSet.getString("FEATURECODE")); str7 = convertValue(resultSet.getString("FROM_MACHTYPEATR")); str8 = convertValue(resultSet.getString("FROM_MODELATR")); str9 = convertValue(resultSet.getString("FROM_FEATURECODE")); str10 = convertValue(resultSet.getString("PARTNUM")); str11 = convertValue(resultSet.getString("OFFERING")); str12 = convertValue(resultSet.getString("START_DATE")); str13 = convertValue(resultSet.getString("CURRENCY")); str14 = convertValue(resultSet.getString("CABLETYPE")); str15 = convertValue(resultSet.getString("CABLEID")); str16 = convertValue(resultSet.getString("RELEASE_TS")); str17 = convertValue(resultSet.getString("PRICE_VALUE")); paramADSABRSTATUS.addDebug("PRICETYPE:" + str21); paramADSABRSTATUS.addDebug("result.getString(\"PRICE_VALUE\"):" + resultSet.getString("PRICE_VALUE")); str18 = convertValue(resultSet.getString("PRICE_POINT_TYPE")); str19 = convertValue(resultSet.getString("PRICE_POINT_VALUE")); str20 = convertValue(resultSet.getString("COUNTRY")); str21 = convertValue(resultSet.getString("PRICE_TYPE")); str22 = convertValue(resultSet.getString("ONSHORE")); str23 = convertValue(resultSet.getString("END_DATE")); str24 = convertValue(resultSet.getString("PRICE_VALUE_USD")); str25 = convertValue(resultSet.getString("FACTOR")); str26 = "Update"; if (!"".equals(paramString4)) str27 = convertValue(resultSet.getString("DIV"));  str28 = ""; i = 0; str29 = ""; str30 = ""; str31 = ""; str32 = ""; str33 = ""; str34 = ""; str35 = ""; b1++; str32 = convertValue(resultSet.getString("PDHDOMAIN")); str33 = convertValue(resultSet.getString("PRODUCTCATEGORY")); str34 = convertValue(resultSet.getString("MKTGNAME")); str35 = convertValue(resultSet.getString("INVNAME")); bool1 = false; if ("EEE".equals(str3) || "OSP".equals(str3)) bool1 = true;  str2 = convertValue(resultSet.getString("PRODUCTOFFERINGTYPE")); str28 = convertValue(resultSet.getString("PRODUCTENTITYTYPE")); if (bool1) { str29 = ""; } else { i = resultSet.getInt("PRODUCTENTITYID"); str29 = String.valueOf(i); }  str30 = convertValue(resultSet.getString("PRICEPOINTENTITYTYPE")); str31 = convertValue(resultSet.getString("PRICEPOINTENTITYID")); if ("LSEOBUNDLE".equals(str33)) str33 = getBUNDLETYPE(paramADSABRSTATUS.getDatabase(), paramEntityItem, i, "LSEOBUNDLE");  if (paramString3 != null && !"".equals(paramString3) && !str33.equals(paramString3)) { b3++; if (maxLimited() && b3 >= this.max) { String str = resultSet.getString("INSERT_TS"); paramADSABRSTATUS.setT2DTS(str); paramADSABRSTATUS.addDebug("The count of msg has reached the max msg definition in the setup entity: " + this.max); paramADSABRSTATUS.addDebug("Set the last run date as : " + str); return paramHashtable1; }  continue; }  if (!"".equals(paramString4) && !hashtable.containsKey(str27)) { b3++; if (maxLimited() && b3 >= this.max) { String str = resultSet.getString("INSERT_TS"); paramADSABRSTATUS.setT2DTS(str); paramADSABRSTATUS.addDebug("The count of msg has reached the max msg definition in the setup entity: " + this.max); paramADSABRSTATUS.addDebug("Set the last run date as : " + str); return paramHashtable1; }  continue; }  if (paramHashtable2.size() > 0 && !bool1) if (paramHashtable2.size() != 1 || !paramHashtable2.containsKey(NONEPDHDOMAIN)) { if (str32 == null || "".equals(str32)) continue;  if (!paramHashtable2.containsKey(str32)) { b3++; if (maxLimited() && b3 >= this.max) { String str = resultSet.getString("INSERT_TS"); paramADSABRSTATUS.setT2DTS(str); paramADSABRSTATUS.addDebug("The count of msg has reached the max msg definition in the setup entity: " + this.max); paramADSABRSTATUS.addDebug("Set the last run date as : " + str); return paramHashtable1; }  continue; }  }   str1 = str32 + "|" + str33 + "|" + str2 + "|" + str20 + "|" + str21; paramADSABRSTATUS.addDebug("PRICETYPE:" + str21); priceInfo = new PriceInfo(str2, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, str17, str18, str19, str20, str21, str22, str23, str24, str25, str26, str32, str33, str28, str29, str30, str31, str34, str35, null); vector = (Vector)paramHashtable1.get(str1); if (vector != null && vector.size() > 0 && !bool) { vector.add(priceInfo); paramHashtable1.put(str1, vector); } else { bool = clearPriceTable(paramADSABRSTATUS, paramString2, paramEntityItem, paramProfile, paramHashtable1, bool); vector = new Vector<>(); vector.add(priceInfo); paramHashtable1.put(str1, vector); }  b2++; b3++; if (maxLimited() && b3 >= this.max) { String str = resultSet.getString("INSERT_TS"); paramADSABRSTATUS.setT2DTS(str); paramADSABRSTATUS.addDebug("The count of msg has reached the max msg definition in the setup entity: " + this.max); paramADSABRSTATUS.addDebug("Set the last run date as : " + str); return paramHashtable1; }  }  if (resultSet != null) { resultSet.close(); resultSet = null; }  if (preparedStatement != null) { preparedStatement.close(); preparedStatement = null; }  return paramHashtable1; } private void getQuerySql(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Profile paramProfile, String paramString6, String paramString7, StringBuffer paramStringBuffer) { String str = paramProfile.getValOn(); paramADSABRSTATUS.addDebug(" ADSPRICEABR t2DTS=" + str + "\r\n"); if (paramString2 != null && paramString2.length() == 10) { paramStringBuffer.append(" AND PRICE.INSERT_TS>=concat('").append(paramString2).append("','-00.00.00.000000') and  PRICE.INSERT_TS<='").append(str).append("'  \r\n"); } else { paramStringBuffer.append(" AND PRICE.INSERT_TS>='").append(paramString2).append("' and  PRICE.INSERT_TS<='").append(str).append("'  \r\n"); }  paramADSABRSTATUS.addDebug("ADSPRICEABR INSERT_TS cause is PRICE.INSERT_TS>='" + paramString2 + "' and  PRICE.INSERT_TS<='" + str + "\r\n"); if (paramString4 != null && !"".equals(paramString4)) if (paramString4.indexOf(",") > -1) { paramStringBuffer.append(" AND PRICE.COUNTRY IN(").append(paramString4).append(") \r\n"); } else { paramStringBuffer.append(" AND PRICE.COUNTRY =").append(paramString4).append(" \r\n"); }   if (paramString5 != null && !"".equals(paramString5)) if (paramString5.indexOf(ALLPRICETYPE) > -1) { if (paramString7 != null && !"".equals(paramString7) && paramString7.indexOf(NONEPRICETYPE) == -1) if (paramString7.indexOf(",") > -1) { paramStringBuffer.append(" AND PRICE.PRICE_TYPE NOT IN(").append(paramString7).append(") \r\n"); } else { paramStringBuffer.append(" AND PRICE.PRICE_TYPE<>").append(paramString7).append(" \r\n"); }   } else if (paramString5.indexOf(",") > -1) { paramStringBuffer.append(" AND PRICE.PRICE_TYPE IN(").append(paramString5).append(") \r\n"); } else { paramStringBuffer.append(" AND PRICE.PRICE_TYPE=").append(paramString5).append(" \r\n"); }   if ("EACM".equals(paramString1)) if (paramString6 != null && !"".equals(paramString6)) paramStringBuffer.append(" AND PRICE.END_DATE>=date('").append(paramString6).append("') \r\n");   if ("WWPRT".equals(paramString1)) { if (paramString6 != null && !"".equals(paramString6)) { paramStringBuffer.append(" AND PRICE.END_DATE>=date('").append(paramString6).append("') \r\n"); } else { paramStringBuffer.append(" AND PRICE.END_DATE>=date('").append(str).append("') \r\n"); }  if (paramString3 != null && !"".equals(paramString3)) if ("NULL".equals(paramString3)) { paramStringBuffer.append(" AND PRICE.OFFERING_TYPE in('',").append(offeringtypes).append(") \r\n"); } else { paramStringBuffer.append(" AND PRICE.OFFERING_TYPE='").append(paramString3).append("' \r\n"); }   }  if (maxLimited()) { paramStringBuffer.append("ORDER BY PRICE.INSERT_TS ASC").append(" \r\n"); paramStringBuffer.append(" FETCH FIRST " + this.max + " ROWS ONLY").append(" \r\n"); }  paramStringBuffer.append(" WITH UR"); paramADSABRSTATUS.addDebug(" ADSPRICEABR process Query SQL=\r\n" + paramStringBuffer.toString()); } public boolean clearPriceTable(ADSABRSTATUS paramADSABRSTATUS, String paramString, EntityItem paramEntityItem, Profile paramProfile, Hashtable paramHashtable, boolean paramBoolean) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException { if (paramBoolean) { if (paramString != null && "WWPRT".equals(paramString)) { sendWWPRTXML(paramADSABRSTATUS, paramProfile, paramEntityItem, paramHashtable); } else { sendEACMXML(paramADSABRSTATUS, paramProfile, paramEntityItem, paramHashtable); }  paramHashtable.clear(); this.isSended = true; paramBoolean = false; System.gc(); }  return paramBoolean; } static { String str1 = ABRServerProperties.getValue("ADSABRSTATUS", "_PRICE_ENTITY_LIMIT", "50000");
/*      */     
/*  144 */     String str2 = ABRServerProperties.getValue("ADSABRSTATUS", "_PRICE_MQ_LIMIT", "1000");
/*      */     
/*  146 */     PRICE_ENTITY_LIMIT = Integer.parseInt(str1);
/*  147 */     PRICE_MQ_LIMIT = Integer.parseInt(str2);
/*  148 */     NONEPDHDOMAIN = ABRServerProperties.getValue("ADSABRSTATUS", "_NONEPDHDOMAIN", "$None$");
/*      */     
/*  150 */     ALLPRICETYPE = ABRServerProperties.getValue("ADSABRSTATUS", "_ALLPRICETYPE", "$ALL$");
/*      */     
/*  152 */     NONEPRICETYPE = ABRServerProperties.getValue("ADSABRSTATUS", "_NONEPRICETYPE", "$NONE$");
/*      */     
/*  154 */     OFFERINGTYPE = ABRServerProperties.getValue("ADSABRSTATUS", "_OFFERINGTYPE", "SEO,EEE,OSP");
/*      */ 
/*      */     
/*  157 */     String[] arrayOfString = OFFERINGTYPE.split(",");
/*  158 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  159 */       if (b == 0) {
/*  160 */         offeringtypes = "'" + arrayOfString[b] + "'";
/*      */       } else {
/*  162 */         offeringtypes += ",'" + arrayOfString[b] + "'";
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1790 */     SQL_MODEL = "  SELECT                                                                       \r\n    PRICE.ID AS ID,                                                            \r\n    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n    MODEL.PDHDOMAIN AS PDHDOMAIN,                                              \r\n    MODEL.COFCAT AS PRODUCTCATEGORY,                                           \r\n    PRICE.OFFERING_TYPE AS PRODUCTOFFERINGTYPE,                                \r\n    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n    'MACHTYPE' AS PRODUCTENTITYTYPE,                                           \r\n    MODEL.MACHID AS PRODUCTENTITYID,                                           \r\n    'MODEL' AS PRICEPOINTENTITYTYPE,                                           \r\n    MODEL.MDLID AS PRICEPOINTENTITYID,                                         \r\n    MODEL.MACHTYPEATR AS MACHTYPEATR,                                          \r\n    MODEL.MODELATR AS MODELATR,                                                \r\n    '' AS FEATURECODE,                                                         \r\n    '' AS FROM_MACHTYPEATR,                                                    \r\n    '' AS FROM_MODELATR,                                                       \r\n    '' AS FROM_FEATURECODE,                                                    \r\n    '' AS PARTNUM,                                                             \r\n    MODEL.MKTGNAME AS MKTGNAME,                                                \r\n    MODEL.INVNAME AS INVNAME,                                                  \r\n    PRICE.OFFERING AS OFFERING,                                                \r\n    PRICE.START_DATE AS START_DATE,                                            \r\n    PRICE.CURRENCY AS CURRENCY,                                                \r\n    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n    PRICE.CABLEID AS CABLEID,                                                  \r\n    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n    PRICE.COUNTRY AS COUNTRY,                                                  \r\n    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n    PRICE.ONSHORE AS ONSHORE,                                                  \r\n    PRICE.END_DATE AS END_DATE,                                                \r\n    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n    PRICE.FACTOR AS FACTOR,                                                    \r\n    MODEL.DIV AS DIV                                                           \r\n  FROM PRICE.PRICE AS PRICE                                                    \r\n  INNER JOIN PRICE.MDLINFO AS MODEL ON                                         \r\n    PRICE.MACHTYPEATR = MODEL.MACHTYPEATR AND                                  \r\n    PRICE.MODELATR = MODEL.MODELATR                                            \r\n  WHERE                                                                        \r\n    PRICE.ACTION = 'I' AND                                                     \r\n    PRICE.PRICE_POINT_TYPE='MOD'                                               \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1834 */     SQL_FEATURE = "  SELECT                                                                                          \r\n    PRICE.ID AS ID,                                                                               \r\n    PRICE.INSERT_TS AS INSERT_TS,                                                                 \r\n    TMF.PDHDOMAIN AS PDHDOMAIN,                                                                   \r\n    TMF.COFCAT AS PRODUCTCATEGORY,                                                                \r\n    TMF.OFFTYPE AS PRODUCTOFFERINGTYPE,                                                           \r\n    TMF.OFFTYPE AS OFFERING_TYPE,                                                                 \r\n    TMF.PRODTYPE AS PRODUCTENTITYTYPE,                                                            \r\n    TMF.MACHID AS PRODUCTENTITYID,                                                                \r\n    TMF.FEATTYPE AS PRICEPOINTENTITYTYPE,                                                         \r\n    TMF.FEATID AS PRICEPOINTENTITYID,                                                             \r\n    TMF.MACHTYPEATR AS MACHTYPEATR,                                                               \r\n    PRICE.MODELATR AS MODELATR,                                                                   \r\n    TMF.FEATURECODE AS FEATURECODE,                                                               \r\n                                                                                                  \r\n    '' AS FROM_MACHTYPEATR,                                                                       \r\n    '' AS FROM_MODELATR,                                                                          \r\n    '' AS FROM_FEATURECODE,                                                                       \r\n    '' AS PARTNUM,                                                                                \r\n    TMF.MKTGNAME AS MKTGNAME,                                                                     \r\n    TMF.BHINVNAME AS INVNAME,                                                                     \r\n    PRICE.OFFERING AS OFFERING,                                                                   \r\n    PRICE.START_DATE AS START_DATE,                                                               \r\n    PRICE.CURRENCY AS CURRENCY,                                                                   \r\n    PRICE.CABLETYPE AS CABLETYPE,                                                                 \r\n    PRICE.CABLEID AS CABLEID,                                                                     \r\n    PRICE.RELEASE_TS AS RELEASE_TS,                                                               \r\n    PRICE.PRICE_VALUE AS PRICE_VALUE,                                                             \r\n    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                                   \r\n    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                                                 \r\n    PRICE.COUNTRY AS COUNTRY,                                                                     \r\n    PRICE.PRICE_TYPE AS PRICE_TYPE,                                                               \r\n    PRICE.ONSHORE AS ONSHORE,                                                                     \r\n    PRICE.END_DATE AS END_DATE,                                                                   \r\n    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                                     \r\n    PRICE.FACTOR AS FACTOR,                                                                       \r\n    TMF.DIV AS DIV                                                                                \r\n  FROM PRICE.PRICE AS PRICE                                                                       \r\n  INNER JOIN PRICE.TMF TMF ON                                                                     \r\n  PRICE.MACHTYPEATR = TMF.MACHTYPEATR AND                                                         \r\n  PRICE.FEATURECODE = TMF.FEATURECODE                                                             \r\n  WHERE                                                                                           \r\n    PRICE.ACTION = 'I' AND                                                                        \r\n    PRICE.PRICE_POINT_TYPE in ('FEA','RPQ')                                                       \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2085 */     SQL_SWFEATURE = "  SELECT                                                                       \r\n    PRICE.ID AS ID,                                                            \r\n    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n    SWTMF.PDHDOMAIN AS PDHDOMAIN,                                              \r\n    SWTMF.COFCAT AS PRODUCTCATEGORY,                                           \r\n    PRICE.OFFERING_TYPE AS PRODUCTOFFERINGTYPE,                                \r\n    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n    'MODEL' AS PRODUCTENTITYTYPE,                                              \r\n    SWTMF.MDLID AS PRODUCTENTITYID,                                            \r\n    'SWFEATURE' AS PRICEPOINTENTITYTYPE,                                       \r\n    SWTMF.SWFEATID AS PRICEPOINTENTITYID,                                      \r\n    SWTMF.MACHTYPEATR AS MACHTYPEATR,                                          \r\n    SWTMF.MODELATR AS MODELATR,                                                \r\n    SWTMF.FEATURECODE AS FEATURECODE,                                          \r\n    '' AS FROM_MACHTYPEATR,                                                    \r\n    '' AS FROM_MODELATR,                                                       \r\n    '' AS FROM_FEATURECODE,                                                    \r\n    '' AS PARTNUM,                                                             \r\n    SWTMF.SWFEATDESC AS MKTGNAME,                                              \r\n    SWTMF.BHINVNAME AS INVNAME,                                                \r\n    PRICE.OFFERING AS OFFERING,                                                \r\n    PRICE.START_DATE AS START_DATE,                                            \r\n    PRICE.CURRENCY AS CURRENCY,                                                \r\n    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n    PRICE.CABLEID AS CABLEID,                                                  \r\n    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n    PRICE.COUNTRY AS COUNTRY,                                                  \r\n    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n    PRICE.ONSHORE AS ONSHORE,                                                  \r\n    PRICE.END_DATE AS END_DATE,                                                \r\n    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n    PRICE.FACTOR AS FACTOR                                                     \r\n  FROM PRICE.PRICE AS PRICE                                                    \r\n  INNER JOIN PRICE.SWTMF AS SWTMF ON                                           \r\n    PRICE.OFFERING=CONCAT(SWTMF.MACHTYPEATR,SWTMF.MODELATR) AND                \r\n    PRICE.PRICE_POINT_VALUE=SWTMF.FEATURECODE                                  \r\n  WHERE                                                                        \r\n    PRICE.ACTION = 'I' AND                                                     \r\n    PRICE.PRICE_POINT_TYPE in ('SWF','WSF')                                    \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2128 */     SQL_FCTRANSACTION = "  SELECT                                                                       \r\n    PRICE.ID AS ID,                                                            \r\n    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n    FCTRANSACTION.PDHDOMAIN AS PDHDOMAIN,                                      \r\n    'Hardware' AS PRODUCTCATEGORY,                                             \r\n    CASE                                                                       \r\n        WHEN PRICE.PRICE_POINT_TYPE='FUP' THEN 'Feature Upgrade'               \r\n        WHEN PRICE.PRICE_POINT_TYPE='TFU' THEN 'Type Feature Upgrade'          \r\n        ELSE ''                                                                \r\n    END AS PRODUCTOFFERINGTYPE,                                                \r\n    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n    'FCTRANSACTION' AS PRODUCTENTITYTYPE,                                      \r\n    FCTRANSACTION.ENTITYID AS PRODUCTENTITYID,                                 \r\n    '' AS PRICEPOINTENTITYTYPE,                                                \r\n    '' AS PRICEPOINTENTITYID,                                                  \r\n    PRICE.MACHTYPEATR AS MACHTYPEATR,                                          \r\n    FCTRANSACTION.TOMODEL AS MODELATR,                                         \r\n    PRICE.FEATURECODE AS FEATURECODE,                                          \r\n    PRICE.FROM_MACHTYPEATR AS FROM_MACHTYPEATR,                                \r\n    FCTRANSACTION.FROMMODEL AS FROM_MODELATR,                                  \r\n    PRICE.FROM_FEATURECODE AS FROM_FEATURECODE,                                \r\n    '' AS PARTNUM,                                                             \r\n    '' AS MKTGNAME,                                                            \r\n    '' AS INVNAME,                                                             \r\n    PRICE.OFFERING AS OFFERING,                                                \r\n    PRICE.START_DATE AS START_DATE,                                            \r\n    PRICE.CURRENCY AS CURRENCY,                                                \r\n    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n    PRICE.CABLEID AS CABLEID,                                                  \r\n    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n    PRICE.COUNTRY AS COUNTRY,                                                  \r\n    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n    PRICE.ONSHORE AS ONSHORE,                                                  \r\n    PRICE.END_DATE AS END_DATE,                                                \r\n    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n    PRICE.FACTOR AS FACTOR                                                     \r\n  FROM PRICE.PRICE AS PRICE                                                    \r\n  INNER JOIN PRICE.FCTRANSACTION AS FCTRANSACTION ON                           \r\n    CAST(PRICE.FROM_MACHTYPEATR AS INTEGER)=  FCTRANSACTION.FROMMACHTYPE AND   \r\n    PRICE.FROM_FEATURECODE = FCTRANSACTION.FROMFEATURECODE AND                 \r\n    CAST(PRICE.MACHTYPEATR AS INTEGER) = FCTRANSACTION.TOMACHTYPE AND          \r\n    PRICE.FEATURECODE = FCTRANSACTION.TOFEATURECODE                            \r\n  WHERE                                                                        \r\n    PRICE.ACTION = 'I' AND                                                     \r\n    PRICE.OFFERING_TYPE = 'FCTRANSACTION' AND                                  \r\n    PRICE.PRICE_POINT_TYPE in('FUP','TFU')                                     \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2178 */     SQL_MODELCONVERT = "  SELECT                                                                       \r\n    PRICE.ID AS ID,                                                            \r\n    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n    MODELCONVERT.PDHDOMAIN AS PDHDOMAIN,                                       \r\n    'Hardware' AS PRODUCTCATEGORY,                                             \r\n    CASE                                                                       \r\n        WHEN PRICE.PRICE_POINT_TYPE='MUP' THEN 'Model Upgrade'                 \r\n        WHEN PRICE.PRICE_POINT_TYPE='TMU' THEN 'Type Model Upgrade'            \r\n        ELSE ''                                                                \r\n    END AS PRODUCTOFFERINGTYPE,                                                \r\n    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n                                                                               \r\n    'MODELCONVERT' AS PRODUCTENTITYTYPE,                                       \r\n    MODELCONVERT.ENTITYID AS PRODUCTENTITYID,                                  \r\n    '' AS PRICEPOINTENTITYTYPE,                                                \r\n    '' AS PRICEPOINTENTITYID,                                                  \r\n                                                                               \r\n    PRICE.MACHTYPEATR AS MACHTYPEATR,                                          \r\n    PRICE.MODELATR AS MODELATR,                                                \r\n    '' AS FEATURECODE,                                                         \r\n    PRICE.FROM_MACHTYPEATR AS FROM_MACHTYPEATR,                                \r\n    PRICE.FROM_MODELATR AS FROM_MODELATR,                                      \r\n    '' AS FROM_FEATURECODE,                                                    \r\n    '' AS PARTNUM,                                                             \r\n    '' AS MKTGNAME,                                                            \r\n    '' AS INVNAME,                                                             \r\n    PRICE.OFFERING AS OFFERING,                                                \r\n    PRICE.START_DATE AS START_DATE,                                            \r\n    PRICE.CURRENCY AS CURRENCY,                                                \r\n    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n    PRICE.CABLEID AS CABLEID,                                                  \r\n    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n    PRICE.COUNTRY AS COUNTRY,                                                  \r\n    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n    PRICE.ONSHORE AS ONSHORE,                                                  \r\n    PRICE.END_DATE AS END_DATE,                                                \r\n    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n    PRICE.FACTOR AS FACTOR                                                     \r\n  FROM PRICE.PRICE AS PRICE                                                    \r\n  INNER JOIN PRICE.MODELCONVERT AS MODELCONVERT ON                             \r\n    CAST(PRICE.FROM_MACHTYPEATR AS INTEGER)=  MODELCONVERT.FROMMACHTYPE AND    \r\n    PRICE.FROM_MODELATR = MODELCONVERT.FROMMODEL AND                           \r\n    CAST(PRICE.MACHTYPEATR AS INTEGER) = MODELCONVERT.TOMACHTYPE AND           \r\n    PRICE.MODELATR = MODELCONVERT.TOMODEL                                      \r\n  WHERE                                                                        \r\n    PRICE.ACTION = 'I' AND                                                     \r\n    PRICE.OFFERING_TYPE = 'MODELCONVERT' AND                                   \r\n    PRICE.PRICE_POINT_TYPE in ('MUP','TMU')                                    \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2230 */     SQL_LSEO = "  SELECT                                                                       \r\n    PRICE.ID AS ID,                                                            \r\n    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n    LSEO.PDHDOMAIN AS PDHDOMAIN,                                               \r\n    LSEO.COFCAT AS PRODUCTCATEGORY,                                            \r\n    LSEO.SEOTYPE AS PRODUCTOFFERINGTYPE,                                       \r\n    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n    LSEO.SEOTYPE AS PRODUCTENTITYTYPE,                                         \r\n    LSEO.ENTITYID AS PRODUCTENTITYID,                                          \r\n    '' AS PRICEPOINTENTITYTYPE,                                                \r\n    '' AS PRICEPOINTENTITYID,                                                  \r\n    LSEO.MACHTYPEATR AS MACHTYPEATR,                                           \r\n    LSEO.MODELATR AS MODELATR,                                                 \r\n    '' AS FEATURECODE,                                                         \r\n    '' AS FROM_MACHTYPEATR,                                                    \r\n    '' AS FROM_MODELATR,                                                       \r\n    '' AS FROM_FEATURECODE,                                                    \r\n    LSEO.SEOID AS PARTNUM,                                                     \r\n    LSEO.MKTGDESC AS MKTGNAME,                                                 \r\n    LSEO.PRCFILENAM AS INVNAME,                                                \r\n    PRICE.OFFERING AS OFFERING,                                                \r\n    PRICE.START_DATE AS START_DATE,                                            \r\n    PRICE.CURRENCY AS CURRENCY,                                                \r\n    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n    PRICE.CABLEID AS CABLEID,                                                  \r\n    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n    PRICE.COUNTRY AS COUNTRY,                                                  \r\n    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n    PRICE.ONSHORE AS ONSHORE,                                                  \r\n    PRICE.END_DATE AS END_DATE,                                                \r\n    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n    PRICE.FACTOR AS FACTOR                                                     \r\n  FROM PRICE.PRICE AS PRICE                                                    \r\n  JOIN PRICE.LSEOINFO AS LSEO                                                  \r\n  ON PRICE.PARTNUM=LSEO.SEOID                                                  \r\n  WHERE                                                                        \r\n    PRICE.ACTION = 'I' AND                                                     \r\n    PRICE.PRICE_POINT_TYPE in('','SEO')                                        \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2274 */     SQL_ESW = "  SELECT                                                                       \r\n    PRICE.ID AS ID,                                                            \r\n    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n    '' AS PDHDOMAIN,                                                           \r\n    PRICE.OFFERING_TYPE AS PRODUCTCATEGORY,                                    \r\n    '' AS PRODUCTOFFERINGTYPE,                                                 \r\n    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n                                                                               \r\n    '' AS PRODUCTENTITYTYPE,                                                   \r\n    '' AS PRODUCTENTITYID,                                                     \r\n    '' AS PRICEPOINTENTITYTYPE,                                                \r\n    '' AS PRICEPOINTENTITYID,                                                  \r\n                                                                               \r\n    '' AS MACHTYPEATR,                                                         \r\n    '' AS MODELATR,                                                            \r\n    '' AS FEATURECODE,                                                         \r\n    '' AS FROM_MACHTYPEATR,                                                    \r\n    '' AS FROM_MODELATR,                                                       \r\n    '' AS FROM_FEATURECODE,                                                    \r\n    '' AS PARTNUM,                                                             \r\n    '' AS MKTGNAME,                                                            \r\n    '' AS INVNAME,                                                             \r\n    PRICE.OFFERING AS OFFERING,                                                \r\n    PRICE.START_DATE AS START_DATE,                                            \r\n    PRICE.CURRENCY AS CURRENCY,                                                \r\n    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n    PRICE.CABLEID AS CABLEID,                                                  \r\n    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n    PRICE.COUNTRY AS COUNTRY,                                                  \r\n    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n    PRICE.ONSHORE AS ONSHORE,                                                  \r\n    PRICE.END_DATE AS END_DATE,                                                \r\n    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n    PRICE.FACTOR AS FACTOR                                                     \r\n  FROM PRICE.PRICE AS PRICE                                                    \r\n  WHERE                                                                        \r\n    PRICE.ACTION = 'I' AND                                                     \r\n    PRICE.OFFERING_TYPE in ('EEE','OSP') AND                                   \r\n    PRICE.PRICE_POINT_TYPE =''                                                 \r\n"; } private String getBUNDLETYPE(Database paramDatabase, EntityItem paramEntityItem, int paramInt, String paramString) throws MiddlewareRequestException, SQLException, MiddlewareException { EntityList entityList = paramDatabase.getEntityList(paramEntityItem.getProfile(), new ExtractActionItem(null, paramDatabase, paramEntityItem.getProfile(), "dummy"), new EntityItem[] { new EntityItem(null, paramEntityItem.getProfile(), paramString, paramInt) }); EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0); String str1 = ""; String str2 = "BUNDLETYPE"; EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(str2); if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) { MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get(); for (byte b = 0; b < arrayOfMetaFlag.length; b++) { if (arrayOfMetaFlag[b].isSelected())
/*      */           if (str1.equals("@@@")) { str1 = arrayOfMetaFlag[b].toString(); } else { str1 = str1 + "," + arrayOfMetaFlag[b].toString(); }   }  }  if (str1.indexOf("Hardware") > -1) { str1 = "Hardware"; } else if (str1.indexOf("Software") > -1) { str1 = "Software"; } else { str1 = "Service"; }  return str1; }
/*      */   private String convertValue(String paramString) { return (paramString == null) ? "" : paramString.trim(); }
/*      */   private void sendWWPRTXML(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile, EntityItem paramEntityItem, Hashtable paramHashtable) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException { paramADSABRSTATUS.addDebug("sendWWPRTXML found " + paramHashtable.size() + " PRICE"); Vector vector = getPeriodicMQ(paramEntityItem); if (vector == null) { paramADSABRSTATUS.addDebug("ADSPRICEABR: No MQ properties files, nothing will be generated."); paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "PRICE"); } else { Enumeration<String> enumeration = paramHashtable.keys(); int i = 0; String str = ""; Vector<String> vector1 = null; StringBuffer stringBuffer = null; int j = 0; int k = 0; while (enumeration.hasMoreElements()) { PRICE_MESSAGE_COUNT++; str = enumeration.nextElement(); vector1 = (Vector)paramHashtable.get(str); stringBuffer = new StringBuffer(); createWWPRTHead(str, stringBuffer); j = 0; k = 0; for (byte b = 0; b < vector1.size(); b++) { PRICE_RECORD_COUNT++; stringBuffer.append(vector1.elementAt(b)); j++; k++; if (j == PRICE_MQ_LIMIT && k != vector1.size()) { sendPriceWWPRTMessage(paramADSABRSTATUS, vector, stringBuffer); stringBuffer = new StringBuffer(); createWWPRTHead(str, stringBuffer); j = 0; PRICE_MESSAGE_COUNT++; i++; }  }  sendPriceWWPRTMessage(paramADSABRSTATUS, vector, stringBuffer); }  paramHashtable.clear(); }  }
/*      */   private void createWWPRTHead(String paramString, StringBuffer paramStringBuffer) { paramStringBuffer.append("<wwprttxn xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" id=\""); paramStringBuffer.append(paramString); paramStringBuffer.append("\" type=\"price\" xsi:noNamespaceSchemaLocation=\"price.xsd\">"); }
/*      */   private void sendPriceWWPRTMessage(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, StringBuffer paramStringBuffer) throws ParserConfigurationException, TransformerException, MissingResourceException { long l = System.currentTimeMillis(); String str1 = paramStringBuffer.toString() + "</wwprttxn>"; String str2 = ""; if (str1.indexOf("offeringtype") != -1) { str2 = "XMLPRODPRICESETUP_W2"; } else { str2 = "XMLPRODPRICESETUP_W1"; }  boolean bool = false; String str3 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str2 + "_XSDNEEDED", "NO"); if ("YES".equals(str3.toUpperCase())) { String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str2 + "_XSDFILE", "NONE"); if ("NONE".equals(str)) { paramADSABRSTATUS.addError("there is no xsdfile for " + str2 + " defined in the propertyfile "); } else { long l1 = System.currentTimeMillis(); Class<?> clazz = getClass(); StringBuffer stringBuffer = new StringBuffer(); bool = ABRUtil.validatexml(clazz, stringBuffer, str, str1); if (stringBuffer.length() > 0) { String str4 = stringBuffer.toString(); if (str4.indexOf("fail") != -1)
/*      */             paramADSABRSTATUS.addError(str4);  paramADSABRSTATUS.addOutput(str4); }  long l2 = System.currentTimeMillis(); paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l2 - l1)); if (bool)
/*      */           paramADSABRSTATUS.addDebug("the xml for " + str2 + " passed the validation");  }  } else { paramADSABRSTATUS.addOutput("the xml for " + str2 + " doesn't need to be validated"); bool = true; }  if (str1 != null && bool) { if (!ADSABRSTATUS.USERXML_OFF_LOG)
/*      */         paramADSABRSTATUS.addDebug("ADSPRICEABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str1 + ADSABRSTATUS.NEWLINE);  paramADSABRSTATUS.notify(this, "PRICE", str1, paramVector); long l1 = System.currentTimeMillis(); if (!ADSABRSTATUS.USERXML_OFF_LOG)
/*      */         paramADSABRSTATUS.addDebug("Sending one wwprt message takes time:" + (l1 - l) + " ms");  this.ALL_WWPRT_SEND_TIME += l1 - l; }  }
/*      */   private void sendPriceMessage(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector, Document paramDocument) throws ParserConfigurationException, TransformerException, MissingResourceException { String str1 = paramADSABRSTATUS.transformXML(this, paramDocument); paramADSABRSTATUS.addDebug("xml:" + str1); boolean bool = false; String str2 = "XMLPRODPRICESETUP_E"; String str3 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str2 + "_XSDNEEDED", "NO"); if ("YES".equals(str3.toUpperCase())) { String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str2 + "_XSDFILE", "NONE"); if ("NONE".equals(str)) { paramADSABRSTATUS.addError("there is no xsdfile for " + str2 + " defined in the propertyfile "); } else { long l1 = System.currentTimeMillis(); Class<?> clazz = getClass(); StringBuffer stringBuffer = new StringBuffer(); bool = ABRUtil.validatexml(clazz, stringBuffer, str, str1); if (stringBuffer.length() > 0) { String str4 = stringBuffer.toString(); if (str4.indexOf("fail") != -1)
/*      */             paramADSABRSTATUS.addError(str4);  paramADSABRSTATUS.addOutput(str4); }  long l2 = System.currentTimeMillis(); paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l2 - l1)); if (bool)
/*      */           paramADSABRSTATUS.addDebug("the xml for " + str2 + " passed the validation");  }  } else { paramADSABRSTATUS.addOutput("the xml for " + str2 + " doesn't need to be validated"); bool = true; }  if (str1 != null && bool) { if (!ADSABRSTATUS.USERXML_OFF_LOG)
/*      */         paramADSABRSTATUS.addDebug("ADSPRICEABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str1 + ADSABRSTATUS.NEWLINE);  paramADSABRSTATUS.addDebug("xml:" + str1); paramADSABRSTATUS.notify(this, "PRICE", str1, paramVector); }  }
/*      */   private void sendEACMXML(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile, EntityItem paramEntityItem, Hashtable paramHashtable) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException { paramADSABRSTATUS.addDebug("sendEACMXML found " + paramHashtable.size() + " PRICE"); Vector vector = getPeriodicMQ(paramEntityItem); if (vector == null) { paramADSABRSTATUS.addDebug("ADSPRICEABR: No MQ properties files, nothing will be generated."); paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "PRICE"); } else { DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); Enumeration<String> enumeration = paramHashtable.keys(); int i = 0; String str = ""; Vector<PriceInfo> vector1 = null; PriceInfo priceInfo = null; Document document = null; Element element1 = null; int j = 0; int k = 0; Element element2 = null; while (enumeration.hasMoreElements()) { PRICE_MESSAGE_COUNT++; str = enumeration.nextElement(); vector1 = (Vector)paramHashtable.get(str); priceInfo = vector1.get(0); document = documentBuilder.newDocument(); element1 = createEACMParent(paramProfile, priceInfo, document); j = 0; k = 0; for (byte b = 0; b < vector1.size(); b++) { PRICE_RECORD_COUNT++; priceInfo = vector1.elementAt(b); element2 = document.createElement("PRODUCTELEMENT"); element1.appendChild(element2); Element element3 = document.createElement("PRODUCTENTITYTYPE"); element3.appendChild(document.createTextNode(priceInfo.PRODUCTENTITYTYPE)); element2.appendChild(element3); element3 = document.createElement("PRODUCTENTITYID"); element3.appendChild(document.createTextNode(priceInfo.PRODUCTENTITYID)); element2.appendChild(element3); element3 = document.createElement("PRICEPOINTENTITYTYPE"); element3.appendChild(document.createTextNode(priceInfo.PRICEPOINTENTITYTYPE)); element2.appendChild(element3); element3 = document.createElement("PRICEPOINTENTITYID"); element3.appendChild(document.createTextNode(priceInfo.PRICEPOINTENTITYID)); element2.appendChild(element3); element3 = document.createElement("MACHTYPE"); element3.appendChild(document.createTextNode(priceInfo.MACHTYPE)); element2.appendChild(element3); element3 = document.createElement("MODEL"); element3.appendChild(document.createTextNode(priceInfo.MODEL)); element2.appendChild(element3); element3 = document.createElement("FEATURECODE"); element3.appendChild(document.createTextNode(priceInfo.FEATURECODE)); element2.appendChild(element3); element3 = document.createElement("FROMMACHTYPE"); element3.appendChild(document.createTextNode(priceInfo.FROMMACHTYPE)); element2.appendChild(element3); element3 = document.createElement("FROMMODEL"); element3.appendChild(document.createTextNode(priceInfo.FROMMODEL)); element2.appendChild(element3); element3 = document.createElement("FROMFEATURECODE"); element3.appendChild(document.createTextNode(priceInfo.FROMFEATURECODE)); element2.appendChild(element3); element3 = document.createElement("SEOID"); element3.appendChild(document.createTextNode(priceInfo.SEOID)); element2.appendChild(element3); element3 = document.createElement("MKTGNAME"); element3.appendChild(document.createTextNode(priceInfo.MKTGNAME)); element2.appendChild(element3); element3 = document.createElement("INVNAME"); element3.appendChild(document.createTextNode(priceInfo.INVNAME)); element2.appendChild(element3); Element element4 = document.createElement("PRICELIST"); element2.appendChild(element4); printPriceInfo(priceInfo, document, element4); j++; k++; if (j == PRICE_MQ_LIMIT && k != vector1.size()) { sendPriceMessage(paramADSABRSTATUS, vector, document); document = documentBuilder.newDocument(); element1 = createEACMParent(paramProfile, priceInfo, document); j = 0; PRICE_MESSAGE_COUNT++; i++; }  }  priceInfo.dereference(); sendPriceMessage(paramADSABRSTATUS, vector, document); }  paramHashtable.clear(); }  }
/*      */   private Element createEACMParent(Profile paramProfile, PriceInfo paramPriceInfo, Document paramDocument) throws DOMException { Element element1 = paramDocument.createElement("PRODUCT_PRICE_UPDATE"); element1.setAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/PRODUCT_PRICE_UPDATE"); element1.appendChild(paramDocument.createComment("PRODUCT_PRICE_UPDATE Version 1 Mod 0")); paramDocument.appendChild(element1); Element element2 = paramDocument.createElement("DTSOFMSG"); element2.appendChild(paramDocument.createTextNode(paramProfile.getEndOfDay())); element1.appendChild(element2); element2 = paramDocument.createElement("ACTIVITY"); element2.appendChild(paramDocument.createTextNode(paramPriceInfo.ACTIVITY)); element1.appendChild(element2); element2 = paramDocument.createElement("PDHDOMAIN"); element2.appendChild(paramDocument.createTextNode(paramPriceInfo.PDHDOMAIN)); element1.appendChild(element2); element2 = paramDocument.createElement("PRODUCTCATEGORY"); element2.appendChild(paramDocument.createTextNode(paramPriceInfo.PRODUCTCATEGORY)); element1.appendChild(element2); element2 = paramDocument.createElement("PRODUCTOFFERINGTYPE"); element2.appendChild(paramDocument.createTextNode(paramPriceInfo.PRODUCTOFFERINGTYPE)); element1.appendChild(element2); Element element3 = paramDocument.createElement("PRODUCTLIST"); element1.appendChild(element3); return element3; }
/*      */   private void printPriceInfo(PriceInfo paramPriceInfo, Document paramDocument, Element paramElement) throws DOMException { Element element2 = paramDocument.createElement("price"); paramElement.appendChild(element2); Element element1 = paramDocument.createElement("offering"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.OFFERING)); element2.appendChild(element1); element1 = paramDocument.createElement("startdate"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.STARTDATE)); element2.appendChild(element1); element1 = paramDocument.createElement("currency"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.CURRENCY)); element2.appendChild(element1); element1 = paramDocument.createElement("cabletype"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.CABLETYPE)); element2.appendChild(element1); element1 = paramDocument.createElement("cableid"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.CABLEID)); element2.appendChild(element1); element1 = paramDocument.createElement("releasets"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.RELEASETS)); element2.appendChild(element1); element1 = paramDocument.createElement("pricevalue"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.PRICEVALUE)); element2.appendChild(element1); element1 = paramDocument.createElement("pricepointtype"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.PRICEPOINTTYPE)); element2.appendChild(element1); element1 = paramDocument.createElement("pricepointvalue"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.PRICEPOINTVALUE)); element2.appendChild(element1); element1 = paramDocument.createElement("country"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.COUNTRY)); element2.appendChild(element1); element1 = paramDocument.createElement("pricetype"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.PRICETYPE)); element2.appendChild(element1); element1 = paramDocument.createElement("onshore"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.ONSHORE)); element2.appendChild(element1); element1 = paramDocument.createElement("enddate"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.ENDDATE)); element2.appendChild(element1); element1 = paramDocument.createElement("pricevalueusd"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.PRICEVALUEUSD)); element2.appendChild(element1); element1 = paramDocument.createElement("factor"); element1.appendChild(paramDocument.createTextNode(paramPriceInfo.FACTOR)); element2.appendChild(element1); }
/*      */   private String getDescription(EntityItem paramEntityItem, String paramString1, String paramString2) { String str = ""; EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1); if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) { MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get(); StringBuffer stringBuffer = new StringBuffer(); for (byte b = 0; b < arrayOfMetaFlag.length; b++) { if (arrayOfMetaFlag[b].isSelected()) { if (stringBuffer.length() > 0)
/*      */             stringBuffer.append(",");  if (paramString2.equals("short")) { stringBuffer.append(arrayOfMetaFlag[b].getShortDescription()); } else if (paramString2.equals("long")) { stringBuffer.append(arrayOfMetaFlag[b].getLongDescription()); } else if (paramString2.equals("flag")) { stringBuffer.append(arrayOfMetaFlag[b].getFlagCode()); } else { stringBuffer.append(arrayOfMetaFlag[b].toString()); }  }  }
/*      */        str = stringBuffer.toString(); }
/*      */      return str; }
/*      */   private String getMultiDescription(EntityItem paramEntityItem, String paramString1, String paramString2) { String str = ""; EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1); if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) { MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get(); StringBuffer stringBuffer = new StringBuffer(); for (byte b = 0; b < arrayOfMetaFlag.length; b++) { if (arrayOfMetaFlag[b].isSelected()) { if (stringBuffer.length() > 0)
/*      */             stringBuffer.append(",");  if (paramString2.equals("short")) { stringBuffer.append("'").append(arrayOfMetaFlag[b].getShortDescription()).append("'"); }
/*      */           else if (paramString2.equals("long")) { stringBuffer.append("'").append(arrayOfMetaFlag[b].getLongDescription()).append("'"); }
/*      */           else if (paramString2.equals("flag")) { stringBuffer.append("'").append(arrayOfMetaFlag[b].getFlagCode()).append("'"); }
/*      */           else { stringBuffer.append("'").append(arrayOfMetaFlag[b].toString()).append("'"); }
/*      */            }
/*      */          }
/*      */        str = stringBuffer.toString(); }
/*      */      return str; }
/*      */   private Hashtable getDescriptionTable(EntityItem paramEntityItem, String paramString1, String paramString2) { Hashtable<Object, Object> hashtable = new Hashtable<>(); EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1); if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) { MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get(); for (byte b = 0; b < arrayOfMetaFlag.length; b++) { if (arrayOfMetaFlag[b].isSelected())
/*      */           if (paramString2.equals("short")) { hashtable.put(arrayOfMetaFlag[b].getShortDescription(), arrayOfMetaFlag[b].getShortDescription()); }
/*      */           else if (paramString2.equals("long")) { hashtable.put(arrayOfMetaFlag[b].getLongDescription(), arrayOfMetaFlag[b].getLongDescription()); }
/*      */           else if (paramString2.equals("flag")) { hashtable.put(arrayOfMetaFlag[b].getFlagCode(), arrayOfMetaFlag[b].getFlagCode()); }
/*      */           else { hashtable.put(arrayOfMetaFlag[b].toString(), arrayOfMetaFlag[b].toString()); }
/*      */             }
/*      */        }
/*      */      return hashtable; }
/*      */   private Hashtable getDIVTEXTTable(String paramString) { Hashtable<Object, Object> hashtable = new Hashtable<>(); if (!"".equals(paramString)) { StringTokenizer stringTokenizer = new StringTokenizer(paramString, ","); String str = ""; while (stringTokenizer.hasMoreTokens()) { str = stringTokenizer.nextToken(); if ("".equals(paramString))
/*      */           return new Hashtable<>();  hashtable.put(str, str); }
/*      */        }
/*      */      return hashtable; }
/*      */   public String getMQCID() { return this.MQCID; }
/*      */   public String getVersion() { return "1.2"; }
/* 2318 */   private static class PriceInfo { String PRODUCTOFFERINGTYPE = "@@";
/* 2319 */     String MACHTYPE = "@@";
/* 2320 */     String MODEL = "@@";
/* 2321 */     String FEATURECODE = "@@";
/* 2322 */     String FROMMACHTYPE = "@@";
/*      */     
/* 2324 */     String FROMMODEL = "@@";
/* 2325 */     String FROMFEATURECODE = "@@";
/* 2326 */     String SEOID = "@@";
/* 2327 */     String OFFERING = "@@";
/* 2328 */     String STARTDATE = "@@";
/*      */     
/* 2330 */     String CURRENCY = "@@";
/* 2331 */     String CABLETYPE = "@@";
/* 2332 */     String CABLEID = "@@";
/* 2333 */     String RELEASETS = "@@";
/* 2334 */     String PRICEVALUE = "@@";
/*      */     
/* 2336 */     String PRICEPOINTTYPE = "@@";
/* 2337 */     String PRICEPOINTVALUE = "@@";
/* 2338 */     String COUNTRY = "@@";
/* 2339 */     String PRICETYPE = "@@";
/* 2340 */     String ONSHORE = "@@";
/*      */     
/* 2342 */     String ENDDATE = "@@";
/* 2343 */     String PRICEVALUEUSD = "@@";
/* 2344 */     String FACTOR = "@@";
/* 2345 */     String ACTIVITY = "@@";
/* 2346 */     String PDHDOMAIN = "@@";
/*      */     
/* 2348 */     String PRODUCTCATEGORY = "@@";
/* 2349 */     String PRODUCTENTITYTYPE = "@@";
/* 2350 */     String PRODUCTENTITYID = "@@";
/* 2351 */     String PRICEPOINTENTITYTYPE = "@@";
/* 2352 */     String PRICEPOINTENTITYID = "@@";
/*      */     
/* 2354 */     String MKTGNAME = "@@";
/* 2355 */     String INVNAME = "@@";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     PriceInfo(String param1String1, String param1String2, String param1String3, String param1String4, String param1String5, String param1String6, String param1String7, String param1String8, String param1String9, String param1String10, String param1String11, String param1String12, String param1String13, String param1String14, String param1String15, String param1String16, String param1String17, String param1String18, String param1String19, String param1String20, String param1String21, String param1String22, String param1String23, String param1String24, String param1String25, String param1String26, String param1String27, String param1String28, String param1String29, String param1String30, String param1String31, String param1String32, Vector param1Vector) {
/* 2366 */       if (param1String1 != null) {
/* 2367 */         this.PRODUCTOFFERINGTYPE = param1String1.trim();
/*      */       }
/* 2369 */       if (param1String2 != null) {
/* 2370 */         this.MACHTYPE = param1String2.trim();
/*      */       }
/* 2372 */       if (param1String3 != null) {
/* 2373 */         this.MODEL = param1String3.trim();
/*      */       }
/* 2375 */       if (param1String4 != null) {
/* 2376 */         this.FEATURECODE = param1String4.trim();
/*      */       }
/* 2378 */       if (param1String5 != null) {
/* 2379 */         this.FROMMACHTYPE = param1String5.trim();
/*      */       }
/*      */       
/* 2382 */       if (param1String6 != null) {
/* 2383 */         this.FROMMODEL = param1String6.trim();
/*      */       }
/* 2385 */       if (param1String7 != null) {
/* 2386 */         this.FROMFEATURECODE = param1String7.trim();
/*      */       }
/* 2388 */       if (param1String8 != null) {
/* 2389 */         this.SEOID = param1String8.trim();
/*      */       }
/* 2391 */       if (param1String9 != null) {
/* 2392 */         this.OFFERING = param1String9.trim();
/*      */       }
/* 2394 */       if (param1String10 != null) {
/* 2395 */         this.STARTDATE = param1String10.trim();
/*      */       }
/*      */       
/* 2398 */       if (param1String11 != null) {
/* 2399 */         this.CURRENCY = param1String11.trim();
/*      */       }
/* 2401 */       if (param1String12 != null) {
/* 2402 */         this.CABLETYPE = param1String12.trim();
/*      */       }
/* 2404 */       if (param1String13 != null) {
/* 2405 */         this.CABLEID = param1String13.trim();
/*      */       }
/* 2407 */       if (param1String14 != null) {
/* 2408 */         this.RELEASETS = param1String14.trim();
/*      */       }
/* 2410 */       if (param1String15 != null) {
/* 2411 */         this.PRICEVALUE = param1String15.trim();
/*      */       }
/*      */       
/* 2414 */       if (param1String16 != null) {
/* 2415 */         this.PRICEPOINTTYPE = param1String16.trim();
/*      */       }
/* 2417 */       if (param1String17 != null) {
/* 2418 */         this.PRICEPOINTVALUE = param1String17.trim();
/*      */       }
/* 2420 */       if (param1String18 != null) {
/* 2421 */         this.COUNTRY = param1String18.trim();
/*      */       }
/* 2423 */       if (param1String19 != null) {
/* 2424 */         this.PRICETYPE = param1String19.trim();
/*      */       }
/* 2426 */       if (param1String20 != null) {
/* 2427 */         this.ONSHORE = param1String20.trim();
/*      */       }
/*      */       
/* 2430 */       if (param1String21 != null) {
/* 2431 */         this.ENDDATE = param1String21.trim();
/*      */       }
/* 2433 */       if (param1String22 != null) {
/* 2434 */         this.PRICEVALUEUSD = param1String22.trim();
/*      */       }
/* 2436 */       if (param1String23 != null) {
/* 2437 */         this.FACTOR = param1String23.trim();
/*      */       }
/* 2439 */       if (param1String24 != null) {
/* 2440 */         this.ACTIVITY = param1String24.trim();
/*      */       }
/* 2442 */       if (param1String25 != null) {
/* 2443 */         this.PDHDOMAIN = param1String25.trim();
/*      */       }
/*      */       
/* 2446 */       if (param1String26 != null) {
/* 2447 */         this.PRODUCTCATEGORY = param1String26.trim();
/*      */       }
/* 2449 */       if (param1String27 != null) {
/* 2450 */         this.PRODUCTENTITYTYPE = param1String27.trim();
/*      */       }
/* 2452 */       if (param1String28 != null) {
/* 2453 */         this.PRODUCTENTITYID = param1String28.trim();
/*      */       }
/* 2455 */       if (param1String29 != null) {
/* 2456 */         this.PRICEPOINTENTITYTYPE = param1String29.trim();
/*      */       }
/* 2458 */       if (param1String30 != null) {
/* 2459 */         this.PRICEPOINTENTITYID = param1String30.trim();
/*      */       }
/*      */       
/* 2462 */       if (param1String31 != null) {
/* 2463 */         this.MKTGNAME = param1String31.trim();
/*      */       }
/* 2465 */       if (param1String32 != null) {
/* 2466 */         this.INVNAME = param1String32.trim();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void dereference() {
/* 2472 */       this.PRODUCTOFFERINGTYPE = null;
/* 2473 */       this.MACHTYPE = null;
/* 2474 */       this.MODEL = null;
/* 2475 */       this.FEATURECODE = null;
/* 2476 */       this.FROMMACHTYPE = null;
/*      */       
/* 2478 */       this.FROMMODEL = null;
/* 2479 */       this.FROMFEATURECODE = null;
/* 2480 */       this.SEOID = null;
/* 2481 */       this.OFFERING = null;
/* 2482 */       this.STARTDATE = null;
/*      */       
/* 2484 */       this.CURRENCY = null;
/* 2485 */       this.CABLETYPE = null;
/* 2486 */       this.CABLEID = null;
/* 2487 */       this.RELEASETS = null;
/* 2488 */       this.PRICEVALUE = null;
/*      */       
/* 2490 */       this.PRICEPOINTTYPE = null;
/* 2491 */       this.PRICEPOINTVALUE = null;
/* 2492 */       this.COUNTRY = null;
/* 2493 */       this.PRICETYPE = null;
/* 2494 */       this.ONSHORE = null;
/*      */       
/* 2496 */       this.ENDDATE = null;
/* 2497 */       this.PRICEVALUEUSD = null;
/* 2498 */       this.FACTOR = null;
/* 2499 */       this.ACTIVITY = null;
/* 2500 */       this.PDHDOMAIN = null;
/*      */       
/* 2502 */       this.PRODUCTCATEGORY = null;
/* 2503 */       this.PRODUCTENTITYTYPE = null;
/* 2504 */       this.PRODUCTENTITYID = null;
/* 2505 */       this.PRICEPOINTENTITYTYPE = null;
/* 2506 */       this.PRICEPOINTENTITYID = null;
/*      */       
/* 2508 */       this.MKTGNAME = null;
/* 2509 */       this.INVNAME = null;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCond(String paramString) {
/* 2516 */     return (paramString != null && paramString.trim().length() > 0);
/*      */   }
/*      */   
/*      */   public boolean maxLimited() {
/* 2520 */     return (this.max > -1);
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSPRICEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */