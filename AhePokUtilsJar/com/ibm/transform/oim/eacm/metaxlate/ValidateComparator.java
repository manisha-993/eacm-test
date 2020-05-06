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
import COM.ibm.eannounce.objects.MetaTranslationItem;

/**
 * Compares TranslationMetaAttributes and MetaTranslationItems. It sorts them by
 * attribute code, type, and value. In ascending order. It assumes the
 * MetaTranslationItems are from the GBL2921 query. It is not consistent with equals
 * and should not be used with Sets or Maps.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: ValidateComparator.java,v $
 * Revision 1.5  2006/04/17 19:37:16  chris
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
 * Revision 1.2  2005/02/16 17:50:20  chris
 * JTest cleanup
 *
 * Revision 1.1.1.1  2004/01/26 17:40:01  chris
 * Latest East Coast Source
 *
 * Revision 1.1  2003/09/04 20:27:08  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.0.0.1  2002/06/28 11:33:23  cstolpe
 * Initialize
 *
 * </pre>
 * @see GBL2921Comparator
 * @see TranslationMetaAttributeComparator
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class ValidateComparator implements Comparator, Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = -1314345549999121402L;
	/**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$

	/**
     * Compares TranslationMetaAttributes and MetaTranslationItems.
     *
     * @return int
     * @param arg0
     * @param arg1 
     */
	public int compare(Object arg0, Object arg1) {
		int returnValue = -1;
		if (arg0 instanceof TranslationMetaAttribute) {
			TranslationMetaAttribute tma0 = (TranslationMetaAttribute) arg0;
			if (arg1 instanceof TranslationMetaAttribute) {
				TranslationMetaAttribute tma1 = (TranslationMetaAttribute) arg1;
				
				// Compare Attribute Code
				returnValue = tma0.getAttributeCode().compareTo(tma1.getAttributeCode());
				// If they are not equal Compare flag type
				if (returnValue == 0) {
					returnValue = tma0.getAttributeType().compareTo(tma1.getAttributeType());
					// If they are not equal Compare flag value
					if (returnValue == 0) {
						returnValue = tma0.getAttributeValue().compareTo(tma1.getAttributeValue());
					}
				}
			}
			else {
				// assume instance of MetaTranslationItem
				MetaTranslationItem mti1 = (MetaTranslationItem) arg1;
				// Compare Attribute Code
				returnValue = tma0.getAttributeCode().compareTo(mti1.getCode());
				// If they are not equal Compare flag type
				if (returnValue != 0) {
					returnValue = tma0.getAttributeType().compareTo(mti1.getType());
					// If they are not equal Compare flag value
					if (returnValue != 0) {
						returnValue = tma0.getAttributeValue().compareTo(mti1.getValue());
					}
				}
			}
		}
		else if (arg0 instanceof MetaTranslationItem) {
			MetaTranslationItem mti0 = (MetaTranslationItem) arg0;
			if (arg1 instanceof TranslationMetaAttribute) {
				TranslationMetaAttribute tma1 = (TranslationMetaAttribute) arg1;
				
				// Compare Attribute Code
				returnValue = mti0.getCode().compareTo(tma1.getAttributeCode());
				// If they are not equal Compare flag type
				if (returnValue != 0) {
					returnValue = mti0.getType().compareTo(tma1.getAttributeType());
					// If they are not equal Compare flag value
					if (returnValue != 0) {
						returnValue = mti0.getValue().compareTo(tma1.getAttributeValue());
					}
				}
			}
			else {
				// assume instance of MetaTranslationItem
				MetaTranslationItem mti1 = (MetaTranslationItem) arg1;
				// Compare Attribute Code
				returnValue = mti0.getCode().compareTo(mti1.getCode());
				// If they are not equal Compare flag type
				if (returnValue != 0) {
					returnValue = mti0.getType().compareTo(mti1.getType());
					// If they are not equal Compare flag value
					if (returnValue != 0) {
						returnValue = mti0.getValue().compareTo(mti1.getValue());
					}
				}
			}
		}
		return returnValue;
	}

}

