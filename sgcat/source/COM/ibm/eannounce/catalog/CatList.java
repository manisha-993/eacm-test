/*
 * Created on May 12, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

import java.util.Collection;
import java.util.Hashtable;

// A list of CatItems

/**
 * CatList
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CatList extends CatItem implements Listable, XMLable {

	//
	// This holds the List
	//
	private Hashtable m_hsh = null;

	/**
     * CatList
     *
     *  @author David Bigelow
     * @param _cid
     */
	public CatList(CatId _cid) {
		super(_cid);
		m_hsh = new Hashtable();
	}

	/**
     * put
     *
     * @param _ci
     */
	public final void put(CatItem _ci) {
		m_hsh.put(_ci.getId(), _ci);
	}

	/**
     * remove
     *
     * @param _ci
     */
	public final  void remove(CatItem _ci) {
		m_hsh.remove(_ci.getId());
	}

	//getAt(int)
	/**
     * get
     *
     * @return CatItem
     * @param _cid
     */
	public final CatItem get(CatId _cid) {
		return (CatItem) m_hsh.get(_cid);
	}

	/**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.Listable#values()
     */
    public Collection values() {
		return m_hsh.values();
	}

	/**
     * size
     *
     * @return
     */
    public final int size() {
		return m_hsh.size();
	}
	//Object getObjectAt(int)
	//indexOf(Object)
	//indexOf(Id)
	//remove(int)
	//removeAll()
	//remove(Object)
	//remove(Id)
	//resetId(OldId)
	//copyto(ObjectArray)
	//replace(Object)
	//clone()
	//copyList(List)
	//output(XMLWriter)

	//subclasses would implement methods like ...

	//CatList getAllWarrantyForProduct(Parms)
	//CatList getAllXforY(Parms)

	//CatList getChangedWarranty(Parms, FromDate, ToDate)

	//CatList getChangedWarrantyForProduct(Parms, FromDate, ToDate)
	//CatList getChangedXforY(Parms, FromDate, ToDate)

	/**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
		return false;
	}

	/**
	 * Automatically generated method: hashCode
	 *
	 * @return int
	 */
	public int hashCode() {
	    return 0;
	}

	public void setAttribute(String _strTag, Object _oAtt) {
		return;
	}


	public Object getAttribute(String _strTag) {
		return null;
	}

	/**
	 * get attribute keys
	 * 20050808
	 * @return keys[]
	 * @author tony
	 */
	public String[] getAttributeKeys() {
		return null;
	}
}

/*
 * $Log: CatList.java,v $
 * Revision 1.2  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:10  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.26  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.25  2005/06/22 22:39:16  gregg
 * remove method
 *
 * Revision 1.24  2005/06/02 04:49:55  dave
 * more clean up
 *
 * Revision 1.23  2005/06/01 20:36:41  gregg
 * get/setAttribute
 *
 * Revision 1.22  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.21  2005/05/28 23:25:59  dave
 * ok.. can we spit out xml?
 *
 * Revision 1.20  2005/05/26 00:06:06  dave
 * adding put to design by contract
 *
 * Revision 1.19  2005/05/19 21:48:13  dave
 * more list trickery
 *
 * Revision 1.18  2005/05/19 15:06:08  dave
 * adding more structure to see what we have created
 *
 * Revision 1.17  2005/05/19 04:44:49  dave
 * more baseline testing and config
 *
 * Revision 1.16  2005/05/18 01:24:14  dave
 * some interface fixes
 *
 * Revision 1.15  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */
