/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EComparator.java,v $
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.1  2004/02/19 21:36:58  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/09/10 17:59:26  tony
 * updated the eComparator to improve functionality.
 * The old version of the code is available in eComparator.old.
 * The new version allows for multiple criteria sorts and is
 * completely backwards compatible.
 *
 * Revision 1.3  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eserver;
import java.util.Comparator;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EComparator implements Comparator {
    private boolean[] ascending = { true };

    /**
     * eComparator
     * @param _asc
     * @author Anthony C. Liberto
     */
    public EComparator(boolean[] _asc) {
        ascending = _asc;
        return;
    }

    /**
     * eComparator
     * @param _asc
     * @author Anthony C. Liberto
     */
    public EComparator(boolean _asc) {
        ascending[0] = _asc;
        return;
    }

    /**
     * eComparator
     * @author Anthony C. Liberto
     */
    public EComparator() {
        return;
    }

    private int compareString(String _s1, String _s2) {
        if (_s1 == null || _s2 == null) {
            return -1;
        }
        return _s1.compareToIgnoreCase(_s2);
    }

    private int compareNumber(Double _n1, Double _n2) {
        return _n1.compareTo(_n2);
    }

    private int compareNumber(Float _n1, Float _n2) {
        return _n1.compareTo(_n2);
    }

    private int compareNumber(Integer _n1, Integer _n2) {
        return _n1.compareTo(_n2);
    }

    private int compareNumber(Long _n1, Long _n2) {
        return _n1.compareTo(_n2);
    }

    private int compareNumber(Number _n1, Number _n2) {
        return compareNumber(new Double(_n1.doubleValue()), new Double(_n2.doubleValue()));
    }

    private int compareNumber(Short _n1, Short _n2) {
        return _n1.compareTo(_n2);
    }

    private boolean getBoolean(int _indx) {
        return ascending[_indx];
    }

    private int getResult(int _result, int _index) {
        if (!getBoolean(_index)) {
            return -_result;
        }
        return _result;
    }

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @author Anthony C. Liberto
     */
    public int compare(Object _o1, Object _o2) {
        int result = 0;
        for (int i = 0; i < ascending.length && result == 0; i++) {
            Object o1 = getObject(_o1, i);
            Object o2 = getObject(_o2, i);
            if (o1 instanceof Double && o2 instanceof Double) {
                result = getResult(compareNumber((Double) o1, (Double) o2), i);
            } else if (o1 instanceof Float && o2 instanceof Float) {
                result = getResult(compareNumber((Float) o1, (Float) o2), i);
            } else if (o1 instanceof Integer && o2 instanceof Integer) {
                result = getResult(compareNumber((Integer) o1, (Integer) o2), i);
            } else if (o1 instanceof Long && _o2 instanceof Long) {
                result = getResult(compareNumber((Long) o1, (Long) o2), i);
            } else if (o1 instanceof Number && o2 instanceof Number) {
                result = getResult(compareNumber((Number) o1, (Number) o2), i);
            } else if (o1 instanceof Short && o2 instanceof Short) {
                result = getResult(compareNumber((Short) o1, (Short) o2), i);
            } else if (o1 instanceof String && o2 instanceof String) {
                result = getResult(compareString((String) o1, (String) o2), i);
            } else {
                result = getResult(compareObject(o1, o2), i);
            }
        }
        return result;
    }

    /*
     might need to overwrite so that customization
     of compare for object can exist, instead of
     using the default toString comparator
     */
    /**
     * compareObject
     * @param _o1
     * @param _o2
     * @return
     * @author Anthony C. Liberto
     */
    public int compareObject(Object _o1, Object _o2) {
        return compareString(_o1.toString(), _o2.toString());
    }

    /*
     overwrite for customization
     of the get.

     should return the appropriate Object value based on the _index
     */
    /**
     * getObject
     * @param _o
     * @param _index
     * @return
     * @author Anthony C. Liberto
     */
    public Object getObject(Object _o, int _index) {
        return _o;
    }
}
