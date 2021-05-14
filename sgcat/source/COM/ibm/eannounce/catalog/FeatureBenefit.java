/*
 * Created on Jun 8, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Set;

import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * FeatureBenefit
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FeatureBenefit extends Collateral {

    /**
     */
    public static final String FB_FBSTMT = "FBSTMT";
    private HashMap m_AttCollection = new HashMap();

    private String FBSTMT = null;

    /**
     * FeatureBenefit
     * @param _cid
     */
    public FeatureBenefit(CatId _cid) {
        super(_cid);
        // TODO Auto-generated constructor stub
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatItem#getAttribute(java.lang.String)
     */
    public Object getAttribute(String _strTag) {
		if (m_AttCollection.containsKey(_strTag)) {
			return m_AttCollection.get(_strTag);
		} else {
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
     *  (non-Javadoc)
     */
    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub

    }

    /**
     *  (non-Javadoc)
     */
    public void setAttribute(String _strTag, Object _oAtt) {
        // TODO Auto-generated method stub

    }

	/**
	 *  (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.CatObject#toString()
	 */
	public String toString() {
		return this.getId().toString();
	}

    /**
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _breif) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public String getFBSTMT() {
        return FBSTMT;
    }

    /**
     * @param string
     */
    public void setFBSTMT(String string) {
        FBSTMT = string;
        m_AttCollection.put(FeatureBenefit.FB_FBSTMT,string);
    }

    /**
     * setAttributes
     *
     * @param _ei
     */
    public final void setAttributes(EntityItem _ei) {

        // lets go call the super
        super.setAttributes(_ei);

        EANTextAttribute tFB_FBSTMT = (EANTextAttribute) _ei.getAttribute(FeatureBenefit.FB_FBSTMT);

        if (tFB_FBSTMT != null) {
            this.setFBSTMT(tFB_FBSTMT.toString());
        }

    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.Databaseable#get(COM.ibm.eannounce.catalog.Catalog)
     */
    public void get(Catalog _cat) {

        Profile prof = _cat.getCatalogProfile();
        CollateralId colid = (CollateralId) this.getId();
        GeneralAreaMapItem gami = colid.getGami();
        CollateralCollectionId colcid = colid.getCollateralCollectionId();
        gami.getEnterprise();

        gami.getNLSID();
        CatalogInterval cati = colcid.getInterval();

        try {
            if (colcid.isFromCAT()) {
            } else if (colcid.isFromPDH() && colcid.isByInterval() && colcid.isFullImages()) {

                if (this.getSmc() == null) {
                    System.out.println("Cannot pull out of the PDH since there is no SycnMap for me.");
                    return;
                }
                prof.setEffOn(cati.getEndDate());
                prof.setValOn(cati.getEndDate());

                EntityItem eiFB = Catalog.getEntityItem(_cat, colid.getCollEntityType(), colid.getCollEntityID());
                this.setAttributes(eiFB);
                //
                // ok.. get it out of the list...
                //
                eiFB.getEntityGroup().resetEntityItem();
            }

        } finally {
            //TODO
        }

    }
    /**
     *  (non-Javadoc)
     *
     */
    public void put(Catalog _cat, boolean _bcommit) {
        if (Catalog.isDryRun()) {
            return;
        }
        super.putCommon(_cat, _bcommit);
        Database db = _cat.getCatalogDatabase();
        if (isActive()) {
            ReturnStatus rets = new ReturnStatus(-1);
            CollateralId colid = (CollateralId) this.getId();
            GeneralAreaMapItem gami = colid.getGami();

            String strEnterprise = gami.getEnterprise();
            String strCountryCode = gami.getCountry();
            String strLanguageCode = gami.getLanguage();
            String strCountryList = gami.getCountryList();
            int iNLSID = gami.getNLSID();
            String strColEntityType = colid.getCollEntityType();
            int iColEntityID = colid.getCollEntityID();

            try {
                db.callGBL8976(
                    rets,
                    strEnterprise,
                    strCountryList,
                    strColEntityType,
                    iColEntityID,
                    iNLSID,
                    (this.getFBSTMT() == null ? "" : this.getFBSTMT()));

                db.freeStatement();
                db.isPending();

            } catch (MiddlewareException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (_bcommit) {
            try {
                db.commit();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

/*
 * $Log: FeatureBenefit.java,v $
 * Revision 1.3  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:18  jingb
 * no message
 *
 * Revision 1.2  2006/04/06 21:18:40  gregg
 * nlsid for 8976
 *
 * Revision 1.1.1.1  2006/03/30 17:36:29  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.20  2005/08/16 16:52:58  tony
 * CatalogViewer
 *
 * Revision 1.19  2005/08/08 20:47:16  tony
 * Added getAttribute logic
 *
 * Revision 1.18  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.17  2005/06/22 19:28:20  dave
 * ok.. trying to add countryList to the mix for my tables
 *
 * Revision 1.16  2005/06/17 04:06:26  dave
 * more trace
 *
 * Revision 1.15  2005/06/17 02:57:57  dave
 * fixes
 *
 * Revision 1.14  2005/06/17 02:49:20  dave
 * going for blob
 *
 * Revision 1.13  2005/06/17 01:45:16  dave
 * new sp's to round out collarteral
 *
 * Revision 1.12  2005/06/13 04:35:33  dave
 * ! needs to be not !
 *
 * Revision 1.11  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.10  2005/06/08 23:40:16  dave
 * ok honor a final clause
 *
 * Revision 1.9  2005/06/08 23:28:07  dave
 * ok,  more collateral
 *
 * Revision 1.8  2005/06/08 20:31:07  dave
 * more changes to start picking up attributes for collateral
 *
 * Revision 1.7  2005/06/08 13:21:21  dave
 * testing waters to see if structures make sense outside of WWProductId
 *
 * Revision 1.6  2005/05/13 20:39:50  roger
 * Turn on logging in source
 *
 */
