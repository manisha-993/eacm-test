//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DecimalRuleException.java,v $
// Revision 1.4  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.3  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.2  2002/04/19 20:55:32  joan
// fixing errors
//
// Revision 1.1  2002/04/15 22:51:11  joan
// remove EAN prefix
//
//

package COM.ibm.eannounce.objects;

/**
 * DecimalRuleException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DecimalRuleException extends EANBusinessRuleException {

    /**
     * DecimalRuleException
     *
     *  @author David Bigelow
     */
    public DecimalRuleException() {
        super();
    }

    /**
     * Constructs a <code>DecimalRuleException</code> with the specified detail message
     *
     * @param s
     */
    public DecimalRuleException(String s) {
        super(s);
    }

    private boolean isDigit(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!(Character.isDigit(c) || c == '.')) {
                return false;
            }
        }
        return true;
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

        clearLists();
        if (eanMA.isDecimal()) {
            if (strValue != null && strValue.length() > 0) {
                int decPlaces = eanMA.getDecimalPlaces();
                int indx = (strValue.indexOf('.') + 1);
                int dec = 0;
                if (!isDigit(strValue)) {
                    add(_eanAttr, strValue + ": Non-numeric input.");
                }

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
                if (decPlaces == 0) {
                    if (dec <= 0) {
                        add(_eanAttr, strValue + ": Need at least one digit to the right of the decimal (.)");
                    }
                }
                if (decPlaces != dec) {
                    add(_eanAttr, strValue + ": Wrong number of decimals (.) found, " + decPlaces + " decimal place(s) required");
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
	}

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: DecimalRuleException.java,v 1.4 2005/09/08 18:12:25 joan Exp $";
    }

}
