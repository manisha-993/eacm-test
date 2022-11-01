package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.eannounce.abr.sg.rfc.entity.*;
import COM.ibm.eannounce.abr.util.RFCConfig;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RdhYMDMGars extends RdhBase
{
    /**
     * Creates or updates a GARS material master.
     * @author yuzgyg
     */
    @SerializedName("Z_GEO")
    private String z_geo;
    @SerializedName("TBL_GARS_MAT")
    private List<RdhYMDMGars_MAT> tbl_gars_mat;
    @SerializedName("TBL_PRODUCTS")
    private List<RdhYMDMGars_PRODUCTS> tbl_products;
    @Foo
    SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
    @Foo
    SimpleDateFormat sdfANNDATE =   new SimpleDateFormat( "MMddyy" );
    @Foo
    SimpleDateFormat sdfPUBFROMDATE =   new SimpleDateFormat( "MMddyyyy" );
    @Foo
    String annnumber = null;
    public RdhYMDMGars(MODEL chwProduct) {
        super(chwProduct.getMACHTYPE()+chwProduct.getMODEL()+"FEA", "RDH_YMDM_GARS".toLowerCase(), null);
        this.pims_identity = "H";
        this.z_geo = "WW";
        List<CountryPlantTax> taxList = RFCConfig.getTaxs();
        for(CountryPlantTax tax : taxList){
            if("19".equals(tax.getINTERFACE_ID())){
                RdhYMDMGars_MAT mat = new RdhYMDMGars_MAT();
                //Add a row to tbl_gars_mat structure
                mat.setMatnr(chwProduct.getMACHTYPE()+"FEA");
                mat.setBrgew("00");
                mat.setPrdha(chwProduct.getPRODHIERCD());
                mat.setNumtp("");
                mat.setEan11("");
                mat.setZconf("E");
                mat.setAeszn(getEarliestAnnDate(chwProduct));
                if(!annnumber.equals("")){
                   mat.setZeiar("RFA");
                } else {
                    mat.setZeiar("");
                }
                mat.setZeinr(annnumber);
                mat.setPrctr(chwProduct.getPRFTCTR());
                mat.setWerks(tax.getPLNT_CD());
                mat.setKtgrm("06");
                mat.setVkorg(tax.getSALES_ORG());
                mat.setDwerk(tax.getDEL_PLNT());
                if(!tax.getTAX_CD().trim().contains("NULL")){
                    mat.setMvgr5(tax.getTAX_CD().trim());
                }else{
                    mat.setMvgr5("");
                }
                mat.setProdh(chwProduct.getPRODHIERCD());
                mat.setVmstd(getEarliestPUBFROM(chwProduct));
                mat.setSptxt("");
                mat.setSpras("E");
                mat.setMaktx("MACHINE TYPE " + chwProduct.getMACHTYPE() + "- MODEL FEA");
                mat.setAland(tax.getTAX_COUNTRY());
                mat.setTaxm1(tax.getTAX_CLAS());
                mat.setTaty1(tax.getTAX_CAT());
                mat.setMm_mach_type(chwProduct.getMACHTYPE());
                if(chwProduct.getUNITCLASS().equals("SIU-CPU")||chwProduct.getUNITCLASS().equals("SIU-Non CPU")){
                    mat.setMm_siu("2");
                }else if(chwProduct.getUNITCLASS().equals("Non SIU- CPU")){
                    mat.setMm_siu("0");
                }else {
                    mat.setMm_siu("");
                }
                if(chwProduct.getINSTALL().equals("CIF")){
                    mat.setMm_fg_installable("CSU");
                }else if(chwProduct.getINSTALL().equals("CE")){
                    mat.setMm_fg_installable("IBI");
                }else {
                    mat.setMm_fg_installable("");
                }
                mat.setMm_identity("");
                mat.setMm_lic(chwProduct.getLIC());
                mat.setMm_bp_cert_specbid(chwProduct.getBPCERTSPECBID());
                mat.setMm_rpqtype("");
                mat.setMm_announcement_type("");

                tbl_gars_mat.add(mat);
            }
        }
        List CountryList = new ArrayList();
        for(CountryPlantTax tax : taxList) {
            if ("19".equals(tax.getINTERFACE_ID())) {
                RdhYMDMGars_PRODUCTS products = new RdhYMDMGars_PRODUCTS();
                products.setPartnum(chwProduct.getMACHTYPE()+"FEA");
                if(CountryList.contains(tax.getTAX_COUNTRY())){
                    continue;
                }
                products.setLand1(tax.getTAX_COUNTRY());
                CountryList.add(tax.getTAX_COUNTRY());
                tbl_products.add(products);
            }
        }

    }
    protected void setDefaultValues() {
        this.tbl_gars_mat = new ArrayList<RdhYMDMGars_MAT>();
        this.tbl_products = new ArrayList<RdhYMDMGars_PRODUCTS>();
    }
    @Override
    protected boolean isReadyToExecute() {
        return true;
    }
    public String getEarliestAnnDate(MODEL model) {
        Date annDate = null;
        Date annTemp = null;
        List<AVAILABILITY> list = model.getAVAILABILITYLIST();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    if (annDate == null) {
                        annnumber= list.get(i).getANNNUMBER();
                        annDate= sdf.parse(list.get(i).getANNDATE());

                    } else {
                        annTemp = sdf.parse(list.get(i).getANNDATE());;

                        if (annTemp.before(annDate)) {
                            annDate = annTemp;
                            annnumber= list.get(i).getANNNUMBER();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(annDate==null)
            return null;
        return sdfANNDATE.format(annDate);
    }
    private String getEarliestPUBFROM(MODEL model) {
        Date pubDate = null;
        Date pubTemp = null;
        String result = "";
        List<AVAILABILITY> list = model.getAVAILABILITYLIST();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    if (pubDate == null) {
                        pubDate = sdf.parse(list.get(i).getPUBFROM());

                    } else {
                        pubTemp = sdf.parse(list.get(i).getPUBFROM());
                        ;

                        if (pubTemp.before(pubDate)) {
                            pubDate = pubTemp;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (pubDate == null)
            return null;
        return sdfPUBFROMDATE.format(pubDate);
    }
}
