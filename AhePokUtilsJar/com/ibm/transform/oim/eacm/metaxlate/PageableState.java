// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;
import java.util.*;
/**
 * All states that have to display values across multiple pages should subclass this.
 * It handles getting the values for a page and other page related calculations.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002,2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: PageableState.java,v $
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
 * Revision 1.0.0.1  2002/06/28 11:33:22  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public abstract class PageableState implements State, Serializable {
    /** Automatically generated javadoc for: MAXVALUES */
    private static final int MAXVALUES = 25;
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = -2641850656935171201L;
	/**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
	 * List of objects to be paged
	 */
	private ArrayList list = new ArrayList();
	/**
	 * Current page number. (0 based)
	 */
	private int currentPageNumber = 0;
	/**
	 * Tracks how many values should be on a page (default 25)
	 */
	private int valuesPerPage = MAXVALUES;
	/**
	 * Default constructor. 
	 */
	public PageableState() {
	}
	/**
	 * Sets the values that should be on a page
	 * @param newNumber int number of values per page
	 */
	public void setValuesPerPage(int newNumber) {
		valuesPerPage = newNumber;
		if (valuesPerPage < 1) {
            valuesPerPage = 1;
		}
	}
	/**
	 * Sets the list of values to be paged
	 * @param newValues
	 */
	public void setList(Collection newValues) {
		getList().clear();
		getList().addAll(newValues);
	}
	/**
	 * Gets the values on the current page.
	 * @return subset of values for current page
	 */
	public List getCurrentPageValues() {
		List values = null;
		if (getList().size() == 0) {
            values = getList();
		}
		else {
			int beginIndex = currentPageNumber * valuesPerPage;
			int endIndex = beginIndex + valuesPerPage;
			if (endIndex >= getList().size()) {
				endIndex = getList().size();
			}
			values = getList().subList(beginIndex, endIndex);
		}
		return values;
	}
	/**
	 * Get the current page number (1 based)
	 * @return int
	 */
	public int getCurrentPageNumber() {
		return currentPageNumber + 1;
	}
	/**
	 * Calculates the last page number based on the number of values on a page.
	 * @return int
	 */
	public int getLastPageNumber() {
		int tmp = getList().size() / valuesPerPage;
		if (getList().size() % valuesPerPage > 0) {
            tmp++;
		}
		return tmp;
	}
	/**
	 * Sets the current page number (no checking)
	 * @param newPage
	 */
	public void setCurrentPageNumber(int newPage) {
		currentPageNumber = newPage - 1;
	}
	/**
	 * Indicates if we are on the first page number
	 * @return boolean
	 */
	public boolean isFirstPage() {
		return currentPageNumber == 0;
	}
	/**
     * Indicates if we are on the last page.
     *
     * @return boolean
     */
	public boolean isLastPage() {
		return (currentPageNumber + 1) == getLastPageNumber();
	}
	/**
	 * Implements the <b>gotoPage</b> action.
	 * @param params HTTP request parameters
	 */
	protected void gotoPage(java.util.Map params) {
		String pNum = (String) params.get("parameter");  //$NON-NLS-1$
		if (pNum != null) {
			int i;
			try {
				i = Integer.parseInt(pNum);
				if (i < 1 || i > getLastPageNumber()) {
					return;
				}
				setCurrentPageNumber(i);
			}
			catch(NumberFormatException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/**
     * Set the list of values that need to be paged
     *
     * @param aList
     */
    protected void setList(ArrayList aList) {
		this.list = aList;
	}

	/**
     * Get the list of values that are being paged
     *
     * @return
     */
    protected ArrayList getList() {
		return list;
	}
}

