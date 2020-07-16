/*
 * Created on Mar 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: CatalogProperties.java,v $
 * Revision 1.5  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.8  2007/10/29 09:36:32  jingb
 * Add getIDLTempTableReorgScript() method
 *
 * Revision 1.7  2007/10/16 08:53:44  jiehou
 * *** empty log message ***
 *
 * Revision 1.6  2007/09/17 06:51:18  jiehou
 * *** empty log message ***
 *
 * Revision 1.5  2007/09/11 05:36:53  jiehou
 * *** empty log message ***
 *
 * Revision 1.4  2007/08/30 06:25:58  jiehou
 * *** empty log message ***
 *
 * Revision 1.3  2007/08/29 06:00:30  jiehou
 * *** empty log message ***
 *
 * Revision 1.2  2007/06/18 01:37:44  liubdl
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2007/06/05 02:09:05  jingb
 * no message
 *
 * Revision 1.3  2006/06/22 22:55:18  gregg
 * getShortNAme
 *
 * Revision 1.2  2006/06/22 22:48:21  gregg
 * changing chunk size and propertizing chunk size
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.24  2006/01/19 21:12:46  gregg
 * some debugs for LanguageMapper
 *
 * Revision 1.23  2006/01/16 21:54:55  gregg
 * introducing language mapper
 *
 * Revision 1.22  2005/12/22 18:03:11  gregg
 * remove debug
 *
 * Revision 1.21  2005/10/26 01:35:53  dave
 * only pick on SMI's the I need
 *
 * Revision 1.20  2005/10/26 01:08:17  dave
 * backing off trace
 *
 * Revision 1.19  2005/09/21 18:09:14  gregg
 * shot at property-izing pdh attribute/catdb columns ...
 *
 * Revision 1.18  2005/09/12 02:01:49  dave
 * attempting to chunk out idl
 *
 * Revision 1.17  2005/07/13 20:32:40  gregg
 * name change
 *
 * Revision 1.16  2005/07/13 20:25:51  gregg
 * property-ize classes to run; add date to log file name
 *
 * Revision 1.15  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.14  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.13  2005/05/29 00:25:29  dave
 * ok.. clean up and reorg to better support pulls from PDH
 *
 * Revision 1.12  2005/05/27 05:34:11  dave
 * doing some date manipulation
 *
 * Revision 1.11  2005/05/17 20:39:05  joan
 * fixes
 *
 * Revision 1.10  2005/05/16 22:45:26  joan
 * change table
 *
 * Revision 1.9  2005/05/16 22:19:20  joan
 * change table
 *
 * Revision 1.8  2005/05/06 20:18:36  joan
 * update
 *
 * Revision 1.7  2005/05/03 19:31:36  joan
 * fixes
 *
 * Revision 1.6  2005/03/31 23:13:15  dave
 * cleanup
 *
 * Revision 1.5  2005/03/30 02:58:40  dave
 * more simplification
 *
 * Revision 1.4  2005/03/30 02:45:00  dave
 * testing hooks
 *
 * Revision 1.3  2005/03/23 00:18:52  dave
 * more generalization
 *
 * Revision 1.2  2005/03/22 23:45:55  dave
 * round out simple testing
 *
 * Revision 1.1  2005/03/22 23:21:29  dave
 * Introducing Properties files
 *
 *
 */

package COM.ibm.eannounce.catalog;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.D;

