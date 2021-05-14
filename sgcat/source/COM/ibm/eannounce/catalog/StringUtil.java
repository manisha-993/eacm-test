//
// Copyright (c) 2005, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: StringUtil.java,v $
// Revision 1.3  2011/05/05 11:21:33  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:29  jingb
// no message
//
// Revision 1.2  2006/07/31 23:14:42  gregg
// remove infinite loop from replaceAll
//
// Revision 1.1.1.1  2006/03/30 17:36:31  gregg
// Moving catalog module from middleware to
// its own module.
//
// Revision 1.3  2005/11/22 21:52:28  gregg
// oh yeah move replaceFirst to StringUtil and use this method per jre 1.3.1
//
// Revision 1.2  2005/11/22 21:16:23  gregg
// more testing
//
// Revision 1.1  2005/11/22 21:04:05  gregg
// initial load
//

package COM.ibm.eannounce.catalog;

import java.util.*;

public class StringUtil {

	public static final void main(String[] _args) {

        String s = "~PLANAR.TOTCARDSLOT~(~DERIVEDDATA.TOTAVAILCARDSLOT~) x ~MECHPKG.TOTBAY~(~DERIVEDDATA.TOTAVAILBAY~)";

        System.out.println(s);
        System.out.println(numOccurances("~",s));
        System.out.println(numSubstitutionElements("~",s));

        int i = 3;
        System.out.println("\nchecking the " + i + "th Element in " + s);
        System.out.println(getSubstitutionElement(s,"~",i));

        String strFinal = s;
        for(int ii = 1; ii <= numSubstitutionElements("~",s); ii++) {
			String strReplace = "~" + getSubstitutionElement(s,"~",ii) + "~";
			System.out.println("replacing:" + strReplace + " in " + strFinal);
			strFinal = replaceSubstitutionElement(strFinal,strReplace,"REPLACED!!");
		}

		System.out.println("\nFinally we have:" + strFinal);

	}

/**
 * Count number of occurances of _strChar in _strCheck.
 *  Note: _strChar should be a single char String for consistent results.
 */
    public static int numOccurances(String _strChar, String _strCheck) {
		String s = _strCheck;
		int iCounter = 0;
		while(s.indexOf(_strChar) > -1) {
			//System.out.println(s + "," + s.indexOf(_strChar));
			s = s.substring(s.indexOf(_strChar)+1,s.length());
			iCounter++;
		}
		return iCounter;
	}


/**
 * Count number of occurances of _strChar substrings between the _strDelims in _strCheck.
 *  Note: _strChar should be a single char String for consistent results.
 */
	public static int numSubstitutionElements(String _strDelim, String _strCheck) {
		return numOccurances(_strDelim, _strCheck)/2;
	}

/**
 * Get the _iElementNum occurance of an element in _strOG deliminted (starting and ending) with _strDelim.
 */
	public static String getSubstitutionElement(String _strOG, String _strDelim, int _iElementNum) {
        int iCounter = 1;
        while(_strOG.indexOf(_strDelim) > -1) {
        	if(_strOG.indexOf(_strDelim) < 0 || _strOG.length() == 0) {
				return ""; // no more elements, so return "";
			}
			_strOG = _strOG.substring(_strOG.indexOf(_strDelim)+1,_strOG.length());
        	if(_strOG.indexOf(_strDelim) < 0 || _strOG.length() == 0) {
				return "";
			}
			String s = _strOG.substring(0,_strOG.indexOf(_strDelim));
			_strOG = _strOG.substring(_strOG.indexOf(_strDelim)+1,_strOG.length());
			if(iCounter == _iElementNum) {
				return s;
			}
			iCounter++;
		}
		return "";
	}

/**
 *
 */
    public static String replaceSubstitutionElement(String _strOG, String _strReplaceString, String _strVal) {
		return StringUtil.replaceFirst(_strOG,_strReplaceString, _strVal);
	}

/**
 * Nifty replace algorithm for 1.3.1 coz we don't got one...
 * @param _s1 the String to interrogate
 * @param _s2 the String to replace
 * @param _s3 the String to replace with
 */
    public static String replaceAll(String _s1, String _s2, String _s3) {
        String strFinal = _s1;
        boolean bContinue = true;
        while(strFinal.indexOf(_s2) > -1 && bContinue) {
			String strRem = _s2;
			int i1 = strFinal.indexOf(strRem);
			int i2 = strRem.length() + i1;
			String strFront = strFinal.substring(0,i1);
			String strBack = strFinal.substring(i2,strFinal.length());
			strFinal = strFront+_s3+strBack;
			if(strBack.indexOf(_s2) < 0) {
				bContinue = false;
			}
		}
	    return strFinal;
	}

/**
 * @param _s1 the String to interrogate
 * @param _s2 the String to replace
 * @param _s3 the String to replace with
 */
    public static String replaceFirst(String _s1, String _s2, String _s3) {
        String strFinal = _s1;
        if(strFinal.indexOf(_s2) > -1) {
			String strRem = _s2;
			int i1 = strFinal.indexOf(strRem);
			int i2 = strRem.length() + i1;
			String strFront = strFinal.substring(0,i1);
			String strBack = strFinal.substring(i2,strFinal.length());
			strFinal = strFront+_s3+strBack;
		}
	    return strFinal;
	}

}
