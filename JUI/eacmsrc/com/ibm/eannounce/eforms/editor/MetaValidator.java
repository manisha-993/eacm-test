/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MetaValidator.java,v $
 * Revision 1.5  2009/06/12 16:06:38  wendy
 * BH SR-14 date warnings
 *
 * Revision 1.4  2009/05/28 13:52:20  wendy
 * remove debug msgs
 *
 * Revision 1.3  2009/05/28 13:08:28  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:58  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.9  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.8  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/02/01 15:36:04  tony
 * PER DWB 20050201
 * Adjusted logic to skip check on Alpha Upper.
 * MW will now automatically convert to uppercase
 * when applicable.
 *
 * Revision 1.6  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.5  2005/01/27 19:15:17  tony
 * JTest Format
 *
 * Revision 1.4  2004/10/05 19:46:03  tony
 * fixed index out of bounds exception.
 *
 * Revision 1.3  2004/05/27 17:01:53  tony
 * removed invalid import statement.
 *
 * Revision 1.2  2004/03/15 17:35:03  tony
 * 53703
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.14  2003/12/09 20:33:05  tony
 * 53004
 *
 * Revision 1.13  2003/10/29 00:18:35  tony
 * removed System.out. statements.
 *
 * Revision 1.12  2003/10/15 19:22:53  tony
 * 52575
 *
 * Revision 1.11  2003/09/03 17:09:10  tony
 * 51984
 *
 * Revision 1.10  2003/08/28 22:55:03  tony
 * added validation print statements
 *
 * Revision 1.9  2003/08/21 19:43:10  tony
 * 51391
 *
 * Revision 1.8  2003/06/27 20:08:45  tony
 * updated logic to prevent validation when in search mode
 * for certain rules.
 *
 * Revision 1.7  2003/06/12 22:23:40  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.6  2003/06/02 21:12:24  tony
 * updated messaging logic to improve performance.
 *
 * Revision 1.5  2003/05/13 16:09:04  tony
 * 50621
 *
 * Revision 1.4  2003/04/21 17:30:19  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.3  2003/04/17 23:13:25  tony
 * played around with editing Colors
 *
 * Revision 1.2  2003/04/09 17:38:23  tony
 * improved source code formatting.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.23  2002/11/14 22:03:36  tony
 * imporved logic for greater than error.
 *
 * Revision 1.22  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.EObject;
