/*
 * Created on Mar 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package COM.ibm.eannounce.catalog;

import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANMetaFoundation;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;


public class FeatureDetailId extends CatId {

    private String m_strFeatEntityType = null;
    private int m_iFeatEntityID = 0;
    private String m_strItemEntityType = null;
    private int m_iItemEntityID = 0;

	public FeatureDetailId(String _strFeatEntityType, int _iFeatEntityID, String _strItemEntityType, int _iItemEntityID, GeneralAreaMapItem _gai, FeatureDetailCollectionId _fdcid) {
        super(_strFeatEntityType + ":" + _iFeatEntityID + ":" + _strItemEntityType + ":" + _iItemEntityID + ":" + _gai, _gai, _fdcid);
        setFeatEntityType(_strFeatEntityType);
        setFeatEntityID(_iFeatEntityID);
        setItemEntityType(_strItemEntityType);
        setItemEntityID(_iItemEntityID);
    }

    public static void main(String[] args) {
    }

    public int getFeatEntityID() {
        return m_iFeatEntityID;
    }

    protected void setFeatEntityID(int _i) {
        m_iFeatEntityID = _i;
    }

    public String getFeatEntityType() {
        return m_strFeatEntityType;
    }

    protected void setFeatEntityType(String _string) {
        m_strFeatEntityType = _string;
    }

    public String getItemEntityType() {
        return m_strItemEntityType;
    }

    protected void setItemEntityType(String _string) {
        m_strItemEntityType = _string;
    }

    public int getItemEntityID() {
        return m_iItemEntityID;
    }

    protected void setItemEntityID(int _i) {
        m_iItemEntityID = _i;
    }

	public final String dump(boolean _b) {
		return toString();
	}

	public final FeatureDetailCollectionId getFeatureDetailCollectionId() {
		return (FeatureDetailCollectionId) getCollectionId();
	}

}
