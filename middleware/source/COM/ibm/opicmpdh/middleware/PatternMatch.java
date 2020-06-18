//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PatternMatch.java,v $
// Revision 1.7  2001/08/22 16:53:00  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:09  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:23  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * A quick and dirty pattern match routine (no support for closures)
 * The meta characters for the pattern are as follows:
 * '#' matches 0-9 numeric
 * '^' matches A-Z uppercase
 * '?' matches 0-9, and A-Z uppercase
 * '=' matches M, N, W only uppercase
 * '!' matches K, P, T only uppercase
 * '~' matches Y, Z only uppercase
 * '@' matches any 0-9, A-Z excluding Y, Z
 * @version @date
 */
public final class PatternMatch {

  // Instance variables
  private String strFailureMessage = null;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String args[]) {
    PatternMatch pm = new PatternMatch();
    String strPattern01 = new String("#^?");
    String strTest01 = new String("0A9");
    String strPattern02 = new String("#^?");
    String strTest02 = new String("AA9");
    String strPattern03 = new String("#^?");
    String strTest03 = new String("099");
    String strPattern04 = new String("#^?");
    String strTest04 = new String("0A!");
    String strPattern05 = new String("#^?");
    String strTest05 = new String("%%%");
    String strPattern06 = new String("#");
    String strTest06 = new String("99999");
    String strPattern07 = new String("???");
    String strTest07 = new String("%%%");
    String strPattern08 = new String("^^^");
    String strTest08 = new String("aaa");
    String strPattern09 = new String("^^^");
    String strTest09 = new String("AAA");

    System.out.println(pm.isMatch(strPattern01, strTest01) + " s/b true " + pm.getMessage() + " pattern: " + strPattern01 + " test: " + strTest01);
    System.out.println(pm.isMatch(strPattern02, strTest02) + " s/b false " + pm.getMessage() + " pattern: " + strPattern02 + " test: " + strTest02);
    System.out.println(pm.isMatch(strPattern03, strTest03) + " s/b false " + pm.getMessage() + " pattern: " + strPattern03 + " test: " + strTest03);
    System.out.println(pm.isMatch(strPattern04, strTest04) + " s/b false " + pm.getMessage() + " pattern: " + strPattern04 + " test: " + strTest04);
    System.out.println(pm.isMatch(strPattern05, strTest05) + " s/b false " + pm.getMessage() + " pattern: " + strPattern05 + " test: " + strTest05);
    System.out.println(pm.isMatch(strPattern06, strTest06) + " s/b false " + pm.getMessage() + " pattern: " + strPattern06 + " test: " + strTest06);
    System.out.println(pm.isMatch(strPattern07, strTest07) + " s/b false " + pm.getMessage() + " pattern: " + strPattern07 + " test: " + strTest07);
    System.out.println(pm.isMatch(strPattern08, strTest08) + " s/b false " + pm.getMessage() + " pattern: " + strPattern08 + " test: " + strTest08);
    System.out.println(pm.isMatch(strPattern09, strTest09) + " s/b true " + pm.getMessage() + " pattern: " + strPattern09 + " test: " + strTest09);
  }

/**
 * The basic constructor
 */
  public PatternMatch() {
    strFailureMessage = new String("");
  }

/**
 * Retrieve the pattern match failure message (if any)
 */
  public final String getMessage() {
    return strFailureMessage;
  }

/**
 * Does the test string match the pattern?
 */
  public final boolean isMatch(String strPattern, String strTest) {
    boolean bIsMatch = true;
    char chTest;
    char chPattern;

    strFailureMessage = new String("OK");

    for (int i = 0; ((i < strTest.length()) && (i < strPattern.length()) && bIsMatch); i++) {
      chTest = strTest.charAt(i);
      chPattern = strPattern.charAt(i);

      if (chPattern == '#') {
        if (!Character.isDigit(chTest)) {
          bIsMatch = false;
          strFailureMessage = new String("digit must be in position " + (i + 1));
        }
      } else if (chPattern == '^') {
        if (!Character.isUpperCase(chTest)) {
          bIsMatch = false;
          strFailureMessage = new String("upper case letter must be in position " + (i + 1));
        }
      } else if (chPattern == '?') {
        if (!(Character.isDigit(chTest) || Character.isUpperCase(chTest))) {
          bIsMatch = false;
          strFailureMessage = new String("upper case letter or digit must be in position " + (i + 1));
        }
      } else if (chPattern == '=') {
        if (!((chTest == 'M') || (chTest == 'N') || (chTest == 'W'))) {
          bIsMatch = false;
          strFailureMessage = new String("M, N, or W must be in position " + (i + 1));
        }
      } else if (chPattern == '!') {
        if (!((chTest == 'K') || (chTest == 'P') || (chTest == 'T'))) {
          bIsMatch = false;
          strFailureMessage = new String("K, P, or T must be in position " + (i + 1));
        }
      } else if (chPattern == '~') {
        if (!((chTest == 'Y') || (chTest == 'Z'))) {
          bIsMatch = false;
          strFailureMessage = new String("Y, or Z must be in position " + (i + 1));
        }
      } else if (chPattern == '@') {
        if (!(Character.isUpperCase(chTest) || Character.isDigit(chTest)) && ((chTest != 'Y') && (chTest != 'Z'))) {
          bIsMatch = false;
          strFailureMessage = new String("upper case letter (excluding Y, Z) or digit must be in position " + (i + 1));
        }
      } else if (chPattern != chTest) {
        bIsMatch = false;
        strFailureMessage = new String(chPattern + " must be in position " + (i + 1));
      }
      D.ebug(D.EBUG_SPEW, "compare test char '" + chTest + "' to pattern char '" + chPattern + "' (" + i + ") match = " + bIsMatch);
    }

    // If no other error has been detected, check the lengths
    if ((strPattern.length() != strTest.length()) && (strFailureMessage.length() == 0)) {
      bIsMatch = false;
      strFailureMessage = new String("incorrect length");
    }
    return bIsMatch;
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: PatternMatch.java,v 1.7 2001/08/22 16:53:00 roger Exp $");
  }
}
