/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * Various routines that will be used throughout the OPICM system.
 *
 * @version 1.2 2002/12/10
 * @author Anthony C. Liberto
 *
 * $Log: Routines.java,v $
 * Revision 1.4  2009/06/09 11:36:23  wendy
 * BH SR-14 date warnings
 *
 * Revision 1.3  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/05/17 18:16:14  wendy
 * Corrected new line character in meta output
 *
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2006/03/23 20:32:44  tony
 * adjusted profile dump to show read-only
 *
 * Revision 1.2  2005/10/05 15:17:42  tony
 * Added property dump logic
 *
 * Revision 1.1.1.1  2005/09/09 20:37:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.19  2005/09/08 17:58:57  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.18  2005/03/15 17:03:31  tony
 * updated versioning logic
 *
 * Revision 1.17  2005/03/07 17:54:04  tony
 * added combine logic
 *
 * Revision 1.16  2005/03/04 19:04:15  tony
 * added combine
 *
 * Revision 1.15  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.14  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.13  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.12  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.11  2004/11/18 18:02:37  tony
 * adjusted keying logic based on middleware changes.
 *
 * Revision 1.10  2004/11/11 23:42:49  tony
 * improved logic
 *
 * Revision 1.9  2004/11/08 19:00:19  tony
 * added trim() statement to numeric conversions.
 *
 * Revision 1.8  2004/09/23 22:26:30  tony
 * fixed number format exception.
 *
 * Revision 1.7  2004/09/23 16:24:21  tony
 * improved logic to acquire focus to textbox on
 * message panel input functionality.
 *
 * Revision 1.6  2004/09/03 20:25:59  tony
 * fixed paste logic.
 *
 * Revision 1.5  2004/08/19 16:58:16  tony
 * xl8r import enhancement
 *
 * Revision 1.4  2004/06/25 17:29:33  tony
 * cleaned-up code.
 *
 * Revision 1.3  2004/03/25 23:37:20  tony
 * cr_216041310
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.21  2003/12/23 21:42:41  tony
 * updated number processing logic.
 *
 * Revision 1.20  2003/12/15 17:44:35  tony
 * added comments
 *
 * Revision 1.19  2003/12/10 22:01:36  tony
 * cleaned-up code.
 *
 * Revision 1.18  2003/12/01 17:46:08  tony
 * accessibility
 *
 * Revision 1.17  2003/11/03 16:04:10  tony
 * 52847
 *
 * Revision 1.16  2003/10/31 17:32:13  tony
 * acl_20031031
 *
 * Revision 1.15  2003/10/06 23:44:53  tony
 * vb.script update, changed logic from a date based
 * file to a random file naming convention.
 *
 * Revision 1.14  2003/09/04 21:13:04  tony
 * 52009
 *
 * Revision 1.13  2003/09/03 17:10:02  tony
 * acl_20030903 sleep update
 *
 * Revision 1.12  2003/08/18 22:46:37  tony
 * middleware update.  updated logic for middleware changes.
 *
 * Revision 1.11  2003/08/14 19:42:45  tony
 * updated information to show rules and more meta details.
 *
 * Revision 1.10  2003/07/25 17:16:34  tony
 * improved logic
 *
 * Revision 1.9  2003/07/10 16:53:10  tony
 * 51431
 *
 * Revision 1.8  2003/07/07 14:54:37  tony
 * added to String method that will convert an array to
 * a delimited string.
 *
 * Revision 1.7  2003/06/27 23:20:36  tony
 * added more pattern matching.
 *
 * Revision 1.6  2003/06/27 22:59:26  tony
 * added pattern matching
 *
 * Revision 1.5  2003/05/29 15:02:54  tony
 * 50986
 *
 * Revision 1.4  2003/05/14 19:03:32  tony
 * added toInteger
 *
 * Revision 1.3  2003/04/23 17:17:30  tony
 * added color info
 *
 * Revision 1.2  2003/04/02 17:56:56  tony
 * added appendArray logic.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.elogin;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Routines implements EAccessConstants {
    /**
     * STR
     */
    protected static String STR = null;
    /**
     * strs
     */
    protected static String[] strs = null;

    /**
     * routines
     * @author Anthony C. Liberto
     */
    private Routines() {
    }

    /*
    	returns approximation of button that is pressed
    	either the left button or the other button.
    	altMask appear to corrupt the call so no
    	determination can be completely accurate
    */
    /**
     * getMouseButton
     * @param e
     * @return
     * @author Anthony C. Liberto
     */
    public static int getMouseButton(MouseEvent e) { //011277
//        int i = e.getModifiers(); //011277
        if (SwingUtilities.isLeftMouseButton(e)) {
            return InputEvent.BUTTON1_MASK;

        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            return InputEvent.BUTTON2_MASK;

        } else if (SwingUtilities.isRightMouseButton(e)) {
            return InputEvent.BUTTON3_MASK;
        }
        return -1;
        //		if (i >= e.BUTTON1_MASK)										//011277
        //			return e.BUTTON1_MASK;										//011277
        //		else if (i < e.BUTTON1_MASK && i >= e.BUTTON2_MASK)				//011277
        //			return e.BUTTON2_MASK;										//011277
        //		else															//011277
        //			return e.BUTTON3_MASK;										//011277
    } //011277

    /**
     * appendArray
     * @param _sb
     * @param _title
     * @param _ra
     * @return
     * @author Anthony C. Liberto
     */
    public static StringBuffer appendArray(StringBuffer _sb, String _title, String[] _ra) {
        if (_ra != null) {
            int ii = _ra.length;
            for (int i = 0; i < ii; ++i) {
                _sb.append(_title + " " + i + " of " + ii + ": " + _ra[i] + RETURN);
            }
        }
        return _sb;
    }

    /*
     * @deprecated
     * use getStringArray(str, RETURN);
     */
    /**
     * splitStringByLines
     * @param str
     * @return
     * @author Anthony C. Liberto
     */
    public static String[] splitStringByLines(String str) {
        int lines = -1;
        int line = 0;
        StringTokenizer st = null;
        if (str == null || str.equals(STR)) {
            return strs;
        }

        STR = str;

        //21805		int lines = getReturnCount(str);
        //21805		strs = new String[lines];
        //21805		StringTokenizer st = new StringTokenizer(str, RETURN);
        st = new StringTokenizer(str, RETURN); //21805
        lines = st.countTokens(); //21805
        strs = new String[lines]; //21805

        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            if (have(s)) {
                strs[line++] = s;
            }
        }

        return strs;
    }

    /*
     * @deprecated
     * use getCharacterCount(s, s.length, '\n');
     */
    /**
     * getReturnCount
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static int getReturnCount(String s) {
        return getCharacterCount(s, RETURN);
    }

    /**
     * getCharacterCount
     * @param _s
     * @param _delim
     * @return
     * @author Anthony C. Liberto
     */
    public static int getCharacterCount(String _s, String _delim) {
        StringTokenizer st = new StringTokenizer(_s, _delim);
        return st.countTokens();
    }

    /**
     * getTypeName
     * @param ss
     * @return
     * @author Anthony C. Liberto
     */
    public static String getTypeName(String ss) {
        String s = ss.trim();
        if (s.equals("I")) {
            return "ID";

        } else if (s.equals("T")) {
            return "Text";

        } else if (s.equals("B")) {
            return "Blob";

        } else if (s.equals("L")) {
            return "LongText";

        } else if (s.equals("P")) {
            return "Prose";

        } else if (s.equals("D")) {
            return "Document";

        } else if (s.equals("U")) {
            return "UniqueFlag";

        } else if (s.equals("F")) {
            return "MultiFlag";

        } else if (s.equals("X")) {
            return "XML";

        } else {
            return "Unknown:  " + s;
        }
    }

    /**
     * deleteString
     * @param oldStr
     * @param deleteStr
     * @return
     * @author Anthony C. Liberto
     */
    public static String deleteString(String oldStr, String deleteStr) {
        StringBuffer out = new StringBuffer(oldStr);
        int indx = -1;
        int newLen = -1;
        oldStr = oldStr.toLowerCase();
        deleteStr = deleteStr.toLowerCase();
        indx = oldStr.lastIndexOf(deleteStr);
        newLen = deleteStr.length();

        while (indx != -1) {
            out.delete(indx, indx + newLen);
            indx = oldStr.lastIndexOf(deleteStr, indx - 1);
        }

        return out.toString();
    }

    //	public static ImageIcon grabIcon(String s) {
    //		String dir = "COM/ibm/opicm/client/Icons/";
    //		return new ImageIcon(dir + s);
    //	}

    /*
     *@deprecated
     * use replace.
     */
    /**
     * replaceString
     * @param oldStr
     * @param findStr
     * @param replaceStr
     * @return
     * @author Anthony C. Liberto
     */
    public static String replaceString(String oldStr, String findStr, String replaceStr) {
        return replace(oldStr, findStr, replaceStr);
    }

    /*
     50986
        public static String replace(String _current, String _find, String _replace) {
    		StringBuffer out = new StringBuffer(_current);
    		_current = _current.toLowerCase();
    		_find = _find.toLowerCase();
    		int indx = _current.lastIndexOf(_find);
    		int newLen = _find.length();

    		while (indx != -1) {
    			out.replace(indx, indx+newLen,_replace);
    			indx = _current.lastIndexOf(_find, indx -1);
    		}
    		return out.toString();
        }
    */
    /**
     * find
     * @param _strSearch
     * @param _strFind
     * @param _bCase
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean find(String _strSearch, String _strFind, boolean _bCase) {
        if (_bCase) {
            return (_strSearch.indexOf(_strFind) != -1);

        } else {
            return (_strSearch.toUpperCase().indexOf(_strFind.toUpperCase()) != -1);
        }
    }

    /**
     * replaceChar
     * @param _str
     * @param _oldChar
     * @param _newChar
     * @return
     * @author Anthony C. Liberto
     */
    public static String replaceChar(String _str, char _oldChar, char _newChar) {
        return _str.replace(_oldChar, _newChar);
    }

    /**
     * maxFlag
     * @param str
     * @return
     * @author Anthony C. Liberto
     */
    public static String maxFlag(String str) {
        String[] s = splitStringByLines(str);
        int ii = s.length;
        String strMax = "";
        for (int i = 0; i < ii; ++i) {
            strMax = maxString(strMax, s[i]);
        }
        return strMax;
    }

    /**
     * isDelimited
     * @param _s
     * @param _delim
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean isDelimited(String _s, String _delim) {
        if (_s != null && _delim != null) {
            return (_s.indexOf(_delim) > 0);
        }
        return false;
    }

    /**
     * getStringArray
     * @param _s
     * @param _delim
     * @param _allowBlanks
     * @return
     * @author Anthony C. Liberto
     */
    public static String[] getStringArray(String _s, String _delim, boolean _allowBlanks) {
        StringTokenizer st = new StringTokenizer(_s, _delim);
        Vector v = new Vector();
        int ii = -1;
        String[] out = null;
        while (st.hasMoreTokens()) {
            String s = st.nextToken().trim();
            if (Routines.have(s)) {
                v.add(s);
            } else if (_allowBlanks) {
                v.add("");
            }
        }
        if (v.isEmpty()) {
            return null;
        }
        ii = v.size();
        out = new String[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = (String) v.get(i);
        }
        return out;
    }

    /**
     * getStringArray
     * @param _s
     * @param _delim
     * @return
     * @author Anthony C. Liberto
     */
    public static String[] getStringArray(String _s, String _delim) {
        return getStringArray(_s, _delim, false);
    }

    /**
     * getIntArray
     * @param _s
     * @param _delim
     * @return
     * @author Anthony C. Liberto
     */
    public static int[] getIntArray(String _s, String _delim) {
        StringTokenizer st = new StringTokenizer(_s, _delim);
        Vector v = new Vector();
        int ii = -1;
        int[] out = null;
        while (st.hasMoreTokens()) {
            String s = st.nextToken().trim();
            if (Routines.have(s)) {
                v.add(s);
            }
        }
        ii = v.size();
        out = new int[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = getInt((String) v.get(i));
        }
        return out;
    }

    /**
     * getInt
     * @param _s
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public static int getInt(String _s, int _def) {
        if (!have(_s)) {
            return _def;
        }
        try {
            return Integer.valueOf(_s).intValue();
        } catch (NumberFormatException nfe) {
            EAccess.report(nfe,false);
        }
        return _def;
    }

    /**
     * getInt
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static int getInt(String _s) {
        return getInt(_s, 0);
    }

    /**
     * getBoolean
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean getBoolean(String _s) {
        Boolean b = new Boolean(_s);
        return b.booleanValue();
    }

    /**
     * getInteger
     * @param _s
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public static Integer getInteger(String _s, int _def) {
        try {
            return new Integer(_s);
        } catch (NumberFormatException nfe) {
            EAccess.report(nfe,false);
        }
        return new Integer(_def);
    }

    /**
     * splitString
     * @param str
     * @return
     * @author Anthony C. Liberto
     */
    public static Object[] splitString(String str) {
        Object[] out = new Object[2];
        String[] s = splitStringByLines(str);
        int ii = s.length;
        String strMax = "";
        for (int i = 0; i < ii; ++i) {
            strMax = maxString(strMax, s[i]);
        }
        out[0] = strMax;
        out[1] = s;
        return out;
    }

    /**
     * proseMax
     * @param str
     * @return
     * @author Anthony C. Liberto
     */
    public static String proseMax(String str) {
        return proseMax(str, 64);
    }

    /**
     * proseMax
     * @param str
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public static String proseMax(String str, int i) {
        String s = splitString(str, i);
        int indx = s.indexOf('\n');
        if (indx == -1) {
            return s;
        }
        return s.substring(0, indx);
    }

    /**
     * maxString
     * @param s
     * @param ss
     * @return
     * @author Anthony C. Liberto
     */
    public static String maxString(String s, String ss) {
        if (s == null && ss == null) {
            return "";

        } else if (s == null) {
            return ss;

        } else if (ss == null) {
            return s;
        }
        if (s.trim().length() > ss.trim().length()) {
            return s.trim();
        }
        return ss.trim();
    }
    /**
     * make sure that at least one return occurs every length characters
     * place return in closest space position if possible.
     *
     * @return String
     * @param length
     * @param str
     */
    public static String splitString(String str, int length) { //013176
        int ii = -1;
        int lastSpace = 0; //013176
        int lastReturn = 0; //013176
        int returnAdjust = 0; //013176
        StringBuffer sb = null;
        if (str == null) { //52847
            return null; //52847
        } //52847
        ii = str.length(); //013176
        if (ii < length) { //013176
            return str;
        } //013176
        sb = new StringBuffer(); //013176
        for (int i = 0; i < ii; ++i) { //013176
            char c = str.charAt(i); //013176
            if (c == '\n') { //013176
                lastReturn = sb.length();
            } else if (c == ' ') { //013176
                lastSpace = sb.length();
            }
            if ((i - lastReturn) >= length) { //013176
                if (lastSpace > lastReturn) { //013176
                    if (lastSpace < sb.length()) {
                        sb.setCharAt(lastSpace, '\n'); //013176
                    } else {
                        c = '\n';
                    }
                    lastReturn = 0 + lastSpace; //013176
                } else { //013176
                    lastReturn = sb.length() - returnAdjust; //013176
                    ++returnAdjust;
                    sb.append('\n'); //013176
                } //013176
            } //013176
            sb.append(c); //013176
        } //013176
        return sb.toString(); //013176
    } //013176

    /**
     * getRowsForString
     * @param s
     * @param cols
     * @param fm
     * @return
     * @author Anthony C. Liberto
     */
    public static int getRowsForString(String s, int cols, FontMetrics fm) { //013688
        String str = splitString(s, cols);
        return getReturnCount(str);
        //		int chrWidth = fm.charWidth('m');										//013688
        //		int cLen = cols * chrWidth;												//013688
        //		int sLen = fm.stringWidth(s);											//013688
        //		int rows = sLen / cLen;													//013688
        //		if ((sLen % cLen) > 0)													//013688
        //			++rows;																//013688
        //		return rows;															//013688
    } //013688

    /**
     * capEditable
     * @param cap
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean capEditable(String cap) {
        //e1.0		if (cap.trim().equals("W") || cap.trim().equals("*"))
        if (cap.trim().equals("W") || cap.trim().equals("*") || cap.trim().equals("C")) { //e1.0
            return true;
        }
        return false;
    }

    /**
     * capViewable
     * @param cap
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean capViewable(String cap) {
        if (cap.equals("W") || cap.equals("L")) {
            return true;
        }
        return false;
    }

    /**
     * maxInt
     * @param i
     * @param ii
     * @return
     * @author Anthony C. Liberto
     */
    public static int maxInt(int i, int ii) {
        return Math.max(i, ii);
    }

    /*
     dwb_20041117
    	public static String parseEntity(String s) {
    		int indx = s.lastIndexOf(":");
    		String str = new String(s.substring(0,indx));
    		return str;
    	}

    	public static int parseEntityID(String s) {
    		int indx = s.lastIndexOf(":") + 1;
    		String str = new String(s.substring(indx));
    		int i = Integer.valueOf(str).intValue();
    		return i;
    	}
    */
    /**
     * have
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean have(String s) {
        String test = null;
        try {
            if (s == null) {
                return false;
            }
            test = s.trim();
            if (test == null || test.length() == 0 || test.equals("")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            EAccess.report(ex,false);
        }
        return false;
    }

    /**
     * have
     * @param o
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean have(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof String) {
            return have((String) o);
        } else {
            return true;
        }
    }

    /**
     * flagLike
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean flagLike(String s) {
        String t = new String(s.trim());
        //2.4		if (t.equalsIgnoreCase("U") || t.equalsIgnoreCase("F"))
        if (t.equalsIgnoreCase("U") || t.equalsIgnoreCase("F") || t.equalsIgnoreCase("S")) { //2.4
            return true;

        } else {
            return false;
        }
    }

    /**
     * textLike
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean textLike(String s) {
        String t = new String(s.trim());
        if (t.equalsIgnoreCase("T") || t.equalsIgnoreCase("I")) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * blobLike
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean blobLike(String s) {
        String t = new String(s.trim());
        if (t.equalsIgnoreCase("B") || t.equalsIgnoreCase("P") || t.equalsIgnoreCase("D")) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * have
     * @param chars
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean have(char[] chars) {
        String test = new String(chars);
        test = test.trim();
        if (test.toString() == null) {
            return false;

        } else if (test.length() == 0) {
            return false;

        } else if (test.toString() == "") {
            return false;

        } else {
            return true;
        }
    }

    /**
     * isEqual
     * @param c
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean isEqual(char c, String s) {
        String ss = new String(String.valueOf(c));
        return s.equals(ss);
    }

    /**
     * isNumeric
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean isNumeric(String s) {
        int len = s.length() - 4;
        for (int i = 0; i < len; ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * GetEntityClass
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static String GetEntityClass(String s) {
        String str = new String();
        s.trim();
        if (s == "E") {
            str = "Entity";

        } else {
            str = "Relator";
        }
        return str;
    }

    /**
     * orLess
     * @param criteria
     * @param in
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean orMore(int criteria, int in) { //013005//013004
        if (in >= criteria) { //013005//013004
            return true;
        } //013005//013004
        return false; //013005//013004
    } //013005//013004

    /**
     * orLess
     * @param criteria
     * @param in
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean orLess(int criteria, int in) { //013005//013004
        if (in <= criteria) { //013005//013004
            return true;
        } //013005//013004
        return false; //013005//013004
    } //013005//013004

    /**
     * truncate
     * @param s
     * @param len
     * @return
     * @author Anthony C. Liberto
     */
    public static String truncate(String s, int len) { //013437
        if (s == null) { //eAnnounce1.1 9/17/01
            return "";
        } //eAnnounce1.1 9/17/01
        if (s.length() <= len) { //013437
            return s;
        } //013437
        return s.substring(0, len) + "..."; //013437
    } //013437

    /**
     * endsWithIgnoreCase
     * @param _input
     * @param _end
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean endsWithIgnoreCase(String _input, String _end) {
        String input = new String(_input);
        String end = new String(_end);
        return input.toLowerCase().endsWith(end.toLowerCase());
    }

    /**
     * truncateFilename
     * @param _len
     * @param _file
     * @return
     * @author Anthony C. Liberto
     */
    public static String truncateFilename(int _len, String _file) { //18970
        if (_file.length() > _len) { //18970
            int i = _file.lastIndexOf("."); //18970
            String ext = _file.substring(i); //18970
            int extLen = ext.length(); //18970
            if (extLen > 4) { //18970
                String ext2 = ext.substring(0, 4); //18970
                String pre = _file.substring(0, (_len - 4)); //18970
                return pre + ext2; //18970
            } else { //18970
                String pre = _file.substring(0, (_len - extLen)); //18970
                return pre + ext; //18970
            } //18970
        } else { //18970
            return _file; //18970
        } //18970
    } //18970

    /**
     * getInformation
     * @param _att
     * @return
     * @author Anthony C. Liberto
     */
    public static String getInformation(EANAttribute _att) {
        if (_att == null) {
            return null;
        }
        return getParentInfo(_att) + getChildInfo(_att) + getMetaInfo(_att);
    }

    private static String getChildInfo(EANAttribute _att) {
        return "Child_Attribute_Code: " + _att.getAttributeCode() + RETURN + "Child_Short_Desc: " + _att.getShortDescription() + RETURN + "Child_Long_Desc: " + _att.getLongDescription() + RETURN + "Child_key: " + _att.getKey() + RETURN;
    }

    private static String getParentInfo(EANFoundation _o) {
        EANFoundation parent = _o.getParent();
        if (parent instanceof EntityItem) {
            EntityItem ei = (EntityItem) parent;
            return "Parent_Entity_Type: "
            + ei.getEntityType()
            + RETURN
            + "Parent_Entity_ID: "
            + ei.getEntityID()
            + RETURN
            + "Parent_Short_Desc: "
            + ei.getShortDescription()
            + RETURN
            + "Parent_Long_Desc: "
            + ei.getLongDescription()
            + RETURN
            + "Parent_Key: "
            + ei.getKey()
            + RETURN;
        }
        return "";
    }

    private static String getMetaInfo(EANAttribute _att) {
        EANMetaAttribute meta = _att.getMetaAttribute();
        if (meta != null) {
            return "actual long description: "
            + meta.getActualLongDescription()
            + RETURN
            + "actual short description: "
            + meta.getActualShortDescription()
            + RETURN
            + "alpha: "
            + meta.isAlpha()
            + RETURN
            + "attribute code: "
            + meta.getAttributeCode()
            + RETURN
            + "attribute type: "
            + meta.getAttributeType()
            + RETURN
            + "attribute type mapping: "
            + meta.getAttributeTypeMapping()
            + RETURN
            + "capability: "
            + meta.getCapability()
            + RETURN
            + "classified: "
            + meta.isClassified()
            + RETURN
            + "comboUnique: "
            + meta.isComboUnique()
            + RETURN
            + "comboUniqueAttributeCode: "
            + meta.getComboUniqueAttributeCode()
            + RETURN
            + "compare field: "
            + meta.getCompareField()
            + RETURN
            + "date: "
            + meta.isDate()
            + RETURN
            + "decimal: "
            + meta.isDecimal()
            + RETURN
            + "decimal Places: "
            + meta.getDecimalPlaces()
            + RETURN
            + "default value: "
            + meta.getDefaultValue()
            + RETURN
            + "displayable for filter: "
            + meta.isDisplayableForFilter()
            + RETURN
            + "domain controlled: "
            + meta.isDomainControlled()
            + RETURN
            + "editable: "
            + meta.isEditable()
            + RETURN
            + "equals len: "
            + meta.getEqualsLen()
            + RETURN
            + "exclude from copy: "
            + meta.isExcludeFromCopy()
            + RETURN
            + "future date: "
            + meta.isFutureDate() +(meta.isFutureDate()&&meta.isWarningDate()?" warning":"")
            + RETURN
            + "geo indicator: "
            + meta.isGeoIndicator()
            + RETURN
            + "greater: "
            + meta.isGreater()
            + RETURN
            + "greater value: "
            + meta.getGreater()
            + RETURN
            + "help value: "
            + meta.getHelpValueText()
            + RETURN
            + "integer: "
            + meta.isInteger()
            + RETURN
            + "max length: "
            + meta.getMaxLen()
            + RETURN
            + "min length: "
            + meta.getMinLen()
            + RETURN
            + "navigate: "
            + meta.isNavigate()
            + RETURN
            + "no blanks: "
            + meta.isNoBlanks()
            + RETURN
            + "numeric: "
            + meta.isNumeric()
            + RETURN
            + "ods length: "
            + meta.getOdsLength()
            + RETURN
            + "order: "
            + meta.getOrder()
            + RETURN
            + "past date: "
            + meta.isPastDate() +(meta.isPastDate()&&meta.isWarningDate()?" warning":"")
            + RETURN
            + "refresh Enabled: "
            + meta.isRefreshEnabled()
            + RETURN
            + "required: "
            + meta.isRequired()
            + RETURN
            + "searchable: "
            + meta.isSearchable()
            + RETURN
            + "select helper: "
            + meta.isSelectHelper()
            + RETURN
            + "short description: "
            + meta.getShortDescription()
            + RETURN
            + "sort type: "
            + meta.getSortType()
            + RETURN
            + "special: "
            + meta.isSpecial()
            + RETURN
            + "spell checkable: "
            + meta.isSpellCheckable()
            + RETURN
            + "super editable: "
            + meta.isSuperEditable()
            + RETURN
            + "time: "
            + meta.isTime()
            + RETURN
            + "unique: "
            + meta.isUnique()
            + RETURN
            + "unique class: "
            + meta.getUniqueClass()
            + RETURN
            + "unique type: "
            + meta.getUniqueType()
            + RETURN
            + "upper: "
            + meta.isUpper()
            + RETURN
            + "US English Only: "
            + meta.isUSEnglishOnly()
            + RETURN
            + "version: "
            + meta.getVersion()
            + RETURN
            + meta.dump(false);
        }
        return "meta unavailable";
    }

    /**
     * getColorInfo
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public static String getColorInfo(Color _c) {
        return "red: " + _c.getRed() + ", green: " + _c.getGreen() + ", blue: " + _c.getBlue();
    }

    /**
     * is
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean is(String _s) {
        return _s.equalsIgnoreCase("true");
    }

    /**
     * not
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean not(String _s) {
        return _s.equalsIgnoreCase("false");
    }

    /**
     * toString
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public static String toString(int _i) {
        return Integer.toString(_i);
    }

    /**
     * toString
     * @param _b
     * @return
     * @author Anthony C. Liberto
     */
    public static String toString(boolean _b) {
        return toString(new Boolean(_b));
    }

    /**
     * toString
     * @param _b
     * @return
     * @author Anthony C. Liberto
     */
    public static String toString(Boolean _b) {
        return _b.toString();
    }

    /**
     * toString
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static String toString(String[] _s) {
        StringBuffer sb = new StringBuffer();
        int ii = _s.length;
        for (int i = 0; i < ii; ++i) {
            if (i > 0) { //51431
                sb.append('\n');
            } //51431
            sb.append(_s[i]);
            //51431			sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * toString
     * @param _s
     * @param _delimit
     * @return
     * @author Anthony C. Liberto
     */
    public static String toString(String[] _s, String _delimit) {
        StringBuffer sb = new StringBuffer();
        int ii = _s.length;
        for (int i = 0; i < ii; ++i) {
            if (i > 0) {
                sb.append(_delimit);
            }
            sb.append(_s[i]);
        }
        return sb.toString();
    }

    /**
     * toDouble
     * @param _double
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static Double toDouble(Double _double, String _s) {
        try {
            return Double.valueOf(_s);
        } catch (NumberFormatException _nfe) {
            EAccess.report(_nfe,false);
        }
        return _double;
    }

    /**
     * toInteger
     * @param _integer
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static Integer toInteger(Integer _integer, String _s) {
        try {
            return Integer.valueOf(_s.trim());
        } catch (NumberFormatException _nfe) {
            EAccess.report(_nfe,false);
        }
        return _integer;
    }

    /**
     * toFloat
     * @param _float
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static Float toFloat(Float _float, String _s) {
        try {
            return Float.valueOf(_s.trim());
        } catch (NumberFormatException _nfe) {
            EAccess.report(_nfe,false);
        }
        return _float;
    }

    /**
     * toLong
     * @param _long
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static Long toLong(Long _long, String _s) {
        try {
            return Long.valueOf(_s.trim());
        } catch (NumberFormatException _nfe) {
            EAccess.report(_nfe,false);
        }
        return _long;
    }

    /**
     * toShort
     * @param _short
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static Short toShort(Short _short, String _s) {
        try {
            return Short.valueOf(_s.trim());
        } catch (NumberFormatException _nfe) {
            EAccess.report(_nfe,false);
        }
        return _short;
    }

    /**
     * toByte
     * @param _byte
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static Byte toByte(Byte _byte, String _s) {
        try {
            return Byte.valueOf(_s.trim());
        } catch (NumberFormatException _nfe) {
            EAccess.report(_nfe,false);
        }
        return _byte;
    }

    /**
     * toNumber
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static Number toNumber(String _s) {
        try {
            return Double.valueOf(_s.trim());
        } catch (NumberFormatException _nfe) {
            EAccess.report(_nfe,false);
        }
        return new Integer(-1);
    }

    /**
     * toDouble
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static double toDouble(String _s) {
        return toNumber(_s).doubleValue();
    }

    /**
     * toInt
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static int toInt(String _s) {
        return toNumber(_s).intValue();
    }

    /**
     * toFloat
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static float toFloat(String _s) {
        return toNumber(_s).floatValue();
    }

    /**
     * toLong
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static long toLong(String _s) {
        return toNumber(_s).longValue();
    }

    /**
     * toShort
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static short toShort(String _s) {
        return toNumber(_s).shortValue();
    }

    /**
     * toByte
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static byte toByte(String _s) {
        return toNumber(_s).byteValue();
    }

    /**
     * isInt
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean isInt(String _s) {
        try {
            Integer.valueOf(_s).intValue();
            return true;
        } catch (NumberFormatException _nfe) {
            _nfe.printStackTrace();
        }
        return false;
    }

    /**
     * display
     * @param _s
     * @author Anthony C. Liberto
     */
    public static void display(String _s) {
        if (_s == null) {
            System.out.println("    String is null");
        } else {
            String[] sa = new String[1];
            sa[0] = _s;
            display(sa);
        }
        return;
    }

    /**
     * display
     * @param _s
     * @author Anthony C. Liberto
     */
    public static void display(String[] _s) {
        if (_s == null) {
            System.out.println("    String is null");
        } else {
            int ii = _s.length;
            for (int i = 0; i < ii; ++i) {
                System.out.println("    String " + i + " of " + ii + " is: " + _s[i] + ".");
            }
        }
        return;
    }

    /**
     * getDisplayString
     * @param _s
     * @param _withReturn
     * @return
     * @author Anthony C. Liberto
     */
    public static String getDisplayString(String _s, boolean _withReturn) { //22563
        StringBuffer sb = new StringBuffer(); //22563
        char[] c = null;
        int ii = -1;
        if (_s == null) { //22563
            return "";
        } //22563
        c = _s.toCharArray(); //22563
        ii = c.length; //22563
        for (int i = 0; i < ii; ++i) { //22563
            if (c[i] == '\t') { //22563
                sb.append("    "); //22563
            } else if (c[i] == '\n' && _withReturn) { //22563
                sb.append(c[i]); //22563
            } else if (Character.isSpaceChar(c[i])) { //22563
                sb.append(c[i]); //22563
            } else if (!Character.isWhitespace(c[i])) { //22563
                sb.append(c[i]); //22563
            } //22563
        } //22563
        return sb.toString(); //22563
    } //22563

    //	public static LinkedList sortFlags(LinkedList in) {								//cs001213
    //		if (in == null)																//cs001213
    //			return in;																//cs001213
    //		LinkedList out = new LinkedList();											//cs001213
    //		int ii = in.size();															//cs001213
    //		Object[] o = new Object[ii];												//cs001213
    //		for (int i=0;i<ii;++i) {													//cs001213
    //			o[i] = in.get(i);														//cs001213
    //		}																			//cs001213
    //		Arrays.sort(o,new OpicmComparator());										//cs001213
    //		for (int i=0;i<ii;++i) {													//cs001213
    //			out.add(o[i]);															//cs001213
    //		}																			//cs001213
    //		return out;																	//cs001213
    //	}																				//cs001213

    /**
     * getProfileText
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public static String getProfileText(Profile _prof) {
        if (_prof != null) {
            return "Enterprise: "
            + _prof.getEnterprise()
            + RETURN
            + "OperatorName: "
            + _prof.getOPName()
            + RETURN
            + "OperatorID: "
            + _prof.getOPID()
            + RETURN
            + "OPWGID: "
            + _prof.getOPWGID()
            + RETURN
            + "ReadOnly: "
            + _prof.isReadOnly()
            + RETURN
            + "RoleCode: "
            + _prof.getRoleCode()
            + RETURN
            + "RoleDescription: "
            + _prof.getRoleDescription()
            + RETURN
            + "WorkGroupName: "
            + _prof.getWGName()
            + RETURN
            + "WorkGroupID: "
            + _prof.getWGID()
            + RETURN
            + "SessionID: "
            + _prof.getSessionID()
            + RETURN
            + "TranID: "
            + _prof.getTranID()
            + RETURN
            + "DefaultIndex: "
            + _prof.getDefaultIndex();
        }
        return null;
    }
    /*
     50986
     */
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
        int indx = -1;
        int iLenFind = -1;
        int iLenReplace = -1;

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

    /*
     pattern matching
     */

    /**
     * match
     * @param _pattern
     * @param _flag
     * @param _match
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean[] match(String _pattern, int _flag, String[] _match) {
        Pattern p = Pattern.compile(_pattern, _flag);
        int ii = _match.length;
        boolean[] bOut = new boolean[ii];
        for (int i = 0; i < ii; ++i) {
            if (_match[i] != null) {
                Matcher m = p.matcher(_match[i]);
                bOut[i] = m.matches();
            }
        }
        return bOut;
    }

    /**
     * match
     * @param _pattern
     * @param _flag
     * @param _match
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean match(String _pattern, int _flag, String _match) {
        Pattern p = Pattern.compile(_pattern, _flag);
        Matcher m = p.matcher(_match);
        return m.matches();
    }

    /**
     * match
     * @param _pattern
     * @param _match
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean match(String _pattern, String _match) {
        return Pattern.matches(_pattern, _match);
    }

    /*
     mw_update
     */
    /**
     * getClientTime
     * @return
     * @author Anthony C. Liberto
     */
    public static String getClientTime() {
        Date d = new Date();
        return d.toString();
    }

    /*
     acl_20030903
     */
    /**
     * sleep
     * @param _millis
     * @author Anthony C. Liberto
     */
    public static void sleep(int _millis) {
        sleep(Thread.currentThread(), 0, 0, _millis);
        return;
    }

    /**
     * sleep
     * @param _seconds
     * @param _millis
     * @author Anthony C. Liberto
     */
    public static void sleep(int _seconds, int _millis) {
        sleep(Thread.currentThread(), 0, _seconds, _millis);
        return;
    }

    /**
     * sleep
     * @param _minutes
     * @param _seconds
     * @param _millis
     * @author Anthony C. Liberto
     */
    public static void sleep(int _minutes, int _seconds, int _millis) {
        sleep(Thread.currentThread(), _minutes, _seconds, _millis);
        return;
    }

    /**
     * sleep
     * @param _thread
     * @param _millis
     * @author Anthony C. Liberto
     */
    public static void sleep(Thread _thread, int _millis) {
        sleep(_thread, 0, 0, _millis);
        return;
    }

    /**
     * sleep
     * @param _thread
     * @param _seconds
     * @param _millis
     * @author Anthony C. Liberto
     */
    public static void sleep(Thread _thread, int _seconds, int _millis) {
        sleep(_thread, 0, _seconds, _millis);
        return;
    }

    /**
     * sleep
     * @param _thread
     * @param _minutes
     * @param _seconds
     * @param _millis
     * @author Anthony C. Liberto
     */
    public static void sleep(Thread _thread, int _minutes, int _seconds, int _millis) {
        if (_thread != null) {
            int millis = ((_minutes * 60000) + (_seconds * 1000) + _millis);
            if (millis > 0) {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException _ie) {
                    _ie.printStackTrace();
                }
            }
        }
        return;
    }

    /**
     * pause
     * @param _millis
     * @author Anthony C. Liberto
     */
    public static void pause(int _millis) {
        pause(Thread.currentThread(), _millis);
        return;
    }

    /**
     * pause
     * @param _thread
     * @param _millis
     * @author Anthony C. Liberto
     */
    public static void pause(Thread _thread, int _millis) {
        if (_millis > 0) {
            try {
                Thread.sleep(_millis);
            } catch (InterruptedException _ie) {
                _ie.printStackTrace();
            }
        }
        return;
    }

    /**
     * yield
     * @author Anthony C. Liberto
     */
    public static void yield() {
        yield(java.lang.Thread.currentThread());
        return;
    }

    /**
     * yield
     * @param _t
     * @author Anthony C. Liberto
     */
    public static void yield(Thread _t) {
        if (_t != null) {
            Thread.yield();
        }
        return;
    }

    /*
     acl_20031006
     */
    /**
     * getRandomString
     * @return
     * @author Anthony C. Liberto
     */
    public static String getRandomString() {
        return toString(getRandom());
    }

    /**
     * getRandom
     * @return
     * @author Anthony C. Liberto
     */
    public static int getRandom() {
        Random rand = new Random(new Date().getTime());
        return rand.nextInt();
    }

    /*
     acl_20031031
     */
    /*
     com.eLogin.routines.test(String);
     */
    /**
     * test
     * @param _str
     * @author Anthony C. Liberto
     */
    public static void test(String _str) {
        test(_str, false);
        return;
    }

    /*
     com.eLogin.routines.test();
     */
    /**
     * test
     * @author Anthony C. Liberto
     */
    public static void test() {
        java.lang.Thread.dumpStack();
        return;
    }

    /*
     com.eLogin.routines.test(String,boolean);
     */
    /**
     * test
     * @param _str
     * @param _dumpStack
     * @author Anthony C. Liberto
     */
    public static void test(String _str, boolean _dumpStack) {
        if (_str != null) {
            System.out.println(_str);
        }
        if (_dumpStack) {
            test();
        }
        return;
    }

    /**
     * whoHasFocus
     * @return
     * @author Anthony C. Liberto
     */
    public static String whoHasFocus() {
        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (c != null) {
            return "focus owned by: " + c.getClass().getName();
        }
        return "Unable to determine focus owner";
    }

	/**
	 * combine to arrays into a single array
	 *
	 * @author Anthony C. Liberto
	 * @return int[]
	 * @param _i
	 * @param _x
	 */
	public static int[] combine(int[] _i, int [] _x) {
		int i = -1;
		int x = -1;
		int[] iOut = null;
        if (_i != null) {
			i = _i.length;
		}
		if (_x != null) {
			x = _x.length;
		}
		if (i <= 0) {
			return _x;
		}
		if (x <= 0) {
			return _i;
		}
		iOut = new int[i+x];
		System.arraycopy(_i,0,iOut,0,i);
		System.arraycopy(_x,0,iOut,i,x);
		return iOut;
	}

	/**
	 * combine to arrays into a single array
	 *
	 * @author Anthony C. Liberto
	 * @return Object[]
	 * @param _i
	 * @param _x
	 */
	public static Object[] combine(Object[] _i, Object[] _x) {
		int i = -1;
		int x = -1;
        Object[] oOut = null;
        if (_i != null) {
			i = _i.length;
		}
		if (_x != null) {
			x = _x.length;
		}
		if (i <= 0) {
			return _x;
		}
		if (x <= 0) {
			return _i;
		}
		oOut = new Object[i+x];
		System.arraycopy(_i,0,oOut,0,i);
		System.arraycopy(_x,0,oOut,i,x);
		return oOut;
	}

	/**
	 * trim
	 *
	 * @author Anthony C. Liberto
	 * @param _s
	 * @param _i
	 * @return String
	 */
	public static String trim(String _s, int _i) {
		if (_s != null) {
			if (_s.length() >= _i) {
				return _s.substring(0,_i);
			}
		}
		return _s;
	}

	/**
	 * dump properties
	 * @author tony
	 */
	public static void dumpProperties() {
		dumpProperties(System.getProperties());
		return;
	}

	/**
	 * dump all the properties
	 * shows the properties we have to work with
	 * @param _prop
	 * @author tony
	 */
    public static void dumpProperties(Properties _prop) {
		if (_prop != null && !_prop.isEmpty()) {
			Enumeration eNum = _prop.keys();
			System.out.println("dumping properties...");
			while (eNum.hasMoreElements()) {
				String str = (String)eNum.nextElement();
				System.out.println("    " + str + ":" + _prop.getProperty(str));
			}
			System.out.println("dump complete");
		}
		return;
	}
}
