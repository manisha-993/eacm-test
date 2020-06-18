//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReturnInteger.java,v $
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
 * @see ReturnStatus
 * @see ReturnID
 */
public abstract class ReturnInteger implements Serializable, Cloneable {

  // Instance variables
  /**
   * @serial
   */
  static final long serialVersionUID = 1L;
  public int m_intReturnInteger = -1;

/**
 * Creates a <code>ReturnInteger</code>
 */
  public ReturnInteger() {
    this.m_intReturnInteger = 0;
  }

/**
 * Creates a <code>ReturnInteger</code> with the specified value
 */
  public ReturnInteger(int iValue) {
    this.m_intReturnInteger = iValue;
  }

/**
 * @return the date/time this class was generated
 */
  public String getVersion() {
    return new String("$Id: ReturnInteger.java,v 1.7 2001/08/22 16:53:03 roger Exp $");
  }

/**
 * @return <code>ReturnInteger</code> value as an int
 */
  public final int intValue() {
    return this.m_intReturnInteger;
  }

/**
 * @return <code>ReturnInteger</code> value as a String
 */
  public final String toString() {
    return String.valueOf(this.m_intReturnInteger);
  }

/**
 * Returns a clone of the object
 */
  public Object clone() {
    Object objClone = null;

    try {
      objClone = super.clone();
    } catch (CloneNotSupportedException x) {}
    ;

    return objClone;
  }

/**
 * Set the value of the <code>ReturnInteger</code>
 * @param set the value of the return integer
 */
  public final void setValue(int iValue) {
    this.m_intReturnInteger = iValue;
  }
}
