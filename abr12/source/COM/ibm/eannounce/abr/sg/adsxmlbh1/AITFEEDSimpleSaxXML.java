/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*     */ import javax.xml.transform.sax.TransformerHandler;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
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
/*     */ public class AITFEEDSimpleSaxXML
/*     */ {
/*     */   private OutputStream os;
/*     */   private TransformerHandler th;
/*     */   private AttributesImpl emptyAttr;
/*     */   private Transformer transformer;
/*     */   private OutputStreamWriter osw;
/*     */   
/*     */   public AITFEEDSimpleSaxXML(String paramString) throws FileNotFoundException, TransformerConfigurationException {
/*  40 */     init(paramString);
/*     */   }
/*     */   
/*     */   private void init(String paramString) throws TransformerConfigurationException, FileNotFoundException {
/*  44 */     this.os = new FileOutputStream(paramString);
/*     */     try {
/*  46 */       this.osw = new OutputStreamWriter(this.os, "UTF-8");
/*  47 */       StreamResult streamResult = new StreamResult(this.osw);
/*  48 */       SAXTransformerFactory sAXTransformerFactory = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
/*  49 */       this.th = sAXTransformerFactory.newTransformerHandler();
/*  50 */       this.transformer = this.th.getTransformer();
/*  51 */       this.transformer.setOutputProperty("encoding", "UTF-8");
/*  52 */       this.transformer.setOutputProperty("indent", "no");
/*  53 */       this.th.setResult(streamResult);
/*  54 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  55 */       unsupportedEncodingException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startDocument() throws SAXException {
/*  60 */     this.th.startDocument();
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*  64 */     this.th.endDocument();
/*     */   }
/*     */   
/*     */   public void addElement(String paramString1, String paramString2, Attributes paramAttributes) throws SAXException {
/*  68 */     this.th.startElement("", "", paramString1, paramAttributes);
/*  69 */     this.th.characters(paramString2.toCharArray(), 0, paramString2.length());
/*  70 */     this.th.endElement("", "", paramString1);
/*     */   }
/*     */   
/*     */   public void addElement(String paramString1, String paramString2) throws SAXException {
/*  74 */     addElement(paramString1, paramString2, this.emptyAttr);
/*     */   }
/*     */   
/*     */   public void startElement(String paramString, Attributes paramAttributes) throws SAXException {
/*  78 */     this.th.startElement("", "", paramString, paramAttributes);
/*     */   }
/*     */   
/*     */   public void startElement(String paramString) throws SAXException {
/*  82 */     this.th.startElement("", "", paramString, this.emptyAttr);
/*     */   }
/*     */   
/*     */   public void endElement(String paramString) throws SAXException {
/*  86 */     this.th.endElement("", "", paramString);
/*     */   }
/*     */   
/*     */   public AttributesImpl createAttribute(String paramString1, String paramString2) {
/*  90 */     AttributesImpl attributesImpl = new AttributesImpl();
/*  91 */     attributesImpl.addAttribute("", "", paramString1, "", paramString2);
/*  92 */     return attributesImpl;
/*     */   }
/*     */   
/*     */   public void setOutputProperty(String paramString1, String paramString2) {
/*  96 */     this.transformer.setOutputProperty(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public void close() {
/* 100 */     if (this.osw != null) {
/*     */       try {
/* 102 */         this.osw.flush();
/* 103 */         this.osw.close();
/* 104 */       } catch (IOException iOException) {
/* 105 */         iOException.printStackTrace();
/*     */       } 
/*     */     }
/* 108 */     if (this.os != null)
/*     */       try {
/* 110 */         this.os.flush();
/* 111 */         this.os.close();
/* 112 */       } catch (IOException iOException) {
/* 113 */         iOException.printStackTrace();
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\AITFEEDSimpleSaxXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */