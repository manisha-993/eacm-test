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
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.NLSItem;

/**
 * Collateral Collection Management Object for Catalog Database
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CollateralCollection
    extends CatList implements XMLable, CatSync {

    private SyncMapCollection m_smc = null;

    public static final String IMG_ENTITYTYPE = "IMG";
    public static final String MM_ENTITYTYPE = "MM";
    public static final String FB_ENTITYTYPE = "FB";
    public static final String MM_VE = "VEMM1";
    public static final String IMG_VE = "VEIMG1";
    public static final String FB_VE = "VEFB1";
    //Added by Guo Bin, 2007-8-03
    private static String SP_SUFFIX = "B";

    /**
     * CollateralCollection
     *
     * @param _cid
     */
    public CollateralCollection(CollateralCollectionId _cid) {
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
            CollateralCollection.syncToCatDb();
        }
        else if (args[0].equals("2")) {
            CollateralCollection.idlToCatDb();
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

    public void deactivateAll(Catalog _cat) {
		if (Catalog.isDryRun()) {
			return;
		}


        ReturnStatus returnStatus = new ReturnStatus( -1);
        Database db = _cat.getCatalogDatabase();
        CollateralCollectionId ccid = (CollateralCollectionId) getId();
        GeneralAreaMapItem gami = ccid.getGami();
        String strEnterprise = gami.getEnterprise();
        int iNLSID = gami.getNLSID();
		String strCountryList = gami.getCountryList();
        D.ebug(this, D.EBUG_DETAIL,
            "deactivateAll() - here is ccid..." + ":isByInteval:" + ccid.isByInterval() + ":isFromPDH: " + ccid.isFromPDH() +
            ":isFullImages:" + ccid.isFullImages());
        try {

			Iterator it = values().iterator();
            while (it.hasNext()) {
                Collateral c = (Collateral) it.next();
                CollateralId cid = (CollateralId) c.getId();

                String strCollEntityType = cid.getCollEntityType();
                int iCollEntityID = cid.getCollEntityID();

                try {
                    db.callGBL8988(returnStatus, strEnterprise, strCountryList, strCollEntityType, iCollEntityID);

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

    /**
     *
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.Databaseable#get(COM.ibm.eannounce.catalog.Catalog)
     */
    public void get(Catalog _cat) {

        CollateralCollectionId colcid = (CollateralCollectionId) getId();
        GeneralAreaMapItem gami = colcid.getGami();
        gami.getEnterprise();
        gami.getNLSID();

        D.ebug(
            this,
            D.EBUG_DETAIL,
            "get() - here is colcid..."
            + ":isByInteval:"
            + colcid.isByInterval()
            + ":isFromPDH: "
            + colcid.isFromPDH()
            + ":isFullImages:"
            + colcid.isFullImages());

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

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        SyncMapCollection smc1 = null;

        String strEntityType = null;
        String strVE = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;
        
        //
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(CollateralCollection.class));
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

            Class mclass = CollateralCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
            Catalog cat = new Catalog();
            Database db = cat.getCatalogDatabase();
            GeneralAreaMap gam = cat.getGam();
            Profile prof = cat.getCatalogProfile();
            CatalogInterval cati = new CatalogInterval(mclass, cat);
            String strEnterprise = cat.getEnterprise();
            Database pdhdb = cat.getPDHDatabase();

            //
            // OK lets do this loop for real
            // we will make two passes..
            //
            //
            // lets temperaroly look at WWSEO's only
            //
            for (int icycle = 0; icycle < 3; icycle++) {
                if (icycle == 0) {
                    strEntityType = CollateralCollection.MM_ENTITYTYPE;
                    strVE = CollateralCollection.MM_VE;
                }
                else if (icycle == 1) {
                    strEntityType = CollateralCollection.FB_ENTITYTYPE;
                    strVE = CollateralCollection.FB_VE;
                }
                else {
                    strEntityType = CollateralCollection.IMG_ENTITYTYPE;
                    strVE = CollateralCollection.IMG_VE;
                }

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

                    smc1 = new SyncMapCollection(cat, strVE, strEntityType,
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

                        CollateralCollectionId colcid =
                            new CollateralCollectionId(cati, gami,
                            CollectionId.PDH_SOURCE,
                            CollectionId.FULL_IMAGES);

                        CollateralCollection colc = new CollateralCollection(
                            colcid);
                        colc.setSmc(smc1);

                        //
                        // Lets go fill this out!
                        //
                        colc.get(cat);

                        //
                        // ok.. we have stubbed it out. lets make them fat!
                        //

                        int icount = 0;

                        it = colc.values().iterator();

                        while (it.hasNext()) {

                            icount++;

                            Collateral col = (Collateral) it.next();

                            SyncMapCollection smc = col.getSmc();
                            D.ebug(D.EBUG_SPEW, smc.toString());
                            D.ebug(D.EBUG_SPEW,
                                   "idlToCatDb().getting collateral and references " +
                                   col.toString());
                            col.get(cat);

                            //
                            // Now.. lets get the references
                            // we have references to world wide product
                            // we have references to family
                            //
                            col.getReferences(cat, Collateral.WORLDWIDEPRODUCT_REFERENCE);
                            col.getReferences(cat, Collateral.FAMILYPAGE_REFERENCE);

                            D.ebug(CollateralCollection.class, D.EBUG_SPEW,
                                   "idlToCatDb() .. updating the col to the CatDb... " +
                                   col.toString());
                            col.put(cat, false);

                            //
                            // DWB .. maybe we can pull when needed
                            //
                            WorldWideProductCollection wwpc = col.getWorldWideProductCollection();
                            D.ebug(CollateralCollection.class, D.EBUG_SPEW,"idlToCatDb()size is :" + wwpc.size());

                            //
                            // since we are calling the our own get references
                            wwpc.put(cat, false);

                            if (icount == iChunk) {
                                db.commit();
                                D.ebug(D.EBUG_DETAIL,
                                       "idlToCatDb() *** COMMITING ***");
                                icount = 0;
                            }

                            FamilyPageCollection fpc = col.getFamilyPageCollection();
                            D.ebug(CollateralCollection.class, D.EBUG_SPEW,"Family Page...idlToCatDb()size is :" + fpc.size());
                            // since we are calling the our own get references
                            fpc.put(cat, false);

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
                        colc.generateXML();
                        D.ebug(D.EBUG_DETAIL, "idlToCatDb() *** COMMITING ***");
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
     * syncToCatDb
     *
     */
    public static void syncToCatDb() {

        SyncMapCollection smc1 = null;

        String strEntityType = null;
        String strVE = null;

    	ReturnDataResultSet rdrs = null;
    	ResultSet rs = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;
		//int iChunk = 1000;
        
        //
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(CollateralCollection.class));
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

            Class mclass = CollateralCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
            Catalog cat = new Catalog();
            String strEnterprise = cat.getEnterprise();
            Database db = cat.getCatalogDatabase();
            GeneralAreaMap gam = cat.getGam();
            Profile prof = cat.getCatalogProfile();
	        String strRoleCode = prof.getRoleCode();

            CatalogInterval cati = new CatalogInterval(mclass, cat);
            prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

            for (int icycle = 0; icycle < 3; icycle++) {
                if (icycle == 0) {
                    strEntityType = CollateralCollection.MM_ENTITYTYPE;
                    strVE = CollateralCollection.MM_VE;
                }
                else if (icycle == 1) {
                    strEntityType = CollateralCollection.FB_ENTITYTYPE;
                    strVE = CollateralCollection.FB_VE;
                }
                else {
                    strEntityType = CollateralCollection.IMG_ENTITYTYPE;
                    strVE = CollateralCollection.IMG_VE;
                }
//            strEntityType = CollateralCollection.MM_ENTITYTYPE;
//            strVE = CollateralCollection.MM_VE;
//            //smc1 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
//
//            strEntityType = CollateralCollection.FB_ENTITYTYPE;
//            strVE = CollateralCollection.FB_VE;
//            //smc2 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
//
//            strEntityType = CollateralCollection.IMG_ENTITYTYPE;
//            strVE = CollateralCollection.IMG_VE;
            //smc3 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);

            //smc1.merge(smc2);
            //smc1.merge(smc3);

            //System.out.println(smc1.toString());

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

        	    smc1 = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd, iSessionID, sessionIdList);

                gamp = gam.lookupGeneralArea(strGeneralArea);
                en = gamp.elements();

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

			  		CollateralCollectionId colcid = new CollateralCollectionId(cati, gami, CollectionId.PDH_SOURCE,CollectionId.FULL_IMAGES);
			  		CollateralCollection colc = new CollateralCollection(colcid);

					colc.setSmc(smc1);

					//
					// Lets go fill this out!
					//
					colc.get(cat);
					colc.deactivateAll(cat);
					//
					// ok.. we have stubbed it out. lets make them fat!
					//

					int icount = 0;

					it = colc.values().iterator();

					while (it.hasNext()) {

						icount++;

						Collateral col = (Collateral) it.next();

						D.ebug(
							D.EBUG_DETAIL,
							"syncToCatDb() .. getting collateral and its wwp collection... " +
							col.toString());

						col.get(cat);
						//
						// Now.. lets get the references
						//
						col.getReferences(cat, Collateral.WORLDWIDEPRODUCT_REFERENCE);
						col.getReferences(cat, Collateral.FAMILYPAGE_REFERENCE);

						D.ebug(D.EBUG_DETAIL,
							   "syncToCatDb() .. updating the wwp to the CatDb... " +
							   col.toString());
						col.put(cat, false);

						//
						// DWB .. maybe we can pull when needed
						//
						WorldWideProductCollection wwpc = col.getWorldWideProductCollection();
						D.ebug(D.EBUG_DETAIL,
							   "syncToCatDb() WorldWideProductCollection size is :" +
							   wwpc.size());
						wwpc.values().iterator();

						//
						// since we are calling the our own get references
						// we only have the stubs of the WWP..
						// the collections put method will know that it was
						// formed by an idl category request and will only
						// mess with the category table in the catdb
						wwpc.put(cat, false);

						FamilyPageCollection fpc = col.getFamilyPageCollection();
						D.ebug(CollateralCollection.class, D.EBUG_SPEW,"Family Page...idlToCatDb()size is :" + fpc.size());
						// since we are calling the our own get references
						fpc.put(cat, false);

						if (icount == iChunk) {
							db.commit();
							D.ebug(D.EBUG_DETAIL,
								   "syncToCatDb() *** COMMITING ***");
							icount = 0;
						}


            		}

					//
					// ok.. lets see what we get!
					//
					colc.generateXML();
				}
          		//
		  		// lets clear it out
		  		//
		  		if (smc1 != null) {
		  		    smc1.clearAll();
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
            
            cati.put(cat);
            db.close();
            }
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

    	ReturnDataResultSet rdrs = null;
    	ResultSet rs = null;

        GeneralAreaMapGroup gamp = null;
        Enumeration en = null;
		//int iChunk = 1000;
        try {

            Class mclass = CollateralCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
            Catalog cat = new Catalog();
            String strEnterprise = cat.getEnterprise();
            Database db = cat.getCatalogDatabase();
            GeneralAreaMap gam = cat.getGam();
            Profile prof = cat.getCatalogProfile();
	        String strRoleCode = prof.getRoleCode();
            //Added by Guo Bin, 2007-08-03
            DatabaseExt dbe = new DatabaseExt(db);

            CatalogInterval cati = new CatalogInterval(mclass, cat);
            prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

            for (int icycle = 0; icycle < 3; icycle++) {
                if (icycle == 0) {
                    strEntityType = CollateralCollection.MM_ENTITYTYPE;
                    strVE = CollateralCollection.MM_VE;
                }
                else if (icycle == 1) {
                    strEntityType = CollateralCollection.FB_ENTITYTYPE;
                    strVE = CollateralCollection.FB_VE;
                }
                else {
                    strEntityType = CollateralCollection.IMG_ENTITYTYPE;
                    strVE = CollateralCollection.IMG_VE;
                }
//            strEntityType = CollateralCollection.MM_ENTITYTYPE;
//            strVE = CollateralCollection.MM_VE;
//            //smc1 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
//
//            strEntityType = CollateralCollection.FB_ENTITYTYPE;
//            strVE = CollateralCollection.FB_VE;
//            //smc2 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
//
//            strEntityType = CollateralCollection.IMG_ENTITYTYPE;
//            strVE = CollateralCollection.IMG_VE;
            //smc3 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);

            //smc1.merge(smc2);
            //smc1.merge(smc3);

            //System.out.println(smc1.toString());

        	//
        	// Newest version of chunking
        	//

        	//
        	// We want to load up trsnetter pass 1 here via gbl8184....!!!
        	// We might want to abstract this call to SyncMapCoillectio later on....
        	//
        	int iSessionID = db.getNewSessionID();
        	
            //begin changed by Guo Bin , 2007-08-03 	
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
            //end changed by Guo Bin , 2007-08-03
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

        	    smc1 = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd, iSessionID,SP_SUFFIX);

                gamp = gam.lookupGeneralArea(strGeneralArea);
                en = gamp.elements();

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

			  		CollateralCollectionId colcid = new CollateralCollectionId(cati, gami, CollectionId.PDH_SOURCE,CollectionId.FULL_IMAGES);
			  		CollateralCollection colc = new CollateralCollection(colcid);

					colc.setSmc(smc1);

					//
					// Lets go fill this out!
					//
					colc.get(cat);
					colc.deactivateAll(cat);
					//
					// ok.. we have stubbed it out. lets make them fat!
					//

					int icount = 0;

					it = colc.values().iterator();

					while (it.hasNext()) {

						icount++;

						Collateral col = (Collateral) it.next();

						D.ebug(
							D.EBUG_DETAIL,
							"syncToCatDb() .. getting collateral and its wwp collection... " +
							col.toString());

						col.get(cat);
						//
						// Now.. lets get the references
						//
						col.getReferences(cat, Collateral.WORLDWIDEPRODUCT_REFERENCE);
						col.getReferences(cat, Collateral.FAMILYPAGE_REFERENCE);

						D.ebug(D.EBUG_DETAIL,
							   "syncToCatDb() .. updating the wwp to the CatDb... " +
							   col.toString());
						col.put(cat, false);

						//
						// DWB .. maybe we can pull when needed
						//
						WorldWideProductCollection wwpc = col.getWorldWideProductCollection();
						D.ebug(D.EBUG_DETAIL,
							   "syncToCatDb() WorldWideProductCollection size is :" +
							   wwpc.size());
						wwpc.values().iterator();

						//
						// since we are calling the our own get references
						// we only have the stubs of the WWP..
						// the collections put method will know that it was
						// formed by an idl category request and will only
						// mess with the category table in the catdb
						wwpc.put(cat, false);

						FamilyPageCollection fpc = col.getFamilyPageCollection();
						D.ebug(CollateralCollection.class, D.EBUG_SPEW,"Family Page...idlToCatDb()size is :" + fpc.size());
						// since we are calling the our own get references
						fpc.put(cat, false);

						if (icount == iChunk) {
							db.commit();
							D.ebug(D.EBUG_DETAIL,
								   "syncToCatDb() *** COMMITING ***");
							icount = 0;
						}


            		}

					//
					// ok.. lets see what we get!
					//
					colc.generateXML();
				}
          		//
		  		// lets clear it out
		  		//
		  		if (smc1 != null) {
		  		    smc1.clearAll();
          		}
			}
            cati.put(cat);
            db.close();
            }
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

        CollateralCollectionId colcid = (CollateralCollectionId)this.getId();
        GeneralAreaMapItem gami = colcid.getGami();
        SyncMapCollection smc = this.getSmc();

        //
        // need to set up the profile here
        //
        CatalogInterval cati = colcid.getInterval();
        Profile prof = _cat.getCatalogProfile();
        prof.setEffOn(cati.getEndDate());
        prof.setValOn(cati.getEndDate());
        //
        // O.K. here is where we get to practice GAMI substitution
        //
        // Here we look at what GenareaName is coming back from the Child
        // if it does not match the current gami.. then we skip it
        //TODO
        for (int x = 0; x < smc.getCount(); x++) {
            SyncMapItem smi = smc.get(x);
            if (smi.getRootType().equals("MM")) {
                if (Catalog.containsCountryListFlagCode(_cat, smi.getRootType(),
                    smi.getRootID(),
                    gami.getCountryList())) {
                    CollateralId colid = new CollateralId(smi.getRootType(),
                        smi.getRootID(), gami, colcid);
                    MarketMessage mm = (MarketMessage)this.get(colid);
                    if (mm == null) {
                        mm = new MarketMessage(colid);
                        mm.setSmc(new SyncMapCollection());
                        this.put(mm);
                    }
					// to set whether the MM is active
					if (smi.getRootType().equals(smi.getChildType()) && (smi.getRootID() == smi.getChildID())) {
						mm.setActive(smi.getChildTran().equals("ON"));
					}

                    mm.getSmc().add(smi);
                }
                else {
                    D.ebug(this, D.EBUG_DETAIL,
                           "processSyncMap() Skipping on outside CountryList " +
                           smc);

                }
            }
            else if (smi.getRootType().equals("FB")) {
                if (Catalog.containsCountryListFlagCode(_cat, smi.getRootType(),
                    smi.getRootID(),
                    gami.getCountryList())) {
                    CollateralId colid = new CollateralId(smi.getRootType(),
                        smi.getRootID(), gami, colcid);
                    FeatureBenefit fb = (FeatureBenefit)this.get(colid);
                    if (fb == null) {
                        fb = new FeatureBenefit(colid);
                        fb.setSmc(new SyncMapCollection());
                        this.put(fb);
                    }
					// to set whether the FB is active
					if (smi.getRootType().equals(smi.getChildType()) && (smi.getRootID() == smi.getChildID())) {
						fb.setActive(smi.getChildTran().equals("ON"));
					}

                    fb.getSmc().add(smi);
                }
                else {
                    D.ebug(this, D.EBUG_DETAIL,
                           "processSyncMap() Skipping on outside CountryList " +
                           smc);

                }
            }
            else if (smi.getRootType().equals("IMG")) {
                if (Catalog.containsCountryListFlagCode(_cat, smi.getRootType(),
                    smi.getRootID(),
                    gami.getCountryList())) {
                    CollateralId colid = new CollateralId(smi.getRootType(),
                        smi.getRootID(), gami, colcid);
                    Image img = (Image)this.get(colid);
                    if (img == null) {
                        img = new Image(colid);
                        img.setSmc(new SyncMapCollection());
                        this.put(img);
                    }
					// to set whether the IMG is active
					if (smi.getRootType().equals(smi.getChildType()) && (smi.getRootID() == smi.getChildID())) {
						img.setActive(smi.getChildTran().equals("ON"));
					}

                    img.getSmc().add(smi);
                }
                else {
                    D.ebug(this, D.EBUG_DETAIL,
                           "processSyncMap() Skipping on outside CountryList " +
                           smc);

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
        CollateralCollectionId colcid = (CollateralCollectionId)this.getId();
        CatalogInterval cati = colcid.getInterval();
        String strFromTimestamp = (colcid.isByInterval() ? cati.getStartDate() :
                                   EPOCH);
        String strToTimestamp = (colcid.isByInterval() ? cati.getEndDate() :
                                 FOREVER);

        int iNumberOfElements = this.size();

        try {

            xml.writeEntity("MarketInfoFile");

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
                Collateral col = (Collateral) it.next();
                col.generateXMLFragment(xml);
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
 * $Log: CollateralCollection.java,v $
 * Revision 1.6  2011/05/05 11:21:34  wendy
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
 * Revision 1.2  2007/06/15 07:29:44  jiangshouyi
 * Call Catalog.execUnixShell try to clean up the temp tables
 *
 * Revision 1.1.1.1  2007/06/15 05:42:53  db2admin
 * no message
 *
 * Revision 1.4  2007/03/29 16:24:08  rick
 * MN 30134650 - changed CollateralCollection.java to
 * process MMs and FBs.
 *
 * Revision 1.3  2006/09/06 20:46:20  gregg
 * chunky munky delta
 *
 * Revision 1.2  2006/04/17 23:17:54  gregg
 * simple profile setting fix
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.34  2005/12/02 00:02:07  joan
 * fixes
 *
 * Revision 1.33  2005/11/30 21:53:20  joan
 * fixes
 *
 * Revision 1.32  2005/11/30 21:43:01  joan
 * work on syncmap
 *
 * Revision 1.31  2005/10/26 18:05:13  dave
 * ok.. family page for collateral
 *
 * Revision 1.30  2005/10/25 17:17:29  dave
 * more trace
 *
 * Revision 1.29  2005/10/25 04:09:24  dave
 * curtailing smc's to spew
 *
 * Revision 1.28  2005/10/25 02:52:21  dave
 * commenting out some debugs
 *
 * Revision 1.27  2005/09/20 04:02:48  dave
 * more trace
 *
 * Revision 1.26  2005/09/20 03:28:31  dave
 * more trace for SCM and collateral collection
 *
 * Revision 1.25  2005/09/20 03:09:11  dave
 * syntax fix
 *
 * Revision 1.24  2005/09/20 03:02:48  dave
 * starting to clean up collateral collection
 *
 * Revision 1.23  2005/09/13 16:44:59  dave
 * fixing chunking on Collateral Information
 *
 * Revision 1.22  2005/09/12 00:12:24  dave
 * new sql file to quickly ensure everything is accurate
 * in our catdb setup for data
 *
 * Revision 1.21  2005/06/23 04:58:43  dave
 * have to deal with profile range dates
 *
 * Revision 1.20  2005/06/23 04:43:09  dave
 * ok.. looking for gami variation in the collarteral
 *
 * Revision 1.19  2005/06/22 21:17:15  gregg
 * change signature for processSyncMap
 *
 * Revision 1.18  2005/06/17 04:06:26  dave
 * more trace
 *
 * Revision 1.17  2005/06/17 03:46:47  dave
 * getting close
 *
 * Revision 1.16  2005/06/17 01:26:56  dave
 * some isPending fixes
 *
 * Revision 1.15  2005/06/17 01:05:26  dave
 * tying it all together
 *
 * Revision 1.14  2005/06/16 18:38:22  dave
 * ok.. found another bug
 *
 * Revision 1.13  2005/06/16 18:10:29  dave
 * found a bug!
 *
 * Revision 1.12  2005/06/16 18:01:54  dave
 * checking in some debug trace
 *
 * Revision 1.11  2005/06/13 04:35:33  dave
 * ! needs to be not !
 *
 * Revision 1.10  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.9  2005/06/12 23:21:24  dave
 * working in WWAttributeLogic
 *
 * Revision 1.8  2005/06/11 02:32:54  dave
 * simple changes, getting Net for MarketInfo
 *
 * Revision 1.7  2005/06/11 02:12:13  dave
 * finalizing marketing info pass I
 *
 * Revision 1.6  2005/06/11 02:02:05  dave
 * some collateral cleanup
 *
 * Revision 1.5  2005/06/08 20:48:32  dave
 * ok.. more stuff
 *
 * Revision 1.4  2005/06/08 18:05:44  dave
 * CollateralCollection Build all
 *
 * Revision 1.3  2005/06/08 16:11:31  dave
 * clearn up from first pass
 *
 * Revision 1.2  2005/06/08 14:36:30  dave
 * ok.. expanding the contstruct
 *
 * Revision 1.1  2005/06/08 13:29:58  dave
 * forgot some source
 *
 * Revision 1.9  2005/06/07 04:34:50  dave
 * working on commit control
 *
 * Revision 1.8  2005/05/27 00:55:17  dave
 * adding the merge method.
 *
 * Revision 1.7  2005/05/26 07:32:30  dave
 * some minor syntax
 *
 * Revision 1.6  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.5  2005/05/19 05:04:04  dave
 * interesting signature
 *
 * Revision 1.4  2005/05/18 01:24:14  dave
 * some interface fixes
 *
 * Revision 1.3  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */
