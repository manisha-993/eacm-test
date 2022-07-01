/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaa_rmclm;

import com.google.gson.annotations.SerializedName;


public class ChwAssignCharToClass extends RdhBase
{
    @SerializedName("J_CLASS")
    private String j_class;
    @SerializedName("J_KLART")
    private String j_klart;
    @SerializedName("MATERIAL")
    private String material;
    @SerializedName("RMCLM")
    private List<RdhClaa_rmclm> rmclms;
    @Foo
    private RdhClaa_rmclm rmclm;
    
    public ChwAssignCharToClass(String obj_id, String clazz, String characteristic, String org_area, String material)
    {
        super(obj_id,"Z_DM_SAP_ASSIGN_CHAR_TO_CLASS".toLowerCase(), null);
        this.pims_identity = "H";
        j_class = clazz;
        j_klart = "300";
        this.material = material;
        rmclm = new RdhClaa_rmclm();
        rmclm.setAbtei(org_area);
        rmclm.setMerkma(characteristic);
        rmclms = new ArrayList<RdhClaa_rmclm>();
        rmclms.add(rmclm);
    }
    
    @Override
    protected boolean isReadyToExecute()
    {
        if(this.checkFieldsNotEmplyOrNull(this, "j_class"))
        {
            
            return this.checkFieldsNotEmplyOrNull(rmclm, "merkma");
        }
        return false;
//        if (isNullOrBlank(j_class))
//        {
//            this.setRfcrc(8);
//            this.setError_text("The RdhAssignCharToClass.j_class attribute is not set to a value");
//            return false;
//        }
//        if (isNullOrBlank(rmclm.getMerkma()))
//        {
//            this.setRfcrc(8);
//            this.setError_text("The RdhClaa_rmclm.merkma attribute is not set to a value");
//            return false;
//        }
//        return true;
    }

    /* (non-Javadoc)
     * @see com.ibm.sdpi.cmd.interfaces.rdh.esw.caller.RdhBase#isValidRfcrc()
     */
    @Override
    protected boolean isValidRfcrc()
    {
        return this.getRfcrc() == 2 || this.getRfcrc() == 0;
    }
}
