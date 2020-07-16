/*
 * Copyright (c) 2005, International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * Created on August 9, 2005
 */
package COM.ibm.eannounce.catalog;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import java.rmi.RemoteException;

import COM.ibm.opicmpdh.transactions.OPICMBlobValue;

import java.sql.SQLException;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * this will help in creating a pdhviewable
 * object from the catalog
 * @author Tony
 */
public class CatalogViewer {
	private Catalog cat = null;
	private Class callingClass = null;

	private String sEnterprise = null;
	private String sArea = null;
	private String sEType = null;
	private int iEID = 0;
	private Profile prof = null;
	/**
	 * available catalog item for viewing
	 */
	public static final String COLLATERAL = "COLLATERAL";
	/**
	 * available catalog item for viewing
	 */
	public static final String COMPONENT = "COMPONENT";
	/**
	 * available catalog item for viewing
	 */
	public static final String COMPONENT_GROUP = "CG";
	/**
	 * available catalog item for viewing
	 */
	public static final String FEATURE = "FEATURE";
	/**
	 * available catalog item for viewing
	 */
	public static final String FEATURE_DETAIL = "FEATUREDETAIL";
	/**
	 * available catalog item for viewing
	 */
	public static final String PROD_STRUCT = "PRODSTRUCT";
	/**
	 * available catalog item for viewing
	 */
	public static final String PRODUCT = "PROD";
	/**
	 * available catalog item for viewing
	 */
	public static final String WORLD_WIDE_PRODUCT = "WWPROD";

	public CatalogViewer(Profile _prof, String _sArea, String _sEType, int _iEID) {
		prof = _prof;
		sEnterprise = _prof.getEnterprise();
		sArea = _sArea;
		sEType = _sEType;
		iEID = _iEID;
		return;
	}

	/**
	 * get the catalog
	 * @return Catalog
	 * @author tony
	 */
	public Catalog getCatalog() {
		if (cat == null) {
			try {
				cat = new Catalog(sEnterprise);
			} catch (SQLException _sql) {
				System.out.println("SQLException on Catalog create");
			} catch (MiddlewareException _me) {
				System.out.println("MiddlewareException on Catalog create");
			}
		}
		return cat;
	}

	/**
	 * get the catalog database
	 * @return database
	 * @author tony
	 */
	public Database getCatalogDatabase() {
		Catalog catlog = getCatalog();
		if (catlog != null) {
			return catlog.getCatalogDatabase();
		}
		return null;
	}

	/**
	 * get the pdh database
	 * @return database
	 * @author tony
	 */
	public Database getPDHDatabase() {
		Catalog catlog = getCatalog();
		if (catlog != null) {
			return catlog.getPDHDatabase();
		}
		return null;
	}

	/**
	 * get the profile from the catalog
	 * @return profile
	 * @author tony
	 */
	public Profile getCatalogProfile() {
		Catalog catlog = getCatalog();
		if (catlog != null) {
			return catlog.getCatalogProfile();
		}
		return null;
	}

	/**
	 * get the profile
	 * @return profile
	 * @author tony
	 */
	public Profile getProfile() {
		return prof;
	}

	/**
	 * get the general area map from the catalog
	 * @return general area map
	 * @author tony
	 */
	public GeneralAreaMap getGeneralAreaMap() {
		Catalog catlog = getCatalog();
		if (catlog != null) {
			return catlog.getGam();
		}
		return null;
	}

	/**
	 * get the general area map group from the general area map
	 * @return general area map group
	 * @author tony
	 */
	public GeneralAreaMapGroup getGeneralAreaMapGroup() {
		GeneralAreaMap gMap = getGeneralAreaMap();
		if (gMap != null) {
			return gMap.lookupGeneralArea(sArea);
		}
		return null;
	}

	/**
	 * get the catalog interval
	 * @return catalog interval
	 * @author tony
	 */
	public CatalogInterval getCatalogInterval() {
		Catalog catlog = getCatalog();
		if (catlog != null) {
			return new CatalogInterval(getCatalogClass(),catlog);
		}
		return null;
	}

	/**
	 * get the catalog class
	 * @return class of catalog
	 * @author tony
	 */
	public Class getCatalogClass() {
		if (callingClass != null) {
			return callingClass;
		}
		return getClass();
	}

