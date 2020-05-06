/*
 * Created on Jan 7, 2005
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 *
 */
package com.ibm.transform.oim.eacm.xalan;


/**
 * An interface to allow setting the Entity Type and Entity ID.
 *
 * <pre>
 * $Log: EntityParam.java,v $
 * Revision 1.2  2006/01/26 15:28:14  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:09:29  wendy
 * New pkg
 *
 * Revision 1.1  2005/02/23 21:13:02  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public interface EntityParam {
    /**
     * Sets the Entity Type. It returns true if set. false otherwise.
     * e.g. if the entity type was null it would ne be set.
     *
     * @param entityType
     * @return boolean
     */
    boolean setEntityType(String entityType);
    /**
     * Sets the Entity ID. It returns true if set. false otherwise.
     * e.g. if the entity id  was less than 0 it would not be set.
     *
     * @param entityID
     * @return boolean
     */
    boolean setEntityID(int entityID);

}
