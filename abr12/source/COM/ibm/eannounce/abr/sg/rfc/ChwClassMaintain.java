/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSABRSTATUS;
import COM.ibm.eannounce.abr.sg.rfc.entity.ChwClas_cla_ch_atr;
import COM.ibm.eannounce.abr.sg.rfc.entity.ChwClas_cla_descr;
import COM.ibm.eannounce.abr.sg.rfc.entity.ChwClas_clclasses;
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
public class ChwClassMaintain extends RdhBase
{
   @Foo
   private String class_name;
    @SerializedName("CLCLASSES")
    ChwClas_clclasses chwClas_clclasses;
    @SerializedName("CLA_DESCR")
    ChwClas_cla_descr chwClas_cla_descr;
    @SerializedName("CLA_CH_ATR")
    List<ChwClas_cla_ch_atr> ChwClas =null;
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
    public ChwClassMaintain (String obj_id, String class_name, String class_desc  )
    {
    	super(obj_id,"Z_DM_SAP_CLASS_MAINTAIN".toLowerCase(),null);
    	 ChwClas = new ArrayList<ChwClas_cla_ch_atr>();
    	pims_identity="C";
    	this.class_name=class_name;
    	chwClas_clclasses.set_class(class_name);
    	chwClas_clclasses.setClass_type("300");
    	chwClas_clclasses.setStatus("1");
    	chwClas_clclasses.setVal_from(DateUtility.getTodayStringWithSimpleFormat());
		chwClas_clclasses.setVal_to("99991231");
		chwClas_clclasses.setCheck_no("X");
		chwClas_clclasses.setOrg_area("PM");
		
		chwClas_cla_descr.set_class(class_name);
		chwClas_cla_descr.setClass_type("300");
		chwClas_cla_descr.setLanguage("E");
		chwClas_cla_descr.setCatchword(class_desc);
    	/*
		 * object_key = new RdhClaf_object_key(); object_key.setKey_feld("MATNR");
		 * object_key.setKpara_valu(obj_id); object_keys = new
		 * ArrayList<RdhClaf_object_key>(); object_keys.add(object_key); klah = new
		 * RdhClaf_klah(); klah.set_class(class_name); klahs = new
		 * ArrayList<RdhClaf_klah>(); klahs.add(klah); kssk = new RdhClaf_kssk();
		 * kssk.setKlart(class_type); kssks = new ArrayList<RdhClaf_kssk>();
		 * kssks.add(kssk); api_ausp = new ArrayList<RdhClaf_api_ausp>();
		 */
    }
    
    /**
     * Adds a characteristic and its value to an SAP classification.
     * @param charact Characteristic name
     * @param value Characteristic value
     * @param abr 
     */
    public void addCharacteristic (String charact)
    {
    	ChwClas_cla_ch_atr atr = new ChwClas_cla_ch_atr();
        
		 atr.set_class(class_name);
		 atr.setCharact(charact);
		 atr.setClass_type("300");
		  ChwClas.add(atr);
		 
    }
    
  
    /* (non-Javadoc)
     * @see com.ibm.sdpi.cmd.interfaces.rdh.esw.caller.RdhBase#setDefaultValues()
     */
    @Override
    protected void setDefaultValues()
    {
        super.setDefaultValues();
       
        chwClas_clclasses = new ChwClas_clclasses();
        chwClas_cla_descr = new ChwClas_cla_descr();
    }

    @Override
    protected boolean isReadyToExecute()
    {
    	return checkFieldsNotEmplyOrNull(chwClas_clclasses, "cLASS")&&
    	checkFieldsNotEmplyOrNull(chwClas_cla_descr, "catchword")&&
    	checkFieldsNotEmplyOrNullInCollection(ChwClas, "charact");

    }

}
