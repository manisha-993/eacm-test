//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WASPool.java,v $
// Revision 1.32  2006/03/02 00:56:29  dave
// new SQL
//
// Revision 1.31  2005/01/27 03:40:37  dave
// More JTest cleanup
//
// Revision 1.30  2002/04/12 16:42:47  roger
// Parameters are no longer used
//
// Revision 1.29  2002/03/12 19:23:25  roger
// Don't comment out the try!
//
// Revision 1.28  2002/03/12 18:20:29  roger
// Init parameters are no longer used
//
// Revision 1.27  2002/03/08 21:31:22  roger
// Servlet name has no meaning since always = "WASPool"
//
// Revision 1.26  2002/03/08 21:28:18  roger
// Show instance name on status output
//
// Revision 1.25  2002/03/07 20:54:15  roger
// Show name earlier
//
// Revision 1.24  2002/03/07 20:52:55  roger
// Set properties should be the very first thing done
//
// Revision 1.23  2002/03/07 00:34:55  roger
// Lookup the value of the property and show it
//
// Revision 1.22  2002/03/06 23:09:23  roger
// System.exit is trapped in WAS4
//
// Revision 1.21  2002/03/06 22:44:11  roger
// Output depends on properties being set
//
// Revision 1.20  2002/03/06 18:00:50  roger
// Set the properties specified in the servlet init-parameters
//
// Revision 1.19  2002/03/06 17:25:38  roger
// Formatting output
//
// Revision 1.18  2002/03/01 21:15:32  roger
// Make DatabasePool static once again
//
// Revision 1.17  2002/03/01 21:06:19  roger
// Change register name back to WASPool
//
// Revision 1.16  2002/03/01 17:04:23  roger
// Register the DatabasePool under the name of the WASPool servlet and not simply "WASPool"
//
// Revision 1.15  2002/03/01 00:11:54  roger
// Java variables ~need~ a data type!
//
// Revision 1.14  2002/03/01 00:05:20  roger
// A variable name change and status output now shows servlet name
//
// Revision 1.13  2002/02/28 23:41:30  roger
// DatabasePool changed from static
//
// Revision 1.12  2002/02/28 23:21:33  roger
// Need cast
//
// Revision 1.11  2002/02/28 23:14:05  roger
// Prepare for initialization parameters
//
// Revision 1.10  2002/01/24 17:57:36  roger
// Use HttpServletRequest/Response
//
// Revision 1.9  2002/01/23 23:35:00  roger
// Clean up
//
// Revision 1.8  2002/01/22 22:53:02  roger
// Clean up
//
// Revision 1.7  2002/01/22 20:39:16  roger
// Changes to support initial use
//
// Revision 1.6  2002/01/22 18:42:28  roger
// Revert
//
// Revision 1.5  2002/01/22 18:25:16  roger
// DatabasePool is no longer an attribute of the context
//
// Revision 1.4  2002/01/22 00:08:20  roger
// Prepare to hide the DatabasePool
//
// Revision 1.3  2002/01/21 22:20:58  roger
// Remove message
//
// Revision 1.2  2002/01/18 17:07:11  roger
// Clean up
//
// Revision 1.1  2002/01/17 22:08:41  roger
// New files to support WAS Pool
//

package COM.ibm.eannounce.was;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.DatabasePool;

import COM.ibm.opicmpdh.middleware.MiddlewareServerDynamicProperties;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet which creates a pool of <code>Database</code> connections for WAS applications
 * @version @date
 * @see UsePool
 * @see Database
 */
public class WASPool extends HttpServlet {
    private static DatabasePool dbpOPICM = null;

    /**
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     * @author Dave
     */
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        try {
            // Save the DatabasePool as an attribute for re-use
            ServletContext context = getServletConfig().getServletContext();

            // Build a DatabasePool using properties in MiddlewareServerProperties
            dbpOPICM = new DatabasePool();

            // Initialize the Pool
            dbpOPICM.init();
            context.setAttribute("WASPool", dbpOPICM);

        } catch (Exception x) {
            D.ebug("Unable to initialize WASPool: " + x.getMessage());

            throw new ServletException("Unable to initialize WASPool: " + x.getMessage());
        }
    }
    /**
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * @author Dave
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String strInstance = MiddlewareServerDynamicProperties.getInstanceName();
        response.setContentType("text/html");

        // Show the status of the DatabasePool
        out.println("<html><head><title>WASPool (" + strInstance + ") status</title></head><body><pre>WASPool (" + strInstance + ") status<p>" + dbpOPICM.getStatus() + "</pre></body></html>");
        out.close();
    }
    /**
     * @see javax.servlet.Servlet#destroy()
     * @author Dave
     */
    public void destroy() {
        // If there is a DatabasePool shut it down
        if (dbpOPICM != null) {
            dbpOPICM.shutdown();

            dbpOPICM = null;
        }
    }
}
