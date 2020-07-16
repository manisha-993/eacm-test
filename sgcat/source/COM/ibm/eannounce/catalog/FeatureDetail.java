/*
 * Created on Mar 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.io.UnsupportedEncodingException;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANMetaFoundation;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FeatureDetail
    extends CatList {
    private SyncMapCollection m_smc = null;
    private HashMap m_AttCollection = new HashMap();

    public FeatureDetail(FeatureDetailId _fdid) {
        super(_fdid);
    }

    public FeatureDetail(FeatureDetailId _fdid, Catalog _cat) {
        super(_fdid);
        get(_cat);
    }

    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer();
        sb.append(getFeatureDetailId().dump(_brief) + "\n");
        return sb.toString();
    }

    public static void main(String[] args) {
    }

    public String getFeatEntityType() {
        return getFeatureDetailId().getFeatEntityType();
    }

    public int getFeatEntityID() {
        return getFeatureDetailId().getFeatEntityID();
    }

    public String getItemEntityType() {
        return getFeatureDetailId().getItemEntityType();
    }

    public int getItemEntityID() {
        return getFeatureDetailId().getItemEntityID();
    }

    public GeneralAreaMapItem getGami() {
        return getFeatureDetailId().getGami();
    }

    public FeatureDetailId getFeatureDetailId() {
        return (FeatureDetailId) getId();
    }

    public void get(Catalog _cat) {
        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        Profile prof = _cat.getCatalogProfile();
        FeatureDetailId fdid = (FeatureDetailId) getId();
        FeatureDetailCollectionId fdcid = fdid.getFeatureDetailCollectionId();
        GeneralAreaMapItem gami = fdid.getGami();
        D.ebug(this, D.EBUG_DETAIL,
            "get() - here is fdcid..." + ":isByInteval:" + fdcid.isByInterval() + ":isFromPDH: " + fdcid.isFromPDH() +
            ":isFullImages:" + fdcid.isFullImages());

        try {
            if (fdcid.isFromCAT()) {
                ReturnStatus returnStatus = new ReturnStatus( -1);
                ReturnDataResultSet rdrs;
                //GeneralAreaMapItem gami = getGami();
                String strEnterprise = gami.getEnterprise();
                int iNLSID = gami.getNLSID();
                String strCountryList = gami.getCountryList();

                rs = db.callGBL9301(returnStatus, strEnterprise, strCountryList, getFeatEntityType(), getFeatEntityID(),
                                    getItemEntityType(), getItemEntityID());

                rdrs = new ReturnDataResultSet(rs);

                rs.close();

                db.commit();
                db.freeStatement();
                db.isPending();

                for (int ii = 0; ii < rdrs.size(); ii++) {
                    int c = 0;

                    String strAttCode = rdrs.getColumn(ii, c++).trim();
                    String strExtAttrCode = rdrs.getColumn(ii, c++).trim();
                    String strAttValue = rdrs.getColumn(ii, c++).trim();
                    String strAttUnitOfMeasuse = rdrs.getColumn(ii, c++).trim();
                    int iDerived = rdrs.getColumnInt(ii, c++);
                    String strValFrom = rdrs.getColumn(ii, c++).trim();
                    String strValTo = rdrs.getColumn(ii, c++).trim();
                    int iActive = rdrs.getColumnInt(ii, c++);

                    DetailFragmentId dfid = new DetailFragmentId(strAttCode, gami);
                    DetailFragment df = new DetailFragment(dfid);

                    df.setAttributeValue(strAttValue);
                    df.setExtAttributeCode(strExtAttrCode);
                    df.setUnitOfMeasure(strAttUnitOfMeasuse);
                    df.setDerived(iDerived == 1);
                    df.setValFrom(strValFrom);
                    df.setValTo(strValTo);
                    df.setActive(iActive == 1);
                    super.put(df);
                }
            }
            else if (fdcid.isByInterval() && fdcid.isFromPDH()) {
                CatalogInterval cati = fdcid.getInterval();
                if (this.getSmc() == null) {
                    System.out.println("Cannot pull out of the PDH since there is no SycnMap for me.");
                    return;
                }

                try {
                    EntityItem ei = Catalog.getEntityItem(_cat, fdid.getItemEntityType(), fdid.getItemEntityID());

                    this.setAttributes(ei, gami);
                }
                finally {

                }

            }
        }
        catch (SQLException _ex) {
            _ex.printStackTrace();
        }
        catch (MiddlewareException e) {
            e.printStackTrace();

        }
        finally {
            try {
                db.commit();
                db.freeStatement();
                db.isPending();
            }
            catch (SQLException _ex) {
                _ex.printStackTrace();
            }
        }
    }

    public void getReferences(Catalog _cat, int _icase) {
    }

    public final String toString() {
        return "";
    }

    /**
     * Gets the first 254 characters
     *
     * @param _strAttributeCode
     * @param _str
     * @return
     * @author Dave
     */
    protected String getFirst254Char(String _strAttributeCode, String _str) {
        try {

            int iMaxLength = 254;
            int iLength = _str.getBytes("UTF8").length;
            String strAnswer = _str;

            if (iLength > iMaxLength) {
                D.ebug(D.EBUG_WARN,
                       "**getFirst254Char: Length Exceeds max length for prodattriute table for " + _strAttributeCode + " (which is 254 chars) for :" +
                       _str);
                while (iLength > iMaxLength) {
                    strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
                    iLength = strAnswer.getBytes("UTF8").length;
                }
            }
            _str = strAnswer;
        }
        catch (UnsupportedEncodingException ex) {
            D.ebug(D.EBUG_ERR, "**getFirst254Char: UnsupportedEncodingException.  Passing entire string back." + ex.getMessage());
        }

        return _str;

    }

    public void put(Catalog _cat, boolean _bcommit) {
        if (Catalog.isDryRun()) {
            return;
        }

        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        FeatureDetailId fdid = this.getFeatureDetailId();
        GeneralAreaMapItem gami = fdid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();
        String strFeatEntityType = fdid.getFeatEntityType();
        int iFeatEntityID = fdid.getFeatEntityID();
        String strItemEntityType = fdid.getItemEntityType();
        int iItemEntityID = fdid.getItemEntityID();
        D.ebug(this, D.EBUG_DETAIL, "put method");
        try {

            Iterator it = this.values().iterator();
            while (it.hasNext()) {
                DetailFragment df = (DetailFragment) it.next();
                D.ebug(this, D.EBUG_DETAIL, "put detail fragment " + df.toString());
                db.callGBL8984(rets, strEnterprise, strCountryCode, strLanguageCode, iNLSID, strCountryList, strFeatEntityType,
                    iFeatEntityID, strItemEntityType, iItemEntityID, df.getAttributeCode(), df.getExtAttributeCode(),
                    getFirst254Char(df.getExtAttributeCode(), df.getAttributeValue()), df.getUnitOfMeasure(),
                    (df.isDerived() ? 1 : 0), (this.isActive() ? 1 : 0));

                if (_bcommit) {
                    db.commit();
                }

                db.freeStatement();
                db.isPending();
            }
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

    public void deactivate(Catalog _cat, boolean _bcommit) {
        if (Catalog.isDryRun()) {
            return;
        }

        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        FeatureDetailId fdid = this.getFeatureDetailId();
        GeneralAreaMapItem gami = fdid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();
        String strFeatEntityType = fdid.getFeatEntityType();
        int iFeatEntityID = fdid.getFeatEntityID();
        String strItemEntityType = fdid.getItemEntityType();
        int iItemEntityID = fdid.getItemEntityID();
        D.ebug(this, D.EBUG_DETAIL, "put method");
        try {

            Iterator it = this.values().iterator();
            while (it.hasNext()) {
                DetailFragment df = (DetailFragment) it.next();
                D.ebug(this, D.EBUG_DETAIL, "put detail fragment " + df.toString());
                db.callGBL8984(rets, strEnterprise, strCountryCode, strLanguageCode, iNLSID, strCountryList, strFeatEntityType,
                    iFeatEntityID, strItemEntityType, iItemEntityID, df.getAttributeCode(), df.getExtAttributeCode(),
                    getFirst254Char(df.getExtAttributeCode(), df.getAttributeValue()), df.getUnitOfMeasure(),
                    (df.isDerived() ? 1 : 0), 0);

                if (_bcommit) {
                    db.commit();
                }

                db.freeStatement();
                db.isPending();
            }
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

    /* (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatItem#merge(COM.ibm.eannounce.catalog.CatItem)
     */
    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub
    }

    public void setAttribute(String _strTag, Object _oAtt) {
        return;
    }

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

    public void generateXMLFragment(XMLWriter _xml) {
        try {

            Iterator it = this.values().iterator();
            FeatureDetailId fdid = this.getFeatureDetailId();
            while (it.hasNext()) {
                DetailFragment df = (DetailFragment) it.next();
                _xml.writeEntity("DETAILVALUE");
                _xml.writeEntity("PARENTID");
                _xml.write(fdid.getItemEntityType() + fdid.getItemEntityID());
                _xml.endEntity();
                _xml.writeEntity("ATTRCODE");
                _xml.write(df.getAttributeCode());
                _xml.endEntity();
                _xml.writeEntity("EXTERNALATTCODE");
                _xml.write(df.getExtAttributeCode());
                _xml.endEntity();
                _xml.writeEntity("ATTRVALUE");
                _xml.write(df.getAttributeValue());
                _xml.endEntity();
                _xml.writeEntity("ATTUNITOFMEASURE");
                _xml.write(df.getUnitOfMeasure());
                _xml.endEntity();
                _xml.writeEntity("DERIVED");
                _xml.write(df.isDerived() ? "Y" : "N");
                _xml.endEntity();
                _xml.endEntity();
            }

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * getSmc
     * @return
     */
    public SyncMapCollection getSmc() {
        return m_smc;
    }

    /**
     * setSmc
     * @param collection
     */
    public void setSmc(SyncMapCollection collection) {
        m_smc = collection;
    }

    public final boolean hasSyncMapCollection() {
        return m_smc != null;
    }

    public void setAttributes(EntityItem _ei, GeneralAreaMapItem _gami) {

        for (int i = 0; i < _ei.getAttributeCount(); i++) {
            EANAttribute att = _ei.getAttribute(i);
            EANMetaAttribute ma = att.getMetaAttribute();
            DetailFragmentId dfid = new DetailFragmentId(att.getAttributeCode(), _gami);
            DetailFragment df = new DetailFragment(dfid);
            df.setExtAttributeCode(att.getAttributeCode());
            StringTokenizer st = new StringTokenizer(att.toString(), "\n");
            StringBuffer sb = new StringBuffer();
            while (st.hasMoreTokens()) {
                String str = st.nextToken();
                str = str.replace('*', ' ').trim();
                sb.append(str);
                sb.append(";");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            df.setAttributeValue(sb.toString());
            df.setValFrom(att.getValFrom());
            df.setActive(true);
            df.setDerived(ma.isDerived() ? true : false);
            m_AttCollection.put(att.getAttributeCode(), sb.toString());
            this.put(df);
        }
    }


}
