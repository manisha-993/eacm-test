/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
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
/*     */ public class AITFEEDANNTMFABR
/*     */   extends AITFEEDXML
/*     */ {
/*     */   public void processThis(AITFEEDABRSTATUS paramAITFEEDABRSTATUS, Profile paramProfile, EntityItem paramEntityItem) throws FileNotFoundException, TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException {
/*  53 */     Vector<EntityItem> vector1 = new Vector();
/*  54 */     Vector<EntityItem> vector2 = new Vector();
/*  55 */     Vector<String> vector3 = new Vector();
/*  56 */     Vector<EntityItem> vector4 = new Vector();
/*  57 */     Vector<String> vector5 = new Vector();
/*  58 */     Vector<AITFEEDXMLRelator> vector = new Vector();
/*     */ 
/*     */     
/*  61 */     EntityList entityList = paramAITFEEDABRSTATUS.getEntityList(paramProfile, getVeName(), paramEntityItem);
/*  62 */     paramAITFEEDABRSTATUS.addDebug("EntityList for " + paramProfile.getValOn() + " extract " + 
/*  63 */         getVeName() + " contains the following entities: \n" + 
/*  64 */         PokUtils.outputList(entityList));
/*  65 */     EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*     */     
/*  67 */     Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(entityItem, "ANNAVAILA", "AVAIL");
/*  68 */     for (byte b = 0; b < vector6.size(); b++) {
/*  69 */       EntityItem entityItem1 = vector6.get(b);
/*     */ 
/*     */       
/*  72 */       String str = getAttributeFlagValue(entityItem1, "AVAILTYPE");
/*  73 */       if (!AVAILTYPE_FILTER.contains(str)) {
/*  74 */         paramAITFEEDABRSTATUS.addDebug("skip " + entityItem1.getKey() + " for AVAILTYPE " + str);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/*  80 */         EntityList entityList1 = paramAITFEEDABRSTATUS.getEntityList(paramProfile, getVeName2(), entityItem1);
/*  81 */         paramAITFEEDABRSTATUS.addDebug("EntityList for " + paramProfile.getValOn() + " extract " + 
/*  82 */             getVeName2() + " contains the following entities: \n" + 
/*  83 */             PokUtils.outputList(entityList1));
/*  84 */         EntityItem entityItem2 = entityList1.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */         
/*  87 */         Vector<EntityItem> vector7 = PokUtils.getAllLinkedEntities(entityItem2, "MODELAVAIL", "MODEL");
/*  88 */         paramAITFEEDABRSTATUS.addDebug("model size " + vector7.size());
/*  89 */         Vector<EntityItem> vector8 = PokUtils.getAllLinkedEntities(entityItem2, "OOFAVAIL", "PRODSTRUCT");
/*  90 */         paramAITFEEDABRSTATUS.addDebug("tmf size " + vector8.size());
/*  91 */         if (vector7.size() > 0 || vector8.size() > 0) {
/*  92 */           vector1.add(entityItem2);
/*     */         } else {
/*  94 */           paramAITFEEDABRSTATUS.addDebug("The " + entityItem2.getKey() + " didn't link model and tmf");
/*     */         } 
/*     */         byte b1;
/*  97 */         for (b1 = 0; b1 < vector7.size(); b1++) {
/*  98 */           EntityItem entityItem3 = vector7.get(b1);
/*  99 */           if (!vector3.contains("" + entityItem3.getEntityID())) {
/* 100 */             vector3.add("" + entityItem3.getEntityID());
/* 101 */             vector2.add(entityItem3);
/*     */           } else {
/* 103 */             paramAITFEEDABRSTATUS.addDebug("duplicate " + entityItem3.getKey());
/*     */           } 
/* 105 */           vector.add(new AITFEEDXMLRelator("MODEL", "" + entityItem3.getEntityID(), "AVAIL", "" + entityItem1.getEntityID()));
/*     */         } 
/*     */         
/* 108 */         for (b1 = 0; b1 < vector8.size(); b1++) {
/* 109 */           EntityItem entityItem3 = vector8.get(b1);
/* 110 */           if (!vector5.contains("" + entityItem3.getEntityID())) {
/* 111 */             vector5.add("" + entityItem3.getEntityID());
/* 112 */             vector4.add(entityItem3);
/*     */           } else {
/* 114 */             paramAITFEEDABRSTATUS.addDebug("duplicate " + entityItem3.getKey());
/*     */           } 
/* 116 */           vector.add(new AITFEEDXMLRelator("PRODSTRUCT", "" + entityItem3.getEntityID(), "AVAIL", "" + entityItem1.getEntityID()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     AITFEEDSimpleSaxXML aITFEEDSimpleSaxXML = null;
/*     */     try {
/* 123 */       aITFEEDSimpleSaxXML = new AITFEEDSimpleSaxXML(ABRServerProperties.getOutputPath() + getXMLFileName() + paramAITFEEDABRSTATUS.getABRTime());
/* 124 */       aITFEEDSimpleSaxXML.startDocument();
/* 125 */       aITFEEDSimpleSaxXML.startElement("ANNTMF", aITFEEDSimpleSaxXML.createAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/ANNTMF"));
/* 126 */       aITFEEDSimpleSaxXML.addElement("ANNNUMBER", getAttributeValue(paramEntityItem, "ANNNUMBER"));
/* 127 */       aITFEEDSimpleSaxXML.addElement("INVENTORYGROUP", getAttributeValue(paramEntityItem, "INVENTORYGROUP"));
/* 128 */       aITFEEDSimpleSaxXML.addElement("ANNTYPE", getAttributeValue(paramEntityItem, "ANNTYPE"));
/*     */ 
/*     */       
/* 131 */       generateAvailList(aITFEEDSimpleSaxXML, vector1, paramAITFEEDABRSTATUS);
/*     */       
/* 133 */       generateModelList(aITFEEDSimpleSaxXML, vector2, paramAITFEEDABRSTATUS);
/*     */       
/* 135 */       generateTmfList(aITFEEDSimpleSaxXML, vector4, paramAITFEEDABRSTATUS);
/*     */       
/* 137 */       generateRelatorList(aITFEEDSimpleSaxXML, vector, paramAITFEEDABRSTATUS);
/*     */       
/* 139 */       aITFEEDSimpleSaxXML.endElement("ANNTMF");
/* 140 */       aITFEEDSimpleSaxXML.endDocument();
/*     */ 
/*     */       
/* 143 */       vector1.clear();
/* 144 */       vector1 = null;
/* 145 */       vector2.clear();
/* 146 */       vector2 = null;
/* 147 */       vector4.clear();
/* 148 */       vector4 = null;
/* 149 */       vector.clear();
/* 150 */       vector = null;
/* 151 */       entityList.dereference();
/* 152 */       entityList = null;
/* 153 */       vector6.clear();
/* 154 */       vector6 = null;
/*     */     } finally {
/* 156 */       if (aITFEEDSimpleSaxXML != null) {
/* 157 */         aITFEEDSimpleSaxXML.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateAvailList(AITFEEDSimpleSaxXML paramAITFEEDSimpleSaxXML, Vector<EntityItem> paramVector, AITFEEDABRSTATUS paramAITFEEDABRSTATUS) throws SAXException {
/* 163 */     paramAITFEEDSimpleSaxXML.startElement("AVAILABILITYLIST");
/* 164 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 165 */       EntityItem entityItem = paramVector.get(b);
/* 166 */       paramAITFEEDSimpleSaxXML.startElement("AVAILABILITYELEMENT");
/* 167 */       paramAITFEEDSimpleSaxXML.addElement("ENTITYID", String.valueOf(entityItem.getEntityID()));
/* 168 */       paramAITFEEDSimpleSaxXML.addElement("AVAILTYPE", getAttributeValue(entityItem, "AVAILTYPE"));
/* 169 */       paramAITFEEDSimpleSaxXML.addElement("EFFECTIVEDATE", getAttributeValue(entityItem, "EFFECTIVEDATE"));
/* 170 */       createCountryListElement(paramAITFEEDSimpleSaxXML, entityItem, paramAITFEEDABRSTATUS);
/* 171 */       paramAITFEEDSimpleSaxXML.endElement("AVAILABILITYELEMENT");
/*     */     } 
/* 173 */     paramAITFEEDSimpleSaxXML.endElement("AVAILABILITYLIST");
/*     */   }
/*     */   
/*     */   private void generateModelList(AITFEEDSimpleSaxXML paramAITFEEDSimpleSaxXML, Vector<EntityItem> paramVector, AITFEEDABRSTATUS paramAITFEEDABRSTATUS) throws SAXException {
/* 177 */     paramAITFEEDSimpleSaxXML.startElement("MODELLIST");
/* 178 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 179 */       EntityItem entityItem = paramVector.get(b);
/* 180 */       paramAITFEEDSimpleSaxXML.startElement("MODELELEMENT");
/* 181 */       paramAITFEEDSimpleSaxXML.addElement("ENTITYID", "" + entityItem.getEntityID());
/* 182 */       paramAITFEEDSimpleSaxXML.addElement("MACHTYPEATR", getAttributeValue(entityItem, "MACHTYPEATR"));
/* 183 */       paramAITFEEDSimpleSaxXML.addElement("MODELATR", getAttributeValue(entityItem, "MODELATR"));
/* 184 */       paramAITFEEDSimpleSaxXML.addElement("MKTGNAME", getAttributeValue(entityItem, "MKTGNAME"));
/* 185 */       paramAITFEEDSimpleSaxXML.addElement("INVNAME", getAttributeValue(entityItem, "INVNAME"));
/* 186 */       paramAITFEEDSimpleSaxXML.addElement("INSTALL", getAttributeValue(entityItem, "INSTALL"));
/* 187 */       paramAITFEEDSimpleSaxXML.addElement("ORDERCODE", getAttributeValue(entityItem, "MODELORDERCODE"));
/* 188 */       paramAITFEEDSimpleSaxXML.endElement("MODELELEMENT");
/*     */     } 
/* 190 */     paramAITFEEDSimpleSaxXML.endElement("MODELLIST");
/*     */   }
/*     */   
/*     */   private void generateTmfList(AITFEEDSimpleSaxXML paramAITFEEDSimpleSaxXML, Vector<EntityItem> paramVector, AITFEEDABRSTATUS paramAITFEEDABRSTATUS) throws SAXException {
/* 194 */     paramAITFEEDSimpleSaxXML.startElement("TMFLIST");
/* 195 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 196 */       EntityItem entityItem1 = paramVector.get(b);
/* 197 */       EntityItem entityItem2 = getModelEntityFromTmf(entityItem1);
/* 198 */       EntityItem entityItem3 = getFeatureEntityFromTmf(entityItem1);
/* 199 */       if (entityItem2 != null && entityItem3 != null) {
/* 200 */         paramAITFEEDSimpleSaxXML.startElement("TMFELEMENT");
/* 201 */         paramAITFEEDSimpleSaxXML.addElement("ENTITYID", "" + entityItem1.getEntityID());
/* 202 */         paramAITFEEDSimpleSaxXML.addElement("ORDERCODE", getAttributeValue(entityItem1, "ORDERCODE"));
/* 203 */         paramAITFEEDSimpleSaxXML.addElement("INSTALL", getAttributeValue(entityItem1, "INSTALL"));
/* 204 */         paramAITFEEDSimpleSaxXML.addElement("RETURNEDPARTS", getAttributeValue(entityItem1, "RETURNEDPARTS"));
/* 205 */         paramAITFEEDSimpleSaxXML.addElement("SYSTEMMIN", getAttributeValue(entityItem1, "SYSTEMMIN"));
/* 206 */         paramAITFEEDSimpleSaxXML.addElement("SYSTEMMAX", getAttributeValue(entityItem1, "SYSTEMMAX"));
/* 207 */         paramAITFEEDSimpleSaxXML.addElement("INTORDERMAX", getAttributeValue(entityItem1, "INITORDERMAX"));
/* 208 */         paramAITFEEDSimpleSaxXML.addElement("OSLEVELCOMPLEMENT", getAttributeValue(entityItem1, "OSLEVELCOMPLEMENT"));
/* 209 */         paramAITFEEDSimpleSaxXML.addElement("TMFEDITORNOTE", getAttributeValue(entityItem1, "EDITORNOTE"));
/* 210 */         paramAITFEEDSimpleSaxXML.addElement("COMMENT", getAttributeValue(entityItem1, "COMMENTS"));
/* 211 */         paramAITFEEDSimpleSaxXML.addElement("PREREQ", getAttributeValue(entityItem1, "PREREQ"));
/* 212 */         paramAITFEEDSimpleSaxXML.addElement("COREQUISITE", getAttributeValue(entityItem1, "COREQUISITE"));
/* 213 */         paramAITFEEDSimpleSaxXML.addElement("LMTATION", getAttributeValue(entityItem1, "LMTATION"));
/* 214 */         paramAITFEEDSimpleSaxXML.addElement("COMPATIBILITY", getAttributeValue(entityItem1, "COMPATIBILITY"));
/* 215 */         paramAITFEEDSimpleSaxXML.addElement("CBLORD", getAttributeValue(entityItem1, "CBLORD"));
/*     */         
/* 217 */         paramAITFEEDSimpleSaxXML.addElement("MACHTYPEATR", getAttributeValue(entityItem2, "MACHTYPEATR"));
/* 218 */         paramAITFEEDSimpleSaxXML.addElement("MODELATR", getAttributeValue(entityItem2, "MODELATR"));
/*     */         
/* 220 */         paramAITFEEDSimpleSaxXML.addElement("FEATURECODE", getAttributeValue(entityItem3, "FEATURECODE"));
/* 221 */         paramAITFEEDSimpleSaxXML.addElement("MODELINVNAME", getAttributeValue(entityItem2, "INVNAME"));
/* 222 */         paramAITFEEDSimpleSaxXML.addElement("INVNAME", getAttributeValue(entityItem3, "INVNAME"));
/* 223 */         paramAITFEEDSimpleSaxXML.addElement("EDITORNOTE", getAttributeValue(entityItem3, "EDITORNOTE"));
/* 224 */         paramAITFEEDSimpleSaxXML.addElement("PRICEDFEATURE", getAttributeValue(entityItem3, "PRICEDFEATURE"));
/* 225 */         paramAITFEEDSimpleSaxXML.addElement("ZEROPRICE", getAttributeValue(entityItem3, "ZEROPRICE"));
/* 226 */         paramAITFEEDSimpleSaxXML.addElement("MAINTPRICE", getAttributeValue(entityItem3, "MAINTPRICE"));
/* 227 */         paramAITFEEDSimpleSaxXML.addElement("HWFCCAT", getAttributeValue(entityItem3, "HWFCCAT"));
/* 228 */         paramAITFEEDSimpleSaxXML.addElement("MKTGNAME", getAttributeValue(entityItem3, "MKTGNAME"));
/* 229 */         paramAITFEEDSimpleSaxXML.addElement("ATTPROVIDED", getAttributeValue(entityItem3, "ATTPROVIDED"));
/* 230 */         paramAITFEEDSimpleSaxXML.addElement("ATTREQUIRED", getAttributeValue(entityItem3, "ATTREQUIRED"));
/* 231 */         paramAITFEEDSimpleSaxXML.addElement("FCMKTGDESC", getAttributeValue(entityItem3, "FCMKTGDESC"));
/* 232 */         paramAITFEEDSimpleSaxXML.addElement("CONFIGURATORFLAG", getAttributeValue(entityItem1, "CONFIGURATORFLAG"));
/* 233 */         paramAITFEEDSimpleSaxXML.addElement("BULKMESINDC", getAttributeValue(entityItem1, "BULKMESINDC"));
/* 234 */         paramAITFEEDSimpleSaxXML.addElement("WARRSVCCOVR", getAttributeValue(entityItem1, "WARRSVCCOVR"));
/* 235 */         paramAITFEEDSimpleSaxXML.endElement("TMFELEMENT");
/*     */       } else {
/* 237 */         paramAITFEEDABRSTATUS.addDebug("model or feature is null skip the " + entityItem1.getKey());
/*     */       } 
/*     */     } 
/* 240 */     paramAITFEEDSimpleSaxXML.endElement("TMFLIST");
/*     */   }
/*     */   
/*     */   private void generateRelatorList(AITFEEDSimpleSaxXML paramAITFEEDSimpleSaxXML, Vector<AITFEEDXMLRelator> paramVector, AITFEEDABRSTATUS paramAITFEEDABRSTATUS) throws SAXException {
/* 244 */     paramAITFEEDSimpleSaxXML.startElement("RELATORLIST");
/* 245 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 246 */       AITFEEDXMLRelator aITFEEDXMLRelator = paramVector.get(b);
/* 247 */       paramAITFEEDSimpleSaxXML.startElement("RELATORELEMENT");
/* 248 */       paramAITFEEDSimpleSaxXML.addElement("ENTITY1TYPE", aITFEEDXMLRelator.getEntit1Type());
/* 249 */       paramAITFEEDSimpleSaxXML.addElement("ENTITY1ID", aITFEEDXMLRelator.getEntity1ID());
/* 250 */       paramAITFEEDSimpleSaxXML.addElement("ENTITY2TYPE", aITFEEDXMLRelator.getEntity2Type());
/* 251 */       paramAITFEEDSimpleSaxXML.addElement("ENTITY2ID", aITFEEDXMLRelator.getEntity2ID());
/* 252 */       paramAITFEEDSimpleSaxXML.endElement("RELATORELEMENT");
/*     */     } 
/* 254 */     paramAITFEEDSimpleSaxXML.endElement("RELATORLIST");
/*     */   }
/*     */   
/*     */   private EntityItem getModelEntityFromTmf(EntityItem paramEntityItem) {
/* 258 */     EntityItem entityItem = null;
/* 259 */     Vector<EntityItem> vector = paramEntityItem.getDownLink();
/* 260 */     if (vector != null && vector.size() > 0) {
/* 261 */       for (byte b = 0; b < vector.size(); b++) {
/* 262 */         EntityItem entityItem1 = vector.get(b);
/* 263 */         if ("MODEL".equals(entityItem1.getEntityType())) {
/* 264 */           entityItem = entityItem1;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 269 */     return entityItem;
/*     */   }
/*     */   
/*     */   private EntityItem getFeatureEntityFromTmf(EntityItem paramEntityItem) {
/* 273 */     EntityItem entityItem = null;
/* 274 */     Vector<EntityItem> vector = paramEntityItem.getUpLink();
/* 275 */     if (vector != null && vector.size() > 0) {
/* 276 */       for (byte b = 0; b < vector.size(); b++) {
/* 277 */         EntityItem entityItem1 = vector.get(b);
/* 278 */         if ("FEATURE".equals(entityItem1.getEntityType())) {
/* 279 */           entityItem = entityItem1;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 284 */     return entityItem;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 290 */     return "AITANNTMF";
/*     */   } private String getVeName2() {
/* 292 */     return "AITANNTMF2";
/*     */   }
/*     */   public String getVersion() {
/* 295 */     return "$Revision: 1.8 $";
/*     */   }
/*     */   
/*     */   public String getXMLFileName() {
/* 299 */     return "ANNTMF.xml";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\AITFEEDANNTMFABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */