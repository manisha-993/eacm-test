package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChwReadSalesBomTest {

    @BeforeClass
    public static void setUpBeforeClass()
    {
    	
    }
	
	
	
	@Test
	public void testSuccess() throws ClassNotFoundException, SQLException 
    {
		System.out.println("------------- Test ChwReadSalesBom start -------------");
		
		ChwReadSalesBom ChwReadSalesBom = new ChwReadSalesBom("0001NEW","1222");
        try
        {
        	ChwReadSalesBom.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        System.out.println("111");
        String logEntry = ChwReadSalesBom.createLogEntry();
        ChwReadSalesBom.getError_text();
        System.out.println("222");
        System.out.println(logEntry);
        System.out.println("333");
        System.out.println(ChwReadSalesBom.getRETURN_MULTIPLE_OBJ());
        System.out.println("444");
        assertEquals(ChwReadSalesBom.getRfcrc(), 0);
        System.out.println("------------- Test ChwReadSalesBom end -------------");
    }
	
	@Test
	public void testNotfound() throws ClassNotFoundException, SQLException 
    {
		System.out.println("------------- Test ChwReadSalesBom testNotfound start -------------");
		
		ChwReadSalesBom ChwReadSalesBom = new ChwReadSalesBom("0002NEW","1222");
        try
        {
        	ChwReadSalesBom.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = ChwReadSalesBom.createLogEntry();
        System.out.println(logEntry);
        assertEquals(ChwReadSalesBom.getRfcrc(), 8);
        System.out.println("------------- Test ChwReadSalesBom testNotfound end -------------");
    }

}
