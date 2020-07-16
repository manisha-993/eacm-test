/*
 * Created on Jun 8, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.transactions.NLSItem;

/**
 * Collateral Collection Management Object for Catalog Database
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CategoryCollection
    extends CatList implements XMLable, CatSync {

    private SyncMapCollection m_smc = null;

    public static final String CATNAV_ENTITYTYPE = "CATNAV";
    public static final String CATNAV_VE = "VECATNAV1";
    //Added by Guo Bin, 2007-8-03
    private static String SP_SUFFIX = "A";

    /**
     * CollateralCollection
     *
     * @param _cid
     */
    public CategoryCollection(CategoryCollectionId _cid) {
        super(_cid);
    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return this.getId().toString();
    }

    /**
     * A simple test of this method
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args[0].equals("1")) {
            CategoryCollection.syncToCatDb();
        }
        else if (args[0].equals("2")) {
            CategoryCollection.idlToCatDb();
        }
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _b) {
        StringBuffer sb = new StringBuffer("test dump: ");

        Iterator it = null;
        int i = 0;

        sb.append(NEW_LINE + "=======================================");
        sb.append(NEW_LINE + "class " + this.getClass().getName());
        sb.append(NEW_LINE + "=======================================");
        sb.append(NEW_LINE + "\tName is: " + toString());
        sb.append(NEW_LINE + "\tCatId is:" + this.getId());
        sb.append(NEW_LINE + "\t---------");
        sb.append(NEW_LINE + "\tCollateral Collection Is");
        sb.append(NEW_LINE + "\t---------");
        it = this.values().iterator();
        i = 1;
        while (it.hasNext()) {
            Collateral col = (Collateral) it.next();
            sb.append(NEW_LINE + "\t" + (i++) + " - " + col);
        }

        return sb.toString();

    }

    /**
     *  (non-Javadoc)
     *
     */
    public void getReferences(Catalog _cat, int _icase) {
        // TODO Auto-generated method stub
    }

    /**
     *  Lets go Set ourselves based upon the Data Source and type of requeset
     *
     */
    public void get(Catalog _cat) {

        CategoryCollectionId colcid = (CategoryCollectionId) getId();
        GeneralAreaMapItem gami = colcid.getGami();
        gami.getEnterprise();
        gami.getNLSID();

        D.ebug(this, D.EBUG_DETAIL,
               "get() - here is clcid..." + ":isByInteval:" +
               colcid.isByInterval() + ":isFromPDH: " + colcid.isFromPDH() +
               ":isFullImages:" + colcid.isFullImages());

        try {

            /*
             * This is the case where we are going by interval and we need
             * to create a SyncMap for each Root EntityType this guy represents.
             */
            if (colcid.isByInterval() && colcid.isFromPDH()) {

                this.processSyncMap(_cat);

            }
            else if (colcid.isFromPDH() && colcid.isFullImages()) {
                //TODO
            }
            else if (colcid.isFromCAT()) {
                //TODO
            }
        }
        finally {
            // TODO future catches
        }
    }

    /**
     * idlToCatDb
     * This guy will take all known MM, FB, IMAGES and load them in one big
     * chunk.. if we run out of of memory, we start employing memory
     * tricks we know all to well to chunk through them
     *
     */
    public static void idlToCatDb() {

        SyncMapCollection smc1 = null;

        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;
        
        //
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(CategoryCollection.class));
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

            Class mclass = CategoryCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
            Catalog catalog = new Catalog();
            Database db = catalog.getCatalogDatabase();
            GeneralAreaMap gam = catalog.getGam();
            Profile prof = catalog.getCatalogProfile();
            CatalogInterval cati = new CatalogInterval(mclass, catalog);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = catalog.getEnterprise();
            Database pdhdb = catalog.getPDHDatabase();

            //
            // OK lets do this loop for real
            // we will make two passes..
            //
            //
            // lets temperaroly look at WWSEO's only
            //
                strEntityType = CategoryCollection.CATNAV_ENTITYTYPE;
                strVE = CategoryCollection.CATNAV_VE;

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
                    D.ebug(D.EBUG_DETAIL,
                           "GBL9010:et" + strEntityType + ":floor:" + iFloor +
                           ":ceiling:" + iCeiling + ":start:" + iStart +
                           ":finish:" +
                           iEnd);

                    smc1 = new SyncMapCollection(catalog, strVE, strEntityType,
                                                 iStart, iEnd);

                    gamp = gam.lookupGeneralArea(strGeneralArea);
                    en = gamp.elements();

                    if (!en.hasMoreElements()) {
                        System.out.println("no gami to find!!!");
                    }

                    while (en.hasMoreElements()) {

                        Iterator it = null;

                        GeneralAreaMapItem gami = (GeneralAreaMapItem) en.
                            nextElement();

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

                        CategoryCollectionId catcid = new CategoryCollectionId(
                            cati, gami, CollectionId.PDH_SOURCE,
                            CollectionId.FULL_IMAGES);

                        CategoryCollection catc = new CategoryCollection(catcid);
                        catc.setSmc(smc1);

                        //
                        // Lets go fill this out!
                        //
                        catc.get(catalog);

                        //
                        // ok.. we have stubbed it out. lets make them fat!
                        //

                        int icount = 0;

                        it = catc.values().iterator();

                        while (it.hasNext()) {

                            icount++;

                            Category cat = (Category) it.next();

                            D.ebug(CategoryCollection.class, D.EBUG_DETAIL,
                                "idlToCatDb() .. getting Category and its collection... " +
                                   cat.toString());

                            cat.get(catalog);
                            //
                            // Now.. lets get the references
                            //
                            // I believe we have no references for Category
                            cat.getReferences(catalog, -9);

                            D.ebug(CategoryCollection.class, D.EBUG_DETAIL,
                                "idlToCatDb() .. updating the cat to the CatDb... " +
                                   cat.toString());
                            cat.put(catalog, false);

                            if (icount == iChunk) {
                                db.commit();
                                D.ebug(D.EBUG_DETAIL,
                                       "idlToCatDb() *** COMMITING ***");
                                icount = 0;
                            }

                        }

                        //
                        // ok.. lets see what we get!
                        //
                        catc.generateXML();
                        D.ebug(D.EBUG_DETAIL, "idlToCatDb() *** COMMITING ***");
                        db.commit();
                    }

                }

            //
            // o.k.  we are now done
            //
            cati.put(catalog);
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
     * syncToCatDb
     *
     */
    public static void syncToCatDb() {

        SyncMapCollection smc1 = null;

        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;

        //
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(CategoryCollection.class));
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

            Class mclass = CategoryCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
            Catalog catalog = new Catalog();
            Database db = catalog.getCatalogDatabase();
            GeneralAreaMap gam = catalog.getGam();
            Profile prof = catalog.getCatalogProfile();
            CatalogInterval cati = new CatalogInterval(mclass, catalog);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = catalog.getEnterprise();
            Database pdhdb = catalog.getPDHDatabase();
	  		String strRoleCode = prof.getRoleCode();


            //
            strEntityType = CategoryCollection.CATNAV_ENTITYTYPE;
            strVE = CategoryCollection.CATNAV_VE;

            //smc1 = new SyncMapCollection(catalog, strEntityType, strVE, cati, 2);

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

        	  smc1 = new SyncMapCollection(catalog, strVE, strEntityType, iStart, iEnd, iSessionID, sessionIdList);


              gamp = gam.lookupGeneralArea(strGeneralArea);
              en = gamp.elements();
            //
            //
            //


              if (!en.hasMoreElements()) {
                System.out.println("no gami to find!!!");
              }

              while (en.hasMoreElements()) {

                Iterator it = null;

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

                CategoryCollectionId catcid = new CategoryCollectionId(cati, gami, CollectionId.PDH_SOURCE,CollectionId.NET_CHANGES);

                CategoryCollection catc = new CategoryCollection(catcid);
                catc.setSmc(smc1);

                //
                // Lets go fill this out!
                //
                catc.get(catalog);

                //int icount = 0;

                it = catc.values().iterator();

                while (it.hasNext()) {

                    //icount++;

                    Category cat = (Category) it.next();

                    D.ebug(CategoryCollection.class, D.EBUG_DETAIL,
                            "syncToCatDb() .. getting Category and its collection... " + cat.toString());

                    // might as well fill out here...
                    cat.get(catalog);

                    try {
	                    // let's FIRST get rid of whatevers we's gots...
	                    pdhdb.callGBL4024(new ReturnStatus(-1),
	                                    strEnterprise, //cat.getStringVal(Category.ENTERPRISE),
	                                    cat.getStringVal(Category.ENTITYTYPE),
	                                    cat.getIntVal(Category.ENTITYID),
	                                    gami.getCountry(), //cat.getStringVal(Category.COUNTRYCODE),
	                                    gami.getLanguage(), //cat.getStringVal(Category.LANGUAGECODE),
	                                    gami.getNLSID()); //cat.getIntVal(Category.NLSID));
	    			} catch (MiddlewareException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} finally {
					    try {
					    	//rs.close();
						    //rs = null;
						    pdhdb.commit();
						    pdhdb.freeStatement();
						    pdhdb.isPending();
					    } catch (SQLException _ex) {
						    _ex.printStackTrace();
					    }
	    			}



                    //
                    // Now.. lets get the references
                    //
                    // I believe we have no references for Category
                    cat.getReferences(catalog, -9);

                    D.ebug(CategoryCollection.class, D.EBUG_DETAIL,
                            "syncToCatDb() .. updating the cat to the CatDb... " + cat.toString());
                    cat.put(catalog, false);

                    //if (icount == iChunk) {
                        db.commit();
                         D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
                         //icount = 0;
                    //}

                }

                //
                // ok.. lets see what we get!
                //
                catc.generateXML();
                D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
                db.commit();
              }
              if (smc1 != null) {
			      smc1.clearAll();
              }
		    }
            
            //Let delete the temptables by sessionID
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


            //
            // o.k. we are now done
            //
            cati.put(catalog);
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
     * Added by  Guo Bin, 2007-08-03 For break-up stored procedures based on
     * Collection
     */
    public static void parallelSyncToCatDb() {

        SyncMapCollection smc1 = null;

        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;

        try {

            Class mclass = CategoryCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
            Catalog catalog = new Catalog();
            Database db = catalog.getCatalogDatabase();
            GeneralAreaMap gam = catalog.getGam();
            Profile prof = catalog.getCatalogProfile();
            CatalogInterval cati = new CatalogInterval(mclass, catalog);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = catalog.getEnterprise();
            Database pdhdb = catalog.getPDHDatabase();
	  		String strRoleCode = prof.getRoleCode();
            //Added by Guo Bin, 2007-08-03
            DatabaseExt dbe = new DatabaseExt(db);


            //
            strEntityType = CategoryCollection.CATNAV_ENTITYTYPE;
            strVE = CategoryCollection.CATNAV_VE;

            //smc1 = new SyncMapCollection(catalog, strEntityType, strVE, cati, 2);

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

        	  smc1 = new SyncMapCollection(catalog, strVE, strEntityType, iStart, iEnd, iSessionID,SP_SUFFIX);


              gamp = gam.lookupGeneralArea(strGeneralArea);
              en = gamp.elements();
            //
            //
            //


              if (!en.hasMoreElements()) {
                System.out.println("no gami to find!!!");
              }

              while (en.hasMoreElements()) {

                Iterator it = null;

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

                CategoryCollectionId catcid = new CategoryCollectionId(cati, gami, CollectionId.PDH_SOURCE,CollectionId.NET_CHANGES);

                CategoryCollection catc = new CategoryCollection(catcid);
                catc.setSmc(smc1);

                //
                // Lets go fill this out!
                //
                catc.get(catalog);

                //int icount = 0;

                it = catc.values().iterator();

                while (it.hasNext()) {

                    //icount++;

                    Category cat = (Category) it.next();

                    D.ebug(CategoryCollection.class, D.EBUG_DETAIL,
                            "syncToCatDb() .. getting Category and its collection... " + cat.toString());

                    // might as well fill out here...
                    cat.get(catalog);

                    try {
	                    // let's FIRST get rid of whatevers we's gots...
	                    pdhdb.callGBL4024(new ReturnStatus(-1),
	                                    strEnterprise, //cat.getStringVal(Category.ENTERPRISE),
	                                    cat.getStringVal(Category.ENTITYTYPE),
	                                    cat.getIntVal(Category.ENTITYID),
	                                    gami.getCountry(), //cat.getStringVal(Category.COUNTRYCODE),
	                                    gami.getLanguage(), //cat.getStringVal(Category.LANGUAGECODE),
	                                    gami.getNLSID()); //cat.getIntVal(Category.NLSID));
	    			} catch (MiddlewareException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} finally {
					    try {
					    	//rs.close();
						    //rs = null;
						    pdhdb.commit();
						    pdhdb.freeStatement();
						    pdhdb.isPending();
					    } catch (SQLException _ex) {
						    _ex.printStackTrace();
					    }
	    			}



                    //
                    // Now.. lets get the references
                    //
                    // I believe we have no references for Category
                    cat.getReferences(catalog, -9);

                    D.ebug(CategoryCollection.class, D.EBUG_DETAIL,
                            "syncToCatDb() .. updating the cat to the CatDb... " + cat.toString());
                    cat.put(catalog, false);

                    //if (icount == iChunk) {
                        db.commit();
                         D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
                         //icount = 0;
                    //}

                }

                //
                // ok.. lets see what we get!
                //
                catc.generateXML();
                D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
                db.commit();
              }
              if (smc1 != null) {
			      smc1.clearAll();
              }
		    }


            //
            // o.k.  we are now done
            //
            cati.put(catalog);
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
     *  (non-Javadoc)
     *
     */
    public void put(Catalog _cat, boolean _bcommit) {
        if (Catalog.isDryRun()) {
            return;
        }

    }

    /**
     *  (non-Javadoc)
     *
     */
    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub
    }

    /**
     * getSmc
     * @return
     */
    public SyncMapCollection getSmc() {
        return m_smc;
    }

    /**
     * setSmc
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

        CategoryCollectionId catcid = (CategoryCollectionId)this.getId();
        GeneralAreaMapItem gami = catcid.getGami();
        SyncMapCollection smc = this.getSmc();

        //
        // need to set up the profile here
        //
        CatalogInterval cati = catcid.getInterval();
        Profile prof = _cat.getCatalogProfile();
        prof.setEffOn(cati.getEndDate());
        prof.setValOn(cati.getEndDate());
        //
        // O.K. here is where we get to practice GAMI substitution
        //
        //TODO
        for (int x = 0; x < smc.getCount(); x++) {
            SyncMapItem smi = smc.get(x);
            if (smi.getRootType().equals(this.CATNAV_ENTITYTYPE)) {
                CategoryId catid = new CategoryId(smi.getRootType(),
                                                  smi.getRootID(), gami, catcid);
                Category category = (Category)this.get(catid);
                if (category == null) {
                    category = new Category(catid);
                    category.setSmc(new SyncMapCollection());
                    // same logic used in Product: initially set to inactive, then turn on if we find active smi's below.
                    category.setActive(false);
                    this.put(category);
                }
                category.getSmc().add(smi);
            }
        }

    	// GAB check for isACtive...we want to find ONE!!
    	Iterator it = this.values().iterator();
    	while (it.hasNext()) {
    	     Category category = (Category) it.next();
    	   SMI_LOOP:
    	 	 for (int x = 0; x < category.getSmc().getCount(); x++) {
    	         SyncMapItem smi = category.getSmc().get(x);
    	         if(smi.getChildTran().equals("ON")) {
					 category.setActive(true);
					 break SMI_LOOP;
				 }
			 }
    	}

    }

    /**
     * First attempt at an XML writer usage - This sends the output to
     * Standard out for now.
     */
    public void generateXML() {

        Iterator it = null;

        XMLWriter xml = new XMLWriter();
        CategoryCollectionId catcid = (CategoryCollectionId)this.getId();
        CatalogInterval cati = catcid.getInterval();
        String strFromTimestamp = (catcid.isByInterval() ? cati.getStartDate() :
                                   EPOCH);
        String strToTimestamp = (catcid.isByInterval() ? cati.getEndDate() :
                                 FOREVER);

        int iNumberOfElements = this.size();

        try {

            xml.writeEntity("CategoryFile");

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
                Category category = (Category) it.next();
                category.generateXMLFragment(xml);
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
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatSync#hasSyncMapCollection()
     */
    public boolean hasSyncMapCollection() {
        // TODO Auto-generated method stub
        return getSmc() != null;
    }



}

/*
 * $Log: CategoryCollection.java,v $
 * Revision 1.5  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.4  2007/10/29 09:32:20  jingb
 * Change code in method syncToCatDb():
 * delete the temp table data by sessionID
 *
 * Revision 1.3  2007/09/13 05:54:52  sulin
 * no message
 *
 * Revision 1.2  2007/09/10 06:45:43  sulin
 * no message
 *
 * Revision 1.2  2007/06/15 07:29:43  jiangshouyi
 * Call Catalog.execUnixShell try to clean up the temp tables
 *
 * Revision 1.1.1.1  2007/06/15 05:42:53  db2admin
 * no message
 *
 * Revision 1.3  2006/09/06 20:39:09  gregg
 * de;ta chunky munky
 *
 * Revision 1.2  2006/07/25 16:56:03  gregg
 * setting isActive + processing OFF/ON childtrans
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.14  2005/12/09 21:28:28  gregg
 * fix for GBL4024 input parms
 *
 * Revision 1.13  2005/12/09 19:26:52  gregg
 * fix
 *
 * Revision 1.12  2005/12/09 18:59:03  gregg
 * fix
 *
 * Revision 1.11  2005/12/09 18:49:20  gregg
 * 4024 free db resources
 *
 * Revision 1.10  2005/12/08 17:40:18  gregg
 * SyncMapCollection constructor -- let's do this right (thanks Joan).
 *
 * Revision 1.9  2005/12/01 23:47:35  gregg
 * syncToCatDb
 *
 * Revision 1.8  2005/10/27 20:47:01  gregg
 * better chunking
 *
 * Revision 1.7  2005/10/25 17:17:29  dave
 * more trace
 *
 * Revision 1.6  2005/10/05 22:52:21  gregg
 * update
 *
 * Revision 1.5  2005/10/05 20:03:50  gregg
 * generateXML fix
 *
 * Revision 1.4  2005/10/05 19:09:13  gregg
 * processSyncMap
 *
 * Revision 1.3  2005/10/04 22:18:04  gregg
 * compile
 *
 * Revision 1.2  2005/10/04 22:01:55  gregg
 * pounding into workingness
 *
 * Revision 1.1  2005/10/04 20:38:02  dave
 * new classes
 *
 */
