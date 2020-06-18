//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TimeRuleException.java,v $
// Revision 1.4  2008/09/08 17:44:36  wendy
// Cleanup some RSA warnings
//
// Revision 1.3  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.2  2002/09/25 17:37:15  joan
// remove System.out
//
// Revision 1.1  2002/06/07 22:39:35  joan
// initial load
//
//

package COM.ibm.eannounce.objects;
import java.lang.String;

public class TimeRuleException extends EANBusinessRuleException {

  	public TimeRuleException() {
  		super();
  	}

  	/**
  	* Constructs a <code>TimeRuleException</code> with the specified detail message
  	*/
  	public TimeRuleException(String s) {
  		super(s);
  	}

	private static boolean isValid(int _h1, int _h2, int _m1, int _m2) {
		if (_h1 > 2) {
			return false;
		} else if (_h1 == 2 && _h2 > 3) {
			return false;
		} else if (_m1 > 5) {
			return false;
		}
		return true;
	}

   	public  void validate(EANAttribute _eanAttr, String _strValue) {
//		System.out.println("TimeRuleException validate(EANAttribute, " + _strValue + ")");
  		EANMetaAttribute eanMA = _eanAttr.getMetaAttribute();
  		String strValue = _strValue;

		clearLists();
		if (eanMA.isTime() && strValue != null) {
			char[] cArray = strValue.toCharArray();
			int ii = cArray.length;
			if (ii <= 0) return;
			if (ii > 5) {
				add(_eanAttr, strValue + ": #1 Wrong time format (hh:mm)");
				return;
			}

			for (int i = 0; i < ii; i++) {
				char c = cArray[i];
				if (i == 2) {
					if (c != ':') {
						add(_eanAttr, strValue + ": #2 Wrong time format (hh:mm)");
						return;
					}
				} else {
					if (!Character.isDigit(c)){
						add(_eanAttr, strValue + ": #3 Wrong time format (hh:mm)");
						return;
					}
				}
			}


			int h1 = 0;
			int h2 = 0;
			int m1 = 0;
			int m2 = 0;
			if (ii > 0)
				h1 = Integer.valueOf(String.valueOf(cArray[0])).intValue();
			if (ii > 1)
				h2 = Integer.valueOf(String.valueOf(cArray[1])).intValue();
			if (ii > 3)
				m1 = Integer.valueOf(String.valueOf(cArray[3])).intValue();
			if (ii > 4)
				m2 = Integer.valueOf(String.valueOf(cArray[4])).intValue();

			if (! isValid(h1,h2,m1,m2)) {
				add(_eanAttr, strValue + ": #4 Wrong time format (hh:mm)");
				return;
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
    return new String("$Id: TimeRuleException.java,v 1.4 2008/09/08 17:44:36 wendy Exp $");
  }

}
