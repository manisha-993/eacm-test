/*
 * Created on May 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FamilyPageCollectionId   extends CollectionId {

    private CollateralId m_CollateralId = null;

    private boolean ByCollateral = false;

    public FamilyPageCollectionId(CollateralId _colid, CatalogInterval _cati, int _iSource, int _iType) {
        super(_cati, _colid.getGami(), _iSource, _iType);
        setCollateralId(_colid);
        setByCollateral(true);
    }

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

    public void setByCollateral(boolean _b) {
        ByCollateral = _b;
    }

    public boolean isByCollateral() {
        return ByCollateral;
    }

    /**
     * @return
     */
    public CollateralId getCollateralId() {
        return m_CollateralId;
    }

    /**
     * @param id
     */
    public void setCollateralId(CollateralId _id) {
        m_CollateralId = _id;
    }

    public final boolean hasCollateralId() {
        return m_CollateralId != null;
    }
}
