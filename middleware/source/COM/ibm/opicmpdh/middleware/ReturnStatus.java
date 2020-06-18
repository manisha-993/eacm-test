//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReturnStatus.java,v $
// Revision 1.7  2001/08/22 16:53:03  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:09  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:25  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;

/**
 * @version @date
 * @see Database
 * @see DatabasePool
 * @see RemoteDatabase
 * @see ReturnData
 * @see ReturnInteger
 * @see ReturnID
 */
public final class ReturnStatus extends ReturnInteger {

/**
 * Creates a stored procedure <code>ReturnStatus</code>
 */
  public ReturnStatus() {
    super(0);
  }

/**
 * Creates a stored procedure <code>ReturnStatus</code> with the specified value
 */
  public ReturnStatus(int iValue) {
    super(iValue);
  }

/**
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: ReturnStatus.java,v 1.7 2001/08/22 16:53:03 roger Exp $");
  }
}
