/*
 * Created on May 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorldWideAttributeCollection extends CatList {

    private static Hashtable c_hsh1 = new Hashtable();

    static {
        c_hsh1.put("DERIVEDDATA", "Y");
        c_hsh1.put("TECHCAP", "Y");
        c_hsh1.put("TECHINFO", "Y");

        // There will be more. .and we simply turn this into property file...

    }

    private SyncMapCollection m_smc = null;

    public WorldWideAttributeCollection(WorldWideAttributeCollectionId _wwacid) {
        super(_wwacid);
    }

    public WorldWideAttributeCollection(WorldWideProductId _cid, Catalog _cat) {
        super(_cid);
        get(_cat);
    }

    public void get(Catalog _cat) {

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        ReturnStatus returnStatus = new ReturnStatus(-1);

        WorldWideAttributeCollectionId wwacid = (WorldWideAttributeCollectionId) getId();
        WorldWideProductId wwpid = wwacid.getWorldWideProductId();
        GeneralAreaMapItem gami = wwacid.getGami();
        String strEnterprise = gami.getEnterprise();
        int iNLSID = gami.getNLSID();

        int iSource = wwacid.getSource();

        D.ebug(
            this,
            D.EBUG_DETAIL,
            "get() - here is wwacid..."
                + ":isByInteval:"
                + wwacid.isByInterval()
                + ":isFromPDH: "
                + wwacid.isFromPDH()
                + ":isFullImages:"
                + wwacid.isFullImages());
        try {

            /*
             * This is the case where we are going by interval and we need
            	 * to create a SyncMap for each Root EntityType this guy represents.
             */
            if (wwacid.isByInterval() && wwacid.isFromPDH()) {
                this.processSyncMap(_cat);
            } else {
                try {
                    rs = db.callGBL4011(new ReturnStatus(-1), strEnterprise, wwpid.getEntityType(), wwpid.getEntityID(), iNLSID);
                    rdrs = new ReturnDataResultSet(rs);

                } finally {
                    rs.close();
                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }
                D.ebug(this,D.EBUG_SPEW,"Getting attributes from CATdb, items returned :"+rdrs.size());
                for (int i = 0; i < rdrs.size(); i++) {

                    String strAttEntityType = rdrs.getColumn(i, 0);
                    int iAttEntityID = rdrs.getColumnInt(i, 1);

                    WorldWideAttributeId wwaid = new WorldWideAttributeId(wwpid, strAttEntityType, iAttEntityID, gami);

                    wwaid.setCollectionId(wwacid);   //set reference back to collection
                    // Put this guy in the list!
                    super.put(new WorldWideAttribute(wwaid));

                }
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setHeavy(true);

    }

    /**
      * This sig says my collection is being driven by another SyncMapCollection..
      * I am not the root.  So I will not have a SyncMap of myown
      * The passed CatId is the controlling parent here.  This eventually can be a
      * private thing.. called by a constructor!
      *
      * road
      * @param _cat
      */
    public void processSyncMap(Catalog _cat) {

        WorldWideAttributeCollectionId wwacid = (WorldWideAttributeCollectionId) this.getId();
        GeneralAreaMapItem gami = wwacid.getGami();
        WorldWideProductId wwpid = wwacid.getWorldWideProductId();
        SyncMapCollection smc = this.getSmc();

        //
        // If I am being tapped by a WorldWideProductId
        //
        // What we are looking for is all theChildren
        // That are in the child hashtable...
        // (Should be a properties file in the end)
        //

        if (wwacid.hasWorldWideProductId()) {
            D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - In WorldWideProductId");

            for (int x = 0; x < smc.getCount(); x++) {
                SyncMapItem smi = smc.get(x);
                //
                // right now.. i do not care about level.. i want anychild that matches this
                // the entitytype in the hash table
                //
                if (c_hsh1.containsKey(smi.getChildType())) {
                    D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - smi is: " + smi.toString());
                    WorldWideAttributeId wwaid = new WorldWideAttributeId(wwpid, smi.getChildType(), smi.getChildID(), wwacid);
                    WorldWideAttribute wwa = (WorldWideAttribute) this.get(wwacid);
                    if (wwa == null) {
                        wwa = new WorldWideAttribute(wwaid);
                        D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - making new WWA based upon wwaid of : " + wwaid);
                        wwa.setSmc(new SyncMapCollection());
                        this.put(wwa);
                    }
                    //
                    // ok.. we need to tuck away the PRODSTRUCT Guy
                    // and any other thing that may be below it...
                    wwa.getSmc().add(smi);

                }
            }
        }
    }

    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer("\n");
        Iterator it = this.values().iterator();
        while (it.hasNext()) {
            WorldWideAttribute wwa = (WorldWideAttribute) it.next();
            sb.append(wwa.dump(_brief));
        }
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.Databaseable#getReferences(COM.ibm.opicmpdh.middleware.Database, int)
     */
    public void getReferences(Catalog _cat, int _icase) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {
    }

    public final WorldWideProductId getWorldWideProductId() {
        return (WorldWideProductId) this.getId();
    }

    public void put(Catalog _cat, boolean _bcommit) {
        if (Catalog.isDryRun()) {
            return;
        }
    }

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

    public final boolean hasSyncMapCollection() {
        return m_smc != null;
    }

    public boolean hasAttEntityType(String _strEntityType) {
	    Iterator it = values().iterator();
		while (it.hasNext()) {
		    WorldWideAttribute wwa = (WorldWideAttribute)it.next();
		    WorldWideAttributeId wwaid = wwa.getWorldWideAttributeId();
		    D.ebug(D.EBUG_SPEW,"hasAttEntityType check for:" + wwaid.getAttEntityType() + ":" + wwaid.getAttEntityType() + " == " + _strEntityType + "?" + wwaid.getAttEntityType().equals(_strEntityType));
		    if(wwaid.getAttEntityType().equals(_strEntityType)) {
				return true;
			}
        }
        return false;
	}
}

/**
* $Log: WorldWideAttributeCollection.java,v $
* Revision 1.3  2011/05/05 11:21:31  wendy
* src from IBMCHINA
*
* Revision 1.1.1.1  2007/06/05 02:09:31  jingb
* no message
*
* Revision 1.2  2006/05/31 22:50:28  gregg
* buildWorldWideAttributes work
*
* Revision 1.1.1.1  2006/03/30 17:36:31  gregg
* Moving catalog module from middleware to
* its own module.
*
* Revision 1.26  2005/12/15 22:09:47  gregg
* BasicRule building logic for WorldWideAttributes!!
*
* Revision 1.25  2005/11/28 16:03:37  bala
* change get() - set reference to collection from wwattribute when rows retrieved from catdb
*
* Revision 1.24  2005/11/08 22:37:58  dave
* Adding TECHINFO to this WWAttribute pull
*
* Revision 1.23  2005/06/23 02:42:42  dave
* minor changes
*
* Revision 1.22  2005/06/22 21:17:16  gregg
* change signature for processSyncMap
*
* Revision 1.21  2005/06/13 05:45:55  dave
* minor xml fix to tag
*
*/
