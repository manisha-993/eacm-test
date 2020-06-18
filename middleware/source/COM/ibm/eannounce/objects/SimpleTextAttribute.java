//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SimpleTextAttribute.java,v $
// Revision 1.3  2008/02/01 22:10:05  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2003/03/25 20:31:41  gregg
// more applyColumnOrders for relator case
//
// Revision 1.1  2003/02/26 21:56:29  gregg
// New SimpleTextAttribute/SimplePicklistAttribute classes + use these in Rendering RowSelectableTable for FilterGroup/FilterItem
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/**
 * This class can be used to hold Attribute-like information without necessarily being tied directly to any instance
 * of PDH Attribute Data, EntityItems, etc..
 * -- E.g. as a placeholder for RowSelectableTable cell information when we don't need/want a full-blown EANAttribute
 * -- SimpleTextAtribute holds one NLS-sensitive value 
 */
public class SimpleTextAttribute extends MetaTag {

  /**
  * @serial
  */
    static final long serialVersionUID = 1L;

	/**
	* Creates the MetaTag
	* @param _ef the Assosicated Creator tied to this Object
	* @param _prof creator's Profile
	* @param _strKey the key for the MetaTag (~attCode)
	* @param _strVal this Attribute's value - NLS sensitive
	*/
	public SimpleTextAttribute(EANFoundation _ef, Profile _prof, String _strKey, String _strValue) throws MiddlewareRequestException {
	    super(_ef,_prof, _strKey);	
		putValue(_strValue);
    }

    protected SimpleTextAttribute getCopy() throws MiddlewareRequestException {
        return new SimpleTextAttribute(getParent(), getProfile(), getKey(), getValue());    
    }
    
/**
 * NLS - sensitive value
 */
	protected void putValue(String _strValue) {
	    putLongDescription(_strValue);    
	}
	
	protected String getValue() {
	    return getLongDescription();
	}
	
/**
 * Get this SimpleTextAttribute's NLS-sensitive value
 * @return attribute's value
 */
	public String toString() {
	    return getValue();
	}
	
    public String getVersion() {
      return new String("$Id: SimpleTextAttribute.java,v 1.3 2008/02/01 22:10:05 wendy Exp $");
    }

	

}

