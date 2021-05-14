/*
 * Created on May 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;


public class FeatureCollectionId extends CollectionId {
	
	private ProdStructId m_psid = null;

	public FeatureCollectionId(ProdStructId _pid, int _iSource, int _iType, CatalogInterval _int) {
		super(_pid.toString(), _pid.getGami(), _iSource, _iType, _int);
		m_psid = _pid;
  	}

	public FeatureCollectionId(CatalogInterval _cati,
		GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_cati, _gami, _iSource, _iType);
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
	 */
	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		
	}
	
	public final ProdStructId getProdStructId() {
		return m_psid;
	}
	
	public final boolean hasProdStructId() {
		return m_psid != null;
	}	
}
