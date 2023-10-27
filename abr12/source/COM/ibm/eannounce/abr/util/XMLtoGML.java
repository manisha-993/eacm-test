/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerConfigurationException;
/*    */ import javax.xml.transform.TransformerException;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.stream.StreamResult;
/*    */ import javax.xml.transform.stream.StreamSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLtoGML
/*    */ {
/*    */   public OutputStream transform(StreamSource paramStreamSource) throws TransformerException, TransformerConfigurationException, FileNotFoundException {
/* 62 */     String str = "XML_to_GML.xslt";
/*    */ 
/*    */ 
/*    */     
/* 66 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 72 */     Transformer transformer = transformerFactory.newTransformer(new StreamSource(str));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 85 */     StreamResult streamResult = new StreamResult(byteArrayOutputStream);
/* 86 */     transformer.transform(paramStreamSource, streamResult);
/*    */     
/* 88 */     return streamResult.getOutputStream();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLtoGML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */