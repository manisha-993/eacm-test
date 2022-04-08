/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;


public class ChwBapiClassCharReadTest
{
	private static String obj_id;
    
    @BeforeClass
    public static void setUpBeforeClass()
    {
    	obj_id = "9080NEW";
    }
    
    @Test
    public void testConstructor()
    {
        System.out.println("-------------  testConstructor start -------------");
        ChwBapiClassCharRead charToClass =new ChwBapiClassCharRead(obj_id, "ESKT", "T", "G");
        try{
           charToClass.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = charToClass.createLogEntry();
        assertEquals(charToClass.getRfcrc(), 8);
        System.out.println(logEntry);
        System.out.println("-------------  testConstructor end -------------");
    }
    
    
//    @Test
//    public void testIsReadyToExecuteWithoutJCLASS()
//    {
//        System.out.println("-------------  testIsReadyToExecuteWithoutJCLASS start -------------");
//        ChwBapiClassCharRead charToClass =new ChwBapiClassCharRead(obj_id, "", "CLSNAME", "CLSTYPE");
//        try{
//            charToClass.execute();
//        } catch (Exception e)
//        {
//            System.err.println(e.getMessage());
//        }
//
//        String logEntry = charToClass.createLogEntry();
//        assertEquals(charToClass.getRfcrc(), 8);
//        assertTrue(logEntry.indexOf("ChwAssignCharToClass.j_class")>0);
//        System.out.println(logEntry);
//        System.out.println("-------------  testIsReadyToExecuteWithoutJCLASS end -------------");
//    }
//    
//    @Test
//    public void testIsReadyToExecuteWithoutMerkma()
//    {
//        System.out.println("-------------  testIsReadyToExecuteWithoutMerkma start -------------");
//        ChwBapiClassCharRead charToClass =new ChwBapiClassCharRead(obj_id, "Test", "", 
//                "XXX");
//        try{
//            charToClass.execute();
//        } catch (Exception e)
//        {
//            System.err.println(e.getMessage());
//        }
//
//        String logEntry = charToClass.createLogEntry();
//        assertEquals(charToClass.getRfcrc(), 8);
//        assertTrue(logEntry.indexOf("RdhClaa_rmclm.merkma")>0);
//        System.out.println(logEntry);
//        System.out.println("-------------  testIsReadyToExecuteWithoutMerkma end -------------");
//    }

	
    
}
