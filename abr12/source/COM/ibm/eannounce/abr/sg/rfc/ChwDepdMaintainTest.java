/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;


public class ChwDepdMaintainTest
{
    private static String obj_id = null;

    @BeforeClass
    public static void setUpBeforeClass() {
        obj_id = "5689R1N";
    }

    @Test
    public void testCreateDepdWithoutDep_Extern()
    {
        ChwDepdMaintain status = new ChwDepdMaintain(obj_id,null,null,null);
        try{
            status.execute();
        }catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        String logEntry = status.createLogEntry();
        assertTrue(logEntry.indexOf("RdhDepd_dep_ident.dep_extern")>0);
        assertEquals(status.getRfcrc(), 8);
        System.out.println(logEntry);
    }
    
    @Test
    public void testCreateDepdWithoutDep_type()
    {
        ChwDepdMaintain status = new ChwDepdMaintain(obj_id,"5609Test",null,null);
        try{
            status.execute();
        }catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        String logEntry = status.createLogEntry();
        assertTrue(logEntry.indexOf("RdhDepd_depdat.dep_type")>0);
        assertEquals(status.getRfcrc(), 8);
        System.out.println(logEntry);
    }
    
    @Test
    public void testCreateDepdWithoutDescript()
    {
        ChwDepdMaintain status = new ChwDepdMaintain(obj_id,"5609Test","0147",null);
        try{
            status.execute();
        }catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        String logEntry = status.createLogEntry();
        assertTrue(logEntry.indexOf("RdhDepd_depdescr.descript")>0);
        assertEquals(status.getRfcrc(), 8);
        System.out.println(logEntry);
    }
    
    
    @Test
    public void testCreateDepd()
    {
        ChwDepdMaintain status = new ChwDepdMaintain(obj_id,"MK_S017LF1_EE_PSELECTED","type","descr");
        try{
            status.execute();
        }catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        String logEntry = status.createLogEntry();
        
        assertEquals(status.getRfcrc(), 0);
        System.out.println(logEntry);
    }
    
    @Test
    public void testCreateDepdAddLine()
    {
        ChwDepdMaintain status = new ChwDepdMaintain(obj_id,"MK_S017LF1_EE_PSELECTED","type","descr");
        try{
            status.addSourceLineCondition("MK_5799_BB1_2201 = 'S01728S'");
            status.execute();
        }catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        String logEntry = status.createLogEntry();
        
        assertEquals(status.getRfcrc(), 8);
        System.out.println(logEntry);
    }
}
