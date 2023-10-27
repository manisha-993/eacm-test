/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AITFEEDANNMODELCONVERTABR
/*     */   extends AITFEEDXML
/*     */ {
/*     */   public void processThis(AITFEEDABRSTATUS paramAITFEEDABRSTATUS, Profile paramProfile, EntityItem paramEntityItem) throws FileNotFoundException, TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException {
/*  37 */     EntityList entityList = paramAITFEEDABRSTATUS.getEntityList(paramProfile, getVeName(), paramEntityItem);
/*  38 */     paramAITFEEDABRSTATUS.addDebug("EntityList for " + paramProfile.getValOn() + " extract " + getVeName() + " contains the following entities: \n" + 
/*  39 */         PokUtils.outputList(entityList));
/*  40 */     EntityGroup entityGroup1 = entityList.getEntityGroup("AVAIL");
/*  41 */     EntityGroup entityGroup2 = entityList.getEntityGroup("MODELCONVERT");
/*  42 */     EntityGroup entityGroup3 = entityList.getEntityGroup("MODELCONVERTAVAIL");
/*     */     
/*  44 */     AITFEEDSimpleSaxXML aITFEEDSimpleSaxXML = null;
/*     */     try {
/*  46 */       aITFEEDSimpleSaxXML = new AITFEEDSimpleSaxXML(ABRServerProperties.getOutputPath() + getXMLFileName() + paramAITFEEDABRSTATUS.getABRTime());
/*  47 */       aITFEEDSimpleSaxXML.startDocument();
/*  48 */       aITFEEDSimpleSaxXML.startElement("ANNMODELCONVERT", aITFEEDSimpleSaxXML.createAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/ANNMODELCONVERT"));
/*  49 */       aITFEEDSimpleSaxXML.addElement("ANNNUMBER", getAttributeValue(paramEntityItem, "ANNNUMBER"));
/*  50 */       aITFEEDSimpleSaxXML.addElement("INVENTORYGROUP", getAttributeValue(paramEntityItem, "INVENTORYGROUP"));
/*  51 */       aITFEEDSimpleSaxXML.addElement("ANNTYPE", getAttributeValue(paramEntityItem, "ANNTYPE"));
/*  52 */       aITFEEDSimpleSaxXML.addElement("PDHDOMAIN", getAttributeValue(paramEntityItem, "PDHDOMAIN"));
/*     */       
/*  54 */       aITFEEDSimpleSaxXML.startElement("MODELCONVERTLIST");
/*  55 */       Vector<String> vector1 = new Vector(1);
/*  56 */       if (entityGroup2 != null) {
/*  57 */         EntityItem[] arrayOfEntityItem = entityGroup2.getEntityItemsAsArray();
/*  58 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0)
/*  59 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  60 */             EntityItem entityItem = arrayOfEntityItem[b];
/*  61 */             String str = String.valueOf(entityItem.getEntityID());
/*  62 */             if (vector1.contains(str)) {
/*  63 */               paramAITFEEDABRSTATUS.addDebug("Ignore duplicate " + entityItem.getKey());
/*     */             } else {
/*     */               
/*  66 */               vector1.add(str);
/*     */               
/*  68 */               aITFEEDSimpleSaxXML.startElement("MODELCONVERTELEMENT");
/*  69 */               aITFEEDSimpleSaxXML.addElement("ENTITYID", str);
/*  70 */               aITFEEDSimpleSaxXML.addElement("FROMMACHTYPE", getAttributeValue(entityItem, "FROMMACHTYPE"));
/*  71 */               aITFEEDSimpleSaxXML.addElement("FROMMODEL", getAttributeValue(entityItem, "FROMMODEL"));
/*  72 */               aITFEEDSimpleSaxXML.addElement("TOMACHTYPE", getAttributeValue(entityItem, "TOMACHTYPE"));
/*  73 */               aITFEEDSimpleSaxXML.addElement("TOMODEL", getAttributeValue(entityItem, "TOMODEL"));
/*  74 */               aITFEEDSimpleSaxXML.addElement("RETURNEDPARTSMES", getAttributeValue(entityItem, "RETURNEDPARTS"));
/*  75 */               aITFEEDSimpleSaxXML.endElement("MODELCONVERTELEMENT");
/*     */             } 
/*     */           }  
/*     */       } 
/*  79 */       aITFEEDSimpleSaxXML.endElement("MODELCONVERTLIST");
/*     */       
/*  81 */       Vector<String> vector2 = new Vector(1);
/*     */       
/*  83 */       aITFEEDSimpleSaxXML.startElement("AVAILABILITYLIST");
/*  84 */       if (entityGroup1 != null) {
/*  85 */         EntityItem[] arrayOfEntityItem = entityGroup1.getEntityItemsAsArray();
/*  86 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  87 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  88 */             EntityItem entityItem = arrayOfEntityItem[b];
/*  89 */             String str1 = String.valueOf(entityItem.getEntityID());
/*  90 */             String str2 = getAttributeFlagValue(entityItem, "AVAILTYPE");
/*  91 */             if (AVAILTYPE_FILTER.contains(str2)) {
/*  92 */               vector2.add(str1);
/*  93 */               aITFEEDSimpleSaxXML.startElement("AVAILABILITYELEMENT");
/*  94 */               aITFEEDSimpleSaxXML.addElement("ENTITYID", str1);
/*  95 */               aITFEEDSimpleSaxXML.addElement("AVAILTYPE", getAttributeValue(entityItem, "AVAILTYPE"));
/*  96 */               aITFEEDSimpleSaxXML.addElement("EFFECTIVEDATE", getAttributeValue(entityItem, "EFFECTIVEDATE"));
/*  97 */               createCountryListElement(aITFEEDSimpleSaxXML, entityItem, paramAITFEEDABRSTATUS);
/*  98 */               aITFEEDSimpleSaxXML.endElement("AVAILABILITYELEMENT");
/*     */             } else {
/* 100 */               paramAITFEEDABRSTATUS.addDebug("Ignore AVAILTYPE:" + str2 + " for " + entityItem.getKey());
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 105 */       aITFEEDSimpleSaxXML.endElement("AVAILABILITYLIST");
/*     */ 
/*     */       
/* 108 */       aITFEEDSimpleSaxXML.startElement("RELATORLIST");
/* 109 */       if (entityGroup3 != null) {
/* 110 */         EntityItem[] arrayOfEntityItem = entityGroup3.getEntityItemsAsArray();
/* 111 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 112 */           for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 113 */             EntityItem entityItem = arrayOfEntityItem[b];
/* 114 */             String str1 = "" + entityItem.getUpLink(0).getEntityID();
/* 115 */             String str2 = "" + entityItem.getDownLink(0).getEntityID();
/* 116 */             if (vector2.contains(str2)) {
/* 117 */               aITFEEDSimpleSaxXML.startElement("RELATORELEMENT");
/* 118 */               aITFEEDSimpleSaxXML.addElement("ENTITY1TYPE", "MODELCONVERT");
/* 119 */               aITFEEDSimpleSaxXML.addElement("ENTITY1ID", str1);
/* 120 */               aITFEEDSimpleSaxXML.addElement("ENTITY2TYPE", "AVAIL");
/* 121 */               aITFEEDSimpleSaxXML.addElement("ENTITY2ID", str2);
/* 122 */               aITFEEDSimpleSaxXML.endElement("RELATORELEMENT");
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 127 */       aITFEEDSimpleSaxXML.endElement("RELATORLIST");
/* 128 */       aITFEEDSimpleSaxXML.endElement("ANNMODELCONVERT");
/* 129 */       aITFEEDSimpleSaxXML.endDocument();
/*     */       
/* 131 */       vector1.clear();
/* 132 */       vector1 = null;
/* 133 */       vector2.clear();
/* 134 */       vector2 = null;
/* 135 */       entityList.dereference();
/* 136 */       entityList = null;
/*     */     } finally {
/* 138 */       if (aITFEEDSimpleSaxXML != null) {
/* 139 */         aITFEEDSimpleSaxXML.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 145 */     return "$Revision: 1.1 $";
/*     */   }
/*     */   
/*     */   public String getXMLFileName() {
/* 149 */     return "ANNMODELCONVERT.xml";
/*     */   }
/*     */   
/*     */   public String getVeName() {
/* 153 */     return "AITANNMODELCONVERT";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\AITFEEDANNMODELCONVERTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */