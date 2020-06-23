//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;


import java.util.HashMap;
import java.util.Vector;

import COM.ibm.eannounce.objects.*;

/**
 * used for fill/copy fill/paste
 * @author Wendy Stimpson
 */
// $Log: FillVector.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class FillVector extends HashMap<String,EANAttribute> {
	private static final long serialVersionUID = 1L;
	private Vector<String> keyVct = new Vector<String>();
	private Vector<Integer> colidVct = new Vector<Integer>();

	/**
     * add the attribute and its model column id
     * @param att
     * @param colid, may be -1 for fill copy entity
     */
    public void add(EANAttribute att, int colid) {
		String key = att.getAttributeCode();
		if (containsKey(key)) {
			remove(key);
			//find the key in the vector, remove it and the column id
			for(int i=0;i<keyVct.size(); i++){
				String oldkey = keyVct.elementAt(i).toString();
				if(oldkey.equals(key)){
					keyVct.remove(i);
					colidVct.remove(i);
					break;
				}
			}
		}
		
		put(key,att);
		keyVct.add(key);
		colidVct.add(new Integer(colid));
	}

	/**
     * getAttribute
     * @param i
     * @return
     */
    public EANAttribute getAttribute(int i) {
    	String key = keyVct.get(i);
		if (containsKey(key)) {
			return get(key);
		}
		return null;
	}
    /**
     * get the column id for this attribute
     * @param i
     * @return
     */
    public int getModelColumnId(int i) {
		return colidVct.get(i).intValue();
	}

	/**
     * @see java.util.Map#clear()
     */
    public void clear() {
		super.clear();
		keyVct.clear();
		colidVct.clear();
	}

}
