package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class Chw001ClfCreateTest {

	@Test
    public void testSuccess() throws ClassNotFoundException, SQLException
    {
        System.out.println("------------- Test Chw001ClfCreate start -------------");
        String xmlPath = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
		String xml = CommonEntities.loadXml(xmlPath);
		String pdhconnect= "fvtcloudpdh";
		Connection pdh_connection = ConnectionFactory.getConnection(pdhconnect);
		
		String odsconnect= "fvtcloudods";
		Connection ods_connection = ConnectionFactory.getConnection(odsconnect);
		//
        Chw001ClfCreate Chw001ClfCreate = new Chw001ClfCreate(xml,"ZPRT","5773E53","MODEL",pdh_connection,ods_connection);
        Chw001ClfCreate.execute();
        System.out.println("------------- Test Chw001ClfCreate end -------------");
    }

}
