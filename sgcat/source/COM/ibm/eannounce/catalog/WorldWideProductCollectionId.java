/*
 * Created on May 22, 2005
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
public class WorldWideProductCollectionId extends CollectionId {
	
	private CollateralId m_CollateralId = null;
    private WorldWideComponentId m_WWcmpid = null;
	
	private boolean ByPartNumber = false;
	private boolean ByEntityIDRange = false;
	private String WWPartNumber = null;
	private int StartingEntityID = 0;
	private int EndingEntityID = 0;
		
	/**
	 * @param _str
	 * @param _gami
	 */
	public WorldWideProductCollectionId(String _strWWPartNumber, GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_strWWPartNumber, _gami, _iSource, _iType);
		this.setWWPartNumber(_strWWPartNumber);
		this.setByPartNumber(true);
	}

	public WorldWideProductCollectionId(
		int _iStartEntityID,
		int _iEndEntityID,
		GeneralAreaMapItem _gami,
		int _iSource,
		int _iType) {
		super(_iStartEntityID + ":" + _iEndEntityID, _gami, _iSource, _iType);
		this.setStartingEntityID(_iStartEntityID);
		this.setEndingEntityID(_iEndEntityID);
		this.setByEntityIDRange(true);
	}

	public WorldWideProductCollectionId(CatalogInterval _cati,
		GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_cati, _gami, _iSource, _iType);
	}

	public WorldWideProductCollectionId(CollateralId _colid, CatalogInterval _cati,
		 int _iSource, int _iType) {
		super(_cati, _colid.getGami(), _iSource, _iType);
		this.setCollateralId(_colid);
	}	
	
	public WorldWideProductCollectionId(WorldWideComponentId _WWcmpid, CatalogInterval _cati,
		 int _iSource, int _iType) {
		super(_cati, _WWcmpid.getGami(), _iSource, _iType);
		this.setWorldWideComponentId(_WWcmpid);
	}	

	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(toString());
		sb.append("\n:source:" + this.getSource());
		sb.append("\n:type" + this.getType());
		sb.append("\n:isfromcat" + this.isFromCAT());
		sb.append("\n:isfrompdh" + this.isFromPDH());
		sb.append("\n:isFullImages" + this.isFullImages());
		sb.append("\n:isNetChanges" + this.isNetChanges());
		sb.append("\n:isbyInterval" + this.isByInterval());
		
		return sb.toString();
		
	}

	public static void main(String[] args) {
	}
	/**
	 * @return
	 */
	public boolean isByPartNumber() {
		return ByPartNumber;
	}

	/**
	 * @return
	 */
	public String getWWPartNumber() {
		return WWPartNumber;
	}

	/**
	 * @param b
	 */
	public void setByPartNumber(boolean b) {
		ByPartNumber = b;
	}

	/**
	 * @param string
	 */
	public void setWWPartNumber(String string) {
		WWPartNumber = string;
	}

	/**
	 * @return
	 */
	public boolean isByEntityIDRange() {
		return ByEntityIDRange;
	}

	/**
	 * @return
	 */
	public int getEndingEntityID() {
		return EndingEntityID;
	}

	/**
	 * @return
	 */
	public int getStartingEntityID() {
		return StartingEntityID;
	}

	/**
	 * @param b
	 */
	public void setByEntityIDRange(boolean b) {
		ByEntityIDRange = b;
	}

	/**
	 * @param i
	 */
	public void setEndingEntityID(int i) {
		EndingEntityID = i;
	}

	/**
	 * @param i
	 */
	public void setStartingEntityID(int i) {
		StartingEntityID = i;
	}

	/**
	 * @return
	 */
	public CollateralId getCollateralId() {
		return m_CollateralId;
	}

	/**
	 * @param id
	 */
	public void setCollateralId(CollateralId _id) {
		m_CollateralId = _id;
	}
	
	public final boolean hasCollateralId() {
		return m_CollateralId != null;
	}

    public WorldWideComponentId getWorldWideComponentId() {
		return m_WWcmpid;
	}

	/**
	 * @param id
	 */
	public void setWorldWideComponentId(WorldWideComponentId _id) {
		m_WWcmpid = _id;
	}
	
	public final boolean hasWorldWideComponentId() {
		return m_WWcmpid != null;
	}

}
