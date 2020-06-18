//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Unicode.java,v $
// Revision 1.7  2001/08/22 16:53:04  roger
// Removed author RM
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
 * A class for conditional output of debug/trace statements to the <code>System.err</code> and <code>System.out</code> print streams
 * @version @date
 */
public final class Unicode {

  // Class constants
  // Class variables

/**
 * Don't let anyone instantiate this class.
 */
  private Unicode() {
  }

/**
 * A unicode capable trim
 */
  public final static String trim(String str) {
    int len = str.length();
    int st = 0;
    int off = 0;
    char[] val = str.toCharArray();

    while ((st < len) && (Character.isSpaceChar(val[off + st]))) {
      st++;
    }

    while ((st < len) && (Character.isSpaceChar(val[off + len - 1]))) {
      len--;
    }

    return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
  }

/**
 * A unicode capable ltrim
 */
  public final static String ltrim(String str) {
    int len = str.length();
    int st = 0;
    int off = 0;
    char[] val = str.toCharArray();

    while ((st < len) && (Character.isSpaceChar(val[off + st]))) {
      st++;
    }

    return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
  }

/**
 * A unicode capable rtrim
 */
  public final static String rtrim(String str) {
    int len = str.length();
    int st = 0;
    int off = 0;
    char[] val = str.toCharArray();

    while ((st < len) && (Character.isSpaceChar(val[off + len - 1]))) {
      len--;
    }

    return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return new String("$Id: Unicode.java,v 1.7 2001/08/22 16:53:04 roger Exp $");
  }
}
