//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CharIncDec.java,v $
// Revision 1.7  2001/08/22 16:52:51  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 15:39:43  roger
// Misc clean up
//
// Revision 1.5  2001/03/21 00:01:06  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:19  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * Used to increment or decrement a character according to certain rules (scheme).
 * The scheme can be any of: 0-9; A-Z; 0-9A-Z; MNW; KPT; !YZ, YZ, or K.
 * K is a special scheme which means that the current value is (K)onstant.
 * Any attempt to increment/decrement a K scheme object will return a borrow/carry true setting
 * which will allow the operation to continue.
 * The default scheme when not specified is 0-9.
 * @version @date
 */
public final class CharIncDec {

  // Instance variables
  private char m_chCharacter;
  private String m_strScheme;
  private boolean m_bBorrowCarry;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String args[]) {

    System.out.println("Test #0");

    CharIncDec ch0 = new CharIncDec();

    System.out.println(ch0);
    System.out.println("Test #1");

    CharIncDec ch1 = new CharIncDec('8');

    System.out.println(ch1);
    ch1.Increment();
    System.out.println(ch1);
    ch1.Increment();
    System.out.println(ch1);
    ch1.Decrement();
    System.out.println(ch1);
    ch1.Decrement();
    System.out.println(ch1);
    System.out.println("Test #2");

    CharIncDec ch2 = new CharIncDec('P', "KPT");

    System.out.println(ch2);
    ch2.Increment();
    System.out.println(ch2);
    ch2.Increment();
    System.out.println(ch2);
    ch2.Decrement();
    System.out.println(ch2);
    ch2.Decrement();
    System.out.println(ch2);
    System.out.println("Test #3");

    CharIncDec ch3 = new CharIncDec('Z', "0-9A-Z");

    for (int i = 0; i < 58; i++) {
      ch3.Increment();
      System.out.println(ch3);
    }

    for (int i = 0; i < 58; i++) {
      ch3.Decrement();
      System.out.println(ch3);
    }

    System.out.println("Test #4");

    CharIncDec ch4 = new CharIncDec('Z', "K");

    System.out.println(ch4);
    ch4.Increment();
    System.out.println(ch4);
    ch4.Increment();
    System.out.println(ch4);
    ch4.Decrement();
    System.out.println(ch4);
    ch4.Decrement();
    System.out.println(ch4);
  }

/**
 * Construct a <code>CharIncDec</code> object
 */
  public CharIncDec() {
    this(' ', "0-9");
  }

/**
 * Construct a <code>CharIncDec</code> object
 */
  public CharIncDec(char ch) {
    this(ch, "0-9");
  }

/**
 * Construct a <code>CharIncDec</code> object
 */
  public CharIncDec(char ch, String strScheme) {
    m_bBorrowCarry = false;
    m_chCharacter = ch;
    m_strScheme = strScheme;
  }

/**
 * Increment the character according to the scheme
 */
  public final void Increment() {
    m_bBorrowCarry = false;
    this.IncDec(+1);
  }

/**
 * Decrement the character according to the scheme
 */
  public final void Decrement() {
    m_bBorrowCarry = false;
    this.IncDec(-1);
  }

/**
 * Determine if the prior Increment or Decrement call resulted in a borrow condition
 */
  public final boolean getBorrowCarry() {
    return m_bBorrowCarry;
  }

/**
 * Print the value of the <code>CharIncDec</code> object
 */
  public final String toString() {
    return (new Character(m_chCharacter)).toString();
  }

/**
 * Adjust the character according to the <code>CharIncDec</code> object's scheme and offset [+1 or -1]
 */
  protected final void IncDec(int iOffset) {
    int iNewOffset = iOffset / Math.abs(iOffset);
    m_bBorrowCarry = false;

    if (m_strScheme.equalsIgnoreCase("0-9")) {
      NumericIncDec(iNewOffset);
    } else if (m_strScheme.equalsIgnoreCase("A-Z")) {
      AlphaIncDec(iNewOffset);
    } else if (m_strScheme.equalsIgnoreCase("0-9A-Z")) {
      NumericAlphaIncDec(iNewOffset);
    } else if (m_strScheme.equalsIgnoreCase("MNW")) {
      MNWIncDec(iNewOffset);
    } else if (m_strScheme.equalsIgnoreCase("KPT")) {
      KPTIncDec(iNewOffset);
    } else if (m_strScheme.equalsIgnoreCase("YZ")) {
      YZIncDec(iNewOffset);
    } else if (m_strScheme.equalsIgnoreCase("!YZ")) {
      NonYZIncDec(iNewOffset);
    } else if (m_strScheme.equalsIgnoreCase("K")) {
      ConstantIncDec(iNewOffset);
    } else {
      D.ebug(D.EBUG_SPEW, "scheme " + m_strScheme + " is not supported in IncDec method");
    }
  }

/**
 * Set the character field as specified
 */
  protected final void setChar(char ch) {
    m_chCharacter = ch;
  }

/**
 * Get the character field as specified
 */
  protected final char getChar() {
    return m_chCharacter;
  }

