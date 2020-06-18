//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LengthRuleException.java,v $
// Revision 1.4  2005/09/08 19:08:11  joan
// fixes
//
// Revision 1.3  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.2  2005/03/04 18:26:23  dave
// Jtest actions for the day
//
// Revision 1.1  2002/04/15 22:51:11  joan
// remove EAN prefix
//
//

package COM.ibm.eannounce.objects;

/**
 * LengthRuleException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LengthRuleException extends EANBusinessRuleException {

    /**
     * LengthRuleException
     *
     *  @author David Bigelow
     */
    public LengthRuleException() {
        super();
    }

    /**
     * Constructs a <code>LengthRuleException</code> with the specified detail message
     *
     * @param s
     */
    public LengthRuleException(String s) {
        super(s);
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
        if (strValue != null) {
            //max length validation
            int len = eanMA.getMaxLen();
            int strLen = strValue.length();
            if (len != 0 && strLen != 0 && strLen > len) {
                add(_eanAttr, strValue + ": Above maximum length requirement of " + len);
            }
            //min length validation
            len = eanMA.getMinLen();
            if (len != 0 && strLen != 0 && strLen < len) {
                add(_eanAttr, strValue + ": Below minimum length requirement of " + len);
            }
            //equal length validation
            len = eanMA.getEqualsLen();
            if (len != 0 && strLen != 0 && strLen != len) {
                add(_eanAttr, strValue + ": Not equal length requirement of " + len);
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
		String strTraceBase = "LengthRuleException validate method ";
        EANMetaAttribute eanMA = _eanMA;
        String strValue = _strValue;

        clearLists();
        if (strValue != null) {
//			System.out.println(strTraceBase + ":" + strValue.length() + ", max:" + eanMA.getMaxLen() + ", min:" + eanMA.getMinLen() + ", equal:" + eanMA.getEqualsLen());
            //max length validation
            int len = eanMA.getMaxLen();
            int strLen = strValue.length();
            if (len != 0 && strLen != 0 && strLen > len) {
                add(_mi, strValue + ": Above maximum length requirement of " + len);
            }
            //min length validation
            len = eanMA.getMinLen();
            if (len != 0 && strLen != 0 && strLen < len) {
                add(_mi, strValue + ": Below minimum length requirement of " + len);
            }
            //equal length validation
            len = eanMA.getEqualsLen();
            if (len != 0 && strLen != 0 && strLen != len) {
                add(_mi, strValue + ": Not equal length requirement of " + len);
            }
        }
	}
    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: LengthRuleException.java,v 1.4 2005/09/08 19:08:11 joan Exp $";
    }

}
