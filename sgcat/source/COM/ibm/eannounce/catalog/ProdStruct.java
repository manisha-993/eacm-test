/*
 * Created on Mar 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: ProdStruct.java,v $
 * Revision 1.23  2013/06/07 02:48:56  liuweimi
 * New Requirement for PUBFROM in catnet (set pubfrom using plan avail.effectivedate if no announcement)
 *
 * Revision 1.22  2013/06/03 17:08:30  liuweimi
 * bug fix - Pubfrom date - Avail don't have any link to announcement
 *
 * Revision 1.21  2013/05/14 13:02:55  liuweimi
 * Fix PUBFROM PUBTO derivation for XCC based on BH - EACM
 *
 * Revision 1.20  2011/07/15 19:59:30  praveen
 * Increase column length for OSLEVEL
 *
 * Revision 1.19  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.11  2009/07/21 18:57:30  rick
 * change to process avails via OOFAVAIL
 * and SWPRODSTRUCTAVAIL for LSEOs
 *
 * Revision 1.10  2009/03/26 21:31:36  rick
 * change to conform with spec
 * SG FS CATNET Catalog Derivation 20090212.doc
 *
 * Revision 1.9  2008/05/09 04:28:23  yang
 * *** empty log message ***
 *
 * Revision 1.8  2008/04/07 03:26:13  yang
 * *** empty log message ***
 *
 * Revision 1.7  2008/04/07 03:15:52  yang
 * *** empty log message ***
 *
 * Revision 1.6  2008/03/27 12:36:35  rick
 * Changes to support confqty on wwseoswprodstruct,
 * lseoprodstruct,lseoswprodstruct and lseobundlelseo.
 *
 * Revision 1.5  2008/02/28 19:35:19  rick
 * RQ to process pub dates on avails on SWPRODSTRUCTs.
 *
 * Revision 1.4  2008/01/29 05:22:03  rick
 * fix in checkavailpubdates ... pubfrom should be pubto
 *
 * Revision 1.3  2008/01/21 15:17:21  rick
 * Changes for pubfrom/pubto date processing
 *
 * Revision 1.2  2007/10/09 08:11:48  sulin
 * no message
 *
 * Revision 1.1.1.1  2007/06/05 02:09:24  jingb
 * no message
 *
 * Revision 1.16  2007/05/31 16:06:31  rick
 * increase length of oslevel and oslevel_fc
 *
 * Revision 1.15  2006/10/30 18:40:56  gregg
 * move projcdnam to product
 *
 * Revision 1.14  2006/10/26 20:26:00  gregg
 * projcdnam, projcdnam_fc
 *
 * Revision 1.13  2006/09/28 18:22:29  gregg
 * configuratorflag_fc
 *
 * Revision 1.12  2006/06/21 20:31:25  gregg
 * HIPO OSLEVEL case per CR0608066849
 *
 * Revision 1.11  2006/06/07 22:21:33  gregg
 * fixing intersectFlagVals logic
 *
 * Revision 1.10  2006/05/30 20:28:59  gregg
 * closing out latest PUBFROM/PUBTO work for ProdStruct
 *
 * Revision 1.9  2006/05/09 19:50:22  gregg
 * update
 *
 * Revision 1.8  2006/04/07 19:21:33  gregg
 * making compile now
 *
 * Revision 1.7  2006/04/07 18:09:39  gregg
 * Who is this jbInit, and WHY IS HE IN MY CODE?!?!?!
 *
 * Revision 1.6  2006/04/07 18:07:57  gregg
 * blah. jbInit. good times.
 *
 * Revision 1.5  2006/04/07 18:05:23  gregg
 * commit merge problems
 *
 * Revision 1.4  2006/04/04 20:34:28  gregg
 * updated code per WAyne's 033106 spec
 *
 * Revision 1.3  2006/04/04 19:47:56  gregg
 * debug
 *
 * Revision 1.2  2006/03/30 22:42:22  gregg
 * starting work on PRODSTRUCT PUBTO via FEATURE
 *
 * Revision 1.1.1.1  2006/03/30 17:36:30  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.80  2006/03/28 20:30:05  gregg
 * Synced up OSLEVEL w/ Product (I.E. lesson in 4th grade cut+paste)
 *
 * Revision 1.79  2006/03/17 18:41:18  gregg
 * nice little pubto comment.
 *
 * Revision 1.78  2006/03/07 19:32:34  gregg
 * truncate current timetamp for pubto compare
 *
 * Revision 1.77  2006/03/07 18:52:27  gregg
 * some PUBTO formatting and such
 *
 * Revision 1.76  2006/03/06 20:29:52  gregg
 * pubto
 *
 * Revision 1.75  2006/02/28 20:54:42  gregg
 * oslevel cleanup old code
 *
 * Revision 1.74  2006/02/28 20:52:16  gregg
 * cleaning up OSLEVEL population for product, wwproduct, prodstruct:
 * ** Net consensus for OSLEVEL is:
 *        1) LCTO's go to GBLI.PRODUCT table.
 *        2) LSEOBUNDLE's go to GBLI.PRODUCT table.
 *        3) MODEL TMF's (WorldWide) go to GBLI.PRODSTRUCT table.
 *
 * Revision 1.73  2006/02/22 20:17:49  gregg
 * fix for MODEL/OSLEVEL/Software
 *
 * Revision 1.72  2006/02/03 20:08:08  gregg
 * updates to what goes into smc for LSEOBUNDLES
 *
 * Revision 1.71  2006/02/02 22:52:47  gregg
 * lseoprodstruct
 *
 * Revision 1.70  2006/02/01 01:05:07  gregg
 * misc
 *
 * Revision 1.69  2006/01/31 23:11:23  gregg
 * closing out pubfromdate (lets hope)
 *
 * Revision 1.68  2006/01/31 01:49:16  gregg
 * backing out previous change
 *
 * Revision 1.67  2006/01/31 01:28:43  gregg
 * grab PRODSTRUCTS from LSEO/LSEOBUNDLES as well..
 *
 * Revision 1.66  2006/01/31 01:07:30  gregg
 * lets see if we break it... adding Product logic into WWProduct logic in get...hrrm
 *
 * Revision 1.65  2006/01/31 00:52:27  gregg
 * yay more pubfrom
 *
 * Revision 1.64  2006/01/31 00:37:37  gregg
 * gues what... pubform!!
 *
 * Revision 1.63  2006/01/31 00:30:46  gregg
 * more pubfrom goodness
 *
 * Revision 1.62  2006/01/23 23:28:02  gregg
 * prepping for pubfrom/pubto
 *
 * Revision 1.61  2006/01/23 21:11:36  gregg
 * Soring MODELCTLGOR in prodstruct's smc for future pubdate processing
 *
 * Revision 1.60  2006/01/19 20:23:58  gregg
 * -Dragging MODEL atts into prodentitytype of WWSEO.
 * -Check for orphaned WWSEO's w/out bridge entity.
 *
 * Revision 1.59  2006/01/19 18:48:01  gregg
 * some debug stmts
 *
 * Revision 1.58  2006/01/14 00:31:22  gregg
 * update for CR5744 per note on 1/13
 *
 * Revision 1.57  2006/01/13 23:49:30  gregg
 * more OSLEVEL processing per 6/12 spec
 *
 * Revision 1.56  2006/01/13 23:32:20  gregg
 * OSLEVEL processing per 6/12 spec
 *
 * Revision 1.55  2006/01/13 20:57:54  gregg
 * ordercode flagcode straightening
 *
 * Revision 1.54  2006/01/11 22:48:01  gregg
 * cr 5744
 *
 * Revision 1.53  2005/12/21 19:25:33  gregg
 * some null checking for above said fix
 *
 * Revision 1.52  2005/12/21 19:12:49  gregg
 * Strip "*" off of OSLEVEL...Re: a standard way to do this?
 *
 * Revision 1.51  2005/11/09 21:01:56  dave
 * Trace
 *
 * Revision 1.50  2005/11/09 20:55:35  dave
 * adding CONFQTY and defaults
 *
 * Revision 1.49  2005/10/25 16:51:06  gregg
 * SWPRODSTRUCT
 *
 * Revision 1.48  2005/09/12 05:31:18  dave
 * ok.. try again 1 char on order code
 *
 * Revision 1.47  2005/09/12 05:20:40  dave
 * they only want the first character
 *
 * Revision 1.46  2005/09/12 03:35:58  dave
 * fixing prodstruct code so we can start testing IDL
 *
 * Revision 1.45  2005/09/12 02:31:30  dave
 * attempting to catch number format exception
 *
 * Revision 1.44  2005/08/16 16:52:58  tony
 * CatalogViewer
 *
 * Revision 1.43  2005/08/08 20:47:16  tony
 * Added getAttribute logic
 *
 * Revision 1.42  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.41  2005/06/23 21:38:17  roger
 * Remove expansions
 *
 * Revision 1.40  2005/06/23 21:33:33  gregg
 *  import java.util.HashMap;
 *
 * Revision 1.39  2005/06/23 19:12:49  roger
 * With Expando expansion
 *
 * Revision 1.38  2005/06/23 19:11:45  roger
 * An example of using Expando
 *
 * Revision 1.37  2005/06/13 04:35:33  dave
 * ! needs to be not !
 *
 * Revision 1.36  2005/06/13 04:02:06  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.35  2005/06/08 13:21:21  dave
 * testing waters to see if structures make sense outside of WWProductId
 *
 * Revision 1.34  2005/06/07 04:34:51  dave
 * working on commit control
 *
 * Revision 1.33  2005/06/05 03:01:13  dave
 * no need to deal with forever in anndate
 *
 * Revision 1.32  2005/06/05 01:51:51  dave
 * anndate fixes
 *
 * Revision 1.31  2005/06/05 01:12:16  dave
 * avoiding null pointer stuff
 *
 * Revision 1.30  2005/06/05 00:53:49  dave
 * ok.. minor SP Fix
 *
 * Revision 1.29  2005/06/05 00:47:35  dave
 * one to many }
 *
 * Revision 1.28  2005/06/05 00:18:47  dave
 * first attempt at hooking up a ProdStruct Save
 *
 * Revision 1.27  2005/06/04 23:15:02  dave
 * just cleaning up abit
 *
 * Revision 1.26  2005/06/04 22:45:56  dave
 * Some ProdStructCleanup
 *
 * Revision 1.25  2005/06/04 22:22:57  dave
 * ok.. lets try to dump out the ProdStruct XML
 * for now.. lets embed it in the other XML
 *
 * Revision 1.24  2005/06/02 21:24:28  gregg
 * more xml
 *
 * Revision 1.23  2005/06/02 21:02:35  gregg
 * fix XML entity tag
 *
 * Revision 1.22  2005/06/02 20:34:09  gregg
 * fixing the parent/child collection reference mayhem
 *
 * Revision 1.21  2005/06/02 19:52:37  gregg
 * tracking down a null
 *
 * Revision 1.20  2005/06/02 18:34:54  gregg
 * compile, compile, compile
 *
 * Revision 1.19  2005/06/02 18:25:53  gregg
 * some debugs
 *
 * Revision 1.18  2005/06/02 18:01:24  gregg
 * s'more XML + dumps
 *
 * Revision 1.17  2005/06/02 17:21:49  gregg
 * xml stufff
 *
 * Revision 1.16  2005/06/02 04:49:55  dave
 * more clean up
 *
 * Revision 1.15  2005/06/01 20:36:41  gregg
 * get/setAttribute
 *
 * Revision 1.14  2005/06/01 06:39:51  dave
 * fixing a null pointer
 *
 * Revision 1.13  2005/06/01 06:31:17  dave
 * ok.. lets see if we can do some ProdStruct Damage
 *
 * Revision 1.12  2005/05/27 16:18:22  gregg
 * fill out Features w/ get()
 *
 * Revision 1.11  2005/05/27 00:55:17  dave
 * adding the merge method.
 *
 * Revision 1.10  2005/05/26 20:31:07  gregg
 * cleaning up source, type, case CONSTANTS.
 * let's also make sure we check for source in building objects within our collections.
 *
 * Revision 1.9  2005/05/26 20:00:05  gregg
 * fill out get() for FeatureCollection
 *
 * Revision 1.8  2005/05/26 19:50:28  gregg
 * FeatureCollection reference added
 *
 * Revision 1.7  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.6  2005/05/26 00:12:47  gregg
 * update
 *
 * Revision 1.5  2005/05/26 00:08:58  gregg
 * rdrs col index fix
 *
 * Revision 1.4  2005/05/26 00:06:06  dave
 * adding put to design by contract
 *
 * Revision 1.3  2005/05/25 21:04:27  gregg
 * compile fix
 *
 * Revision 1.2  2005/05/25 20:57:39  gregg
 * getFatAndDeep methods to traverse the tree.
 *
 * Revision 1.1  2005/05/25 17:36:46  gregg
 * going for ProdStruct objects
 *
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * This represent a generic product for IBM product internal Catalogues
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProdStruct
    extends CatItem {


    //expando
    //@var int Count xmlTagCount
    //@var String f1 xmlTagf1
    //@var int f2 xmlTagf2
    //@var String f3 xmlTagf3
    //@var HashMap f4 xmlTagf4
    //@xmlgroup SEOFREL xmlTagCount xmlTagf1
    //@xmlgroup TMFREL xmlTagf1 xmlTagf2 xmlTagf4
    //@genvars
    //expando

    private String ORDERCODE = null;
    private String ORDERCODE_FC = null;
    private String OSLEVEL = null;
    private String OSLEVEL_FC = null;
    private String ANNDATE = null;
    private String WITHDRAWDATE = null;
    private String PUBFROMDATE = null;
    private String PUBTODATE = null;
    private int SYSTEMMAX = 254; // Default
    private int SYSTEMMIN = 0; // Default
    private int CONFQTY = 1; // Default
    private String STATUS = null;
    private String STATUS_FC = null;
    private String CONFIGURATORFLAG_FC = null;
    private String HERITAGE = null;
    private String VALFROM = null;
    private String VALTO = null;
    private boolean ISACTIVE = true;
    private String CFGFLAG = null; // For CR072607687
    private String CFGFLAG_FC = null;// For CR072607687
    private String m_PUBFROMDATE_CATLGOR = null; // used to save pubfrom from the CATLGOR
    private String m_PUBTODATE_CATLGOR = null; // used to save pubto from the CATLGOR
    private String m_PUBFROMDATE_AVAIL143 = null; // used to save effectivedate from the first order avail
    private String m_PUBFROMDATE_AVAIL146 = null; // used to save effective date from the planned avaiability avail
    private boolean avail146_found = false;       // used to save whether a planned availability is found
    private String m_PUBFROMDATE_MODELAVAIL146 = null; // used to save effective date from the model planned avaiability avail
    private boolean modelAvail146_found = false;       // used to save whether a model planned availability is found
    private String m_PUBTODATE_AVAIL149 = null; // used to save effective date from the last order avail
    private String m_PUBTODATE_MODELAVAIL149 = null; // used to save effective date from the last order avail
    private String m_PRODSTRUCT_ANNDATE = null;   // used to save prodstruct ANNDATE
    private String m_PRODSTRUCT_WTHDRWEFFCTVDATE = null; // used to save PRODSTRUCT WITHDRWEFFCTVDATE
    private String m_FEATURE_FIRSTANNDATE = null; // used to save feature firstanndate
    private String m_FEATURE_WITHDRAWDATEEFF_T = null;   // used to save feature withdrawdateeff_t
    private String m_FEATURE_FCTYPE = null;    // used to save feature fctype FC
    private boolean featureCountry = false;       // used to save whether a feature countrylist matches catalog country

    private FeatureCollection m_fc = null;

    private SyncMapCollection m_smc = null;

    /**
     */
    public static final String PRODSTRUCT_ORDERCODE = "ORDERCODE";
    /**
     */
    public static final String PRODSTRUCT_OSLEVEL = "OSLEVEL";

    /**
     */
    public static final String PRODSTRUCT_ANNDATE = "ANNDATE";
    /**
     */
    public static final String PRODSTRUCT_WITHDRAWDATE = "WITHDRAWDATE";
    public static final String PRODSTRUCT_WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";
    /**
     */
    public static final String PRODSTRUCT_SYSTEMMAX = "SYSTEMMAX";
    /**
     */
    public static final String PRODSTRUCT_SYSTEMMIN = "SYSTEMMIN";
    /**
     */
    public static final String WWSEOPRODSTRUCT_CONFQTY = "CONFQTY";
    public static final String WWSEOSWPRODSTRUCT_SWCONFQTY = "SWCONFQTY"; // RQK - 03252008
    public static final String LSEOPRODSTRUCT_CONFQTY = "CONFQTY";        // RQK - 03252008
    public static final String LSEOSWPRODSTRUCT_SWCONFQTY = "SWCONFQTY";  // RQK - 03252008
    public static final String LSEOBUNDLELSEO_LSEOQTY = "LSEOQTY";        // RQK - 03252008

    public static final String PRODSTRUCT_PUBFROMDATE = "PUBFROMDATE";
    public static final String PRODSTRUCT_PUBTODATE = "PUBTODATE";

    public static final String FEATURE_WITHDRAWDATE = "WITHDRAWDATEEFF_T";
    public static final String FEATURE_ANNDATE = "FIRSTANNDATE";
    public static final String FEATURE_FCTYPE = "FCTYPE";

    public static final String CONFIGURATORFLAG = "CONFIGURATORFLAG";

    /**
     */
    public static final String PRODSTRUCT_STATUS = "STATUS";

    private static final String PRODSTRUCT_ENTITY_GROUP = "PRODSTRUCT";
    private static final String SWPRODSTRUCT_ENTITY_GROUP = "SWPRODSTRUCT";
    private static final String WWSEOPRODSTRUCT_ENTITY_GROUP = "WWSEOPRODSTRUCT";
    private static final String WWSEOSWPRODSTRUCT_ENTITY_GROUP = "WWSEOSWPRODSTRUCT";  // RQK - 03252008
    private static final String LSEOPRODSTRUCT_ENTITY_GROUP = "LSEOPRODSTRUCT";        // RQK - 03252008
    private static final String LSEOSWPRODSTRUCT_ENTITY_GROUP = "LSEOSWPRODSTRUCT";    // RQK - 03252008
    private static final String LSEOBUNDLELSEO_ENTITY_GROUP = "LSEOBUNDLELSEO";        // RQK - 03252008
    private static final String FEATURE_ENTITY_GROUP = "FEATURE";
    private static final String MODEL_ENTITY_GROUP = "MODEL";
    private static final String CATLGOR_ENTITY_GROUP = "CATLGOR";
    private static final String LSEOBUNDLE_ENTITY_GROUP = "LSEOBUNDLE";
    public static final String ANNOUNCEMENT_ENTITYTYPE = "ANNOUNCEMENT";

    private HashMap m_AttCollection = new HashMap();

	private boolean m_bSoftwareModel = false;
	private boolean m_bHardwareModel = false;
	private boolean m_bSystemModel = false;
	private boolean m_bServiceModel = false;
	private boolean m_bBaseModel = false;
	private boolean m_bOperatingSystem = false;
	private boolean m_bHIPOModel = false;
	private boolean m_bNASubGrpModel = false;

    /**
     * Product lets get the Light
     * @param _cid
     */
    public ProdStruct(ProdStructId _cid) {
        super(_cid);

    }

    // TODO Auto-generated constructor stub

    /**
     * Product - lets get the heavy
     * Version
     *
     * @param _cid
     * @param _cat
     */
    public ProdStruct(ProdStructId _cid, Catalog _cat) {
        this(_cid);
        get(_cat);
    }

    /**
     * main
     *
     * @param args
     *  @author David Bigelow
     */
    public static void main(String[] args) {

    }

    public void get(Catalog _cat) {

        //
        // When we are getting something out of the CatDB
        // We need to pull enterprise out of the ProductID.

        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        Profile prof = _cat.getCatalogProfile();

        ProdStructId psid = getProdStructId();
        ProdStructCollectionId pscid = psid.getProdStructCollectionId();

        if (pscid == null) {
            D.ebug(this, D.EBUG_ERR, "get(): ** ERROR *** psid is null!!");
        }

        GeneralAreaMapItem gami = psid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strProdEntityType = psid.getProdEntityType();
        int iProdEntityId = psid.getProdEntityId();
        String strFeatEntityType = psid.getFeatEntityType();
        int iFeatEntityId = psid.getFeatEntityId();
        String strCountryList = gami.getCountryList();

        featureCountry = Product.isCountryList(_cat,gami,strFeatEntityType,iFeatEntityId);

        int iNLSID = gami.getNLSID();

        D.ebug(this, D.EBUG_DETAIL,
            "get():pscid:isByInteval:" + pscid.isByInterval() + ":isFromPDH: " + pscid.isFromPDH() +
            ":isFullImages:" + pscid.isFullImages());
        try {

            // O.K.. lets see what we got!

            if (pscid.isByInterval() && pscid.isFromPDH()) {
                CatalogInterval cati = pscid.getInterval();
                //
                // o.k. we have a world wide Product that made us.
                //
                if (pscid.hasWorldWideProductId() || pscid.hasProductId()) {

                    WorldWideProductId wwpid = pscid.getWorldWideProductId();

                    // Right now .. we are gong to have to rebuild this guy.. and
                    // any other thing it may be resonsible for
                    // at this point.. we will always need to

                    if (this.getSmc() == null) {
                        System.out.println("Cannot pull out of the PDH since there is no SycnMap for me.");
                        return;
                    }

                    //
                    // Lets set the valon's in the profile for the Catalog to
                    // I am not sure i like setting them here.
                    //

                    prof.setEffOn(cati.getEndDate());
                    prof.setValOn(cati.getEndDate());

                    try {

                        //
                        // Not sure we need to reget Model or WWSEO here
                        // DWB its important to cache these extracts
                        // so we can simply reuse in the memory
                        //

                        EntityItem eiFeat = Catalog.getEntityItem(_cat, psid.getFeatEntityType(), psid.getFeatEntityId());
                        EntityItem eiProdStruct = Catalog.getEntityItem(_cat, psid.getStructEntityType(), psid.getStructEntityId());
                        EntityItem eiProd = Catalog.getEntityItem(_cat, psid.getProdEntityType(), psid.getProdEntityId());

                        this.setAttributes(eiProd);
                        this.setAttributes(eiProdStruct);
                        this.setAttributes(eiFeat);

                        //
                        // Lets get the Bridge (WWSEOPRODSTRUCT
                        //
                        if (psid.hasBridgeEntity()) {
                            D.ebug(this, D.EBUG_DETAIL,"Adding Bridge Entity:" + psid.getBridgeEntityType() + ":" +  psid.getBridgeEntityId());
                            EntityItem eiBridge = Catalog.getEntityItem(_cat, psid.getBridgeEntityType(), psid.getBridgeEntityId());
                            this.setAttributes(eiBridge);
                        } else if(getPRODENTITYTYPE().equals("WWSEO")) {
							// this might mean we have "bad" prodstruct.
							//  i.e. we are have a prodstruct which did NOT come from our WWSEO.
							D.ebug(D.EBUG_WARN,"WARNING: NO BRIDGE FOUND FOR PROD:" + eiProd.getEntityType() + ":" + eiProd.getEntityID() +
							                    ",STRUCT:" + eiProdStruct.getEntityType() + ":" + eiProdStruct.getEntityID() +". SHOULD WE JUMP?");
						}


		                // Per CR5744: take a pass through and find out
		                //             a) if we are on a Software or Service type MODEL
		                //             b) if ORDERCODE is not null
		                //             --> THEN we need to default ORDERCODE to "I"
		                //
		                // changed a bit....1/13/06...as follows...
		                String strORDERCODE = getORDERCODE();
		                if(getPRODENTITYTYPE().equals("MODEL") && (isSoftwareModel() || isServiceModel())) {
							D.ebug(this, D.EBUG_SPEW,"PRODENTITYTYPE == MODEL... checking ORDERCODE...");
							if(strORDERCODE == null || strORDERCODE.equals("")) {
								D.ebug(this, D.EBUG_SPEW,"setORDERCODE(I)");
								setORDERCODE("I");
								setORDERCODE_FC("5957");
							}
						} else if(getPRODENTITYTYPE().equals("WWSEO")) {
							D.ebug(this, D.EBUG_SPEW,"PRODENTITYTYPE == WWSEO... checking ORDERCODE...");
							// let's dig out and set up our MODEL info since we are not doing this for WWSEO's..
							for (int i = 0; i < this.getSmc().getCount(); i++) {
							    SyncMapItem smi = getSmc().get(i);
								    if (smi.getChildType().equals("PRODSTRUCT") || smi.getChildType().equals("SWPRODSTRUCT")) {
									    EntityItem eiMODEL = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
							            this.setAttributes(eiMODEL);
									}
                   				}
                   		    // now we should have these flags set where applicable...
                            if(isSoftwareModel() || isServiceModel() || isHardwareModel()) {
                   			    D.ebug(D.EBUG_SPEW,"Ordercode MODEL found... ");
								if(strORDERCODE == null || strORDERCODE.equals("")) {
									D.ebug(this, D.EBUG_SPEW,"setORDERCODE(B)");
									setORDERCODE("B");
									setORDERCODE_FC("5955");
								}
							}
						}

                        setOSLevels(_cat);

                        //
                        // For LSEOBUNDLEs we need to set attributes for the LSEOBUNDLELSEO relator. This is where the
                        // quantity (LSEOQTY) sits. RQK 03252008
                        //
                        if(getPRODENTITYTYPE().equals("LSEOBUNDLE")) {
							D.ebug(D.EBUG_SPEW,"LSEOBUNDLE...going for LSEOBUNDLELSEO...." + this.getSmc().getCount() + " records");
                    	    for (int i = 0; i < this.getSmc().getCount(); i++) {
                     		    SyncMapItem smi = getSmc().get(i);
                     		    D.ebug(D.EBUG_SPEW,"smiChildType for LSEOBUNDLE:" + smi.getChildType());
                     		    D.ebug(D.EBUG_SPEW,"and here's the smi...:" + smi.toString());
                     		    if(smi.getChildType().equals("LSEOBUNDLELSEO") &&
                     		       smi.getChildTran().equals("ON") &&
                     		       smi.getEntity2Type() == psid.getFeatEntityType() &&
                     		       smi.getEntity2ID() == psid.getFeatEntityId()) {
									D.ebug(D.EBUG_SPEW,"LSEOBUNDLE...going for PRODSTRUCT....here we go...");
                     		        D.ebug(D.EBUG_SPEW, this.getClass().getName() + ".get(): DP1" + smi.toString());
                     		        EntityItem eiPRODSTRUCT = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
                                    this.setAttributes(eiPRODSTRUCT);
                     		    }
                     		}
						}

						setConfiguratorFlag_fc(_cat);
						setcfgflag(_cat); // For CR072607687

                    	//
                    	// CATALOGOVERRIDE IS LAST, YAY!!!
                    	//
                    	for (int i = 0; i < this.getSmc().getCount(); i++) {
                    	    SyncMapItem smi = getSmc().get(i);
                    	    if (smi.getChildType().equals("PRODSTRUCTCATLGOR")
                    	    		|| smi.getChildType().equals("SWPRODSTRCATLGOR")) {
                    	        db.debug(D.EBUG_SPEW, this.getClass().getName() + ".get(): DP1" + smi.toString());
                    	        EntityItem eiCATLGOR = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
                    	        String[] saOFFCOUNTRY = CatDBTableRecord.getAttributeValue(eiCATLGOR, "OFFCOUNTRY");
                    	        db.debug(D.EBUG_SPEW,"Checking CATLGOR OFFCOUNTRY filter " + eiCATLGOR.getKey() +",OFFCOUNTRY=" + saOFFCOUNTRY[0] + ",COUNTRYLIST=" + strCountryList);
                    	        if(strCountryList != null && saOFFCOUNTRY[1] != null && strCountryList.equals(saOFFCOUNTRY[1])) {
									this.setAttributes(eiCATLGOR);
								}
                    	    }
                    	}                    	

                    	// spec change 051506 PUBFROM.PUBTO processing - this has to be at the end because
                    	// it is in context of some other things.
                    	for (int i = 0; i < this.getSmc().getCount(); i++) {
                    	    SyncMapItem smi = getSmc().get(i);
                    	    D.ebug(D.EBUG_SPEW,"rqk - and here's the smi...:" + smi.toString());
                    	    // Adding to process avail pub dates for swprodstruct also for RQ - RQK 02282008
                    	    if(smi.getChildType().equals("PRODSTRUCT") || smi.getChildType().equals("SWPRODSTRUCT")
                               || smi.getChildType().equals("MODELAVAIL")) {
                    	    	db.debug(D.EBUG_SPEW,"Calling checkAVAILPubDates ");
                    	        checkAVAILPubDates(_cat,smi.getChildType(),smi.getChildID());
							}
                    	    if(smi.getChildType().equals("LSEOPRODSTRUCT") || smi.getChildType().equals("LSEOSWPRODSTRUCT")) {
                         	    	db.debug(D.EBUG_SPEW,"Calling checkAVAILPubDates ");
                         	        checkAVAILPubDates(_cat,smi.getEntity2Type(),smi.getEntity2ID());
     						}
					    }

                    }
                    finally {
                     if (getPRODENTITYTYPE().equals("LCTO") || getPRODENTITYTYPE().equals("LSEO")) {

                       D.ebug(D.EBUG_SPEW,"Dates "
                            + " m_PUBFROMDATE_CATLGOR = " + m_PUBFROMDATE_CATLGOR
                            + " m_PUBTODATE_CATLGOR = " + m_PUBTODATE_CATLGOR
                            + " m_PUBFROMDATE_AVAIL143 = " + m_PUBFROMDATE_AVAIL143
                            + " m_PUBFROMDATE_AVAIL146 = " + m_PUBFROMDATE_AVAIL146
                            + " avail146_found = " + avail146_found
                            + " m_PUBFROMDATE_MODELAVAIL146 = " + m_PUBFROMDATE_MODELAVAIL146
                            + " modelAvail146_found = " + modelAvail146_found
                            + " m_PUBTODATE_AVAIL149 = " + m_PUBTODATE_AVAIL149
                            + " m_PUBTODATE_MODELAVAIL149 = " + m_PUBTODATE_MODELAVAIL149
                            + " m_PRODSTRUCT_ANNDATE = " + m_PRODSTRUCT_ANNDATE
                            + " m_PRODSTRUCT_WTHDRWEFFCTVDATE = " + m_PRODSTRUCT_WTHDRWEFFCTVDATE
                            + " m_FEATURE_FIRSTANNDATE = " + m_FEATURE_FIRSTANNDATE
                            + " m_FEATURE_WITHDRAWDATEEFF_T = " + m_FEATURE_WITHDRAWDATEEFF_T
                            + " m_FEATURE_FCTYPE = " + m_FEATURE_FCTYPE);

                       if (getPRODENTITYTYPE().equals("LCTO") && getFEATENTITYTYPE().equals("FEATURE") &&
                    	   m_FEATURE_FCTYPE != null &&
                           (m_FEATURE_FCTYPE.equals("120") || m_FEATURE_FCTYPE.equals("130") ||
                            m_FEATURE_FCTYPE.equals("402"))) {
                    	   if (m_PUBFROMDATE_CATLGOR != null) setPUBFROMDATE(m_PUBFROMDATE_CATLGOR);
                    	   else if (m_PRODSTRUCT_ANNDATE != null) setPUBFROMDATE(m_PRODSTRUCT_ANNDATE);
                          else if (m_PUBFROMDATE_MODELAVAIL146 != null && m_FEATURE_FIRSTANNDATE != null) {
                                 // set max
                                if (m_PUBFROMDATE_MODELAVAIL146.compareTo(m_FEATURE_FIRSTANNDATE) > 0)
                                  setPUBFROMDATE(m_PUBFROMDATE_MODELAVAIL146);
                                 else setPUBFROMDATE(m_FEATURE_FIRSTANNDATE);
	                       }
                               else if (m_PUBFROMDATE_MODELAVAIL146 != null) setPUBFROMDATE(m_PUBFROMDATE_MODELAVAIL146);
                               else if (m_FEATURE_FIRSTANNDATE != null) setPUBFROMDATE(m_FEATURE_FIRSTANNDATE);
                    	   if (m_PUBTODATE_CATLGOR != null) setPUBTODATE(m_PUBTODATE_CATLGOR);
                    	   else if (m_PRODSTRUCT_WTHDRWEFFCTVDATE != null) setPUBTODATE(m_PRODSTRUCT_WTHDRWEFFCTVDATE);
                          else if (m_PUBTODATE_MODELAVAIL149 != null && m_FEATURE_WITHDRAWDATEEFF_T != null) {
                                 if (m_PUBTODATE_MODELAVAIL149.compareTo(m_FEATURE_WITHDRAWDATEEFF_T) < 0)
                                  setPUBTODATE(m_PUBTODATE_MODELAVAIL149);
                                 else setPUBTODATE(m_FEATURE_WITHDRAWDATEEFF_T);
	                       }
                               else if (m_PUBTODATE_MODELAVAIL149 != null) setPUBTODATE(m_PUBTODATE_MODELAVAIL149);
                               else if (m_FEATURE_WITHDRAWDATEEFF_T != null) setPUBTODATE(m_FEATURE_WITHDRAWDATEEFF_T);

                          if (!featureCountry) this.setActive(false);
                          if (getPUBFROMDATE() == null) this.setActive(false);

                       } // end if lcto and fctype
                       else {
                             if (m_PUBFROMDATE_CATLGOR != null) setPUBFROMDATE(m_PUBFROMDATE_CATLGOR);
//                             else if (m_PUBFROMDATE_AVAIL143 != null) setPUBFROMDATE(m_PUBFROMDATE_AVAIL143);
                             else if (m_PUBFROMDATE_AVAIL146 != null) setPUBFROMDATE(m_PUBFROMDATE_AVAIL146);

                             if (m_PUBTODATE_CATLGOR != null) setPUBTODATE(m_PUBTODATE_CATLGOR);
                             else if (m_PUBTODATE_AVAIL149 != null) setPUBTODATE(m_PUBTODATE_AVAIL149);

                             if (!avail146_found) this.setActive(false);
                             if (getPUBFROMDATE() == null) this.setActive(false);
                            }
                     }   // end if lcto or lseo
                    }    	// end finally
                }
            }
            else if (getProdStructCollectionId().isFromCAT()) {
                System.out.println("ProdStruct: isFromCat()");
                rs = db.callGBL4018(new ReturnStatus( -1), strEnterprise, strProdEntityType, iProdEntityId, strFeatEntityType,
                    iFeatEntityId, iNLSID);

                ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);

                for (int i = 0; i < rdrs.getRowCount(); i++) {
                    //setENTERPRISE(rdrs.getColumn(i,0));
                    //setNLSID(rdrs.getColumnInt(i,3));
                    setORDERCODE(rdrs.getColumn(i, 8));
                    setORDERCODE_FC(rdrs.getColumn(i, 9));
                    setOSLEVEL(rdrs.getColumn(i, 10));
                    setOSLEVEL_FC(rdrs.getColumn(i, 11));
                    setANNDATE(rdrs.getColumn(i, 14));
                    setWITHDRAWDATE(rdrs.getColumn(i, 15));
                    setPUBFROMDATE(rdrs.getColumn(i, 14));
                    setPUBTODATE(rdrs.getColumn(i, 15));
                    setSYSTEMMAX(rdrs.getColumnInt(i, 16));
                    setSYSTEMMIN(rdrs.getColumnInt(i, 17));
                    setCONFQTY(rdrs.getColumnInt(i, 18));
                    setSTATUS(rdrs.getColumn(i, 19));
                    setSTATUS_FC(rdrs.getColumn(i, 20));
                    setCONFIGURATORFLAG_FC(rdrs.getColumn(i,21));
                    setVALFROM(rdrs.getColumn(i, 22));
                    setVALTO(rdrs.getColumn(i, 23));
                    setISACTIVE(rdrs.getColumnInt(i, 24) == 1);
                }
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
                if (rs != null) {
                    rs.close();
                    rs = null;
                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }
            }
            catch (SQLException _ex) {
                _ex.printStackTrace();
            }
        }
    }

    /**
     *  (non-Javadoc)
     *
     */
    public void getReferences(Catalog _cat, int _iCase) {
        // fill out references
        int iSource = getProdStructCollectionId().getSource();
        int iType = getProdStructCollectionId().getType();
        FeatureCollectionId fcid = new FeatureCollectionId(getProdStructId(), iSource, iType, null);
        m_fc = new FeatureCollection(fcid);
    }

    /**
     * This is going to fill out ALL Data (fat) and ALL references (deep) _iLvl's deep.
     *
     * @param _cat
     * @param _iLvl
     */
    public void getFatAndDeep(Catalog _cat, int _iLvl) {
        if (_iLvl < 0) {
            return;
        }
        _iLvl--;
        get(_cat);
        getReferences(_cat, -1);

        //
        // This is what we eventually want...but for now let's just fill out direct descendents...
        //
        //if(isFeatureCollectionLoaded()) { // it should be!
        //    getFeatureCollection().getFatAndDeep(db,_iCase,_iLvl);
        //}
        //

        if (isFeatureCollectionLoaded()) { // it should be!
            getFeatureCollection().get(_cat);
            Iterator it = getFeatureCollection().values().iterator();
            while (it.hasNext()) {
                Feature feature = (Feature) it.next();
                feature.get(_cat);
            }
        }
    }

    /**
     * For debugging XML
     */
    public void dumpXML(XMLWriter _xml, boolean _bDeep) {
        generateXMLFragment(_xml);
        if (_bDeep) {
            if (isFeatureCollectionLoaded()) {
                Iterator it = getFeatureCollection().values().iterator();
                while (it.hasNext()) {
                    Feature feature = (Feature) it.next();
                    //feature.dumpXML(_xml,_bDeep);
                    feature.generateXMLFragment(_xml);
                }
            }
        }
    }

    /**
     * isFeatureCollectionLoaded
     *
     * @return
     */
    public boolean isFeatureCollectionLoaded() {
        return (m_fc != null);
    }

    /**
     * getFeatureCollection
     *
     * @return
     */
    public FeatureCollection getFeatureCollection() {
        return m_fc;
    }

    /**
     * getProdStructId
     *
     * @return
     */
    public ProdStructId getProdStructId() {
        return (ProdStructId)super.getId();
    }

    /**
     * Because we want to know how we got here..
     *
     * @return ProdStructCollectionId
     */
    public ProdStructCollectionId getProdStructCollectionId() {
        ProdStructId psid = getProdStructId();
        if (psid != null) {
            return psid.getProdStructCollectionId();
        }
        return null;
    }

    /**
     * setProdStructCollectionId
     *
     * @param _pscid
     */
    //public void setProdStructCollectionId(ProdStructCollectionId _pscid) {
    //    m_pscid = _pscid;
    //}

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer sb = new StringBuffer("[PRODSTRUCT]:");
        if (_bBrief) {
            sb.append(getENTERPRISE() + ",");
            sb.append(getPRODENTITYTYPE() + ",");
            sb.append(getPRODENTITYID() + ",");
            sb.append(getFEATENTITYTYPE() + ",");
            sb.append(getFEATENTITYID() + ",");
            sb.append(getNLSID() + ",");
        }
        else {
            sb.append(getENTERPRISE() + ",");
            sb.append(getPRODENTITYTYPE() + ",");
            sb.append(getPRODENTITYID() + ",");
            sb.append(getFEATENTITYTYPE() + ",");
            sb.append(getFEATENTITYID() + ",");
            sb.append(getNLSID() + ",");
            sb.append(getPRODENTITYTYPE() + ",");
            sb.append(getPRODENTITYID() + ",");
            sb.append(getSTRUCTENTITYTYPE() + ",");
            sb.append(getSTRUCTENTITYID() + ",");
            sb.append(getFEATENTITYTYPE() + ",");
            sb.append(getFEATENTITYID() + ",");
            sb.append(ORDERCODE + ",");
            sb.append(ORDERCODE_FC + ",");
            sb.append(OSLEVEL + ",");
            sb.append(OSLEVEL_FC + ",");
            sb.append(ANNDATE + ",");
            sb.append(WITHDRAWDATE + ",");
            sb.append(PUBFROMDATE + ",");
            sb.append(PUBTODATE + ",");
            sb.append(SYSTEMMAX + ",");
            sb.append(SYSTEMMIN + ",");
            sb.append(CONFQTY + ",");
            sb.append(STATUS + ",");
            sb.append(STATUS_FC + ",");
            sb.append(HERITAGE + ",");
            sb.append(VALFROM + ",");
            sb.append(VALTO + ",");
            sb.append(ISACTIVE);
            if (isFeatureCollectionLoaded()) {
                sb.append("\n                " + getFeatureCollection().dump(false));
            }
        }

        return sb.toString();
    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return dump(true);
    }

    //
    // Getters
    //
    /**
     * getENTERPRISE
     *
     * @return
     */
    public final String getENTERPRISE() {
        //return ENTERPRISE;
        return getId().getGami().getEnterprise();
    }

    /**
     * getCOUNTRYCODE
     *
     * @return
     */
    public final String getCOUNTRYCODE() {
        return getId().getGami().getLanguage();
    }

    /**
     * getLANGUAGECODE
     *
     * @return
     */
    public final String getLANGUAGECODE() {
        return getId().getGami().getLanguage();
    }

    /**
     * getNLSID
     *
     * @return
     */
    public final int getNLSID() {
        //return NLSID;
        return getId().getGami().getNLSID();
    }

    /**
     * getPRODENTITYTYPE
     *
     * @return
     */
    public final String getPRODENTITYTYPE() {
        if (this.getProdStructId() != null) {
            return this.getProdStructId().getProdEntityType();
        }
        return "";
    }

    /**
     * getPRODENTITYID
     *
     * @return
     */
    public final int getPRODENTITYID() {
        if (this.getProdStructId() != null) {
            return this.getProdStructId().getProdEntityId();
        }
        return -1;
    }

    public final String getSTRUCTENTITYTYPE() {
        if (this.getProdStructId() != null) {
            return this.getProdStructId().getStructEntityType();
        }
        return "";
    }

    public final int getSTRUCTENTITYID() {
        if (this.getProdStructId() != null) {
            return this.getProdStructId().getStructEntityId();
        }
        return -1;
    }

    /**
     * getFEATENTITYTYPE
     *
     * @return
     */
    public final String getFEATENTITYTYPE() {
        if (this.getProdStructId() != null) {
            return this.getProdStructId().getFeatEntityType();
        }
        return "";
    }

    /**
     * getFEATENTITYID
     *
     * @return
     */
    public final int getFEATENTITYID() {
        if (this.getProdStructId() != null) {
            return this.getProdStructId().getFeatEntityId();
        }
        return -1;
    }

    /**
     * getORDERCODE
     *
     * @return
     */
    public final String getORDERCODE() {
        return (ORDERCODE == null ? "" : ORDERCODE);
    }

    /**
     * getORDERCODE_FC
     *
     * @return
     */
    public final String getORDERCODE_FC() {
        return (ORDERCODE_FC == null ? "" : ORDERCODE_FC);
    }

    /**
     * getOSLEVEL
     *
     * @return
     */
    public final String getOSLEVEL() {
      if (OSLEVEL != null && OSLEVEL.length() > 13400) {
	OSLEVEL = OSLEVEL.substring(0,13399);
      }
        return (OSLEVEL == null ? "" : OSLEVEL);
    }

    /**
     * getOSLEVEL_FC
     *
     * @return
     */
    public final String getOSLEVEL_FC() {
        return (OSLEVEL_FC == null ? "" : OSLEVEL_FC);
    }

    /**
     * getANNDATE
     *
     * @return
     */
    public final String getANNDATE() {
        return (ANNDATE == null ? "NULL" : ANNDATE);
    }

    /**
     * getWITHDRAWDATE
     *
     * @return
     */
    public final String getWITHDRAWDATE() {
        return (WITHDRAWDATE == null ? "NULL" : WITHDRAWDATE);
    }

    /**
     * getPUBFROMDATE
     *
     * @return
     */
    // Changed to return null, as this is what is checked for throughout program.
    public final String getPUBFROMDATE() {
        return (PUBFROMDATE == null ? null : PUBFROMDATE);
    }

    /**
     * getPUBTODATE
     *
     * @return
     */
    //  Changed to return null, as this is what is checked for throughout program.
    public final String getPUBTODATE() {
        return (PUBTODATE == null ? null : PUBTODATE);
    }

    /**
     * getSYSTEMMAX
     *
     * @return
     */
    public final int getSYSTEMMAX() {
        return SYSTEMMAX;
    }

    /**
     * getSYSTEMMIN
     *
     * @return
     */
    public final int getSYSTEMMIN() {
        return SYSTEMMIN;
    }

    /**
     * getWWSEOFCQTY
     *
     * @return
     */
    public final int getCONFTQY() {
        return CONFQTY;
    }

    /**
     * getSTATUS
     *
     * @return
     */
    public final String getSTATUS() {
        return (STATUS == null ? "" : STATUS);
    }

    /**
     * getSTATUS_FC
     *
     * @return
     */
    public final String getSTATUS_FC() {
        return (STATUS_FC == null ? "" : STATUS_FC);
    }

    /**
     * getCONFIGURATORFLAG_FC
     *
     * @return
     */
    public final String getCONFIGURATORFLAG_FC() {
        return (CONFIGURATORFLAG_FC == null ? "" : CONFIGURATORFLAG_FC);
    }
    /**
     * getCFGFLAG FOR CR072607687
     *
     * @return
     */
    public final String getCFGFLAG() {
        return (CFGFLAG == null ? "" : CFGFLAG);
    }
    /**
     * CFGFLAG_FC FOR CR072607687
     *
     * @return
     */
    public final String getCFGFLAG_FC() {
        return (CFGFLAG_FC == null ? "" : CFGFLAG_FC);
    }
    /**
     * getVALFROM
     *
     * @return
     */
    public final String getVALFROM() {
        return (VALFROM == null ? "" : VALFROM);
    }

    /**
     * getVALTO
     *
     * @return
     */
    public final String getVALTO() {
        return (VALTO == null ? "" : VALTO);
    }

    /**
     * getISACTIVE
     *
     * @return
     */
    public final boolean getISACTIVE() {
        return ISACTIVE;
    }

    /**
     * setORDERCODE
     * If a value is passed, we only want the first Character
     *
     * @param _s
     */
    public final void setORDERCODE(String _s) {
        if (_s != null) {
            ORDERCODE = _s.substring(0, 1);
        }
        else {
            ORDERCODE = _s;
        }
        m_AttCollection.put(PRODSTRUCT_ORDERCODE, _s);
    }

    /**
     * setORDERCODE_FC
     *
     * @param _s
     */
    public final void setORDERCODE_FC(String _s) {
        ORDERCODE_FC = _s;
    }

    /**
     * setOSLEVEL
     *
     * @param _s
     */
    public final void setOSLEVEL(String _s) {
        OSLEVEL = _s;
        m_AttCollection.put(PRODSTRUCT_OSLEVEL, _s);
    }

    /**
     * setOSLEVEL_FC
     *
     * @param _s
     */
    public final void setOSLEVEL_FC(String _s) {
        OSLEVEL_FC = _s;
    }

    /**
     * setANNDATE
     *
     * @param _s
     */
    public final void setANNDATE(String _s) {
        ANNDATE = _s;
        m_AttCollection.put(PRODSTRUCT_ANNDATE, _s);
    }

    /**
     * setWITHDRAWDATE
     *
     * @param _s
     */
    public final void setWITHDRAWDATE(String _s) {
        WITHDRAWDATE = _s;
        m_AttCollection.put(PRODSTRUCT_WITHDRAWDATE, _s);
    }

    /**
     * setPUBFROMDATE
     *
     * @param _s
     */
    public final void setPUBFROMDATE(String _s) {
        PUBFROMDATE = _s;
        m_AttCollection.put(PRODSTRUCT_PUBFROMDATE, _s);
    }

    /**
     * setPUBTODATE
     *
     * @param _s
     */
    public final void setPUBTODATE(String _s) {
        PUBTODATE = _s;
        m_AttCollection.put(PRODSTRUCT_PUBTODATE, _s);
    }

    /**
     * setSYSTEMMAX
     *
     * @param _i
     */
    public final void setSYSTEMMAX(int _i) {
        SYSTEMMAX = _i;
        m_AttCollection.put(PRODSTRUCT_SYSTEMMAX, new Integer(_i));
    }

    /**
     * setSYSTEMMIN
     *
     * @param _i
     */
    public final void setSYSTEMMIN(int _i) {
        SYSTEMMIN = _i;
        m_AttCollection.put(PRODSTRUCT_SYSTEMMIN, new Integer(_i));
    }

    /**
     * setCONFQTY
     *
     * @param _i
     */
    public final void setCONFQTY(int _i) {
        CONFQTY = _i;
    }

    /**
     * setSTATUS
     *
     * @param _s
     */
    public final void setSTATUS(String _s) {
        STATUS = _s;
    }

    /**
     * setCONFIGURATORFLAG_FC
     *
     * @param _s
     */
    public final void setCONFIGURATORFLAG_FC(String _s) {
        CONFIGURATORFLAG_FC = _s;
    }
    /**
     * setCFGFLAG  FOR CR072607687
     *
     * @param _s
     */
    public final void setCFGFLAG(String _s) {
    	CFGFLAG = _s;
    }
    /**
     * setCFGFLAG_FC  FOR CR072607687
     *
     * @param _s
     */
    public final void setCFGFLAG_FC(String _s) {
    	CFGFLAG_FC = _s;
    }
    /**
     * setSTATUS_FC
     *
     * @param _s
     */
    public final void setSTATUS_FC(String _s) {
        STATUS_FC = _s;
    }

    /**
     * setVALFROM
     *
     * @param _s
     */
    public final void setVALFROM(String _s) {
        VALFROM = _s;
    }

    /**
     * setVALTO
     *
     * @param _s
     */
    public final void setVALTO(String _s) {
        VALTO = _s;
    }

    /**
     * setISACTIVE
     *
     * @param _b
     */
    public final void setISACTIVE(boolean _b) {
        ISACTIVE = _b;
    }

    /////////////////
    // XML writers //
    /////////////////

    /**
     * xmlPRODENTITYTYPE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlPRODENTITYTYPE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PRODENTITYTYPE");
        _xml.write(getPRODENTITYTYPE());
        _xml.endEntity();
    }

    /**
     * xmlPRODENTITYID
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlPRODENTITYID(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PRODENTITYID");
        _xml.write(String.valueOf(getPRODENTITYID()));
        _xml.endEntity();
    }

    /**
     * xmlFEATENTITYTYPE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFEATENTITYTYPE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FEATENTITYTYPE");
        _xml.write(getFEATENTITYTYPE());
        _xml.endEntity();
    }

    /**
     * xmlFEATENTITYID
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlFEATENTITYID(XMLWriter _xml) throws Exception {
        _xml.writeEntity("FEATENTITYID");
        _xml.write(String.valueOf(getFEATENTITYID()));
        _xml.endEntity();
    }

    /**
     * xmlORDERCODE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlORDERCODE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("ORDERCODE");
        _xml.write(ORDERCODE);
        _xml.endEntity();
    }

    /**
     * xmlOSLEVEL
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlOSLEVEL(XMLWriter _xml) throws Exception {
        _xml.writeEntity("OSLEVEL");
        _xml.write(OSLEVEL);
        _xml.endEntity();
    }

    /**
     * xmlANNDATE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlANNDATE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("ANNDATE");
        _xml.write(ANNDATE);
        _xml.endEntity();
    }

    /**
     * xmlPUBFROMDATE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlPUBFROMDATE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PUBFROMDATE");
        _xml.write(PUBFROMDATE);
        _xml.endEntity();
    }

    /**
     * xmlPUBTODATE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlPUBTODATE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PUBTODATE");
        _xml.write(PUBTODATE);
        _xml.endEntity();
    }

    /**
     * xmlWITHDRAWDATE
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlWITHDRAWDATE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("WITHDRAWDATE");
        _xml.write(WITHDRAWDATE);
        _xml.endEntity();
    }

    /**
     * xmlSYSTEMMAX
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlSYSTEMMAX(XMLWriter _xml) throws Exception {
        _xml.writeEntity("SYSTEMMAX");
        _xml.write(String.valueOf(SYSTEMMAX));
        _xml.endEntity();
    }

    /**
     * xmlSYSTEMMIN
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlSYSTEMMIN(XMLWriter _xml) throws Exception {
        _xml.writeEntity("SYSTEMMIN");
        _xml.write(String.valueOf(SYSTEMMIN));
        _xml.endEntity();
    }

    /**
     * xmlCONFQTY
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlCONFQTY(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CONFQTY");
        _xml.write(String.valueOf(CONFQTY));
        _xml.endEntity();
    }

    /**
     * xmlSTATUS
     *
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlSTATUS(XMLWriter _xml) throws Exception {
        _xml.writeEntity("STATUS");
        _xml.write(STATUS);
        _xml.endEntity();
    }

    /**
     * xmlVALFROM
     *
     * @param _xml
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
     * @param _xml
     * @throws java.lang.Exception
     */
    public void xmlVALTO(XMLWriter _xml) throws Exception {
        _xml.writeEntity("VALTO");
        _xml.write(VALTO);
        _xml.endEntity();
    }

    public void xmlPARENTID(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PARENTID");
        _xml.write(getPRODENTITYTYPE() + getPRODENTITYID());
        _xml.endEntity();
    }

    public void xmlCHILDID(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CHILDID");
        _xml.write(getFEATENTITYTYPE() + getFEATENTITYID());
        _xml.endEntity();
    }

    public void xmlPARENTPRODCLASS(XMLWriter _xml) throws Exception {
        ProdStructId psid = this.getProdStructId();
        ProdStructCollectionId pscid = psid.getProdStructCollectionId();
        WorldWideProductId wwpid = pscid.getWorldWideProductId();
        ProductId pid = pscid.getProductId();

        if (wwpid != null) {
            if (wwpid.isSEO()) {
                _xml.writeEntity("PARENDPRODCLASS");
                _xml.write("SEO");
                _xml.endEntity();
            }
            else {
                _xml.writeEntity("PARENDPRODCLASS");
                _xml.write("TMF");
                _xml.endEntity();
            }
        }
    }

    /**
     * This generates an XMLFragment per the XML Interface
     *
     * Over the time, we can make new getXXXXX's and pass the
     * xml object to them and they will generate their own fragements
     * @param _xml
     */
    public void generateXMLFragment(XMLWriter _xml) {

        ProdStructId psid = this.getProdStructId();
        ProdStructCollectionId pscid = psid.getProdStructCollectionId();
        WorldWideProductId wwpid = pscid.getWorldWideProductId();
        ProductId pid = pscid.getProductId();

        //
        // O.K.  did we come from a WW Product?
        //
        try {
            if (wwpid != null) {
                if (wwpid.isSEO()) {
                    _xml.writeEntity("SEOFREL");
                    this.xmlPARENTID(_xml);
                    this.xmlCHILDID(_xml);
                    this.xmlORDERCODE(_xml);
                    this.xmlOSLEVEL(_xml);
                    this.xmlANNDATE(_xml);
                    this.xmlWITHDRAWDATE(_xml);
                    this.xmlPUBFROMDATE(_xml);
                    this.xmlPUBTODATE(_xml);
                    this.xmlSYSTEMMAX(_xml);
                    this.xmlSYSTEMMIN(_xml);
                    _xml.endEntity();
                }
                else {
                    _xml.writeEntity("TMFREL");
                    this.xmlPARENTID(_xml);
                    this.xmlCHILDID(_xml);
                    this.xmlORDERCODE(_xml);
                    this.xmlOSLEVEL(_xml);
                    this.xmlANNDATE(_xml);
                    this.xmlWITHDRAWDATE(_xml);
                    this.xmlPUBFROMDATE(_xml);
                    this.xmlPUBTODATE(_xml);
                    this.xmlSYSTEMMAX(_xml);
                    this.xmlSYSTEMMIN(_xml);
                    _xml.endEntity();
                }
            }
            else {
                //
                //  We must have came from a Localized product
                //
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /////////////////
    /////////////////

    /**
     *  (non-Javadoc)
     *
     */
    public void put(Catalog _cat, boolean _bcommit) {

        if (Catalog.isDryRun()) {
            return;
        }

        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        ProdStructId psid = this.getProdStructId();
        GeneralAreaMapItem gami = psid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        int iNLSID = gami.getNLSID();


        D.ebug(this, D.EBUG_DETAIL, "BEGIN CALL GBLI8981"
				+ " CONFIGURATORFLAG_FC=" + getCONFIGURATORFLAG_FC()
				+ " LENGTH OF CONFIGURATORFLAG_FC=" + getCONFIGURATORFLAG_FC().length()
				+ " CFGFLAG=" + getCFGFLAG()
				+ " CFGFLAG_FC=" + getCFGFLAG_FC());
        try {
            db.callGBL8981(rets, strEnterprise, strCountryCode, strLanguageCode, iNLSID, this.getPRODENTITYTYPE(),
                this.getPRODENTITYID(), this.getSTRUCTENTITYTYPE(), this.getSTRUCTENTITYID(), this.getFEATENTITYTYPE(),
                this.getFEATENTITYID(), this.getORDERCODE(), this.getORDERCODE_FC(), this.getOSLEVEL(), this.getOSLEVEL_FC(),
                (this.getANNDATE() == null ? "NULL" : this.getANNDATE()),
                (this.getWITHDRAWDATE() == null ? "NULL" : this.getWITHDRAWDATE()),
                (this.getPUBFROMDATE() == null ? "NULL" : this.getPUBFROMDATE()),
                (this.getPUBTODATE() == null ? "NULL" : this.getPUBTODATE()), this.getSYSTEMMAX(), this.getSYSTEMMIN(),
                this.getCONFTQY(), this.getSTATUS(), this.getSTATUS_FC(),
                this.getCONFIGURATORFLAG_FC(),this.getCFGFLAG(),this.getCFGFLAG_FC(),
                (this.isActive() ? 1 : 0));

            if (_bcommit) {
                db.commit();
            }
            db.freeStatement();
            db.isPending();

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
     * sets all the attributes it can given the EntityItem that was passed
     * This handles Model EntityItems
     *
     * This method
     * @param _ei
     */
    public void setAttributes(EntityItem _ei) {

        D.ebug(D.EBUG_SPEW,"setAttributes for:" + _ei.getKey());

        Profile prof = _ei.getProfile();
        String strNow = prof.getValOn();

        //
        // OK.. lets look at Model Stuff first
        // This is where we get all smart and derive rules based upon the data coming
        // in
        //
        if (_ei.getEntityType().equals(WorldWideProduct.TMF_ENTITY_GROUP)) {
			// For CR5744, we need to track which type of Model this is...
				String[] saCOFCAT = CatDBTableRecord.getAttributeValue(_ei,"COFCAT");
				D.ebug(D.EBUG_SPEW,"sa[1] for COFCAT:" + saCOFCAT[1]);
				String[] saCOFSUBCAT = CatDBTableRecord.getAttributeValue(_ei,"COFSUBCAT");
				D.ebug(D.EBUG_SPEW,"sa[1] for COFSUBCAT:" + saCOFSUBCAT[1]);
				String[] saCOFGRP = CatDBTableRecord.getAttributeValue(_ei,"COFGRP");
				D.ebug(D.EBUG_SPEW,"sa[1] for COFGRP:" + saCOFGRP[1]);
				String[] saCOFSUBGRP = CatDBTableRecord.getAttributeValue(_ei,"COFSUBGRP");
				D.ebug(D.EBUG_SPEW,"sa[1] for COFSUBGRP:" + saCOFSUBGRP[1]);
				if(saCOFCAT[1] != null && saCOFCAT[1].equals("101")) { // Software
				    setSoftwareModel(true);
                    String[] saAPPLICATIONTYPE = CatDBTableRecord.getAttributeValue(_ei, "APPLICATIONTYPE");
                    D.ebug(D.EBUG_SPEW, "sa[1] for APPLICATIONTYPE:" + saAPPLICATIONTYPE[1]);
                    if (saAPPLICATIONTYPE[1] != null && saAPPLICATIONTYPE[1].equals("33")) { // now for OperatingSystem only!
                        setOperatingSystem(true);
                    }
				}
				if(saCOFCAT[1] != null && saCOFCAT[1].equals("100")) { // Hardware
				    setHardwareModel(true);
				}
				if(saCOFSUBCAT[1] != null && saCOFSUBCAT[1].equals("126")) { // System
				    setSystemModel(true);
				}
				if(saCOFCAT[1] != null && saCOFCAT[1].equals("102")) { // Service
				    setServiceModel(true);
				}
				if(saCOFGRP[1] != null && saCOFGRP[1].equals("150")) { // Base
				    setBaseModel(true);
				}
				if(saCOFSUBCAT[1] != null && saCOFSUBCAT[1].equals("125")) { // HIPO
				    setHIPOModel(true);
				}
				if(saCOFSUBGRP[1] != null && saCOFSUBGRP[1].equals("010")) { // N/A
				    setNASubGrpModel(true);
				}
        }
        else if (_ei.getEntityType().equals(WorldWideProduct.SEO_ENTITY_GROUP)) {
        }
        else if (_ei.getEntityType().equals(ProdStruct.WWSEOPRODSTRUCT_ENTITY_GROUP)) {
            // CONFQTY ANNDATE
            EANTextAttribute taWWSEOPRODSTRUCT_CONFQTY = (EANTextAttribute) _ei.getAttribute(this.WWSEOPRODSTRUCT_CONFQTY);
            if (taWWSEOPRODSTRUCT_CONFQTY != null) {
                try {
                    this.setCONFQTY(Integer.parseInt(taWWSEOPRODSTRUCT_CONFQTY.toString()));
                }
                catch (NumberFormatException ex) {
                    System.out.println("Number format exception for: CONFQTY: " + taWWSEOPRODSTRUCT_CONFQTY.toString());
                }
            }
        }
        else if (_ei.getEntityType().equals(ProdStruct.WWSEOSWPRODSTRUCT_ENTITY_GROUP)) {
            // WWSEO SWCONFQTY - RQK 03182008
            EANTextAttribute taWWSEOSWPRODSTRUCT_SWCONFQTY = (EANTextAttribute) _ei.getAttribute(this.WWSEOSWPRODSTRUCT_SWCONFQTY);
            if (taWWSEOSWPRODSTRUCT_SWCONFQTY != null) {
                try {
                    this.setCONFQTY(Integer.parseInt(taWWSEOSWPRODSTRUCT_SWCONFQTY.toString()));
                }
                catch (NumberFormatException ex) {
                    System.out.println("Number format exception for: SWCONFQTY: " + taWWSEOSWPRODSTRUCT_SWCONFQTY.toString());
                }
            }
        }
        else if (_ei.getEntityType().equals(ProdStruct.LSEOPRODSTRUCT_ENTITY_GROUP)) {
            // LSEO CONFQTY - RQK 03182008
            EANTextAttribute taLSEOPRODSTRUCT_CONFQTY = (EANTextAttribute) _ei.getAttribute(this.LSEOPRODSTRUCT_CONFQTY);
            if (taLSEOPRODSTRUCT_CONFQTY != null) {
               try {
                   this.setCONFQTY(Integer.parseInt(taLSEOPRODSTRUCT_CONFQTY.toString()));
               }
               catch (NumberFormatException ex) {
                   System.out.println("Number format exception for: CONFQTY: " + taLSEOPRODSTRUCT_CONFQTY.toString());
               }
           }
        }
        else if (_ei.getEntityType().equals(ProdStruct.LSEOSWPRODSTRUCT_ENTITY_GROUP)) {
            // LSEO SW SWCONFQTY - RQK 03182008
            EANTextAttribute taLSEOSWPRODSTRUCT_SWCONFQTY = (EANTextAttribute) _ei.getAttribute(this.LSEOSWPRODSTRUCT_SWCONFQTY);
            if (taLSEOSWPRODSTRUCT_SWCONFQTY != null) {
                try {
                    this.setCONFQTY(Integer.parseInt(taLSEOSWPRODSTRUCT_SWCONFQTY.toString()));
                }
                catch (NumberFormatException ex) {
                    System.out.println("Number format exception for: SWCONFQTY: " + taLSEOSWPRODSTRUCT_SWCONFQTY.toString());
                }
            }
        }
        else if (_ei.getEntityType().equals(ProdStruct.LSEOBUNDLELSEO_ENTITY_GROUP)) {
            // LSEOBUNDLELSEO LSEOQTY - RQK 03182008
            EANTextAttribute taLSEOBUNDLELSEO_LSEOQTY = (EANTextAttribute) _ei.getAttribute(this.LSEOBUNDLELSEO_LSEOQTY);
            if (taLSEOBUNDLELSEO_LSEOQTY != null) {
                try {
                    this.setCONFQTY(Integer.parseInt(taLSEOBUNDLELSEO_LSEOQTY.toString()));
                }
                catch (NumberFormatException ex) {
                    System.out.println("Number format exception for: LSEOQTY: " + taLSEOBUNDLELSEO_LSEOQTY.toString());
                }
            }
        }
        else if (_ei.getEntityType().equals(ProdStruct.FEATURE_ENTITY_GROUP)) {

            // FEATURE WITHDRAWDATE - also new (033106) PUBTO logic
            EANTextAttribute taFEATURE_WITHDRAWDATE = (EANTextAttribute) _ei.getAttribute(FEATURE_WITHDRAWDATE);
            if (taFEATURE_WITHDRAWDATE != null) {
				String strWITHDRAWDATE = taFEATURE_WITHDRAWDATE.toString();
                m_FEATURE_WITHDRAWDATEEFF_T = strWITHDRAWDATE;

				// THIS for FEATURE too?
                this.setWITHDRAWDATE( strWITHDRAWDATE);

                //String strNowFormatted = CalendarAdjust.DBTimestampToTruncatedTimestamp(strNow);
                //D.ebug(D.EBUG_SPEW,"FEATURE.WITHDRAWDATEEFF_T is:" + strWITHDRAWDATE + ",strNow is:" + strNowFormatted);
                //if(strWITHDRAWDATE.compareTo(strNowFormatted) <= 0) {
				//	setPUBTODATE(strWITHDRAWDATE);
				//}
            }
            EANTextAttribute taFEATURE_FIRSTANNDATE = (EANTextAttribute) _ei.getAttribute(FEATURE_ANNDATE);
            if (taFEATURE_FIRSTANNDATE != null) m_FEATURE_FIRSTANNDATE = taFEATURE_FIRSTANNDATE.toString();
            EANFlagAttribute faFEATURE_FCTYPE = (EANFlagAttribute) _ei.getAttribute(FEATURE_FCTYPE);
            if (faFEATURE_FCTYPE != null) m_FEATURE_FCTYPE = faFEATURE_FCTYPE.getFirstActiveFlagCode();

        }
        else if (_ei.getEntityType().equals(ProdStruct.PRODSTRUCT_ENTITY_GROUP) ||
                 _ei.getEntityType().equals(ProdStruct.SWPRODSTRUCT_ENTITY_GROUP)) {

            // PRODSTRUCT ORDERCODE
            EANFlagAttribute faPRODSTRUCT_ORDERCODE = (EANFlagAttribute) _ei.getAttribute(PRODSTRUCT_ORDERCODE);
            if (faPRODSTRUCT_ORDERCODE != null) {
                this.setORDERCODE(faPRODSTRUCT_ORDERCODE.toString());
                this.setORDERCODE_FC(faPRODSTRUCT_ORDERCODE.getFirstActiveFlagCode());
            }

            // PRODSTRUCT OSLEVEL
            // Does this Go Away???
            //EANFlagAttribute faPRODSTRUCT_OSLEVEL = (EANFlagAttribute) _ei.getAttribute(PRODSTRUCT_OSLEVEL);
            //if (faPRODSTRUCT_OSLEVEL != null) {
			//	String strOSLEVEL = faPRODSTRUCT_OSLEVEL.toString();
            //    if(strOSLEVEL != null && strOSLEVEL.length() > 1 && strOSLEVEL.startsWith("*")) {
            //        strOSLEVEL = strOSLEVEL.substring(2);
            //    }
            //    this.setOSLEVEL(strOSLEVEL);
            //    this.setOSLEVEL_FC(faPRODSTRUCT_OSLEVEL.getFirstActiveFlagCode());
            //}

            // PRODSTRUCT ANNDATE
            EANTextAttribute taPRODSTRUCT_ANNDATE = (EANTextAttribute) _ei.getAttribute(PRODSTRUCT_ANNDATE);
            D.ebug(D.EBUG_SPEW,"checking PRODSTRUCT.ANNDATE ... ");
            if (taPRODSTRUCT_ANNDATE != null) {
	        D.ebug(D.EBUG_SPEW,"PRODSTRUCT.ANNDATE is:" + taPRODSTRUCT_ANNDATE.toString());
                this.setANNDATE( (taPRODSTRUCT_ANNDATE.toString()));
				// 4.	PRODSTRUCT: PUBFROM
				// The greater (later) of:
				// PRODSTRUCT.ANNDATE
				// PRODSTRUCT:CATLGOR.PUBFROM where the catalog country = CATLGOR.OFFCOUNTRY
                m_PRODSTRUCT_ANNDATE = taPRODSTRUCT_ANNDATE.toString();
                // if(_ei.getEntityType().equals(ProdStruct.PRODSTRUCT_ENTITY_GROUP)) {
                //	this.setPUBFROMDATE(taPRODSTRUCT_ANNDATE.toString());
		//		}
            }

            // PRODSTRUCT WITHDRAWDATE
            EANTextAttribute taPRODSTRUCT_WITHDRAWDATE = (EANTextAttribute) _ei.getAttribute(PRODSTRUCT_WITHDRAWDATE);
            if (taPRODSTRUCT_WITHDRAWDATE != null) {
				String strWITHDRAWDATE = taPRODSTRUCT_WITHDRAWDATE.toString();
                this.setWITHDRAWDATE( strWITHDRAWDATE);
            }
            EANTextAttribute taPRODSTRUCT_WTHDRWEFFCTVDATE = (EANTextAttribute) _ei.getAttribute(PRODSTRUCT_WTHDRWEFFCTVDATE);
            if (taPRODSTRUCT_WTHDRWEFFCTVDATE != null) m_PRODSTRUCT_WTHDRWEFFCTVDATE = taPRODSTRUCT_WTHDRWEFFCTVDATE.toString();
                // PUBTO
                 //WITHDRAWDATE in form 2005-12-31
                 //strNow is: 2005-12-31-00.00.00.000000"

                //I am commenting out the enforcement that withdrawdate is less or equal to current date before setting
                //into pubtodate. If withdrawdate is set on the prodstruct it needs to be set into the pubtodate
                // regardless of the value. RQK 1/17/2008

                //String strNowFormatted = CalendarAdjust.DBTimestampToTruncatedTimestamp(strNow);
                //D.ebug(D.EBUG_SPEW,"PRODSTRUCT.WITHDRAWDATE is:" + strWITHDRAWDATE + ",strNow is:" + strNowFormatted);
                //if(strWITHDRAWDATE.compareTo(strNowFormatted) <= 0) {
		//			setPUBTODATE(strWITHDRAWDATE);
				//}



            // PRODSTRUCT SYSTEMMAX
            EANTextAttribute taPRODSTRUCT_SYSTEMMAX = (EANTextAttribute) _ei.getAttribute(PRODSTRUCT_SYSTEMMAX);
            if (taPRODSTRUCT_SYSTEMMAX != null) {
                try {
                    this.setSYSTEMMAX( (Integer.parseInt(taPRODSTRUCT_SYSTEMMAX.toString())));
                }
                catch (NumberFormatException ex) {
                    System.out.println("Number format exception for: SystemMax: " + taPRODSTRUCT_SYSTEMMAX.toString());
                }
            }

            // PRODSTRUCT SYSTEMMIN
            EANTextAttribute taPRODSTRUCT_SYSTEMMIN = (EANTextAttribute) _ei.getAttribute(PRODSTRUCT_SYSTEMMIN);
            if (taPRODSTRUCT_SYSTEMMIN != null) {
                try {
                    this.setSYSTEMMIN( (Integer.parseInt(taPRODSTRUCT_SYSTEMMIN.toString())));
                }
                catch (NumberFormatException ex) {
                    System.out.println("Number format exception for: SystemMin: " + taPRODSTRUCT_SYSTEMMIN.toString());
                }
            }

        }

        // CATALOG OVERRIDE... currently, we are only going to get here if:
        //   1) we're a Product (ctry-lvl).
        //   2) our CATLGOR.OFFCOUNTRY == COUNTRYLIST
        //
        //  Remember, we already set above on first pass from PRODSTRUCT.ANNDATE, here we are seeing ionly if we override..
        else if (_ei.getEntityType().equals(ProdStruct.CATLGOR_ENTITY_GROUP)) {
            // String strPUBFROMDATE = getPUBFROMDATE();
            m_PUBFROMDATE_CATLGOR = CatDBTableRecord.getAttributeValue(_ei, "PUBFROM")[0];
            D.ebug(D.EBUG_SPEW,"CATLGOR found for ProdStruct:" + _ei.getKey() + ",PUBFROM=" + m_PUBFROMDATE_CATLGOR);

            // if(strPUBFROMDATE == null) {
	    //			setPUBFROMDATE(strPF2);
	    //  	} else if(strPF2 == null) {
				// nothing...
	    //  	} else { // neither null
            //    if(strPF2.compareTo(strPUBFROMDATE) > 0) {
	    //				setPUBFROMDATE(strPF2);
	    //			}
	    //		}
	    //	     D.ebug(D.EBUG_SPEW,"Ok, CATLGOR prodstruct pubfrom date we are going with:" + getPUBFROMDATE());
		    // PUBTO -remember we were smart enough earlier to filter by OFFCOUNTRY prior to setAttribtues call.
		    m_PUBTODATE_CATLGOR = CatDBTableRecord.getAttributeValue(_ei, "PUBTO")[0];
                    D.ebug(D.EBUG_SPEW,"CATLGOR found for ProdStruct:" + _ei.getKey() + ",PUBTO=" + m_PUBTODATE_CATLGOR);
            // if(strPT2 != null && strPT2.compareTo(strNow) <= 0) {
	    //			setPUBTODATE(strPT2);
	    //		}
		}
    }

    public void setAttribute(String _strTag, Object _oAtt) {
        return;
    }

    public Object getAttribute(String _strTag) {
        if (m_AttCollection.containsKey(_strTag)) {
            return m_AttCollection.get(_strTag);
        }
        else {
            System.out.println("attribute not found for " + _strTag);
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
//
//

    private final void setConfiguratorFlag_fc(Catalog _cat) {
		String[] saCONFIGURATORFLAG_FEATURE = null;
		String[] saCONFIGURATORFLAG_PRODSTRUCT = null;
    	for (int i = 0; i < this.getSmc().getCount(); i++) {
       		SyncMapItem smi = getSmc().get(i);
	       	if (smi.getChildType().equals("PRODSTRUCT") || smi.getChildType().equals("SWPRODSTRUCT")) {
				EntityItem eiPRODSTRUCT = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
           		saCONFIGURATORFLAG_PRODSTRUCT = CatDBTableRecord.getAttributeValue(eiPRODSTRUCT,CONFIGURATORFLAG);
				EntityItem eiFEATURE = Catalog.getEntityItem(_cat, smi.getEntity1Type(), smi.getEntity1ID());
           		saCONFIGURATORFLAG_FEATURE = CatDBTableRecord.getAttributeValue(eiFEATURE,CONFIGURATORFLAG);
		        // first, PRODSTRUCT, then, FEATURE...
		        if(saCONFIGURATORFLAG_PRODSTRUCT != null && saCONFIGURATORFLAG_PRODSTRUCT[1] != null && !saCONFIGURATORFLAG_PRODSTRUCT[1].equals("")) {
					setCONFIGURATORFLAG_FC(saCONFIGURATORFLAG_PRODSTRUCT[1]);
					D.ebug(this, D.EBUG_SPEW,"CONFIGURATORFLAG cas I for " + eiPRODSTRUCT.getKey() + " = " + saCONFIGURATORFLAG_PRODSTRUCT[1]);
				} else if(saCONFIGURATORFLAG_FEATURE != null) {
					setCONFIGURATORFLAG_FC(saCONFIGURATORFLAG_FEATURE[1]);
				    D.ebug(this, D.EBUG_SPEW,"CONFIGURATORFLAG cas II for " + eiFEATURE.getKey() + " = " + saCONFIGURATORFLAG_FEATURE[1]);
				}
		    }
		}
	}

	// For CR072607687
	// Asign a character to CFGFLAG according to the value of CONFIGURATORFLAG
	private final void setcfgflag(Catalog _cat) {
		String[] saCONFIGURATORFLAG_FEATURE = null;
		String[] saCONFIGURATORFLAG_PRODSTRUCT = null;
		for (int i = 0; i < this.getSmc().getCount(); i++) {
			SyncMapItem smi = getSmc().get(i);
			if (smi.getChildType().equals("PRODSTRUCT")
					|| smi.getChildType().equals("SWPRODSTRUCT")) {
				EntityItem eiPRODSTRUCT = Catalog.getEntityItem(_cat, smi
						.getChildType(), smi.getChildID());
				saCONFIGURATORFLAG_PRODSTRUCT = CatDBTableRecord
						.getAttributeValue(eiPRODSTRUCT, CONFIGURATORFLAG);
				EntityItem eiFEATURE = Catalog.getEntityItem(_cat, smi
						.getEntity1Type(), smi.getEntity1ID());
				saCONFIGURATORFLAG_FEATURE = CatDBTableRecord
						.getAttributeValue(eiFEATURE, CONFIGURATORFLAG);
				if (saCONFIGURATORFLAG_FEATURE[1] != null
						&& saCONFIGURATORFLAG_FEATURE[1].trim().equals("0040")) {
					setCFGFLAG("N");
				    setCFGFLAG_FC("0040");
				} else if (saCONFIGURATORFLAG_PRODSTRUCT[1] != null
						&& saCONFIGURATORFLAG_PRODSTRUCT[1].trim().equals(
								"0010")) {
					setCFGFLAG("C");
					setCFGFLAG_FC("0010");
				} else if (saCONFIGURATORFLAG_PRODSTRUCT[1] != null
						&& saCONFIGURATORFLAG_PRODSTRUCT[1].trim().equals(
								"0020")) {
					setCFGFLAG("U");
					setCFGFLAG_FC("0020");
				} else if (saCONFIGURATORFLAG_PRODSTRUCT[1] != null
						&& saCONFIGURATORFLAG_PRODSTRUCT[1].trim().equals(
								"0030")) {
					setCFGFLAG("P");
					setCFGFLAG_FC("0030");
				} else if (saCONFIGURATORFLAG_PRODSTRUCT[1] != null
						&& saCONFIGURATORFLAG_PRODSTRUCT[1].trim().equals(
								"0040")) {
					setCFGFLAG("N");
					setCFGFLAG_FC("0040");
				} else {
					setCFGFLAG("");
					setCFGFLAG_FC("");
				}
				D.ebug(this, D.EBUG_DETAIL, "saCONFIGURATORFLAG_FEATURE[1] "
						+ eiFEATURE.getKey() + " = "
						+ saCONFIGURATORFLAG_FEATURE[1]
						+ " saCONFIGURATORFLAG_PRODSTRUCT[1] "
						+ eiPRODSTRUCT.getKey() + " = "
						+ saCONFIGURATORFLAG_PRODSTRUCT[1]
						+ " CONFIGURATORFLAG_FC=" + getCONFIGURATORFLAG_FC()
					    + " CFGFLAG=" + getCFGFLAG()
						+ " CFGFLAG_FC=" + getCFGFLAG_FC());
			}
		}
	}

    private final void setOSLevels(Catalog _cat) {
		//
		// GAB: changed 2/28 per note w/ Hsi-Ho
		//

		if(getPRODENTITYTYPE().equals("MODEL")) {

			//
			// GAB OSLEVEL for WW MODEL TMF's ONLY have their oslevel data stored in prodstruct per 2/28 notes w/ Hsi-Ho.
			//    NOTE!!!!! : This is the Identical logic as LCTO's in Product. *GAB - Rev:060706:Not no more. Reverted LCTO logic in
			//                                                                         Product to include all OSLEVEL vals. Same as
			//                                                                         in wwproduct for MODELS.
			//

			// See table in spec (Net:  PRODSTRUCT is the first choice, then FEATURE, and then MODEL.)
		    if(isHardwareModel()) {
				String[] saOSLEVEL_MODEL = null;
				String[] saOSLEVEL_FEATURE = null;
				String[] saOSLEVEL_PRODSTRUCT = null;
        		for (int i = 0; i < this.getSmc().getCount(); i++) {
            		SyncMapItem smi = getSmc().get(i);
	            	if (smi.getChildType().equals("PRODSTRUCT")) {
						EntityItem eiPRODSTRUCT = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
                		saOSLEVEL_PRODSTRUCT = CatDBTableRecord.getAttributeValue(eiPRODSTRUCT,"OSLEVEL");
						EntityItem eiFEATURE = Catalog.getEntityItem(_cat, smi.getEntity1Type(), smi.getEntity1ID());
                		saOSLEVEL_FEATURE = CatDBTableRecord.getAttributeValue(eiFEATURE,"OSLEVEL");
 						EntityItem eiMODEL = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
                 		saOSLEVEL_MODEL = CatDBTableRecord.getAttributeValue(eiMODEL,"OSLEVEL");


            			//Limit to MODELs where COFCAT=Hardware, COFSUBCAT=System, COFGRP=Base.
						//The MODEL specifies the list of OSes that it can support and will be populated (an EXIST group for STATUS=FINAL will be added).
						//FEATURE specifies the list of OSes that it can support. If not populated, then this implies �all� (i.e. OS independent).
						//If PRODSTRUCT OSes is specified, then use this set of OSes for this MODEL with this FEATURE (aka PRODSTRUCT).
						//If PRODSTRUCT OSes is not specified and
						//	-If FEATURE OSes is populated, then the derived PRODSTRUCT OSes is the intersection of the MODEL OSes and the FEATURE OSes.
 						//	-If FEATURE OSes is not populated, then the derived PRODSTRUCT OSes is equal to the MODEL OSes.
                    	if(isSystemModel() && isBaseModel()) {
							if(saOSLEVEL_PRODSTRUCT != null && saOSLEVEL_PRODSTRUCT[0] != null && !saOSLEVEL_PRODSTRUCT[0].equals("")) {
					    		D.ebug(this, D.EBUG_SPEW,"OSLEVEL case II for " + eiPRODSTRUCT.getKey());
					    		setOSLEVEL(saOSLEVEL_PRODSTRUCT[0]);
		   						setOSLEVEL_FC(saOSLEVEL_PRODSTRUCT[1]);
							} else if(saOSLEVEL_FEATURE != null && saOSLEVEL_FEATURE[0] != null && !saOSLEVEL_FEATURE[0].equals("")) {
					    		D.ebug(this, D.EBUG_SPEW,"OSLEVEL case III for " + eiFEATURE.getKey());
					    		String[] saNew = null;
					    		if(saOSLEVEL_FEATURE[1].equals("10589") && (saOSLEVEL_MODEL != null && saOSLEVEL_MODEL[1] != null && saOSLEVEL_MODEL[1].equals("10589"))) {
									D.ebug(this, D.EBUG_SPEW,"OSLEVEL case III for " + eiFEATURE.getKey() + ":mergeFlagVals...");
									saNew = CatDBTableRecord.mergeFlagVals(saOSLEVEL_FEATURE,saOSLEVEL_MODEL);
								} else {
									D.ebug(this, D.EBUG_SPEW,"OSLEVEL case III for " + eiFEATURE.getKey() + ":intersectFlagVals...");
									saNew = CatDBTableRecord.intersectFlagVals(saOSLEVEL_FEATURE,saOSLEVEL_MODEL);
							    }
							    if(saNew != null) {
									D.ebug(this, D.EBUG_SPEW,"OSLEVEL case III for " + eiFEATURE.getKey() + ":saNew != null... setting to :" + saNew[0] + ";" + saNew[1]);
					    			setOSLEVEL(saNew[0]);
		   							setOSLEVEL_FC(saNew[1]);
								} else {
									D.ebug(this, D.EBUG_SPEW,"OSLEVEL case III for " + eiFEATURE.getKey() + ":saNew == null!!");
								}
							} else {
					    		D.ebug(this, D.EBUG_SPEW,"OSLEVEL case IV for " + eiMODEL.getKey());
					    		setOSLEVEL(saOSLEVEL_MODEL[0]);
		   						setOSLEVEL_FC(saOSLEVEL_MODEL[1]);
							}
						}
						//Limit to MODELs where COFCAT=Hardware, COFSUBCAT<>System.
						//If PRODSTRUCT OSes is specified, then use this set of OSes for this MODEL with this FEATURE (aka PRODSTRUCT).
						//If PRODSTRUCT OSes is not specified and
						//	-If FEATURE OSes is populated, then the derived PRODSTRUCT OSes is equal to the FEATURE OSes.
						//	-If FEATURE OSes is not populated, then the derived PRODSTRUCT OSes is equal to the MODEL OSes.
                    	else if(!isSystemModel()) {
							if(saOSLEVEL_PRODSTRUCT != null && saOSLEVEL_PRODSTRUCT[0] != null && !saOSLEVEL_PRODSTRUCT[0].equals("")) {
						    	D.ebug(this, D.EBUG_SPEW,"OSLEVEL case V for " + eiPRODSTRUCT.getKey());
						    	setOSLEVEL(saOSLEVEL_PRODSTRUCT[0]);
		    					setOSLEVEL_FC(saOSLEVEL_PRODSTRUCT[1]);
							} else if(saOSLEVEL_FEATURE != null && saOSLEVEL_FEATURE[0] != null && !saOSLEVEL_FEATURE[0].equals("")) {
						    	D.ebug(this, D.EBUG_SPEW,"OSLEVEL case VI for " + eiFEATURE.getKey());
						    	setOSLEVEL(saOSLEVEL_FEATURE[0]);
		    					setOSLEVEL_FC(saOSLEVEL_FEATURE[1]);
							} else {
								D.ebug(this, D.EBUG_SPEW,"OSLEVEL case VII for " + eiMODEL.getKey());
						    	setOSLEVEL(saOSLEVEL_MODEL[0]);
		    					setOSLEVEL_FC(saOSLEVEL_MODEL[1]);
							}
						}
						//Limit to MODELs where COFCAT=Service.
                        //OSLEVEL is N/A in this release.
                        else if(isServiceModel()) {
						    // nada!
						}

					}

        		}
		    } else if(isSoftwareModel() && isHIPOModel() && isBaseModel() && isNASubGrpModel()) { // HIPO case needs to be before "standard" SW/OS case.
        		for (int i = 0; i < this.getSmc().getCount(); i++) {
            		SyncMapItem smi = getSmc().get(i);
	            	if (smi.getChildType().equals("SWPRODSTRUCT")) {
						EntityItem eiSWFEATURE = Catalog.getEntityItem(_cat, smi.getEntity1Type(), smi.getEntity1ID());
                		EntityItem eiMODEL = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
                		//String[] saSWCCAT = CatDBTableRecord.getAttributeValue(eiSWFEATURE,"SWFCCAT");
						//if(saSWCCAT[1] != null && saSWCCAT[1].equals("319")) {
						String[] saOSLEVEL_FEATURE = CatDBTableRecord.getAttributeValue(eiSWFEATURE,"OSLEVEL");
						if(saOSLEVEL_FEATURE != null && saOSLEVEL_FEATURE[0] != null && !saOSLEVEL_FEATURE[0].equals("")) {
					    	D.ebug(this, D.EBUG_SPEW,"OSLEVEL case VIII.a for " + eiSWFEATURE.getKey());
					    	setOSLEVEL(saOSLEVEL_FEATURE[0]);
		   					setOSLEVEL_FC(saOSLEVEL_FEATURE[1]);
						}
            		}
        		}
			} else if(isSoftwareModel() && isOperatingSystem()) {
                //Limit to MODELs where COFCAT=Software.
                //The MODEL specifies the list of OSes that it can support and will be populated.
                //SWFEATURE specifies the list of OSes that it can support.
                //	-If not populated, then use the OSes from MODEL for SWPRODSTRUCT.
                //	-If populated, then the OSes for this MODEL wth this SWFEATURE (aka SWPRODSTRUCT) is the intersection of the OSes for MODEL and SWFEATURE.

        		for (int i = 0; i < this.getSmc().getCount(); i++) {
            		SyncMapItem smi = getSmc().get(i);
	            	if (smi.getChildType().equals("SWPRODSTRUCT")) {
						EntityItem eiSWFEATURE = Catalog.getEntityItem(_cat, smi.getEntity1Type(), smi.getEntity1ID());
                		EntityItem eiMODEL = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
                		//String[] saSWCCAT = CatDBTableRecord.getAttributeValue(eiSWFEATURE,"SWFCCAT");
						//if(saSWCCAT[1] != null && saSWCCAT[1].equals("319")) {
						String[] saOSLEVEL_FEATURE = CatDBTableRecord.getAttributeValue(eiSWFEATURE,"OSLEVEL");
						String[] saOSLEVEL_MODEL = CatDBTableRecord.getAttributeValue(eiMODEL,"OSLEVEL");
						if(saOSLEVEL_FEATURE != null && saOSLEVEL_FEATURE[0] != null && !saOSLEVEL_FEATURE[0].equals("")) {
					    	D.ebug(this, D.EBUG_SPEW,"OSLEVEL case VIII.b for " + eiSWFEATURE.getKey());
					    	String[] saNew = null;
					    	if(saOSLEVEL_FEATURE[1].equals("10589") || (saOSLEVEL_MODEL != null && saOSLEVEL_MODEL[1] != null && saOSLEVEL_MODEL[1].equals("10589"))) { // OS Independent.
					    	    saNew = CatDBTableRecord.mergeFlagVals(saOSLEVEL_FEATURE,saOSLEVEL_MODEL);
					    	} else {
								saNew = CatDBTableRecord.intersectFlagVals(saOSLEVEL_FEATURE,saOSLEVEL_MODEL);
							}
							if(saNew != null) {
					    		setOSLEVEL(saNew[0]);
		   						setOSLEVEL_FC(saNew[1]);
							}
						} else {
						// this part's open for interpretation.... we'll do it like this ONLY take off
						// of these types of SWFEATURES... effectively only from ValueMetric's)
					    	D.ebug(this, D.EBUG_SPEW,"OSLEVEL case IX.b for " + eiMODEL.getKey() + " \"" + saOSLEVEL_MODEL[0] + "\"");
					    	setOSLEVEL(saOSLEVEL_MODEL[0]);
		   					setOSLEVEL_FC(saOSLEVEL_MODEL[1]);
						}
						//}
            		}
        		}
			}
		}
		//
		// End OSLEVEL Processing
		//
	}

/**
 * THIS HAS TO BE CALLED LAST IN PROCESSSYNCMAP!!!
 */
    private void checkAVAILPubDates(Catalog _cat, String _strPSEntityType, int _iPSEntityID) {

        ProdStructId psid = this.getProdStructId();
        GeneralAreaMapItem gami = psid.getGami();

        String strRelType = "OOFAVAIL";
        // We need to process swprodstruct avail pub dates also for RQ - RQK 02282008
        String strRelType2 = "SWPRODSTRUCTAVAIL";
        String strRelType3 = "MODELAVAIL";

        for (int i = 0; i < getSmc().getCount(); i++) {
            SyncMapItem smiInner = getSmc().get(i);
            // pull out child avails..
            if((smiInner.getChildType().equals(strRelType) || smiInner.getChildType().equals(strRelType2)
                        || smiInner.getChildType().equals(strRelType3))
            		&& (smiInner.getEntity1ID() == _iPSEntityID
            				|| (smiInner.getChildType().equals(strRelType3) && smiInner.getChildID() == _iPSEntityID))) {
			    EntityItem eiAVAIL = Catalog.getEntityItem(_cat, smiInner.getEntity2Type(), smiInner.getEntity2ID());
        		String[] saAVAILTYPE = CatDBTableRecord.getAttributeValue(eiAVAIL,"AVAILTYPE");
        		String[] saORDERSYSNAME = CatDBTableRecord.getAttributeValue(eiAVAIL,"ORDERSYSNAME");
        		if (saAVAILTYPE == null || saAVAILTYPE[1] == null || saORDERSYSNAME == null ||
        				saORDERSYSNAME[1] == null || !saORDERSYSNAME[1].equals("4142")) {
        		    continue;
        		} else if(saAVAILTYPE[1].equals("146")) { // "Planned Availability" - this is for PUBFROM
					D.ebug(D.EBUG_SPEW,"A Planned Availability found for:" + eiAVAIL.getKey());
					if(Product.isCountryList(_cat,gami,eiAVAIL.getEntityType(),eiAVAIL.getEntityID())) {
						D.ebug(D.EBUG_SPEW,"Country Planned Availability found for:" + eiAVAIL.getKey());
                        
						//one change on May 07 2013, get pubfrom from annoucement:anndate if it is avail 146                        
                        boolean findAnn = false;
                        for (int j = 0; j < getSmc().getCount(); j++) {
                            SyncMapItem smiann = getSmc().get(j);
                            if(smiann.getChildType().equals("AVAILANNA") && smiann.getEntity1ID() == eiAVAIL.getEntityID() && smiann.getChildTran().equals("ON")){
                            	findAnn = true;                            	
                            	EntityItem eiAnn = Catalog.getEntityItem(_cat, smiann.getEntity2Type(), smiann.getEntity2ID());
                            	D.ebug(D.EBUG_SPEW, "PUBFROM: get it from ANNOUNCEMENT.ANNDATE: " + eiAnn.getKey());
                            	String[] saPUBFROMDATE_ANN = CatDBTableRecord.getAttributeValue(eiAnn, "ANNDATE");
                                if(saPUBFROMDATE_ANN[0] != null){
	                            	if (smiInner.getChildType().equals(strRelType3)) {
										m_PUBFROMDATE_MODELAVAIL146 = saPUBFROMDATE_ANN[0];
										modelAvail146_found = true;
										D.ebug(D.EBUG_SPEW, "PUBFROM on AVAIL 146's ANNOUNCEMENT (m_PUBFROMDATE_MODELAVAIL146) is " + m_PUBFROMDATE_MODELAVAIL146);
			                        }
			                        else {
			                        	m_PUBFROMDATE_AVAIL146 = saPUBFROMDATE_ANN[0];
			                        	avail146_found = true;
			                        	D.ebug(D.EBUG_SPEW, "PUBFROM on AVAIL 146's ANNOUNCEMENT (m_PUBFROMDATE_AVAIL146) is " + m_PUBFROMDATE_AVAIL146);
			                        }
                                }
                            }
                        }
                        if(!findAnn){
                        	//in case: we didn't have any ann that linked to avail
							// we're assuming:
	                        // a) all other pubdates have been set previously.
	                        // b) We'll only find one of these dates here.		
                        	D.ebug(D.EBUG_SPEW, "There is no ann that linked to this avail. will get it from avail.effectivedate for PUBFROM: " + eiAVAIL.getKey());
	                        String[] saEffectiveDate = CatDBTableRecord.getAttributeValue(eiAVAIL,"EFFECTIVEDATE");
	                        if (saEffectiveDate[0] != null) {
	                           if (smiInner.getChildType().equals(strRelType3)) {
	                             m_PUBFROMDATE_MODELAVAIL146 = saEffectiveDate[0];
	                             modelAvail146_found = true;
	                             D.ebug(D.EBUG_SPEW, "PUBFROM on AVAIL 146 (m_PUBFROMDATE_MODELAVAIL146) is " + m_PUBFROMDATE_MODELAVAIL146);
	                           }
	                           else {
	                             m_PUBFROMDATE_AVAIL146 = saEffectiveDate[0];
	                             avail146_found = true;
	                             D.ebug(D.EBUG_SPEW, "PUBFROM on AVAIL 146 (m_PUBFROMDATE_AVAIL146) is " + m_PUBFROMDATE_AVAIL146);
	                           }
	                        }
                        }


                        // String strCurrPubDate = getPUBFROMDATE();
                        // if(strCurrPubDate == null) {
			//				D.ebug(D.EBUG_SPEW,"checkAVAILPub(FROM)Dates:curr pubfromdate is null, using avail's:" + saEffectiveDate[0]);
			//				setPUBFROMDATE(saEffectiveDate[0]);
			//			} else if(saEffectiveDate[0] == null) {
			//		        D.ebug(D.EBUG_SPEW,"checkAVAILPub(FROM)Dates:AVAIL's effectivedate is null, keeping orginal date:" + strCurrPubDate);
                        //    continue;
			//			} else {
			//				//now we just want to take the Latest (greatest) date.
			//				D.ebug(D.EBUG_SPEW,"checkAVAILPub(FROM)Dates neither dates are null--> checking for latest date:" + saEffectiveDate[0] + " vs " + strCurrPubDate + "(current)");
			//			    if(saEffectiveDate[0].compareTo(strCurrPubDate) > 0) {
			//					setPUBFROMDATE(saEffectiveDate[0]);
			//				}
			//			}
					}
				} else if(saAVAILTYPE[1].equals("149")) { // "Last Order" - this is for PUBTO
					D.ebug(D.EBUG_SPEW,"A Last Order found for:" + eiAVAIL.getKey());
					if(Product.isCountryList(_cat,gami,eiAVAIL.getEntityType(),eiAVAIL.getEntityID())) {
						D.ebug(D.EBUG_SPEW,"Country Last Order found for:" + eiAVAIL.getKey());
                        // we're assuming:
                        // a) We'll only find one of these dates here.
                        String[] saEffectiveDate = CatDBTableRecord.getAttributeValue(eiAVAIL,"EFFECTIVEDATE");
                        if (saEffectiveDate[0] != null) {
                           if (smiInner.getChildType().equals(strRelType3)) {
                             m_PUBTODATE_MODELAVAIL149 = saEffectiveDate[0];
                             }
                           else {
                             m_PUBTODATE_AVAIL149 = saEffectiveDate[0];
                             }
	                }
                        D.ebug(D.EBUG_SPEW,"checkAVAILPub(TO)Dates from AVAIL 149:" + m_PUBTODATE_AVAIL149);
                        // String strCurrPubDate = getPUBTODATE();
                        // if(strCurrPubDate == null) {
			//				D.ebug(D.EBUG_SPEW,"checkAVAILPub(TO)Dates:curr pubtodate is null, using avail's:" + saEffectiveDate[0]);
			//				setPUBTODATE(saEffectiveDate[0]);
			//			} else if(saEffectiveDate[0] == null) {
			//		        D.ebug(D.EBUG_SPEW,"checkAVAILPub(TO)Dates:AVAIL's effectivedate is null, keeping orginal date:" + strCurrPubDate);
                        //    continue;
			//			} else {
			//				//now we just want to take the earliest (lowest) date.
			//				D.ebug(D.EBUG_SPEW,"checkAVAILPub(TO)Dates neither dates are null--> checking for earliest date:" + saEffectiveDate[0] + " vs " + strCurrPubDate + "(current)");
			//			    if(saEffectiveDate[0].compareTo(strCurrPubDate) < 0) {
			//					setPUBTODATE(saEffectiveDate[0]);
			//				}
			//			}
					}       // end if countrylist
				}		// end avail 149
			else if(saAVAILTYPE[1].equals("143")) { // "First Order" - this is for PUBFROM
					D.ebug(D.EBUG_SPEW,"A First Order found for:" + eiAVAIL.getKey());
					if(Product.isCountryList(_cat,gami,eiAVAIL.getEntityType(),eiAVAIL.getEntityID())) {
						D.ebug(D.EBUG_SPEW,"Country First Order found for:" + eiAVAIL.getKey());
                        String[] saEffectiveDate = CatDBTableRecord.getAttributeValue(eiAVAIL,"EFFECTIVEDATE");
                        if (saEffectiveDate[0] != null) m_PUBFROMDATE_AVAIL143 = saEffectiveDate[0];
                        D.ebug(D.EBUG_SPEW,"checkAVAILPub(FROM)Date from AVAIL 143:" + m_PUBFROMDATE_AVAIL143);

					}       // end if countrylist
				}		// end avail 143
			}			// end If for correct SMI
		}				// end for loop
	}					// end proc

    private void setSoftwareModel(boolean _b) {
		D.ebug(D.EBUG_SPEW,"setSoftwareModel=" + _b);
		m_bSoftwareModel = _b;
	}

    private void setHardwareModel(boolean _b) {
		D.ebug(D.EBUG_SPEW,"setHardwareModel=" + _b);
		m_bHardwareModel = _b;
	}

    private void setSystemModel(boolean _b) {
		D.ebug(D.EBUG_SPEW,"setSystemModel=" + _b);
		m_bSystemModel = _b;
	}

    private void setServiceModel(boolean _b) {
		D.ebug(D.EBUG_SPEW,"setServiceModel=" + _b);
		m_bServiceModel = _b;
	}

    private void setBaseModel(boolean _b) {
		D.ebug(D.EBUG_SPEW,"setBaseModel=" + _b);
		m_bBaseModel = _b;
	}

    private void setOperatingSystem(boolean _b) {
		D.ebug(D.EBUG_SPEW,"setPeratingSystem=" + _b);
        m_bOperatingSystem = _b;
    }

    private void setHIPOModel(boolean _b) {
		D.ebug(D.EBUG_SPEW,"setHIPOModel=" + _b);
		m_bHIPOModel = _b;
	}

    private void setNASubGrpModel(boolean _b) {
		D.ebug(D.EBUG_SPEW,"setNASubGrpModel=" + _b);
		m_bNASubGrpModel = _b;
	}

    private boolean isSoftwareModel() {
		return m_bSoftwareModel;
	}

    private boolean isHardwareModel() {
		return m_bHardwareModel;
	}

    private boolean isSystemModel() {
		return m_bSystemModel;
	}

    private boolean isServiceModel() {
		return m_bServiceModel;
	}

	private boolean isBaseModel() {
		return m_bBaseModel;
	}

    private boolean isOperatingSystem() {
        return m_bOperatingSystem;
    }

    private boolean isHIPOModel() {
		return m_bHIPOModel;
	}

    private boolean isNASubGrpModel() {
		return m_bNASubGrpModel;
	}



}
