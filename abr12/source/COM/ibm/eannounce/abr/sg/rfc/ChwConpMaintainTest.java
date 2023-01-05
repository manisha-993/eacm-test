/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChwConpMaintainTest
{
    private static String obj_id = null;
    
    @BeforeClass
    public static void setUpBeforeClass()
    {
        obj_id = "5689R2N";
    }
    
    @Test
    public void testConstructor()
    {
        System.out.println("------------- Test Constructor start -------------");
       /* ChwConpMaintain conpMaintain =new ChwConpMaintain(
                obj_id, "PROFILE 1","SD01","2","design");
        try{
            conpMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = conpMaintain.createLogEntry();
        assertEquals(conpMaintain.getRfcrc(), 8);
        System.out.println(logEntry);*/
        System.out.println("------------- Test Constructor end -------------");
    }
    
    @Test
    public void testAddConfigDependency()
    {
        System.out.println("------------- Test addConfigDependency start -------------");
      /*  ChwConpMaintain conpMaintain =new ChwConpMaintain(
                obj_id, "PROFILE 1","SD01","2","design");
        try{
            conpMaintain.addConfigDependency("MK_INITIALIZE", "");
            conpMaintain.execute();
            
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = conpMaintain.createLogEntry();
        assertEquals(conpMaintain.getRfcrc(), 8);
        System.out.println(logEntry);*/
        System.out.println("------------- Test addConfigDependency end -------------");
    }
    
    
    
    @Test
    public void testIsReadyToExecuteWithoutKpara_valu()
    {
        System.out.println("------------- Test IsReadyToExecuteWithoutKpara_valu start -------------");
       /* ChwConpMaintain conpMaintain =new ChwConpMaintain(
                obj_id,"PROFILE 1","SD01","2","design");
        try{
            conpMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = conpMaintain.createLogEntry();
        assertEquals(conpMaintain.getRfcrc(), 8);
        assertTrue(logEntry.indexOf("RdhConp_cpdep_dat.dep_intern")>0);
        System.out.println(logEntry);
        System.out.println("------------- Test IsReadyToExecuteWithoutKpara_valu end -------------");*/
    }
    
    @Test
    public void testIsReadyToExecuteWithoutDep_intern()
    {
        System.out.println("------------- Test IsReadyToExecuteWithoutDep_intern start -------------");
      /*  ChwConpMaintain conpMaintain =new ChwConpMaintain(
                obj_id, "PROFILE 1","SD01","2","design");
        try{
            conpMaintain.addConfigDependency("", "");
            conpMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = conpMaintain.createLogEntry();
        assertEquals(conpMaintain.getRfcrc(), 8);
        assertTrue(logEntry.indexOf("RdhConp_cpdep_dat.dep_intern")>0);
        System.out.println(logEntry);
        System.out.println("------------- Test IsReadyToExecuteWithoutDep_intern end -------------");*/
    }
    
    @Test
    public void testSuccess()
    {
        System.out.println("------------- Test IsReadyToExecuteWithoutDep_intern start -------------");
       /* ChwConpMaintain conpMaintain =new ChwConpMaintain(
                obj_id, "PROFILE 1","SD01","2","design");
        try{
            conpMaintain.addConfigDependency("MK_S017LF1_EE_PSELECTED", "MK");
            conpMaintain.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        String logEntry = conpMaintain.createLogEntry();
        assertEquals(conpMaintain.getRfcrc(), 8);
        assertTrue(logEntry.indexOf("RdhConp_cpdep_dat.dep_intern")>0);
        System.out.println(logEntry);*/
        System.out.println("------------- Test IsReadyToExecuteWithoutDep_intern end -------------");
    }
    
    
}
