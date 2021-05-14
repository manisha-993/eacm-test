//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.awt.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;


import COM.ibm.eannounce.objects.*;

/**
 * utilities...
 *
 * @author Wendy Stimpson 
 */
// $Log: Routines.java,v $
// Revision 1.2  2013/07/18 19:51:48  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public class Routines implements EACMGlobals {
    /**
     * STR
     */
    private static String STR = null;
    /**
     * strs
     */
    private static String[] strs = null;

    /**
     * routines
     *  
     */
    private Routines() {
    }

    public static boolean isFound(String str, String find, boolean caseSensitive) {
    	boolean found = false;
    	if (str==null){
    		str="";
    	}
        if (caseSensitive) {
        	found = str.indexOf(find) != -1;
        } else {
            found = str.toUpperCase().indexOf(find.toUpperCase()) != -1;
        }
        return found;
    }

    /**
     * Labels will render as multiline if wrapped in html tags and br replaces newlines
     * @param str
     * @return
     */
    public static String convertToMultilineHTML(String str)
    {
    	String txt = str;
    	if(txt!=null){
    		//this is a simple way to get a multiline label
    		int starttagid = txt.trim().indexOf("<");
    		int endtagid = txt.trim().lastIndexOf(">");
    		if(starttagid==0 && endtagid==txt.length()-1){ // this is xml content
    			txt="<html>"+txt+"</html>";
    		}else if (txt.indexOf(NEWLINE)!=-1){
    			StringBuffer sb = new StringBuffer("<html>");
    			StringTokenizer st = new StringTokenizer(txt, NEWLINE);
    			while (st.hasMoreTokens()) {
    				String s = st.nextToken();
    				sb.append(s);
    				if(st.hasMoreTokens()){
    					sb.append("<br>");
    				}
    			}
    			sb.append("</html>");
    			txt=sb.toString();
    		}
    	}
    	return txt;
    }
    /**
     * splitStringByLines
     * @param str
     * @return
     *  
     */
    public static String[] splitStringByLines(String str) {
        int lines = -1;
        int line = 0;
        StringTokenizer st = null;
        if (str == null || str.equals(STR)) {
            return strs;
        }

        STR = str;

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
    /**
     * split the string into an array using the specified delimiter
     * @param str
     * @param delim
     * @return
     */
    public static String[] splitString(String str,String delim) {
    	String[] ss = null;

        if (str != null) {
        	StringTokenizer st = new StringTokenizer(str, delim);
        	Vector<String> v  =new Vector<String>();
        	while (st.hasMoreTokens()) {
        		String s = st.nextToken();
        		if (have(s)) {
        			v.add(s);
        		}
        	}
        	ss = new String[v.size()];
        	v.copyInto(ss);
        }

        return ss;
    }

    /********************************************************************************
    * Convert the string into an array.  The string is a list of values delimited by
    * Delimiter
    *
    * @param data String
    * @returns String[] one delimited string per element
    */
    public static String[] convertToArray(String data, String delimiter)
    {
        Vector<String> vct = new Vector<String>();
        String array[] = null;
        // parse the string into substrings
        if (data!=null)  {
        	data = addLineWraps(data, 80); // break at 80 chars
            StringTokenizer st = new StringTokenizer(data,delimiter);
            while(st.hasMoreTokens()) {
            	String token =st.nextToken(); 
                vct.addElement(token);
            }
        }
        array= new String[vct.size()];
        vct.copyInto(array);
        return array;
    }
    
    public static String convertToHTML(String txt)
    {
		String retVal=txt;

		if (txt.indexOf("&")!=-1 ||txt.indexOf("<")!=-1 ||txt.indexOf(">")!=-1 ||txt.indexOf("\"")!=-1
			||txt.indexOf("\n")!=-1)
		{

			StringBuffer htmlSB = new StringBuffer();
			StringCharacterIterator sci = null;
			char ch = ' ';
			if (txt != null) {
				sci = new StringCharacterIterator(txt);
				ch = sci.first();
				while(ch != CharacterIterator.DONE)
				{
					switch(ch)
					{
					case '<': // could be saved as &lt; also. this will be &#60;
					case '>': // could be saved as &gt; also. this will be &#62;
					case '"': // could be saved as &quot; also. this will be &#34;
					case '&': // ignore entity references such as &lt; if user typed it, user will see it
							  // could be saved as &amp; also. this will be &#38;
						htmlSB.append("&#"+((int)ch)+";");
						break;
					case '\n':  // maintain new lines
						htmlSB.append("<br />");
						break;
					default:
						//if (Character.isSpaceChar(ch))// check for unicode space character
						//{
						//    htmlSB.append("&#32;"); // this fails because extra whitespace, even &#32;, is discarded
							// but left to be consistent with WestCoast code
		//                      htmlSB.append("&nbsp;"); // this will correctly maintain spaces
						//}
						//else {
						htmlSB.append(ch);
							//}
						break;
					}
					ch = sci.next();
				}
				retVal = htmlSB.toString();
			}
		}
        return retVal;
    }
    /**
     * getCharacterCount
     * @param _s
     * @param _delim
     * @return
     *  
     */
    public static int getCharacterCount(String _s, String _delim) {
        StringTokenizer st = new StringTokenizer(_s, _delim);
        return st.countTokens();
    }

    /**
     * find
     * @param _strSearch
     * @param _strFind
     * @param _bCase
     * @return
     *  
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
     *  
     */
    public static String replaceChar(String _str, char _oldChar, char _newChar) {
        return _str.replace(_oldChar, _newChar);
    }


    /**
     * isDelimited
     * @param _s
     * @param _delim
     * @return
     *  
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
     *  
     */
    public static String[] getStringArray(String _s, String _delim, boolean _allowBlanks) {
        StringTokenizer st = new StringTokenizer(_s, _delim);
        Vector<String> v = new Vector<String>();
        String[] out = null;
        while (st.hasMoreTokens()) {
            String s = st.nextToken().trim();
            if (Routines.have(s)) {
                v.add(s);
            } else if (_allowBlanks) {
                v.add("");
            }
        }
        if (!v.isEmpty()) {
        	out = new String[v.size()];
        	v.copyInto(out);
        }
 
        return out;
    }

    /**
     * getStringArray
     * @param _s
     * @param _delim
     * @return
     *  
     */
    public static String[] getStringArray(String _s, String _delim) {
        return getStringArray(_s, _delim, false);
    }

    /**
     * getIntArray
     * @param _s
     * @param _delim
     * @return
     *  
     */
    public static int[] getIntArray(String _s, String _delim) {
        StringTokenizer st = new StringTokenizer(_s, _delim);
        Vector<String> v = new Vector<String>();
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
     * get Int from properties
     *
     * @param _code
     * @return
     */
    public static int getIntProperty(String _code) {
        String sNumb = EACMProperties.getProperty(_code);
        try {
            return Integer.parseInt(sNumb);
        } catch (NumberFormatException _nfe) {
            _nfe.printStackTrace();
        }
        return 0;
    }
    
    /**
     * getInt
     * @param _s
     * @return
     *  
     */
    public static int getInt(String _s) {
        return getInt(_s, 0);
    }
    /**
     * getInt
     * @param _s
     * @param _def
     * @return
     *  
     */
    public static int getInt(String _s, int _def) {
        if (!have(_s)) {
            return _def;
        }
        try {
            return Integer.valueOf(_s).intValue();
        } catch (NumberFormatException nfe) {
        	nfe.printStackTrace();
        }
        return _def;
    }

    /**
     * getBoolean
     * @param _s
     * @return
     *  
     */
    public static boolean getBoolean(String _s) {
        Boolean b = new Boolean(_s);
        return b.booleanValue();
    }

    /**
     * splitString
     * @param str
     * @return
     *  
     */
    public static Object[] splitString(String str) {
        Object[] out = new Object[2];
        String[] s = splitStringByLines(str);
      
        String strMax = "";
        for (int i = 0; i < s.length; ++i) {
            strMax = maxString(strMax, s[i]);
        }
        out[0] = strMax;
        out[1] = s;
        return out;
    }

    /**
     * maxString
     * @param s
     * @param ss
     * @return
     *  
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
    public static String addLineWraps(String str, int length) { 
    	if(str==null || str.length()<=length){
    		return str;
    	}

    	StringBuilder sb = new StringBuilder();
    	StringTokenizer st = new StringTokenizer(str,NEWLINE);
    	while(st.hasMoreTokens()){
    		int linelength = 0;
    		String part = st.nextToken();
    		StringTokenizer partst = new StringTokenizer(part," \t\n\r\f",true);
        	while(partst.hasMoreTokens()){
        		String word = partst.nextToken();
        		linelength += word.length();
        		if(linelength >length){
        			linelength = word.length();
        			sb.append(NEWLINE);
        		}
        		sb.append(word);
        	}
        	sb.append(NEWLINE);
    	}
    	return sb.toString();
    }
    
 
    /**
     * have
     * @param s
     * @return
     *  
     */
    public static boolean have(String s) {
    	return (s != null && s.length() > 0);
    }

    /**
     * have
     * @param o
     * @return
     *  
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
     * have
     * @param chars
     * @return
     *  
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
     * truncate
     * @param s
     * @param len
     * @return
     *  
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
     *  
     */
    public static boolean endsWithIgnoreCase(String _input, String _end) {
        return _input.toLowerCase().endsWith(_end.toLowerCase());
    }

    /**
     * truncateFilename
     * @param _len
     * @param _file
     * @return
     *  
     */
    public static String truncateFilename(int _len, String _file) { 
        if (_file.length() > _len) { 
            int i = _file.lastIndexOf("."); 
            String ext = _file.substring(i); 
            int extLen = ext.length(); 
            if (extLen > 4) { 
                String ext2 = ext.substring(0, 4); 
                String pre = _file.substring(0, (_len - 4)); 
                return pre + ext2; 
            } else { 
                String pre = _file.substring(0, (_len - extLen)); 
                return pre + ext; 
            } 
        } else { 
            return _file; 
        } 
    } 

    /**
     * getInformation
     * @param _att
     * @return
     *  
     */
    public static String getInformation(EANAttribute _att) {
        if (_att == null) {
            return Utils.getResource("nia");// null;
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
            + "comboUniqueOptional: "
            + meta.isComboUniqueOptional()
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
     * not
     * @param _s
     * @return
     *  
     */
    public static boolean not(String _s) {
        return _s.equalsIgnoreCase("false");
    }

    /**
     * toString
     * @param _b
     * @return
     *  
     */
    public static String toString(boolean _b) {
        return new Boolean(_b).toString();
    }


    /**
     * toString
     * @param _s
     * @return
     *  
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
     * toNumber
     * @param _s
     * @return
     *  
     */
    private static Number toNumber(String _s) {
        try {
            return Double.valueOf(_s.trim());
        } catch (NumberFormatException _nfe) {
            _nfe.printStackTrace();
        }
        return new Integer(-1);
    }
    /**
     * toInt
     * @param _s
     * @return
     *  
     */
    public static int toInt(String _s) {
        return toNumber(_s).intValue();
    }


    /**
     * isInt
     * @param _s
     * @return
     *  
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
     * getDisplayString
     * @param _s
     * @param _withReturn
     * @return
     *  
     */
    public static String getDisplayString(String _s, boolean _withReturn) {  

        if (_s == null) {  
            return "";
        }  
        StringBuffer sb = new StringBuffer();  
        char[] c = _s.toCharArray();  
        for (int i = 0; i < c.length; ++i) {  
            if (c[i] == '\t') {  
                sb.append("    ");  
            } else if (c[i] == '\n' && _withReturn) {  
                sb.append(c[i]);  
            } else if (Character.isSpaceChar(c[i])) {  
                sb.append(c[i]);  
            } else if (!Character.isWhitespace(c[i])) {  
                sb.append(c[i]);  
            }  
        }  
        return sb.toString();  
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
     *  
     */
    public static String replace(String _current, String _find, String _replace) {
        StringBuffer out = new StringBuffer(_current);
        int indx = -1;
        int iLenFind = -1;
        int iLenReplace = -1;

        _current = _current.toLowerCase();
        _find = _find.toLowerCase();
        if (_replace == null) {
            _replace = "null";
        }
        indx = _current.indexOf(_find);
        iLenFind = _find.length();
        iLenReplace = _replace.length();

        while (indx != -1) {
            out.replace(indx, (indx + iLenFind), _replace);
            _current = out.toString().toLowerCase();
            indx = _current.indexOf(_find, indx + iLenReplace);
        }
        return out.toString();
    }

    /**
     * sleep
     * @param _thread
     * @param _millis
     *  
     */
    public static void sleep(int millis) {
    	if (millis > 0) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException _ie) {
                _ie.printStackTrace();
            }
        }
    }

    /**
     * pause
     * @param _millis
     *  
     */
    public static void pause(int _millis) {
    	try {
            Thread.sleep(_millis);
        } catch (InterruptedException _ie) {
            _ie.printStackTrace();
        }
    }

    /*
     acl_20031006
     */
    /**
     * getRandomString
     * @return
     *  
     */
    public static String getRandomString() {
    	 Random rand = new Random(new Date().getTime());
         int i =  rand.nextInt();
        return Integer.toString(i);
    }

    /**
     * whoHasFocus
     * @return
     *  
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
	 *  
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
	 * trim
	 *
	 *  
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

}
