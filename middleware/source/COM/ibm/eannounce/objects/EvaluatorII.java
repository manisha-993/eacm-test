/**
 * Copyright (c) 2001 International Buisiness Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @version    2.4
 * @date       2001/08/16
 * @author     Joan Tran
 *
 * $Log: EvaluatorII.java,v $
 * Revision 1.48  2013/07/26 19:05:14  wendy
 * fix locking rules
 *
 * Revision 1.47  2008/02/01 22:10:08  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.46  2006/02/20 21:50:04  joan
 * clean up System.out.println
 *
 * Revision 1.45  2006/02/20 21:39:47  joan
 * clean up System.out.println
 *
 * Revision 1.44  2005/03/03 18:59:35  dave
 * small JTest Fix
 *
 * Revision 1.43  2005/03/03 18:36:51  dave
 * more Jtest
 *
 * Revision 1.42  2003/06/12 23:08:29  joan
 * move changes from v111
 *
 * Revision 1.41  2003/06/06 00:04:19  joan
 * move changes from v111
 *
 * Revision 1.40  2003/05/14 16:11:17  dave
 * removing uneeded commented out code
 *
 * Revision 1.39  2003/05/13 22:23:08  joan
 * remove System.out
 *
 * Revision 1.38  2003/05/08 21:17:44  dave
 * tracking so we can find some code stacks
 *
 * Revision 1.37  2003/04/30 19:11:07  dave
 * adding index + 1
 *
 * Revision 1.36  2003/04/30 16:56:05  dave
 * typo
 *
 * Revision 1.35  2003/04/30 16:43:27  dave
 * more efficiencies
 *
 * Revision 1.34  2003/04/30 08:58:53  dave
 * fix
 *
 * Revision 1.33  2003/04/30 08:48:04  dave
 * almost there
 *
 * Revision 1.32  2003/04/30 08:35:15  dave
 * another fix
 *
 * Revision 1.31  2003/04/30 08:22:13  dave
 * more
 *
 * Revision 1.30  2003/04/30 08:00:06  dave
 * another way
 *
 * Revision 1.29  2003/04/30 07:27:36  dave
 * using array of string now
 *
 * Revision 1.28  2003/04/30 07:05:59  dave
 * trace
 *
 * Revision 1.27  2003/04/30 06:45:01  dave
 * more shortcuts
 *
 * Revision 1.26  2003/04/30 06:40:29  dave
 * more optimization
 *
 * Revision 1.25  2003/04/30 06:16:50  dave
 * fixing paran processing
 *
 * Revision 1.24  2003/04/30 06:10:48  dave
 * more simplification
 *
 * Revision 1.23  2003/04/30 05:33:58  dave
 * remove trace
 *
 * Revision 1.22  2003/04/30 05:17:48  dave
 * zeroing in
 *
 * Revision 1.21  2003/04/30 05:04:29  dave
 * flood plane
 *
 * Revision 1.20  2003/04/30 04:47:59  dave
 * more changes
 *
 * Revision 1.19  2003/04/30 04:14:04  dave
 * comparator fix
 *
 * Revision 1.18  2003/04/30 03:56:11  dave
 * trace
 *
 * Revision 1.17  2003/04/30 03:45:39  dave
 * trying to make evaluator run faster
 *
 * Revision 1.16  2003/04/30 02:07:42  dave
 * removed stopwatch for timing
 *
 * Revision 1.15  2003/04/28 22:49:36  dave
 * adding toClassificationString on entityitem
 *
 * Revision 1.14  2003/04/23 19:56:36  dave
 * import statement missing (Stopwatch)
 *
 * Revision 1.13  2003/04/23 19:48:29  dave
 * adding the timings for the test method
 *
 * Revision 1.12  2003/04/16 04:25:37  dave
 * remove trace
 *
 * Revision 1.11  2003/04/16 04:00:35  dave
 * almost there!
 *
 * Revision 1.10  2003/04/16 03:45:52  dave
 * language foul
 *
 * Revision 1.9  2003/04/16 03:37:44  dave
 * and again
 *
 * Revision 1.8  2003/04/16 03:36:05  dave
 * need to get the set to 3 so it can process the atomic expression
 * w/o recursion
 *
 * Revision 1.7  2003/04/16 03:22:26  dave
 * fix
 *
 * Revision 1.6  2003/04/16 03:15:06  dave
 * pointers.. pointers...
 *
 * Revision 1.5  2003/04/16 03:07:25  dave
 * recusion issue
 *
 * Revision 1.4  2003/04/16 02:51:33  dave
 * fixing precidence for == !=
 *
 * Revision 1.3  2003/04/15 23:15:43  dave
 * minor fix
 *
 * Revision 1.2  2003/04/15 23:04:15  dave
 * made everything static
 *
 * Revision 1.1  2003/04/15 22:50:09  dave
 * new Evalutor based upon 1.3 java and vectors
 *
 */

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;

