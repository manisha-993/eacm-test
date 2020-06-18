//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Relator.java,v $
// Revision 1.16  2008/01/31 21:42:00  wendy
// Cleanup RSA warnings
//
// Revision 1.15  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.14  2001/09/19 15:24:59  roger
// Remove constructors with Profile as parm
//
// Revision 1.13  2001/09/17 22:28:13  roger
// Undo more
//
// Revision 1.12  2001/09/17 22:17:49  roger
// Remove protected from constructors
//
// Revision 1.11  2001/09/17 22:07:33  roger
// Needed import
//
// Revision 1.10  2001/09/17 22:00:00  roger
// Open up constructors and members
//
// Revision 1.9  2001/09/17 21:48:34  roger
// New accessors + mutators
//
// Revision 1.8  2001/09/17 20:24:28  roger
// Use Profile for values of Enterprise, OPENID, TranID, etc
//
// Revision 1.7  2001/08/22 16:53:08  roger
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
import java.util.Vector;

/**
 * Relator
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
 * @see SingleFlag
 * @see Text
 */
public final class Relator extends MetaEntity implements Serializable, Cloneable {

  // Class constants

  /**
   * @serial
   */
  static final long serialVersionUID = 10L;

  // Class variables
  // Instance variables
  public int m_iEntityID = 0;
  public String m_strEntity1Type = null;
  public int m_iEntity1ID = 0;
  public String m_strEntity2Type = null;
  public int m_iEntity2ID = 0;
  public Vector m_vctAttributes = null;

  /**
   * Main method which performs a simple test of this class
   */
  public static void main(String args[]) {
  }

//  /**
//   * Construct a <code>Relator</code>
//   */
//  public Relator(Profile _prof, String strEntityType, int iEntityID, String strEntity1Type, int iEntity1ID, String strEntity2Type, int iEntity2ID, ControlBlock cbControlBlock) {
//
//    m_strEnterprise = _prof.getEnterprise();
//    m_strEntityType = strEntityType;
//    m_iEntityID = iEntityID;
//    m_strEntityClass = "Relator";
////    m_strDescription = strDescription;
//    m_strEntity1Type = strEntity1Type;
//    m_iEntity1ID = iEntity1ID;
//    m_strEntity2Type = strEntity2Type;
//    m_iEntity2ID = iEntity2ID;
//    m_cbControlBlock = cbControlBlock;
//  }
//

  /**
   * Construct a default <code>Relator</code>
   */
  public Relator() {
    this("", "", 0, "Relator", "", "", 0, "", 0, new ControlBlock());
  }

  /**
   * Construct a <code>Relator</code>
   */
  public Relator(String strEntityType, int iEntityID, String strEntity1Type, int iEntity1ID, String strEntity2Type, int iEntity2ID) {
    this("", strEntityType, iEntityID, "Relator", "", strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, new ControlBlock());
  }

  /**
   * Construct a <code>Relator</code>
   */
  public Relator(String strEnterprise, String strEntityType, int iEntityID, String strEntity1Type, int iEntity1ID, String strEntity2Type, int iEntity2ID) {
    this(strEnterprise, strEntityType, iEntityID, "Relator", "", strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, new ControlBlock());
  }

  /**
   * Construct a <code>Relator</code>
   */
  public Relator(String strEnterprise, String strEntityType, int iEntityID, String strEntity1Type, int iEntity1ID, String strEntity2Type, int iEntity2ID, ControlBlock cbControlBlock) {
    this(strEnterprise, strEntityType, iEntityID, "Relator", "", strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, cbControlBlock);
  }

  /**
   * Construct a <code>Relator</code>
   */
  public Relator(String strEnterprise, String strEntityType, int iEntityID, String strEntityClass, String strDescription, String strEntity1Type, int iEntity1ID, String strEntity2Type, int iEntity2ID, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {

    m_strEnterprise = strEnterprise;
    m_strEntityType = strEntityType;
    m_iEntityID = iEntityID;
    m_strEntityClass = strEntityClass;
    m_strDescription = strDescription;
    m_strEntity1Type = strEntity1Type;
    m_iEntity1ID = iEntity1ID;
    m_strEntity2Type = strEntity2Type;
    m_iEntity2ID = iEntity2ID;
    m_cbControlBlock = new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID);
  }

  /**
   * Construct a <code>Relator</code>
   */
  public Relator(String strEnterprise, String strEntityType, int iEntityID, String strEntityClass, String strDescription, String strEntity1Type, int iEntity1ID, String strEntity2Type, int iEntity2ID, ControlBlock cbControlBlock) {
    super(strEnterprise, strEntityType, "Relator", strDescription, cbControlBlock);

    m_iEntityID = iEntityID;
    m_strEntity1Type = new String(strEntity1Type);
    m_iEntity1ID = iEntity1ID;
    m_strEntity2Type = new String(strEntity2Type);
    m_iEntity2ID = iEntity2ID;
  }

  /**
   *
   */
  public int getEntityID() {
    return m_iEntityID;
  }

  /**
   *
   */
  public void setEntityID(int m_iEntityID) {
    this.m_iEntityID = m_iEntityID;
  }

  /**
   *
   */
  public String getEntity1Type() {
    return m_strEntity1Type;
  }

  /**
   *
   */
  public void setEntity1Type(String m_strEntity1Type) {
    this.m_strEntity1Type = m_strEntity1Type;
  }

  /**
   *
   */
  public int getEntity1ID() {
    return m_iEntity1ID;
  }

  /**
   *
   */
  public void setEntity1ID(int m_iEntity1ID) {
    this.m_iEntity1ID = m_iEntity1ID;
  }

  /**
   *
   */
  public String getEntity2Type() {
    return m_strEntity2Type;
  }

  /**
   *
   */
  public void setEntity2Type(String m_strEntity2Type) {
    this.m_strEntity2Type = m_strEntity2Type;
  }

  /**
   *
   */
  public int getEntity2ID() {
    return m_iEntity2ID;
  }

  /**
   *
   */
  public void setEntity2ID(int m_iEntity2ID) {
    this.m_iEntity2ID = m_iEntity2ID;
  }

  /**
   *
   */
  public Vector getAttributes() {
    return m_vctAttributes;
  }

  /**
   *
   */
  public void setAttributes(Vector m_vctAttributes) {
    this.m_vctAttributes = m_vctAttributes;
  }

  /**
   * The <code>Relator</code> as a String
   * @return <code>Relator</code> definition as a <code>String</code>
   */
  public final String toString() {
    return new String("Enterprise:" + m_strEnterprise + " EntityType:" + m_strEntityType + " EntityID:" + m_iEntityID + " EntityClass:" + m_strEntityClass + " Description:" + m_strDescription + " Entity1Type:" + m_strEntity1Type + " Entity1ID:" + m_iEntity1ID + " Entity2Type:" + m_strEntity2Type + " Entity2ID:" + m_iEntity2ID + " Control Block:" + m_cbControlBlock);
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
    return new String("$Id: Relator.java,v 1.16 2008/01/31 21:42:00 wendy Exp $");
  }
}
