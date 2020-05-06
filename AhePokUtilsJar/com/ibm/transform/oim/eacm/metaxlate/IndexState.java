// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;
import java.util.*;
import COM.ibm.opicmpdh.translation.TranslationMetaAttribute;
import COM.ibm.eannounce.objects.MetaTranslationItem;

/**
 * Creates a list mapping the first occurrence of an attribute code for a given
 * flag type to its index in the source list. This creates an index of attribute
 * codes.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: IndexState.java,v $
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
 * Revision 1.3  2003/09/04 20:27:07  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.2  2002/08/27 16:15:19  cstolpe
 * Fix for PR21004
 *
 * Revision 1.1  2002/07/09 12:36:32  cstolpe
 * Fix for PR20622 exception on first index entry
 *
 * Revision 1.0.0.1  2002/06/28 11:33:22  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class IndexState extends PageableState implements Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = 2745987958650418279L;
	// TODO I think these should be the same
	/** Automatically generated javadoc for: VALUESPERPAGE2 */
    private static final int VALUESPERPAGE2 = 25;
    /** Automatically generated javadoc for: VALUESPERPAGE */
    private static final int VALUESPERPAGE = 84;
    /**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
	 * Keeps track of the states 
	 */
	private Stack stack = null;
	/**
     * Constructor from CompleteState. Its list is of Map.Entry where the
     * key is TranslationMetaAttribute and the value is a ReturnDataRow
     *
     * @pre pState != null
     */
	public IndexState() {
	}
	/**
     * Initial list to first occurence of attribute code/ flag value
     *
     * @param pState 
     */
	public void init(PageableState pState) {
		Object lastCode = "";  //$NON-NLS-1$
		Object lastType = "";  //$NON-NLS-1$
		Map index = new TreeMap();
		Iterator list;
		stack = pState.getStack();
		list = pState.getList().iterator();
		while (list.hasNext()) {
			Object entry = list.next();
			Object thisCode = "";  //$NON-NLS-1$
			Object thisType = "";  //$NON-NLS-1$
			if (entry instanceof MetaTranslationItem){
				MetaTranslationItem mti = (MetaTranslationItem) entry; 
				thisCode = mti.getCode();
				thisType = mti.getType();
			}
			else {
				TranslationMetaAttribute tma = (TranslationMetaAttribute) entry; 
				thisCode = tma.getAttributeCode();
				thisType = tma.getAttributeType();
			}
			if (!lastCode.equals(thisCode) || !lastType.equals(thisType)) {
				lastCode = thisCode;
				lastType = thisType;
				index.put(
					lastCode,
					new Integer(
						pState.getList().indexOf(
							entry)));
			}
		}
		setList(index.entrySet());
		setValuesPerPage(VALUESPERPAGE);
	}
	/**
	 * Implements back, gotoKey, and gotoPage actions
	 * <pre>
	 * back     - go back to the prior state
	 * gotoKey  - Calculate page number using integer passed in <b>parameter</b>
	 *            set prior state to that page and return to the prior state
	 * gotoPage - set prior state to the page passed in <b>parameter</b>
	 *            and return to the prior state.
	 * </pre>
	 * @see State#doAction(Map)
	 */
	public void doAction(Map params) {
		Object action = params.get("action");  //$NON-NLS-1$
		if ("back".equals(action)) {  //$NON-NLS-1$
			stack.pop();
		}
		else if ("gotoKey".equals(action)) {  //$NON-NLS-1$
			PageableState pState;
			// Calculate page number based on index
			int pNum = 
				Integer.parseInt(
					(String) params.get("parameter"));  //$NON-NLS-1$
			pNum = (pNum / VALUESPERPAGE2) + 1;
			stack.pop(); // remove IndexState
			pState = (PageableState) stack.peek();
			pState.setCurrentPageNumber(pNum);
		}
		else if ("gotoPage".equals(params.get("action"))) {  //$NON-NLS-1$  //$NON-NLS-2$
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
	public Integer getStateID() { return STATE_INDEX; } 	
}

