//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MiddlewareClientProperties.java,v $
// Revision 1.24  2014/08/21 21:36:24  wendy
// IN5468431 - SSL connection issue for JUI/BUI
//
// Revision 1.23  2004/10/19 21:00:40  dave
// more client settings
//
// Revision 1.22  2004/08/24 23:55:43  dave
// small change to 8102
// and whip
//
// Revision 1.21  2004/07/25 07:12:59  dave
// final tuning
//
// Revision 1.20  2004/07/25 06:59:46  dave
// track connection timeout
//
// Revision 1.19  2004/07/25 04:24:40  dave
// more default changing
//
// Revision 1.18  2004/07/25 03:52:07  dave
// more logging
//
// Revision 1.17  2004/07/25 03:39:27  dave
// more settings
//
// Revision 1.16  2004/07/25 03:23:34  dave
// more RMI config exposure
//
// Revision 1.15  2004/07/25 01:11:52  dave
// increase default timeouts
//
// Revision 1.14  2004/07/25 00:43:18  dave
// syntax
//
// Revision 1.13  2004/07/25 00:33:27  dave
// lets see if we can share hashcode and equals
//
// Revision 1.12  2004/07/24 23:50:28  dave
// trying some client settings.. expire the client side
// connection after 30 secs of non use
//
// Revision 1.11  2001/11/29 19:00:31  roger
// Provide default name for SSL trust store
//
// Revision 1.10  2001/11/29 18:40:09  roger
// Set TrustStore property programatically
//
// Revision 1.9  2001/11/01 21:45:42  roger
// Implement SSL socket type
//
// Revision 1.8  2001/10/30 02:29:47  roger
// Small steps toward SSL support
//
// Revision 1.7  2001/08/22 16:52:57  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:33:21  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:08  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:20  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.util.*;

/**
 * Retrieve configuration properties for <code>Middleware</code>
 * @version @date
 */
public final class MiddlewareClientProperties extends Properties {

  // Class constants
  private static final String PROPERTIES_FILENAME = new String("middleware.client.properties");
  // Class variables
  private static Properties c_propMiddleware = null;


/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {
    System.out.println(MiddlewareClientProperties.getVersion());
    System.out.println(MiddlewareClientProperties.getDatabaseObjectName());
    System.out.println(MiddlewareClientProperties.getObjectConnectIpAddress());
    System.out.println(MiddlewareClientProperties.getObjectConnectPort());
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
  private MiddlewareClientProperties() {
  }

/**
 * Load the Middleware properties from the middleware.client.properties file
 */
  private final static void loadProperties() {
    try {
      if (c_propMiddleware == null) {
        c_propMiddleware = new Properties();
        FileInputStream inProperties = new FileInputStream(PROPERTIES_FILENAME);
        c_propMiddleware.load(inProperties);
      }
    } catch (Exception x) {
      D.ebug(D.EBUG_ERR, "Unable to loadProperties " + x);
    }
  }

/**
 * Reload the properties from the middleware.client.properties file
 */
  public static final void reloadProperties() {
    loadProperties();
  }

/**
 * Return the Middleware RMI Object Name property from the middleware.client.properties file
 * @see Middleware#connect()
 */
  public final static String getDatabaseObjectName() {
    return c_propMiddleware.getProperty("object_name");
  }

/**
 * Return the RMI object IP Address property from the middleware.client.properties file (default = 127.0.0.1)
 * @see Middleware#connect()
 */
  public final static String getObjectConnectIpAddress() {
    return c_propMiddleware.getProperty("object_connect_ip_address", "127.0.0.1");
  }

/**
 * Return the RMI object port property from the middleware.client.properties file (default = 1099)
 * @see Middleware#connect()
 */
  public final static String getObjectConnectPort() {
    return c_propMiddleware.getProperty("object_connect_port", "1099");
  }

/**
 * Return the TrustStore filename for ssl
 */
 public static final String getSSLTrustStore() {
   return c_propMiddleware.getProperty("ssl_truststore", "");//IN5468431  this must have a valid value or it breaks JUI/BUI SSL connection for reports "eatruststore");
 }

 public static final String getSunRMIClientLogCalls() {
   return c_propMiddleware.getProperty("sun.rmi.client.logCalls","false");
 }

  public static final String getSunRMITransportConnectionTimeout() {
    return c_propMiddleware.getProperty("sun.rmi.transport.connectionTimeout","30000");
  }

  public static final String getSunRMIDGCClientGCInterval() {
    return c_propMiddleware.getProperty("sun.rmi.dgc.client.gcInterval","600000");
  }

  public static final String getSunRMIServerLogLevel() {
    return c_propMiddleware.getProperty("sun.rmi.server.logLevel","BRIEF");
  }

  public static final String getSunRMITransportLogLevel() {
    return c_propMiddleware.getProperty("sun.rmi.transport.logLevel","BRIEF");
  }

  public static final int  getSocketSendBufferSize() {
    return Integer.parseInt(c_propMiddleware.getProperty("socket.send.buffersize","64000"));
  }

  public static final int  getSocketReceiveBufferSize() {
    return Integer.parseInt(c_propMiddleware.getProperty("socket.receive.buffersize","64000"));
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return new String("$Id: MiddlewareClientProperties.java,v 1.24 2014/08/21 21:36:24 wendy Exp $");
  }
}
