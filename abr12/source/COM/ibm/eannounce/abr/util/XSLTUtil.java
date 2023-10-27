/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Templates;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.io.DocumentSource;
/*     */ import org.dom4j.io.SAXReader;
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
/*     */ public class XSLTUtil
/*     */ {
/*  36 */   static Map xsltCache = new HashMap<>();
/*  37 */   static Map entityMap = new HashMap<>();
/*  38 */   static Set entityTypeSet = new HashSet();
/*     */   
/*     */   public static final String XSLT_ENABLE = "XSLT_enable";
/*     */   
/*     */   public static final String ENTITY_TYPE_LIST = "_typelist";
/*     */   
/*     */   public static String transferXML(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  45 */     String str = paramString2 + "_" + paramString3;
/*  46 */     System.out.println(str);
/*  47 */     if (!isXSLTEnable())
/*     */     {
/*  49 */       return null;
/*     */     }
/*     */     try {
/*  52 */       SAXReader sAXReader = new SAXReader();
/*  53 */       ByteArrayInputStream byteArrayInputStream = null;
/*  54 */       Transformer transformer = getTransformer(paramString2, paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  63 */       transformer.setOutputProperty("method", "xml");
/*  64 */       transformer.setOutputProperty("encoding", "UTF-8");
/*     */       
/*  66 */       byteArrayInputStream = new ByteArrayInputStream(paramString1.getBytes("UTF-8"));
/*  67 */       Document document = sAXReader.read(byteArrayInputStream);
/*  68 */       StringWriter stringWriter = new StringWriter();
/*  69 */       StreamResult streamResult = new StreamResult(stringWriter);
/*  70 */       DocumentSource documentSource = new DocumentSource(document);
/*     */       
/*  72 */       transformer.transform((Source)documentSource, streamResult);
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
/*  84 */       return stringWriter.toString();
/*     */     }
/*  86 */     catch (DocumentException documentException) {
/*     */       
/*  88 */       documentException.printStackTrace();
/*  89 */     } catch (TransformerConfigurationException transformerConfigurationException) {
/*     */       
/*  91 */       transformerConfigurationException.printStackTrace();
/*  92 */     } catch (TransformerException transformerException) {
/*     */       
/*  94 */       transformerException.printStackTrace();
/*  95 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       
/*  97 */       unsupportedEncodingException.printStackTrace();
/*     */     } 
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Transformer getTransformer(String paramString1, String paramString2) throws TransformerConfigurationException {
/* 106 */     String str = paramString1 + "_" + paramString2;
/* 107 */     Templates templates = (Templates)xsltCache.get(str);
/*     */ 
/*     */     
/* 110 */     if (templates == null) {
/* 111 */       synchronized (xsltCache) {
/*     */         
/* 113 */         templates = (Templates)xsltCache.get(str);
/* 114 */         if (templates == null) {
/*     */           
/* 116 */           TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */           
/* 118 */           String str1 = getXsltPath(paramString1, paramString2);
/* 119 */           System.out.println("Loading xslt..." + str1);
/* 120 */           templates = transformerFactory.newTemplates(new StreamSource(str1));
/* 121 */           System.out.println("success to load xslt - " + str1);
/*     */           
/* 123 */           xsltCache.put(str, templates);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 128 */     return templates.newTransformer();
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
/*     */   public static String getXmlPath(String paramString1, String paramString2) {
/* 140 */     System.out.println("getXmlPath:" + paramString1 + "_" + paramString2);
/* 141 */     return ABRServerProperties.getValue(paramString1, "_" + paramString2, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getXsltPath(String paramString1, String paramString2) {
/* 151 */     System.out.println("getXsltPath:" + paramString1 + "_" + paramString2);
/* 152 */     return ABRServerProperties.getValue(paramString1, "_" + paramString2, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isXSLTEnable() {
/* 158 */     String str = ABRServerProperties.getValue("XSLT_enable", "", null);
/* 159 */     if (null != str && "TRUE".equals(str.toUpperCase().trim()))
/* 160 */       return true; 
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean checkEntityType(String paramString1, String paramString2) {
/* 165 */     Set<String> set = (Set)entityMap.get(paramString1);
/* 166 */     if (set == null) {
/* 167 */       synchronized (entityMap) {
/* 168 */         set = (Set)entityMap.get(paramString1);
/* 169 */         if (set == null) {
/* 170 */           String str = ABRServerProperties.getValue(paramString1, "_typelist", null);
/* 171 */           set = new HashSet();
/* 172 */           if (null != str) {
/* 173 */             String[] arrayOfString = str.split(",");
/* 174 */             for (byte b = 0; b < arrayOfString.length; b++) {
/* 175 */               set.add(arrayOfString[b].trim());
/*     */             }
/*     */           } 
/* 178 */           entityMap.put(paramString1, set);
/*     */         } 
/*     */       } 
/*     */     }
/* 182 */     if (set != null && set.contains(paramString2)) {
/* 183 */       return true;
/*     */     }
/* 185 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XSLTUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */