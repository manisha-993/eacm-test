//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DateRuleException.java,v $
// Revision 1.11  2011/09/19 20:53:50  wendy
// Add colon to error msg
//
// Revision 1.10  2009/06/09 17:32:32  wendy
// BH SR-14 date warnings
//
// Revision 1.9  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.8  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.7  2003/04/24 18:32:15  dave
// getting rid of traces and system out printlns
//
// Revision 1.6  2002/11/12 17:18:26  dave
// System.out.println clean up
//
// Revision 1.5  2002/09/25 17:37:14  joan
// remove System.out
//
// Revision 1.4  2002/06/07 22:30:29  joan
// working on business rule
//
// Revision 1.3  2002/06/05 16:04:33  joan
// add pastdate, futuredate
//
// Revision 1.2  2002/04/16 22:35:01  joan
// fixing bugs
//
// Revision 1.1  2002/04/15 22:51:11  joan
// remove EAN prefix
//

package COM.ibm.eannounce.objects;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * DateRuleException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateRuleException extends EANBusinessRuleException {

	private static final long serialVersionUID = 1L;
	/**
     * DateRuleException
     *
     *  @author David Bigelow
     */
    public DateRuleException() {
        super();
    }

    /**
     * Constructs a <code>DateRuleException</code> with the specified detail message
     *
     * @param s
     */
    public DateRuleException(String s) {
        super(s);
    }

    private boolean isLeapYear(int _iYear) {
        boolean test = false;
        if (_iYear == 3600) {
            test = false;
        } else if (_iYear % 100 == 0) {
            if (_iYear % 400 == 0) {
                test = true;
            }
        } else if (_iYear % 4 == 0) {
            test = true;
        }
        return test;
    }

    private boolean isDayValid(int _iDay, int _iMonth, int _iYear) {
        switch (_iMonth) {
        case 1 :
        case 3 :
        case 5 :
        case 7 :
        case 8 :
        case 10 :
        case 12 :
            if (_iDay >= 1 && _iDay <= 31) {
                return true;

            } else {
                return false;
            }
        case 4 :
        case 6 :
        case 9 :
        case 11 :
            if (_iDay >= 1 && _iDay <= 30) {
                return true;

            } else {
                return false;
            }
        case 2 :
            if (isLeapYear(_iYear)) {
                if (_iDay >= 1 && _iDay <= 29) {
                    return true;

                } else {
                    return false;
                }
            } else {
                if (_iDay >= 1 && _iDay <= 28) {
                    return true;

                } else {
                    return false;
                }
            }
        default :
            break;
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

        clearLists();
        if (eanMA.isDate() && strValue != null) {
            int day = 0;
            int year = 0;
            int month = 0;

            int strLen = strValue.length();
            if (strLen <= 0) {
                return;
            }

            if (strLen != 10) {
                add(_eanAttr, strValue + ": Wrong date format (YYYY-MM-DD)");
                return;
            }

            //check whether input is in correct format

            for (int i = 0; i < strLen; i++) {
                char c = strValue.charAt(i);
                if (i == 4 || i == 7) {
                    if (c != '-') {
                        add(_eanAttr, strValue + ": Wrong date format, (YYYY-MM-DD)");
                        return;
                    }
                } else {
                    if (!Character.isDigit(c)) {
                        add(_eanAttr, strValue + ": Wrong date format, non-numeric input");
                        return;
                    }
                }
            }

            // check if year is not less than 1980
            year = Integer.valueOf(new String(strValue.toCharArray(), 0, 4)).intValue();
            if (year < 1980) {
                add(_eanAttr, strValue + ": Year must be greater than or equal 1980");
                return;
            }

            // check if month from 1 to 12
            month = Integer.valueOf(new String(strValue.toCharArray(), 5, 2)).intValue();
            if (month < 1 || month > 12) {
                add(_eanAttr, strValue + ": Month must be from 1 to 12");
                return;
            }

            // check if day is valid
            day = Integer.valueOf(new String(strValue.toCharArray(), 8, 2)).intValue();
            if (!isDayValid(day, month, year)) {
                add(_eanAttr, strValue + ": Day is not valid");
                return;
            }

            //past date validation
            if (eanMA.isPastDate() && !eanMA.isWarningDate()) {
                Profile p = _eanAttr.getProfile();
                String strNow = p.getValOn();
                if (strValue.compareToIgnoreCase(strNow.substring(0, strLen)) > 0) {
                    add(_eanAttr, strValue + ": Past date required");
                    return;
                }
            }
            //future date validation
            if (eanMA.isFutureDate() && !eanMA.isWarningDate()) {
                Profile p = _eanAttr.getProfile();
                String strNow = p.getValOn();
                if (strValue.compareToIgnoreCase(strNow.substring(0, strLen)) < 0) {
                    add(_eanAttr, strValue + ": Future date required");
                    return;
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
        return "$Id: DateRuleException.java,v 1.11 2011/09/19 20:53:50 wendy Exp $";
    }

}
