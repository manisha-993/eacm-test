package COM.ibm.eannounce.catalog;
import java.util.HashMap;
import java.util.Set;

public class Warranty extends CatItem {

	private String m_strWarrantyPeriod = null;
	private String m_strWarrantyPeriodFlagCode = null;
	private String m_strWarrantyType = null;
	private String m_strWarrantyTypeFlagCode = null;
	private HashMap m_AttCollection = new HashMap();

    /**
     * Warranty
     * @param _cid
     */
    public Warranty(CatId _cid) {
        super(_cid);
        // TODO Auto-generated constructor stub
    }

    /**
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.Collateral#put(COM.ibm.eannounce.catalog.Catalog, boolean)
     */
    public void put(Catalog _cat, boolean _bool) {
		if (Catalog.isDryRun()) {
			return;
		}

    }
    /**
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.Collateral#get(COM.ibm.eannounce.catalog.Catalog)
     */
    public void get(Catalog _cat) {
        // TODO Auto-generated method stub

    }
    /**
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatItem#merge(COM.ibm.eannounce.catalog.CatItem)
     */
    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub

    }
    /**
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatItem#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String _strTag, Object _oAtt) {
        // TODO Auto-generated method stub

    }
    /**
     *  (non-Javadoc)
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
     * @see COM.ibm.eannounce.catalog.CatObject#toString()
     */
    public String toString() {
        // TODO Auto-generated method stub
        return null;
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
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.Databaseable#getReferences(COM.ibm.eannounce.catalog.Catalog, int)
     */
    public void getReferences(Catalog _cat, int _icase) {
        // TODO Auto-generated method stub

    }

    //Warranty(Database, Profile, ENTERPRISE, COUNTRYCODE, LANGUAGECODE, NLSID, COLLENTITYTYPE, COLLENTITYID)
    //Warranty(Database, Profile, WarrantyId)
    //Warranty retrieveFromDatabase(Database, Profile, WarrantyId)
    //Warranty(ENTERPRISE, COUNTRYCODE, LANGUAGECODE, NLSID, GENAREANAME, GENAREANAME_FC, COLLENTITYTYPE, COLLENTITYID, STATUS, STATUS_FC, PUBLISHFROM, PUBLISHTO, WARRANTYPERIOD, WARRANTYPERIOD_FC, WARRANTYTYPE, WARRANTYTYPE_FC, VALFROM, VALTO, ISACTIVE)
    //WarrantyId getId()
    //WarrantyGroup getWarrantys(Database, Profile, Product)
    //WarrantyGroup getWarrantys(Database, Profile, ProductId)
    //saveToDatabase(Database, Profile)
    //boolean equals(Object)
    //XMLWriter outputAsXML()
    //StringBuffer outputAsXML()
    //toString
    //dump
}

/*
 * $Log: Warranty.java,v $
 * Revision 1.2  2011/05/05 11:21:31  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:30  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.12  2005/08/08 20:47:16  tony
 * Added getAttribute logic
 *
 * Revision 1.11  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.10  2005/06/13 04:35:34  dave
 * ! needs to be not !
 *
 * Revision 1.9  2005/06/13 04:02:06  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.8  2005/06/08 23:40:16  dave
 * ok honor a final clause
 *
 * Revision 1.7  2005/06/08 13:21:21  dave
 * testing waters to see if structures make sense outside of WWProductId
 *
 * Revision 1.6  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */
