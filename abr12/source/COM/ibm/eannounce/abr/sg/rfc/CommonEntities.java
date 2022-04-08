package COM.ibm.eannounce.abr.sg.rfc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class CommonEntities {
	
	public static FCTRANSACTION getFctransactionFromXml(String xml) {
		FCTRANSACTION FctransactionObj =null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 		
			factory.setNamespaceAware(false);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));		 
		    FctransactionObj = getFctransactionFromDoc(doc, FCTRANSACTION.class);
		} catch (Throwable e) {
			e.printStackTrace();
		}  
		
		return FctransactionObj;
	}
	
	private static <T> T getFctransactionFromDoc(Document doc, Class<FCTRANSACTION> class1) throws JAXBException {

		try {
			JAXBContext context = JAXBContext.newInstance(FCTRANSACTION.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			return (T) unMarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			throw e;
		}				
	}
	
	public static MODELCONVERT getModelConvertFromXml(String xml) {
		  MODELCONVERT ModelConvertObj =null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 		
			factory.setNamespaceAware(false);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));		 
		    ModelConvertObj = getModelConvertFromDoc(doc, MODELCONVERT.class);
		} catch (Throwable e) {
			e.printStackTrace();
		}  
		
		return ModelConvertObj;
	}
	
	private static <T> T getModelConvertFromDoc(Document doc, Class<MODELCONVERT> class1) throws JAXBException {

		try {
			JAXBContext context = JAXBContext.newInstance(MODELCONVERT.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			return (T) unMarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			throw e;
		}				
	}
	
	public static MODEL getModelFromXml(String xml) {
		  MODEL modelObj =null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 		
			factory.setNamespaceAware(false);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));		 
		    modelObj = getModelFromDoc(doc, MODEL.class);
		} catch (Throwable e) {
			e.printStackTrace();
		}  
		
		return modelObj;
	}
	
	private static <T> T getModelFromDoc(Document doc, Class<MODEL> class1) throws JAXBException {

		try {
			JAXBContext context = JAXBContext.newInstance(MODEL.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			return (T) unMarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			throw e;
		}				
	}
	
	public static SVCLEV getSVCLEVFromXml(String xml) {
		SVCLEV SVCLEV =null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 		
			factory.setNamespaceAware(false);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));		 
		    SVCLEV = getSVCLEVFromDoc(doc, SVCLEV.class);
		} catch (Throwable e) {
			e.printStackTrace();
		}  
		
		return SVCLEV;
	}
	
	private static <T> T getSVCLEVFromDoc(Document doc, Class<SVCLEV> class1) throws JAXBException {

		try {
			JAXBContext context = JAXBContext.newInstance(SVCLEV.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			return (T) unMarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			throw e;
		}				
	}
	
	
	@SuppressWarnings("resource")
	public static String loadXml(String xmlPath){
		StringBuffer stringBuffer = new StringBuffer();
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(xmlPath));		
			String line = null;
			while ((line=reader.readLine())!=null) {
				stringBuffer.append(line);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stringBuffer.toString();
		
	}
	public static void main(String[] args) {
		String xmlPath = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
		String xml = loadXml(xmlPath);
		
	}

}
