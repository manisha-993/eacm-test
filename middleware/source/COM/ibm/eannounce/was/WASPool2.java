//
// Copyright (c) 2006, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WASPool2.java,v $
// Revision 1.3  2006/03/10 18:58:12  roger
// Fixes
//
// Revision 1.2  2006/03/10 18:49:47  roger
// Fixes
//
// Revision 1.1  2006/03/10 18:38:34  roger
// New DatabaseAdapter
//

package COM.ibm.eannounce.was;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.DatabaseAdapter;

import COM.ibm.opicmpdh.middleware.MiddlewareServerDynamicProperties;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WASPool2 extends HttpServlet {
    private static DatabaseAdapter dbaOPICM = null;

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        try {
            // Save the DatabaseAdapter as an attribute for re-use
            ServletContext context = getServletConfig().getServletContext();

            // Build a DatabaseAdapter using properties in MiddlewareServerProperties
            dbaOPICM = new DatabaseAdapter();

            context.setAttribute("WASPool2", dbaOPICM);

        } catch (Exception x) {
            D.ebug("Unable to initialize WASPool2: " + x.getMessage());

            throw new ServletException("Unable to initialize WASPool2: " + x.getMessage());
        }
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String strInstance = MiddlewareServerDynamicProperties.getInstanceName();
        response.setContentType("text/html");

        // Show the status of the DatabasePool
//### this is commented out call to status
        out.println("<html><head><title>WASPool2 (" + strInstance + ") status</title></head><body><pre>WASPool2 (" + strInstance + ") status<p>" + "dbaOPICM.getStatus()" + "</pre></body></html>");
        out.close();
    }
    public void destroy() {
    }
}
