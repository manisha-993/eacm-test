/*
 * Created on May 25, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;


/**
 * The PK for the object in the Catalog database [a lightweight version of CatItem]
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CatId extends CatObject {

	private String m_strBaseKey = null;
	private GeneralAreaMapItem m_gami = null;
	private CollectionId m_colid = null;

	/**
	 * Fully Loaded CatID
	 * 
	 * @param _str
	 * @param _gami
	 */
	public CatId(String _str, GeneralAreaMapItem _gami) {
		m_strBaseKey = _str;
		m_gami = _gami;

	}

	/**
	 * Here, we could be derived from a collectionId - so we need to know
	 * The intent of that guy and pull those key values from it.
	 *
	 * @param _str
	 * @param _gami
	 * @param _Id 
	 */
	public CatId(String _str, GeneralAreaMapItem _gami, CollectionId _Id) {
		m_strBaseKey = _str;
		m_gami = _gami;
		m_colid = _Id;

	}

	/**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object _obj) {
		return (_obj instanceof CatId && _obj.toString().equals(toString()));
	}

	/**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
		return m_strBaseKey + ":" + m_gami;
	}

	/**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
		return this.toString().hashCode();
	}

	/**
     * getGami
     *
     * @return
     */
    public final GeneralAreaMapItem getGami() {
		return m_gami;
	}

	/**
     * getCollectionId
     *
     * @return
     */
    public final CollectionId getCollectionId() {
		return m_colid;
	}
	
	/**
     * setCollectionId
     *
     * @param _colid
     */
    protected final void setCollectionId(CollectionId _colid) {
		m_colid = _colid;
	}
}

/*
 * $Log: CatId.java,v $
 * Revision 1.2  2011/05/05 11:21:33  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:09  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.19  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.18  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.17  2005/05/25 23:34:41  dave
 * adding the hashcode
 *
 * Revision 1.16  2005/05/23 16:13:53  dave
 * placing interval signatures back to the collection id
 *
 * Revision 1.15  2005/05/23 14:28:54  dave
 * simplifying CatId constructors
 *
 * Revision 1.14  2005/05/23 14:14:32  dave
 * adding source, type, and interval sigs to keys
 * adding getGami to Catalog statics
 *
 * Revision 1.13  2005/05/19 15:51:33  dave
 * adding Gami to the CatId (needed for everything
 * so i promoted to the key
 *
 * Revision 1.12  2005/05/13 20:47:53  roger
 * Telling about sameObject
 *
 * Revision 1.11  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */
