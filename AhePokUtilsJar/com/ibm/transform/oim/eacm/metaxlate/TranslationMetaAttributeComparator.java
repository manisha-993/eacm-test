// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;
import java.util.Comparator;
import COM.ibm.opicmpdh.translation.TranslationMetaAttribute;

/**
 * Sorts TranslationMetaAttributes objects by attribute code, type, and value.
 * This implementation is not consistent with equals so it shouldn't be used in Sets or Maps.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: TranslationMetaAttributeComparator.java,v $
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
 * Revision 1.0.0.1  2002/06/28 11:33:23  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class TranslationMetaAttributeComparator implements Comparator, Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = 3055306499751154943L;
	/**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
     * Compares TranslationMetaAttributes.
     *
     * @return int
     * @param arg0
     * @param arg1 
     */
	public int compare(Object arg0, Object arg1) {
		int returnValue = -1;
		if (arg0 != null && arg0 instanceof TranslationMetaAttribute &&
			arg1 != null && arg1 instanceof TranslationMetaAttribute) 
		{
			TranslationMetaAttribute tma0 = (TranslationMetaAttribute) arg0;
			TranslationMetaAttribute tma1 = (TranslationMetaAttribute) arg1;
			// Compare Attribute Code
			returnValue = tma0.getAttributeCode().compareTo(tma1.getAttributeCode());
			// If they are not equal return the result
			if (returnValue == 0) {
				// Compare flag type
				returnValue = tma0.getAttributeType().compareTo(tma1.getAttributeType());
				// If they are not equal return the result
				if (returnValue == 0) {
					// Compare flag value
					returnValue = tma0.getAttributeValue().compareTo(tma1.getAttributeValue());
				}
			}
		}
		return returnValue;
	}

}

