//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: UsePool.java,v $
// Revision 1.18  2005/01/27 03:40:37  dave
// More JTest cleanup
//
// Revision 1.17  2004/11/04 22:26:15  dave
// minor fixes
//
// Revision 1.16  2002/01/24 22:38:49  roger
// Need import
//
// Revision 1.15  2002/01/24 22:27:40  roger
// Rethrow exceptions which are trapped
//
// Revision 1.14  2002/01/24 17:55:26  roger
// Use HttpServletRequest/Response
//
// Revision 1.13  2002/01/22 22:53:02  roger
// Clean up
//
// Revision 1.12  2002/01/22 21:44:51  roger
// Purpose causing trouble
//
// Revision 1.11  2002/01/22 21:38:03  roger
// serviceWithDB
//
// Revision 1.10  2002/01/18 22:29:06  roger
// Change method name
//
// Revision 1.9  2002/01/18 19:15:50  roger
// Simplify package name for MiddlewareException
//
// Revision 1.8  2002/01/18 18:59:07  roger
// Complete package name used for MiddlewareException
//
// Revision 1.7  2002/01/18 18:22:18  roger
// Package/import needs
//
// Revision 1.6  2002/01/18 17:48:49  roger
// Need to declare throws so parent catch handle MiddlewareException by default
//
// Revision 1.5  2002/01/18 17:30:14  roger
// Needed imports
//
// Revision 1.4  2002/01/18 17:23:47  roger
// Need to extend the proper class
//
// Revision 1.3  2002/01/18 17:17:41  roger
// Missing brace
//
// Revision 1.2  2002/01/18 17:07:32  roger
// Simplify
//
// Revision 1.1  2002/01/17 22:08:41  roger
// New files to support WAS Pool
//


package COM.ibm.eannounce.was;


//import COM.ibm.eannounce.was.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;


/**
 * An example servlet which uses the WAS pool of <code>Database</code> connections
 * @version @date
 * @see WASPool
 * @see Database
 */
public class UsePool extends PoolServlet {
    /**
     * @see COM.ibm.eannounce.was.PoolServletRequests#serviceWithDB(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, COM.ibm.opicmpdh.middleware.Database)
     * @author Dave
     */
    public void serviceWithDB(HttpServletRequest request, HttpServletResponse response, Database _dbCurrent) throws ServletException, IOException, MiddlewareException, SQLException {
        // Identify what we are calling so Pool status will show what is running
        PrintWriter out = response.getWriter();
        DatePackage pkgDate = null;

        _dbCurrent.setPurpose("unknown");

        // Initialize output
        response.setContentType("text/html");
        out.println("<html>" + "<head><title>UsePool Test</title></head><body><pre>");

        // Make a MW call
        pkgDate = _dbCurrent.getDates();

        // Show results from call
        out.println("Now:     " + pkgDate.m_strNow);
        out.println("Forever: " + pkgDate.m_strForever);
        out.println("Epoch:   " + pkgDate.m_strEpoch);

        // Finish output
        out.println("</pre></body></html>");
        out.close();
    }
}
