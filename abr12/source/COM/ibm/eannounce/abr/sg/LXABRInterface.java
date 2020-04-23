//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;

import org.w3c.dom.*;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

/**********************************************************************************
* LXABRInterface interface LX* classes will implement this for each entity type
* LXABRSTATUS will launch the correct class
* From "SG FS Inbound Feed Leads 20090108.doc"
*
*
*/
// LXABRInterface.java,v
// Revision 1.2  2009/04/02 19:44:31  wendy
// Output factsheet header when possible
//
// Revision 1.1  2009/01/20 19:40:14  wendy
// CQ00016138-RQ: STG - HVEC EACM Inbound Feed from LEADS - New Feed
// CQ00002984-RQ: STG - EACM Inbound Feed from LEADS - New Feed
//
//
public interface LXABRInterface
{		
    /***********************************************
     *  Validate current data and structure - check for any missing information
     * @param theAbr
	 * @param rootElem
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	void validateData(LXABRSTATUS theAbr, Element rootElem)	
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException;
	
    /***********************************************
     *  Execute - create entities as needed
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws LockException
	 * @throws WorkflowException
	 */
	void execute()
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException;
    /***********************************************
     *  release memory
     */
	void dereference();		
    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
	String getVersion();
	/***********************************************
	 *  Get the title
	 *
	 *@return java.lang.String
	 */
	String getTitle();
	
	/***********************************************
	 *  Get the header
	 *
	 *@return java.lang.String
	 */
	String getHeader();
    /***********************************************
     *  Get the description
     *
     *@return java.lang.String
     */
	String getDescription();
	
}
