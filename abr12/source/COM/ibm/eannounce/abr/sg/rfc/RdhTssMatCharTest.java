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
/*     */ 
/*     */ public class RdhTssMatCharTest
/*     */ {
/*  33 */   private static SVCMOD svcmod = null;
/*     */ 
/*     */   
/*     */   @BeforeClass
/*     */   public static void setUpBeforeClass() {
/*  38 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  39 */     documentBuilderFactory.setNamespaceAware(false);
/*     */ 
/*     */     
/*     */     try {
/*  43 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */       
/*  45 */       File file1 = new File("C:\\Users\\QianYuGao\\Desktop\\695525D.xml");
/*  46 */       String str = file2String(file1, "UTF-8").replace("&", "&amp;");
/*  47 */       string2File(str, "C:\\Users\\QianYuGao\\Desktop\\695525D-toUse.xml");
/*  48 */       File file2 = new File("C:\\Users\\QianYuGao\\Desktop\\695525D-toUse.xml");
/*  49 */       Document document = documentBuilder.parse(file2);
/*  50 */       svcmod = getObjFromDoc(document, SVCMOD.class);
/*  51 */     } catch (ParserConfigurationException parserConfigurationException) {
/*  52 */       parserConfigurationException.printStackTrace();
/*  53 */     } catch (SAXException sAXException) {
/*  54 */       sAXException.printStackTrace();
/*  55 */     } catch (IOException iOException) {
/*  56 */       iOException.printStackTrace();
/*  57 */     } catch (JAXBException jAXBException) {
/*  58 */       jAXBException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testRdhTssMatChar() {
/*  65 */     System.out.println("------------- Test RdhTssMatChar start -------------");
/*     */     
/*  67 */     RdhTssMatChar rdhTssMatChar = new RdhTssMatChar(svcmod);
/*     */     
/*     */     try {
/*  70 */       String str1 = rdhTssMatChar.generateJson();
/*  71 */       String str2 = formatJson(str1);
/*  72 */       string2File(str2, "C:\\Users\\QianYuGao\\Desktop\\695525D-TssMatChar.json");
/*  73 */     } catch (Exception exception) {
/*  74 */       exception.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  79 */       rdhTssMatChar.execute();
/*  80 */     } catch (Exception exception) {
/*     */       
/*  82 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/*  85 */     String str = rdhTssMatChar.createLogEntry();
/*  86 */     System.out.println(str);
/*  87 */     Assert.assertEquals(rdhTssMatChar.getRfcrc(), 0L);
/*  88 */     System.out.println("------------- Test RdhTssMatChar end -------------");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T getObjFromDoc(Document paramDocument, Class<SVCMOD> paramClass) throws JAXBException {
/*     */     try {
/*  96 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { SVCMOD.class });
/*  97 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/*  98 */       return (T)unmarshaller.unmarshal(paramDocument);
/*  99 */     } catch (JAXBException jAXBException) {
/*     */       
/* 101 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean string2File(String paramString1, String paramString2) {
/* 106 */     boolean bool = true;
/* 107 */     BufferedReader bufferedReader = null;
/* 108 */     BufferedWriter bufferedWriter = null;
/*     */     try {
/* 110 */       File file = new File(paramString2);
/* 111 */       if (!file.getParentFile().exists()) file.getParentFile().mkdirs(); 
/* 112 */       bufferedReader = new BufferedReader(new StringReader(paramString1));
/* 113 */       bufferedWriter = new BufferedWriter(new FileWriter(file));
/* 114 */       char[] arrayOfChar = new char[1024];
/*     */       int i;
/* 116 */       while ((i = bufferedReader.read(arrayOfChar)) != -1) {
/* 117 */         bufferedWriter.write(arrayOfChar, 0, i);
/*     */       }
/* 119 */       bufferedWriter.flush();
/* 120 */       bufferedReader.close();
/* 121 */       bufferedWriter.close();
/* 122 */     } catch (IOException iOException) {
/* 123 */       iOException.printStackTrace();
/* 124 */       bool = false;
/* 125 */       return bool;
/*     */     } finally {
/* 127 */       if (bufferedReader != null) {
/*     */         try {
/* 129 */           bufferedReader.close();
/* 130 */         } catch (IOException iOException) {
/* 131 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 135 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String file2String(File paramFile, String paramString) {
/* 140 */     InputStreamReader inputStreamReader = null;
/* 141 */     StringWriter stringWriter = new StringWriter();
/*     */     try {
/* 143 */       if (paramString == null || "".equals(paramString.trim())) {
/* 144 */         inputStreamReader = new InputStreamReader(new FileInputStream(paramFile), paramString);
/*     */       } else {
/* 146 */         inputStreamReader = new InputStreamReader(new FileInputStream(paramFile));
/*     */       } 
/* 148 */       char[] arrayOfChar = new char[1024];
/* 149 */       int i = 0;
/* 150 */       while (-1 != (i = inputStreamReader.read(arrayOfChar))) {
/* 151 */         stringWriter.write(arrayOfChar, 0, i);
/*     */       }
/* 153 */     } catch (Exception exception) {
/* 154 */       exception.printStackTrace();
/* 155 */       return null;
/*     */     } finally {
/* 157 */       if (inputStreamReader != null)
/*     */         try {
/* 159 */           inputStreamReader.close();
/* 160 */         } catch (IOException iOException) {
/* 161 */           iOException.printStackTrace();
/*     */         }  
/*     */     } 
/* 164 */     if (stringWriter != null)
/* 165 */       return stringWriter.toString(); 
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatJson(String paramString) {
/* 171 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 173 */     int i = paramString.length();
/* 174 */     byte b1 = 0;
/* 175 */     char c = Character.MIN_VALUE;
/*     */     
/* 177 */     for (byte b2 = 0; b2 < i; b2++) {
/*     */       
/* 179 */       c = paramString.charAt(b2);
/*     */       
/* 181 */       if (c == '[' || c == '{') {
/*     */         
/* 183 */         if (b2 - 1 > 0 && paramString.charAt(b2 - 1) == ':') {
/*     */           
/* 185 */           stringBuffer.append('\n');
/* 186 */           stringBuffer.append(indent(b1));
/*     */         } 
/*     */         
/* 189 */         stringBuffer.append(c);
/*     */         
/* 191 */         stringBuffer.append('\n');
/*     */         
/* 193 */         b1++;
/* 194 */         stringBuffer.append(indent(b1));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 199 */       else if (c == ']' || c == '}') {
/*     */         
/* 201 */         stringBuffer.append('\n');
/*     */         
/* 203 */         b1--;
/* 204 */         stringBuffer.append(indent(b1));
/*     */         
/* 206 */         stringBuffer.append(c);
/*     */         
/* 208 */         if (b2 + 1 < i && paramString.charAt(b2 + 1) != ',')
/*     */         {
/* 210 */           stringBuffer.append('\n');
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 216 */       else if (c == ',') {
/*     */         
/* 218 */         stringBuffer.append(c);
/* 219 */         stringBuffer.append('\n');
/* 220 */         stringBuffer.append(indent(b1));
/*     */       }
/*     */       else {
/*     */         
/* 224 */         stringBuffer.append(c);
/*     */       } 
/*     */     } 
/* 227 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String indent(int paramInt) {
/* 232 */     String str = "    ";
/*     */     
/* 234 */     StringBuffer stringBuffer = new StringBuffer();
/* 235 */     for (byte b = 0; b < paramInt; b++)
/*     */     {
/* 237 */       stringBuffer.append(str);
/*     */     }
/* 239 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhTssMatCharTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */