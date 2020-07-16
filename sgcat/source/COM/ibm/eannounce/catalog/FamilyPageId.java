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
public class FamilyPageId extends CatId {

    private String EntityType = null;
    private int EntityID = 0;

    public FamilyPageId(String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami) {
        super(_iEntityID + ":" + _strEntityType, _gami);
        setEntityType(_strEntityType);
        setEntityID(_iEntityID);
    }

    public FamilyPageId(String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami, FamilyPageCollectionId _colid) {
        super(_strEntityType + ":" + _iEntityID, _gami, _colid);
        this.setEntityType(_strEntityType);
        this.setEntityID(_iEntityID);
    }

    public int getEntityID() {
        return EntityID;
    }

    public String getEntityType() {
        return EntityType;
    }

    public void setEntityID(int i) {
        EntityID = i;
    }

    public void setEntityType(String _s) {
        EntityType = _s;
    }

    public final String dump(boolean _b) {
        if (_b) {
            return toString();
        }
        else {
            return toString();
        }
    }

    public FamilyPageCollectionId getFamilyPageCollectionId() {
        return (FamilyPageCollectionId) getCollectionId();
    }

    public void setFamilyPageCollectionId(FamilyPageCollectionId id) {
        setCollectionId(id);
    }

    /**
     * isFMLY
     *
     * @return
     */
    public boolean isFMLY() {
        return getEntityType().equals(FamilyPage.FMLY_ENTITY_GROUP);
    }

    /**
     * isSER
     *
     * @return
     */
    public boolean isSER() {
        return getEntityType().equals(FamilyPage.SER_ENTITY_GROUP);
    }
}

/*
 * $Log: FamilyPageId.java,v $
 * Revision 1.2  2011/05/05 11:21:33  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:16  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.1  2005/10/26 18:05:07  dave
 * ok.. family page for collateral
 *
 *
 */
