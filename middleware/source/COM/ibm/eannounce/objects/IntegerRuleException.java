//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: IntegerRuleException.java,v $
// Revision 1.5  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.4  2005/03/03 22:39:32  dave
// JTest working and cleanup
//
// Revision 1.3  2002/08/15 16:59:39  joan
// fix integer check
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
 * IntegerRuleException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IntegerRuleException extends EANBusinessRuleException {

    /**
     * IntegerRuleException
     *
     *  @author David Bigelow
     */
    public IntegerRuleException() {
        super();
    }

    /**
     * Constructs a <code>IntegerRuleException</code> with the specified detail message
     *
     * @param s
     */
    public IntegerRuleException(String s) {
        super(s);
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
        if (eanMA.isInteger() && (!eanMA.isAlpha()) && (!eanMA.isSpecial())) {
            if (strValue != null & strValue.length() > 0) {
                if (!isDigit(strValue)) {
                    add(_eanAttr, strValue + ": Non-integer input.");
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

        clearLists();
        if (eanMA.isInteger() && (!eanMA.isAlpha()) && (!eanMA.isSpecial())) {
            if (strValue != null & strValue.length() > 0) {
                if (!isDigit(strValue)) {
                    add(_mi, strValue + ": Non-integer input.");
                }
            }
        }
	}
    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: IntegerRuleException.java,v 1.5 2005/09/08 18:12:25 joan Exp $";
    }
}
