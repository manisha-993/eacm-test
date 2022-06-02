package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChwBomCreateTest {

    @BeforeClass
    public static void setUpBeforeClass()
    {
    	
    }
	
	
	
	@Test
	public void testSuccess() throws ClassNotFoundException, SQLException 
    {
		System.out.println("------------- Test ChwBomCreateTest start -------------");
		
		ChwBomCreate chwBomCreate = new ChwBomCreate("9080NEW","1222");
        try
        {
        	chwBomCreate.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = chwBomCreate.createLogEntry();
        System.out.println(logEntry);
        assertEquals(chwBomCreate.getRfcrc(), 0);
        System.out.println("------------- Test ChwBomCreateTest end -------------");
    }
	
	

}
