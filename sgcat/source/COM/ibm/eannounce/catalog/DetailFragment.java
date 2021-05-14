/*
 * Created on May 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 * This holds a small piece of the attribute
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DetailFragment extends CatItem {
    private String ExtAttributeCode = null;
    private String AttributeValue = null;
    private String m_strUnitOfMeasure = null;
    private String ValFrom = null;
    private String ValTo = null;
	private boolean IsDerived = false;

	/**
     * DetailFragment
     *
     * @param _cid
     */
    public DetailFragment(DetailFragmentId _cid) {
        super(_cid);
    }

    /**
     *  (non-Javadoc) - This guy should never implement this class fully.
     * it is too inefficient to go get a single text string from the database
     * we must use this with caution.
     *
     */
    public void get(Catalog _cat) {
        // TODO Auto-generated method stub

    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // Good Enough For Now..
        return super.getId()
        + ":"
        + getAttributeCode()
        + ":"
        + getExtAttributeCode()
        + ":"
        + getAttributeValue()
        + ":"
        + getUnitOfMeasure()
        + ":"
        + getValFrom()
        + ":"
        + getValTo()
        + ":"
        + isActive();
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _breif) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *  (non-Javadoc)
     *
     */
    public void getReferences(Catalog _cat, int _icase) {
        // TODO Auto-generated method stub

    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
    }


    /**
     * getAttributeValue
     *
     * @return
     */
    public String getAttributeValue() {
        return AttributeValue;
    }

    /**
     * getFlagCode
     *
     * @return
     */
    public String getExtAttributeCode() {
        return ExtAttributeCode;
    }

    /**
     * setAttributeValue
     *
     * @param string
     */
    public void setAttributeValue(String string) {
        AttributeValue = string;
    }

    /**
     * setFlagCode
     *
     * @param string
     */
    public void setExtAttributeCode(String string) {
        ExtAttributeCode = string;
    }

    /**
     * getValFrom
     *
     * @return
     */
    public String getValFrom() {
        return ValFrom;
    }

    /**
     * setValFrom
     *
     * @param string
     */
    public void setValFrom(String string) {
        ValFrom = string;
    }

    /**
     * getValTo
     *
     * @return
     */
    public String getValTo() {
        return ValTo;
    }

    /**
     * setValFrom
     *
     * @param string
     */
    public void setValTo(String string) {
        ValTo = string;
    }

    /**
     * isDerived
     *
     * @return
     */
    public boolean isDerived() {
        return IsDerived;
    }

    /**
     * setDerived
     *
     * @param string
     */
    public void setDerived(boolean _b) {
        IsDerived = _b;
    }

    /**
     * getUnitOfMeasure
     * @return
     */
    public String getUnitOfMeasure() {
		return (m_strUnitOfMeasure != null? m_strUnitOfMeasure: "");
    }

    /**
     * setUnitOfMeasure
     * @param string
     */
    public void setUnitOfMeasure(String string) {
        m_strUnitOfMeasure = string;
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.Databaseable#put(COM.ibm.eannounce.catalog.Catalog)
     */
    public void put(Catalog _cat, boolean _bcommit) {
		if (!Catalog.isDryRun()) {
			return;
		}

    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatItem#merge(COM.ibm.eannounce.catalog.CatItem)
     */
    public final void merge(CatItem _cat) {
        if (_cat instanceof DetailFragment) {
            // Lets do
        }
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

    public final String getAttributeCode() {
    	return ((DetailFragmentId)this.getId()).getAttributeCode();
    }

}

/**
 * $Log: DetailFragment.java,v $
 * Revision 1.2  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:15  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.5  2005/09/14 19:41:24  joan
 * fixes
 *
 * Revision 1.4  2005/09/13 20:59:00  joan
 * change for column
 *
 * Revision 1.3  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.2  2005/06/15 22:11:24  joan
 * fixes
 *
 * Revision 1.1  2005/06/15 21:35:00  joan
 * intial load
 *
 */
