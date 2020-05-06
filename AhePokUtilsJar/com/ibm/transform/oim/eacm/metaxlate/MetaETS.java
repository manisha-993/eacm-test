// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.transactions.*;

/**
 * This is the Controller for the Flag Translation Function. It delegates the
 * action required to the State sublcasses. They perform all processing. It then
 * passes the state to appropriate view (JSP's) for display
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: MetaETS.java,v $
 * Revision 1.5  2008/07/24 20:39:01  wendy
 * CQ00006253 - fix servlet redirection to Login.wss and use Constants to find ProfileSet
 *
 * Revision 1.4  2008/01/22 18:33:28  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.3  2006/04/17 19:37:16  chris
 * JTest changes
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
 * Revision 1.9  2003/09/26 18:15:55  cstolpe
 *  Fix for FB 52420:47D24B
 *
 * Revision 1.8  2003/09/11 21:57:03  cstolpe
 * Compiler Version Issue!
 *
 * Revision 1.7  2003/09/11 21:45:28  cstolpe
 * Latest Updates
 *
 * Revision 1.6  2003/09/04 20:27:07  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.5  2003/06/20 12:50:37  cstolpe
 * Initial 1.2H port
 *
 * Revision 1.4  2002/08/13 18:20:43  cstolpe
 * Throw middleware exceptions as ServletExceptions
 *
 * Revision 1.3  2002/08/09 18:13:17  cstolpe
 * Changed Constants() constructor.
 *
 * Revision 1.2  2002/08/07 16:08:07  cstolpe
 * CR0718026417 (CR0625023357) Multiple urls for V1.0.1
 * TODO: correct Constants() constructor when available.
 *
 * Revision 1.1  2002/07/09 13:05:37  cstolpe
 * Fixes for PR20618, PR20618, PR20644. Reset to status on profile change or return from e-announce
 *
 * Revision 1.0.0.1  2002/06/28 11:33:22  cstolpe
 * Initialize
 *
 * </pre>
 * @see State
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class MetaETS extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Automatically generated javadoc for: HTTPFORMDATAINDEX */
    private static final int HTTPFORMDATAINDEX = 38;
    private static Map stateToJSP = new HashMap();
	private static Map urlToState = new HashMap();
	private static Map raikToNLS = new HashMap();
	/**
	 * CVS Version
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	// Static initializer
	{
		stateToJSP.put(State.STATE_COMPLETE,  "MXlateComplete.jsp");  //$NON-NLS-1$
		stateToJSP.put(State.STATE_EDIT,      "MXlateEdit.jsp");  //$NON-NLS-1$
		stateToJSP.put(State.STATE_INDEX,     "MXlateIndex.jsp");  //$NON-NLS-1$
		stateToJSP.put(State.STATE_ORIGINAL,  "MXlateOriginal.jsp");  //$NON-NLS-1$
		stateToJSP.put(State.STATE_SUBMIT2,   "MXlateSubmit2.jsp");  //$NON-NLS-1$
		stateToJSP.put(State.STATE_VALIDATE1, "MXlateValidate1.jsp");  //$NON-NLS-1$
		stateToJSP.put(State.STATE_VALIDATE2, "MXlateValidate2.jsp");  //$NON-NLS-1$
		stateToJSP.put(State.STATE_VALIDATE3, "MXlateValidate3.jsp");  //$NON-NLS-1$

		urlToState.put("MetaTranslationSubmission", State.STATE_SUBMIT2);  //$NON-NLS-1$
		urlToState.put("MetaTranslationValidation", State.STATE_VALIDATE1);  //$NON-NLS-1$
		urlToState.put("MetaTranslationViewSent", State.STATE_ORIGINAL);  //$NON-NLS-1$
		urlToState.put("MetaTranslationViewPosted", State.STATE_COMPLETE);  //$NON-NLS-1$
		urlToState.put("MetaTranslationSubmission.wss", State.STATE_SUBMIT2);  //$NON-NLS-1$
		urlToState.put("MetaTranslationValidation.wss", State.STATE_VALIDATE1);  //$NON-NLS-1$
		urlToState.put("MetaTranslationViewSent.wss", State.STATE_ORIGINAL);  //$NON-NLS-1$
		urlToState.put("MetaTranslationViewPosted.wss", State.STATE_COMPLETE);  //$NON-NLS-1$

		raikToNLS.put("2", Profile.GERMAN_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("3", Profile.ITALIAN_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("4", Profile.JAPANESE_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("5", Profile.FRENCH_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("6", Profile.SPANISH_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("7", Profile.UK_ENGLISH_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("8", Profile.KOREAN_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("9", Profile.CHINESE_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("10", Profile.FRENCH_CANADIAN_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("11", Profile.CHINESE_SIMPLIFIED_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("12", Profile.SPANISH_LATINAMERICAN_LANGUAGE);  //$NON-NLS-1$
		raikToNLS.put("13", Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);  //$NON-NLS-1$
	}
	/**
     * Overide doPost to invoke doAction
     *
     * @concurrency $none
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
	public void doPost(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
	throws
		javax.servlet.ServletException,
		java.io.IOException
	{
		Database dbCurrent = null;
		// Get a database for the scope of this method
		// Get the pool from this session
		Object dbpAttribute = getServletContext().getAttribute("WASPool");  //$NON-NLS-1$
		DatabasePool dbpCurrent;
		if (dbpAttribute != null && dbpAttribute instanceof DatabasePool) {
			// Get the session Id
			String strPurpose = getServletName();
			StringBuffer sbId = new StringBuffer("."); //$NON-NLS-1$
			dbpCurrent = (DatabasePool) dbpAttribute;
			sbId.append(strPurpose);
			sbId.insert(0, request.getSession().getId());
			try {
				String view;
				synchronized (this) {
					// Ask the pool for a connection to use
					// It will be freeed when at the end of this method
					dbCurrent = dbpCurrent.getConnection(strPurpose, sbId.toString());
				}
				view = doAction(request, dbCurrent);
				if (dbpCurrent != null) {
					dbpCurrent.freeConnection(dbCurrent, sbId.toString());
					dbpCurrent = null;
				}
				// Go
				if (view.endsWith("Navigate.jsp")) {  //$NON-NLS-1$
					response.sendRedirect(view);
				}
				else {
					response.setHeader("Content-Type", "text/html; charset=UTF-8");  //$NON-NLS-1$  //$NON-NLS-2$
					getServletContext().getRequestDispatcher(view).forward(request, response);
				}
			}
			catch(java.sql.SQLException e) {
				throw new javax.servlet.ServletException(e);
			}
			catch(COM.ibm.eannounce.objects.EANBusinessRuleException e) {
				throw new javax.servlet.ServletException(e);
			}
			catch(MiddlewareShutdownInProgressException e) {
				throw new javax.servlet.ServletException(e);
			}
			catch(MiddlewareException e) {
				throw new javax.servlet.ServletException(e);
			}
			catch(WorkflowException e) {
				throw new javax.servlet.ServletException(e);
			}
			finally {
				// Free the connection before going to the view
				if (dbpCurrent != null) {
					dbpCurrent.freeConnection(dbCurrent, sbId.toString());
				}
			}
		}
		else {
			if (dbpAttribute == null) {
				throw new ServletException("WASPool is not in the session"); //$NON-NLS-1$
			}
			else {
				throw new ServletException("WASPool is not an instance of DatabasePool"); //$NON-NLS-1$
			}
		}
	}
	/**
     * Overide doGet to invoke doAction
     *
     * @concurrency $none
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
	public void doGet(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
	throws
		javax.servlet.ServletException,
		java.io.IOException
	{
		Database dbCurrent = null;
		// Get a database for the scope of this method
		// Get the pool from this session
		Object dbpAttribute = getServletContext().getAttribute("WASPool");  //$NON-NLS-1$
		DatabasePool dbpCurrent;
		if (dbpAttribute instanceof DatabasePool) {
			// Get the session Id
			String strPurpose = getServletName();
			StringBuffer sbId = new StringBuffer("."); //$NON-NLS-1$
			dbpCurrent = (DatabasePool) dbpAttribute;
			sbId.append(strPurpose);
			sbId.insert(0, request.getSession().getId());
			try {
				String view;
				synchronized (this) {
					// Ask the pool for a connection to use
					// It will be freeed when at the end of this method
					dbCurrent = dbpCurrent.getConnection(strPurpose, sbId.toString());
				}
				view = doAction(request, dbCurrent);
				if (dbpCurrent != null) {
					dbpCurrent.freeConnection(dbCurrent, sbId.toString());
					dbpCurrent = null;
				}
				// Go
				if (view.endsWith("Navigate.jsp")) {  //$NON-NLS-1$
					response.sendRedirect(view);
				}
				else {
					response.setHeader("Content-Type", "text/html; charset=UTF-8");  //$NON-NLS-1$  //$NON-NLS-2$
					getServletContext().getRequestDispatcher(view).forward(request, response);
				}
			}
			catch(java.sql.SQLException e) {
				throw new javax.servlet.ServletException(e);
			}
			catch(COM.ibm.eannounce.objects.EANBusinessRuleException e) {
				throw new javax.servlet.ServletException(e);
			}
			catch(MiddlewareShutdownInProgressException e) {
				throw new javax.servlet.ServletException(e);
			}
			catch(MiddlewareException e) {
				throw new javax.servlet.ServletException(e);
			}
			catch(WorkflowException e) {
				throw new javax.servlet.ServletException(e);
			}
			finally {
				// Free the connection before going to the view
				if (dbpCurrent != null) {
					dbpCurrent.freeConnection(dbCurrent, sbId.toString());
				}
			}
		}
		else {
			if (dbpAttribute == null) {
				throw new ServletException("WASPool is not in the session"); //$NON-NLS-1$
			}
			else {
				throw new ServletException("WASPool is not an instance of DatabasePool"); //$NON-NLS-1$
			}
		}
	}
	/**
	 * Delegate the action to the current state
	 */
	private String doAction(javax.servlet.http.HttpServletRequest request,Database dbCurrent)
	throws
		COM.ibm.eannounce.objects.WorkflowException,
		javax.servlet.ServletException,
		java.io.IOException,
		java.sql.SQLException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		MiddlewareShutdownInProgressException,
		MiddlewareException
	{
		HttpSession session;
		ProfileSet profileSet;
		String entityType;
		String entityId;
		String raik;
		Stack stackState;
		State state;
		String view;
		boolean initialize;

		com.ibm.transform.oim.eacm.bui.webapp.Constants constants = new com.ibm.transform.oim.eacm.bui.webapp.Constants(request);
		request.setCharacterEncoding("UTF-8");  //$NON-NLS-1$
		session = request.getSession();
		// Get the parameter(s)
		profileSet = (ProfileSet) session.getAttribute(constants.getSESProfileSet());
		entityType = request.getParameter("entityType");  //$NON-NLS-1$
		entityId = request.getParameter("entityID");  //$NON-NLS-1$
		raik = request.getParameter("raik");  //$NON-NLS-1$
		stackState = (Stack) session.getAttribute("stateStack");  //$NON-NLS-1$

		// If the session was lost the user will have to login again
		if (profileSet == null) {
			view = "/Login.wss";  //$NON-NLS-1$
		}
		else {
			state = null; // current state
			// initialize the stack when first accessed or invoked from Navigate.jsp
			initialize = (raik != null);
			if (stackState != null) {
				Object unknownState = stackState.firstElement();
				Profile currentProfile = profileSet.getActiveProfile();
				// URL must match state
				if (!((State) unknownState).getStateID().equals(urlToState.get(getServletName()))) {
					initialize = true;
				}
				// Profile Language must match Package Language
				if (unknownState instanceof OriginalState) {
					OriginalState original = (OriginalState) unknownState;
					if (!original.getProfile().equals(currentProfile)) {
						original.setProfile(currentProfile);
					}
				}
				else if (unknownState instanceof Submit2State) {
					Submit2State submit = (Submit2State) unknownState;
					if (!submit.getProfile().equals(currentProfile)) {
						submit.setProfile(currentProfile);
					}
				}
				else if (unknownState instanceof CompleteState) {
					CompleteState complete = (CompleteState) unknownState;
					if (!complete.getProfile().equals(currentProfile)) {
						complete.setProfile(currentProfile);
					}
				}
				else if (unknownState instanceof Validate1State) {
					Validate1State validate = (Validate1State) unknownState;
					if (!validate.getProfile().equals(currentProfile)) {
						validate.setProfile(currentProfile);
					}
				}
			}
			else {
				initialize = true;
			}
			// State ID of first element should
			// or if the active profile has changed
			if (initialize) {
				if (stackState != null) {
					stackState.clear();
				}
				switch (((Integer) urlToState.get(getServletName())).intValue()) {
				case 0: // complete
					// Initialize to status state
					state = new CompleteState();
					((CompleteState)state).init(dbCurrent,
							profileSet.getActiveProfile(),
							entityType,
							Integer.parseInt(entityId),
							getNLS(raik));
					break;
				case 1: // original
					// Initialize to status state
					state = new OriginalState();
					((OriginalState)state).init(
							dbCurrent,
							profileSet.getActiveProfile(),
							entityType,
							Integer.parseInt(entityId),
							getNLS(raik));
					break;
				case 2: // submit
					// Initialize to status state
					state = new Submit2State();
					((Submit2State) state).init(
							dbCurrent,
							profileSet.getActiveProfile(),
							entityType,
							Integer.parseInt(entityId));
					break;
				case 3: // validate
					// Initialize to status state
					state = new Validate1State();
					((Validate1State)state).init(
							dbCurrent,
							profileSet.getActiveProfile(),
							entityType,
							Integer.parseInt(entityId),
							getNLS(raik));
				default :
					break;
				}
				// Save the stack of states to the session
				stackState = state.getStack();
				session.setAttribute("stateStack", stackState);  //$NON-NLS-1$
			}
			else {
				// Get all HTTP request parameters
				Map params = new HashMap();
				if (request.getContentType() != null &&
					request.getContentType().startsWith("multipart/form-data"))  //$NON-NLS-1$
				{
					String boundry = request.getContentType();
					java.io.BufferedReader s;
					boundry = boundry.substring(boundry.indexOf("boundary=") + 9);  //$NON-NLS-1$
					s = request.getReader();
					if (s != null) {
						String line = s.readLine();
						while (line != null) {
							if (line.startsWith("Content-Disposition: form-data; name=\"")) {  //$NON-NLS-1$
								String fieldName = line.substring(HTTPFORMDATAINDEX, line.indexOf('"', HTTPFORMDATAINDEX));
								StringBuffer fieldValue;
								line = s.readLine();
								fieldValue = new StringBuffer();
								while (line != null && line.indexOf(boundry) < 0) {
									fieldValue.append(line);
									line = s.readLine();
								}
								params.put(fieldName, fieldValue.toString());
							}
							line = s.readLine();
						}
					}
				}
				else {
					Enumeration e = request.getParameterNames();
					while (e.hasMoreElements()) {
						String pName = (String) e.nextElement();
						String[] pValues = request.getParameterValues(pName);
						if (pValues != null) {
							if (pValues.length == 1) {
								params.put(pName, pValues[0]);
							}
							else {
								params.put(pName, pValues);
							}
						}
					}
					if (request.getParameter("action") == null) {  //$NON-NLS-1$
						while (stackState.size() > 1) {
							stackState.pop();
						}
					}
				}
				// Perform the action in the request
				// Transition to a new state if necessary
				state = (State) stackState.peek(); // current state
				// Pass the Database connection
				params.put("dbCurrent", dbCurrent);  //$NON-NLS-1$
				state.doAction(params);
			}
			if (stackState != null && stackState.size() == 0) {
				// An action was taken that requires us to return to where we were invoked
				session.removeAttribute("stateStack");  //$NON-NLS-1$
				view = "Navigate.jsp";  //$NON-NLS-1$
			}
			else {
				// Get state again in case action causes state change
				state = (State) stackState.peek();
				// Pass state to view for display
				request.setAttribute("state", state);  //$NON-NLS-1$
				// Pass servlet name
				request.setAttribute("servletName", getServletName());  //$NON-NLS-1$
				// Determine what view to go to
				view = (String) stateToJSP.get(state.getStateID());
			}
		}
		return view;
	}

	private static NLSItem getNLS(String raik) {
		int i = raik.length() - 1; // Point to last character
		while (Character.isDigit(raik.charAt(i - 1))) { // If prior character is a digit
			i--; // Point to it
		}
		return (NLSItem) raikToNLS.get(raik.substring(i));
	}
}
