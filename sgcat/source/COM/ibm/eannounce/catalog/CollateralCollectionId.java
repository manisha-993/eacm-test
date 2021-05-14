/*
 * Created on May 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.util.Iterator;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CollateralCollectionId extends CollectionId {

	WorldWideProductId m_WorldWideProductId = null;

	public CollateralCollectionId(CatalogInterval _cati, GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_cati, _gami, _iSource, _iType);
	}
		
	public CollateralCollectionId(WorldWideProductId _wwpid, CatalogInterval _cati, int _iSource, int _iType) {
		super(_cati, _wwpid.getGami(), _iSource, _iType);
		this.setWorldWideProductId(_wwpid);
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
	 */
	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(toString());
		sb.append("\n:source:" + this.getSource());
		sb.append("\n:type" + this.getType());
		sb.append("\n:isfromcat" + this.isFromCAT());
		sb.append("\n:isfrompdh" + this.isFromPDH());
		sb.append("\n:isFullImages" + this.isFullImages());
		sb.append("\n:isNetChanges" + this.isNetChanges());
		sb.append("\n:isbyInterval" + this.isByInterval());
		
		return sb.toString();
		
	}

	public static void main(String[] args) {
	}

    /**
     * getWorldWideProductId
     * @return
     */
    public WorldWideProductId getWorldWideProductId() {
        return m_WorldWideProductId;
    }

    /**
     * setWorldWideProductId
     * @param id
     */
    public void setWorldWideProductId(WorldWideProductId id) {
        m_WorldWideProductId = id;
    }

}
