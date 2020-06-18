//
// Copyright (c) 2006, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PoolServlet2.java,v $
// Revision 1.15  2006/05/17 21:00:54  roger
// Changes
//
// Revision 1.14  2006/05/17 20:52:59  roger
// Changes
//
// Revision 1.13  2006/05/17 20:44:32  roger
// Changes for new WAS pooling
//
// Revision 1.12  2006/05/16 21:44:24  roger
// Trying to find trouble
//
// Revision 1.11  2006/05/04 16:29:05  roger
// Changes for new WAS pooling
//
// Revision 1.10  2006/05/01 19:47:39  roger
// Changed the JNDI name for PDH
//
// Revision 1.9  2006/03/20 23:01:51  roger
// Fixes
//
// Revision 1.8  2006/03/13 17:40:29  roger
// Fix
//
// Revision 1.7  2006/03/13 17:18:10  roger
// Fix
//
// Revision 1.6  2006/03/13 17:04:07  roger
// Fix
//
// Revision 1.5  2006/03/13 17:01:57  roger
// Fix
//
// Revision 1.4  2006/03/13 16:43:29  roger
// Get connection
//
// Revision 1.3  2006/03/10 18:58:13  roger
// Fixes
//
// Revision 1.2  2006/03/10 18:49:47  roger
// Fixes
//
// Revision 1.1  2006/03/10 18:38:34  roger
// New DatabaseAdapter
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

abstract public class PoolServlet2 extends HttpServlet implements PoolServletRequests {
  public final void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    DatabaseAdapter dbaCurrent = null;
    HttpSession session = null;
    String strId = null;
    Database dbCurrent = null;
    ServletContext sc = getServletContext();
    java.sql.Connection connPDH = null;
    java.sql.Connection connODS = null;

    try {
      java.util.Properties parms = new java.util.Properties();

      parms.setProperty(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");

      javax.naming.Context ctx = new javax.naming.InitialContext(parms);
      javax.sql.DataSource dsPDH = (javax.sql.DataSource) ctx.lookup("jdbc/ALPHAPDH");

      connPDH = dsPDH.getConnection();

      javax.sql.DataSource dsODS = (javax.sql.DataSource) ctx.lookup("jdbc/ALPHAODS");

      connODS = dsODS.getConnection();

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
        D.ebug("PoolServlet2.service() *** MILD PANIC *** request is null");
      }

      if (sc != null) {
        dbaCurrent = (DatabaseAdapter) sc.getAttribute("WASPool2");

        if (dbaCurrent == null) {
          D.ebug("PoolServlet2.service() dbaCurrent is null ... this is BAD");
        }

        // Ask the pool for a connection to use
        session = request.getSession(false);

        if (session != null) {
          strId = session.getId() + ".SERVLET";
        } else {
          strId = "LOSTSESSION";

          D.ebug("*** Lost the Session Context:.. Possible user timeout");
        }

        if (dbaCurrent != null) {
          dbCurrent = dbaCurrent.getConnection("unknown Servlet", connPDH, connODS, strId);
        } else {
          D.ebug("*** unable to acquire a database:.. Possible user timeout");
        }
      } else {
        D.ebug("PoolServlet2.service() *** MILD PANIC *** ServletContext is null");
      }

      // Call the method on the subclass
      serviceWithDB(request, response, dbCurrent);
      // close the pdh and ods connections
      connPDH.close();
      connODS.close();
    } catch (SQLException sx) {
      D.ebug("SQL Exception: " + sx.getMessage());
    } catch (MiddlewareWaitTimeoutException to) {
      D.ebug("Middleware unable to provide connection: " + to.getMessage());
    } catch (MiddlewareShutdownInProgressException sd) {
      D.ebug("Middleware shut down in progress: " + sd.getMessage());
    } catch (MiddlewareException mx) {
      D.ebug("Middleware exception: " + mx.getMessage());
    } catch (javax.naming.NamingException nx) {
      D.ebug("Naming exception: " + nx.getMessage());
    } finally {
      dbaCurrent.freeConnection(dbCurrent, strId);
    }
  }
}
