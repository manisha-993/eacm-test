package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class ChwMachTypeUpgTest {
	
	@Test
	public void testSuccess() throws ClassNotFoundException, SQLException 
    {
		System.out.println("------------- Test ChwMachTypeUpg start -------------");
        String xmlPath = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
		String xml = CommonEntities.loadXml(xmlPath);
		String pdhconnect= "fvtcloudpdh";
		Connection pdh_connection = ConnectionFactory.getConnection(pdhconnect);
		
		String odsconnect= "fvtcloudods";
		Connection ods_connection = ConnectionFactory.getConnection(odsconnect);
		//
		ChwMachTypeUpg ChwMachTypeUpg = new ChwMachTypeUpg(xml,pdh_connection,ods_connection);
		ChwMachTypeUpg.execute();
        System.out.println("------------- Test ChwMachTypeUpg end -------------");
    }

}
