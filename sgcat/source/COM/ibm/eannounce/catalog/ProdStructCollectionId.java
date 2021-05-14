/*
 * Created on May 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: ProdStructCollectionId.java,v $
 * Revision 1.2  2011/05/05 11:21:31  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:25  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:30  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.5  2005/09/13 20:08:54  gregg
 * add CatItem into ProdStructCollectionId Constructor
 *
 * Revision 1.4  2005/09/13 19:48:14  gregg
 * setInterval in Product Constructor
 *
 * Revision 1.3  2005/09/13 16:09:29  gregg
 * hasProductId
 *
 * Revision 1.2  2005/06/01 06:31:17  dave
 * ok.. lets see if we can do some ProdStruct Damage
 *
 * Revision 1.1  2005/05/24 22:57:18  gregg
 * initial load
 *
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

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProdStructCollectionId extends CollectionId {

	public static final int BY_LOCENTITYTYPE_LOCENTITYID = 1;

	private int m_iCollectionType = -1;
	private ProductId m_pid = null;
	private WorldWideProductId m_wpid = null;

	/**
	 * @param _str
	 * @param _gami
	 * @param int
	 */
	public ProdStructCollectionId(ProductId _pid, int _iSource, int _iType, int _iCollectionType, CatalogInterval _cati) {
		super(_pid.toString(), _pid.getGami(), _iSource, _iType);
        m_iCollectionType = _iCollectionType;
        this.setInterval(_cati);
        m_pid = _pid;
	}

	/**
	 * @param _str
	 * @param _gami
	 * @param int
	 */
	public ProdStructCollectionId(WorldWideProductId _pid, int _iSource, int _iType, CatalogInterval _cati) {
		super(_pid.toString(), _pid.getGami(), _iSource, _iType);
		this.setInterval(_cati);
		m_wpid = _pid;
	}

	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
        // let's try and build one of these all the way up in ProductCollectionId...
	}

	/**
	 * @return what kind of ProdStructCollection are we gathering?
	 */
    public final int getCollectionType() {
		return m_iCollectionType;
	}

	public final ProductId getProductId() {
		return m_pid;
	}

	public final boolean hasProductId() {
		return m_pid != null;
	}

	public final WorldWideProductId getWorldWideProductId() {
		return m_wpid;
	}

	public final boolean hasWorldWideProductId() {
		return m_wpid != null;
	}
}
