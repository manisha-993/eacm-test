//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CustomServerSocketFactory.java,v $
// Revision 1.47  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.46  2004/07/25 23:35:20  dave
// trying to increase backlog on connections (make it controlled)
// by properties file
//
// Revision 1.45  2004/07/25 02:08:24  dave
// synctx
//
// Revision 1.44  2004/07/25 02:02:52  dave
// server trace
//
// Revision 1.43  2004/07/25 00:47:07  dave
// removing some statics
//
// Revision 1.42  2004/07/25 00:43:18  dave
// syntax
//
// Revision 1.41  2004/07/25 00:33:27  dave
// lets see if we can share hashcode and equals
//
// Revision 1.40  2003/05/23 18:51:22  dave
// not supported in 1.3
//
// Revision 1.39  2003/05/23 18:39:28  dave
// setting some trace to see socket buffer size
//
// Revision 1.38  2001/11/29 19:16:17  roger
// Make SSL Live!
//
// Revision 1.37  2001/11/29 16:57:40  roger
// Seems like this can be uncommented and still work
//
// Revision 1.36  2001/11/20 21:14:17  roger
// Clean up for SSL
//
// Revision 1.35  2001/11/06 23:58:43  roger
// Trace statements changed to spew mode
//
// Revision 1.34  2001/11/06 23:21:40  roger
// SSL fixes
//
// Revision 1.33  2001/11/06 21:37:26  roger
// SSL fixes
//
// Revision 1.32  2001/11/06 21:21:32  roger
// SSL fixes
//
// Revision 1.31  2001/11/06 20:47:25  roger
// SSL changes
//
// Revision 1.30  2001/11/06 19:05:59  roger
// Small changes for SSL
//
// Revision 1.29  2001/11/05 16:53:34  roger
// Include code to dynamically register security provider
//
// Revision 1.28  2001/11/02 19:26:27  roger
// What? semicolons aren't optional?
//
// Revision 1.27  2001/11/02 19:19:00  roger
// Another SSL fix
//
// Revision 1.26  2001/11/02 19:17:12  roger
// Clean up from adding SSL
//
// Revision 1.25  2001/11/02 18:57:12  roger
// Improve parameter name
//
// Revision 1.24  2001/11/02 18:54:18  roger
// SSL Support
//
// Revision 1.23  2001/11/02 17:19:48  roger
// Comment out SSL code so nothing is broken
//
// Revision 1.22  2001/11/02 00:02:33  roger
// Trace statement
//
// Revision 1.21  2001/11/01 23:53:18  roger
// Added serialID
//
// Revision 1.20  2001/11/01 23:42:39  roger
// Remove serialID
//
// Revision 1.19  2001/11/01 22:47:54  roger
// Needed default contructors and serialID
//
// Revision 1.18  2001/11/01 22:14:17  roger
// Helpful message
//
// Revision 1.17  2001/11/01 21:52:37  roger
// Parameter names were wrong
//
// Revision 1.16  2001/11/01 21:45:42  roger
// Implement SSL socket type
//
// Revision 1.15  2001/10/30 02:29:47  roger
// Small steps toward SSL support
//
// Revision 1.14  2001/08/22 16:52:54  roger
// Removed author RM
//
// Revision 1.13  2001/07/24 20:23:29  roger
// Remove SSL until later
//
// Revision 1.12  2001/07/23 19:57:35  roger
// Changes for SSL support
//
// Revision 1.11  2001/07/23 18:06:06  roger
// Braces wrong
//
// Revision 1.10  2001/07/23 17:58:13  roger
// Brace wrong
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

import com.sun.net.ssl.KeyManagerFactory;
import com.sun.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;
import java.security.KeyStore;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * @version @date
 */
public final class CustomServerSocketFactory implements RMIServerSocketFactory, Serializable {
  // Class constants
  /**
   * @serial
   */
  static final long serialVersionUID = 1L;
  // Class variables
  private static String c_strKeyFileName = null;
  private static char[] c_acPassPhrase = null;
  private static SSLServerSocketFactory ssf = null;
  private String m_strSocketType = null;
  private static byte pattern = (byte) 0xAC;


/**
 * Main method which performs a simple test of this class
 * @exception IOException
 */
  public static void main(String[] args) throws IOException {
  }
/**
 * Construct the <code>CustomServerSocketFactory</code> object
 */
  public CustomServerSocketFactory() {
    this("", "", "");
  }
/**
 * Construct the <code>CustomServerSocketFactory</code> object
 */
  public CustomServerSocketFactory(String _strSocketType, String _strPassPhrase, String _strKeyFileName) {
    this.m_strSocketType = _strSocketType;
    this.c_strKeyFileName = _strKeyFileName;
    c_acPassPhrase = _strPassPhrase.toCharArray();
  }
/**
 * Create a server socket
 * @exception IOException
 */
  public ServerSocket createServerSocket(int _iPort) throws IOException {

    D.ebug(D.EBUG_SPEW, "Creating RMI socket for socket_type = " + m_strSocketType);

    if (m_strSocketType.equalsIgnoreCase("ssl")) {
      if (ssf == null) {
        try {
          // Dynamic registration of the SunJSSE Cryptographic Service Provider
          java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

          KeyStore ks = KeyStore.getInstance("JKS");

          ks.load(new FileInputStream(c_strKeyFileName), c_acPassPhrase);

          KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");

          kmf.init(ks, c_acPassPhrase);

          SSLContext sslContext = SSLContext.getInstance("TLS");

          sslContext.init(kmf.getKeyManagers(), null, null);

          ssf = sslContext.getServerSocketFactory();
        } catch (Exception e) {
          D.ebug(D.EBUG_ERR, "Trouble initializing SSL feature: " + e);
        }
      }

      return ssf.createServerSocket(_iPort);
    } else {
     // ServerSocket ss = RMISocketFactory.getDefaultSocketFactory().createServerSocket(_iPort);

      int ibacklog = MiddlewareServerProperties.getServerSocketBacklogSize();
      ServerSocket ss = new ServerSocket(_iPort,ibacklog);


      D.ebug("CSSF.SOCKET:Backlog:" + ibacklog + ":Port:" + _iPort + ":SS:" + ss);

      return ss;
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
    return new String("$Id: CustomServerSocketFactory.java,v 1.47 2008/01/31 22:55:00 wendy Exp $");
  }

/**
* Lets try sharring
*/
  public int hashCode() {
    return (int) pattern;
  }

  public boolean equals(Object obj) {
    return (getClass() == obj.getClass() &&
    pattern == ((CustomServerSocketFactory) obj).pattern);
  }

}
