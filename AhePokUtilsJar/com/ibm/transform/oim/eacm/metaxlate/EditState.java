// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//// Licensed Materials -- Property of IBM
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;
import java.util.*;
import COM.ibm.opicmpdh.translation.TranslationMetaAttribute;

/**
 * Determines what values are editable on a page and wether they have been
 * modified. It marks the prior state modified if the changes are accepted. A
 * value is considered modified if the new value is different from the old value.
 * A value is considered editable if it was in the original package and has not
 * been deleted since th translation request.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: EditState.java,v $
 * Revision 1.5  2006/04/17 19:37:16  chris
 * JTest changes
 *
 * Revision 1.4  2006/03/10 21:11:31  chris
 * added serializeable and default constructors
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
 * Revision 1.2  2005/02/16 17:50:20  chris
 * JTest cleanup
 *
 * Revision 1.1.1.1  2004/01/26 17:40:01  chris
 * Latest East Coast Source
 *
 * Revision 1.1  2003/09/04 20:27:07  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.0.0.1  2002/06/28 11:33:22  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class EditState extends Observable implements State, Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = -2387266905719580196L;
	/**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
	 * Keep track of the states
	 */
	private Stack stack;
	/**
	 * The list of editable values 
	 */
	private ArrayList editable = new ArrayList();
	/**
     * Constructor
     *
     */
    public EditState() {
	}

	/**
     * Having a constructor per state controls which states can transition to the EditState.
     *
     * @param pState 
     * @pre pState != null
     */
	public void init(Validate1State pState) {
		Iterator entries;
		stack = pState.getStack();
		entries = pState.getCurrentPageValues().iterator();
		while (entries.hasNext()) {
			Object entry = entries.next();
			if (entry instanceof TranslationMetaAttribute &&
				!pState.isDeleted((TranslationMetaAttribute) entry)) 
			{
				editable.add(entry);
			}
		}
	}
	/**
     * Having a constructor per state controls which states can transition to the EditState.
     *
     * @param pState 
     * @pre pState != null
     */
	public EditState(Validate2State pState) {
		stack = pState.getStack();
		editable.addAll(pState.getCurrentPageValues());
	}
	/**
	 * Implements back and save actions
	 * <pre>
	 * back - go back to the prior state, discarding any changes
	 * save - go back to the prior state and save any changes
	 * </pre>
	 * @see State#doAction(Map)
	 */
	public void doAction(Map params) {
		Object action = params.get("action");  //$NON-NLS-1$
		if ("back".equals(action)) {  //$NON-NLS-1$
			stack.pop();
			deleteObservers();
		}
		else if ("save".equals(action)) {  //$NON-NLS-1$
			// update values
			Iterator i = editable.iterator();
			Boolean hasChanged = Boolean.FALSE;
			while (i.hasNext()) {
				TranslationMetaAttribute tma = (TranslationMetaAttribute) i.next();
				String value = (String) params.get(tma.getKey());
				String override = tma.getTransDescOverride();
				if (override == null) {
					override = tma.getTranslatedDescription();
				}
				if (!override.equals(value)) { /* NOI18N */
					tma.setTransDescOverride(value);
					hasChanged = Boolean.TRUE;
				}
			}
			if (hasChanged == Boolean.TRUE) {
				setChanged();
				notifyObservers(Boolean.TRUE);
				// wait until everyone has been notified
				while (hasChanged()) {
                    Thread.yield();
				}
				deleteObservers();
			}
			stack.pop(); // remove EditState
		}
	}
	/**
	 * @see State#getStack()
	 */
	public Stack getStack() { return stack; }
	/**
	 * @see State#getStateID()
	 */
	public Integer getStateID() { return STATE_EDIT; } 
	/**
     * Needed by view
     *
     * @return List
     */
	public List getValues() { return editable; }
}

