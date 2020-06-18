//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LongTextAttribute.java,v $
// Revision 1.2  2005/03/04 21:35:51  dave
// Jtest work
//
// Revision 1.1  2002/02/12 23:18:13  dave
// added more attribute objects
//
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * LongTextAttribute
 */
public class LongTextAttribute extends EANTextAttribute {

    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: LongTextAttribute.java,v 1.2 2005/03/04 21:35:51 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Manages EANTextAttributes in the e-announce data model
     *
     * @param _edf
     * @param _prof
     * @param _mlta
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public LongTextAttribute(EANDataFoundation _edf, Profile _prof, MetaLongTextAttribute _mlta) throws MiddlewareRequestException {
        super(_edf, _prof, _mlta);
    }

}
