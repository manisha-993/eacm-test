/*
 * Created on Mar 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * Revision 1.1  2008/01/30 2:01:31  guobin
 * add attribute code CONFIGURATORFLAG which come from FEATURE.CONFIGURATORFLAG, insert it into gbli.feature
 *
 */

package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * Feature
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Feature
    extends CatItem {
    /**
     * FIELD NAME
     */
    public static final int FEATUREDETAIL_REFERENCE = 0;

    private String FEATURECODE = null;
    private String FCMKTGDESC = null;
    private String INVNAME = null;
    private String FCTYPE = null;
    private String FCTYPE_FC = null;
    private String ANNDATE = null;
    private String WITHDRAWDATE = null;

    private String PRICEDFEATURE = null;
    private String PRICEDFEATURE_FC = null;
    private String TANDC = null;

    private String CATEGORY = null;
    private String CATEGORY_FC = null;
    private String SUBCATEGORY = null;
    private String SUBCATEGORY_FC = null;
    private String GROUP = null;
    private String GROUP_FC = null;

    private String STATUS = null;
    private String STATUS_FC = null;

    private String CGTYPE = null;
    private String CGTYPE_FC = null;

    private String OSLEVEL = null;
    private String OSLEVEL_FC = null;

    //for CR072607687
    //added by guobin 2008-04-07
    private String CONFIGURATORFLAG = null;
    private String CONFIGURATORFLAG_FC = null;
    //end added by guobin 2008-04-07

    private String VALFROM = null;
    private String VALTO = null;

    private SyncMapCollection m_smc = null;
    private FeatureDetailCollection m_fdc = null;

    private static final String SWFEATURE_ENTITY_TYPE = "SWFEATURE";
    private static final String HWFEATURE_ENTITY_TYPE = "FEATURE";

    private static final String FEATURE_FCTYPE = "FCTYPE";
    private static final String FEATURE_OSLEVEL = "OSLEVEL";
    private static final String FEATURE_FEATURECODE = "FEATURECODE";
    private static final String FEATURE_FIRSTANNDATE = "FIRSTANNDATE";
    private static final String FEATURE_HWFCCAT = "HWFCCAT";
    private static final String FEATURE_HWFCSUBCAT = "HWFCSUBCAT";
    private static final String FEATURE_HWFCGRP = "HWFCGRP";
    //private static final String FEATURE_FCMKTGDESC = "FCMKTGDESC";  // Reverting back according to Nancy/Dave - Bala 5/5/06
    private static final String FEATURE_FCMKTGDESC = "FCMKTGSHRTDESC"; // GAB - changed 050206 for TIR 6PEGU4. Ignoring CR6315 due to lack of follow up.
    private static final String FEATURE_INVNAME = "INVNAME"; // Lenovo CQ - 2/18/2015
    private static final String FEATURE_PRICEDFEATURE = "PRICEDFEATURE";
    private static final String FEATURE_STATUS = "STATUS";
    private static final String FEATURE_WITHDRAWANNDATE_T = "WITHDRAWANNDATE_T";
    private static final String FEATURE_CGTYPE = "CGTYPE";

    //for CR072607687
    //added by guobin 2008-04-07
    private static final String FEATURE_CONFIGURATORFLAG = "CONFIGURATORFLAG";
    //end added by guobin 2008-04-07
    private static final String SWFEATURE_SWFCCAT = "SWFCCAT";
    private static final String SWFEATURE_SWFCSUBCAT = "SWFCSUBCAT";
    private static final String SWFEATURE_SWFCGRP = "SWFCGRP";
    private static final String SWFEATURE_SWFEATDESC = "SWFEATDESC";
    private static final String SWFEATURE_CHARGEOPTION = "CHARGEOPTION";

    private HashMap m_AttCollection = new HashMap();

    /**
     * Feature
     *
     * @param _fid
     */
    public Feature(FeatureId _fid) {
        super(_fid);
    }

    /**
     * Feature
     *
     * @param _fid
     * @param _cat
     */
    public Feature(FeatureId _fid, Catalog _cat) {
        super(_fid);
        get(_cat);
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer();
        sb.append("Feature: " + getId().toString() + NEW_LINE);
        if (!_brief) {
            sb.append("FEATURECODE: " + getFEATURECODE() + NEW_LINE);
            sb.append("FCMKTGDESC: " + getFCMKTGDESC() + NEW_LINE);
            sb.append("INVNAME: " + getINVNAME() + NEW_LINE);
            sb.append("FCTYPE: " + getFCTYPE() + NEW_LINE);
            sb.append("FCTYPE_FC: " + getFCTYPE_FC() + NEW_LINE);
            sb.append("ANNDATE: " + getANNDATE() + NEW_LINE);
            sb.append("WITHDRAWDATE: " + getWITHDRAWDATE() + NEW_LINE);

            sb.append("PRICEDFEATURE: " + getPRICEDFEATURE() + NEW_LINE);
            sb.append("PRICEDFEATURE_FC: " + getPRICEDFEATURE_FC() + NEW_LINE);

            sb.append("TANDC: " + getTANDC() + NEW_LINE);

            sb.append("CATEGORY: " + getCATEGORY() + NEW_LINE);
            sb.append("CATEGORY_FC: " + getCATEGORY_FC() + NEW_LINE);
            sb.append("SUBCATEGORY: " + getSUBCATEGORY() + NEW_LINE);
            sb.append("SUBCATEGORY_FC: " + getSUBCATEGORY_FC() + NEW_LINE);

            sb.append("STATUS: " + getSTATUS() + NEW_LINE);
            sb.append("STATUS_FC: " + getSTATUS_FC() + NEW_LINE);

            sb.append("CGTYPE: " + getCGTYPE() + NEW_LINE);
            sb.append("CGTYPE_FC: " + getCGTYPE_FC() + NEW_LINE);

            sb.append("OSLEVEL: " + getOSLEVEL() + NEW_LINE);
            sb.append("OSLEVEL_FC: " + getOSLEVEL_FC() + NEW_LINE);

            sb.append("VALFROM: " + getVALFROM() + NEW_LINE);
            sb.append("VALTO: " + getVALTO() + NEW_LINE);
            sb.append("ISACTIVE: " + isActive() + NEW_LINE);
        }

        sb.append(m_fdc != null ? m_fdc.dump(_brief) : "");
        return sb.toString();

    }

    /**
     * getFeatEntityID
     *
     * @return
     */
    public int getFeatEntityID() {
        return getFeatureId().getFeatEntityID();
    }

    /**
     * getFeatEntityType
     *
     * @return
     */
    public String getFeatEntityType() {
        return getFeatureId().getFeatEntityType();
    }

    /**
     * getGami
     *
     * @return
     */
    public GeneralAreaMapItem getGami() {
        return getFeatureId().getGami();
    }

    /**
     *  (non-Javadoc)
     *
     * @param _cat
     */
    public void get(Catalog _cat) {

        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        Profile prof = _cat.getCatalogProfile();
        FeatureId fid = getFeatureId();
        GeneralAreaMapItem gami = fid.getGami();
        FeatureCollectionId fcid = fid.getFeatureCollectionId();
        String strEnterprise = gami.getEnterprise();

        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();
        CatalogInterval cati = fcid.getInterval();

        D.ebug(this, D.EBUG_DETAIL,
            "get() - here is fcid..." + ":isByInteval:" + fcid.isByInterval() + ":isFromPDH: " + fcid.isFromPDH() +
            ":isFullImages:" + fcid.isFullImages());

        try {
            if (fcid.isFromCAT()) {
                ReturnStatus returnStatus = new ReturnStatus( -1);
                ReturnDataResultSet rdrs;

                try {
                    rs = db.callGBL9300(returnStatus, strEnterprise, strCountryList, getFeatEntityType(), getFeatEntityID());
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
                    rs.close();

                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }

                for (int ii = 0; ii < rdrs.size(); ii++) {
                    int y = 0;
                    setFEATURECODE(rdrs.getColumn(ii, y++));
                    setFCMKTGDESC(rdrs.getColumn(ii, y++));
                    setFCTYPE(rdrs.getColumn(ii, y++).trim());
                    setFCTYPE_FC(rdrs.getColumn(ii, y++));
                    setANNDATE(rdrs.getColumn(ii, y++));
                    setWITHDRAWDATE(rdrs.getColumn(ii, y++));
                    setPRICEDFEATURE(rdrs.getColumn(ii, y++));
                    setPRICEDFEATURE_FC(rdrs.getColumn(ii, y++));
                    setTANDC(rdrs.getColumn(ii, y++));
                    setCATEGORY(rdrs.getColumn(ii, y++));
                    setCATEGORY_FC(rdrs.getColumn(ii, y++));
                    setSUBCATEGORY(rdrs.getColumn(ii, y++));
                    setSUBCATEGORY_FC(rdrs.getColumn(ii, y++));
                    setGROUP(rdrs.getColumn(ii, y++));
                    setGROUP_FC(rdrs.getColumn(ii, y++));
                    setSTATUS(rdrs.getColumn(ii, y++));
                    setSTATUS_FC(rdrs.getColumn(ii, y++));
                    setCGTYPE(rdrs.getColumn(ii, y++));
                    setCGTYPE_FC(rdrs.getColumn(ii, y++));
                    setOSLEVEL(rdrs.getColumn(ii, y++));
                    setOSLEVEL_FC(rdrs.getColumn(ii, y++));
                    setVALFROM(rdrs.getColumn(ii, y++));
                    setVALTO(rdrs.getColumn(ii, y++));
                    setActive(rdrs.getColumnInt(ii, y++) == 1);
                    setINVNAME(rdrs.getColumn(ii, y++));
                    }
            } else if (fcid.isFromPDH() && fcid.isByInterval() && fcid.isFullImages()) {

                if (this.getSmc() == null) {
                    System.out.println("Cannot pull out of the PDH since there is no SycnMap for me.");
                    return;
                }
                prof.setEffOn(cati.getEndDate());
                prof.setValOn(cati.getEndDate());

                EntityItem eiFeat = Catalog.getEntityItem(_cat, getFeatEntityType(), getFeatEntityID());
                this.setAttributes(eiFeat);
                //
                // ok.. get it out of the list...
                //
                eiFeat.getEntityGroup().resetEntityItem();
            }
        }
        catch (SQLException _ex) {
            _ex.printStackTrace();
        }
        catch (MiddlewareException e) {
            e.printStackTrace();
        }
        finally {
            //TODO
        }

    }

    /**
     *  (non-Javadoc)
     *
     * @param _cat
     * @param _icase
     */
    public void getReferences(Catalog _cat, int _icase) {
        // Here is where we get FeatureDetail

        FeatureCollectionId fcid = this.getFeatureId().getFeatureCollectionId();

        switch (_icase) {

            case FEATUREDETAIL_REFERENCE:

                FeatureDetailCollectionId fdcid = new FeatureDetailCollectionId(this.getFeatureId(), fcid.getSource(), fcid.getType(),
                    fcid.getInterval());
                setFeatureDetailCollection(new FeatureDetailCollection(fdcid));

                //
                // Lets share the SMC stuff
                //
                if (this.hasSyncMapCollection()) {
                    D.ebug(this, D.EBUG_DETAIL, "getReferences(FEATUREDETAIL_REFERENCE) - setting feature detail's SMC");
                    getFeatureDetailCollection().setSmc(this.getSmc());
                }

                D.ebug(this, D.EBUG_DETAIL, "getReferences(FEATUREDETAIL_REFERENCE) - lets go stub out the feature detail" + fdcid);
                getFeatureDetailCollection().get(_cat);

                setDeep(true);

                break;

            default:

                break;
        }
    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return getId().toString();
    }

    /**
     * setFEATURECODE
     *
     * @param _s
     */
    protected void setFEATURECODE(String _s) {
        FEATURECODE = _s;
        m_AttCollection.put(FEATURE_FEATURECODE, _s);
    }

    /**
     * setFCMKTGNAME
     *
     * !!!NOTE: Per CR6315 this is changing to FCTECHNAME when we get word.
     *
     * @param _s
     */
    protected void setFCMKTGDESC(String _s) {
        FCMKTGDESC = _s;
        m_AttCollection.put(FEATURE_FCMKTGDESC, _s);
    }

    /**
     * setFCTYPE
     *
     * @param _s
     */
    protected void setFCTYPE(String _s) {
        FCTYPE = _s;
        m_AttCollection.put(FEATURE_FCTYPE, _s);
    }

    /**
     * setFCTYPE_FC
     *
     * @param _s
     */
    protected void setFCTYPE_FC(String _s) {
        FCTYPE_FC = _s;
    }

    /**
     * setANNDATE
     *
     * @param _s
     */
    protected void setANNDATE(String _s) {
        ANNDATE = _s;
        m_AttCollection.put(FEATURE_FIRSTANNDATE, _s);
    }

    /**
     * setWITHDRAWDATE
     *
     * @param _s
     */
    protected void setWITHDRAWDATE(String _s) {
        WITHDRAWDATE = _s;
        m_AttCollection.put(FEATURE_WITHDRAWANNDATE_T, _s);
    }

    /**
     * setPRICEDFEATURE
     *
     * @param _s
     */
    protected void setPRICEDFEATURE(String _s) {
        PRICEDFEATURE = _s;
        m_AttCollection.put(FEATURE_PRICEDFEATURE, _s);
    }

    /**
     * setPRICEDFEATURE_FC
     *
     * @param _s
     */
    protected void setPRICEDFEATURE_FC(String _s) {
        PRICEDFEATURE_FC = _s;
    }

    /**
     * setTANDC
     *
     * @param _s
     */
    protected void setTANDC(String _s) {
        TANDC = _s;
        m_AttCollection.put(SWFEATURE_CHARGEOPTION, _s);
    }

    /**
     * setCATEGORY
     *
     * @param _s
     */
    protected void setCATEGORY(String _s) {
        CATEGORY = _s;
        m_AttCollection.put(FEATURE_HWFCCAT, _s);
    }

    /**
     * setCATEGORY_FC
     *
     * @param _s
     */
    protected void setCATEGORY_FC(String _s) {
        CATEGORY_FC = _s;
    }

    /**
     * setSUBCATEGORY
     *
     * @param _s
     */
    protected void setSUBCATEGORY(String _s) {
        SUBCATEGORY = _s;
        m_AttCollection.put(FEATURE_HWFCSUBCAT, _s);
    }

    /**
     * setSUBCATEGORY_FC
     *
     * @param _s
     */
    protected void setSUBCATEGORY_FC(String _s) {
        SUBCATEGORY_FC = _s;
    }

    /**
     * setSTATUS
     *
     * @param _s
     */
    protected void setSTATUS(String _s) {
        STATUS = _s;
        m_AttCollection.put(FEATURE_STATUS, _s);
    }

    /**
     * setSTATUS_FC
     *
     * @param _s
     */
    protected void setSTATUS_FC(String _s) {
        STATUS_FC = _s;
    }

    /**
     * setCGTYPE
     *
     * @param _s
     */
    protected void setCGTYPE(String _s) {
        CGTYPE = _s;
    }

    /**
     * setCGTYPE_FC
     *
     * @param _s
     */
    protected void setCGTYPE_FC(String _s) {
        CGTYPE_FC = _s;
    }
 // begin added by guobin 2008-1-31
    /**
     * setCONFIGURATORFLAG
     *
     * @param _s
     */
    protected void setCONFIGURATORFLAG(String _s) {
    	CONFIGURATORFLAG = _s;
    }
    /**
     * setCONFIGURATORFLAG_FC
     *
     * @param _s
     */
    protected void setCONFIGURATORFLAG_FC(String _s) {
    	CONFIGURATORFLAG_FC = _s;
    }
    // end added by guobin 2008-1-31
    /**
     * setOSLEVEL
     *
     * @param _s
     */
    protected void setOSLEVEL(String _s) {
        OSLEVEL = _s;
    }

    /**
     * setOSLEVEL_FC
     *
     * @param _s
     */
    protected void setOSLEVEL_FC(String _s) {
        OSLEVEL_FC = _s;
    }

    /**
     * setVALFROM
     *
     * @param _s
     */
    protected void setVALFROM(String _s) {
        VALFROM = _s;
    }

    /**
     * setVALTO
     *
     * @param _s
     */
    protected void setVALTO(String _s) {
        VALTO = _s;
    }

    /**
     * setINVNAME
     *
     * Lenovo CQ - 2/18/2015
     *
     * @param _s
     */
    protected void setINVNAME(String _s) {
        INVNAME = _s;
        m_AttCollection.put(FEATURE_INVNAME, _s);
    }


    /**
     * getFEATURECODE
     *
     * @return
     */
    public String getFEATURECODE() {
        if (FEATURECODE == null) {
            return "";
        }
        return FEATURECODE;
    }

    /**
     * getFCMKTGNAME
     *
     * @return
     */
    public String getFCMKTGDESC() {
        if (FCMKTGDESC == null) {
            return "";
        }
        return FCMKTGDESC;
    }

    /**
     * getFCTYPE
     *
     * @return
     */
    public String getFCTYPE() {
        if (FCTYPE == null) {
            return "";
        }
        return FCTYPE;
    }

    /**
     * getFCTYPE_FC
     *
     * @return
     */
    public String getFCTYPE_FC() {
        if (FCTYPE_FC == null) {
            return "";
        }
        return FCTYPE_FC;
    }

    /**
     * getANNDATE
     *
     * @return
     */
    public String getANNDATE() {
        if (ANNDATE == null) {
            return "";
        }
        return ANNDATE;
    }

    /**
     * getWITHDRAWDATE
     *
     * @return
     */
    public String getWITHDRAWDATE() {
        if (WITHDRAWDATE == null) {
            return "";
        }
        return WITHDRAWDATE;
    }

    /**
     * getPRICEDFEATURE
     *
     * @return
     */
    public String getPRICEDFEATURE() {
        if (PRICEDFEATURE == null) {
            return "";
        }
        return PRICEDFEATURE;
    }

    /**
     * getPRICEDFEATURE_FC
     *
     * @return
     */
    public String getPRICEDFEATURE_FC() {
        if (PRICEDFEATURE_FC == null) {
            return "";
        }
        return PRICEDFEATURE_FC;
    }

    /**
     * getTANDC
     *
     * @return
     */
    public String getTANDC() {
        if (TANDC == null) {
            return "";
        }
        return TANDC;
    }

    /**
     * getCATEGORY
     *
     * @return
     */
    public String getCATEGORY() {
        if (CATEGORY == null) {
            return "";
        }
        return CATEGORY;
    }

    /**
     * getCATEGORY_FC
     *
     * @return
     */
    public String getCATEGORY_FC() {
        if (CATEGORY_FC == null) {
            return "";
        }
        return CATEGORY_FC;
    }

    /**
     * getSUBCATEGORY
     *
     * @return
     */
    public String getSUBCATEGORY() {
        if (SUBCATEGORY == null) {
            return "";
        }
        return SUBCATEGORY;
    }

    /**
     * getSUBCATEGORY_FC
     *
     * @return
     */
    public String getSUBCATEGORY_FC() {
        if (SUBCATEGORY_FC == null) {
            return "";
        }
        return SUBCATEGORY_FC;
    }

    /**
     * getSTATUS
     *
     * @return
     */
    public String getSTATUS() {
        if (STATUS == null) {
            return "";
        }
        return STATUS;
    }

    /**
     * getSTATUS_FC
     *
     * @return
     */
    public String getSTATUS_FC() {
        if (STATUS_FC == null) {
            return "";
        }
        return STATUS_FC;
    }

    /**
     * getCGTYPE
     *
     * @return
     */
    public String getCGTYPE() {
        if (CGTYPE == null) {
            return "";
        }
        return CGTYPE;
    }

    /**
     * getCGTYPE_FC
     *
     * @return
     */
    public String getCGTYPE_FC() {
        if (CGTYPE_FC == null) {
            return "";
        }
        return CGTYPE_FC;
    }
    //begin added by guobin 2008-1-31
    /**
     * getCONFIGURATORFLAG
     *
     * @return
     */
    public String getCONFIGURATORFLAG() {
        if (CONFIGURATORFLAG == null) {
            return "";
        }
        return CONFIGURATORFLAG;
    }
    /**
     * getCONFIGURATORFLAG_FC
     *
     * @return
     */
    public String getCONFIGURATORFLAG_FC() {
        if (CONFIGURATORFLAG_FC == null) {
            return "";
        }
        return CONFIGURATORFLAG_FC;
    }
    /**
     * getOSLEVEL
     *
     * @return
     */
    public String getOSLEVEL() {
        if (OSLEVEL == null) {
            return "";
        }
        return OSLEVEL;
    }

    /**
     * getOSLEVEL_FC
     *
     * @return
     */
    public String getOSLEVEL_FC() {
        if (OSLEVEL_FC == null) {
            return "";
        }
        return OSLEVEL_FC;
    }

    /**
     * getVALFROM
     *
     * @return
     */
    public String getVALFROM() {
        if (VALFROM == null) {
            return "";
        }
        return VALFROM;
    }

    /**
     * getVALTO
     *
     * @return
     */
    public String getVALTO() {
        if (VALTO == null) {
            return "";
        }
        return VALTO;
    }

    /**
     * getINVNAME
     *
     * Lenovo CQ - 2/18/2015
     *
     * @param _s
     */
    public String getINVNAME() {
        if (INVNAME == null) {
            return "";
        }
        return INVNAME;
    }

    /**
     * getFeatureId
     *
     * @return
     */
    public FeatureId getFeatureId() {
        return (FeatureId) getId();
    }

    /**
     * xmlFEATID
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFEATID(XMLWriter _xml) throws Exception {
        FeatureId fid = getFeatureId();
        _xml.writeEntity("FEATID");
        _xml.write(fid.getFeatEntityType() + fid.getFeatEntityID()); // + fid.getItemEntityType() + fid.getItemEntityID());
        _xml.endEntity();
    }

    /**
     * xmlFEATURECODE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFEATURECODE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FEATURECODE");
        _xml.write(FEATURECODE);
        _xml.endEntity();
    }

    /**
     * xmlFCMKTGNAME
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFCMKTGDESC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FCMKTGDESC");
        _xml.write(FCMKTGDESC);
        _xml.endEntity();
    }

    /**
     * xmlFCTYPE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFCTYPE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FCTYPE");
        _xml.write(FCTYPE);
        _xml.endEntity();
    }

    /**
     * xmlFCTYPE_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFCTYPE_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FCTYPE_FC");
        _xml.write(FCTYPE_FC);
        _xml.endEntity();
    }

    /**
     * xmlANNDATE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlANNDATE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("ANNDATE");
        _xml.write(ANNDATE);
        _xml.endEntity();
    }

    /**
     * xmlWITHDRAWDATE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlWITHDRAWDATE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("WITHDRAWDATE");
        _xml.write(WITHDRAWDATE);
        _xml.endEntity();
    }

    /**
     * xmlPRICEDFEATURE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlPRICEDFEATURE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PRICEDFEATURE");
        _xml.write(PRICEDFEATURE);
        _xml.endEntity();
    }

    /**
     * xmlPRICEDFEATURE_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlPRICEDFEATURE_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PRICEDFEATURE_FC");
        _xml.write(PRICEDFEATURE_FC);
        _xml.endEntity();
    }

    /**
     * xmlTANDC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlTANDC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CHARGEOPTION");
        _xml.write(TANDC);
        _xml.endEntity();
    }

    /**
     * xmlFCCAT
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFCCAT(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FCCAT");
        _xml.write(CATEGORY);
        _xml.endEntity();
    }

    /**
     * xmlFCCAT_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFCCAT_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FCCAT_FC");
        _xml.write(CATEGORY_FC);
        _xml.endEntity();
    }

    /**
     * xmlFCSUBCAT
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFCSUBCAT(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FCSUBCAT");
        _xml.write(SUBCATEGORY);
        _xml.endEntity();
    }

    /**
     * xmlFCSUBCAT_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFCSUBCAT_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FCSUBCAT_FC");
        _xml.write(SUBCATEGORY_FC);
        _xml.endEntity();
    }

    /**
     * xmlCATEGORY
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlCATEGORY(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CATEGORY");
        _xml.write(CATEGORY);
        _xml.endEntity();
    }

    /**
     * xmlCATEGORY_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlCATEGORY_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CATEGORY_FC");
        _xml.write(CATEGORY_FC);
        _xml.endEntity();
    }

    /**
     * xmlSUBCATEGORY
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlSUBCATEGORY(XMLWriter _xml) throws Exception {
        _xml.writeEntity("SUBCATEGORY");
        _xml.write(SUBCATEGORY);
        _xml.endEntity();
    }

    /**
     * xmlSUBCATEGORY_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlSUBCATEGORY_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("SUBCATEGORY_FC");
        _xml.write(SUBCATEGORY_FC);
        _xml.endEntity();
    }

    /**
     * xmlSUBGROUP
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlGROUP(XMLWriter _xml) throws Exception {
        _xml.writeEntity("SWFCGRP");
        _xml.write(GROUP);
        _xml.endEntity();
    }

    /**
     * xmlSUBGROUP_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlGROUP_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("SUBGROUP_FC");
        _xml.write(GROUP_FC);
        _xml.endEntity();
    }

    /**
     * xmlSTATUS
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlSTATUS(XMLWriter _xml) throws Exception {
        _xml.writeEntity("STATUS");
        _xml.write(STATUS);
        _xml.endEntity();
    }

    /**
     * xmlSTATUS_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlSTATUS_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("STATUS_FC");
        _xml.write(STATUS_FC);
        _xml.endEntity();
    }

    /**
     * xmlCGTYPE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlCGTYPE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CGTYPE");
        _xml.write(CGTYPE);
        _xml.endEntity();
    }

    /**
     * xmlCGTYPE_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlCGTYPE_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CGTYPE_FC");
        _xml.write(CGTYPE_FC);
        _xml.endEntity();
    }

    /**
     * xmlOSLEVEL
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlOSLEVEL(XMLWriter _xml) throws Exception {
        _xml.writeEntity("OSLEVEL");
        _xml.write(OSLEVEL);
        _xml.endEntity();
    }

    /**
     * xmlCGTYPE_FC
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlOSLEVEL_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("OSLEVEL_FC");
        _xml.write(OSLEVEL_FC);
        _xml.endEntity();
    }

    /**
     * xmlVALFROM
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlVALFROM(XMLWriter _xml) throws Exception {
        _xml.writeEntity("VALFROM");
        _xml.write(VALFROM);
        _xml.endEntity();
    }

    /**
     * xmlVALTO
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlVALTO(XMLWriter _xml) throws Exception {
        _xml.writeEntity("VALTO");
        _xml.write(VALTO);
        _xml.endEntity();
    }

    /**
     * xmlINVNAME
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlINVNAME(XMLWriter _xml) throws Exception {
        _xml.writeEntity("INVNAME");
        _xml.write(INVNAME);
        _xml.endEntity();
    }

    /**
     * generateXMLFragment
     *
     * @param _xml
     */
    public void generateXMLFragment(XMLWriter _xml) {

        FeatureId fid = getFeatureId();

        try {
            if (fid.isHardwareFeature()) {
                _xml.writeEntity("HWFEATURE");
                xmlFEATID(_xml);
                xmlFEATURECODE(_xml);
                xmlFCMKTGDESC(_xml);
                xmlFCTYPE(_xml);
                xmlFCTYPE_FC(_xml);
                xmlANNDATE(_xml);
                xmlWITHDRAWDATE(_xml);

                xmlPRICEDFEATURE(_xml);
                xmlPRICEDFEATURE_FC(_xml);
                //xmlTANDC(_xml);

                xmlFCCAT(_xml);
                xmlFCCAT_FC(_xml);
                xmlFCSUBCAT(_xml);
                xmlFCSUBCAT_FC(_xml);
                //xmlFCGRP(_xml);
                //xmlFCGRP_FC(_xml);

                xmlSTATUS(_xml);
                xmlSTATUS_FC(_xml);
                xmlCGTYPE(_xml);
                xmlCGTYPE_FC(_xml);

                xmlOSLEVEL(_xml);
                xmlOSLEVEL_FC(_xml);
                xmlINVNAME(_xml);

                _xml.endEntity();

            }
            else if (fid.isSoftwareFeature()) {
                _xml.writeEntity("SWFEATURE");
                xmlFEATID(_xml);
                xmlFEATURECODE(_xml);
                xmlFCMKTGDESC(_xml);
                xmlFCTYPE(_xml);
                xmlFCTYPE_FC(_xml);
                //xmlANNDATE(_xml);
                xmlWITHDRAWDATE(_xml);

                xmlPRICEDFEATURE(_xml);
                xmlPRICEDFEATURE_FC(_xml);
                xmlTANDC(_xml);

                xmlCATEGORY(_xml);
                xmlCATEGORY_FC(_xml);
                xmlSUBCATEGORY(_xml);
                xmlSUBCATEGORY_FC(_xml);
                xmlGROUP(_xml);
                xmlGROUP_FC(_xml);

                xmlSTATUS(_xml);
                xmlSTATUS_FC(_xml);
                xmlCGTYPE(_xml);
                xmlCGTYPE_FC(_xml);
                xmlOSLEVEL(_xml);
                xmlOSLEVEL_FC(_xml);
                xmlINVNAME(_xml);

                _xml.endEntity();

            }

            if (m_fdc != null) {
                Iterator it = m_fdc.values().iterator();
                while (it.hasNext()) {
                    FeatureDetail fd = (FeatureDetail) it.next();
                    fd.generateXMLFragment(_xml);
                }
            }
            else {
                System.out.println("Feature generateXMLFragment m_fdc is null.");
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // end of xml methods

    /**
     * setFeatureDetailCollection
     *
     * @param _fdc
     */
    protected void setFeatureDetailCollection(FeatureDetailCollection _fdc) {
        m_fdc = _fdc;
    }

    /**
     * getFeatureDetailCollection
     *
     * @return
     */
    public FeatureDetailCollection getFeatureDetailCollection() {
        return m_fdc;
    }

    /**
     *  This guy is reposible for putting a current image of himself into the CatDb
     *
     * @param _cat
     * @param _bcommit
     */
    public void put(Catalog _cat, boolean _bcommit) {

        if (Catalog.isDryRun()) {
            return;
        }

        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        FeatureId fid = this.getFeatureId();
        GeneralAreaMapItem gami = fid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();
        String strFeatEntityType = fid.getFeatEntityType();
        int iFeatEntityID = fid.getFeatEntityID();

        try {
        	//for CR072607687(modified by houjie 2008-04-07)
        	//add column CONFIGURATORFLAG,CONFIGURATORFLAG_FC
        	db.callGBL8980(rets, strEnterprise, strCountryCode, strLanguageCode, iNLSID, strCountryList, strFeatEntityType,
                iFeatEntityID, this.getFEATURECODE(), this.getFCMKTGDESC(), this.getFCTYPE(), this.getFCTYPE_FC(), this.getANNDATE(),
                this.getWITHDRAWDATE(), this.getPRICEDFEATURE(), this.getPRICEDFEATURE_FC(), this.getTANDC(),
                this.trimUnicodeString(this.getCATEGORY(),"CATEGORY",32),
                this.getCATEGORY_FC(), this.getSUBCATEGORY(), this.getSUBCATEGORY_FC(), this.getGROUP(), this.getGROUP_FC(),
                this.getCGTYPE(), this.getCGTYPE_FC(), this.getOSLEVEL(), this.getOSLEVEL_FC(), this.getSTATUS(),
                this.getSTATUS_FC(), this.getCONFIGURATORFLAG(),this.getCONFIGURATORFLAG_FC(),(this.isActive() ? 1 : 0),this.getINVNAME());

            if (_bcommit) {
                db.commit();
            }
            db.freeStatement();
            db.isPending();

        }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     *  This guy is reposible for deactivate a current image of himself in the CatDb
     *
     * @param _cat
     * @param _bcommit
     */
    public void deactivate(Catalog _cat, boolean _bcommit) {

        if (Catalog.isDryRun()) {
            return;
        }

        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        FeatureId fid = this.getFeatureId();
        GeneralAreaMapItem gami = fid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();
        String strFeatEntityType = fid.getFeatEntityType();
        int iFeatEntityID = fid.getFeatEntityID();

        try {
        	db.callGBL8980(rets, strEnterprise, strCountryCode, strLanguageCode, iNLSID, strCountryList, strFeatEntityType,
                iFeatEntityID, this.getFEATURECODE(), this.getFCMKTGDESC(), this.getFCTYPE(), this.getFCTYPE_FC(), this.getANNDATE(),
                this.getWITHDRAWDATE(), this.getPRICEDFEATURE(), this.getPRICEDFEATURE_FC(), this.getTANDC(), this.getCATEGORY(),
                this.getCATEGORY_FC(), this.getSUBCATEGORY(), this.getSUBCATEGORY_FC(), this.getGROUP(), this.getGROUP_FC(),
                this.getCGTYPE(), this.getCGTYPE_FC(), this.getOSLEVEL(), this.getOSLEVEL_FC(), this.getSTATUS(),
                this.getSTATUS_FC(), this.getCONFIGURATORFLAG(),this.getCONFIGURATORFLAG_FC(),(this.isActive() ? 1 : 0),this.getINVNAME());

            if (_bcommit) {
                db.commit();
            }

            db.freeStatement();
            db.isPending();

        }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     *  (non-Javadoc)
     *
     * @param _ci
     */
    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub
    }

    /**
     * getSmc
     *
     * @return
     */
    public SyncMapCollection getSmc() {
        return m_smc;
    }

    /**
     * setSmc
     *
     * @param collection
     */
    public void setSmc(SyncMapCollection collection) {
        m_smc = collection;
    }

    /**
     * setAttributes
     *
     * @param _ei
     */
    public void setAttributes(EntityItem _ei) {

        String strTraceBase = "Feature setAttributes method ";
        if (_ei.getEntityType().equals(Feature.HWFEATURE_ENTITY_TYPE)) {
            // set FEATURECODE
            EANTextAttribute taFEATURE_FEATURECODE = (EANTextAttribute) _ei.getAttribute(FEATURE_FEATURECODE);
            if (taFEATURE_FEATURECODE != null) {
                setFEATURECODE(taFEATURE_FEATURECODE.toString());
            }

            // set FCMKTGNAME
            EANTextAttribute taFEATURE_FCMKTGDESC = (EANTextAttribute) _ei.getAttribute(FEATURE_FCMKTGDESC);
            D.ebug(D.EBUG_SPEW,"USING FCMKTGSHRTDESC!!! A :" + FEATURE_FCMKTGDESC);
            if (taFEATURE_FCMKTGDESC != null) {
				D.ebug(D.EBUG_SPEW,"USING FCMKTGSHRTDESC!!! B :" + taFEATURE_FCMKTGDESC.getAttributeCode());
				D.ebug(D.EBUG_SPEW,"USING FCMKTGSHRTDESC!!! C :" + taFEATURE_FCMKTGDESC.toString());
                setFCMKTGDESC(taFEATURE_FCMKTGDESC.toString());
            }

            // set INVNAME
            EANTextAttribute taFEATURE_INVNAME = (EANTextAttribute) _ei.getAttribute(FEATURE_INVNAME);
            D.ebug(D.EBUG_SPEW,"USING INVNAME!!! A :" + FEATURE_INVNAME);
            if (taFEATURE_INVNAME != null) {
				D.ebug(D.EBUG_SPEW,"USING INVNAME!!! B :" + taFEATURE_INVNAME.getAttributeCode());
				D.ebug(D.EBUG_SPEW,"USING INVNAME!!! C :" + taFEATURE_INVNAME.toString());
                setINVNAME(taFEATURE_INVNAME.toString());
            }

            // set FCTYPE, FCTYPE_FC
            EANFlagAttribute faFEATURE_FCTYPE = (EANFlagAttribute) _ei.getAttribute(FEATURE_FCTYPE);
            if (faFEATURE_FCTYPE != null) {
                setFCTYPE(faFEATURE_FCTYPE.toString());
                setFCTYPE_FC(faFEATURE_FCTYPE.getFirstActiveFlagCode());
            }

            // set ANNDATE
            EANTextAttribute taFEATURE_FIRSTANNDATE = (EANTextAttribute) _ei.getAttribute(FEATURE_FIRSTANNDATE);
            if (taFEATURE_FIRSTANNDATE != null) {
                setANNDATE(taFEATURE_FIRSTANNDATE.toString());
            }

            // set WITHDRAWDATE
            EANTextAttribute taFEATURE_WITHDRAWANNDATE_T = (EANTextAttribute) _ei.getAttribute(FEATURE_WITHDRAWANNDATE_T);
            if (taFEATURE_WITHDRAWANNDATE_T != null) {
                setWITHDRAWDATE(taFEATURE_WITHDRAWANNDATE_T.toString());
            }

            // set PRICEDFEATURE, PRICEDFEATURE_FC
            EANFlagAttribute faFEATURE_PRICEDFEATURE = (EANFlagAttribute) _ei.getAttribute(FEATURE_PRICEDFEATURE);
            if (faFEATURE_PRICEDFEATURE != null) {
                setPRICEDFEATURE(faFEATURE_PRICEDFEATURE.toString());
                setPRICEDFEATURE_FC(faFEATURE_PRICEDFEATURE.getFirstActiveFlagCode());
            }

            // no TANDC for FEATURE

            // set CATEGORY, CATEGORY_FC
            EANFlagAttribute faFEATURE_HWFCCAT = (EANFlagAttribute) _ei.getAttribute(FEATURE_HWFCCAT);
            if (faFEATURE_HWFCCAT != null) {
                setCATEGORY(faFEATURE_HWFCCAT.toString());
                setCATEGORY_FC(faFEATURE_HWFCCAT.getFirstActiveFlagCode());
            }

            // set SUBCATEGORY, SUBCATEGORY_FC
            EANFlagAttribute faFEATURE_HWFCSUBCAT = (EANFlagAttribute) _ei.getAttribute(FEATURE_HWFCSUBCAT);
            if (faFEATURE_HWFCSUBCAT != null) {
                setSUBCATEGORY(faFEATURE_HWFCSUBCAT.toString());
                setSUBCATEGORY_FC(faFEATURE_HWFCSUBCAT.getFirstActiveFlagCode());
            }

            // set GROUP, GROUP_FC
            EANFlagAttribute faFEATURE_HWFCGRP = (EANFlagAttribute) _ei.getAttribute(FEATURE_HWFCGRP);
            if (faFEATURE_HWFCGRP != null) {
                setGROUP(faFEATURE_HWFCGRP.toString());
                setGROUP_FC(faFEATURE_HWFCGRP.getFirstActiveFlagCode());
            }

            // set STATUS, STATUS_FC
            EANFlagAttribute faFEATURE_STATUS = (EANFlagAttribute) _ei.getAttribute(FEATURE_STATUS);
            if (faFEATURE_STATUS != null) {
                setSTATUS(faFEATURE_STATUS.toString());
                setSTATUS_FC(faFEATURE_STATUS.getFirstActiveFlagCode());
            }

            // set CGTYPE, CGTYPE_FC
            EANFlagAttribute faFEATURE_CGTYPE = (EANFlagAttribute) _ei.getAttribute(FEATURE_CGTYPE);
            if (faFEATURE_CGTYPE != null) {
                setCGTYPE(faFEATURE_CGTYPE.toString());
                setCGTYPE_FC(faFEATURE_CGTYPE.getFirstActiveFlagCode());
            }

        	//for CR072607687(modified by guobin 2008-04-07)
        	//set CONFIGURATORFLAG,CONFIGURATORFLAG_FC
            EANFlagAttribute faFEATURE_CONFIGURATORFLAG = (EANFlagAttribute) _ei.getAttribute(FEATURE_CONFIGURATORFLAG);
            if (faFEATURE_CONFIGURATORFLAG != null) {
                setCONFIGURATORFLAG(faFEATURE_CONFIGURATORFLAG.toString());
                setCONFIGURATORFLAG_FC(faFEATURE_CONFIGURATORFLAG.getFirstActiveFlagCode());
            }
            D.ebug(D.EBUG_INFO, "now the value of the attribute CONFIGURATORFLAG IS : " + getCONFIGURATORFLAG());
            D.ebug(D.EBUG_INFO, "now the value of the attribute CONFIGURATORFLAG_FC IS : " +  getCONFIGURATORFLAG_FC());
            //end added by guobin 2008-04-07

            // set OSLEVEL, OSLEVEL_FC
            // GAB 053106 - REMOVED OSLEVEL!!
            /*
  rf53rick@us.ibm...	is there a reported problem (TIR) for oslevel on feature?
  1burns1@us.ib...	sort of
  1burns1@us.ib...	.... the problem was a missing (inactive==0) feature(s)
  rf53rick@us.ibm...	oh i see.
  1burns1@us.ib...	which was due to my code failing to insert the record due to lengthy oslevel
  rf53rick@us.ibm...	well. the catalog does not need oslevel on features.
  1burns1@us.ib...	Hah!
  rf53rick@us.ibm...	so we don't even need it to be in the feature table.
  1burns1@us.ib...	I was wondering why that data was so screwy and nobody had noticed before..
  1burns1@us.ib...	cool.
  1burns1@us.ib...	I can take it out then... just leave the oslevel col blank?
  rf53rick@us.ibm...	yeah. you can leave oslevel and oslevel_fc blank in feature table.
            */
            //EANFlagAttribute faFEATURE_OSLEVEL = (EANFlagAttribute) _ei.getAttribute(FEATURE_OSLEVEL);
            //if (faFEATURE_OSLEVEL != null) {
            //    setOSLEVEL(faFEATURE_OSLEVEL.toString());
            //    setOSLEVEL_FC(faFEATURE_OSLEVEL.getFirstActiveFlagCode());
            //}

        }
        else if (_ei.getEntityType().equals(Feature.SWFEATURE_ENTITY_TYPE)) {
            // Dealing with the software Feature

            // set FEATURECODE
            EANTextAttribute taSWFEATURE_FEATURECODE = (EANTextAttribute) _ei.getAttribute(FEATURE_FEATURECODE);
            if (taSWFEATURE_FEATURECODE != null) {
                setFEATURECODE(taSWFEATURE_FEATURECODE.toString());
            }

            // set FCMKTGNAME=SWFEATURE.SWFEATDESC
            EANTextAttribute taSWFEATURE_SWFEATDESC = (EANTextAttribute) _ei.getAttribute(SWFEATURE_SWFEATDESC);
            if (taSWFEATURE_SWFEATDESC != null) {
                setFCMKTGDESC(taSWFEATURE_SWFEATDESC.toString());
            }

            // set INVNAME
            EANTextAttribute taSWFEATURE_INVNAME = (EANTextAttribute) _ei.getAttribute(FEATURE_INVNAME);
            if (taSWFEATURE_INVNAME != null) {
                setINVNAME(taSWFEATURE_INVNAME.toString());
            }

            // set FCTYPE, FCTYPE_FC
            EANFlagAttribute faSWFEATURE_FCTYPE = (EANFlagAttribute) _ei.getAttribute(FEATURE_FCTYPE);
            if (faSWFEATURE_FCTYPE != null) {
                setFCTYPE(faSWFEATURE_FCTYPE.toString());
                setFCTYPE_FC(faSWFEATURE_FCTYPE.getFirstActiveFlagCode());
            }

            // no ANNDATE for SWFEATURE

            // set WITHDRAWDATE
            EANTextAttribute taSWFEATURE_WITHDRAWANNDATE_T = (EANTextAttribute) _ei.getAttribute(FEATURE_WITHDRAWANNDATE_T);
            if (taSWFEATURE_WITHDRAWANNDATE_T != null) {
                setWITHDRAWDATE(taSWFEATURE_WITHDRAWANNDATE_T.toString());
            }

            // set PRICEDFEATURE, PRICEDFEATURE_FC
            EANFlagAttribute faSWFEATURE_PRICEDFEATURE = (EANFlagAttribute) _ei.getAttribute(FEATURE_PRICEDFEATURE);
            if (faSWFEATURE_PRICEDFEATURE != null) {
                setPRICEDFEATURE(faSWFEATURE_PRICEDFEATURE.toString());
                setPRICEDFEATURE_FC(faSWFEATURE_PRICEDFEATURE.getFirstActiveFlagCode());
            }

            // set TANDC for SWFEATURE = CHARGEOPTION
            EANFlagAttribute faSWFEATURE_CHARGEOPTION = (EANFlagAttribute) _ei.getAttribute(SWFEATURE_CHARGEOPTION);
            if (faSWFEATURE_CHARGEOPTION != null) {
                setTANDC(faSWFEATURE_CHARGEOPTION.toString());
            }

            // set CATEGORY, CATEGORY_FC
            EANFlagAttribute faSWFEATURE_SWFCCAT = (EANFlagAttribute) _ei.getAttribute(SWFEATURE_SWFCCAT);
            if (faSWFEATURE_SWFCCAT != null) {
                setCATEGORY(faSWFEATURE_SWFCCAT.toString());
                setCATEGORY_FC(faSWFEATURE_SWFCCAT.getFirstActiveFlagCode());
            }

            // set SUBCATEGORY, SUBCATEGORY_FC
            EANFlagAttribute faSWFEATURE_SWFCSUBCAT = (EANFlagAttribute) _ei.getAttribute(SWFEATURE_SWFCSUBCAT);
            if (faSWFEATURE_SWFCSUBCAT != null) {
                setSUBCATEGORY(faSWFEATURE_SWFCSUBCAT.toString());
                setSUBCATEGORY_FC(faSWFEATURE_SWFCSUBCAT.getFirstActiveFlagCode());
            }

            // set GROUP, GROUP_FC
            EANFlagAttribute faSWFEATURE_SWFCGRP = (EANFlagAttribute) _ei.getAttribute(SWFEATURE_SWFCGRP);
            if (faSWFEATURE_SWFCGRP != null) {
                setGROUP(faSWFEATURE_SWFCGRP.toString());
                setGROUP_FC(faSWFEATURE_SWFCGRP.getFirstActiveFlagCode());
            }

            // set STATUS, STATUS_FC
            EANFlagAttribute faSWFEATURE_STATUS = (EANFlagAttribute) _ei.getAttribute(FEATURE_STATUS);
            if (faSWFEATURE_STATUS != null) {
                setSTATUS(faSWFEATURE_STATUS.toString());
                setSTATUS_FC(faSWFEATURE_STATUS.getFirstActiveFlagCode());
            }

            // don't know what are for CGTYPE, CGTYPE_FC
            // set OSLELVEL, OSLEVEL_FC

            // removed OSLEVEL- see comment above..
            //EANFlagAttribute faSWFEATURE_OSLEVEL = (EANFlagAttribute) _ei.getAttribute(FEATURE_OSLEVEL);
            //if (faSWFEATURE_OSLEVEL != null) {
            //    setOSLEVEL(faSWFEATURE_OSLEVEL.toString());
            //    setOSLEVEL_FC(faSWFEATURE_OSLEVEL.getFirstActiveFlagCode());
            //}

        }
    }

    /**
     *  (non-Javadoc)
     *
     * @param _str
     * @param _oAtt
     */
    public void setAttribute(String _str, Object _oAtt) {
        if (_oAtt instanceof EntityItem) {
        }
        return;
    }

    /**
     *  (non-Javadoc)
     *
     * @return Object
     * @param _str
     */
    public Object getAttribute(String _strTag) {
        if (m_AttCollection.containsKey(_strTag)) {
            return m_AttCollection.get(_strTag);
        }
        else {
            System.out.println("attribute not found for " + _strTag);
        }
        return null;
    }

    /**
     * get attribute keys
     * 20050808
     * @return keys[]
     * @author tony
     */
    public String[] getAttributeKeys() {
        if (m_AttCollection != null) {
            Set keys = m_AttCollection.keySet();
            if (keys != null) {
                return (String[]) keys.toArray(new String[m_AttCollection.size()]);
            }
        }
        return null;
    }

    /**
     * getGROUP
     * @return
     */
    public String getGROUP() {
        if (GROUP == null) {
            return "";
        }
        return GROUP;
    }

    /**
     * getGROUP_FC
     * @return
     */
    public String getGROUP_FC() {
        if (GROUP_FC == null) {
            return "";
        }
        return GROUP_FC;
    }

    /**
     * setGROUP
     * @param string
     */
    public void setGROUP(String string) {
        GROUP = string;
        m_AttCollection.put(FEATURE_HWFCGRP, string);
    }

    /**
     * setGROUP_FC
     * @param string
     */
    public void setGROUP_FC(String string) {
        GROUP_FC = string;
    }

    /**
     * hasSyncMapCollection
     *
     * @return
     */
    public final boolean hasSyncMapCollection() {
        return m_smc != null;
    }

    private String trimUnicodeString(String _strVal, String _strColName, int _iColLen) {
    	if (_strVal == null) {
    	return _strVal;
    	}
    	try {
    	int iLength = _strVal.getBytes("UTF-8").length;
    	String strAnswer = new String(_strVal);
    	if (iLength > _iColLen) {
    	D.ebug(D.EBUG_WARN,
    	"**trimUnicodeString: Length Exceeds max length for " +
    	_strColName + ":" + _strVal + "it is being truncated");
    	while (iLength > _iColLen) {
    	strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
    	iLength = strAnswer.getBytes("UTF-8").length;
    	}
    	}
    	_strVal = strAnswer;
    	}
    	catch (Exception ex) {
    	D.ebug(D.EBUG_ERR,
    	"**trimUnicodeString: UnsupportedEncodingException. Passing entire string back." +
    	ex.getMessage());
    	}
    	return _strVal;
    	}


}
/**
 * $Log: Feature.java,v $
 * Revision 1.16  2015/03/05 16:12:08  ptatinen
 * Add new attributes for Lenovo CQ
 *
 * Revision 1.15  2011/05/05 11:21:35  wendy
 * src from IBMCHINA
 *
 * Revision 1.6  2009/03/26 19:58:42  rick
 * Change to trim CATEGORY using UTF-8.
 *
 * Revision 1.5  2009/01/23 18:47:30  rick
 * MN - 37820948 ... truncate CATEGORY to 32
 *
 * Revision 1.4  2008/05/09 05:33:23  yang
 * *** empty log message ***
 *
 * Revision 1.3  2008/05/09 04:28:42  yang
 * *** empty log message ***
 *
 * Revision 1.2  2008/04/07 02:38:49  yang
 * *** empty log message ***
 *
 * Revision 1.5  2008/03/28 02:29:46  yang
 * *** empty log message ***
 *
 * Revision 1.4  2008/03/27 08:49:36  yang
 * *** empty log message ***
 *
 * Revision 1.3  2008/03/26 06:28:13  yang
 * *** empty log message ***
 *
 * Revision 1.2  2008/01/31 07:25:59  yang
 * changed by guobin
 *
 * Revision 1.1  2008/01/30 02:49:34  yang
 * no message
 *
 * Revision 1.1.1.1  2007/06/05 02:09:18  jingb
 * no message
 *
 * Revision 1.13  2006/11/15 21:07:37  gregg
 * a couple of trace stmts only
 *
 * Revision 1.12  2006/09/28 18:05:19  gregg
 * fully backing out configuratorflag
 *
 * Revision 1.11  2006/09/15 17:52:23  gregg
 * backing out configurator flag
 *
 * Revision 1.10  2006/09/14 21:09:13  gregg
 * configurator_fc return empty string on null.
 *
 * Revision 1.9  2006/09/11 20:33:21  gregg
 * configuratorflag_fc
 *
 * Revision 1.8  2006/05/31 18:54:58  gregg
 * removed OSLEVEL per chat w/ Rick.
 *
 * Revision 1.7  2006/05/12 21:26:16  gregg
 * debug
 *
 * Revision 1.6  2006/05/12 20:24:17  gregg
 * putting FCMKTGSHRTDESC back in per request. Hopefully for the last time.
 *
 * Revision 1.5  2006/05/12 18:26:37  gregg
 * FCMKTGSHRTDESC
 *
 * Revision 1.4  2006/05/12 16:30:05  gregg
 * FCMKTGSHRTDESC
 *
 * Revision 1.3  2006/05/05 21:08:21  bala
 * Reverting back according to Nancy/Dave
 *
 * Revision 1.2  2006/05/02 17:04:56  gregg
 * changed mapping for FCMKTGDESC --> FCMKTGSHRTDESC
 *
 * Revision 1.1.1.1  2006/03/30 17:36:29  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.65  2006/03/14 19:46:42  gregg
 * just a comment about CR6315. Nothing changed yet.
 * (FCMKTGDESC->FCTECHNAME)
 *
 * Revision 1.64  2005/11/21 17:35:40  joan
 * fixes
 *
 * Revision 1.63  2005/11/18 18:55:54  joan
 * work on syncToCatDb
 *
 * Revision 1.62  2005/11/16 20:37:07  joan
 * fixes
 *
 * Revision 1.61  2005/11/14 17:14:41  dave
 * putting back middleware catch
 *
 * Revision 1.59  2005/11/07 22:16:57  joan
 * change COMPTYPE to CGTYPE for feature
 *
 * Revision 1.58  2005/10/25 17:26:57  joan
 * FIXES
 *
 * Revision 1.57  2005/10/24 21:02:36  joan
 * fixes
 *
 * Revision 1.56  2005/10/24 18:58:23  joan
 * fixes
 *
 * Revision 1.55  2005/09/26 16:21:49  joan
 * initial load
 *
 * Revision 1.54  2005/09/14 19:41:24  joan
 * fixes
 *
 * Revision 1.53  2005/09/13 20:59:00  joan
 * change for column
 *
 * Revision 1.52  2005/09/12 22:28:30  joan
 * fixes for change of columns
 *
 * Revision 1.51  2005/08/17 21:00:28  joan
 * fixes
 *
 * Revision 1.50  2005/08/17 20:40:13  joan
 * fixes
 *
 * Revision 1.49  2005/08/16 16:52:58  tony
 * CatalogViewer
 *
 * Revision 1.48  2005/08/11 20:26:19  joan
 * fixes
 *
 * Revision 1.47  2005/08/08 20:47:16  tony
 * Added getAttribute logic
 *
 * Revision 1.46  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.45  2005/06/23 21:20:46  joan
 * adjust for countrylist
 *
 * Revision 1.44  2005/06/23 19:23:56  joan
 * fixes
 *
 * Revision 1.43  2005/06/23 19:16:35  joan
 * adjust for countrylist
 *
 * Revision 1.42  2005/06/17 18:54:06  joan
 * fixes
 *
 * Revision 1.41  2005/06/17 17:32:12  joan
 * more work
 *
 * Revision 1.40  2005/06/15 21:33:17  joan
 * add code
 *
 * Revision 1.39  2005/06/13 04:35:33  dave
 * ! needs to be not !
 *
 * Revision 1.38  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.37  2005/06/08 22:27:01  dave
 * ok.. lets keep forging ahead
 *
 * Revision 1.36  2005/06/08 20:31:07  dave
 * more changes to start picking up attributes for collateral
 *
 * Revision 1.35  2005/06/07 04:34:50  dave
 * working on commit control
 *
 * Revision 1.34  2005/06/05 21:06:00  dave
 * standardizing on some field sizes
 *
 * Revision 1.33  2005/06/05 20:57:06  dave
 * closing out final null
 *
 * Revision 1.32  2005/06/05 20:49:46  dave
 * de nulling getters...
 *
 * Revision 1.31  2005/06/05 20:33:55  dave
 * going for the Feature Update to the CatDB
 *
 * Revision 1.30  2005/06/05 19:29:55  dave
 * more cleanup
 *
 * Revision 1.29  2005/06/05 19:17:30  dave
 * attribute mapping
 *
 * Revision 1.28  2005/06/05 18:55:42  dave
 * xml cleanup
 *
 * Revision 1.27  2005/06/05 18:28:32  dave
 * ok.. working on null pointer again
 *
 * Revision 1.26  2005/06/05 18:13:18  dave
 * adding logging
 *
 *
 */
