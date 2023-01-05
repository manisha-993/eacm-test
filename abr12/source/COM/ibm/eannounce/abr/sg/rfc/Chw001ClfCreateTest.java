package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class Chw001ClfCreateTest {

	@Test
    public void testSuccess() throws Exception
    {
        System.out.println("------------- Test Chw001ClfCreate start -------------");
        String xmlPath = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
		String xml = CommonEntities.loadXml(xmlPath);
		String pdhconnect= "fvtcloudpdh";
		Connection pdh_connection = ConnectionFactory.getConnection(pdhconnect);
		
		String odsconnect= "fvtcloudods";
		Connection ods_connection = ConnectionFactory.getConnection(odsconnect);
		
		MODEL chwModel = CommonEntities.getModelFromXml(xml);
		
		//
      /*  Chw001ClfCreate Chw001ClfCreate = new Chw001ClfCreate(chwModel,"ZPRT","5773E53",ods_connection);
        Chw001ClfCreate.execute();
        Chw001ClfCreate.getRptSb().toString();
        */
        System.out.println("------------- Test Chw001ClfCreate end -------------");
    }

}
