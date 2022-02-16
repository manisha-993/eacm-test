package COM.ibm.eannounce.abr.sg.rfc;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class CommonEntities {
	
	public static MODEL getModelFromXml(String xml) {
		  MODEL modelObj =null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 		
			factory.setNamespaceAware(false);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));		 
		    modelObj = getObjFromDoc(doc, MODEL.class);
		     //695561U.xml
		} catch (Throwable e) {
			e.printStackTrace();
		}  
		
		return modelObj;
	}
	
	private static <T> T getObjFromDoc(Document doc, Class<MODEL> class1) throws JAXBException {

		try {
			JAXBContext context = JAXBContext.newInstance(MODEL.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			return (T) unMarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			throw e;
		}				
	}

}
