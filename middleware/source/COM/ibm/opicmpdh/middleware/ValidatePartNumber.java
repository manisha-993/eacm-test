//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ValidatePartNumber.java,v $
// Revision 1.8  2001/08/22 16:53:05  roger
// Removed author RM
//
// Revision 1.7  2001/03/26 16:33:23  roger
// Misc formatting clean up
//
// Revision 1.6  2001/03/21 00:01:10  roger
// Implement java class file branding in getVersion method
//
// Revision 1.5  2001/03/16 15:52:26  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

/**
 * @version @date
 */
public final class ValidatePartNumber {

  // Class variables
  private String pmPSGOF = new String("???????");
  private String pmPSGOFSYSTEM = new String("????@??");
  private String pmPSGCVOF = new String("####CTO");
  private String pmPSGPCSL = new String("####~??");
  private String pmPSGCPSL = new String("####~??");
  private String pmPSGCB = new String("=??????");
  private String pmPSGROF = new String("!????xx");
  private String pmPSGSBB = new String("???????");
  private String pmPSGCVSL = new String("####CTO");
  private String pmPSGCCSL = new String("####CTO");
  private String pmPSGSOL = new String("???????"); // may not be complete or accurate
  private String pmPSGCSOL = new String("???????");// may not be complete or accurate

  // Instance variables
  private PatternMatch pmPattern = new PatternMatch();

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String args[]) {
  }

/**
 * The constructor
 */
  public ValidatePartNumber() {
  }

/**
 * Retrieve the pattern match failure message (if any)
 */
  public final String getMessage() {
    return pmPattern.getMessage();
  }

/**
 * Is this a valid, unused Part Number for the specified Entity Type?
 * @exception MiddlewareException
 */
  public final boolean isValidPartNumber(String strEntityType, String strEntityTypeSubset, String strPartNumber) throws MiddlewareException {
    boolean bResult = false;
    String strPattern = null;

    T.est(strEntityType != null, "strEntityType is null");
    T.est(strEntityTypeSubset != null, "strEntityTypeSubset is null");
    T.est(strPartNumber != null, "strPartNumber is null");
    D.ebug(D.EBUG_SPEW, "EntityType: " + strEntityType);
    D.ebug(D.EBUG_SPEW, "EntityTypeSubset: " + strEntityTypeSubset);
    D.ebug(D.EBUG_SPEW, "PartNumber: " + strPartNumber);

    if (strEntityType.equalsIgnoreCase("PSGOF")) {
      if (strEntityTypeSubset.equalsIgnoreCase("SYSTEM")) {
        strPattern = pmPSGOFSYSTEM;
      } else {
        strPattern = pmPSGOF;
      }
    } else if (strEntityType.equalsIgnoreCase("PSGCVOF")) {
      strPattern = pmPSGCVOF;
    } else if (strEntityType.equalsIgnoreCase("PSGPCSL")) {
      strPattern = pmPSGPCSL;
    } else if (strEntityType.equalsIgnoreCase("PSGCPSL")) {
      strPattern = pmPSGCPSL;
    } else if (strEntityType.equalsIgnoreCase("PSGCB")) {
      strPattern = pmPSGCB;
    } else if (strEntityType.equalsIgnoreCase("PSGROF")) {
      strPattern = pmPSGROF;
    } else if (strEntityType.equalsIgnoreCase("PSGSBB")) {
      strPattern = pmPSGSBB;
    } else if (strEntityType.equalsIgnoreCase("PSGCVSL")) {
      strPattern = pmPSGCVSL;
    } else if (strEntityType.equalsIgnoreCase("PSGCCSL")) {
      strPattern = pmPSGCCSL;
    } else if (strEntityType.equalsIgnoreCase("PSGSOL")) {
      strPattern = pmPSGSOL;
    } else if (strEntityType.equalsIgnoreCase("PSGCSOL")) {
      strPattern = pmPSGCSOL;
    } else {
      D.ebug("EntityType " + strEntityType + " is not supported in isValidPartNumber");
    }

    T.est(strPattern != null, "strPattern is null");

    bResult = pmPattern.isMatch(strPattern, strPartNumber);

    D.ebug(D.EBUG_SPEW, "compare PartNumber '" + strPartNumber + "' to pattern '" + strPattern + "' for EntityType '" + strEntityType + "' = " + bResult);

    return bResult;
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: ValidatePartNumber.java,v 1.8 2001/08/22 16:53:05 roger Exp $");
  }
}
