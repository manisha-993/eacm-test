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
/*     */ 
/*     */ 
/*     */ public class RdhTssFcProdTest
/*     */ {
/*  32 */   private static SVCMOD svcmod = null;
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
/*  49 */       svcmod = getSVCMODObjFromDoc(document, SVCMOD.class);
/*  50 */     } catch (ParserConfigurationException parserConfigurationException) {
/*  51 */       parserConfigurationException.printStackTrace();
/*  52 */     } catch (SAXException sAXException) {
/*  53 */       sAXException.printStackTrace();
/*  54 */     } catch (IOException iOException) {
/*  55 */       iOException.printStackTrace();
/*  56 */     } catch (JAXBException jAXBException) {
/*  57 */       jAXBException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testRdhTssFcProd() {
/*  64 */     System.out.println("------------- Test RdhTssFcProd start -------------");
/*     */     
/*  66 */     RdhTssFcProd rdhTssFcProd = new RdhTssFcProd(svcmod);
/*     */     
/*     */     try {
/*  69 */       String str1 = rdhTssFcProd.generateJson();
/*  70 */       String str2 = formatJson(str1);
/*  71 */       string2File(str2, "C:\\Users\\QianYuGao\\Desktop\\695525D-TssFcProd.json");
/*  72 */     } catch (Exception exception) {
/*  73 */       exception.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  78 */       rdhTssFcProd.execute();
/*  79 */     } catch (Exception exception) {
/*     */       
/*  81 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/*  84 */     String str = rdhTssFcProd.createLogEntry();
/*  85 */     System.out.println(str);
/*  86 */     Assert.assertEquals(rdhTssFcProd.getRfcrc(), 0L);
/*  87 */     System.out.println("------------- Test RdhTssFcProd end -------------");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T getSVCMODObjFromDoc(Document paramDocument, Class<SVCMOD> paramClass) throws JAXBException {
/*     */     try {
/*  94 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { SVCMOD.class });
/*  95 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/*  96 */       return (T)unmarshaller.unmarshal(paramDocument);
/*  97 */     } catch (JAXBException jAXBException) {
/*  98 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initiateXML() {}
/*     */ 
/*     */   
/*     */   public static boolean string2File(String paramString1, String paramString2) {
/* 108 */     boolean bool = true;
/* 109 */     BufferedReader bufferedReader = null;
/* 110 */     BufferedWriter bufferedWriter = null;
/*     */     try {
/* 112 */       File file = new File(paramString2);
/* 113 */       if (!file.getParentFile().exists()) file.getParentFile().mkdirs(); 
/* 114 */       bufferedReader = new BufferedReader(new StringReader(paramString1));
/* 115 */       bufferedWriter = new BufferedWriter(new FileWriter(file));
/* 116 */       char[] arrayOfChar = new char[1024];
/*     */       int i;
/* 118 */       while ((i = bufferedReader.read(arrayOfChar)) != -1) {
/* 119 */         bufferedWriter.write(arrayOfChar, 0, i);
/*     */       }
/* 121 */       bufferedWriter.flush();
/* 122 */       bufferedReader.close();
/* 123 */       bufferedWriter.close();
/* 124 */     } catch (IOException iOException) {
/* 125 */       iOException.printStackTrace();
/* 126 */       bool = false;
/* 127 */       return bool;
/*     */     } finally {
/* 129 */       if (bufferedReader != null) {
/*     */         try {
/* 131 */           bufferedReader.close();
/* 132 */         } catch (IOException iOException) {
/* 133 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 137 */     return bool;
/*     */   }
/*     */   
/*     */   public static String file2String(File paramFile, String paramString) {
/* 141 */     InputStreamReader inputStreamReader = null;
/* 142 */     StringWriter stringWriter = new StringWriter();
/*     */     try {
/* 144 */       if (paramString == null || "".equals(paramString.trim())) {
/* 145 */         inputStreamReader = new InputStreamReader(new FileInputStream(paramFile), paramString);
/*     */       } else {
/* 147 */         inputStreamReader = new InputStreamReader(new FileInputStream(paramFile));
/*     */       } 
/* 149 */       char[] arrayOfChar = new char[1024];
/* 150 */       int i = 0;
/* 151 */       while (-1 != (i = inputStreamReader.read(arrayOfChar))) {
/* 152 */         stringWriter.write(arrayOfChar, 0, i);
/*     */       }
/* 154 */     } catch (Exception exception) {
/* 155 */       exception.printStackTrace();
/* 156 */       return null;
/*     */     } finally {
/* 158 */       if (inputStreamReader != null)
/*     */         try {
/* 160 */           inputStreamReader.close();
/* 161 */         } catch (IOException iOException) {
/* 162 */           iOException.printStackTrace();
/*     */         }  
/*     */     } 
/* 165 */     if (stringWriter != null)
/* 166 */       return stringWriter.toString(); 
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatJson(String paramString) {
/* 172 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 174 */     int i = paramString.length();
/* 175 */     byte b1 = 0;
/* 176 */     char c = Character.MIN_VALUE;
/*     */     
/* 178 */     for (byte b2 = 0; b2 < i; b2++) {
/*     */       
/* 180 */       c = paramString.charAt(b2);
/*     */       
/* 182 */       if (c == '[' || c == '{') {
/*     */         
/* 184 */         if (b2 - 1 > 0 && paramString.charAt(b2 - 1) == ':') {
/*     */           
/* 186 */           stringBuffer.append('\n');
/* 187 */           stringBuffer.append(indent(b1));
/*     */         } 
/*     */         
/* 190 */         stringBuffer.append(c);
/*     */         
/* 192 */         stringBuffer.append('\n');
/*     */         
/* 194 */         b1++;
/* 195 */         stringBuffer.append(indent(b1));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 200 */       else if (c == ']' || c == '}') {
/*     */         
/* 202 */         stringBuffer.append('\n');
/*     */         
/* 204 */         b1--;
/* 205 */         stringBuffer.append(indent(b1));
/*     */         
/* 207 */         stringBuffer.append(c);
/*     */         
/* 209 */         if (b2 + 1 < i && paramString.charAt(b2 + 1) != ',')
/*     */         {
/* 211 */           stringBuffer.append('\n');
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 217 */       else if (c == ',') {
/*     */         
/* 219 */         stringBuffer.append(c);
/* 220 */         stringBuffer.append('\n');
/* 221 */         stringBuffer.append(indent(b1));
/*     */       }
/*     */       else {
/*     */         
/* 225 */         stringBuffer.append(c);
/*     */       } 
/*     */     } 
/* 228 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String indent(int paramInt) {
/* 233 */     String str = "    ";
/*     */     
/* 235 */     StringBuffer stringBuffer = new StringBuffer();
/* 236 */     for (byte b = 0; b < paramInt; b++)
/*     */     {
/* 238 */       stringBuffer.append(str);
/*     */     }
/* 240 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhTssFcProdTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */