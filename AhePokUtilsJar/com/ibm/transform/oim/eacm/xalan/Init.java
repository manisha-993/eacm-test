/**
 * Created on Jan 10, 2005
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 */
package com.ibm.transform.oim.eacm.xalan;


/**
 * Seperates lifecycle events from Table interface.
 *
 * <pre>
 * $Log: Init.java,v $
 * Revision 1.3  2006/10/19 21:29:43  chris
 * Interface changes
 *
 * Revision 1.2  2006/01/26 15:28:14  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:09:29  wendy
 * New pkg
 *
 * Revision 1.1  2005/02/23 21:13:03  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public interface Init {

    /**
     * Seperates initialization from the Table creation. Middleware calls should be done in this method.
     * This is called by the style sheet extension when it gets the table
     * @return true if initialization successfull
     */
    boolean initialize();

}
