//
// Copyright (c) 2006, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: UsePool2.java,v $
// Revision 1.1  2006/03/10 21:12:52  roger
// For new DatabaseAdapter
//

package COM.ibm.eannounce.was;


import COM.ibm.opicmpdh.middleware.MiddlewareException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;

public class UsePool2 extends PoolServlet2 {
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
