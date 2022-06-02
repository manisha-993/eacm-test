package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_csap_mbom;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_csap_mbom_Adapter;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomm_api03;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomm_csdata;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomm_csdep_dat;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomm_stko_api01;
import COM.ibm.eannounce.abr.util.DateUtility;

public class ChwBomMaintain extends RdhBase {
	
	@SerializedName("csap_mbom")
    private RdhBomc_csap_mbom_Adapter csap_mbom;

    @SerializedName("STPO_API03")
    private List<RdhBomm_api03> api03;

    @SerializedName("CSDEP_DAT")
    private List<RdhBomm_csdep_dat> csdep_dat;

    @SerializedName("STKO_API01")
    private List<RdhBomm_stko_api01> stko_api01;

    @SerializedName("CSDATA")
    private List<RdhBomm_csdata> csdata;
	
	public ChwBomMaintain (String bom_header_id, String mfg_plant, String component_id,
            String item_no, String dependency) {	
		super(bom_header_id, "Z_DM_SAP_BOM_MAINTAIN".toLowerCase(), null);
		this.pims_identity = "H";
		this.default_mandt = "10H";
		
        if (this.getRfcrc() != 0)
            return;
        System.out.println("111");
        System.out.println("csap_mbom=" + csap_mbom);
        csap_mbom.getMap().setMatnr(bom_header_id);
        csap_mbom.getMap().setWerks(mfg_plant);
        csap_mbom.getMap().setDatuv(DateUtility.getTodayStringWithSimpleFormat());

        RdhBomm_api03 api = new RdhBomm_api03();
        api.setComponent(component_id);
        api.setItem_no(item_no);
        api.setItem_categ("N");
        api.setComp_qty("1");
        api.setComp_unit("EA");
        api.setRel_sales("X");
        api.setIdentifier("A1");
        api03.add(api);

        RdhBomm_csdep_dat dat = new RdhBomm_csdep_dat();
        if (dependency != null)
        {
            dat.setDep_intern(dependency);
            dat.setObject_id("2");
            dat.setIdentifier("A1");
            dat.setStatus("1");
            csdep_dat.add(dat);
        }
        
        RdhBomm_stko_api01 rdhBomm_stko_api01 = new RdhBomm_stko_api01();       
        if(rdhBomm_stko_api01 != null) {
        	rdhBomm_stko_api01.setBom_status("01");
        	 stko_api01.add(rdhBomm_stko_api01);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.sdpi.cmd.interfaces.esw.rdh.RdhBase#setDefaultValues()
     */
    @Override
    protected void setDefaultValues()
    {
    
    	csap_mbom = new RdhBomc_csap_mbom_Adapter();
        csap_mbom.setStuctName("csap_mbom");
        RdhBomc_csap_mbom mbom = new RdhBomc_csap_mbom();
        csap_mbom.setMap(mbom);
        mbom.setStlan("5");
        
        api03 = new ArrayList<RdhBomm_api03>();
        csdep_dat = new ArrayList<RdhBomm_csdep_dat>();
        stko_api01 = new ArrayList<RdhBomm_stko_api01>();
        csdata = new ArrayList<RdhBomm_csdata>();

    }



    /**
     * The following attributes must have string values with length > 0:
     * RdhBomm_mbom.matnr RdhBomm_mbom.werks RdhBomm_api03.component If all of
     * the above attributes meet the validation requirements, then return TRUE.
     * If any of the above attributes fail the validation requirements: Set the
     * <RdhBase.rfcrc> attribute to '8'. Set the <RdhBase.error_text> attribute
     * to an error message. The error message text should identify the attribute
     * and the reason for the failure (ex.
     * "The zzfv_obj attribute is not set to a value"). Return FALSE.
     */
    @Override
    protected boolean isReadyToExecute()
    {
        if (this.getRfcrc() != 0)
            return false;
        RdhBomc_csap_mbom bom = this.csap_mbom.getMap();
        List<String> fields = new ArrayList<String>();
        fields.add("matnr");
        fields.add("werks");
        if(this.checkFieldsNotEmplyOrNull(bom, fields, true)){
            return this.checkFieldsNotEmplyOrNullInCollection(api03, "component");
        }
        
        return false;
    }
}
