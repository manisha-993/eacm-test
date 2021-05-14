/*
 * Created on May 25, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.sql.ResultSet;

import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Enumeration;
import COM.ibm.eannounce.catalog.CatalogProperties;
import COM.ibm.eannounce.objects.EANAttribute;

/**
 * This guy is the glue to all the objects
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Catalog {

    /**
     * POST_TO_PDH Flag - univerally used to say what datasource to post back to
     * currently.. all changes are always posted to the CatDb
     *
     */
    public static final int POST_TO_PDH = 100;
    /**
     * POST_TO_ODS Flag - universally used to drive posting any changes back to Cat
     */
    public static final int POST_TO_CAT = 200;

    private Database CatalogDatabase = null;
    private Database PDHDatabase = null;
    private Profile CatalogProfile = null;
    private String Enterprise = null;
    private int ProfileNumber = 0;

    private GeneralAreaMapItem Gami = null;

    // Lets get a Gami here...
    private static GeneralAreaMap c_gam = null;

    private Hashtable m_hashBasicRuleCollections = null;

    private static EANList c_egList = new EANList(100);
    private static EANList c_eiList = new EANList(10000);

    private static LanguageMapper c_languageMapper = null;

    public static final void resetLists() {
        c_egList = new EANList(100);
        c_eiList = new EANList(10000);
    }

    public static final String COUNTRYLIST = new String("COUNTRYLIST");
    /**
     * getEntityGroup
     *
     * @param _cat
     * @param _strEntityType
     * @return
     */
    public static EntityGroup getEntityGroup(Catalog _cat,
                                             String _strEntityType) {

        // LCTO ~really~ is MODEL per Wayne
        if (_strEntityType.equals(Product.LCTO_ENTITYTYPE)) {
            _strEntityType = Product.MODEL_ENTITYTYPE;
        }

        try {
            if (c_egList.get(_strEntityType) == null) {
                EntityGroup eg = new EntityGroup(null, _cat.getPDHDatabase(),
                                                 _cat.getCatalogProfile(),
                                                 _strEntityType, "Extract");
                c_egList.put(_strEntityType, eg);
                return eg;
            }
            else {
                //D.ebug("Catalog: *** reusing entity group " + _strEntityType);
                return (EntityGroup) c_egList.get(_strEntityType);
            }

        }
        catch (MiddlewareRequestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static final void resetEntityItemList() {
        for (int i = 0; i < c_egList.size(); i++) {
            EntityGroup eg = (EntityGroup) c_egList.getAt(i);
            for (int y = eg.getEntityItemCount() - 1; y >= 0; y--) {
                EntityItem ei = eg.getEntityItem(y);
                ei.setParent(null);
                for (int z = ei.getAttributeCount() - 1; z >= 0; z--) {
                    EANAttribute att = ei.getAttribute(z);
                    ei.removeAttribute(att);
                }
                eg.removeEntityItem(ei);
            }
        }

        D.ebug(D.EBUG_DETAIL,
               "resetEntitListGroup.. EI List AFTER SIZE for " + c_eiList.size() +
               " is ");

    }

    public static LanguageMapper getLanguageMapper() {
        return c_languageMapper;
    }

    protected void buildLanguageMapper() throws SQLException,
        MiddlewareException {
        D.ebug(D.EBUG_SPEW, "buildLanguageMapper...");
        if (c_languageMapper == null) {
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...a");
            String s = CatalogProperties.getLanguageMapping();
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...b");
            StringTokenizer st = new StringTokenizer(s, ":");
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...c");
            String strEntity = st.nextToken();
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...d");
            String strAttFrom = st.nextToken();
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...e");
            String strAttTo = st.nextToken();
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...f");
            Database db = this.getPDHDatabase();
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...g");
            Profile prof = this.getCatalogProfile();
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...h");
            String strEnterprise = prof.getEnterprise();
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...i");
            c_languageMapper = new LanguageMapper(db, strEnterprise, strEntity,
                                                  strAttFrom, strAttTo);
            D.ebug(D.EBUG_SPEW, "buildLanguageMapper...j");
        }
        return;
    }

    /**
     * getEntityItem
     *
     * @param _cat
     * @param _strEntityType
     * @param _iEntityID
     * @return
     */
    public static EntityItem getEntityItem(Catalog _cat, String _strEntityType,
                                           int _iEntityID) {

        // LCTO ~really~ is MODEL per Wayne
        if (_strEntityType.equals(Product.LCTO_ENTITYTYPE)) {
            _strEntityType = Product.MODEL_ENTITYTYPE;
        }

        try {
            if (c_eiList.get(_strEntityType + _iEntityID) == null) {
                Profile prof = _cat.getCatalogProfile();
                Database db = _cat.getPDHDatabase();

                EntityGroup eg = Catalog.getEntityGroup(_cat, _strEntityType);
                EntityItem ei = new EntityItem(eg, prof, db, _strEntityType,
                                               _iEntityID);
                c_eiList.put(ei);
                return ei;
            }
            else {
                //D.ebug("Catalog: *** reusing entity item " + _strEntityType + _iEntityID);
                return (EntityItem) c_eiList.get(_strEntityType + _iEntityID);
            }

        }
        catch (MiddlewareRequestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This is a great way to package a database, profile, and PDH Enterprise
     * @param _strEnterprise
     * @param _iop
     * @throws SQLException
     * @throws MiddlewareException
     */
    public Catalog(String _strEnterprise, int _iop) throws SQLException,
        MiddlewareException {
        this.setEnterprise(_strEnterprise);
        this.setProfileNumber(_iop);
        this.setCatalogDatabase(new Database());
        this.setPDHDatabase(new Database());
        this.setCatalogProfile(new Profile(getCatalogDatabase(), getEnterprise(),
                                           getProfileNumber()));
        //this.buildLanguageMapper();
    }

    /**
     * Rely on the properties file for your information
     * @throws SQLException
     * @throws MiddlewareException
     */
    public Catalog() throws SQLException, MiddlewareException {
        this.setEnterprise(CatalogProperties.getEnterprise());
        this.setProfileNumber(CatalogProperties.getProfileNumber());
        this.setCatalogDatabase(new Database());
        this.setPDHDatabase(new Database());
        this.setCatalogProfile(new Profile(getCatalogDatabase(), getEnterprise(),
                                           getProfileNumber()));
        //this.buildLanguageMapper();
    }

    /**
     * This is a great way to package a PDH Enterprise w/ no Profile
     * @param _strEnterprise
     * @throws SQLException
     * @throws MiddlewareException
     */
    public Catalog(String _strEnterprise) throws SQLException,
        MiddlewareException {
        this.setEnterprise(_strEnterprise);
        this.setPDHDatabase(new Database());
        this.setCatalogDatabase(new Database());
        //this.buildLanguageMapper();
    }

    /**
     * zapDate
     *
     * @param _strDate
     * @return
     */
    public static String zapDate(String _strDate) {
        if (_strDate == null) {
            return null;
        }
        return _strDate.replace(' ', '-').replace(':', '.');
    }

    /**
     * This guy uses borrowed information to kick start the catalog.
     * @param _db
     * @param _prof
     * @throws SQLException
     * @throws MiddlewareException
     */
    public Catalog(Database _db, Profile _prof) throws SQLException,
        MiddlewareException {
        this.setCatalogDatabase(_db);
        this.setCatalogProfile(_prof);
        this.setEnterprise(_prof.getEnterprise());
        this.setProfileNumber(_prof.getOPWGID());
        //this.buildLanguageMapper();
    }

    /**
     * Returns a new GeneralAreaMap and stores it in this Catalog
     * for programatic reference
     * @return
     */
    public GeneralAreaMap getGam() {
        if (c_gam == null) {
            Database db = this.getCatalogDatabase();
            String strEnterprise = this.getEnterprise();
            c_gam = new GeneralAreaMap(strEnterprise, db);
            //
            Class mclass = ProductCollection.class;
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
            GeneralAreaMapGroup gamp = c_gam.lookupGeneralArea(strGeneralArea);
            Enumeration en = gamp.elements();
            if (!en.hasMoreElements()) {
                System.out.println(
                    "Building GeneralAreaMap: no gami to find!!!");
            }
            while (en.hasMoreElements()) {
                GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
                System.out.println("Building GeneralAreaMap:" + gami);
            }
            //
        }
        return c_gam;
    }

    /**
     * close
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    public void close() throws SQLException, MiddlewareException {
        this.getCatalogDatabase().commit();
        this.getCatalogDatabase().close();
        this.setCatalogDatabase(null);
        this.getPDHDatabase().commit();
        this.getPDHDatabase().close();
        this.setPDHDatabase(null);
    }

    //public static PrintStream out = System.out;
    //public static PrintStream log = System.out;
    //public static PrintStream xml = System.out;

    //public static setOut(PrintStream _ps) {
    //out = _ps;
    //}

    //public static setLog(PrintStream _ps) {
    //log = _ps;
    //}

    //public static setXML(PrintStream _ps) {
    //xml = _ps;
    //}

    //public void saveRunTime(Database _db, Profile _prof, String _strObject, String _strNow) {
    //}

    /**
     * getCatalogDatabase
     *
     * @return
     */
    public Database getCatalogDatabase() {
        return CatalogDatabase;
    }

    /**
     * getCatalogProfile
     *
     * @return
     */
    public Profile getCatalogProfile() {
        return CatalogProfile;
    }

    /**
     * setCatalogDatabase
     *
     * @param database
     * @return
     */
    public Database setCatalogDatabase(Database database) {
        CatalogDatabase = database;
        return CatalogDatabase;
    }

    /**
     * setCatalogProfile
     *
     * @param profile
     */
    public void setCatalogProfile(Profile profile) {
        CatalogProfile = profile;
    }

    /**
     * getEnterprise
     *
     * @return
     */
    public String getEnterprise() {
        return Enterprise;
    }

    /**
     * setEnterprise
     *
     * @param string
     */
    public void setEnterprise(String string) {
        Enterprise = string;
    }

    /**
     * getProfileNumber
     *
     * @return
     */
    public int getProfileNumber() {
        return ProfileNumber;
    }

    /**
     * setProfileNumber
     *
     * @param i
     */
    public void setProfileNumber(int i) {
        ProfileNumber = i;
    }

    /**
     * getGami
     *
     * @return
     */
    public GeneralAreaMapItem getGami() {
        return Gami;
    }

    /**
     * setGami
     *
     * @param item
     */
    public void setGami(GeneralAreaMapItem item) {
        Gami = item;
    }

    /**
     * getPDHDatabase
     * @return
     */
    public Database getPDHDatabase() {
        return PDHDatabase;
    }

    /**
     * setPDHDatabase
     * @param database
     */
    public void setPDHDatabase(Database database) {
        PDHDatabase = database;
    }

    /**
     * isDryRun
     *
     * @return
     */
    public static final boolean isDryRun() {
        return CatalogProperties.isDryRun();
    }

    /**
     * Here we also want to deal with WW settings.. etc..  WW implies all is good for this
     * entity.. no matter what country list has been passed
     * containsCountryListFlagCode
     * @param _cat
     * @param _strEntityType
     * @param _iEntityID
     * @param _strCountryList
     * @return
     */
    public static boolean containsCountryListFlagCode(
        Catalog _cat,
        String _strEntityType,
        int _iEntityID,
        String _strCountryList) {
        EntityGroup eg = getEntityGroup(_cat, _strEntityType);
        EANMetaAttribute ma = eg.getMetaAttribute(COUNTRYLIST);
        if (ma == null) {
            return true;
        }
        else {
            EntityItem ei = getEntityItem(_cat, _strEntityType, _iEntityID);
            EANFlagAttribute att = (EANFlagAttribute) ei.getAttribute(
                COUNTRYLIST);
            //
            // assume that if no country list is set
            // it is for all countries!!
            //
            if (att == null) {
                return true;
            }
            MetaFlag[] mfa = (MetaFlag[]) att.get();
            for (int f = 0; f < mfa.length; f++) {
                MetaFlag mf = mfa[f];
                String flagCode = mf.getFlagCode();

                if (mf.isSelected() && flagCode.equals(_strCountryList)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int getMaxEntityId(String _strEntityType) throws SQLException {

        int iMaxID = 0;
        ResultSet rs = null;
        try {
            //Now get the max rows we have to retrieve for this entity
            PDHDatabase.debug(D.EBUG_DETAIL,
                              "gbl9001:params:" + Enterprise + ":" +
                              _strEntityType + ":" + "NOOP");
            rs = PDHDatabase.callGBL9001(new ReturnStatus( -1), Enterprise,
                                         _strEntityType, "NOOP");
            if (rs.next()) {
                iMaxID = rs.getInt(1);
                int iStartID = rs.getInt(2);
                int iEntityCount = rs.getInt(3);
                PDHDatabase.debug(D.EBUG_DETAIL,
                                  "gbl9001:answer: Max:" + iMaxID + ":Start:" +
                                  iStartID + ":Rows:" + iEntityCount);
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
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            PDHDatabase.commit();
            PDHDatabase.freeStatement();
            PDHDatabase.isPending();
        }

        return iMaxID;

    }

    public BasicRuleCollection getBasicRuleCollection(String _strTable,
        GeneralAreaMapItem _gami) {
        if (m_hashBasicRuleCollections == null) {
            m_hashBasicRuleCollections = new Hashtable();
        }
        String strKey = BasicRuleCollection.buildKey(_strTable, _gami);
        BasicRuleCollection brc = (BasicRuleCollection)
            m_hashBasicRuleCollections.get(strKey);
        if (brc == null) {
            brc = new BasicRuleCollection(this, _strTable, _gami);
            m_hashBasicRuleCollections.put(strKey, brc);
        }
        return brc;
    }

    /**
     * Call unix shell
     * @param _strCommand
     * @throws IOException
     * @throws InterruptedException
     */
    public static void execUnixShell(String _strCommand) throws IOException,
			InterruptedException {

		D.ebug(D.EBUG_DETAIL, "execUnixShell:" + _strCommand);

		Runtime rtime = Runtime.getRuntime();

		//start unix shell
		Process child = rtime.exec(System.getProperty("os.shell", "/bin/sh"));

		String line;
		BufferedWriter outCommand = new BufferedWriter(new OutputStreamWriter(
				child.getOutputStream()));
		BufferedReader input = new BufferedReader(new InputStreamReader(child
				.getInputStream()));

		//run passed command
		outCommand.write(_strCommand + "\n");
		outCommand.flush();

		outCommand.write("exit" + "\n");
		outCommand.flush();

		while ((line = input.readLine()) != null) {
			D.ebug(">" + line);
		}
		input.close();

		int wait = child.waitFor();
		//
		// close
		//
		outCommand.close();
		input.close();

		D.ebug("Finish the script.. here is the answer: " + wait);

	}
}

/*
 * $Log: Catalog.java,v $
 * Revision 1.5  2011/08/11 14:24:29  praveen
 * Comment reuse group/item debug stmts to improve performance
 *
 * Revision 1.4  2011/05/05 11:21:31  wendy
 * src from IBMCHINA
 *
 * Revision 1.2  2007/09/10 06:45:43  sulin
 * no message
 *
 * Revision 1.2  2007/06/15 07:11:37  jiangshouyi
 * Add "execUnixShell" method
 *
 * Revision 1.1.1.1  2007/06/15 05:42:52  db2admin
 * no message
 *
 * Revision 1.2  2006/08/28 20:07:26  dave
 * ok.. minor memory fixes
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.45  2006/02/21 19:13:30  gregg
 * debug for gam
 *
 * Revision 1.44  2006/01/20 19:04:34  gregg
 * buildFeatureCollection-> protected
 *
 * Revision 1.43  2006/01/20 18:58:01  gregg
 * LangugageMapper D3bugs
 *
 * Revision 1.42  2006/01/20 17:46:41  joan
 * work on null pointer
 *
 * Revision 1.41  2006/01/19 21:12:46  gregg
 * some debugs for LanguageMapper
 *
 * Revision 1.40  2006/01/17 17:56:30  gregg
 * remove a connect
 *
 * Revision 1.39  2006/01/17 16:55:22  dave
 * <No Comment Entered>
 *
 * Revision 1.38  2006/01/16 21:54:55  gregg
 * introducing language mapper
 *
 * Revision 1.37  2005/11/22 22:53:43  gregg
 * prepping for wayne spec cases
 *
 * Revision 1.36  2005/11/16 22:45:11  gregg
 * basicrulecollection
 *
 * Revision 1.35  2005/10/31 17:20:53  gregg
 * resetLists
 *
 * Revision 1.34  2005/10/25 07:05:49  dave
 * memory cleanup
 *
 * Revision 1.33  2005/09/14 17:49:39  gregg
 * fixes
 *
 * Revision 1.32  2005/09/14 17:42:58  gregg
 * map MODEL<->LCTO
 * general setAttribtue cleanup
 *
 * Revision 1.31  2005/09/14 16:44:54  gregg
 * LCTO ~really~ is MODEL per Wayne
 *
 * Revision 1.30  2005/09/13 02:24:58  dave
 * ok change conflict fixed
 *
 * Revision 1.29  2005/06/23 05:06:41  dave
 * no att = all countries
 *
 * Revision 1.28  2005/06/23 04:43:09  dave
 * ok.. looking for gami variation in the collarteral
 *
 * Revision 1.27  2005/06/22 19:59:27  joan
 * add method
 *
 * Revision 1.26  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.25  2005/06/07 13:33:27  dave
 * making lists bigger to start for cache
 *
 * Revision 1.24  2005/06/07 04:34:50  dave
 * working on commit control
 *
 * Revision 1.23  2005/06/05 21:01:34  dave
 * beginnings of tracking cached EntityGroups and EntityItems
 *
 * Revision 1.22  2005/06/05 20:33:55  dave
 * going for the Feature Update to the CatDB
 *
 * Revision 1.21  2005/06/05 18:28:32  dave
 * ok.. working on null pointer again
 *
 * Revision 1.20  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.19  2005/06/01 02:20:11  dave
 * ok.. more cleanup and ve work
 *
 * Revision 1.18  2005/05/29 00:25:29  dave
 * ok.. clean up and reorg to better support pulls from PDH
 *
 * Revision 1.17  2005/05/28 23:25:59  dave
 * ok.. can we spit out xml?
 *
 * Revision 1.16  2005/05/27 05:34:11  dave
 * doing some date manipulation
 *
 * Revision 1.15  2005/05/27 05:13:36  dave
 * ok.. expanding profile information
 *
 * Revision 1.14  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.13  2005/05/26 00:06:06  dave
 * adding put to design by contract
 *
 * Revision 1.12  2005/05/23 14:14:32  dave
 * adding source, type, and interval sigs to keys
 * adding getGami to Catalog statics
 *
 * Revision 1.11  2005/05/13 21:11:56  roger
 * Clean up
 *
 * Revision 1.10  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */
