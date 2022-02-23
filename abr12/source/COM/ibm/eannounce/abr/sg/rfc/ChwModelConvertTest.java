package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ChwModelConvertTest {
	private MODELCONVERT modelConvert = null;

    @BeforeClass
    public static void setUpBeforeClass()
    {
    	
    }
	
	
	
	@Test
	public void testSuccess() throws ClassNotFoundException, SQLException 
    {
		System.out.println("------------- Test ChwModelConvert start -------------");
		String xmlPath = "C:/EACM_DEV/xml/MODELCONVERT.xml";
		String xml = CommonEntities.loadXml(xmlPath);
		modelConvert = CommonEntities.getModelConvertFromXml(xml);
		
		ChwModelConvert ChwModelConvert = new ChwModelConvert(modelConvert);
        try
        {
        	ChwModelConvert.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = ChwModelConvert.createLogEntry();
        System.out.println(logEntry);
        assertEquals(ChwModelConvert.getRfcrc(), 0);
        System.out.println("------------- Test ChwModelConvert end -------------");
    }

}
