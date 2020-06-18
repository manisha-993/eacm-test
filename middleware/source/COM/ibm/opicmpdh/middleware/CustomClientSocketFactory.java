//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CustomClientSocketFactory.java,v $
// Revision 1.65  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.64  2004/10/19 21:00:40  dave
// more client settings
//
// Revision 1.63  2004/07/25 23:44:20  dave
// syntax
//
// Revision 1.62  2004/07/25 23:35:20  dave
// trying to increase backlog on connections (make it controlled)
// by properties file
//
// Revision 1.61  2004/07/25 06:59:46  dave
// track connection timeout
//
// Revision 1.60  2004/07/25 06:40:56  dave
// properties moving
//
// Revision 1.58  2004/07/25 06:18:30  dave
// lets see the client settings
//
// Revision 1.57  2004/07/25 03:58:27  dave
// remove stack trace
//
// Revision 1.56  2004/07/25 03:52:07  dave
// more logging
//
// Revision 1.55  2004/07/25 03:45:23  dave
// moving static
//
// Revision 1.54  2004/07/25 03:23:33  dave
// more RMI config exposure
//
// Revision 1.53  2004/07/25 02:13:34  dave
// dump error
//
// Revision 1.52  2004/07/25 02:02:52  dave
// server trace
//
// Revision 1.51  2004/07/25 01:55:31  dave
// trace to see who is calling Create Socket every 5 minutes
// on the client
//
// Revision 1.50  2004/07/25 00:47:07  dave
// removing some statics
//
// Revision 1.49  2004/07/25 00:43:18  dave
// syntax
//
// Revision 1.48  2004/07/25 00:33:27  dave
// lets see if we can share hashcode and equals
//
// Revision 1.47  2004/07/24 23:50:28  dave
// trying some client settings.. expire the client side
// connection after 30 secs of non use
//
// Revision 1.46  2004/07/24 23:28:00  dave
// Changed a lne from SPEW to detail
//
// Revision 1.45  2004/07/24 23:09:50  dave
// more RMI config control in props
//
// Revision 1.44  2004/03/25 23:22:44  dave
// adding version visibility for clients
//
// Revision 1.43  2004/01/14 19:05:04  dave
// lets see if making socket big helps again
//
// Revision 1.42  2004/01/12 22:37:34  dave
// more squeezing
//
// Revision 1.41  2004/01/12 22:25:14  dave
// backed out custom socket sizing
//
// Revision 1.40  2003/05/27 19:33:53  dave
// putting back Socket Exception
//
// Revision 1.39  2003/05/27 19:32:46  dave
// catching Exception now
//
// Revision 1.38  2003/05/23 19:10:58  dave
// try to make buffer bigger
//
// Revision 1.37  2003/05/23 18:39:28  dave
// setting some trace to see socket buffer size
//
// Revision 1.36  2001/11/29 19:16:04  roger
// Make SSL live!
//
// Revision 1.35  2001/11/20 22:17:32  roger
// We can't add a provider dynamically, need to try with mw.jar in ext
//
// Revision 1.34  2001/11/20 22:06:23  roger
// Chase a bug in SSL
//
// Revision 1.33  2001/11/20 19:29:51  roger
// SSL is now live!
//
// Revision 1.32  2001/11/09 17:24:16  roger
// Need semi
//
// Revision 1.31  2001/11/08 22:16:17  roger
// If SSL not used, do nothing on client side
//
// Revision 1.30  2001/11/06 23:58:43  roger
// Trace statements changed to spew mode
//
// Revision 1.29  2001/11/06 23:21:40  roger
// SSL fixes
//
// Revision 1.28  2001/11/06 21:37:26  roger
// SSL fixes
//
// Revision 1.27  2001/11/06 21:21:32  roger
// SSL fixes
//
// Revision 1.26  2001/11/06 20:47:25  roger
// SSL changes
//
// Revision 1.25  2001/11/06 19:05:59  roger
// Small changes for SSL
//
// Revision 1.24  2001/11/05 16:53:34  roger
// Include code to dynamically register security provider
//
// Revision 1.23  2001/11/02 19:17:11  roger
// Clean up from adding SSL
//
// Revision 1.22  2001/11/02 18:57:12  roger
// Improve parameter name
//
// Revision 1.21  2001/11/02 17:19:49  roger
// Comment out SSL code so nothing is broken
//
// Revision 1.20  2001/11/02 00:02:33  roger
// Trace statement
//
// Revision 1.19  2001/11/01 23:53:19  roger
// Added serialID
//
// Revision 1.18  2001/11/01 23:42:39  roger
// Remove serialID
//
// Revision 1.17  2001/11/01 22:47:54  roger
// Needed default contructors and serialID
//
// Revision 1.16  2001/11/01 21:52:37  roger
// Parameter names were wrong
//
// Revision 1.15  2001/11/01 21:45:41  roger
// Implement SSL socket type
//
// Revision 1.14  2001/10/30 02:29:47  roger
// Small steps toward SSL support
//
// Revision 1.13  2001/08/22 16:52:53  roger
// Removed author RM
//
// Revision 1.12  2001/07/24 20:23:29  roger
// Remove SSL until later
//
// Revision 1.11  2001/07/23 19:57:35  roger
// Changes for SSL support
//
// Revision 1.10  2001/07/23 18:06:04  roger
// Braces wrong
//
// Revision 1.9  2001/07/23 17:34:40  roger
// Changes to support multiple users
//
// Revision 1.8  2001/03/26 16:33:20  roger
// Misc formatting clean up
//
// Revision 1.7  2001/03/21 00:01:07  roger
// Implement java class file branding in getVersion method
//
// Revision 1.6  2001/03/19 20:39:22  roger
// Fixed javadoc @exception tag
//
// Revision 1.5  2001/03/18 15:22:39  roger
// Ensure all classes have getVersion method
//
package COM.ibm.opicmpdh.middleware;

