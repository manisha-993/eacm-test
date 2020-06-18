//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PoolServletRequests.java,v $
// Revision 1.15  2005/01/27 03:40:37  dave
// More JTest cleanup
//
// Revision 1.14  2004/11/04 22:19:08  dave
// need interface changes
//
// Revision 1.13  2002/01/24 22:38:49  roger
// Need import
//
// Revision 1.12  2002/01/24 22:27:40  roger
// Rethrow exceptions which are trapped
//
// Revision 1.11  2002/01/24 17:56:02  roger
// Use HttpServletRequest/Response
//
// Revision 1.10  2002/01/22 22:53:02  roger
// Clean up
//
// Revision 1.9  2002/01/22 20:39:16  roger
// Changes to support initial use
//
// Revision 1.8  2002/01/22 19:34:31  roger
// Need service method
//
// Revision 1.7  2002/01/18 22:29:06  roger
// Change method name
//
// Revision 1.6  2002/01/18 19:15:50  roger
// Simplify package name for MiddlewareException
//
// Revision 1.5  2002/01/18 19:06:58  roger
// Complete package name for MiddlewareException
//
// Revision 1.4  2002/01/18 17:55:14  roger
// Needed MWException on throws
//
// Revision 1.3  2002/01/18 17:37:51  roger
// Needed imports and throws
//
// Revision 1.2  2002/01/18 17:17:22  roger
// Needed parms and imports
//
// Revision 1.1  2002/01/18 17:06:52  roger
// New files needed for simplified example
//
//


package COM.ibm.eannounce.was;

import COM.ibm.opicmpdh.middleware.MiddlewareException;


import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import COM.ibm.opicmpdh.middleware.Database;


/**
 * An interface which specifies methods a <code>PoolServlet</code> must implement
 * @version @date
 * @see PoolServlet
 * @see UsePool
 */
public interface PoolServletRequests {
    /**
     * @param request
     * @param response
     * @param _database
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     * @author Dave
     */
    void serviceWithDB(HttpServletRequest request, HttpServletResponse response, Database _database) throws ServletException, IOException, MiddlewareException, SQLException;
}
