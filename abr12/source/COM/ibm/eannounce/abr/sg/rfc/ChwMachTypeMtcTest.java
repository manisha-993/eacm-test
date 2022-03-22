package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class ChwMachTypeMtcTest {
	
	@Test
	public void testSuccess() throws Exception 
    {
		System.out.println("------------- Test ChwMachTypeMtc start -------------");
        String xmlPath = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
		String xml = CommonEntities.loadXml(xmlPath);
		MODEL chwModel = CommonEntities.getModelFromXml(xml);
		String pdhconnect= "fvtcloudpdh";
		Connection pdh_connection = ConnectionFactory.getConnection(pdhconnect);
		
		String odsconnect= "fvtcloudods";
		Connection ods_connection = ConnectionFactory.getConnection(odsconnect);
		//
		ChwMachTypeMtc ChwMachTypeMtc = new ChwMachTypeMtc(chwModel,pdh_connection,ods_connection);
		ChwMachTypeMtc.execute();
        System.out.println("------------- Test ChwMachTypeMtc end -------------");
    }

}
