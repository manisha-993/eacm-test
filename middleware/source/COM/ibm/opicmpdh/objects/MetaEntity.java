//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaEntity.java,v $
// Revision 1.17  2008/01/31 21:42:00  wendy
// Cleanup RSA warnings
//
// Revision 1.16  2001/09/20 17:16:26  roger
// Fixes
//
// Revision 1.15  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.14  2001/09/19 15:24:58  roger
// Remove constructors with Profile as parm
//
// Revision 1.13  2001/09/17 22:17:49  roger
// Remove protected from constructors
//
// Revision 1.12  2001/09/17 22:07:33  roger
// Needed import
//
// Revision 1.11  2001/09/17 22:00:00  roger
// Open up constructors and members
//
// Revision 1.10  2001/09/17 21:48:34  roger
// New accessors + mutators
//
// Revision 1.9  2001/09/17 20:32:10  roger
// Remove final from methods
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
 * MetaEntity
 * @version @date
 * @see Attribute
 * @see Blob
 * @see ControlBlock
 * @see EntitiesAndRelator
 * @see Entity
 * @see Flag
 * @see LongText
 * @see MultipleFlag
 * @see Relator
 * @see SingleFlag
 * @see Text
 */
public class MetaEntity implements Serializable, Cloneable {

  // Class constants

  /**
   * @serial
   */
  static final long serialVersionUID = 10L;

  // Class variables
  // Instance variables
  public String m_strEnterprise = null;
  public String m_strEntityType = null;
  public String m_strEntityClass = null;
  public String m_strDescription = null;
  public ControlBlock m_cbControlBlock = null;
  public Object m_objReference = null;

  /**
   * Main method which performs a simple test of this class
   */
  public static void main(String args[]) {
  }

//  /**
//   * Construct a <code>MetaEntity</code>
//   */
//  public MetaEntity(Profile _prof, String strEntityType, String strEntityClass, String strDescription, ControlBlock cbControlBlock) {
//    m_strEnterprise = _prof.getEnterprise();
//    m_strEntityType = strEntityType;
//    m_strEntityClass = strEntityClass;
//    m_strDescription = strDescription;
//    m_cbControlBlock = cbControlBlock;
//  }
//

  /**
   * Construct a default <code>MetaEntity</code>
   */
  public MetaEntity() {
    this("", "", "", "", new ControlBlock());
  }

  /**
   * Construct a <code>MetaEntity</code>
   */
  public MetaEntity(String strEnterprise, String strEntityType, String strEntityClass, String strDescription, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
    m_strEnterprise = strEnterprise;
    m_strEntityType = strEntityType;
    m_strEntityClass = strEntityClass;
    m_strDescription = strDescription;
    m_cbControlBlock = new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID);
  }

  /**
   * Construct a <code>MetaEntity</code>
   */
  public MetaEntity(String strEnterprise, String strEntityType, String strEntityClass, String strDescription, ControlBlock cbControlBlock) {
    m_strEnterprise = strEnterprise;
    m_strEntityType = strEntityType;
    m_strEntityClass = strEntityClass;
    m_strDescription = strDescription;
    m_cbControlBlock = cbControlBlock;
  }

  /**
   *
   */
  public String getEnterprise() {
    return m_strEnterprise;
  }

  /**
   *
   */
  public void setEnterprise(String m_strEnterprise) {
    this.m_strEnterprise = m_strEnterprise;
  }

  /**
   *
   */
  public String getEntityType() {
    return m_strEntityType;
  }

  /**
   *
   */
  public void setEntityType(String m_strEntityType) {
    this.m_strEntityType = m_strEntityType;
  }

  /**
   *
   */
  public String getEntityClass() {
    return m_strEntityClass;
  }

  /**
   *
   */
  public void setEntityClass(String m_strEntityClass) {
    this.m_strEntityClass = m_strEntityClass;
  }

  /**
   *
   */
  public String getDescription() {
    return m_strDescription;
  }

  /**
   *
   */
  public void setDescription(String m_strDescription) {
    this.m_strDescription = m_strDescription;
  }

  /**
   *
   */
  public ControlBlock getControlBlock() {
    return m_cbControlBlock;
  }

  /**
   *
   */
  public void setControlBlock(ControlBlock m_cbControlBlock) {
    this.m_cbControlBlock = m_cbControlBlock;
  }

  /**
   *
   */
  public Object getObjectReference() {
    return m_objReference;
  }

  /**
   *
   */
  public void setObjectReference(Object m_objReference) {
    this.m_objReference = m_objReference;
  }

  /**
   * The <code>MetaEntity</code> as a String
   * @return <code>MetaEntity</code> definition as a <code>String</code>
   */
  public String toString() {
    return new String(" Enterprise:" + m_strEnterprise + " EntityType:" + m_strEntityType + " EntityClass:" + m_strEntityClass + " Description:" + m_strDescription + m_cbControlBlock);
  }

///**
// * Returns a clone of the object
// */
//  public Object clone() {
//    Object objClone = null;
//
//    try {
//      objClone = super.clone();
//    } catch (CloneNotSupportedException x) {}
//    ;
//
//    return objClone;
//  }
//

  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public String getVersion() {
    return new String("$Id: MetaEntity.java,v 1.17 2008/01/31 21:42:00 wendy Exp $");
  }
}
