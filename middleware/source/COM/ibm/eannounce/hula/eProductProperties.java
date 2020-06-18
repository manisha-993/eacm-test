//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eProductProperties.java,v $
// Revision 1.17  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.16  2004/10/15 17:25:25  gregg
// dropping in some finals
//
// Revision 1.15  2004/09/28 16:38:54  gregg
// property-ize MAX_ENTITYITEMS_STORED
//
// Revision 1.14  2004/09/23 16:12:43  gregg
// more props
//
// Revision 1.13  2004/09/23 15:36:25  gregg
// restrict processing products to specified genareas
//
// Revision 1.12  2004/09/20 17:33:16  gregg
// getCharacterEncoding() to trim column vals to actual length
//
// Revision 1.11  2004/09/17 20:15:24  gregg
// getProductFlagShortDescMappings
//
// Revision 1.10  2004/09/17 19:58:33  gregg
// change namings
//
// Revision 1.9  2004/09/17 19:50:27  gregg
// getProductFlagCodeMapping, Audience control flags
//
// Revision 1.8  2004/09/16 23:21:22  gregg
// s'more for isSkipProductCheck
//
// Revision 1.7  2004/09/16 18:46:57  gregg
// skipProductCheck for SBB
//
// Revision 1.6  2004/09/14 18:19:12  gregg
// getProductCommitChunk(), store more PreparedStatements.
//
// Revision 1.5  2004/09/07 18:20:50  gregg
// isRootProductDetailAttribute
//
// Revision 1.4  2004/08/24 21:28:13  gregg
// some more NLS processing
//
// Revision 1.3  2004/08/24 17:39:18  gregg
// some empty val checks
//
// Revision 1.2  2004/08/23 23:39:29  gregg
// getVersion() method
//
// Revision 1.1  2004/08/23 16:42:20  gregg
// load to middleware module
//
//

package COM.ibm.eannounce.hula;

import java.io.*;
import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.eannounce.objects.*;

/**
 * Retrieve configuration properties for <code>ODS Related Functions</code>
 * @version @date
 */
public final class eProductProperties {

    static final long serialVersionUID = 20011106L;

      // Class constants
      private static final String PROPERTIES_FILENAME = new String("eProduct.properties");

      // Class variables
      private static Properties c_props = null;

    /**
     * Some class level initialization
     */
      static {
        // Load the properties from file so they are available for each accessor method
        reloadProperties();
      }

    /**
     * Main method which performs a simple test of this class
     */
      public static void main(String arg[]) {
        System.out.println(eProductProperties.getVersion());
        System.out.println(eProductProperties.getDatabaseURL());
        System.out.println(eProductProperties.getDatabaseUser());
        System.out.println(eProductProperties.getDatabasePassword());
      }

    /**
     * Don't let anyone instantiate this class.
     */
      private eProductProperties() {}

    /**
     * Load the Middleware properties from the middleware.server.properties file
     */
      private static final void loadProperties() {
          try {
              if (c_props == null) {
                  c_props = new Properties();
                  FileInputStream inProperties = new FileInputStream("./" + PROPERTIES_FILENAME);
                  c_props.load(inProperties);
                  inProperties.close();
              }
          } catch (Exception x) {
              D.ebug("Unable to loadProperties for " + PROPERTIES_FILENAME + " " + x);
          }
      }

/**
 * Reload the properties from the middleware.server.properties file
 */
    public static final void reloadProperties() {
        c_props = null;
        loadProperties();
    }

/**
 * Return the Database User property from the ods.server.properties file
 */
    public static final String getDatabaseUser() {
        return c_props.getProperty("ods_database_user", "");
    }

/**
 * Return the Database password property from the ods.server.properties file
 */
    public static final String getDatabasePassword() {
        return c_props.getProperty("ods_database_password", "");
    }

/**
 * Return the Database URL property from from the ods.server.properties file
 */
    public static final String getDatabaseURL() {
        return c_props.getProperty("ods_database_url", "");
    }

/**
 * Return the Database Schema property from from the ods.server.properties file
 */
    public static final String getDatabaseSchema() {
        return c_props.getProperty("ods_database_schema", "gbli").toUpperCase();
    }

    public static final String getRootType(String _strVEName) {
        String strKey = _strVEName + "_roottype";
        return c_props.getProperty(strKey);
    }

    public static final String getVEName(String _strRootType) {
        String strKey = _strRootType + "_vename";
        return c_props.getProperty(strKey);
    }

//////////////////////////////////////////////////////////////////////////
// These represent AttributeCodes to be mapped to product table columns //
//////////////////////////////////////////////////////////////////////////


