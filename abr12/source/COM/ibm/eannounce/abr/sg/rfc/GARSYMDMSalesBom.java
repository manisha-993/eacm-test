package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.eannounce.abr.sg.rfc.entity.*;
import COM.ibm.eannounce.abr.util.RFCConfig;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class GARSYMDMSalesBom extends RdhBase
{
    /**
     * Creates or updates a GARS material master.
     * @author yuzgyg
     */
    @SerializedName("Z_GEO")
    private String z_geo;
    @SerializedName("TBL_MAST_STRUCTURE")
    private List<GARSYMDMSalesBom_MAST> tbl_mast;
    @SerializedName("TBL_STKO_STRUCTURE")
    private List<GARSYMDMSalesBom_STKO> tbl_stko;
    @SerializedName("TBL_STPO_STRUCTURE")
    private List<GARSYMDMSalesBom_STPO> tbl_stpo;
    @SerializedName("TBL_CUKB_STRUCTURE")
    private List<GARSYMDMSalesBom_CUKB> tbl_cukb;

    @SerializedName("TMF_ID")
    private String tmf_id;
    @SerializedName("MACHINE_TYPE")
    private String machine_type;
    @SerializedName("MODEL")
    private String model;

    public GARSYMDMSalesBom(String machType, String model) {
        super(machType + model + "BOM", "RDH_YMDMSALES_BOM".toLowerCase(),null);
        this.pims_identity = "H";
        this.z_geo = "WW";
        this.tmf_id = machType + "-" + model;
        this.machine_type = machType;
        this.model = model;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        Set<String> plnts = RFCConfig.getBHPlnts();
        for(String plnt : plnts) {
                GARSYMDMSalesBom_MAST mast = new GARSYMDMSalesBom_MAST();
                mast.setMatnr(machType + "FEA");
                mast.setWerks(plnt);
                mast.setLosvn("0.000");
                mast.setLosbs("0.000");
                tbl_mast.add(mast);

                GARSYMDMSalesBom_STKO stko = new GARSYMDMSalesBom_STKO();
                stko.setMatnr(machType + "FEA");
                stko.setWerks(plnt);
                stko.setDatuv(sdf.format(new Date()));
                tbl_stko.add(stko);

                GARSYMDMSalesBom_STPO stpo = new GARSYMDMSalesBom_STPO();
                stpo.setMatnr(machType + "FEA");
                stpo.setWerks(plnt);
                stpo.setIdnrk(machType + model);
                tbl_stpo.add(stpo);

                GARSYMDMSalesBom_CUKB cukb = new GARSYMDMSalesBom_CUKB();
                cukb.setMatnr(machType + "MEB");
                cukb.setWerks(plnt);
                cukb.setIdnrk(machType + model);
                cukb.setDep_intern("SC_" + machType + "_MOD_" + model);
                cukb.setDep_type("5");
                tbl_cukb.add(cukb);
        }
    }
    protected void setDefaultValues() {
        this.tbl_mast = new ArrayList<GARSYMDMSalesBom_MAST>();
        this.tbl_stko = new ArrayList<GARSYMDMSalesBom_STKO>();
        this.tbl_stpo = new ArrayList<GARSYMDMSalesBom_STPO>();
        this.tbl_cukb = new ArrayList<GARSYMDMSalesBom_CUKB>();
    }
    @Override
    protected boolean isReadyToExecute() {
        return true;
    }

}
