//
// Copyright (c) 2001,2010 International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SingleFlag.java,v $
// Revision 1.18  2010/07/12 21:02:44  wendy
// BH SR87, SR655 - extended combounique rule
//
// Revision 1.17  2008/01/31 21:42:00  wendy
// Cleanup RSA warnings
//
// Revision 1.16  2003/07/10 18:35:54  dave
// fixes for null pointer
//
// Revision 1.15  2003/05/19 16:03:41  dave
// Clean up for descriptions, and exception flagging
//
// Revision 1.14  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.13  2001/09/19 15:24:59  roger
// Remove constructors with Profile as parm
//
// Revision 1.12  2001/09/17 22:17:50  roger
// Remove protected from constructors
//
// Revision 1.11  2001/09/17 22:07:33  roger
// Needed import
//
// Revision 1.10  2001/09/17 22:00:00  roger
// Open up constructors and members
//
// Revision 1.9  2001/09/17 20:24:28  roger
// Use Profile for values of Enterprise, OPENID, TranID, etc
//
// Revision 1.8  2001/08/22 16:53:08  roger
// Removed author RM
//
// Revision 1.7  2001/04/03 21:18:38  dave
// misc @see tag clean up
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
 * SingleFlag
 * @version @date
 * @see Attribute
 * @see Blob
 * @see ControlBlock
 * @see EntitiesAndRelator
 * @see Entity
 * @see Flag
 * @see LongText
 * @see MetaEntity
 * @see MultipleFlag
 * @see Relator
 * @see Text
 */
public final class SingleFlag extends Flag implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

/**
   * Main method which performs a simple test of this class
   */
  public static void main(String args[]) {
  }
  
  public String m_strFlagDescription = "";
  public String m_strDescription = "";

//  /**
//   * Construct a <code>SingleFlag</code>
//   */
//  public SingleFlag(Profile _prof, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, ControlBlock cbControlBlock) {
//    super(_prof.getEnterprise(), strEntityType, iEntityID, strAttributeCode, strAttributeValue, _prof.getReadLanguage().getNLSID(), cbControlBlock);
//  }
//

  /**
   * Construct a default <code>SingleFlag</code>
   */
  public SingleFlag() {
    super();
  }

  /**
   * Construct a <code>SingleFlag</code>
   */
  public SingleFlag(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID) {
    super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID);
  }

  /**
   * Construct a <code>SingleFlag</code>
   */
  public SingleFlag(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, ControlBlock cbControlBlock) {
    super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbControlBlock);
  }

  /**
   * Construct a <code>SingleFlag</code>
   */
  public SingleFlag(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
    super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID));
  }

  /**
   * The <code>SingleFlag</code> as a String
   * @return <code>SingleFlag</code> definition as a <code>String</code>
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
    return "$Id: SingleFlag.java,v 1.18 2010/07/12 21:02:44 wendy Exp $";
  }
}
