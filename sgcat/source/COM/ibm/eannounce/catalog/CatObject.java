/*
 * Created on May 31, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;



// Basic atomic object

/**
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CatObject implements XMLable {

	/**
     */
    public static final String EPOCH = "1980-01-01-00.00.00.000000";
	/**
     */
    public static final String FOREVER = "9999-12-31-00.00.00.000000";

	/**
     */
    public static final String NEW_LINE = "\n";
	/**
     */
    public static final String TAB = "\t";

    /**
     * sameObject
     *
     * @param _obj
     * @return
     */
    public boolean sameObject(Object _obj) {
        return (_obj.hashCode() == hashCode());
	}

	//
	// These are the things we want to have for sure
	//
	/**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public abstract String toString();
	/**
     * dump
     *
     * @param _breif
     * @return
     */
    public abstract String dump(boolean _breif);
	/**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public abstract boolean equals(Object obj);

	/**
	 * Automatically generated method: hashCode
	 *
	 * @return int
	 */
	public int hashCode() {
	    return 0;
	}

//clone()
//output(XMLWriter)
}

/**
 * $Log: CatObject.java,v $
 * Revision 1.2  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:10  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.14  2005/09/23 17:00:11  gregg
 * CatDBTableRecord now extends CatItem
 *
 * Revision 1.13  2005/09/22 23:15:37  gregg
 * going for putStringVal, etc...
 *
 * Revision 1.12  2005/09/22 23:00:22  gregg
 * private->protected
 *
 * Revision 1.11  2005/09/22 22:18:08  gregg
 * move some common getAttribtue functionality up to CatObject
 *
 * Revision 1.10  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.9  2005/06/01 02:20:11  dave
 * ok.. more cleanup and ve work
 *
 * Revision 1.8  2005/05/18 01:06:00  dave
 * trying to write a main test
 *
 * Revision 1.7  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */

