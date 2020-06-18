//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: OPICMObject.java,v $
// Revision 1.4  2005/01/26 23:20:02  dave
// Jtest clean up
//
// Revision 1.3  2001/03/21 20:39:49  roger
// Added branded feature to interfaces
//
// Revision 1.2  2001/03/16 16:08:59  roger
// Added Log keyword, and standard copyright
//


package COM.ibm.opicmpdh.transactions;


/**
 * Ensures that a hashkey is thought of
 * when implementing OPICM objects
 */
public interface OPICMObject {

    /**
     * CLASS_BRAND
     */
    String CLASS_BRAND =
        "$Id: OPICMObject.java,v 1.4 2005/01/26 23:20:02 dave Exp $";

    /**
     * This is used to pull out the key identifier of the object.
     *
     * @return String
     */
    String hashkey();
}
