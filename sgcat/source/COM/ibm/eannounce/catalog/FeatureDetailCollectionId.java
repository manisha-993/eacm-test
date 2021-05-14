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


public class FeatureDetailCollectionId extends CollectionId {

	private FeatureId m_fid = null;

	/**
	 * @param _str
	 * @param _gami
	 * @param int _iCollectionType
	 */
	public FeatureDetailCollectionId(FeatureId _fid, int _iSource, int _iType, CatalogInterval _int) {
		super(_fid.toString(), _fid.getGami(), _iSource, _iType, _int);
        m_fid = _fid;
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
	 */
	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
/*
		try {
		    Database db = new Database();
		    String strEnterprise = args[0];
		    String strWWEntityType = args[1];
		    int iWWEntityID = Integer.parseInt(args[2]);
		    String strGeneralArea = args[3];

			GeneralAreaMap gam = Catalog.getGam(strEnterprise);
			GeneralAreaMapGroup gamp = gam.lookupGeneralArea(strGeneralArea);

			Enumeration en = gamp.elements();

			if (!en.hasMoreElements()) {
				System.out.println("no gami to find!!!");
			}
			while (en.hasMoreElements()) {
				GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
				System.out.println(gami);
				WorldWideProductId wwpid = new WorldWideProductId(strWWEntityType,iWWEntityID,gami);
				FeatureDetailCollectionId pcid = new FeatureDetailCollectionId(wwpid,CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, null);
				FeatureCollection pc = new FeatureCollection(db, pcid);
			    // fill out product data completely
			    Iterator it = pc.values().iterator();
				while (it.hasNext()) {
				    Feature feat = (Feature)it.next();
					feat.get(db);
		        }
				System.out.println(pc.dump(false));
			}

			db.commit();
			db.close();
			db = null;
		} catch (MiddlewareException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

	public final FeatureId getFeatureId() {
		return m_fid;
	}

}
