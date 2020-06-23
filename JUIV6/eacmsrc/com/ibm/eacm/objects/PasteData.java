//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.io.Serializable;

import COM.ibm.eannounce.objects.EANAttribute;

/**
 * this is used to hold data from an EANAttribute for paste.  it is serialized to the clipboard
 * EANAttribute is not used because too much is serialized and can't clone it without bringing in EntityGroup
 * @author Wendy Stimpson
 */
public class PasteData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String attrCode = null;
	private Object value = null;
	private String stringValue = null;
	
	public PasteData(EANAttribute ean){
		attrCode = ean.getAttributeCode();
		stringValue = ean.toString();
		value = ean.get(); //this is either MetaFlag[] or String
	}
	public String getAttrCode(){
		return attrCode;
	}
	public Object getValue(){
		return value;
	}
	public void dereference(){
		attrCode = null;
		value = null;
		stringValue = null;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		return stringValue;
	}
}
