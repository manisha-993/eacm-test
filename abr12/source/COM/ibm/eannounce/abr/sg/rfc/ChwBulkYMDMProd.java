package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.eannounce.abr.sg.rfc.entity.*;
import COM.ibm.eannounce.abr.util.RFCConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import static COM.ibm.eannounce.abr.sg.rfc.XMLParse.getObjectFromXml;

public class ChwBulkYMDMProd extends RdhBase {

    @SerializedName("TBL_MODEL")
    List<ChwBulkYMDMProd_MODEL> tbl_model;
    @SerializedName("TBL_MODEL_MAKT")
    List<ChwBulkYMDMProd_MAKT> tbl_model_makt;
    @SerializedName("TBL_MODEL_MLAN")
    List<ChwBulkYMDMProd_MLAN> tbl_mlan;
    @SerializedName("TBL_MODEL_MVKE")
    List<ChwBulkYMDMProd_MVKE> tbl_mvke;
    @SerializedName("TBL_TMF")
    List<ChwBulkYMDMProd_TMF> tbl_tmf;
    @SerializedName("TBL_FEATURE")
    List<ChwBulkYMDMProd_FEATURE> tbl_feature;
    @SerializedName("TBL_FEATURE_MAKT")
    List<ChwBulkYMDMProd_MAKT> tbl_feature_makt;

    @Foo
    MODEL chwProduct;
    @SerializedName("MATERIAL_TYPE")
    String materialType;
    @Foo
    String tmfEntityID;
    @Foo
    Connection odsConnection;
    @Foo
    Connection pdhConnection;

    public ChwBulkYMDMProd(MODEL model, String materialType, String tmfEntityID, Connection odsConnection, Connection pdhConnection) throws Exception {
        super(model.getMACHTYPE() + model.getMODEL(), "Z_YMDMBLK_PROD1".toLowerCase(), null);
        this.pims_identity = "H";
        this.chwProduct = model;
        this.materialType = materialType;
        this.tmfEntityID = tmfEntityID;
        this.odsConnection = odsConnection;
        this.pdhConnection = pdhConnection;

        //Add a row in tbl_model structure
        tbl_model.get(0).setModelEntitytype(chwProduct.getMODELENTITYTYPE());
        tbl_model.get(0).setModelEntityID(chwProduct.getMODELENTITYID());
        tbl_model.get(0).setMachType(chwProduct.getMACHTYPE());
        tbl_model.get(0).setModel(chwProduct.getMODEL());
        tbl_model.get(0).setCategory(chwProduct.getCATEGORY());
        tbl_model.get(0).setSubGroup(chwProduct.getSUBGROUP());
        tbl_model.get(0).setPrftctr(chwProduct.getPRFTCTR());
        tbl_model.get(0).setUnitClass(chwProduct.getUNITCLASS());
        tbl_model.get(0).setPricedInd(chwProduct.getPRICEDIND());
        tbl_model.get(0).setInstall(chwProduct.getINSTALL());
        tbl_model.get(0).setZeroPrice(chwProduct.getZEROPRICE());
        tbl_model.get(0).setUnspsc(chwProduct.getUNSPSC());
        tbl_model.get(0).setMeasuremetric(chwProduct.getMEASUREMETRIC());
        tbl_model.get(0).setProdHiercd(chwProduct.getPRODHIERCD());
        tbl_model.get(0).setAcctAsgnGrp(chwProduct.getACCTASGNGRP());
        tbl_model.get(0).setAmrtztnlngth(chwProduct.getAMRTZTNLNGTH());
        tbl_model.get(0).setZmrtztnstrt(chwProduct.getAMRTZTNSTRT());
        tbl_model.get(0).setProdID(chwProduct.getPRODID());
        tbl_model.get(0).setSomFamily(chwProduct.getSOMFAMILY());
        tbl_model.get(0).setLic(chwProduct.getLIC());
        tbl_model.get(0).setBpcertspecbid(chwProduct.getBPCERTSPECBID());
        tbl_model.get(0).setWwocCode(chwProduct.getWWOCCODE());
        tbl_model.get(0).setPrpqIndc("");
        tbl_model.get(0).setOrderCode(chwProduct.getORDERCODE());

        //Add a row to tbl_model_makt structure separately for nlsid "E", "1", "D"
        ChwBulkYMDMProd_MAKT makt = new ChwBulkYMDMProd_MAKT();
        makt.setEntitytype(chwProduct.getMODELENTITYTYPE());
        makt.setEntityID(chwProduct.getMODELENTITYID());
        makt.setNlsid("E");
        makt.setInvName("MACHINE TYPE " + chwProduct.getMACHTYPE() + " - MODEL MEB");
        makt.setMktgDesc("MACHINE TYPE " + chwProduct.getMACHTYPE() + " - MODEL MEB");
        makt.setMktgName("MACHINE TYPE " + chwProduct.getMACHTYPE() + " - MODEL MEB");
        tbl_model_makt.add(makt); 
        
       /* ChwBulkYMDMProd_MAKT makt1 = new ChwBulkYMDMProd_MAKT();
        makt1.setEntitytype(chwProduct.getMODELENTITYTYPE());
        makt1.setEntityID(chwProduct.getMODELENTITYID());
        makt1.setNlsid("E");
        makt1.setInvName("MACHINE TYPE "+chwProduct.getMODELENTITYTYPE()+" - MODEL MEB");
        makt1.setMktgDesc("MACHINE TYPE "+chwProduct.getMODELENTITYTYPE()+" - MODEL MEB");
        makt1.setMktgName("MACHINE TYPE "+chwProduct.getMODELENTITYTYPE()+" - MODEL MEB");
        tbl_model_makt.add(makt1);
        
        ChwBulkYMDMProd_MAKT makt2 = new ChwBulkYMDMProd_MAKT();
        makt2.setEntitytype(chwProduct.getMODELENTITYTYPE());
        makt2.setEntityID(chwProduct.getMODELENTITYID());
        makt2.setNlsid("E");
        makt2.setInvName("MACHINE TYPE "+chwProduct.getMODELENTITYTYPE()+" - MODEL MEB");
        makt2.setMktgDesc("MACHINE TYPE "+chwProduct.getMODELENTITYTYPE()+" - MODEL MEB");
        makt2.setMktgName("MACHINE TYPE "+chwProduct.getMODELENTITYTYPE()+" - MODEL MEB");
        tbl_model_makt.add(makt2);*/

        //query country_plant_tax where INTERFACE_ID = "7"
        //For each unique value of TAX_COUNTRY  in the return result sets, create one row into tbl_mlan structure
        List countryKey = new ArrayList();
        List taxKey = new ArrayList();
        List<CountryPlantTax> taxList = RFCConfig.getTaxs();
        for (CountryPlantTax tax : taxList) {
            if ("7".equals(tax.getINTERFACE_ID()) && !countryKey.contains(tax.getTAX_COUNTRY())) {
                ChwBulkYMDMProd_MLAN mlan = new ChwBulkYMDMProd_MLAN();
                mlan.setModelEntitytype(chwProduct.getMODELENTITYTYPE());
                mlan.setModelEntityid(chwProduct.getMODELENTITYID());
                mlan.setTaxcategoryAction("Update");
                mlan.setCountryAction("Update");
                mlan.setCountry_fc(tax.getTAX_COUNTRY());
                mlan.setTaxcategoryValue(tax.getTAX_CAT());
                mlan.setTaxClassification("1");
                countryKey.add(tax.getTAX_COUNTRY());
                tbl_mlan.add(mlan);
            }
        }
        //For each unique combination of column SALES_ORG and DEL_PLNT in the return result sets, create one row into tbl_mvke structure
        for (CountryPlantTax tax : taxList) {
            if ("7".equals(tax.getINTERFACE_ID()) && !taxKey.contains(tax.getTAX_COUNTRY() + tax.getSALES_ORG() + tax.getPLNT_CD())) {
                ChwBulkYMDMProd_MVKE mvke = new ChwBulkYMDMProd_MVKE();
                mvke.setModelEntitytype(chwProduct.getMODELENTITYTYPE());
                mvke.setModelEntityid(chwProduct.getMODELENTITYID());
                mvke.setCountry_fc(tax.getTAX_COUNTRY());
                mvke.setSleorg(tax.getSALES_ORG());
                mvke.setPlntCd(tax.getPLNT_CD());
                mvke.setPlntDel(tax.getDEL_PLNT());
                taxKey.add(tax.getTAX_COUNTRY() + tax.getSALES_ORG() + tax.getPLNT_CD());
                tbl_mvke.add(mvke);
            }
        }

        //Construct tbl_tmf structure
        if ("MODEL".equals(materialType)) {
            //search for all of PRODSTRUCTs related to the MODEL where BULKMESINDC = "MES0001" (Yes) in PDH database
            List<String> id_list = getTMFid(chwProduct.getMODELENTITYID());
            //retrieve the XML of PRODSTRUCT from cache table
            List<String> xmls = getXML(id_list);
            for (String xml : xmls) {
                TMF_UPDATE tmf = getObjectFromXml(xml, TMF_UPDATE.class);
                //Create one row into tbl_tmf structure as below
                ChwBulkYMDMProd_TMF t_tmf = new ChwBulkYMDMProd_TMF();
                t_tmf.setEntityType(tmf.getENTITYTYPE());
                t_tmf.setEntityID(tmf.getENTITYID());
                t_tmf.setModelEntityType(tmf.getMODELENTITYTYPE());
                t_tmf.setModelEntityID(tmf.getMODELENTITYID());
                t_tmf.setFeatureEntityType(tmf.getFEATUREENTITYTYPE());
                t_tmf.setFeatureEntityID(tmf.getFEATUREENTITYID());
                t_tmf.setMachType(tmf.getMACHTYPE());
                t_tmf.setModel(tmf.getMODEL());
                t_tmf.setFeatureCode(tmf.getFEATURECODE());
                t_tmf.setPubFromAnnDate(tmf.getAVAILABILITYLIST().get(0).getANNDATE());
                t_tmf.setSystemMax(tmf.getSYSTEMMAX());
                t_tmf.setFcType(tmf.getFCTYPE());
                t_tmf.setBlkMesIndc(tmf.getBULKMESINDC());
                tbl_tmf.add(t_tmf);
                //Create one row into tbl_feature structure as below.
                ChwBulkYMDMProd_FEATURE feature = new ChwBulkYMDMProd_FEATURE();
                feature.setEntityType("FEATURE");
                feature.setEntityID(tmf.getFEATUREENTITYID());
                feature.setFeatureCode(tmf.getFEATURECODE());
                tbl_feature.add(feature);
                //Create one row into tbl_feature_makt structure
                Map<String, String> featureAtt = getFeatureAtt(tmf.getFEATUREENTITYID());
                ChwBulkYMDMProd_MAKT f_makt = new ChwBulkYMDMProd_MAKT();
                f_makt.setEntitytype("FEATURE");
                f_makt.setEntityID(tmf.getFEATUREENTITYID());
                f_makt.setNlsid("E");
                String string = featureAtt.get("BHINVNAME");
                string = string.length() > 30 ? string.substring(0, 30) : string;
                f_makt.setMktgDesc(string);
                f_makt.setMktgName(featureAtt.get("MKTGNAME"));
                f_makt.setInvName(featureAtt.get("INVNAME"));
                f_makt.setBhInvName(featureAtt.get("BHINVNAME"));
                tbl_feature_makt.add(f_makt);
            }
        } else if ("PRODSTRUCT".equals(materialType)) {
            //use <tmfEntityID> to retrieve the XML of PRODSTRUCT from cache table       	
            List<String> xmls = getXML(Arrays.asList(tmfEntityID));
            TMF_UPDATE tmf = getObjectFromXml(xmls.get(0), TMF_UPDATE.class);
            //Create one row into tbl_tmf structure as below
            ChwBulkYMDMProd_TMF t_tmf = new ChwBulkYMDMProd_TMF();
            t_tmf.setEntityType(tmf.getENTITYTYPE());
            t_tmf.setEntityID(tmf.getENTITYID());
            t_tmf.setModelEntityType(tmf.getMODELENTITYTYPE());
            t_tmf.setModelEntityID(tmf.getMODELENTITYID());
            t_tmf.setFeatureEntityType(tmf.getFEATUREENTITYTYPE());
            t_tmf.setFeatureEntityID(tmf.getFEATUREENTITYID());
            t_tmf.setMachType(tmf.getMACHTYPE());
            t_tmf.setModel(tmf.getMODEL());
            t_tmf.setFeatureCode(tmf.getFEATURECODE());
            t_tmf.setPubFromAnnDate(tmf.getAVAILABILITYLIST().get(0).getANNDATE());
            t_tmf.setSystemMax(tmf.getSYSTEMMAX());
            t_tmf.setFcType(tmf.getFCTYPE());
            t_tmf.setBlkMesIndc(tmf.getBULKMESINDC());
            tbl_tmf.add(t_tmf);
            //Create one row into tbl_feature structure as below.
            ChwBulkYMDMProd_FEATURE feature = new ChwBulkYMDMProd_FEATURE();
            feature.setEntityType("FEATURE");
            feature.setEntityID(tmf.getFEATUREENTITYID());
            feature.setFeatureCode(tmf.getFEATURECODE());
            tbl_feature.add(feature);
            //Create one row into tbl_feature_makt structure
            Map<String, String> featureAtt = getFeatureAtt(tmf.getFEATUREENTITYID());
            ChwBulkYMDMProd_MAKT f_makt = new ChwBulkYMDMProd_MAKT();
            String value = featureAtt.get("BHINVNAME");
            value = value.length() > 30 ? value.substring(0, 30) : value;

            f_makt.setEntitytype("FEATURE");
            f_makt.setEntityID(tmf.getFEATUREENTITYID());
            f_makt.setNlsid("E");
            f_makt.setMktgDesc(value);
            f_makt.setMktgName(featureAtt.get("MKTGNAME"));
            f_makt.setInvName(featureAtt.get("INVNAME"));
            f_makt.setBhInvName(featureAtt.get("BHINVNAME"));
            tbl_feature_makt.add(f_makt);
            this.rfa_num = this.rfa_num + tmf.getFEATURECODE() + "MEB";
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
    protected void setDefaultValues() {
        tbl_model = new ArrayList<ChwBulkYMDMProd_MODEL>();
        ChwBulkYMDMProd_MODEL model = new ChwBulkYMDMProd_MODEL();
        tbl_model.add(model);

        tbl_model_makt = new ArrayList<ChwBulkYMDMProd_MAKT>();
        tbl_mlan = new ArrayList<ChwBulkYMDMProd_MLAN>();
        tbl_mvke = new ArrayList<ChwBulkYMDMProd_MVKE>();
        tbl_tmf = new ArrayList<ChwBulkYMDMProd_TMF>();
        tbl_feature = new ArrayList<ChwBulkYMDMProd_FEATURE>();
        tbl_feature_makt = new ArrayList<ChwBulkYMDMProd_MAKT>();

    }


    public List getTMFid(String modelid) throws SQLException {
        String sql = "select distinct r.entityid as TMFID from opicm.relator r\n" +
                "join opicm.flag f on f.entitytype=r.entitytype and f.entityid=r.entityid and f.attributecode='BULKMESINDC' and f.attributevalue='MES0001'\n" +
                "where r.entitytype='PRODSTRUCT' and r.entity2type='MODEL' and r.entity2id = ?\n" +
                "and r.valto>current timestamp and r.effto>current timestamp and f.valto>current timestamp and f.effto>current timestamp with ur";
        List<String> ids = new ArrayList();
        PreparedStatement statement = null;
        statement = pdhConnection.prepareStatement(sql);
        statement.setString(1, modelid);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ids.add(resultSet.getInt("TMFID") + "");
        }

        return ids;
    }

    public List<String> getXML(List ids) throws SQLException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i == 0) sb.append("?");
            else sb.append(",?");
        }
        String sql = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'PRODSTRUCT' and XMLENTITYID in (" + sb.toString() + ")  and XMLCACHEVALIDTO > current timestamp fetch first 1 rows only with ur";
        List<String> xmls = new ArrayList();
        PreparedStatement statement = null;
        statement = odsConnection.prepareStatement(sql);
        for (int j = 0; j < ids.size(); j++)
            statement.setString(j + 1, (String) ids.get(j));
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            xmls.add(resultSet.getString("XMLMESSAGE"));
            break;
        }

        return xmls;
    }

    public Map getFeatureAtt(String id) throws SQLException {

        String sql = "select t1.attributevalue as MKTGNAME,t2.attributevalue as INVNAME,t3.attributevalue as BHINVNAME " +
                "from opicm.text t1 " +
                "left join opicm.text t2 on t2.entityid= t1.entityid and t2.entitytype= t1.entitytype and t2.attributecode='INVNAME' " +
                "left join opicm.text t3 on t3.entityid= t2.entityid and t3.entitytype= t2.entitytype and t3.attributecode='BHINVNAME' " +
                "where t1.entityid= ? and t1.entitytype='FEATURE' and t1.attributecode='MKTGNAME' and t1.nlsid=1 and t2.nlsid=1 and t3.nlsid=1 " +
                "and t1.valto>current timestamp and t1.effto>current timestamp and t2.valto>current timestamp and t2.effto>current timestamp and t3.valto>current timestamp and t3.effto>current timestamp with ur";
        Map result = new HashMap();

        PreparedStatement statement = null;
        statement = pdhConnection.prepareStatement(sql);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            result.put("MKTGNAME", resultSet.getString("MKTGNAME"));
            result.put("INVNAME", resultSet.getString("INVNAME"));
            result.put("BHINVNAME", resultSet.getString("BHINVNAME"));
        }

        return result;
    }
}
