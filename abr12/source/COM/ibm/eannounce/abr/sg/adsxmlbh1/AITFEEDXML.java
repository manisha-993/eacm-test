/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AITFEEDXML
/*     */ {
/*     */   protected static final String ANNTYPE_NEW = "19";
/*     */   protected static final String ANNTYPE_EOL = "14";
/*     */   protected static final int FLAGVAL = 0;
/*     */   protected static final int SHORTDESC = 1;
/*  51 */   protected static final Vector AVAILTYPE_FILTER = new Vector(2); static {
/*  52 */     AVAILTYPE_FILTER.add("146");
/*  53 */     AVAILTYPE_FILTER.add("149");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  58 */   protected static final Vector INVALID_COUNTRYCODE = new Vector(2); static {
/*  59 */     INVALID_COUNTRYCODE.add("1667");
/*  60 */     INVALID_COUNTRYCODE.add("2001");
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
/*     */   protected void createCountryListElement(AITFEEDSimpleSaxXML paramAITFEEDSimpleSaxXML, EntityItem paramEntityItem, AITFEEDABRSTATUS paramAITFEEDABRSTATUS) throws SAXException {
/*  72 */     paramAITFEEDSimpleSaxXML.startElement("COUNTRYLIST");
/*  73 */     Vector<String> vector = getMultiFlagAttributeValues(paramEntityItem, "COUNTRYLIST", 0);
/*  74 */     if (vector != null && vector.size() > 0) {
/*  75 */       for (byte b = 0; b < vector.size(); b++) {
/*  76 */         String str = vector.get(b);
/*  77 */         if (INVALID_COUNTRYCODE.contains(str)) {
/*  78 */           paramAITFEEDABRSTATUS.addDebug("Ignore invalid country code:" + str + " for " + paramEntityItem.getKey());
/*     */         } else {
/*     */           
/*  81 */           String str1 = paramAITFEEDABRSTATUS.getGenareaName(str);
/*  82 */           if (str1 == null) {
/*  83 */             paramAITFEEDABRSTATUS.addDebug("Can't get GenareName for country code:" + (String)vector.get(b) + " for AVAIL:" + paramEntityItem.getKey());
/*     */           }
/*  85 */           paramAITFEEDSimpleSaxXML.addElement("COUNTRY_FC", (str1 != null) ? str1 : "");
/*     */         } 
/*     */       } 
/*  88 */       vector.clear();
/*  89 */       vector = null;
/*     */     } 
/*  91 */     paramAITFEEDSimpleSaxXML.endElement("COUNTRYLIST");
/*     */   }
/*     */   
/*     */   protected Vector getMultiFlagAttributeValues(EntityItem paramEntityItem, String paramString, int paramInt) {
/*  95 */     Vector<String> vector = null;
/*  96 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString);
/*  97 */     if (eANMetaAttribute.getAttributeType().equals("F")) {
/*     */       
/*  99 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/* 100 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/* 101 */         vector = new Vector(1);
/*     */         
/* 103 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 104 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 106 */           if (arrayOfMetaFlag[b].isSelected())
/*     */           {
/* 108 */             if (paramInt == 0) {
/* 109 */               vector.addElement(arrayOfMetaFlag[b].getFlagCode());
/* 110 */             } else if (paramInt == 1) {
/* 111 */               vector.addElement(arrayOfMetaFlag[b].getShortDescription());
/*     */             } else {
/* 113 */               vector.addElement(arrayOfMetaFlag[b].toString());
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 119 */     return vector;
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
/*     */   protected String getAttributeFlagValue(EntityItem paramEntityItem, String paramString) {
/* 132 */     return PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*     */   }
/*     */   
/*     */   protected String getAttributeValue(EntityItem paramEntityItem, String paramString) {
/* 136 */     return PokUtils.getAttributeValue(paramEntityItem, paramString, ",", "", false);
/*     */   }
/*     */   
/*     */   public abstract void processThis(AITFEEDABRSTATUS paramAITFEEDABRSTATUS, Profile paramProfile, EntityItem paramEntityItem) throws FileNotFoundException, TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException;
/*     */   
/*     */   public abstract String getVeName();
/*     */   
/*     */   public abstract String getVersion();
/*     */   
/*     */   public abstract String getXMLFileName();
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\AITFEEDXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */