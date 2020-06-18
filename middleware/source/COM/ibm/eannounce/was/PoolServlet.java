//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PoolServlet.java,v $
// Revision 1.34  2005/01/27 03:40:37  dave
// More JTest cleanup
//
// Revision 1.33  2004/11/05 00:44:21  dave
// code cleanup
//
// Revision 1.32  2004/11/04 22:26:15  dave
// minor fixes
//
// Revision 1.31  2004/11/04 22:13:23  dave
// trying some scoping
//
// Revision 1.30  2002/11/15 19:31:07  dave
// bracket fix
//
// Revision 1.29  2002/11/15 19:18:03  dave
// trapping all possible null pointers
//
// Revision 1.28  2002/11/12 23:50:11  dave
// syntax
//
// Revision 1.27  2002/11/12 23:29:18  dave
// more Cookie info
//
// Revision 1.26  2002/11/12 22:27:59  dave
// syntax
//
// Revision 1.25  2002/11/12 22:19:43  dave
// Seeing if the clients are stuffing cookies into their mouths
//
// Revision 1.24  2002/11/11 20:48:00  dave
// misc fixes
//
// Revision 1.23  2002/11/07 18:05:45  dave
// oops fixing my code
//
// Revision 1.22  2002/11/07 17:48:43  dave
// Seeing what happens if you call let the servlet
// happen .. even when you cannot get a database connection
// (because of user inactivity?)
//
// Revision 1.21  2002/10/16 03:38:30  dave
// Adding strID to free connection to compare values to that of
// get connection.
// EC may be chaining servlets and jsp's together within one
// session.
//
// Revision 1.20  2002/10/02 17:36:43  roger
// Clean up
//
// Revision 1.19  2002/09/30 22:35:58  roger
// getConnection needs parameter for session Id
//
// Revision 1.18  2002/01/24 22:51:29  roger
// No rethrows yet
//
// Revision 1.17  2002/01/24 22:27:40  roger
// Rethrow exceptions which are trapped
//
// Revision 1.16  2002/01/24 22:23:34  roger
// Re-throw exceptions which are logged to mw log file
//
// Revision 1.15  2002/01/24 22:20:29  roger
// Don't acquire a PrintWriter and don't write output and don't write to err file
//
// Revision 1.14  2002/01/24 17:55:26  roger
// Use HttpServletRequest/Response
//
// Revision 1.13  2002/01/23 23:35:00  roger
// Clean up
//
// Revision 1.12  2002/01/22 22:53:01  roger
// Clean up
//
// Revision 1.11  2002/01/22 21:29:30  roger
// SQLException
//
// Revision 1.10  2002/01/22 20:49:43  roger
// Catch SQLException
//
// Revision 1.9  2002/01/22 20:39:16  roger
// Changes to support initial use
//
// Revision 1.8  2002/01/22 18:52:56  roger
// Comment WASPool
//
// Revision 1.7  2002/01/22 18:42:04  roger
// Revert
//
// Revision 1.6  2002/01/22 18:34:23  roger
// Retain WASPool object rather than DatabasePool object
//
// Revision 1.5  2002/01/18 22:29:06  roger
// Change method name
//
// Revision 1.4  2002/01/18 19:15:50  roger
// Simplify package name for MiddlewareException
//
// Revision 1.3  2002/01/18 18:59:07  roger
// Complete package name used for MiddlewareException
//
// Revision 1.2  2002/01/18 18:37:36  roger
// Don't import myself
//
// Revision 1.1  2002/01/18 17:06:52  roger
// New files needed for simplified example
//
//


package COM.ibm.eannounce.was;

import COM.ibm.opicmpdh.middleware.*;


import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;


/**
 * A servlet class which handles the responsibility of get/free of <code>Database</code> objects from <code>DatabasePool</code>
 * @version @date
 * @see WASPool
 * @see DatabasePool
 * @see Database
 * @see PoolServletRequests
 * @see UsePool
 */
abstract public class PoolServlet extends HttpServlet implements PoolServletRequests {

    /**
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * @author Dave
     */
    public final void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatabasePool dbpCurrent = null;
        HttpSession session = null;
        String strId = null;
        Database dbCurrent = null;
        ServletContext sc = getServletContext();

        try {

            if (request != null) {
                // Lets test some stuff out here.
                // lets look at some possible cookies
                Cookie[] aCookie = request.getCookies();
                if (aCookie != null) {
                    D.ebug("Cookie cutter:" + aCookie.length);
                    for (int idwb = 0; idwb < aCookie.length; idwb++) {
                        Cookie ck = aCookie[idwb];
                        if (ck != null) {
                            D.ebug("Cookie:" + idwb + ":" + ck.getName() + ":" + ck.getValue() + ":" + ck.getMaxAge() + ":" + ck.getPath() + ":" + ck.getDomain() + ":" + ck.getSecure() + ":" + ck.getComment());
                        } else {
                            D.ebug("Cookie:" + idwb + ": is null");
                        }
                    }
                }
            } else {
                D.ebug("PoolServlet.service() *** MILD PANIC *** request is null");
            }

            if (sc != null) {

                dbpCurrent = (DatabasePool) sc.getAttribute("WASPool");
                // Ask the pool for a connection to use
                session = request.getSession(false);

                if (session != null) {
                    strId = session.getId() + ".SERVLET";
                } else {
                    strId = "LOSTSESSION";
                    D.ebug("*** Lost the Session Context:.. Possible user timeout");
                }
                if (dbpCurrent != null) {
                    dbCurrent = dbpCurrent.getConnection("unknown Servlet", strId);
                } else {
                    D.ebug("*** unable to acquire a database pool:.. Possible user timeout");
                }
            } else {
                D.ebug("PoolServlet.service() *** MILD PANIC *** ServletContext is null");
            }

            // Call the method on the subclass

            serviceWithDB(request, response, dbCurrent);

        } catch (SQLException sx) {
            D.ebug("SQL Exception: " + sx.getMessage());
        } catch (MiddlewareWaitTimeoutException to) {
            D.ebug("Middleware unable to provide connection: " + to.getMessage());
        } catch (MiddlewareShutdownInProgressException sd) {
            D.ebug("Middleware shut down in progress: " + sd.getMessage());
        } catch (MiddlewareException mx) {
            D.ebug("Middleware exception: " + mx.getMessage());
            //   } catch (NullPointerException np) {
            //      D.ebug("unable to acquire a database pool: " + np.getMessage());
        } finally {
            dbpCurrent.freeConnection(dbCurrent, strId);
        }
    }
}
