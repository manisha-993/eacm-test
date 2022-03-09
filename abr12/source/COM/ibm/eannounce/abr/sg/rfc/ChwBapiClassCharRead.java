/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.OutputData;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_cpdep_dat;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_cpro_attr;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_object_key;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_rcuco;

import com.google.gson.annotations.SerializedName;

/**
 * creates a configuration profile for a material master
 * @author wangyul
 *
 */
public class ChwBapiClassCharRead extends RdhBase
{
     
	@SerializedName("OBJ_ID")
    private String obj_id;
	@SerializedName("FEATURE_CODE")
	private String feature_code;
	
	@SerializedName("TARGET_INDC")
	private String target_indc;
	
	@SerializedName("CHAR_TYPE")
	private String char_type;
	
	private String suffix;
	
    public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public ChwBapiClassCharRead(String obj_id, String feature_code, 
    		String target_indc, String char_type)
    {
        super(obj_id, "BAPI_CLASS_CHAR_READ".toLowerCase(), null);
        this.pims_identity = "H";
        this.obj_id = obj_id;
        this.default_mandt ="10H";
        
        this.feature_code = feature_code;
        this.target_indc = target_indc;
        this.char_type = char_type;
        
       
    }

    @Override
    protected boolean isReadyToExecute()
    { 
        if(this.checkFieldsNotEmplyOrNull(feature_code, "feature_code")){
            if(this.checkFieldsNotEmplyOrNull(target_indc, "target_indc")){
            	return this.checkFieldsNotEmplyOrNull(char_type, "char_type");
            }
        }
        return false;
    }
    
 
    /* (non-Javadoc)
     * @see com.ibm.sdpi.cmd.interfaces.rdh.esw.caller.RdhBase#setDefaultValues()
     */
    @Override
    protected void setDefaultValues()
    {
        
    }
    
//    protected String saveRfcResults (){
//    	this.suffix = this.getError_text();  
//    	return suffix;
//    }
    @Override
    public void saveRfcResults(OutputData output) 
    {
        super.saveRfcResults(output);
        HashMap<String, HashMap<String, String>> return_OBJ = output.getRETURN_OBJ();
        if(return_OBJ != null)
        {
        	HashMap<String,String> returnMap = return_OBJ.get("RETURNMAP");
            if(returnMap != null)
            {
                String suffix = returnMap.get("SUFFIX");
                if(suffix != null)
                {
                    this.suffix = suffix;
                }
            }
            
        }
    }
    
    
}
