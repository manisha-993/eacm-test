//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: middleware.txt,v $
// Revision 1.35  2014/08/21 21:36:07  wendy
// IN5468431 - SSL connection issue for JUI/BUI
//
// Revision 1.34  2005/03/11 19:50:50  roger
// New method for MW versioning
//
// Revision 1.33  2005/02/08 20:47:25  roger
// jTest indent
//
// Revision 1.32  2004/08/24 23:55:43  dave
// small change to 8102
// and whip
//
// Revision 1.31  2004/07/26 01:13:39  dave
// litle sleep modification
//
// Revision 1.30  2004/07/25 18:14:26  dave
// added a small sleep to the end of the disconnect
//
// Revision 1.29  2004/07/25 06:40:08  dave
// re-arranging local settings
//
// Revision 1.28  2004/07/25 05:53:54  dave
// need a try catch
//
// Revision 1.27  2004/07/25 05:44:36  dave
// and again
//
// Revision 1.26  2004/07/25 05:37:15  dave
// try againg :-) unexport
//
// Revision 1.25  2004/07/25 05:26:13  dave
// another cast?
//
// Revision 1.24  2004/07/25 05:19:23  dave
// minor fix
//
// Revision 1.23  2004/07/25 05:13:52  dave
// unexportObject(this,true)
//
// Revision 1.22  2001/11/29 18:40:08  roger
// Set TrustStore property programatically
//
// Revision 1.21  2001/09/07 22:26:08  roger
// Remove imports of login package
//
// Revision 1.20  2001/08/29 22:52:13  roger
// Fixes for LDAP
//
// Revision 1.19  2001/08/29 22:24:01  roger
// Put LDAP feature in database instead
//
// Revision 1.18  2001/08/24 00:25:38  roger
// Trouble getting member variable use method instead
//
// Revision 1.17  2001/08/24 00:12:36  roger
// If no ldap_server configured, we can't authenticate against one
//
// Revision 1.16  2001/08/24 00:11:35  roger
// Use actual property instead of hard-coded test value
//
// Revision 1.15  2001/08/23 23:52:39  roger
// Fixes
//
// Revision 1.14  2001/08/23 23:46:21  roger
// Add the LDAP code
//
// Revision 1.13  2001/08/23 23:32:33  roger
// Needed throws
//
// Revision 1.12  2001/08/23 23:24:34  roger
// Fixes
//
// Revision 1.11  2001/08/23 23:05:15  roger
// New login method
//
// Revision 1.10  2001/08/23 22:57:01  roger
// Make an use Middleware.login
//
// Revision 1.9  2001/08/22 16:54:08  roger
// Removed author RM
//
// Revision 1.8  2001/03/21 16:12:38  roger
// Tweaked and/or added getVersion method
//
// Revision 1.7  2001/03/16 03:48:57  roger
// Misc clean up
//
// Revision 1.6  2001/03/16 03:18:48  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.util.*;
import java.rmi.*;
import java.net.*;

/**
 * <code>Middleware</code>
 * @version 2020-06-15-05.16.43.621000
 */

public final class Middleware {

    private static String c_strSSLTrustStore = MiddlewareClientProperties.getSSLTrustStore();

    /**
    * Don't let anyone instantiate this class.
    */
    private Middleware() {}

    /**
    * Connect to the configured <code>Middleware</code> server
    * @see MiddlewareClientProperties
    */
    public static final RemoteDatabaseInterface connect() throws RemoteException {
        return Middleware.connect(MiddlewareClientProperties.getObjectConnectIpAddress(), Integer.parseInt(MiddlewareClientProperties.getObjectConnectPort()), MiddlewareClientProperties.getDatabaseObjectName());
    }

    /**
    * Connect to the specified <code>Middleware</code> server
    */
    public static final RemoteDatabaseInterface connect(String strIpAddress, int iPort, String strDatabaseObject) throws RemoteException {

        RemoteDatabaseInterface ro = null;

        if(c_strSSLTrustStore !=null && c_strSSLTrustStore.trim().length()>0){
        	//IN5468431 this must have a valid value or it breaks JUI/BUI SSL connection for reports
        	System.setProperty("javax.net.ssl.trustStore", c_strSSLTrustStore);
        }
        System.setProperty("sun.rmi.dgc.client.gcInterval", MiddlewareClientProperties.getSunRMIDGCClientGCInterval());
        System.setProperty("sun.rmi.transport.logLevel", MiddlewareClientProperties.getSunRMITransportLogLevel());
        System.setProperty("sun.rmi.client.logCalls", MiddlewareClientProperties.getSunRMIClientLogCalls());
        System.setProperty("sun.rmi.server.logLevel", MiddlewareClientProperties.getSunRMIServerLogLevel());
        System.setProperty("sun.rmi.transport.connectionTimeout", MiddlewareClientProperties.getSunRMITransportConnectionTimeout());

        // display All System Properties
        System.getProperties().list(System.out);


        try {
            ro = (RemoteDatabaseInterface)Naming.lookup("rmi://"
                + strIpAddress +
                ":" +
                iPort +
                "/" +
                strDatabaseObject);
        }
        catch (NotBoundException nbx) {
            throw new RemoteException("Can't connect to server " + nbx);
        }
        catch (MalformedURLException mux) {
            throw new RemoteException("Bad connection URL " + mux);
        }
        catch (Exception e) {
            System.out.println("Naming.Lookup exception occurred " + e);
        }
        return ro;
    }

    /**
    * Disconnect from the <code>Middleware</code> server
    */
    public static final void disconnect(RemoteDatabaseInterface ro) {
        try {
            java.rmi.server.UnicastRemoteObject.unexportObject((Remote)ro,true);
        } catch (NoSuchObjectException ex) {
            System.out.println("No Such Object to unExport");
        }
        ro = null;

        // Sleep abit
        try {
            //Sleep a bit
            Thread.sleep(10000);
        } catch (Exception x) {}
    }

    /**
    * @return the date/time this class was generated
    */
    public final static String getVersion() {
        return new String("$Id: middleware.txt,v 1.35 2014/08/21 21:36:07 wendy Exp $");
    }

    public final static String getMiddlewareVersion() {
        return "2020-06-15-05.16.43.621000";
    }
}
