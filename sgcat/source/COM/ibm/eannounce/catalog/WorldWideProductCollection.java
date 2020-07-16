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
 * This hold a collection of WorldWideProducts
 * 
 * How this collection is derived is expressed in the
 * WorldWideProductCollectionId
 * 
 * @author David Bigelow
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Commentss
 */
public class WorldWideProductCollection extends CatList implements XMLable,
		CatSync {

	/**
	 * FIELD = WWSEO EntityType in eannounce
	 */
	public static final String SEO_ENTITYTYPE = "WWSEO";

	/**
	 * FIELD - TMF EntityType in eannounce
	 */
	public static final String TMF_ENTITYTYPE = "MODEL";

	public static final String TMF_VE = "VETMF1";

	public static final String SEO_VE = "VEWWSEO1";

	//Added by Guo Bin, 2007-8-03
	private static String SP_SUFFIX = "F";

	/**
	 * Holds the answer to GBL8104
	 */
	private SyncMapCollection m_smc = null;

	/**
	 * This is the World Wide Product Collection with nothing in its list the
	 * _cid is the CollectionID that instructs it how to fill its list out when
	 * its .get method is called
	 * 
	 * @param _cid
	 */
	public WorldWideProductCollection(WorldWideProductCollectionId _cid) {
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
			WorldWideProductCollection.syncToCatDb();
		} else if (args[0].equals("2")) {
			WorldWideProductCollection.idlToCatDb();
		}
	}

	/**
	 * (non-Javadoc)
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
		sb.append(NEW_LINE + "\tCatId is:"
				+ this.getWorldWideProductCollectionId());
		sb.append(NEW_LINE + "\t---------");
		sb.append(NEW_LINE + "\tWWP");
		sb.append(NEW_LINE + "\t---------");
		it = this.values().iterator();
		i = 1;
		while (it.hasNext()) {
			WorldWideProduct wwp = (WorldWideProduct) it.next();
			sb.append(NEW_LINE + "\t" + (i++) + " - " + wwp);
		}

		return sb.toString();

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		return this.getWorldWideProductCollectionId().toString();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public final boolean equals(Object obj) {
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param _cat
	 * @param _icase
	 */
	public void getReferences(Catalog _cat, int _icase) {
		// TODO Auto-generated method stub
	}

	/**
	 * This guy will retrieve a list of WorldWideProduct Objects Based upon the
	 * flags set in its Collection Id It will also pass its Collection Id Down
	 * 
	 * @param _cat
	 */
	public void get(Catalog _cat) {

		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;
		Database db = _cat.getCatalogDatabase();

		WorldWideProductCollectionId wwpcid = getWorldWideProductCollectionId();
		GeneralAreaMapItem gami = wwpcid.getGami();
		String strEnterprise = gami.getEnterprise();
		int iNLSID = gami.getNLSID();

		D.ebug(D.EBUG_SPEW, "get() - here is wwcpid..." + ":isByInteval:"
				+ wwpcid.isByInterval() + ":isFromPDH: " + wwpcid.isFromPDH()
				+ ":isFullImages:" + wwpcid.isFullImages() + "isByPartNumber"
				+ wwpcid.isByPartNumber());

		try {

			/*
			 * This is the case where we are going by interval and we need to
			 * create a SyncMap for each Root EntityType this guy represents.
			 */
			if (wwpcid.isByInterval() && wwpcid.isFromPDH()) {

				this.processSyncMap(_cat);

			} else if (wwpcid.isByPartNumber()) {

				if (wwpcid.isFromCAT() && wwpcid.isNetChanges()) {
					try {
						rs = db.callGBL4015(new ReturnStatus(-1),
								strEnterprise, iNLSID, wwpcid.getWWPartNumber()
										+ "%");
						rdrs = new ReturnDataResultSet(rs);
					} finally {
						rs.close();
						rs = null;
						db.commit();
						db.freeStatement();
						db.isPending();
					}

					for (int i = 0; i < rdrs.size(); i++) {

						String strEntityType = rdrs.getColumn(i, 0);
						int iEntityID = rdrs.getColumnInt(i, 1);

						//
						// Lets build one in the context of this list
						// we do this by passing the wwpcid into to wwpid
						//
						// this is much better than passing the entire structure
						// as
						// we currently do in the eannounce object model
						//
						WorldWideProductId wwpid = new WorldWideProductId(
								strEntityType, iEntityID, gami, wwpcid);
						WorldWideProduct wwp = new WorldWideProduct(wwpid);
						this.put(wwp);
					}
				} else if (wwpcid.isFromPDH() && wwpcid.isFullImages()) {

					// Lets get stuff from the PDH here
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
	 * getWorldWideProductCollectionId
	 * 
	 * @return
	 */
	public final WorldWideProductCollectionId getWorldWideProductCollectionId() {
		return (WorldWideProductCollectionId) this.getId();
	}

	/**
	 * put - Since we are collection, this guy manages relationships between the
	 * things in this list.. and the controlling parent ID
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
		WorldWideProductCollectionId wwpcid = this
				.getWorldWideProductCollectionId();

		GeneralAreaMapItem gami = wwpcid.getGami();

		String strEnterprise = gami.getEnterprise();
		String strCountryCode = gami.getCountry();
		String strLanguageCode = gami.getLanguage();
		int iNLSID = gami.getNLSID();

		//
		// Were we spawned from a CollateralId?
		//

		if (wwpcid.hasCollateralId() && wwpcid.isFromPDH()
				&& wwpcid.isFullImages()) {

			CollateralId colid = wwpcid.getCollateralId();
			String strCollEntityType = colid.getCollEntityType();
			int iCollEntityID = colid.getCollEntityID();

			D.ebug(D.EBUG_SPEW,
					"put() - here we are managing our wwProduct Relators..."
							+ ":isByInteval:" + wwpcid.isByInterval()
							+ ":isFromPDH: " + wwpcid.isFromPDH()
							+ ":isFullImages:" + wwpcid.isFullImages()
							+ ":hasCollateralId:" + wwpcid.hasCollateralId()
							+ ":colid:" + wwpcid.getCollateralId());

			Iterator it = this.values().iterator();
			while (it.hasNext()) {
				WorldWideProduct wwp = (WorldWideProduct) it.next();
				WorldWideProductId wwpid = wwp.getWorldWideProductId();
				String strWWEntityType = wwpid.getEntityType();
				int iWWEntityID = wwpid.getEntityID();

				try {
					db.callGBL8979(new ReturnStatus(-1), strEnterprise,
							strWWEntityType, iWWEntityID, strCollEntityType,
							iCollEntityID, (wwp.isActive() ? 1 : 0));
					if (_bcommit) {
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
	 * (non-Javadoc)
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
	 * This is they guy that drives reading and drilling down through the
	 * SyncMapCollection and bringing the Catalog Up to speed The sync map is
	 * going to be the same one no matter the language and intent.. we will need
	 * to sort this one out as we move further down the road
	 */
	public void processSyncMap(Catalog _cat) {

		WorldWideProductCollectionId wwpcid = this
				.getWorldWideProductCollectionId();
		GeneralAreaMapItem gami = wwpcid.getGami();
		SyncMapCollection smc = this.getSmc();

		//
		// O.K. did we come from a colateral person?
		//
		// if so. we need to look for both MODELS.. and WWSEO's
		// These are the guys that have to be tied back to us..
		//

		if (wwpcid.hasCollateralId()) {

			D.ebug(D.EBUG_SPEW, "processSyncMap has Collateral ID");
			//for (int x = 0; x < smc.getCount(); x++) {
			for (int x = smc.getCount() - 1; x >= 0; x--) {
				SyncMapItem smi = smc.get(x);
				D.ebug(D.EBUG_SPEW, "...processSyncMap smi:" + smi.toString());
				if (smi.getChildType().equals(this.SEO_ENTITYTYPE)
						&& smi.getChildLevel() == 0) {
					D
							.ebug(D.EBUG_SPEW,
									"processSyncMap has Collateral ID SEO_ENTITYTYPE 1");
					WorldWideProductId wwpid = new WorldWideProductId(smi
							.getChildType(), smi.getChildID(), gami, wwpcid);
					WorldWideProduct wwp = (WorldWideProduct) this.get(wwpid);
					if (wwp == null) {
						wwp = new WorldWideProduct(wwpid);
						wwp.setSmc(new SyncMapCollection());
						this.put(wwp);
					}
					wwp.setActive(smi.getChildTran().equals("ON"));
					wwp.getSmc().add(smi);
				} else if ((smi.getChildType().equals(this.TMF_ENTITYTYPE) && smi
						.getChildLevel() == 0)) {
					D
							.ebug(D.EBUG_SPEW,
									"processSyncMap has Collateral ID TMF_ENTITYTYPE 1");
					WorldWideProductId wwpid = new WorldWideProductId(smi
							.getChildType(), smi.getChildID(), gami, wwpcid);
					WorldWideProduct wwp = (WorldWideProduct) this.get(wwpid);
					if (wwp == null) {
						wwp = new WorldWideProduct(wwpid);
						wwp.setSmc(new SyncMapCollection());
						this.put(wwp);
					}
					wwp.setActive(smi.getChildTran().equals("ON"));
					wwp.getSmc().add(smi);
					//
					// Special LSEOBUNDLE Case
					//
				} else if ((smi.getChildType().equals("LSEOBUNDLE") && smi
						.getChildLevel() == 0)) {
					D.ebug(D.EBUG_SPEW,
							"processSyncMap has Collateral ID LSEOBUNDLE...");
					WorldWideProductId wwpid = new WorldWideProductId(smi
							.getChildType(), smi.getChildID(), gami, wwpcid);
					WorldWideProduct wwp = (WorldWideProduct) this.get(wwpid);
					if (wwp == null) {
						wwp = new WorldWideProduct(wwpid);
						wwp.setSmc(new SyncMapCollection());
						this.put(wwp);
					}
					wwp.setActive(smi.getChildTran().equals("ON"));
					wwp.getSmc().add(smi);
				}

			}
		} else if (wwpcid.hasWorldWideComponentId()) {
			D.ebug(D.EBUG_SPEW,
					"processSyncMap has WWCOMPONENT ID TMF_ENTITYTYPE 1");
			for (int x = 0; x < smc.getCount(); x++) {
				SyncMapItem smi = smc.get(x);
				if (smi.getRootType().equals(this.SEO_ENTITYTYPE)) {
					D
							.ebug(D.EBUG_SPEW,
									"processSyncMap has WWCOMPONENT ID SEO_ENTITYTYPE 1");
					WorldWideProductId wwpid = new WorldWideProductId(smi
							.getRootType(), smi.getRootID(), gami, wwpcid);
					WorldWideProduct wwp = (WorldWideProduct) this.get(wwpid);
					if (wwp == null) {
						wwp = new WorldWideProduct(wwpid);
						wwp.setSmc(new SyncMapCollection());
						this.put(wwp);
					}
					wwp.getSmc().add(smi);
				}
			}

		} else {
			//
			// For now.. we must have not came from some higher form...
			//
			// ok.. we have to distribute the smc's down to the ww product
			// itself
			//
			for (int x = 0; x < smc.getCount(); x++) {
				SyncMapItem smi = smc.get(x);
				D.ebug(D.EBUG_SPEW, "processSyncMap:Processing Item:" + x + ":"
						+ smi.dump(false));
				WorldWideProductId wwpid = new WorldWideProductId(smi
						.getRootType(), smi.getRootID(), gami, wwpcid);
				WorldWideProduct wwp = (WorldWideProduct) this.get(wwpid);
				if (wwp == null) {
					wwp = new WorldWideProduct(wwpid);
					wwp.setSmc(new SyncMapCollection());
					this.put(wwp);
				}
				wwp.getSmc().add(smi);
			}
			
		}
	}

	/**
	 * This is the guy who moves data between the PDH and the CatDb
	 *  
	 */
	public static void syncToCatDb() {

		SyncMapCollection smcOuter = null;

		String strEntityType = null;
		String strVE = null;

		GeneralAreaMapGroup gamp = null;
		Enumeration en = null;

		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;

		int icommitchunk = 1000;
		D.ebug(D.EBUG_SPEW, "WorldWideProductCollection.syncToCatDb");
		//
		// Lets try to clean up the temp tables first
		//    //
		//    try {
		//        Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(WorldWideProductCollection.class));
		//    }
		//    catch (InterruptedException ex1) {
		//        ex1.printStackTrace();
		//        System.exit( -1);
		//    }
		//    catch (IOException ex1) {
		//        ex1.printStackTrace();
		//        System.exit( -1);
		//    }

		try {

			Class mclass = WorldWideProductCollection.class;
			String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

			Catalog cat = new Catalog();
			Database db = cat.getCatalogDatabase();
			String strEnterprise = cat.getEnterprise();
			GeneralAreaMap gam = cat.getGam();
			Profile prof = cat.getCatalogProfile();
			String strRoleCode = prof.getRoleCode();

			CatalogInterval cati = new CatalogInterval(mclass, cat);
			prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());
			gamp = gam.lookupGeneralArea(strGeneralArea);
			en = gamp.elements();

			if (!en.hasMoreElements()) {
				D.ebug(D.EBUG_WARN, "no gami to find!!!");
			}

			for (int icycle = 0; icycle < 2; icycle++) {
				//
				// lets clear it out
				//
				if (smcOuter != null) {
					smcOuter.clearAll();
				}

				if (icycle == 0) {
					// SEO section
					strEntityType = WorldWideProductCollection.SEO_ENTITYTYPE;
					strVE = WorldWideProductCollection.SEO_VE;
					//smcOuter = new SyncMapCollection(cat, strEntityType,
					// strVE, cati, 9);
				} else {
					// TMF section
					strEntityType = WorldWideProductCollection.TMF_ENTITYTYPE;
					strVE = WorldWideProductCollection.TMF_VE;
					//smcOuter = new SyncMapCollection(cat, strEntityType,
					// strVE, cati, 9);
				}
				//add by houjie&liubing 2007-08-28, get configurable NLSID
				// information for WorldWideProduct,ProdStruct
				int iNLSSave[] = new int[2];
				iNLSSave[0] = CatalogProperties.getSaveNLSID(
						WorldWideProductCollection.class,
						WorldWideProduct.class, strVE);
				iNLSSave[1] = CatalogProperties.getSaveNLSID(
						WorldWideProductCollection.class, ProdStruct.class,
						strVE);
				D.ebug(D.EBUG_SPEW,
						"WorldWideProductCollection.WorldWideProduct." + strVE
								+ "_nlssave=" + iNLSSave[0]);
				D.ebug(D.EBUG_SPEW, "WorldWideProductCollection.ProdStruct."
						+ strVE + "_nlssave=" + iNLSSave[1]);
				//
				// Newest version of chunking
				//

				//
				// We want to load up trsnetter pass 1 here via gbl8184....!!!
				// We might want to abstract this call to SyncMapCoillectio
				// later on....
				//
				int iSessionID = db.getNewSessionID();
				List sessionIdList = new ArrayList();
				sessionIdList.add(new Integer(iSessionID));
				try {
					db.callGBL8184(new ReturnStatus(-1), iSessionID,
							strEnterprise, strEntityType, strVE, strRoleCode,
							cati.getStartDate(), cati.getEndDate(), 9, "", -1,
							1);
				} finally {
					db.commit();
					db.freeStatement();
					db.isPending();
				}

				int iChunk = CatalogProperties.getIDLChunkSize(mclass, strVE);
				try {
					rs = db.callGBL9012(new ReturnStatus(-1), strEnterprise,
							strEntityType, iSessionID, iChunk);
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
					D.ebug(D.EBUG_SPEW, "GBL9012:et" + strEntityType
							+ ":floor:" + iFloor + ":ceiling:" + iCeiling
							+ ":start:" + iStart + ":finish:" + iEnd);

					smcOuter = new SyncMapCollection(cat, strVE, strEntityType,
							iStart, iEnd, iSessionID, sessionIdList);

					//
					// need to put this here
					//
					en = gamp.elements();

					while (en.hasMoreElements()) {

						Iterator it = null;

						GeneralAreaMapItem gami = (GeneralAreaMapItem) en
								.nextElement();
						//
						// Lets set the NLSItem we need to be working in right
						// now
						//
						Vector vct = prof.getReadLanguages();
						for (int i = 0; i < vct.size(); i++) {
							NLSItem nlsi = (NLSItem) vct.elementAt(i);
							D.ebug(D.EBUG_SPEW,
									"WorldWideProductCollection gami/prof check:nlsi.getNLSID()="
											+ nlsi.getNLSID()
											+ ",gami.getNLSID()="
											+ gami.getNLSID());
							if (nlsi.getNLSID() == gami.getNLSID()) {
								D.ebug(D.EBUG_SPEW,
										"WorldWideProductCollection gami/prof check: setting read language to:"
												+ nlsi.getNLSID());
								prof.setReadLanguage(nlsi);
								break;
							}
						}

						WorldWideProductCollectionId wwpcid = new WorldWideProductCollectionId(
								cati, gami, CollectionId.PDH_SOURCE,
								CollectionId.NET_CHANGES);

						WorldWideProductCollection wwpc = new WorldWideProductCollection(
								wwpcid);
						wwpc.setSmc(smcOuter);

						//
						// Lets go fill this out!
						//
						wwpc.get(cat);

						//
						// ok.. we have stubbed it out. lets make them fat!
						//
						it = wwpc.values().iterator();

						int icount = 0;

						while (it.hasNext()) {

							icount++;

							WorldWideProduct wwp = (WorldWideProduct) it.next();
							WorldWideProductId wwpid = wwp
									.getWorldWideProductId();

							//For each world wide product, stub out its chidren
							D.ebug(D.EBUG_SPEW, "Deactivating structure for "
									+ wwpid.dump(false));
							wwpc.deactivateStructure(wwpid, cat, false);

							//
							// lets take a look at the sync map col
							//
							SyncMapCollection smc = wwp.getSmc();
							D.ebug(D.EBUG_SPEW, smc.toString());

							D.ebug(D.EBUG_SPEW,
									"syncToCatDb() .. getting wwp and its collections... "
											+ wwp.toString());

							//add by houjie&liubing 2007-08-28 : check NLSID
							// for WorldWideProduct
							boolean isWWPLoaded = false;
							if (iNLSSave[0] == 0
									|| iNLSSave[0] == gami.getNLSID()) {
								wwp.get(cat);
								D.ebug(D.EBUG_SPEW,
										"syncToCatDb() .. updating the wwp to the CatDb... "
												+ wwp.toString());
								wwp.put(cat, false);
								isWWPLoaded = true;
							} else {
								isWWPLoaded = false;
								D.ebug(D.EBUG_SPEW, ">>Skipping WWP save for "
										+ wwp.getId()
										+ ".  Targeting NLSID of "
										+ iNLSSave[0] + " not "
										+ gami.getNLSID());
							}

							//add by houjie&liubing 2007-08-28 : check NLSID
							// for ProdStructCollection
							if (isWWPLoaded
									&& (iNLSSave[1] == gami.getNLSID() || iNLSSave[1] == 0)) {
								wwp.getReferences(cat,
										WorldWideProduct.PRODSTRUCT_REFERENCE);
								//
								// DWB .. maybe we can pull when needed
								//
								// delete by guobin 2007-10-16
								//                  ProdStructCollection psc =
								// wwp.getProdStructCollection();
								//                  D.ebug(D.EBUG_SPEW,
								//                         "syncToCatDb() ProdStructCollection size is
								// :" + psc.size());
								//                  Iterator it2 = psc.values().iterator();
								//                  while (it2.hasNext()) {
								//                    ProdStruct ps = (ProdStruct) it2.next();
								//                    ps.get(cat);
								//                    //
								//                    // we would normally compare here
								//                    // before we do a put
								//                    //
								//                    ps.put(cat, false);
								//                  }
							} else {
								D.ebug(D.EBUG_SPEW,
										">>> Skipping ProdStruct Save for "
												+ wwp.getId()
												+ ".  Only Targeting NLSID of "
												+ iNLSSave[1] + "(not "
												+ gami.getNLSID() + ")");
							}

							//add by houjie&liubing 2007-08-28 : no need to
							// check NLSID for WorldWideAttributeCollection
							wwp.getReferences(cat,
									WorldWideProduct.ATTRIBUTE_REFERENCE);
							//
							// ok.. lets do some ww attributes
							//
							WorldWideAttributeCollection wwac = wwp
									.getWorldWideAttributeCollection();
							D.ebug(D.EBUG_SPEW,
									"syncToCatDb() WorldWideAttributeCollection size is :"
											+ wwac.size());
							Iterator it2 = wwac.values().iterator();
							while (it2.hasNext()) {
								WorldWideAttribute wwa = (WorldWideAttribute) it2
										.next();
								wwa.get(cat);
								//
								// we would normally compare here
								// before we do a put
								//
								wwa.put(cat, false);
							}

							//
							//
							// GAB: HERE'S WHERE OUR BASICRULE PROCESSING GOES
							// FOR A GIVEN ***WWATTRIBUTE***.
							//   - Assume: rules are per
							// (item)EntityType/(item)EntityId in our
							// WWATTRIBUTE table.
							//   - we can mfg a wwattribute per ea. basicrule and
							// put()
							//
							BasicRuleCollection brc = cat
									.getBasicRuleCollection("WWATTRIBUTES",
											gami);
							WorldWideAttribute[] awwa = brc
									.buildWorldWideAttributes(cat, wwp);
							D.ebug(D.EBUG_SPEW,
									"idlToCatDb() WWAttributeArray from BasicRulesCollection size is :"
											+ awwa.length);
							for (int i = 0; i < awwa.length; i++) {
								WorldWideAttribute wwa = awwa[i];
								wwac.put(wwa);
								//wwa.get(cat); // nope, get's are done through
								// basicrule logic.
								wwa.put(cat, false);
							}
							//
							// GAB end basicrules prcessing for this
							// worldwideattribute.
							//

							if (icount == icommitchunk) {
								db.commit();
								icount = 0;
							}

						}

						//
						// ok.. lets see what we get!
						//
						//wwpc.generateXML();
						db.commit();

						//
						// lets clear it out
						//
						//if (smcOuter != null) {
						//    smcOuter.clearAll();
						//}

					}

				}

				//      Let delete the temptables by sessionID
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

		SyncMapCollection smcOuter = null;

		String strEntityType = null;
		String strVE = null;

		GeneralAreaMapGroup gamp = null;
		Enumeration en = null;

		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;

		int icommitchunk = 1000;

		try {

			Class mclass = WorldWideProductCollection.class;
			String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);

			Catalog cat = new Catalog();
			Database db = cat.getCatalogDatabase();
			String strEnterprise = cat.getEnterprise();
			GeneralAreaMap gam = cat.getGam();
			Profile prof = cat.getCatalogProfile();
			String strRoleCode = prof.getRoleCode();
			//Added by Guo Bin, 2007-08-03
			DatabaseExt dbe = new DatabaseExt(db);

			CatalogInterval cati = new CatalogInterval(mclass, cat);
			prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());
			gamp = gam.lookupGeneralArea(strGeneralArea);
			en = gamp.elements();

			if (!en.hasMoreElements()) {
				D.ebug(D.EBUG_WARN, "no gami to find!!!");
			}

			for (int icycle = 0; icycle < 2; icycle++) {
				//
				// lets clear it out
				//
				if (smcOuter != null) {
					smcOuter.clearAll();
				}

				if (icycle == 0) {
					// SEO section
					strEntityType = WorldWideProductCollection.SEO_ENTITYTYPE;
					strVE = WorldWideProductCollection.SEO_VE;
					//smcOuter = new SyncMapCollection(cat, strEntityType,
					// strVE, cati, 9);
				} else {
					// TMF section
					strEntityType = WorldWideProductCollection.TMF_ENTITYTYPE;
					strVE = WorldWideProductCollection.TMF_VE;
					//smcOuter = new SyncMapCollection(cat, strEntityType,
					// strVE, cati, 9);
				}

				//add by houjie&liubing 2007-09-13, get configurable NLSID
				// information for WorldWideProduct,ProdStruct
				int iNLSSave[] = new int[2];
				iNLSSave[0] = CatalogProperties.getSaveNLSID(
						WorldWideProductCollection.class,
						WorldWideProduct.class, strVE);
				iNLSSave[1] = CatalogProperties.getSaveNLSID(
						WorldWideProductCollection.class, ProdStruct.class,
						strVE);
				D.ebug(D.EBUG_SPEW,
						"WorldWideProductCollection.WorldWideProduct." + strVE
								+ "_nlssave=" + iNLSSave[0]);
				D.ebug(D.EBUG_SPEW, "WorldWideProductCollection.ProdStruct."
						+ strVE + "_nlssave=" + iNLSSave[1]);

				//
				// Newest version of chunking
				//

				//
				// We want to load up trsnetter pass 1 here via gbl8184....!!!
				// We might want to abstract this call to SyncMapCoillectio
				// later on....
				//
				int iSessionID = db.getNewSessionID();
				try {
					//add this yangxiaojiang
					if(strVE.equals(SEO_VE)){
						db.callGBL8184W(new ReturnStatus(-1), iSessionID,
								strEnterprise, strEntityType, strVE, strRoleCode,
								cati.getStartDate(), cati.getEndDate(), 9, "", -1,
								1);
					}
					else{
					dbe.callGBL8184(new ReturnStatus(-1), iSessionID,
							strEnterprise, strEntityType, strVE, strRoleCode,
							cati.getStartDate(), cati.getEndDate(), 9, "", -1,
							1, SP_SUFFIX);
					}
				} finally {
					db.commit();
					db.freeStatement();
					db.isPending();
				}

				int iChunk = CatalogProperties.getIDLChunkSize(mclass, strVE);
				try {
					rs = dbe.callGBL9012(new ReturnStatus(-1), strEnterprise,
							strEntityType, iSessionID, iChunk, SP_SUFFIX);
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
					D.ebug(D.EBUG_SPEW, "GBL9012:et" + strEntityType
							+ ":floor:" + iFloor + ":ceiling:" + iCeiling
							+ ":start:" + iStart + ":finish:" + iEnd);
					///add by yangxiaojiang
					smcOuter = new SyncMapCollection(cat, strVE, strEntityType,
							iStart, iEnd, iSessionID, SP_SUFFIX);

					//
					// need to put this here
					//
					en = gamp.elements();

					while (en.hasMoreElements()) {

						Iterator it = null;

						GeneralAreaMapItem gami = (GeneralAreaMapItem) en
								.nextElement();
						//
						// Lets set the NLSItem we need to be working in right
						// now
						//
						Vector vct = prof.getReadLanguages();
						for (int i = 0; i < vct.size(); i++) {
							NLSItem nlsi = (NLSItem) vct.elementAt(i);
							D.ebug(D.EBUG_SPEW,
									"WorldWideProductCollection gami/prof check:nlsi.getNLSID()="
											+ nlsi.getNLSID()
											+ ",gami.getNLSID()="
											+ gami.getNLSID());
							if (nlsi.getNLSID() == gami.getNLSID()) {
								D.ebug(D.EBUG_SPEW,
										"WorldWideProductCollection gami/prof check: setting read language to:"
												+ nlsi.getNLSID());
								prof.setReadLanguage(nlsi);
								break;
							}
						}

						WorldWideProductCollectionId wwpcid = new WorldWideProductCollectionId(
								cati, gami, CollectionId.PDH_SOURCE,
								CollectionId.NET_CHANGES);

						WorldWideProductCollection wwpc = new WorldWideProductCollection(
								wwpcid);
						wwpc.setSmc(smcOuter);

						//
						// Lets go fill this out!
						//
						wwpc.get(cat);

						//
						// ok.. we have stubbed it out. lets make them fat!
						//
						it = wwpc.values().iterator();

						int icount = 0;

						while (it.hasNext()) {

							icount++;

							WorldWideProduct wwp = (WorldWideProduct) it.next();
							WorldWideProductId wwpid = wwp
									.getWorldWideProductId();

							//For each world wide product, stub out its chidren
							D.ebug(D.EBUG_SPEW, "Deactivating structure for "
									+ wwpid.dump(false));
							wwpc.deactivateStructure(wwpid, cat, false);

							//
							// lets take a look at the sync map col
							//
							SyncMapCollection smc = wwp.getSmc();
							D.ebug(D.EBUG_SPEW, smc.toString());

							D.ebug(D.EBUG_SPEW,
									"syncToCatDb() .. getting wwp and its collections... "
											+ wwp.toString());
							//add by houjie&liubing 2007-09-13 : check NLSID
							// for WorldWideProduct
							boolean isWWPLoaded = false;
							if (iNLSSave[0] == 0
									|| iNLSSave[0] == gami.getNLSID()) {
								wwp.get(cat);
								D.ebug(D.EBUG_DETAIL,
										"syncToCatDb() .. updating the wwp to the CatDb... "
												+ wwp.toString());
								wwp.put(cat, false);
								isWWPLoaded = true;
							} else {
								isWWPLoaded = false;
								D.ebug(D.EBUG_SPEW, ">>Skipping WWP save for "
										+ wwp.getId()
										+ ".  Targeting NLSID of "
										+ iNLSSave[0] + " not "
										+ gami.getNLSID());
							}
							//add by houjie&liubing 2007-09-13 : check NLSID
							// for ProdStructCollection
							if (isWWPLoaded
									&& (iNLSSave[1] == gami.getNLSID() || iNLSSave[1] == 0)) {
								wwp.getReferences(cat,
										WorldWideProduct.PRODSTRUCT_REFERENCE);
								//
								// DWB .. maybe we can pull when needed
								//
								//                  ProdStructCollection psc =
								// wwp.getProdStructCollection();
								//                  D.ebug(D.EBUG_SPEW,
								//                         "syncToCatDb() ProdStructCollection size is
								// :" + psc.size());
								//                  Iterator it2 = psc.values().iterator();
								//                  while (it2.hasNext()) {
								//                    ProdStruct ps = (ProdStruct) it2.next();
								//                    ps.get(cat);
								//                    //
								//                    // we would normally compare here
								//                    // before we do a put
								//                    //
								//                    ps.put(cat, false);
								//                  }
							} else {
								D.ebug(D.EBUG_SPEW,
										">>> Skipping ProdStruct Save for "
												+ wwp.getId()
												+ ".  Only Targeting NLSID of "
												+ iNLSSave[1] + "(not "
												+ gami.getNLSID() + ")");
							}
							//add by houjie&liubing 2007-09-13 : do not config
							// NLSID for WorldWideAttribute
							wwp.getReferences(cat,
									WorldWideProduct.ATTRIBUTE_REFERENCE);
							//
							// ok.. lets do some ww attributes
							//
							WorldWideAttributeCollection wwac = wwp
									.getWorldWideAttributeCollection();
							D.ebug(D.EBUG_SPEW,
									"syncToCatDb() WorldWideAttributeCollection size is :"
											+ wwac.size());
							Iterator it2 = wwac.values().iterator();
							while (it2.hasNext()) {
								WorldWideAttribute wwa = (WorldWideAttribute) it2
										.next();
								wwa.get(cat);
								//
								// we would normally compare here
								// before we do a put
								//
								wwa.put(cat, false);
							}

							//
							//
							// GAB: HERE'S WHERE OUR BASICRULE PROCESSING GOES
							// FOR A GIVEN ***WWATTRIBUTE***.
							//   - Assume: rules are per
							// (item)EntityType/(item)EntityId in our
							// WWATTRIBUTE table.
							//   - we can mfg a wwattribute per ea. basicrule and
							// put()
							//
							BasicRuleCollection brc = cat
									.getBasicRuleCollection("WWATTRIBUTES",
											gami);
							WorldWideAttribute[] awwa = brc
									.buildWorldWideAttributes(cat, wwp);
							D.ebug(D.EBUG_SPEW,
									"idlToCatDb() WWAttributeArray from BasicRulesCollection size is :"
											+ awwa.length);
							for (int i = 0; i < awwa.length; i++) {
								WorldWideAttribute wwa = awwa[i];
								wwac.put(wwa);
								//wwa.get(cat); // nope, get's are done through
								// basicrule logic.
								wwa.put(cat, false);
							}
							//
							// GAB end basicrules prcessing for this
							// worldwideattribute.
							//

							if (icount == icommitchunk) {
								db.commit();
								icount = 0;
							}

						}

						//
						// ok.. lets see what we get!
						//
						//wwpc.generateXML();
						db.commit();

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

		} catch (MiddlewareException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * deactivateStructure
	 * 
	 * @param _wwpid
	 *            WorldWideProductId
	 */
	public void deactivateStructure(WorldWideProductId _wwpid, Catalog _cat,
			boolean _bcommit) {

		if (Catalog.isDryRun()) {
			return;
		}

		String strWWProdEType = _wwpid.getEntityType();
		int iWWProdEId = _wwpid.getEntityID();

		Database db = _cat.getCatalogDatabase();
		ReturnStatus rets = new ReturnStatus(-1);
		GeneralAreaMapItem gami = _wwpid.getGami();

		String strEnterprise = gami.getEnterprise();
		String strCountryCode = gami.getCountry();
		String strLanguageCode = gami.getLanguage();
		int iNLSID = gami.getNLSID();
		String strCountryList = gami.getCountryList();

		/*
		 * Call the sp to deactivate all children (key is Model or wwseo) A
		 * wwproduct has Prodstruct and WWAttributes as children A prodstruct is
		 * a relationship betw MODEL and FEATURE. so when a WWPRODUCT (MODEL) is
		 * deactivated, that is not handled in this class, it is handled in the
		 * ProductCollection class
		 */
		try {
			db.callGBL4023(new ReturnStatus(-1), strEnterprise, strWWProdEType,
					iWWProdEId, strCountryCode, strLanguageCode, iNLSID);
			if (_bcommit) {
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

	/**
	 * idlToCatDb This guy will take all known root entities and process them in
	 * one big chunk.. if we run out of of memory, we start employing memory
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
		D.ebug(D.EBUG_SPEW, "WorldWideProductCollection.idlToCatDb");
		//
		// Lets try to clean up the temp tables first
		//
		//    try {
		//        Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(WorldWideProductCollection.class));
		//    }
		//    catch (InterruptedException ex1) {
		//        ex1.printStackTrace();
		//        System.exit( -1);
		//    }
		//    catch (IOException ex1) {
		//        ex1.printStackTrace();
		//        System.exit( -1);
		//    }

		try {

			Class mclass = WorldWideProductCollection.class;
			String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
			Catalog cat = new Catalog();
			String strEnterprise = cat.getEnterprise();
			Database db = cat.getCatalogDatabase();
			Database pdhdb = cat.getPDHDatabase();
			GeneralAreaMap gam = cat.getGam();
			Profile prof = cat.getCatalogProfile();

			CatalogInterval cati = new CatalogInterval(mclass, cat);

			//
			// lets establish the gamp
			//
			gamp = gam.lookupGeneralArea(strGeneralArea);
			en = gamp.elements();
			if (!en.hasMoreElements()) {
				D.ebug(D.EBUG_WARN, "no gami to find!!!");
			}

			//
			// OK lets do this loop for real
			// we will make two passes..
			//

			//
			// lets temperaroly look at WWSEO's only
			//
			for (int icycle = 0; icycle < 2; icycle++) {
				if (icycle == 0) {
					strEntityType = WorldWideProductCollection.SEO_ENTITYTYPE;
					strVE = WorldWideProductCollection.SEO_VE;
				} else {
					strEntityType = WorldWideProductCollection.TMF_ENTITYTYPE;
					strVE = WorldWideProductCollection.TMF_VE;
				}
				//add by houjie&liubing 2007-08-28, get configurable NLSID
				// information for WorldWideProduct,ProdStruct
				int iNLSSave[] = new int[2];
				iNLSSave[0] = CatalogProperties.getSaveNLSID(
						WorldWideProductCollection.class,
						WorldWideProduct.class, strVE);
				iNLSSave[1] = CatalogProperties.getSaveNLSID(
						WorldWideProductCollection.class, ProdStruct.class,
						strVE);
				D.ebug(D.EBUG_SPEW,
						"WorldWideProductCollection.WorldWideProduct." + strVE
								+ "_nlssave=" + iNLSSave[0]);
				D.ebug(D.EBUG_SPEW, "WorldWideProductCollection.ProdStruct."
						+ strVE + "_nlssave=" + iNLSSave[1]);
				//
				// Newest version of chunking
				//
				int iChunk = CatalogProperties.getIDLChunkSize(mclass, strVE);
				try {
					rs = pdhdb.callGBL9010(new ReturnStatus(-1), strEnterprise,
							strEntityType, iChunk);
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
				for (int x = 0; x < rdrs.size(); x++) {

					iFloor = rdrs.getColumnInt(x, 0);
					iCeiling = rdrs.getColumnInt(x, 1);
					iStart = rdrs.getColumnInt(x, 2);
					iEnd = rdrs.getColumnInt(x, 3);
					D.ebug(D.EBUG_SPEW, "GBL9010:et" + strEntityType
							+ ":floor:" + iFloor + ":ceiling:" + iCeiling
							+ ":start:" + iStart + ":finish:" + iEnd);

					smc1 = new SyncMapCollection(cat, strVE, strEntityType,
							iStart, iEnd);

					en = gamp.elements();
					while (en.hasMoreElements()) {

						Iterator it = null;

						GeneralAreaMapItem gami = (GeneralAreaMapItem) en
								.nextElement();

						//
						// Lets set the NLSItem we need to be working in right
						// now
						//
						Vector vct = prof.getReadLanguages();
						for (int i = 0; i < vct.size(); i++) {
							NLSItem nlsi = (NLSItem) vct.elementAt(i);
							D.ebug(D.EBUG_SPEW,
									"WorldWideProductCollection gami/prof check:nlsi.getNLSID()="
											+ nlsi.getNLSID()
											+ ",gami.getNLSID()="
											+ gami.getNLSID());
							if (nlsi.getNLSID() == gami.getNLSID()) {
								D.ebug(D.EBUG_SPEW,
										"WorldWideProductCollection gami/prof check: setting read language to:"
												+ nlsi.getNLSID());
								prof.setReadLanguage(nlsi);
								break;
							}
						}

						WorldWideProductCollectionId wwpcid = new WorldWideProductCollectionId(
								cati, gami, CollectionId.PDH_SOURCE,
								CollectionId.FULL_IMAGES);

						WorldWideProductCollection wwpc = new WorldWideProductCollection(
								wwpcid);
						wwpc.setSmc(smc1);
						//
						// Lets go fill this out!
						//
						wwpc.get(cat);
						//
						// ok.. we have stubbed it out. lets make them fat!
						//
						int icount = 0;

						it = wwpc.values().iterator();
						while (it.hasNext()) {

							icount++;

							WorldWideProduct wwp = (WorldWideProduct) it.next();
							WorldWideAttributeCollection wwac = null;

							//
							// lets take a look at the sync map col
							SyncMapCollection smc = wwp.getSmc();
							D.ebug(D.EBUG_SPEW, smc.toString());

							D.ebug(D.EBUG_SPEW,
									"idlToCatDb() .. getting wwp and its collections ... "
											+ wwp.toString());

							// GAB 092106 - allow a special toggle to load basic
							// rules...
							if (!CatalogRunner.isBasicRuleLoad()) {
								//add by houjie&liubing 2007-08-28 : check
								// NLSID for WorldWideProduct
								boolean isWWPLoaded = false;
								if (iNLSSave[0] == 0
										|| iNLSSave[0] == gami.getNLSID()) {
									wwp.get(cat);
									//
									// Now.. lets
									//
									D.ebug(D.EBUG_SPEW,
											"idlToCatDb() .. updating the wwp to the CatDb... "
													+ wwp.toString());
									wwp.put(cat, false);
									isWWPLoaded = true;
								} else {
									isWWPLoaded = false;
									D.ebug(D.EBUG_SPEW,
											">>Skipping WWP save for "
													+ wwp.getId()
													+ ".  Targeting NLSID of "
													+ iNLSSave[0] + " not "
													+ gami.getNLSID());
								}
								//add by houjie&liubing 2007-08-28 : check
								// NLSID for ProdStructCollection
								if (isWWPLoaded
										&& (iNLSSave[1] == gami.getNLSID() || iNLSSave[1] == 0)) {
									wwp
											.getReferences(
													cat,
													WorldWideProduct.PRODSTRUCT_REFERENCE);
									//
									// DWB .. maybe we can pull when needed
									//
									ProdStructCollection psc = wwp
											.getProdStructCollection();
									D.ebug(D.EBUG_SPEW,
											"idlToCatDb() ProdStructCollection size is :"
													+ psc.size());
									Iterator it2 = psc.values().iterator();
									//
									// !!DWB
									// Here is where we get to blow this stuff
									// away first
									// to make way for the new stuff
									//
									while (it2.hasNext()) {
										ProdStruct ps = (ProdStruct) it2.next();
										ps.get(cat);
										//
										// we would normally compare here
										// before we do a put
										//
										ps.put(cat, false);
									}
								} else {
									D
											.ebug(
													D.EBUG_SPEW,
													">>> Skipping ProdStruct Save for "
															+ wwp.getId()
															+ ".  Only Targeting NLSID of "
															+ iNLSSave[1]
															+ "(not "
															+ gami.getNLSID()
															+ ")");
								}

								//add by houjie&liubing 2007-08-28 : no need to
								// check NLSID for WorldWideAttributeCollection
								wwp.getReferences(cat,
										WorldWideProduct.ATTRIBUTE_REFERENCE);
								//
								// ok.. lets do some ww attributes
								//
								wwac = wwp.getWorldWideAttributeCollection();
								D.ebug(D.EBUG_SPEW,
										"idlToCatDb() WorldWideAttributeCollection size is :"
												+ wwac.size());
								Iterator it2 = wwac.values().iterator();
								while (it2.hasNext()) {
									WorldWideAttribute wwa = (WorldWideAttribute) it2
											.next();
									wwa.get(cat);
									//
									// we would normally compare here
									// before we do a put
									//
									wwa.put(cat, false);
								}
							} // end "real" IDL-only logic.

							//
							//
							// GAB: HERE'S WHERE OUR BASICRULE PROCESSING GOES
							// FOR A GIVEN ***WWATTRIBUTE***.
							//   - Assume: rules are per
							// (item)EntityType/(item)EntityId in our
							// WWATTRIBUTE table.
							//   - we can mfg a wwattribute per ea. basicrule and
							// put()
							//
							if (wwac == null
									&& !CatalogRunner.isBasicRuleLoad()) {
								wwac = wwp.getWorldWideAttributeCollection();
							}
							if (CatalogRunner.isBasicRuleLoad()) {
								//add by houjie&liubing 2007-08-28 : check
								// NLSID for WorldWideProduct
								boolean isWWPLoaded = false;
								if (iNLSSave[0] == 0
										|| iNLSSave[0] == gami.getNLSID()) {
									wwp.get(cat);
									isWWPLoaded = true;
								} else {
									isWWPLoaded = false;
									D.ebug(D.EBUG_SPEW,
											">>Skipping WWP save for "
													+ wwp.getId()
													+ ".  Targeting NLSID of "
													+ iNLSSave[0] + " not "
													+ gami.getNLSID());
								}
								//add by houjie&liubing 2007-08-28 : check
								// NLSID for ProdStructCollection
								if (isWWPLoaded
										&& (iNLSSave[1] == gami.getNLSID() || iNLSSave[1] == 0)) {
									wwp
											.getReferences(
													cat,
													WorldWideProduct.PRODSTRUCT_REFERENCE);
									ProdStructCollection psc = wwp
											.getProdStructCollection();
									D.ebug(D.EBUG_SPEW,
											"idlToCatDb() ProdStructCollection size is :"
													+ psc.size());
									Iterator it2 = psc.values().iterator();
									while (it2.hasNext()) {
										ProdStruct ps = (ProdStruct) it2.next();
										ps.get(cat);
									}
								} else {
									D
											.ebug(
													D.EBUG_SPEW,
													">>> Skipping ProdStruct Save for "
															+ wwp.getId()
															+ ".  Only Targeting NLSID of "
															+ iNLSSave[1]
															+ "(not "
															+ gami.getNLSID()
															+ ")");
								}

								//add by houjie&liubing 2007-08-28 : no need to
								// check NLSID for WorldWideAttributeCollection
								wwp.getReferences(cat,
										WorldWideProduct.ATTRIBUTE_REFERENCE);
								//
								// ok.. lets do some ww attributes
								//
								wwac = wwp.getWorldWideAttributeCollection();
								D.ebug(D.EBUG_SPEW,
										"idlToCatDb() WorldWideAttributeCollection size is :"
												+ wwac.size());
								Iterator it2 = wwac.values().iterator();
								while (it2.hasNext()) {
									WorldWideAttribute wwa = (WorldWideAttribute) it2
											.next();
									wwa.get(cat);
								}
							}//end of CatalogRunner.isBasicRuleLoad()
							BasicRuleCollection brc = cat
									.getBasicRuleCollection("WWATTRIBUTES",
											gami);
							WorldWideAttribute[] awwa = brc
									.buildWorldWideAttributes(cat, wwp);
							D.ebug(D.EBUG_SPEW,
									"idlToCatDb() WWAttributeArray from BasicRulesCollection size is :"
											+ awwa.length);
							for (int i = 0; i < awwa.length; i++) {
								WorldWideAttribute wwa = awwa[i];
								wwac.put(wwa);
								//wwa.get(cat); // nope, get's are done through
								// basicrule logic.
								wwa.put(cat, false);
							}
							//
							// GAB end basicrules prcessing for this
							// worldwideattribute.
							//

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
						//wwpc.generateXML();
						db.commit();

					}

					//
					// lets clear it out
					//
					if (smc1 != null) {
						smc1.clearAll();
					}

				}
			}

			//
			// o.k. we are now done
			//
			cati.put(cat);
			db.close();
			Catalog.resetLists();

		} catch (MiddlewareException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * First attempt at an XML writer usage - This sends the output to Standard
	 * out for now.
	 */
	public void generateXML() {

		Iterator it = null;

		XMLWriter xml = new XMLWriter();
		WorldWideProductCollectionId wwpcid = this
				.getWorldWideProductCollectionId();
		CatalogInterval cati = wwpcid.getInterval();
		String strFromTimestamp = (wwpcid.isByInterval() ? cati.getStartDate()
				: EPOCH);
		String strToTimestamp = (wwpcid.isByInterval() ? cati.getEndDate()
				: FOREVER);

		int iNumberOfElements = this.size();

		try {

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
				WorldWideProduct wwp = (WorldWideProduct) it.next();
				wwp.generateXMLFragment(xml);
			}

			xml.endEntity();

			xml.finishEntity();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see COM.ibm.eannounce.catalog.CatSync#hasSyncMapCollection()
	 */
	public final boolean hasSyncMapCollection() {
		return m_smc != null;
	}
}
/**
 * $Log: WorldWideProductCollection.java,v $
 * Revision 1.24  2011/05/05 11:21:33  wendy
 * src from IBMCHINA
 *
 * Revision 1.14  2008/12/03 09:47:20  yang
 * add prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate()) at line:900 GUO
 *
 * Revision 1.13  2008/11/19 01:17:34  yang
 * add by guo bin for gbl8184w
 *
 * Revision 1.23  2008/05/23 07:22:08  yang
 * no message
 *
 * Revision 1.12  2008/01/18 04:22:20  yang
 * recover to previous version
 *
 *  
 * Revision 1.10 2007/10/29 09:32:20
 * jingb Change code in method syncToCatDb(): delete the temp table data by
 * sessionID
 * 
 * Revision 1.9 2007/10/16 05:38:46 jingb *** empty log message ***
 * 
 * Revision 1.8 2007/09/13 08:36:56 jiehou *** empty log message ***
 * 
 * Revision 1.7 2007/09/13 05:54:53 sulin no message
 * 
 * Revision 1.6 2007/09/10 06:45:43 sulin no message
 * 
 * Revision 1.5 2007/09/03 03:11:31 jiehou *** empty log message ***
 * 
 * Revision 1.4 2007/08/31 06:41:56 jiehou *** empty log message ***
 * 
 * Revision 1.3 2007/08/30 06:25:58 jiehou *** empty log message ***
 * 
 * Revision 1.2 2007/08/29 06:00:30 jiehou *** empty log message ***
 * 
 * Revision 1.1.1.1 2007/06/05 02:09:34 jingb no message
 * 
 * Revision 1.22 2007/05/15 18:10:34 rick changed to run IDL logic gets in basic
 * rule load path
 * 
 * Revision 1.21 2006/10/16 18:18:08 gregg childtype = MODEL, level==0.
 * 
 * Revision 1.20 2006/09/21 20:58:44 gregg prepping for BasicRuleLoad
 * 
 * Revision 1.19 2006/09/14 23:04:02 gregg reverse smc loop in processsyncmap to
 * really order by OFF/ON
 * 
 * Revision 1.18 2006/09/14 22:04:13 gregg trying to see if we can "fix"
 * processSyncMap wrt IMG/SEO
 * 
 * Revision 1.17 2006/09/14 21:49:33 gregg debug
 * 
 * Revision 1.16 2006/08/28 20:07:26 dave ok.. minor memory fixes
 * 
 * Revision 1.15 2006/08/24 20:21:37 gregg no smcOuter.clearAll at end of nls
 * loop!
 * 
 * Revision 1.14 2006/08/10 21:33:57 gregg Use new SyncMapCollection constructor
 * for chunky munky
 * 
 * Revision 1.13 2006/08/08 23:30:26 gregg little database switch up fixing
 * 
 * Revision 1.12 2006/08/07 21:50:38 gregg chunky munky for delta
 * 
 * Revision 1.11 2006/07/11 18:14:17 gregg taking back debugs to spew
 * 
 * Revision 1.10 2006/06/29 21:31:38 gregg BasicRule processing in sync.
 * 
 * Revision 1.9 2006/06/27 22:41:31 gregg null ptr fix
 * 
 * Revision 1.8 2006/06/22 22:51:07 gregg one more fix
 * 
 * Revision 1.7 2006/06/22 22:50:31 gregg compile phicks
 * 
 * Revision 1.6 2006/06/22 22:48:21 gregg changing chunk size and propertizing
 * chunk size
 * 
 * Revision 1.5 2006/06/20 02:49:09 dave need to reset enum between cycles
 * 
 * Revision 1.4 2006/06/20 02:42:26 dave memory cleanup
 * 
 * Revision 1.3 2006/06/20 01:12:13 dave test reverse
 * 
 * Revision 1.2 2006/06/19 23:39:39 gregg use icycle in syncToCatDb
 * 
 * Revision 1.1.1.1 2006/03/30 17:36:31 gregg Moving catalog module from
 * middleware to its own module.
 * 
 * Revision 1.101 2006/02/27 22:41:47 dave added LSEOBUNDLE from wwproduct
 * colleciton to handle multi-media
 * 
 * Revision 1.100 2006/02/21 22:41:41 gregg more of said debugs
 * 
 * Revision 1.99 2006/02/21 21:37:06 gregg gami debugs
 * 
 * Revision 1.98 2005/12/15 22:09:47 gregg BasicRule building logic for
 * WorldWideAttributes!!
 * 
 * Revision 1.97 2005/12/01 23:44:45 joan fixes
 * 
 * Revision 1.96 2005/11/30 22:12:47 bala deactivate prodstructcollection
 * structure
 * 
 * Revision 1.95 2005/11/29 19:19:48 dave formatting
 * 
 * Revision 1.94 2005/11/28 16:04:52 bala change syncToCatdb - add debug
 * statements
 * 
 * Revision 1.93 2005/11/28 00:58:50 bala <No Comment Entered>
 * 
 * Revision 1.92 2005/11/18 23:26:57 bala syncToCatDB - reset the children
 * before updates
 * 
 * Revision 1.91 2005/10/26 00:57:14 dave <No Comment Entered>
 * 
 * Revision 1.90 2005/10/25 07:05:49 dave memory cleanup
 * 
 * Revision 1.89 2005/10/25 04:09:24 dave curtailing smc's to spew
 * 
 * Revision 1.88 2005/10/25 02:52:21 dave commenting out some debugs
 * 
 * Revision 1.87 2005/10/14 21:37:22 gregg fix null ptr
 * 
 * Revision 1.86 2005/10/12 12:04:24 dave newest way to chunk
 * 
 * Revision 1.85 2005/10/04 23:24:16 joan work on component
 * 
 * Revision 1.84 2005/10/04 20:35:30 joan fixes
 * 
 * Revision 1.83 2005/10/04 16:28:14 joan fixes
 * 
 * Revision 1.82 2005/10/04 16:22:06 joan add component
 * 
 * Revision 1.81 2005/10/04 16:04:59 joan fixes
 * 
 * Revision 1.80 2005/10/03 22:08:33 joan work on component
 * 
 * Revision 1.79 2005/09/20 03:02:48 dave starting to clean up collateral
 * collection
 * 
 * Revision 1.78 2005/09/13 05:52:24 dave setting full idl run
 * 
 * Revision 1.77 2005/09/13 04:14:31 dave ok.. lets add the PROJCDNAM, and lets
 * take a hard look at WWSEO and how to get prod structures
 * 
 * Revision 1.76 2005/09/13 03:09:32 dave cleanup and more trace
 * 
 * Revision 1.75 2005/09/13 02:53:47 dave putting some headlights on the SMC
 * 
 * Revision 1.74 2005/09/13 02:24:58 dave ok change conflict fixed
 * 
 * Revision 1.73 2005/09/13 01:58:22 dave testing out the wwseo
 * 
 * Revision 1.72 2005/09/12 03:53:45 dave fixing sql parms
 * 
 * Revision 1.71 2005/09/12 03:40:53 dave fix to looping
 * 
 * Revision 1.70 2005/09/12 02:01:49 dave attempting to chunk out idl
 * 
 * Revision 1.69 2005/09/12 01:34:21 dave backing off IDL initial load
 * 
 * Revision 1.68 2005/09/12 00:12:24 dave new sql file to quickly ensure
 * everything is accurate in our catdb setup for data
 * 
 * Revision 1.67 2005/07/07 19:36:58 bala update WorldWideComponent catalog
 * table
 * 
 * Revision 1.66 2005/07/07 18:43:09 bala plug for WorldWideComponents
 * 
 * Revision 1.65 2005/06/23 03:00:43 dave iterator probwem
 * 
 * Revision 1.64 2005/06/23 02:42:42 dave minor changes
 * 
 * Revision 1.63 2005/06/22 21:17:16 gregg change signature for processSyncMap
 * 
 * Revision 1.62 2005/06/17 01:26:56 dave some isPending fixes
 * 
 * Revision 1.61 2005/06/17 01:05:26 dave tying it all together
 * 
 * Revision 1.60 2005/06/16 16:46:05 dave changes for Collateral
 * 
 * Revision 1.59 2005/06/13 04:54:40 dave missed a processSyncMap
 * 
 * Revision 1.58 2005/06/13 04:35:34 dave ! needs to be not !
 * 
 * Revision 1.57 2005/06/13 04:02:06 dave new dryrun feature to keep things from
 * being updated
 * 
 * Revision 1.56 2005/06/11 02:32:54 dave simple changes, getting Net for
 * MarketInfo
 * 
 * Revision 1.55 2005/06/08 23:28:07 dave ok, more collateral
 * 
 * Revision 1.54 2005/06/08 21:28:50 dave Collateral Common
 * 
 * Revision 1.53 2005/06/08 20:31:08 dave more changes to start picking up
 * attributes for collateral
 * 
 * Revision 1.52 2005/06/08 19:40:08 dave need new SP
 * 
 * Revision 1.51 2005/06/08 19:37:15 dave ok.. lets try to start updating sp
 * information
 * 
 * Revision 1.50 2005/06/08 18:05:44 dave CollateralCollection Build all
 * 
 * Revision 1.49 2005/06/08 13:21:21 dave testing waters to see if structures
 * make sense outside of WWProductId
 * 
 * Revision 1.48 2005/06/07 14:38:36 dave commit and close
 * 
 * Revision 1.47 2005/06/07 13:21:24 dave closing the loop and posting time back
 * to the timetable
 * 
 * Revision 1.46 2005/06/07 05:28:42 dave more cleanup and NLSid ing
 * 
 * Revision 1.45 2005/06/07 04:49:47 dave minor fix to syntax
 * 
 * Revision 1.44 2005/06/07 04:44:04 dave secondary fixes to commit chunk
 * 
 * Revision 1.43 2005/06/07 04:34:51 dave working on commit control
 * 
 * Revision 1.42 2005/06/07 03:54:23 dave not callable fix
 * 
 * Revision 1.41 2005/06/07 03:43:28 dave adding the idl piece
 * 
 * Revision 1.40 2005/06/07 03:25:29 dave new IDL sp and approach
 * 
 * Revision 1.39 2005/06/05 16:54:52 dave rolling in the feature concept
 * 
 * Revision 1.38 2005/06/05 00:51:18 dave lets do the put
 * 
 * Revision 1.37 2005/06/04 22:22:57 dave ok.. lets try to dump out the
 * ProdStruct XML for now.. lets embed it in the other XML
 * 
 * Revision 1.36 2005/06/04 16:46:05 dave more trace
 * 
 * Revision 1.35 2005/06/03 21:25:51 dave lets start getting prodstruct data
 * 
 * Revision 1.34 2005/06/02 08:16:19 dave ok.. lets give it a whirl
 * 
 * Revision 1.33 2005/06/02 04:49:55 dave more clean up
 * 
 * Revision 1.32 2005/06/01 06:31:17 dave ok.. lets see if we can do some
 * ProdStruct Damage
 * 
 * Revision 1.31 2005/06/01 02:20:11 dave ok.. more cleanup and ve work
 * 
 * Revision 1.30 2005/05/29 00:25:30 dave ok.. clean up and reorg to better
 * support pulls from PDH
 * 
 * Revision 1.29 2005/05/28 23:25:59 dave ok.. can we spit out xml?
 * 
 * Revision 1.28 2005/05/27 22:52:33 dave lets see if we can get something from
 * the PDH
 * 
 * Revision 1.27 2005/05/27 22:19:41 dave need to save off list
 * 
 * Revision 1.26 2005/05/27 22:06:51 dave trying for a single level dump
 * 
 * Revision 1.25 2005/05/27 21:46:59 dave trying to split it up
 * 
 * Revision 1.24 2005/05/27 05:13:36 dave ok.. expanding profile information
 * 
 * Revision 1.23 2005/05/27 05:09:08 dave adding profile stuff to main test
 * 
 * Revision 1.22 2005/05/27 04:58:57 dave fixing CollectionId
 *  
 */
