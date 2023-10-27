/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileReader;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommonEntities
/*     */ {
/*     */   public static FCTRANSACTION getFctransactionFromXml(String paramString) {
/*  18 */     FCTRANSACTION fCTRANSACTION = null;
/*     */     try {
/*  20 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  21 */       documentBuilderFactory.setNamespaceAware(false);
/*  22 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  23 */       Document document = documentBuilder.parse(new ByteArrayInputStream(paramString.getBytes()));
/*  24 */       fCTRANSACTION = getFctransactionFromDoc(document, FCTRANSACTION.class);
/*  25 */     } catch (Throwable throwable) {
/*  26 */       throwable.printStackTrace();
/*     */     } 
/*     */     
/*  29 */     return fCTRANSACTION;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T getFctransactionFromDoc(Document paramDocument, Class<FCTRANSACTION> paramClass) throws JAXBException {
/*     */     try {
/*  35 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { FCTRANSACTION.class });
/*  36 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/*  37 */       return (T)unmarshaller.unmarshal(paramDocument);
/*  38 */     } catch (JAXBException jAXBException) {
/*  39 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MODELCONVERT getModelConvertFromXml(String paramString) {
/*  44 */     MODELCONVERT mODELCONVERT = null;
/*     */     try {
/*  46 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  47 */       documentBuilderFactory.setNamespaceAware(false);
/*  48 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  49 */       Document document = documentBuilder.parse(new ByteArrayInputStream(paramString.getBytes()));
/*  50 */       mODELCONVERT = getModelConvertFromDoc(document, MODELCONVERT.class);
/*  51 */     } catch (Throwable throwable) {
/*  52 */       throwable.printStackTrace();
/*     */     } 
/*     */     
/*  55 */     return mODELCONVERT;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T getModelConvertFromDoc(Document paramDocument, Class<MODELCONVERT> paramClass) throws JAXBException {
/*     */     try {
/*  61 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { MODELCONVERT.class });
/*  62 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/*  63 */       return (T)unmarshaller.unmarshal(paramDocument);
/*  64 */     } catch (JAXBException jAXBException) {
/*  65 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MODEL getModelFromXml(String paramString) {
/*  70 */     MODEL mODEL = null;
/*     */     try {
/*  72 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  73 */       documentBuilderFactory.setNamespaceAware(false);
/*  74 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  75 */       Document document = documentBuilder.parse(new ByteArrayInputStream(paramString.getBytes()));
/*  76 */       mODEL = getModelFromDoc(document, MODEL.class);
/*  77 */     } catch (Throwable throwable) {
/*  78 */       throwable.printStackTrace();
/*     */     } 
/*     */     
/*  81 */     return mODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T getModelFromDoc(Document paramDocument, Class<MODEL> paramClass) throws JAXBException {
/*     */     try {
/*  87 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { MODEL.class });
/*  88 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/*  89 */       return (T)unmarshaller.unmarshal(paramDocument);
/*  90 */     } catch (JAXBException jAXBException) {
/*  91 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SVCLEV getSVCLEVFromXml(String paramString) {
/*  96 */     SVCLEV sVCLEV = null;
/*     */     try {
/*  98 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  99 */       documentBuilderFactory.setNamespaceAware(false);
/* 100 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 101 */       Document document = documentBuilder.parse(new ByteArrayInputStream(paramString.getBytes()));
/* 102 */       sVCLEV = getSVCLEVFromDoc(document, SVCLEV.class);
/* 103 */     } catch (Throwable throwable) {
/* 104 */       throwable.printStackTrace();
/*     */     } 
/*     */     
/* 107 */     return sVCLEV;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T getSVCLEVFromDoc(Document paramDocument, Class<SVCLEV> paramClass) throws JAXBException {
/*     */     try {
/* 113 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { SVCLEV.class });
/* 114 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/* 115 */       return (T)unmarshaller.unmarshal(paramDocument);
/* 116 */     } catch (JAXBException jAXBException) {
/* 117 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String loadXml(String paramString) {
/* 124 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/*     */     try {
/* 127 */       BufferedReader bufferedReader = new BufferedReader(new FileReader(paramString));
/* 128 */       String str = null;
/* 129 */       while ((str = bufferedReader.readLine()) != null) {
/* 130 */         stringBuffer.append(str);
/*     */       }
/* 132 */     } catch (Exception exception) {
/* 133 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 136 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 140 */     String str1 = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
/* 141 */     String str2 = loadXml(str1);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\CommonEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */