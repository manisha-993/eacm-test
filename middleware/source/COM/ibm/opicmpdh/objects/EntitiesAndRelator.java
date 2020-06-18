//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EntitiesAndRelator.java,v $
// Revision 1.17  2008/01/31 21:42:00  wendy
// Cleanup RSA warnings
//
// Revision 1.16  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.15  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.14  2005/01/27 02:42:20  dave
// Jtest format cleanup
//
// Revision 1.13  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.12  2001/09/17 22:07:32  roger
// Needed import
//
// Revision 1.11  2001/09/17 22:00:00  roger
// Open up constructors and members
//
// Revision 1.10  2001/09/17 21:48:34  roger
// New accessors + mutators
//
// Revision 1.9  2001/09/17 20:24:27  roger
// Use Profile for values of Enterprise, OPENID, TranID, etc
//
// Revision 1.8  2001/08/22 16:53:06  roger
// Removed author RM
//
// Revision 1.7  2001/03/26 16:46:03  roger
// Misc formatting clean up
//
// Revision 1.6  2001/03/21 00:01:11  roger
// Implement java class file branding in getVersion method
//
// Revision 1.5  2001/03/16 15:52:27  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.objects;

import java.io.Serializable;


/**
 * This is a container for data required in an Entity and Relator transaction.
 * An Entity and Relator transaction creates (2) new Entities and (1) new Relator
 * between the newly created Entities based on the Left and Right Entities and
 * the relator between them
 * @version @date
 * @see COM.ibm.opicmpdh.objects.Entity
 * @see COM.ibm.opicmpdh.objects.Relator
 */
public class EntitiesAndRelator implements Serializable, Cloneable {

    // Instance variables

    /**
     * @serial
     */
    static final long serialVersionUID = 10L;
    /**
     * FIELD
     */
    public Entity m_entLeft = null;
    /**
     * FIELD
     */
    public Entity m_entRight = null;
    /**
     * FIELD
     */
    public Relator m_relRelator = null;


    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates a stored procedure <code>EntitiesAndRelator</code> object
     *
     * @param entLeft
     * @param entRight
     * @param relRelator
     */
    public EntitiesAndRelator(Entity entLeft, Entity entRight, Relator relRelator) {
        m_entLeft = entLeft;
        m_entRight = entRight;
        m_relRelator = relRelator;
    }

    /**
     * getLeftEntity
     * @return Entity
     */
    public Entity getLeftEntity() {
        return m_entLeft;
    }

    /**
     * setLeftEntity
     *
     * @param _entLeft
     */
    public void setLeftEntity(Entity _entLeft) {
        this.m_entLeft = _entLeft;
    }

    /**
     * getRightEntity
     * @return Entity
     */
    public Entity getRightEntity() {
        return m_entRight;
    }

    /**
     * setRightEntity
     *
     * @param _entRight
     */
    public void setRightEntity(Entity _entRight) {
        this.m_entRight = _entRight;
    }

    /**
     * getRelator
     * @return Relator
     */
    public Relator getRelator() {
        return m_relRelator;
    }

    /**
     * setRelator
     *
     * @param _relRelator
     */
    public void setRelator(Relator _relRelator) {
        this.m_relRelator = _relRelator;
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final String getVersion() {
        return "$Id: EntitiesAndRelator.java,v 1.17 2008/01/31 21:42:00 wendy Exp $";
    }
}