    public static final String getProductEntityTypeMapping(String _strVEName, String _strColName) {
        String strKey = _strVEName.toUpperCase() + "_" + _strColName.toLowerCase() + "_attcode";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return null;
        }
        if(strVal.equals("")) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(strVal,":");
        return st.nextToken();
    }

    public static final String getProductAttCodeMapping(String _strVEName, String _strColName) {
        String strKey = _strVEName.toUpperCase() + "_" + _strColName.toLowerCase() + "_attcode";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return null;
        }
        if(strVal.equals("")) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(strVal,":");
        st.nextToken();
        return st.nextToken();
    }

    public static final String getProductFlagCodeMapping(String _strVEName, String _strColName) {
        String strKey = _strVEName.toUpperCase() + "_" + _strColName.toLowerCase() + "_flagcode";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return null;
        }
        if(strVal.equals("")) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(strVal,":");
        st.nextToken();
        st.nextToken();
        return st.nextToken();
    }

    public static final String[] getProductFlagShortDescMappings(String _strVEName, String _strColName) {
        String strKey = _strVEName.toUpperCase() + "_" + _strColName.toLowerCase() + "_flag_sd";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return null;
        }
        if(strVal.equals("")) {
            return new String[0];
        }
        StringTokenizer st1 = new StringTokenizer(strVal,":");
        st1.nextToken();
        st1.nextToken();
        String strFlags = st1.nextToken();
        Vector v = new Vector();
        StringTokenizer st2 = new StringTokenizer(strFlags,",");
        while(st2.hasMoreTokens()) {
            v.addElement(st2.nextToken());
        }
        String[] sa = new String[v.size()];
        v.copyInto(sa);
        return sa;
    }

/**
 * These guys get left out of PRODUCTDETAIL (for the most part)
 */
    public static final boolean isProductDetailEntity(String _strVEName, String _strEntityType) {
        boolean b = true;
        String strKey = _strVEName.toUpperCase() + "_skip_productattribute_entities";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return true;
        }
        StringTokenizer st = new StringTokenizer(strVal,":");
        while(st.hasMoreTokens()) {
            String strEType = st.nextToken();
            if(strEType.equals(_strEntityType)) {
                b = false;
            }
        }
        return b;
    }

/**
 * Certain VE's are country-independent ... so we need to process any relevant NLSID's
 */
    public static final boolean isProcessAllNLSIDs(String _strVEName) {
        String strKey = _strVEName.toUpperCase() + "_all_nls";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return false;
        }
        if(strVal.equals("")) {
            return false;
        }
        return (strVal.equalsIgnoreCase("Y"));
    }


/**
 * These are Attributes on the root entity which need to be stored in PRODUCTDETAIL
 */
    public static final boolean isRootProductDetailAttribute(String _strVEName, String _strEntityType, String _strAttributeCode) {
        boolean b = false;
        String strKey = _strVEName.toUpperCase() + "_root_productdetail_attcodes";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return false;
        }
        StringTokenizer st1 = new StringTokenizer(strVal,",");
        while(st1.hasMoreTokens()) {
            String strToken = st1.nextToken();
            StringTokenizer st2 = new StringTokenizer(strToken,":");
            String strEntityType = st2.nextToken();
            String strAttributeCode = st2.nextToken();
            if(strEntityType.equals(_strEntityType) &&
               strAttributeCode.equals(_strAttributeCode)) {
                b = true;
            }
        }
        return b;
    }

    public static final int getProductCommitChunk() {
        String strKey = "product_commit_chunk";
        String s = c_props.getProperty(strKey);
        int iChunk = 10000;
        try {
            iChunk = Integer.parseInt(s);
        } catch(NumberFormatException x) {
            eProductUpdater.err("ERROR parsing value in getProductCommitChunk() .. defaulting to " + iChunk);
        }
        return iChunk;
    }

    public static final boolean isSkipProductCheck(String _strVEName) {
        String strKey = _strVEName + "_skip_product_check";
        String s = c_props.getProperty(strKey);
        if(s == null || s.equals("") || s.equalsIgnoreCase("N")) {
            return false;
        }
        return true;
    }

    public static final String getCharacterEncoding() {
        String strKey = "character_encoding";
        String s = c_props.getProperty(strKey);
        if(s == null || s.equals("")) {
            return "UTF-8";
        }
        return s;
    }

    public static final boolean includeGenArea(String _strVEName, String _strGenArea_fc) {
        String strKey = _strVEName + "_include_genarea";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return true; // if left blank, then we dont care lets include all
        } else if(strVal.equals("") || strVal.equalsIgnoreCase("ALL")) {
            return true;
        }
        StringTokenizer st = new StringTokenizer(strVal,",");
        while(st.hasMoreTokens()) {
            String strTok = st.nextToken();
            if(strTok.equals(_strGenArea_fc)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean redirectOut() {
        String strKey = "redirect_out";
        String strVal = c_props.getProperty(strKey);
        if(strVal == null) {
            return false;
        }
        if(strVal.equalsIgnoreCase("Yes")) {
            return true;
        }
        return false;
    }

    public static final int getOPWGID() {
        String strKey = "opwgid";
        String strVal = c_props.getProperty(strKey);
        int i = -1;
        try {
            i = Integer.parseInt(strVal);
        } catch(NumberFormatException x) {
            eProductUpdater.err("ERROR parsing value in getOPWGID() .. defaulting to -1");
        }
        return i;
    }

    public static final String getEnterprise() {
        return c_props.getProperty("enterprise");
    }

    public static final boolean debug() {
        return (c_props.getProperty("debug").equalsIgnoreCase("yes"));
    }

    public static final int getMaxEntityItemsStored() {
        String strKey = "max_entityitems_stored";
        String s = c_props.getProperty(strKey);
        int iMax = 2000;
        try {
            iMax = Integer.parseInt(s);
        } catch(NumberFormatException x) {
            eProductUpdater.err("ERROR parsing value in getMaxEntityItemsStored() .. defaulting to " + iMax);
        }
        return iMax;
    }


/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
    public static final String getVersion() {
        return new String("$Id: eProductProperties.java,v 1.17 2008/01/31 21:05:17 wendy Exp $");
    }

}