	/**
	 * set the catalog calling class
	 * @param the calling object
	 * @author tony
	 */
	public void setCatalogClass(Object _o) {
		if (_o != null) {
			callingClass = _o.getClass();
		} else {
			callingClass = null;
		}
		return;
	}

	/**
	 * get an entity item from the catalog
	 * @return EntityItem
	 * @author Tony
	 */
	public EntityItem getEntityItem() {
		Catalog catlog = getCatalog();
		if (catlog != null) {
			return catlog.getEntityItem(cat,sEType,iEID);
		}
		return null;
	}

	/**
	 * this needs to be adjusted as new catagory items come to light
	 * @return catItem
	 */
	public CatItem[] getCatItems(String _type, int _source, int _extract) {
		Vector out = new Vector();
		Catalog catlog = getCatalog();
		if (catlog != null) {
			CatalogInterval ci = new CatalogInterval(getCatalogClass(), catlog);
			GeneralAreaMap gam = catlog.getGam();
			GeneralAreaMapGroup gamp = gam.lookupGeneralArea(sArea);
	        Enumeration en = gamp.elements();
            while (en.hasMoreElements()) {
				GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
				System.out.println("have gami: " + gami);
				if (_type.equals(FEATURE)) {
					WorldWideProductCollectionId wwpcid = new WorldWideProductCollectionId(ci, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);
					System.out.println("wwpcid: " + wwpcid);
					WorldWideProductId wwpid = new WorldWideProductId(sEType, iEID, gami, wwpcid);
					System.out.println("wwpid: " + wwpid);
					WorldWideProduct wwp = new WorldWideProduct(wwpid, cat);


					//retrieve what the entities are that you map to


					System.out.println("wwp: " + wwp);
					wwp.getReferences(cat, WorldWideProduct.PRODSTRUCT_REFERENCE);
					ProductCollectionId pcid = new ProductCollectionId(wwpid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ProductCollectionId.BY_WWENTITYTYPE_WWENTITYID);
					System.out.println("pcid: " + pcid);
					ProductId pid = new ProductId(sEType, iEID, gami, pcid);
					System.out.println("pid: " + pid);
					ProdStructCollectionId pscid = new ProdStructCollectionId(wwpid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ci);
					System.out.println("pscid: " + pscid);
					// GAB 9/13/05 - Careful!!!  Note we are passing no valid ProdStruct EType+ID.
					// Currently FeatureCollection does NOT need these, so we are safe.
					// But please keep in mind if somewhere we are looking for these from CatalogViewer-built FeatureCollections,
					// they WILL NOT BE THERE!
					ProdStructId psid = new ProdStructId(pid, "N/A", -1, pid.getEntityType(), pid.getEntityId(), gami, pscid);
					System.out.println("psid: " + psid);
					FeatureCollectionId fcid = new FeatureCollectionId(psid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ci);
					System.out.println("fcid: " + fcid);
					FeatureCollection fc = new FeatureCollection(cat, fcid);


					//retrieve what the entities are that you map to


					System.out.println("fc: " + fc);
					fc.getReferences(cat,1);
					Iterator it = fc.values().iterator();
					while (it.hasNext()) {
						Feature feat = (Feature)it.next();


						//retrieve what the entities are that you map to


						out.add(feat);
					}
				} else if (_type.equals(COMPONENT)) {
//					ComponentCollection cc = new ComponentCollection(Database _db, Profile _prof, int _iControl, String _strStartDate, String _strEndDate)
					System.out.println("Component currently not supported");
				} else if (_type.equals(COMPONENT_GROUP)) {
					ComponentGroupCollectionId cgcid = new ComponentGroupCollectionId(ci,gami, _source, _extract);
					System.out.println("cgcid: " + cgcid);
					ComponentGroupCollection cgc = new ComponentGroupCollection(cgcid);


					//retrieve what the entities are that you map to


					System.out.println("cgc: " + cgc);
					Iterator it = cgc.values().iterator();
					while (it != null && it.hasNext()) {
						ComponentGroup cg = (ComponentGroup)it.next();


						//retrieve what the entities are that you map to


						out.add(cg);
					}
				} else if (_type.equals(COLLATERAL)) {
					System.out.println("processing collateral");
					CollateralCollectionId ccid = new CollateralCollectionId(ci, gami, _source, _extract);
					System.out.println("ccid: " + ccid);
					if (ccid != null) {
						CollateralCollection cc = new CollateralCollection(ccid);


						//retrieve what the entities are that you map to


						System.out.println("cc: " + cc);
						if (cc != null) {
							Iterator it = cc.values().iterator();
							while (it != null && it.hasNext()) {
								Collateral col = (Collateral)it.next();


								//retrieve what the entities are that you map to


								out.add(col);
							}
						}
					}
				} else if (_type.equals(FEATURE_DETAIL)) {
					WorldWideProductCollectionId wwpcid = new WorldWideProductCollectionId(ci, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);
					System.out.println("wwpcid: " + wwpcid);
					WorldWideProductId wwpid = new WorldWideProductId(sEType, iEID, gami, wwpcid);
					System.out.println("wwpid: " + wwpid);
					WorldWideProduct wwp = new WorldWideProduct(wwpid, cat);


					//retrieve what the entities are that you map to


					System.out.println("wwp: " + wwp);
					wwp.getReferences(cat, WorldWideProduct.PRODSTRUCT_REFERENCE);
					ProductCollectionId pcid = new ProductCollectionId(wwpid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ProductCollectionId.BY_WWENTITYTYPE_WWENTITYID);
					System.out.println("pcid: " + pcid);
					ProductId pid = new ProductId(sEType, iEID, gami, pcid);


					//retrieve what the entities are that you map to


					System.out.println("pid: " + pid);
					ProdStructCollectionId pscid = new ProdStructCollectionId(wwpid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ci);
					System.out.println("pscid: " + pscid);
					// GAB 9/13/05 - Careful !!! Please see similar disclaimer above..
					ProdStructId psid = new ProdStructId(pid, "N/A", -1, pid.getEntityType(), pid.getEntityId(), gami, pscid);
					System.out.println("psid: " + psid);
					FeatureCollectionId fcid = new FeatureCollectionId(psid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ci);
					System.out.println("fcid: " + fcid);
					FeatureCollection fc = new FeatureCollection(cat, fcid);


					//retrieve what the entities are that you map to


//fc: MODEL:288:MODEL:288:SGV30B:WW:us:en:1:1652:SGV30B:WW:us:en:1:1652:CATI:class COM.ibm.eannounce.catalog.CatalogViewer:1980-01-01-00.00.00.000000:2005-08-15-15.37.09.173953:
					System.out.println("fc: " + fc);
					fc.getReferences(cat,1);
					Iterator it = fc.values().iterator();
					if (it.hasNext()) {
						System.out.println("    collection has data");
					} else {
						System.out.println("    collection is empty");
					}
					while (it.hasNext()) {
						Feature feat = (Feature) it.next();


						//retrieve what the entities are that you map to


						feat.get(cat);
						out.add(feat);
						FeatureDetailCollectionId fdcid = new FeatureDetailCollectionId(feat.getFeatureId(), _source, _extract, ci);
						System.out.println("fcid: " + fcid);
						FeatureDetailCollection fdc = new FeatureDetailCollection(fdcid);


						//retrieve what the entities are that you map to


						System.out.println("fdc: " + fdc);
						Iterator it2 = fdc.values().iterator();
						if (it2.hasNext()) {
							System.out.println("    collection2 has data");
						} else {
							System.out.println("    collection2 is empty");
						}
						while (it2 != null && it2.hasNext()) {
							FeatureDetail featDet = (FeatureDetail)it.next();


							//retrieve what the entities are that you map to


							featDet.get(cat);
							out.add(featDet);
						}
					}
				} else if (_type.equals(PROD_STRUCT)) {
					WorldWideProductCollectionId wwpcid = new WorldWideProductCollectionId(ci, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);
					System.out.println("wwpcid: " + wwpcid);
					WorldWideProductId wwpid = new WorldWideProductId(sEType, iEID, gami, wwpcid);
					System.out.println("wwpid: " + wwpid);
					WorldWideProduct wwp = new WorldWideProduct(wwpid, cat);


					//retrieve what the entities are that you map to


					System.out.println("wwp: " + wwp);
					wwp.getReferences(cat, WorldWideProduct.PRODSTRUCT_REFERENCE);
					ProductCollectionId pcid = new ProductCollectionId(wwpid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ProductCollectionId.BY_WWENTITYTYPE_WWENTITYID);
					System.out.println("pcid: " + pcid);
					ProductId pid = new ProductId(sEType, iEID, gami, pcid);


					//retrieve what the entities are that you map to


					System.out.println("pid: " + pid);
					ProdStructCollectionId pscid = new ProdStructCollectionId(wwpid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ci);
					System.out.println("pscid: " + pscid);
					ProdStructCollection psc = new ProdStructCollection(pscid);
					Iterator it = psc.values().iterator();
					if (it.hasNext()) {
						System.out.println("    collection has data");
					} else {
						System.out.println("    collection is empty");
					}
					while (it.hasNext()) {
						ProdStruct prodS = (ProdStruct)it.next();


						//retrieve what the entities are that you map to


						out.add(prodS);
					}
				} else if (_type.equals(PRODUCT)) {
					WorldWideProductCollectionId wwpcid = new WorldWideProductCollectionId(ci, gami, CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);
					System.out.println("wwpcid: " + wwpcid);
					WorldWideProductId wwpid = new WorldWideProductId(sEType, iEID, gami, wwpcid);
					System.out.println("wwpid: " + wwpid);
					WorldWideProduct wwp = new WorldWideProduct(wwpid, cat);


					//retrieve what the entities are that you map to


					System.out.println("wwp: " + wwp);
					wwp.getReferences(cat, WorldWideProduct.PRODSTRUCT_REFERENCE);
					ProductCollectionId pcid = new ProductCollectionId(wwpid, CollectionId.CAT_SOURCE, CollectionId.FULL_IMAGES, ProductCollectionId.BY_WWENTITYTYPE_WWENTITYID);
					ProductCollection pc = new ProductCollection(pcid);


					//retrieve what the entities are that you map to


					Iterator it = pc.values().iterator();
					if (it.hasNext()) {
						System.out.println("    collection has data");
					} else {
						System.out.println("    collection is empty");
					}
					while (it.hasNext()) {
						Product prod = (Product)it.next();


						//retrieve what the entities are that you map to


						out.add(prod);
					}
				} else if (_type.equals(WORLD_WIDE_PRODUCT)) {
					WorldWideProductCollectionId wwpcid = new WorldWideProductCollectionId(ci,gami,_source, _extract);
					System.out.println("wwpcid: " + wwpcid);
					WorldWideProductCollection wwpc = new WorldWideProductCollection(wwpcid);


					//retrieve what the entities are that you map to


					System.out.println("wwpc: " + wwpc);
					Iterator it = wwpc.values().iterator();
					if (it.hasNext()) {
						System.out.println("    collection has data");
					} else {
						System.out.println("    collection is empty");
					}
					while (it.hasNext()) {
						WorldWideProduct wwp = (WorldWideProduct)it.next();


						//retrieve what the entities are that you map to


						out.add(wwp);
					}
				} else {
					System.out.println(_type + " is not defined in the catalog viewer catalog");
				}
			}
			System.out.println("I have no more enumeration");
			if (!out.isEmpty()) {
				System.out.println("RETURNING DATA");
				return (CatItem[]) out.toArray(new CatItem[out.size()]);
			} else {
				System.out.println("no data found in catalog");
			}
		}
		if (!out.isEmpty()) {
			return (CatItem[])out.toArray(new CatItem[out.size()]);
		}
		return null;
	}

	/**
	 * get the entity list based on catalog data
	 * use the PDH_SOURCE and FULL_IMAGES
	 * @param _type
	 * @return entitylist
	 * @author tony
	 */
	public EntityList getEntityList(Profile _prof, EANActionItem _ean, EntityItem[] _ei,String _type) {
		return getEntityList(_prof,_ean,_ei,_type,CollectionId.PDH_SOURCE,CollectionId.FULL_IMAGES);
	}

	public EntityList getEntityList(Database _db, Profile _prof, EntityItem _ei) {
		return null;
	}

	/**
	 * get the entity list based on catalog data
	 * @param _type
	 * @param _datasource CollectionId.PDH_SOURCE
	 * @param _extract type CollectionId.FULL_IMAGES
	 * @return entitylist
	 * @author tony
	 */
	public EntityList getEntityList(Profile _prof, EANActionItem _ean, EntityItem[] _ei, String _type, int _source, int _extract) {
		System.out.println("    CatView processing on...");
		System.out.println("        entity:  " + sEType);
		System.out.println("        entityID: " + iEID);
		System.out.println("        enterprise: " + sEnterprise);
		System.out.println("        area: " + sArea);
		if (_type == null) {
			System.out.println("cv.type is null");
			return null;
		}
		EntityList el = null;
		EntityGroup eg = null;
		CatItem[] raCat = null;
		StringTokenizer st = new StringTokenizer(_type,":");
		try {
			System.out.println("creating Entitylist");
			/*
			 we really only need a meta return here....
			 the data is pretty much irrelevent
			 */
			el = EntityList.createEntityList(getPDHDatabase(),_prof,_ean,_ei);
			System.out.println("created Entitylist");
		} catch (SQLException _sql) {
			System.out.println("SQLException on EntityList create");
			return null;
		} catch (MiddlewareShutdownInProgressException _shut) {
			System.out.println("MiddlewareShutdownInProgressException on EntityList create");
			return null;
		} catch (MiddlewareException _me) {
			System.out.println("MiddlewareException on EntityList create");
			return null;
		} catch (RemoteException _re) {
			System.out.println("RemoteException on EntityList create");
			return null;
		}
		if (el != null) {
			eg = el.getEntityGroup(sEType);
			//if there are no tokens run it once
			if (!st.hasMoreTokens()) {
				raCat = getCatItems(_type,_source,_extract);
				if (raCat != null) {
					int ii = raCat.length;
					for (int i=0;i<ii;++i) {
						EntityItem ei = getEntityItem(eg,raCat[i]);
						if (ei != null) {
							eg.putEntityItem(ei);
						}
					}
				}
			} else {
				String strToken = null;
				while (st.hasMoreTokens()) {
					strToken = st.nextToken();
					raCat = getCatItems(strToken,_source,_extract);
					if (raCat != null) {
						int ii = raCat.length;
						for (int i=0;i<ii;++i) {
							EntityItem ei = getEntityItem(eg,raCat[i]);
							if (ei != null) {
								eg.putEntityItem(ei);
							}
						}
					}
				}
			}
		}
		return el;
	}

