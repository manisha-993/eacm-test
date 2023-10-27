/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.junit.Assert;
/*     */ import org.junit.BeforeClass;
/*     */ import org.junit.Test;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RdhChwFcProdTest
/*     */ {
/*  30 */   private static MODEL model = null;
/*  31 */   private static TMF_UPDATE tmf = null;
/*  32 */   private static FEATURE feature = null;
/*     */ 
/*     */   
/*     */   @BeforeClass
/*     */   public static void setUpBeforeClass() {
/*  37 */     initiateXML();
/*  38 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  39 */     documentBuilderFactory.setNamespaceAware(false);
/*     */ 
/*     */     
/*     */     try {
/*  43 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*  44 */       File file1 = new File("C:\\Users\\QianYuGao\\Desktop\\695525D.xml");
/*  45 */       String str = file2String(file1, "UTF-8").replace("&", "&amp;");
/*  46 */       string2File(str, "C:\\Users\\QianYuGao\\Desktop\\695525D-toUse.xml");
/*  47 */       File file2 = new File("C:\\Users\\QianYuGao\\Desktop\\695525D-toUse.xml");
/*  48 */       Document document = documentBuilder.parse(file2);
/*  49 */       model = getMODELObjFromDoc(document, MODEL.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  58 */     catch (ParserConfigurationException parserConfigurationException) {
/*  59 */       parserConfigurationException.printStackTrace();
/*  60 */     } catch (SAXException sAXException) {
/*  61 */       sAXException.printStackTrace();
/*  62 */     } catch (IOException iOException) {
/*  63 */       iOException.printStackTrace();
/*  64 */     } catch (JAXBException jAXBException) {
/*  65 */       jAXBException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testRdhTssFcProd() {
/*  72 */     System.out.println("------------- Test RdhChwFcProd start -------------");
/*     */     
/*  74 */     RdhChwFcProd rdhChwFcProd = new RdhChwFcProd(model, null);
/*     */     
/*     */     try {
/*  77 */       String str1 = rdhChwFcProd.generateJson();
/*  78 */       String str2 = formatJson(str1);
/*  79 */       string2File(str2, "C:\\Users\\QianYuGao\\Desktop\\695525D-ChwFcProd.json");
/*  80 */     } catch (Exception exception) {
/*  81 */       exception.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  86 */       rdhChwFcProd.execute();
/*  87 */     } catch (Exception exception) {
/*     */       
/*  89 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/*  92 */     String str = rdhChwFcProd.createLogEntry();
/*  93 */     System.out.println(str);
/*  94 */     Assert.assertEquals(rdhChwFcProd.getRfcrc(), 0L);
/*  95 */     System.out.println("------------- Test RdhChwFcProd end -------------");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T getMODELObjFromDoc(Document paramDocument, Class<MODEL> paramClass) throws JAXBException {
/*     */     try {
/* 102 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { MODEL.class });
/* 103 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/* 104 */       return (T)unmarshaller.unmarshal(paramDocument);
/* 105 */     } catch (JAXBException jAXBException) {
/* 106 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T getTMFObjFromDoc(Document paramDocument, Class<TMF_UPDATE> paramClass) throws JAXBException {
/*     */     try {
/* 114 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { TMF_UPDATE.class });
/* 115 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/* 116 */       return (T)unmarshaller.unmarshal(paramDocument);
/* 117 */     } catch (JAXBException jAXBException) {
/* 118 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T getFEATUREObjFromDoc(Document paramDocument, Class<FEATURE> paramClass) throws JAXBException {
/*     */     try {
/* 126 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { FEATURE.class });
/* 127 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/* 128 */       return (T)unmarshaller.unmarshal(paramDocument);
/* 129 */     } catch (JAXBException jAXBException) {
/* 130 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initiateXML() {}
/*     */ 
/*     */   
/*     */   public static boolean string2File(String paramString1, String paramString2) {
/* 140 */     boolean bool = true;
/* 141 */     BufferedReader bufferedReader = null;
/* 142 */     BufferedWriter bufferedWriter = null;
/*     */     try {
/* 144 */       File file = new File(paramString2);
/* 145 */       if (!file.getParentFile().exists()) file.getParentFile().mkdirs(); 
/* 146 */       bufferedReader = new BufferedReader(new StringReader(paramString1));
/* 147 */       bufferedWriter = new BufferedWriter(new FileWriter(file));
/* 148 */       char[] arrayOfChar = new char[1024];
/*     */       int i;
/* 150 */       while ((i = bufferedReader.read(arrayOfChar)) != -1) {
/* 151 */         bufferedWriter.write(arrayOfChar, 0, i);
/*     */       }
/* 153 */       bufferedWriter.flush();
/* 154 */       bufferedReader.close();
/* 155 */       bufferedWriter.close();
/* 156 */     } catch (IOException iOException) {
/* 157 */       iOException.printStackTrace();
/* 158 */       bool = false;
/* 159 */       return bool;
/*     */     } finally {
/* 161 */       if (bufferedReader != null) {
/*     */         try {
/* 163 */           bufferedReader.close();
/* 164 */         } catch (IOException iOException) {
/* 165 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 169 */     return bool;
/*     */   }
/*     */   
/*     */   public static String file2String(File paramFile, String paramString) {
/* 173 */     InputStreamReader inputStreamReader = null;
/* 174 */     StringWriter stringWriter = new StringWriter();
/*     */     try {
/* 176 */       if (paramString == null || "".equals(paramString.trim())) {
/* 177 */         inputStreamReader = new InputStreamReader(new FileInputStream(paramFile), paramString);
/*     */       } else {
/* 179 */         inputStreamReader = new InputStreamReader(new FileInputStream(paramFile));
/*     */       } 
/* 181 */       char[] arrayOfChar = new char[1024];
/* 182 */       int i = 0;
/* 183 */       while (-1 != (i = inputStreamReader.read(arrayOfChar))) {
/* 184 */         stringWriter.write(arrayOfChar, 0, i);
/*     */       }
/* 186 */     } catch (Exception exception) {
/* 187 */       exception.printStackTrace();
/* 188 */       return null;
/*     */     } finally {
/* 190 */       if (inputStreamReader != null)
/*     */         try {
/* 192 */           inputStreamReader.close();
/* 193 */         } catch (IOException iOException) {
/* 194 */           iOException.printStackTrace();
/*     */         }  
/*     */     } 
/* 197 */     if (stringWriter != null)
/* 198 */       return stringWriter.toString(); 
/* 199 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatJson(String paramString) {
/* 204 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 206 */     int i = paramString.length();
/* 207 */     byte b1 = 0;
/* 208 */     char c = Character.MIN_VALUE;
/*     */     
/* 210 */     for (byte b2 = 0; b2 < i; b2++) {
/*     */       
/* 212 */       c = paramString.charAt(b2);
/*     */       
/* 214 */       if (c == '[' || c == '{') {
/*     */         
/* 216 */         if (b2 - 1 > 0 && paramString.charAt(b2 - 1) == ':') {
/*     */           
/* 218 */           stringBuffer.append('\n');
/* 219 */           stringBuffer.append(indent(b1));
/*     */         } 
/*     */         
/* 222 */         stringBuffer.append(c);
/*     */         
/* 224 */         stringBuffer.append('\n');
/*     */         
/* 226 */         b1++;
/* 227 */         stringBuffer.append(indent(b1));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 232 */       else if (c == ']' || c == '}') {
/*     */         
/* 234 */         stringBuffer.append('\n');
/*     */         
/* 236 */         b1--;
/* 237 */         stringBuffer.append(indent(b1));
/*     */         
/* 239 */         stringBuffer.append(c);
/*     */         
/* 241 */         if (b2 + 1 < i && paramString.charAt(b2 + 1) != ',')
/*     */         {
/* 243 */           stringBuffer.append('\n');
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 249 */       else if (c == ',') {
/*     */         
/* 251 */         stringBuffer.append(c);
/* 252 */         stringBuffer.append('\n');
/* 253 */         stringBuffer.append(indent(b1));
/*     */       }
/*     */       else {
/*     */         
/* 257 */         stringBuffer.append(c);
/*     */       } 
/*     */     } 
/* 260 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String indent(int paramInt) {
/* 265 */     String str = "    ";
/*     */     
/* 267 */     StringBuffer stringBuffer = new StringBuffer();
/* 268 */     for (byte b = 0; b < paramInt; b++)
/*     */     {
/* 270 */       stringBuffer.append(str);
/*     */     }
/* 272 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhChwFcProdTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */