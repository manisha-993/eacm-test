//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EntityAndRelatorTransaction.java,v $
// Revision 1.11  2008/01/31 21:54:22  wendy
// Cleanup RSA warnings
//
// Revision 1.10  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.9  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.8  2005/01/27 02:02:17  dave
// Jtest format cleanup
//
// Revision 1.7  2001/08/22 16:52:51  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 15:39:42  roger
// Misc clean up
//
// Revision 1.5  2001/03/21 00:01:06  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:19  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.containers;


import COM.ibm.opicmpdh.objects.Entity;
import COM.ibm.opicmpdh.objects.Relator;
import java.io.Serializable;


/**
 * This is a container for data required in an Entity and Relator transaction.
 * An Entity and Relator transaction creates (2) new Entities and (1) new Relator
 * between the newly created Entities based on the Left and Right Entities and
 * the relator between them
 * @version @date
 * @see COM.ibm.opicmpdh.objects.Entity
 * @see COM.ibm.opicmpdh.objects.Relator
 * @deprecated
 */
public class EntityAndRelatorTransaction implements Serializable, Cloneable {

    // Instance variables

    /**
     * @serial
     */
    static final long serialVersionUID = 1L;
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
     * Creates a stored procedure <code>EntityAndRelatorTransaction</code> object
     *
     * @param entLeft
     * @param entRight
     * @param relRelator
     */
    public EntityAndRelatorTransaction(Entity entLeft, Entity entRight, Relator relRelator) {
        m_entLeft = entLeft;
        m_entRight = entRight;
        m_relRelator = relRelator;
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: EntityAndRelatorTransaction.java,v 1.11 2008/01/31 21:54:22 wendy Exp $";
    }
}
