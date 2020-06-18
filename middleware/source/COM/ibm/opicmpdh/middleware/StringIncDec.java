//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: StringIncDec.java,v $
// Revision 1.10  2008/01/31 22:54:59  wendy
// Cleanup RSA warnings
//
// Revision 1.9  2002/03/06 17:20:57  roger
// This guy was setting the D.ebugLevel - not nice!
//
// Revision 1.8  2001/09/12 17:52:14  roger
// Profile changes
//
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
 * Used to increment or decrement a string according to rules (scheme)
 * of the <code>CharIncDec</code> objects which comprise it.
 * @version @date
 */
public final class StringIncDec {

  // Instance variables
  CharIncDec[] m_achid;
  // unused or unnecessary?
  int m_iLeftLimit;
  int m_iRightLimit;
  String m_strSeedValue = null;// saved for future comparisons

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String args[]) {
    // PSGOF
    CharIncDec[] acidPSGOF = new CharIncDec[7];

    acidPSGOF[0] = new CharIncDec('A', "K");
    acidPSGOF[1] = new CharIncDec('A', "K");
    acidPSGOF[2] = new CharIncDec('A', "K");
    acidPSGOF[3] = new CharIncDec('A', "K");
    acidPSGOF[4] = new CharIncDec('A', "K");
    acidPSGOF[5] = new CharIncDec('A', "K");
    acidPSGOF[6] = new CharIncDec('A', "K");

    StringIncDec sidPSGOF = new StringIncDec(acidPSGOF);

    // PSGOFSYSTEM
    CharIncDec[] acidPSGOFSYSTEM = new CharIncDec[7];

    acidPSGOFSYSTEM[0] = new CharIncDec('A', "K");
    acidPSGOFSYSTEM[1] = new CharIncDec('A', "K");
    acidPSGOFSYSTEM[2] = new CharIncDec('A', "K");
    acidPSGOFSYSTEM[3] = new CharIncDec('A', "K");
    acidPSGOFSYSTEM[4] = new CharIncDec('0', "!YZ");
    acidPSGOFSYSTEM[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGOFSYSTEM[6] = new CharIncDec('0', "0-9A-Z");

    StringIncDec sidPSGOFSYSTEM = new StringIncDec(acidPSGOFSYSTEM);

    // PSGCVOF
    CharIncDec[] acidPSGCVOF = new CharIncDec[7];

    acidPSGCVOF[0] = new CharIncDec('0', "0-9");
    acidPSGCVOF[1] = new CharIncDec('0', "0-9");
    acidPSGCVOF[2] = new CharIncDec('0', "0-9");
    acidPSGCVOF[3] = new CharIncDec('0', "0-9");
    acidPSGCVOF[4] = new CharIncDec('C', "K");
    acidPSGCVOF[5] = new CharIncDec('T', "K");
    acidPSGCVOF[6] = new CharIncDec('O', "K");

    //StringIncDec sidCVOF = new StringIncDec(acidPSGCVOF);

    // PSGPCSL
    CharIncDec[] acidPSGPCSL = new CharIncDec[7];

    acidPSGPCSL[0] = new CharIncDec('C', "K");
    acidPSGPCSL[1] = new CharIncDec('V', "K");
    acidPSGPCSL[2] = new CharIncDec('S', "K");
    acidPSGPCSL[3] = new CharIncDec('L', "K");
    acidPSGPCSL[4] = new CharIncDec('Y', "YZ");
    acidPSGPCSL[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGPCSL[6] = new CharIncDec('0', "0-9A-Z");

    StringIncDec sidPSGPCSL = new StringIncDec(acidPSGPCSL);

    // PSGCB
    CharIncDec[] acidPSGCB = new CharIncDec[7];

    acidPSGCB[0] = new CharIncDec('M', "MNW");
    acidPSGCB[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[4] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[6] = new CharIncDec('0', "0-9A-Z");

    //StringIncDec sidPSGCB = new StringIncDec(acidPSGCB);

    // PSGROF
    CharIncDec[] acidPSGROF = new CharIncDec[7];

    acidPSGROF[0] = new CharIncDec('K', "KPT");
    acidPSGROF[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGROF[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGROF[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGROF[4] = new CharIncDec('0', "0-9A-Z");
    acidPSGROF[5] = new CharIncDec('x', "K");
    acidPSGROF[6] = new CharIncDec('x', "K");

    StringIncDec sidPSGROF = new StringIncDec(acidPSGROF);

    // PSGSBB
    CharIncDec[] acidPSGSBB = new CharIncDec[7];

    acidPSGSBB[0] = new CharIncDec('A', "K");
    acidPSGSBB[1] = new CharIncDec('A', "K");
    acidPSGSBB[2] = new CharIncDec('A', "K");
    acidPSGSBB[3] = new CharIncDec('A', "K");
    acidPSGSBB[4] = new CharIncDec('A', "K");
    acidPSGSBB[5] = new CharIncDec('A', "K");
    acidPSGSBB[6] = new CharIncDec('A', "K");

    StringIncDec sidPSGSBB = new StringIncDec(acidPSGSBB);

    // PSGCVSL
    CharIncDec[] acidPSGCVSL = new CharIncDec[7];

    acidPSGCVSL[0] = new CharIncDec('C', "K");
    acidPSGCVSL[1] = new CharIncDec('V', "K");
    acidPSGCVSL[2] = new CharIncDec('O', "K");
    acidPSGCVSL[3] = new CharIncDec('F', "K");
    acidPSGCVSL[4] = new CharIncDec('Y', "YZ");
    acidPSGCVSL[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGCVSL[6] = new CharIncDec('0', "0-9A-Z");

    StringIncDec sidPSGCVSL = new StringIncDec(acidPSGCVSL);

    // PSGCCSL
    CharIncDec[] acidPSGCCSL = new CharIncDec[7];

    acidPSGCCSL[0] = new CharIncDec('A', "K");
    acidPSGCCSL[1] = new CharIncDec('A', "K");
    acidPSGCCSL[2] = new CharIncDec('A', "K");
    acidPSGCCSL[3] = new CharIncDec('A', "K");
    acidPSGCCSL[4] = new CharIncDec('A', "K");
    acidPSGCCSL[5] = new CharIncDec('A', "K");
    acidPSGCCSL[6] = new CharIncDec('A', "K");

    //StringIncDec sidPSGCCSL = new StringIncDec(acidPSGCCSL);

    // PSGSOL
    CharIncDec[] acidPSGSOL = new CharIncDec[7];

    acidPSGSOL[0] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[4] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[6] = new CharIncDec('0', "0-9A-Z");

    //StringIncDec sidPSGSOL = new StringIncDec(acidPSGSOL);

    // PSGCSOL
    CharIncDec[] acidPSGCSOL = new CharIncDec[7];

    acidPSGCSOL[0] = new CharIncDec('0', "K");
    acidPSGCSOL[1] = new CharIncDec('0', "K");
    acidPSGCSOL[2] = new CharIncDec('0', "K");
    acidPSGCSOL[3] = new CharIncDec('0', "K");
    acidPSGCSOL[4] = new CharIncDec('0', "K");
    acidPSGCSOL[5] = new CharIncDec('0', "K");
    acidPSGCSOL[6] = new CharIncDec('0', "K");

    StringIncDec sidPSGCSOL = new StringIncDec(acidPSGCSOL);

    // Test
    CharIncDec[] ach = new CharIncDec[6];

    ach[0] = new CharIncDec('2', "0-9");
    ach[1] = new CharIncDec('6', "0-9");
    ach[2] = new CharIncDec('4', "0-9");
    ach[3] = new CharIncDec('7', "0-9");
    ach[4] = new CharIncDec('M', "MNW");
    ach[5] = new CharIncDec('9', "0-9");

    System.out.println("initial " + sidPSGROF.getValue());
    sidPSGROF.Decrement();
    System.out.println("after dec " + sidPSGROF.getValue());
    sidPSGROF.Increment();
    System.out.println("after inc " + sidPSGROF.getValue());
    System.exit(0);

    // Create a stringincdec using an array of charincdec and a variable left pos, variable right pos
    StringIncDec strid = new StringIncDec(ach);

    System.out.println(strid);

    for (int i = 0; i < 15; i++) {
      System.out.println(strid.getValue());
      strid.Increment();
    }

    strid.setValue("2999W0");
    System.out.println(strid);

    for (int i = 0; i < 15; i++) {
      System.out.println(strid.getValue());
      strid.Increment();
    }
  }

/**
 * Construct a <code>StringIncDec</code> object
 */
  public StringIncDec(CharIncDec[] achid) {
    this(achid, 0, achid.length - 1);
  }

/**
 * Construct a <code>StringIncDec</code> object
 */
  public StringIncDec(CharIncDec[] achid, int iVariableLeftLimit, int iVariableRightLimit) {
    m_achid = achid;
    m_iLeftLimit = iVariableLeftLimit;
    m_iRightLimit = iVariableRightLimit;
    m_strSeedValue = new String("");
  }

/**
 * Increment the string
 */
  public final void Increment() {
    this.IncDec(+1);
  }

/**
 * Decrement the string
 */
  public final void Decrement() {
    this.IncDec(-1);
  }

/**
 * Adjust the string
 */
  protected final void IncDec(int iOffset) {

    boolean bAgain = true;
    int iPosition = m_iRightLimit;

    while (bAgain) {
      bAgain = false;

      D.ebug(D.EBUG_SPEW, "incdec position (" + iPosition + ") contains " + m_achid[iPosition]);
      m_achid[iPosition].IncDec(iOffset);
      D.ebug(D.EBUG_SPEW, "incdec position (" + iPosition + ") NOW contains " + m_achid[iPosition]);
      D.ebug(D.EBUG_SPEW, "borrow/carry = " + m_achid[iPosition].getBorrowCarry());

      if (m_achid[iPosition].getBorrowCarry() == true) {
        D.ebug(D.EBUG_SPEW, "do it again");

        bAgain = true;

        --iPosition;
      }

      // If i go to the left of leftmost position, this is a problem
      if (iPosition < m_iLeftLimit) {
        D.ebug(D.EBUG_SPEW, "IncDec attempted to borrow/carry from leftmost position");

        bAgain = false;
      }
    }
  }

/**
 * Set the seed value of the <code>StringIncDec</code> object
 */
  public final void setSeedValue(String strSeedValue) {
    m_strSeedValue = new String(strSeedValue);

    this.setValue(strSeedValue);
  }

/**
 * Get the seed value of the <code>StringIncDec</code> object
 */
  public final String getSeedValue() {
    return this.m_strSeedValue;
  }

/**
 * Set the value of the <code>StringIncDec</code> object
 */
  public final void setValue(String strStringValue) {
    // need to ensure sizes match exactly
    for (int i = 0; i < strStringValue.length(); i++) {
      // truncate value if necessary - this is NOT GOOD
      if (i <= m_achid.length) {
        m_achid[i].setChar(strStringValue.charAt(i));
      }
    }
  }

/**
 * Print the value left limit of the <code>StringIncDec</code> object
 */
  protected final void setLeftLimit(int _iLeftLimit) {
    m_iLeftLimit = _iLeftLimit;
  }

/**
 * Print the value right limit of the <code>StringIncDec</code> object
 */
  protected final void setRightLimit(int _iRightLimit) {
    m_iRightLimit = _iRightLimit;
  }

/**
 * Print the value of the <code>StringIncDec</code> object
 */
  public final String toString() {
    StringBuffer strbHold = new StringBuffer();

    for (int i = 0; i < m_achid.length; i++) {
      strbHold.append(m_achid[i].getChar());
    }
    return new String("Value:" + strbHold + "\nLeft Limit:" + m_iLeftLimit + "\nRight Limit:" + m_iRightLimit);
  }

/**
 * Print the value of the <code>StringIncDec</code> object
 */
  public final String getValue() {
    StringBuffer strbHold = new StringBuffer();

    for (int i = 0; i < m_achid.length; i++) {
      strbHold.append(m_achid[i].getChar());
    }
    return new String(strbHold);
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: StringIncDec.java,v 1.10 2008/01/31 22:54:59 wendy Exp $");
  }
}
