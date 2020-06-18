//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Validate.java,v $
// Revision 1.8  2001/08/22 16:53:04  roger
// Removed author RM
//
// Revision 1.7  2001/07/24 02:53:18  roger
// Only ISO date format is now allowed
//
// Revision 1.6  2001/03/26 16:33:23  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:10  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:26  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * A class useful for validation of data
 * @version @date
 */
public final class Validate {

/**
 * Don't let anyone instantiate this class.
 */
  private Validate() {}

/**
 * Is this a valid iso date?
 */
  public final static boolean isoDate(String strTest) {
    StringBuffer strbSimplified = new StringBuffer();
    String strSimplified = null;

    // Eventually drop down to just pattern1 and pattern3 being legal
    String strPattern1 = new String("####-##-##-##.##.##.######");
//    String strPattern2 = new String("####-##-## ##:##:##.######");
//    String strPattern3 = new String("####-##-## ##:##:##");
    boolean res1 = true;
//    boolean res2 = true;
//    boolean res3 = true;

    for (int i = 0; i < strTest.length(); i++) {
      char chTest = strTest.charAt(i);

      if (Character.isDigit(chTest)) {
        strbSimplified.append("#");
      } else {
        strbSimplified.append(chTest);
      }
    }

    strSimplified = new String(strbSimplified);
    res1 = strPattern1.equals(strSimplified);
//    res2 = strPattern2.equals(strSimplified);
//    res3 = strPattern3.equals(strSimplified);

    D.ebug(D.EBUG_SPEW, "compare '" + strTest + "' to '" + strPattern1 + "' (result '" + strSimplified + "' : " + res1 + ")");
//    D.ebug(D.EBUG_SPEW, "compare '" + strTest + "' to '" + strPattern2 + "' (result '" + strSimplified + "' : " + res2 + ")");
//    D.ebug(D.EBUG_SPEW, "compare '" + strTest + "' to '" + strPattern3 + "' (result '" + strSimplified + "' : " + res3 + ")");

//    return ((res1 == false) && (res2 == false) && (res3 == false)) ? false : true;
    return res1;
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return new String("$Id: Validate.java,v 1.8 2001/08/22 16:53:04 roger Exp $");
  }
}
