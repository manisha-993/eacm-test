package COM.ibm.eannounce.abr.sg.rfc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

public class CommonEntitiesTest {
	
	@Test
	public void testModel() {
		System.out.println("------------- Test CommonEntitiesTest start -------------");
		String xmlPath = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
		String xml = CommonEntities.loadXml(xmlPath);
		MODEL model = CommonEntities.getModelFromXml(xml);		
		System.out.println("model.getSVCLEVCD = " + model.getSVCLEVCD());
		System.out.println("------------- Test CommonEntitiesTest end -------------");
		
	}

}