import COM.ibm.eannounce.objects.*;
import java.awt.Color;
import java.awt.Component;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MetaValidator extends EObject implements EAccessConstants {
	//private transient static InterfaceDialog id = null;
 
	//private static final int DEFAULT = -1;
    /**
     * date_validator
     */
	private static final int DATE_VALIDATOR = 0;
    /**
     * future_date_validator
     */
	private static final int FUTURE_DATE_VALIDATOR = 1;
    /**
     * past_date_validator
     */
	public static final int PAST_DATE_VALIDATOR = 2;
    /**
     * time_validator
     */
	private static final int TIME_VALIDATOR = 3;

    private transient EANMetaAttribute meta = null;
    private transient EANAttribute att = null;
    
    private static final int PASS = 1;
    private static final int FAIL = -1;
    private static final int DEF = 0; //indeferent
    
    private static final int INTVAL = 0; //20907
    private static final int ALPHAVAL = 1; //2.4
    private static final int SPECVAL = 2; //2.4
    private static final int DATEVAL = 3; //2.4
    private static final int TIMEVAL = 4;
    private static final int PARTTIMEVAL = 5;
    private static final int NUMDECVAL = 6; //20907
    private static final int XX = 7; //20907
    private static final int REQVAL = 0; //2.4
    private static final int MINVAL = 1; //2.4
    private static final int MAXVAL = 2; //2.4
    private static final int GRTVAL = 3; //22455
    private static final int LL = 4; //2.4
    private static final int FULLDATE = 0; //2.4
    private static final int LENDATE = 1; //2.4
    private static final int PARTDATE = 2; //2.4
    private static final int PASTDATE = 3; //2.4
    private static final int FUTUREDATE = 4; //2.4
    private static final int DD = 5; //2.4
    
    private String decVal = null;
    private int[] dateRa = new int[DD]; //2.4
    private int[] lenRa = new int[LL]; //2.4
    private EditorInterface editor = null;
    private int type = -1;
    private boolean bSearch = false; //dwb_20030527
    private String[] valSRa = new String[XX]; //2.4
    private int[] valRa = new int[XX];
    
    private static final String DATECHECKS ="dateRa";
    private static final String LENGTHCHECKS ="lenRa";
    private static final String FORMATCHECKS ="valRa";

    /**
     * metaValidator
     * @author Anthony C. Liberto
     */
    public MetaValidator() {}

    /**
     * setType
     * @param _type
     * @author Anthony C. Liberto
     */
    public void setType(int _type) {
        type = _type;
    }

    /**
     * getType
     * @return
     * @author Anthony C. Liberto
     */
    public int getType() {
        return type;
    }

    /**
     * setEditor
     * @param _editor
     * @author Anthony C. Liberto
     */
    public void setEditor(EditorInterface _editor) {
        editor = _editor;
    }

    /**
     * getEditor
     * @return
     * @author Anthony C. Liberto
     * /
    private EditorInterface getEditor() {
        return editor;
    }*/

    /**
     * reportResults
     * @return
     * @author Anthony C. Liberto
     * /
    public String reportResults() { //2.4
        String s = reportResults("Required Validation    :  ", lenRa, REQVAL, RETURN); //2.4
        s = s + reportResults("Min Validation         :  ", lenRa, MINVAL, RETURN); //2.4
        s = s + reportResults("Max Validation         :  ", lenRa, MAXVAL, RETURN); //2.4
        s = s + reportResults("Full Date Validation   :  ", dateRa, FULLDATE, RETURN); //2.4
        s = s + reportResults("Date Length Validation :  ", dateRa, LENDATE, RETURN); //2.4
        s = s + reportResults("Partial Date Validation:  ", dateRa, PARTDATE, RETURN); //2.4
        s = s + reportResults("Past Date Validation   :  ", dateRa, PASTDATE, RETURN); //2.4
        s = s + reportResults("Future Date Validation :  ", dateRa, FUTUREDATE, RETURN); //2.4
        s = s + reportResults("Integer Validation      :  ", valRa, INTVAL, RETURN); //20907
        s = s + reportResults("Numeric/Decimal Validation      :  ", valRa, NUMDECVAL, RETURN); //20907
        s = s + reportResults("Alpha Validation       :  ", valRa, ALPHAVAL, RETURN); //2.4
        s = s + reportResults("Special Validation     :  ", valRa, SPECVAL, RETURN); //2.4
        s = s + reportResults("Date Validation        :  ", valRa, DATEVAL, ""); //2.4
        s = s + reportResults("Time Validation        :  ", valRa, TIMEVAL, ""); //2.4
        return s; //2.4
    } //2.4
    */

    /**
     * getResult
     * @param s
     * @param ra
     * @param i
     * @param rtn
     * @return
     * @author Anthony C. Liberto
     * /
    private String reportResults(String s, int[] ra, int i, String rtn) { //2.4
        if (ra[i] == PASS) { //2.4
            return s + "PASSED" + rtn; //2.4

        } else if (ra[i] == FAIL) { //2.4
            return s + "FAILED" + rtn; //2.4

        } else if (ra[i] == DEF) { //2.4
            return s + "DEFAULT" + rtn; //2.4

        } else { //2.4
            return s + "N/A (" + i + ")" + rtn;
        } //2.4
    } //2.4
    */

    /**
     * setMeta
     * @param _meta
     * @author Anthony C. Liberto
     */
    private void setMeta(EANMetaAttribute _meta) {
        meta = _meta;
    }

    /**
     * getMeta
     * @return
     * @author Anthony C. Liberto
     * /
    public EANMetaAttribute getMeta() {
        return meta;
    }*/

    /**
     * setAttribute
     * @param _att
     * @author Anthony C. Liberto
     */
    public void setAttribute(EANAttribute _att) {
        att = _att;
        if (_att != null) {
            setMeta(_att.getMetaAttribute());
        }
    }

    /**
     * getAttribute
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
        return att;
    }

    /**
     * validate
     * @param s
     * @param fullValue
     * @param pos
     * @return
     * @author Anthony C. Liberto
     */
    public boolean validate(String _s, String _fullValue, int _pos) {
    	boolean b = validChange(_s, _fullValue, _pos);
    	return b;
    }
    /**
     * generateErrorMessage
     * @param s
     * @param val
     * @author Anthony C. Liberto
     */
    private void generateErrorMessage(String _s, String _val) {
    	String str = null;																							//23484
    	//String msg = "";
    	StringBuffer msgSb = new StringBuffer();
    	if (_val != null && _val.length() == 1) {																	//23484
    		str = "Attribute " + getAttributeCode() + " value of '" + _val + "' failed the following rules..." + RETURN;	//23484
    	} else {																									//23484
    		str = "Attribute " + getAttributeCode() + " failed the following rules..." + RETURN;							//23484
    	}																											//23484
    	if (_s.equals(FORMATCHECKS) || _s.equals("ALL")) {
    		if (valRa[INTVAL] == FAIL) {
    			msgSb.append("Integer Validation" + valSRa[INTVAL]);
    		}
    		if (valRa[NUMDECVAL] == FAIL) {													//20907
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Numeric/Decimal Validation" + valSRa[NUMDECVAL]);
    		}																				//20907
    		if (valRa[ALPHAVAL] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Alpha Validation" + valSRa[ALPHAVAL]);
    		}
    		if (valRa[SPECVAL] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Special Validation");
    		}
    		if (valRa[DATEVAL] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Date Validation");
    		}
    		if (valRa[TIMEVAL] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Time Validation");
    		}
    	}
    	if (_s.equals(LENGTHCHECKS) || _s.equals("ALL")) {
    		if (lenRa[REQVAL] == FAIL) {
    			showError(null,"msg23030");	//msg23030 = field is required.
    			return;
    		}
    		if (lenRa[MINVAL] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Minimum Length of " + getMinLen() + " not met");		//22860
    		}
    		if (lenRa[MAXVAL] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Maximum Length of " + getMaxLen() + " Exceeded");		//22553
    		}
    		if (lenRa[GRTVAL] == FAIL) {						//22455
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Greater than Validation (value must be > " + getGreater() + ")");			//22455
    		}													//22455
    	}
    	if (_s.equals(DATECHECKS) || _s.equals("ALL")) {
    		if (dateRa[FULLDATE] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Date Format Validation (YYYY-MM-DD)");
    		}
    		if (dateRa[LENDATE] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Date Length Validation");
    		}
    		if (dateRa[PARTDATE] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Partial Date Validation (at least 1980-01-01)");
    		}
    		if (dateRa[PASTDATE] == FAIL) {
    			if (isWarningDate()){ //BH - SR14 warning date
    				return;  // already output msg
    			}
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Past Date Validation");
    		}
    		if (dateRa[FUTUREDATE] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Future Date Validation");
    		}
    		if (valRa[PARTTIMEVAL] == FAIL) {
    			if(msgSb.length()>0){
    				msgSb.append(", ");
    			}
    			msgSb.append("Time not between 00:00 and 23:59");
    		}
    	}
    	if (_s.equals("decVal") || _s.equals("ALL")) {
    		if(msgSb.length()>0){
				msgSb.append(", ");
			}
    		msgSb.append(decVal);
    	}

    	//if(!msg.equals("null")) {
    		setMessage(str + msgSb.toString() + ".");
    		showError((Component)editor);		//50621
    	//}
    }
    /**
     * errorMsg
     * @param _s
     * @author Anthony C. Liberto
     * /
    private void errorMsg(String _s) {
		//showError((Component)id,_s);
		showError(null,_s);
	}*/
    /**
     * fyiMsg
     * @param _s
     * @author Anthony C. Liberto
     * /
    private void fyiMsg(String _s) {
		showFYI((Component)id,_s);
	}*/

    /**
     * isAlpha
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isAlpha() {
        if (meta == null) {
            return false;
        }
        return meta.isAlpha();
    }

    /**
     * isDate
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isDate() {
        if (meta == null) {
            return (type == DATE_VALIDATOR || type == FUTURE_DATE_VALIDATOR || type == PAST_DATE_VALIDATOR);
        }
        return meta.isDate();
    }

    /**
     * isTime
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isTime() { //isTime
        if (meta == null) {
            return (type == TIME_VALIDATOR);
        }
        return meta.isTime(); //isTime
    }

    /**
     * isDecimal
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isDecimal() {
        if (meta == null) {
            return false;
        }
        return meta.isDecimal();
    }

    /**
     * isEditable
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isEditable() {
        if (meta == null) {
            return (type >= 0);
        }
        return meta.isEditable();
    }*/

    /**
     * isFutureDate
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFutureDate() {
        if (bSearch) { //53004
            return false; //53004
        } //53004
        if (meta == null) {
            return (type == FUTURE_DATE_VALIDATOR);
        }
        return meta.isFutureDate();
    }

    /**
     * isInteger
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isInteger() {
        if (meta == null) {
            return false;
        }
        return meta.isInteger();
    }

    /**
     * isLower
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isLower() {
        return false;
    }

    /**
     * isNumeric
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isNumeric() {
        if (meta == null) {
            return false;
        }
        return meta.isNumeric();
    }

    /**
     * isGreater
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isGreater() { //22455
        if (meta == null) {
            return false;
        } //22455
        return meta.isGreater(); //22455
    } //22455

    /**
     * getGreater
     * @return
     * @author Anthony C. Liberto
     */
    private int getGreater() { //acl
        if (meta == null) {
            return -1;
        } //acl
        return meta.getGreater(); //acl
    } //acl

    /**
     * isPastDate
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPastDate() {
        if (bSearch) { //53004
            return false; //53004
        } //53004
        if (meta == null) {
            return (type == PAST_DATE_VALIDATOR);
        }
        return meta.isPastDate();
    }
    /**
     * BH - SR14 warning date
     * @return
     */
    public boolean isWarningDate() {
        if (bSearch || meta==null) { 
            return false; 
        } 
       
        return meta.isWarningDate();
    }
    /**
     * isNoBlanks
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isNoBlanks() { //21185
        if (meta == null) {
            return false;
        } //21185
        return meta.isNoBlanks(); //21185
    } //21185

    /**
     * isRequired
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isRequired() {
        if (bSearch) { //dwb_20030527
            return false; //dwb_20030527
        } //dwb_20030527
        if (editor != null) {
            return editor.isRequired();
        }
        return false;
    }

    /**
     * isSpecial
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isSpecial() {
        if (meta == null) {
            return false;
        }
        return meta.isSpecial();
    }

    /**
     * isUpper
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isUpper() {
        if (meta == null) {
            return false;
        }
        return meta.isUpper();
    }

    /**
     * getMinLen
     * @return
     * @author Anthony C. Liberto
     */
    private int getMinLen() {
        int eqLen = -1;
        //dwb_20030527		if (meta == null) return 0;
        if (meta == null || bSearch) { //dwb_20030527
            return 0; //dwb_20030527
        } //dwb_20030527
        eqLen = getEqualsLen();
        if (eqLen != 0) {
            return eqLen;
        }
        return meta.getMinLen();
    }

    /**
     * getEqualsLen
     * @return
     * @author Anthony C. Liberto
     */
    private int getEqualsLen() {
        //dwb_20030527		if (meta == null)
        if (meta == null || bSearch) { //dwb_20030527
            return 0; //dwb_20030527
        } //dwb_20030527
        return meta.getEqualsLen();
    }

    /**
     * getMaxLen
     * @return
     * @author Anthony C. Liberto
     */
    private int getMaxLen() {
        int eqLen = -1;
        if (meta == null) {
            return 0;
        }
        eqLen = getEqualsLen();
        if (eqLen != 0) {
            return eqLen;
        }
        return meta.getMaxLen();
    }

    /**
     * isSpellCheckable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSpellCheckable() {
        //dwb_20030527		if (meta != null) {
        if (meta == null || bSearch) { //dwb_20030527
            return false;
        } //dwb_20030527
        if (meta.getNLSID() != NLSID_ENGLISH) { //53703
            return false; //53703
        } //53703
        return meta.isSpellCheckable();
    }

    private void reset(int[] ra, String[] sRa, int max) { //2.4
        for (int x = 0; x < max; ++x) { //2.4
            ra[x] = DEF; //2.4
            sRa[x] = ""; //2.4
        } //2.4
    } //2.4

    private void reset(int[] ra, int max) { //2.4
        for (int x = 0; x < max; ++x) { //2.4
            ra[x] = DEF; //2.4
        } //2.4
    } //2.4

    /**
     * canLeave
     * @param _fullValue
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave(String _fullValue) {
        boolean out = false;
        if (meta == null) {
            return true;
        }
        out = checkLength(_fullValue, _fullValue.length(), true);

        if (!out) {
            generateErrorMessage(LENGTHCHECKS, _fullValue);
            return out;
        }
        out = validateDateTime(_fullValue, true);
        if (!out) {
            generateErrorMessage(DATECHECKS, _fullValue);
            return out;
        }
        return isDecimalFormatted(_fullValue);
    }

    /**
     * getEditorColor
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public Color getEditorColor(String _s) {
        boolean bRequired = isRequired();
        boolean bLessThanMin = isLessThanMin(_s);
        if (bRequired && bLessThanMin) {
            return getPrefColor(PREF_COLOR_LOW_REQUIRED, DEFAULT_COLOR_LOW_REQUIRED);
        } else if (bRequired) {
            return getPrefColor(PREF_COLOR_REQUIRED, DEFAULT_COLOR_REQUIRED);
        } else if (bLessThanMin) {
            return getPrefColor(PREF_COLOR_LOW, DEFAULT_COLOR_LOW);
        } else {
            return getPrefColor(PREF_COLOR_OK, DEFAULT_COLOR_OK);
        }
    }

    /**
     * isLessThanMin
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isLessThanMin(String _s) {
        int minLen = getMinLen();
        if (minLen <= 0) {
            return false;
        } else if (_s == null || _s.length() < minLen) {
            return true;
        }
        return false;
    }

    /**
     * preQualify
     * @param _meta
     * @param _s
     * @return
     * @author Anthony C. Liberto
     * /
    public String preQualify(EANMetaAttribute _meta, String _s) { //pre-qual
        int p = 0; //pre-qual
        int f = 0; //pre-qual
        boolean showMessage = false;
        char[] c = null;
        int ii = -1;
        StringBuffer sb = null;
        if (_meta == null) {
            return _s;
        } //pre-qual
        setMeta(_meta); //pre-qual
        c = _s.toCharArray(); //pre-qual
        sb = new StringBuffer(); //pre-qual
        ii = c.length; //pre-qual
        for (int i = 0; i < ii; ++i) { //pre-qual
            if (isNoBlanks() && Character.isSpaceChar(c[i])) { //pre-qual
                continue; //pre-qual
            } //pre-qual
            //pre-qual
            reset(valRa, XX); //pre-qual
            p = 0; //pre-qual
            f = 0; //pre-qual
            //pre-qual
            valRa[INTVAL] = intValidation(c[i]); //pre-qual
            valRa[ALPHAVAL] = alphaValidation(c[i]); //pre-qual
            valRa[SPECVAL] = specialValidation(c[i]); //pre-qual
            valRa[DATEVAL] = dateValidation(c[i], i); //pre-qual

            for (int x = 0; x < XX; ++x) { //pre-qual
                if (valRa[x] > 0) { //pre-qual
                    ++p; //pre-qual
                } else if (valRa[x] < 0) { //pre-qual
                    ++f; //pre-qual
                } //pre-qual
            } //pre-qual

            if (p >= 1 || f == 0) { //pre-qual
                sb.append(c[i]); //pre-qual
            } else { //pre-qual
                showMessage = true; //pre-qual
            } //pre-qual

        } //pre-qual
        if (showMessage) { //pre-qual
            fyiMsg("msg24018");
        } //pre-qual
        return sb.toString(); //pre-qual
    } //pre-qual
    */

    /**
     * validChange
     * @param in
     * @param fullValue
     * @param pos
     * @return
     * @author Anthony C. Liberto
     */
    private boolean validChange(String in, String fullValue, int pos) {
        char[] c = in.toCharArray();
        boolean out = false;
        int ii = c.length;
        int p = 0;
        int f = 0;
        reset(valRa, valSRa, XX);
        valRa[NUMDECVAL] = numdecValidation(fullValue); //CR2228//20907
        for (int i = 0; i < ii; ++i) {
            if (isNoBlanks() && Character.isSpaceChar(c[i])) { //21185
            	showError(null,"msg24017"); //msg24017 = No Blanks are allowed in this attribute.
                return false; //21185
            } //21185
            valRa[INTVAL] = intValidation(c[i]); //20907
            valRa[ALPHAVAL] = alphaValidation(c[i]);
            valRa[SPECVAL] = specialValidation(c[i]);
            if (ii == 1) {
                valRa[DATEVAL] = dateValidation(c[i], pos);
                valRa[TIMEVAL] = timeValidation(c[i], pos);
            } else {
                valRa[DATEVAL] = dateValidation(c[i], i);
                valRa[TIMEVAL] = timeValidation(c[i], pos);
            }
            p = 0;
            f = 0;
            for (int x = 0; x < XX; ++x) {
                if (valRa[x] > 0) {
                    ++p;
                } else if (valRa[x] < 0) {
                    ++f;
                }
            }
            if (p == 0 && f > 0) {
                generateErrorMessage(FORMATCHECKS, in);
                return false;
            }
        }
        //		if (ii == 1)
        //			out = checkLength(in,pos + in.length(),false);
        //		else
        //			out = checkLength(in,ii,false);
        out = checkLength(in, fullValue.length(), false);
        if (!out) {
            generateErrorMessage(LENGTHCHECKS, in);
            return false;
        }
        if (ii == 1) {
            out = validateDateTime(fullValue, false);
        } else {
            out = validateDateTime(in, false);
        }
        if (!out) {
            generateErrorMessage(DATECHECKS, in);
        }
        return out;
    }

    /**
     * checkLength
     * @param s
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean checkLength(String s) {
        return checkLength(s, s.length(), false);
    }*/

    /**
     * checkLength
     * @param str
     * @param pos
     * @param exit
     * @return
     * @author Anthony C. Liberto
     */
    public boolean checkLength(String str, int pos, boolean exit) {
        int f = 0;
        
        reset(lenRa, LL);
        evaluateLength(str, pos, exit);
        evaluateGreater(str, exit); //22455
        for (int l = 0; l < LL; ++l) {
            if (lenRa[l] < 0) {
                ++f;
            }
        }
        if (f > 0) {
            return false;
        }
        return true;
    }

    private void evaluateGreater(String _str, boolean _exit) { //22455
        lenRa[GRTVAL] = DEF; //22455
        if (!isGreater() || !_exit) { //22455
            return; //22455
        } //22455
        try { //22455
            int i = Integer.valueOf(_str).intValue(); //22455
            if (isGreater(i)) { //22455
                lenRa[GRTVAL] = PASS; //22455
            } else { //22455
                lenRa[GRTVAL] = FAIL; //22455
            } //22455
        } catch (NumberFormatException _nfe) { //22455
            _nfe.printStackTrace(); //22455
            lenRa[GRTVAL] = FAIL; //22455
        } //22455
        return; //22455
    } //22455

    private boolean isGreater(int _i) { //22455
        if (meta == null) { //22455
            return true; //22455
        } //22455
        return _i > meta.getGreater(); //22455
    } //22455

    private void evaluateLength(String str, int pos, boolean exit) {
        int minLen = -1;
        int maxLen = -1;
        if (exit) {
            lenRa[REQVAL] = evaluateRequired(str, pos);
            
            minLen = getMinLen();
            if (minLen == 0 || pos == 0) {
                lenRa[MINVAL] = DEF;

            } else if (pos < minLen) {
                lenRa[MINVAL] = FAIL;

            } else if (pos >= minLen) {
                lenRa[MINVAL] = PASS;
            }
        } else {
            lenRa[REQVAL] = DEF;
            lenRa[MINVAL] = DEF;
        }
        maxLen = getMaxLen();
        if (maxLen == 0 || pos == 0) {
            lenRa[MAXVAL] = DEF;

        } else if (pos > maxLen) {
            lenRa[MAXVAL] = FAIL;

        } else if (pos <= maxLen) {
            lenRa[MAXVAL] = PASS;
        }
    }

    private int evaluateRequired(String s, int len) {
        boolean b = false;
        if (len > 0) {
            return PASS;
        }
        b = isRequired();
        if (!b) {
            return DEF;
        } else if (b && len == 0) {
            if (Routines.have(s)) {
                return PASS;
            }
            return FAIL;
        } else {
            return PASS;
        }
    }
/*
    private int evaluateRequired(String s) {
        return evaluateRequired(s, s.length());
    }
*/
    private int numdecValidation(String _s) { //CR2228
        char[] c = _s.toCharArray(); //CR2228
        int ii = c.length; //CR2228
        if (isDecimal()) { //CR2228
            valSRa[NUMDECVAL] = " (Decimal)"; //20907
            //20864			if (isDecimalFormatted(_s,meta,false)) {											//CR2228
            if (isDecimalFormatted(c, ii)) { //20864
                return PASS; //CR2228
            } else { //CR2228
                return FAIL; //CR2228
            } //CR2228
        } else if (isNumeric()) { //CR2228
            valSRa[NUMDECVAL] = " (Numeric)"; //20907
            if (isNumericFormatted(c, ii)) { //CR2228
                return PASS; //CR2228
            } else { //CR2228
                return FAIL; //CR2228
            } //CR2228
        } //CR2228
        return DEF; //CR2228
    } //CR2228

    /**
     * isDecimalFormatted
     * @param _c
     * @param _ii
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isDecimalFormatted(char[] _c, int _ii) { //20864
        boolean decPres = false; //20864
        for (int i = 0; i < _ii; ++i) { //20864
            if (_c[i] == '.') { //20864
                if (decPres) { //20864
                    return false; //20864
                } else { //20864
                    decPres = true; //20864
                } //20864
            } else if (!Character.isDigit(_c[i])) { //20864
                return false; //20864
            } //20864
        } //20864
        return true; //20864
    } //20864

    /**
     * isNumericFormatted
     * @param _c
     * @param _ii
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isNumericFormatted(char[] _c, int _ii) { //CR2228
        boolean decPres = false; //CR2228
        for (int i = 0; i < _ii; ++i) { //CR2228
            if (i == 0) { //CR2228
                if (!Character.isDigit(_c[i]) && _c[i] != '.' && _c[i] != '+' && _c[i] != '-') { //CR2228
                    return false; //CR2228
                } //CR2228
                if (_c[i] == '.') { //CR2228
                    if (decPres) { //CR2228
                        return false; //CR2228
                    } else { //CR2228
                        decPres = true; //CR2228
                    } //CR2228
                } //CR2228
            } else { //CR2228
                if (_c[i] == '.') { //CR2228
                    if (decPres) { //CR2228
                        return false; //CR2228
                    } else { //CR2228
                        decPres = true; //CR2228
                    } //CR2228
                } else if (!Character.isDigit(_c[i])) { //CR2228
                    return false; //CR2228
                } //CR2228
            } //CR2228
        } //CR2228
        return true; //CR2228
    } //CR2228

    private int intValidation(char c) {
        if (isInteger()) {
            valSRa[INTVAL] = " (Integer)";
            if (Character.isDigit(c)) {
                return PASS;

            } else {
                return FAIL;
            }
        }
        return DEF;
    }

    private int alphaValidation(char c) {
        if (isUpper()) {
            valSRa[ALPHAVAL] = " (Upper)";
            if (isLetter(c) || Character.isSpaceChar(c)) { //20050201
                return PASS;
            } else {
                return FAIL;
            }
        } else if (isLower()) {
            valSRa[ALPHAVAL] = " (Lower)";
            if (isLetter(c) && (!Character.isUpperCase(c) || Character.isSpaceChar(c))) { //51984
                return PASS;
            } else {
                return FAIL;
            }
        } else if (isAlpha()) {
            valSRa[ALPHAVAL] = " (Alpha)";
            if (isLetter(c)) {
                return PASS;
            } else {
                return FAIL;
            }
        }
        return DEF;
    }

    private boolean isLetter(char c) {
        if (Character.isLetter(c) || Character.isSpaceChar(c)) {
            return true;
        }
        return false;
    }

    private int specialValidation(char c) {
        if (isSpecial()) {
            if (!isLetter(c) && !Character.isDigit(c)) { //1.1
                return PASS;
            }
        }
        return DEF;
    }

    //	private int requiredValidation(String s) {
    //		if (isRequired()) {
    //			if (routines.have(s))
    //				return PASS;
    //		}
    //		return def;
    //	}

    private int dateValidation(char c, int pos) {
        if (isDate()) {
            if (pos == 4 || pos == 7) {
                if (c != '-') {
                    return FAIL;
                }
            } else {
                if (!Character.isDigit(c)) {
                    return FAIL;
                }
            }
        }
        return DEF;
    }

    private int timeValidation(char c, int pos) {
        if (isTime()) {
            if (pos == 2) {
                if (c != ':') {
                    return FAIL;
                }
            } else {
                if (!Character.isDigit(c)) {
                    return FAIL;
                }
            }
        }
        return DEF;
    }

    private boolean validateDateTime(String _dateTime, boolean _exit) {
        if (isDate()) {
            return validateDate(_dateTime, _exit);
        } else if (isTime()) {
            return validateTime(_dateTime, _exit);
        }
        return true;
    }

    private boolean validateTime(String _time, boolean _exit) {
        boolean bValid = getDateRoutines().isValidPartialTime(_time);
        valSRa[PARTTIMEVAL] = "partial time";
        if (bValid) {
            valRa[PARTTIMEVAL] = PASS;
            return true;
        } else {
            valRa[PARTTIMEVAL] = FAIL;
            return false;
        }
    }

    /**
     * getAttributeCode
     * @return
     * @author Anthony C. Liberto
     */
    private String getAttributeCode() {
        if (meta != null) {
            return meta.getAttributeCode();
        }
        if (type == DATE_VALIDATOR) {
            return "Date";

        } else if (type == PAST_DATE_VALIDATOR) {
            return "Past Date";

        } else if (type == FUTURE_DATE_VALIDATOR) {
            return "Future Date";

        } else if (type == TIME_VALIDATOR) {
            return "Time";
        }
        return "unknown";
    }

    /**
     * validateDate
     * @param strDate
     * @param exit
     * @return
     * @author Anthony C. Liberto
     */
    public boolean validateDate(String strDate, boolean exit) {
        reset(dateRa, DD);
        if (!isDate()) {
            return true;
        }
        dateRa[FULLDATE] = isValidDate(strDate);
        if (strDate.length() > 10) {
            dateRa[LENDATE] = FAIL;
        } else {
            dateRa[LENDATE] = PASS;
        }
        dateRa[PARTDATE] = isPartialDate(strDate);
        dateRa[PASTDATE] = isPastDate(strDate);
        dateRa[FUTUREDATE] = isFutureDate(strDate);
        return evaluateDateArray(exit);
    }

    private boolean evaluateDateArray(boolean exit) {
        if (dateRa[FULLDATE] == FAIL && exit) {
            return false;
        } else if (dateRa[LENDATE] == FAIL) {
            return false;
        } else if (dateRa[PARTDATE] == FAIL && !exit) {
            return false;
        } else if (dateRa[PASTDATE] == FAIL) {
            return false;
        } else if (dateRa[FUTUREDATE] == FAIL) {
            return false;
        }
        return true;
    }

    private int isValidDate(String inDate) {
        //		if (!isDate())
        //			return def;
        boolean b = getDateRoutines().validDate(inDate);
        if (b) {
            return PASS;
        }
        return FAIL;
    }

    private int isPartialDate(String inDate) {
        //		if (!isDate())
        //			return def;
        boolean b = getDateRoutines().isValidPartialDate(inDate, false);
        if (b) {
            return PASS;
        }
        return FAIL;
    }

    private int isPastDate(String inDate) {
        if (isPastDate()) {
            int pos = inDate.length();
            try {
                if (inDate.substring(0, pos).compareToIgnoreCase(getCurrentDate().substring(0, pos)) <= 0) {
                    return PASS;
                } else {
                	if (isWarningDate()){ //BH - SR14 warning date
                		//Warning Message could be eg : "2009-07-01" is not in the past.
                		setMessage("Warning: '"+inDate+"' is not in the past.  Do you want to continue?");
                		int r = eaccess().showConfirm((Component)editor, YES_NO, true);
                        if (r == YES) {
                            return PASS; 
                        } 
                	}
                    return FAIL;
                }
            } catch (IndexOutOfBoundsException _ioob) {
                EAccess.report(_ioob,false);
                return FAIL;
            }
        }
        return DEF;
    }

    /**
     * isFutureDate
     * @param inDate
     * @return
     * @author Anthony C. Liberto
     */
    private int isFutureDate(String inDate) {
        if (isFutureDate()) {
            int pos = inDate.length();
            if (inDate.substring(0, pos).compareToIgnoreCase(getCurrentDate().substring(0, pos)) >= 0) {
                return PASS;
            } else {
            	if (isWarningDate()){ //BH - SR14 warning date
            		//Warning Message could be eg : "2009-07-01" is not in the future.
            		setMessage("Warning: '"+inDate+"' is not in the future.  Do you want to continue?");
            		int r = eaccess().showConfirm((Component)editor, YES_NO, true);
                    if (r == YES) {
                        return PASS; 
                    } 
            	}
                return FAIL;
            }
        }
        return DEF;
    }

    /**
     * isDecimalFormatted
     * @param value
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isDecimalFormatted(String value) {
        return isDecimalFormatted(value, true);
    }

    /**
     * isDecimalFormatted
     * @param value
     * @param msg
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isDecimalFormatted(String value, boolean msg) {
        int decPlaces = -1;
        int indx = -1;
        int dec = 0;
        if (meta == null) { //eAnnounce1.1 9/20/01
            return false; //eAnnounce1.1 9/20/01
        }
        if (!meta.isDecimal()) {
            return true; //<--unFormatted attribute
        }
        if (!Routines.have(value)) { //if nothing there than pass required will check for required.
            return true;
        }
        decPlaces = meta.getDecimalPlaces();
        indx = (value.indexOf('.') + 1);
        if (indx == 0) {
            decVal = "No Decimal (.) found";
            if (msg) {
                generateErrorMessage("decVal", value);
            }
            return false; //<--no decimal ('.') found
        }
        if (indx != (value.lastIndexOf('.') + 1)) {
            decVal = "Multiple Decimals (.) found";
            if (msg) {
                generateErrorMessage("decVal", value);
            }
            return false; //<--more than a single decimal
        }
        if (indx < value.length()) {
            dec = value.substring(indx).length();
        }
        if (decPlaces == 0) { //0 implies everything legal except no dec
            if (dec > 0) {
                return true;
            } else {
                decVal = "Need at least one digit to the right of the decimal (.)";
                if (msg) {
                    generateErrorMessage("decVal", value);
                }
                return false; //<--must at least be .n
            }
        }
        if (decPlaces != dec) {
            decVal = "Wrong number of decimals (.) found, " + decPlaces + " decimal place(s) required";
            if (msg) {
                generateErrorMessage("decVal", value);
            }
            return false; //<--wrong number of decimal places
        }
        return true; //<--correct format
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        att = null;
        meta = null;
        //id = null; 
    }

    /**
     * getNow
     * @return
     * @author Anthony C. Liberto
     * /
    public String getNow() {
        return eaccess().getNow();
    }*/

    /*
     acl_20030511
     */
    /**
     * getCurrentDate
     * @return
     * @author Anthony C. Liberto
     */
    private String getCurrentDate() {
        return System.getProperty("mw.now.date");
    }

    /*
     dwb_20030527
     */
    /**
     * setSearch
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSearch(boolean _b) {
        bSearch = _b;
    }

    /**
     * isSearch
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isSearch() {
        return bSearch;
    }*/
}
