/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClas_cla_ch_atr;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClas_cla_descr;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClas_clclasses;
import COM.ibm.eannounce.abr.util.DateUtility;

import com.google.gson.annotations.SerializedName;


/**
 * creates a classification definition (ex. "MK_5655DB2_001").
 * @author wangyul
 *
 */
public class ChwClassMaintain extends RdhBase
{

    // private ProductSchedule productSchedule;
    // private String class_;
    // private String class_desc;
    @SerializedName("CLCLASSES")
    private List<RdhClas_clclasses> clclasses_list;
    @Foo
    private RdhClas_clclasses clclasses;
    @SerializedName("CLA_DESCR")
    private List<RdhClas_cla_descr> cla_descr_list;
    @Foo
    private RdhClas_cla_descr cla_descr;
    @SerializedName("CLA_CH_ATR")
    private List<RdhClas_cla_ch_atr> cla_ch_atr;

    /**
     * @param productSchedule The product schedule which supplies the handle and
     * details of the product data feed.
     * @param class Classification name (ex. "MK_5655DB2_001").
     * @param class_desc Descriptive name of classification (ex.
     * "Class for SWO 5655DB2").
     */
    public ChwClassMaintain (String obj_id, String class_, String class_desc){

        super(obj_id, "Z_DM_SAP_CLASS_MAINTAIN".toLowerCase(),null);
        this.rfa_num = obj_id;   
        this.pims_identity = "H";
        this.default_mandt = "10H";
		

        clclasses.set_class(class_);
        cla_descr.set_class(class_);
        cla_descr.setCatchword(class_desc);
    }

    @Override
    protected void setDefaultValues()
    {
        super.setDefaultValues();
        clclasses = new RdhClas_clclasses();
        cla_descr = new RdhClas_cla_descr();
        cla_ch_atr = new ArrayList<RdhClas_cla_ch_atr>();
        clclasses_list = new ArrayList<RdhClas_clclasses>();
        clclasses_list.add(clclasses);
        cla_descr_list = new ArrayList<RdhClas_cla_descr>();
        cla_descr_list.add(cla_descr);
        // RdhClas_clclasses status Set to "1".
        clclasses.setStatus("1");
        // RdhClas_clclasses class_type Set to "300".
        clclasses.setClass_type("300");
        // RdhClas_clclasses org_area Set to "PM".
        clclasses.setOrg_area("PM");
        // RdhClas_clclasses val_from Set to current date. Format is YYYYMMDD.
        clclasses.setVal_from(DateUtility.getTodayStringWithSapFormat());
        // RdhClas_clclasses val_to Set to "99991231".
        clclasses.setVal_to("99991231");
        // RdhClas_clclasses check_no Set to "X".
        clclasses.setCheck_no("X");
        
        // RdhClas_cla_descr class_type Set to "300.
        cla_descr.setClass_type("300");
        // RdhClas_cla_descr language Set to "E"
        cla_descr.setLanguage("E");
    }

    /**
     * Create an RdhClas_cla_ch_atr object and add to the
     * RdhClassMaintain.cla_ch_atr collection.
     * 
     * @param charact
     */
    public void addCharacteristic(String charact)
    {
        RdhClas_cla_ch_atr chatr = new RdhClas_cla_ch_atr();
        chatr.set_class(clclasses.get_class());
        chatr.setClass_type(clclasses.getClass_type());
        chatr.setCharact(charact);
        cla_ch_atr.add(chatr);
    }

    @Override
    protected boolean isReadyToExecute()
    {
        // The following attributes must have string values with length > 0:
        // RdhClas_clclasses.class
        // RdhClas_cla_descr.catch_word
        // RdhClas_cla_ch_atr.charact for each RdhClas_cla_ch_atr object
        // If all of the above attributes meet the validation requirements, then
        // return TRUE.
        // If any of the above attributes fail the validation requirements:
        // Set the <RdhBase.rfcrc> attribute to '8'.
        // Set the <RdhBase.error_text> attribute to an error message. The error
        // message text should identify the attribute and the reason for the
        // failure (ex. "The zzfv_obj attribute is not set to a value").
        // Return FALSE.
        
        if(this.checkFieldsNotEmplyOrNull(clclasses, "cLASS"))
        {
            if(this.checkFieldsNotEmplyOrNull(cla_descr, "catchword"))
            {
                for (RdhClas_cla_ch_atr atr : cla_ch_atr)
                {
                    if (isNullOrBlank(atr.getCharact()))
                    {
                        this.setRfcrc(8);
                        this.setError_text("One RdhClas_cla_ch_atr.charact attribute is not set to a value(class="
                                + atr.get_class() + " classtype=" + atr.getClass_type() + ")");
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
        
//        if (isNullOrBlank(clclasses.get_class()))
//        {
//            this.setRfcrc(8);
//            this.setError_text("The RdhClas_clclasses.class attribute is not set to a value");
//            return false;
//        }
//        if (isNullOrBlank(this.cla_descr.getCatchword()))
//        {
//            this.setRfcrc(8);
//            this.setError_text("The RdhClas_cla_descr.catch_word attribute is not set to a value");
//            return false;
//        }
//        for (RdhClas_cla_ch_atr atr : cla_ch_atr)
//        {
//            if (isNullOrBlank(atr.getCharact()))
//            {
//                this.setRfcrc(8);
//                this.setError_text("One RdhClas_cla_ch_atr.charact attribute is not set to a value(class="
//                        + atr.get_class() + " classtype=" + atr.getClass_type() + ")");
//                return false;
//            }
//        }
//        return true;
    }

}
