/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.middleware.Stopwatch;

/**
 * World Wide Product
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class WWProdCompat extends CatItem {


    //
    // Here is the PDH Attribute Tokens to avoid hard coding
    //
    public static final String MODEL_MACHTYPEATR = "MACHTYPEATR";
    public static final String MODEL_MODELATR = "MODELATR";
    public static final String WWSEO_SEOID = "SEOID";
    public static final String ATT_RELTYPE = "RELTYPE";
    public static final String ATT_PUBFROM = "PUBFROM";
    public static final String ATT_PUBTO = "PUBTO";
    public static final String ATT_OS = "OS";

    public static final String TMF_ENTITY_GROUP = "MODEL";
    public static final String SEO_ENTITY_GROUP = "WWSEO";
    public static final String BDL_ENTITY_GROUP = "LSEOBUNDLE";
    public static final String MDLCGOSMDL_ENTITY_GROUP = "MDLCGOSMDL";
    public static final String SEOCGOSSEO_ENTITY_GROUP = "SEOCGOSSEO";
    public static final String MDLCGOSWWSEO_ENTITY_GROUP = "MDLCGOSWWSEO";
    public static final String MODELCGOS_ENTITY_GROUP = "MODELCGOS";
    public static final String SEOCGOS_ENTITY_GROUP = "SEOCGOS";

    private SyncMapCollection m_smc = null;

    private String ENTERPRISE = null;
    private String COUNTRYCODE = null;
    private String LANGUAGECODE = null;
    private int NLSID = 0;
    private String COUNTRYLIST = null;

    private String COMPATTYPE = null;
    private String OS = null;
    private String OS_FC = null;

    private String WWPARTNUMBERFROM = null;
    private String WWPARTNUMBERTO = null;

    private String PUBFROM = null;
    private String PUBTO = null;
    private String VALFROM = null;
    private String VALTO = null;

    private boolean ISACTIVE = true;

    private HashMap m_AttCollection = new HashMap();


    /**
     * WWProdCompat lets get the Light
     * @param _cid
     */
    public WWProdCompat(WWProdCompatId _cid) {
        super(_cid);
    }

    /**
     * WWProdCompat - lets get the heavy
     * Version
     *
     * @param _cid
     * @param _cat
     */
    public WWProdCompat(WWProdCompatId _cid, Catalog _cat) {
        super(_cid);
        get(_cat);

    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {

        //
        // ok.. here is where we  test it all out
        //

        try {

            String strEnterprise = args[0];
            String strEntityType = args[1];
            int iEntityID = Integer.parseInt(args[2]);
            String strGeneralArea = args[3];

            Catalog cat = new Catalog(strEnterprise);

            GeneralAreaMap gam = cat.getGam();
            GeneralAreaMapGroup gamp = gam.lookupGeneralArea(strGeneralArea);

            Enumeration en = gamp.elements();

            if (!en.hasMoreElements()) {
       //         D.ebug(D.EBUG_WARN,"no gami to find!!!");
            }
            while (en.hasMoreElements()) {
                GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();

            }

            cat.close();

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
     * @param _cat
     */
    public void get(Catalog _cat) {
        String strTraceBase = "WWProdCompat get method ";

    //    D.ebug(D.EBUG_SPEW,strTraceBase + " yo!");

        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        Profile prof = _cat.getCatalogProfile();
        WWProdCompatId wwpcid = getWWProdCompatId();
        GeneralAreaMapItem gami = wwpcid.getGami();
        WWProdCompatCollectionId wwpccid = wwpcid.getWWProdCompatCollectionId();
        String strEnterprise = gami.getEnterprise();

        String strProdEntityTypeFrom = wwpcid.getProdEntityTypeFrom();
        String strProdEntityTypeTo = wwpcid.getProdEntityTypeTo();
        int iProdEntityIDFrom = wwpcid.getProdEntityIDFrom();
        int iProdEntityIDTo = wwpcid.getProdEntityIDTo();

        int iNLSID = gami.getNLSID();
        CatalogInterval cati = wwpccid.getInterval();
        String strCountryList = gami.getCountryList();

        // O.K.. lets see what we got!

        if (wwpccid.isByInterval() && wwpccid.isFromPDH()) {

            if (this.getSmc() == null) {
         //       D.ebug(D.EBUG_SPEW,"Cannot pull out of the PDH since there is no SycnMap for me.");
                return;
            }

            prof.setEffOn(cati.getEndDate());
            prof.setValOn(cati.getEndDate());

            try {

                EntityItem eiFrom = Catalog.getEntityItem(_cat, wwpcid.getProdEntityTypeFrom(), wwpcid.getProdEntityIDFrom());
                //setActive(eiFrom.isActive());
                EntityItem eiTo = Catalog.getEntityItem(_cat, wwpcid.getProdEntityTypeTo(), wwpcid.getProdEntityIDTo());
                //setActive(eiTo.isActive());

                //db.debug(D.EBUG_SPEW, "eiFrom:" + eiFrom.getKey() + ", eiTo:" + eiTo.getKey());

                this.setAttributes(eiFrom, true);
                this.setAttributes(eiTo, false);

                //
                // O.K.  here is where I have to go get other things If I have to find kiddies.
                // If we have a sync map collection.. lets get it and search for other things
                //
                if (this.hasSyncMapCollection()) {

               //     db.debug(D.EBUG_SPEW, this.getSmc().toString());

                    for (int i = 0; i < this.getSmc().getCount(); i++) {
                        SyncMapItem smi = getSmc().get(i);

                //        db.debug(D.EBUG_SPEW,"before final check before setAttributes:" + smi.toString());

                        if (smi.getRootType().equals(eiFrom.getEntityType()) && smi.getRootID() == eiFrom.getEntityID()) {

                 //           db.debug(D.EBUG_SPEW, strTraceBase + " call setAttributes " + smi.toString());
                            EntityItem eiC = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
                            this.setAttributes(eiC, true);
                            EntityItem ei1 = Catalog.getEntityItem(_cat, smi.getEntity1Type(), smi.getEntity1ID());
                            this.setAttributes(ei1, true);
                            break;
                        } else {
				//			db.debug(D.EBUG_SPEW, strTraceBase + " call setAttributes SKIPPED " + smi.toString());
						}
                    }
                }

            }
            finally {
                //TODO
            }
            //
            // Now we have to put the attributes in the right spot
            //

        }
        else {
            try {
                rs = db.callGBL8501(new ReturnStatus( -1), strEnterprise, strProdEntityTypeFrom, iProdEntityIDFrom, strProdEntityTypeFrom, iProdEntityIDTo, strCountryList);

                if (rs.next()) {

                    int i = 1;

                    setCOMPATTYPE(rs.getString(i++));
                    setOS(rs.getString(i++));
                    setOS_FC(rs.getString(i++));
                    setWWPRODENTITYTYPEFROM(rs.getString(i++));
                    setWWPRODENTITYIDFROM(rs.getInt(i++));
                    setWWPARTNUMBERFROM(rs.getString(i++));
                    setWWPRODENTITYTYPETO(rs.getString(i++));
                    setWWPRODENTITYIDTO(rs.getInt(i++));
                    setWWPARTNUMBERTO(rs.getString(i++));
                    setPUBFROM(rs.getString(i++));
                    setPUBTO(rs.getString(i++));
                    setVALFROM(rs.getString(i++));
                    setVALTO(rs.getString(i++));
                }

            }
            catch (SQLException _ex) {
                _ex.printStackTrace();

            }
            catch (MiddlewareException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
                try {
                    rs.close();
                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }
                catch (SQLException _ex) {
                    _ex.printStackTrace();
                }
            }
        }

    }

    /**
     *  (non-Javadoc)
     *
     * @param _cat
     * @param _icase
     */
    public void getReferences(Catalog _cat, int _icase) {

    }

    /**
     * getWWProdCompatId
     *
     * @return
     */
    public WWProdCompatId getWWProdCompatId() {
        return (WWProdCompatId)super.getId();
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public final String dump(boolean _b) {

        StringBuffer sb = new StringBuffer("test dump: ");
        sb.append(NEW_LINE + "=======================================");
        sb.append(NEW_LINE + "class " + this.getClass().getName());
        sb.append(NEW_LINE + "=======================================");
        sb.append(NEW_LINE + "Name is: " + toString());
        sb.append(NEW_LINE + "CatId is:" + this.getWWProdCompatId());
        sb.append(NEW_LINE + "----------------------------------------");
        sb.append(NEW_LINE + "Member Variables");
        sb.append(NEW_LINE + "----------------");
        sb.append(NEW_LINE + "COMPATTYPE: " + this.getCOMPATTYPE());
        sb.append(NEW_LINE + "OS: " + this.getOS());
        sb.append(NEW_LINE + "OS_FC: " + this.getOS_FC());

        sb.append(NEW_LINE + "WWPRODENTITYTYPEFROM: " + this.getWWPRODENTITYTYPEFROM());
        sb.append(NEW_LINE + "WWPRODENTITYIDFROM: " + this.getWWPRODENTITYIDFROM());
        sb.append(NEW_LINE + "WWPARTNUMBERTO: " + this.getWWPARTNUMBERTO());

        sb.append(NEW_LINE + "WWPRODENTITYTYPETO: " + this.getWWPRODENTITYTYPETO());
        sb.append(NEW_LINE + "WWPRODENTITYIDTO: " + this.getWWPRODENTITYIDTO());
        sb.append(NEW_LINE + "WWPARTNUMBERTO: " + this.getWWPARTNUMBERTO());

        sb.append(NEW_LINE + "PUBFROM: " + this.getPUBFROM());
        sb.append(NEW_LINE + "PUBTO: " + this.getPUBTO());

        sb.append(NEW_LINE + "VALFROM: " + this.getVALFROM());
        sb.append(NEW_LINE + "VALTO: " + this.getVALTO());
        sb.append(NEW_LINE + "----------------------------------------");

        if (this.getSmc() != null) {
            sb.append(NEW_LINE + "\t---------");
            sb.append(NEW_LINE + "\tSYNC MAP COLLECTION...");
            sb.append(NEW_LINE + "\t---------");

            for (int x = 0; x < getSmc().getCount(); x++) {
                sb.append(NEW_LINE + "\t" + getSmc().get(x).toString());
            }
        }
        else {
            sb.append(NEW_LINE + "\t---------");
            sb.append(NEW_LINE + "\tNO SYNC MAP COLLECTION ");
            sb.append(NEW_LINE + "\t---------");

        }

        return sb.toString();

    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return this.getWWPARTNUMBERFROM()
            + ", "
            + this.getWWPARTNUMBERTO();
    }

    /**
     * hasSyncMapCollection
     *
     * @return
     */
    public final boolean hasSyncMapCollection() {
        return m_smc != null;
    }

    /**
     *  This guy is reposible for deactivate a current image of himself into the CatDb
     *
     * @param _cat
     * @param _bcommit
     */
    public void deactivate(Catalog _cat, boolean _bcommit) {

        if (Catalog.isDryRun()) {
            return;
        }
        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        WWProdCompatId wwpcid = this.getWWProdCompatId();
        GeneralAreaMapItem gami = wwpcid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();

        try {
            db.callGBL8502(
                rets,
                strEnterprise,
                strCountryCode,
                strLanguageCode,
                iNLSID,
                strCountryList,
                this.getCOMPATTYPE(),
                this.getOS(),
                this.getOS_FC(),
                this.getWWPRODENTITYTYPEFROM(),
                this.getWWPRODENTITYIDFROM(),
                this.getWWPARTNUMBERFROM(),
                this.getWWPRODENTITYTYPETO(),
                this.getWWPRODENTITYIDTO(),
                this.getWWPARTNUMBERTO(),
                this.getPUBFROM(),
                this.getPUBTO(),
                0);

            if (_bcommit) {
                db.commit();
            }
            db.freeStatement();
            db.isPending();
        }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            db.freeStatement();
            db.isPending();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            db.freeStatement();
            db.isPending();
        }

    }

    /**
     *  This guy is reposible for putting a current image of himself into the CatDb
     *
     * @param _cat
     * @param _bcommit
     */
    public void put(Catalog _cat, boolean _bcommit) {

        if (Catalog.isDryRun()) {
            return;
        }
        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        WWProdCompatId wwpcid = this.getWWProdCompatId();
        GeneralAreaMapItem gami = wwpcid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();

        int iActive = (this.isActive() ? 1 : 0);

        Stopwatch sw1 = new Stopwatch();
        sw1.start();

        try {
            db.callGBL8502(
                rets,
                strEnterprise,
                strCountryCode,
                strLanguageCode,
                iNLSID,
                strCountryList,
                this.getCOMPATTYPE(),
                this.getOS(),
                this.getOS_FC(),
                this.getWWPRODENTITYTYPEFROM(),
                this.getWWPRODENTITYIDFROM(),
                this.getWWPARTNUMBERFROM(),
                this.getWWPRODENTITYTYPETO(),
                this.getWWPRODENTITYIDTO(),
                this.getWWPARTNUMBERTO(),
                this.getPUBFROM(),
                this.getPUBTO(),
                iActive);

            if (_bcommit) {
                db.commit();
            }
            db.freeStatement();
            db.isPending();
        }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            db.freeStatement();
            db.isPending();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            db.freeStatement();
            db.isPending();
        }

        double dSw1 = sw1.getFinish();
        WWProdCompatCollection.c_d8502TotalTimings+=dSw1;
        WWProdCompatCollection.c_d8502CallCount+=1.0;
        if(WWProdCompatCollection.c_d8502MaxTiming < dSw1) {
			WWProdCompatCollection.c_d8502MaxTiming = dSw1;
		}
        if(WWProdCompatCollection.c_d8502MinTiming > dSw1 || WWProdCompatCollection.c_d8502MinTiming == 0.0) {
			WWProdCompatCollection.c_d8502MinTiming = dSw1;
		}
     //   D.ebug(D.EBUG_SPEW,"BOB WWProdCompat.put #" + WWProdCompatCollection.c_d8502CallCount + " took " + dSw1 + " ms.");

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
     * If I have an Smc Collection.  This means I have work to do to sync up to the CatDb
     *
     * @return
     */
    public SyncMapCollection getSmc() {
        return m_smc;
    }

    /**
     * If I have an Smc Collection.  This means I have work to do to sync up to the CatDb
     * @param collection
     */
    public void setSmc(SyncMapCollection collection) {
        m_smc = collection;
    }

    /**
     * setAttributes
     * sets all the attributes it can given the EntityItem that was passed
     * This handles all EntityTypes that have to run through this object
     * Here is where we flatten things out and apply any concatenation rules
     *
     * @param _ei
     */
    public void setAttributes(EntityItem _ei, boolean _bFrom) {
        String strTraceBase = "WWProdCompat setAttributes method ";

   //     D.ebug(D.EBUG_SPEW,"setATtributes for:" + _ei.getKey());

        //
        // This is some ugly code. but we keep it all in one spot
        //
        if (_ei.getEntityType().equals(WWProdCompat.TMF_ENTITY_GROUP)) {

   //        D.ebug(D.EBUG_SPEW, this.getClass().getName() + " .getAttributes(): Scanning MODEL EntityType");

            // Machine Type Model - Text
            EANFlagAttribute faMODEL_MACHTYPEATR = (EANFlagAttribute) _ei.getAttribute(MODEL_MACHTYPEATR);
            EANTextAttribute taMODEL_MODELATR = (EANTextAttribute) _ei.getAttribute(MODEL_MODELATR);

            if (faMODEL_MACHTYPEATR != null && taMODEL_MODELATR != null) {
                String strPartNumber = faMODEL_MACHTYPEATR.toString() + taMODEL_MODELATR.toString();
                if (_bFrom) {
                    this.setWWPARTNUMBERFROM(strPartNumber);
                }
                else {
                    this.setWWPARTNUMBERTO(strPartNumber);
                }
            }

            //
            EANTextAttribute ta_PUBFROM = (EANTextAttribute) _ei.getAttribute(ATT_PUBFROM);
            if (ta_PUBFROM != null) {
				String strPUBFROM_prev = this.getPUBFROM();
				String strPUBFROM_new = ta_PUBFROM.toString();
				if(strPUBFROM_prev != null) {
					if(strPUBFROM_new != null) {
						if(strPUBFROM_new.compareTo(strPUBFROM_prev) > 0) {
							setPUBFROM(strPUBFROM_new);
						}
					} else {
						// nothing. its already set.
					}
				} else {
					setPUBFROM(strPUBFROM_new);
				}
			}
			//
        }
        else if (_ei.getEntityType().equals(WWProdCompat.SEO_ENTITY_GROUP) || _ei.getEntityType().equals(WWProdCompat.BDL_ENTITY_GROUP)) {

     //       D.ebug(D.EBUG_SPEW, this.getClass().getName() + " .getAttributes(): in SEO Section");

            // WWSEO_SEOID
            EANTextAttribute taSEO_SEOID = (EANTextAttribute) _ei.getAttribute(WWSEO_SEOID);
            if (taSEO_SEOID != null) {
                String strPartNumber = taSEO_SEOID.toString();
                if (_bFrom) {
                    this.setWWPARTNUMBERFROM(strPartNumber);
                }
                else {
                    this.setWWPARTNUMBERTO(strPartNumber);
                }
            }
            else {
      //          D.ebug(
      //              D.EBUG_ERR,
      //              this.getClass().getName() + " .getAttributes(): *** CANNOT FIND " + WWSEO_SEOID + " in ei " + _ei);
            }

            //
            EANTextAttribute ta_PUBFROM = (EANTextAttribute) _ei.getAttribute(ATT_PUBFROM);
            if (ta_PUBFROM != null) {
				String strPUBFROM_prev = this.getPUBFROM();
				String strPUBFROM_new = ta_PUBFROM.toString();
				if(strPUBFROM_prev != null) {
					if(strPUBFROM_new != null) {
						if(strPUBFROM_new.compareTo(strPUBFROM_prev) > 0) {
							setPUBFROM(strPUBFROM_new);
						}
					} else {
						// nothing. its already set.
					}
				} else {
				    setPUBFROM(strPUBFROM_new);
				}
			}

        }
        else if (_ei.getEntityType().equals(WWProdCompat.MDLCGOSMDL_ENTITY_GROUP)
                 || _ei.getEntityType().equals(WWProdCompat.SEOCGOSSEO_ENTITY_GROUP)
                 || _ei.getEntityType().equals(WWProdCompat.MDLCGOSWWSEO_ENTITY_GROUP)) {
     //       D.ebug(D.EBUG_SPEW, strTraceBase + " in MDLCGOSMDL Section " + _ei.dump(false));
            EANFlagAttribute faRELTYPE = (EANFlagAttribute) _ei.getAttribute(ATT_RELTYPE);
            if (faRELTYPE != null) {
				// 070706: We want to force this English Only: (i.e. "Peer" or "Child")
				Profile profCurr = faRELTYPE.getProfile();
				NLSItem nlsItemCurr = profCurr.getReadLanguage();
            	Vector vct = profCurr.getReadLanguages();
            	for (int i = 0; i < vct.size(); i++) {
                	NLSItem nlsiENG = (NLSItem) vct.elementAt(i);
                	if (nlsiENG.getNLSID() == 1) {
     //               	D.ebug(D.EBUG_SPEW,"WWProdCompat.COMPATTYPE: setting read language to:" + nlsiENG.getNLSID() + " (from " + nlsItemCurr.getNLSID() + ")");
                    	profCurr.setReadLanguage(nlsiENG);
                    	break;
                	}
				}
                this.setCOMPATTYPE(faRELTYPE.toString());
                // now set back...
                profCurr.setReadLanguage(nlsItemCurr);
            }

            //
            EANTextAttribute ta_PUBFROM = (EANTextAttribute) _ei.getAttribute(ATT_PUBFROM);
            if (ta_PUBFROM != null) {
				String strPUBFROM_prev = this.getPUBFROM();
				String strPUBFROM_new = ta_PUBFROM.toString();
				if(strPUBFROM_prev != null) {
					if(strPUBFROM_new != null) {
						if(strPUBFROM_new.compareTo(strPUBFROM_prev) > 0) {
							setPUBFROM(strPUBFROM_new);
						}
					} else {
						// nothing. its already set.
					}
				} else {
				    setPUBFROM(strPUBFROM_new);
				}
			}

            EANTextAttribute ta_PUBTO = (EANTextAttribute) _ei.getAttribute(ATT_PUBTO);
            if (ta_PUBTO != null) {
                this.setPUBTO(ta_PUBTO.toString());
            }

        }
        else if (_ei.getEntityType().equals(WWProdCompat.MODELCGOS_ENTITY_GROUP)
                 || _ei.getEntityType().equals(WWProdCompat.SEOCGOS_ENTITY_GROUP)) {

      //      D.ebug(D.EBUG_SPEW, strTraceBase + " in MODELCGOS Section " + _ei.dump(false));
            EANFlagAttribute faOS = (EANFlagAttribute) _ei.getAttribute(ATT_OS);
            if (faOS != null) {
                this.setOS(faOS.toString());
                this.setOS_FC(faOS.getFirstActiveFlagCode());
            }

        }
    }

/* Need to check COMPATPUBFLG on the MDLCGOSMDL  */

    public static final boolean isCompatpub(Catalog _cat, SyncMapItem _smiRoot, SyncMapCollection _smc) {

        String strCPChildType = _smiRoot.getChildType();
        int iCPChildID = _smiRoot.getChildID();

        if (strCPChildType.equals("MDLCGOSMDL") || strCPChildType.equals("MDLCGOSWWSEO")
             || strCPChildType.equals("SEOCGOSSEO")) {  
                
        EntityItem eiITEM1CGOSITEM2 = Catalog.getEntityItem(_cat, strCPChildType, iCPChildID);
        EANFlagAttribute faCOMPATPUBFLG = (EANFlagAttribute) eiITEM1CGOSITEM2.getAttribute("COMPATPUBFLG");
	   if (faCOMPATPUBFLG != null) {
            if (faCOMPATPUBFLG.getFirstActiveFlagCode().equals("No")) { 
             return false; 
            }
	    else { 
	     return true; 
	    } 
	   }
	   else {
            return true;
	   }
         }
	 else {
	  return true;
	 }
	} 

/**
 * See Section VI in spec.
 * Basically, we are checking on the compat of the two ends (root, end leaf)
 * Good thing to remember: _smiRoot is always itself contained in _smc. Hence, roots are the same.
 */
    public static final boolean isOSCompat(Catalog _cat, SyncMapItem _smiRoot, SyncMapCollection _smc) {

        //if(true) return true; // always true for now.

        String strRootType = _smiRoot.getRootType();
        int iRootID = _smiRoot.getRootID();
        String strEntity2Type = _smiRoot.getEntity2Type();
        String strEntity1Type = _smiRoot.getEntity1Type();
        int iEntity2ID = _smiRoot.getEntity2ID();
        int iEntity1ID = _smiRoot.getEntity1ID();

        String strKey = strRootType + ":" + iRootID + "--" + strEntity2Type + ":" + iEntity2ID;

        MetaFlag[] amfOSLEVEL_left = getLeftOSLEVEL(_cat,_smiRoot,_smc);
        String strOS_FC_right = getRightOS(_cat,_smiRoot);

        // DEBUG OSLEVEL RESULTS HERE..
        String strLeftFlags = "";
        if(amfOSLEVEL_left == null) {
			strLeftFlags = "null";
		} else {
			if(amfOSLEVEL_left[0] == null) {
				strLeftFlags = "Special Fallback Case: use 10589";
			} else {
        		for(int i = 0; i < amfOSLEVEL_left.length; i++) {
        		    MetaFlag mf = amfOSLEVEL_left[i];
        		    if (mf.isSelected()) {
        		        strLeftFlags = mf.getFlagCode() + "|";
        		    }
				}
			}
		}
		if(strLeftFlags.length() > 0) {
			strLeftFlags = strLeftFlags.substring(0,strLeftFlags.length()-1);
		}
	//	D.ebug(D.EBUG_SPEW,"isOSCompat left/right debug for " + strKey + ": Left=\"" + strLeftFlags + "\", Right=\"" + strOS_FC_right + "\"");
        //

        if(amfOSLEVEL_left == null) {
	//		D.ebug(D.EBUG_WARN,"isOSCompat WARNING: No Left OSLEVELs found (null) for:" + strKey + ". We can still check right side.");
			//return false;
		}

        if(strOS_FC_right == null) {
	//		D.ebug(D.EBUG_WARN,"isOSCompat WARNING: No Right OSLEVELs found (null) for:" + strKey + ". We can still check left side.");
			//return false;
		}
		// this is for the 2nd fallback in LSEOBUNDLE case...
		if(amfOSLEVEL_left != null && amfOSLEVEL_left[0] == null) {
	//		D.ebug(D.EBUG_SPEW,"isOSCompat: fallback 2 for:" + strKey + ", returning true");
			return true;
		}

        if(amfOSLEVEL_left != null) {
        	for(int i = 0; i < amfOSLEVEL_left.length; i++) {
        	    MetaFlag mf = amfOSLEVEL_left[i];
        	    if (!mf.isSelected()) {
        	        continue;
        	    }
        	    String strFC = mf.getFlagCode();
        	    if(strFC.equals("10589")) {
     //   	        D.ebug(D.EBUG_SPEW,"case 1: isOSCompat is 10589 (true) for " + strKey);
        	        return true; // always pass these!!!
        	    }
        	}
		}

        if(strOS_FC_right != null && strOS_FC_right.equals("10589")) {
     //       D.ebug(D.EBUG_SPEW,"case 2: isOSCompat is 10589 (true) for " + strKey);
            return true; // always pass these!!!
        } else if(amfOSLEVEL_left != null && strOS_FC_right != null) {
     //       D.ebug(D.EBUG_SPEW,"case 3: isOSCompat comparing OSLEVELs for " + strKey);
            for(int i = 0; i < amfOSLEVEL_left.length; i++) {
                MetaFlag mf = amfOSLEVEL_left[i];
                if (!mf.isSelected()) {
                    continue;
                }
                String strFC2 = mf.getFlagCode();
                if(strOS_FC_right.equals(strFC2)) {
      //              D.ebug(D.EBUG_SPEW,"isOSCompat is:" + strOS_FC_right + "==" + strFC2 + " (true) for " + strKey);
                    return true;
                }
			}
		}
	//	D.ebug(D.EBUG_SPEW,"isOSCompat is False for " + _smiRoot.toString());
		return false;
	}
/**
 * List of possible OSLEVELS for left side of the compat tree
 */
    private static final MetaFlag[] getLeftOSLEVEL(Catalog _cat, SyncMapItem _smiRoot, SyncMapCollection _smc) {

        String strRootType = _smiRoot.getRootType();
        int iRootID = _smiRoot.getRootID();
        String strEntity2Type = _smiRoot.getEntity2Type();
        String strEntity1Type = _smiRoot.getEntity1Type();
        int iEntity2ID = _smiRoot.getEntity2ID();
        int iEntity1ID = _smiRoot.getEntity1ID();

        String strKey = strRootType + ":" + iRootID + "--" + strEntity2Type + ":" + iEntity2ID;

     //   D.ebug(D.EBUG_SPEW,"getLeftOSLevel for:" + strKey);


        MetaFlag[] amfOSLEVEL_left = null;

        boolean bSoftwareOSModelFound = false;
        boolean bHardwareModelFound = false;

        if(strRootType.equals(WWProdCompatCollection.TMF_ENTITYTYPE)) {

	//		D.ebug(D.EBUG_SPEW,"getLeftOSLevel block A (MODEL)");

            EntityItem eiModelRoot = Catalog.getEntityItem(_cat, strRootType, iRootID);
            EANFlagAttribute faMODEL_OSLEVEL = (EANFlagAttribute) eiModelRoot.getAttribute("OSLEVEL");
            if (faMODEL_OSLEVEL != null) {
                // Multi-Flag!!!
                amfOSLEVEL_left = (MetaFlag[]) faMODEL_OSLEVEL.get();
            }
		} else if(strRootType.equals(WWProdCompatCollection.BDL_ENTITYTYPE) || strRootType.equals(WWProdCompatCollection.SEO_ENTITYTYPE)) {

          SMI_LOOP:
            for (int x = 0; x < _smc.getCount(); x++) {
                SyncMapItem smi = _smc.get(x);

                String strChildType_smi = smi.getChildType();
                int iChildID_smi = smi.getChildID();
        		String strEntity2Type_smi = smi.getEntity2Type();
        		String strEntity1Type_smi = smi.getEntity1Type();
        		int iEntity2ID_smi = smi.getEntity2ID();
        		int iEntity1ID_smi = smi.getEntity1ID();

                //D.ebug(D.EBUG_SPEW,"   strRoorType:" + strRootType);
        		//D.ebug(D.EBUG_SPEW,"   strChildType_smi:" + strChildType_smi);

                // MODELWWSEO
                if(strRootType.equals(WWProdCompatCollection.SEO_ENTITYTYPE) && strChildType_smi.equals("MODELWWSEO")) {

      //              D.ebug(D.EBUG_SPEW,"getLeftOSLevel block B (WWSEO) " + ">>>" + strKey + "<<<");

                    EntityItem eiModel = Catalog.getEntityItem(_cat, strEntity1Type_smi, iEntity1ID_smi);
                    EANFlagAttribute faMODEL_OSLEVEL = (EANFlagAttribute) eiModel.getAttribute("OSLEVEL");
           			if (faMODEL_OSLEVEL != null) {
                		amfOSLEVEL_left = (MetaFlag[]) faMODEL_OSLEVEL.get();
                		break SMI_LOOP;
            		}
                } else if(strRootType.equals(WWProdCompatCollection.BDL_ENTITYTYPE)) {

		//			D.ebug(D.EBUG_SPEW,"getLeftOSLevel block C (LSEOBUNDLE)" + ">>>" + strKey + "<<<");

                    // 1) FIND MODEL through LSEOBUNDLE-LSEO-WWSEO-MODEL
                    //    - where, COFCAT = “Software” and APPLICATIONTYPE = “OperatingSystem” (33).
                    if(strChildType_smi.equals("MODELWWSEO")) {
					    EntityItem eiModel = Catalog.getEntityItem(_cat, strEntity1Type_smi, iEntity1ID_smi);
	                    EANFlagAttribute faCOFCAT = (EANFlagAttribute) eiModel.getAttribute("COFCAT");
	                    EANFlagAttribute faAPPLICATIONTYPE = (EANFlagAttribute) eiModel.getAttribute("APPLICATIONTYPE");
	                    if(faCOFCAT != null && faAPPLICATIONTYPE != null &&
	                       faCOFCAT.getFirstActiveFlagCode().equals("101") &&
	                       faAPPLICATIONTYPE.getFirstActiveFlagCode().equals("33") ) {

		//				    D.ebug(D.EBUG_SPEW,"getLeftOSLevel (LSEOBUNDLE):Found a SW MODEL:" + eiModel.getEntityID() + ">>>" + strKey + "<<<");

					        // Yeehaw, now fish out the right SWFEATURE
					        bSoftwareOSModelFound = true;
                            // Now, take OSLEVEL from SWFEATURE via above found WWSEO-SWPRODSTRUCT, where SWPRODSTRUCT.SWCAT = 'ValueMetric".
            				SyncMapCollection smcSWPRODSTRUCTS = new SyncMapCollection();
            				// dont be too frightened by all these inner loops.. they'll really only be executed once
            				// per pass through outer smc if all goes well.
            				for (int g = 0; g < _smc.getCount(); g++) {
            				    SyncMapItem smi2 = _smc.get(g);
            				    // find children SWPRODSTRUCT(s) matching this parent WWSEO child of MODELWWSEO we care about.
            				    if(smi2.getChildType().equals("WWSEOSWPRODSTRUCT") && smi2.getEntity1ID() == iEntity2ID_smi) {
									smcSWPRODSTRUCTS.add(smi2); // store these away.. later we'll need to pass trough em.
								}
							}
							// at this point, we all should be losing our sanity...
							for(int asdfasdf = 0; asdfasdf < smcSWPRODSTRUCTS.getCount(); asdfasdf++) {
								SyncMapItem smiASDFASDF = smcSWPRODSTRUCTS.get(asdfasdf);

		//						D.ebug(D.EBUG_SPEW,"getLeftOSLevel (LSEOBUNDLE):looking at candidate SWPRODSTRUCT:" + smiASDFASDF.getEntity2ID() + ">>>" + strKey + "<<<");

								for(int qwerqwer = 0; qwerqwer < _smc.getCount(); qwerqwer++) {
									SyncMapItem smiQWERQWER = _smc.get(qwerqwer);
									// So, this is the droid...err... SWPRODSTRUCT we're looking for.
									if(smiQWERQWER.getChildType().equals("SWPRODSTRUCT") && smiASDFASDF.getEntity2ID() == smiQWERQWER.getChildID()) {
										EntityItem eiSWFEATURE = Catalog.getEntityItem(_cat, smiQWERQWER.getEntity1Type(),smiQWERQWER.getEntity1ID());
									    EANFlagAttribute faSWFCAT = (EANFlagAttribute) eiSWFEATURE.getAttribute("SWFCCAT");
									    if(faSWFCAT != null && faSWFCAT.getFirstActiveFlagCode().equals("319")) {

		//						            D.ebug(D.EBUG_SPEW,"getLeftOSLevel (LSEOBUNDLE):Found our 319 SWFEATURE:" + eiSWFEATURE.getEntityID() + ">>>" + strKey + "<<<");

											// uuhhh.. we like, found it, beavis.
 	                   						EANFlagAttribute faSWFEATURE_OSLEVEL = (EANFlagAttribute) eiSWFEATURE.getAttribute("OSLEVEL");
           									if(faSWFEATURE_OSLEVEL != null) {
                								amfOSLEVEL_left = (MetaFlag[]) faSWFEATURE_OSLEVEL.get();
                								break SMI_LOOP;
            								}
										}
									}
								}
							}
                            break SMI_LOOP;
					    }
					}
                }
            }

		}

		// fallback if no software model found...
        if(strRootType.equals(WWProdCompatCollection.BDL_ENTITYTYPE) && !bSoftwareOSModelFound) {
          SMI_LOOP:
            for (int x = 0; x < _smc.getCount(); x++) {
                SyncMapItem smi = _smc.get(x);
                String strChildType_smi = smi.getChildType();
                int iChildID_smi = smi.getChildID();
                String strEntity1Type_smi = smi.getEntity1Type();
                int iEntity1ID_smi = smi.getEntity1ID();
                if(strChildType_smi.equals("MODELWWSEO")) {
			        EntityItem eiModel = Catalog.getEntityItem(_cat, strEntity1Type_smi, iEntity1ID_smi);
	                EANFlagAttribute faCOFCAT = (EANFlagAttribute) eiModel.getAttribute("COFCAT");
	                if(faCOFCAT != null && faCOFCAT.getFirstActiveFlagCode().equals("100")) {
						bHardwareModelFound = true;
                    	EANFlagAttribute faOSLEVEL = (EANFlagAttribute) eiModel.getAttribute("OSLEVEL");
           				if (faOSLEVEL != null) {
                			amfOSLEVEL_left = (MetaFlag[]) faOSLEVEL.get();
                			break SMI_LOOP;
            			}
					}
				}

			}
		}

		// nother fallback if no hardware found either. consider 10589
        if(strRootType.equals(WWProdCompatCollection.BDL_ENTITYTYPE) && !bSoftwareOSModelFound && !bHardwareModelFound) {
            amfOSLEVEL_left = new MetaFlag[]{null}; // let's just pretend this means all.
		}

        //D.ebug(D.EBUG_SPEW," ---> getLeftOSLevel returning:array of length:" + (amfOSLEVEL_left == null ? "null" : String.valueOf(amfOSLEVEL_left.length)));

		return amfOSLEVEL_left;
	}

/**
 * OSLEVEL for right side of the compat tree
 */
    private static final String getRightOS(Catalog _cat, SyncMapItem _smiRoot) {
        String strRootType = _smiRoot.getRootType();
        int iRootID = _smiRoot.getRootID();
        String strEntity2Type = _smiRoot.getEntity2Type();
        String strEntity1Type = _smiRoot.getEntity1Type();
        int iEntity2ID = _smiRoot.getEntity2ID();
        int iEntity1ID = _smiRoot.getEntity1ID();

        String strOS_FC = null;

        if(strEntity1Type.equals("MODELCGOS") || strEntity1Type.equals("SEOCGOS")) {
            EntityItem ei = Catalog.getEntityItem(_cat, strEntity1Type, iEntity1ID);
            EANFlagAttribute faOS = (EANFlagAttribute) ei.getAttribute("OS");
            if(faOS != null) {
                strOS_FC = faOS.getFirstActiveFlagCode();
            }
        }
        return strOS_FC;
	}

    /**
     * This here will pick off any relevent keys we need and covert them to attributes
     * Should always be called from a get(cat)
     *
     * @param _smi
     */
    public void setAttributeKeys(SyncMapCollection _smi) {
    }

    /**
     * This generates an XMLFragment per the XML Interface
     *
     * Over the time, we can make new getXXXXX's and pass the
     * xml object to them and they will generate their own fragements
     * @param _xml
     */
    public void generateXMLFragment(XMLWriter _xml) {

        WWProdCompatId wwpcid = this.getWWProdCompatId();

        try {
            _xml.writeEntity("WWPRODCOMPAT");
            xmlCOMPATTYPE(_xml);
            xmlOS(_xml);
            xmlOS_FC(_xml);
            xmlWWPRODENTITYTYPEFROM(_xml);
            xmlWWPRODENTITYIDFROM(_xml);
            xmlWWPARTNUMBERFROM(_xml);
            xmlWWPRODENTITYTYPETO(_xml);
            xmlWWPRODENTITYIDTO(_xml);
            xmlWWPARTNUMBERTO(_xml);
            xmlPUBFROM(_xml);
            xmlPUBTO(_xml);
            xmlVALFROM(_xml);
            xmlVALTO(_xml);
            _xml.endEntity();

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     *  (non-Javadoc)
     *
     * @param _strTag
     * @param _oAtt
     */
    public void setAttribute(String _strTag, Object _oAtt) {
        return;
    }

    /**
     *  (non-Javadoc)
     *
     * @return Object
     * @param _strTag
     */
    public Object getAttribute(String _strTag) {
        if (m_AttCollection.containsKey(_strTag)) {
            return m_AttCollection.get(_strTag);
        }
        else {
     //       D.ebug(D.EBUG_WARN,"attribute not found for " + _strTag);
        }
        return null;
    }

    /**
     * get attribute keys
     * 20050808
     * @return keys[]
     * @author tony
     */
    public String[] getAttributeKeys() {
        if (m_AttCollection != null) {
            Set keys = m_AttCollection.keySet();
            if (keys != null) {
                return (String[]) keys.toArray(new String[m_AttCollection.size()]);
            }
        }
        return null;
    }

    /**
     * setENTERPRISE
     *
     * @param String
     */
    public void setENTERPRISE(String _s) {
        ENTERPRISE = _s;
    }

    /**
     * setCOUNTRYCODE
     *
     * @param String
     */
    public void setCOUNTRYCODE(String _s) {
        COUNTRYCODE = _s;
    }

    /**
     * setLANGUAGECODE
     *
     * @param String
     */
    public void setLANGUAGECODE(String _s) {
        LANGUAGECODE = _s;
    }

    /**
     * setNLSID
     *
     * @param int
     */
    public void setNLSID(int _i) {
        NLSID = _i;
    }

    /**
     * setCOUNTRYLIST
     *
     * @param String
     */
    public void setCOUNTRYLIST(String _s) {
        COUNTRYLIST = _s;
    }

    /**
     * setCOMPATTYPE
     *
     * @param String
     */
    public void setCOMPATTYPE(String _s) {
        COMPATTYPE = _s;
    }

    /**
     * setOS
     *
     * @param String
     */
    public void setOS(String _s) {
        OS = _s;
    }

    /**
     * setOS_FC
     *
     * @param String
     */
    public void setOS_FC(String _s) {
        OS_FC = _s;
    }

    /**
     * setWWPRODENTITYTYPEFROM
     *
     * @param String
     */
    public void setWWPRODENTITYTYPEFROM(String _s) {
        //WWPRODENTITYTYPEFROM = _s;
    }

    /**
     * setWWPRODENTITYTYPEFROM
     *
     * @param String
     */
    public void setWWPRODENTITYIDFROM(int _i) {
        //WWPRODENTITYIDFROM = _i;
    }

    /**
     * setWWPARTNUMBERFROM
     *
     * @param String
     */
    public void setWWPARTNUMBERFROM(String _s) {
        WWPARTNUMBERFROM = _s;
    }

    /**
     * setWWPRODENTITYTYPETO
     *
     * @param String
     */
    public void setWWPRODENTITYTYPETO(String _s) {
        //WWPRODENTITYTYPETO = _s;
    }

    /**
     * setWWPRODENTITYIDTO
     *
     * @param String
     */
    public void setWWPRODENTITYIDTO(int _i) {
        //WWPRODENTITYIDTO = _i;
    }

    /**
     * setWWPARTNUMBERTO
     *
     * @param String
     */
    public void setWWPARTNUMBERTO(String _s) {
        WWPARTNUMBERTO = _s;
    }

    /**
     * setPUBFROM
     *
     * @param String
     */
    public void setPUBFROM(String _s) {
        PUBFROM = _s;
    }

    /**
     * setPUBTO
     *
     * @param String
     */
    public void setPUBTO(String _s) {
        PUBTO = _s;
    }

    /**
     * setVALFROM
     *
     * @param String
     */
    public void setVALFROM(String _s) {
        VALFROM = _s;
    }

    /**
     * setVALTO
     *
     * @param String
     */
    public void setVALTO(String _s) {
        VALTO = _s;
    }

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /**
     * getENTERPRISE
     *
     * @return
     */
    public String getENTERPRISE() {
        if (ENTERPRISE == null) {
            return "";
        }
        return ENTERPRISE;
    }

    /**
     * getCOUNTRYCODE
     *
     * @return
     */
    public String getCOUNTRYCODE() {
        if (COUNTRYCODE == null) {
            return "";
        }
        return COUNTRYCODE;
    }

    /**
     * getLANGUAGECODE
     *
     * @return
     */
    public String getLANGUAGECODE() {
        if (LANGUAGECODE == null) {
            return "";
        }
        return LANGUAGECODE;
    }

    /**
     * getNLSID
     *
     * @return
     */
    public int getNLSID() {
        return NLSID;
    }

    /**
     * getCOUNTRYLIST
     *
     * @return
     */
    public String getCOUNTRYLIST() {
        if (COUNTRYLIST == null) {
            return "";
        }
        return COUNTRYLIST;
    }

    /**
     * getWWENTITYTYPE
     *
     * @return
     */
    public String getCOMPATTYPE() {
        if (COMPATTYPE == null) {
            return "";
        }
        return COMPATTYPE;
    }

    /**
     * getOS
     *
     * @return
     */
    public String getOS() {
        if (OS == null) {
            return "";
        }
        return OS;
    }

    /**
     * getOS_FC
     *
     * @return
     */
    public String getOS_FC() {
        if (OS_FC == null) {
            return "";
        }
        return OS_FC;
    }

    /**
     * getWWPRODENTITYTYPEFROM
     *
     * @return
     */
    public String getWWPRODENTITYTYPEFROM() {
        return getWWProdCompatId().getProdEntityTypeFrom();
    }

    /**
     * getWWPRODENTITYIDFROM
     *
     * @return
     */
    public int getWWPRODENTITYIDFROM() {
        return getWWProdCompatId().getProdEntityIDFrom();
    }

    /**
     * getMODEL
     *
     * @return
     */
    public String getWWPARTNUMBERFROM() {
        if (WWPARTNUMBERFROM == null) {
            return "";
        }
        return WWPARTNUMBERFROM;
    }

    /**
     * getWWPRODENTITYTYPETO
     *
     * @return
     */
    public String getWWPRODENTITYTYPETO() {
        return getWWProdCompatId().getProdEntityTypeTo();
    }

    /**
     * getWWPRODENTITYIDTO
     *
     * @return
     */
    public int getWWPRODENTITYIDTO() {
        return getWWProdCompatId().getProdEntityIDTo();
    }

    /**
     * getWWPARTNUMBERTO
     *
     * @return
     */
    public String getWWPARTNUMBERTO() {
        if (WWPARTNUMBERTO == null) {
            return "";
        }
        return WWPARTNUMBERTO;
    }

    /**
     * getPUBFROM
     *
     * @return
     */
    public String getPUBFROM() {
        if (PUBFROM == null) {
            return "";
        }
        return PUBFROM;
    }

    /**
     * getPUBTO
     *
     * @return
     */
    public String getPUBTO() {
        if (PUBTO == null) {
            return "";
        }
        return PUBTO;
    }

    /**
     * getVALFROM
     *
     * @return
     */
    public String getVALFROM() {
        if (VALFROM == null) {
            return "";
        }
        return VALFROM;
    }

    /**
     * getVALTO
     *
     * @return
     */
    public String getVALTO() {
        if (VALTO == null) {
            return "";
        }
        return VALTO;
    }

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /**
     * xmlENTERPRISE
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlENTERPRISE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("ENTERPRISE");
        _xml.write(ENTERPRISE);
        _xml.endEntity();
    }

    /**
     * xmlCOUNTRYCODE
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlCOUNTRYCODE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("COUNTRYCODE");
        _xml.write(COUNTRYCODE);
        _xml.endEntity();
    }

    /**
     * xmlLANGUAGECODE
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlLANGUAGECODE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("LANGUAGECODE");
        _xml.write(LANGUAGECODE);
        _xml.endEntity();
    }

    /**
     * xmlCOUNTRYLIST
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlCOUNTRYLIST(XMLWriter _xml) throws Exception {
        _xml.writeEntity("COUNTRYLIST");
        _xml.write(COUNTRYLIST);
        _xml.endEntity();
    }

    /**
     * xmlCOMPATTYPE
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlCOMPATTYPE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("COMPATTYPE");
        _xml.write(COMPATTYPE);
        _xml.endEntity();
    }

    /**
     * xmlOS
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlOS(XMLWriter _xml) throws Exception {
        _xml.writeEntity("OS");
        _xml.write(OS);
        _xml.endEntity();
    }

    /**
     * xmlOS_FC
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlOS_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("OS_FC");
        _xml.write(OS_FC);
        _xml.endEntity();
    }

    /**
     * xmlWWPRODENTITYTYPEFROM
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlWWPRODENTITYTYPEFROM(XMLWriter _xml) throws Exception {
        _xml.writeEntity("WWPRODENTITYTYPEFROM");
        _xml.write(getWWPRODENTITYTYPEFROM());
        _xml.endEntity();
    }

    /**
     * xmlWWPRODENTITYIDFROM
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlWWPRODENTITYIDFROM(XMLWriter _xml) throws Exception {
        _xml.writeEntity("WWPRODENTITYIDFROM");
        _xml.write(getWWPRODENTITYIDFROM() + "");
        _xml.endEntity();
    }

    /**
     * xmlWWPARTNUMBERFROM
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlWWPARTNUMBERFROM(XMLWriter _xml) throws Exception {
        _xml.writeEntity("WWPARTNUMBERFROM");
        _xml.write(WWPARTNUMBERFROM);
        _xml.endEntity();
    }

    /**
     * xmlWWPRODENTITYTYPETO
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlWWPRODENTITYTYPETO(XMLWriter _xml) throws Exception {
        _xml.writeEntity("WWPRODENTITYTYPETO");
        _xml.write(getWWPRODENTITYTYPETO());
        _xml.endEntity();
    }

    /**
     * xmlWWPRODENTITYIDTO
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlWWPRODENTITYIDTO(XMLWriter _xml) throws Exception {
        _xml.writeEntity("WWPRODENTITYIDTO");
        _xml.write(getWWPRODENTITYIDTO() + "");
        _xml.endEntity();
    }

    /**
     * xmlWWPARTNUMBERTO
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlWWPARTNUMBERTO(XMLWriter _xml) throws Exception {
        _xml.writeEntity("WWPARTNUMBERTO");
        _xml.write(WWPARTNUMBERTO);
        _xml.endEntity();
    }

    /**
     * xmlPUBFROM
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlPUBFROM(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PUBFROM");
        _xml.write(PUBFROM);
        _xml.endEntity();
    }

    /**
     * xmlPUBTO
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlPUBTO(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PUBTO");
        _xml.write(PUBTO);
        _xml.endEntity();
    }

    /**
     * xmlVALFROM
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlVALFROM(XMLWriter _xml) throws Exception {
        _xml.writeEntity("VALFROM");
        _xml.write(VALFROM);
        _xml.endEntity();
    }

    /**
     * xmlVALTO
     *
     * @param XMLWriter
     * @throws java.lang.Exception
     */
    public void xmlVALTO(XMLWriter _xml) throws Exception {
        _xml.writeEntity("VALTO");
        _xml.write(VALTO);
        _xml.endEntity();
    }

    private void jbInit() throws Exception {
    }

}
/**
<<<<<<< WWProdCompat.java
 * $Log: WWProdCompat.java,v $
 * Revision 1.46  2011/05/05 11:21:33  wendy
 * src from IBMCHINA
 *
 * Revision 1.2  2007/10/09 08:11:48  sulin
 * no message
 *
 * Revision 1.1.1.1  2007/06/05 02:09:36  jingb
 * no message
 *
 * Revision 1.44  2007/01/18 14:49:46  rick
 * MN fix for compatpubflg
 *
 * Revision 1.43  2006/11/16 21:02:12  gregg
 * compile
 *
 * Revision 1.42  2006/11/16 21:00:24  gregg
 * fix
 *
 * Revision 1.41  2006/11/16 20:59:23  gregg
 * fix timing info
 *
 * Revision 1.40  2006/11/16 20:55:49  gregg
 * timings for put
 *
 * Revision 1.39  2006/11/15 23:01:19  gregg
 * more debugs for preformance analysis
 *
 * Revision 1.38  2006/11/06 19:28:14  gregg
 * remove some debug code
 *
 * Revision 1.37  2006/10/20 22:29:44  dave
 * clean up import
 *
 * Revision 1.36  2006/10/20 19:26:21  gregg
 * fix boolean logic
 *
 * Revision 1.35  2006/10/20 19:13:52  gregg
 * debug
 *
 * Revision 1.34  2006/10/20 19:04:21  gregg
 * fix
 *
 * Revision 1.33  2006/10/20 18:27:55  gregg
 * bail out switch for debug purposes
 *
 * Revision 1.32  2006/10/20 18:12:07  gregg
 * debug
 *
 * Revision 1.31  2006/10/20 17:04:45  gregg
 * debug
 *
 * Revision 1.30  2006/10/19 22:29:03  gregg
 * removing said debugs
 *
 * Revision 1.29  2006/10/19 20:54:47  gregg
 * mo' debugs
 *
 * Revision 1.28  2006/09/19 18:08:58  gregg
 * PUBFROM precedence per spec
 *
 * Revision 1.27  2006/09/01 22:10:31  gregg
 * remove some debugs
 *
 * Revision 1.26  2006/08/31 19:36:14  gregg
 * removing debugs
 *
 * Revision 1.25  2006/08/31 17:43:57  gregg
 * null fix
 *
 * Revision 1.24  2006/08/31 17:39:49  gregg
 * debugging
 *
 * Revision 1.23  2006/07/11 18:25:14  gregg
 * pulling back debugs to spew
 *
 * Revision 1.22  2006/07/07 18:15:04  gregg
 * force English only for COMPATTYPE
 *
 * Revision 1.21  2006/04/25 22:59:51  gregg
 * PAtch up Compat filtering CR logic, where left side OSLEVEL flags is null.
 * Meaning, we still should check right side OS for "OS Independent"
 *
 * Revision 1.20  2006/04/18 22:43:48  gregg
 * fix OSCompat logic
 *
 * Revision 1.19  2006/04/18 22:34:10  gregg
 * OSCompat == true on fallback (10589) case.
 *
 * Revision 1.18  2006/04/18 21:32:41  gregg
 * oops one more fix
 *
 * Revision 1.17  2006/04/18 21:29:06  gregg
 * typo:SWFCCAT
 *
 * Revision 1.16  2006/04/18 21:18:29  gregg
 * small debug fix
 *
 * Revision 1.15  2006/04/18 20:57:51  gregg
 * massive messy debugs for os filtering
 *
 * Revision 1.14  2006/04/18 17:36:19  gregg
 * more debug
 *
 * Revision 1.13  2006/04/18 17:25:34  gregg
 * OSCompat left/right side debug
 *
 * Revision 1.12  2006/04/18 00:04:50  gregg
 * os filter fixes
 *
 * Revision 1.11  2006/04/17 23:59:44  gregg
 * more debugging
 *
 * Revision 1.10  2006/04/17 23:52:01  gregg
 * debugging
 *
 * Revision 1.9  2006/04/13 22:24:12  gregg
 * enabling isOSCompat
 *
 * Revision 1.8  2006/04/13 22:21:01  gregg
 * compat filtering.
 *
 * Revision 1.7  2006/04/11 18:20:05  gregg
 * isOSCompat always return true until completed
 *
 * Revision 1.6  2006/04/07 22:08:46  gregg
 * fixes
 *
 * Revision 1.5  2006/04/07 18:12:36  gregg
 * warning:committing from j-builder
 *
 * Revision 1.1.1.1  2006/03/30 17:36:32  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.15  2006/02/06 18:57:13  joan
 * CR2340
 *
 * Revision 1.14  2005/12/16 19:36:29  joan
 * fixes
 *
 * Revision 1.13  2005/11/22 16:57:36  joan
 * work on idl
 *
 * Revision 1.12  2005/10/03 17:40:18  joan
 * fixes
 *
 * Revision 1.11  2005/09/29 18:26:17  joan
 * fixes
 *
 * Revision 1.10  2005/09/29 16:21:05  joan
 * fixes
 *
 * Revision 1.9  2005/09/28 23:13:50  joan
 * fixes
 *
 * Revision 1.8  2005/09/28 23:05:52  joan
 * fixes
 *
 * Revision 1.7  2005/09/27 18:09:49  joan
 * fixes
 *
 * Revision 1.6  2005/09/27 15:31:15  joan
 * fix compile
 *
 * Revision 1.5  2005/09/27 15:26:15  joan
 * fix compile
 *
 * Revision 1.4  2005/09/27 15:19:25  joan
 * fixes
 *
 * Revision 1.3  2005/09/26 18:07:09  joan
 * fix compile
 *
 * Revision 1.2  2005/09/26 17:41:21  joan
 * fix compile
 *
 * Revision 1.1  2005/09/26 16:21:49  joan
 * initial load
 *
 *
 **/

