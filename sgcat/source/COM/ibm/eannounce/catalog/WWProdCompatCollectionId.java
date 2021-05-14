/*
 * Created on May 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;




public class WWProdCompatCollectionId extends CollectionId {

	private WorldWideProductId m_wwpid = null;

	public WWProdCompatCollectionId(WorldWideProductId _wwpid, int _iSource, int _iType, CatalogInterval _int) {
		super(_wwpid.toString(), _wwpid.getGami(), _iSource, _iType, _int);
		m_wwpid = _wwpid;
  	}

	public WWProdCompatCollectionId(CatalogInterval _cati,
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

	public final WorldWideProductId getWorldWideProductId() {
		return m_wwpid;
	}

	public final boolean hasWorldWideProductId() {
		return m_wwpid != null;
	}

	public final String getEntityType() {
		if (hasWorldWideProductId()) {
			return m_wwpid.getEntityType();
		}
		return "";
	}

	public final int getEntityId() {
		if (hasWorldWideProductId()) {
			return m_wwpid.getEntityID();

		}
		return 0;
	}
}