import java.io.IOException;
import java.net.SocketException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 * @version @date
 */
public final class CustomClientSocketFactory implements RMIClientSocketFactory, Serializable {
  // Class constants
  /**
   * @serial
   */
  static final long serialVersionUID = 1L;
  // Class variables
  private static SSLSocketFactory sslFactory = null;
  //private static RMISocketFactory defaultSocketFactory = RMISocketFactory.getDefaultSocketFactory();
  private String m_strSocketType = null;
  private static byte pattern = (byte) 0xAC;



/**
 * Main method which performs a simple test of this class
 * @exception IOException
 */
  public static void main(String[] args) throws IOException {
  }
/**
 * Construct the <code>CustomClientSocketFactory</code> object
 */
  public CustomClientSocketFactory() {
    this("");
  }
/**
 * Construct the <code>CustomClientSocketFactory</code> object
 */
  public CustomClientSocketFactory(String _strSocketType) {
    this.m_strSocketType = _strSocketType;
  }
/**
 * Create a socket
 * @exception IOException
 */
  public Socket createSocket(String _strHost, int _iPort) throws IOException {

    if (m_strSocketType.equalsIgnoreCase("ssl")) {
      if (sslFactory == null) {
        try {
          // Dynamic registration of the SunJSSE Cryptographic Service Provider
          java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        } catch (Exception e) {
          D.ebug(D.EBUG_ERR, "Trouble initializing SSL feature: " + e);
        }

        sslFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      }

      return sslFactory.createSocket(_strHost, _iPort);
    } else {
  //    Socket sock = RMISocketFactory.getDefaultSocketFactory().createSocket(_strHost, _iPort);
      Socket sock = new Socket(_strHost, _iPort);
      try {
        sock.setSendBufferSize(MiddlewareClientProperties.getSocketSendBufferSize());
        sock.setReceiveBufferSize(MiddlewareClientProperties.getSocketReceiveBufferSize());
      } catch (SocketException x) {
        x.printStackTrace();
      }

      System.setProperty("sun.rmi.transport.connectionTimeout", MiddlewareClientProperties.getSunRMITransportConnectionTimeout());

      D.ebug("CCSF.SOCKET:TO:" + System.getProperty("sun.rmi.transport.connectionTimeout") + ":H:" + _strHost + ":P:" + _iPort + ":" + ":S:"  +  sock.getSendBufferSize() + ":R:" + sock.getReceiveBufferSize() + ":SOCK:" + sock);
      return sock;
     }
  }
/**
 * Show the state of the <code>Connections</code> object
 * @return a string representation of this object.
 */
  public String toString() {
    StringBuffer strbResult = new StringBuffer();

    return new String(strbResult);
  }
/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return new String("$Id: CustomClientSocketFactory.java,v 1.65 2008/01/31 22:55:00 wendy Exp $");
  }

/**
* Lets try sharring
*/
  public int hashCode() {
    return (int) pattern;
  }

  public boolean equals(Object obj) {
    return (getClass() == obj.getClass() &&
    pattern == ((CustomClientSocketFactory) obj).pattern);
  }
}
