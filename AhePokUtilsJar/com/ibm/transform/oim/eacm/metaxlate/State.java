// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;

/**
 * This represents the interface all states have in the Flag Translation Function.
 * Each JSP page is associated with one state. The state handles the actions the
 * JSP page generates. The actions may cause a transition to another state using 
 * the stack of states.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: State.java,v $
 * Revision 1.4  2006/04/17 19:37:16  chris
 * JTest changes
 *
 * Revision 1.3  2006/03/10 20:00:08  chris
 * Make all versions final
 *
 * Revision 1.2  2006/01/26 20:52:51  sergio
 * AHE copyright
 *
 * Revision 1.1  2005/09/16 18:20:10  chris
 * Updates for Application Hosting Environment
 *
 * Revision 1.2  2005/02/16 17:50:21  chris
 * JTest cleanup
 *
 * Revision 1.1.1.1  2004/01/26 17:40:01  chris
 * Latest East Coast Source
 *
 * Revision 1.2  2003/09/04 20:27:08  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.1  2003/06/20 12:50:37  cstolpe
 * Initial 1.2H port
 *
 * Revision 1.0.0.1  2002/06/28 11:33:22  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.4 $
 */
public interface State extends Serializable  {
	/**
	 * CVS Version 
	 */
	String VERSION = "$Revision: 1.4 $";  //$NON-NLS-1$
	/**
	 * Identifier for the complete state.
	 */
	Integer STATE_COMPLETE = new Integer(0);
	/**
	 * Identifier for the original state.
	 */
	Integer STATE_ORIGINAL = new Integer(1);
	/**
	 * Identifier for the submit2 state.
	 */
	Integer STATE_SUBMIT2 = new Integer(2);
	/**
	 * Identifier for the validate1 state.
	 */
	Integer STATE_VALIDATE1 = new Integer(3);
	/**
	 * Identifier for the validate2 state.
	 */
	Integer STATE_VALIDATE2 = new Integer(4);
	/**
	 * Identifier for the validate3 state.
	 */
	Integer STATE_VALIDATE3 = new Integer(5);
	/**
	 * Identifier for the edit state.
	 */
	Integer STATE_EDIT = new Integer(6);
	/**
	 * Identifier for the index state.
	 */
	Integer STATE_INDEX = new Integer(7);
	/**
	 * Performs the action specified by the <b>action</b> parameter in the parameterMap.
	 * Handles state transitions by pushing or popping  states from the
	 * stack of states.
	 * @param parameterMap Contains all parameters from the HTTP request.
	 * @throws COM.ibm.eannounce.objects.WorkflowException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws java.sql.SQLException
	 */
	void doAction(java.util.Map parameterMap) 
	throws 
		COM.ibm.eannounce.objects.WorkflowException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		java.sql.SQLException;
	/**
	 * Get the ID of the state.
	 * @return Integer
	 */
	Integer getStateID();
	/**
	 * Get the stack of states.
	 * @return java.util.Stack
	 */
	java.util.Stack getStack();
}
