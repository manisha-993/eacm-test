/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChwClsfCharCreateTest
{
    private static String obj_id = null;
    
    @BeforeClass
    public static void setUpBeforeClass()
    {
        obj_id = "9080NEW";
    }
    
    @Test
    public void testCreateGroupChar()
    {
        System.out.println("------------- Test CreateGroupChar start -------------");
        ChwClsfCharCreate ChwClsfCharCreate = new ChwClsfCharCreate();
        
        try{
        	obj_id = "9080NEW";
        	String target_indc="T";
        	String mach_type ="9080";
        	String feature_code="1234";
        	String feature_code_desc="FC1234";
        //	ChwClsfCharCreate.CreateGroupChar(obj_id,target_indc,mach_type,feature_code,feature_code_desc);
        } catch (Exception e)
        {
            System.err.println("@@@@" +e.getMessage());
        }
        System.out.println("------------- Test CreateGroupChar log Begin -------------");
        System.out.println(ChwClsfCharCreate.getRptSb().toString());
        System.out.println("------------- Test CreateGroupChar log end -------------");
        System.out.println("------------- Test CreateGroupChar end -------------");
    }
    
    
    @Test
    public void testCreateQTYChar()
    {
        System.out.println("------------- Test CreateQTYChar start -------------");
        ChwClsfCharCreate ChwClsfCharCreate = new ChwClsfCharCreate();
        
        try{
        	obj_id = "9080NEW";
        	String target_indc="T";
        	String mach_type ="9080";
        	String feature_code="1234";
        	//ChwClsfCharCreate.CreateQTYChar(obj_id,target_indc,mach_type,feature_code);
        } catch (Exception e)
        {
            System.err.println("@@@@" +e.getMessage());
        }
        System.out.println("------------- Test CreateQTYChar log Begin -------------");
        System.out.println(ChwClsfCharCreate.getRptSb().toString());
        System.out.println("------------- Test CreateQTYChar log end -------------");
        System.out.println("------------- Test CreateQTYChar end -------------");
    }
    
    @Test
    public void testCreateRPQGroupChar()
    {
        System.out.println("------------- Test CreateRPQGroupChar start -------------");
        ChwClsfCharCreate ChwClsfCharCreate = new ChwClsfCharCreate();
        
        try{
        	obj_id = "9080NEW";
        	String target_indc="T";
        	String mach_type ="9080";
        	String feature_code="1234";
        	String feature_code_desc="FC1234";
        	//ChwClsfCharCreate.CreateRPQGroupChar(obj_id,target_indc,mach_type,feature_code,feature_code_desc);
        } catch (Exception e)
        {
            System.err.println("@@@@" +e.getMessage());
        }
        System.out.println("------------- Test CreateRPQGroupChar log Begin -------------");
        System.out.println(ChwClsfCharCreate.getRptSb().toString());
        System.out.println("------------- Test CreateRPQGroupChar log end -------------");
        System.out.println("------------- Test CreateRPQGroupChar end -------------");
    }
    
    
    @Test
    public void testCreateRPQQTYChar()
    {
        System.out.println("------------- Test CreateRPQQTYChar start -------------");
        ChwClsfCharCreate ChwClsfCharCreate = new ChwClsfCharCreate();
        
        try{
        	obj_id = "9080NEW";
        	String target_indc="T";
        	String mach_type ="9080";
        	String feature_code="1234";
        	//ChwClsfCharCreate.CreateRPQQTYChar(obj_id,target_indc,mach_type,feature_code);
        } catch (Exception e)
        {
            System.err.println("@@@@" +e.getMessage());
        }
        System.out.println("------------- Test CreateRPQQTYChar log Begin -------------");
        System.out.println(ChwClsfCharCreate.getRptSb().toString());
        System.out.println("------------- Test CreateRPQQTYChar log end -------------");
        System.out.println("------------- Test CreateRPQQTYChar end -------------");
    }
    
    
    @Test
    public void testCreateAlphaGroupChar()
    {
        System.out.println("------------- Test CreateAlphaGroupChar start -------------");
        ChwClsfCharCreate ChwClsfCharCreate = new ChwClsfCharCreate();
        
        try{
        	obj_id = "9080NEW";
        	String target_indc="T";
        	String mach_type ="9080";
        	String feature_code="1234";
        	String feature_code_desc="FC1234";
        	//ChwClsfCharCreate.CreateRPQGroupChar(obj_id,target_indc,mach_type,feature_code,feature_code_desc);
        } catch (Exception e)
        {
            System.err.println("@@@@" +e.getMessage());
        }
        System.out.println("------------- Test CreateAlphaGroupChar log Begin -------------");
        System.out.println(ChwClsfCharCreate.getRptSb().toString());
        System.out.println("------------- Test CreateAlphaGroupChar log end -------------");
        System.out.println("------------- Test CreateAlphaGroupChar end -------------");
    }
    
    
    @Test
    public void testCreateAlphaQTYChar()
    {
        System.out.println("------------- Test  CreateAlphaQTYChar start -------------");
        ChwClsfCharCreate ChwClsfCharCreate = new ChwClsfCharCreate();
        
        try{
        	obj_id = "7890NEW";
        	String target_indc="T";
        	String mach_type ="9080";
        	String feature_code="1234";
        	//ChwClsfCharCreate. CreateAlphaQTYChar(obj_id,target_indc,mach_type,feature_code,obj_id);
        } catch (Exception e)
        {
            System.err.println("@@@@" +e.getMessage());
        }
        System.out.println("------------- Test CreateAlphaQTYChar log Begin -------------");
        System.out.println(ChwClsfCharCreate.getRptSb().toString());
        System.out.println("------------- Test CreateRPQQTYChar log end -------------");
        System.out.println("------------- Test CreateRPQQTYChar end -------------");
    }
    
    
}
