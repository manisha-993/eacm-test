/*      */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
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
/*      */ import java.util.Iterator;
/*      */ import java.util.MissingResourceException;
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
/*      */ public class ADSWWCOMPATXMLABR
/*      */   extends XMLMQAdapter
/*      */ {
/*      */   private static final int MW_ENTITY_LIMIT;
/*      */   private static final int WWCOMPAT_ROW_LIMIT;
/*  110 */   protected static int WWCOMPAT_MESSAGE_COUNT = 0;
/*  111 */   Connection connection = null;
/*      */   static {
/*  113 */     String str1 = ABRServerProperties.getValue("ADSABRSTATUS", "_entitylimit", "3000");
/*      */     
/*  115 */     MW_ENTITY_LIMIT = Integer.parseInt(str1);
/*  116 */     String str2 = ABRServerProperties.getValue("ADSABRSTATUS", "_XMLGENCOUNT", "500000");
/*      */     
/*  118 */     WWCOMPAT_ROW_LIMIT = Integer.parseInt(str2);
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
/*      */   private static final String COMPAT_SQL = "select activity,'LSEO',left_lseo.entityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseo.seoid,right_lseo.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 and left_wwseolseo.isactive=1 join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid and left_modelwwseo.isactive=1 join price.model left_model on left_model.entityid=left_modelwwseo.id1 and left_model.isactive=1 where systementitytype='WWSEO' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'MODEL',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),cast(null as char),left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 where systementitytype='MODEL' and optionentitytype='MODEL' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),right_lseo.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 where systementitytype='MODEL' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),right_lseobundle.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 where systementitytype='MODEL' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEO',left_lseo.entityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseo.seoid,right_lseobundle.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 and left_wwseolseo.isactive=1 join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid and left_modelwwseo.isactive=1 join price.model left_model on left_model.entityid=left_modelwwseo.id1 and left_model.isactive=1 where systementitytype='WWSEO' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEOBUNDLE',systementityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseobundle.seoid,right_lseo.seoid,cast(null as char),cast(null as char) from gbli.wwtechcompat wwtc join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid and left_lseobundle.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 where systementitytype='LSEOBUNDLE' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEOBUNDLE',systementityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseobundle.seoid,right_lseobundle.seoid,cast(null as char),cast(null as char) from gbli.wwtechcompat wwtc join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid and left_lseobundle.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 where systementitytype='LSEOBUNDLE' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' with ur";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String WITHDRAWUPDATE_SQL = "update gbli.wwtechcompat set activity = 'W' where SYSTEMENTITYTYPE = ? and SYSTEMENTITYID = ? and SYSTEMOS = ? and GROUPENTITYTYPE = ? and GROUPENTITYID = ? and OSENTITYTYPE = ? and OSENTITYID = ? and OS = ? and OPTIONENTITYTYPE = ? and OPTIONENTITYID = ?";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String WITHDRAW_SQL = "select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 and right_lseo.nlsid=1 and right_lseo.lseounpubdatemtrgt + 90 day < current date where optionentitytype='WWSEO' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.model right_model on right_model.entityid=wwtc.optionentityid and right_model.isactive=1 and right_model.nlsid=1 and right_model.wthdrweffctvdate + 90 day < current date where optionentitytype='MODEL' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.lseobundle right_lseobundle on right_lseobundle.entityid=wwtc.optionentityid and right_lseobundle.isactive=1 and right_lseobundle.nlsid=1 and right_lseobundle.bundlunpubdatemtrgt + 90 day < current date where optionentitytype='LSEOBUNDLE' and activity in('A','C') and updated BETWEEN ? AND ?  ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  401 */     String str1 = paramProfile1.getValOn();
/*  402 */     String str2 = paramProfile2.getValOn();
/*      */     
/*  404 */     paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR.processThis checking between " + str1 + " and " + str2);
/*      */     
/*  406 */     WWCOMPAT_MESSAGE_COUNT = 0;
/*  407 */     paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR.withdraw offering set activity ='W' between " + str1 + " and " + str2);
/*  408 */     withdrawOffering(paramADSABRSTATUS, str1, str2);
/*  409 */     processCompat(paramADSABRSTATUS, str1, str2, paramProfile2, paramADSABRSTATUS.getDatabase(), paramEntityItem);
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
/*      */   private void processMQ(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile, Vector<CompatInfo> paramVector1, Vector paramVector2) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
/*  463 */     if (paramVector2 == null) {
/*  464 */       paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR: No MQ properties files, nothing will be generated.");
/*      */       
/*  466 */       paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "ADSWWCOMPATXMLABR");
/*      */     
/*      */     }
/*  469 */     else if (paramVector1.size() > MW_ENTITY_LIMIT) {
/*  470 */       int i = 0;
/*  471 */       byte b1 = 0;
/*  472 */       Vector<CompatInfo> vector = new Vector();
/*  473 */       if (paramVector1.size() % MW_ENTITY_LIMIT != 0) {
/*  474 */         i = paramVector1.size() / MW_ENTITY_LIMIT + 1;
/*      */       } else {
/*  476 */         i = paramVector1.size() / MW_ENTITY_LIMIT;
/*      */       } 
/*  478 */       WWCOMPAT_MESSAGE_COUNT += i;
/*  479 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  480 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  481 */       for (byte b2 = 0; b2 < i; b2++) {
/*  482 */         for (byte b3 = 0; b3 < MW_ENTITY_LIMIT && 
/*  483 */           b1 != paramVector1.size(); b3++)
/*      */         {
/*      */           
/*  486 */           vector.add(paramVector1.elementAt(b1++));
/*      */         }
/*  488 */         Document document = documentBuilder.newDocument();
/*  489 */         String str1 = "WWCOMPAT_UPDATE";
/*  490 */         String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */         
/*  493 */         Element element1 = document.createElementNS(str2, str1);
/*      */         
/*  495 */         document.appendChild(element1);
/*  496 */         element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*  497 */         element1.appendChild(document.createComment("WWCOMPAT_UPDATE Version 1 Mod 0"));
/*      */         
/*  499 */         Element element2 = document.createElement("DTSOFMSG");
/*  500 */         element2.appendChild(document.createTextNode(paramProfile.getEndOfDay()));
/*  501 */         element1.appendChild(element2);
/*  502 */         for (byte b4 = 0; b4 < vector.size(); b4++) {
/*  503 */           CompatInfo compatInfo = vector.elementAt(b4);
/*  504 */           Element element = document.createElement("COMPATELEMENT");
/*  505 */           element1.appendChild(element);
/*      */           
/*  507 */           element2 = document.createElement("ACTIVITY");
/*  508 */           element2.appendChild(document.createTextNode(compatInfo.activity_xml));
/*  509 */           element.appendChild(element2);
/*      */ 
/*      */           
/*  512 */           element2 = document.createElement("BRANDCD");
/*  513 */           element2.appendChild(document.createTextNode(compatInfo.brandcd_fc_xml));
/*  514 */           element.appendChild(element2);
/*      */ 
/*      */           
/*  517 */           element2 = document.createElement("STATUS");
/*  518 */           element2.appendChild(document.createTextNode("0020"));
/*  519 */           element.appendChild(element2);
/*      */           
/*  521 */           element2 = document.createElement("SYSTEMENTITYTYPE");
/*  522 */           element2.appendChild(document.createTextNode(compatInfo.systementitytype_xml));
/*  523 */           element.appendChild(element2);
/*      */           
/*  525 */           element2 = document.createElement("SYSTEMENTITYID");
/*  526 */           element2.appendChild(document.createTextNode(compatInfo.systementityid_xml));
/*  527 */           element.appendChild(element2);
/*      */           
/*  529 */           element2 = document.createElement("SYSTEMMACHTYPE");
/*  530 */           element2.appendChild(document.createTextNode(compatInfo.systemmachtype_xml));
/*  531 */           element.appendChild(element2);
/*      */           
/*  533 */           element2 = document.createElement("SYSTEMMODEL");
/*  534 */           element2.appendChild(document.createTextNode(compatInfo.systemmodel_xml));
/*  535 */           element.appendChild(element2);
/*      */           
/*  537 */           element2 = document.createElement("SYSTEMSEOID");
/*  538 */           element2.appendChild(document.createTextNode(compatInfo.systemseoid_xml));
/*  539 */           element.appendChild(element2);
/*      */           
/*  541 */           element2 = document.createElement("GROUPENTITYTYPE");
/*  542 */           element2.appendChild(document.createTextNode(compatInfo.groupentitytype_xml));
/*  543 */           element.appendChild(element2);
/*      */           
/*  545 */           element2 = document.createElement("GROUPENTITYID");
/*  546 */           element2.appendChild(document.createTextNode(compatInfo.groupentityid_xml));
/*  547 */           element.appendChild(element2);
/*      */           
/*  549 */           element2 = document.createElement("OKTOPUB");
/*  550 */           element2.appendChild(document.createTextNode(compatInfo.oktopub_xml));
/*  551 */           element.appendChild(element2);
/*      */           
/*  553 */           element2 = document.createElement("OPTIONENTITYTYPE");
/*  554 */           element2.appendChild(document.createTextNode(compatInfo.optionentitytype_xml));
/*  555 */           element.appendChild(element2);
/*      */           
/*  557 */           element2 = document.createElement("OPTIONENTITYID");
/*  558 */           element2.appendChild(document.createTextNode(compatInfo.optionentityid_xml));
/*  559 */           element.appendChild(element2);
/*      */           
/*  561 */           element2 = document.createElement("OPTIONSEOID");
/*  562 */           element2.appendChild(document.createTextNode(compatInfo.optionseoid_xml));
/*  563 */           element.appendChild(element2);
/*      */           
/*  565 */           element2 = document.createElement("COMPATPUBFLAG");
/*  566 */           element2.appendChild(document.createTextNode(compatInfo.compatibilitypublishingflag_xml));
/*  567 */           element.appendChild(element2);
/*      */           
/*  569 */           element2 = document.createElement("RELTYPE");
/*  570 */           element2.appendChild(document.createTextNode(compatInfo.relationshiptype_xml));
/*  571 */           element.appendChild(element2);
/*      */           
/*  573 */           element2 = document.createElement("PUBFROM");
/*  574 */           element2.appendChild(document.createTextNode(compatInfo.publishfrom_xml));
/*  575 */           element.appendChild(element2);
/*      */           
/*  577 */           element2 = document.createElement("PUBTO");
/*  578 */           element2.appendChild(document.createTextNode(compatInfo.publishto_xml));
/*  579 */           element.appendChild(element2);
/*      */ 
/*      */           
/*  582 */           compatInfo.dereference();
/*      */         } 
/*  584 */         vector.clear();
/*  585 */         String str3 = paramADSABRSTATUS.transformXML(this, document);
/*      */ 
/*      */         
/*  588 */         boolean bool = false;
/*      */         
/*  590 */         String str4 = "XMLCOMPATSETUP";
/*  591 */         String str5 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDNEEDED", "NO");
/*  592 */         if ("YES".equals(str5.toUpperCase())) {
/*  593 */           String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDFILE", "NONE");
/*  594 */           if ("NONE".equals(str)) {
/*  595 */             paramADSABRSTATUS.addError("there is no xsdfile for " + str4 + " defined in the propertyfile ");
/*      */           } else {
/*  597 */             long l1 = System.currentTimeMillis();
/*  598 */             Class<?> clazz = getClass();
/*  599 */             StringBuffer stringBuffer = new StringBuffer();
/*  600 */             bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  601 */             if (stringBuffer.length() > 0) {
/*  602 */               String str6 = stringBuffer.toString();
/*  603 */               if (str6.indexOf("fail") != -1)
/*  604 */                 paramADSABRSTATUS.addError(str6); 
/*  605 */               paramADSABRSTATUS.addOutput(str6);
/*      */             } 
/*  607 */             long l2 = System.currentTimeMillis();
/*  608 */             paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/*  609 */             if (bool) {
/*  610 */               paramADSABRSTATUS.addDebug("the xml for " + str4 + " passed the validation");
/*      */             }
/*      */           } 
/*      */         } else {
/*  614 */           paramADSABRSTATUS.addOutput("the xml for " + str4 + " doesn't need to be validated");
/*  615 */           bool = true;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  621 */         if (str3 != null && bool) {
/*  622 */           if (!ADSABRSTATUS.USERXML_OFF_LOG)
/*      */           {
/*      */             
/*  625 */             paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str3 + ADSABRSTATUS.NEWLINE);
/*      */           }
/*  627 */           paramADSABRSTATUS.notify(this, "WWCOMPAT", str3, paramVector2);
/*      */         } 
/*  629 */         document = null;
/*  630 */         System.gc();
/*      */       } 
/*      */     } else {
/*      */       
/*  634 */       WWCOMPAT_MESSAGE_COUNT++;
/*  635 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  636 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  637 */       Document document = documentBuilder.newDocument();
/*  638 */       String str1 = "WWCOMPAT_UPDATE";
/*  639 */       String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */       
/*  642 */       Element element1 = document.createElementNS(str2, str1);
/*      */       
/*  644 */       document.appendChild(element1);
/*  645 */       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*  646 */       element1.appendChild(document.createComment("WWCOMPAT_UPDATE Version 1 Mod 0"));
/*      */       
/*  648 */       Element element2 = document.createElement("DTSOFMSG");
/*  649 */       element2.appendChild(document.createTextNode(paramProfile.getEndOfDay()));
/*  650 */       element1.appendChild(element2);
/*  651 */       for (byte b = 0; b < paramVector1.size(); b++) {
/*  652 */         CompatInfo compatInfo = paramVector1.elementAt(b);
/*  653 */         Element element = document.createElement("COMPATELEMENT");
/*  654 */         element1.appendChild(element);
/*      */         
/*  656 */         element2 = document.createElement("ACTIVITY");
/*  657 */         element2.appendChild(document.createTextNode(compatInfo.activity_xml));
/*  658 */         element.appendChild(element2);
/*      */ 
/*      */         
/*  661 */         element2 = document.createElement("BRANDCD");
/*  662 */         element2.appendChild(document.createTextNode(compatInfo.brandcd_fc_xml));
/*  663 */         element.appendChild(element2);
/*      */ 
/*      */         
/*  666 */         element2 = document.createElement("STATUS");
/*  667 */         element2.appendChild(document.createTextNode("0020"));
/*  668 */         element.appendChild(element2);
/*      */         
/*  670 */         element2 = document.createElement("SYSTEMENTITYTYPE");
/*  671 */         element2.appendChild(document.createTextNode(compatInfo.systementitytype_xml));
/*  672 */         element.appendChild(element2);
/*      */         
/*  674 */         element2 = document.createElement("SYSTEMENTITYID");
/*  675 */         element2.appendChild(document.createTextNode(compatInfo.systementityid_xml));
/*  676 */         element.appendChild(element2);
/*      */         
/*  678 */         element2 = document.createElement("SYSTEMMACHTYPE");
/*  679 */         element2.appendChild(document.createTextNode(compatInfo.systemmachtype_xml));
/*  680 */         element.appendChild(element2);
/*      */         
/*  682 */         element2 = document.createElement("SYSTEMMODEL");
/*  683 */         element2.appendChild(document.createTextNode(compatInfo.systemmodel_xml));
/*  684 */         element.appendChild(element2);
/*      */         
/*  686 */         element2 = document.createElement("SYSTEMSEOID");
/*  687 */         element2.appendChild(document.createTextNode(compatInfo.systemseoid_xml));
/*  688 */         element.appendChild(element2);
/*      */         
/*  690 */         element2 = document.createElement("GROUPENTITYTYPE");
/*  691 */         element2.appendChild(document.createTextNode(compatInfo.groupentitytype_xml));
/*  692 */         element.appendChild(element2);
/*      */         
/*  694 */         element2 = document.createElement("GROUPENTITYID");
/*  695 */         element2.appendChild(document.createTextNode(compatInfo.groupentityid_xml));
/*  696 */         element.appendChild(element2);
/*      */         
/*  698 */         element2 = document.createElement("OKTOPUB");
/*  699 */         element2.appendChild(document.createTextNode(compatInfo.oktopub_xml));
/*  700 */         element.appendChild(element2);
/*      */         
/*  702 */         element2 = document.createElement("OPTIONENTITYTYPE");
/*  703 */         element2.appendChild(document.createTextNode(compatInfo.optionentitytype_xml));
/*  704 */         element.appendChild(element2);
/*      */         
/*  706 */         element2 = document.createElement("OPTIONENTITYID");
/*  707 */         element2.appendChild(document.createTextNode(compatInfo.optionentityid_xml));
/*  708 */         element.appendChild(element2);
/*      */         
/*  710 */         element2 = document.createElement("OPTIONSEOID");
/*  711 */         element2.appendChild(document.createTextNode(compatInfo.optionseoid_xml));
/*  712 */         element.appendChild(element2);
/*      */         
/*  714 */         element2 = document.createElement("COMPATPUBFLAG");
/*  715 */         element2.appendChild(document.createTextNode(compatInfo.compatibilitypublishingflag_xml));
/*  716 */         element.appendChild(element2);
/*      */         
/*  718 */         element2 = document.createElement("RELTYPE");
/*  719 */         element2.appendChild(document.createTextNode(compatInfo.relationshiptype_xml));
/*  720 */         element.appendChild(element2);
/*      */         
/*  722 */         element2 = document.createElement("PUBFROM");
/*  723 */         element2.appendChild(document.createTextNode(compatInfo.publishfrom_xml));
/*  724 */         element.appendChild(element2);
/*      */         
/*  726 */         element2 = document.createElement("PUBTO");
/*  727 */         element2.appendChild(document.createTextNode(compatInfo.publishto_xml));
/*  728 */         element.appendChild(element2);
/*      */ 
/*      */         
/*  731 */         compatInfo.dereference();
/*      */       } 
/*      */       
/*  734 */       String str3 = paramADSABRSTATUS.transformXML(this, document);
/*      */ 
/*      */       
/*  737 */       boolean bool = false;
/*      */       
/*  739 */       String str4 = "XMLCOMPATSETUP";
/*  740 */       String str5 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDNEEDED", "NO");
/*  741 */       if ("YES".equals(str5.toUpperCase())) {
/*  742 */         String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDFILE", "NONE");
/*  743 */         if ("NONE".equals(str)) {
/*  744 */           paramADSABRSTATUS.addError("there is no xsdfile for " + str4 + " defined in the propertyfile ");
/*      */         } else {
/*  746 */           long l1 = System.currentTimeMillis();
/*  747 */           Class<?> clazz = getClass();
/*  748 */           StringBuffer stringBuffer = new StringBuffer();
/*  749 */           bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  750 */           if (stringBuffer.length() > 0) {
/*  751 */             String str6 = stringBuffer.toString();
/*  752 */             if (str6.indexOf("fail") != -1)
/*  753 */               paramADSABRSTATUS.addError(str6); 
/*  754 */             paramADSABRSTATUS.addOutput(str6);
/*      */           } 
/*  756 */           long l2 = System.currentTimeMillis();
/*  757 */           paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/*  758 */           if (bool) {
/*  759 */             paramADSABRSTATUS.addDebug("the xml for " + str4 + " passed the validation");
/*      */           }
/*      */         } 
/*      */       } else {
/*  763 */         paramADSABRSTATUS.addOutput("the xml for " + str4 + " doesn't need to be validated");
/*  764 */         bool = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  769 */       if (str3 != null && bool) {
/*  770 */         if (!ADSABRSTATUS.USERXML_OFF_LOG)
/*      */         {
/*      */           
/*  773 */           paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str3 + ADSABRSTATUS.NEWLINE);
/*      */         }
/*  775 */         paramADSABRSTATUS.notify(this, "WWCOMPAT", str3, paramVector2);
/*      */       } 
/*  777 */       document = null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void withdrawOffering(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2) throws SQLException {
/*  915 */     ResultSet resultSet = null;
/*  916 */     Connection connection = null;
/*  917 */     PreparedStatement preparedStatement = null;
/*  918 */     Vector<String[]> vector = new Vector();
/*  919 */     long l1 = System.currentTimeMillis();
/*  920 */     long l2 = 0L;
/*  921 */     byte b = 0;
/*      */     try {
/*  923 */       paramADSABRSTATUS.addDebug("");
/*  924 */       connection = setupConnection();
/*  925 */       preparedStatement = connection.prepareStatement("select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 and right_lseo.nlsid=1 and right_lseo.lseounpubdatemtrgt + 90 day < current date where optionentitytype='WWSEO' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.model right_model on right_model.entityid=wwtc.optionentityid and right_model.isactive=1 and right_model.nlsid=1 and right_model.wthdrweffctvdate + 90 day < current date where optionentitytype='MODEL' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.lseobundle right_lseobundle on right_lseobundle.entityid=wwtc.optionentityid and right_lseobundle.isactive=1 and right_lseobundle.nlsid=1 and right_lseobundle.bundlunpubdatemtrgt + 90 day < current date where optionentitytype='LSEOBUNDLE' and activity in('A','C') and updated BETWEEN ? AND ?  ");
/*  926 */       preparedStatement.clearParameters();
/*  927 */       preparedStatement.setString(1, paramString1);
/*  928 */       preparedStatement.setString(2, paramString2);
/*  929 */       preparedStatement.setString(3, paramString1);
/*  930 */       preparedStatement.setString(4, paramString2);
/*  931 */       preparedStatement.setString(5, paramString1);
/*  932 */       preparedStatement.setString(6, paramString2);
/*  933 */       resultSet = preparedStatement.executeQuery();
/*  934 */       l2 = System.currentTimeMillis();
/*  935 */       paramADSABRSTATUS.addDebugComment(3, "Execute query SQL:select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 and right_lseo.nlsid=1 and right_lseo.lseounpubdatemtrgt + 90 day < current date where optionentitytype='WWSEO' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.model right_model on right_model.entityid=wwtc.optionentityid and right_model.isactive=1 and right_model.nlsid=1 and right_model.wthdrweffctvdate + 90 day < current date where optionentitytype='MODEL' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.lseobundle right_lseobundle on right_lseobundle.entityid=wwtc.optionentityid and right_lseobundle.isactive=1 and right_lseobundle.nlsid=1 and right_lseobundle.bundlunpubdatemtrgt + 90 day < current date where optionentitytype='LSEOBUNDLE' and activity in('A','C') and updated BETWEEN ? AND ?  Time is : " + Stopwatch.format(l2 - l1));
/*  936 */       while (resultSet.next()) {
/*  937 */         String str1 = resultSet.getString(1);
/*  938 */         int i = resultSet.getInt(2);
/*  939 */         String str2 = resultSet.getString(3);
/*  940 */         String str3 = resultSet.getString(4);
/*  941 */         int j = resultSet.getInt(5);
/*  942 */         String str4 = resultSet.getString(6);
/*  943 */         int k = resultSet.getInt(7);
/*  944 */         String str5 = resultSet.getString(8);
/*  945 */         String str6 = resultSet.getString(9);
/*  946 */         int m = resultSet.getInt(10);
/*      */ 
/*      */ 
/*      */         
/*  950 */         String[] arrayOfString = { str1, Integer.toString(i), str2, str3, Integer.toString(j), str4, Integer.toString(k), str5, str6, Integer.toString(m) };
/*  951 */         vector.add(arrayOfString);
/*  952 */         b++;
/*  953 */         if (vector.size() >= 5000) {
/*  954 */           updateWithdrawAct(paramADSABRSTATUS, vector);
/*      */         }
/*      */       } 
/*  957 */       if (vector.size() > 0) {
/*  958 */         updateWithdrawAct(paramADSABRSTATUS, vector);
/*      */       }
/*  960 */       l2 = System.currentTimeMillis();
/*  961 */       paramADSABRSTATUS.addDebugComment(3, "Process withdraw offerings options products count:" + b + ". Total time is : " + Stopwatch.format(l2 - l1));
/*      */     } finally {
/*      */       
/*      */       try {
/*  965 */         if (preparedStatement != null) {
/*  966 */           preparedStatement.close();
/*  967 */           preparedStatement = null;
/*      */         } 
/*  969 */       } catch (Exception exception) {
/*  970 */         System.err.println("getCompat(), unable to close statement. " + exception);
/*  971 */         paramADSABRSTATUS.addDebug("getCompat unable to close statement. " + exception);
/*      */       } 
/*  973 */       if (resultSet != null) {
/*  974 */         resultSet.close();
/*      */       }
/*  976 */       if (this.connection != null) {
/*  977 */         this.connection.close();
/*  978 */         this.connection = null;
/*      */       } 
/*      */       
/*  981 */       closeConnection(connection);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateWithdrawAct(ADSABRSTATUS paramADSABRSTATUS, Vector paramVector) throws SQLException {
/*  992 */     long l = System.currentTimeMillis();
/*  993 */     Iterator<String[]> iterator = paramVector.iterator();
/*      */     
/*  995 */     PreparedStatement preparedStatement = null;
/*      */ 
/*      */     
/*      */     try {
/*  999 */       if (this.connection == null) {
/* 1000 */         this.connection = setupConnection();
/*      */       }
/* 1002 */       preparedStatement = this.connection.prepareStatement("update gbli.wwtechcompat set activity = 'W' where SYSTEMENTITYTYPE = ? and SYSTEMENTITYID = ? and SYSTEMOS = ? and GROUPENTITYTYPE = ? and GROUPENTITYID = ? and OSENTITYTYPE = ? and OSENTITYID = ? and OS = ? and OPTIONENTITYTYPE = ? and OPTIONENTITYID = ?");
/* 1003 */       while (iterator.hasNext()) {
/* 1004 */         String[] arrayOfString = iterator.next();
/* 1005 */         preparedStatement.setString(1, arrayOfString[0]);
/* 1006 */         preparedStatement.setInt(2, Integer.parseInt(arrayOfString[1]));
/* 1007 */         preparedStatement.setString(3, arrayOfString[2]);
/* 1008 */         preparedStatement.setString(4, arrayOfString[3]);
/* 1009 */         preparedStatement.setInt(5, Integer.parseInt(arrayOfString[4]));
/* 1010 */         preparedStatement.setString(6, arrayOfString[5]);
/* 1011 */         preparedStatement.setInt(7, Integer.parseInt(arrayOfString[6]));
/* 1012 */         preparedStatement.setString(8, arrayOfString[7]);
/* 1013 */         preparedStatement.setString(9, arrayOfString[8]);
/* 1014 */         preparedStatement.setInt(10, Integer.parseInt(arrayOfString[9]));
/* 1015 */         preparedStatement.addBatch();
/*      */       } 
/* 1017 */       preparedStatement.executeBatch();
/* 1018 */       if (this.connection != null) {
/* 1019 */         this.connection.commit();
/*      */       }
/*      */       
/* 1022 */       paramADSABRSTATUS.addDebug(paramVector.size() + " records was updated in the table wwtechcompat. Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/* 1023 */     } catch (SQLException sQLException) {
/* 1024 */       paramADSABRSTATUS.addDebug("SQLException on ? " + sQLException);
/* 1025 */       sQLException.printStackTrace();
/* 1026 */       throw sQLException;
/*      */     } finally {
/* 1028 */       paramVector.clear();
/* 1029 */       if (preparedStatement != null) {
/*      */         try {
/* 1031 */           preparedStatement.close();
/* 1032 */         } catch (SQLException sQLException) {
/* 1033 */           sQLException.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processCompat(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2, Profile paramProfile, Database paramDatabase, EntityItem paramEntityItem) throws SQLException, DOMException, MissingResourceException, ParserConfigurationException, TransformerException {
/* 1055 */     Vector<CompatInfo> vector = new Vector();
/* 1056 */     ResultSet resultSet = null;
/* 1057 */     Connection connection = null;
/* 1058 */     PreparedStatement preparedStatement = null;
/*      */     try {
/* 1060 */       connection = setupConnection();
/* 1061 */       preparedStatement = connection.prepareStatement("select activity,'LSEO',left_lseo.entityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseo.seoid,right_lseo.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 and left_wwseolseo.isactive=1 join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid and left_modelwwseo.isactive=1 join price.model left_model on left_model.entityid=left_modelwwseo.id1 and left_model.isactive=1 where systementitytype='WWSEO' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'MODEL',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),cast(null as char),left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 where systementitytype='MODEL' and optionentitytype='MODEL' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),right_lseo.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 where systementitytype='MODEL' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),right_lseobundle.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 where systementitytype='MODEL' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEO',left_lseo.entityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseo.seoid,right_lseobundle.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 and left_wwseolseo.isactive=1 join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid and left_modelwwseo.isactive=1 join price.model left_model on left_model.entityid=left_modelwwseo.id1 and left_model.isactive=1 where systementitytype='WWSEO' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEOBUNDLE',systementityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseobundle.seoid,right_lseo.seoid,cast(null as char),cast(null as char) from gbli.wwtechcompat wwtc join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid and left_lseobundle.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 where systementitytype='LSEOBUNDLE' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEOBUNDLE',systementityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseobundle.seoid,right_lseobundle.seoid,cast(null as char),cast(null as char) from gbli.wwtechcompat wwtc join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid and left_lseobundle.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 where systementitytype='LSEOBUNDLE' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' with ur");
/*      */ 
/*      */       
/* 1064 */       preparedStatement.setString(1, paramString1);
/* 1065 */       preparedStatement.setString(3, paramString1);
/* 1066 */       preparedStatement.setString(5, paramString1);
/* 1067 */       preparedStatement.setString(7, paramString1);
/* 1068 */       preparedStatement.setString(9, paramString1);
/* 1069 */       preparedStatement.setString(11, paramString1);
/* 1070 */       preparedStatement.setString(13, paramString1);
/* 1071 */       preparedStatement.setString(2, paramString2);
/* 1072 */       preparedStatement.setString(4, paramString2);
/* 1073 */       preparedStatement.setString(6, paramString2);
/* 1074 */       preparedStatement.setString(8, paramString2);
/* 1075 */       preparedStatement.setString(10, paramString2);
/* 1076 */       preparedStatement.setString(12, paramString2);
/* 1077 */       preparedStatement.setString(14, paramString2);
/*      */       
/* 1079 */       resultSet = preparedStatement.executeQuery();
/* 1080 */       byte b = 1;
/* 1081 */       boolean bool = false;
/* 1082 */       while (resultSet.next()) {
/* 1083 */         String str1 = resultSet.getString(1);
/* 1084 */         String str2 = resultSet.getString(2);
/* 1085 */         int i = resultSet.getInt(3);
/*      */         
/* 1087 */         String str3 = resultSet.getString(4);
/* 1088 */         int j = resultSet.getInt(5);
/* 1089 */         String str4 = resultSet.getString(6);
/*      */ 
/*      */ 
/*      */         
/* 1093 */         String str5 = resultSet.getString(7);
/* 1094 */         int k = resultSet.getInt(8);
/* 1095 */         String str6 = resultSet.getString(9);
/* 1096 */         String str7 = resultSet.getString(10);
/* 1097 */         String str8 = resultSet.getString(11);
/* 1098 */         String str9 = resultSet.getString(12);
/* 1099 */         String str10 = resultSet.getString(13);
/* 1100 */         String str11 = resultSet.getString(14);
/* 1101 */         String str12 = resultSet.getString(15);
/* 1102 */         String str13 = resultSet.getString(16);
/* 1103 */         String str14 = resultSet.getString(17);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1168 */         if (!ADSABRSTATUS.USERXML_OFF_LOG) {
/* 1169 */           paramADSABRSTATUS.addDebugComment(2, "getCompat activity:" + str1 + " systementitytype:" + str2 + " systementityid:" + i + " groupentitytype:" + str3 + " groupentityid:" + j + " oktopub:" + str4 + " optionentitytype:" + str5 + " optionentityid:" + k + " compatibilitypublishingflag:" + str6 + " relationshiptype:" + str7 + " publishfrom:" + str8 + " publishto:" + str9 + " brandcd_fc:" + str10 + " systemmachtype:" + str13 + " systemmodel:" + str14 + " systemseoid:" + str11 + " optionseoid:" + str12);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1178 */         vector.add(new CompatInfo(str1, str2, i, "@@", str3, j, str4, "@@", 1, "@@", str5, k, str6, str7, str8, str9, str10, str13, str14, str11, str12));
/*      */ 
/*      */ 
/*      */         
/* 1182 */         if (vector.size() >= WWCOMPAT_ROW_LIMIT) {
/* 1183 */           paramADSABRSTATUS.addDebug("Chunking size is " + WWCOMPAT_ROW_LIMIT + ". Start to run chunking " + b++ + " times.");
/* 1184 */           sentToMQ(paramADSABRSTATUS, vector, paramProfile, paramEntityItem);
/*      */         } 
/*      */       } 
/*      */       
/* 1188 */       if (vector.size() > 0) {
/* 1189 */         sentToMQ(paramADSABRSTATUS, vector, paramProfile, paramEntityItem);
/*      */       }
/* 1191 */       paramADSABRSTATUS.addDebug("WWCOMPAT_MESSAGE_COUNT is " + WWCOMPAT_MESSAGE_COUNT);
/* 1192 */       this.wwcompMQTable.clear();
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1202 */         if (preparedStatement != null) {
/* 1203 */           preparedStatement.close();
/* 1204 */           preparedStatement = null;
/*      */         } 
/* 1206 */       } catch (Exception exception) {
/* 1207 */         System.err.println("getCompat(), unable to close statement. " + exception);
/* 1208 */         paramADSABRSTATUS.addDebug("getCompat unable to close statement. " + exception);
/*      */       } 
/* 1210 */       if (resultSet != null) {
/* 1211 */         resultSet.close();
/*      */       }
/* 1213 */       closeConnection(connection);
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
/*      */   private void sentToMQ(ADSABRSTATUS paramADSABRSTATUS, Vector<CompatInfo> paramVector, Profile paramProfile, EntityItem paramEntityItem) throws DOMException, MissingResourceException, ParserConfigurationException, TransformerException {
/* 1229 */     if (paramVector.size() == 0) {
/*      */       
/* 1231 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "ADSWWCOMPATXMLABR");
/*      */     } else {
/* 1233 */       paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR.processThis found " + paramVector.size() + " WWTECHCOMPAT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1264 */       Vector vector1 = getPeriodicMQ(paramEntityItem);
/*      */       
/* 1266 */       Vector vector2 = new Vector();
/* 1267 */       String str1 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "BRANDCD"));
/* 1268 */       String str2 = getDescription(paramEntityItem, "BRANDCD", "long");
/* 1269 */       if (str1.equals("")) {
/* 1270 */         str1 = "@@@";
/*      */       } else {
/* 1272 */         paramADSABRSTATUS.addXMLGenMsg("FILTER", "WWCOMPAT Periodic ABR is filtered by BRANDCD=" + str1 + "(" + str2 + ")");
/*      */       } 
/* 1274 */       paramADSABRSTATUS.addDebug("wwcompat filter key =" + str1);
/* 1275 */       paramADSABRSTATUS.addDebug("wwcompat MQ vector  =" + vector1);
/*      */       
/* 1277 */       if (str1.equals("@@@")) {
/* 1278 */         processMQ(paramADSABRSTATUS, paramProfile, paramVector, vector1);
/*      */       } else {
/* 1280 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1281 */           CompatInfo compatInfo = paramVector.elementAt(b);
/* 1282 */           String str = compatInfo.brandcd_fc_xml;
/* 1283 */           if (str1.equals(str)) {
/* 1284 */             vector2.add(paramVector.get(b));
/*      */           }
/*      */         } 
/* 1287 */         processMQ(paramADSABRSTATUS, paramProfile, vector2, vector1);
/*      */         
/* 1289 */         vector2.clear();
/*      */       } 
/*      */       
/* 1292 */       paramVector.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getDescription(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 1303 */     String str = "";
/* 1304 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1);
/* 1305 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 1307 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1308 */       StringBuffer stringBuffer = new StringBuffer();
/* 1309 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 1311 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 1313 */           if (stringBuffer.length() > 0) {
/* 1314 */             stringBuffer.append(",");
/*      */           }
/* 1316 */           if (paramString2.equals("short")) {
/* 1317 */             stringBuffer.append(arrayOfMetaFlag[b].getShortDescription());
/* 1318 */           } else if (paramString2.equals("long")) {
/* 1319 */             stringBuffer.append(arrayOfMetaFlag[b].getLongDescription());
/* 1320 */           } else if (paramString2.equals("flag")) {
/* 1321 */             stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/*      */           } else {
/*      */             
/* 1324 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */           } 
/*      */         } 
/*      */       } 
/* 1328 */       str = stringBuffer.toString();
/*      */     } 
/* 1330 */     return str;
/*      */   }
/*      */   
/*      */   private String convertValue(String paramString) {
/* 1334 */     return (paramString == null) ? "" : paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1342 */     return "1.6";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMQCID() {
/* 1349 */     return "WWCOMPAT_UPDATE";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getStatusAttr() {
/* 1354 */     return "ADSABRSTATUS";
/*      */   }
/*      */   
/*      */   private static class CompatInfo {
/* 1358 */     String activity_xml = "@@";
/* 1359 */     String systementitytype_xml = "@@";
/* 1360 */     String systementityid_xml = "@@";
/* 1361 */     String systemos_xml = "@@";
/* 1362 */     String groupentitytype_xml = "@@";
/* 1363 */     String groupentityid_xml = "@@";
/* 1364 */     String oktopub_xml = "@@";
/* 1365 */     String osentitytype_xml = "@@";
/* 1366 */     String osentityid_xml = "@@";
/* 1367 */     String os_xml = "@@";
/* 1368 */     String optionentitytype_xml = "@@";
/* 1369 */     String optionentityid_xml = "@@";
/* 1370 */     String compatibilitypublishingflag_xml = "@@";
/* 1371 */     String relationshiptype_xml = "@@";
/* 1372 */     String publishfrom_xml = "@@";
/* 1373 */     String publishto_xml = "@@";
/* 1374 */     String brandcd_fc_xml = "@@";
/* 1375 */     String systemmachtype_xml = "@@";
/* 1376 */     String systemmodel_xml = "@@";
/* 1377 */     String systemseoid_xml = "@@";
/* 1378 */     String optionseoid_xml = "@@";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CompatInfo(String param1String1, String param1String2, int param1Int1, String param1String3, String param1String4, int param1Int2, String param1String5, String param1String6, int param1Int3, String param1String7, String param1String8, int param1Int4, String param1String9, String param1String10, String param1String11, String param1String12, String param1String13, String param1String14, String param1String15, String param1String16, String param1String17) {
/* 1403 */       if (param1String1.equals("A") || param1String1.equals("C")) {
/* 1404 */         this.activity_xml = "Update";
/*      */       } else {
/* 1406 */         this.activity_xml = "Delete";
/*      */       } 
/* 1408 */       if (param1String2 != null) {
/* 1409 */         this.systementitytype_xml = param1String2.trim();
/*      */       }
/*      */       
/* 1412 */       if (param1Int1 != 0) {
/* 1413 */         this.systementityid_xml = Integer.toString(param1Int1);
/*      */       }
/*      */       
/* 1416 */       if (param1String3 != null) {
/* 1417 */         this.systemos_xml = param1String3.trim();
/*      */       }
/*      */       
/* 1420 */       if (param1String4 != null) {
/* 1421 */         this.groupentitytype_xml = param1String4.trim();
/*      */       }
/*      */       
/* 1424 */       if (param1Int2 != 0) {
/* 1425 */         this.groupentityid_xml = Integer.toString(param1Int2);
/*      */       }
/*      */       
/* 1428 */       if (param1String5 != null) {
/* 1429 */         this.oktopub_xml = param1String5.trim();
/*      */       }
/* 1431 */       if (param1String6 != null) {
/* 1432 */         this.osentitytype_xml = param1String6.trim();
/*      */       }
/*      */       
/* 1435 */       if (param1Int3 != 0) {
/* 1436 */         this.osentityid_xml = Integer.toString(param1Int3);
/*      */       }
/*      */       
/* 1439 */       if (param1String7 != null) {
/* 1440 */         this.os_xml = param1String7.trim();
/*      */       }
/*      */       
/* 1443 */       if (param1String8 != null) {
/* 1444 */         this.optionentitytype_xml = param1String8.trim();
/*      */       }
/*      */       
/* 1447 */       if (param1Int4 != 0) {
/* 1448 */         this.optionentityid_xml = Integer.toString(param1Int4);
/*      */       }
/*      */       
/* 1451 */       if (param1String9 != null) {
/* 1452 */         this.compatibilitypublishingflag_xml = param1String9.trim();
/*      */       }
/*      */       
/* 1455 */       if (param1String10 != null) {
/* 1456 */         this.relationshiptype_xml = param1String10.trim();
/*      */       }
/*      */       
/* 1459 */       if (param1String11 != null) {
/* 1460 */         this.publishfrom_xml = param1String11.trim();
/*      */       }
/*      */       
/* 1463 */       if (param1String12 != null) {
/* 1464 */         this.publishto_xml = param1String12.trim();
/*      */       }
/*      */       
/* 1467 */       if (param1String13 != null) {
/* 1468 */         this.brandcd_fc_xml = param1String13.trim();
/*      */       }
/*      */       
/* 1471 */       if (param1String14 != null) {
/* 1472 */         this.systemmachtype_xml = param1String14.trim();
/*      */       }
/*      */       
/* 1475 */       if (param1String15 != null) {
/* 1476 */         this.systemmodel_xml = param1String15.trim();
/*      */       }
/*      */       
/* 1479 */       if (param1String16 != null) {
/* 1480 */         this.systemseoid_xml = param1String16.trim();
/*      */       }
/*      */       
/* 1483 */       if (param1String17 != null) {
/* 1484 */         this.optionseoid_xml = param1String17.trim();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void dereference() {
/* 1490 */       this.activity_xml = null;
/* 1491 */       this.systementitytype_xml = null;
/* 1492 */       this.systementityid_xml = null;
/* 1493 */       this.systemos_xml = null;
/* 1494 */       this.groupentitytype_xml = null;
/* 1495 */       this.groupentityid_xml = null;
/* 1496 */       this.oktopub_xml = null;
/* 1497 */       this.osentitytype_xml = null;
/* 1498 */       this.osentityid_xml = null;
/* 1499 */       this.os_xml = null;
/* 1500 */       this.optionentitytype_xml = null;
/* 1501 */       this.optionentityid_xml = null;
/* 1502 */       this.compatibilitypublishingflag_xml = null;
/* 1503 */       this.relationshiptype_xml = null;
/* 1504 */       this.publishfrom_xml = null;
/* 1505 */       this.publishto_xml = null;
/* 1506 */       this.brandcd_fc_xml = null;
/* 1507 */       this.systemmachtype_xml = null;
/* 1508 */       this.systemmodel_xml = null;
/* 1509 */       this.systemseoid_xml = null;
/* 1510 */       this.optionseoid_xml = null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSWWCOMPATXMLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */