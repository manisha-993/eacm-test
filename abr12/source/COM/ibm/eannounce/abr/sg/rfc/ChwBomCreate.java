/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_csap_mbom;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_csap_mbom_Adapter;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_stko_api01;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_stko_api01_Adapter;
import COM.ibm.eannounce.abr.util.DateUtility;


public class ChwBomCreate extends RdhBase {
	
	@SerializedName("csap_mbom")
    private RdhBomc_csap_mbom_Adapter  csap_mbom;
    @SerializedName("jIStko")
    private RdhBomc_stko_api01_Adapter jIStko;
	
	public ChwBomCreate (
			String matnr, String mfg_plant) {	
		super(matnr, "Z_DM_SAP_BOM_CREATE".toLowerCase(), null);
		this.pims_identity = "H";
		this.default_mandt = "10H";
		
		 csap_mbom.getMap().setMatnr(matnr);
	     csap_mbom.getMap().setWerks(mfg_plant);
	     csap_mbom.getMap().setDatuv(DateUtility.getTodayStringWithSimpleFormat()); //current data
		
	}
    public ChwBomCreate (
            String matnr, String bom_rfanum,String mfg_plant) {
        super(bom_rfanum, "Z_DM_SAP_BOM_CREATE".toLowerCase(), null);
        this.pims_identity = "H";
        this.default_mandt = "10H";

        csap_mbom.getMap().setMatnr(matnr);
        csap_mbom.getMap().setWerks(mfg_plant);
        csap_mbom.getMap().setDatuv(DateUtility.getTodayStringWithSimpleFormat()); //current data

    }

	@Override
	protected void setDefaultValues() {
		csap_mbom = new RdhBomc_csap_mbom_Adapter();
        csap_mbom.setStuctName("csap_mbom");
        RdhBomc_csap_mbom mbom = new RdhBomc_csap_mbom();
        csap_mbom.setMap(mbom);
        mbom.setStlan("5");
        mbom.setStlal("1");
        
        jIStko = new RdhBomc_stko_api01_Adapter();
        jIStko.setStuctName("jIStko");;
        RdhBomc_stko_api01 stko = new RdhBomc_stko_api01();
        jIStko.setMap(stko);
        stko.setBase_quan("1");
        stko.setBom_status("01");
		
	}

	@Override
	protected boolean isReadyToExecute() {
		RdhBomc_csap_mbom bom = csap_mbom.getMap();
        
        List<String> fields = new ArrayList<String>();
        fields.add("matnr");
        fields.add("werks");
        return this.checkFieldsNotEmplyOrNull(bom, fields, true);
	}
	
	@Override
    protected boolean isValidRfcrc()
    { 
        return this.getRfcrc() == 0 || this.getRfcrc() == 2;
    }
}
