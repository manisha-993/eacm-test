/*
 * Created on May 12, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

/**
 * CatItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CatItem extends CatObject implements Databaseable, XMLable {

    // This guy holds the ID [light weight]
    private CatId m_cid = null;

    private boolean Heavy = false;
    private boolean Deep = false;
    private boolean Active = true;
    private boolean Changed = false;

    /**
     * This class takes the information from the passed CatItem and merges it
     * into this CatItem.
     *
     * If anything has changed, we will trip the changed flag the means it should be posted.
     *
     * @param _ci
     */
    public abstract void merge(CatItem _ci);

    /**
     * Provide a generic means of setting/getting attribute. This will be useful for storing derived attributes, for example.
     *
     * @param _oAtt
     */
    public abstract void setAttribute(String _strTag, Object _oAtt);

    /**
     * Provide a generic means of setting/getting attribute. This will be useful for storing derived attributes, for example.
     *
     * @return _oAtt
     */
    public abstract Object getAttribute(String _strTag);

	/**
	 * get attribute keys
	 * 20050808
	 * @return keys[]
	 * @author tony
	 */
	 public abstract String[] getAttributeKeys();

	/**
	 * get EntityType(s)
	 * get the associated entity types to we can match you back up.
	 * 20050808
	 * @return keys[]
	 * @author tony
	 */
	 public String[] getEntityTypes() {
	 	return null;
	 }


    /**
     * CatItem
     * This guy simply makes a light wieght version of itselft
     *
     * @param _cid
     *  @author David Bigelow
     */
    public CatItem(CatId _cid) {
        m_cid = _cid;
    }

    /**
     * getId
     *
     * Needed to be managed in a list
     *
     * @return
     *  @author David Bigelow
     */
    public final CatId getId() {
        return m_cid;
    }

    /**
     * shrinkItem
     *
     */
    public final void shrinkItem() {
    }

    //putItem(Database, Profile)
    //replaceId(CatId)  // this may be dangerous
    //hasSameId(CatId) // compare to CatId
    //hasSameId(CatItem) // compare this CatItem's CatId to the CatId of another CatItem

    /**
     * isHeavy
     *
     * @return
     */
    public final boolean isHeavy() {
        // Heavy means we have a CatId AND a CatObject
        // Having a CatObject but NOT a CatId would be a bad thing ...
        return (!(m_cid == null) && Heavy);
    }

    /**
     * isDeep
     *
     * @return
     */
    public final boolean isDeep() {
        // Heavy means we have a CatId AND a CatObject
        // Having a CatObject but NOT a CatId would be a bad thing ...
        return (Heavy && Deep);
    }

    /**
     * isLight
     *
     * @return
     */
    public final boolean isLight() {
        // Light means we have a CatId and NOT a CatItem
        // Having a CatObject but NOT a CatId would be a bad thing ...
        return (!(m_cid == null) && !Heavy && !Deep);
    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object _obj) {
        return _obj instanceof CatItem && getId().equals(((CatItem) _obj).getId());
    }

    /**
     * setDeep
     *
     * @param _b
     */
    public void setDeep(boolean _b) {
        Deep = _b;
    }

    /**
     * setHeavy
     *
     * @param _b
     */
    public void setHeavy(boolean _b) {
        Heavy = _b;
    }

    /**
     * isActive
     *
     * @return
     */
    public boolean isActive() {
        return Active;
    }

    /**
     * setActive
     *
     * @param b
     */
    public void setActive(boolean b) {
        Active = b;
    }

    /**
     * Automatically generated method: hashCode
     *
     * @return int
     */
    public int hashCode() {
        return 0;
    }

    /**
     * isChanged
     *
     * @return
     */
    public boolean isChanged() {
        return Changed;
    }

    /**
     * setChanged
     *
     * @param b
     */
    public void setChanged(boolean b) {
        Changed = b;
    }

}

/*
 * $Log: CatItem.java,v $
 * Revision 1.2  2011/05/05 11:21:31  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:10  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.32  2005/09/23 17:00:11  gregg
 * CatDBTableRecord now extends CatItem
 *
 * Revision 1.31  2005/09/09 18:53:13  gregg
 * use NO_DATE_VAL
 *
 * Revision 1.30  2005/08/18 16:16:09  tony
 * *** empty log message ***
 *
 * Revision 1.29  2005/08/08 18:54:25  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.28  2005/06/02 04:49:55  dave
 * more clean up
 *
 * Revision 1.27  2005/06/01 20:31:33  gregg
 * set/get Attribute
 *
 * Revision 1.26  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.25  2005/05/27 00:55:17  dave
 * adding the merge method.
 *
 * Revision 1.24  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.23  2005/05/19 23:47:02  dave
 * taking out the gami at the catitem level
 *
 * Revision 1.22  2005/05/19 23:20:27  bala
 * need to be able to get to GAMI
 *
 * Revision 1.21  2005/05/19 04:44:49  dave
 * more baseline testing and config
 *
 * Revision 1.20  2005/05/19 03:20:48  dave
 * adding getReference concept and changing DDL abit
 * to remove the not nulls
 *
 * Revision 1.19  2005/05/18 01:24:14  dave
 * some interface fixes
 *
 * Revision 1.18  2005/05/18 01:06:00  dave
 * trying to write a main test
 *
 * Revision 1.17  2005/05/17 23:35:06  dave
 * databasable get
 *
 * Revision 1.16  2005/05/13 21:35:23  roger
 * Fix
 *
 * Revision 1.15  2005/05/13 20:56:33  roger
 * Added CatObject and shrinkItem()
 *
 * Revision 1.14  2005/05/13 20:50:10  roger
 * Added isHeavy/Light methods
 *
 * Revision 1.13  2005/05/13 20:39:50  roger
 * Turn on logging in source
 *
 */
