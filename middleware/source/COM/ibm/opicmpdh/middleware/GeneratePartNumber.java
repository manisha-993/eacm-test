//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: GeneratePartNumber.java,v $
// Revision 1.8  2001/08/22 16:52:56  roger
// Removed author RM
//
// Revision 1.7  2001/03/26 16:33:21  roger
// Misc formatting clean up
//
// Revision 1.6  2001/03/21 00:01:08  roger
// Implement java class file branding in getVersion method
//
// Revision 1.5  2001/03/16 15:52:20  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * Used to get the next available part number for a given entity type
 * @version @date
 */
public final class GeneratePartNumber {

  // Instance variables
  // PSGOF
  private CharIncDec[] acidPSGOF = new CharIncDec[7];
  private StringIncDec sidPSGOF;

  // PSGOFSYSTEM
  private CharIncDec[] acidPSGOFSYSTEM = new CharIncDec[7];
  private StringIncDec sidPSGOFSYSTEM;

  // PSGCVOF
  private CharIncDec[] acidPSGCVOF = new CharIncDec[7];
  private StringIncDec sidPSGCVOF;

  // PSGPCSL
  private CharIncDec[] acidPSGPCSL = new CharIncDec[7];
  private StringIncDec sidPSGPCSL;

  // PSGCB
  private CharIncDec[] acidPSGCB = new CharIncDec[7];
  private StringIncDec sidPSGCB;

  // PSGCPSL
  private CharIncDec[] acidPSGCPSL = new CharIncDec[7];
  private StringIncDec sidPSGCPSL;

  // PSGROF
  private CharIncDec[] acidPSGROF = new CharIncDec[7];
  private StringIncDec sidPSGROF;

  // PSGSBB
  private CharIncDec[] acidPSGSBB = new CharIncDec[7];
  private StringIncDec sidPSGSBB;

  // PSGCVSL
  private CharIncDec[] acidPSGCVSL = new CharIncDec[7];
  private StringIncDec sidPSGCVSL;

  // PSGCCSL
  private CharIncDec[] acidPSGCCSL = new CharIncDec[7];
  private StringIncDec sidPSGCCSL;

  // PSGSOL
  private CharIncDec[] acidPSGSOL = new CharIncDec[7];
  private StringIncDec sidPSGSOL;

  // PSGCSOL
  private CharIncDec[] acidPSGCSOL = new CharIncDec[7];
  private StringIncDec sidPSGCSOL;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String args[]) {
    GeneratePartNumber gpn = new GeneratePartNumber();

    try {
      for (int i = 0; i < 22; i++) {
        System.out.println(gpn.getNextPartNumber("PSGOF", "SYSTEM", "0000XZZ"));
      }
    } catch (Exception x) {}
  }

