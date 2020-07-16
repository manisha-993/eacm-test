//
//$Log: ProdStructId.java,v $
//Revision 1.2  2011/05/05 11:21:33  wendy
//src from IBMCHINA
//
//Revision 1.1.1.1  2007/06/05 02:09:25  jingb
//no message
//
//Revision 1.1.1.1  2006/03/30 17:36:30  gregg
//Moving catalog module from middleware to
//its own module.
//
//Revision 1.7  2005/11/09 20:55:36  dave
//adding CONFQTY and defaults
//
//Revision 1.6  2005/09/13 16:28:03  gregg
//properly building ProdStructs for Products
//
//Revision 1.5  2005/06/04 22:22:57  dave
//ok.. lets try to dump out the ProdStruct XML
//for now.. lets embed it in the other XML
//
//Revision 1.4  2005/06/02 20:34:09  gregg
//fixing the parent/child collection reference mayhem
//
//Revision 1.3  2005/06/01 06:31:17  dave
//ok.. lets see if we can do some ProdStruct Damage
//
//Revision 1.2  2005/05/25 17:46:24  gregg
//compile fixes
//
//Revision 1.1  2005/05/25 17:36:46  gregg
//going for ProdStruct objects
//
//

package COM.ibm.eannounce.catalog;

public class ProdStructId
    extends CatId {

    private String m_strProdEntityType = null;
    private int m_iProdEntityId = -1;

    private String m_strStructEntityType = null;
    private int m_iStructEntityId = 0;

    private String m_strFeatEntityType = null;
    private int m_iFeatEntityId = -1;

    private String m_strBridgeEntityType = null;
    private int m_iBridgeEntityId = -1;

    public ProdStructId(ProductId _pid, String _strStructEntityType, int _iStructEntityID, String _strFeatEntityType,
                        int _iFeatEntityId, GeneralAreaMapItem _gami, ProdStructCollectionId _pscid) {
        super(_pid.getEntityType() + ":" + _pid.getEntityId() + ":" + _strFeatEntityType + ":" + _iFeatEntityId, _gami);
        setProdEntityType(_pid.getEntityType());
        setProdEntityId(_pid.getEntityId());
        setFeatEntityType(_strFeatEntityType);
        setFeatEntityId(_iFeatEntityId);
        setStructEntityType(_strStructEntityType);
        setStructEntityId(_iStructEntityID);
        setCollectionId(_pscid);
    }

    public ProdStructId(WorldWideProductId _pid, String _strStructEntityType, int _iStructEntityID, String _strFeatEntityType,
                        int _iFeatEntityId, GeneralAreaMapItem _gami, ProdStructCollectionId _pscid) {
        super(_strStructEntityType + ":" + _iStructEntityID, _gami);
        setProdEntityType(_pid.getEntityType());
        setProdEntityId(_pid.getEntityID());
        setFeatEntityType(_strFeatEntityType);
        setFeatEntityId(_iFeatEntityId);
        setStructEntityType(_strStructEntityType);
        setStructEntityId(_iStructEntityID);
        setCollectionId(_pscid);
    }

    public String toString() {
        return super.toString();
    }

    public String dump(boolean _b) {
        return "TBD";
    }

    //
    // getters
    //

    public final String getProdEntityType() {
        return m_strProdEntityType;
    }

    public final int getProdEntityId() {
        return m_iProdEntityId;
    }

    public final String getFeatEntityType() {
        return m_strFeatEntityType;
    }

    public final int getFeatEntityId() {
        return m_iFeatEntityId;
    }

    //
    // setters
    //

    public final void setProdEntityType(String _s) {
        m_strProdEntityType = _s;
    }

    public final void setProdEntityId(int _i) {
        m_iProdEntityId = _i;
    }

    public final void setFeatEntityType(String _s) {
        m_strFeatEntityType = _s;
    }

    public final void setFeatEntityId(int _i) {
        m_iFeatEntityId = _i;
    }

    /**
     * getStructEntityId
     * @return
     */
    public int getStructEntityId() {
        return m_iStructEntityId;
    }

    /**
     * getStructEntityType
     * @return
     */
    public String getStructEntityType() {
        return m_strStructEntityType;
    }

    /**
     * setStructEntityId
     * @param i
     */
    public void setStructEntityId(int i) {
        m_iStructEntityId = i;
    }

    /**
     * setStructEntityType
     * @param string
     */
    public void setStructEntityType(String string) {
        m_strStructEntityType = string;
    }

    public ProdStructCollectionId getProdStructCollectionId() {
        return (ProdStructCollectionId) getCollectionId();
    }

    public boolean hasBridgeEntity() {
        return this.m_strBridgeEntityType != null;
    }

    public String getBridgeEntityType() {
        return m_strBridgeEntityType;
    }

    public int getBridgeEntityId() {
        return m_iBridgeEntityId;
    }

    public void setBridgeEntityType(String string) {
        m_strBridgeEntityType = string;
    }

    public void setBridgeEntityId(int i) {
        m_iBridgeEntityId = i;
    }
}
