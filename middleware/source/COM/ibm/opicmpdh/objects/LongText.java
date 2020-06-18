//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LongText.java,v $
// Revision 1.15  2008/01/31 21:42:00  wendy
// Cleanup RSA warnings
//
// Revision 1.14  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.13  2001/09/19 15:24:58  roger
// Remove constructors with Profile as parm
//
// Revision 1.12  2001/09/17 23:18:55  roger
// More undo
//
// Revision 1.11  2001/09/17 22:17:49  roger
// Remove protected from constructors
//
// Revision 1.10  2001/09/17 22:07:33  roger
// Needed import
//
// Revision 1.9  2001/09/17 22:00:00  roger
// Open up constructors and members
//
// Revision 1.8  2001/09/17 20:24:27  roger
// Use Profile for values of Enterprise, OPENID, TranID, etc
//
// Revision 1.7  2001/08/22 16:53:07  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:46:04  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:11  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:27  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.objects;

import java.io.Serializable;

/**
 * LongText
 * @version @date
 * @see Attribute
 * @see Blob
 * @see ControlBlock
 * @see EntitiesAndRelator
 * @see Entity
 * @see Flag
 * @see MetaEntity
 * @see MultipleFlag
 * @see Relator
 * @see SingleFlag
 * @see Text
 */
public final class LongText extends Attribute implements Serializable, Cloneable {

  // Class constants

  /**
   * @serial
   */
  static final long serialVersionUID = 10L;

  // Class variables
  // Instance variables

  /**
   * Main method which performs a simple test of this class
   */
  public static void main(String args[]) {
  }

//  /**
//   * Construct a <code>LongText</code>
//   */
//  public LongText(Profile _prof, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, ControlBlock cbControlBlock) {
////    super(_prof.getEnterprise(), strEntityType, iEntityID, strAttributeCode, strAttributeValue, cbControlBlock);
//  }
//

  /**
   * Construct a default <code>LongText</code>
   */
  public LongText() {
    this("", "", 0, "", "", 0, new ControlBlock());
  }

  /**
   * Construct a <code>LongText</code>
   */
  public LongText(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID) {
    super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID);
  }

  /**
   * Construct a <code>LongText</code>
   */
  public LongText(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, ControlBlock cbControlBlock) {
    super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbControlBlock);
  }

  /**
   * Construct a <code>LongText</code>
   */
  public LongText(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
    super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID));
  }

  /**
   * The <code>LongText</code> as a String
   * @return <code>LongText</code> definition as a <code>String</code>
   */
  public final String toString() {
    return super.toString();
  }

///**
// * Save the object in the database
// */
//  public void saveToDatabase() {
//  }
//

  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public final String getVersion() {
    return new String("$Id: LongText.java,v 1.15 2008/01/31 21:42:00 wendy Exp $");
  }
}
