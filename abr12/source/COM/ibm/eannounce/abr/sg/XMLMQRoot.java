/*     */ package COM.ibm.eannounce.abr.sg;
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
/*     */ 
/*     */ 
/*     */     
/*  92 */     EntityList entityList1 = paramADSABRSTATUS.getEntityListForDiff(paramProfile2, getVeName(), paramEntityItem);
/*     */ 
/*     */     
/*  95 */     EntityList entityList2 = paramADSABRSTATUS.getEntityListForDiff(paramProfile1, getVeName(), paramEntityItem);
/*     */     
/*  97 */     l2 = System.currentTimeMillis();
/*  98 */     paramADSABRSTATUS.addDebug("Time for both pulls: " + Stopwatch.format(l2 - l1));
/*  99 */     l1 = l2;
/*     */ 
/*     */ 
/*     */     
/* 103 */     Hashtable hashtable = ((ExtractActionItem)entityList1.getParentActionItem()).generateVESteps(paramADSABRSTATUS.getDB(), paramProfile2, paramEntityItem
/* 104 */         .getEntityType());
/*     */ 
/*     */     
/* 107 */     DiffVE diffVE = new DiffVE(entityList2, entityList1, hashtable);
/* 108 */     diffVE.setCheckAllNLS(true);
/*     */     
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
/*     */ 
/*     */ 
/*     */       
/* 133 */       hashtable1.put(diffEntity.getKey(), diffEntity);
/*     */       
/* 135 */       String str = diffEntity.getEntityType();
/* 136 */       if (diffEntity.isRoot()) {
/* 137 */         str = "ROOT";
/*     */       }
/* 139 */       Vector<DiffEntity> vector1 = (Vector)hashtable1.get(str);
/* 140 */       if (vector1 == null) {
/* 141 */         vector1 = new Vector();
/* 142 */         hashtable1.put(str, vector1);
/*     */       } 
/* 144 */       vector1.add(diffEntity);
/*     */     } 
/*     */     
/* 147 */     if (bool) {
/*     */       
/* 149 */       for (b = 0; b < entityList1.getEntityGroupCount(); b++) {
/* 150 */         String str = entityList1.getEntityGroup(b).getEntityType();
/* 151 */         Vector vector2 = (Vector)hashtable1.get(str);
/* 152 */         if (vector2 == null) {
/* 153 */           vector2 = new Vector();
/* 154 */           hashtable1.put(str, vector2);
/*     */         } 
/*     */       } 
/*     */       
/* 158 */       Vector vector1 = getMQPropertiesFN();
/* 159 */       if (vector1 == null) {
/* 160 */         paramADSABRSTATUS.addDebug("No MQ properties files, nothing will be generated.");
/*     */         
/* 162 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", paramEntityItem.getKey());
/*     */       } else {
/* 164 */         String str = generateXML(paramADSABRSTATUS, hashtable1);
/* 165 */         if (paramBoolean && str != null) {
/* 166 */           paramADSABRSTATUS.notify(this, paramEntityItem.getKey(), str);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 171 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", paramEntityItem.getKey());
/*     */     } 
/*     */ 
/*     */     
/* 175 */     entityList2.dereference();
/* 176 */     entityList1.dereference();
/* 177 */     hashtable.clear();
/* 178 */     vector.clear();
/* 179 */     diffVE.dereference();
/*     */     
/* 181 */     for (Enumeration<Object> enumeration = hashtable1.elements(); enumeration.hasMoreElements(); ) {
/* 182 */       Vector vector1 = (Vector)enumeration.nextElement();
/* 183 */       if (vector1 instanceof Vector) {
/* 184 */         ((Vector)vector1).clear();
/*     */       }
/*     */     } 
/* 187 */     hashtable1.clear();
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
/* 206 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 207 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 208 */     Document document = documentBuilder.newDocument();
/* 209 */     XMLElem xMLElem = getXMLMap();
/*     */     
/* 211 */     Element element = null;
/*     */     
/* 213 */     StringBuffer stringBuffer = new StringBuffer();
/* 214 */     xMLElem.addElements(paramADSABRSTATUS.getDB(), paramHashtable, document, element, null, stringBuffer);
/*     */     
/* 216 */     paramADSABRSTATUS.addDebug("GenXML debug: " + ADSABRSTATUS.NEWLINE + stringBuffer.toString());
/* 217 */     String str = paramADSABRSTATUS.transformXML(this, document);
/* 218 */     paramADSABRSTATUS.addDebug("Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/*     */     
/* 220 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 228 */     return "1.7";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\XMLMQRoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */