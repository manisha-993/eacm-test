/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import COM.ibm.eannounce.objects.EntityItem;

/**
 * World Wide Product
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FamilyPage extends CatDBTableRecord {

    public static final String FMLY_ENTITY_GROUP = "FMLY";
    public static final String SER_ENTITY_GROUP = "SER";

    private SyncMapCollection m_smc = null;
    private CollateralCollection m_col = null;

    public static final String TABLE_NAME = "NA";

    /**
     * WorldWideProduct lets get the Light
     * @param _cid
     */
    public FamilyPage(FamilyPageId _cid) {
        super(TABLE_NAME, _cid);
    }

    /**
     * WorldWideProduct - lets get the heavy
     * Version
     *
     * @param _cid
     * @param _cat
     */
    public FamilyPage(FamilyPageId _cid, Catalog _cat) {
        this(_cid);
        get(_cat);
    }

    /**
     *  (non-Javadoc)
     *
     * @param _cat
     */
    public void get(Catalog _cat) {

        // TBD
    }

    /**
     *  (non-Javadoc)
     *
     * @param _cat
     * @param _icase
     */
    public void getReferences(Catalog _cat, int _icase) {

    }

    public final String dump(boolean _b) {

        StringBuffer sb = new StringBuffer("test dump: ");
        sb.append(NEW_LINE + "=======================================");
        sb.append(NEW_LINE + "class " + this.getClass().getName());
        sb.append(NEW_LINE + "=======================================");
        sb.append(NEW_LINE + "Name is: " + toString());
        sb.append(NEW_LINE + "FamPageId is:" + this.getFamilyPageId());
        sb.append(NEW_LINE + "----------------------------------------");
        sb.append(NEW_LINE + "Member Variables");
        sb.append(NEW_LINE + "----------------");
        sb.append(NEW_LINE + "TBD");
        sb.append(NEW_LINE + "=======================================");

        return sb.toString();

    }

    public final String toString() {
        return "TDB";
    }


    /**
     * getCollateralCollection
     *
     * @return
     */
    public CollateralCollection getCollateralCollection() {
        return m_col;
    }

    /**
     * setCollateralCollection
     *
     * @param _col
     */
    public void setCollateralCollection(CollateralCollection _col) {
        m_col = _col;
    }

    /**
     * hasSyncMapCollection
     *
     * @return
     */
    public final boolean hasSyncMapCollection() {
        return m_smc != null;
    }

    public void put(Catalog _cat, boolean _bcommit) {
    }

    /**
     *  (non-Javadoc)
     *
     * @param _ci
     */
    public void merge(CatItem _ci) {
    }

    /**
     * If I have an Smc Collection.  This means I have work to do to sync up to the CatDb
     *
     * @return
     */
    public SyncMapCollection getSmc() {
        return m_smc;
    }

    /**
     * If I have an Smc Collection.  This means I have work to do to sync up to the CatDb
     * @param collection
     */
    public void setSmc(SyncMapCollection collection) {
        m_smc = collection;
    }

    /**
     * setAttributes
     * sets all the attributes it can given the EntityItem that was passed
     * This handles all EntityTypes that have to run through this object
     * Here is where we flatten things out and apply any concatenation rules
     *
     * @param _ei
     */
    public void setAttributes(EntityItem _ei) {
        //
        // No Attributes to set yet!
    }

    /**
     * This here will pick off any relevent keys we need and covert them to attributes
     * Should always be called from a get(cat)
     *
     * @param _smi
     */
    public void setAttributeKeys(SyncMapCollection _smi) {
    }

    /**
     * isSEO
     *
     * @return
     */
    public boolean isSER() {
        return getFamilyPageId().isSER();
    }

    public boolean isFMLY() {
        return getFamilyPageId().isFMLY();
    }

    /**
     * Returns true if we have a WW Att Collection
     * @return
     */
    public final boolean hasCollateralCollection() {
        return this.m_col != null;
    }

    public void putStringVal(String _strColKey, String _strVal) {
    }

    public void putIntVal(String _strColKey, int _iVal) {
    }

    public String getStringVal(String _strColKey) {
        return null;
    }

    public FamilyPageId getFamilyPageId() {
        return (FamilyPageId)this.getId();
    }

    public int getIntVal(String _iColKey) {
        return super.getIntVal(_iColKey);
    }

}
/**
 * $Log: FamilyPage.java,v $
 * Revision 1.2  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:16  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.1  2005/10/26 18:04:54  dave
 * ok.. family page for collateral
 *
 **/
