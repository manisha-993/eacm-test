/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.HashMap;

import COM.ibm.eannounce.abr.sg.rfc.entity.OutputData;

import com.google.gson.annotations.SerializedName;


/**
 * The RdhGetMaxClass300Suffix returns the highest suffix (number) for a classification name "stem".  
 * For example, if the caller supplies the class_name input parameter with a value of "MK_5751CS3_" 
 * and classifications are defined with names "MK_5751CS3_001" through "MK_5751CS3_007", 
 * then this function will return "007".  If there are no classifications which begin with 
 * the stem value of "MK_5751CS3_", then a return code value of "4" (not found) is returned.
 * @author will
 *
 */
public class ChwGetMaxClass300Suffix extends RdhBase
{
    @SerializedName("CLASS_NAME_STEM")
    private String class_name_stem;
    @SerializedName("MAX_SUFFIX")
    private int max_suffix;
    
    public ChwGetMaxClass300Suffix(String obj_id, String class_name_stem)
    {
        super(obj_id,
                "RDH_GET_MAX_CLASS300_SUFFIX".toLowerCase(),null);
        this.pims_identity = "H";
        this.class_name_stem = class_name_stem;
        
    }

    @Override
    protected boolean isReadyToExecute()
    {
        return true;
    }
    
    
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
                String suffix = returnMap.get("MAX_SUFFIX");
                if(suffix != null)
                {
                    this.max_suffix = Integer.parseInt(suffix);
                }
            }
        }
    }

    /**
     * @return the max_suffix
     */
    public int getMax_suffix()
    {
        return max_suffix;
    }

    /* (non-Javadoc)
     * @see com.ibm.sdpi.cmd.interfaces.rdh.esw.caller.RdhBase#isValidRfcrc()
     */
    @Override
    protected boolean isValidRfcrc()
    {
        return this.getRfcrc() == 4 || this.getRfcrc() == 0;
    }
}