/**
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CatalogProperties {

    // Class constants
    private static final String PROPERTIES_FILENAME = "catalog.properties";

    // Class variables
    private static Properties c_props = null;

    /**
     * Some class level initialization
     */
    static {
        reloadProperties();
    }

    private CatalogProperties() {
    }

    private static final void loadProperties() {

        FileInputStream inProperties = null;

        try {

            if (c_props == null) {
                try {
                    inProperties = new FileInputStream("./" + PROPERTIES_FILENAME);
                    c_props = new Properties();
                    c_props.load(inProperties);
                    inProperties.close();
                }
                finally {
                    inProperties.close();
                }
            }
        }
        catch (Exception x) {
            D.ebug("Unable to loadProperties for " + PROPERTIES_FILENAME + " " + x);
        }
    }

    /**
     * reloadProperties
     *
     *  @author David Bigelow
     */
    public static final void reloadProperties() {
        c_props = null;
        loadProperties();
    }

    /**
     * getProfileNumber
     *
     * @return
     *  @author David Bigelow
     */
    public static final int getProfileNumber() {
        String strKey = "profile_number";
        String strVal = c_props.getProperty(strKey);
        int i = -1;
        try {
            i = Integer.parseInt(strVal);
        }
        catch (NumberFormatException x) {
            x.getMessage();
            D.ebug("ERROR parsing value in getOPWGID() .. defaulting to -1");
        }
        return i;
    }

    /**
     * getDatabaseUser
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getDatabaseUser() {
        return c_props.getProperty("catalog_database_user", "");
    }

    /**
     * getDatabasePassword
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getDatabasePassword() {
        return c_props.getProperty("catalog_database_password", "");
    }

    /**
     * getDatabaseURL
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getDatabaseURL() {
        return c_props.getProperty("catalog_database_url", "");
    }

    /**
     * getDatabaseSchema
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getDatabaseSchema() {
        return c_props.getProperty("catalog_database_schema", "gbli").toUpperCase();
    }

    /**
     * getRootType
     *
     * @return
     *  @author David Bigelow
     * @param _i
     */
    public static final String getRootType(int _i) {
        String strKey = _i + "_roottype";
        return c_props.getProperty(strKey);
    }

    /**
     * getVEName
     *
     * @return
     *  @author David Bigelow
     * @param _i
     */
    public static final String getVEName(int _i) {
        String strKey = _i + "_vename";
        return c_props.getProperty(strKey);
    }

    //////////////////////////////////////////////////////////////////////////
    // These represent AttributeCodes to be mapped to product table columns //
    //////////////////////////////////////////////////////////////////////////

    /**
     * getProductEntityTypeMapping
     *
     * @param _strVEName
     * @param _strColName
     * @return
     *  @author David Bigelow
     */
    public static final String getProductEntityTypeMapping(String _strVEName, String _strColName) {
        StringTokenizer st = null;
        String strKey = _strVEName.toUpperCase() + "_" + _strColName.toLowerCase() + "_attcode";
        String strVal = c_props.getProperty(strKey);
        if (strVal == null) {
            return null;
        }
        if (strVal.equals("")) {
            return "";
        }
        st = new StringTokenizer(strVal, ":");
        return st.nextToken();
    }

    /**
     * getProductAttCodeMapping
     *
     * @param _strVEName
     * @param _strColName
     * @return
     *  @author David Bigelow
     */
    public static final String getProductAttCodeMapping(String _strVEName, String _strColName) {
        String strKey = _strVEName.toUpperCase() + "_" + _strColName.toLowerCase() + "_attcode";
        String strVal = c_props.getProperty(strKey);
        StringTokenizer st = null;
        if (strVal == null) {
            return null;
        }
        if (strVal.equals("")) {
            return "";
        }

        st = new StringTokenizer(strVal, ":");
        st.nextToken();
        return st.nextToken();
    }

    /**
     * getProductFlagCodeMapping
     *
     * @param _strVEName
     * @param _strColName
     * @return
     *  @author David Bigelow
     */
    public static final String getProductFlagCodeMapping(String _strVEName, String _strColName) {
        String strKey = _strVEName.toUpperCase() + "_" + _strColName.toLowerCase() + "_flagcode";
        String strVal = c_props.getProperty(strKey);
        StringTokenizer st = null;
        if (strVal == null) {
            return null;
        }
        if (strVal.equals("")) {
            return "";
        }

        st = new StringTokenizer(strVal, ":");
        st.nextToken();
        st.nextToken();
        return st.nextToken();
    }

    /**
     * getProductFlagShortDescMappings
     *
     * @param _strVEName
     * @param _strColName
     * @return
     *  @author David Bigelow
     */
    public static final String[] getProductFlagShortDescMappings(String _strVEName, String _strColName) {

        StringTokenizer st1 = null;
        StringTokenizer st2 = null;
        String strFlags = null;

        String[] sa = null;

        Vector v = new Vector();

        String strKey = _strVEName.toUpperCase() + "_" + _strColName.toLowerCase() + "_flag_sd";
        String strVal = c_props.getProperty(strKey);
        if (strVal == null) {
            return null;
        }
        if (strVal.equals("")) {
            return new String[0];
        }
        st1 = new StringTokenizer(strVal, ":");
        st1.nextToken();
        st1.nextToken();
        strFlags = st1.nextToken();
        st2 = new StringTokenizer(strFlags, ",");
        while (st2.hasMoreTokens()) {
            v.addElement(st2.nextToken());
        }
        sa = new String[v.size()];
        v.copyInto(sa);
        return sa;
    }

    /**
     * isProductDetailEntity
     *
     * @param _strVEName
     * @param _strEntityType
     * @return
     *  @author David Bigelow
     */
    public static final boolean isProductDetailEntity(String _strVEName, String _strEntityType) {
        boolean b = true;
        StringTokenizer st = null;

        String strKey = _strVEName.toUpperCase() + "_skip_productattribute_entities";
        String strVal = c_props.getProperty(strKey);
        if (strVal == null) {
            return true;
        }

        st = new StringTokenizer(strVal, ":");
        while (st.hasMoreTokens()) {
            String strEType = st.nextToken();
            if (strEType.equals(_strEntityType)) {
                b = false;
            }
        }
        return b;
    }

    /**
     * isProcessAllNLSIDs
     *
     * @param _strVEName
     * @return
     *  @author David Bigelow
     */
    public static final boolean isProcessAllNLSIDs(String _strVEName) {
        String strKey = _strVEName.toUpperCase() + "_all_nls";
        String strVal = c_props.getProperty(strKey);
        if (strVal == null) {
            return false;
        }
        if (strVal.equals("")) {
            return false;
        }
        return (strVal.equalsIgnoreCase("Y"));
    }

    /**
     * isRootProductDetailAttribute
     *
     * @param _strVEName
     * @param _strEntityType
     * @param _strAttributeCode
     * @return
     *  @author David Bigelow
     */
    public static final boolean isRootProductDetailAttribute(String _strVEName, String _strEntityType, String _strAttributeCode) {
        boolean b = false;
        StringTokenizer st1 = null;
        String strKey = _strVEName.toUpperCase() + "_root_productdetail_attcodes";
        String strVal = c_props.getProperty(strKey);
        if (strVal == null) {
            return false;
        }
        st1 = new StringTokenizer(strVal, ",");
        while (st1.hasMoreTokens()) {
            String strToken = st1.nextToken();
            StringTokenizer st2 = new StringTokenizer(strToken, ":");
            String strEntityType = st2.nextToken();
            String strAttributeCode = st2.nextToken();
            if (strEntityType.equals(_strEntityType) && strAttributeCode.equals(_strAttributeCode)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * getProductCommitChunk
     *
     * @return
     *  @author David Bigelow
     */
    public static final int getProductCommitChunk() {
        String strKey = "product_commit_chunk";
        String s = c_props.getProperty(strKey);
        int iChunk = 10000;
        try {
            iChunk = Integer.parseInt(s);
        }
        catch (NumberFormatException x) {
            x.getMessage();
            D.ebug("ERROR parsing value in getProductCommitChunk() .. defaulting to " + iChunk);
        }
        return iChunk;
    }

    /**
     * isSkipProductCheck
     *
     * @param _strVEName
     * @return
     *  @author David Bigelow
     */
    public static final boolean isSkipProductCheck(String _strVEName) {
        String strKey = _strVEName + "_skip_product_check";
        String s = c_props.getProperty(strKey);
        if (s == null || s.equals("") || s.equalsIgnoreCase("N")) {
            return false;
        }
        return true;
    }

    /**
     * getCharacterEncoding
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getCharacterEncoding() {
        String strKey = "character_encoding";
        String s = c_props.getProperty(strKey);
        if (s == null || s.equals("")) {
            return "UTF-8";
        }
        return s;
    }

    /**
     * includeGenArea
     *
     * @param _strVEName
     * @param _strGenArea_fc
     * @return
     *  @author David Bigelow
     */
    public static final boolean includeGenArea(String _strVEName, String _strGenArea_fc) {
        StringTokenizer st = null;
        String strKey = _strVEName + "_include_genarea";
        String strVal = c_props.getProperty(strKey);
        if (strVal == null) {
            return true; // if left blank, then we dont care lets include all
        }
        else if (strVal.equals("") || strVal.equalsIgnoreCase("ALL")) {
            return true;
        }
        st = new StringTokenizer(strVal, ",");
        while (st.hasMoreTokens()) {
            String strTok = st.nextToken();
            if (strTok.equals(_strGenArea_fc)) {
                return true;
            }
        }
        return false;
    }

    /**
     * redirectOut
     *
     * @return
     *  @author David Bigelow
     */
    public static final boolean redirectOut() {
        String strKey = "redirect_out";
        String strVal = c_props.getProperty(strKey);
        if (strVal == null) {
            return false;
        }
        if (strVal.equalsIgnoreCase("Yes")) {
            return true;
        }
        return false;
    }

    /**
     * getEnterprise
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getEnterprise() {
        return c_props.getProperty("enterprise").trim();
    }

    /**
     * debug
     *
     * @return
     *  @author David Bigelow
     */
    public static final boolean debug() {
        return (c_props.getProperty("debug").equalsIgnoreCase("yes"));
    }

    /**
     * getMaxEntityItemsStored
     *
     * @return
     *  @author David Bigelow
     */
    public static final int getMaxEntityItemsStored() {
        String strKey = "max_entityitems_stored";
        String s = c_props.getProperty(strKey);
        int iMax = 2000;
        try {
            iMax = Integer.parseInt(s);
        }
        catch (NumberFormatException x) {
            x.getMessage();
            D.ebug("ERROR parsing value in getMaxEntityItemsStored() .. defaulting to " + iMax);
        }
        return iMax;
    }

    /**
     * getTestProjectID
     *
     * @return
     */
    public static final int getTestProjectID() {
        String strKey = "test_projectid";
        String s = c_props.getProperty(strKey);
        int i = 0;
        try {
            i = Integer.parseInt(s);
        }
        catch (NumberFormatException x) {
            x.getMessage();
            D.ebug("ERROR parsing value in getTestProjectID() .. defaulting to " + i);
        }
        return i;
    }

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getVersion() {
        return "$Id: CatalogProperties.java,v 1.5 2011/05/05 11:21:32 wendy Exp $";
    }

    /**
     * main
     *
     * @param args
     *  @author David Bigelow
     */
    public static void main(String[] args) {
    }

    /**
     * getComponentEntityType
     *
     * @param _i
     * @return String
     */

    public static final String getFeatureEntityType(int _i) {
        String strKey = _i + "_featureentitytype";
        return c_props.getProperty(strKey);
    }

    /**
     * getVEName
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public static final String getFeatureVEName(int _i) {
        String strKey = _i + "_featurevename";
        return c_props.getProperty(strKey);
    }

    /**
     * getFeatureType
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public static final String getFeatureType(int _i) {
        String strKey = _i + "_componenttype";
        return c_props.getProperty(strKey);
    }

    /**
     * getFeatureColumns
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public static final String[] getFeatureColumns(int _i) {
        String strKey = _i + "_featurecols";
        String s = c_props.getProperty(strKey);
        StringTokenizer st = new StringTokenizer(s, ";");
        Vector vctCols = new Vector();
        while (st.hasMoreTokens()) {
            vctCols.add(st.nextToken());
        }

        String[] astrCols = new String[vctCols.size()];
        vctCols.toArray(astrCols);

        return astrCols;

    }

    /**
     * getGeneralAreaKey
     *
     * @param _class
     * @return
     */
    public static final String getGeneralAreaKey(Class _class) {
        String strKey = _class.getName() + "_generalareakey";
        return c_props.getProperty(strKey, "WW");
    }

    /**
     *
     * getIDLTop
     * @param _class
     * @return
     */
    public static final int getIDLTop(Class _class) {
        String strKey = _class.getName() + "_idltop";
        return Integer.parseInt(c_props.getProperty(strKey, "100"));
    }

    public static final boolean isDryRun() {
        return c_props.getProperty("dry.run.enabled", "true").equals("true");
    }

    /**
     * getCatRunnerClasses
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public static final String[] getCatRunnerClasses() {
        String strKey = "catrunnerclasses";
        String s = c_props.getProperty(strKey);
        StringTokenizer st = new StringTokenizer(s, ",");
        Vector vctCols = new Vector();
        while (st.hasMoreTokens()) {
            vctCols.add(st.nextToken());
        }

        String[] astrCols = new String[vctCols.size()];
        vctCols.toArray(astrCols);

        return astrCols;
    }

    /**
     * getFeatureColumns
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public static final String getColAttMapping(String _strTable, String _strVE, String _strCatCol) {
        String strKey = _strTable.toLowerCase() + "_colattmap_" + _strVE.toUpperCase() + "_" + _strCatCol.toUpperCase();
        String s = c_props.getProperty(strKey);
        if (s == null) {
            s = new String();
        }
        //D.ebug(D.EBUG_SPEW, "getColAttMapping key:" + strKey + " = \"" + s + "\"");
        return s.trim();
    }

    /**
     * getLanguageMapping
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getLanguageMapping() {
        String strKey = "language_mapping";
        D.ebug(D.EBUG_SPEW,"language_mapping:" + c_props.getProperty(strKey));
        return c_props.getProperty(strKey);
    }

    /**
     *
     * getIDLTop
     * @param _class
     * @return
     */
    public static final int getIDLChunkSize(Class _class, String _strVE) {
        String strKey = getShortName(_class) + "_" + _strVE + "_idlchunksize";
        return Integer.parseInt(c_props.getProperty(strKey, "500"));
    }

    private static String getShortName(Class _class) {
        String strClass = _class.getName();
        int idot = strClass.lastIndexOf(".");
        return strClass.substring(idot + 1);
    }
    
    /**
     * Get run_catnet_alone flag.
     * @return
     */    
    public static String getRunCATNETAlone(){
    	String strKey ="run_catnet_alone";
    	D.ebug(D.EBUG_SPEW,"run_catnet_alone:" + c_props.getProperty(strKey));
    	return c_props.getProperty(strKey);
    }
    
    /**
     * Get IDL temp table clean up script
     * @param _class
     * @return
     */
    public static final String getIDLTempTableCleanUpScript(Class _class) {
        String strKey = getShortName(_class) + "_sys_db2_tmptable_cleanup_script";
        return c_props.getProperty(strKey, "ls");
    }    

    /**
     * add by houjie & liubing @2007-08-28---specify NLSID for WorldWideProduct,ProdStructCollection
     * In certain cases, we should only want to save certain NLSID's
     * We can specify generally, or by VE for the given object
     *
     * @param _class Class
     * @param _strVE String
     * @return int
     */
    public static int getSaveNLSID(Class collectionClass,Class entityClass, String _strVE) {
      String strKey = getShortName(collectionClass) + "." + getShortName(entityClass) + "." + _strVE + "_nlssave";
      String strAns = c_props.getProperty(strKey, "0");
      if(strAns.trim().toLowerCase().equals("all")){
    	  strAns="0";
      }
      return Integer.parseInt(strAns);
    }    
    
    public static int getLimitCountryCode(Class collectionClass,Class entityClass) {
        String strKey = getShortName(collectionClass) + "." + getShortName(entityClass) + ".LIMITCOUNTRYCODE";
        String strAns = c_props.getProperty(strKey, "0");
        if(strAns.trim().toLowerCase().equals("yes")){
        	strAns="1";
        }else{
        	strAns="0";
        }
        return Integer.parseInt(strAns);
    }    
    
    /**
     * Get IDL temp table reorg script
     * @param _class
     * @return
     */
    public static final String getIDLTempTableReorgScript(Class _class) {
        String strKey = getShortName(_class) + "_sys_db2_tmptable_reorg_script";
        return c_props.getProperty(strKey, "ls");
    } 
}
