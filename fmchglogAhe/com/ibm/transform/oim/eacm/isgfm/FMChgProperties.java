// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

import java.io.*;
import java.util.*;
/**********************************************************************************
 * Retrieve configuration properties for <code>FMChgLog</code>
 *
 *@author     Wendy Stimpson
 *@created    Oct 6, 2004
 */
// $Log: FMChgProperties.java,v $
// Revision 1.6  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.5  2009/06/25 20:39:07  wendy
// MN39715268 - Support run of 1 day only reports for a subset of InventoryGroups
//
// Revision 1.4  2006/05/03 18:01:29  wendy
// CR0503064033 prevent 7 and 30 day logs for specified invgrps (configurable)
//
// Revision 1.3  2006/05/01 21:31:07  wendy
// Added property for max size to write to the PDH while processing.
// This was done to reduce memory usage.  If a file is too large it will
// be written to the server and then moved to the PDH after lists are freed.
//
// Revision 1.2  2006/01/25 19:26:03  wendy
// AHE copyright
//
// Revision 1.1  2006/01/24 18:39:15  wendy
// Init for AHE
//
//
public final class FMChgProperties
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.6 $";
    private static final String PROPERTIES_FNAME = "FMChgLog.properties";
    private static Properties fmProperties = null;

    static
    {
        // Load the properties from file so they are available for each accessor method
        loadProperties();
    }

    /**
    * Don't let anyone instantiate this class.
    */
    private FMChgProperties() {}

    /**
    * Load the properties
    */
    private static final void loadProperties()
    {
        if (fmProperties == null)
        {
            FileInputStream inProperties = null;
            try {
                inProperties = new FileInputStream(PROPERTIES_FNAME);
                fmProperties = new Properties();
                fmProperties.load(inProperties);
            } catch (Exception x) {
                System.err.println("FMChgProperties Unable to loadProperties " + x);
            }
            finally {
                if (inProperties != null) {
                    try {
                        inProperties.close();
                    } catch (java.io.IOException e) {
                        System.out.println("I/O Exception "+e.getMessage());
                    }
                }
            }
        }
    }

    /**
    * Return the User Password - not really used for this application ID
    * @return String
    */
    public static final String getUserPassword() {
        return fmProperties.getProperty("user_password", "password");
    }

    /**
    * Return the Userid
    * @return String
    */
    public static final String getUserid() {
        return fmProperties.getProperty("userid", "CHGLOGEXTRACT");
    }

    /**
    * Return the version literal
    * @return String
    */
    public static final String getVersionLiteral() {
        return fmProperties.getProperty("version_literal", "POTPALASDEENALAB");
    }

    /**
    * Return the Role Code
    * @return String
    */
    public static final String getRoleCode() {
        return fmProperties.getProperty("role_code", "Manager");
    }

    /**
    * Return the enterprise
    * @return String
    */
    public static final String getEnterprise() {
        return fmProperties.getProperty("enterprise", "SGV13");
    }

    /**
    * Return the debug trace level
    * @return int
    */
    public static final int getDebugTraceLevel() {
        return Integer.parseInt(fmProperties.getProperty("debug_trace_level", "4"));
    }

    /**
    * Return the maximum number of days report stays active in PDH
    * @return int
    */
    public static final int getMaxAge() {
        return Integer.parseInt(fmProperties.getProperty("max_pdh_age", "7"));
    }

    /**
    * Return the maximum number of hours to be used for 1 day type report 6 days =144 hrs
    * @return int
    */
    public static final int getMaxHoursSinceLastRan() {
        return Integer.parseInt(fmProperties.getProperty("max_hrs_since_lastran", "144"));
    }

    /**
    * Return write files to server or not
    * @return boolean
    */
    public static final boolean getWriteToServer() {
        return fmProperties.getProperty("write_to_server", "true").equalsIgnoreCase("true");
    }

    /**
    * Return update last ran dts or not
    * @return boolean
    */
    public static final boolean getUpdateLastRanDTS() {
        return !fmProperties.getProperty("update_last_ran_dts", "true").equalsIgnoreCase("false");
    }

    /**
    * Return write files to PDH or not
    * @return boolean
    */
    public static final boolean getWriteToPDH() {
        return !fmProperties.getProperty("write_to_pdh", "true").equalsIgnoreCase("false");
    }

    /**
    * Return the minimum date, change log will not go back past this date
    * @return String
    */
    public static final String getMinimumDate() {
        return fmProperties.getProperty("minimum_date", "");
    }

    /**
    * Return the maximum size file to write to the PDH during processing.  If larger than this
    * number it will be copied to the PDH after EntityLists have been dereferenced
    * @return int
    */
    public static final int getMaxProcessSize() {
//        return Integer.parseInt(fmProperties.getProperty("max_pdh_process_size", "1000000"));  // 1Mb
        return Integer.parseInt(fmProperties.getProperty("max_pdh_process_size", "10000000"));  // 10Mb.. basically prevent this behavior for now
    }
    /**
    * Return the maximum number of ids for a VE extract action
    * @return int
    */
    public static final int getMaxVELimit() {
        return Integer.parseInt(fmProperties.getProperty("max_ve_limit", "1000"));  
    }
    /**
    * Return the list of inventory groups to skip for the 7 and 30 day reports  CR0503064033
    * @return String comma delimited list of flag codes for inventory groups
    */
    public static final String getSkippedInvGrps_7_30() {
        return fmProperties.getProperty("skip_invgrps_7_30", "");
    }
    /**
     * MN39715268
     * Return the list of inventory groups to run the 1 day report for
     * WARNING: 7 and 30 day reports will not be run at all
     * @return String comma delimited list of flag codes for inventory groups
     */
     public static final String get1DayonlyInvGrps() {
         return fmProperties.getProperty("run_1day_only_invgrps", "");
     }
    /**
    * Main method which performs a simple test of this class
    * @param arg String[]
    */
    public static void main(String arg[]) {
        System.out.println("getEnterprise      =<"+FMChgProperties.getEnterprise()+">");
        System.out.println("getRoleCode        =<"+FMChgProperties.getRoleCode()+">");
        System.out.println("getUserPassword    =<"+FMChgProperties.getUserPassword()+">");
        System.out.println("getUserid          =<"+FMChgProperties.getUserid()+">");
        System.out.println("getVersionLiteral  =<"+FMChgProperties.getVersionLiteral()+">");
    }
}
