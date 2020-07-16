package COM.ibm.eannounce.catalog;
public class WorldWideComponentId  extends CatId {

	public static final int FULL_IMAGES = 0;
	public static final int NET_CHANGES = 1;
	public static final int PDH_SOURCE = 2;
	public static final int CAT_SOURCE = 3;

	private String m_strWWEntityType = null;
	private int m_iWWEntityId = 0;
	private String m_strCmpntEntityType = null;
	private int m_iCmpntEntityId = 0;

	private boolean FromPDH = false;
	private boolean FromCAT = false;
	private boolean FullImages = false;
	private boolean NetChanges = false;
	private CatalogInterval Interval = null;
	private int Source = -1;
	private int Type = -1;

    private WorldWideComponentCollectionId m_WWcmpcid = null;


	/**
	   * WorldWideComponentId
       * A World Wide Component Identifier is uniquly defined as WWEntityid,WWEntityType,ComponentEntityid,ComponentEntityType,Enterprise
 	   * @param _str
	   */
	  public WorldWideComponentId(  String _strWWEntityType,int _iWWEntityID, String _strCmpntEntityType, int _iCmpntEntityID, GeneralAreaMapItem _gami, WorldWideComponentCollectionId _colid) {
		  super(_strWWEntityType + ":" + _iWWEntityID +":" +_strCmpntEntityType +":"+ _iCmpntEntityID , _gami, _colid);
			setWWEntityType(_strWWEntityType);
			setWWEntityID(_iWWEntityID);
			setCmpntEntityType(_strCmpntEntityType);
			setCmpntEntityID(_iCmpntEntityID);
			setWorldWideComponentCollectionId(_colid);
	  }


	public final String dump(boolean _b) {
		if (_b) {
			return toString();
		} else {
			return toString();
		}
	}
  /**
   * @return
   */
  public int getCmpntEntityID() {
    return m_iCmpntEntityId;
  }

  /**
   * @return
   */
  public int getWWEntityID() {
    return m_iWWEntityId;
  }

  /**
   * @return
   */
  public String getCmpntEntityType() {
    return m_strCmpntEntityType;
  }

  /**
   * @return
   */
  public String getWWEntityType() {
    return m_strWWEntityType;
  }

  /**
   * @param i
   */
  public void setCmpntEntityID(int _i) {
    m_iCmpntEntityId = _i;
  }

  /**
   * @param _i
   */
  public void setWWEntityID(int _i) {
    m_iWWEntityId = _i;
  }

  /**
   * @param string
   */
  public void setCmpntEntityType(String _s) {
    m_strCmpntEntityType = _s;
  }

  /**
   * @param _s
   */
  public void setWWEntityType(String _s) {
    m_strWWEntityType = _s;
  }


	public boolean isFromCAT() {
		return getSource() == WorldWideComponentId.CAT_SOURCE;
	}

	/**
	 * @return
	 */
	public boolean isFromPDH() {
		return getSource() == WorldWideComponentId.PDH_SOURCE;
	}

	/**
	 * @return
	 */
	public boolean isFullImages() {
		return getType() == WorldWideComponentId.FULL_IMAGES;
	}

	/**
	 * @return
	 */
	public CatalogInterval getInterval() {
		return Interval;
	}

	/**
	 * @return
	 */
	public boolean isNetChanges() {
		return getType() == WorldWideComponentId.NET_CHANGES;
	}

	/**
	 * @param interval
	 */
	public void setInterval(CatalogInterval interval) {
		Interval = interval;
	}

	public final boolean isByInterval() {
		return (this.getInterval() != null);
	}

	//  clone()
	// boolean sameObject() is on CatObject

	/**
	 * @return
	 */
	public int getSource() {
		return Source;
	}

	/**
	 * @return
	 */
	public int getType() {
		return Type;
	}

	/**
	 * @param i
	 */
	public void setSource(int i) {
		Source = i;
	}

	/**
	 * @param i
	 */
	public void setType(int i) {
		Type = i;
	}

    public void setWorldWideComponentCollectionId(WorldWideComponentCollectionId _wid)   {
        m_WWcmpcid = _wid;
    }

    public WorldWideComponentCollectionId getWorldWideComponentCollectionId()   {
        return m_WWcmpcid ;
    }

}

/*
 * $Log: WorldWideComponentId.java,v $
 * Revision 1.2  2011/05/05 11:21:35  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:31  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.5  2005/10/04 20:35:30  joan
 * fixes
 *
 * Revision 1.4  2005/07/12 23:03:09  bala
 * sync up
 *
 * Revision 1.3  2005/06/22 17:15:01  bala
 * more attributes
 *
 * Revision 1.2  2005/06/22 15:24:46  bala
 * added final attributes
 *
 * Revision 1.1  2005/05/23 20:19:05  bala
 * check in
 *
 */
