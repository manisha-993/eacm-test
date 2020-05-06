// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;

import COM.ibm.opicmpdh.transactions.NLSItem;

/**
 * Compares NLSItems and sorts them by NLSDescription in ascending order.
 * It is not consistent with equals and should not be used with Sets or Maps.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: NLSItemComparator.java,v $
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
public class NLSItemComparator implements java.util.Comparator, Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = 7811854857971336821L;	
	/**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
     * Compares NLSItems.
     *
     * @return int
     * @param o1
     * @param o2 
     */
	public int compare(Object o1, Object o2) {
		NLSItem item1;
		NLSItem item2;
		int result = -1;
		if (o1 != null && (o1 instanceof NLSItem) &&
			o2 != null && (o2 instanceof NLSItem))
		{
			String item1Description;
			String item2Description;
			item1 = (NLSItem) o1;
			item2 = (NLSItem) o2;
			item1Description = item1.getNLSDescription();
			item2Description = item2.getNLSDescription();
			if (item1Description != null && item2Description != null) {
				result = item1Description.compareTo(item2Description);
			}
		}
		return result;
	}	
}
