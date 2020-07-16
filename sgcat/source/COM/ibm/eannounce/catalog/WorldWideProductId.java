/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorldWideProductId
    extends CatId {

    private String EntityType = null;
    private int EntityID = 0;

    //private WorldWideProductCollectionId WWProductCollectionId = null;

    /**
     * WordWideProductId
     * A World Wide Product Identifier is uniquly defined as Enterprise, EntityType, EntityID, NLSID
     * @param _str
     */
    public WorldWideProductId(String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami) {
        super(_iEntityID + ":" + _strEntityType, _gami);
        setEntityType(_strEntityType);
        setEntityID(_iEntityID);
    }

    /**
     * WordWideProductId
     * A World Wide Product Identifier is uniquly defined as Enterprise, EntityType, EntityID, NLSID
     * @param _str
     */
    public WorldWideProductId(String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami, WorldWideProductCollectionId _colid) {
        super(_strEntityType + ":" + _iEntityID, _gami, _colid);
        this.setEntityType(_strEntityType);
        this.setEntityID(_iEntityID);
        //
        // Lets record how this guy got created
        //
        //this.setWWProductCollectionId(_colid);
    }

    /**
     * @return
     */
    public int getEntityID() {
        return EntityID;
    }

    /**
     * @return
     */
    public String getEntityType() {
        return EntityType;
    }

    /**
     * @param i
     */
    public void setEntityID(int i) {
        EntityID = i;
    }

    /**
     * @param string
     */
    public void setEntityType(String _s) {
        EntityType = _s;
        //if(_s != null && _s.equals("MODEL")) {
        //	EntityType = "TM";
        //} else if(_s != null && _s.equals("WWSEO")) {
        //	EntityType = "SEO";
        //} else {
        //    EntityType = _s;
        //}
    }

    public final String dump(boolean _b) {
        if (_b) {
            return toString();
        }
        else {
            return toString();
        }
    }

    public WorldWideProductCollectionId getWWProductCollectionId() {
        return (WorldWideProductCollectionId) getCollectionId();
    }

    public void setWWProductCollectionId(WorldWideProductCollectionId id) {
        setCollectionId(id);
    }

    /**
     * isSEO
     *
     * @return
     */
    public boolean isSEO() {
        return getEntityType().equals(WorldWideProduct.SEO_ENTITY_GROUP);
    }

    /**
     * isTMF
     *
     * @return
     */
    public boolean isTMF() {
        return getEntityType().equals(WorldWideProduct.TMF_ENTITY_GROUP);
        //return getEntityType().equals("TM");
    }
}

/*
 * $Log: WorldWideProductId.java,v $
 * Revision 1.2  2011/05/05 11:21:35  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:34  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.22  2005/10/26 18:05:14  dave
 * ok.. family page for collateral
 *
 * Revision 1.21  2005/09/30 21:24:25  gregg
 * fix
 *
 * Revision 1.20  2005/09/30 20:53:35  gregg
 * back to MODEL/WWSEO phew
 *
 * Revision 1.19  2005/09/29 22:25:41  gregg
 * npe fix
 *
 * Revision 1.18  2005/09/29 22:14:51  gregg
 * somne debugs and such
 *
 * Revision 1.17  2005/09/29 21:24:26  gregg
 * fixcx
 *
 * Revision 1.16  2005/09/29 21:19:25  gregg
 * mapping TM<->MODEL, SEO<->WWSEO
 *
 * Revision 1.15  2005/06/04 23:15:02  dave
 * just cleaning up abit
 *
 * Revision 1.14  2005/06/01 06:31:17  dave
 * ok.. lets see if we can do some ProdStruct Damage
 *
 * Revision 1.13  2005/06/01 03:39:02  dave
 * some syntax fix
 *
 * Revision 1.12  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.11  2005/05/27 22:52:33  dave
 * lets see if we can get something from the PDH
 *
 * Revision 1.10  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.9  2005/05/26 00:06:06  dave
 * adding put to design by contract
 *
 * Revision 1.8  2005/05/24 02:44:41  dave
 * CollectionId passed to ItemId for Product
 *
 * Revision 1.7  2005/05/23 14:28:54  dave
 * simplifying CatId constructors
 *
 * Revision 1.6  2005/05/23 14:14:32  dave
 * adding source, type, and interval sigs to keys
 * adding getGami to Catalog statics
 *
 * Revision 1.5  2005/05/23 01:22:32  dave
 * ok.. more changes.. trying to load a collection here
 *
 * Revision 1.4  2005/05/22 23:04:36  dave
 * Added CollectionId
 * addind Catalog Interval
 * Placed enterprise in the Gami
 *
 * Revision 1.3  2005/05/19 16:03:23  dave
 * fixing syntax
 *
 * Revision 1.2  2005/05/18 01:06:00  dave
 * trying to write a main test
 *
 * Revision 1.1  2005/05/17 23:43:29  dave
 * fixed class misspelling
 *
 * Revision 1.3  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */
