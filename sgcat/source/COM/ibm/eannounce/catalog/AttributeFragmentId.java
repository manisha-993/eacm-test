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
public class AttributeFragmentId extends CatId {

	private String m_strAttributeCode = null;
	
	private WorldWideAttributeId m_acid = null;
	
	/**
	 * AttributeFragmentId by AttributeCode and gami
	 * 
     * @param _strAttributeCode
     * @param _gami 
     */
	public AttributeFragmentId(WorldWideAttributeId _acid, String _strAttributeCode, GeneralAreaMapItem _gami) {
		super(_strAttributeCode, _gami);
		this.setAttributeCode(_strAttributeCode);
		this.m_acid = _acid;
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
	
	public WorldWideAttributeId getWorldWideAttributeId() {
		return m_acid;
	}
}

/*
 * $Log: AttributeFragmentId.java,v $
 * Revision 1.2  2011/05/05 11:21:33  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:02  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.8  2005/06/23 01:56:18  dave
 * lets hook up the update of the wwattributes
 *
 * Revision 1.7  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.6  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.5  2005/05/23 16:22:41  dave
 * rearrange Intent back to CollectionId
 *
 * Revision 1.4  2005/05/23 14:14:31  dave
 * adding source, type, and interval sigs to keys
 * adding getGami to Catalog statics
 *
 * Revision 1.3  2005/05/22 23:04:36  dave
 * Added CollectionId
 * addind Catalog Interval
 * Placed enterprise in the Gami
 *
 */
