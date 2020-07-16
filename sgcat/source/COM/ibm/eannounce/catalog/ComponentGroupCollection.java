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
 * This holds a collection of ComponentGroups
 *
 * How this collection is derived is expressed in the ComponentGroupCollectionId
 *
 * @author Bala
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComponentGroupCollection extends CatList implements XMLable, CatSync {

	public static final String CMP_VE = "VEWWCOMP1";

    public static final String CMPNTGRP_ENTITYTYPE = "COMPGRP";
    //Added by Guo Bin, 2007-8-03
    private static String SP_SUFFIX = "C";
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
	public ComponentGroupCollection(ComponentGroupCollectionId _cid) {
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
			ComponentGroupCollection.syncToCatDb();
		} else if (args[0].equals("2")) {
            System.out.println("Starting idl");
			ComponentGroupCollection.idlToCatDb();
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
		sb.append(NEW_LINE + "\tCompGrpCollId is:" + this.getComponentGroupCollectionId());
		sb.append(NEW_LINE + "\t---------");
		it = this.values().iterator();
		i = 1;
		while (it.hasNext()) {
			ComponentGroup cmpgrp = (ComponentGroup) it.next();
			sb.append(NEW_LINE + "\t" + (i++) + " - " + cmpgrp.dump(false));
		}

		return sb.toString();

	}

	/**
	 *  (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		return getComponentGroupCollectionId().toString();
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

    public void deactivateAll(Catalog _cat) {
		if (Catalog.isDryRun()) {
			return;
		}

        ReturnStatus returnStatus = new ReturnStatus( -1);
        Database db = _cat.getCatalogDatabase();
        ComponentGroupCollectionId cgid = getComponentGroupCollectionId();
        GeneralAreaMapItem gami = cgid.getGami();
        String strEnterprise = gami.getEnterprise();
        int iNLSID = gami.getNLSID();
		String strCountryList = gami.getCountryList();
        D.ebug(this, D.EBUG_DETAIL,
            "deactivateAll() - here is cgid..." + ":isByInteval:" + cgid.isByInterval() + ":isFromPDH: " + cgid.isFromPDH() +
            ":isFullImages:" + cgid.isFullImages());
        try {

			Iterator it = values().iterator();
            while (it.hasNext()) {
                ComponentGroup cg = (ComponentGroup) it.next();
                String strCompEntityType = cg.getCOMPENTITYTYPE();
                int iCompEntityID = cg.getCOMPENTITYID();

                try {
                    db.callGBL8987(returnStatus, strEnterprise, iNLSID, strCompEntityType, iCompEntityID);

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
	 * This guy will retrieve a list of ComponentGroup Objects
	 * Based upon the flags set in its Collection Id
	 * It will also pass its Collection Id Down
	 *
	 * @param _cat
	 */
	public void get(Catalog _cat) {
		String strTraceBase = " ComponentGroupCollection get method ";

		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;
		Database db = _cat.getCatalogDatabase();

		ComponentGroupCollectionId cmpgrcid = getComponentGroupCollectionId();
		GeneralAreaMapItem gami = cmpgrcid.getGami();
		String strEnterprise = gami.getEnterprise();
		int iNLSID = gami.getNLSID();

		D.ebug(this, D.EBUG_DETAIL, "get() - here is cmpgrcid..." + ":isByInteval:" + cmpgrcid.isByInterval() + ":isFromPDH: " + cmpgrcid.isFromPDH() + ":isFullImages:" + cmpgrcid.isFullImages());

		/*
		 * This is the case where we are going by interval and we need
		 * to create a SyncMap for each Root EntityType this guy represents.
		 */
		if (cmpgrcid.isByInterval() && cmpgrcid.isFromPDH()) {
			this.processSyncMap(_cat);
        } else if (cmpgrcid.isFromCAT()) {

			WorldWideComponentId wwcid = cmpgrcid.getWorldWideComponentId();
			if (wwcid != null) {
				String strProdEntityType = wwcid.getWWEntityType();
				int iProdEntityId = wwcid.getWWEntityID();
			    String strCompEntityType = wwcid.getCmpntEntityType();
                int iCompEntityId = wwcid.getCmpntEntityID();

                ComponentGroupId cgid = new ComponentGroupId(strCompEntityType, iCompEntityId, gami, cmpgrcid);
                ComponentGroup cg = (ComponentGroup) this.get(cgid);

                if (cg == null) {
                    cg = new ComponentGroup(cgid);
                    this.put(cg);
                }
			}
		}
	}

	/**
	 * getComponentGroupCollectionId
	 * @return
	 */
	public final ComponentGroupCollectionId getComponentGroupCollectionId() {
		return (ComponentGroupCollectionId) this.getId();
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
		ReturnStatus rets = new ReturnStatus(-1);
		ComponentGroupCollectionId cmpgrpcid = this.getComponentGroupCollectionId();

		GeneralAreaMapItem gami = cmpgrpcid.getGami();

		String strEnterprise = gami.getEnterprise();
		String strCountryCode = gami.getCountry();
		String strLanguageCode = gami.getLanguage();
		int iNLSID = gami.getNLSID();

		//
		// Were we spawned from a WorldWideComponentid?
		//

		if (cmpgrpcid.hasWorldWideComponentId() && cmpgrpcid.isFromPDH() && cmpgrpcid.isFullImages()) {

			WorldWideComponentId wwcmpid = cmpgrpcid.getWorldWideComponentId();

			String strWWEType = wwcmpid.getWWEntityType();
			int iWWEID = wwcmpid.getWWEntityID();

			Iterator it = this.values().iterator();
			while (it.hasNext()) {
				ComponentGroup cmpgrp = (ComponentGroup) it.next();
				ComponentGroupId cmpgrpid = cmpgrp.getComponentGroupId();
				String strWWEntityType = cmpgrpid.getEntityType();
				int iWWEntityID = cmpgrpid.getEntityID();

				try {
					db.callGBL8400(new ReturnStatus(-1), strEnterprise, strWWEType, iWWEID, cmpgrpid.getEntityType(), cmpgrpid.getEntityID(), (cmpgrp.isActive() ? 1 : 0));
				 if(_bcommit) {
				 	db.commit();
				 }
				 db.freeStatement();
				 db.isPending();

				} catch (MiddlewareException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
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
		String strTraceBase = "ComponentGroupCollection processSyncMap method";
		ComponentGroupCollectionId cmpgrpcid = this.getComponentGroupCollectionId();
		GeneralAreaMapItem gami = cmpgrpcid.getGami();
		SyncMapCollection smc = this.getSmc();

		//
		// O.K.  did we come from a worldwide component?
		//
		//

		if (cmpgrpcid.hasWorldWideComponentId()) {

			for (int x = 0; x < smc.getCount(); x++) {
				SyncMapItem smi = smc.get(x);
				if (smi.getEntity2Type().equals(this.CMPNTGRP_ENTITYTYPE) && smi.getChildLevel() == 0) {
					ComponentGroupId cmpgrpid = new ComponentGroupId(smi.getEntity2Type(), smi.getEntity2ID(), gami, cmpgrpcid);
					ComponentGroup cmpgrp = (ComponentGroup) this.get(cmpgrpid);
					if (cmpgrp == null) {
						cmpgrp = new ComponentGroup(cmpgrpid);
						cmpgrp.setSmc(new SyncMapCollection());
						this.put(cmpgrp);
					}
					cmpgrp.getSmc().add(smi);

					// to set whether the componentgroup is active
					if (smi.getRootType().equals(smi.getChildType()) && (smi.getRootID() == smi.getChildID())) {
						System.out.println(strTraceBase + " at the root: " + smi.dump(false));
						cmpgrp.setActive(smi.getChildTran().equals("ON"));
					}
				}
			}
		} else {
			//
			// For now.. we must have not came from some higher form...
			//
			for (int x = 0; x < smc.getCount(); x++) {
				SyncMapItem smi = smc.get(x);
//System.out.println("Processing smi"+smi.toString());
				if (smi.getRootType().equals(this.CMPNTGRP_ENTITYTYPE) && smi.getChildLevel() == 0) {
                    ComponentGroupId cmpgrpid = new ComponentGroupId(smi.getRootType(), smi.getRootID(), gami, cmpgrpcid);

		            ComponentGroup cmpgrp = (ComponentGroup) this.get(cmpgrpid);
		            if (cmpgrp == null) {

//System.out.println("Creating new CMPGRPID with "+cmpgrpid.toString());
	                    cmpgrp = new ComponentGroup(cmpgrpid);
//System.out.println("Creating new CMPGRP with "+cmpgrp.toString());
	                    cmpgrp.setSmc(new SyncMapCollection());
	                    this.put(cmpgrp);
					}

					// to set whether the componentgroup is active
					if (smi.getRootType().equals(smi.getChildType()) && (smi.getRootID() == smi.getChildID())) {
						System.out.println(strTraceBase + " at the root: " + smi.dump(false));
						cmpgrp.setActive(smi.getChildTran().equals("ON"));
					}

                    cmpgrp.getSmc().add(smi);
                }
			}
		}
	}

	/**
	 * This is the guy who moves data between the PDH and the CatDb
	 *
	 */
	public static void syncToCatDb() {

        System.out.println("In syncToCatDb");
	 	SyncMapCollection smcOuter = null;

		String strEntityType = null;
		String strVE = null;

		GeneralAreaMapGroup gamp = null;
		Enumeration en = null;

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;

		int ichunk = 1000;
		
		//
        // Lets try to clean up the temp tables first
        //
//        try {
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(ComponentGroupCollection.class));
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

			Class mclass = ComponentGroupCollection.class;
			String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

			Catalog cat = new Catalog();
			Database db = cat.getCatalogDatabase();
			Profile prof = cat.getCatalogProfile();
		    String strRoleCode = prof.getRoleCode();
            String strEnterprise = cat.getEnterprise();

			GeneralAreaMap gam = cat.getGam();

			CatalogInterval cati = new CatalogInterval(mclass, cat);

			strEntityType = ComponentGroupCollection.CMPNTGRP_ENTITYTYPE;
			strVE = ComponentGroupCollection.CMP_VE;

			//smcOuter = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
            //System.out.println(smc1.dump(true));

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
        	} finally {
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
          	// need to put this here
          	//
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

					ComponentGroupCollectionId cmpgrpcid = new ComponentGroupCollectionId(cati, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);

					ComponentGroupCollection cmpgrpc = new ComponentGroupCollection(cmpgrpcid);
					cmpgrpc.setSmc(smcOuter);

					//
					// Lets go fill this out!
					//
					cmpgrpc.get(cat);
					cmpgrpc.deactivateAll(cat);
					//
					// ok.. we have stubbed it out. lets make them fat!
					//

					int icount = 0;

					it = cmpgrpc.values().iterator();
            	    if (!it.hasNext())D.ebug(D.EBUG_DETAIL, "syncToCatDb() Did not find any cmgrp in collection");
					while (it.hasNext()) {

						icount++;

						ComponentGroup cmpgrp = (ComponentGroup) it.next();

						D.ebug(D.EBUG_DETAIL, "syncToCatDb() .. getting cmpgrp and its collections ... " + cmpgrp.toString());

						cmpgrp.get(cat);
						cmpgrp.getReferences(cat, cmpgrp.WWCOMPONENT_REFERENCE);

						D.ebug(D.EBUG_DETAIL, "syncToCatDb() .. updating the cmpgrp to the CatDb... " + cmpgrp.toString());
						cmpgrp.put(cat, false);

            	        WorldWideComponentCollection wwcc = cmpgrp.getWorldWideComponentCollection();
            	        D.ebug(D.EBUG_DETAIL, "syncToCatDb() WorldWideComponentCollection size is :" + wwcc.size());
            	        Iterator it2 = wwcc.values().iterator();
            	        while (it2.hasNext()) {
            	            WorldWideComponent wwc = (WorldWideComponent) it2.next();
            	            wwc.get(cat);

            	            wwc.put(cat, false);
            	        }

						if (icount == ichunk) {
							db.commit();
							D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
							icount = 0;
						}

					}

					//
					// ok.. lets see what we get!
					//
					cmpgrpc.generateXML();
					db.commit();

				}
				if (smcOuter != null) {
				    smcOuter.clearAll();
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

		} catch (MiddlewareException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
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

        System.out.println("In syncToCatDb");
	 	SyncMapCollection smcOuter = null;

		String strEntityType = null;
		String strVE = null;

		GeneralAreaMapGroup gamp = null;
		Enumeration en = null;

        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;

		int ichunk = 1000;

		try {

			Class mclass = ComponentGroupCollection.class;
			String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

			Catalog cat = new Catalog();
			Database db = cat.getCatalogDatabase();
			Profile prof = cat.getCatalogProfile();
		    String strRoleCode = prof.getRoleCode();
            String strEnterprise = cat.getEnterprise();

			GeneralAreaMap gam = cat.getGam();

			CatalogInterval cati = new CatalogInterval(mclass, cat);

			strEntityType = ComponentGroupCollection.CMPNTGRP_ENTITYTYPE;
			strVE = ComponentGroupCollection.CMP_VE;
            //Added by Guo Bin, 2007-08-03
            DatabaseExt dbe = new DatabaseExt(db);


			//smcOuter = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
            //System.out.println(smc1.dump(true));

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
        	} finally {
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
          	// need to put this here
          	//
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

					ComponentGroupCollectionId cmpgrpcid = new ComponentGroupCollectionId(cati, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);

					ComponentGroupCollection cmpgrpc = new ComponentGroupCollection(cmpgrpcid);
					cmpgrpc.setSmc(smcOuter);

					//
					// Lets go fill this out!
					//
					cmpgrpc.get(cat);
					cmpgrpc.deactivateAll(cat);
					//
					// ok.. we have stubbed it out. lets make them fat!
					//

					int icount = 0;

					it = cmpgrpc.values().iterator();
            	    if (!it.hasNext())D.ebug(D.EBUG_DETAIL, "syncToCatDb() Did not find any cmgrp in collection");
					while (it.hasNext()) {

						icount++;

						ComponentGroup cmpgrp = (ComponentGroup) it.next();

						D.ebug(D.EBUG_DETAIL, "syncToCatDb() .. getting cmpgrp and its collections ... " + cmpgrp.toString());

						cmpgrp.get(cat);
						cmpgrp.getReferences(cat, cmpgrp.WWCOMPONENT_REFERENCE);

						D.ebug(D.EBUG_DETAIL, "syncToCatDb() .. updating the cmpgrp to the CatDb... " + cmpgrp.toString());
						cmpgrp.put(cat, false);

            	        WorldWideComponentCollection wwcc = cmpgrp.getWorldWideComponentCollection();
            	        D.ebug(D.EBUG_DETAIL, "syncToCatDb() WorldWideComponentCollection size is :" + wwcc.size());
            	        Iterator it2 = wwcc.values().iterator();
            	        while (it2.hasNext()) {
            	            WorldWideComponent wwc = (WorldWideComponent) it2.next();
            	            wwc.get(cat);

            	            wwc.put(cat, false);
            	        }

						if (icount == ichunk) {
							db.commit();
							D.ebug(D.EBUG_DETAIL, "syncToCatDb() *** COMMITING ***");
							icount = 0;
						}

					}

					//
					// ok.. lets see what we get!
					//
					cmpgrpc.generateXML();
					db.commit();

				}
				if (smcOuter != null) {
				    smcOuter.clearAll();
                }
			}

			cati.put(cat);
			db.close();

		} catch (MiddlewareException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * idlToCatDb
	 * This guy will take all known root entities and process them in one
	 * big chunk.. if we run out of of memory, we start employing memory
	 * tricks we know all to well to chunk through them
	 *
	 */
	public static void idlToCatDb() {
        System.out.println("In idl");
	 	SyncMapCollection smc1 = null;

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
//            Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(ComponentGroupCollection.class));
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

			Class mclass = ComponentGroupCollection.class;
			String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

			Catalog cat = new Catalog();
            String strEnterprise = cat.getEnterprise();
            Database db = cat.getCatalogDatabase();
            Database pdhdb = cat.getPDHDatabase();
			Profile prof = cat.getCatalogProfile();

			GeneralAreaMap gam = cat.getGam();

			CatalogInterval cati = new CatalogInterval(mclass, cat);
			prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

			strEntityType = ComponentGroupCollection.CMPNTGRP_ENTITYTYPE;
			strVE = ComponentGroupCollection.CMP_VE;

			//
			// Newest version of chunking
			//
			int iChunk = 1000;
			try {
				rs = pdhdb.callGBL9010(new ReturnStatus(-1),strEnterprise, strEntityType, iChunk);
				rdrs = new ReturnDataResultSet(rs);
			} finally {
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
			for (int x = 0; x < rdrs.size();x++) {

				iFloor = rdrs.getColumnInt(x,0);
				iCeiling = rdrs.getColumnInt(x,1);
				iStart = rdrs.getColumnInt(x,2);
				iEnd = rdrs.getColumnInt(x,3);
				D.ebug(D.EBUG_DETAIL, "GBL9010:et" + strEntityType + ":floor:" + iFloor + ":ceiling:" + iCeiling + ":start:" + iStart + ":finish:" + iEnd);

				smc1 = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd);

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

					ComponentGroupCollectionId cmpgrpcid =
						new ComponentGroupCollectionId(cati, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);

					ComponentGroupCollection cmpgrpc = new ComponentGroupCollection(cmpgrpcid);
					cmpgrpc.setSmc(smc1);

					//
					// Lets go fill this out!
					//
					cmpgrpc.get(cat);

					//
					// ok.. we have stubbed it out. lets make them fat!
					//

					int icount = 0;

					it = cmpgrpc.values().iterator();
                	if (!it.hasNext())D.ebug(D.EBUG_DETAIL, "idlToCatDb() Did not find any cmgrp in collection");
					while (it.hasNext()) {

						icount++;

						ComponentGroup cmpgrp = (ComponentGroup) it.next();

						D.ebug(D.EBUG_DETAIL, "idlToCatDb() .. getting cmpgrp and its collections ... " + cmpgrp.toString());

						cmpgrp.get(cat);
						cmpgrp.getReferences(cat, cmpgrp.WWCOMPONENT_REFERENCE);

						D.ebug(D.EBUG_DETAIL, "idlToCatDb() .. updating the cmpgrp to the CatDb... " + cmpgrp.toString());
						cmpgrp.put(cat, true);

                        WorldWideComponentCollection wwcc = cmpgrp.getWorldWideComponentCollection();
                        D.ebug(D.EBUG_DETAIL, "idlToCatDb() WorldWideComponentCollection size is :" + wwcc.size());
                        Iterator it2 = wwcc.values().iterator();
                        while (it2.hasNext()) {
                            WorldWideComponent wwc = (WorldWideComponent) it2.next();
                            wwc.get(cat);
                            wwc.put(cat, false);
                        }

						if (icount == iChunk) {
							db.commit();
							D.ebug(D.EBUG_DETAIL, "idlToCatDb() *** COMMITING ***");
							icount = 0;
						}
					}

					//
					// ok.. lets see what we get!
					//
					cmpgrpc.generateXML();
					db.commit();

				}
			}
			cati.put(cat);
			db.close();

		} catch (MiddlewareException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
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
		ComponentGroupCollectionId cmpgrpcid = this.getComponentGroupCollectionId();
		CatalogInterval cati = cmpgrpcid.getInterval();
		String strFromTimestamp = (cmpgrpcid.isByInterval() ? cati.getStartDate() : EPOCH);
		String strToTimestamp = (cmpgrpcid.isByInterval() ? cati.getEndDate() : FOREVER);

		int iNumberOfElements = this.size();

		try {
/*
			xml.writeEntity("ProductFile");

			xml.writeEntity("FromTimestamp");
			xml.write(strFromTimestamp);
			xml.endEntity();

			xml.writeEntity("ToTimestamp");
			xml.write(strToTimestamp);
			xml.endEntity();

			xml.writeEntity("NumberOfElements");
			xml.write(iNumberOfElements + "");
			xml.endEntity();

 */			/*
			 * For now .. we just have the update command..
			 */
	/* 		xml.writeEntity("Command");
			xml.write("update");
			xml.endEntity();

			xml.writeEntity("Source");
			xml.write("STG");
			xml.endEntity();
 */
			it = values().iterator();

			while (it.hasNext()) {
				ComponentGroup cmpgrp = (ComponentGroup) it.next();
				cmpgrp.generateXMLFragment(xml);
			}

			//xml.endEntity();

			xml.finishEntity();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
 * $Log: ComponentGroupCollection.java,v $
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
 * Revision 1.2  2007/06/15 07:29:44  jiangshouyi
 * Call Catalog.execUnixShell try to clean up the temp tables
 *
 * Revision 1.1.1.1  2007/06/15 05:42:53  db2admin
 * no message
 *
 * Revision 1.3  2006/09/06 20:35:17  gregg
 * fix
 *
 * Revision 1.2  2006/09/06 20:31:29  gregg
 * chunky munky delta
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.27  2005/12/08 21:33:20  joan
 * fixes
 *
 * Revision 1.26  2005/11/30 22:06:26  joan
 * fixes
 *
 * Revision 1.25  2005/11/30 21:43:01  joan
 * work on syncmap
 *
 * Revision 1.24  2005/11/23 18:00:05  joan
 * fixes
 *
 * Revision 1.23  2005/11/23 17:55:21  joan
 * fixes
 *
 * Revision 1.22  2005/11/23 00:01:48  joan
 * fixes
 *
 * Revision 1.21  2005/11/21 23:52:25  joan
 * fixes
 *
 * Revision 1.20  2005/11/21 23:46:13  joan
 * fixes
 *
 * Revision 1.19  2005/10/12 23:22:11  joan
 * adjust to new version of chunking
 *
 * Revision 1.18  2005/10/06 21:03:39  joan
 * fixes
 *
 * Revision 1.17  2005/10/05 20:59:28  joan
 * fixes
 *
 * Revision 1.16  2005/10/05 19:30:26  joan
 * fixes
 *
 * Revision 1.15  2005/10/04 23:24:16  joan
 * work on component
 *
 * Revision 1.14  2005/10/04 20:43:01  joan
 * fixes
 *
 * Revision 1.13  2005/10/04 20:35:30  joan
 * fixes
 *
 * Revision 1.12  2005/10/03 17:40:18  joan
 * fixes
 *
 * Revision 1.11  2005/07/14 17:34:22  bala
 * fixes
 *
 * Revision 1.10  2005/07/13 21:37:07  bala
 * fixes
 *
 * Revision 1.9  2005/07/12 21:57:30  bala
 * sync up
 *
 */
