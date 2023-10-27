/*     */ package COM.ibm.eannounce.abr.sg.wave2;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffVE;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XMLMQRoot
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  68 */     processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem, boolean paramBoolean) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  87 */     long l1 = System.currentTimeMillis();
/*  88 */     long l2 = 0L;
/*  89 */     String str = "1980-01-01-00.00.00.000000";
/*     */ 
/*     */ 
/*     */     
/*  93 */     EntityList entityList1 = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, getVeName(), paramEntityItem);
/*     */ 
/*     */     
/*  96 */     EntityList entityList2 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, getVeName(), paramEntityItem);
/*     */     
/*  98 */     l2 = System.currentTimeMillis();
/*  99 */     paramADSABRSTATUS.addDebug("Time for both pulls: " + Stopwatch.format(l2 - l1));
/* 100 */     l1 = l2;
/*     */ 
/*     */ 
/*     */     
/* 104 */     Hashtable hashtable = ((ExtractActionItem)entityList1.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, paramEntityItem
/* 105 */         .getEntityType());
/*     */ 
/*     */     
/* 108 */     DiffVE diffVE = new DiffVE(entityList2, entityList1, hashtable);
/* 109 */     diffVE.setCheckAllNLS(true);
/* 110 */     paramADSABRSTATUS.addDebug("hshMap: " + hashtable);
/* 111 */     paramADSABRSTATUS.addDebug("time1 flattened VE: " + diffVE.getPriorDiffVE());
/* 112 */     paramADSABRSTATUS.addDebug("time2 flattened VE: " + diffVE.getCurrentDiffVE());
/*     */ 
/*     */     
/* 115 */     Vector<DiffEntity> vector = diffVE.diffVE();
/* 116 */     paramADSABRSTATUS.addDebug(" diffVE info:\n" + diffVE.getDebug());
/* 117 */     paramADSABRSTATUS.addDebug(" diffVE flattened VE: " + vector);
/*     */     
/* 119 */     l2 = System.currentTimeMillis();
/* 120 */     paramADSABRSTATUS.addDebug(" Time for diff: " + Stopwatch.format(l2 - l1));
/* 121 */     l1 = l2;
/*     */ 
/*     */     
/* 124 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 125 */     boolean bool = false; byte b;
/* 126 */     for (b = 0; b < vector.size(); b++) {
/* 127 */       DiffEntity diffEntity = vector.elementAt(b);
/*     */       
/* 129 */       bool = (bool || diffEntity.isChanged()) ? true : false;
/*     */     } 
/* 131 */     if (bool && paramADSABRSTATUS.fullMode() && !paramADSABRSTATUS.isPeriodicABR()) {
/*     */       
/* 133 */       paramProfile1.setValOnEffOn(str, str);
/* 134 */       EntityList entityList = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, getVeName(), paramEntityItem);
/* 135 */       DiffVE diffVE1 = new DiffVE(entityList, entityList1, hashtable);
/* 136 */       diffVE1.setCheckAllNLS(true);
/*     */       
/* 138 */       paramADSABRSTATUS.addDebug("hshMapfull: " + hashtable);
/* 139 */       paramADSABRSTATUS.addDebug("time1full flattened VE: " + diffVE1.getPriorDiffVE());
/* 140 */       paramADSABRSTATUS.addDebug("time2full flattened VE: " + diffVE1.getCurrentDiffVE());
/*     */ 
/*     */       
/* 143 */       Vector<DiffEntity> vector1 = diffVE1.diffVE();
/* 144 */       paramADSABRSTATUS.addDebug(" diffVctfull info:\n" + diffVE1.getDebug());
/* 145 */       paramADSABRSTATUS.addDebug(" diffVctfull flattened VE: " + vector1);
/*     */       
/*     */       byte b1;
/* 148 */       for (b1 = 0; b1 < vector1.size(); b1++) {
/* 149 */         DiffEntity diffEntity = vector1.elementAt(b1);
/*     */ 
/*     */ 
/*     */         
/* 153 */         hashtable1.put(diffEntity.getKey(), diffEntity);
/*     */         
/* 155 */         String str1 = diffEntity.getEntityType();
/* 156 */         if (diffEntity.isRoot()) {
/* 157 */           str1 = "ROOT";
/*     */         }
/* 159 */         Vector<DiffEntity> vector3 = (Vector)hashtable1.get(str1);
/* 160 */         if (vector3 == null) {
/* 161 */           vector3 = new Vector();
/* 162 */           hashtable1.put(str1, vector3);
/*     */         } 
/* 164 */         vector3.add(diffEntity);
/*     */       } 
/* 166 */       for (b1 = 0; b1 < entityList1.getEntityGroupCount(); b1++) {
/* 167 */         String str1 = entityList1.getEntityGroup(b1).getEntityType();
/* 168 */         Vector vector3 = (Vector)hashtable1.get(str1);
/* 169 */         if (vector3 == null) {
/* 170 */           vector3 = new Vector();
/* 171 */           hashtable1.put(str1, vector3);
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       Vector vector2 = getMQPropertiesFN();
/* 176 */       if (vector2 == null) {
/* 177 */         paramADSABRSTATUS.addDebug("No MQ properties files, nothing will be generated.");
/*     */         
/* 179 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", paramEntityItem.getKey());
/*     */       } else {
/* 181 */         String str1 = generateXML(paramADSABRSTATUS, hashtable1);
/* 182 */         if (paramBoolean && str1 != null) {
/* 183 */           paramADSABRSTATUS.notify(this, paramEntityItem.getKey(), str1);
/*     */         }
/*     */       } 
/*     */       
/* 187 */       entityList.dereference();
/* 188 */       vector1.clear();
/* 189 */       diffVE1.dereference();
/*     */     }
/* 191 */     else if (bool) {
/* 192 */       for (b = 0; b < vector.size(); b++) {
/* 193 */         DiffEntity diffEntity = vector.elementAt(b);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 198 */         hashtable1.put(diffEntity.getKey(), diffEntity);
/*     */         
/* 200 */         String str1 = diffEntity.getEntityType();
/* 201 */         if (diffEntity.isRoot()) {
/* 202 */           str1 = "ROOT";
/*     */         }
/* 204 */         Vector<DiffEntity> vector2 = (Vector)hashtable1.get(str1);
/* 205 */         if (vector2 == null) {
/* 206 */           vector2 = new Vector();
/* 207 */           hashtable1.put(str1, vector2);
/*     */         } 
/* 209 */         vector2.add(diffEntity);
/*     */       } 
/*     */       
/* 212 */       for (b = 0; b < entityList1.getEntityGroupCount(); b++) {
/* 213 */         String str1 = entityList1.getEntityGroup(b).getEntityType();
/* 214 */         Vector vector2 = (Vector)hashtable1.get(str1);
/* 215 */         if (vector2 == null) {
/* 216 */           vector2 = new Vector();
/* 217 */           hashtable1.put(str1, vector2);
/*     */         } 
/*     */       } 
/*     */       
/* 221 */       Vector vector1 = getMQPropertiesFN();
/* 222 */       if (vector1 == null) {
/* 223 */         paramADSABRSTATUS.addDebug("No MQ properties files, nothing will be generated.");
/*     */         
/* 225 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", paramEntityItem.getKey());
/*     */       } else {
/* 227 */         String str1 = generateXML(paramADSABRSTATUS, hashtable1);
/* 228 */         if (paramBoolean && str1 != null) {
/* 229 */           paramADSABRSTATUS.notify(this, paramEntityItem.getKey(), str1);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 234 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", paramEntityItem.getKey());
/*     */     } 
/*     */ 
/*     */     
/* 238 */     entityList2.dereference();
/* 239 */     entityList1.dereference();
/* 240 */     hashtable.clear();
/* 241 */     vector.clear();
/* 242 */     diffVE.dereference();
/*     */     
/* 244 */     for (Enumeration<Object> enumeration = hashtable1.elements(); enumeration.hasMoreElements(); ) {
/* 245 */       Vector vector1 = (Vector)enumeration.nextElement();
/* 246 */       if (vector1 instanceof Vector) {
/* 247 */         ((Vector)vector1).clear();
/*     */       }
/*     */     } 
/* 250 */     hashtable1.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String generateXML(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException {
/* 269 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 270 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 271 */     Document document = documentBuilder.newDocument();
/* 272 */     XMLElem xMLElem = getXMLMap();
/*     */     
/* 274 */     Element element = null;
/*     */     
/* 276 */     StringBuffer stringBuffer = new StringBuffer();
/* 277 */     xMLElem.addElements(paramADSABRSTATUS.getDB(), paramHashtable, document, element, null, stringBuffer);
/*     */     
/* 279 */     paramADSABRSTATUS.addDebug("GenXML debug: " + ADSABRSTATUS.NEWLINE + stringBuffer.toString());
/* 280 */     String str = paramADSABRSTATUS.transformXML(this, document);
/* 281 */     paramADSABRSTATUS.addDebug("Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/*     */     
/* 283 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 291 */     return "1.7";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\wave2\XMLMQRoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */