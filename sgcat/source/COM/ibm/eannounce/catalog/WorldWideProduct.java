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
import java.util.Iterator;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * World Wide Product
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorldWideProduct
    extends CatDBTableRecord {

    //
    // Here is the PDH Attribute Tokens to avoid hard coding
    //
    public static final String MODEL_MACHTYPEATR = "MACHTYPEATR";
    public static final String MODEL_MODELATR = "MODELATR";
    public static final String WWSEO_SEOID = "SEOID";
    public static final String MODEL_MKTGNAME = "MKTGNAME";
    public static final String WWSEO_SPECBID = "SPECBID";
    public static final String WWSEO_PRCFILENAM = "PRCFILENAM";
    public static final String AVAIL_LASTORDERDATE = "LASTORDERDATE";
    public static final String ANNOUNCEMENT_ANNDATE = "ANNDATE";
    public static final String MODEL_STATUS = "STATUS";
    public static final String WWSEO_STATUS = "STATUS";
    public static final String MODEL_COFCAT = "COFCAT";
    public static final String MODEL_COFSUBCAT = "COFSUBCAT";
    public static final String MODEL_COFGRP = "COFGRP";
    public static final String MODEL_COFSUBGRP = "COFSUBGRP";
    public static final String MODEL_MODELORDERCODE = "MODELORDERCODE";
    public static final String MODEL_SARINDC = "SARINDC";
    public static final String MODEL_RATECARDCD = "RATECARDCD";
    public static final String MODEL_SYSIDUNIT = "SYSIDUNIT";
    public static final String MODEL_UNSPSCCD = "UNSPSCCD";
    public static final String WWSEO_UNSPSCCD = "UNSPSCCD";
    public static final String MODEL_UNSPSCSECONDARY = "UNSPSCSECONDARY";
    public static final String WWSEO_UNSPSCSECONDARY = "UNSPSCSECONDARY";
    public static final String MODEL_UNITOFMEAS = "UNITOFMEAS";
    public static final String WWSEO_UNITOFMEAS = "UNITOFMEAS";
    public static final String MODEL_STANDALONE = "STANDALONE";
    public static final String MODEL_ANNDATE = "ANNDATE";
    public static final String MODEL_WITDRAWDATE = "ANNDATE";
    public static final String MODEL_PROJCDNAM = "PROJCDNAM";
    public static final String WWSEO_PROJCDNAM = "PROJCDNAM";
    //add new attribute for dualpipe by Will
    public static final String MODEL_DUALPIPE = "DUALPIPE";

    public static final int PRODSTRUCT_REFERENCE = 1;
    public static final int ATTRIBUTE_REFERENCE = 2;
    public static final int COLLATERAL_REFERENCE = 3;
    public static final int WWCOMPGRP_REFERENCE = 4;

    public static final String TMF_ENTITY_GROUP = "MODEL";
    public static final String SEO_ENTITY_GROUP = "WWSEO";

    private SyncMapCollection m_smc = null;

    private WorldWideAttributeCollection m_wwac = null;
    private ProdStructCollection m_psc = null;
    private CollateralCollection m_col = null;

    public static final String ENTERPRISE = "ENTERPRISE";
    public static final String COUNTRYCODE = "COUNTRYCODE";
    public static final String LANGUAGECODE = "LANGUAGECODE";
    public static final String NLSID = "NLSID";
    public static final String COUNTRYLIST = "COUNTRYLIST";
    public static final String WWENTITYTYPE = "WWENTITYTYPE";
    public static final String WWENTITYID = "WWENTITYID";
    public static final String STATUS = "STATUS";
    public static final String STATUS_FC = "STATUS_FC";
    public static final String PROJCDNAM = "PROJCDNAM";
    public static final String PROJCDNAM_FC = "PROJCDNAM_FC";
    public static final String WWPARTNUMBER = "WWPARTNUMBER";
    public static final String MACHTYPE = "MACHTYPE";
    public static final String MODEL = "MODEL";
    public static final String SPECIALBID = "SPECIALBID";
    public static final String SPECIALBID_FC = "SPECIALBID_FC";
    public static final String PRCFILENAM = "PRCFILENAM";
    public static final String CATEGORY = "CATEGORY";
    public static final String CATEGORY_FC = "CATEGORY_FC";
    public static final String SUBCATEGORY = "SUBCATEGORY";
    public static final String SUBCATEGORY_FC = "SUBCATEGORY_FC";
    public static final String GROUP = "GROUP";
    public static final String GROUP_FC = "GROUP_FC";
    public static final String SUBGROUP = "SUBGROUP";
    public static final String SUBGROUP_FC = "SUBGROUP_FC";
    public static final String ORDERCODE = "ORDERCODE";
    public static final String ORDERCODE_FC = "ORDERCODE_FC";
    public static final String SARINDC = "SARINDC";
    public static final String SARINDC_FC = "SARINDC_FC";
    public static final String SERVICEINDICATOR = "SERVICEINDICATOR";
    public static final String DIVISION = "DIVISION";
    public static final String DIVISION_FC = "DIVISION_FC";
    public static final String RATECARDCODE = "RATECARDCODE";
    public static final String RATECARDCODE_FC = "RATECARDCODE_FC";
    public static final String MKTNAME = "MKTNAME";
    public static final String ANNDATE = "ANNDATE";
    public static final String WITHDRAWDATE = "WITHDRAWDATE";
    public static final String STANDALONE = "STANDALONE";
    public static final String STANDALONE_FC = "STANDALONE_FC";
    public static final String UNITCLASS = "UNITCLASS";
    public static final String UNITCLASS_FC = "UNITCLASS_FC";
    public static final String UNSPSC = "UNSPSC";
    public static final String UNSPSC_FC = "UNSPSC_FC";
    public static final String UNSPSCSEC = "UNSPSCSEC";
    public static final String UNSPSCSEC_FC = "UNSPSCSEC_FC";
    public static final String UNUOM = "UNUOM";
    public static final String UNUOM_FC = "UNUOM_FC";
    public static final String UNUOMSEC = "UNUOMSEC";
    public static final String UNUOMSEC_FC = "UNUOMSEC_FC";
    public static final String APPLICATIONTYPE = "APPLICATIONTYPE";
    public static final String APPLICATIONTYPE_FC = "APPLICATIONTYPE_FC";
    public static final String OSLEVEL = "OSLEVEL";
    public static final String OSLEVEL_FC = "OSLEVEL_FC";
    public static final String VALFROM = "VALFROM";
    public static final String VALTO = "VALTO";
    //add new column for dualpipe by Will
    public static final String DUALPIPE = "DUALPIPE";
    public static final String DUALPIPE_FC = "DUALPIPE_FC";

    public static final String TABLE_NAME = "WWPRODUCT";

    static {
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(ENTERPRISE,8,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(COUNTRYCODE,2,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LANGUAGECODE,2,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(NLSID,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(COUNTRYLIST,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WWENTITYTYPE,32,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(WWENTITYID,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(STATUS,25,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(STATUS_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PROJCDNAM,50,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PROJCDNAM_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WWPARTNUMBER,7,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(MACHTYPE,4,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(MODEL,3,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SPECIALBID,10,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SPECIALBID_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATEGORY,20,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATEGORY_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SUBCATEGORY,80,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SUBCATEGORY_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(GROUP,20,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(GROUP_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SUBGROUP,60,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SUBGROUP_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(ORDERCODE,1,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(ORDERCODE_FC,2,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SARINDC,10,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SARINDC_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SERVICEINDICATOR,10,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(DIVISION,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(DIVISION_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(RATECARDCODE,10,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(RATECARDCODE_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(MKTNAME,254,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(ANNDATE,10,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WITHDRAWDATE,10,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(STANDALONE,10,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(STANDALONE_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNITCLASS,10,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNITCLASS_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNSPSC,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNSPSC_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNSPSCSEC,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNSPSCSEC_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNUOM,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNUOM_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNUOMSEC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(UNUOMSEC_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(APPLICATIONTYPE,15,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(APPLICATIONTYPE_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(OSLEVEL,13400,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(OSLEVEL_FC,1024,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(VALFROM,26,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(VALTO,26,false);
        //add new column for Dualpipe by Will, currently not sure if we need to add two column, one is DualPipe, another is Dualpipe_fc
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(DUALPIPE,3,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(DUALPIPE_FC,3,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PRCFILENAM,128,true);
    }

    //private boolean ISACTIVE = true;

    /**
     * WorldWideProduct lets get the Light
     * @param _cid
     */
    public WorldWideProduct(WorldWideProductId _cid) {
        super(TABLE_NAME, _cid);
    }

    /**
     * WorldWideProduct - lets get the heavy
     * Version
     *
     * @param _cid
     * @param _cat
     */
    public WorldWideProduct(WorldWideProductId _cid, Catalog _cat) {
        this(_cid);
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
                D.ebug(D.EBUG_WARN,"no gami to find!!!");
            }
            while (en.hasMoreElements()) {
                GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
                WorldWideProductId wwpid = new WorldWideProductId(strEntityType,
                    iEntityID, gami);
                WorldWideProduct wwp = new WorldWideProduct(wwpid, cat);
                wwp.getReferences(cat, WorldWideProduct.ATTRIBUTE_REFERENCE);
                WorldWideAttributeCollection wwac = wwp.
                    getWorldWideAttributeCollection();
                wwac.get(cat);

                //
                // Lets iterate and make it even heavier
                //

                Iterator it = wwac.values().iterator();
                while (it.hasNext()) {
                    WorldWideAttribute wwa = (WorldWideAttribute) it.next();
                    wwa.get(cat);
                }

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

        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        Profile prof = _cat.getCatalogProfile();
        WorldWideProductId wwpid = getWorldWideProductId();
        GeneralAreaMapItem gami = wwpid.getGami();
        WorldWideProductCollectionId wwpcid = wwpid.getWWProductCollectionId();
        String strEnterprise = gami.getEnterprise();
        String strEntityType = wwpid.getEntityType();
        int iEntityID = wwpid.getEntityID();
        int iNLSID = gami.getNLSID();
        CatalogInterval cati = wwpcid.getInterval();

        // O.K.. lets see what we got!

        if (wwpcid.isByInterval() && wwpcid.isFromPDH()) {

            // Right now .. we are gong to have to rebuild this guy.. and
            // any other thing it may be resonsible for
            // at this point.. we will always need to

            if (this.getSmc() == null) {
                D.ebug(D.EBUG_WARN,"Cannot pull out of the PDH since there is no SycnMap for me.");
                return;
            }

            //
            // Lets set the valon's in the profile for the Catalog to
            // I am not sure i like setting them here.
            //

            prof.setEffOn(cati.getEndDate());
            prof.setValOn(cati.getEndDate());

            try {

                EntityItem eiRoot = Catalog.getEntityItem(_cat, wwpid.getEntityType(), wwpid.getEntityID());
                setActive(eiRoot.isActive());

                db.debug(D.EBUG_SPEW,
                         "eiRoot:" + eiRoot.getKey() + " isActive?" +
                         eiRoot.isActive());

                this.setAttributes( (eiRoot));

                //
                // O.K.  here is where I have to go get other things If I have to find kiddies.
                // If we have a sync map collection.. lets get it and search for other things
                //
                //if (this.isSEO() && this.hasSyncMapCollection()) {

                db.debug(D.EBUG_SPEW, this.getSmc().toString());

                //
                // These are all the things I care about..
                //
                HashMap  hmMyEnts = new HashMap();
                hmMyEnts.put("MODEL","Y");
                hmMyEnts.put("ANNOUNCEMENT","Y");

                for (int i = 0; i < this.getSmc().getCount(); i++) {
                    SyncMapItem smi = getSmc().get(i);
                    String strChildType = smi.getChildType();
                    if ((hmMyEnts.containsKey(strChildType)) && (smi.getChildTran().equals("ON"))) {

                        // We may just want to limit this to what we know we want
                        // to save time
                        //
                        // We know that the SMI Should contain a Model..
                        // so lets fish one out
                        //
                        db.debug(D.EBUG_SPEW,
                                 this.getClass().getName() + ".get(): DP1" +
                                 smi.toString());
                        eiRoot = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
                        this.setAttributes(eiRoot);

                    }
                }

                // Per CR5744: take a pass through and find out
                //             a) if we are on a Software or Service type MODEL
                //             b) if ORDERCODE is not null
                //             --> THEN we need to default ORDERCODE to "I"
                //
                // changed a bit....1/13/06...as follows...
                String strORDERCODE = getStringVal("ORDERCODE");
                if(wwpid.isTMF() && (isSoftwareModel() || isServiceModel())) {
					if(strORDERCODE == null || strORDERCODE.equals("")) {
						putStringVal(ORDERCODE,"I");
						putStringVal(ORDERCODE_FC,"100");
					}
				} else if(wwpid.isSEO() && (isSoftwareModel() || isServiceModel() || isHardwareModel())) {
					if(strORDERCODE == null || strORDERCODE.equals("")) {
						putStringVal(ORDERCODE,"B");
						putStringVal(ORDERCODE_FC,"120");
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
                rs = db.callGBL4010(new ReturnStatus( -1), strEnterprise,
                                    strEntityType, iEntityID, iNLSID);

                if (rs.next()) {

                    int i = 1;

                    putStringVal(PROJCDNAM, rs.getString(i++));
                    putStringVal(PROJCDNAM_FC, rs.getString(i++));
                    putStringVal(WWPARTNUMBER, rs.getString(i++));
                    putStringVal(MACHTYPE, rs.getString(i++));
                    putStringVal(MODEL, rs.getString(i++));
                    putStringVal(SPECIALBID, rs.getString(i++));
                    putStringVal(SPECIALBID_FC, rs.getString(i++));
                    putStringVal(CATEGORY, rs.getString(i++));
                    putStringVal(CATEGORY_FC, rs.getString(i++));
                    putStringVal(SUBCATEGORY, rs.getString(i++));
                    putStringVal(SUBCATEGORY_FC, rs.getString(i++));
                    putStringVal(GROUP, rs.getString(i++));
                    putStringVal(GROUP_FC, rs.getString(i++));
                    putStringVal(SUBGROUP, rs.getString(i++));
                    putStringVal(SUBGROUP_FC, rs.getString(i++));
                    putStringVal(ORDERCODE, rs.getString(i++));
                    putStringVal(ORDERCODE_FC, rs.getString(i++));
                    putStringVal(SARINDC, rs.getString(i++));
                    putStringVal(SARINDC_FC, rs.getString(i++));
                    putStringVal(SERVICEINDICATOR, rs.getString(i++));
                    putStringVal(DIVISION, rs.getString(i++));
                    putStringVal(DIVISION_FC, rs.getString(i++));
                    putStringVal(RATECARDCODE, rs.getString(i++));
                    putStringVal(RATECARDCODE_FC, rs.getString(i++));
                    //add dualpipe
                    putStringVal(DUALPIPE, rs.getString(i++));
                    putStringVal(DUALPIPE_FC, rs.getString(i++));
                    putStringVal(STATUS, rs.getString(i++));
                    putStringVal(STATUS_FC, rs.getString(i++));
                    putStringVal(VALFROM, rs.getString(i++));
                    putStringVal(VALTO, rs.getString(i++));
                    //setISACTIVE(rs.getInt(i++) == 1);
                    putStringVal(PRCFILENAM, rs.getString(i++));

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

        WorldWideProductId wwpid = this.getWorldWideProductId();
        WorldWideProductCollectionId wwpcid = wwpid.getWWProductCollectionId();
        int iType = wwpcid.getType();
        // GAB 033106 - now the reason we want this, is because we are reall grabbing full CURRENT images for EACH prodstruct under this product..
        //              Because we have already deactivated all ps records for this product in the catdb.
        int iPSType = iType;
        if(iPSType == CollectionId.NET_CHANGES) {
			iPSType = CollectionId.FULL_IMAGES;
		}
        //
        int iSource = wwpcid.getSource();
        CatalogInterval cati = wwpcid.getInterval();

        switch (_icase) {
            case ATTRIBUTE_REFERENCE:

                WorldWideAttributeCollectionId wwacid = new WorldWideAttributeCollectionId(wwpid, iSource, iType, cati);
                setWorldWideAttributeCollection(new WorldWideAttributeCollection(wwacid));

                //
                // Lets share the SMC stuff
                //
                if (this.hasSyncMapCollection()) {
                    D.ebug(D.EBUG_SPEW,"getReferences(ATTRIBUTE_REFERENCE) - setting wwac's SMC");
                    getWorldWideAttributeCollection().setSmc(this.getSmc());
                }

                D.ebug(D.EBUG_SPEW,
                       "getReferences(ATTRIBUTE_REFERENCE) - lets go stub out the WWAC" +
                       wwacid);
                getWorldWideAttributeCollection().get(_cat);
                setDeep(true);
                break;
            case PRODSTRUCT_REFERENCE:

                D.ebug(this, D.EBUG_DETAIL,
                       "getReferences(PRODSTRUCT_REFERENCE) Lets go after this");

                //
                // Lets make a new list for ProdStruct and pass on the context of the list
                //
                ProdStructCollectionId pscid = new ProdStructCollectionId(wwpid,
                    iSource, iPSType, cati);
                D.ebug(D.EBUG_SPEW,
                       "getReferences(PRODSTRUCT_REFERENCE) - making new PSC from pscid" +
                       pscid);
                setProdStructCollection(new ProdStructCollection(pscid));

                //
                // Lets share the SMC stuff
                //
                if (this.hasSyncMapCollection()) {
                    D.ebug(D.EBUG_SPEW,
                           "getReferences(PRODSTRUCT_REFERENCE) - setting wwpc's SMC");
                    getProdStructCollection().setSmc(this.getSmc());
                }

                //
                // Lets get a list of Stubs
                //
                D.ebug(D.EBUG_SPEW,
                       "getReferences(PRODSTRUCT_REFERENCE) - lets go stub pout the PSC" +
                       pscid);
                getProdStructCollection().get(_cat);
                setDeep(true);
                break;

            case COLLATERAL_REFERENCE:

                D.ebug(D.EBUG_SPEW,
                       "getReferences(COLLATERAL_REFERENCE) Lets go after this");

                //
                // Lets make a new list for ProdStruct and pass on the context of the list
                //
                CollateralCollectionId colcid = new CollateralCollectionId(
                    wwpid, cati, iSource, iType);
                D.ebug(D.EBUG_SPEW,
                       "getReferences(COLLATERAL_REFERENCE) - making new colc from colcid" +
                       colcid);
                setCollateralCollection(new CollateralCollection(colcid));

                //
                // Lets share the SMC stuff
                //
                if (this.hasSyncMapCollection()) {
                    D.ebug(D.EBUG_SPEW,
                           "getReferences(COLLATERAL_REFERENCE) - setting wwpc's SMC");
                    getCollateralCollection().setSmc(this.getSmc());
                }

                //
                // Lets get a list of Stubs
                //
                D.ebug(D.EBUG_SPEW,
                    "getReferences(COLLATERAL_REFERENCE) - lets go stub out the CollateralCollection" +
                    colcid);
                getCollateralCollection().get(_cat);
                setDeep(true);

                break;
            default:

                break;
        }

    }

    /**
     * getWorldWideProductId
     *
     * @return
     */
    public WorldWideProductId getWorldWideProductId() {
        return (WorldWideProductId)super.getId();
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
        sb.append(NEW_LINE + "CatId is:" + this.getWorldWideProductId());
        sb.append(NEW_LINE + "----------------------------------------");
        sb.append(NEW_LINE + "Member Variables");
        sb.append(NEW_LINE + "----------------");
        sb.append(NEW_LINE + "PROJCDNAM: " + this.getStringVal(PROJCDNAM));
        sb.append(NEW_LINE + "PROJCDNAM_FC: " + this.getStringVal(PROJCDNAM_FC));
        sb.append(NEW_LINE + "WWPARTNUMBER: " + this.getStringVal(WWPARTNUMBER));
        sb.append(NEW_LINE + "MACHTYPE: " + this.getStringVal(MACHTYPE));
        sb.append(NEW_LINE + "MODEL: " + this.getStringVal(MODEL));
        sb.append(NEW_LINE + "SPECIALBID: " + this.getStringVal(SPECIALBID));
        sb.append(NEW_LINE + "SPECIALBID_FC: " +
                  this.getStringVal(SPECIALBID_FC));
        sb.append(NEW_LINE + "STATUS: " + this.getStringVal(STATUS));
        sb.append(NEW_LINE + "STATUS_FC: " + this.getStringVal(STATUS_FC));
        sb.append(NEW_LINE + "ANNDATE: " + this.getStringVal(ANNDATE));
        sb.append(NEW_LINE + "WITHDRAWDATE: " + this.getStringVal(WITHDRAWDATE));

        sb.append(NEW_LINE + "CATEGORY: " + this.getStringVal(CATEGORY));
        sb.append(NEW_LINE + "CATEGORY_FC: " + this.getStringVal(CATEGORY_FC));
        sb.append(NEW_LINE + "SUBCATEGORY: " + this.getStringVal(SUBCATEGORY));
        sb.append(NEW_LINE + "SUBCATEGORY_FC: " +
                  this.getStringVal(SUBCATEGORY_FC));
        sb.append(NEW_LINE + "GROUP: " + this.getStringVal(GROUP));
        sb.append(NEW_LINE + "GROUP_FC: " + this.getStringVal(GROUP_FC));
        sb.append(NEW_LINE + "SUBGROUP: " + this.getStringVal(SUBGROUP));
        sb.append(NEW_LINE + "SUBGROUP_FC: " + this.getStringVal(SUBGROUP_FC));
        sb.append(NEW_LINE + "ORDERCODE: " + this.getStringVal(ORDERCODE));
        sb.append(NEW_LINE + "ORDERCODE_FC: " + this.getStringVal(ORDERCODE_FC));
        sb.append(NEW_LINE + "SARINDC: " + this.getStringVal(SARINDC));
        sb.append(NEW_LINE + "SARINCD_FC: " + this.getStringVal(SARINDC_FC));
        sb.append(NEW_LINE + "SERVICEINDICATOR:" +
                  this.getStringVal(SERVICEINDICATOR));
        sb.append(NEW_LINE + "DIVSION:" + this.getStringVal(DIVISION));
        sb.append(NEW_LINE + "DIVSION_FC:" + this.getStringVal(DIVISION_FC));
        sb.append(NEW_LINE + "RATECARDCODE:" + this.getStringVal(RATECARDCODE));
        sb.append(NEW_LINE + "RATECARDCODE_FC:" +
                  this.getStringVal(RATECARDCODE_FC));
        sb.append(NEW_LINE + "UNITCLASS:" + this.getStringVal(UNITCLASS));
        sb.append(NEW_LINE + "UNITCLASS_FC:" + this.getStringVal(UNITCLASS_FC));
        sb.append(NEW_LINE + "UNSPSC:" + this.getStringVal(UNSPSC));
        sb.append(NEW_LINE + "UNSPSC_FC:" + this.getStringVal(UNSPSC_FC));
        sb.append(NEW_LINE + "UNUOM:" + this.getStringVal(UNUOM));
        sb.append(NEW_LINE + "UNUOM_FC:" + this.getStringVal(UNUOM_FC));
        //add dualpipe
        sb.append(NEW_LINE + "DUALPIPE:" + this.getStringVal(DUALPIPE));
        sb.append(NEW_LINE + "DUALPIPE_FC:" + this.getStringVal(DUALPIPE_FC));
        sb.append(NEW_LINE + "PRCFILENAM:" + this.getStringVal(PRCFILENAM));
        sb.append(NEW_LINE + "VALFROM:" + this.getStringVal(VALFROM));
        sb.append(NEW_LINE + "VALTO:" + this.getStringVal(VALTO));
        //sb.append(NEW_LINE + "ISACTIVE:" + this.isISACTIVE());
        sb.append(NEW_LINE + "----------------------------------------");
        //TODO add dualpile to the append log

        if (this.hasWorldWideAttributeCollection()) {
            sb.append(NEW_LINE + "\t---------");
            sb.append(NEW_LINE + "\tWW ATT COLLECTION...");
            sb.append(NEW_LINE + "\t---------");
            Iterator it = getWorldWideAttributeCollection().values().iterator();
            int i = 1;
            while (it.hasNext()) {
                WorldWideAttribute wwa = (WorldWideAttribute) it.next();
                sb.append(NEW_LINE + "\t" + (i++) + " - " + wwa);
                Iterator it2 = wwa.values().iterator();
                while (it2.hasNext()) {
                    AttributeFragment wwaf = (AttributeFragment) it2.next();
                    sb.append(NEW_LINE + "\t\t" + wwaf);
                }
            }
        }
        else {
            sb.append(NEW_LINE + "\t---------");
            sb.append(NEW_LINE + "\tNO WWATT COLLECTION IN THIS OBJECT");
            sb.append(NEW_LINE + "\t---------");

        }

        if (this.hasCollateralCollection()) {
            sb.append(NEW_LINE + "\t---------");
            sb.append(NEW_LINE + "\tWW Collateral Collection...");
            sb.append(NEW_LINE + "\t---------");
            Iterator it = getCollateralCollection().values().iterator();
            int i = 1;
            while (it.hasNext()) {
                Collateral col = (Collateral) it.next();
                sb.append(NEW_LINE + "\t" + (i++) + " - " + col);
            }
        }
        else {
            sb.append(NEW_LINE + "\t---------");
            sb.append(NEW_LINE + "\tNo Collateral COLLECTION IN THIS OBJECT");
            sb.append(NEW_LINE + "\t---------");

        }

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

        sb.append(NEW_LINE + "\t---------");
        sb.append(NEW_LINE + "\tLocProd...");
        sb.append(NEW_LINE + "\t---------");

        sb.append(NEW_LINE + "\t---------");
        sb.append(NEW_LINE + "\tCompGrps...");
        sb.append(NEW_LINE + "\t---------");

        sb.append(NEW_LINE + "=======================================");

        return sb.toString();

    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return this.getStringVal(WWPARTNUMBER)
            + ", "
            + this.getStringVal(CATEGORY)
            + ":"
            + this.getStringVal(SUBCATEGORY)
            + ", "
            + this.getStringVal(GROUP)
            + ":"
            + this.getStringVal(SUBGROUP);
    }

    /**
     * getWorldWideAttributeCollection
     *
     * @return
     */
    public WorldWideAttributeCollection getWorldWideAttributeCollection() {
        return m_wwac;
    }

    /**
     * setWorldWideAttributeCollection
     *
     * @param collection
     */
    public void setWorldWideAttributeCollection(WorldWideAttributeCollection
                                                collection) {
        m_wwac = collection;
    }

    /**
     * getProdStructCollection
     *
     * @return
     */
    public ProdStructCollection getProdStructCollection() {
        return m_psc;
    }

    /**
     * getCollateralCollection
     *
     * @return
     */
    public CollateralCollection getCollateralCollection() {
        return m_col;
    }

    /**
     * setCollateralCollection
     *
     * @param _col
     */
    public void setCollateralCollection(CollateralCollection _col) {
        m_col = _col;
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
     * setProdStructCollection
     *
     * @param collection
     */
    public void setProdStructCollection(ProdStructCollection collection) {
        m_psc = collection;
    }

    /**
     * isAttributeCollectionLoaded
     *
     * @return
     */
    public boolean isAttributeCollectionLoaded() {
        return m_wwac != null;
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
        WorldWideProductId wwpid = this.getWorldWideProductId();
        GeneralAreaMapItem gami = wwpid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();
        String strWWEntityType = wwpid.getEntityType();
        int iWWEntityID = wwpid.getEntityID();

        int iActive = (this.isActive() ? 1 : 0);
        //TODO after confirm the new table structure to modify it
        try {
            db.callGBL8982(
                rets,
                strEnterprise,
                strCountryCode,
                strLanguageCode,
                iNLSID,
                strCountryList,
                strWWEntityType,
                iWWEntityID,
                this.getStringVal(STATUS),
                this.getStringVal(STATUS_FC),
                this.getStringVal(PROJCDNAM),
                this.getStringVal(PROJCDNAM_FC),
                this.getStringVal(WWPARTNUMBER),
                this.getStringVal(MACHTYPE),
                this.getStringVal(MODEL),
                this.getStringVal(SPECIALBID),
                this.getStringVal(SPECIALBID_FC),
                this.getStringVal(CATEGORY),
                this.getStringVal(CATEGORY_FC),
                this.getStringVal(SUBCATEGORY),
                this.getStringVal(SUBCATEGORY_FC),
                this.getStringVal(GROUP),
                this.getStringVal(GROUP_FC),
                this.getStringVal(SUBGROUP),
                this.getStringVal(SUBGROUP_FC),
                this.getStringVal(ORDERCODE),
                this.getStringVal(ORDERCODE_FC),
                this.getStringVal(SARINDC),
                this.getStringVal(SARINDC_FC),
                this.getStringVal(SERVICEINDICATOR),
                this.getStringVal(DIVISION),
                this.getStringVal(DIVISION_FC),
                this.getStringVal(RATECARDCODE),
                this.getStringVal(RATECARDCODE_FC),
                this.getStringVal(MKTNAME),
                this.getStringVal(ANNDATE),
                this.getStringVal(WITHDRAWDATE),
                this.getStringVal(STANDALONE),
                this.getStringVal(STANDALONE_FC),
                this.getStringVal(UNITCLASS),
                this.getStringVal(UNITCLASS_FC),
                this.getStringVal(UNSPSC),
                this.getStringVal(UNSPSC_FC),
                this.getStringVal(UNSPSCSEC),
                this.getStringVal(UNSPSCSEC_FC),
                this.getStringVal(UNUOM),
                this.getStringVal(UNUOM_FC),
                this.getStringVal(UNUOMSEC),
                this.getStringVal(UNUOMSEC_FC),
                this.getStringVal(APPLICATIONTYPE),
                this.getStringVal(APPLICATIONTYPE_FC),
                this.getStringVal(OSLEVEL),
                this.getStringVal(OSLEVEL_FC),
                this.getStringVal(DUALPIPE),//add these two for dualpipe
                this.getStringVal(DUALPIPE_FC),
                iActive,
                this.getStringVal(PRCFILENAM));

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
    public void setAttributes(EntityItem _ei) {

        String strVE = null;
        System.out.println(getWorldWideProductId().getEntityType());
        if (isTMF()) {
            strVE = "TMF";
        }
        else if (isSEO()) {
            strVE = "SEO";
        }

        setAttributesFromProps(_ei, strVE);

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
     * isSEO
     *
     * @return
     */
    public boolean isSEO() {
        return getWorldWideProductId().isSEO();
    }

    /**
     * isTMF
     *
     * @return
     */
    public boolean isTMF() {
        return getWorldWideProductId().isTMF();
    }

    /**
     * Returns true if we have a WW Att Collection
     * @return
     */
    public final boolean hasWorldWideAttributeCollection() {
        return this.m_wwac != null;
    }

    /**
     * Returns true if we have a WW Att Collection
     * @return
     */
    public final boolean hasCollateralCollection() {
        return this.m_col != null;
    }

    /**
     * This generates an XMLFragment per the XML Interface
     *
     * Over the time, we can make new getXXXXX's and pass the
     * xml object to them and they will generate their own fragements
     * @param _xml
     */
    public void generateXMLFragment(XMLWriter _xml) {

        WorldWideProductId wwpid = this.getWorldWideProductId();

        if (this.isTMF()) {

            try {
                _xml.writeEntity(wwpid.getEntityType());
                this.writeXMLString(_xml, WWPARTNUMBER);
                this.writeXMLString(_xml, ANNDATE);
                this.writeXMLString(_xml, WITHDRAWDATE);
                this.writeXMLString(_xml, STATUS);
                this.writeXMLString(_xml, CATEGORY);
                this.writeXMLString(_xml, SUBCATEGORY);
                this.writeXMLString(_xml, GROUP);
                this.writeXMLString(_xml, SUBGROUP);
                this.writeXMLString(_xml, ORDERCODE);
                this.writeXMLString(_xml, STANDALONE);
                this.writeXMLString(_xml, SARINDC);
                this.writeXMLString(_xml, SERVICEINDICATOR);
                this.writeXMLString(_xml, RATECARDCODE);
                this.writeXMLString(_xml, UNITCLASS);
                this.writeXMLString(_xml, PROJCDNAM);

                //
                // Now.. lets iterate through Prodstruct

                Iterator it = this.getProdStructCollection().values().iterator();

                while (it.hasNext()) {
                    ProdStruct ps = (ProdStruct) it.next();
                    ps.generateXMLFragment(_xml);
                }
                _xml.endEntity();

            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        else if (this.isSEO()) {

            try {
                _xml.writeEntity(wwpid.getEntityType());
                this.writeXMLString(_xml, WWPARTNUMBER);
                this.writeXMLString(_xml, SPECIALBID);
                this.writeXMLString(_xml, ANNDATE);
                this.writeXMLString(_xml, WITHDRAWDATE);
                this.writeXMLString(_xml, STATUS);
                this.writeXMLString(_xml, CATEGORY);
                this.writeXMLString(_xml, SUBCATEGORY);
                this.writeXMLString(_xml, GROUP);
                this.writeXMLString(_xml, SUBGROUP);
                this.writeXMLString(_xml, ORDERCODE);
                this.writeXMLString(_xml, STANDALONE);
                this.writeXMLString(_xml, SARINDC);
                this.writeXMLString(_xml, SERVICEINDICATOR);
                this.writeXMLString(_xml, RATECARDCODE);
                this.writeXMLString(_xml, UNITCLASS);
                this.writeXMLString(_xml, PROJCDNAM);
                this.writeXMLString(_xml, PRCFILENAM);

                Iterator it = this.getWorldWideAttributeCollection().values().
                    iterator();

                while (it.hasNext()) {
                    WorldWideAttribute wwa = (WorldWideAttribute) it.next();
                    wwa.generateXMLFragment(_xml);
                }

                it = this.getProdStructCollection().values().iterator();

                while (it.hasNext()) {
                    ProdStruct ps = (ProdStruct) it.next();
                    ps.generateXMLFragment(_xml);
                }

                _xml.endEntity();
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void putStringVal(String _strColKey, String _strVal) {
        super.putStringVal(_strColKey, _strVal);
    }

    public void putIntVal(String _strColKey, int _iVal) {
        super.putIntVal(_strColKey, _iVal);
    }

    public String getStringVal(String _strColKey) {
        String s = super.getStringVal(_strColKey);
        if (s == null &&
            (_strColKey.equals(ANNDATE) ||
             _strColKey.equals(WITHDRAWDATE))) {
            s = NO_DATE_VAL;
        }
        if (s == null) {
            s = "";
        }
        return s;
    }

    public int getIntVal(String _iColKey) {
        return super.getIntVal(_iColKey);
    }

    private boolean isSoftwareModel() {
		String s = getStringVal("CATEGORY");
		if(s != null && s.trim().equalsIgnoreCase("Software")) {
			return true;
		}
		return false;
	}

    private boolean isServiceModel() {
		String s = getStringVal("CATEGORY");
		if(s != null && s.trim().equalsIgnoreCase("Service")) {
			return true;
		}
		return false;
	}

    private boolean isHardwareModel() {
		String s = getStringVal("CATEGORY");
		if(s != null && s.trim().equalsIgnoreCase("Hardware")) {
			return true;
		}
		return false;
	}

    private boolean isSystemModel() {
		String s = getStringVal("SUBCATEGORY");
		if(s != null && s.trim().equalsIgnoreCase("System")) {
			return true;
		}
		return false;
	}

    private boolean isBaseModel() {
		String s = getStringVal("GROUP");
		if(s != null && s.trim().equalsIgnoreCase("Base")) {
			return true;
		}
		return false;
	}

}
/**
 * $Log: WorldWideProduct.java,v $
 * Revision 1.9  2015/03/05 16:12:39  ptatinen
 * Add new attributes for Lenovo CQ
 *
 * Revision 1.8  2011/06/15 20:09:29  praveen
 * Increase oslevel length to 13400
 *
 * Revision 1.4  2011/05/10 07:09:56  guobin
 * add two new attributes to wwproduct: dualpipe and dualpipe_fc
 *
 * Revision 1.6  2011/05/05 11:21:31  wendy
 * src from IBMCHINA
 *
 * Revision 1.3  2008/01/18 04:22:19  yang
 * recover to previous version
 *
 *
 * Revision 1.1.1.1  2007/06/05 02:09:33  jingb
 * no message
 *
 * Revision 1.5  2007/05/31 16:07:36  rick
 * increase length of oslevel and oslevel_fc
 *
 * Revision 1.4  2007/03/29 16:25:07  rick
 * MN 31018919 - changed  WorldWideProduct.java to not
 * use inactive child relators in get method
 *
 * Revision 1.3  2006/07/11 18:25:14  gregg
 * pulling back debugs to spew
 *
 * Revision 1.2  2006/03/31 21:36:48  gregg
 * for net type products, prodstructs beneath are now dubbed "full"
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.134  2006/02/28 23:12:55  gregg
 * SPECIALBID_FC col 8
 *
 * Revision 1.133  2006/02/28 20:54:42  gregg
 * oslevel cleanup old code
 *
 * Revision 1.132  2006/01/16 19:23:51  gregg
 * OSLEVEL: update per 1/15 discussion
 * Net:
 *  - LSEO, LSEO BUNDLE has OSLEVEL move into product table (and prodstruct additionally for kicks, though not specified)
 *  - LCTOs taken from Model and moved into the Product table.
 *       (then additional spec logic is applied to these in prodstruct)
 * - WW TMF's to prodstruct only. MODEL.OSLEVEL for wwproducts moved over as is.
 *
 * Revision 1.131  2006/01/14 00:31:22  gregg
 * update for CR5744 per note on 1/13
 *
 * Revision 1.130  2006/01/13 23:49:30  gregg
 * more OSLEVEL processing per 6/12 spec
 *
 * Revision 1.129  2006/01/13 20:57:54  gregg
 * ordercode flagcode straightening
 *
 * Revision 1.128  2006/01/11 22:03:23  gregg
 * CR5744
 *
 * Revision 1.127  2005/12/14 19:09:35  gregg
 * putting empty strings (instead of nulls) back in for now until Alpha1 allow nulls is figured out
 *
 * Revision 1.126  2005/12/13 18:30:57  gregg
 * allow nulls
 *
 * Revision 1.125  2005/12/13 18:11:32  gregg
 * OSLEVEL
 *
 * Revision 1.124  2005/10/28 17:03:04  gregg
 * columns object allowNull property to return null or ""
 *
 * Revision 1.123  2005/10/26 18:05:13  dave
 * ok.. family page for collateral
 *
 * Revision 1.122  2005/10/26 01:35:53  dave
 * only pick on SMI's the I need
 *
 * Revision 1.121  2005/10/26 00:57:14  dave
 * <No Comment Entered>
 *
 * Revision 1.120  2005/10/04 23:24:16  joan
 * work on component
 *
 * Revision 1.119  2005/10/03 22:35:19  joan
 * fixes
 *
 * Revision 1.118  2005/10/03 22:08:33  joan
 * work on component
 *
 * Revision 1.117  2005/09/29 22:14:51  gregg
 * somne debugs and such
 *
 * Revision 1.116  2005/09/28 23:20:55  gregg
 * switch on VEs
 *
 * Revision 1.115  2005/09/28 23:15:40  gregg
 * update
 *
 * Revision 1.114  2005/09/28 22:57:34  gregg
 * some property-izing
 *
 * Revision 1.113  2005/09/26 18:35:16  gregg
 * fix some columns widths
 *
 * Revision 1.112  2005/09/26 18:21:29  gregg
 * NO_DATE_VAL
 *
 * Revision 1.111  2005/09/23 20:55:57  gregg
 * parm fix
 *
 * Revision 1.110  2005/09/23 20:47:25  gregg
 * syncing up parms w/ ddl
 *
 * Revision 1.109  2005/09/23 20:34:32  gregg
 * column defs
 *
 * Revision 1.108  2005/09/23 19:54:56  gregg
 * WorldWideProduct properties for col att mappings
 *
 * Revision 1.107  2005/09/23 17:55:39  gregg
 * one more fix
 *
 * Revision 1.106  2005/09/23 17:47:01  gregg
 * ok, got it
 *
 * Revision 1.105  2005/09/23 17:41:13  gregg
 * extend CatDBTableRecord
 *
 * Revision 1.104  2005/09/22 23:42:02  gregg
 * fixx
 *
 * Revision 1.103  2005/09/22 23:36:40  gregg
 * compile fix
 *
 * Revision 1.102  2005/09/22 23:23:51  gregg
 * compile fix
 *
 * Revision 1.101  2005/09/22 23:15:37  gregg
 * going for putStringVal, etc...
 *
 * Revision 1.100  2005/09/22 22:45:22  gregg
 * compile fix
 *
 * Revision 1.99  2005/09/13 05:42:19  dave
 * final PROJCDNAM fix
 *
 * Revision 1.98  2005/09/13 05:26:39  dave
 * some more trace
 *
 * Revision 1.97  2005/09/13 05:24:40  dave
 * adding PROJCDNAM to xml stream
 *
 * Revision 1.96  2005/09/13 05:20:33  dave
 * ok.. projcdnam is getting close
 *
 * Revision 1.95  2005/09/13 05:10:06  dave
 * ok.. we may have product structure now for WWSEO
 *
 * Revision 1.94  2005/09/13 04:14:31  dave
 * ok.. lets add the PROJCDNAM, and lets take a hard look at
 * WWSEO and how to get prod structures
 *
 * Revision 1.93  2005/09/12 18:16:11  gregg
 * put back old Exceptions
 *
 * Revision 1.92  2005/09/12 15:34:21  gregg
 * add back in m_AttCollection.put's
 *
 * Revision 1.91  2005/09/12 02:59:12  dave
 * temp fix NULL = 'NULL'
 *
 * Revision 1.90  2005/09/09 21:20:16  gregg
 * trying to accept nulls
 *
 * Revision 1.89  2005/09/09 18:53:12  gregg
 * use NO_DATE_VAL
 *
 * Revision 1.88  2005/09/09 15:31:48  gregg
 * small tweaks to  date vals
 *
 * Revision 1.87  2005/09/08 23:22:51  gregg
 * lets try again
 *
 * Revision 1.86  2005/09/08 23:17:31  gregg
 * error tracing
 *
 * Revision 1.85  2005/09/07 19:24:57  gregg
 * moving isActive around
 *
 * Revision 1.84  2005/09/07 18:05:48  gregg
 * 8982 parms
 *
 * Revision 1.83  2005/09/07 17:12:26  gregg
 * zeroing in on 8982 parm problem
 *
 * Revision 1.82  2005/09/07 16:23:28  gregg
 * more param disappearance for 8982
 *
 * Revision 1.81  2005/09/07 15:59:08  gregg
 * more 8982 work
 *
 * Revision 1.80  2005/09/06 22:17:51  gregg
 * 8982 fun
 *
 * Revision 1.79  2005/09/01 22:12:07  gregg
 * trying something
 *
 * Revision 1.78  2005/08/31 00:24:06  gregg
 * syncing fields w/ DDL
 *
 * Revision 1.77  2005/08/30 23:22:46  gregg
 * more fix for insert sp
 *
 * Revision 1.76  2005/08/30 23:12:16  gregg
 * update SP parms
 *
 * Revision 1.75  2005/08/16 16:52:58  tony
 * CatalogViewer
 *
 * Revision 1.74  2005/08/08 20:47:16  tony
 * Added getAttribute logic
 *
 * Revision 1.73  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.72  2005/06/23 01:56:18  dave
 * lets hook up the update of the wwattributes
 *
 * Revision 1.71  2005/06/22 19:48:51  dave
 * more country list
 *
 * Revision 1.70  2005/06/13 18:31:04  dave
 * check in conflict
 *
 * Revision 1.68  2005/06/13 17:59:01  dave
 * need more parms
 *
 * Revision 1.67  2005/06/13 17:51:54  dave
 * adding more fields
 *
 * Revision 1.66  2005/06/13 04:35:34  dave
 * ! needs to be not !
 *
 * Revision 1.65  2005/06/13 04:02:06  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.64  2005/06/12 23:21:24  dave
 * working in WWAttributeLogic
 *
 * Revision 1.63  2005/06/08 13:21:21  dave
 * testing waters to see if structures make sense outside of WWProductId
 *
 * Revision 1.62  2005/06/07 05:28:42  dave
 * more cleanup and NLSid ing
 *
 * Revision 1.61  2005/06/07 04:49:47  dave
 * minor fix to syntax
 *
 * Revision 1.60  2005/06/07 04:34:51  dave
 * working on commit control
 *
 * Revision 1.59  2005/06/04 23:22:30  dave
 * more cleanup
 *
 * Revision 1.58  2005/06/04 23:15:02  dave
 * just cleaning up abit
 *
 * Revision 1.57  2005/06/04 22:45:56  dave
 * Some ProdStructCleanup
 *
 * Revision 1.56  2005/06/04 22:22:57  dave
 * ok.. lets try to dump out the ProdStruct XML
 * for now.. lets embed it in the other XML
 *
 * Revision 1.55  2005/06/04 16:46:05  dave
 * more trace
 *
 * Revision 1.54  2005/06/04 16:22:19  dave
 * formatting...
 *
 * Revision 1.53  2005/06/04 16:21:22  dave
 * ok.. fixed type source vs source type
 *
 * Revision 1.52  2005/06/04 16:12:30  dave
 * more trace is good
 *
 * Revision 1.51  2005/06/03 21:25:51  dave
 * lets start getting prodstruct data
 *
 * Revision 1.50  2005/06/02 08:37:49  dave
 * one more null
 *
 * Revision 1.49  2005/06/02 08:31:52  dave
 * ok no likie nulls
 *
 * Revision 1.48  2005/06/02 08:20:31  dave
 * doing to freeStatementing
 *
 * Revision 1.47  2005/06/02 08:16:19  dave
 * ok.. lets give it a whirl
 *
 * Revision 1.46  2005/06/02 07:29:41  dave
 * _db to db
 *
 * Revision 1.45  2005/06/02 07:28:44  dave
 * ok.. attempting an update start
 *
 * Revision 1.44  2005/06/02 06:09:25  dave
 * ok.. found issue
 *
 * Revision 1.43  2005/06/02 05:46:41  dave
 * more trace.. getting SEOID
 *
 * Revision 1.42  2005/06/02 05:35:11  dave
 * ok.. freeing up variable settings
 *
 * Revision 1.41  2005/06/02 05:17:25  dave
 * trace for debug
 *
 * Revision 1.40  2005/06/02 04:49:55  dave
 * more clean up
 *
 * Revision 1.39  2005/06/01 20:36:41  gregg
 * get/setAttribute
 *
 * Revision 1.38  2005/06/01 06:31:17  dave
 * ok.. lets see if we can do some ProdStruct Damage
 *
 * Revision 1.37  2005/05/29 00:25:30  dave
 * ok.. clean up and reorg to better support pulls from PDH
 *
 * Revision 1.36  2005/05/28 23:25:59  dave
 * ok.. can we spit out xml?
 *
 * Revision 1.35  2005/05/28 19:46:23  dave
 * group by OJBECT_KEY and some dump cleanup
 *
 * Revision 1.34  2005/05/28 19:38:11  dave
 * more null pointer trapping
 *
 * Revision 1.33  2005/05/28 19:29:45  dave
 * MACHTYPE is flag in the model entity
 *
 * Revision 1.32  2005/05/28 19:17:53  dave
 * ok.. lets go for a robust dump statement
 *
 * Revision 1.31  2005/05/27 23:28:19  dave
 * fixing a null pointer
 *
 * Revision 1.30  2005/05/27 23:18:08  dave
 * another fix
 *
 * Revision 1.29  2005/05/27 23:17:23  dave
 * another opportunity
 *
 * Revision 1.28  2005/05/27 23:10:01  dave
 * need to set profile dates
 *
 * Revision 1.27  2005/05/27 22:52:33  dave
 * lets see if we can get something from the PDH
 *
 * Revision 1.26  2005/05/27 21:46:59  dave
 * trying to split it up
 *
 * Revision 1.25  2005/05/27 00:55:17  dave
 * adding the merge method.
 *
 * Revision 1.24  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.23  2005/05/26 00:06:06  dave
 * adding put to design by contract
 *
 * Revision 1.22  2005/05/23 14:14:32  dave
 * adding source, type, and interval sigs to keys
 * adding getGami to Catalog statics
 *
 * Revision 1.21  2005/05/23 01:34:33  dave
 * small test changes to main
 *
 * Revision 1.20  2005/05/23 00:36:10  dave
 * ok.. attempting to build my first collection
 *
 * Revision 1.19  2005/05/22 23:04:36  dave
 * Added CollectionId
 * addind Catalog Interval
 * Placed enterprise in the Gami
 *
 * Revision 1.18  2005/05/20 00:16:32  dave
 * lets go deeper
 *
 * Revision 1.17  2005/05/20 00:03:15  dave
 * more fancy debug
 *
 * Revision 1.16  2005/05/19 23:50:16  dave
 * more dump and view unit testing
 *
 * Revision 1.15  2005/05/19 21:48:13  dave
 * more list trickery
 *
 * Revision 1.14  2005/05/19 21:05:49  dave
 * fixing null pointer
 *
 * Revision 1.13  2005/05/19 20:46:46  dave
 * trimming excess blanks
 *
 * Revision 1.12  2005/05/19 20:35:22  dave
 * testing WWProduct Object
 *
 * Revision 1.11  2005/05/19 20:08:36  dave
 * more trace and debug
 *
 * Revision 1.10  2005/05/19 15:51:34  dave
 * adding Gami to the CatId (needed for everything
 * so i promoted to the key
 *
 * Revision 1.9  2005/05/19 15:23:54  dave
 * catching more exceptions
 *
 * Revision 1.8  2005/05/19 15:06:08  dave
 * adding more structure to see what we have created
 *
 * Revision 1.7  2005/05/19 04:44:49  dave
 * more baseline testing and config
 *
 * Revision 1.6  2005/05/19 03:20:48  dave
 * adding getReference concept and changing DDL abit
 * to remove the not nulls
 *
 * Revision 1.5  2005/05/18 01:29:13  dave
 * more main test
 *
 * Revision 1.4  2005/05/18 01:06:00  dave
 * trying to write a main test
 *
 * Revision 1.3  2005/05/17 23:43:38  dave
 * added logging
 *
 *
 *
 **/
