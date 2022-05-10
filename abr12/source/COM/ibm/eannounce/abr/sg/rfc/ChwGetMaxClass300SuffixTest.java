package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChwGetMaxClass300SuffixTest
{
    private static String obj_id = null;
    @BeforeClass
    public static void setUpBeforeClass()
    {
    	obj_id ="ST00001Y";
    }
    
    @Test
    public void testGetMax_suffix() throws Exception
    {
        ChwGetMaxClass300Suffix getMaxClassSuffix = new ChwGetMaxClass300Suffix(obj_id,"MK_T_7778_ALPH_");
        try{
            getMaxClassSuffix.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        assertTrue(getMaxClassSuffix.getMax_suffix() >= 0);
        
    }

}
