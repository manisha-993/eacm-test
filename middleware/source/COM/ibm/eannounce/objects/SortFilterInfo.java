//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SortFilterInfo.java,v $
// Revision 1.14  2009/05/11 15:52:32  wendy
// Support dereference for memory release
//
// Revision 1.13  2002/12/26 21:32:15  gregg
// ok, remove equalsWithWildcardsExact method
//
// Revision 1.12  2002/12/26 20:55:06  gregg
// equalsWithWildcardsExact
//
// Revision 1.11  2002/12/12 18:54:39  gregg
// 2 more checks in performEqualsBlock method:
//   case 1) s2 == substring of s1: avoid outOfBoundsExc here and return false
//   case 2) s1 = "A", s2 = "A1A".  this should now return false.
//
// Revision 1.10  2002/11/19 01:00:34  gregg
// more equals w/ wildcards
//
// Revision 1.9  2002/11/18 22:53:41  gregg
// more revising of equalsWithWildcards
//
// Revision 1.8  2002/11/18 21:20:24  gregg
// ignore case logic on performFilter
//
// Revision 1.7  2002/11/18 18:28:09  gregg
// allow use of wildcards on performFilter()
//
// Revision 1.6  2002/09/06 17:38:46  gregg
// SortFilterInfo now uses String sort/filter key constants (were ints)
//
// Revision 1.5  2002/08/09 16:37:43  roger
// Add a serial version number literal constant field value as needed for serialization of the said object
//
// Revision 1.4  2002/08/08 20:04:29  gregg
// allow sortType/filterType to be Strings also
//
// Revision 1.3  2002/07/19 21:15:30  gregg
// import io
//
// Revision 1.2  2002/07/19 21:14:34  gregg
// implement Serializable
//
// Revision 1.1  2002/07/18 17:44:14  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.io.*;

/**
 * Small data structure to hold sort and filter info for sorting and/or filtering objects by field.
 * This class can be used in conjunction w/ a list of EANComparable objects.
 */
public final class SortFilterInfo implements Serializable {

    /**
     * @serial
     */
    static final long serialVersionUID = 1L;

    //private int m_iSortType = -1;
    private boolean m_bAsc  = true;
    //private int m_iFilterType = -1;
    private String m_strFilter = null;
	private String m_strSortType = null;
	private String m_strFilterType = null;
	
	public void dereference(){
		m_strFilter = null;
		m_strSortType = null;
		m_strFilterType = null;
	}

    public SortFilterInfo(String _strSortType, boolean _bAsc, String _strFilterType, String _strFilter) {
        setSortType(_strSortType);
        setAscending(_bAsc);
        setFilterType(_strFilterType);
        setFilter(_strFilter);
    }

    public String getSortType() {
        return m_strSortType;
    }

    public boolean isAscending() {
        return m_bAsc;
    }

    public String getFilterType() {
        return m_strFilterType;
    }

    public String getFilter() {
        return m_strFilter;
    }

    public void setSortType(String _s) {
        m_strSortType = _s;
        return;
    }

    public void setAscending(boolean _b) {
        m_bAsc = _b;
        return;
    }

    public void flipAscending() {
        m_bAsc = !m_bAsc;
        return;
    }

    public void setFilterType(String _s) {
        m_strFilterType = _s;
        return;
    }

    public void setFilter(String _str) {
        m_strFilter = _str;
        return;
    }

    public void resetFilter() {
        m_strFilter = null;
        return;
    }

    public String dump() {
        StringBuffer sb = new StringBuffer();
        sb.append("---Sort/Filter Info---\n");
        sb.append("m_strSortType:" + m_strSortType + "\n");
        sb.append("m_bAsc:" + m_bAsc + "\n");
        sb.append("m_strFilterType:" + m_strFilterType + "\n");
        sb.append("m_strFilter:" + m_strFilter + "\n");
        return sb.toString();
    }

/////////////////////////////////////////////////////////
	
/**
 * Compare two strings allowing wildcard in s1
 * @param s1 the String that may contain wildcards
 * @param s2 the String to compare against
 * @param _caWildcards a char array containg the possible wildcards
 * @param _bIgnoreCase use equalsIgnoreCase or not
 */
	public static boolean equalsWithWildcards(String _s1, String _s2, char[] _caWildcards, boolean _bIgnoreCase) {
		_s1 = formatString(_s1,_caWildcards);
        if(!performEqualsBlock(_s1,_s2,_caWildcards,_bIgnoreCase))
            return false;
	    //now flip the other way and go it again
	    //   -- this covers the case where s1 is merely a substring of s2 (i.e. terminates early)
        if(!performEqualsBlock(new StringBuffer(_s1).reverse().toString(),new StringBuffer(_s2).reverse().toString(),_caWildcards,_bIgnoreCase))
            return false;        
	    return true;
	}	
	
/**
 * The main block for equality checking
 */
    private static boolean performEqualsBlock(String _s1, String _s2, char[] _caWildcards, boolean _bIgnoreCase) {
	    for(int i = 0; i < _s1.length(); i++) {
	        //special processing for wildcard case
	        if(isCharWildcard(_s1.charAt(i),_caWildcards)) {
	            //if this is the last char and is wildcard and we made it this far, then itsa match!!
	            if(i == (_s1.length()-1))
	                return true;
	            //else -> take the rest of _s1, find the next matching character in _s2, and recurse the rest...
	            String strRestOfS1 = _s1.substring(i+1,_s1.length());
	            for(int j = i; j < _s2.length(); j++) {
	                try {
	                	if(charEquals(strRestOfS1.charAt(0),_s2.charAt(j),_bIgnoreCase) || isCharWildcard(strRestOfS1.charAt(0),_caWildcards)) {                   
	                    	if(equalsWithWildcards(strRestOfS1,_s2.substring(j,_s2.length()),_caWildcards,_bIgnoreCase))
	                        	return true;
	                	}
	                }catch(StringIndexOutOfBoundsException sioobExc){}
	            } 
	            return false;
	      //s2 is not past the end AND if this char equals
	      //also catch the case where s1 is at last char, but there is more to match in s2 (ie non-match)
	        } else if((_s2.length() > i) && charEquals(_s1.charAt(i),_s2.charAt(i),_bIgnoreCase) && !((_s1.length() == i+1) && (_s2.length() > i+1))) {
				continue;
	        } else
	            return false;
	    }
	    return true;
	}

/**
 * remove adjacent wildcards...
 */
    private static String formatString(String _s, char[] _caWildcards) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < _s.length()-1; i++) {
            if(!(isCharWildcard(_s.charAt(i),_caWildcards) && isCharWildcard(_s.charAt(i+1),_caWildcards)))
                sb.append(_s.charAt(i));
            else
                continue;
        }
        sb.append(_s.charAt(_s.length()-1));
        return sb.toString();
    }

    private static boolean charEquals(char _c1, char _c2, boolean _bIgnoreCase) {
        if(_bIgnoreCase)
            return String.valueOf(_c1).equalsIgnoreCase(String.valueOf(_c2));
        else
            return _c1 == _c2;
    }

	private static boolean isCharWildcard(char _c, char[] _caWildcards) {
	    for(int i = 0; i < _caWildcards.length; i++) {
	        if(_c == _caWildcards[i])
	            return true;
	    }
	    return false;
	}
	
}
