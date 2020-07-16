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
public class CategoryCollectionId extends CollectionId {

	//
	// This list can be generated from a Collateral List.
	//
	CollateralId m_colid = null;

	public CategoryCollectionId(CatalogInterval _cati, GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_cati, _gami, _iSource, _iType);
	}
		
	public CategoryCollectionId(CollateralId _colid, CatalogInterval _cati, int _iSource, int _iType) {
		super(_cati, _colid.getGami(), _iSource, _iType);
		this.setCollateralId(_colid);
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
		sb.append("\n:isbyInterval" + this.m_colid);
		
		return sb.toString();
		
	}

	public static void main(String[] args) {
	}

	/**
	 * getCollateralId
	 * 
	 * @return
	 */
    public CollateralId getCollateralId() {
        return m_colid;
    }

    /**
     * setCollateralId
     * @param id
     */
    public void setCollateralId(CollateralId id) {
		m_colid = id;
    }

}
