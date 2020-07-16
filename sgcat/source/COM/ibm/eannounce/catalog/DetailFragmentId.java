/*
 * Created on May 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 * This holds an Attribute Fragment
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DetailFragmentId extends CatId {

	private String m_strAttributeCode = null;

	/**
	 * DetailFragmentId by AttributeCode and gami
	 *
     * @param _strAttributeCode
     * @param _gami
     */
	public DetailFragmentId(String _strAttributeCode, GeneralAreaMapItem _gami) {
		super(_strAttributeCode, _gami);
		this.setAttributeCode(_strAttributeCode);

	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
	 */
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
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
	}
    /**
     * getAttributeCode
     * @return
     */
    public String getAttributeCode() {
        return m_strAttributeCode;
    }

    /**
     * setAttributeCode
     * @param string
     */
    public void setAttributeCode(String string) {
        m_strAttributeCode = string;
    }

}

/*
 * $Log: DetailFragmentId.java,v $
 * Revision 1.2  2011/05/05 11:21:35  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:15  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.1  2005/06/15 21:35:00  joan
 * intial load
 *
 *
 */
