//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eannounce.eforms.table;
import java.util.HashMap;
import java.util.Vector;

import COM.ibm.eannounce.objects.*;

/**
 * used for fill/copy fill/paste
 */
public class Fillvector extends HashMap {
	private static final long serialVersionUID = 1L;
	private Vector v = new Vector();
	private Vector colidVct = new Vector();
	private boolean fillCopyEntity = false;

	/**
	 * does this hold fill copy entity attributes?
	 * @return
	 */
	public boolean isFillCopyEntity(){
		return fillCopyEntity;
	}
	/**
     * add the attribute and its model column id
     * @param _att
     * @param colid, may be -1 for fill copy entity
     */
    public void add(EANAttribute _att, int colid) {
		String key = _att.getAttributeCode();
		if (containsKey(key)) {
			remove(key);
			//find the key in the vector, remove it and the column id
			for(int i=0;i<v.size(); i++){
				String oldkey = v.elementAt(i).toString();
				if(oldkey.equals(key)){
					v.remove(i);
					colidVct.remove(i);
					break;
				}
			}
		}
		
		put(key,_att);
		v.add(key);
		colidVct.add(new Integer(colid));
		if(colid==-1){
			fillCopyEntity = true;
		}
	}

	/**
     * getAttribute
     * @param _i
     * @return
     */
    public EANAttribute getAttribute(int _i) {
    	Object o = v.get(_i);
		if (containsKey(o)) {
			return (EANAttribute)get(o);
		}
		return null;
	}
    /**
     * get the column id for this attribute, if -1 then copy entity was used
     * @param _i
     * @return
     */
    public int getModelColumnId(int _i) {
		return ((Integer)colidVct.get(_i)).intValue();
	}
    

	/**
     * getAttributeValue
     * @param _i
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getAttributeValue(int _i) {
		EANAttribute att = getAttribute(_i);
		if (att != null) {
			return att.get();
		}
		return null;
	}

	/**
     * getAttributeValue
     * @param _o
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getAttributeValue(Object _o) {
		EANAttribute att = getAttribute(_o);
		if (att != null) {
			return att.get();
		}
		return null;
	}

	/**
     * getKey
     * @param _i
     * @return
     * @author Anthony C. Liberto
     * /
    public String getKey(int _i) {
		return getKey(getAttribute(_i));
	}

	/**
     * getKey
     * @param _o
     * @return
     * @author Anthony C. Liberto
     * /
    public String getKey(Object _o) {
		return getKey(getAttribute(_o));
	}

	/**
     * getKey
     * @param _att
     * @return
     * @author Anthony C. Liberto
     * /
    private String getKey(EANAttribute _att) {
//dwb_20041117		if (_att != null)
//dwb_20041117			return _att.getEntityItem().getEntityType() + ":" + _att.getAttributeCode();
		if (_att != null) {												//dwb_20041117
			EntityItem ei = _att.getEntityItem();						//dwb_20041117
			if (ei != null) {											//dwb_20041117
				return eaccess().getKey(ei,_att.getAttributeCode());	//dwb_20041117
			}															//dwb_20041117
		}
		return null;
	}*/

	/**
     * @see java.util.Map#clear()
     */
    public void clear() {
		super.clear();
		v.clear();
		colidVct.clear();
		fillCopyEntity = false;
	}

/*
 dwb_20041117
 */
	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     * /
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}*/

}
