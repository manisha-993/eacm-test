//
//$Log: XProperties.java,v $
//Revision 1.3  2012/11/29 19:25:29  wendy
//Changed variable 'enum' to enm because 'enum' is now a reserved keyword
//
//Revision 1.2  2011/05/05 11:21:33  wendy
//src from IBMCHINA
//
//Revision 1.1.1.1  2007/06/05 02:09:38  jingb
//no message
//
//Revision 1.1.1.1  2006/03/30 17:36:32  gregg
//Moving catalog module from middleware to
//its own module.
//
//Revision 1.11  2003/05/20 21:05:51  gregg
//trim on getProperty
//
//Revision 1.10  2003/05/12 21:11:04  gregg
//formatting....
//
//Revision 1.9  2003/05/12 21:04:42  gregg
//some javadoc'ing
//
//Revision 1.8  2003/05/12 20:56:48  gregg
//mo' comments fixing
//
//Revision 1.7  2003/05/12 20:49:35  gregg
//paste some test properties in comments
//
//Revision 1.6  2003/05/12 20:41:10  gregg
//some comments
//
//Revision 1.5  2003/05/12 20:35:44  gregg
//re-package since copy
//
//Revision 1.4  2003/05/12 20:30:04  gregg
//ignore esape'd escape characters
//
//

package COM.ibm.eannounce.catalog;

import java.util.*;
import java.io.*;

/**
 * An enhanced <CODE>Properties</CODE> class.
 * <PRE>
 *
 * Contains the following extended features:
 *  - Allows for constant substitution in properties files enclosed in '{}'.
 *  - Enables multiplication of integers using '*' operator.
 *  - Allows the storing of more than one value per a single key.
 *
 *
 *  NOTES:
 *    - Constant substitution can be performed on lists, HOWEVER: these must be themselves seperated by commas
 *           e.g.
 *             LIST1 = 3,4
 *             LIST2 = 1,2,{LIST1},5,6
 *           would result in
 *             LIST2 = 1,2,3,4,5,6
 *
 *    - Substitution may be used in conjunction with Multiplication and lists, so "1,{TWO}*{ONE},3" would work.
 *    - Multiplication currently only works for 2 vals ([multiplier] * [multiplicand]).
 *    - Multiple values for a single key can be defined in the properties file in two ways:
 *        a) a comma separated list
 *           e.g.
 *                 values = 1,2,3
 *           OR
 *        b) repeat entries
 *           e.g.
 *                 values = 1
 *                 values = 2
 *                 values = 3
 * =================================================================================================================
 * An Example .properties file:
 *
 * ONE = 1
 * TWO = 2
 * THREE = 3
 * PRODUCT_OF_3_AND_2 = {THREE}*{TWO}
 * LIST1 = {ONE}
 * LIST1 = {TWO}
 * LIST1 = {THREE}
 * LIST1 = 4,5,{PRODUCT_OF_3_AND_2},N
 * LIST2 = {LIST1},XXX,{ONE}*2,{ONE}{TWO}{THREE},321
 * ONE_TWO_THREE = 1,{ONE}*{TWO},3
 * TWENTY_THREE = {TWO}{THREE}
 * SOME_SPECIALS_ESCAPED = ^{^}^,^{^}
 * MULTIPLY_ESCAPED = 5^*3
 *  ---------------------------
 *  would result in:
 *
 * ONE = "1"
 * TWO = "2"
 * THREE = "3"
 * PRODUCT_OF_3_AND_2 = "6"
 * LIST1 = "1","2","3","4","5","6","N"
 * LIST2 = "1","2","3","4","5","6","N","XXX","2","123","321"
 * SOME_SPECIALS_ESCAPED = "{},{}"
 * ONE_TWO_THREE = "1","2","3"
 * TWENTY_THREE = "23"
 * MULTIPLY_ESCAPED = "5*3"
 * </PRE>
 */
public class XProperties extends Properties {

    /**
     * Denotes the beginning of a value to be substituted for
     */
    protected static final char BEGIN_SUBS_DELIM_CHAR = '{';
    /**
     * Denotes the ending of a value to be substituted for
     */
    protected static final char END_SUBS_DELIM_CHAR = '}';
    /**
     * Denotes a multiplication of two integers
     */
    protected static final char MULTIPLY_CHAR = '*';
    /**
     * Denotes the separating of values for a single key
     */
    protected static final char COMMA_CHAR = ',';
    /**
     * Escape any of the 'special' characters. Use this in front of any of the above chars which should be used
     * literally. You can also escape this escape char w/ two in a row.
     */
    protected static final char ESCAPE_CHAR = '^';

