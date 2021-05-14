/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * This holds a collection of WorldWideProducts
 *
 * How this collection is derived is expressed in the FamilyPageCollectionId
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FamilyPageCollection
    extends CatList implements XMLable, CatSync {

    /**
     * Holds the answer  to GBL8104
     */
    private SyncMapCollection m_smc = null;

    /**
     * This is the World Wide Product Collection
     * with nothing in its list the _cid is the CollectionID that instructs
     * it how to fill its list out when its .get method is called
     * @param _cid
     */
    public FamilyPageCollection(FamilyPageCollectionId _cid) {
        super(_cid);
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
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public final String dump(boolean _b) {
        StringBuffer sb = new StringBuffer("test dump: ");

        Iterator it = null;
        int i = 0;

        sb.append(NEW_LINE + "=======================================");
        sb.append(NEW_LINE + "class " + this.getClass().getName());
        sb.append(NEW_LINE + "=======================================");
        sb.append(NEW_LINE + "\tName is: " + toString());
        sb.append(NEW_LINE + "\tCatId is:" + this.getFamilyPageCollectionId());
        sb.append(NEW_LINE + "\t---------");
        sb.append(NEW_LINE + "\tFAM");
        sb.append(NEW_LINE + "\t---------");
        it = this.values().iterator();
        i = 1;
        while (it.hasNext()) {
            FamilyPage wwp = (FamilyPage) it.next();
            sb.append(NEW_LINE + "\t" + (i++) + " - " + wwp);
        }

        return sb.toString();

    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return this.getFamilyPageCollectionId().toString();
    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public final boolean equals(Object obj) {
        return false;
    }

    /**
     *  (non-Javadoc)
     *
     * @param _cat
     * @param _icase
     */
    public void getReferences(Catalog _cat, int _icase) {
        // TODO Auto-generated method stub
    }

    /**
     * This guy will retrieve a list of WorldWideProduct Objects
     * Based upon the flags set in its Collection Id
     * It will also pass its Collection Id Down
     *
     * @param _cat
     */
    public void get(Catalog _cat) {

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();

        FamilyPageCollectionId wwpcid = getFamilyPageCollectionId();
        GeneralAreaMapItem gami = wwpcid.getGami();
        String strEnterprise = gami.getEnterprise();
        int iNLSID = gami.getNLSID();

        D.ebug(this, D.EBUG_SPEW,
               "get() - here is wwcpid..." + ":isByInteval:" + wwpcid.isByInterval() + ":isFromPDH: " + wwpcid.isFromPDH() +
               ":isFullImages:" + wwpcid.isFullImages());

        /*
         * This is the case where we are going by interval and we need
         * to create a SyncMap for each Root EntityType this guy represents.
         */
        if (wwpcid.isByInterval() && wwpcid.isFromPDH()) {
            this.processSyncMap(_cat);
        }

    }

    /**
     * getFamilyPageCollectionId
     * @return
     */
    public final FamilyPageCollectionId getFamilyPageCollectionId() {
        return (FamilyPageCollectionId)this.getId();
    }

    /**
     *  put - Since we are collection, this guy manages relationships between the
     *  things in this list.. and the controlling parent ID
     *
     * @param _cat
     * @param _bcommit
     */
    public void put(Catalog _cat, boolean _bcommit) {
        if (Catalog.isDryRun()) {
            return;
        }
        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        FamilyPageCollectionId fpcid = this.getFamilyPageCollectionId();

        GeneralAreaMapItem gami = fpcid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();

        //
        // Were we spawned from a CollateralId?
        //

        if (fpcid.hasCollateralId() && fpcid.isFromPDH() && fpcid.isFullImages()) {

            CollateralId colid = fpcid.getCollateralId();
            String strCollEntityType = colid.getCollEntityType();
            int iCollEntityID = colid.getCollEntityID();

            D.ebug(this, D.EBUG_SPEW,
                   "put() - here we are managing our wwProduct Relators..." + ":isByInteval:" + fpcid.isByInterval() +
                   ":isFromPDH: " + fpcid.isFromPDH() + ":isFullImages:" + fpcid.isFullImages() + ":hasCollateralId:" +
                   fpcid.hasCollateralId() + ":colid:" + fpcid.getCollateralId());

            Iterator it = this.values().iterator();
            while (it.hasNext()) {
                FamilyPage fp = (FamilyPage) it.next();
                FamilyPageId fpid = fp.getFamilyPageId();
                String strFPEntityType = fpid.getEntityType();
                int iFPEntityID = fpid.getEntityID();

                try {
                    db.callGBL8979(new ReturnStatus( -1), strEnterprise, strFPEntityType, iFPEntityID, strCollEntityType,
                                   iCollEntityID, (fp.isActive() ? 1 : 0));
                    if (_bcommit) {
                        db.commit();
                    }
                    db.freeStatement();
                    db.isPending();

                }
                catch (MiddlewareException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     *  (non-Javadoc)
     *
     * @param _ci
     */
    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub

    }

    /**
     * getSmc
     *
     * @return
     */
    public SyncMapCollection getSmc() {
        return m_smc;
    }

    /**
     * setSmc
     *
     * @param collection
     */
    public void setSmc(SyncMapCollection collection) {
        m_smc = collection;
    }

    /**
     * This is they guy that drives reading and drilling down through the SyncMapCollection and
     * bringing the Catalog Up to speed The sync map is  going to be the same one no matter the
     * language and intent.. we will need to sort this one out as we move further down the
     * road
     */
    public void processSyncMap(Catalog _cat) {

        FamilyPageCollectionId fpcid = this.getFamilyPageCollectionId();
        GeneralAreaMapItem gami = fpcid.getGami();
        SyncMapCollection smc = this.getSmc();

        HashMap  hmMyEnts = new HashMap();
        hmMyEnts.put("FMLY","Y");
        hmMyEnts.put("CATNAV","Y");


        //
        // O.K.  did we come from a colateral person?
        //
        // if so. we need to look for both FMLY.. and SER
        // These are the guys that have to be tied back to us..
        //
        if (fpcid.hasCollateralId()) {
            for (int x = 0; x < smc.getCount(); x++) {
                SyncMapItem smi = getSmc().get(x);
                String strChildType = smi.getChildType();
        // RQK 11192007 adding a check for childtran ON to prevent the case of an inactive relator turning off an 
        // active relator to the same child (ie happens when the same child is delinked and then linked again)  
                if (hmMyEnts.containsKey(strChildType) && smi.getChildTran().equals("ON")) {
                    FamilyPageId fpid = new FamilyPageId(smi.getChildType(), smi.getChildID(), gami, fpcid);
                    FamilyPage fp = (FamilyPage)this.get(fpid);
                    if (fp == null) {
                        fp = new FamilyPage(fpid);
                        fp.setSmc(new SyncMapCollection());
                        this.put(fp);
                    }
                    fp.setActive(smi.getChildTran().equals("ON"));
                    fp.getSmc().add(smi);
                }
            }
        }
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatSync#hasSyncMapCollection()
     */
    public final boolean hasSyncMapCollection() {
        return m_smc != null;
    }
}
/**
 * $Log: FamilyPageCollection.java,v $
 * Revision 1.3  2011/05/05 11:21:31  wendy
 * src from IBMCHINA
 *
 * Revision 1.3  2009/02/18 19:51:01  rick
 * change to move IMG/MM from SER to CATNAV
 *
 * Revision 1.2  2007/11/20 02:50:05  rick
 * Added check of childtran on to prevent the case of an
 * inactive relator turing off an active relator to the same child.
 * ie - happens when the same child is delinked and then
 * linked again.
 *
 * Revision 1.1.1.1  2007/06/05 02:09:16  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.2  2005/12/01 23:44:45  joan
 * fixes
 *
 * Revision 1.1  2005/10/26 18:04:58  dave
 * ok.. family page for collateral
 *
 *
 */
