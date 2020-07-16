/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: ProdStructCollection.java,v $
 * Revision 1.5  2013/05/14 13:02:55  liuweimi
 * Fix PUBFROM PUBTO derivation for XCC based on BH - EACM
 *
 * Revision 1.4  2011/05/05 11:21:31  wendy
 * src from IBMCHINA
 *
 * Revision 1.5  2009/07/21 18:58:03  rick
 * change to process avails via OOFAVAIL
 * and SWPRODSTRUCTAVAIL for LSEOs
 *
 * Revision 1.4  2009/05/26 14:40:58  rick
 * fix so that catlgor and avail are only used for
 * the specific prodstruct being processed.
 *
 * Revision 1.3  2008/03/27 12:37:03  rick
 * Changes to support confqty on wwseoswprodstruct,
 * lseoprodstruct,lseoswprodstruct and lseobundlelseo.
 *
 * Revision 1.2  2008/01/21 15:18:17  rick
 * changes for pubto/pubfrom date processing
 *
 * Revision 1.1.1.1  2007/06/05 02:09:24  jingb
 * no message
 *
 * Revision 1.2  2006/05/25 18:09:20  gregg
 * debug
 *
 * Revision 1.1.1.1  2006/03/30 17:36:30  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.53  2006/02/03 20:08:08  gregg
 * updates to what goes into smc for LSEOBUNDLES
 *
 * Revision 1.52  2006/01/23 21:11:36  gregg
 * Soring MODELCTLGOR in prodstruct's smc for future pubdate processing
 *
 * Revision 1.51  2006/01/18 18:32:05  dave
 * cleaning up some debug
 *
 * Revision 1.50  2005/12/15 18:59:51  gregg
 * adding in WWSEOSWPRODSTRUCT
 *
 * Revision 1.49  2005/11/29 20:35:01  dave
 * fix out the product
 *
 * Revision 1.48  2005/11/28 16:00:16  bala
 * change get() return when no product found
 *
 * Revision 1.47  2005/11/10 15:05:49  dave
 * for loop fix
 *
 * Revision 1.46  2005/11/09 20:55:35  dave
 * adding CONFQTY and defaults
 *
 * Revision 1.45  2005/11/09 18:40:06  gregg
 * going for LSEOBUNDLES
 *
 * Revision 1.44  2005/10/25 20:04:48  gregg
 * more SWPRODSTRUCT
 *
 * Revision 1.43  2005/10/25 18:38:12  gregg
 * add SWPRODSTRUCT
 *
 * Revision 1.42  2005/10/24 23:45:32  joan
 * fix compile
 *
 * Revision 1.41  2005/10/24 23:30:19  dave
 * minor changes for logging
 *
 * Revision 1.40  2005/09/14 16:34:25  gregg
 * logging smc change
 *
 * Revision 1.39  2005/09/13 22:20:29  gregg
 * some traces
 *
 * Revision 1.38  2005/09/13 17:11:29  gregg
 * fix
 *
 * Revision 1.37  2005/09/13 17:06:02  gregg
 * passing StructEntityType/StructEntityId to ProdStructId Constructor (Product flavor)
 *
 * Revision 1.36  2005/09/13 16:28:03  gregg
 * properly building ProdStructs for Products
 *
 * Revision 1.35  2005/09/13 05:42:19  dave
 * final PROJCDNAM fix
 *
 * Revision 1.34  2005/09/13 05:10:06  dave
 * ok.. we may have product structure now for WWSEO
 *
 * Revision 1.33  2005/09/13 04:14:31  dave
 * ok.. lets add the PROJCDNAM, and lets take a hard look at
 * WWSEO and how to get prod structures
 *
 * Revision 1.32  2005/06/22 21:17:16  gregg
 * change signature for processSyncMap
 *
 * Revision 1.31  2005/06/13 04:35:33  dave
 * ! needs to be not !
 *
 * Revision 1.30  2005/06/13 04:02:06  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.29  2005/06/07 04:44:04  dave
 * secondary fixes to commit chunk
 *
 * Revision 1.28  2005/06/07 04:34:51  dave
 * working on commit control
 *
 * Revision 1.27  2005/06/05 16:54:52  dave
 * rolling in the feature concept
 *
 * Revision 1.26  2005/06/04 17:31:23  dave
 * syntax fix
 *
 * Revision 1.25  2005/06/04 17:21:50  dave
 * more trace
 *
 * Revision 1.24  2005/06/04 16:46:05  dave
 * more trace
 *
 * Revision 1.23  2005/06/04 16:21:22  dave
 * ok.. fixed type source vs source type
 *
 * Revision 1.22  2005/06/04 16:12:30  dave
 * more trace is good
 *
 * Revision 1.21  2005/06/04 15:54:44  dave
 * Lets start going after WW product collection
 *
 * Revision 1.20  2005/06/02 20:45:20  gregg
 * darnit i cannt tyype
 *
 * Revision 1.19  2005/06/02 20:40:34  gregg
 * fix
 *
 * Revision 1.18  2005/06/02 20:34:09  gregg
 * fixing the parent/child collection reference mayhem
 *
 * Revision 1.17  2005/06/02 18:25:53  gregg
 * some debugs
 *
 * Revision 1.16  2005/06/02 18:07:43  gregg
 * compile
 *
 * Revision 1.15  2005/06/02 18:01:24  gregg
 * s'more XML + dumps
 *
 * Revision 1.14  2005/06/02 04:49:55  dave
 * more clean up
 *
 * Revision 1.13  2005/06/01 06:39:51  dave
 * fixing a null pointer
 *
 * Revision 1.12  2005/06/01 06:31:17  dave
 * ok.. lets see if we can do some ProdStruct Damage
 *
 * Revision 1.11  2005/05/27 00:55:17  dave
 * adding the merge method.
 *
 * Revision 1.10  2005/05/26 20:31:07  gregg
 * cleaning up source, type, case CONSTANTS.
 * let's also make sure we check for source in building objects within our collections.
 *
 * Revision 1.9  2005/05/26 16:52:40  gregg
 * ALL constant
 *
 * Revision 1.8  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.7  2005/05/26 00:11:54  dave
 * missed saves in jtest
 *
 * Revision 1.5  2005/05/25 22:49:22  gregg
 * some dumping
 *
 * Revision 1.4  2005/05/25 20:57:39  gregg
 * getFatAndDeep methods to traverse the tree.
 *
 * Revision 1.3  2005/05/25 17:51:51  gregg
 * one last fix
 *
 * Revision 1.2  2005/05/25 17:46:24  gregg
 * compile fixes
 *
 * Revision 1.1  2005/05/25 17:00:26  gregg
 * initial load
 *
 *
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.eannounce.catalog.ProductId;

