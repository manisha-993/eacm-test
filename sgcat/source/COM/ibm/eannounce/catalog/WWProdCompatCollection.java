/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.eannounce.catalog.Catalog;
import COM.ibm.opicmpdh.middleware.Stopwatch;

/**
 * This holds a collection of WorldWideProducts
 *
 * How this collection is derived is expressed in the WWProdCompatCollectionId
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WWProdCompatCollection
    extends CatList implements XMLable, CatSync {

    /**
     * FIELD = WWSEO EntityType in eannounce
     */
    public static final String SEO_ENTITYTYPE = "WWSEO";
    /**
     * FIELD - TMF EntityType in eannounce
     */
    public static final String TMF_ENTITYTYPE = "MODEL";
    /**
     * FIELD - LSEOBUNDLE EntityType in eannounce
     */
    public static final String BDL_ENTITYTYPE = "LSEOBUNDLE";

    public static final String MODELCGOS_ENTITYTYPE = "MODELCGOS";

    /**
     * FIELD - VE FOR MODEL
     */

    public static final String TMF_VE = "VEWWCOMPATIBILITY1";
    /**
     * FIELD - VE FOR WWSEO
     */

    public static final String SEO_VE = "VEWWCOMPATIBILITY2";
    /**
     * FIELD - VE FOR LSEOBUNDLE
     */

    public static final String BDL_VE = "VEWWCOMPATIBILITY3";

    /**
     * Holds the answer  to GBL8104
     */
    private SyncMapCollection m_smc = null;

    private static int c_iObjCount = 0;
    private static double c_dEnumSub4 = 0;
    private static double c_d8184TimeTotal = 0;
    private static double c_dRdrsLoop1 = 0;
    private static double c_dEnumLoop = 0;
    private static double c_dCatiPut = 0;
    private static double c_dEnumSub1 = 0;
    private static double c_dEnumSub3 = 0;
    private static double c_dEnumSub5 = 0;
    private static double c_dEnumSub6 = 0;
    private static double c_dEnumSub2 = 0;
    protected static double c_d8502CallCount = 0;
    protected static double c_d8502TotalTimings = 0;
    protected static double c_d8502MaxTiming = 0;
    protected static double c_d8502MinTiming = 0;

    //Added by Guo Bin, 2007-8-03
    private static String SP_SUFFIX = "G";
    /**
     * This is the World Wide Product Collection
     * with nothing in its list the _cid is the CollectionID that instructs
     * it how to fill its list out when its .get method is called
     * @param _cid
     */
    public WWProdCompatCollection(WWProdCompatCollectionId _cid) {
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
     * A simple test of this method
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args[0].equals("1")) {
            WWProdCompatCollection.syncToCatDb();
        }
        else if (args[0].equals("2")) {
            WWProdCompatCollection.idlToCatDb();
        }
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
        sb.append(NEW_LINE + "\tCatId is:" + this.getWWProdCompatCollectionId());
        sb.append(NEW_LINE + "\t---------");
        sb.append(NEW_LINE + "\tWWP");
        sb.append(NEW_LINE + "\t---------");
        it = this.values().iterator();
        i = 1;
        while (it.hasNext()) {
            WWProdCompat wwpc = (WWProdCompat) it.next();
            sb.append(NEW_LINE + "\t" + (i++) + " - " + wwpc);
        }

        return sb.toString();

    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return this.getWWProdCompatCollectionId().toString();
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

        WWProdCompatCollectionId wwpccid = getWWProdCompatCollectionId();
        GeneralAreaMapItem gami = wwpccid.getGami();
        String strEnterprise = gami.getEnterprise();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();

        // D.ebug(D.EBUG_SPEW,
        //       "get() - here is WWProdCompatCollectionId..." + ":isByInteval:" +
        //       wwpccid.isByInterval() + ":isFromPDH: " +
        //       wwpccid.isFromPDH() + ":isFullImages:" + wwpccid.isFullImages());

        try {

            /*
             * This is the case where we are going by interval and we need
             * to create a SyncMap for each Root EntityType this guy represents.
             */
            if (wwpccid.isByInterval() && wwpccid.isFromPDH()) {

                this.processSyncMap(_cat);

            }
            else if (wwpccid.isFromCAT()) {
                try {
                    String strEntityType = wwpccid.getEntityType();
                    int iEntityId = wwpccid.getEntityId();
                    rs = db.callGBL8503(new ReturnStatus( -1), strEnterprise,
                                        strEntityType, iEntityId,
                                        strCountryList);
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
                    String strProdEntityTypeFrom = rdrs.getColumn(0, 0);
                    int iProdEntityIDFrom = rdrs.getColumnInt(0, 1);
                    String strProdEntityTypeTo = rdrs.getColumn(0, 2);
                    int iProdEntityIDTo = rdrs.getColumnInt(0, 3);
                    String strOS_FC = rdrs.getColumn(0, 4);

                    WWProdCompatId wwcid = new WWProdCompatId(
                        strProdEntityTypeFrom, iProdEntityIDFrom,
                        strProdEntityTypeTo,
                        iProdEntityIDTo, strOS_FC, gami, getWWProdCompatCollectionId());
                    WWProdCompat wwpc = new WWProdCompat(wwcid);
                    this.put(wwpc);
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
     * getWWProdCompatCollectionId
     * @return
     */
    public final WWProdCompatCollectionId getWWProdCompatCollectionId() {
        return (WWProdCompatCollectionId)this.getId();
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
        String strTraceBase = "WWProdCompatCollection processSyncMap method ";
        System.out.println(strTraceBase);

        WWProdCompatCollectionId wwpcid = this.getWWProdCompatCollectionId();
        GeneralAreaMapItem gami = wwpcid.getGami();
        SyncMapCollection smc = this.getSmc();

        // GAB - 040606- okay.. first pass is to pull out all single smi's for data which might pertain to multiple prodcompats...
        // Store by root type.
        Hashtable hashSmi = new Hashtable();
        for (int x = 0; x < smc.getCount(); x++) {
            SyncMapItem smi = smc.get(x);
            String strRootType = smi.getRootType();
            int iRootID = smi.getRootID();
            String strEntity2Type = smi.getEntity2Type();
            String strEntity1Type = smi.getEntity1Type();
            int iEntity2ID = smi.getEntity2ID();
            String strChildTran = smi.getChildTran();
            String strChildType = smi.getChildType();
            if (strRootType.equals(BDL_ENTITYTYPE) ||
                strRootType.equals(SEO_ENTITYTYPE) && strChildTran.equals("ON")) {
                //if(strChildType.equals("LSEOBUNDLELSEO") || strChildType.equals("MODELWWSEO")) {
                String strKey = strRootType + iRootID;
                SyncMapCollection smcTmp = (SyncMapCollection) hashSmi.get(
                    strKey);
                if (smcTmp == null) {
                    smcTmp = new SyncMapCollection();
                }
            //    D.ebug(D.EBUG_SPEW,
            //           "putting smi into smcTmp:" + strKey + ":" + smi.dump(false));
                smcTmp.add(smi);
                hashSmi.put(strKey, smcTmp);
                //}
            }

        }
        //

        for (int x = 0; x < smc.getCount(); x++) {
            SyncMapItem smi = smc.get(x);
            D.ebug(D.EBUG_SPEW, "processSyncMap smi: " + smi.dump(false));
            String strRootType = smi.getRootType();
            int iRootID = smi.getRootID();
            String strEntity2Type = smi.getEntity2Type();
            String strEntity1Type = smi.getEntity1Type();
            int iEntity2ID = smi.getEntity2ID();
            int iEntity1ID = smi.getEntity1ID();
            String strChildTran = smi.getChildTran();
            String strKey = strRootType + iRootID;

            String strOS_FC = null;

            boolean bPut = false;
            // 1) case MODEL-MODEL, WWSEO-WWSEO. (different entityids) (VE1, VE2)
            if (strRootType.equals(strEntity2Type) && iRootID != iEntity2ID && strChildTran.equals("ON")) {
                SyncMapCollection smcTmp = (SyncMapCollection) hashSmi.get(strKey);
                if (smcTmp == null) {
                    smcTmp = new SyncMapCollection();
                }

            //    D.ebug(D.EBUG_SPEW,"processSyncMap: IA : checking isOSCompat for:" + smi.toString());
		 
		if (WWProdCompat.isCompatpub(_cat, smi, smcTmp)) {
                    bPut = true;
                }                

                if ((bPut == true) && (WWProdCompat.isOSCompat(_cat, smi, smcTmp))) {
                    bPut = true;
                }
	        else {
		    bPut = false; 
	              } 
               
             //   D.ebug(D.EBUG_SPEW,"processSyncMap: IA : bPut?" + bPut);

                // GAB 040406 - MODEL to itself. Through MODEL-MODELCG-MODELCGOD-MODEL. (VE1)
            }
            else if (strRootType.equals(TMF_ENTITYTYPE) &&
                     strRootType.equals(strEntity2Type) &&
                     iRootID == iEntity2ID &&
                     strEntity1Type.equals(MODELCGOS_ENTITYTYPE) &&
                     strChildTran.equals("ON")) {
           //     D.ebug(D.EBUG_SPEW,
           //            "FOUND a MODEL-MODEL (itself):" + iEntity2ID);
                SyncMapCollection smcTmp = (SyncMapCollection) hashSmi.get(
                    strKey);
                if (smcTmp == null) {
                    smcTmp = new SyncMapCollection();
                }
                if (WWProdCompat.isCompatpub(_cat, smi, smcTmp)) {
                    bPut = true;
                }                

                if ((bPut == true) && (WWProdCompat.isOSCompat(_cat, smi, smcTmp))) {
                    bPut = true;
                }
	        else {
		    bPut = false; 
	              } 
                // MODEL-WWSEO (VE1)
            }
            else if (strRootType.equals(TMF_ENTITYTYPE) &&
                     strEntity2Type.equals(SEO_ENTITYTYPE) &&
                     strChildTran.equals("ON")) {
                SyncMapCollection smcTmp = (SyncMapCollection) hashSmi.get(
                    strKey);
                if (smcTmp == null) {
                    smcTmp = new SyncMapCollection();
                }
                if (WWProdCompat.isCompatpub(_cat, smi, smcTmp)) {
                    bPut = true;
                }                

                if ((bPut == true) && (WWProdCompat.isOSCompat(_cat, smi, smcTmp))) {
                    bPut = true;
                }
	        else {
		    bPut = false; 
	              } 
                // LSEOBUNDLE-WWSEO (VE3)
            }
            else if (strRootType.equals(BDL_ENTITYTYPE) &&
                     strEntity2Type.equals(SEO_ENTITYTYPE) &&
                     strChildTran.equals("ON")) {
                SyncMapCollection smcTmp = (SyncMapCollection) hashSmi.get(
                    strKey);
                if (smcTmp == null) {
                    smcTmp = new SyncMapCollection();
                }
                if (WWProdCompat.isCompatpub(_cat, smi, smcTmp)) {
                    bPut = true;
                }                

                if ((bPut == true) && (WWProdCompat.isOSCompat(_cat, smi, smcTmp))) {
                    bPut = true;
                }
	        else {
		    bPut = false; 
	              } 
            }
            else if (strRootType.equals(SEO_ENTITYTYPE) &&
                    strEntity2Type.equals(BDL_ENTITYTYPE) &&
                    strChildTran.equals("ON")) {
               SyncMapCollection smcTmp = (SyncMapCollection) hashSmi.get(
                   strKey);
               if (smcTmp == null) {
                   smcTmp = new SyncMapCollection();
               }
               if (WWProdCompat.isCompatpub(_cat, smi, smcTmp)) {
                   bPut = true;
               }                

               if ((bPut == true) && (WWProdCompat.isOSCompat(_cat, smi, smcTmp))) {
                   bPut = true;
               }
	        else {
		    bPut = false; 
	              } 
           }
            if (bPut) {
           //     D.ebug(D.EBUG_SPEW, "processSyncMap put smi !!!!!: " + smi.dump(false));

		if(strEntity1Type.equals("MODELCGOS") || strEntity1Type.equals("SEOCGOS")) {
                   EntityItem ei = Catalog.getEntityItem(_cat, strEntity1Type, iEntity1ID);
                   EANFlagAttribute faOS = (EANFlagAttribute) ei.getAttribute("OS");
                   if(faOS != null) {
                     strOS_FC = faOS.getFirstActiveFlagCode();
                   }
		} 
                WWProdCompatId wwpid = new WWProdCompatId(strRootType, iRootID,
                    strEntity2Type, iEntity2ID, strOS_FC, gami,
                    getWWProdCompatCollectionId());
                WWProdCompat wwp = (WWProdCompat)this.get(wwpid);
                if (wwp == null) {
                    wwp = new WWProdCompat(wwpid);

                    c_iObjCount++;

            //        D.ebug(D.EBUG_SPEW, "SETTING new smi on new wwp:" + wwp.dump(false));
                    wwp.setSmc(new SyncMapCollection());
                    this.put(wwp);
                }
                //wwp.setActive(strChildTran.equals("ON"));
            //    D.ebug(D.EBUG_SPEW, "ADDING SMI: " + smi.dump(false));
                wwp.getSmc().add(smi);
            }
        }
    }

    public void deactivateAll(Catalog _cat) {
        String strTraceBase = "WWWProdCompatCollection deactivateAll method ";
        if (Catalog.isDryRun()) {
            return;
        }
        ReturnStatus returnStatus = new ReturnStatus( -1);
        Database db = _cat.getCatalogDatabase();
        WWProdCompatCollectionId wwpcid = this.getWWProdCompatCollectionId();
        GeneralAreaMapItem gami = wwpcid.getGami();
        String strEnterprise = gami.getEnterprise();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();

        SyncMapCollection smc = this.getSmc();
        try {
            Hashtable ht = new Hashtable();

            for (int x = 0; x < smc.getCount(); x++) {
                SyncMapItem smi = smc.get(x);

                String strRootType = smi.getRootType();
                int iRootID = smi.getRootID();
                if (ht.get(strRootType + iRootID) == null) {
                    ht.put(strRootType + iRootID, strRootType + iRootID);
                    D.ebug(D.EBUG_SPEW,
                           strTraceBase + " gbl8989 parms: " + strRootType +
                           ":" + iRootID);
                    try {
                        db.callGBL8989(returnStatus, strEnterprise,
                                       strCountryList, strRootType, iRootID, iNLSID);
                    }
                    finally {
                        db.commit();
                        db.freeStatement();
                        db.isPending();
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
     * This is the guy who moves data between the PDH and the CatDb
     *
     */
    public static void syncToCatDb() {

		Stopwatch sw1 = new Stopwatch();
		sw1.start();

        SyncMapCollection smcOuter = null;
        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        
        //
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(WWProdCompatCollection.class));
//        }
//        catch (InterruptedException ex1) {
//            ex1.printStackTrace();
//            System.exit( -1);
//        }
//        catch (IOException ex1) {
//            ex1.printStackTrace();
//            System.exit( -1);
//        }

        try {

            Class mclass = WWProdCompatCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

            Catalog cat = new Catalog();
            Database db = cat.getCatalogDatabase();
            GeneralAreaMap gam = cat.getGam();
            Profile prof = cat.getCatalogProfile();
            String strEnterprise = cat.getEnterprise();
            String strRoleCode = prof.getRoleCode();

            CatalogInterval cati = new CatalogInterval(mclass, cat);
            prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

            // Dump of the smc
            //System.out.println(smc1.toString());

            gamp = gam.lookupGeneralArea(strGeneralArea);

            ///
            ///
            ///
            for (int icycle = 0; icycle < 3; icycle++) {

                //
                // lets clear it out
                //
                if (smcOuter != null) {
                    smcOuter.clearAll();
                }

                //
                // Clear out entity items between runs
                //
                cat.resetEntityItemList();

                if (icycle == 0) {
                    // TMF section
                    strEntityType = WWProdCompatCollection.TMF_ENTITYTYPE;
                    strVE = WWProdCompatCollection.TMF_VE;
                    //smcOuter = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
                }

                else if (icycle == 1) {

                    // SEO section
                    strEntityType = WWProdCompatCollection.SEO_ENTITYTYPE;
                    strVE = WWProdCompatCollection.SEO_VE;
                    //smcOuter = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
                }
                else if (icycle == 2) {

                    // BUNDLE section
                    strEntityType = WWProdCompatCollection.BDL_ENTITYTYPE;
                    strVE = WWProdCompatCollection.BDL_VE;
                    //smcOuter = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
                }
				// add by houjie&liubing 2007-09-28 : check NLSID for WWProdCompat only!                
                int iNLSSave[]=new int[1];
                iNLSSave[0] = CatalogProperties.getSaveNLSID(WWProdCompatCollection.class,WWProdCompat.class, strVE);
                D.ebug(D.EBUG_SPEW,"WWProdCompatCollection.WWProdCompat."+strVE+"_nlssave="+iNLSSave[0]);
                                
                //
                // Newest version of chunking
                //

                //
                // We want to load up trsnetter pass 1 here via gbl8184....!!!
                // We might want to abstract this call to SyncMapCollection later on....
                //
                Stopwatch sw4 = new Stopwatch();
                sw4.start();

                int iSessionID = db.getNewSessionID();
                List sessionIdList = new ArrayList();
                sessionIdList.add(new Integer(iSessionID));
                try {

                    db.callGBL8184(
                        new ReturnStatus( -1),
                        iSessionID,
                        strEnterprise,
                        strEntityType,
                        strVE,
                        strRoleCode,
                        cati.getStartDate(),
                        cati.getEndDate(),
                        9, "", -1, 1);
                }

                finally {
                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }

                int iChunk = CatalogProperties.getIDLChunkSize(mclass, strVE);

                try {
                    rs = db.callGBL9012(new ReturnStatus( -1), strEnterprise,
                                        strEntityType, iSessionID, iChunk);
                    rdrs = new ReturnDataResultSet(rs);
                }

                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }

                int iFloor = 0;
                int iCeiling = 0;
                int iStart = 0;
                int iEnd = 0;

                c_d8184TimeTotal+=sw4.getFinish();
                //
                //

                for (int x = 0; x < rdrs.size(); x++) {

                    Stopwatch sw5 = new Stopwatch();
                    sw5.start();

                    iFloor = rdrs.getColumnInt(x, 0);
                    iCeiling = rdrs.getColumnInt(x, 1);
                    iStart = rdrs.getColumnInt(x, 2);
                    iEnd = rdrs.getColumnInt(x, 3);

                    D.ebug(D.EBUG_SPEW,
                           "GBL9012:et" + strEntityType + ":floor:" + iFloor +
                           ":ceiling:" + iCeiling + ":start:" + iStart +
                           ":finish:" + iEnd);
                    //
                    // lets clear it out
                    //

                    D.ebug(D.EBUG_DETAIL, "OUTER - Pre-gc Cleanup.." + D.etermineMemory());
                    // Catalog.resetLists();
                    System.gc();
                    D.ebug(D.EBUG_DETAIL, "OUTER - Post gc -Cleanup.." + D.etermineMemory());

                    if (smcOuter != null) {
                        smcOuter.clearAll();

                    }
                    System.gc();
                    D.ebug(D.EBUG_DETAIL, " POST SMC Clear-Cleanup.." + D.etermineMemory());

                    smcOuter = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd, iSessionID, sessionIdList);

                    D.ebug(D.EBUG_SPEW,"BOB smcOuter (" + strVE + ") size is:" + smcOuter.getCount());

                    ////
                    ////
                    ////
                    c_dRdrsLoop1+=sw5.getFinish();


                    en = gamp.elements();
                    if (!en.hasMoreElements()) {
                        D.ebug(D.EBUG_WARN, "no gami to find!!!");
                    }

                    while (en.hasMoreElements()) {

                        /// THIS Loop's timer
                        Stopwatch sw3 = new Stopwatch();
                        sw3.start();

                        // SUB 1 START
                        Stopwatch sw8 = new Stopwatch();
                        sw8.start();

                        Iterator it = null;

                        GeneralAreaMapItem gami = (GeneralAreaMapItem) en.
                            nextElement();
						// add by houjie&liubing 2007-09-28 : check NLSID for WWProdCompat only!
                        if(!(iNLSSave[0]==0 || iNLSSave[0]==gami.getNLSID())){
                            D.ebug(D.EBUG_SPEW,
                                    ">>Skipping WWProdCompat save ,Targeting NLSID of " +
                                    iNLSSave[0] + " not " + gami.getNLSID());                            		
                        	continue;
                        }                            
                        //
                        // Lets set the NLSItem we need to be working in right now
                        //
                        Vector vct = prof.getReadLanguages();
                        for (int i = 0; i < vct.size(); i++) {
                            NLSItem nlsi = (NLSItem) vct.elementAt(i);
                            if (nlsi.getNLSID() == gami.getNLSID()) {
                                prof.setReadLanguage(nlsi);
                                break;
                            }
                        }

                        WWProdCompatCollectionId wwpcid = new
                            WWProdCompatCollectionId(cati, gami,
                            CollectionId.PDH_SOURCE,
                            CollectionId.FULL_IMAGES);

                        WWProdCompatCollection wwpc = new
                            WWProdCompatCollection(wwpcid);
                        wwpc.setSmc(smcOuter);
                        wwpc.deactivateAll(cat);

                        c_dEnumSub1+=sw8.getFinish();
                        // SUB 1 FINISH


                        // SUB 2 START
                        Stopwatch sw7 = new Stopwatch();
                        sw7.start();
                        wwpc.get(cat);
                        c_dEnumSub2+=sw7.getFinish();
                        // SUB 2 FINISH

                        // SUB 3 START
                        Stopwatch sw9 = new Stopwatch();
                        sw9.start();

                        //
                        // ok.. we have stubbed it out. lets make them fat!
                        //
                        it = wwpc.values().iterator();

                        int icount = 0;


                        while (it.hasNext()) {

                            icount++;

                            WWProdCompat wwp = (WWProdCompat) it.next();

                            //
                            // lets take a look at the sync map col
                            SyncMapCollection smc = wwp.getSmc();
                            D.ebug(D.EBUG_SPEW, smc.toString());

                            D.ebug(D.EBUG_SPEW,
                                   "syncToCatDb() .. getting wwp and its collections... " +
                                   wwp.toString());


                            c_dEnumSub3+=sw9.getFinish();
                            // SUB 3 FINISH


                            // SUB 4 START
                            Stopwatch sw2 = new Stopwatch();
                            sw2.start();
                            wwp.get(cat);
                            double dTemp = sw2.getFinish();
                            c_dEnumSub4+=dTemp;
                            D.ebug(D.EBUG_SPEW,"BOB timing get for " + wwp.toString() + ": " + dTemp + " ms");
                            // SUB 4 FINSIH

                            // SUB 5 START
                            Stopwatch sw10 = new Stopwatch();
                            sw10.start();

                            D.ebug(D.EBUG_SPEW,"syncToCatDb() .. updating the wwp to the CatDb... " + wwp.toString());
                            wwp.put(cat, false);

                            if (icount == iChunk) {
                                db.commit();
                                icount = 0;
                            }

                            c_dEnumSub5+=sw10.getFinish();
                            // SUB 5 FINISH

                        }


                        // SUB 6 START
                        Stopwatch sw11 = new Stopwatch();
                        sw11.start();

                        wwpc.generateXML();
                        db.commit();

						c_dEnumSub6+=sw11.getFinish();
						// SUB 6 FINISH


                        c_dEnumLoop+= sw3.getFinish();
                        // TOTAL loop finish

                    }
                }
                
//              Let delete the temptables by sessionID
                DatabaseExt dbe = new DatabaseExt(db);
                for (int i = 0; i < sessionIdList.size(); i++) {
                    int sessionId = ((Integer) sessionIdList.get(i)).intValue();
                    try {
                        dbe.callGBL8109A(new ReturnStatus(-1), sessionId,
                                strEnterprise);
                    } catch (MiddlewareException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            db.commit();
                            db.freeStatement();
                            db.isPending();
                        } catch (SQLException _ex) {
                            _ex.printStackTrace();
                        }
                    }
                }
                try {
                    Catalog.execUnixShell(CatalogProperties.getIDLTempTableReorgScript(ProductStructCollection.class));
                }
                catch (InterruptedException ex1) {
                    ex1.printStackTrace();
                    System.exit( -1);
                }
                catch (IOException ex1) {
                    ex1.printStackTrace();
                    System.exit( -1);
                }
            }

            Stopwatch sw6 = new Stopwatch();
            sw6.start();

            cati.put(cat);

            db.close();

            c_dCatiPut += sw6.getFinish();

        }
        catch (MiddlewareException ex) {
            ex.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb ================ <timings> ================== ");
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb SP 8184/9102 total time:" + c_d8184TimeTotal + " ms");
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 1st part of rdrs loop total time:" + c_dRdrsLoop1 + " ms");
		D.ebug(D.EBUG_SPEW,"BOB syncToCatDb Enumerator loop total time:" + c_dEnumLoop + " ms");
		D.ebug(D.EBUG_SPEW,"BOB syncToCatDb final cati.put:" + c_dCatiPut + " ms");

		D.ebug(D.EBUG_SPEW,"BOB syncToCatDb");

		D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub1:" + c_dEnumSub1 + " ms");
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub2:" + c_dEnumSub2 + " ms");
		D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub3:" + c_dEnumSub3 + " ms");
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub4:" + c_dEnumSub4 + " ms");
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub5:" + c_dEnumSub5 + " ms");
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub6:" + c_dEnumSub6 + " ms");
        double dEnumVerTot = c_dEnumSub1 + c_dEnumSub2 + c_dEnumSub3 + c_dEnumSub4 + c_dEnumSub5 + c_dEnumSub6;
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb verify enum totals:" + dEnumVerTot + " ms");

        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb");
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 call count:" + c_d8502CallCount);
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 total time:" + c_d8502TotalTimings);
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 Max time:" + c_d8502MaxTiming);
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 Min time:" + c_d8502MinTiming);
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 Avg time:" + (c_d8502TotalTimings/c_d8502CallCount));

        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb");
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb [" + c_iObjCount + " items] took:" + sw1.finish() );
        D.ebug(D.EBUG_SPEW,"BOB syncToCatDb ================ </timings> ================== ");
    }
    
    /**
     * parallelSyncToCatDb
     * 
     * Added by  Guo Bin, 2007-08-03 For break-up stored procedures based on
     * Collection
     */
    public static void parallelSyncToCatDb() {

		Stopwatch sw1 = new Stopwatch();
		sw1.start();

        SyncMapCollection smcOuter = null;
        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;

        try {

            Class mclass = WWProdCompatCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

            Catalog cat = new Catalog();
            Database db = cat.getCatalogDatabase();
            GeneralAreaMap gam = cat.getGam();
            Profile prof = cat.getCatalogProfile();
            String strEnterprise = cat.getEnterprise();
            String strRoleCode = prof.getRoleCode();
            //Added by Guo Bin, 2007-08-03
            DatabaseExt dbe = new DatabaseExt(db);
            
            CatalogInterval cati = new CatalogInterval(mclass, cat);
            prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

            // Dump of the smc
            //System.out.println(smc1.toString());

            gamp = gam.lookupGeneralArea(strGeneralArea);

            ///
            ///
            ///
            for (int icycle = 0; icycle < 3; icycle++) {

                //
                // lets clear it out
                //
                if (smcOuter != null) {
                    smcOuter.clearAll();
                }

                //
                // Clear out entity items between runs
                //
                cat.resetEntityItemList();

                if (icycle == 0) {
                    // TMF section
                    strEntityType = WWProdCompatCollection.TMF_ENTITYTYPE;
                    strVE = WWProdCompatCollection.TMF_VE;
                    //smcOuter = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
                }

                else if (icycle == 1) {

                    // SEO section
                    strEntityType = WWProdCompatCollection.SEO_ENTITYTYPE;
                    strVE = WWProdCompatCollection.SEO_VE;
                    //smcOuter = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
                }
                else if (icycle == 2) {

                    // BUNDLE section
                    strEntityType = WWProdCompatCollection.BDL_ENTITYTYPE;
                    strVE = WWProdCompatCollection.BDL_VE;
                    //smcOuter = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
                }
				// add by houjie&liubing 2007-09-28 : check NLSID for WWProdCompat only!                
                int iNLSSave[]=new int[1];
                iNLSSave[0] = CatalogProperties.getSaveNLSID(WWProdCompatCollection.class,WWProdCompat.class, strVE);
                D.ebug(D.EBUG_SPEW,"WWProdCompatCollection.WWProdCompat."+strVE+"_nlssave="+iNLSSave[0]);
                
                //
                // Newest version of chunking
                //

                //
                // We want to load up trsnetter pass 1 here via gbl8184....!!!
                // We might want to abstract this call to SyncMapCollection later on....
                //
                Stopwatch sw4 = new Stopwatch();
                sw4.start();

                int iSessionID = db.getNewSessionID();
                try {

                    dbe.callGBL8184(
                        new ReturnStatus( -1),
                        iSessionID,
                        strEnterprise,
                        strEntityType,
                        strVE,
                        strRoleCode,
                        cati.getStartDate(),
                        cati.getEndDate(),
                        9, "", -1, 1,SP_SUFFIX);
                }

                finally {
                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }

                int iChunk = CatalogProperties.getIDLChunkSize(mclass, strVE);

                try {
                    rs = dbe.callGBL9012(new ReturnStatus( -1), strEnterprise,
                                        strEntityType, iSessionID, iChunk,SP_SUFFIX);
                    rdrs = new ReturnDataResultSet(rs);
                }

                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }

                int iFloor = 0;
                int iCeiling = 0;
                int iStart = 0;
                int iEnd = 0;

                c_d8184TimeTotal+=sw4.getFinish();
                //
                //

                for (int x = 0; x < rdrs.size(); x++) {

                    Stopwatch sw5 = new Stopwatch();
                    sw5.start();

                    iFloor = rdrs.getColumnInt(x, 0);
                    iCeiling = rdrs.getColumnInt(x, 1);
                    iStart = rdrs.getColumnInt(x, 2);
                    iEnd = rdrs.getColumnInt(x, 3);

                //    D.ebug(D.EBUG_SPEW,
                //           "GBL9012:et" + strEntityType + ":floor:" + iFloor +
                //           ":ceiling:" + iCeiling + ":start:" + iStart +
                //           ":finish:" + iEnd);
                    //
                    // lets clear it out
                    //

                //    D.ebug(D.EBUG_DETAIL, "OUTER - Pre-gc Cleanup.." + D.etermineMemory());
                    // Catalog.resetLists();
                    System.gc();
                //    D.ebug(D.EBUG_DETAIL, "OUTER - Post gc -Cleanup.." + D.etermineMemory());

                    if (smcOuter != null) {
                        smcOuter.clearAll();

                    }
                    System.gc();
                 //   D.ebug(D.EBUG_DETAIL, " POST SMC Clear-Cleanup.." + D.etermineMemory());

                    smcOuter = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd, iSessionID,SP_SUFFIX);

                 //   D.ebug(D.EBUG_SPEW,"BOB smcOuter (" + strVE + ") size is:" + smcOuter.getCount());

                    ////
                    ////
                    ////
                    c_dRdrsLoop1+=sw5.getFinish();


                    en = gamp.elements();
                    if (!en.hasMoreElements()) {
                 //       D.ebug(D.EBUG_WARN, "no gami to find!!!");
                    }

                    while (en.hasMoreElements()) {

                        /// THIS Loop's timer
                        Stopwatch sw3 = new Stopwatch();
                        sw3.start();

                        // SUB 1 START
                        Stopwatch sw8 = new Stopwatch();
                        sw8.start();

                        Iterator it = null;

                        GeneralAreaMapItem gami = (GeneralAreaMapItem) en.
                            nextElement();
						// add by houjie&liubing 2007-09-28 : check NLSID for WWProdCompat only!
                        if(!(iNLSSave[0]==0 || iNLSSave[0]==gami.getNLSID())){ 
                            D.ebug(D.EBUG_SPEW,
                                    ">>Skipping WWProdCompat save ,Targeting NLSID of " +
                                    iNLSSave[0] + " not " + gami.getNLSID());                             	
                        	continue;
                        }                            
                        
//                        System.out.println("GAMI" + gami);
//                        
//                        if (gami.getNLSID() != 1) { 
//                        	System.out.println("since nlsid not 1, we are skipping this one");
//                        	continue;
//                        }
                        
                        //
                        // Lets set the NLSItem we need to be working in right now
                        //
                        Vector vct = prof.getReadLanguages();
                        for (int i = 0; i < vct.size(); i++) {
                            NLSItem nlsi = (NLSItem) vct.elementAt(i);
                            if (nlsi.getNLSID() == gami.getNLSID()) {
                                prof.setReadLanguage(nlsi);
                                break;
                            }
                        }

                        WWProdCompatCollectionId wwpcid = new
                            WWProdCompatCollectionId(cati, gami,
                            CollectionId.PDH_SOURCE,
                            CollectionId.FULL_IMAGES);

                        WWProdCompatCollection wwpc = new
                            WWProdCompatCollection(wwpcid);
                        wwpc.setSmc(smcOuter);
                        wwpc.deactivateAll(cat);

                        c_dEnumSub1+=sw8.getFinish();
                        // SUB 1 FINISH


                        // SUB 2 START
                        Stopwatch sw7 = new Stopwatch();
                        sw7.start();
                        wwpc.get(cat);
                        c_dEnumSub2+=sw7.getFinish();
                        // SUB 2 FINISH

                        // SUB 3 START
                        Stopwatch sw9 = new Stopwatch();
                        sw9.start();

                        //
                        // ok.. we have stubbed it out. lets make them fat!
                        //
                        it = wwpc.values().iterator();

                        int icount = 0;


                        while (it.hasNext()) {

                            icount++;

                            WWProdCompat wwp = (WWProdCompat) it.next();

                            //
                            // lets take a look at the sync map col
                            SyncMapCollection smc = wwp.getSmc();
                     //       D.ebug(D.EBUG_SPEW, smc.toString());

                     //       D.ebug(D.EBUG_SPEW,
                     //              "syncToCatDb() .. getting wwp and its collections... " +
                     //              wwp.toString());


                            c_dEnumSub3+=sw9.getFinish();
                            // SUB 3 FINISH


                            // SUB 4 START
                            Stopwatch sw2 = new Stopwatch();
                            sw2.start();
                            wwp.get(cat);
                            double dTemp = sw2.getFinish();
                            c_dEnumSub4+=dTemp;
                     //       D.ebug(D.EBUG_SPEW,"BOB timing get for " + wwp.toString() + ": " + dTemp + " ms");
                            // SUB 4 FINSIH

                            // SUB 5 START
                            Stopwatch sw10 = new Stopwatch();
                            sw10.start();

                     //       D.ebug(D.EBUG_SPEW,"syncToCatDb() .. updating the wwp to the CatDb... " + wwp.toString());
                            wwp.put(cat, false);

                            if (icount == iChunk) {
                                db.commit();
                                icount = 0;
                            }

                            c_dEnumSub5+=sw10.getFinish();
                            // SUB 5 FINISH

                        }


                        // SUB 6 START
                        Stopwatch sw11 = new Stopwatch();
                        sw11.start();

                        wwpc.generateXML();
                        db.commit();

						c_dEnumSub6+=sw11.getFinish();
						// SUB 6 FINISH


                        c_dEnumLoop+= sw3.getFinish();
                        // TOTAL loop finish

                    }
                }
            }

            Stopwatch sw6 = new Stopwatch();
            sw6.start();

            cati.put(cat);

            db.close();

            c_dCatiPut += sw6.getFinish();

        }
        catch (MiddlewareException ex) {
            ex.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb ================ <timings> ================== ");
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb SP 8184/9102 total time:" + c_d8184TimeTotal + " ms");
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 1st part of rdrs loop total time:" + c_dRdrsLoop1 + " ms");
	//	D.ebug(D.EBUG_SPEW,"BOB syncToCatDb Enumerator loop total time:" + c_dEnumLoop + " ms");
	//	D.ebug(D.EBUG_SPEW,"BOB syncToCatDb final cati.put:" + c_dCatiPut + " ms");

	//	D.ebug(D.EBUG_SPEW,"BOB syncToCatDb");

	//	D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub1:" + c_dEnumSub1 + " ms");
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub2:" + c_dEnumSub2 + " ms");
	//	D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub3:" + c_dEnumSub3 + " ms");
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub4:" + c_dEnumSub4 + " ms");
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub5:" + c_dEnumSub5 + " ms");
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb enum sub6:" + c_dEnumSub6 + " ms");
        double dEnumVerTot = c_dEnumSub1 + c_dEnumSub2 + c_dEnumSub3 + c_dEnumSub4 + c_dEnumSub5 + c_dEnumSub6;
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb verify enum totals:" + dEnumVerTot + " ms");

    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb");
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 call count:" + c_d8502CallCount);
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 total time:" + c_d8502TotalTimings);
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 Max time:" + c_d8502MaxTiming);
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 Min time:" + c_d8502MinTiming);
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb 8502 Avg time:" + (c_d8502TotalTimings/c_d8502CallCount));

    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb");
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb [" + c_iObjCount + " items] took:" + sw1.finish() );
    //    D.ebug(D.EBUG_SPEW,"BOB syncToCatDb ================ </timings> ================== ");
    }

    public static void idlToCatDb() {

        SyncMapCollection smc1 = null;
        SyncMapCollection smc2 = null;
        SyncMapCollection smc3 = null;

        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        
        //
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(WWProdCompatCollection.class));
//        }
//        catch (InterruptedException ex1) {
//            ex1.printStackTrace();
//            System.exit( -1);
//        }
//        catch (IOException ex1) {
//            ex1.printStackTrace();
//            System.exit( -1);
//        }

        try {

            Class mclass = WWProdCompatCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

            Catalog cat = new Catalog();
            String strEnterprise = cat.getEnterprise();
            Database db = cat.getCatalogDatabase();
            Database pdhdb = cat.getPDHDatabase();
            Profile prof = cat.getCatalogProfile();

            GeneralAreaMap gam = cat.getGam();

            CatalogInterval cati = new CatalogInterval(mclass, cat);
            prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

            //
            // OK lets do this loop for real
            // we will make two passes..
            //

            //
            // lets temperaroly look at WWSEO's only
            //
            for (int icycle = 0; icycle < 3; icycle++) {
                if (icycle == 0) {
                    strEntityType = WWProdCompatCollection.SEO_ENTITYTYPE;
                    strVE = WWProdCompatCollection.SEO_VE;
                }
                else if (icycle == 1) {
                    strEntityType = WWProdCompatCollection.TMF_ENTITYTYPE;
                    strVE = WWProdCompatCollection.TMF_VE;
                }
                else {
                    strEntityType = WWProdCompatCollection.BDL_ENTITYTYPE;
                    strVE = WWProdCompatCollection.BDL_VE;
                }
				// add by houjie&liubing 2007-09-28 : check NLSID for WWProdCompat only!                
                int iNLSSave[]=new int[1];
                iNLSSave[0] = CatalogProperties.getSaveNLSID(WWProdCompatCollection.class,WWProdCompat.class, strVE);
                D.ebug(D.EBUG_SPEW,"WWProdCompatCollection.WWProdCompat."+strVE+"_nlssave="+iNLSSave[0]);
                //
                // Newest version of chunking
                //
                int iChunk = 1000;
                try {
                    rs = pdhdb.callGBL9010(new ReturnStatus( -1), strEnterprise,
                                           strEntityType, iChunk);
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    pdhdb.commit();
                    pdhdb.freeStatement();
                    pdhdb.isPending();
                }

                int iFloor = 0;
                int iCeiling = 0;
                int iStart = 0;
                int iEnd = 0;
                for (int x = 0; x < rdrs.size(); x++) {

                    iFloor = rdrs.getColumnInt(x, 0);
                    iCeiling = rdrs.getColumnInt(x, 1);
                    iStart = rdrs.getColumnInt(x, 2);
                    iEnd = rdrs.getColumnInt(x, 3);
                    D.ebug(D.EBUG_SPEW,
                           "GBL9010:et" + strEntityType + ":floor:" + iFloor +
                           ":ceiling:" + iCeiling + ":start:" + iStart +
                           ":finish:" + iEnd);

                    smc1 = new SyncMapCollection(cat, strVE, strEntityType,
                                                 iStart, iEnd);

                    gamp = gam.lookupGeneralArea(strGeneralArea);
                    en = gamp.elements();

                    if (!en.hasMoreElements()) {
                        D.ebug(D.EBUG_WARN, "no gami to find!!!");
                    }

                    while (en.hasMoreElements()) {

                        Iterator it = null;

                        GeneralAreaMapItem gami = (GeneralAreaMapItem) en.
                            nextElement();
						// add by houjie&liubing 2007-09-28 : check NLSID for WWProdCompat only!
                        if(!(iNLSSave[0]==0 || iNLSSave[0]==gami.getNLSID())){ 
                            D.ebug(D.EBUG_SPEW,
                                    ">>Skipping WWProdCompat save ,Targeting NLSID of " +
                                    iNLSSave[0] + " not " + gami.getNLSID());                             	
                        	continue;
                        }                        
                        //
                        // Lets set the NLSItem we need to be working in right now
                        //
                        Vector vct = prof.getReadLanguages();
                        for (int i = 0; i < vct.size(); i++) {
                            NLSItem nlsi = (NLSItem) vct.elementAt(i);
                            if (nlsi.getNLSID() == gami.getNLSID()) {
                                prof.setReadLanguage(nlsi);
                                break;
                            }
                        }

                        WWProdCompatCollectionId wwpccid = new
                            WWProdCompatCollectionId(cati, gami,
                            CollectionId.PDH_SOURCE,
                            CollectionId.FULL_IMAGES);

                        WWProdCompatCollection wwpcc = new
                            WWProdCompatCollection(wwpccid);
                        wwpcc.setSmc(smc1);
                        //
                        // Lets go fill this out!
                        //
                        wwpcc.get(cat);
                        //
                        // ok.. we have stubbed it out. lets make them fat!
                        //
                        int icount = 0;

                        it = wwpcc.values().iterator();
                        while (it.hasNext()) {

                            icount++;

                            WWProdCompat wwpc = (WWProdCompat) it.next();
                            //
                            // lets take a look at the sync map col
                            SyncMapCollection smc = wwpc.getSmc();
                            D.ebug(D.EBUG_SPEW, smc.toString());

                            D.ebug(D.EBUG_SPEW,
                                   "idlToCatDb() .. getting wwpc and its collections ... " +
                                   wwpc.toString());

                            wwpc.get(cat);

                            //
                            // Now.. lets
                            //
                            D.ebug(D.EBUG_SPEW,
                                   "idlToCatDb() .. updating the wwpc to the CatDb... " +
                                   wwpc.toString());
                            wwpc.put(cat, false);

                            if (icount == iChunk) {
                                db.commit();
                                D.ebug(D.EBUG_SPEW,
                                       "idlToCatDb() *** COMMITING ***");
                                icount = 0;
                            }

                        }

                        //
                        // ok.. lets see what we get!
                        //
                        wwpcc.generateXML();
                        db.commit();

                    }

                }
            }

            //
            // o.k.  we are now done
            //
            cati.put(cat);
            db.close();

        }
        catch (MiddlewareException ex) {
            ex.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * First attempt at an XML writer usage - This sends the output to
     * Standard out for now.
     */
    public void generateXML() {

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
 * $Log: WWProdCompatCollection.java,v $
 * Revision 1.36  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.8  2009/11/18 18:15:24  rick
 * schooner project update to send right side LSEOBUNDLE
 *
 * Revision 1.7  2007/10/29 09:32:20  jingb
 * Change code in method syncToCatDb():
 * delete the temp table data by sessionID
 *
 * Revision 1.6  2007/10/09 08:11:48  sulin
 * no message
 *
 * Revision 1.5  2007/09/28 07:42:18  jiehou
 * *** empty log message ***
 *
 * Revision 1.4  2007/09/28 06:54:29  jiehou
 * *** empty log message ***
 *
 * Revision 1.3  2007/09/13 05:54:53  sulin
 * no message
 *
 * Revision 1.2  2007/09/10 06:45:43  sulin
 * no message
 *
 * Revision 1.1.1.1  2007/06/05 02:09:37  jingb
 * no message
 *
 * Revision 1.32  2007/01/30 22:50:06  rick
 * Change for os_fc to be part of the primary key.
 *
 * Revision 1.31  2007/01/18 14:49:46  rick
 * MN fix for compatpubflg
 *
 * Revision 1.30  2006/11/16 20:59:23  gregg
 * fix timing info
 *
 * Revision 1.29  2006/11/16 20:55:49  gregg
 * timings for put
 *
 * Revision 1.28  2006/11/16 20:34:37  gregg
 * more
 *
 * Revision 1.27  2006/11/16 20:24:24  gregg
 * more
 *
 * Revision 1.26  2006/11/16 19:14:33  gregg
 * more
 *
 * Revision 1.25  2006/11/16 18:55:41  gregg
 * more timings
 *
 * Revision 1.24  2006/11/16 18:34:10  gregg
 * traces of timings in sync
 *
 * Revision 1.23  2006/11/15 23:07:57  gregg
 * more detail
 *
 * Revision 1.22  2006/11/15 23:01:19  gregg
 * more debugs for preformance analysis
 *
 * Revision 1.21  2006/11/15 22:34:00  gregg
 * timings for debug
 *
 * Revision 1.20  2006/10/19 17:51:05  gregg
 * debug
 *
 * Revision 1.19  2006/08/31 20:43:24  gregg
 * nlsid to 8989
 *
 * Revision 1.18  2006/08/28 20:07:26  dave
 * ok.. minor memory fixes
 *
 * Revision 1.17  2006/08/25 21:19:21  gregg
 * chunky monkey
 * +special bonus bug fixing and super memory bonus saver enhancement!
 *  (aka remove vestigial wwproductcollection code in syncToCatDb)
 *
 * Revision 1.16  2006/07/27 18:07:47  gregg
 * icycle in sync
 *
 * Revision 1.15  2006/07/11 18:25:14  gregg
 * pulling back debugs to spew
 *
 * Revision 1.14  2006/04/13 22:21:01  gregg
 * compat filtering.
 *
 * Revision 1.13  2006/04/07 22:17:19  gregg
 * fix
 *
 * Revision 1.12  2006/04/07 22:16:29  gregg
 * fixes
 *
 * Revision 1.11  2006/04/07 18:12:26  gregg
 * warning:committing from j-builder
 *
 * Revision 1.10  2006/04/06 23:14:46  gregg
 * getting to compile .. working on rev 0331 spec changes
 *
 * Revision 1.9  2006/04/04 22:47:51  gregg
 * fix
 *
 * Revision 1.8  2006/04/04 22:31:50  gregg
 * WWProdCompat Filtering (033106 rev spec) pt. I:TMF
 *
 * Revision 1.7  2006/04/04 17:17:45  gregg
 * more
 *
 * Revision 1.6  2006/04/04 17:06:06  gregg
 * fix
 *
 * Revision 1.5  2006/04/04 17:03:38  gregg
 * compat MODEL to itself work
 *
 * Revision 1.4  2006/04/04 16:30:01  gregg
 * debug
 *
 * Revision 1.3  2006/04/03 18:33:48  gregg
 * more debugs
 *
 * Revision 1.2  2006/03/31 20:50:06  gregg
 * for net type products, prodstructs beneath are now dubbed "full"
 *
 * Revision 1.1.1.1  2006/03/30 17:36:32  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.19  2006/03/29 20:30:58  gregg
 * allow prods to be compat with themselves
 *
 * Revision 1.18  2006/02/06 18:57:13  joan
 * CR2340
 *
 * Revision 1.17  2006/01/25 04:15:42  dave
 * <No Comment Entered>
 *
 * Revision 1.16  2005/12/19 20:27:54  joan
 * fixes
 *
 * Revision 1.15  2005/12/16 19:36:29  joan
 * fixes
 *
 * Revision 1.14  2005/12/16 17:08:00  joan
 * add some traces
 *
 * Revision 1.13  2005/11/22 16:57:36  joan
 * work on idl
 *
 * Revision 1.12  2005/10/25 04:09:24  dave
 * curtailing smc's to spew
 *
 * Revision 1.11  2005/10/25 02:52:21  dave
 * commenting out some debugs
 *
 * Revision 1.10  2005/10/12 23:22:12  joan
 * adjust to new version of chunking
 *
 * Revision 1.9  2005/10/03 17:40:18  joan
 * fixes
 *
 * Revision 1.8  2005/09/29 18:26:17  joan
 * fixes
 *
 * Revision 1.7  2005/09/29 16:21:05  joan
 * fixes
 *
 * Revision 1.6  2005/09/28 23:05:52  joan
 * fixes
 *
 * Revision 1.5  2005/09/27 18:09:49  joan
 * fixes
 *
 * Revision 1.4  2005/09/27 16:50:28  joan
 * fixes
 *
 * Revision 1.3  2005/09/27 15:19:25  joan
 * fixes
 *
 * Revision 1.2  2005/09/26 17:41:21  joan
 * fix compile
 *
 * Revision 1.1  2005/09/26 16:21:49  joan
 * initial load
 *
 */
