// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;
import java.util.*;
import COM.ibm.opicmpdh.translation.*;

/**
 * Implements the second step in the validation process. In this step we check
 * for any values that are too long to be stored in the PDH.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: Validate2State.java,v $
 * Revision 1.5  2006/04/17 19:37:15  chris
 * JTest changes
 *
 * Revision 1.4  2006/03/10 21:11:31  chris
 * added serializeable and default constructors
 *
 * Revision 1.3  2006/03/10 20:00:08  chris
 * Make all versions final
 *
 * Revision 1.2  2006/01/26 20:52:52  sergio
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
 * Revision 1.1  2002/08/13 16:54:32  cstolpe
 * Fix for PR21198, edit last page of truncations
 *
 * Revision 1.0.0.1  2002/06/28 11:33:23  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class Validate2State extends PageableState implements Observer,Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = 7451596302073325818L;
	/** Automatically generated javadoc for: MAXLENGTHFLAGDESCRIPTION */
    private static final int MAXLENGTHFLAGDESCRIPTION = 128;
    /**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
	 * Keep track of the states
	 */
	private Stack stack;
	/**
     * Constructor
     *
     */
    public Validate2State() {
	}
	/**
     * CVS Version
     *
     * @param pState 
     * @pre pState != null
     */
	public void init(Validate1State pState) {
		ArrayList truncations;
		Iterator i;
		stack = pState.getStack();
		
		truncations = new ArrayList();
		i = pState.getList().iterator();
		while (i.hasNext()) {
			Object o = i.next();
			if (o instanceof TranslationMetaAttribute) {
				TranslationMetaAttribute tma = (TranslationMetaAttribute) o;
				String value = tma.getTransDescOverride(); // Look for an overide
				if (value == null) {
					value = tma.getTranslatedDescription(); // Otherwise use the original
				}
				if (isTooLong(value)) {
					if (!pState.isDeleted(tma)) {
						truncations.add(tma); // If it is not deleted add it to the list
					}
					else {
						// Auto truncate deleted values
						while (isTooLong(value)) {
							value = value.substring(0, value.length() - 1);
						}
						tma.setTransDescOverride(value);
					}
				}
			}
		}
		setList(truncations);
	}
	/**
	 * Is this value to long to be stored in the PDH
	 * @param value the string to check
	 * @return boolean true if UTF8 length is longer than 128 bytes
	 */
	private static boolean isTooLong(String value) {
		boolean returnValue = false;
		try {
			returnValue = value.getBytes("UTF8").length > MAXLENGTHFLAGDESCRIPTION;  //$NON-NLS-1$
		}
		catch (java.io.UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		return returnValue;
	}
	/**
	 * Implements the back, continue, edit, and gotoPage actions.
	 * <pre>
	 * back     - Goes back to the StatusState discarding any changes.
	 * continue - Goes to the Validate2State 
	 * edit     - Goes to the EditState
	 * gotoPage - go to the page specified by <b>parameter</b>.
	 *            if not a valid number (e.g. letter) or page do nothing.
	 * </pre>
	 * @see State#doAction(Map)
	 */
	public void doAction(Map params) {
		Object action = params.get("action");  //$NON-NLS-1$
		if ("back".equals(action)) {  //$NON-NLS-1$
			stack.pop();
		}
		else if ("continue".equals(action)) {  //$NON-NLS-1$
			// get the Validate1State
			Observer o = (Observer) stack.elementAt(1);
			// Create the next state
			Observable newState = new Validate3State();
			((Validate3State) newState).init(this);
			// All Validate1State to be notified of saves in Validate3State
			newState.addObserver(o);
			// Go to the next state
			stack.push(newState);
		}
		else if ("edit".equals(action)) {  //$NON-NLS-1$
			// get the Validate1State
			Observer o = (Observer) stack.elementAt(1);
			// Create the next state
			Observable newState = new EditState(this);
			// Allow Validate1State to be notified of saves in EditState
			newState.addObserver(o);
			// Allow Validate2State to be notified of changes
			newState.addObserver(this);
			// Go to the next state
			stack.push(newState);
		}
		else if ("gotoPage".equals(action)) {  //$NON-NLS-1$
			gotoPage(params);
		}
	}
	/**
	 * @see State#getStack()
	 */
	public Stack getStack() { return stack; }
	/**
	 * @see State#getStateID()
	 */
	public Integer getStateID() { return STATE_VALIDATE2; } 
	/**
	 * Implements Observer interface
	 * @see State#getStateID()
	 */
	public void update(Observable o, Object arg) {
		// When a value is no longer too long remove it from this list
		Iterator i = getCurrentPageValues().iterator();
		while (i.hasNext()) {
			TranslationMetaAttribute tma = (TranslationMetaAttribute) i.next();
			String value = tma.getTransDescOverride(); // Look for overide
			if (value == null) {
				value = tma.getTranslatedDescription(); // No overide use original
			}
			if (!isTooLong(value)) {
				i.remove();
			}
		}
		if (Boolean.TRUE.equals(arg)) {
			((Validate1State) stack.firstElement()).update(o, arg);
		}
		// The current page values may have been removed if all the truncation errors are fixed
		if (getCurrentPageNumber() > getLastPageNumber()) {
			setCurrentPageNumber(getLastPageNumber());
		}
	}
}
