/*
 * Created on Jun 8, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

/**
 * FeatureId
 *
 * Holds the information for Features
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FeatureId extends CatId {

	private String m_strFeatEntityType = null;
	private int m_iFeatEntityID = 0;
//	private String m_strItemEntityType = null;
//	private int m_iItemEntityID = 0;

	/**
	 * FeatureId
	 *
	 * @param _strFeatEntityType
	 * @param _iFeatEntityID
	 * @param _strItemEntityType
	 * @param _iItemEntityID
	 * @param _gami
	 */
//public FeatureId(String _strFeatEntityType, int _iFeatEntityID, String _strItemEntityType, int _iItemEntityID, GeneralAreaMapItem _gami) {
	public FeatureId(String _strFeatEntityType, int _iFeatEntityID, GeneralAreaMapItem _gami) {
//		super(_strFeatEntityType + ":" + _iFeatEntityID + ":" + _strItemEntityType + ":" + _iItemEntityID, _gami);
		super(_strFeatEntityType + ":" + _iFeatEntityID, _gami);
		setFeatEntityType(_strFeatEntityType);
		setFeatEntityID(_iFeatEntityID);
//		setItemEntityType(_strItemEntityType);
//		setItemEntityID(_iItemEntityID);
	}

	/**
	 * FeatureId
	 *
	 * @param _strFeatEntityType
	 * @param _iFeatEntityID
	 * @param _strItemEntityType
	 * @param _iItemEntityID
	 * @param _gami
	 * @param _fcid
	 */
//	public FeatureId(String _strFeatEntityType, int _iFeatEntityID, String _strItemEntityType, int _iItemEntityID, GeneralAreaMapItem _gami, FeatureCollectionId _fcid) {
	public FeatureId(String _strFeatEntityType, int _iFeatEntityID, GeneralAreaMapItem _gami, FeatureCollectionId _fcid) {
//		super(_strFeatEntityType + ":" + _iFeatEntityID + ":" + _strItemEntityType + ":" + _iItemEntityID, _gami, _fcid);
		super(_strFeatEntityType + ":" + _iFeatEntityID, _gami, _fcid);
		setFeatEntityType(_strFeatEntityType);
		setFeatEntityID(_iFeatEntityID);
//		setItemEntityType(_strItemEntityType);
//		setItemEntityID(_iItemEntityID);
	}
	/**
	 * getFeatEntityID
	 *
	 * @return
	 */
	public int getFeatEntityID() {
		return m_iFeatEntityID;
	}

	/**
	 * setFeatEntityID
	 *
	 * @param _i
	 */
	protected void setFeatEntityID(int _i) {
		m_iFeatEntityID = _i;
	}

	/**
	 * getFeatEntityType
	 *
	 * @return
	 */
	public String getFeatEntityType() {
		return m_strFeatEntityType;
	}

	/**
	 * setFeatEntityType
	 *
	 * @param _string
	 */
	protected void setFeatEntityType(String _string) {
		m_strFeatEntityType = _string;
	}

	/**
	 * getItemEntityType
	 *
	 * @return
	 */
//	public String getItemEntityType() {
//		return m_strItemEntityType;
//	}

	/**
	 * setItemEntityType
	 *
	 * @param _string
	 */
//	protected void setItemEntityType(String _string) {
//		m_strItemEntityType = _string;
//	}

	/**
	 * getItemEntityID
	 *
	 * @return
	 */
//	public int getItemEntityID() {
//		return m_iItemEntityID;
//	}

	/**
	 * setItemEntityID
	 *
	 * @param _i
	 */
//	protected void setItemEntityID(int _i) {
//		m_iItemEntityID = _i;
//	}

	/**
	 * test tool
	 */
	public final String dump(boolean _b) {
		if (_b) {
			return toString();
		} else {
			return toString();
		}
	}

	/**
	 * FeatureId
	 *
	 * @return
	 */
	public final FeatureCollectionId getFeatureCollectionId() {
		return (FeatureCollectionId) getCollectionId();
	}

	/**
	 * FeatureId
	 *
	 * @return
	 */
	public final boolean isHardwareFeature() {
		return getFeatEntityType().equals(FeatureCollection.FEATURE_ENTITYTYPE);
	}

	/**
	 * FeatureId
	 *
	 * @return
	 */
	public final boolean isSoftwareFeature() {
		return getFeatEntityType().equals(FeatureCollection.SWFEATURE_ENTITYTYPE);
	}
}
