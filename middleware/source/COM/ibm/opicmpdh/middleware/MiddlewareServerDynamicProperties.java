//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareServerDynamicProperties.java,v $
// Revision 1.22  2002/04/10 21:06:57  dave
// massive .close() effort on classes and methods
//
// Revision 1.21  2002/03/12 18:18:51  roger
// Remove display
//
// Revision 1.20  2002/03/12 17:22:02  roger
// Remove display
//
// Revision 1.19  2002/03/08 23:22:05  roger
// Unoptimize
//
// Revision 1.18  2002/03/08 22:51:03  roger
// Optimize
//
// Revision 1.17  2002/03/08 18:14:05  roger
// Don't show property file name
//
// Revision 1.16  2002/03/08 17:17:58  roger
// Don't use D.isplay here - recursive loop
//
// Revision 1.15  2002/03/08 03:03:03  roger
// Fix it
//
// Revision 1.14  2002/03/07 22:10:53  roger
// Use default if specified is length 0
//
// Revision 1.13  2002/03/07 21:07:31  roger
// Properties should have defaults
//
// Revision 1.12  2002/03/07 17:05:06  roger
// Run a test to find out why not using specified property file
//
// Revision 1.11  2002/03/06 23:20:27  roger
// Message is misleading
//
// Revision 1.10  2002/03/06 22:57:20  roger
// Output ~after~ getting properties
//
// Revision 1.9  2002/03/06 22:10:31  roger
// Handle System property
//
// Revision 1.8  2002/03/06 20:52:39  roger
// Make output D.isplay
//
// Revision 1.7  2002/03/06 18:37:37  roger
// Fix it
//
// Revision 1.6  2002/03/06 18:22:47  roger
// Derive the property file name from system property, or the default value
//
// Revision 1.5  2002/03/06 18:04:37  roger
// Test if System properties are being set by WASPool
//
// Revision 1.4  2001/08/22 16:52:58  roger
// Removed author RM
//
// Revision 1.3  2001/06/29 15:51:18  roger
// Remove java_options
//
// Revision 1.2  2001/06/27 00:04:23  roger
// Missing Dynamic in class name
//
// Revision 1.1  2001/06/26 23:59:45  roger
// Added support for dynamic properties
//
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.util.*;

/**
 * Retrieve dynamic configuration properties for <code>Middleware</code>
 * @version @date
 */
public final class MiddlewareServerDynamicProperties extends Properties {

  // Class constants
  private static String c_strPropertiesFilename = "middleware.server.dynamic.properties";

  // Class variables
  private static Properties c_propMiddleware = null;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {
    System.out.println(MiddlewareServerDynamicProperties.getHostname());
    System.out.println(MiddlewareServerDynamicProperties.getIpAddress());
    System.out.println(MiddlewareServerDynamicProperties.getInstanceName());
    System.out.println(MiddlewareServerDynamicProperties.getDirectory());
  }

/**
 * Some class level initialization
 */
  static {
    // Load the properties from file so they are available for each accessor method
    reloadProperties();
  }

/**
 * Don't let anyone instantiate this class.
 */
  private MiddlewareServerDynamicProperties() {
  }

/**
 * Load the Middleware properties from the middleware.server.properties file
 */
  private static final void loadProperties() {
    String strPropertyFilename = System.getProperty("com.ibm.opicmpdh.middleware.middlewareserverdynamicproperties.filename");
    if (strPropertyFilename != null) {
      c_strPropertiesFilename = strPropertyFilename;
    }
    try {
      if (c_propMiddleware == null) {
        c_propMiddleware = new Properties();
        FileInputStream inProperties = new FileInputStream(c_strPropertiesFilename);
        c_propMiddleware.load(inProperties);
        inProperties.close();
      }
    } catch (Exception x) {
      D.ebug(D.EBUG_ERR, "Unable to loadProperties " + x);
    }
  }

/**
 * Reload the properties from the middleware.server.dynamic.properties file
 */
  public static final void reloadProperties() {
    loadProperties();
  }

/**
 * Return the instance name of the current instance of middleware
 */
  public static final String getInstanceName() {
    reloadProperties();
    return c_propMiddleware.getProperty("instance" ,"");
  }

/**
 * Return the actual ip address of the current instance of middleware
 */
  public static final String getIpAddress() {
    reloadProperties();
    return c_propMiddleware.getProperty("actual_ip_address", "");
  }

/**
 * Return the CWD of the current instance of middleware
 */
  public static final String getDirectory() {
    reloadProperties();
    return c_propMiddleware.getProperty("directory", "");
  }

/**
 * Return the hostname of the current instance of middleware
 */
  public static final String getHostname() {
    reloadProperties();
    return c_propMiddleware.getProperty("hostname", "");
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public static final String getVersion() {
    reloadProperties();
    return new String("$Id: MiddlewareServerDynamicProperties.java,v 1.22 2002/04/10 21:06:57 dave Exp $");
  }
}