	private int getNLS() {
		return 0;
	}

	/**
	 * retrieve an entityItem from the catalog
	 * @param catItem
	 * @author Tony
	 */
	public EntityItem getEntityItem(EntityGroup _eg, CatItem _item) {
		EntityItem out = _eg.getEntityItem(sEType,iEID);
		if (out == null) {
			try {
				System.out.println("generating new EntityItem");
				out = new EntityItem(null, prof, sEType, iEID);
				System.out.println("    generation successful");
			} catch (MiddlewareRequestException _mre) {
				System.out.println("MiddlewareRequestException on EntityItem create");
			}
		}
		if (_eg != null && _item != null) {
int xx = _eg.getMetaAttributeCount();
for (int x=0;x<xx;++x) {
System.out.println("att " + x + " of " + xx + " is: " + _eg.getMetaAttribute(x).getKey());
}
			String[] attCode = _item.getAttributeKeys();
			TextAttribute ta = null;
			SingleFlagAttribute sfa = null;
			StatusAttribute sa = null;
			MultiFlagAttribute mfa = null;
			TaskAttribute tska = null;
			LongTextAttribute lta = null;
			XMLAttribute xa = null;
			BlobAttribute ba = null;
			Object oAtt = null;
			if (attCode != null) {
				int ii = attCode.length;
				for (int i=0;i<ii;++i) {
					EANMetaAttribute ma = _eg.getMetaAttribute(attCode[i]);
System.out.println("    catView processing AttCode: " + attCode[i]);
					Object oValue = _item.getAttribute(attCode[i]);
					String sValue = oValue.toString();
System.out.println("        catView found value of: " + sValue);
					if (ma == null) {
						System.out.println("meta Attribute is null for: " + attCode[i]);
					}
					if (out != null && ma != null) {
System.out.println("    HANG ON HERE WE GO");
						EANAttribute att = out.getAttribute(attCode[i]);
						if (ma instanceof MetaTextAttribute) {
							if (att == null) {
								try {
									ta = new TextAttribute(out, null, (MetaTextAttribute) ma);
									out.putAttribute(ta);
								} catch (MiddlewareRequestException _mre) {
									continue;
								}
							} else {
								ta = (TextAttribute)att;
							}
							ta.putPDHData(getNLS(), sValue);
						} else if (ma instanceof MetaSingleFlagAttribute) {
							if (att == null) {
								try {
									sfa = new SingleFlagAttribute(out, null, (MetaSingleFlagAttribute) ma);
									out.putAttribute(sfa);
								} catch (MiddlewareRequestException _mre) {
									continue;
								}
							} else {
								sfa = (SingleFlagAttribute)att;
							}
							sfa.putPDHFlag(sValue);
						} else if (ma instanceof MetaMultiFlagAttribute) {
							if (att == null) {
								try {
									mfa = new MultiFlagAttribute(out, null, (MetaMultiFlagAttribute) ma);
									out.putAttribute(mfa);
								} catch (MiddlewareRequestException _mre) {
									continue;
								}
							} else {
								mfa = (MultiFlagAttribute)att;
							}
							mfa.putPDHFlag(sValue);
						} else if (ma instanceof MetaStatusAttribute) {
							if (att == null) {
								try {
									sa = new StatusAttribute(out, null, (MetaStatusAttribute) ma);
									out.putAttribute(sa);
								} catch (MiddlewareRequestException _mre) {
									continue;
								}
							} else {
								sa = (StatusAttribute)att;
							}
							sa.putPDHFlag(sValue);
						} else if (ma instanceof MetaTaskAttribute) {
							if (att == null) {
								try {
									tska = new TaskAttribute(out, null, (MetaTaskAttribute) ma);
									out.putAttribute(tska);
								} catch (MiddlewareRequestException _mre) {
									continue;
								}
							} else {
								tska = (TaskAttribute)att;
							}
							sfa.putPDHFlag(sValue);
						} else if (ma instanceof MetaLongTextAttribute) {
							if (att == null) {
								try {
									lta = new LongTextAttribute(out, null, (MetaLongTextAttribute) ma);
									out.putAttribute(lta);
								} catch (MiddlewareRequestException _mre) {
									continue;
								}
							} else {
								lta = (LongTextAttribute)att;
							}
							lta.putPDHData(getNLS(), sValue);
						} else if (ma instanceof MetaXMLAttribute) {
							if (att == null) {
								try {
									xa = new XMLAttribute(out, getProfile(), (MetaXMLAttribute) ma);
									out.putAttribute(xa);
								} catch (MiddlewareRequestException _mre) {
									continue;
								}
							} else {
								xa = (XMLAttribute)att;
							}
							xa.putPDHData(getNLS(), sValue);
						} else if (ma instanceof MetaBlobAttribute) {
							if (att == null) {
								try {
									ba = new BlobAttribute(out, null, (MetaBlobAttribute) ma);
									out.putAttribute(ba);
								} catch (MiddlewareRequestException _mre) {
									continue;
								}
							} else {
								ba = (BlobAttribute)att;
							}
							ba.putPDHData(getNLS(), new OPICMBlobValue(getNLS(), sValue, null));
						}
					}
				}
			}
		}
		return out;
	}
}
/**
 * $Log: CatalogViewer.java,v $
 * Revision 1.2  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:06  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.15  2005/09/13 17:06:02  gregg
 * passing StructEntityType/StructEntityId to ProdStructId Constructor (Product flavor)
 *
 * Revision 1.14  2005/08/18 16:16:09  tony
 * *** empty log message ***
 *
 * Revision 1.13  2005/08/16 16:52:58  tony
 * CatalogViewer
 *
 * Revision 1.12  2005/08/12 15:25:48  tony
 * *** empty log message ***
 *
 * Revision 1.11  2005/08/11 22:32:27  tony
 * updated display statements
 *
 * Revision 1.10  2005/08/11 18:06:03  tony
 * import statement adjusted
 *
 * Revision 1.9  2005/08/11 18:04:10  tony
 * fixed brace
 *
 * Revision 1.8  2005/08/11 17:59:30  tony
 * improved exception handing
 *
 * Revision 1.7  2005/08/11 17:52:22  tony
 * improved EntityList creation.
 *
 * Revision 1.6  2005/08/11 17:30:13  tony
 * fixed compile issues
 *
 * Revision 1.5  2005/08/11 17:21:27  tony
 * added EntityItem array to catalog function
 *
 * Revision 1.4  2005/08/10 18:06:18  tony
 * updated logic
 *
 * Revision 1.3  2005/08/10 17:33:48  tony
 * updated literal values
 *
 * Revision 1.2  2005/08/10 16:14:23  tony
 * improved catalog viewer functionality.
 *
 * Revision 1.1  2005/08/09 20:10:27  tony
 * Added Catalog Viewer
 *
 */
