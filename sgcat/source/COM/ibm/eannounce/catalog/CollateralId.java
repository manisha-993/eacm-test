package COM.ibm.eannounce.catalog;

public class CollateralId extends CatId {
	
	private String m_strCollEntityType = null;
	private int m_iCollEntityID = -1;
	

    /**
     * CollateralId
     * @param _str
     * @param _gami
     */
    public CollateralId(String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami) {
        super(_iEntityID + _strEntityType, _gami);
        this.setCollEntityType(_strEntityType);
        this.setCollEntityID(_iEntityID);
    }

	/**
	 * CollateralId with Reference to its collection ID origins.
	 * @param _str
	 * @param _gami
	 */
	public CollateralId(String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami, CollateralCollectionId _colcid) {
		super(_iEntityID + _strEntityType, _gami, _colcid);
		this.setCollEntityType(_strEntityType);
		this.setCollEntityID(_iEntityID);
	}

    /**
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _breif) {
        // TODO Auto-generated method stub
        return null;
    }

	public final boolean isMarketMessage() {
		return getCollEntityType().equals(CollateralCollection.MM_ENTITYTYPE);
	}

	public final boolean isImage() {
		return getCollEntityType().equals(CollateralCollection.IMG_ENTITYTYPE);
	}

	public final boolean isFeatureBenefit() {
		return getCollEntityType().equals(CollateralCollection.FB_ENTITYTYPE);
	}

	/**
	 * @return
	 */
	public int getCollEntityID() {
		return m_iCollEntityID;
	}

	/**
	 * @return
	 */
	public String getCollEntityType() {
		return m_strCollEntityType;
	}

	/**
	 * @param i
	 */
	public void setCollEntityID(int i) {
		m_iCollEntityID = i;
	}

	/**
	 * @param string
	 */
	public void setCollEntityType(String string) {
		m_strCollEntityType = string;
	}
	
	public final CollateralCollectionId getCollateralCollectionId() {
		return (CollateralCollectionId)super.getCollectionId();
	}

}

/*
 * $Log: CollateralId.java,v $
 * Revision 1.2  2011/05/05 11:21:31  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:11  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.16  2005/06/16 18:23:21  dave
 * found a null pointer
 *
 * Revision 1.15  2005/06/16 16:46:05  dave
 * changes for Collateral
 *
 * Revision 1.14  2005/06/08 23:28:07  dave
 * ok,  more collateral
 *
 * Revision 1.13  2005/06/08 18:05:44  dave
 * CollateralCollection Build all
 *
 * Revision 1.12  2005/06/08 14:36:30  dave
 * ok.. expanding the contstruct
 *
 * Revision 1.11  2005/05/13 21:41:43  roger
 * Go away
 *
 * Revision 1.10  2005/05/13 21:35:13  roger
 * Not now
 *
 * Revision 1.9  2005/05/13 21:20:18  roger
 * See if this flies
 *
 * Revision 1.8  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */

