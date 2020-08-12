// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import java.io.*;
import java.util.*;
/**********************************************************************************
 * Retrieve configuration properties for <code>FMChgLog</code>
 *
 *@author     Wendy Stimpson
 *@created    Oct 6, 2004
 */
// $Log: FMChgProperties.java,v $
// Revision 1.5  2005/06/09 15:32:50  wendy
// Jtest changes
//
// Revision 1.4  2005/06/06 13:56:49  wendy
// Added maximum number of hours to be used for 1 day type report
//
// Revision 1.3  2005/03/11 01:56:09  wendy
// CR0302055218 prevent update of last run time for debug
//
// Revision 1.2  2004/11/03 18:53:00  wendy
// Added minimum date
//
// Revision 1.1  2004/10/15 23:38:48  wendy
// Init for FM Chg Log application
//
public final class FMChgProperties
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.5 $";
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
    * Return the User Password
    * @return String
    */
    public static final String getUserPassword() {
        return fmProperties.getProperty("user_password", "*NoUserPW");
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