/**
 * Create the <code>GeneratePartNumber</code> object
 */
  public GeneratePartNumber() {
    // PSGOF
    acidPSGOF[0] = new CharIncDec('0', "0-9A-Z");
    acidPSGOF[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGOF[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGOF[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGOF[4] = new CharIncDec('0', "0-9A-Z");
    acidPSGOF[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGOF[6] = new CharIncDec('0', "0-9A-Z");
    sidPSGOF = new StringIncDec(acidPSGOF);

    // PSGOFSYSTEM
    acidPSGOFSYSTEM[0] = new CharIncDec('0', "0-9A-Z");
    acidPSGOFSYSTEM[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGOFSYSTEM[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGOFSYSTEM[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGOFSYSTEM[4] = new CharIncDec('0', "!YZ");
    acidPSGOFSYSTEM[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGOFSYSTEM[6] = new CharIncDec('0', "0-9A-Z");
    sidPSGOFSYSTEM = new StringIncDec(acidPSGOFSYSTEM);

    // PSGCVOF
    acidPSGCVOF[0] = new CharIncDec('0', "0-9");
    acidPSGCVOF[1] = new CharIncDec('0', "0-9");
    acidPSGCVOF[2] = new CharIncDec('0', "0-9");
    acidPSGCVOF[3] = new CharIncDec('0', "0-9");
    acidPSGCVOF[4] = new CharIncDec('C', "K");
    acidPSGCVOF[5] = new CharIncDec('T', "K");
    acidPSGCVOF[6] = new CharIncDec('O', "K");
    sidPSGCVOF = new StringIncDec(acidPSGCVOF);

    // PSGPCSL
    acidPSGPCSL[0] = new CharIncDec('0', "0-9");
    acidPSGPCSL[1] = new CharIncDec('0', "0-9");
    acidPSGPCSL[2] = new CharIncDec('0', "0-9");
    acidPSGPCSL[3] = new CharIncDec('0', "0-9");
    acidPSGPCSL[4] = new CharIncDec('Y', "YZ");
    acidPSGPCSL[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGPCSL[6] = new CharIncDec('0', "0-9A-Z");
    sidPSGPCSL = new StringIncDec(acidPSGPCSL);

    // PSGCB
    acidPSGCB[0] = new CharIncDec('M', "MNW");
    acidPSGCB[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[4] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGCB[6] = new CharIncDec('0', "0-9A-Z");
    sidPSGCB = new StringIncDec(acidPSGCB);

    // PSGCPSL
    acidPSGCPSL[0] = new CharIncDec('0', "0-9");
    acidPSGCPSL[1] = new CharIncDec('0', "0-9");
    acidPSGCPSL[2] = new CharIncDec('0', "0-9");
    acidPSGCPSL[3] = new CharIncDec('0', "0-9");
    acidPSGCPSL[4] = new CharIncDec('Y', "YZ");
    acidPSGCPSL[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGCPSL[6] = new CharIncDec('0', "0-9A-Z");
    sidPSGCPSL = new StringIncDec(acidPSGCPSL);

    // PSGROF
    acidPSGROF[0] = new CharIncDec('K', "KPT");
    acidPSGROF[1] = new CharIncDec('A', "0-9A-Z");
    acidPSGROF[2] = new CharIncDec('A', "0-9A-Z");
    acidPSGROF[3] = new CharIncDec('A', "0-9A-Z");
    acidPSGROF[4] = new CharIncDec('A', "0-9A-Z");
    acidPSGROF[5] = new CharIncDec('x', "K");
    acidPSGROF[6] = new CharIncDec('x', "K");
    sidPSGROF = new StringIncDec(acidPSGROF);

    // PSGSBB
    acidPSGSBB[0] = new CharIncDec('0', "0-9A-Z");
    acidPSGSBB[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGSBB[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGSBB[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGSBB[4] = new CharIncDec('0', "0-9A-Z");
    acidPSGSBB[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGSBB[6] = new CharIncDec('0', "0-9A-Z");
    sidPSGSBB = new StringIncDec(acidPSGSBB);

    // PSGCVSL
    acidPSGCVSL[0] = new CharIncDec('0', "0-9");
    acidPSGCVSL[1] = new CharIncDec('0', "0-9");
    acidPSGCVSL[2] = new CharIncDec('0', "0-9");
    acidPSGCVSL[3] = new CharIncDec('0', "0-9");
    acidPSGCVSL[4] = new CharIncDec('C', "K");
    acidPSGCVSL[5] = new CharIncDec('T', "K");
    acidPSGCVSL[6] = new CharIncDec('O', "K");
    sidPSGCVSL = new StringIncDec(acidPSGCVSL);

    // PSGCCSL
    acidPSGCCSL[0] = new CharIncDec('0', "0-9");
    acidPSGCCSL[1] = new CharIncDec('0', "0-9");
    acidPSGCCSL[2] = new CharIncDec('0', "0-9");
    acidPSGCCSL[3] = new CharIncDec('0', "0-9");
    acidPSGCCSL[4] = new CharIncDec('C', "K");
    acidPSGCCSL[5] = new CharIncDec('T', "K");
    acidPSGCCSL[6] = new CharIncDec('O', "K");
    sidPSGCCSL = new StringIncDec(acidPSGCCSL);

    // PSGSOL
    acidPSGSOL[0] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[4] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGSOL[6] = new CharIncDec('0', "0-9A-Z");
    sidPSGSOL = new StringIncDec(acidPSGSOL);

    // PSGCSOL
    acidPSGCSOL[0] = new CharIncDec('0', "0-9A-Z");
    acidPSGCSOL[1] = new CharIncDec('0', "0-9A-Z");
    acidPSGCSOL[2] = new CharIncDec('0', "0-9A-Z");
    acidPSGCSOL[3] = new CharIncDec('0', "0-9A-Z");
    acidPSGCSOL[4] = new CharIncDec('0', "0-9A-Z");
    acidPSGCSOL[5] = new CharIncDec('0', "0-9A-Z");
    acidPSGCSOL[6] = new CharIncDec('0', "0-9A-Z");
    sidPSGCSOL = new StringIncDec(acidPSGCSOL);
  }

/**
 * Return the next available part number based on parameters
 * @exception MiddlewareException
 */
  public final String getNextPartNumber(String strEntityType, String strEntityTypeSubset, String strSeedValue) throws MiddlewareException {
    StringIncDec sidWhich = null;

    T.est(strEntityType != null, "strEntityType is null");
    T.est(strEntityTypeSubset != null, "strEntityTypeSubset is null");
    T.est(strSeedValue != null, "strSeedValue is null");
    D.ebug(D.EBUG_SPEW, "get a PartNumber for " + strEntityType + ":" + strEntityTypeSubset + " using seed " + strSeedValue);

    if (strEntityType.equalsIgnoreCase("PSGOF")) {
      if (strEntityTypeSubset.equalsIgnoreCase("SYSTEM")) {
        sidWhich = sidPSGOFSYSTEM;
      } else {
        sidWhich = sidPSGOF;
      }
    } else if (strEntityType.equalsIgnoreCase("PSGCVOF")) {
      sidWhich = sidPSGCVOF;
    } else if (strEntityType.equalsIgnoreCase("PSGPCSL")) {
      sidWhich = sidPSGPCSL;
    } else if (strEntityType.equalsIgnoreCase("PSGCB")) {
      sidWhich = sidPSGCB;
    } else if (strEntityType.equalsIgnoreCase("PSGCPSL")) {
      sidWhich = sidPSGCPSL;
    } else if (strEntityType.equalsIgnoreCase("PSGROF")) {
      sidWhich = sidPSGROF;
    } else if (strEntityType.equalsIgnoreCase("PSGSBB")) {
      sidWhich = sidPSGSBB;
    } else if (strEntityType.equalsIgnoreCase("PSGCVSL")) {
      sidWhich = sidPSGCVSL;
    } else if (strEntityType.equalsIgnoreCase("PSGCCSL")) {
      sidWhich = sidPSGCCSL;
    } else if (strEntityType.equalsIgnoreCase("PSGSOL")) {
      sidWhich = sidPSGSOL;
    } else if (strEntityType.equalsIgnoreCase("PSGCSOL")) {
      sidWhich = sidPSGCSOL;
    }
    T.est(sidWhich != null, "sidWhich is null");

    // If seed value matches, simply increment; else it has changed so reseed it
    if (strSeedValue.compareTo(sidWhich.getSeedValue()) == 0) {
      D.ebug(D.EBUG_SPEW, "simple increment");
      sidWhich.Increment();
    } else {
      D.ebug(D.EBUG_SPEW, "reseeding value");
      sidWhich.setSeedValue(strSeedValue);

// This is a primitive method of ensuring that a PartNumber conforming to the rules is returned if a bad seed is sent
//this does not work for K0000xx
//sidWhich.Decrement();
//sidWhich.Increment();
//      T.est(strSeedValue.equalsIgnoreCase(sidWhich.getValue()), "GeneratePartNumber:seed did not return to normal - problem!");
    }

    D.ebug(D.EBUG_SPEW, "returning " + sidWhich.getValue());

    return sidWhich.getValue();
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: GeneratePartNumber.java,v 1.8 2001/08/22 16:52:56 roger Exp $");
  }
}
