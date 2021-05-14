/*
 * Created on Jun 12, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorldWideAttributeCollectionId extends CollectionId {

    private WorldWideProductId m_wpid = null;

    /**
     * WorldWideAttributeCollectionId
     * @param _str
     * @param _gami
     * @param _iSource
     * @param _iType
     * @param _int
     */
    public WorldWideAttributeCollectionId(String _str, GeneralAreaMapItem _gami, int _iSource, int _iType, CatalogInterval _int) {
        super(_str, _gami, _iSource, _iType, _int);
        // TODO Auto-generated constructor stub
    }

    /**
     * WorldWideAttributeCollectionId
     * @param _str
     * @param _gami
     * @param _iSource
     * @param _iType
     */
    public WorldWideAttributeCollectionId(WorldWideProductId _pid, int _iSource, int _iType, CatalogInterval _cati) {
        super(_pid.toString(), _pid.getGami(), _iSource, _iType);
        this.setInterval(_cati);
        m_wpid = _pid;
    }

    /**
     * WorldWideAttributeCollectionId
     * @param _str
     * @param _gami
     */
    public WorldWideAttributeCollectionId(String _str, GeneralAreaMapItem _gami) {
        super(_str, _gami);
        // TODO Auto-generated constructor stub
    }

    /**
     * WorldWideAttributeCollectionId
     * @param _cati
     * @param _gami
     * @param _iSource
     * @param _iType
     */
    public WorldWideAttributeCollectionId(CatalogInterval _cati, GeneralAreaMapItem _gami, int _iSource, int _iType) {
        super(_cati, _gami, _iSource, _iType);
        // TODO Auto-generated constructor stub
    }

    /**
     * WorldWideAttributeCollectionId
     * @param _str
     * @param _cati
     * @param _gami
     * @param _iSource
     * @param _iType
     */
    public WorldWideAttributeCollectionId(String _str, CatalogInterval _cati, GeneralAreaMapItem _gami, int _iSource, int _iType) {
        super(_str, _cati, _gami, _iSource, _iType);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
    }

    public final WorldWideProductId getWorldWideProductId() {
        return m_wpid;
    }

    public final boolean hasWorldWideProductId() {
        return m_wpid != null;
    }
}
