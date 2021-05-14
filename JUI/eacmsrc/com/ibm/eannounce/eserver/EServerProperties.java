/**
 *
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * e-announce chat functionality
 *
 * @version 1.3  2004/02/10
 * @author Anthony C. Liberto
 *
 * $Log: EServerProperties.java,v $
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.8  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.7  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.6  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.5  2004/03/03 00:01:42  tony
 * added to functionality, moved firewall to preference.
 *
 * Revision 1.4  2004/03/01 23:48:24  tony
 * usability update.
 *
 * Revision 1.3  2004/03/01 19:38:05  tony
 * usability enhancements.
 *
 * Revision 1.2  2004/02/26 21:51:55  tony
 * firewall update
 *
 * Revision 1.1  2004/02/19 21:36:58  tony
 * e-announce1.3
 *
 *
 */
package com.ibm.eannounce.eserver;
import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EServerProperties {
    private static ResourceBundle rbProp = null;
    static final long serialVersionUID = 1L;
    private static String sUserName = null;
    private static boolean bBusy = false;
    private static boolean bBehindFire = false;
    /**
     * Automatically generated constructor for utility class
     */
    private EServerProperties() {
    }

    private static ResourceBundle getResourceBundle() {
        if (rbProp == null) {
            rbProp = ResourceBundle.getBundle("ea-server");
        }
        return rbProp;
    }

    /**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public static String getString(String _code) {
        if (rbProp == null) {
            getResourceBundle();
        }
        if (rbProp != null) {
            try {
                return rbProp.getString(_code);
            } catch (MissingResourceException _mre) {
                _mre.printStackTrace();
            }
        }
        return null;
    }

    /**
     * getStringArray
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public static String[] getStringArray(String _code) {
        return getStringArray(_code, ",");
    }

    /**
     * getStringArray
     * @param _code
     * @param _deLim
     * @return
     * @author Anthony C. Liberto
     */
    public static String[] getStringArray(String _code, String _deLim) {
        String tmp = getString(_code);
        if (tmp != null) {
            StringTokenizer st = new StringTokenizer(tmp, _deLim);
            Vector v = new Vector();
            while (st.hasMoreTokens()) {
                v.add(st.nextToken().trim());
            }
            if (!v.isEmpty()) {
                return (String[]) v.toArray(new String[v.size()]);
            }
        }
        return null;
    }

    /**
     * getInt
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public static int getInt(String _code) {
        String tmp = getString(_code);
        if (tmp != null) {
            try {
                return Integer.valueOf(tmp).intValue();
            } catch (NumberFormatException _nfe) {
                _nfe.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * is
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean is(String _code) {
        String tmp = getString(_code);
        if (tmp != null) {
            return tmp.equals("true");
        }
        return false;
    }

    /**
     * getMessage
     * @param _code
     * @param _parms
     * @return
     * @author Anthony C. Liberto
     */
    public static String getMessage(String _code, String[] _parms) {
        String out = getString(_code);
        if (_parms != null) {
            int ii = _parms.length;
            for (int i = 0; i < ii; ++i) {
                out = replace(out, "{parm" + i + "}", _parms[i]);
            }
        }
        return out;
    }

    /**
     * getUserName
     * @return
     * @author Anthony C. Liberto
     */
    public static String getUserName() {
        return sUserName;
    }

    /**
     * setUserName
     * @param _s
     * @author Anthony C. Liberto
     */
    public static void setUserName(String _s) {
        if (_s != null) {
            sUserName = new String(_s);
        }
        return;
    }

    /**
     * setBehindFirewall
     * @param _behind
     * @author Anthony C. Liberto
     */
    public static void setBehindFirewall(boolean _behind) {
        bBehindFire = _behind;
        return;
    }

    /**
     * isBehindFirewall
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean isBehindFirewall() {
        return bBehindFire;
    }

    /**
     * replace
     * @param _current
     * @param _find
     * @param _replace
     * @return
     * @author Anthony C. Liberto
     */
    public static String replace(String _current, String _find, String _replace) {
        StringBuffer out = new StringBuffer(_current);
        int indx = 0;
        int iLenFind = 0;
        int iLenReplace = 0;
        _current = _current.toLowerCase();
        _find = _find.toLowerCase();

        indx = _current.indexOf(_find);
        iLenFind = _find.length();
        iLenReplace = _replace.length();
        if (_replace == null) {
            _replace = "null";
        }
        while (indx != -1) {
            out.replace(indx, (indx + iLenFind), _replace);
            _current = out.toString().toLowerCase();
            indx = _current.indexOf(_find, indx + iLenReplace);
        }
        return out.toString();
    }

    /**
     * setBusy
     * @param _b
     * @author Anthony C. Liberto
     */
    public static void setBusy(boolean _b) {
        bBusy = _b;
        return;
    }

    /**
     * isBusy
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean isBusy() {
        return bBusy;
    }
}
