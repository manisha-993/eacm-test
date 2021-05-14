/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EVector.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/04/11 20:02:27  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EVector extends HashMap implements Serializable {
	private Vector v = null;
    static final long serialVersionUID = 19721222L;
	/**
     * eVector
     * @author Anthony C. Liberto
     */
    public EVector() {
		super();
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

	/**
     * put
     * @param key
     * @param o
     * @return
     * @author Anthony C. Liberto
     */
    public Object put(String key, Object o) {
		if (v == null) {
			v = new Vector();
		}
		if (containsKey(key)) {												//013087
			remove(indexOf(key));											//21808
			return put(key,o);												//013087
		} else {															//013087
			v.add(key);
			return super.put(key,o);
		}
	}

	/**
     * put
     * @param key
     * @param o
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Object put(String key, Object o, int _i) {
		if (v == null) {
			v = new Vector();
		}
		if (containsKey(key)) {												//013087
			Object obj = get(key);											//013087
			remove(v.indexOf(obj));											//013087
			return put(key,o,_i);											//013087
		} else {															//013087
			v.add(_i,key);
			return super.put(key,o);
		}
	}

	/**
     * putMap
     * @param _key
     * @param _att
     * @author Anthony C. Liberto
     */
    public void putMap(String _key, String _att) {
		if (!containsKey(_key)) {
			HashMap h = new HashMap();
			h.put(_att,_att);
			super.put(_key,h);
		} else {
			HashMap h = (HashMap)get(_key);
			if (!h.containsKey(_att)) {
				h.put(_att,_att);
				super.put(_key,h);
			}
		}
		return;
	}

	/**
     * mapContains
     * @param _key
     * @param _att
     * @return
     * @author Anthony C. Liberto
     */
    public boolean mapContains(String _key, String _att) {
		Object o = null;
        if (!containsKey(_key)) {
			return false;
		}
		o = get(_key);
		if (o instanceof HashMap) {
			return ((HashMap)o).containsKey(_att);
		}
		return false;
	}

	/**
     * indexOf
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public int indexOf(String _key) {
		return v.indexOf(_key);
	}

	/**
     * getKeys
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getKeys() {
		int ii = -1;
        String[] out = null;
        if (v == null || v.isEmpty()) {
			return null;
		}
		ii = v.size();
		out = new String[ii];
		for (int i=0;i<ii;++i) {
			out[i] = (String)v.get(i);
		}
		return out;
	}

	/**
     * put
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public Object put(String _s) {											//013332
		return super.put(_s,_s);											//013332
	}																		//013332

	/**
     * get
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public Object get(int i) {
		if (v != null) {
			return get(v.get(i));
		}
		return null;
	}

	/**
     * remove
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public Object remove(int i) {
		if (v != null && !v.isEmpty()) {									//013205
			Object o = v.remove(i);											//013205
			return super.remove(o);
		}																	//013205
		return null;														//013205
	}

	/**
     * @see java.util.Map#remove(java.lang.Object)
     * @author Anthony C. Liberto
     */
    public Object remove(Object key) {
		Object o =  super.remove(key);
		if (v != null) {
			v.remove(o);
		}
		return o;
	}

	/**
     * @see java.util.Map#clear()
     * @author Anthony C. Liberto
     */
    public void clear() {													//013337
		if (v != null) {														//013337
			v.clear();
		}														//013337
		super.clear();														//013337
		return;																//013337
	}																		//013337

	/**
     * removeAll
     * @author Anthony C. Liberto
     */
    public void removeAll() {												//013087
		int ii = -1;
        if (v == null) {														//013087
			return;
		}															//013087
		ii = size();													//013087
		for (int i=ii-1; i>=0; --i) {											//013087
			remove(i);
		}														//013087
		return;																//013087
	}																		//013087
}
