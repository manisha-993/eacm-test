//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: AlphaNumberRuleException.java,v $
// Revision 1.26  2005/09/08 18:12:24  joan
// add testing rule for meta maintenance
//
// Revision 1.25  2005/02/09 23:46:46  dave
// more Jtest Cleanup
//
// Revision 1.24  2004/10/28 17:48:19  dave
// some minor fixes
//
// Revision 1.23  2003/09/15 23:43:16  joan
// fb51984
//
// Revision 1.22  2003/04/23 23:46:57  dave
// fix decimal place 0 again
//
// Revision 1.21  2003/04/21 21:12:53  dave
// fixing decimal zero rule.. and attributecode
//
// Revision 1.20  2003/04/02 23:51:29  dave
// fixed more int rules
//
// Revision 1.19  2003/04/02 22:52:55  dave
// Syntax fix
//
// Revision 1.18  2003/04/02 22:21:00  dave
// fixing the Number rule (digit, . +-)
//
// Revision 1.17  2002/10/14 23:23:45  dave
// syntax fix
//
// Revision 1.16  2002/10/14 23:08:01  dave
// adding Greater Than Rule
//
// Revision 1.15  2002/10/07 23:29:50  dave
// fix on index for String Check
//
// Revision 1.14  2002/10/07 22:53:12  dave
// more paren balancing
//
// Revision 1.13  2002/10/07 22:32:36  dave
// fix to syntax
//
// Revision 1.12  2002/10/07 22:23:45  dave
// fix to error checking
//
// Revision 1.11  2002/08/29 18:26:37  joan
// add isDigitOrDecimalPoint method
//
// Revision 1.10  2002/08/16 22:29:46  dave
// simple fix to syntax
//
// Revision 1.9  2002/08/16 22:14:40  dave
// fix to the e-announce no blanks exception thingie
//
// Revision 1.8  2002/08/15 15:09:10  dave
// isUpper needs Letters only
//
// Revision 1.7  2002/08/15 14:49:09  dave
// added Integer checking to 1.1 and alpha.. upper.. etc
//
// Revision 1.6  2002/08/15 14:19:30  dave
// more local rules on put
//
// Revision 1.5  2002/08/14 22:55:33  dave
// clean up in business rule checks
//
// Revision 1.4  2002/08/14 22:18:51  dave
// minor fix for alphanumericupper
//
// Revision 1.3  2002/08/02 17:29:24  joan
// change ALPHAINTEGER
//
// Revision 1.2  2002/04/19 20:55:32  joan
// fixing errors
//
// Revision 1.1  2002/04/15 22:51:11  joan
// remove EAN prefix
//

package COM.ibm.eannounce.objects;

