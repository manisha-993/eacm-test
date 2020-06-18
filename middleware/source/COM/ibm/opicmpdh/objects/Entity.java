//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Entity.java,v $
// Revision 1.18  2008/01/31 21:42:00  wendy
// Cleanup RSA warnings
//
// Revision 1.17  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.16  2005/01/27 03:40:37  dave
// More JTest cleanup
//
// Revision 1.14  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.13  2001/09/19 15:24:58  roger
// Remove constructors with Profile as parm
//
// Revision 1.12  2001/09/17 22:17:48  roger
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
// Revision 1.8  2001/09/17 20:24:27  roger
// Use Profile for values of Enterprise, OPENID, TranID, etc
//
// Revision 1.7  2001/08/22 16:53:06  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:46:03  roger
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
 * Entity
 * @version @date
 * @see Attribute
 * @see Blob
 * @see ControlBlock
 * @see EntitiesAndRelator
 * @see Flag
 * @see LongText
 * @see MetaEntity
 * @see MultipleFlag
 * @see Relator
 * @see SingleFlag
 * @see Text
 */
public final class Entity extends MetaEntity implements Serializable, Cloneable {

    // Class constants

    /**
     * @serial
     */
    static final long serialVersionUID = 10L;

    // Class variables
    // Instance variables
    /**
     * FIELD
     */
    public int m_iEntityID = 0;
    /**
     * FIELD
     */
    public Vector m_vctAttributes = null;


    /**
     * Main method which performs a simple test of this class
     *
     * @param args
     */
    public static void main(String args[]) {
    }

    //  /**
    //   * Construct a <code>Entity</code>
    //   */
    //  public Entity(Profile _prof, String strEntityType, int iEntityID, String strDescription, ControlBlock cbControlBlock) {
    //    m_strEnterprise = _prof.getEnterprise();
    //    m_strEntityType = strEntityType;
    //    m_iEntityID = iEntityID;
    //    m_strEntityClass = "Entity";
    //    m_strDescription = strDescription;
    //    m_cbControlBlock = cbControlBlock;
    //  }
    //

    /**
     * Construct a default <code>Entity</code>
     */
    public Entity() {
        this("", "", 0, "Entity", "", new ControlBlock());
    }

    /**
     * Construct a <code>Entity</code>
     *
     * @param strEntityType
     * @param iEntityID
     */
    public Entity(String strEntityType, int iEntityID) {
        this("", strEntityType, iEntityID, "", "", new ControlBlock());
    }

    /**
     * Construct a <code>Entity</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     */
    public Entity(String strEnterprise, String strEntityType, int iEntityID) {
        this(strEnterprise, strEntityType, iEntityID, "", "", new ControlBlock());
    }

    /**
     * Construct a <code>Entity</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param cbControlBlock
     */
    public Entity(String strEnterprise, String strEntityType, int iEntityID, ControlBlock cbControlBlock) {
        this(strEnterprise, strEntityType, iEntityID, "", "", cbControlBlock);
    }

    /**
     * Construct a <code>Entity</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strEntityClass
     * @param strDescription
     * @param strValFrom
     * @param strValTo
     * @param strEffFrom
     * @param strEffTo
     * @param openID
     */
    public Entity(String strEnterprise, String strEntityType, int iEntityID, String strEntityClass, String strDescription, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
        m_strEnterprise = strEnterprise;
        m_strEntityType = strEntityType;
        m_iEntityID = iEntityID;
        m_strEntityClass = strEntityClass;
        m_strDescription = strDescription;
        m_cbControlBlock = new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID);
    }

    /**
     * Construct a <code>Entity</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strEntityClass
     * @param strDescription
     * @param cbControlBlock
     */
    public Entity(String strEnterprise, String strEntityType, int iEntityID, String strEntityClass, String strDescription, ControlBlock cbControlBlock) {
        super(strEnterprise, strEntityType, "Entity", strDescription, cbControlBlock);
        if (strEntityClass == strDescription) {
            // Do nothing
        }
        m_iEntityID = iEntityID;
    }

    /**
     * getEntityID
     * @return int
     */
    public int getEntityID() {
        return m_iEntityID;
    }

    /**
     * setEntityID
     *
     * @param _iEntityID
     */
    public void setEntityID(int _iEntityID) {
        this.m_iEntityID = _iEntityID;
    }

    /**
     * getAttributes
     * @return Vector
     */
    public Vector getAttributes() {
        return m_vctAttributes;
    }

    /**
     * setAttributes
     *
     * @param _vctAttributes
     */
    public void setAttributes(Vector _vctAttributes) {
        this.m_vctAttributes = _vctAttributes;
    }

    /**
     * The <code>Entity</code> as a String
     * @return <code>Entity</code> definition as a <code>String</code>
     */
    public final String toString() {
        return "Enterprise:" + m_strEnterprise + " EntityType:" + m_strEntityType + " EntityID:" + m_iEntityID;
    }

    ///**
    // * Are the two objects equal?
    // */
    //  public boolean equals(Entity entAnother) {
    //    return (false);
    //
    ////    return (this.m_strEnterprise.equals(entAnother.m_strEnterprise)
    ////      && this.m_strEntityType.equals(entAnother.m_strEntityType)
    ////      && this.m_iEntityID = entAnother.m_iEntityID
    ////      && this.m_strEntityClass.equals(entAnother.m_strEntityClass)
    ////      && this.m_strDescription.equals(entAnother.m_strDescription)
    ////    );
    //  }
    //
    ///**
    // * Save the object in the database
    // */
    //  public final void saveToDatabase() {
    //  }
    //

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final String getVersion() {
        return "$Id: Entity.java,v 1.18 2008/01/31 21:42:00 wendy Exp $";
    }
}
