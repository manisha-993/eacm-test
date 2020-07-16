/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: WorldWideComponentCollection.java,v $
 * Revision 1.2  2011/05/05 11:21:33  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:31  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.7  2005/11/29 19:19:48  dave
 * formatting
 *
 * Revision 1.6  2005/11/21 23:46:14  joan
 * fixes
 *
 * Revision 1.5  2005/10/24 23:30:19  dave
 * minor changes for logging
 *
 * Revision 1.4  2005/10/05 19:30:26  joan
 * fixes
 *
 * Revision 1.3  2005/10/04 23:24:16  joan
 * work on component
 *
 * Revision 1.2  2005/10/03 22:35:19  joan
 * fixes
 *
 * Revision 1.1  2005/10/03 22:08:33  joan
 * work on component
 *
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorldWideComponentCollection extends CatList implements CatSync {

    private SyncMapCollection m_smc = null;

    /**
     * WorldWideComponentCollection
     *
     */
    public WorldWideComponentCollection(WorldWideComponentCollectionId _cid) {
        super(_cid);
    }

    /**
     * WorldWideComponentCollection
     *
     */
    public WorldWideComponentCollection(WorldWideComponentCollectionId _cid, Catalog _cat) {
        this(_cid);
        get(_cat);
    }

    public static void main(String[] args) {
        // look for logic in the main() method of ProductCollectionId!
    }

    public final String dump(boolean _b) {
        WorldWideComponentCollectionId wwccid = getWorldWideComponentCollectionId();

        WorldWideProductId pid = wwccid.getWorldWideProductId();
        String strHeader = "**WorldWideComponentCollection **\n";
        strHeader += "=== WWPRODUCT: " + pid.getEntityType() + ":" + pid.getEntityID();
        StringBuffer sb = new StringBuffer();
        sb.append(strHeader);
        if (!_b) {
            sb.append("\n          << " + values().size() + " WWComponents >>");
            Iterator it = values().iterator();
            while (it.hasNext()) {
                WorldWideComponent wwc = (WorldWideComponent) it.next();
                sb.append("\n          " + wwc.dump(_b));
            }
        }
        return sb.toString();
    }

    public final String toString() {
        return "TBD";
    }

    public final boolean equals(Object obj) {
        return false;
    }

    public void getReferences(Catalog _cat, int _icase) {
        //
    }

    /**
     * This is going to fill out ALL Data (fat) and ALL references (deep) _iLvl's deep.
     */
    public void getFatAndDeep(Catalog _cat, int _iLvl) {
        if (_iLvl < 0) {
            return;
        }
        _iLvl--;
        get(_cat);
        getReferences(_cat, -1);
        Iterator it = values().iterator();
        while (it.hasNext()) {
            ProdStruct ps = (ProdStruct) it.next();
            ps.getFatAndDeep(_cat, _iLvl);
        }
    }

    /**
     * For debugging XML
     */
    public void dumpXML(XMLWriter _xml, boolean _bDeep) {
        Iterator it = values().iterator();
        if (_bDeep) {
            while (it.hasNext()) {
                ProdStruct ps = (ProdStruct) it.next();
                ps.dumpXML(_xml, _bDeep); //true
            }
        }
    }

    /**
     * Here is where we query and fill out the ProductId's
     */
    public void get(Catalog _cat) {

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        ReturnStatus returnStatus = new ReturnStatus(-1);

        WorldWideComponentCollectionId wwccid = getWorldWideComponentCollectionId();
        WorldWideProductId wwpid = wwccid.getWorldWideProductId();
        ProductId pid = wwccid.getProductId();
        ComponentGroupId cgid = wwccid.getComponentGroupId();

        GeneralAreaMapItem gami = wwccid.getGami();
        int iSource = wwccid.getSource();

        D.ebug(
            this,
            D.EBUG_DETAIL,
            "get() - here is wwccid..."
                + ":isByInteval:"
                + wwccid.isByInterval()
                + ":isFromPDH: "
                + wwccid.isFromPDH()
                + ":isFullImages:"
                + wwccid.isFullImages());

        try {
            if (wwccid.isFromCAT()) {

                D.ebug(this, D.EBUG_DETAIL, "get() - wwccid isFromCat ...");

                String strEnterprise = gami.getEnterprise();
                int iNLSID = gami.getNLSID();
                String strLocEntityType = null;
                int iLocEntityID = -1;
                if (wwpid != null) {
                	strLocEntityType = wwpid.getEntityType();
                	iLocEntityID = wwpid.getEntityID();
				} else if (pid != null) {
                	strLocEntityType = pid.getEntityType();
                	iLocEntityID = pid.getEntityId();
				}

				if (strLocEntityType != null) {
					try {
						rs = db.callGBL4051(returnStatus, strEnterprise, strLocEntityType, iLocEntityID);
						rdrs = new ReturnDataResultSet(rs);
					} finally {
						rs.close();
						rs = null;
						db.commit();
						db.freeStatement();
						db.isPending();
					}

					for (int i = 0; i < rdrs.size(); i++) {
						String strCOMPENTITYTYPE = rdrs.getColumn(i, 0);
						int iCOMPENTITYId = rdrs.getColumnInt(i, 1);

						WorldWideComponentId wwcid =
							new WorldWideComponentId(strLocEntityType, iLocEntityID, strCOMPENTITYTYPE, iCOMPENTITYId, gami, this.getWorldWideComponentCollectionId());
						WorldWideComponent wwc = new WorldWideComponent(wwcid);
						this.put(wwc);
					}
				}
            } else {

                /*
                 * This is the case where we are going by interval and we need
                 * to create a SyncMap for each Root EntityType this guy represents.
                 */
                if (wwccid.isByInterval() && wwccid.isFromPDH() && wwccid.isFullImages()) {
                    //
                    // did we come from a world wide Product? and do we have a
                    //
                    if (wwpid != null) {
                        D.ebug(this, D.EBUG_DETAIL, "get() - PDH,byInterval, FullImage - world wide product id source");
                        if (this.hasSyncMapCollection() && wwccid.hasWorldWideProductId()) {
                            this.processSyncMap(_cat);
                        }
                    } else if (pid != null) {
                        D.ebug(this, D.EBUG_DETAIL, "get() - PDH,byInterval, FullImage - product id source");
                        if (this.hasSyncMapCollection() && wwccid.hasProductId()) {
                            this.processSyncMap(_cat);
                        }
                    } else if (cgid != null) {
                        D.ebug(this, D.EBUG_DETAIL, "get() - PDH,byInterval, FullImage - ComponentGroupID source");
                        if (this.hasSyncMapCollection() && wwccid.hasComponentGroupId()) {
                            this.processSyncMap(_cat);
                        }
                    } else {
                        db.debug(
                            D.EBUG_DETAIL,
                            this.getClass().getName() + ".get() PDH,byInterval, FullImage - *** CANNOT FIND A PRESPECTIVE ***");
                    }
                }
            }

        } catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

        WorldWideComponentCollectionId wwccid = this.getWorldWideComponentCollectionId();
        GeneralAreaMapItem gami = wwccid.getGami();
        WorldWideProductId wwpid = wwccid.getWorldWideProductId();
        ProductId pid = wwccid.getProductId();
        ComponentGroupId cgid = wwccid.getComponentGroupId();
        SyncMapCollection smc = this.getSmc();

        if (hasSyncMapCollection()) {
            for (int x = 0; x < smc.getCount(); x++) {
                SyncMapItem smi = smc.get(x);

                D.ebug(this, D.EBUG_DETAIL, "processSyncMap() smi [" + x + "]:" + smi.toString());

				if(wwccid.hasWorldWideProductId() || wwccid.hasProductId()) {
		            D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - In WorldWideProductId/ProductId");

				//
				// if we are full images .. we only care about the ON stuff!
				// we will not worry about true net changes now!!!
                //
                    if (wwccid.isFromPDH() && wwccid.isFullImages()) {
                        if (smi.getRootType().equals("MODEL")) {
                            if (smi.getChildType().equals("COMPGRP") && smi.getChildLevel() == 0) {
                                D.ebug(this, D.EBUG_SPEW, "WorldWideComponentCollection block:MODEL:processSyncMap() - smi is: " + smi.toString());
                                WorldWideComponentId wwcid =
                                    new WorldWideComponentId(smi.getRootType(),smi.getRootID(), smi.getChildType(),smi.getChildID(),gami,wwccid);
                                WorldWideComponent wwc = (WorldWideComponent) this.get(wwcid);
                                if (wwc == null) {
                                    wwc = new WorldWideComponent(wwcid);
                                    D.ebug(this, D.EBUG_SPEW, "WorldWideComponentCollection block:processSyncMap() - making new PS based upon wwcid of : " + wwcid);
                                    wwc.setSmc(new SyncMapCollection());
                                    this.put(wwc);
                                }
                                wwc.setActive(smi.getChildTran().equals("ON"));
                                wwc.getSmc().add(smi);
                            }
                        }
					}
				} else if (wwccid.hasComponentGroupId()) {
					D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - In ComponentGroupId");
                    if (wwccid.isFromPDH() && wwccid.isFullImages()) {
                        if (smi.getRootType().equals("COMPGRP")) {
                            if (smi.getEntity1Type().equals("MODEL") && smi.getChildLevel() == 0) {
                                D.ebug(this, D.EBUG_SPEW, "WorldWideComponentCollection block:MODEL:processSyncMap() - smi is: " + smi.toString());
                                WorldWideComponentId wwcid =
                                    new WorldWideComponentId(smi.getEntity1Type(),smi.getEntity1ID(), smi.getRootType(),smi.getRootID(), gami,wwccid);
                                WorldWideComponent wwc = (WorldWideComponent) this.get(wwcid);
                                if (wwc == null) {
                                    wwc = new WorldWideComponent(wwcid);
                                    D.ebug(this, D.EBUG_SPEW, "WorldWideComponentCollection block:processSyncMap() - making new PS based upon wwcid of : " + wwcid);
                                    wwc.setSmc(new SyncMapCollection());
                                    this.put(wwc);
                                }

                                wwc.setActive(smi.getChildTran().equals("ON"));
                                wwc.getSmc().add(smi);
                            }
                        }
					}
				}
            }
        }
    }

    public final SyncMapCollection diveSmc(SyncMapCollection _smc, SyncMapItem _smi) {
        SyncMapCollection smcFinal = new SyncMapCollection();

        int iLevel = _smi.getChildLevel();

        for (int i = 0; i < _smc.getCount(); i++) {
            SyncMapItem smi = _smc.get(i);
        }

        return smcFinal;
        //

        //
        // OK we simply will take this list and return everything that is below
        // The starting marker point.

    }
    public final WorldWideComponentCollectionId getWorldWideComponentCollectionId() {
        return (WorldWideComponentCollectionId) this.getId();
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

}