/**
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProdStructCollection
    extends CatList implements CatSync {

    private SyncMapCollection m_smc = null;

    /**
     * ProdStructCollection
     *
     */
    public ProdStructCollection(ProdStructCollectionId _cid) {
        super(_cid);
    }

    /**
     * ProdStructCollection
     *
     */
    public ProdStructCollection(ProdStructCollectionId _cid, Catalog _cat) {
        this(_cid);
        get(_cat);
    }

    public static void main(String[] args) {
        // look for logic in the main() method of ProductCollectionId!
    }

    public final String dump(boolean _b) {
        ProdStructCollectionId pscid = getProdStructCollectionId();
        ProductId pid = pscid.getProductId();
        String strHeader = "**ProdStructCollection **\n";
        strHeader += "=== PRODSTRUCT: " + pid.getEntityType() + ":" + pid.getEntityId();
        StringBuffer sb = new StringBuffer();
        sb.append(strHeader);
        if (!_b) {
            sb.append("\n          << " + values().size() + " ProdStructs >>");
            Iterator it = values().iterator();
            while (it.hasNext()) {
                ProdStruct ps = (ProdStruct) it.next();
                sb.append("\n          " + ps.dump(_b));
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
        ReturnStatus returnStatus = new ReturnStatus( -1);

        ProdStructCollectionId pscid = getProdStructCollectionId();

        ProductId pid = pscid.getProductId();
        WorldWideProductId wwpid = pscid.getWorldWideProductId();

        GeneralAreaMapItem gami = pscid.getGami();
        int iSource = pscid.getSource();

        D.ebug(this, D.EBUG_DETAIL,
               "get() - here is pscid..." + ":isByInteval:" + pscid.isByInterval() + ":isFromPDH: " + pscid.isFromPDH() +
               ":isFullImages:" + pscid.isFullImages());

        try {
            if (pscid.isFromCAT()) {

                D.ebug(this, D.EBUG_DETAIL, "get() - pscid isFromCat ...");
                D.ebug(this, D.EBUG_DETAIL, "get() - pscid isFromCat ...again");

                String strEnterprise = gami.getEnterprise();
                int iNLSID = gami.getNLSID();
                String strLocEntityType = pid.getEntityType();
                int iLocEntityID = pid.getEntityId();

                if (pscid.getCollectionType() == ProdStructCollectionId.BY_LOCENTITYTYPE_LOCENTITYID) {
                    try {
                        rs = db.callGBL4017(returnStatus, strEnterprise, strLocEntityType, iLocEntityID, iNLSID);
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
                        rs.close();
                        rs = null;
                        db.commit();
                        db.freeStatement();
                        db.isPending();
                    }

                    for (int i = 0; i < rdrs.size(); i++) {

                        String strFeatureEntityType = rdrs.getColumn(i, 0);
                        int iFeatureEntityId = rdrs.getColumnInt(i, 1);

                        String strStructEntityType = rdrs.getColumn(i, 2);
                        int iStructEntityId = rdrs.getColumnInt(i, 3);
                        D.ebug(this, D.EBUG_SPEW,
                               "strFeatureEntityType:" + strFeatureEntityType + ":iFeatureEntityId:" + iFeatureEntityId +
                               ":strStructEntityType:" + strStructEntityType + ":iStructEntityId:" + iStructEntityId);
                        //
                        // Lets build one in the context of this list
                        // we do this by passing the wwpcid into to wwpid
                        //
                        // this is much better than passing the entire structure as
                        // we currently do in the eannounce object model
                        //
                        ProdStructId psid = new ProdStructId(pid, strStructEntityType, iStructEntityId, strFeatureEntityType,
                            iFeatureEntityId, gami, this.getProdStructCollectionId());
                        ProdStruct ps = new ProdStruct(psid);
                        this.put(ps);
                    }
                }

            }
            else {

                /*
                 * This is the case where we are going by interval and we need
                 * to create a SyncMap for each Root EntityType this guy represents.
                 */
                if (pscid.isByInterval() && pscid.isFromPDH() && pscid.isFullImages()) {
                    //
                    // did we come from a world wide Product? and do we have a
                    //
                    if (wwpid != null) {
                        D.ebug(this, D.EBUG_DETAIL, "get() - PDH,byInterval, FullImage - world wide product id source");
                        if (this.hasSyncMapCollection() && pscid.hasWorldWideProductId()) {
                            this.processSyncMap(_cat);
                        }
                    }
                    else if (pid != null) {
                        D.ebug(this, D.EBUG_DETAIL, "get() - PDH,byInterval, FullImage - product id source");
                        if (this.hasSyncMapCollection() && pscid.hasProductId()) {
                            this.processSyncMap(_cat);
                        }
                    }
                    else {
                        db.debug(D.EBUG_DETAIL,
                                 this.getClass().getName() + ".get() PDH,byInterval, FullImage - *** CANNOT FIND A PRESPECTIVE ***");
                    }
                }
            }

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

        ProdStructCollectionId pscid = this.getProdStructCollectionId();
        GeneralAreaMapItem gami = pscid.getGami();
        WorldWideProductId wwpid = pscid.getWorldWideProductId();
        ProductId pid = pscid.getProductId();
        SyncMapCollection smc = this.getSmc();

        //
        // If I am being tapped by a WorldWideProductId
        //
        if (pscid.hasWorldWideProductId() || pscid.hasProductId()) {
            //
            // for this kind.. we only want collections directly off the model
            // or directly off the WWSEO
            //
            // we will have to determine if its on or Off later
            //
            D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - In WorldWideProductId/ProductId");

            boolean bpassII = false;

            for (int x = 0; x < smc.getCount(); x++) {
                SyncMapItem smi = smc.get(x);

                //D.ebug(this, D.EBUG_DETAIL, "processSyncMap() smi [" + x + "]:" + smi.toString());

                //
                // WorldWideProductId Parent case
                //
                if (pscid.hasWorldWideProductId()) {
                    //
                    // if we are full images .. we only care about the ON stuff!
                    // we will not worry about true net changes now!!!
                    //
                    if (pscid.isFromPDH() && pscid.isFullImages()) {
                        if (smi.getRootType().equals("MODEL")) {
                            if ( (smi.getChildType().equals("PRODSTRUCT") || smi.getChildType().equals("SWPRODSTRUCT")) &&
                                smi.getChildLevel() == 0 && smi.getChildTran().equals("ON")) {
                                D.ebug(this, D.EBUG_SPEW, "WorldWideProduct block:MODEL:processSyncMap() - smi is: " + smi.toString());
                                ProdStructId psid = new ProdStructId(wwpid, smi.getChildType(), smi.getChildID(),
                                    smi.getEntity1Type(), smi.getEntity1ID(), gami, pscid);
                                ProdStruct ps = (ProdStruct)this.get(psid);
                                if (ps == null) {
                                    ps = new ProdStruct(psid);
                                    D.ebug(this, D.EBUG_SPEW,
                                           "WorldWideProduct block:processSyncMap() - making new PS based upon psid of : " + psid);
                                    ps.setSmc(new SyncMapCollection());
                                    this.put(ps);
                                }
                                //
                                // ok.. we need to tuck away the PRODSTRUCT Guy
                                // and any other thing that may be below it...
                                ps.getSmc().add(smi);
                            }
                        }
                        else if (smi.getRootType().equals("WWSEO")) {
                            //
                            // Here we need to pull in the WWSEOPRODSTRUCT VALUE
                            // We need to find another smi. that has prodstruct as the Entity2Type and entity2id.. and
                            // is also a level 0.  This smi must be added to it!
                            //
                            // we can then start pulling off quantities. that are needed.
                            //
                            if ( (smi.getChildType().equals("PRODSTRUCT") || smi.getChildType().equals("SWPRODSTRUCT")) &&
                                smi.getChildLevel() == 0 && smi.getChildTran().equals("ON")) {
                                D.ebug(this, D.EBUG_SPEW, "WorldWideProduct block:WWSEO:processSyncMap() - smi is: " + smi.toString());
                                ProdStructId psid = new ProdStructId(wwpid, smi.getChildType(), smi.getChildID(),
                                    smi.getEntity1Type(), smi.getEntity1ID(), gami, pscid);
                                ProdStruct ps = (ProdStruct)this.get(psid);
                                if (ps == null) {
                                    ps = new ProdStruct(psid);
                                    D.ebug(this, D.EBUG_SPEW,
                                           "WorldWideProduct block:processSyncMap() - making new PS based upon psid of : " + psid);
                                    ps.setSmc(new SyncMapCollection());
                                    this.put(ps);
                                }
                                psid = ps.getProdStructId();
                                //
                                // ok.. we need to tuck away the PRODSTRUCT Guy
                                // and any other thing that may be below it...
                                ps.getSmc().add(smi);
                                //
                                // ok.. lets look to find its WWSEOPRODSTRUCT.  Much More efficient way
                                // but for now.. its ok
                                //
                                for (int z = 0; z < smc.getCount(); z++) {
                                    SyncMapItem smi2 = smc.get(z);
                                    if ((smi2.getChildType().equals("WWSEOPRODSTRUCT") || smi2.getChildType().equals("WWSEOSWPRODSTRUCT"))&&
                                    	smi2.getChildTran().equals("ON") &&
                                        smi2.getEntity2Type().equals(smi.getChildType()) && smi2.getEntity2ID() == smi.getChildID()) {
                                        ps.getSmc().add(smi2);
                                        psid.setBridgeEntityType(smi2.getChildType());
                                        psid.setBridgeEntityId(smi2.getChildID());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } // end WorldWideProduct Case

                //
                // ProductId Parent case
                //
                if (pscid.hasProductId()) {
                    D.ebug(this, D.EBUG_SPEW, "hasProductId == true!!");
                    //
                    // if we are full images .. we only care about the ON stuff!
                    // we will not worry about true net changes now!!!
                    //
                    if (pscid.isFromPDH() && pscid.isFullImages()) {
                        if (smi.getRootType().equals("MODEL")) {
                            if ( (smi.getChildType().equals("PRODSTRUCT") || smi.getChildType().equals("SWPRODSTRUCT")) &&
                                smi.getChildLevel() == 0 && smi.getChildTran().equals("ON")) {
                                D.ebug(this, D.EBUG_SPEW, "Product block:MODEL:processSyncMap() - smi is: " + smi.toString());
                                ProdStructId psid = new ProdStructId(pid, smi.getChildType(), smi.getChildID(), smi.getEntity1Type(),
                                    smi.getEntity1ID(), gami, pscid);
                                ProdStruct ps = (ProdStruct)this.get(psid);
                                if (ps == null) {
                                    ps = new ProdStruct(psid);
                                    D.ebug(this, D.EBUG_SPEW,
                                           "Product block:processSyncMap() - making new PS based upon psid of : " + psid);
                                    ps.setSmc(new SyncMapCollection());
                                    this.put(ps);
                                }
                                //
                                // ok.. we need to tuck away the PRODSTRUCT Guy
                                // and any other thing that may be below it...
                                ps.getSmc().add(smi);

                                // we want these overriders in there too..
                                // 
                                // Adding to tuck away the AVAILs also. We need these to resolve the pubfrom/pubtodates later.
                                // Also adding a check for CHILDTRAN so that only active ones are looked at. RQK 01202008
                        	    for (int y = 0; y < smc.getCount(); y++) {
                					SyncMapItem smi2 = smc.get(y);
                	 	       		// CATLGOR and AVAIL is going to be added in for -Country- level only.
                     		   		if ((((smi2.getEntity2Type().equals("CATLGOR")) || (smi2.getEntity2Type().equals("AVAIL")))
                     		   				&& smi2.getChildTran().equals("ON")
                     		   				&& smi2.getEntity1Type().equals(smi.getChildType())
                     		   				&& smi2.getEntity1ID() == smi.getChildID()) || (smi2.getEntity2Type().equals("AVAIL")
                     		   					&& smi2.getChildTran().equals("ON")
                         		   				&& smi2.getEntity1Type().equals(smi.getRootType())
                         		   				&& smi2.getEntity1ID() == smi.getRootID())) {
										D.ebug(this, D.EBUG_SPEW, "Product block:MODEL:putting CATLGOR or AVAIL for:" + smi2.toString());
									    ps.getSmc().add(smi2);
									    //add Announcement 
									    if(smi2.getEntity2Type().equals("AVAIL")){
									    	for (int z = 0; z < smc.getCount(); z++){
									    		SyncMapItem smi3 = smc.get(z);
									    		if(smi3.getEntity2Type().equals("ANNOUNCEMENT")
									    				&& smi3.getChildTran().equals("ON")
									    				&& smi3.getEntity1Type().equals(smi2.getEntity2Type())
									    				&& smi3.getEntity1ID() == smi2.getEntity2ID()){
									    			D.ebug(this, D.EBUG_SPEW, "Product block:MODEL:putting ANNOUNCEMENT for:" + smi3.toString());
									    			ps.getSmc().add(smi3);
									    		}
									    	}
									    }
									}
								}
                            }
                        }
                        else if (smi.getRootType().equals("LSEO")) {

							D.ebug(this, D.EBUG_SPEW, "Product block:LSEO:processSyncMap() - smi is: " + smi.toString());

                            if ( (smi.getChildType().equals("PRODSTRUCT") || smi.getChildType().equals("SWPRODSTRUCT")) &&
                                smi.getChildLevel() == 0 && smi.getChildTran().equals("ON")) {
                                //D.ebug(this, D.EBUG_SPEW, "Product block:LSEO:processSyncMap() - smi is: " + smi.toString());
                                ProdStructId psid = new ProdStructId(pid, smi.getChildType(), smi.getChildID(), smi.getEntity1Type(),
                                    smi.getEntity1ID(), gami, pscid);
                                ProdStruct ps = (ProdStruct)this.get(psid);
                                if (ps == null) {
                                    ps = new ProdStruct(psid);
                                    D.ebug(this, D.EBUG_SPEW,
                                           "Product block:processSyncMap() - making new PS based upon psid of : " + psid);
                                    ps.setSmc(new SyncMapCollection());
                                    this.put(ps);
                                }
                                // we want these overriders in there too..
                        	    for (int y = 0; y < smc.getCount(); y++) {
                					SyncMapItem smi2 = smc.get(y);
//                					 CATLGOR and AVAIL is going to be added in for -Country- level only.
                     		   		if (((smi2.getEntity2Type().equals("CATLGOR")) || (smi2.getEntity2Type().equals("AVAIL")))
                     		   				&& smi2.getChildTran().equals("ON")
                     		   				&& smi2.getEntity1Type().equals(smi.getChildType())
                     		   				&& smi2.getEntity1ID() == smi.getChildID()) {
										D.ebug(this, D.EBUG_SPEW, "Product block:LSEO:putting  CATLGOR or AVAIL for:" + smi2.toString());
									    ps.getSmc().add(smi2);
									  //add Announcement 
									    if(smi2.getEntity2Type().equals("AVAIL")){
									    	for (int z = 0; z < smc.getCount(); z++){
									    		SyncMapItem smi3 = smc.get(z);
									    		if(smi3.getEntity2Type().equals("ANNOUNCEMENT")
									    				&& smi3.getChildTran().equals("ON")
									    				&& smi3.getEntity1Type().equals(smi2.getEntity2Type())
									    				&& smi3.getEntity1ID() == smi2.getEntity2ID()){
									    			D.ebug(this, D.EBUG_SPEW, "Product block:LSEO:putting ANNOUNCEMENT for:" + smi3.toString());
									    			ps.getSmc().add(smi3);
									    		}
									    	}
									    }
									}
								}
                        	    //
                                // ok.. lets look to find its LSEOPRODSTRUCT or LSEOSWPRODSTRUCT, 
                                // we need to get quantity from these - RQK 03182008
                                for (int z = 0; z < smc.getCount(); z++) {
                                    SyncMapItem smi2 = smc.get(z);
                                    if ((smi2.getChildType().equals("LSEOPRODSTRUCT") || smi2.getChildType().equals("LSEOSWPRODSTRUCT")) &&
                                        smi2.getEntity2Type().equals(smi.getChildType()) && smi2.getEntity2ID() == smi.getChildID() &&
                                        smi2.getChildTran().equals("ON")) {
                                        ps.getSmc().add(smi2);
                                        psid.setBridgeEntityType(smi2.getChildType());
                                        psid.setBridgeEntityId(smi2.getChildID());
                                        break;
                                    }
                                }
                            }
                        }
                        else if (smi.getRootType().equals("LSEOBUNDLE")) {
                            if ( (smi.getChildType().equals("LSEOBUNDLELSEO") /*|| smi.getChildType().equals("SWPRODSTRUCT")*/) &&
                                smi.getChildLevel() == 0 && smi.getChildTran().equals("ON")) {
                                D.ebug(this, D.EBUG_SPEW, "Product block:LSEO:processSyncMap() - smi is: " + smi.toString());
                                ProdStructId psid = new ProdStructId(pid, smi.getEntity1Type(), smi.getEntity1ID(),  // LSEOBUNDLE,  LSEOBUNDLE
                                    smi.getEntity2Type(), smi.getEntity2ID(), gami, pscid); // LSEO,  LSEO
                                ProdStruct ps = (ProdStruct)this.get(psid);
                                if (ps == null) {
                                    ps = new ProdStruct(psid);
                                    D.ebug(this, D.EBUG_SPEW,
                                           "Product block:processSyncMap() - making new PS based upon psid of : " + psid);
                                    ps.setSmc(new SyncMapCollection());
                                    this.put(ps);

                                }

                                // we want these overriders in there too..
                        	    for (int y = 0; y < smc.getCount(); y++) {
                					SyncMapItem smi2 = smc.get(y);
                	 	       		// CATLGOR is going to be added in for -Country- level only.
                     		   		if (smi2.getEntity2Type().equals("CATLGOR")) {
										D.ebug(this, D.EBUG_SPEW, "Product block:LSEOBUNDLE:putting CATLGOR for:" + smi2.toString());
									    ps.getSmc().add(smi2);
									}
                	 	       		// PRODSTRUCTs for LSEOBUNDLEs are actually LSEOBUNDLELSEOs - 03252008
                     		   		if (smi2.getChildType().equals("LSEOBUNDLELSEO") && smi2.getChildTran().equals("ON")) {
										D.ebug(this, D.EBUG_SPEW, "Product block:LSEOBUNDLE:putting LSEOBUNDLELSEO for:" + smi2.toString());
									    ps.getSmc().add(smi2);
									}
								}
                            }
                        }
                    }
                } // end Product case
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

    public final ProdStructCollectionId getProdStructCollectionId() {
        return (ProdStructCollectionId)this.getId();
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
