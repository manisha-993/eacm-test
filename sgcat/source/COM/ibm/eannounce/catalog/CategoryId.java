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
public class CategoryId extends CatId {

	private String EntityType = null;
	private int EntityID = 0;
	
   /**
     * WordWideProductId
     * A World Wide Product Identifier is uniquly defined as Enterprise, EntityType, EntityID, NLSID
     * @param _str
     */
    public CategoryId( String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami) {
        super(_iEntityID + ":" + _strEntityType , _gami);
        setEntityType(_strEntityType);
        setEntityID(_iEntityID);
    }

	/**
	   * WordWideProductId
	   * A World Wide Product Identifier is uniquly defined as Enterprise, EntityType, EntityID, NLSID
	   * @param _str
	   */
	  public CategoryId( String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami, CategoryCollectionId _colid) {
		  super(_strEntityType + ":" + _iEntityID, _gami, _colid);
		  this.setEntityType(_strEntityType);
		  this.setEntityID(_iEntityID);
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
	public void setEntityType(String string) {
		EntityType = string;
	}

	public final String dump(boolean _b) {
		if (_b) {
			return toString();
		} else {
			return toString();
		}
	}

	public CategoryCollectionId getCategoryCollectionId() {
		return (CategoryCollectionId)getCollectionId();
	}

	public void setCategoryCollectionId(CategoryCollectionId id) {
		setCollectionId(id);
	}

}

/*
 * $Log: CategoryId.java,v $
 * Revision 1.2  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:09  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.1  2005/10/04 20:38:02  dave
 * new classes
 *
 */
