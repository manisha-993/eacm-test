/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSABRSTATUS;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_api_ausp;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_klah;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_kssk;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_mara;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_object_key;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_rcuco;
import COM.ibm.eannounce.abr.util.DateUtility;

import com.google.gson.annotations.SerializedName;


/**
 * The RdhClassificationMaint java class assigns a classification definition (ex. "MM_FIELDS") to a material master.  
 * If characteristics and values are supplied with the RFC call, this remote function will assign the attributes 
 * associated with a characteristic.
 * @author will
 *
 */
public class RdhClassificationMaint extends RdhBase
{
    @SerializedName("CHARVAL_REFRESH")
    private String charval_refresh;
    @SerializedName("OBJECT_KEY")
    private List<RdhClaf_object_key> object_keys;
    @Foo
    private RdhClaf_object_key object_key;
    
    @SerializedName("KLAH")
    private List<RdhClaf_klah> klahs;
    @Foo
    private RdhClaf_klah klah;
    
    @SerializedName("KSSK")
    private List<RdhClaf_kssk> kssks;
    @Foo
    private RdhClaf_kssk kssk;
    
    @SerializedName("RCUCO")
    private List<RdhClaf_rcuco> rcucos;
    @Foo
    private RdhClaf_rcuco rcuco;
    
    @SerializedName("MARA")
    private List <RdhClaf_mara> maras;
    @Foo
    private RdhClaf_mara mara;
    
    @SerializedName("API_AUSP")
    private List<RdhClaf_api_ausp> api_ausp;
    
   /* *//**
     * Constructor
     * @param productSchedule
     * @param obj_id
     * @param class_name
     * @param class_type
     *//*
    public RdhClassificationMaint ( String obj_id, String class_name, String class_type ){
        super("",
                "Z_DM_SAP_CLASSIFICATION_MAINT".toLowerCase(), null);
       
        charval_refresh="1";
        
        
        object_key = new RdhClaf_object_key();
        object_key.setKey_feld("MATNR");
        object_key.setKpara_valu(obj_id);
        object_keys = new ArrayList<RdhClaf_object_key>();
        object_keys.add(object_key);
        
        klah = new RdhClaf_klah();
        klah.set_class(class_name);
        kssk = new RdhClaf_kssk();
        kssk.setKlart(class_type);
        
        rcuco= new RdhClaf_rcuco();
        rcuco.s
        klahs = new ArrayList<RdhClaf_klah>();
        klahs.add(klah);
       
        kssks = new ArrayList<RdhClaf_kssk>();
        kssks.add(kssk);
        api_ausp = new ArrayList<RdhClaf_api_ausp>();
    }*/
    /**
     * Constructor
     * @param productSchedule
     * @param obj_id
     * @param class_name
     * @param class_type
     * @param enablementprocess
     */
    public RdhClassificationMaint (String obj_id, String class_name, String class_type )
    {
    	super(obj_id,"Z_DM_SAP_CLASSIFICATION_MAINT".toLowerCase(),null);
    	charval_refresh = "1";
    	object_key = new RdhClaf_object_key();
    	object_key.setKey_feld("MATNR");
    	object_key.setKpara_valu(obj_id);
    	object_keys = new ArrayList<RdhClaf_object_key>();
    	object_keys.add(object_key);
    	klah = new RdhClaf_klah();
    	klah.set_class(class_name);
    	klahs = new ArrayList<RdhClaf_klah>();
    	klahs.add(klah);
    	kssk = new RdhClaf_kssk();
    	kssk.setKlart(class_type);
    	kssks = new ArrayList<RdhClaf_kssk>();
    	kssks.add(kssk);
    	api_ausp = new ArrayList<RdhClaf_api_ausp>();
    }
    
    /**
     * Adds a characteristic and its value to an SAP classification.
     * @param charact Characteristic name
     * @param value Characteristic value
     * @param abr 
     */
    public void addCharacteristic (String charact, String value)
    {
        RdhClaf_api_ausp apiausp = new RdhClaf_api_ausp();
        apiausp.setCharact(charact);
        apiausp.setValue(value);
        api_ausp.add(apiausp);
		/*
		 * if(value!=null&&value.length()>0) {
		 * 
		 * api_ausp.add(apiausp); }
		 */
    }
    
    /**
     * Returns the number of characteristics which have been added to the RdhClassificationMaint object.
     * @return
     */
    public int getCharacteristicCount()
    {
        return api_ausp.size();
    }
    
    public String getClassificationName(){
        return klah.get_class();
    }
    
    /* (non-Javadoc)
     * @see com.ibm.sdpi.cmd.interfaces.rdh.esw.caller.RdhBase#setDefaultValues()
     */
    @Override
    protected void setDefaultValues()
    {
        super.setDefaultValues();
        charval_refresh = "1";
        rcuco = new RdhClaf_rcuco();
        rcuco.setObtab("MARA");
        rcucos = new ArrayList<RdhClaf_rcuco>();
        rcucos.add(rcuco);
        mara = new RdhClaf_mara();
        mara.setErsda(DateUtility.getTodayStringWithSapFormat());
        maras = new ArrayList<RdhClaf_mara>();
        maras.add(mara);
    }

    @Override
    protected boolean isReadyToExecute()
    {
        if(this.checkFieldsNotEmplyOrNull(object_key, "kpara_valu"))
        {
            
            if(this.checkFieldsNotEmplyOrNull(klah, "_class"))
            {
                
                if(this.checkFieldsNotEmplyOrNull(kssk, "klart"))
                {
                    return checkFieldsNotEmplyOrNullInCollection(api_ausp,"value");
                }
            }
        }
        return false;
//        if (isNullOrBlank(object_key.getKpara_valu()))
//        {
//            this.setRfcrc(8);
//            this.setError_text("The Rdhclaf_object_key.kpara_valu attribute is not set to a value");
//            return false;
//        }
//        if (isNullOrBlank(klah.get_class()))
//        {
//            this.setRfcrc(8);
//            this.setError_text("The RdhClaf_klah.class attribute is not set to a value");
//            return false;
//        }
//        if (isNullOrBlank(kssk.getKlart()))
//        {
//            this.setRfcrc(8);
//            this.setError_text("The RdhClaf_kssk.klart attribute is not set to a value");
//            return false;
//        }
//        return true;
    }

}