import java.util.StringTokenizer;
import java.io.Serializable;
import COM.ibm.opicmpdh.middleware.D;

/**
 *  Description of the Class
 *
 * @author     davidbig
 * @created    April 15, 2003
 */
public class EvaluatorII implements Serializable, Cloneable {

    /**
     * @serial
     */
    final static long serialVersionUID = 10L;
    /**
     */
    public static final String OPERATORS = "&|?=<>!";
    /**
     */
    public static final String PARENS = "()";
    /**
     */
    public static final String DOTS = ".";
    /**
     */
    public static final String BLANK = " ";
    /**
     */
    public static final String VARTRIGGER = ":";

    /**
     * A way to test this on its own
     *
     * @param arg
     */
    public static void main(String[] arg) {
        System.out.println("the asnwer is:" + evaluate(prep(null, arg[0])));
    }

    /**
    *Constructor for the Evaluator object
    */
    private EvaluatorII() {
    }

    /**
     * This is the thing everyone calls to evaluate what
     *
     * @param _strCondition
     * @return boolean
     * @param _ei
     */
    public static boolean test(EntityItem _ei, String _strCondition) {

        // We have to check role
        Profile prof = _ei.getProfile();

        if (_strCondition == null || _strCondition.equals("")) {
            return false;
        }

        // O.K.  We want to pick off any role information here... So lets see if the role is present
        // We look because a ? is used as a divider
        if (_strCondition.indexOf("?") != -1) {
            StringTokenizer st = new StringTokenizer(_strCondition, "?");
            String strRoleCode = st.nextToken();
            _strCondition = st.nextToken();

            if (prof != null) {
                if (!strRoleCode.equals(prof.getRoleCode())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return evaluate(prep(_ei, _strCondition));

    }

    /**
     *  Gets the first setFlag attribute of the Evaluator object
     *
     * @param  _fa  The Flag Attribute
     * @return      The setFlag value
     */
    private static String getSetFlag(EANFlagAttribute _fa) {
        MetaFlag[] amf = (MetaFlag[]) _fa.get();
        for (int ii = 0; ii < amf.length; ii++) {
            if (amf[ii].isSelected()) {
                return amf[ii].getFlagCode();
            }
        }
        return "";
    }

    /**
     *  Gets the value attribute of the Evaluator object
     *
     * @param  _ei          Description of the Parameter
     * @param  _strAttCode  Description of the Parameter
     * @return              The value value
     */
    private static String getValue(EntityItem _ei, String _strAttCode) {

        EANAttribute att = null;
        EANMetaAttribute ma = null;

        if (_ei == null && _strAttCode == null) {
            D.ebug(D.EBUG_SPEW,"getValue() -- EntityItem and attributeCode required.");
            return "";
        } else if (_ei == null) {
            D.ebug(D.EBUG_SPEW,"Evaluator.getValue requires non-null Entity Item");
            return "";
        } else if (_strAttCode == null) {
            D.ebug(D.EBUG_SPEW,"Evaluator.getValue requires non-null attCode");
            return "";
        }
        
        att = _ei.getAttribute(_strAttCode);
        if (att == null) {
        	D.ebug(D.EBUG_SPEW,"getValue() -- attribute not found for "+_ei.getKey()+" " + _strAttCode + ". Returning empty string");
            return "";
        }
        
        // Lets get the PDHMeta Attribute
        ma = att.getMetaAttribute();
        if (ma == null) {
            D.ebug(D.EBUG_SPEW,"getValue() -- meta not found for "+_ei.getKey()+" " + _strAttCode + ". Returning empty string");
            return "";
        }
        if (att instanceof EANFlagAttribute) {
            EANFlagAttribute fa = (EANFlagAttribute) att;
            return getSetFlag(fa);
        } else {
            EANTextAttribute ta = (EANTextAttribute) att;
            return ta.toString();
        }
    }

    /**
     * prep
     *
     * @param _ei
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public static String[] prep(EntityItem _ei, String _str) {

        String astr2[] = null;

        StringTokenizer st = new StringTokenizer(_str, OPERATORS + PARENS + DOTS + BLANK, true);
        int isize = st.countTokens();
        String astr1[] = new String[isize];
        int fsize = 0;
        
        for (int ii = 0; ii < isize; ii++) {
            String strC = st.nextToken();
            if (!strC.equals(" ")) {
                if (OPERATORS.indexOf(strC) != -1) {
                    strC = strC + st.nextToken();
                    ii++;
                } else if (DOTS.indexOf(strC) != -1) {
                    strC = strC + st.nextToken() + st.nextToken();
                    ii++;
                    ii++;
                } else if (strC.indexOf(VARTRIGGER) == 0) {
                    strC = getValue(_ei, strC.substring(1));
                }
                astr1[fsize++] = strC;
            }
        }

        astr2 = new String[fsize];
        for (int ii = 0; ii < fsize; ii++) {
            astr2[ii] = astr1[ii];
        }
        return astr2;
    }

    private static boolean evaluate(String[] _astr) {

        String str = "";
        int iSIndex = -1;
        boolean bcontinue = true;

        for (int ii = 0; ii < _astr.length; ii++) {
            str = str + " " + _astr[ii];
        }

        // O.K.. lets do a while loop and stop recursion
        while ((iSIndex = contains(_astr, "==", iSIndex + 1)) != -1) {
            flood(_astr, iSIndex, (bcontinue = evaluatePrimative(_astr[iSIndex - 1], _astr[iSIndex].charAt(0), _astr[iSIndex + 1])), false);
            if (!bcontinue && iSIndex + 2 < _astr.length && _astr[iSIndex + 2].equals("&&")) {
                return false;
            }
        }
        while ((iSIndex = contains(_astr, "!=", iSIndex + 1)) != -1) {
            flood(_astr, iSIndex, (bcontinue = evaluatePrimative(_astr[iSIndex - 1], _astr[iSIndex].charAt(0), _astr[iSIndex + 1])), false);
        }

        while ((iSIndex = contains(_astr, ">", iSIndex + 1)) != -1) {
            flood(_astr, iSIndex, (bcontinue = evaluatePrimative(_astr[iSIndex - 1], _astr[iSIndex].charAt(0), _astr[iSIndex + 1])), false);
        }

        while ((iSIndex = contains(_astr, "<", iSIndex + 1)) != -1) {
            flood(_astr, iSIndex, (bcontinue = evaluatePrimative(_astr[iSIndex - 1], _astr[iSIndex].charAt(0), _astr[iSIndex + 1])), false);
        }

        while ((iSIndex = contains(_astr, "||", iSIndex + 1)) != -1) {
        	//if an attribute does not have a value, an empty string is returned from getValue() in prep()
        	//prevent java.lang.StringIndexOutOfBoundsException - assumption - no restriction if no value
        	if(_astr[iSIndex - 1].length()==0 || _astr[iSIndex + 1].length()==0){
        		return false;
        	}
            flood(_astr, iSIndex, evaluatePrimative(_astr[iSIndex - 1].charAt(0), _astr[iSIndex].charAt(0), _astr[iSIndex + 1].charAt(0)), true);
        }

        bcontinue = true;
        while ((iSIndex = contains(_astr, "&&", iSIndex + 1)) != -1 && bcontinue) {
         	//if an attribute does not have a value, an empty string is returned from getValue() in prep()
         	//prevent java.lang.StringIndexOutOfBoundsException - assumption - no restriction if no value
         	if(_astr[iSIndex - 1].length()==0 || _astr[iSIndex + 1].length()==0){
         		return false;
         	}
            flood(_astr, iSIndex, (bcontinue = evaluatePrimative(_astr[iSIndex - 1].charAt(0), _astr[iSIndex].charAt(0), _astr[iSIndex + 1].charAt(0))), true);
        }

        return (_astr[0].equals("T"));

    }


    private static int contains(String[] _astr, String _str, int _i) {
        for (int ii = _i; ii < _astr.length; ii++) {
            if (_astr[ii].trim().equals(_str.trim())) {
                return ii;
            }
        }
        return -1;
    }


    private static boolean evaluatePrimative(char _cLeft, char _cOp, char _cRight) {

        if (_cOp == '|' && (_cRight == 'T' || _cLeft == 'T')) {
            return true;
        } else if (_cOp == '&' && (_cRight == 'T' && _cLeft == 'T')) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean evaluatePrimative(String _strLeft, char _cOp, String _strRight) {
        if (_cOp == '=') {
            return (_strLeft.equals(_strRight));
        } else if (_cOp == '!') {
            return (!_strLeft.equals(_strRight));
        } else if (_cOp == '>') {
            return (_strLeft.compareToIgnoreCase(_strRight) > 0);
        } else if (_cOp == '<') {
            return (_strLeft.compareToIgnoreCase(_strRight) < 0);
        } else {
            return false;
        }
    }

    private static void flood(String[] _astr, int _i, boolean _b, boolean _bspill) {

        //  First set it up
        _astr[_i] = (_b ? "T" : "F");

        if (!_bspill) {
            _astr[_i - 1] = (_b ? "T" : "F");
            _astr[_i + 1] = (_b ? "T" : "F");
        } else {
            // then go backwards as long as we are T or F
            for (int ii = _i - 1; ii > -1; ii--) {
                if ((_astr[ii].equals("T") || _astr[ii].equals("F")) && !_astr[ii].equals(_b ? "T" : "F")) {
                    _astr[ii] = (_b ? "T" : "F");
                } else {
                    break;
                }
            }

            for (int ii = _i + 1; ii < _astr.length; ii++) {
                if ((_astr[ii].equals("T") || _astr[ii].equals("F")) && !_astr[ii].equals(_b ? "T" : "F")) {
                    _astr[ii] = (_b ? "T" : "F");
                } else {
                    break;
                }
            }
        }
    }

}
