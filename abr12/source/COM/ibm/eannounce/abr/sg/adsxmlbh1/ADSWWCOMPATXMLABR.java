/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*      */ public class ADSWWCOMPATXMLABR
/*      */   extends XMLMQAdapter
/*      */ {
/*      */   private static final int MW_ENTITY_LIMIT;
/*      */   private static final int WWCOMPAT_ROW_LIMIT;
/*  107 */   protected static int WWCOMPAT_MESSAGE_COUNT = 0;
/*  108 */   Connection connection = null;
/*      */   static {
/*  110 */     String str1 = ABRServerProperties.getValue("ADSABRSTATUS", "_entitylimit", "3000");
/*      */     
/*  112 */     MW_ENTITY_LIMIT = Integer.parseInt(str1);
/*  113 */     String str2 = ABRServerProperties.getValue("ADSABRSTATUS", "_XMLGENCOUNT", "500000");
/*      */     
/*  115 */     WWCOMPAT_ROW_LIMIT = Integer.parseInt(str2);
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
/*  398 */     String str1 = paramProfile1.getValOn();
/*  399 */     String str2 = paramProfile2.getValOn();
/*      */     
/*  401 */     paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR.processThis checking between " + str1 + " and " + str2);
/*      */     
/*  403 */     WWCOMPAT_MESSAGE_COUNT = 0;
/*  404 */     paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR.withdraw offering set activity ='W' between " + str1 + " and " + str2);
/*  405 */     withdrawOffering(paramADSABRSTATUS, str1, str2);
/*  406 */     processCompat(paramADSABRSTATUS, str1, str2, paramProfile2, paramADSABRSTATUS.getDatabase(), paramEntityItem);
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
/*  460 */     if (paramVector2 == null) {
/*  461 */       paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR: No MQ properties files, nothing will be generated.");
/*      */       
/*  463 */       paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "ADSWWCOMPATXMLABR");
/*      */     
/*      */     }
/*  466 */     else if (paramVector1.size() > MW_ENTITY_LIMIT) {
/*  467 */       int i = 0;
/*  468 */       byte b1 = 0;
/*  469 */       Vector<CompatInfo> vector = new Vector();
/*  470 */       if (paramVector1.size() % MW_ENTITY_LIMIT != 0) {
/*  471 */         i = paramVector1.size() / MW_ENTITY_LIMIT + 1;
/*      */       } else {
/*  473 */         i = paramVector1.size() / MW_ENTITY_LIMIT;
/*      */       } 
/*  475 */       WWCOMPAT_MESSAGE_COUNT += i;
/*  476 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  477 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  478 */       for (byte b2 = 0; b2 < i; b2++) {
/*  479 */         for (byte b3 = 0; b3 < MW_ENTITY_LIMIT && 
/*  480 */           b1 != paramVector1.size(); b3++)
/*      */         {
/*      */           
/*  483 */           vector.add(paramVector1.elementAt(b1++));
/*      */         }
/*  485 */         Document document = documentBuilder.newDocument();
/*  486 */         String str1 = "WWCOMPAT_UPDATE";
/*  487 */         String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */         
/*  490 */         Element element1 = document.createElementNS(str2, str1);
/*      */         
/*  492 */         document.appendChild(element1);
/*  493 */         element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*  494 */         element1.appendChild(document.createComment("WWCOMPAT_UPDATE Version 1 Mod 0"));
/*      */         
/*  496 */         Element element2 = document.createElement("DTSOFMSG");
/*  497 */         element2.appendChild(document.createTextNode(paramProfile.getEndOfDay()));
/*  498 */         element1.appendChild(element2);
/*  499 */         for (byte b4 = 0; b4 < vector.size(); b4++) {
/*  500 */           CompatInfo compatInfo = vector.elementAt(b4);
/*  501 */           Element element = document.createElement("COMPATELEMENT");
/*  502 */           element1.appendChild(element);
/*      */           
/*  504 */           element2 = document.createElement("ACTIVITY");
/*  505 */           element2.appendChild(document.createTextNode(compatInfo.activity_xml));
/*  506 */           element.appendChild(element2);
/*      */ 
/*      */           
/*  509 */           element2 = document.createElement("BRANDCD");
/*  510 */           element2.appendChild(document.createTextNode(compatInfo.brandcd_fc_xml));
/*  511 */           element.appendChild(element2);
/*      */ 
/*      */           
/*  514 */           element2 = document.createElement("STATUS");
/*  515 */           element2.appendChild(document.createTextNode("0020"));
/*  516 */           element.appendChild(element2);
/*      */           
/*  518 */           element2 = document.createElement("SYSTEMENTITYTYPE");
/*  519 */           element2.appendChild(document.createTextNode(compatInfo.systementitytype_xml));
/*  520 */           element.appendChild(element2);
/*      */           
/*  522 */           element2 = document.createElement("SYSTEMENTITYID");
/*  523 */           element2.appendChild(document.createTextNode(compatInfo.systementityid_xml));
/*  524 */           element.appendChild(element2);
/*      */           
/*  526 */           element2 = document.createElement("SYSTEMMACHTYPE");
/*  527 */           element2.appendChild(document.createTextNode(compatInfo.systemmachtype_xml));
/*  528 */           element.appendChild(element2);
/*      */           
/*  530 */           element2 = document.createElement("SYSTEMMODEL");
/*  531 */           element2.appendChild(document.createTextNode(compatInfo.systemmodel_xml));
/*  532 */           element.appendChild(element2);
/*      */           
/*  534 */           element2 = document.createElement("SYSTEMSEOID");
/*  535 */           element2.appendChild(document.createTextNode(compatInfo.systemseoid_xml));
/*  536 */           element.appendChild(element2);
/*      */           
/*  538 */           element2 = document.createElement("GROUPENTITYTYPE");
/*  539 */           element2.appendChild(document.createTextNode(compatInfo.groupentitytype_xml));
/*  540 */           element.appendChild(element2);
/*      */           
/*  542 */           element2 = document.createElement("GROUPENTITYID");
/*  543 */           element2.appendChild(document.createTextNode(compatInfo.groupentityid_xml));
/*  544 */           element.appendChild(element2);
/*      */           
/*  546 */           element2 = document.createElement("OKTOPUB");
/*  547 */           element2.appendChild(document.createTextNode(compatInfo.oktopub_xml));
/*  548 */           element.appendChild(element2);
/*      */           
/*  550 */           element2 = document.createElement("OPTIONENTITYTYPE");
/*  551 */           element2.appendChild(document.createTextNode(compatInfo.optionentitytype_xml));
/*  552 */           element.appendChild(element2);
/*      */           
/*  554 */           element2 = document.createElement("OPTIONENTITYID");
/*  555 */           element2.appendChild(document.createTextNode(compatInfo.optionentityid_xml));
/*  556 */           element.appendChild(element2);
/*      */           
/*  558 */           element2 = document.createElement("OPTIONSEOID");
/*  559 */           element2.appendChild(document.createTextNode(compatInfo.optionseoid_xml));
/*  560 */           element.appendChild(element2);
/*      */           
/*  562 */           element2 = document.createElement("COMPATPUBFLAG");
/*  563 */           element2.appendChild(document.createTextNode(compatInfo.compatibilitypublishingflag_xml));
/*  564 */           element.appendChild(element2);
/*      */           
/*  566 */           element2 = document.createElement("RELTYPE");
/*  567 */           element2.appendChild(document.createTextNode(compatInfo.relationshiptype_xml));
/*  568 */           element.appendChild(element2);
/*      */           
/*  570 */           element2 = document.createElement("PUBFROM");
/*  571 */           element2.appendChild(document.createTextNode(compatInfo.publishfrom_xml));
/*  572 */           element.appendChild(element2);
/*      */           
/*  574 */           element2 = document.createElement("PUBTO");
/*  575 */           element2.appendChild(document.createTextNode(compatInfo.publishto_xml));
/*  576 */           element.appendChild(element2);
/*      */ 
/*      */           
/*  579 */           compatInfo.dereference();
/*      */         } 
/*  581 */         vector.clear();
/*  582 */         String str3 = paramADSABRSTATUS.transformXML(this, document);
/*      */ 
/*      */         
/*  585 */         boolean bool = false;
/*      */         
/*  587 */         String str4 = "XMLCOMPATSETUP";
/*  588 */         String str5 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDNEEDED", "NO");
/*  589 */         if ("YES".equals(str5.toUpperCase())) {
/*  590 */           String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDFILE", "NONE");
/*  591 */           if ("NONE".equals(str)) {
/*  592 */             paramADSABRSTATUS.addError("there is no xsdfile for " + str4 + " defined in the propertyfile ");
/*      */           } else {
/*  594 */             long l1 = System.currentTimeMillis();
/*  595 */             Class<?> clazz = getClass();
/*  596 */             StringBuffer stringBuffer = new StringBuffer();
/*  597 */             bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  598 */             if (stringBuffer.length() > 0) {
/*  599 */               String str6 = stringBuffer.toString();
/*  600 */               if (str6.indexOf("fail") != -1)
/*  601 */                 paramADSABRSTATUS.addError(str6); 
/*  602 */               paramADSABRSTATUS.addOutput(str6);
/*      */             } 
/*  604 */             long l2 = System.currentTimeMillis();
/*  605 */             paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/*  606 */             if (bool) {
/*  607 */               paramADSABRSTATUS.addDebug("the xml for " + str4 + " passed the validation");
/*      */             }
/*      */           } 
/*      */         } else {
/*  611 */           paramADSABRSTATUS.addOutput("the xml for " + str4 + " doesn't need to be validated");
/*  612 */           bool = true;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  618 */         if (str3 != null && bool) {
/*  619 */           if (!ADSABRSTATUS.USERXML_OFF_LOG)
/*      */           {
/*      */             
/*  622 */             paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str3 + ADSABRSTATUS.NEWLINE);
/*      */           }
/*  624 */           paramADSABRSTATUS.notify(this, "WWCOMPAT", str3, paramVector2);
/*      */         } 
/*  626 */         document = null;
/*  627 */         System.gc();
/*      */       } 
/*      */     } else {
/*      */       
/*  631 */       WWCOMPAT_MESSAGE_COUNT++;
/*  632 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  633 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  634 */       Document document = documentBuilder.newDocument();
/*  635 */       String str1 = "WWCOMPAT_UPDATE";
/*  636 */       String str2 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str1;
/*      */ 
/*      */       
/*  639 */       Element element1 = document.createElementNS(str2, str1);
/*      */       
/*  641 */       document.appendChild(element1);
/*  642 */       element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str2);
/*  643 */       element1.appendChild(document.createComment("WWCOMPAT_UPDATE Version 1 Mod 0"));
/*      */       
/*  645 */       Element element2 = document.createElement("DTSOFMSG");
/*  646 */       element2.appendChild(document.createTextNode(paramProfile.getEndOfDay()));
/*  647 */       element1.appendChild(element2);
/*  648 */       for (byte b = 0; b < paramVector1.size(); b++) {
/*  649 */         CompatInfo compatInfo = paramVector1.elementAt(b);
/*  650 */         Element element = document.createElement("COMPATELEMENT");
/*  651 */         element1.appendChild(element);
/*      */         
/*  653 */         element2 = document.createElement("ACTIVITY");
/*  654 */         element2.appendChild(document.createTextNode(compatInfo.activity_xml));
/*  655 */         element.appendChild(element2);
/*      */ 
/*      */         
/*  658 */         element2 = document.createElement("BRANDCD");
/*  659 */         element2.appendChild(document.createTextNode(compatInfo.brandcd_fc_xml));
/*  660 */         element.appendChild(element2);
/*      */ 
/*      */         
/*  663 */         element2 = document.createElement("STATUS");
/*  664 */         element2.appendChild(document.createTextNode("0020"));
/*  665 */         element.appendChild(element2);
/*      */         
/*  667 */         element2 = document.createElement("SYSTEMENTITYTYPE");
/*  668 */         element2.appendChild(document.createTextNode(compatInfo.systementitytype_xml));
/*  669 */         element.appendChild(element2);
/*      */         
/*  671 */         element2 = document.createElement("SYSTEMENTITYID");
/*  672 */         element2.appendChild(document.createTextNode(compatInfo.systementityid_xml));
/*  673 */         element.appendChild(element2);
/*      */         
/*  675 */         element2 = document.createElement("SYSTEMMACHTYPE");
/*  676 */         element2.appendChild(document.createTextNode(compatInfo.systemmachtype_xml));
/*  677 */         element.appendChild(element2);
/*      */         
/*  679 */         element2 = document.createElement("SYSTEMMODEL");
/*  680 */         element2.appendChild(document.createTextNode(compatInfo.systemmodel_xml));
/*  681 */         element.appendChild(element2);
/*      */         
/*  683 */         element2 = document.createElement("SYSTEMSEOID");
/*  684 */         element2.appendChild(document.createTextNode(compatInfo.systemseoid_xml));
/*  685 */         element.appendChild(element2);
/*      */         
/*  687 */         element2 = document.createElement("GROUPENTITYTYPE");
/*  688 */         element2.appendChild(document.createTextNode(compatInfo.groupentitytype_xml));
/*  689 */         element.appendChild(element2);
/*      */         
/*  691 */         element2 = document.createElement("GROUPENTITYID");
/*  692 */         element2.appendChild(document.createTextNode(compatInfo.groupentityid_xml));
/*  693 */         element.appendChild(element2);
/*      */         
/*  695 */         element2 = document.createElement("OKTOPUB");
/*  696 */         element2.appendChild(document.createTextNode(compatInfo.oktopub_xml));
/*  697 */         element.appendChild(element2);
/*      */         
/*  699 */         element2 = document.createElement("OPTIONENTITYTYPE");
/*  700 */         element2.appendChild(document.createTextNode(compatInfo.optionentitytype_xml));
/*  701 */         element.appendChild(element2);
/*      */         
/*  703 */         element2 = document.createElement("OPTIONENTITYID");
/*  704 */         element2.appendChild(document.createTextNode(compatInfo.optionentityid_xml));
/*  705 */         element.appendChild(element2);
/*      */         
/*  707 */         element2 = document.createElement("OPTIONSEOID");
/*  708 */         element2.appendChild(document.createTextNode(compatInfo.optionseoid_xml));
/*  709 */         element.appendChild(element2);
/*      */         
/*  711 */         element2 = document.createElement("COMPATPUBFLAG");
/*  712 */         element2.appendChild(document.createTextNode(compatInfo.compatibilitypublishingflag_xml));
/*  713 */         element.appendChild(element2);
/*      */         
/*  715 */         element2 = document.createElement("RELTYPE");
/*  716 */         element2.appendChild(document.createTextNode(compatInfo.relationshiptype_xml));
/*  717 */         element.appendChild(element2);
/*      */         
/*  719 */         element2 = document.createElement("PUBFROM");
/*  720 */         element2.appendChild(document.createTextNode(compatInfo.publishfrom_xml));
/*  721 */         element.appendChild(element2);
/*      */         
/*  723 */         element2 = document.createElement("PUBTO");
/*  724 */         element2.appendChild(document.createTextNode(compatInfo.publishto_xml));
/*  725 */         element.appendChild(element2);
/*      */ 
/*      */         
/*  728 */         compatInfo.dereference();
/*      */       } 
/*      */       
/*  731 */       String str3 = paramADSABRSTATUS.transformXML(this, document);
/*      */ 
/*      */       
/*  734 */       boolean bool = false;
/*      */       
/*  736 */       String str4 = "XMLCOMPATSETUP";
/*  737 */       String str5 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDNEEDED", "NO");
/*  738 */       if ("YES".equals(str5.toUpperCase())) {
/*  739 */         String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str4 + "_XSDFILE", "NONE");
/*  740 */         if ("NONE".equals(str)) {
/*  741 */           paramADSABRSTATUS.addError("there is no xsdfile for " + str4 + " defined in the propertyfile ");
/*      */         } else {
/*  743 */           long l1 = System.currentTimeMillis();
/*  744 */           Class<?> clazz = getClass();
/*  745 */           StringBuffer stringBuffer = new StringBuffer();
/*  746 */           bool = ABRUtil.validatexml(clazz, stringBuffer, str, str3);
/*  747 */           if (stringBuffer.length() > 0) {
/*  748 */             String str6 = stringBuffer.toString();
/*  749 */             if (str6.indexOf("fail") != -1)
/*  750 */               paramADSABRSTATUS.addError(str6); 
/*  751 */             paramADSABRSTATUS.addOutput(str6);
/*      */           } 
/*  753 */           long l2 = System.currentTimeMillis();
/*  754 */           paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/*  755 */           if (bool) {
/*  756 */             paramADSABRSTATUS.addDebug("the xml for " + str4 + " passed the validation");
/*      */           }
/*      */         } 
/*      */       } else {
/*  760 */         paramADSABRSTATUS.addOutput("the xml for " + str4 + " doesn't need to be validated");
/*  761 */         bool = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  766 */       if (str3 != null && bool) {
/*  767 */         if (!ADSABRSTATUS.USERXML_OFF_LOG)
/*      */         {
/*      */           
/*  770 */           paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str3 + ADSABRSTATUS.NEWLINE);
/*      */         }
/*  772 */         paramADSABRSTATUS.notify(this, "WWCOMPAT", str3, paramVector2);
/*      */       } 
/*  774 */       document = null;
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
/*  912 */     ResultSet resultSet = null;
/*  913 */     Connection connection = null;
/*  914 */     PreparedStatement preparedStatement = null;
/*  915 */     Vector<String[]> vector = new Vector();
/*  916 */     long l1 = System.currentTimeMillis();
/*  917 */     long l2 = 0L;
/*  918 */     byte b = 0;
/*      */     try {
/*  920 */       paramADSABRSTATUS.addDebug("");
/*  921 */       connection = setupConnection();
/*  922 */       preparedStatement = connection.prepareStatement("select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 and right_lseo.nlsid=1 and right_lseo.lseounpubdatemtrgt + 90 day < current date where optionentitytype='WWSEO' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.model right_model on right_model.entityid=wwtc.optionentityid and right_model.isactive=1 and right_model.nlsid=1 and right_model.wthdrweffctvdate + 90 day < current date where optionentitytype='MODEL' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.lseobundle right_lseobundle on right_lseobundle.entityid=wwtc.optionentityid and right_lseobundle.isactive=1 and right_lseobundle.nlsid=1 and right_lseobundle.bundlunpubdatemtrgt + 90 day < current date where optionentitytype='LSEOBUNDLE' and activity in('A','C') and updated BETWEEN ? AND ?  ");
/*  923 */       preparedStatement.clearParameters();
/*  924 */       preparedStatement.setString(1, paramString1);
/*  925 */       preparedStatement.setString(2, paramString2);
/*  926 */       preparedStatement.setString(3, paramString1);
/*  927 */       preparedStatement.setString(4, paramString2);
/*  928 */       preparedStatement.setString(5, paramString1);
/*  929 */       preparedStatement.setString(6, paramString2);
/*  930 */       resultSet = preparedStatement.executeQuery();
/*  931 */       l2 = System.currentTimeMillis();
/*  932 */       paramADSABRSTATUS.addDebugComment(3, "Execute query SQL:select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 and right_lseo.nlsid=1 and right_lseo.lseounpubdatemtrgt + 90 day < current date where optionentitytype='WWSEO' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.model right_model on right_model.entityid=wwtc.optionentityid and right_model.isactive=1 and right_model.nlsid=1 and right_model.wthdrweffctvdate + 90 day < current date where optionentitytype='MODEL' and activity in('A','C') and updated BETWEEN ? AND ? union select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid from gbli.wwtechcompat wwtc join price.lseobundle right_lseobundle on right_lseobundle.entityid=wwtc.optionentityid and right_lseobundle.isactive=1 and right_lseobundle.nlsid=1 and right_lseobundle.bundlunpubdatemtrgt + 90 day < current date where optionentitytype='LSEOBUNDLE' and activity in('A','C') and updated BETWEEN ? AND ?  Time is : " + Stopwatch.format(l2 - l1));
/*  933 */       while (resultSet.next()) {
/*  934 */         String str1 = resultSet.getString(1);
/*  935 */         int i = resultSet.getInt(2);
/*  936 */         String str2 = resultSet.getString(3);
/*  937 */         String str3 = resultSet.getString(4);
/*  938 */         int j = resultSet.getInt(5);
/*  939 */         String str4 = resultSet.getString(6);
/*  940 */         int k = resultSet.getInt(7);
/*  941 */         String str5 = resultSet.getString(8);
/*  942 */         String str6 = resultSet.getString(9);
/*  943 */         int m = resultSet.getInt(10);
/*      */ 
/*      */ 
/*      */         
/*  947 */         String[] arrayOfString = { str1, Integer.toString(i), str2, str3, Integer.toString(j), str4, Integer.toString(k), str5, str6, Integer.toString(m) };
/*  948 */         vector.add(arrayOfString);
/*  949 */         b++;
/*  950 */         if (vector.size() >= 5000) {
/*  951 */           updateWithdrawAct(paramADSABRSTATUS, vector);
/*      */         }
/*      */       } 
/*  954 */       if (vector.size() > 0) {
/*  955 */         updateWithdrawAct(paramADSABRSTATUS, vector);
/*      */       }
/*  957 */       l2 = System.currentTimeMillis();
/*  958 */       paramADSABRSTATUS.addDebugComment(3, "Process withdraw offerings options products count:" + b + ". Total time is : " + Stopwatch.format(l2 - l1));
/*      */     } finally {
/*      */       
/*      */       try {
/*  962 */         if (preparedStatement != null) {
/*  963 */           preparedStatement.close();
/*  964 */           preparedStatement = null;
/*      */         } 
/*  966 */       } catch (Exception exception) {
/*  967 */         System.err.println("getCompat(), unable to close statement. " + exception);
/*  968 */         paramADSABRSTATUS.addDebug("getCompat unable to close statement. " + exception);
/*      */       } 
/*  970 */       if (resultSet != null) {
/*  971 */         resultSet.close();
/*      */       }
/*  973 */       if (this.connection != null) {
/*  974 */         this.connection.close();
/*  975 */         this.connection = null;
/*      */       } 
/*      */       
/*  978 */       closeConnection(connection);
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
/*  989 */     long l = System.currentTimeMillis();
/*  990 */     Iterator<String[]> iterator = paramVector.iterator();
/*      */     
/*  992 */     PreparedStatement preparedStatement = null;
/*      */ 
/*      */     
/*      */     try {
/*  996 */       if (this.connection == null) {
/*  997 */         this.connection = setupConnection();
/*      */       }
/*  999 */       preparedStatement = this.connection.prepareStatement("update gbli.wwtechcompat set activity = 'W' where SYSTEMENTITYTYPE = ? and SYSTEMENTITYID = ? and SYSTEMOS = ? and GROUPENTITYTYPE = ? and GROUPENTITYID = ? and OSENTITYTYPE = ? and OSENTITYID = ? and OS = ? and OPTIONENTITYTYPE = ? and OPTIONENTITYID = ?");
/* 1000 */       while (iterator.hasNext()) {
/* 1001 */         String[] arrayOfString = iterator.next();
/* 1002 */         preparedStatement.setString(1, arrayOfString[0]);
/* 1003 */         preparedStatement.setInt(2, Integer.parseInt(arrayOfString[1]));
/* 1004 */         preparedStatement.setString(3, arrayOfString[2]);
/* 1005 */         preparedStatement.setString(4, arrayOfString[3]);
/* 1006 */         preparedStatement.setInt(5, Integer.parseInt(arrayOfString[4]));
/* 1007 */         preparedStatement.setString(6, arrayOfString[5]);
/* 1008 */         preparedStatement.setInt(7, Integer.parseInt(arrayOfString[6]));
/* 1009 */         preparedStatement.setString(8, arrayOfString[7]);
/* 1010 */         preparedStatement.setString(9, arrayOfString[8]);
/* 1011 */         preparedStatement.setInt(10, Integer.parseInt(arrayOfString[9]));
/* 1012 */         preparedStatement.addBatch();
/*      */       } 
/* 1014 */       preparedStatement.executeBatch();
/* 1015 */       if (this.connection != null) {
/* 1016 */         this.connection.commit();
/*      */       }
/*      */       
/* 1019 */       paramADSABRSTATUS.addDebug(paramVector.size() + " records was updated in the table wwtechcompat. Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/* 1020 */     } catch (SQLException sQLException) {
/* 1021 */       paramADSABRSTATUS.addDebug("SQLException on ? " + sQLException);
/* 1022 */       sQLException.printStackTrace();
/* 1023 */       throw sQLException;
/*      */     } finally {
/* 1025 */       paramVector.clear();
/* 1026 */       if (preparedStatement != null) {
/*      */         try {
/* 1028 */           preparedStatement.close();
/* 1029 */         } catch (SQLException sQLException) {
/* 1030 */           sQLException.printStackTrace();
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
/* 1052 */     Vector<CompatInfo> vector = new Vector();
/* 1053 */     ResultSet resultSet = null;
/* 1054 */     Connection connection = null;
/* 1055 */     PreparedStatement preparedStatement = null;
/*      */     try {
/* 1057 */       connection = setupConnection();
/* 1058 */       preparedStatement = connection.prepareStatement("select activity,'LSEO',left_lseo.entityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseo.seoid,right_lseo.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 and left_wwseolseo.isactive=1 join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid and left_modelwwseo.isactive=1 join price.model left_model on left_model.entityid=left_modelwwseo.id1 and left_model.isactive=1 where systementitytype='WWSEO' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'MODEL',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),cast(null as char),left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 where systementitytype='MODEL' and optionentitytype='MODEL' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),right_lseo.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 where systementitytype='MODEL' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'MODEL',systementityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,cast(null as char),right_lseobundle.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 where systementitytype='MODEL' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEO',left_lseo.entityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseo.seoid,right_lseobundle.seoid,left_model.machtypeatr,left_model.modelatr from gbli.wwtechcompat wwtc join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 and left_wwseolseo.isactive=1 join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid and left_modelwwseo.isactive=1 join price.model left_model on left_model.entityid=left_modelwwseo.id1 and left_model.isactive=1 where systementitytype='WWSEO' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEOBUNDLE',systementityid,groupentitytype,groupentityid,oktopub,'LSEO',right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseobundle.seoid,right_lseo.seoid,cast(null as char),cast(null as char) from gbli.wwtechcompat wwtc join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid and left_lseobundle.isactive=1 join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 and right_wwseolseo.isactive=1 join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 where systementitytype='LSEOBUNDLE' and optionentitytype='WWSEO' and updated BETWEEN ? AND ? and activity <> 'W' union select activity,'LSEOBUNDLE',systementityid,groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,brandcd_fc,left_lseobundle.seoid,right_lseobundle.seoid,cast(null as char),cast(null as char) from gbli.wwtechcompat wwtc join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid and left_lseobundle.isactive=1 join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and right_lseobundle.isactive=1 where systementitytype='LSEOBUNDLE' and optionentitytype='LSEOBUNDLE' and updated BETWEEN ? AND ? and activity <> 'W' with ur");
/*      */ 
/*      */       
/* 1061 */       preparedStatement.setString(1, paramString1);
/* 1062 */       preparedStatement.setString(3, paramString1);
/* 1063 */       preparedStatement.setString(5, paramString1);
/* 1064 */       preparedStatement.setString(7, paramString1);
/* 1065 */       preparedStatement.setString(9, paramString1);
/* 1066 */       preparedStatement.setString(11, paramString1);
/* 1067 */       preparedStatement.setString(13, paramString1);
/* 1068 */       preparedStatement.setString(2, paramString2);
/* 1069 */       preparedStatement.setString(4, paramString2);
/* 1070 */       preparedStatement.setString(6, paramString2);
/* 1071 */       preparedStatement.setString(8, paramString2);
/* 1072 */       preparedStatement.setString(10, paramString2);
/* 1073 */       preparedStatement.setString(12, paramString2);
/* 1074 */       preparedStatement.setString(14, paramString2);
/*      */       
/* 1076 */       resultSet = preparedStatement.executeQuery();
/* 1077 */       byte b = 1;
/* 1078 */       boolean bool = false;
/* 1079 */       while (resultSet.next()) {
/* 1080 */         String str1 = resultSet.getString(1);
/* 1081 */         String str2 = resultSet.getString(2);
/* 1082 */         int i = resultSet.getInt(3);
/*      */         
/* 1084 */         String str3 = resultSet.getString(4);
/* 1085 */         int j = resultSet.getInt(5);
/* 1086 */         String str4 = resultSet.getString(6);
/*      */ 
/*      */ 
/*      */         
/* 1090 */         String str5 = resultSet.getString(7);
/* 1091 */         int k = resultSet.getInt(8);
/* 1092 */         String str6 = resultSet.getString(9);
/* 1093 */         String str7 = resultSet.getString(10);
/* 1094 */         String str8 = resultSet.getString(11);
/* 1095 */         String str9 = resultSet.getString(12);
/* 1096 */         String str10 = resultSet.getString(13);
/* 1097 */         String str11 = resultSet.getString(14);
/* 1098 */         String str12 = resultSet.getString(15);
/* 1099 */         String str13 = resultSet.getString(16);
/* 1100 */         String str14 = resultSet.getString(17);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1165 */         if (!ADSABRSTATUS.USERXML_OFF_LOG) {
/* 1166 */           paramADSABRSTATUS.addDebugComment(2, "getCompat activity:" + str1 + " systementitytype:" + str2 + " systementityid:" + i + " groupentitytype:" + str3 + " groupentityid:" + j + " oktopub:" + str4 + " optionentitytype:" + str5 + " optionentityid:" + k + " compatibilitypublishingflag:" + str6 + " relationshiptype:" + str7 + " publishfrom:" + str8 + " publishto:" + str9 + " brandcd_fc:" + str10 + " systemmachtype:" + str13 + " systemmodel:" + str14 + " systemseoid:" + str11 + " optionseoid:" + str12);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1175 */         vector.add(new CompatInfo(str1, str2, i, "@@", str3, j, str4, "@@", 1, "@@", str5, k, str6, str7, str8, str9, str10, str13, str14, str11, str12));
/*      */ 
/*      */ 
/*      */         
/* 1179 */         if (vector.size() >= WWCOMPAT_ROW_LIMIT) {
/* 1180 */           paramADSABRSTATUS.addDebug("Chunking size is " + WWCOMPAT_ROW_LIMIT + ". Start to run chunking " + b++ + " times.");
/* 1181 */           sentToMQ(paramADSABRSTATUS, vector, paramProfile, paramEntityItem);
/*      */         } 
/*      */       } 
/*      */       
/* 1185 */       if (vector.size() > 0) {
/* 1186 */         sentToMQ(paramADSABRSTATUS, vector, paramProfile, paramEntityItem);
/*      */       }
/* 1188 */       paramADSABRSTATUS.addDebug("WWCOMPAT_MESSAGE_COUNT is " + WWCOMPAT_MESSAGE_COUNT);
/* 1189 */       this.wwcompMQTable.clear();
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1199 */         if (preparedStatement != null) {
/* 1200 */           preparedStatement.close();
/* 1201 */           preparedStatement = null;
/*      */         } 
/* 1203 */       } catch (Exception exception) {
/* 1204 */         System.err.println("getCompat(), unable to close statement. " + exception);
/* 1205 */         paramADSABRSTATUS.addDebug("getCompat unable to close statement. " + exception);
/*      */       } 
/* 1207 */       if (resultSet != null) {
/* 1208 */         resultSet.close();
/*      */       }
/* 1210 */       closeConnection(connection);
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
/* 1226 */     if (paramVector.size() == 0) {
/*      */       
/* 1228 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "ADSWWCOMPATXMLABR");
/*      */     } else {
/* 1230 */       paramADSABRSTATUS.addDebug("ADSWWCOMPATXMLABR.processThis found " + paramVector.size() + " WWTECHCOMPAT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1261 */       Vector vector1 = getPeriodicMQ(paramEntityItem);
/*      */       
/* 1263 */       Vector vector2 = new Vector();
/* 1264 */       String str1 = convertValue(PokUtils.getAttributeFlagValue(paramEntityItem, "BRANDCD"));
/* 1265 */       String str2 = getDescription(paramEntityItem, "BRANDCD", "long");
/* 1266 */       if (str1.equals("")) {
/* 1267 */         str1 = "@@@";
/*      */       } else {
/* 1269 */         paramADSABRSTATUS.addXMLGenMsg("FILTER", "WWCOMPAT Periodic ABR is filtered by BRANDCD=" + str1 + "(" + str2 + ")");
/*      */       } 
/* 1271 */       paramADSABRSTATUS.addDebug("wwcompat filter key =" + str1);
/* 1272 */       paramADSABRSTATUS.addDebug("wwcompat MQ vector  =" + vector1);
/*      */       
/* 1274 */       if (str1.equals("@@@")) {
/* 1275 */         processMQ(paramADSABRSTATUS, paramProfile, paramVector, vector1);
/*      */       } else {
/* 1277 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1278 */           CompatInfo compatInfo = paramVector.elementAt(b);
/* 1279 */           String str = compatInfo.brandcd_fc_xml;
/* 1280 */           if (str1.equals(str)) {
/* 1281 */             vector2.add(paramVector.get(b));
/*      */           }
/*      */         } 
/* 1284 */         processMQ(paramADSABRSTATUS, paramProfile, vector2, vector1);
/*      */         
/* 1286 */         vector2.clear();
/*      */       } 
/*      */       
/* 1289 */       paramVector.clear();
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
/* 1300 */     String str = "";
/* 1301 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1);
/* 1302 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 1304 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1305 */       StringBuffer stringBuffer = new StringBuffer();
/* 1306 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 1308 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 1310 */           if (stringBuffer.length() > 0) {
/* 1311 */             stringBuffer.append(",");
/*      */           }
/* 1313 */           if (paramString2.equals("short")) {
/* 1314 */             stringBuffer.append(arrayOfMetaFlag[b].getShortDescription());
/* 1315 */           } else if (paramString2.equals("long")) {
/* 1316 */             stringBuffer.append(arrayOfMetaFlag[b].getLongDescription());
/* 1317 */           } else if (paramString2.equals("flag")) {
/* 1318 */             stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/*      */           } else {
/*      */             
/* 1321 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */           } 
/*      */         } 
/*      */       } 
/* 1325 */       str = stringBuffer.toString();
/*      */     } 
/* 1327 */     return str;
/*      */   }
/*      */   
/*      */   private String convertValue(String paramString) {
/* 1331 */     return (paramString == null) ? "" : paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1339 */     return "1.6";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMQCID() {
/* 1346 */     return "WWCOMPAT_UPDATE";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getStatusAttr() {
/* 1351 */     return "ADSABRSTATUS";
/*      */   }
/*      */   
/*      */   private static class CompatInfo {
/* 1355 */     String activity_xml = "@@";
/* 1356 */     String systementitytype_xml = "@@";
/* 1357 */     String systementityid_xml = "@@";
/* 1358 */     String systemos_xml = "@@";
/* 1359 */     String groupentitytype_xml = "@@";
/* 1360 */     String groupentityid_xml = "@@";
/* 1361 */     String oktopub_xml = "@@";
/* 1362 */     String osentitytype_xml = "@@";
/* 1363 */     String osentityid_xml = "@@";
/* 1364 */     String os_xml = "@@";
/* 1365 */     String optionentitytype_xml = "@@";
/* 1366 */     String optionentityid_xml = "@@";
/* 1367 */     String compatibilitypublishingflag_xml = "@@";
/* 1368 */     String relationshiptype_xml = "@@";
/* 1369 */     String publishfrom_xml = "@@";
/* 1370 */     String publishto_xml = "@@";
/* 1371 */     String brandcd_fc_xml = "@@";
/* 1372 */     String systemmachtype_xml = "@@";
/* 1373 */     String systemmodel_xml = "@@";
/* 1374 */     String systemseoid_xml = "@@";
/* 1375 */     String optionseoid_xml = "@@";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1400 */       if (param1String1.equals("A") || param1String1.equals("C")) {
/* 1401 */         this.activity_xml = "Update";
/*      */       } else {
/* 1403 */         this.activity_xml = "Delete";
/*      */       } 
/* 1405 */       if (param1String2 != null) {
/* 1406 */         this.systementitytype_xml = param1String2.trim();
/*      */       }
/*      */       
/* 1409 */       if (param1Int1 != 0) {
/* 1410 */         this.systementityid_xml = Integer.toString(param1Int1);
/*      */       }
/*      */       
/* 1413 */       if (param1String3 != null) {
/* 1414 */         this.systemos_xml = param1String3.trim();
/*      */       }
/*      */       
/* 1417 */       if (param1String4 != null) {
/* 1418 */         this.groupentitytype_xml = param1String4.trim();
/*      */       }
/*      */       
/* 1421 */       if (param1Int2 != 0) {
/* 1422 */         this.groupentityid_xml = Integer.toString(param1Int2);
/*      */       }
/*      */       
/* 1425 */       if (param1String5 != null) {
/* 1426 */         this.oktopub_xml = param1String5.trim();
/*      */       }
/* 1428 */       if (param1String6 != null) {
/* 1429 */         this.osentitytype_xml = param1String6.trim();
/*      */       }
/*      */       
/* 1432 */       if (param1Int3 != 0) {
/* 1433 */         this.osentityid_xml = Integer.toString(param1Int3);
/*      */       }
/*      */       
/* 1436 */       if (param1String7 != null) {
/* 1437 */         this.os_xml = param1String7.trim();
/*      */       }
/*      */       
/* 1440 */       if (param1String8 != null) {
/* 1441 */         this.optionentitytype_xml = param1String8.trim();
/*      */       }
/*      */       
/* 1444 */       if (param1Int4 != 0) {
/* 1445 */         this.optionentityid_xml = Integer.toString(param1Int4);
/*      */       }
/*      */       
/* 1448 */       if (param1String9 != null) {
/* 1449 */         this.compatibilitypublishingflag_xml = param1String9.trim();
/*      */       }
/*      */       
/* 1452 */       if (param1String10 != null) {
/* 1453 */         this.relationshiptype_xml = param1String10.trim();
/*      */       }
/*      */       
/* 1456 */       if (param1String11 != null) {
/* 1457 */         this.publishfrom_xml = param1String11.trim();
/*      */       }
/*      */       
/* 1460 */       if (param1String12 != null) {
/* 1461 */         this.publishto_xml = param1String12.trim();
/*      */       }
/*      */       
/* 1464 */       if (param1String13 != null) {
/* 1465 */         this.brandcd_fc_xml = param1String13.trim();
/*      */       }
/*      */       
/* 1468 */       if (param1String14 != null) {
/* 1469 */         this.systemmachtype_xml = param1String14.trim();
/*      */       }
/*      */       
/* 1472 */       if (param1String15 != null) {
/* 1473 */         this.systemmodel_xml = param1String15.trim();
/*      */       }
/*      */       
/* 1476 */       if (param1String16 != null) {
/* 1477 */         this.systemseoid_xml = param1String16.trim();
/*      */       }
/*      */       
/* 1480 */       if (param1String17 != null) {
/* 1481 */         this.optionseoid_xml = param1String17.trim();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void dereference() {
/* 1487 */       this.activity_xml = null;
/* 1488 */       this.systementitytype_xml = null;
/* 1489 */       this.systementityid_xml = null;
/* 1490 */       this.systemos_xml = null;
/* 1491 */       this.groupentitytype_xml = null;
/* 1492 */       this.groupentityid_xml = null;
/* 1493 */       this.oktopub_xml = null;
/* 1494 */       this.osentitytype_xml = null;
/* 1495 */       this.osentityid_xml = null;
/* 1496 */       this.os_xml = null;
/* 1497 */       this.optionentitytype_xml = null;
/* 1498 */       this.optionentityid_xml = null;
/* 1499 */       this.compatibilitypublishingflag_xml = null;
/* 1500 */       this.relationshiptype_xml = null;
/* 1501 */       this.publishfrom_xml = null;
/* 1502 */       this.publishto_xml = null;
/* 1503 */       this.brandcd_fc_xml = null;
/* 1504 */       this.systemmachtype_xml = null;
/* 1505 */       this.systemmodel_xml = null;
/* 1506 */       this.systemseoid_xml = null;
/* 1507 */       this.optionseoid_xml = null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSWWCOMPATXMLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */