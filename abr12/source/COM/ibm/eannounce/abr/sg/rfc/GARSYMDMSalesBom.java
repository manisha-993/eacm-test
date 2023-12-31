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

    @SerializedName("TBL_MAST")
    private List<GARSYMDMSalesBom_MAST> tbl_mast;
    @SerializedName("TBL_STKO")
    private List<GARSYMDMSalesBom_STKO> tbl_stko;
    @SerializedName("TBL_STPO")
    private List<GARSYMDMSalesBom_STPO> tbl_stpo;
    @SerializedName("TBL_CUKB")
    private List<GARSYMDMSalesBom_CUKB> tbl_cukb;
    @SerializedName("MACHINE_TYPE")
    private String machine_type;
    @SerializedName("MODEL")
    private String model;

    public GARSYMDMSalesBom(MODEL chwProduct, String plnt) {
               super(chwProduct.getMACHTYPE() + "BOMFEA" + plnt, "RDH_YMDMSALES_BOM".toLowerCase(),null);
               this.pims_identity = "H";
               this.machine_type = chwProduct.getMACHTYPE();
               this.model = chwProduct.getMODEL();
               SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

                GARSYMDMSalesBom_MAST mast = new GARSYMDMSalesBom_MAST();
                mast.setMatnr(machine_type + "FEA");
                mast.setWerks(plnt);
                mast.setLosvn("0.000");
                mast.setLosbs("0.000");
                tbl_mast.add(mast);

                GARSYMDMSalesBom_STKO stko = new GARSYMDMSalesBom_STKO();
                stko.setMatnr(machine_type + "FEA");
                stko.setWerks(plnt);
                stko.setDatuv(sdf.format(new Date()));
                tbl_stko.add(stko);

                GARSYMDMSalesBom_STPO stpo = new GARSYMDMSalesBom_STPO();
                stpo.setMatnr(machine_type + "FEA");
                stpo.setWerks(plnt);
                stpo.setIdnrk(machine_type + model);
                tbl_stpo.add(stpo);

                GARSYMDMSalesBom_CUKB cukb = new GARSYMDMSalesBom_CUKB();
                cukb.setMatnr(machine_type + "FEA");
                cukb.setWerks(plnt);
                cukb.setIdnrk(machine_type + model);
                cukb.setDep_intern("SC_" + machine_type + "_MOD_" + model);
                cukb.setDep_type("5");
                tbl_cukb.add(cukb);
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
