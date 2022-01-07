package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;


public class ChwCharMaintainTest
{
    private static String obj_id = null;
    
    @BeforeClass
    public static void setUpBeforeClass()
    {
    	obj_id = "123456";
    }
    
    @Test
    public void testSuccess()
    {
        System.out.println("------------- Test CreateCharsWithoutCharDescr start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(
        		obj_id, "MK_5799_BB1_2201", "CHAR", 4, 
        		"0", "X", "X", "CHO", 
        		"s", "X", "X", "X", 
        		"D");
        try{
            charmaintain.execute();//TODO change that: There should be at least 1 row in the characts structure.
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertEquals(charmaintain.getRfcrc(), 8);
        System.out.println(logEntry);
        System.out.println("------------- Test CreateCharsWithoutCharDescr end -------------");
    }
    
    @Test
    public void testCreateCharsWithoutCharDescr()
    {
        System.out.println("------------- Test CreateCharsWithoutCharDescr start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(
        		obj_id, "Test", "CHAR", 2, 
        		null, null, null, null, 
        		null, null, null, null, 
        		null);
        try{
            charmaintain.execute();//TODO change that: There should be at least 1 row in the characts structure.
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertEquals(charmaintain.getRfcrc(), 8);
        System.out.println(logEntry);
        System.out.println("------------- Test CreateCharsWithoutCharDescr end -------------");
    }
    
    @Test
    public void testCreateCharsWithCharDescr()
    {
        System.out.println("------------- Test CreateCharsWithCharDescr start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(obj_id, "Test", "CHAR", 
                2, null, null, null, null, null, null, null, null, "TestDescr");
        try{
//            charmaintain.execute();//TODO two structures are not supplied in input json, that will cause 500 error.
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertEquals(charmaintain.getRfcrc(), 0);
        System.out.println(logEntry);
        System.out.println("------------- Test CreateCharsWithCharDescr end -------------");
    }
    
    @Test
    public void testAddChars()
    {
        System.out.println("------------- Test AddChars start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(obj_id, "Test", "TestVal","TestDescr");
        try{
            charmaintain.execute();//TODO change that: There should be at least 1 row in the characts structure.
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertEquals(charmaintain.getRfcrc(), 8);
        System.out.println(logEntry);
        System.out.println("------------- Test AddChars end -------------");
    }
    
    @Test
    public void testAddValue()
    {
        System.out.println("------------- Test addValue start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(obj_id, "Test", "TestVal","TestDescr");
        try{
            charmaintain.addValue("TestVal2", "TestDescr2");
//            charmaintain.execute();//TODO change that: There should be at least 1 row in the characts structure.
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertTrue(logEntry.indexOf("TestVal2")>0);
        assertTrue(logEntry.indexOf("TestVal")>0);
        assertTrue(logEntry.indexOf("TestDescr")>0);
        assertTrue(logEntry.indexOf("TestDescr2")>0);
//        assertEquals(charmaintain.getRfcrc(), 4);
        System.out.println(logEntry);
        System.out.println("------------- Test addValue end -------------");
    }
    
    @Test
    public void testIsReadyToExecuteWithoutCharact()
    {
        System.out.println("------------- Test IsReadyToExecuteWithoutCharact start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(obj_id, "", "CHAR", 
                2, null, null, null, null, null, null, null, null, null);
        try{
            charmaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertEquals(charmaintain.getRfcrc(), 8);
        assertTrue(logEntry.indexOf("characts.CHARACT")>0);
        System.out.println(logEntry);
        System.out.println("------------- Test IsReadyToExecuteWithoutCharact end -------------");
    }
    
    @Test
    public void testIsReadyToExecuteWithoutDataType()
    {
        System.out.println("------------- Test IsReadyToExecuteWithoutDataType start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(obj_id, "Test", "", 
                2, null, null, null, null, null, null, null, null, null);
        try{
            charmaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertEquals(charmaintain.getRfcrc(), 8);
        assertTrue(logEntry.indexOf("characts.DATATYPE")>0);
        System.out.println(logEntry);
        System.out.println("------------- Test IsReadyToExecuteWithoutDataType end -------------");
    }
    
    @Test
    public void testIsReadyToExecuteWithoutValue()
    {
        System.out.println("------------- Test IsReadyToExecuteWithoutValue start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(obj_id, "Test", "","TestDescr");
        try{
            charmaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertEquals(charmaintain.getRfcrc(), 8);
        assertTrue(logEntry.indexOf("char_vals[1].VALUE")>0);
        System.out.println(logEntry);
        System.out.println("------------- Test IsReadyToExecuteWithoutValue end -------------");
    }
    
    @Test
    public void testIsReadyToExecuteWithoutValDescr()
    {
        System.out.println("------------- Test IsReadyToExecuteWithoutValDescr start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(obj_id, "Test", "TestVal","");
        try{
            charmaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charmaintain.createLogEntry();
        assertEquals(charmaintain.getRfcrc(), 8);
        assertTrue(logEntry.indexOf("char_vals[1].CHARACT")>0);
        System.out.println(logEntry);
        System.out.println("------------- Test IsReadyToExecuteWithoutValDescr end -------------");
    }
    
    @Test
    public void testSetRefreshVals()
    {
        System.out.println("------------- Test setRefreshVals start -------------");
        ChwCharMaintain charmaintain =new ChwCharMaintain(obj_id, "Test", "TestVal","");
        try{
//            charmaintain.setRefreshVals(true);

            String logEntry = charmaintain.createLogEntry();
    //        assertEquals(charmaintain, 8);
    //        assertTrue(logEntry.indexOf("\"REFRESH_VALS\":\"X\"")>0);
            System.out.println(logEntry);
//            charmaintain.setRefreshVals(false);
//            logEntry = charmaintain.createLogEntry();
//            assertTrue(logEntry.indexOf("\"REFRESH_VALS\":\"\"")>0);
//            System.out.println(logEntry);
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        System.out.println("------------- Test setRefreshVals end -------------");
    }
}

