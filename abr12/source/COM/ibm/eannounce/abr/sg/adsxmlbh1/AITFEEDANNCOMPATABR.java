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
/*     */ import java.util.HashSet;
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
/*     */ public class AITFEEDANNCOMPATABR
/*     */   extends AITFEEDXML
/*     */ {
/*     */   public void processThis(AITFEEDABRSTATUS paramAITFEEDABRSTATUS, Profile paramProfile, EntityItem paramEntityItem) throws FileNotFoundException, TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException {
/*  42 */     AITFEEDSimpleSaxXML aITFEEDSimpleSaxXML = null;
/*     */     try {
/*  44 */       aITFEEDSimpleSaxXML = new AITFEEDSimpleSaxXML(ABRServerProperties.getOutputPath() + getXMLFileName() + paramAITFEEDABRSTATUS.getABRTime());
/*  45 */       aITFEEDSimpleSaxXML.startDocument();
/*  46 */       aITFEEDSimpleSaxXML.startElement("ANNCOMPAT", aITFEEDSimpleSaxXML.createAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/ANNCOMPAT"));
/*  47 */       aITFEEDSimpleSaxXML.addElement("ANNNUMBER", getAttributeValue(paramEntityItem, "ANNNUMBER"));
/*  48 */       aITFEEDSimpleSaxXML.addElement("INVENTORYGROUP", getAttributeValue(paramEntityItem, "INVENTORYGROUP"));
/*  49 */       aITFEEDSimpleSaxXML.addElement("ANNTYPE", getAttributeValue(paramEntityItem, "ANNTYPE"));
/*     */       
/*  51 */       aITFEEDSimpleSaxXML.startElement("AVAILABILITYLIST");
/*  52 */       Vector<EntityItem> vector = new Vector(1);
/*  53 */       String str1 = getVeName();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  58 */       EntityList entityList = paramAITFEEDABRSTATUS.getEntityList(paramProfile, str1, paramEntityItem);
/*     */       
/*  60 */       paramAITFEEDABRSTATUS.addDebug("EntityList for " + paramProfile.getValOn() + " extract " + str1 + " contains the following entities: \n" + 
/*  61 */           PokUtils.outputList(entityList));
/*  62 */       EntityGroup entityGroup = entityList.getEntityGroup("AVAIL");
/*  63 */       if (entityGroup != null) {
/*  64 */         EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*  65 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  66 */           for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  67 */             EntityItem entityItem = arrayOfEntityItem[b1];
/*     */ 
/*     */             
/*  70 */             String str = getAttributeFlagValue(entityItem, "AVAILTYPE");
/*  71 */             if (AVAILTYPE_FILTER.contains(str)) {
/*  72 */               vector.add(entityItem);
/*     */               
/*  74 */               aITFEEDSimpleSaxXML.startElement("AVAILABILITYELEMENT");
/*  75 */               aITFEEDSimpleSaxXML.addElement("ENTITYID", String.valueOf(entityItem.getEntityID()));
/*  76 */               aITFEEDSimpleSaxXML.addElement("AVAILTYPE", getAttributeValue(entityItem, "AVAILTYPE"));
/*  77 */               aITFEEDSimpleSaxXML.addElement("EFFECTIVEDATE", getAttributeValue(entityItem, "EFFECTIVEDATE"));
/*  78 */               createCountryListElement(aITFEEDSimpleSaxXML, entityItem, paramAITFEEDABRSTATUS);
/*  79 */               aITFEEDSimpleSaxXML.endElement("AVAILABILITYELEMENT");
/*     */             } else {
/*  81 */               paramAITFEEDABRSTATUS.addDebug("Ignore AVAILTYPE:" + str + " for " + entityItem.getKey());
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*  86 */       aITFEEDSimpleSaxXML.endElement("AVAILABILITYLIST");
/*     */ 
/*     */       
/*  89 */       aITFEEDSimpleSaxXML.startElement("COMPATLIST");
/*  90 */       String str2 = getVeName2();
/*  91 */       Vector<AITFEEDXMLRelator> vector1 = new Vector(1);
/*  92 */       HashSet<String> hashSet = new HashSet();
/*  93 */       Vector<String> vector2 = new Vector(1);
/*  94 */       Vector<String> vector3 = new Vector(); byte b;
/*  95 */       for (b = 0; b < vector.size(); b++) {
/*  96 */         EntityItem entityItem = vector.get(b);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 101 */         EntityList entityList1 = paramAITFEEDABRSTATUS.getEntityList(paramProfile, str2, entityItem);
/*     */         
/* 103 */         paramAITFEEDABRSTATUS.addDebug("EntityList for " + paramProfile.getValOn() + " extract " + str2 + " contains the following entities: \n" + 
/* 104 */             PokUtils.outputList(entityList1));
/* 105 */         EntityGroup entityGroup1 = entityList1.getEntityGroup("MODELAVAIL");
/* 106 */         if (entityGroup1 != null) {
/* 107 */           EntityItem[] arrayOfEntityItem = entityGroup1.getEntityItemsAsArray();
/* 108 */           if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 109 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 110 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 111 */               Vector<EntityItem> vector4 = getUpOrDownRelatorEntityItems(entityItem1, "UP", "MODEL");
/* 112 */               if (vector4 != null && vector4.size() > 0)
/* 113 */               { for (byte b2 = 0; b2 < vector4.size(); b2++) {
/* 114 */                   EntityItem entityItem2 = vector4.get(b2);
/*     */                   
/* 116 */                   String str = String.valueOf(entityItem2.getEntityID());
/*     */ 
/*     */                   
/* 119 */                   AITFEEDXMLRelator aITFEEDXMLRelator = new AITFEEDXMLRelator();
/* 120 */                   aITFEEDXMLRelator.setEntity1ID(str);
/* 121 */                   aITFEEDXMLRelator.setEntity2ID(String.valueOf(entityItem.getEntityID()));
/* 122 */                   vector1.add(aITFEEDXMLRelator);
/*     */ 
/*     */                   
/* 125 */                   if (vector2.contains(str)) {
/* 126 */                     paramAITFEEDABRSTATUS.addDebug("Ignore duplicate MODEL entity id:" + str);
/*     */                   } else {
/*     */                     
/* 129 */                     vector2.add(str);
/*     */ 
/*     */                     
/* 132 */                     Vector<EntityItem> vector5 = getUpOrDownEntityItems(entityItem2, "UP", "MDLCGMDL", "MODELCG");
/* 133 */                     if (vector5 != null && vector5.size() > 0) {
/* 134 */                       for (byte b3 = 0; b3 < vector5.size(); b3++) {
/* 135 */                         EntityItem entityItem3 = vector5.get(b3);
/* 136 */                         String str3 = getAttributeValue(entityItem3, "OKTOPUB");
/*     */                         
/* 138 */                         Vector<EntityItem> vector6 = getUpOrDownEntityItems(entityItem3, "DOWN", "MDLCGMDLCGOS", "MODELCGOS");
/* 139 */                         if (vector6 != null && vector6.size() > 0) {
/* 140 */                           for (byte b4 = 0; b4 < vector6.size(); b4++) {
/* 141 */                             EntityItem entityItem4 = vector6.get(b4);
/*     */                             
/* 143 */                             Vector<EntityItem> vector7 = getUpOrDownEntityItems(entityItem4, "DOWN", "MDLCGOSMDL", "MODEL");
/* 144 */                             if (vector7 != null && vector7.size() > 0) {
/* 145 */                               for (byte b5 = 0; b5 < vector7.size(); b5++) {
/* 146 */                                 EntityItem entityItem5 = vector7.get(b5);
/*     */                                 
/* 148 */                                 String str4 = String.valueOf(entityItem5.getEntityID());
/* 149 */                                 String str5 = str + str3 + str4;
/*     */                                 
/* 151 */                                 if (vector3.contains(str5)) {
/* 152 */                                   paramAITFEEDABRSTATUS.addDebug("Ignore duplicate COMPAT " + str5 + " for " + entityItem3.getKey() + " for " + entityItem4.getKey());
/*     */                                 } else {
/* 154 */                                   vector3.add(str5);
/* 155 */                                   hashSet.add(str);
/*     */                                   
/* 157 */                                   aITFEEDSimpleSaxXML.startElement("COMPATELEMENT");
/* 158 */                                   aITFEEDSimpleSaxXML.addElement("FROMENTITYID", str);
/* 159 */                                   aITFEEDSimpleSaxXML.addElement("FROMMACHTYPE", getAttributeValue(entityItem2, "MACHTYPEATR"));
/* 160 */                                   aITFEEDSimpleSaxXML.addElement("FROMMODEL", getAttributeValue(entityItem2, "MODELATR"));
/* 161 */                                   aITFEEDSimpleSaxXML.addElement("OKTOPUB", str3);
/* 162 */                                   aITFEEDSimpleSaxXML.addElement("TOENTITYID", str4);
/* 163 */                                   aITFEEDSimpleSaxXML.addElement("TOMACHTYPE", getAttributeValue(entityItem5, "MACHTYPEATR"));
/* 164 */                                   aITFEEDSimpleSaxXML.addElement("TOMODEL", getAttributeValue(entityItem5, "MODELATR"));
/* 165 */                                   aITFEEDSimpleSaxXML.addElement("MKTGNAME", getAttributeValue(entityItem5, "MKTGNAME"));
/* 166 */                                   aITFEEDSimpleSaxXML.addElement("COMPATDVCCAT", getAttributeValue(entityItem5, "COMPATDVCCAT"));
/* 167 */                                   aITFEEDSimpleSaxXML.addElement("COMPATDVCSUBCAT", getAttributeValue(entityItem5, "COMPATDVCSUBCAT"));
/* 168 */                                   aITFEEDSimpleSaxXML.endElement("COMPATELEMENT");
/*     */                                 } 
/*     */                               } 
/*     */                             } else {
/* 172 */                               paramAITFEEDABRSTATUS.addDebug("Not found option MODEL for " + entityItem3.getKey() + " for " + entityItem2.getKey() + " for " + entityItem4.getKey());
/*     */                             } 
/*     */                           } 
/*     */                         } else {
/* 176 */                           paramAITFEEDABRSTATUS.addDebug("Not found MODELCGOS for " + entityItem3.getKey() + " for " + entityItem2.getKey());
/*     */                         } 
/*     */                       } 
/*     */                     } else {
/* 180 */                       paramAITFEEDABRSTATUS.addDebug("Not found MODELCG for " + entityItem2.getKey());
/*     */                     } 
/*     */                   } 
/*     */                 }  }
/* 184 */               else { paramAITFEEDABRSTATUS.addDebug("Not found MODEL for " + entityItem1.getKey()); }
/*     */             
/*     */             } 
/*     */           }
/*     */         } 
/* 189 */         entityList1.dereference();
/* 190 */         entityList1 = null;
/*     */       } 
/* 192 */       aITFEEDSimpleSaxXML.endElement("COMPATLIST");
/*     */ 
/*     */       
/* 195 */       aITFEEDSimpleSaxXML.startElement("RELATORLIST");
/* 196 */       for (b = 0; b < vector1.size(); b++) {
/* 197 */         AITFEEDXMLRelator aITFEEDXMLRelator = vector1.get(b);
/* 198 */         if (hashSet.contains(aITFEEDXMLRelator.getEntity1ID())) {
/* 199 */           aITFEEDSimpleSaxXML.startElement("RELATORELEMENT");
/* 200 */           aITFEEDSimpleSaxXML.addElement("ENTITY1TYPE", "MODEL");
/* 201 */           aITFEEDSimpleSaxXML.addElement("ENTITY1ID", aITFEEDXMLRelator.getEntity1ID());
/* 202 */           aITFEEDSimpleSaxXML.addElement("ENTITY2TYPE", "AVAIL");
/* 203 */           aITFEEDSimpleSaxXML.addElement("ENTITY2ID", aITFEEDXMLRelator.getEntity2ID());
/* 204 */           aITFEEDSimpleSaxXML.endElement("RELATORELEMENT");
/*     */         } else {
/* 206 */           paramAITFEEDABRSTATUS.addDebug("The MODEL not dispaly in the COMPATLIST, it will not display in RELATORLIST for MODEL" + aITFEEDXMLRelator.getEntity1ID());
/*     */         } 
/*     */       } 
/* 209 */       aITFEEDSimpleSaxXML.endElement("RELATORLIST");
/*     */       
/* 211 */       aITFEEDSimpleSaxXML.endElement("ANNCOMPAT");
/* 212 */       aITFEEDSimpleSaxXML.endDocument();
/*     */       
/* 214 */       vector1.clear();
/* 215 */       vector1 = null;
/* 216 */       vector.clear();
/* 217 */       vector = null;
/* 218 */       entityList.dereference();
/* 219 */       entityList = null;
/* 220 */       vector2.clear();
/* 221 */       vector2 = null;
/* 222 */       vector3.clear();
/* 223 */       vector3 = null;
/* 224 */       hashSet.clear();
/* 225 */       hashSet = null;
/*     */     } finally {
/* 227 */       if (aITFEEDSimpleSaxXML != null) {
/* 228 */         aITFEEDSimpleSaxXML.close();
/*     */       }
/*     */     } 
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
/*     */   private Vector getUpOrDownRelatorEntityItems(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 242 */     Vector<EntityItem> vector2, vector1 = null;
/*     */     
/* 244 */     if ("UP".equalsIgnoreCase(paramString1)) {
/* 245 */       vector2 = paramEntityItem.getUpLink();
/*     */     } else {
/* 247 */       vector2 = paramEntityItem.getDownLink();
/*     */     } 
/* 249 */     if (vector2 != null && vector2.size() > 0) {
/* 250 */       vector1 = new Vector(1);
/* 251 */       for (byte b = 0; b < vector2.size(); b++) {
/* 252 */         EntityItem entityItem = vector2.get(b);
/* 253 */         if (entityItem.getEntityType().equals(paramString2)) {
/* 254 */           vector1.add(entityItem);
/*     */         }
/*     */       } 
/*     */     } 
/* 258 */     return vector1;
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
/*     */   private Vector getUpOrDownEntityItems(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3) {
/* 270 */     Vector<EntityItem> vector2, vector1 = null;
/*     */     
/* 272 */     if ("UP".equalsIgnoreCase(paramString1)) {
/* 273 */       vector2 = paramEntityItem.getUpLink();
/*     */     } else {
/* 275 */       vector2 = paramEntityItem.getDownLink();
/*     */     } 
/* 277 */     if (vector2 != null && vector2.size() > 0) {
/* 278 */       vector1 = new Vector(1);
/* 279 */       for (byte b = 0; b < vector2.size(); b++) {
/* 280 */         EntityItem entityItem = vector2.get(b);
/* 281 */         if (entityItem.getEntityType().equals(paramString2)) {
/*     */           Vector<EntityItem> vector;
/* 283 */           if ("UP".equalsIgnoreCase(paramString1)) {
/* 284 */             vector = entityItem.getUpLink();
/*     */           } else {
/* 286 */             vector = entityItem.getDownLink();
/*     */           } 
/* 288 */           if (vector != null && vector.size() > 0) {
/* 289 */             for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 290 */               EntityItem entityItem1 = vector.get(b1);
/* 291 */               if (entityItem1.getEntityType().equals(paramString3)) {
/* 292 */                 vector1.add(entityItem1);
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 299 */     return vector1;
/*     */   }
/*     */   
/*     */   public String getVeName() {
/* 303 */     return "AITANNCOMPAT";
/*     */   }
/*     */   
/*     */   private String getVeName2() {
/* 307 */     return "AITANNCOMPAT2";
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 311 */     return "$Revision: 1.2 $";
/*     */   }
/*     */   
/*     */   public String getXMLFileName() {
/* 315 */     return "ANNCOMPAT.xml";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\AITFEEDANNCOMPATABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */