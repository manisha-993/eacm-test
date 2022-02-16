/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * creates a classification definition test (ex. "MK_5655DB2_001").
 * @author wangyul
 *
 */

public class ChwClassMaintainTest
{
    private static String obj_id = null;
    
    @BeforeClass
    public static void setUpBeforeClass()
    {
        obj_id = "ST00001Y";
//        SoftwareProduct prod = new SoftwareProduct();
//        obj_id.setMainProduct(prod);
//        prod.setProductIdentifier("5609-M03");
//        obj_id.setId("ST00001Y");
    }

    @Test
    public void testExecuteNullCharAdded()
    {
    	System.out.println("------------- Test ExecuteNullCharAdded start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001",
                "Class for SWO 5655DB2");
        try
        {
            classMaintain.addCharacteristic(null);
            classMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = classMaintain.createLogEntry();
        assertEquals(classMaintain.getRfcrc(), 8);
        System.out.println(logEntry.substring(logEntry.indexOf("Result")));
        System.out.println("------------- Test ExecuteNullCharAdded end -------------");
    }

    @Test
    public void testExecuteEmptyCharAdded()
    {
    	System.out.println("------------- Test ExecuteEmptyCharAdded start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001",
                "Class for SWO 5655DB2");
        try
        {
            classMaintain.addCharacteristic("");
            classMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = classMaintain.createLogEntry();
        assertEquals(classMaintain.getRfcrc(), 8);
        System.out.println(logEntry.substring(logEntry.indexOf("Result")));
        System.out.println("------------- Test ExecuteEmptyCharAdded end -------------");
    }

    @Test
    public void testExecuteNoCharAdded()
    {
    	System.out.println("------------- Test ExecuteNoCharAdded start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001",
                "Class for SWO 5655DB2");
        try
        {
            classMaintain.execute();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        assertEquals(classMaintain.getRfcrc(), 0);
        String logEntry = classMaintain.createLogEntry();
        System.out.println(logEntry);
        System.out.println(logEntry.substring(logEntry.indexOf("CLA_CH_ATR"), logEntry.indexOf("PIMS_IDENTITY")-3));
        System.out.println("------------- Test ExecuteNoCharAdded end -------------");
    }

    @Test
    public void testExecuteOneCharAdded()
    {
    	System.out.println("------------- Test ExecuteOneCharAdded start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001",
                "Class for SWO 5655DB2");
        try
        {
            classMaintain.addCharacteristic("TEST1");
            classMaintain.execute();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        assertEquals(classMaintain.getRfcrc(), 0);
        String logEntry = classMaintain.createLogEntry();
        System.out.println(logEntry.substring(logEntry.indexOf("CLA_CH_ATR"), logEntry.indexOf("PIMS_IDENTITY")-3));
        System.out.println("------------- Test ExecuteOneCharAdded end -------------");
    }

    @Test
    public void testExecuteMoreThanOneCharsAdded()
    {
    	System.out.println("------------- Test ExecuteMoreThanOneCharsAdded start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001",
                "Class for SWO 5655DB2");
        try
        {
            classMaintain.addCharacteristic("TEST1");
            classMaintain.addCharacteristic("TEST2");
            classMaintain.addCharacteristic("TEST3");
            classMaintain.execute();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        assertEquals(classMaintain.getRfcrc(), 0);
        String logEntry = classMaintain.createLogEntry();
        System.out.println(logEntry.substring(logEntry.indexOf("CLA_CH_ATR"), logEntry.indexOf("PIMS_IDENTITY")-3));
        System.out.println("------------- Test ExecuteMoreThanOneCharsAdded end -------------");
    }

    @Test
    public void testInvalidServiceNameSet()
    {
    	System.out.println("------------- Test InvalidServiceNameSet start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "INVALID_SERVICE_NAME",
                "Class for SWO 5655DB2");
        try
        {
            classMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        String logEntry = classMaintain.createLogEntry();
        assertEquals(classMaintain.getRfcrc(), 8);
        System.out.println(logEntry.substring(logEntry.indexOf("Result")));
        System.out.println("------------- Test InvalidServiceNameSet end -------------");
    }

    @Test
    public void testNullServiceNameSet()
    {
    	System.out.println("------------- Test NullServiceNameSet start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, null, "Class for SWO 5655DB2");
        try
        {
            classMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = classMaintain.createLogEntry();
        assertEquals(classMaintain.getRfcrc(), 8);
        System.out.println(logEntry.substring(logEntry.indexOf("Result")));
        System.out.println("------------- Test NullServiceNameSet end -------------");
    }

    @Test
    public void testEmptyServiceNameSet()
    {
    	System.out.println("------------- Test EmptyServiceNameSet start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "", "Class for SWO 5655DB2");
        try
        {
            classMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = classMaintain.createLogEntry();
        assertEquals(classMaintain.getRfcrc(), 8);
        System.out.println(logEntry.substring(logEntry.indexOf("Result")));
        System.out.println("------------- Test EmptyServiceNameSet end -------------");
    }

    @Test
    public void testNullClassDescIsSet()
    {
    	System.out.println("------------- Test NullClassDescIsSet start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", null);
        try
        {
            classMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        String logEntry = classMaintain.createLogEntry();
        assertEquals(classMaintain.getRfcrc(), 8);
        System.out.println(logEntry.substring(logEntry.indexOf("Result")));
        System.out.println("------------- Test NullClassDescIsSet end -------------");
    }

    @Test
    public void testEmptyClassDescIsSet()
    {
    	System.out.println("------------- Test EmptyClassDescIsSet start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "");
        try
        {
            classMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        String logEntry = classMaintain.createLogEntry();
        assertEquals(classMaintain.getRfcrc(), 8);
        System.out.println(logEntry.substring(logEntry.indexOf("Result")));
        System.out.println("------------- Test EmptyClassDescIsSet end -------------");
    }

    @Test
    public void testAllFieldsGenerated()
    {
    	System.out.println("------------- Test AllFieldsGenerated start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001",
                "Class for SWO 5655DB2");
        try
        {
            classMaintain.addCharacteristic("TEST");
            classMaintain.execute();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        String[] allField =
        { "PIMS_IDENTITY", "RFA_NUM", "DEFAULT_MANDT", "CLIENT_NAME", "N45BZDM_GEO_TO_CLASS", "Z_GEO",
                "CLCLASSES", "CLASS", "CLASS_TYPE", "STATUS", "VAL_FROM", "VAL_TO", "CHECK_NO", "ORG_AREA",
                "CLA_DESCR", "CLASS", "CLASS_TYPE", "LANGUAGE", "CATCHWORD", "CLA_CH_ATR", "CLASS", "CLASS_TYPE",
                "CHARACT" };
        String logEntry = classMaintain.createLogEntry();
        String inputData = logEntry.substring(logEntry.indexOf("{"), logEntry.lastIndexOf("}"));
        for (String field : allField)
        {
            assertTrue(inputData.contains(field));
        }
        String[] fields = inputData.split("\":");
        assertEquals(allField.length, fields.length - 1);
        System.out.println("------------- Test AllFieldsGenerated end -------------");
    }

    @Test
    public void testExecute()
    {
    	System.out.println("------------- Test Execute start -------------");
        ChwClassMaintain classMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001",
                "Class for SWO 5655DB2");
        try
        {
            classMaintain.execute();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // verify rfc result code
        assertEquals(classMaintain.getRfcrc(), 0);

        // The JSON should be compressed but contain a blank space after each
        // comma.
        String logEntry = classMaintain.createLogEntry();
        assertFalse(logEntry.replace(", ", " ").contains("  "));
        assertFalse(logEntry.replace(", ", "").contains(","));
        System.out.println("------------- Test Execute end -------------");
    }
}