    // trim any leading/trailing whitespace from ~values~ ?
    private boolean m_bTrimValues = true;


/**
 * Creates an empty property list with no default values.
 */
    public XProperties() {
	    super();
    }

/**
 * Creates an empty property list with the specified defaults.
 *
 * @param   defaults   the defaults.
 */
    public XProperties(Properties _defaults) {
 		super(_defaults);
    }

/**
 * <PRE>
 * Override Properties class's load() method.
 * First call super.load(), then:
 * 1) seprate comma-delimited values
 * 2) perform any constant substitutions
 * 3) seprate comma-delimited values ~again~
 * 4) perform any multiplications
 * </PRE>
 */
    public synchronized void load(InputStream _inStream) throws IOException {

        super.load(_inStream);
        separateCommaValues();
        try {
            performConstantSubstitutions();
        } catch(Exception exc) {
            throw new IOException(exc.getMessage());
        }
        // call again, 'coz substitution could have put another list in there....
        separateCommaValues();
        try {
            performMultiplications();
        } catch(Exception exc) {
            throw new IOException(exc.getMessage());
        }
        // now remove escape chars
        removeEscapeChars();
    }

/**
 * <PRE>
 * Override Hashtable's put() method to accept multiple values for one key.
 *
 * This is necessary as opposed to merely overriding setProperty, b/c
 * within Properties super class, put() is called directly from:
 * 1) load() method when properties are initially loaded.
 * 2) setProperty() method (thus we can avoid directly overriding this method).
 * </PRE>
 */
    public synchronized Object put(Object _objKey, Object _objValue) {
        String[] strArr = (String[])super.get(_objKey);
        if(strArr == null) {
            strArr = new String[1];
        } else {
            String[] strArrTemp = new String[strArr.length+1];
            for(int i = 0; i < strArr.length; i++) {
                strArrTemp[i] = strArr[i];
            }
            strArr = strArrTemp;
        }
        strArr[strArr.length-1] = (String)_objValue;
        return super.put(_objKey,strArr);
    }



/**
 * This will return a String value, or 1st value if there are multiple values.
 */
    public String getProperty(String _strKey) {
        Object obj = super.get(_strKey);
        //should be!!
        if(obj instanceof String[]) {
            String[] strArr = (String[])obj;
            if(strArr != null && strArr.length > 0) {
                return (m_bTrimValues?(String)strArr[0].trim():(String)strArr[0]);

            }
        }
        return null;
    }

/**
 * This will return an array of Strings
 */
    public String[] getProperties(String _strKey) {
        return (String[])get(_strKey);
    }

/**
 * Are there multiple values for this key?
 */
    public boolean hasMultipleValues(String _strKey) {
        String[] strArr = getProperties(_strKey);
        if(strArr == null) {
            return false;
        }
        return (strArr.length > 1);
    }

/**
 *
 */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Enumeration enm = this.propertyNames();
        while(enm.hasMoreElements()) {
            String strKey = (String)enm.nextElement();
            sb.append(strKey);
            sb.append(" = ");
            if(!hasMultipleValues(strKey)) {
                sb.append("\"");
                sb.append(getProperty(strKey));
                sb.append("\"");
            } else {
                String[] strArr = getProperties(strKey);
                for(int i = 0; i < strArr.length; i++) {
                    if(i > 0) {
                        sb.append(",");
                    }
                    sb.append("\"");
                    sb.append(strArr[i]);
                    sb.append("\"");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

/**
 * Trim any leading/trailing whitespace from values?  Default == true.
 */
    public void setTrimValues(boolean _b) {
        m_bTrimValues = _b;
    }

//////////////////////
// private routines //
//////////////////////

/**
 * Check if ANY of the values contain the indicated character
 * Ignore escaped special chars
 */
    private boolean valuesContainChar(char _c) {
        // all elements should be String[]'s
        Enumeration elements = elements();
        while(elements.hasMoreElements()) {
            String[] strArr = (String[])elements.nextElement();
            for(int istrArr = 0; istrArr < strArr.length; istrArr++) {
                String s = (String)strArr[istrArr];
                for(int iStrLen = 0; iStrLen < s.length(); iStrLen++) {
                    if(s.charAt(iStrLen) == _c && !isSpecialCharEscaped(s,iStrLen)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

/**
 * This will go through and attempt to REPLACE any value enclosed in the specified delimiters <BR>
 * (BEGIN_SUBS_DELIM_CHAR -- END_SUBS_DELIM_CHAR)
 */
    private void performConstantSubstitutions() throws Exception {
        //we've got to keep doing this until we're done 'coz we could have nested constants i.e. a->b, b->c, etc...
        while(valuesContainChar(BEGIN_SUBS_DELIM_CHAR)) {
        	Enumeration enm = this.propertyNames();
       		while(enm.hasMoreElements()) {
        	    String strKey = (String)enm.nextElement();
        	    String[] strArrVals = getProperties(strKey);
        	    if(strArrVals != null) {
        	        for(int iNumVals = 0; iNumVals < strArrVals.length; iNumVals++) {
        	           	String strVal = strArrVals[iNumVals];
        	           	StringBuffer sbNewVal = new StringBuffer();
        	        	StringBuffer sbSubVal = new StringBuffer();
        	        	boolean bInSubstitution = false;
        	        	for(int iValLen = 0; iValLen < strVal.length(); iValLen++) {
        	        	    char c = strVal.charAt(iValLen);
        	        	    if(checkSpecialChar(strVal,iValLen,BEGIN_SUBS_DELIM_CHAR)) {
        	    		        if(bInSubstitution) { // i.e. two begins in a row
        	    		            throw new Exception("Unbalanced \"" + BEGIN_SUBS_DELIM_CHAR + "\" for key: \"" + strKey + "\", value:" + strVal + "");
        	    		        }
        	    		        sbSubVal = new StringBuffer();
        	    		        bInSubstitution = true;
        	    		    }
        	    		    else if(checkSpecialChar(strVal,iValLen,END_SUBS_DELIM_CHAR)) {
        	    		        if(!bInSubstitution) {
        	    		            throw new Exception("Unbalanced \"" + END_SUBS_DELIM_CHAR + "\" for key: \"" + strKey + "\", value:" + strVal + "");
        	    		        }
        	    		        // CAREFUL!!!
        	    		        // This is where we may be substituting another list...
        	    		        // - Simple solution is turn this into a comma-separated list and make another
        	    		        //   subsequent call to seperateCommaValues()
        	    		        String[] strArrSubs = getProperties(sbSubVal.toString());
        	    		        if(strArrSubs == null || strArrSubs.length == 0) {
        	    		            throw new Exception("property value(s) for: \"" + BEGIN_SUBS_DELIM_CHAR + sbSubVal.toString() + END_SUBS_DELIM_CHAR + "\" not found in properties file.");
        	    		        }
        	    		        for(int iSubs = 0; iSubs < strArrSubs.length; iSubs++) {
                                    if(iSubs > 0) {
                                        sbNewVal.append(",");
                                    }
        	    		            sbNewVal.append(strArrSubs[iSubs]);
        	    		        }
        	    		        ///
        	    		        bInSubstitution = false;
        	    		    } else if(!bInSubstitution) { //maintain the previous chars in the value in case substitution must be performed
        	    		        sbNewVal.append(c);
        	    		    }
        	    		    else {
        	    		        sbSubVal.append(c);
        	    		    }
        	     		    // if there is no closing substitution delimiter
        	     		    if(iValLen == (strVal.length()-1) && bInSubstitution) {
        	     		        throw new Exception("Unbalanced \"" + BEGIN_SUBS_DELIM_CHAR + "\" for key: \"" + strKey + "\", value:" + strVal + "");
        	    		    }
        	    		}
						strArrVals[iNumVals] = sbNewVal.toString();
        	        }
        	        // MUST call ~super's~ put!!
        	        superPut(strKey,strArrVals);
        	    }
        	}
        }
        return;
    }

/**
 * Multiply two values seperated by the MULTIPLY_CHAR
 */
    private void performMultiplications() throws Exception {
        Enumeration enm = this.propertyNames();
        while(enm.hasMoreElements()) {
            String strKey = (String)enm.nextElement();
            String[] strArrVals = getProperties(strKey);
            if(strArrVals != null) {
                for(int iNumVals = 0; iNumVals < strArrVals.length; iNumVals++) {
                   	String strVal = strArrVals[iNumVals];
                   	boolean bPerformMultiplication = false;
                    //search for multiplication delim char
                    for(int iValLen = 0; iValLen < strVal.length(); iValLen++) {
                        char c = strVal.charAt(iValLen);
                        if(checkSpecialChar(strVal,iValLen,MULTIPLY_CHAR)) {
                            bPerformMultiplication = true;
                            iValLen = strVal.length(); //break
                            continue;
                        }
                    }
                    if(bPerformMultiplication) {
                    	StringBuffer sbMultiplier = new StringBuffer();
                    	StringBuffer sbMultiplicand = new StringBuffer();
                    	boolean bPastOperator = false;
                    	for(int iValLen = 0; iValLen < strVal.length(); iValLen++) {
              			    char c = strVal.charAt(iValLen);
              			    if(checkSpecialChar(strVal,iValLen,MULTIPLY_CHAR)) {
                                bPastOperator = true;
              			    }
              			    else if(!bPastOperator) {
              			        sbMultiplier.append(c);
              			    }
              			    else if(bPastOperator) {
              			        sbMultiplicand.append(c);
              			    }
                    	}
              			if(sbMultiplier.length() == 0) {
              			    throw new Exception("multiplier missing in key: \"" + strKey + "\", val:\"" + strVal + "\"");
              			}
               			else if(sbMultiplicand.length() == 0) {
              			    throw new Exception("multiplicand missing in key: \"" + strKey + "\", val:\"" + strVal + "\"");
                        }
                        long longMultiplier = 0;
                        long longMultiplicand = 0;
                        try {
                            longMultiplier = Long.parseLong(sbMultiplier.toString());
                        } catch(NumberFormatException nfe) {
                            throw new Exception("Invalid multiplier in key: \"" + strKey + "\", val:\"" + strVal + "\"");
                        }
                        try {
                            longMultiplicand = Long.parseLong(sbMultiplicand.toString());
                        } catch(NumberFormatException nfe) {
                            throw new Exception("Invalid multiplicand in key: \"" + strKey + "\", val:\"" + strVal + "\"");
                        }
                        long longProduct = longMultiplier*longMultiplicand;
                        strArrVals[iNumVals] = String.valueOf(longProduct);
                    }
                }
                // MUST call ~super's~ put!!
                superPut(strKey,strArrVals);
            }
        }
        return;
    }

/**
 * Pull out individual values from a comma-seperated list
 */
    private void separateCommaValues() {
        Enumeration enm = this.propertyNames();
        while(enm.hasMoreElements()) {
            String strKey = (String)enm.nextElement();
            String[] strArrVals = getProperties(strKey);
            //store the "new" vals - these will need to be ~replaced~ for this key
            Vector vctNewVals = new Vector();
            if(strArrVals != null) {
                for(int iNumVals = 0; iNumVals < strArrVals.length; iNumVals++) {
                   	String strVal = strArrVals[iNumVals];
                    int iLastSepIndex = 0;
                    //search for multiplication delim char
                    for(int iValLen = 0; iValLen < strVal.length(); iValLen++) {
                        if(checkSpecialChar(strVal,iValLen,COMMA_CHAR)) {
                            vctNewVals.addElement(strVal.substring(iLastSepIndex,iValLen++));
                            //iValLen++;
                            iLastSepIndex = iValLen;

                        }
                    }
                    //remember - this will add whole String if no comma found
                    vctNewVals.addElement(strVal.substring(iLastSepIndex,strVal.length()));

                }
                // NOW, we can replace all values for key
                strArrVals = new String[vctNewVals.size()];
                for(int iNewVals = 0; iNewVals < strArrVals.length; iNewVals++) {
                    strArrVals[iNewVals] = (String)vctNewVals.elementAt(iNewVals);
                }
                // MUST call ~super's~ put!!
                superPut(strKey,strArrVals);
            }
        }
        return;
    }

/**
 * Check if the char at this index equals _c.
 * Reason for this method is to check for previous escape character
 * @return true if charAt(_iIndex) == _c AND previous char is NOT escape char.
 */
    private boolean checkSpecialChar(String _strVal, int _iIndex, char _c) {
         if(_strVal.charAt(_iIndex) == _c) {
             return !isSpecialCharEscaped(_strVal,_iIndex);
         }
         return false;
    }

/**
 * Determine whether or not the indicated special character should be taken literally
 */
    private boolean isSpecialCharEscaped(String _s, int _iIndex) {
        if(_iIndex == 0) {
            return false;
        }
        if(_s.charAt(_iIndex-1) == ESCAPE_CHAR) {
            if(_iIndex-1 == 0) {
                return true;
            }
            return !isSpecialCharEscaped(_s,_iIndex-1);
        }
        return false;
    }

/**
 * Remove all relevant escape characters
 */
    private void removeEscapeChars() {
        // all elements should be String[]'s
        Enumeration keys = keys();
        while(keys.hasMoreElements()) {
            String strKey = (String)keys.nextElement();
            String[] strArr = (String[])get(strKey);
            for(int istrArr = 0; istrArr < strArr.length; istrArr++) {
                String s = (String)strArr[istrArr];
                StringBuffer sb = new StringBuffer();
                for(int iVal = 0; iVal < s.length(); iVal++) {
                    // skip escape'd escape chars
                    if(s.charAt(iVal) != ESCAPE_CHAR) {
                        sb.append(s.charAt(iVal));
                    } else if(isSpecialCharEscaped(s,iVal)) {
                        sb.append(s.charAt(iVal));
                    }
                }
                strArr[istrArr] = sb.toString();
            }
            // MUST call ~super's~ put!!
            superPut(strKey,strArr);
        }
        return;
    }

/**
 * This is necessary, because when internally putting an array of values, we dont want to do any converting such as
 * is done in ~this~ put() method above...
 */
     private void superPut(String _strKey, String[] _strArrVals) {
         if(m_bTrimValues && _strArrVals != null) {
             for(int i = 0; i < _strArrVals.length; i++) {
                 _strArrVals[i] = _strArrVals[i].trim();
             }
         }
         super.put(_strKey,_strArrVals);
     }

}
