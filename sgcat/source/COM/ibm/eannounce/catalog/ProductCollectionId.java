/*
 * Created on May 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: ProductCollectionId.java,v $
 * Revision 1.2  2011/05/05 11:21:33  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:28  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.14  2005/06/08 23:31:18  gregg
 * more SyncMap/pull from PDH stuff
 *
 * Revision 1.13  2005/06/02 18:07:43  gregg
 * compile
 *
 * Revision 1.12  2005/06/02 18:01:24  gregg
 * s'more XML + dumps
 *
 * Revision 1.11  2005/05/29 00:25:30  dave
 * ok.. clean up and reorg to better support pulls from PDH
 *
 * Revision 1.10  2005/05/26 20:31:06  gregg
 * cleaning up source, type, case CONSTANTS.
 * let's also make sure we check for source in building objects within our collections.
 *
 * Revision 1.9  2005/05/26 07:32:30  dave
 * some minor syntax
 *
 * Revision 1.8  2005/05/25 21:04:27  gregg
 * compile fix
 *
 * Revision 1.7  2005/05/25 20:57:39  gregg
 * getFatAndDeep methods to traverse the tree.
 *
 * Revision 1.6  2005/05/25 17:38:26  gregg
 * remove main() method until ProdStruct is fleshed out
 *
 * Revision 1.5  2005/05/25 17:36:46  gregg
 * going for ProdStruct objects
 *
 * Revision 1.4  2005/05/24 22:53:53  gregg
 * variable name changes
 *
 * Revision 1.3  2005/05/24 22:30:44  gregg
 * dump in main
 *
 * Revision 1.2  2005/05/24 22:03:48  gregg
 * some fixes
 *
 * Revision 1.1  2005/05/24 21:45:10  gregg
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
public class ProductCollectionId extends CollectionId {

	public static final int BY_WWENTITYTYPE_WWENTITYID = 1;
	public static final int BY_WWPARTNUMBER = 2;

	private int m_iCollectionType = -1;
	private WorldWideProductId m_wwpid = null;

	/**
	 * @param _str
	 * @param _gami
	 * @param int _iCollectionType
	 */
	public ProductCollectionId(WorldWideProductId _wwpid, int _iSource, int _iType, int _iCollectionType) {
		super(_wwpid.toString(), _wwpid.getGami(), _iSource, _iType);
        m_iCollectionType = _iCollectionType;
        m_wwpid = _wwpid;
	}

	public ProductCollectionId(CatalogInterval _cati,GeneralAreaMapItem _gami, int _iSource, int _iType) {
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

		try {

		    String strEnterprise = args[0];
		    String strWWEntityType = args[1];
		    int iWWEntityID = Integer.parseInt(args[2]);
		    String strGeneralArea = args[3];

			Catalog cat = new Catalog(strEnterprise);
			GeneralAreaMap gam = cat.getGam();
			GeneralAreaMapGroup gamp = gam.lookupGeneralArea(strGeneralArea);

			Enumeration en = gamp.elements();

			if (!en.hasMoreElements()) {
				System.out.println("no gami to find!!!");
			}
			while (en.hasMoreElements()) {
				GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
				System.out.println(gami);
				WorldWideProductId wwpid = new WorldWideProductId(strWWEntityType,iWWEntityID,gami);
				ProductCollectionId pcid = new ProductCollectionId(wwpid
				                                                  ,CollectionId.CAT_SOURCE
				                                                  ,CollectionId.FULL_IMAGES
				                                                  ,ProductCollectionId.BY_WWENTITYTYPE_WWENTITYID);
				ProductCollection pc = new ProductCollection(cat,pcid);

			    pc.getFatAndDeep(cat,99);

				System.out.println(pc.dump(false));
				//
				XMLWriter xml = new XMLWriter();
				System.out.println("\n\n>>> HERE GOES SOME XML: <<<\n\n");
				pc.dumpXML(xml,true);

			}

			cat.close();
		} catch (MiddlewareException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return what kind of ProductCollection are we gathering?
	 */
    public final int getCollectionType() {
		return m_iCollectionType;
	}

	public final WorldWideProductId getWorldWideProductId() {
		return m_wwpid;
	}

}
