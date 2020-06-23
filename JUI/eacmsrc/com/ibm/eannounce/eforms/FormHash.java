/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormHash.java,v $
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:40  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/01 22:06:31  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:14  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:21  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms;
import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormHash extends HashMap {
	private static final long serialVersionUID = 1L;
	private Vector v = new Vector();
    private String name = null;

    /**
     * formHash
     * @author Anthony C. Liberto
     */
    public FormHash() {
        super();
    }

    /**
     * setName
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setName(String _s) {
        name = new String(_s);
    }

    /**
     * getName
     * @return
     * @author Anthony C. Liberto
     */
    public String getName() {
        return name;
    }

    /**
     * @see java.util.Map#clear()
     * @author Anthony C. Liberto
     */
    public void clear() {
        super.clear();
        v.clear();
        return;
    }

    /**
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     * @author Anthony C. Liberto
     */
    public Object put(Object key, Object o) {
        if (containsKey(key)) {
            Object obj = get(key);
            if (obj instanceof Vector) {
                Vector tmp = (Vector) obj;
                tmp.add(o);
                return tmp;
            }
            return null;
        } else {
            Vector tmp = new Vector();
            v.add(o);
            tmp.add(o);
            return super.put(key, tmp);
        }
    }

    /**
     * get
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public Object get(int i) {
        return v.get(i);
    }

    //	public FormEditor getEditor(String key) {
    //		Object o = get(key);
    //		if (o != null && o instanceof Vector) {
    //			Vector tmp = (Vector)o;
    //			int ii = tmp.size();
    //			for (int i=0;i<ii;++i) {
    //				Object obj = tmp.get(i);
    //				if (obj != null && obj instanceof Component) {
    //					Component c = (Component)obj;
    //					if (c.isShowing() && c instanceof FormEditor)
    //						return (FormEditor)c;
    //				}
    //			}
    //		}
    //		return null;
    //	}

    /**
     * replace
     * @param key
     * @param oldO
     * @param newO
     * @author Anthony C. Liberto
     */
    public void replace(String key, Object oldO, Object newO) {
        Object o = get(key);
        if (o != null && o instanceof Vector) {
            Vector tmp = (Vector) o;
            int i = tmp.indexOf(oldO);
            if (i >= 0) {
                tmp.remove(i);
                tmp.add(i, newO);
            }
            i = v.indexOf(oldO);
            if (i >= 0) {
                v.remove(i);
                v.add(i, newO);
            }
        }
        return;
    }

    /**
     * getIndex
     * @param _o
     * @return
     * @author Anthony C. Liberto
     */
    public int getIndex(Object _o) { //17687b
        int index = -1; //17687b
        for (int i = 0; i < size(); ++i) { //17687b
            Object obj = get(i); //17687b
            if (obj.equals(_o)) { //17687b
                index = i; //17687b
                break; //17687b
            } //17687b
        } //17687b
        return index; //17687b
    }
}
