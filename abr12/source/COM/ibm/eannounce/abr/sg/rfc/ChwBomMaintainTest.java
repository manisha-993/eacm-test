package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChwBomMaintainTest {

    @BeforeClass
    public static void setUpBeforeClass()
    {
    	
    }
	
	
	
	@Test
	public void testSuccess() throws ClassNotFoundException, SQLException 
    {
		System.out.println("------------- Test ChwBomMaintainTest start -------------");
		
		ChwBomMaintain chwBomMaintain = new ChwBomMaintain("9080NEW","1222","0017NEW","5","depend");
        try
        {
        	chwBomMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = chwBomMaintain.createLogEntry();
        System.out.println(logEntry);
        assertEquals(chwBomMaintain.getRfcrc(), 0);
        System.out.println("------------- Test ChwBomMaintainTest end -------------");
    }
	
	

}