/**
 * AlphaNumberRuleException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AlphaNumberRuleException extends EANBusinessRuleException {

    private static final String SPEC_CHAR = "!@#$%^&*()_-+=|]}[{';:?/>.<,\"";

    /**
     * AlphaNumberRuleException
     *
     *  @author David Bigelow
     */
    public AlphaNumberRuleException() {
        super();
    }

    /**
     * Constructs a <code>AlphaNumberRuleException</code> with the specified detail message
     *
     * @param s
     */
    public AlphaNumberRuleException(String s) {
        super(s);
    }

    private boolean isAlpha(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!(Character.isLetter(c) || Character.isSpaceChar(c))) {
                return false;
            }
        }
        return true;
    }

    private boolean isAlphaInteger(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!(Character.isLetterOrDigit(c) || Character.isSpaceChar(c))) {
                return false;
            }
        }
        return true;
    }

    private boolean isAlphaIntegerSpecial(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!(Character.isLetterOrDigit(c) || Character.isWhitespace(c) || Character.isSpaceChar(c) || SPEC_CHAR.indexOf(c + "") != -1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAlphaSpecial(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!(Character.isLetter(c) || Character.isSpaceChar(c) || SPEC_CHAR.indexOf(c + "") != -1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNum(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!Character.isDigit(c) && !(c == '.') && !(c ==  '+') && !(c == '-')) {
                return false;
            }
        }
        return true;
    }

    private boolean isDigit(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isDigitOrDecimalPoint(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!(Character.isDigit(c) || c == '.')) {
                return false;
            }
        }
        return true;
    }


    private boolean isUpper(String _s) {
        boolean bUpper = true;
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            // First .. ensure that is is a Character we are checking
            if (Character.isLetter(c)) {
                if (!Character.isLowerCase(c) || Character.isSpaceChar(c)) {
                    bUpper = true;
                } else {
                    return false;
                }
            }
        }
        return true;

    }

    private boolean hasBlanks(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (Character.isWhitespace(c) || Character.isSpaceChar(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * (non-Javadoc)
     * validate
     *
     * @see COM.ibm.eannounce.objects.EANBusinessRuleException#validate(COM.ibm.eannounce.objects.EANAttribute, java.lang.String)
     */
    public void validate(EANAttribute _eanAttr, String _strValue) {

        EANMetaAttribute eanMA = _eanAttr.getMetaAttribute();
        String strValue = _strValue;

        // Lets bring in all the checks
        boolean isAlpha = eanMA.isAlpha();
        boolean isNum = eanMA.isNumeric();
        boolean isInt = eanMA.isInteger();
        boolean isSpec = eanMA.isSpecial();
        boolean isUpper = eanMA.isUpper();
        boolean isDec = eanMA.isDecimal();
        boolean isNoBla = eanMA.isNoBlanks();
        boolean isGreater = eanMA.isGreater();
        int iGreater = eanMA.getGreater();
        int dec = 0;
        int decPlaces = 0;
        int indx = 0;

        clearLists();

        // Leave if there is nothing to check
        if (strValue == null || strValue.length() == 0) {
            return;
        }

        // if no blanks are allowed.. lets check now.. otherwise.. let them have blanks
        if (isNoBla) {
            if (hasBlanks(strValue)) {
                add(_eanAttr, strValue + ": No Blanks or Spaces are allowed in this Attribute.");
            }
        }

        // Check for Greater

        if (isGreater) {
            if (isDigit(strValue)) {
                if (Integer.valueOf(strValue).intValue() <= iGreater) {
                    add(_eanAttr, strValue + ": Has to be greater than " + iGreater + ".");
                }
            }
        }

        if (isAlpha && isInt && isSpec && isUpper) {
            if (!(isAlphaIntegerSpecial(strValue) && isUpper(strValue))) {
                add(_eanAttr, strValue + ": Needs to contain only alpha, integer, or special characters, and must be upper case.");
            }
        } else if (isAlpha && isInt && isSpec) {
            if (!(isAlphaIntegerSpecial(strValue))) {
                add(_eanAttr, strValue + ": Needs to contain only alpha, integer, or special characters.");
            }
        } else if (isAlpha && isInt && isUpper) {
            if (!(isAlphaInteger(strValue) && isUpper(strValue))) {
                add(_eanAttr, strValue + ": Needs to contain only alpha, or integer characters, and must be upper case.");
            }
        } else if (isAlpha && isSpec && isUpper) {
            if (!(isAlphaSpecial(strValue) && isUpper(strValue))) {
                add(_eanAttr, strValue + ": Needs to contain only alpha or special characters, and must be upper case.");
            }
        } else if (isAlpha && isInt) {
            if (!isAlphaInteger(strValue)) {
                add(_eanAttr, strValue + ": Needs to contain only alpha, or integer characters.");
            }
        } else if (isAlpha && isSpec) {
            if (!isAlphaSpecial(strValue)) {
                add(_eanAttr, strValue + ": Needs to contain only alpha, or special characters");
            }
        } else if (isAlpha && isUpper) {
            if (!(isAlpha(strValue) && isUpper(strValue))) {
                add(_eanAttr, strValue + ": Needs to contain only alpha characters and must be upper case");
            }
        } else if (isAlpha) {
            if (!(isAlpha(strValue))) {
                add(_eanAttr, strValue + ": Needs to contain only alpha characters");
            }
        } else if (isNum) {
            if (!isNum(strValue)) {
                add(_eanAttr, strValue + ": Needs to contain only numbers, '.', or '+', '-'");
            }
        } else if (isInt) {
            if (!isDigit(strValue)) {
                add(_eanAttr, strValue + ": Needs to contain only Integer number");
            }
        } else if (isDec) {
            if (strValue != null && strValue.length() > 0) {
                if (!isDigitOrDecimalPoint(strValue)) {
                    add(_eanAttr, strValue + ": Non-numeric input.");
                }
                decPlaces = eanMA.getDecimalPlaces();
                indx = (strValue.indexOf('.') + 1);
                if (indx == 0) {
                    add(_eanAttr, strValue + ": No Decimal (.) found");
                }
                if (indx != (strValue.lastIndexOf('.') + 1)) {
                    add(_eanAttr, strValue + ": Multiple Decimals (.) found");
                }
                dec = 0;
                if (indx < strValue.length()) {
                    dec = strValue.substring(indx).length();
                }
                if (decPlaces > 0) {
                    if (dec <= 0) {
                        add(_eanAttr, strValue + ": Need at least one digit to the right of the decimal (.)");
                    }
                    if (decPlaces != dec) {
                        add(_eanAttr, strValue + ": Wrong number of decimals (.) found, " + decPlaces + " decimal place(s) required");
                    }
                }
            }
        }
    }

    /**
     * (non-Javadoc)
     * validate
     *
     * @see COM.ibm.eannounce.objects.EANBusinessRuleException#validate(COM.ibm.eannounce.objects.EANMetaAttribute, MetaFlagMaintItem _mi, java.lang.String)
     */
    public void validate(EANMetaAttribute _eanMA, MetaFlagMaintItem _mi, String _strValue) {

        EANMetaAttribute eanMA = _eanMA;
        String strValue = _strValue;

        // Lets bring in all the checks
        boolean isAlpha = eanMA.isAlpha();
        boolean isNum = eanMA.isNumeric();
        boolean isInt = eanMA.isInteger();
        boolean isSpec = eanMA.isSpecial();
        boolean isUpper = eanMA.isUpper();
        boolean isDec = eanMA.isDecimal();
        boolean isNoBla = eanMA.isNoBlanks();
        boolean isGreater = eanMA.isGreater();
        int iGreater = eanMA.getGreater();
        int dec = 0;
        int decPlaces = 0;
        int indx = 0;

        clearLists();

        // Leave if there is nothing to check
        if (strValue == null || strValue.length() == 0) {
            return;
        }

        // if no blanks are allowed.. lets check now.. otherwise.. let them have blanks
        if (isNoBla) {
            if (hasBlanks(strValue)) {
                add(_mi, strValue + ": No Blanks or Spaces are allowed in this Attribute.");
            }
        }

        // Check for Greater

        if (isGreater) {
            if (isDigit(strValue)) {
                if (Integer.valueOf(strValue).intValue() <= iGreater) {
                    add(_mi, strValue + ": Has to be greater than " + iGreater + ".");
                }
            }
        }

        if (isAlpha && isInt && isSpec && isUpper) {
            if (!(isAlphaIntegerSpecial(strValue) && isUpper(strValue))) {
                add(_mi, strValue + ": Needs to contain only alpha, integer, or special characters, and must be upper case.");
            }
        } else if (isAlpha && isInt && isSpec) {
            if (!(isAlphaIntegerSpecial(strValue))) {
                add(_mi, strValue + ": Needs to contain only alpha, integer, or special characters.");
            }
        } else if (isAlpha && isInt && isUpper) {
            if (!(isAlphaInteger(strValue) && isUpper(strValue))) {
                add(_mi, strValue + ": Needs to contain only alpha, or integer characters, and must be upper case.");
            }
        } else if (isAlpha && isSpec && isUpper) {
            if (!(isAlphaSpecial(strValue) && isUpper(strValue))) {
                add(_mi, strValue + ": Needs to contain only alpha or special characters, and must be upper case.");
            }
        } else if (isAlpha && isInt) {
            if (!isAlphaInteger(strValue)) {
                add(_mi, strValue + ": Needs to contain only alpha, or integer characters.");
            }
        } else if (isAlpha && isSpec) {
            if (!isAlphaSpecial(strValue)) {
                add(_mi, strValue + ": Needs to contain only alpha, or special characters");
            }
        } else if (isAlpha && isUpper) {
            if (!(isAlpha(strValue) && isUpper(strValue))) {
                add(_mi, strValue + ": Needs to contain only alpha characters and must be upper case");
            }
        } else if (isAlpha) {
            if (!(isAlpha(strValue))) {
                add(_mi, strValue + ": Needs to contain only alpha characters");
            }
        } else if (isNum) {
            if (!isNum(strValue)) {
                add(_mi, strValue + ": Needs to contain only numbers, '.', or '+', '-'");
            }
        } else if (isInt) {
            if (!isDigit(strValue)) {
                add(_mi, strValue + ": Needs to contain only Integer number");
            }
        } else if (isDec) {
            if (strValue != null && strValue.length() > 0) {
                if (!isDigitOrDecimalPoint(strValue)) {
                    add(_mi, strValue + ": Non-numeric input.");
                }
                decPlaces = eanMA.getDecimalPlaces();
                indx = (strValue.indexOf('.') + 1);
                if (indx == 0) {
                    add(_mi, strValue + ": No Decimal (.) found");
                }
                if (indx != (strValue.lastIndexOf('.') + 1)) {
                    add(_mi, strValue + ": Multiple Decimals (.) found");
                }
                dec = 0;
                if (indx < strValue.length()) {
                    dec = strValue.substring(indx).length();
                }
                if (decPlaces > 0) {
                    if (dec <= 0) {
                        add(_mi, strValue + ": Need at least one digit to the right of the decimal (.)");
                    }
                    if (decPlaces != dec) {
                        add(_mi, strValue + ": Wrong number of decimals (.) found, " + decPlaces + " decimal place(s) required");
                    }
                }
            }
        }
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: AlphaNumberRuleException.java,v 1.26 2005/09/08 18:12:24 joan Exp $";
    }
}
