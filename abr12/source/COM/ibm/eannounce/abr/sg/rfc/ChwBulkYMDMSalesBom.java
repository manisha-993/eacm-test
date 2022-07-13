package COM.ibm.eannounce.abr.sg.rfc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.google.gson.annotations.SerializedName;

import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMSalesBom_CUKB;
import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMSalesBom_MAST;
import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMSalesBom_STKO;
import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMSalesBom_STPO;
import COM.ibm.eannounce.abr.util.RFCConfig;

public class ChwBulkYMDMSalesBom extends RdhBase{

    @Foo
    private String TMF_ID;
    @SerializedName("TBL_MAST")
    private ArrayList<ChwBulkYMDMSalesBom_MAST> tbl_mast;
    @SerializedName("TBL_STKO")
    private ArrayList<ChwBulkYMDMSalesBom_STKO> tbl_stko;
    @SerializedName("TBL_STPO")
    private ArrayList<ChwBulkYMDMSalesBom_STPO> tbl_stpo;
    @SerializedName("TBL_CUKB")
    private ArrayList<ChwBulkYMDMSalesBom_CUKB> tbl_cukb;

    public ChwBulkYMDMSalesBom(String machType, String model, String featurecode) {
        super(machType + "MEB", "RDH_YMDMSALES_BOM".toLowerCase(), null);
        this.pims_identity="H";
        TMF_ID = machType + "-" + model + "-" +featurecode;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

        Set<String> plnts = RFCConfig.getBHPlnts();
        for(String plnt : plnts) {
            ChwBulkYMDMSalesBom_MAST mast = new ChwBulkYMDMSalesBom_MAST();
            mast.setMatnr(machType + "MEB");
            mast.setWerks(plnt);
            mast.setLosvn("0.000");
            mast.setLosbs("0.000");
            tbl_mast.add(mast);

            ChwBulkYMDMSalesBom_STKO stko = new ChwBulkYMDMSalesBom_STKO();
            stko.setMatnr(machType + "MEB");
            stko.setWerks(plnt);
            stko.setDatuv(sdf.format(new Date()));
            tbl_stko.add(stko);

            ChwBulkYMDMSalesBom_STPO stpo1 = new ChwBulkYMDMSalesBom_STPO();
            stpo1.setMatnr(machType + "MEB");
            stpo1.setWerks(plnt);
            stpo1.setIdnrk(machType + "F" + featurecode);
            tbl_stpo.add(stpo1);

            ChwBulkYMDMSalesBom_STPO stpo2 = new ChwBulkYMDMSalesBom_STPO();
            stpo2.setMatnr(machType + "MEB");
            stpo2.setWerks(plnt);
            stpo2.setIdnrk(machType + model);
            tbl_stpo.add(stpo2);

            ChwBulkYMDMSalesBom_CUKB cukb1 = new ChwBulkYMDMSalesBom_CUKB();
            cukb1.setMatnr(machType + "MEB");
            cukb1.setWerks(plnt);
            cukb1.setIdnrk(machType + "F" + featurecode);
            cukb1.setDep_intern("SC_" + machType + "_FC_" + featurecode + "_BLK");
            cukb1.setDep_type("5");
            tbl_cukb.add(cukb1);

            ChwBulkYMDMSalesBom_CUKB cukb2 = new ChwBulkYMDMSalesBom_CUKB();
            cukb2.setMatnr(machType + "MEB");
            cukb2.setWerks(plnt);
            cukb2.setIdnrk(machType + "F" + featurecode);
            cukb2.setDep_intern("PR_" + machType + "_FC_" + featurecode + "_BLK_QTY");
            cukb2.setDep_type("7");
            tbl_cukb.add(cukb2);

            ChwBulkYMDMSalesBom_CUKB cukb3 = new ChwBulkYMDMSalesBom_CUKB();
            cukb3.setMatnr(machType + "MEB");
            cukb3.setWerks(plnt);
            cukb3.setIdnrk(machType + model);
            cukb3.setDep_intern("SC_" + machType + "_MOD_" + model);
            cukb3.setDep_type("5");
            tbl_cukb.add(cukb3);
        }
    }

    @Override
    protected boolean isReadyToExecute() {
        if (this.getRfcrc() != 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void setDefaultValues()
    {
        tbl_mast = new ArrayList<ChwBulkYMDMSalesBom_MAST>();
        tbl_stko = new ArrayList<ChwBulkYMDMSalesBom_STKO>();
        tbl_stpo = new ArrayList<ChwBulkYMDMSalesBom_STPO>();
        tbl_cukb = new ArrayList<ChwBulkYMDMSalesBom_CUKB>();

    }
}
