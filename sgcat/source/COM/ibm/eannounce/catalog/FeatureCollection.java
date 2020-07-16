/*
 * Created on Mar 21, 2005
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
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Iterator;

import COM.ibm.eannounce.objects.EANDataFoundation;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.transactions.NLSItem;

public class FeatureCollection
    extends CatList implements CatSync {

    private SyncMapCollection m_smc = null;

    public static final String FEATURE_VE = "EXTCOMPONENT01";
    public static final String SWFEATURE_VE = "EXTCOMPONENT02";

    public static final String FEATURE_ENTITYTYPE = "FEATURE";
    public static final String SWFEATURE_ENTITYTYPE = "SWFEATURE";
    //Added by Guo Bin, 2007-8-03
    private static String SP_SUFFIX = "D";
    //
    // we will eventually make this a properties file load
    //
    public static Hashtable c_hsh1 = new Hashtable();
    public static boolean haveItemHit(String _strItemEntityType) {
        return c_hsh1.containsKey(_strItemEntityType);
    }

    static {

        //
        // These represent all the feature/item combonations we need to
        // look for
        //

        c_hsh1.put("PORT", "");
        c_hsh1.put("CCIN", "");

        c_hsh1.put("AUD", "");
        c_hsh1.put("CABLE", "");
        c_hsh1.put("CNTRYPACK", "");
        c_hsh1.put("ENVIRNMTLINFO", "");
        c_hsh1.put("FAXMODM", "");
        c_hsh1.put("GRPHADAPTER", "");
        c_hsh1.put("HDC", "");
        c_hsh1.put("HDD", "");
        c_hsh1.put("INPUTDVC", "");
        c_hsh1.put("KEYBD", "");
        c_hsh1.put("LANGPACK", "");

        c_hsh1.put("MECHPKG", "");
        c_hsh1.put("MECHRACKCAB", "");
        c_hsh1.put("MEMORY", "");
        c_hsh1.put("MEMORYCARD", "");
        c_hsh1.put("MONITOR", "");
        c_hsh1.put("NIC", "");
        c_hsh1.put("OPTCALDVC", "");

        c_hsh1.put("PLANAR", "");
        c_hsh1.put("PRN", "");
        c_hsh1.put("PROC", "");
        c_hsh1.put("PWRSUPPLY", "");
        c_hsh1.put("REMOVBLSTOR", "");
        c_hsh1.put("SPEAKER", "");

        c_hsh1.put("SYSMGADAPTR", "");
        c_hsh1.put("TAPEDRIVE", "");
        c_hsh1.put("TECHINFO", "");
        c_hsh1.put("TECHINFOFEAT", "");
        c_hsh1.put("WLESSNC", "");
        c_hsh1.put("SYSMGADPTR", "");
    }

    public FeatureCollection(Catalog _cat, FeatureCollectionId _fcid) {
        super(_fcid);
        get(_cat);
    }

    public FeatureCollection(FeatureCollectionId _fcid) {
        super(_fcid);
    }

    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer();
        sb.append(toString());
        Iterator it = values().iterator();
        while (it.hasNext()) {
            Feature feat = (Feature) it.next();
            sb.append(feat.dump(_brief));
        }

        return sb.toString();
    }

    public String toString() {
        return getId().toString();
    }

    /**
     * A simple test of this method
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args[0].equals("1")) {
            FeatureCollection.syncToCatDb();
        }
        else if (args[0].equals("2")) {
            FeatureCollection.idlToCatDb();
        }

    }

    public FeatureCollectionId getFeatureCollectionId() {
        return (FeatureCollectionId)this.getId();
    }

    public void getReferences(Catalog _cat, int _icase) {
        // TODO Auto-generated method stub
    }

    public void deactivateAll(Catalog _cat) {
		if (Catalog.isDryRun()) {
			return;
		}


        ReturnStatus returnStatus = new ReturnStatus( -1);
        Database db = _cat.getCatalogDatabase();
        FeatureCollectionId fcid = getFeatureCollectionId();
        GeneralAreaMapItem gami = fcid.getGami();
        String strEnterprise = gami.getEnterprise();
        int iNLSID = gami.getNLSID();
		String strCountryList = gami.getCountryList();
        D.ebug(this, D.EBUG_DETAIL,
            "deactivateAll() - here is fcid..." + ":isByInteval:" + fcid.isByInterval() + ":isFromPDH: " + fcid.isFromPDH() +
            ":isFullImages:" + fcid.isFullImages());
        try {

			Iterator it = values().iterator();
            while (it.hasNext()) {
                Feature f = (Feature) it.next();
                String strFeatEntityType = f.getFeatEntityType();
                int iFeatEntityID = f.getFeatEntityID();

                try {
                    db.callGBL8986(returnStatus, strEnterprise, strCountryList, strFeatEntityType, iFeatEntityID, iNLSID);

                }
                finally {
                    db.commit();
                    db.freeStatement();
                    db.isPending();
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

    public void get(Catalog _cat) {

        ReturnDataResultSet rdrs = null;
        ReturnStatus returnStatus = new ReturnStatus( -1);
        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        FeatureCollectionId fcid = getFeatureCollectionId();
        GeneralAreaMapItem gami = fcid.getGami();
        String strEnterprise = gami.getEnterprise();
        int iNLSID = gami.getNLSID();

        D.ebug(this, D.EBUG_DETAIL,
            "get() - here is fcid..." + ":isByInteval:" + fcid.isByInterval() + ":isFromPDH: " + fcid.isFromPDH() +
            ":isFullImages:" + fcid.isFullImages());
        try {

            /*
             * This is the case where we are going by interval and we need
             * to create a SyncMap for each Root EntityType this guy represents.
             */
            if (fcid.isByInterval() && fcid.isFromPDH() && fcid.isFullImages()) {
                D.ebug(this, D.EBUG_DETAIL, "get() - PDH,byInterval, FullImage - Feature id source: " + fcid);
                if (this.hasSyncMapCollection()) {
                    this.processSyncMap(_cat);
                }
                else {
                    db.debug(D.EBUG_DETAIL,
                        this.getClass().getName() + ".get() PDH,byInterval, FullImage - *** CANNOT FIND A PRESPECTIVE ***");
                }
            }
            else if (fcid.isFromCAT()) {
                ProdStructId psid = fcid.getProdStructId();

                String strProdEntityType = psid.getProdEntityType();
                int iProdEntityId = psid.getProdEntityId();

                D.ebug(this, D.EBUG_DETAIL, "get() - isFromCat - Feature id source: " + fcid);

                try {
                    rs = db.callGBL9302(returnStatus, strEnterprise, iNLSID, strProdEntityType, iProdEntityId);
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
//                    String strItemEntityType = rdrs.getColumn(i, 2);
//                    int iItemEntityId = rdrs.getColumnInt(i, 3);

                    //FeatureId fid = new FeatureId(strFeatureEntityType, iFeatureEntityId, strItemEntityType, iItemEntityId, gami, fcid);
                    FeatureId fid = new FeatureId(strFeatureEntityType, iFeatureEntityId, gami, fcid);
                    Feature f = (Feature)this.get(fid);
                    if (f == null) {
                        f = new Feature(fid);
                        this.put(f);
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
     * This is they guy that drives reading and drilling down through the SyncMapCollection and
     * bringing the Catalog Up to speed The sync map is  going to be the same one no matter the
     * language and intent.. we will need to sort this one out as we move further down the
     * road
     * @param _cat
     */
    public void processSyncMap(Catalog _cat) {
        String strTraceBase = "FeatureCollection processSyncMap method.";
        FeatureCollectionId fcid = this.getFeatureCollectionId();
        GeneralAreaMapItem gami = fcid.getGami();
        SyncMapCollection smc = this.getSmc();

        // Lets make some WW Product Ids
        // And lets get t

        for (int x = 0; x < smc.getCount(); x++) {
            SyncMapItem smi = smc.get(x);
            D.ebug(D.EBUG_SPEW,strTraceBase + " smi: " + smi.dump(false));

            if (smi.getRootType().equals(FEATURE_ENTITYTYPE) || smi.getRootType().equals(SWFEATURE_ENTITYTYPE)) {

                FeatureId fid = new FeatureId(smi.getRootType(), smi.getRootID(), gami, fcid);
                Feature f = (Feature)this.get(fid);
                if (f == null) {
                    f = new Feature(fid);
                    f.setSmc(new SyncMapCollection());
                    this.put(f);
                }

				// to set whether the feature is active
				if (smi.getRootType().equals(smi.getChildType()) && (smi.getRootID() == smi.getChildID())) {
					D.ebug(strTraceBase + " at the root: " + smi.dump(false));
					f.setActive(smi.getChildTran().equals("ON"));
				}
                f.getSmc().add(smi);
            }
        }
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

    public void put(Catalog _cat, boolean _bcommit) {
        if (Catalog.isDryRun()) {
            return;
        }
    }

    /* (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatItem#merge(COM.ibm.eannounce.catalog.CatItem)
     */
    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub

    }

    public boolean hasSyncMapCollection() {
        return m_smc != null;
    }

    /**
     * idlToCatDb
     * This guy will take all known root entities and process them in one
     * big chunk.. if we run out of of memory, we start employing memory
     * tricks we know all to well to chunk through them
     *
     */
    public static void idlToCatDb() {

        SyncMapCollection smc1 = null;
        SyncMapCollection smc2 = null;

        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        D.ebug(D.EBUG_SPEW,"FeatureCollection->idlToCatDb");
        //
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(FeatureCollection.class));
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

            Class mclass = FeatureCollection.class;
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

            for (int icycle = 0; icycle < 2; icycle++) {
                if (icycle == 0) {
                    strEntityType = FeatureCollection.FEATURE_ENTITYTYPE;
                    strVE = FeatureCollection.FEATURE_VE;
                }
                else {
                    strEntityType = FeatureCollection.SWFEATURE_ENTITYTYPE;
                    strVE = FeatureCollection.SWFEATURE_VE;
                }
                //add by houjie&liubing 2007-08-28, get configurable NLSID information for Feature,FeatureDetail
                int iNLSSave[]=new int[2];
                iNLSSave[0] = CatalogProperties.getSaveNLSID(FeatureCollection.class,Feature.class, strVE);
                iNLSSave[1] = CatalogProperties.getSaveNLSID(FeatureCollection.class,FeatureDetail.class, strVE);
                D.ebug(D.EBUG_SPEW,"FeatureCollection.Feature."+strVE+"_nlssave="+iNLSSave[0]);
                D.ebug(D.EBUG_SPEW,"FeatureCollection.FeatureDetail."+strVE+"_nlssave="+iNLSSave[1]);
                
                //
                // Newest version of chunking
                //
                int iChunk = 1000;
                try {
                    rs = pdhdb.callGBL9010(new ReturnStatus( -1), strEnterprise, strEntityType, iChunk);
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
                           "GBL9010:et" + strEntityType + ":floor:" + iFloor + ":ceiling:" + iCeiling + ":start:" + iStart +
                           ":finish:" + iEnd);

                    // HW Feature Section

                    smc1 = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd);

                    gamp = gam.lookupGeneralArea(strGeneralArea);
                    en = gamp.elements();

                    if (!en.hasMoreElements()) {
                        D.ebug(D.EBUG_SPEW,"no gami to find!!!");
                    }
                    while (en.hasMoreElements()) {

                        Iterator it = null;

                        int icount = 0;

                        GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
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
                        FeatureCollectionId fcid = new FeatureCollectionId(cati, gami, CollectionId.PDH_SOURCE,CollectionId.FULL_IMAGES);

                        FeatureCollection fc = new FeatureCollection(fcid);
                        fc.setSmc(smc1);

                        //
                        // Lets go fill this out!
                        //
                        fc.get(cat);

                        //
                        // ok.. we have stubbed it out. lets make them fat!
                        //
                        it = fc.values().iterator();
                        while (it.hasNext()) {

                            icount++;
                            Feature f = (Feature) it.next();
                            FeatureDetailCollection fdc = null;

                            // GAB 092106 - allow a special toggle to load basic rules...
                            if(!CatalogRunner.isBasicRuleLoad()) {
                            	//add by houjie&liubing 2007-08-28 : check NLSID for Feature
                            	if(iNLSSave[0]==0 || iNLSSave[0]==gami.getNLSID()){
	                            	D.ebug(D.EBUG_SPEW, "idlToCatDb() .. getting Feature ... " + f.toString());
	                            	f.get(cat);
	                             	//
	                            	// Now.. lets
	                            	//
	                            	D.ebug(D.EBUG_SPEW, "idlToCatDb() .. updating the Feature to the CatDb... " + f.toString());
	                            	f.put(cat, false);
                            	}else{
                                    D.ebug(D.EBUG_SPEW,
                                            ">>Skipping Feature save for " + f.getId() + ".  Targeting NLSID of " +
                                            iNLSSave[0] + " not " + gami.getNLSID());                            		
                            	}
                            	//add by houjie&liubing 2007-08-28 : check NLSID for FeatureDetail
                            	if(iNLSSave[1]==0 || iNLSSave[1]==gami.getNLSID()){
	                            	f.getReferences(cat, f.FEATUREDETAIL_REFERENCE);
	                            	fdc = f.getFeatureDetailCollection();
	                            	D.ebug(D.EBUG_SPEW, "idlToCatDb() FeatureDetailCollection size is :" + fdc.size());
	                            	Iterator it2 = fdc.values().iterator();
	                            	while (it2.hasNext()) {
	                            	    FeatureDetail fd = (FeatureDetail) it2.next();
	                            	    fd.get(cat);
	                            	    //
	                            	    // we would normally compare here
	                            	    // before we do a put
	                            	    //
	                            	    fd.put(cat, false);
	                            	}
                            	}else{
                                    D.ebug(D.EBUG_SPEW,
                                            ">>Skipping FeatureDetail save for " + f.getId() + ".  Targeting NLSID of " +
                                            iNLSSave[1] + " not " + gami.getNLSID());                            		
                            	}
							} // end "real" IDL logic.

                            //
                            //
                            // GAB: HERE'S WHERE OUR BASICRULE PROCESSING GOES FOR A GIVEN ***FEATURE***.
                            //   - Assume: rules are per (item)EntityType/(item)EntityId in our FeatureDetail table.
                            //   - we can mfg a featuredetail per ea. basicrule and put()
                            //
                            if(fdc == null && !CatalogRunner.isBasicRuleLoad()) {
								fdc = f.getFeatureDetailCollection(); // we dont need anything filled out at this point...
							}
                            if(CatalogRunner.isBasicRuleLoad()) {
                            	//add by houjie&liubing 2007-08-28 : check NLSID for Feature
                            	if(iNLSSave[0]==0 || iNLSSave[0]==gami.getNLSID()){                            	
	                            	D.ebug(D.EBUG_SPEW, "getcat");
	                            	f.get(cat);
	                            	D.ebug(D.EBUG_SPEW, "getreferences");	                                
	                                D.ebug(D.EBUG_SPEW, "getfeaturedetailcollection");
                            	}else{
                                    D.ebug(D.EBUG_DETAIL,
                                            ">>Skipping Feature save for " + f.getId() + ".  Targeting NLSID of " +
                                            iNLSSave[0] + " not " + gami.getNLSID());                            		
                            	}
                            	//add by houjie&liubing 2007-08-28 : check NLSID for FeatureDetail
                            	if(iNLSSave[1]==0 || iNLSSave[1]==gami.getNLSID()){ 
                            		f.getReferences(cat, f.FEATUREDETAIL_REFERENCE);
	                                fdc = f.getFeatureDetailCollection();
	                                D.ebug(D.EBUG_SPEW, "set iterator");
	                                Iterator it2 = fdc.values().iterator();
	                                D.ebug(D.EBUG_SPEW, "start loop");
	                            	while (it2.hasNext()) {
	                            		D.ebug(D.EBUG_SPEW, "in loop fd");
	                            	    FeatureDetail fd = (FeatureDetail) it2.next();
	                            	    D.ebug(D.EBUG_SPEW, "in loop get fd");
	                            	    fd.get(cat);
	                            	}
                            	}else{
                                    D.ebug(D.EBUG_SPEW,
                                            ">>Skipping FeatureDetail save for " + f.getId() + ".  Targeting NLSID of " +
                                            iNLSSave[1] + " not " + gami.getNLSID());                            		
                            	}
                            }   
  
                        	//add by houjie&liubing 2007-08-28 : check NLSID for FeatureDetail
                        	if(iNLSSave[1]==0 || iNLSSave[1]==gami.getNLSID()){  
	                            BasicRuleCollection brc = cat.getBasicRuleCollection("FEATUREDETAIL",gami);
	                            D.ebug(D.EBUG_SPEW, "calling buildFeatureDeatails from FeatureCollection for:" + brc.getKey());
	                            FeatureDetail[] afd = brc.buildFeatureDetails(cat,f);
	                            D.ebug(D.EBUG_DETAIL, "idlToCatDb() FeatureDetailArray from BasicRulesCollection size is :" + afd.length);
	                            for(int i = 0; i < afd.length;i++) {
	                            	D.ebug(D.EBUG_SPEW, "featuredetail afd");
	                            	FeatureDetail fd = afd[i];
	                            	D.ebug(D.EBUG_SPEW, "doing fdc put");
	                                fdc.put(fd);
	                                //fd.get(cat); // nope, get's are done through basicrule logic.
	                                D.ebug(D.EBUG_SPEW, "doing fd put");
	                                fd.put(cat, false);
	                            }
                        	}else{
                                D.ebug(D.EBUG_SPEW,
                                        ">>Skipping FeatureDetail save for " + f.getId() + ".  Targeting NLSID of " +
                                        iNLSSave[1] + " not " + gami.getNLSID());                            		
                        	}	                            
                            //
                            // GAB end basicrules prcessing for this feature.
                            //

                            if (icount == iChunk) {
                                db.commit();
                                D.ebug(D.EBUG_DETAIL, "idlToCatDb() *** COMMITING ***");
                                icount = 0;
                            }
                        }

                        db.commit();
                        D.ebug(D.EBUG_DETAIL, "idlToCatDb() *** COMMITING ***");

                        //
                        // ok.. lets see what we get!
                        //
                        fc.generateXML();

                    }
                }
            }
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
     * This is the guy who moves data between the PDH and the CatDb
     *
     * @param _iType
     */
    public static void syncToCatDb() {

        SyncMapCollection smcOuter = null;

        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;

    	ReturnDataResultSet rdrs = null;
    	ResultSet rs = null;
    	
        int ichunk = 1000;
        D.ebug(D.EBUG_SPEW,"FeatureCollection->syncToCatDb");
        //
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(FeatureCollection.class));
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

            Class mclass = FeatureCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

            Catalog cat = new Catalog();
            Database db = cat.getCatalogDatabase();
            Profile prof = cat.getCatalogProfile();
            String strRoleCode = prof.getRoleCode();
            String strEnterprise = prof.getEnterprise();

            GeneralAreaMap gam = cat.getGam();

            CatalogInterval cati = new CatalogInterval(mclass, cat);
			prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

      		for (int icycle = 0; icycle < 2; icycle++) {
      		  //
      		  // lets clear it out
      		  //
      		  if (smcOuter != null) {
      		    smcOuter.clearAll();
      		  }

      		  if(icycle == 0) {
			    // HW Feature Section
            	strEntityType = FeatureCollection.FEATURE_ENTITYTYPE;
            	strVE = FeatureCollection.FEATURE_VE;
                //smc1 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
			  }

              if(icycle == 1) {
                // Software Feature Section
                strEntityType = FeatureCollection.SWFEATURE_ENTITYTYPE;
                strVE = FeatureCollection.SWFEATURE_VE;
                //smc2 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
			  }

              //smc1.merge(smc2);

            // Dump of the smc
            //D.ebug(D.EBUG_SPEW,smc1.toString());
              
              //add by houjie&liubing 2007-08-28, get configurable NLSID information for Feature,FeatureDetail
              int iNLSSave[]=new int[2];
              iNLSSave[0] = CatalogProperties.getSaveNLSID(FeatureCollection.class,Feature.class, strVE);
              iNLSSave[1] = CatalogProperties.getSaveNLSID(FeatureCollection.class,FeatureDetail.class, strVE);
              D.ebug(D.EBUG_SPEW,"FeatureCollection.Feature."+strVE+"_nlssave="+iNLSSave[0]);
              D.ebug(D.EBUG_SPEW,"FeatureCollection.FeatureDetail."+strVE+"_nlssave="+iNLSSave[1]);
              
            gamp = gam.lookupGeneralArea(strGeneralArea);
            en = gamp.elements();

            if (!en.hasMoreElements()) {
                D.ebug(D.EBUG_SPEW,"no gami to find!!!");
            }

          	//
          	// Newest version of chunking
          	//

          	//
          	// We want to load up trsnetter pass 1 here via gbl8184....!!!
          	// We might want to abstract this call to SyncMapCoillectio later on....
          	//
          	int iSessionID = db.getNewSessionID();
            List sessionIdList = new ArrayList();
            sessionIdList.add(new Integer(iSessionID));
          	try {
		 		db.callGBL8184(
		  			new ReturnStatus(-1),
		  			iSessionID,
		  			strEnterprise,
					strEntityType,
					strVE,
					strRoleCode,
					cati.getStartDate(),
					cati.getEndDate(),
					9, "", -1,1);
          	}
          	finally {
          	  db.commit();
          	  db.freeStatement();
          	  db.isPending();
          	}

          	int iChunk = CatalogProperties.getIDLChunkSize(mclass,strVE);
          	try {
          	  rs = db.callGBL9012(new ReturnStatus( -1), strEnterprise,strEntityType, iSessionID, iChunk);
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
          	for (int x = 0; x < rdrs.size(); x++) {

          		iFloor = rdrs.getColumnInt(x, 0);
          		iCeiling = rdrs.getColumnInt(x, 1);
          		iStart = rdrs.getColumnInt(x, 2);
          		iEnd = rdrs.getColumnInt(x, 3);
          		D.ebug(D.EBUG_SPEW,
          		       "GBL9012:et" + strEntityType + ":floor:" + iFloor +
          		       ":ceiling:" + iCeiling + ":start:" + iStart +
          		       ":finish:" + iEnd);

          		smcOuter = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd, iSessionID, sessionIdList);

            	//
          		if (!en.hasMoreElements()) {
          		  D.ebug(D.EBUG_WARN,"A ... no gami to find");
          		} else {
		    	  D.ebug(D.EBUG_WARN,"A gami elements found.");
				}

          		gamp = gam.lookupGeneralArea(strGeneralArea);
          		en = gamp.elements();

          		if (!en.hasMoreElements()) {
          		  D.ebug(D.EBUG_WARN,"B ... no gami to find");
          		} else {
		    	  D.ebug(D.EBUG_WARN,"B gami elements found.");
				}
            	//


          		while (en.hasMoreElements()) {

                	Iterator it = null;

                	int icount = 0;

                	GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
                	D.ebug("for GeneralAreaMapItem: " + gami.toString());
                	//
                	// Lets set the NLSItem we need to be working in right now
                	//
      		  		Vector vct = prof.getReadLanguages();
      		  		for (int i = 0; i < vct.size(); i++) {
      		  		    NLSItem nlsi = (NLSItem) vct.elementAt(i);
      		  		    D.ebug(D.EBUG_SPEW,"ProductCollection gami/prof check:nlsi.getNLSID()=" + nlsi.getNLSID() + ",gami.getNLSID()=" + gami.getNLSID());
      		  		    if (nlsi.getNLSID() == gami.getNLSID()) {
      		  		        D.ebug(D.EBUG_SPEW,"ProductCollection gami/prof check: setting read language to:" + nlsi.getNLSID());
      		  		        prof.setReadLanguage(nlsi);
      		  		        break;
      		  		    }
      		  		}

                	FeatureCollectionId fcid = new FeatureCollectionId(cati, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);

                	FeatureCollection fc = new FeatureCollection(fcid);
                	fc.setSmc(smcOuter);

                	//
                	// Lets go fill this out!
                	//
                	fc.get(cat);

					// turn off first
					fc.deactivateAll(cat);
                	//
                	// ok.. we have stubbed it out. lets make them fat!
                	//
                	it = fc.values().iterator();
                	while (it.hasNext()) {
                	    icount++;
                	    Feature f = (Feature) it.next();
                	    D.ebug(D.EBUG_SPEW, "syncToCatDb() .. getting Feature ... " + f.toString());
                    	
                	    //add by houjie&liubing 2007-08-28 : check NLSID for Feature                	    
                    	if(iNLSSave[0]==0 || iNLSSave[0]==gami.getNLSID()){                         	    
	                	    f.get(cat);
	                	    //
	                	    // Now.. lets
	                	    //
	                	    D.ebug(D.EBUG_SPEW, "syncToCatDb() .. updating the Feature to the CatDb... " + f.toString() + ", isActive:" + f.isActive());
	                	    f.put(cat, false);
                    	}else{
                            D.ebug(D.EBUG_SPEW,
                                    ">>Skipping Feature save for " + f.getId() + ".  Targeting NLSID of " +
                                    iNLSSave[0] + " not " + gami.getNLSID());                            		
                    	}
                    	//add by houjie&liubing 2007-08-28 : check NLSID for FeatureDetail
                    	if(iNLSSave[1]==0 || iNLSSave[1]==gami.getNLSID()){                           	
	                	    f.getReferences(cat, f.FEATUREDETAIL_REFERENCE);
	                	    FeatureDetailCollection fdc = f.getFeatureDetailCollection();
	                	    D.ebug(D.EBUG_SPEW, "syncToCatDb() FeatureDetailCollection size is :" + fdc.size());
	                	    Iterator it2 = fdc.values().iterator();
	                	    while (it2.hasNext()) {
	                	        FeatureDetail fd = (FeatureDetail) it2.next();
	                	        fd.get(cat);
	
	                	        //
	                	        // we would normally compare here
	                	        // before we do a put
	                	        //
	                	        fd.put(cat, false);
	                	    }
	                        //
	                        //
	                        // GAB: HERE'S WHERE OUR BASICRULE PROCESSING GOES FOR A GIVEN ***FEATURE***.
	                        //   - Assume: rules are per (item)EntityType/(item)EntityId in our FeatureDetail table.
	                        //   - we can mfg a featuredetail per ea. basicrule and put()
	                        //
	                        BasicRuleCollection brc = cat.getBasicRuleCollection("FEATUREDETAIL",gami);
	                        D.ebug(D.EBUG_SPEW, "calling buildFeatureDeatails from FeatureCollection for:" + brc.getKey());
	                        FeatureDetail[] afd = brc.buildFeatureDetails(cat,f);
	                        D.ebug(D.EBUG_SPEW, "idlToCatDb() FeatureDetailArray from BasicRulesCollection size is :" + afd.length);
	                        for(int i = 0; i < afd.length;i++) {
	                            FeatureDetail fd = afd[i];
	                            fdc.put(fd);
	                            //fd.get(cat); // nope, get's are done through basicrule logic.
	                            fd.put(cat, false);
	                        }
                    	}else{
                            D.ebug(D.EBUG_SPEW,
                                    ">>Skipping FeatureDetail save for " + f.getId() + ".  Targeting NLSID of " +
                                    iNLSSave[1] + " not " + gami.getNLSID());                            		
                    	}                        
                        //
                        // GAB end basicrules prcessing for this feature.
                        //

                	    if (icount == ichunk) {
                	        db.commit();
                	        //D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
                	        icount = 0;
                	    }

                	}

                	db.commit();
                	//D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
                    //
  		            // lets clear it out
  		            //
  		            //if (smcOuter != null) {
  		            //    smcOuter.clearAll();
                    //}

				}
			  }
            

				//          Let delete the temptables by sessionID
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
					Catalog
							.execUnixShell(CatalogProperties
									.getIDLTempTableReorgScript(ProductStructCollection.class));
				} catch (InterruptedException ex1) {
					ex1.printStackTrace();
					System.exit(-1);
				} catch (IOException ex1) {
					ex1.printStackTrace();
					System.exit(-1);
				}

            }
            
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
	 * parallelSyncToCatDb
	 * 
	 * Added by Guo Bin, 2007-08-03 For break-up stored procedures based on
	 * Collection
	 */
    public static void parallelSyncToCatDb() {

        SyncMapCollection smcOuter = null;

        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;

    	ReturnDataResultSet rdrs = null;
    	ResultSet rs = null;

        int ichunk = 1000;
        try {

            Class mclass = FeatureCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

            Catalog cat = new Catalog();
            Database db = cat.getCatalogDatabase();
            Profile prof = cat.getCatalogProfile();
            String strRoleCode = prof.getRoleCode();
            String strEnterprise = prof.getEnterprise();

            GeneralAreaMap gam = cat.getGam();

            CatalogInterval cati = new CatalogInterval(mclass, cat);
			prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());
            //Added by Guo Bin, 2007-08-03
            DatabaseExt dbe = new DatabaseExt(db);

      		for (int icycle = 0; icycle < 2; icycle++) {
      		  //
      		  // lets clear it out
      		  //
      		  if (smcOuter != null) {
      		    smcOuter.clearAll();
      		  }

      		  if(icycle == 0) {
			    // HW Feature Section
            	strEntityType = FeatureCollection.FEATURE_ENTITYTYPE;
            	strVE = FeatureCollection.FEATURE_VE;
                //smc1 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
			  }

              if(icycle == 1) {
                // Software Feature Section
                strEntityType = FeatureCollection.SWFEATURE_ENTITYTYPE;
                strVE = FeatureCollection.SWFEATURE_VE;
                //smc2 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
			  }
              //add by houjie&liubing 2007-09-13, get configurable NLSID information for Feature,FeatureDetail
              int iNLSSave[]=new int[2];
              iNLSSave[0] = CatalogProperties.getSaveNLSID(FeatureCollection.class,Feature.class, strVE);
              iNLSSave[1] = CatalogProperties.getSaveNLSID(FeatureCollection.class,FeatureDetail.class, strVE);
              D.ebug(D.EBUG_SPEW,"FeatureCollection.Feature."+strVE+"_nlssave="+iNLSSave[0]);
              D.ebug(D.EBUG_SPEW,"FeatureCollection.FeatureDetail."+strVE+"_nlssave="+iNLSSave[1]);

              //smc1.merge(smc2);

            // Dump of the smc
            //D.ebug(D.EBUG_SPEW,smc1.toString());

            gamp = gam.lookupGeneralArea(strGeneralArea);
            en = gamp.elements();

            if (!en.hasMoreElements()) {
                D.ebug(D.EBUG_SPEW,"no gami to find!!!");
            }

          	//
          	// Newest version of chunking
          	//

          	//
          	// We want to load up trsnetter pass 1 here via gbl8184....!!!
          	// We might want to abstract this call to SyncMapCoillectio later on....
          	//
          	int iSessionID = db.getNewSessionID();
          	try {
		 		dbe.callGBL8184(
		  			new ReturnStatus(-1),
		  			iSessionID,
		  			strEnterprise,
					strEntityType,
					strVE,
					strRoleCode,
					cati.getStartDate(),
					cati.getEndDate(),
					9, "", -1,1,SP_SUFFIX);
          	}
          	finally {
          	  db.commit();
          	  db.freeStatement();
          	  db.isPending();
          	}

          	int iChunk = CatalogProperties.getIDLChunkSize(mclass,strVE);
          	try {
          	  rs = dbe.callGBL9012(new ReturnStatus( -1), strEnterprise,strEntityType, iSessionID, iChunk,SP_SUFFIX);
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
          	for (int x = 0; x < rdrs.size(); x++) {

          		iFloor = rdrs.getColumnInt(x, 0);
          		iCeiling = rdrs.getColumnInt(x, 1);
          		iStart = rdrs.getColumnInt(x, 2);
          		iEnd = rdrs.getColumnInt(x, 3);
          		D.ebug(D.EBUG_SPEW,
          		       "GBL9012:et" + strEntityType + ":floor:" + iFloor +
          		       ":ceiling:" + iCeiling + ":start:" + iStart +
          		       ":finish:" + iEnd);

          		smcOuter = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd, iSessionID,SP_SUFFIX);

            	//
          		if (!en.hasMoreElements()) {
          		  D.ebug(D.EBUG_WARN,"A ... no gami to find");
          		} else {
		    	  D.ebug(D.EBUG_WARN,"A gami elements found.");
				}

          		gamp = gam.lookupGeneralArea(strGeneralArea);
          		en = gamp.elements();

          		if (!en.hasMoreElements()) {
          		  D.ebug(D.EBUG_WARN,"B ... no gami to find");
          		} else {
		    	  D.ebug(D.EBUG_WARN,"B gami elements found.");
				}
            	//


          		while (en.hasMoreElements()) {

                	Iterator it = null;

                	int icount = 0;

                	GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
                	D.ebug("for GeneralAreaMapItem: " + gami.toString());
                	//
                	// Lets set the NLSItem we need to be working in right now
                	//
      		  		Vector vct = prof.getReadLanguages();
      		  		for (int i = 0; i < vct.size(); i++) {
      		  		    NLSItem nlsi = (NLSItem) vct.elementAt(i);
      		  		    D.ebug(D.EBUG_SPEW,"ProductCollection gami/prof check:nlsi.getNLSID()=" + nlsi.getNLSID() + ",gami.getNLSID()=" + gami.getNLSID());
      		  		    if (nlsi.getNLSID() == gami.getNLSID()) {
      		  		        D.ebug(D.EBUG_SPEW,"ProductCollection gami/prof check: setting read language to:" + nlsi.getNLSID());
      		  		        prof.setReadLanguage(nlsi);
      		  		        break;
      		  		    }
      		  		}

                	FeatureCollectionId fcid = new FeatureCollectionId(cati, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);

                	FeatureCollection fc = new FeatureCollection(fcid);
                	fc.setSmc(smcOuter);

                	//
                	// Lets go fill this out!
                	//
                	fc.get(cat);

					// turn off first
					fc.deactivateAll(cat);
                	//
                	// ok.. we have stubbed it out. lets make them fat!
                	//
                	it = fc.values().iterator();
                	while (it.hasNext()) {

                	    icount++;
                	    Feature f = (Feature) it.next();

                	    D.ebug(D.EBUG_DETAIL, "syncToCatDb() .. getting Feature ... " + f.toString());
                	    //add by houjie&liubing 2007-09-13 : check NLSID for Feature                	    
                    	if(iNLSSave[0]==0 || iNLSSave[0]==gami.getNLSID()){  
                    		f.get(cat);
                    	    //
                    	    // Now.. lets
                    	    //
                    	    D.ebug(D.EBUG_DETAIL, "syncToCatDb() .. updating the Feature to the CatDb... " + f.toString() + ", isActive:" + f.isActive());
                    	    f.put(cat, false);
                    	}else{
                            D.ebug(D.EBUG_SPEW,
                                    ">>Skipping Feature save for " + f.getId() + ".  Targeting NLSID of " +
                                    iNLSSave[0] + " not " + gami.getNLSID());                            		
                    	}
                    	//add by houjie&liubing 2007-09-13 : check NLSID for FeatureDetail
                    	if(iNLSSave[1]==0 || iNLSSave[1]==gami.getNLSID()){     
                    	    f.getReferences(cat, f.FEATUREDETAIL_REFERENCE);
                    	    FeatureDetailCollection fdc = f.getFeatureDetailCollection();
                    	    D.ebug(D.EBUG_DETAIL, "syncToCatDb() FeatureDetailCollection size is :" + fdc.size());
                    	    Iterator it2 = fdc.values().iterator();
                    	    while (it2.hasNext()) {
                    	        FeatureDetail fd = (FeatureDetail) it2.next();
                    	        fd.get(cat);

                    	        //
                    	        // we would normally compare here
                    	        // before we do a put
                    	        //
                    	        fd.put(cat, false);
                    	    }

                            //
                            //
                            // GAB: HERE'S WHERE OUR BASICRULE PROCESSING GOES FOR A GIVEN ***FEATURE***.
                            //   - Assume: rules are per (item)EntityType/(item)EntityId in our FeatureDetail table.
                            //   - we can mfg a featuredetail per ea. basicrule and put()
                            //
                            BasicRuleCollection brc = cat.getBasicRuleCollection("FEATUREDETAIL",gami);
                            D.ebug(D.EBUG_SPEW, "calling buildFeatureDeatails from FeatureCollection for:" + brc.getKey());
                            FeatureDetail[] afd = brc.buildFeatureDetails(cat,f);
                            D.ebug(D.EBUG_DETAIL, "idlToCatDb() FeatureDetailArray from BasicRulesCollection size is :" + afd.length);
                            for(int i = 0; i < afd.length;i++) {
                                FeatureDetail fd = afd[i];
                                fdc.put(fd);
                                //fd.get(cat); // nope, get's are done through basicrule logic.
                                fd.put(cat, false);
                            }
                            //
                            // GAB end basicrules prcessing for this feature.
                            //                    		
                    	}else{
                            D.ebug(D.EBUG_SPEW,
                                    ">>Skipping FeatureDetail save for " + f.getId() + ".  Targeting NLSID of " +
                                    iNLSSave[1] + " not " + gami.getNLSID());                            		
                    	}     
                	    if (icount == ichunk) {
                	        db.commit();
                	        //D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
                	        icount = 0;
                	    }
                	}

                	db.commit();
                	//D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
                    //
  		            // lets clear it out
  		            //
  		            //if (smcOuter != null) {
  		            //    smcOuter.clearAll();
                    //}

				}
			  }

            }

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

        Iterator it = null;

        XMLWriter xml = new XMLWriter();
        FeatureCollectionId fcid = this.getFeatureCollectionId();
        CatalogInterval cati = fcid.getInterval();
        String strFromTimestamp = (fcid.isByInterval() ? cati.getStartDate() : EPOCH);
        String strToTimestamp = (fcid.isByInterval() ? cati.getEndDate() : FOREVER);

        int iNumberOfElements = this.size();

        try {

            xml.writeEntity("FeatureFile");

            xml.writeEntity("FromTimestamp");
            xml.write(strFromTimestamp);
            xml.endEntity();

            xml.writeEntity("ToTimestamp");
            xml.write(strToTimestamp);
            xml.endEntity();

            xml.writeEntity("NumberOfElements");
            xml.write(iNumberOfElements + "");
            xml.endEntity();

            /*
             * For now .. we just have the update command..
             */
            xml.writeEntity("Command");
            xml.write("update");
            xml.endEntity();

            xml.writeEntity("Source");
            xml.write("STG");
            xml.endEntity();

            it = values().iterator();

            while (it.hasNext()) {
                Feature f = (Feature) it.next();
                f.generateXMLFragment(xml);
            }

            xml.endEntity();

            xml.finishEntity();

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /**
     * $Log: FeatureCollection.java,v $
     * Revision 1.15  2011/05/05 11:21:32  wendy
     * src from IBMCHINA
     *
     * Revision 1.8  2007/10/29 09:32:20  jingb
     * Change code in method syncToCatDb():
     * delete the temp table data by sessionID
     *
     * Revision 1.7  2007/09/13 08:43:36  jiehou
     * add configurable NLSID for parallelSyncToCatDb()
     *
     * Revision 1.6  2007/09/13 05:54:52  sulin
     * no message
     *
     * Revision 1.5  2007/09/10 06:45:43  sulin
     * no message
     *
     * Revision 1.4  2007/09/05 01:35:30  jiehou
     * *** empty log message ***
     *
     * Revision 1.3  2007/09/03 03:11:31  jiehou
     * *** empty log message ***
     *
     * Revision 1.2  2007/08/31 06:41:56  jiehou
     * *** empty log message ***
     *
     * Revision 1.1.1.1  2007/06/05 02:09:19  jingb
     * no message
     *
     * Revision 1.13  2007/05/15 18:30:49  rick
     * changed to run IDL logic 'gets' in basic rule load path
     *
     * Revision 1.12  2006/11/15 21:07:38  gregg
     * a couple of trace stmts only
     *
     * Revision 1.11  2006/09/21 20:58:44  gregg
     * prepping for BasicRuleLoad
     *
     * Revision 1.10  2006/09/01 21:54:24  gregg
     * fixing gamap feature problem
     *
     * Revision 1.9  2006/08/29 16:56:52  gregg
     * removed filtering by configuratorflag (CR5836)
     *
     * Revision 1.8  2006/08/24 20:21:37  gregg
     * no smcOuter.clearAll at end of nls loop!
     *
     * Revision 1.7  2006/08/23 17:06:19  gregg
     * Add BasicRule construction in syncToCatDb
     *
     * Revision 1.6  2006/08/10 21:33:57  gregg
     * Use new SyncMapCollection constructor for chunky munky
     *
     * Revision 1.5  2006/08/10 17:56:28  gregg
     * fix icycle logic
     *
     * Revision 1.4  2006/08/10 17:13:28  gregg
     * delta chunky munky
     *
     * Revision 1.3  2006/06/29 21:40:15  gregg
     * add Nlsid to GBL8986
     *
     * Revision 1.2  2006/05/09 21:01:59  gregg
     * basic rule debugging
     *
     * Revision 1.1.1.1  2006/03/30 17:36:29  gregg
     * Moving catalog module from middleware to
     * its own module.
     *
     * Revision 1.61  2006/03/15 22:27:08  gregg
     * CR5836
     *
     * Revision 1.60  2006/01/20 19:05:09  gregg
     * cleaning up to prior version (oops wrong file)
     *
     * Revision 1.59  2006/01/20 18:56:33  gregg
     * update
     *
     * Revision 1.58  2005/11/30 21:43:01  joan
     * work on syncmap
     *
     * Revision 1.57  2005/11/29 17:14:53  joan
     * fixes
     *
     * Revision 1.56  2005/11/29 17:02:33  joan
     * add new sp
     *
     * Revision 1.55  2005/11/22 22:53:43  gregg
     * prepping for wayne spec cases
     *
     * Revision 1.54  2005/11/21 18:25:59  gregg
     * basicrule.featuredetail building step 1
     *
     * Revision 1.53  2005/11/21 17:35:40  joan
     * fixes
     *
     * Revision 1.52  2005/11/18 18:55:54  joan
     * work on syncToCatDb
     *
     * Revision 1.51  2005/11/16 22:45:11  gregg
     * basicrulecollection
     *
     * Revision 1.50  2005/11/16 22:31:01  gregg
     * BasicRules
     *
     * Revision 1.49  2005/11/10 18:37:50  dave
     * adding milliseconds to backlog raw
     *
     * Revision 1.48  2005/10/24 21:02:36  joan
     * fixes
     *
     * Revision 1.47  2005/10/24 18:58:23  joan
     * fixes
     *
     * Revision 1.46  2005/10/12 23:22:12  joan
     * adjust to new version of chunking
     *
     * Revision 1.45  2005/09/16 22:21:33  joan
     * fixes
     *
     * Revision 1.44  2005/09/13 03:20:03  dave
     * enhanced looping for other areas
     *
     * Revision 1.43  2005/08/17 19:53:58  joan
     * fixes
     *
     * Revision 1.42  2005/06/22 21:17:15  gregg
     * change signature for processSyncMap
     *
     * Revision 1.41  2005/06/22 17:10:42  joan
     * fixes
     *
     * Revision 1.40  2005/06/17 18:54:06  joan
     * fixes
     *
     * Revision 1.39  2005/06/16 18:24:50  joan
     * fixes
     *
     * Revision 1.38  2005/06/16 17:14:58  joan
     * add code
     *
     * Revision 1.37  2005/06/15 21:33:17  joan
     * add code
     *
     */
}