/**
 * Set the scheme field as specified
 */
  protected final void setScheme(String strScheme) {
    m_strScheme = new String(strScheme);
  }

/**
 * Get the scheme field as specified
 */
  protected final String getScheme() {
    return m_strScheme;
  }

/**
 * Adjust the character according to the 0-9 scheme and offset
 */
  private final void NumericIncDec(int iOffset) {
    int ich = (int) m_chCharacter;
    ich += iOffset;

    if (ich > (int) '9') {
      ich = (int) '0';
      m_bBorrowCarry = true;
    } else if (ich < (int) '0') {
      ich = (int) '9';
      m_bBorrowCarry = true;
    }
    m_chCharacter = (char) ich;
  }

/**
 * Adjust the character according to the A-Z scheme and offset
 */
  private final void AlphaIncDec(int iOffset) {
    int ich = (int) m_chCharacter;
    ich += iOffset;

    if (ich > (int) 'Z') {
      ich = (int) 'A';
      m_bBorrowCarry = true;
    } else if (ich < (int) 'A') {
      ich = (int) 'Z';
      m_bBorrowCarry = true;
    }
    m_chCharacter = (char) ich;
  }

/**
 * Adjust the character according to the 0-9A-Z scheme and offset
 */
  private final void NumericAlphaIncDec(int iOffset) {
    int ich = (int) m_chCharacter;
    ich += iOffset;

    if (iOffset > 0) {
      // Increment
      if (ich == ((int) '9' + 1)) {
        ich = (int) 'A';
      }
      if (ich == ((int) 'Z' + 1)) {
        ich = (int) '0';
        m_bBorrowCarry = true;
      }
    } else if (iOffset < 0) {
      // Decrement
      if (ich == ((int) 'A' - 1)) {
        ich = (int) '9';
      }
      if (ich == ((int) '0' - 1)) {
        ich = (int) 'Z';
        m_bBorrowCarry = true;
      }
    }
    m_chCharacter = (char) ich;
  }

/**
 * Adjust the character according to the MNW scheme and offset
 */
  private final void MNWIncDec(int iOffset) {
    if (iOffset > 0) {
      // Increment
      if (m_chCharacter == 'M') {
        m_chCharacter = 'N';
      } else if (m_chCharacter == 'N') {
        m_chCharacter = 'W';
      } else if (m_chCharacter == 'W') {
        m_chCharacter = 'M';
        m_bBorrowCarry = true;
      }
    } else if (iOffset < 0) {
      // Decrement
      if (m_chCharacter == 'M') {
        m_chCharacter = 'W';
        m_bBorrowCarry = true;
      } else if (m_chCharacter == 'N') {
        m_chCharacter = 'M';
      } else if (m_chCharacter == 'W') {
        m_chCharacter = 'N';
      }
    }
  }

/**
 * Adjust the character according to the KPT scheme and offset
 */
  private final void KPTIncDec(int iOffset) {
    if (iOffset > 0) {
      // Increment
      if (m_chCharacter == 'K') {
        m_chCharacter = 'P';
      } else if (m_chCharacter == 'P') {
        m_chCharacter = 'T';
      } else if (m_chCharacter == 'T') {
        m_chCharacter = 'K';
        m_bBorrowCarry = true;
      }
    } else if (iOffset < 0) {
      // Decrement
      if (m_chCharacter == 'K') {
        m_chCharacter = 'T';
        m_bBorrowCarry = true;
      } else if (m_chCharacter == 'P') {
        m_chCharacter = 'K';
      } else if (m_chCharacter == 'T') {
        m_chCharacter = 'P';
      }
    }
  }

/**
 * Adjust the character according to the YZ scheme and offset
 */
  private final void YZIncDec(int iOffset) {
    if (iOffset > 0) {
      // Increment
      if (m_chCharacter == 'Y') {
        m_chCharacter = 'Z';
      } else if (m_chCharacter == 'Z') {
        m_chCharacter = 'Y';
        m_bBorrowCarry = true;
      }
    } else if (iOffset < 0) {
      // Decrement
      if (m_chCharacter == 'Y') {
        m_chCharacter = 'Z';
        m_bBorrowCarry = true;
      } else if (m_chCharacter == 'Z') {
        m_chCharacter = 'Y';
      }
    }
  }

/**
 * Adjust the character according to the !YZ scheme and offset
 */
  private final void NonYZIncDec(int iOffset) {
    if (iOffset > 0) {
      // Increment
      if (m_chCharacter == 'X') {
        m_chCharacter = '0';
        m_bBorrowCarry = true;
      }
    } else if (iOffset < 0) {
      // Decrement
      if (m_chCharacter == '0') {
        m_chCharacter = 'X';
        m_bBorrowCarry = true;
      }
    }
  }

/**
 * Adjust the character according to the K scheme and offset
 */
  private final void ConstantIncDec(int iOffset) {
    m_bBorrowCarry = true;
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: CharIncDec.java,v 1.7 2001/08/22 16:52:51 roger Exp $");
  }
}
